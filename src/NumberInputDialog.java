import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.lang.Double.parseDouble;

public class NumberInputDialog {

    public static void showInputDialog(JFrame parentFrame, int studentID, String courseName) {
        try {
            Connection conn = connectDatabase.getConnection();

            // check if the student has already been added to the database
            String query = "SELECT COUNT(*) FROM score WHERE studentID = ? AND courseName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentID);
            pstmt.setString(2, courseName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                JOptionPane.showMessageDialog(parentFrame, "This student has already been added for this course.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // if the student hasn't been added yet, show the input dialog
            JPanel panel = new JPanel(new GridLayout(4, 2));
            JTextField quizField = new JTextField();
            JTextField midtermField = new JTextField();
            JTextField assignmentField = new JTextField();
            JTextField finalField = new JTextField();

            panel.add(new JLabel("Quiz:"));
            panel.add(quizField);
            panel.add(new JLabel("Midterm:"));
            panel.add(midtermField);
            panel.add(new JLabel("Assignment:"));
            panel.add(assignmentField);
            panel.add(new JLabel("Final:"));
            panel.add(finalField);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Enter scores for student " + studentID + " in course " + courseName, JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                // insert the student's scores into the database
                double quiz = parseDouble(quizField.getText());
                double midterm = parseDouble(midtermField.getText());
                double assignment = parseDouble(assignmentField.getText());
                double finalScore = parseDouble(finalField.getText());
                double total = quiz + midterm + assignment + finalScore;
                double average = total / 4.0;

                query = "INSERT INTO score (studentID, courseName, quiz, midterm, assignment, final, total, average) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, studentID);
                pstmt.setString(2, courseName);
                pstmt.setDouble(3, quiz);
                pstmt.setDouble(4, midterm);
                pstmt.setDouble(5, assignment);
                pstmt.setDouble(6, finalScore);
                pstmt.setDouble(7, total);
                pstmt.setDouble(8, average);

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(parentFrame, "Score added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(ScoreFrame.score());
                frame.revalidate();
                frame.repaint();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void showUpdateDialog(JFrame parentFrame, int studentID, String courseName) {

        try {
            Connection conn = connectDatabase.getConnection();

            // Get existing score for the student in the course
            String query = "SELECT quiz, midterm, assignment, final FROM score WHERE studentID = ? AND courseName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentID);
            pstmt.setString(2, courseName);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(parentFrame, "No score found for student " + studentID + " in course " + courseName, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create input fields for new score values
            JPanel panel = new JPanel(new GridLayout(4, 2));
            JTextField quizField = new JTextField(rs.getDouble("quiz") + "");
            JTextField midtermField = new JTextField(rs.getDouble("midterm") + "");
            JTextField assignmentField = new JTextField(rs.getDouble("assignment") + "");
            JTextField finalField = new JTextField(rs.getDouble("final") + "");

            panel.add(new JLabel("Quiz:"));
            panel.add(quizField);
            panel.add(new JLabel("Midterm:"));
            panel.add(midtermField);
            panel.add(new JLabel("Assignment:"));
            panel.add(assignmentField);
            panel.add(new JLabel("Final:"));
            panel.add(finalField);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Update scores for student " + studentID + " in course " + courseName, JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {

                double quiz = parseDouble(quizField.getText());
                double midterm = parseDouble(midtermField.getText());
                double assignment = parseDouble(assignmentField.getText());
                double finalScore = parseDouble(finalField.getText());
                double total = quiz + midterm + assignment + finalScore;
                double average = total / 4.0;

                // Update the score in the database
                query = "UPDATE score SET quiz = ?, midterm = ?, assignment = ?, final = ?, total = ?, average = ? WHERE studentID = ? AND courseName = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setDouble(1, quiz);
                pstmt.setDouble(2, midterm);
                pstmt.setDouble(3, assignment);
                pstmt.setDouble(4, finalScore);
                pstmt.setDouble(5, total);
                pstmt.setDouble(6, average);
                pstmt.setInt(7, studentID);
                pstmt.setString(8, courseName);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    JOptionPane.showMessageDialog(parentFrame, "No score found for student " + studentID + " in course " + courseName, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Score updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                    frame.setContentPane(ScoreFrame.score());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void showDeleteDialog(JFrame parentFrame, int studentID, String courseName) {
        int confirmed = JOptionPane.showConfirmDialog(parentFrame,
                "Are you sure you want to delete the scores for student " + studentID + " in course " + courseName + "?",
                "Delete Scores", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            try {
                Connection conn = connectDatabase.getConnection();
                String query = "DELETE FROM score WHERE studentID = ? AND courseName = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, studentID);
                pstmt.setString(2, courseName);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(parentFrame, "Score deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(parentFrame);
                frame.setContentPane(ScoreFrame.score());
                frame.revalidate();
                frame.repaint();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
