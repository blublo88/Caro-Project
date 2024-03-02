package model;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import view.CaroGraphics;

public class MyImage {

    private String urlImage = "/images/"; // Đường dẫn tới thư mục chứa hình ảnh
    public Image imgCross; // Hình ảnh X
    public Image imgNought; // Hình ảnh O

    // Constructor của lớp MyImage
    public MyImage() {
        int size = CaroGraphics.sizeCell - 2; // Kích thước hình ảnh sẽ được điều chỉnh kích thước
        imgCross = reSizeImage(getMyImageIcon("cross.gif"), size, size); // Lấy hình ảnh X và điều chỉnh kích thước
        imgNought = reSizeImage(getMyImageIcon("nought.gif"), size, size); // Lấy hình ảnh O và điều chỉnh kích thước
    }

    // Phương thức thay đổi kích thước của hình ảnh
    public Image reSizeImage(Image image, int width, int height) {
        image = new ImageIcon(image.getScaledInstance(width, height,
                imgCross.SCALE_SMOOTH)).getImage();
        return image;
    }

    // Phương thức lấy hình ảnh từ tên hình ảnh
    public Image getMyImageIcon(String nameImageIcon) {
        Image ii = new ImageIcon(getClass().getResource(
                urlImage + nameImageIcon)).getImage();
        return ii;
    }
}
