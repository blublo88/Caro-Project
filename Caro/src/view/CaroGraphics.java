package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;

import model.MyImage;
import model.MoiTruong;
import model.Quaylai;



public class CaroGraphics extends JPanel {

    private static final long serialVersionUID = 1L; // Số phiên bản serial version
    public final static int sizeCell = 30; // Kích thước ô cờ (cell) trong trò chơi
    public final static int row = 18; // Số dòng cờ
    public final static int col = 18; // Số cột cờ
    public final static int width = sizeCell * col + 1; // Chiều rộng của giao diện cờ
    public final static int height = sizeCell * row + 1; // Chiều cao của giao diện cờ

    private int sizeImg = sizeCell - 2; // Kích thước hình ảnh X và O
    public boolean player, playerRoot; // Biến người chơi hiện tại và người chơi đầu tiên
    private MoiTruong moiTruong; // Đối tượng quản lý trạng thái của trò chơi

    private MyImage myImage = new MyImage(); // Đối tượng quản lý hình ảnh
    private Icon iconActive; // Icon hiển thị người chơi đang thực hiện nước đi
    private UndoManager undoManager = new UndoManager(); // Đối tượng quản lý hoạt động undo/redo
    protected Vector<Point> pointVector; // Danh sách các điểm (ô cờ) đã được đánh

    private int winer = 0; // Biến lưu trữ kết quả của trò chơi (người chiến thắng)

    public int getWiner() {
        return winer;
    }

    public void setWiner(int winer) {
        this.winer = winer;
    }

    // Constructor của lớp CaroGraphics
    public CaroGraphics() {
        makeIcon(); // Tạo các biểu tượng (icons)
        setPreferredSize(new Dimension(width, height)); // Đặt kích thước ảnh nền cho giao diện
        init(); // Khởi tạo trạng thái ban đầu của trò chơi
    }

    // Phương thức khởi tạo trạng thái ban đầu của trò chơi
    public void init() {
        winer = 0; // Đặt kết quả trò chơi ban đầu là không có người chiến thắng
        moiTruong = new MoiTruong(); // Khởi tạo đối tượng quản lý trạng thái của trò chơi
        player = playerRoot; // Đặt người chơi hiện tại bằng người chơi đầu tiên
        pointVector = new Vector<Point>(); // Khởi tạo danh sách các điểm (ô cờ) đã được đánh
        repaint(); // Vẽ lại giao diện để hiển thị trạng thái ban đầu của trò chơi
    }

