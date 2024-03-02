package model;

import java.awt.Point;
import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;

public class Quaylai extends AbstractUndoableEdit {

    protected Vector points; // Danh sách các điểm cần quay lại (undo)
    protected Point point; // Điểm được quay lại (undo)

    // Constructor của lớp Quaylai, nhận danh sách điểm và điểm cần quay lại
    public Quaylai(Point p, Vector v) {
        points = v;
        point = p;
    }

    // Phương thức trả về tên hiển thị cho hoạt động undo
    public String getPresentationName() {
        return "Square Addition"; // Tên hiển thị cho hoạt động undo
    }

    // Phương thức thực hiện hoạt động undo
    public void undo() {
        super.undo();
        points.remove(point); // Loại bỏ điểm khỏi danh sách, thực hiện hoạt động undo
    }

    // Phương thức thực hiện hoạt động redo
    public void redo() {
        super.redo();
        points.add(point); // Thêm điểm lại vào danh sách, thực hiện hoạt động redo
    }
}
