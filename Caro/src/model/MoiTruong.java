package model;

import java.awt.Point;

import view.CaroGraphics;

public class MoiTruong {

    private int matrix[][]; // Ma trận biểu diễn trạng thái của trò chơi
    private int win = 0; // Biến đánh dấu người chiến thắng (1 hoặc 2)

    // Các phương thức getter và setter cho trường win
    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    // Khởi tạo kích thước ma trận dựa trên số hàng và cột của giao diện
    public MoiTruong() {
        matrix = new int[CaroGraphics.row + 2][CaroGraphics.col + 2];
    }

    // Cập nhật ma trận khi người chơi thực hiện nước đi, useCross biểu thị người chơi
    public boolean updateMatrix(boolean useCross, Point point) {
        int row = point.x + 1;
        int col = point.y + 1;
        short player = (short) (useCross ? 1 : 2);

        for (int i = 0; i < CaroGraphics.row; i++) {
            System.out.println();
        }

        if (matrix[row][col] == 0) {
            matrix[row][col] = player;
        } else {
            System.out.println("error"); // Thông báo lỗi nếu ô đã được đánh
            return false;
        }

        // In ma trận sau khi cập nhật
        for (int i = 1; i < CaroGraphics.row - 1; i++) {
            for (int j = 1; j < CaroGraphics.col - 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        // Kiểm tra xem có người chiến thắng sau nước đi này không
        win = checkWin(row, col);
        return true;
    }

    // Hủy bỏ nước đi trước đó bằng cách đặt giá trị của ô về 0
    public void undoMatrix(Point point) {
        int row = point.x + 1;
        int col = point.y + 1;
        matrix[row][col] = 0;
    }

    // Phương thức kiểm tra chiến thắng sau mỗi nước đi
    private int checkWin(int row, int col) {
        int[][] rc = {{0, -1, 0, 1}, {-1, 0, 1, 0}, {1, -1, -1, 1},
        {-1, -1, 1, 1}};
        int i = row, j = col;

        // Lặp qua các hướng kiểm tra chiến thắng (ngang, dọc, chéo trái, chéo phải)
        for (int direction = 0; direction < 4; direction++) {
            int count = 0;
            System.out.println("[" + direction + "]-" + "[" + row + "," + col
                    + "]  ");

            i = row;
            j = col;
            // Kiểm tra theo hướng đang xét cho đến khi không còn ô liên tiếp cùng loại hoặc đạt 5 ô liên tiếp
            while (i > 0 && i < matrix.length && j > 0 && j < matrix.length
                    && matrix[i][j] == matrix[row][col]) {
                count++;
                if (count == 5) {
                    return matrix[row][col]; // Nếu có 5 ô liên tiếp cùng loại, trả về người chiến thắng
                }
                System.out.print("\t[" + i + "," + j + "]  ");
                i += rc[direction][0];
                j += rc[direction][1];
                System.out.println("--->[" + i + "," + j + "]  ");
            }
            System.out.println("\tcount1 : " + count); 
            count--; // Tránh đếm ô hiện tại một lần nữa
            
            i = row;
            j = col;
            // Kiểm tra ngược hướng để kiểm tra các ô liên tiếp khác
            while (i > 0 && i < matrix.length && j > 0 && j < matrix.length
                    && matrix[i][j] == matrix[row][col]) {
                count++;
                if (count == 5) {
                    return matrix[row][col]; // Nếu có 5 ô liên tiếp cùng loại, trả về người chiến thắng
                }
                System.out.print("\t[" + i + "," + j + "]  ");
                i += rc[direction][2];
                j += rc[direction][3];
                System.out.println("--->[" + i + "," + j + "]  ");
            }
            System.out.println("\tcount : " + count);
        }
        return 0; // Nếu không có người chiến thắng
    }
}
