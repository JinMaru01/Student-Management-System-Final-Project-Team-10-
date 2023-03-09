import javax.swing.*;
import java.awt.*;

public class Main {
    public static void Frame(JFrame frame) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//full screen
        frame.setSize(screenSize);

        frame.add(Input.login());
//        frame.add(StudentFrame.studentInfo());
//        frame.add(CourseFrame.mainCourse());
//        frame.add(ScoreFrame.score());
        frame.getContentPane();

        // Make the frame full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set the frame to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Student Management System");
        Frame(frame);
    }
}