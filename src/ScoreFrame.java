import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

import static javax.swing.JOptionPane.*;

public class ScoreFrame {
    public static JPanel score () throws Exception {
        JPanel panel = new JPanel();

        panel.setLayout(null);

        JComboBox jComboBox1 = new JComboBox<>();
        JLabel jLabel1 = new JLabel();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();
        JButton jButton3 = new JButton();
        JButton jButton4 = new JButton();
        JButton jButton5 = new JButton();
        JButton jButton6 = new JButton();

        jLabel1.setText(" Scoring and grade of Student ");
        jLabel1.setFont(new Font("AKbalthom HighSchool",Font.BOLD,27));
        jLabel1.setBounds(620,20,1000,50);
        panel.add(jLabel1);

        jComboBox1.setFont(new Font("Times new roman",Font.PLAIN,17));
        jComboBox1.setBounds(50,750,230,50);
        panel.add(jComboBox1);

        jButton1.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton1.setBounds(320,750,155,50);
        panel.add(jButton1);

        jButton2.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton2.setBounds(530,750,155,50);
        panel.add(jButton2);

        jButton3.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton3.setBounds(730,750,155,50);
        panel.add(jButton5);

        jButton4.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton4.setBounds(930,750,155,50);
        panel.add(jButton3);

        jButton5.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton5.setBounds(1130,750,155,50);
        panel.add(jButton6);

        jButton6.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton6.setBounds(1330,750,155,50);
        panel.add(jButton4);

        jButton1.setBackground(Color.ORANGE);
        jButton2.setBackground(Color.ORANGE);
        jButton3.setBackground(Color.ORANGE);
        jButton4.setBackground(Color.ORANGE);
        jButton5.setBackground(Color.ORANGE);
        jButton6.setBackground(Color.ORANGE);
        jComboBox1.setBackground(Color.white);

        Connection conn = connectDatabase.getConnection();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select courseName from course order by courseName");
        while(rs.next()){
            String string = rs.getString("courseName");
            jComboBox1.addItem(string);
        }

        // Create table and model
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setFont(new Font("Times new roman",Font.PLAIN,15));

        // Set column names
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Birthday");
        model.addColumn("Course");
        model.addColumn("Quiz");
        model.addColumn("Assignment");
        model.addColumn("Mid Term");
        model.addColumn("Final Exam");
        model.addColumn("Total");
        model.addColumn("Average");
        model.addColumn("Grade");

        try {
            String query = "SELECT score.studentID, score.courseName, score.midTerm, score.quiz, score.final, score.assignment, total, average, student.studentID, student.firstName, student.lastName, student.birthday from score INNER JOIN student ON score.studentID = student.studentID ORDER BY score.courseName ASC ";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs2 = pstmt.executeQuery();

            while (rs2.next()) {
                int id = rs2.getInt("studentID");
                String firstName = rs2.getString("firstName");
                String lastName = rs2.getString("lastName");
                Date birthday = rs2.getDate("birthday");
                double quiz = rs2.getDouble("quiz");
                double midTerm = rs2.getDouble("midTerm");
                double assignment = rs2.getDouble("assignment");
                double finalExam = rs2.getDouble("final");
                double total = rs2.getDouble("total");
                double average = rs2.getDouble("average");
                String courseName = rs2.getString("courseName");
                String grade = null ;

                if (average >= 90 && average <= 100){
                    grade = "A";
                }else if (average>=80 && average<90){
                    grade = "B";
                }else if (average>=70 && average<80){
                    grade = "C";
                }else if (average>=60 && average<70){
                    grade = "D";
                }else if (average>=50 && average<60){
                    grade = "E";
                }else if (average<50){
                    grade = "F";
                }
                model.addRow(new Object[]{id, firstName +" "+lastName,birthday,courseName,quiz,assignment,midTerm,finalExam,total,average,grade});
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        // Set model to table
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 1430, 650);
        panel.add(scrollPane);

        jButton1.setText("View All");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create table and model
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel();
                table.setFont(new Font("Times new roman",Font.PLAIN,15));

                // Set column names
                model.addColumn("Student ID");
                model.addColumn("Name");
                model.addColumn("Birthday");
                model.addColumn("Course");
                model.addColumn("Quiz");
                model.addColumn("Assignment");
                model.addColumn("Mid Term");
                model.addColumn("Final Exam");
                model.addColumn("Total");
                model.addColumn("Average");
                model.addColumn("Grade");

                try {
                    Connection conn = connectDatabase.getConnection();
                    String query = "SELECT score.studentID, score.courseName, score.midTerm, score.quiz, score.final, score.assignment, total, average, student.studentID, student.firstName, student.lastName, student.birthday from score INNER JOIN student ON score.studentID = student.studentID ORDER BY score.courseName ASC";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    ResultSet rs2 = pstmt.executeQuery();

                    while (rs2.next()) {
                        int id = rs2.getInt("studentID");
                        String firstName = rs2.getString("firstName");
                        String lastName = rs2.getString("lastName");
                        Date birthday = rs2.getDate("birthday");
                        double quiz = rs2.getDouble("quiz");
                        double midTerm = rs2.getDouble("midTerm");
                        double assignment = rs2.getDouble("assignment");
                        double finalExam = rs2.getDouble("final");
                        double total = rs2.getDouble("total");
                        double average = rs2.getDouble("average");
                        String courseName = rs2.getString("courseName");
                        String grade = null ;

                        if (average >= 90 && average <= 100){
                            grade = "A";
                        }else if (average>=80 && average<90){
                            grade = "B";
                        }else if (average>=70 && average<80){
                            grade = "C";
                        }else if (average>=60 && average<70){
                            grade = "D";
                        }else if (average>=50 && average<60){
                            grade = "E";
                        }else if (average<50){
                            grade = "F";
                        }
                        model.addRow(new Object[]{id, firstName +" "+lastName,birthday,courseName,quiz,assignment,midTerm,finalExam,total,average,grade});
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                // Set model to table
                table.setModel(model);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(50, 80, 1430, 650);
                panel.add(scrollPane);
            }
        });


        jButton2.setText("View This Course");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create table and model
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel();
                table.setFont(new Font("Times new roman",Font.PLAIN,15));

                // Set column names
                model.addColumn("Student ID");
                model.addColumn("Name");
                model.addColumn("Birthday");
                model.addColumn("Course");
                model.addColumn("Quiz");
                model.addColumn("Assignment");
                model.addColumn("Mid Term");
                model.addColumn("Final Exam");
                model.addColumn("Total");
                model.addColumn("Average");
                model.addColumn("Grade");

                try {
                    Connection conn = connectDatabase.getConnection();
                    String selectedCourse = (String) jComboBox1.getSelectedItem();
                    String query = "SELECT score.studentID, score.courseName, score.midTerm, score.quiz, score.final, score.assignment, total, average, student.studentID, student.firstName, student.lastName, student.birthday from score INNER JOIN student ON score.studentID = student.studentID where score.courseName = ? ORDER BY score.studentID";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, selectedCourse);
                    ResultSet rs2 = pstmt.executeQuery();

                    while (rs2.next()) {
                        int id = rs2.getInt("studentID");
                        String firstName = rs2.getString("firstName");
                        String lastName = rs2.getString("lastName");
                        Date birthday = rs2.getDate("birthday");
                        double quiz = rs2.getDouble("quiz");
                        double midTerm = rs2.getDouble("midTerm");
                        double assignment = rs2.getDouble("assignment");
                        double finalExam = rs2.getDouble("final");
                        double total = rs2.getDouble("total");
                        double average = rs2.getDouble("average");
                        String courseName = rs2.getString("courseName");
                        String grade = null ;

                        if (average >= 90 && average <= 100){
                            grade = "A";
                        }else if (average>=80 && average<90){
                            grade = "B";
                        }else if (average>=70 && average<80){
                            grade = "C";
                        }else if (average>=60 && average<70){
                            grade = "D";
                        }else if (average>=50 && average<60){
                            grade = "E";
                        }else if (average<50){
                            grade = "F";
                        }
                        model.addRow(new Object[]{id, firstName +" "+lastName,birthday,courseName,quiz,assignment,midTerm,finalExam,total,average,grade});
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                // Set model to table
                table.setModel(model);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(50, 80, 1430, 650);
                panel.add(scrollPane);
            }
        });

        jButton3.setText("Insert Score");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) jComboBox1.getSelectedItem();
                String studentID = showInputDialog(panel, "Input student ID:");
                if (studentID == null){
                    return;
                }else if (studentID.isEmpty()){
                    showMessageDialog(panel, "Please enter a Student ID first.", "Error", ERROR_MESSAGE);
                }else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        connectDatabase.updateScoreTable();
                        String query = "SELECT * FROM enroll INNER JOIN student ON student.studentID = enroll.studentID WHERE enroll.studentID = ? AND enroll.courseName = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentID);
                        pstmt.setString(2, selectedCourse);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            int id = rs.getInt("studentID");
                            int confirmation = showConfirmDialog(
                                    panel, "Are you sure you want to insert score for\n"+ "Student ID : " + id + " Name :" + firstName + " " + lastName  + "\nInto course : " + selectedCourse + " ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                                NumberInputDialog.showInputDialog(frame, Integer.parseInt(studentID), selectedCourse);
                            }
                        } else {
                            showMessageDialog(panel, "No student found with ID : " + studentID+"\nIn course"+selectedCourse);
                            int confirmation = showConfirmDialog(
                                    panel, "Do you want to want view list of Enrollment ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(CourseFrame.mainCourse());
                                frame.revalidate();
                                frame.repaint();
                            }
                        }
                    } catch (SQLException ex) {
                        showMessageDialog(panel, "Error searching for student : " + ex.getMessage());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        jButton4.setText("Update Score");
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) jComboBox1.getSelectedItem();
                String studentID = showInputDialog(panel, "Input student ID:");
                if (studentID == null){
                    return;
                }else if (studentID.isEmpty()){
                    showMessageDialog(panel, "Please enter a Student ID first.", "Error", ERROR_MESSAGE);
                }else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        connectDatabase.updateScoreTable();
                        String query = "SELECT * FROM score INNER JOIN student ON student.studentID = student.studentID WHERE score.studentID = ? AND score.courseName = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentID);
                        pstmt.setString(2, selectedCourse);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            int id = rs.getInt("studentID");
                            int confirmation = showConfirmDialog(
                                    panel, "Are you sure you want to update score for\n"+ "Student ID : " + id + " Name :" + firstName + " " + lastName  + "\nInto course : " + selectedCourse + " ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                                NumberInputDialog.showUpdateDialog(frame, Integer.parseInt(studentID), selectedCourse);
                            }
                        } else {
                            showMessageDialog(panel, "No student found with ID : " + studentID+"\nIn course"+selectedCourse);
                            int confirmation = showConfirmDialog(
                                    panel, "Do you want to want view list of Enrollment ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(CourseFrame.mainCourse());
                                frame.revalidate();
                                frame.repaint();
                            }
                        }
                    } catch (SQLException ex) {
                        showMessageDialog(panel, "Error searching for student : " + ex.getMessage());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        jButton5.setText("Delete Score");
        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) jComboBox1.getSelectedItem();
                String studentID = showInputDialog(panel, "Input student ID:");
                if (studentID == null){
                    return;
                }else if (studentID.isEmpty()){
                    showMessageDialog(panel, "Please enter a Student ID first.", "Error", ERROR_MESSAGE);
                }else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        connectDatabase.updateScoreTable();
                        String query = "SELECT * FROM score INNER JOIN student ON student.studentID = student.studentID WHERE score.studentID = ? AND score.courseName = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentID);
                        pstmt.setString(2, selectedCourse);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            int id = rs.getInt("studentID");
                            int confirmation = showConfirmDialog(
                                    panel, "Are you sure you want to delete score for\n"+ "Student ID : " + id + " Name :" + firstName + " " + lastName  + "\nInto course : " + selectedCourse + " ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                                NumberInputDialog.showDeleteDialog(frame, Integer.parseInt(studentID), selectedCourse);
                            }
                        } else {
                            showMessageDialog(panel, "No student found with ID : " + studentID+"\nIn course "+selectedCourse);
                            int confirmation = showConfirmDialog(
                                    panel, "Do you want to want view list of Enrollment ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(CourseFrame.mainCourse());
                                frame.revalidate();
                                frame.repaint();
                            }
                        }
                    } catch (SQLException ex) {
                        showMessageDialog(panel, "Error searching for student : " + ex.getMessage());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        jButton6.setText("Back To Home");
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(MainFrame.homePage());
                frame.revalidate();
                frame.repaint();
            }
        });

        return panel;
    }
}
