package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.PlayerController;
import model.MyImage;



public class CaroFrame extends JFrame implements ActionListener {

    // Khai báo các thuộc tính của lớp
    private int width = CaroGraphics.width;
    private int height = CaroGraphics.height;

    private CaroGraphics caroGraphics; // Giao diện trò chơi Caro

    public static JLabel lbStatusO; // Label hiển thị trạng thái của người chơi O
    public static JLabel lbStatusX; // Label hiển thị trạng thái của người chơi X
    private JLabel lbNamePlayerO; // Label hiển thị tên người chơi O
    private JLabel lbNamePlayerX; // Label hiển thị tên người chơi X
    private JLabel lbScoreO; // Label hiển thị điểm số của người chơi O
    private JLabel lbScoreX; // Label hiển thị điểm số của người chơi X
    private ImageIcon iconPlayerO; // Biểu tượng (icon) của người chơi O
    private ImageIcon iconPlayerX; // Biểu tượng (icon) của người chơi X
    private int scoreO = 0, scoreX = 0; // Điểm số của người chơi O và X

    private String playerName1 = "Tên người 1"; // Tên người chơi 1 (mặc định)
    private String playerName2 = "Tên người 2"; // Tên người chơi 2 (mặc định)

    private MyImage myImage = new MyImage(); // Đối tượng MyImage chứa hình ảnh
    private PlayerController selectPlayerFrame; // Giao diện lựa chọn người chơi

    // Phương thức khởi tạo của lớp CaroFrame
    public CaroFrame() {
        init(); // Gọi phương thức khởi tạo
    }

    // Phương thức khởi tạo giao diện chính của trò chơi
    private void init() {
        setTitle("Caro 2 người chơi"); // Đặt tiêu đề của cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đặt chế độ đóng cửa sổ khi kết thúc trò chơi
        setLayout(new BorderLayout()); // Sử dụng giao diện BorderLayout

        initGraphics(); // Khởi tạo giao diện trò chơi
        setJMenuBar(createJMenuBar()); // Tạo và đặt thanh menu
        add(createMainPainl()); // Tạo và thêm giao diện chính của trò chơi

        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
        pack(); // Tự động đặt kích thước cửa sổ dựa trên nội dung
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở trung tâm màn hình
        setVisible(true); // Hiển thị cửa sổ

        selectPlayer(); // Hiển thị giao diện để chọn người chơi
    }

