package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

public class CaroInfo extends JFrame { 
    private String direction = "/textInfo/"; // Đường dẫn tới thư mục chứa nội dung văn bản
    private String[] fileName = {"caroHelp", "caroAbout"}; // Tên các tệp chứa nội dung
    private String[] title = {"Hướng dẫn", "Giới thiệu"}; // Tiêu đề cửa sổ

    // Constructor mặc định
    public CaroInfo() {
    }

    // Constructor chấp nhận một tham số kiểu int để xác định loại thông tin (hướng dẫn hoặc giới thiệu)
    public CaroInfo(int type) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 300);
        setTitle("Caro - " + title[type]);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JTextArea ta = createJTextArea(type);
        mainPanel.add(ta);

        // Nếu loại thông tin là giới thiệu, thêm hình ảnh vào cửa sổ
        if (type == 1) {
            // Tạo danh sách hình ảnh cùng với thông tin về vị trí
            List<ImageInfo> images = new ArrayList<>();
            images.add(new ImageInfo("/images/hongTRuong.jpg", 0, 0));
            images.add(new ImageInfo("/images/quocHao.jpg", 0, 0));
            images.add(new ImageInfo("/images/thanhBinh.jpg", 0, 0));
            images.add(new ImageInfo("/images/chiCuong.jpg", 0, 0));
            images.add(new ImageInfo("/images/thanhPhuc.jpg", 0, 0));

            // Lặp qua danh sách hình ảnh và hiển thị chúng trong cửa sổ
            for (ImageInfo imageInfo : images) {
                ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageInfo.path));
                Image image = imageIcon.getImage();

                int desiredWidth = 100;
                int desiredHeight = 100;

                // Tạo ImageIcon đã được điều chỉnh kích thước từ hình ảnh gốc
                ImageIcon scaledImageIcon = new ImageIcon(image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH)); 
                // Tạo một JLabel để hiển thị hình ảnh đã được điều chỉnh kích thước
                JLabel imageLabel = new JLabel(scaledImageIcon); 
                // Đặt vị trí và kích thước của JLabel dựa trên thông tin vị trí trong danh sách ImageInfo
                imageLabel.setBounds(imageInfo.x, imageInfo.y, desiredWidth, desiredHeight); 
                // Thêm JLabel chứa hình ảnh vào mainPanel
                mainPanel.add(imageLabel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Phương thức tạo một JTextArea chứa nội dung từ tệp văn bản
    private JTextArea createJTextArea(int type) {
        InputStream in = getClass().getResourceAsStream(direction + fileName[type]);
        System.out.println(direction + fileName[type]);
        JTextArea ta = new JTextArea();
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setEditable(false);
        ta.setBackground(null);
        try {
            ta.read(new InputStreamReader(in), null);
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return ta;
    }

    // Lớp tĩnh lưu trữ thông tin về hình ảnh và vị trí
    private static class ImageInfo {

        String path;
        int x;
        int y;

        public ImageInfo(String path, int x, int y) {
            this.path = path;
            this.x = x;
            this.y = y;
        }
    }
}
