package controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



import view.CaroFrame;

public class PlayerController extends JDialog implements ActionListener {

    private String playerName1 = "Người chơi 1", playerName2 = "Người chơi 2";
    private JTextField tfPlayer1, tfPlayer2;
    private JRadioButton radPlayer1, radPlayer2;
    private int start = 1;
    private CaroFrame caroFrame;

    public CaroFrame getCaroFrame() {
        return caroFrame;
    }

    public void setCaroFrame(CaroFrame caroFrame) {
        this.caroFrame = caroFrame;
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public void setPlayerName1(String playerName1) {
        this.playerName1 = playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public void setPlayerName2(String playerName2) {
        this.playerName2 = playerName2;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public PlayerController(CaroFrame caroFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Đặt kiểu đóng cửa sổ khi người dùng đóng nó
        setModalityType(ModalityType.APPLICATION_MODAL);  // Đặt kiểu modality cho cửa sổ là APPLICATION_MODAL
        setResizable(false); // Không cho phép thay đổi kích thước cửa sổ

        this.caroFrame = caroFrame; // Lưu tham chiếu đến CaroFrame
        init();  // Thực hiện các cài đặt ban đầu
        add(createPanel());  // Tạo và thêm nội dung cửa sổ

        pack();   // Điều chỉnh kích thước cửa sổ dựa trên nội dung
        setLocationRelativeTo(null); // Đặt cửa sổ ở trung tâm màn hình
    }

    // Tạo và trả về JPanel chứa nội dung cửa sổ
    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titlePanel(), BorderLayout.PAGE_START);  // Thêm phần tiêu đề vào vị trí PAGE_START (đầu cửa sổ)
        panel.add(mainPanel(), BorderLayout.CENTER); // Thêm phần chính vào vị trí CENTER (trung tâm cửa sổ)
        panel.add(buttonPanel(), BorderLayout.PAGE_END);  // Thêm phần nút vào vị trí PAGE_END (cuối cửa sổ)
        return panel;
    }

    // Tạo và trả về JPanel chứa tiêu đề
    private JPanel titlePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Chọn người chơi")); // Tạo và thêm một nhãn với nội dung "Chọn người chơi"
        return panel;
    }