    // Khởi tạo giao diện trò chơi Caro
    private void initGraphics() {
        caroGraphics = new CaroGraphics(); // Tạo đối tượng CaroGraphics
        caroGraphics.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                caroGraphics.actionClick(e.getPoint()); // Xử lý khi người chơi nhấp chuột vào ô cờ
                if (caroGraphics.getWiner() > 0) {
                    win(caroGraphics.getWiner()); // Kiểm tra nếu có người chiến thắng
                }
            }
        });
    }

    // Hiển thị giao diện để chọn người chơi
    private void selectPlayer() {
        if (selectPlayerFrame == null) {
            selectPlayerFrame = new PlayerController(this); // Tạo đối tượng PlayerController
        }
        selectPlayerFrame.setVisible(true); // Hiển thị giao diện lựa chọn người chơi
    }

    // Cập nhật trạng thái và thông tin của người chơi
    public void updateStatus() {
        playerName1 = selectPlayerFrame.getPlayerName1(); // Lấy tên của người chơi 1
        playerName2 = selectPlayerFrame.getPlayerName2(); // Lấy tên của người chơi 2
        caroGraphics.player = caroGraphics.playerRoot; // Cập nhật người chơi hiện tại
        lbNamePlayerX.setText(playerName1); // Hiển thị tên người chơi 1
        lbNamePlayerO.setText(playerName2); // Hiển thị tên người chơi 2
        if (selectPlayerFrame.getStart() == 1) {
            caroGraphics.playerRoot = true; // Đặt người chơi O là người chơi bắt đầu
        } else {
            caroGraphics.playerRoot = false; // Đặt người chơi X là người chơi bắt đầu
        }
        caroGraphics.player = caroGraphics.playerRoot; // Cập nhật người chơi hiện tại
        caroGraphics.setStatus(); // Cập nhật trạng thái của trò chơi
        System.out.println("updated"); // In ra thông báo khi cập nhật xong
    }

    // Tạo và trả về thanh menu của ứng dụng
    private JMenuBar createJMenuBar() {
        JMenuBar mb = new JMenuBar();
        String[] game = {"Trò chơi mới", "Hiệp mới", "", "Thoát"};
        mb.add(createJMenu("Trò chơi", game, KeyEvent.VK_T));
        String[] help = {"Hướng dẫn", "", "Giới thiệu"};
        mb.add(createJMenu("Hướng dẫn", help, KeyEvent.VK_H));
        return mb;
    }

    // Tạo một menu chứa các mục con và đặt thuộc tính cho menu
    private JMenu createJMenu(String menuName, String itemName[], int key) {
        JMenu m = new JMenu(menuName);
        m.addActionListener(this);
        m.setMnemonic(key);

        for (int i = 0; i < itemName.length; i++) {
            if (itemName[i].equals("")) {
                m.add(new JSeparator()); // Thêm dấu ngăn cách nếu là chuỗi trống
            } else {
                m.add(createJMenuItem(itemName[i])); // Thêm mục con vào menu
            }
        }
        return m;
    }

    // Tạo một mục con trong menu và đặt sự kiện khi được chọn
    private JMenuItem createJMenuItem(String itName) {
        JMenuItem mi = new JMenuItem(itName);
        mi.addActionListener(this);
        return mi;
    }

    // Tạo và trả về giao diện chính của ứng dụng, bao gồm trò chơi và bảng điểm
    private JPanel createMainPainl() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createPanelGraphics(), BorderLayout.CENTER); // Thêm trò chơi vào vùng trung tâm
        panel.add(createSidebarPanel(true), BorderLayout.WEST); // Thêm bảng điểm cho người chơi O vào bên trái
        panel.add(createSidebarPanel(false), BorderLayout.EAST); // Thêm bảng điểm cho người chơi X vào bên phải
        return panel;
    }

    // Tạo panel chứa bảng cờ Caro
    private JPanel createPanelGraphics() {
        JPanel panelGraphics = new JPanel(null); // Tạo panel với layout là null để có thể điều chỉnh vị trí của giao diện con
        panelGraphics.add(caroGraphics, BorderLayout.CENTER); // Thêm bảng cờ Caro vào vùng trung tâm của panel
        int bound = 10;
        caroGraphics.setBounds(bound, bound, caroGraphics.width, caroGraphics.height); // Đặt vị trí và kích thước của bảng cờ Caro
        panelGraphics.setPreferredSize(new Dimension(caroGraphics.width + bound * 2, caroGraphics.height + bound * 2)); // Đặt kích thước ước tính cho panel
        panelGraphics.setBorder(new LineBorder(Color.black)); // Đặt viền đen cho panel
        panelGraphics.setBackground(Color.blue); // Đặt màu nền là màu xanh
        return panelGraphics;
    }

    // Tạo panel bên trái hoặc bên phải của giao diện chính
    private JPanel createSidebarPanel(boolean player) {
        JPanel panel = new JPanel(new BorderLayout()); // Tạo panel với layout BorderLayout
        panel.add(createPanelStatus(player), BorderLayout.PAGE_START); // Thêm panel trạng thái ở vị trí đầu trang (PAGE_START)
        panel.add(createPlayerPanel(player), BorderLayout.CENTER); // Thêm panel thông tin người chơi ở vùng giữa (CENTER)
        panel.add(createPanelBottom(player), BorderLayout.PAGE_END); // Thêm panel nút bên dưới (PAGE_END)
        return panel;
    }

    // Tạo panel chứa trạng thái của người chơi (X hoặc O)
    private JPanel createPanelStatus(boolean player) {
        JPanel panelStatus = new JPanel(new GridLayout(2, 1, 2, 2)); // Tạo panel với layout GridLayout 2 hàng và 1 cột
        JPanel panel1 = new JPanel();

        if (player) { // Nếu là người chơi X
            lbStatusX = new JLabel(); // Tạo label cho trạng thái người chơi X
            lbStatusX.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label
            lbNamePlayerX = new JLabel("Trần Văn A"); // Tạo label cho tên người chơi X
            lbNamePlayerX.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label tên người chơi

            lbScoreX = new JLabel("0"); // Tạo label cho điểm người chơi X
            lbScoreX.setFont(lbScoreX.getFont().deriveFont(Font.PLAIN, 35f)); // Đặt font và kích thước cho label điểm
            lbScoreX.setForeground(Color.red); // Đặt màu chữ cho label điểm là màu đỏ
            lbScoreX.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label điểm

            panel1.add(lbStatusX); // Thêm label trạng thái người chơi X vào panel1
            panel1.add(lbNamePlayerX); // Thêm label tên người chơi X vào panel1
            panelStatus.add(panel1); // Thêm panel1 vào panel trạng thái
            panelStatus.add(lbScoreX); // Thêm label điểm người chơi X vào panel trạng thái
        } else { // Nếu là người chơi O
            lbStatusO = new JLabel(); // Tạo label cho trạng thái người chơi O
            lbStatusO.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label
            lbNamePlayerO = new JLabel("Nguyễn Thị B"); // Tạo label cho tên người chơi O
            lbNamePlayerO.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label tên người chơi

            lbScoreO = new JLabel("0"); // Tạo label cho điểm người chơi O
            lbScoreO.setFont(lbScoreO.getFont().deriveFont(Font.PLAIN, 35f)); // Đặt font và kích thước cho label điểm
            lbScoreO.setForeground(Color.blue); // Đặt màu chữ cho label điểm là màu xanh
            lbScoreO.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa cho label điểm

            panel1.add(lbStatusO); // Thêm label trạng thái người chơi O vào panel1
            panel1.add(lbNamePlayerO); // Thêm label tên người chơi O vào panel1
            panelStatus.add(panel1); // Thêm panel1 vào panel trạng thái
            panelStatus.add(lbScoreO); // Thêm label điểm người chơi O vào panel trạng thái
        }
        int bound = 1;
        panelStatus.setBorder(new LineBorder(Color.green)); // Đặt viền màu xanh cho panel trạng thái
        panelStatus.setPreferredSize(new Dimension(width / 3, height / 6 - 25)); // Đặt kích thước ước tính cho panel trạng thái
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(bound, bound, bound, bound)); // Đặt viền trống cho panel
        panel.add(panelStatus); // Thêm panel trạng thái vào panel
        return panel; // Trả về panel chứa trạng thái của người chơi
    }

    // Tạo panel hiển thị thông tin người chơi (hình ảnh X hoặc O)
    private JPanel createPlayerPanel(boolean player) {
        int boundw = 10; // Biên ngang
        int boundh = 10; // Biên dọc
        int h = height * 2 / 3 + boundh; // Chiều cao của panel chứa hình ảnh người chơi
        int w = width / 3; // Chiều rộng của panel chứa hình ảnh người chơi
        String imgPlayerO = "playerO.gif"; // Tên hình ảnh người chơi O
        String imgPlayerX = "playerX.gif"; // Tên hình ảnh người chơi X

        // Tạo hình ảnh cho người chơi O và X, đặt kích thước
        iconPlayerO = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerO), w - boundw, h - boundh));
        iconPlayerX = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerX), w - boundw, h - boundh));

        // Lấy hình ảnh cho người chơi hiện tại (X hoặc O)
        ImageIcon icon = player ? iconPlayerX : iconPlayerO;
        JLabel lbPlayer = new JLabel(icon); // Tạo label chứa hình ảnh người chơi 
        JPanel panel = new JPanel(new BorderLayout()); // Tạo panel với layout BorderLayout
        panel.setPreferredSize(new Dimension(w, h)); // Đặt kích thước ước tính cho panel 
        panel.add(lbPlayer, BorderLayout.CENTER); // Thêm label hình ảnh người chơi vào panel 
        int bound = 1;
        panel.setBorder(new LineBorder(Color.green)); // Đặt viền màu xanh cho panel
        JPanel panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(bound, bound, bound, bound)); // Đặt viền trống cho panel
        panel1.add(panel); // Thêm panel chứa hình ảnh người chơi vào panel1
        return panel1; // Trả về panel chứa hình ảnh người chơi
    }

    // Tạo panel chứa các nút đi lại, xin thua (đối với người chơi X) hoặc trò chơi mới, hiệp mới (đối với người chơi O)
    private JPanel createPanelBottom(boolean player) {
        String[] str1 = {"Đi lại", "Xin thua"}; // Các tùy chọn cho người chơi X
        String[] str2 = {"Trò chơi mới", "Hiệp mới"}; // Các tùy chọn cho người chơi O
        String[] str;

        if (player) {
            str = str1; // Sử dụng tùy chọn của người chơi X
        } else {
            str = str2; // Sử dụng tùy chọn của người chơi O
        }
        int size = str.length; // Số lượng tùy chọn
        JPanel panel = new JPanel(new GridLayout(size, 1, 5, 5)); // Tạo panel với layout GridLayout
        for (int i = 0; i < size; i++) {
            panel.add(createJButton(str[i])); // Thêm nút cho mỗi tùy chọn
        }
        int bound = 1;
        panel.setBorder(new LineBorder(Color.green)); // Đặt viền màu xanh cho panel
        panel.setPreferredSize(new Dimension(width / 3, height / 6)); // Đặt kích thước ước tính cho panel
        JPanel panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(bound, bound, bound, bound)); // Đặt viền trống cho panel
        panel1.add(panel); // Thêm panel chứa các nút vào panel1
        return panel1; // Trả về panel chứa các nút tương ứng với người chơi X hoặc O
    }

    // Tạo nút với tên là 'btnName' và thêm một action listener cho nút
    private JButton createJButton(String btnName) {
        JButton btn = new JButton(btnName);
        btn.addActionListener(this);
        return btn;
    }

    // Xử lý các sự kiện actionPerformed khi người dùng tương tác với các nút hoặc menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // Kiểm tra lựa chọn của người dùng và thực hiện các hành động tương ứng
        if (command.equals("Trò chơi mới")) {
            actionNewGame();
        }
        if (command.equals("Hiệp mới")) {
            actionNewUnit();
        }
        if (command.equals("Thoát")) {
            actionExit();
        }
        if (command.equals("Hướng dẫn")) {
            actionHelp();
        }
        if (command.equals("Giới thiệu")) {
            actionAbout();
        }
        if (command.equals("Đi lại")) {
            actionUndo();
        }
        if (command.equals("Xin thua")) {
            actionGiveIn();
        }
    }

    // Xử lý tạo trò chơi mới
    private void actionNewGame() {
        int select = showDialog("Các bạn thực sự muốn tạo trò chơi mới?", "Trò chơi mới");
        if (select == 0) {
            scoreO = 0;
            scoreX = 0;
            clear();
        }
    }

    // Xử lý hành động tạo hiệp mới
    private void actionNewUnit() {
        int select = showDialog("Các bạn thực sự muốn tạo hiệp mới?",
                "Hiệp mới");
        if (select == 0) {
            clear();
        }
    }

    // Xử lý hành động thoát khỏi ứng dụng
    private void actionExit() {
        int select = showDialog("Các bạn thực sự muốn thoát?\n"
                + "Cảm ơn bạn đã chơi trò chơi này. GoodBye", "Thoát");
        if (select == 0) {
            System.exit(0);
        }
    }

    // Xử lý hành động hiển thị hướng dẫn
    private void actionHelp() {
        new CaroInfo(0);
    }

    // Xử lý hành động hiển thị giới thiệu
    private void actionAbout() {
        new CaroInfo(1);
    }

    // Xử lý hành động đi lại (undo)
    private void actionUndo() {
        caroGraphics.undo();
    }

    // Xử lý khi người chơi muốn xin thua
    private void actionGiveIn() {
        int sO = 0, sX = 0; // Điểm số cho người chơi O và X sau khi xin thua
        String playerName = ""; // Tên của người chơi đang xin thua
        if (caroGraphics.player) {
            sO = 1; // Nếu người chơi O đang xin thua, cộng 1 điểm cho O
            playerName = playerName1;
        } else {
            sX = 1; // Nếu người chơi X đang xin thua, cộng 1 điểm cho X
            playerName = playerName2;
        }
        int select = showDialog(playerName + " thực sự muốn xin thua?", "Trò chơi mới");
        if (select == 0) {
            scoreO += sO; // Cập nhật điểm số cho người chơi O
            scoreX += sX; // Cập nhật điểm số cho người chơi X
            clear(); // Đặt lại trò chơi sau khi xin thua
        }
    }

    // Hiển thị hộp thoại cho người chơi xác nhận xin thua hoặc các tùy chọn khác
    private int showDialog(String message, String title) {
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        return select; // Trả về lựa chọn của người chơi (0 nếu đồng ý, 1 nếu từ chối)
    }

    // Đặt lại trò chơi về trạng thái ban đầu
    private void clear() {
        caroGraphics.init(); // Khởi tạo lại đối tượng CaroGraphics
        updateScore(); // Cập nhật điểm số người chơi
        selectPlayer(); // Hiển thị hộp thoại chọn người chơi
        caroGraphics.setStatus(); // Cập nhật trạng thái hiển thị cho CaroGraphics
    }

    // Cập nhật điểm số người chơi trên giao diện
    private void updateScore() {
        lbScoreO.setText(scoreO + ""); // Cập nhật điểm số người chơi O
        lbScoreX.setText(scoreX + ""); // Cập nhật điểm số người chơi X
    }

    // Xử lý khi một người chơi chiến thắng
    private void win(int winer) {
        String playerName = "";
        if (winer == 1) {
            scoreX++; // Cộng điểm cho người chơi X nếu thắng
            playerName = playerName1;
        } else {
            scoreO++; // Cộng điểm cho người chơi O nếu thắng
            playerName = playerName2;
        }
        Object[] options = {"Trò chơi mới", "Hiệp mới", "Thoát"};
        int select = JOptionPane.showOptionDialog(this, "Chúc mừng "
                + playerName + " đã chiến thắng trong hiệp đấu "
                + (scoreO + scoreX), "A Silly Question",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[options.length - 1]);
        if (select == 2) {
            actionExit(); // Thoát khỏi trò chơi nếu chọn "Thoát"
        } else if (select == 0) {
            scoreO = 0; // Đặt lại điểm số cho người chơi O nếu chọn "Trò chơi mới"
            scoreX = 0; // Đặt lại điểm số cho người chơi X nếu chọn "Trò chơi mới"
        }
        clear(); // Đặt lại trò chơi sau khi xử lý kết thúc trò chơi
    }
}