    // Phương thức vẽ hình ảnh ô lưới bàn cờ
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(238, 238, 238)); // Đặt màu nền của giao diện

        for (int i = 0; i <= row; i++) { // Vẽ lưới ô cờ bằng các đường kẻ
            g.drawLine(i * sizeCell, 0, i * sizeCell, height - 1);
            g.drawLine(0, i * sizeCell, width - 1, i * sizeCell);
        }
        drawImg(g); // Vẽ hình ảnh (X và O) trên ô cờ
        System.out.println("a"); // In ra màn hình (dùng cho mục đích debug)
    }

    // Phương thức vẽ hình ảnh (X và O) trên ô cờ
    private void drawImg(Graphics g) {
        boolean player = playerRoot; // Biến người chơi hiện tại, bắt đầu bằng người chơi đầu tiên (playerRoot)
        for (int i = 0; i < pointVector.size(); i++) {
            Image image = player ? myImage.imgCross : myImage.imgNought; // Chọn hình ảnh (X hoặc O) tương ứng với người chơi
            Point point = convertPointToCaro(convertPoint(pointVector.get(i))); // Chuyển đổi tọa độ màn hình thành tọa độ của ô cờ
            g.drawImage(image, point.x, point.y, null); // Vẽ hình ảnh lên ô cờ tại tọa độ đã chuyển đổi
            player = !player; // Chuyển đổi người chơi cho lượt tiếp theo
        }
    }

    // Phương thức chuyển đổi tọa độ màn hình thành tọa độ của ô cờ
    private Point convertPoint(Point point) {
        int x, y;
        int deviation = 1; // Độ lệch cho phép (được sử dụng để xác định xem tọa độ màn hình thuộc ô cờ nào)
        x = (point.x % sizeCell > deviation) ? (point.x / sizeCell * sizeCell + sizeCell / 2)
                : (point.x / sizeCell * sizeCell - sizeCell / 2);
        y = (point.y % sizeCell > deviation) ? (point.y / sizeCell * sizeCell + sizeCell / 2)
                : (point.y / sizeCell * sizeCell - sizeCell / 2);
        return new Point(x, y); // Trả về tọa độ đã chuyển đổi
    }

    // Phương thức chuyển đổi tọa độ màn hình thành tọa độ trong ma trận ô cờ (Matrix)
    private Point convertPointToMaxtrix(Point point) {
        return new Point(point.y / sizeCell, point.x / sizeCell); // Trả về tọa độ trong ma trận ô cờ
    }

    // Phương thức chuyển đổi tọa độ màn hình thành tọa độ để hiển thị hình ảnh trung tâm trên ô cờ
    private Point convertPointToCaro(Point point) {
        return new Point(point.x - sizeImg / 2, point.y - sizeImg / 2); // Trả về tọa độ đã chuyển đổi để căn giữa hình ảnh
    }

    // Phương thức để cập nhật trạng thái của trò chơi
    public void setStatus() {
        // Đặt biểu tượng (icon) cho người chơi đang thực hiện nước đi (được hiển thị trên giao diện)
        CaroFrame.lbStatusO.setIcon(iconActive);
        CaroFrame.lbStatusX.setIcon(iconActive);

        // Xác định người chơi hiện tại và hiển thị biểu tượng tương ứng cho họ
        if (player) {
            CaroFrame.lbStatusX.setEnabled(true);
            CaroFrame.lbStatusO.setEnabled(false);
        } else {
            CaroFrame.lbStatusX.setEnabled(false);
            CaroFrame.lbStatusO.setEnabled(true);
        }
    }

    // Phương thức để tạo biểu tượng (icon) cho người chơi đang thực hiện nước đi
    private void makeIcon() {
        // Tạo biểu tượng (icon) từ hình ảnh "active.png" và thay đổi kích thước của nó thành 20x20 pixel
        iconActive = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon("active.png"), 20, 20));
    }

    // Phương thức xử lý sự kiện khi người chơi nhấp chuột vào ô cờ
    void actionClick(Point point) {
        Point pointTemp = convertPoint(point); // Chuyển đổi tọa độ màn hình thành tọa độ ô cờ

        // Cập nhật ma trận trò chơi và kiểm tra nếu nước đi hợp lệ
        if (moiTruong.updateMatrix(player, convertPointToMaxtrix(pointTemp))) {
            pointVector.addElement(point); // Thêm điểm đã đánh vào danh sách lưu trữ

            // Tạo một sự kiện "UndoableEdit" và thông báo cho UndoManager
            undoManager.undoableEditHappened(new UndoableEditEvent(this,
                    new Quaylai(point, pointVector)));

            repaint(); // Vẽ lại giao diện để hiển thị nước đi mới
            player = !player; // Chuyển đổi người chơi cho lượt tiếp theo
            setStatus(); // Cập nhật trạng thái (status) của trò chơi

            if (moiTruong.getWin() > 0) {
                winer = moiTruong.getWin(); // Kiểm tra nếu có người chiến thắng
            }
        }
    }

    // Phương thức để hoàn tác (undo) nước đi cuối cùng
    public void undo() {
        player = !player; // Chuyển đổi người chơi để hoàn tác nước đi của người trước đó
        Point point = pointVector.get(pointVector.size() - 1);
        point = convertPointToMaxtrix(convertPoint(point)); // Chuyển đổi tọa độ màn hình thành tọa độ trong ma trận ô cờ
        moiTruong.undoMatrix(point); // Hoàn tác ma trận trò chơi
        undoManager.undo(); // Thực hiện hoàn tác sự kiện
        setStatus(); // Cập nhật trạng thái (status) của trò chơi
        repaint(); // Vẽ lại giao diện
    }

    // Phương thức kiểm tra xem có thể hoàn tác (undo) nước đi hay không
    public boolean canUndo() {
        return undoManager.canUndo(); // Kiểm tra xem UndoManager có thể hoàn tác một sự kiện hay không
    }
}