    // Tạo và trả về JPanel chứa phần chính của cửa sổ
    private JPanel mainPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 2, 2)); // Thêm hai playerPanel vào panel chính, sử dụng GridLayout 2 dòng 1 cột
        panel.add(playerPanel(true));  // Thêm playerPanel cho người chơi 1
        panel.add(playerPanel(false)); // Thêm playerPanel cho người chơi 2
        return panel;
    }

    // Tạo và trả về JPanel chứa phần nút
    private JPanel buttonPanel() {
        JPanel panel = new JPanel();
        panel.add(createJButton("Xong")); // Thêm một JButton có nội dung "Xong" vào panel nút
        return panel;
    }

    // Tạo và trả về JPanel chứa thông tin về người chơi
    private JPanel playerPanel(boolean player) {
        JTextField tf;
        JLabel lb;
        JRadioButton rad;
        if (player) {  // Dựa vào biến boolean 'player', quyết định xem đây là panel cho người chơi 1 hay người chơi 2
            tf = tfPlayer1;  // Sử dụng JTextField tfPlayer1 cho người chơi 1
            lb = new JLabel("Nguời chơi 1: "); // Tạo nhãn cho người chơi 1
            rad = radPlayer1;  // Sử dụng JRadioButton radPlayer1 cho người chơi 1
        } else {
            tf = tfPlayer2;  // Sử dụng JTextField tfPlayer2 cho người chơi 2
            lb = new JLabel("Nguời chơi 2: "); // Tạo nhãn cho người chơi 2
            rad = radPlayer2;  // Sử dụng JRadioButton radPlayer2 cho người chơi 2
        }

        JPanel panel = new JPanel(); // Tạo một JPanel chứa các thành phần của người chơi (nhãn, trường nhập và nút radio)
        panel.add(lb);  // Thêm nhãn vào panel
        panel.add(tf);  // Thêm trường nhập vào panel
        panel.add(rad); // Thêm nút radio vào panel
        return panel;
    }

    // Thực hiện các cài đặt ban đầu của cửa sổ
    private void init() {
        // Tạo và cấu hình các trường nhập cho người chơi 1 và người chơi 2
        tfPlayer1 = createJTextField(true);
        tfPlayer2 = createJTextField(false);
        createpalyerStart(); // Tạo và cấu hình nút radio cho lựa chọn người chơi ban đầu
    }

    // Tạo và trả về một trường nhập (JTextField) với tên người chơi đã được đặt sẵn
    private JTextField createJTextField(boolean player) {
        String playerName = player ? playerName1 : playerName2;
        JTextField tf = new JTextField(15);
        tf.setText(playerName);
        return tf;
    }

    // Tạo và trả về một nút (JButton) với tên đã cho
    private JButton createJButton(String btnName) {
        JButton btn = new JButton(btnName);
        btn.addActionListener(this); // Đăng ký sự kiện ActionListener cho nút
        return btn;
    }

    // Tạo và cấu hình nút radio để chọn người chơi ban đầu
    private void createpalyerStart() {
        ButtonGroup btnG = new ButtonGroup(); // Tạo một nhóm nút radio
        radPlayer1 = new JRadioButton();
        radPlayer1.setSelected(true); // Đặt nút radio cho người chơi 1 là lựa chọn ban đầu
        radPlayer1.addActionListener(this); // Đăng ký sự kiện ActionListener cho nút radio người chơi 1

        radPlayer2 = new JRadioButton();
        radPlayer2.addActionListener(this); // Đăng ký sự kiện ActionListener cho nút radio người chơi 2

        btnG.add(radPlayer1); // Thêm nút radio người chơi 1 vào nhóm
        btnG.add(radPlayer2); // Thêm nút radio người chơi 2 vào nhóm
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Xử lý sự kiện khi có sự kiện xảy ra
        if (e.getSource() == radPlayer1) {  // Kiểm tra xem nguồn sự kiện (source) là nút radio của người chơi 1
            start = 1; // Đặt người chơi bắt đầu là người chơi 1
        }
        if (e.getSource() == radPlayer2) { // Kiểm tra xem nguồn sự kiện là nút radio của người chơi 2
            start = 2; // Đặt người chơi bắt đầu là người chơi 2
        }
        if (e.getActionCommand() == "Xong") { // Kiểm tra xem nguồn sự kiện có là nút "Xong" (OK) không
            if (checkEmpty(tfPlayer1)) { // Kiểm tra và ngăn người dùng từ việc bỏ trống trường nhập của người chơi 1
                return;
            }
            if (checkEmpty(tfPlayer2)) { // Kiểm tra và ngăn người dùng từ việc bỏ trống trường nhập của người chơi 2
                return;
            }
            // Lấy tên người chơi từ trường nhập và cập nhật chúng
            playerName1 = tfPlayer1.getText();
            playerName2 = tfPlayer2.getText();
            caroFrame.updateStatus(); // Gọi phương thức updateStatus() trên đối tượng caroFrame để cập nhật trạng thái
            System.out.println(playerName1 + ", " + playerName2 + ", " + start); // In thông tin về người chơi và người chơi bắt đầu ra màn hình console
            setVisible(false); // Ẩn cửa sổ hiện tại
        }
    }

    // Kiểm tra xem trường nhập có bỏ trống hay không và tập trung vào nó
    private boolean checkEmpty(JTextField tf) {
        if (tf.getText().trim().equals("")) {
            tf.requestFocus(); // Tập trung vào trường nhập
            return true; // Trường nhập bỏ trống
        }
        return false; // Trường nhập không bỏ trống
    }
}
