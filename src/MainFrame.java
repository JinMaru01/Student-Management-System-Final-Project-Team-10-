import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    public static JPanel homePage (){
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton student;
        JButton score;
        JButton course;
        JButton exit;

        panel.setLayout(null);

        label.setText("Student Management System ");
        label.setBounds(500,50,1000,50);
        label.setFont(new Font("CADT Mono Display", Font.BOLD,30));
        panel.add(label);

        course = new JButton("View Course");
        course.setBounds(100,750,250,50);
        course.setFont(new Font("Times new roman",Font.PLAIN,17));
        course.setBackground(Color.ORANGE);
        panel.add(course);

        student = new JButton("View Student");
        student.setBounds(450,750,250,50);
        student.setFont(new Font("Times new roman",Font.PLAIN,17));
        student.setBackground(Color.ORANGE);
        panel.add(student);

        score = new JButton("View Score");
        score.setBounds(800,750,250,50);
        score.setFont(new Font("Times new roman",Font.PLAIN,17));
        score.setBackground(Color.ORANGE);
        panel.add(score);

        exit = new JButton("Quit");
        exit.setBounds(1150,750,250,50);
        exit.setFont(new Font("Times new roman",Font.PLAIN,17));
        exit.setBackground(Color.ORANGE);
        panel.add(exit);

        // create a JLabel to hold the image
        JLabel imageLabel = new JLabel();

        // create an ImageIcon from the file path of the image
        ImageIcon imageIcon = new ImageIcon("image/main.png");

        // set the image icon to the JLabel
        imageLabel.setIcon(imageIcon);

        // set the bounds of the image label
        imageLabel.setBounds(300, 10, 1400, 800);

        // add the image label to the panel
        panel.add(imageLabel);

        panel.setBackground(Color.white);


        course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                try {
                    frame.setContentPane(CourseFrame.mainCourse());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.revalidate();
                frame.repaint();
            }
        });
        student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                try {
                    frame.setContentPane(StudentFrame.studentInfo());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.revalidate();
                frame.repaint();
            }
        });
        score.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                try {
                    frame.setContentPane(ScoreFrame.score());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.revalidate();
                frame.repaint();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        return panel;
    }
}
