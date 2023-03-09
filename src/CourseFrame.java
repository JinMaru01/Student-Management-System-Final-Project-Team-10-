import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static javax.swing.JOptionPane.*;

public class CourseFrame {
    public static JPanel mainCourse() throws Exception {
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

        jLabel1.setText("List of Course Enrollment");
        jLabel1.setFont(new Font("AKbalthom HighSchool",Font.BOLD,27));
        jLabel1.setBounds(670,20,1000,50);
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

        jButton5.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton5.setBounds(730,750,155,50);
        panel.add(jButton5);

        jButton3.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton3.setBounds(930,750,155,50);
        panel.add(jButton3);

        jButton6.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton6.setBounds(1130,750,155,50);
        panel.add(jButton6);

        jButton4.setFont(new Font("Times new roman",Font.PLAIN,17));
        jButton4.setBounds(1330,750,155,50);
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
        model.addColumn("Age");
        model.addColumn("Birthday");
        model.addColumn("Phone Number");
        model.addColumn("Course");

        try {
            String query = "SELECT student.studentID, student.firstName, student.lastName, student.age, student.birthday, student.phoneNumber, enroll.courseName " +
                    "FROM student " +
                    "INNER JOIN enroll ON student.studentID = enroll.studentID " +
                    "ORDER BY enroll.courseName ASC, studentID ASC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs1 = pstmt.executeQuery();
            while (rs1.next()) {
                int id = rs1.getInt("studentID");
                String firstName = rs1.getString("firstName");
                String lastName = rs1.getString("lastName");
                int age = rs1.getInt("age");
                Date birthday = rs1.getDate("birthday");
                String phoneNumber = rs1.getString("phoneNumber");
                String courseName = rs1.getString("courseName");
                model.addRow(new Object[]{id, firstName + " " + lastName, age, birthday, phoneNumber, courseName});
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Set model to table
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 1430, 650);
        panel.add(scrollPane);

        jButton1.setText("Add Course");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show input dialog to get course name
                String courseName = showInputDialog(panel, "Enter course name:");
                // Check if input is not empty
                if (courseName==null){
                    return;
                }else if (courseName.isEmpty()){
                    showMessageDialog(panel, "Please enter a course name.", "Error", ERROR_MESSAGE);
                }else {
                    // Check if course name already exists in database
                    try {
                        Connection conn = connectDatabase.getConnection();
                        Statement stm = conn.createStatement();
                        connectDatabase.updateCourseTable();
                        ResultSet rs = stm.executeQuery("SELECT courseName FROM course WHERE courseName='" + courseName + "'");

                        if (rs.next()) {
                            // Course name already exists in database
                            showMessageDialog(panel, "Course already exists.", "Error", ERROR_MESSAGE);
                        } else {
                            // Course name doesn't exist in database
                            // Add course to database
                            stm.executeUpdate("INSERT INTO course (courseName) VALUES ('" + courseName + "')");
                            // Add course to menu
                            jComboBox1.addItem(courseName);
                            JOptionPane.showMessageDialog(panel, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        }
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        jButton2.setText("Enroll Student");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedCourse = (String) jComboBox1.getSelectedItem();
                String studentID = showInputDialog(panel, "Input student ID:");
                if (studentID == null){
                    return;
                }else if (studentID.isEmpty()){
                    showMessageDialog(panel, "Please enter a Student ID first.", "Error", ERROR_MESSAGE);
                }else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        connectDatabase.updateEnrollTable();
                        String query = "SELECT * FROM student WHERE studentID = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentID);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {

                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            int id = rs.getInt("studentID");

                            int confirmation = JOptionPane.showConfirmDialog(
                                    panel, "Are you sure you want to add student\n"+ "Student ID " + id + " Name :" + firstName + " " + lastName + "\nInto course : " + selectedCourse + " ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                String query2 = "INSERT INTO enroll (studentID,courseName) VALUES (?,?)";
                                PreparedStatement pstmt2 = conn.prepareStatement(query2);
                                pstmt2.setInt(1, id);
                                pstmt2.setString(2, selectedCourse);
                                pstmt2.executeUpdate();

                                JOptionPane.showMessageDialog(panel, "Enroll student successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(CourseFrame.mainCourse());
                                frame.revalidate();
                                frame.repaint();
                            }
                        } else {
                            showMessageDialog(panel, "No student found with ID : " + studentID);
                            int confirmation = JOptionPane.showConfirmDialog(
                                    panel, "Do you want to want view list of students  ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation==OK_OPTION){
                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                                frame.setContentPane(StudentFrame.studentInfo());
                                frame.revalidate();
                                frame.repaint();
                            }
                        }
                    } catch (SQLException ex) {
                        showMessageDialog(panel, "Error searching for student : " + ex.getMessage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        jButton3.setText("Remove Course");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String selectedCourse = (String) jComboBox1.getSelectedItem();
                    int confirmation = JOptionPane.showConfirmDialog(panel, "Are you sure you want to remove " + selectedCourse + "?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        Connection conn = connectDatabase.getConnection();
                        Statement stm = conn.createStatement();
                        stm.executeUpdate("DELETE FROM score WHERE courseName='" + selectedCourse + "'");
                        stm.executeUpdate("DELETE FROM enroll WHERE courseName='" + selectedCourse + "'");
                        stm.executeUpdate("DELETE FROM course WHERE courseName='" + selectedCourse + "'");
                        jComboBox1.removeItem(selectedCourse);
                        JOptionPane.showMessageDialog(panel, "Course removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        conn.close();
                    }
                } catch (SQLException ex) {
                    showMessageDialog(panel, "Error removing course: " + ex.getMessage(), "Error", ERROR_MESSAGE);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        jButton4.setText("Back To Home");
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(MainFrame.homePage());
                frame.revalidate();
                frame.repaint();
            }
        });

        jButton5.setText("View Enrolled");
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                panel.remove(scrollPane);
                // Create table and model
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel();
                table.setFont(new Font("Times new roman",Font.PLAIN,15));

                // Set column names
                model.addColumn("Student ID");
                model.addColumn("Name");
                model.addColumn("Age");
                model.addColumn("Birthday");
                model.addColumn("Phone Number");
                model.addColumn("Course");
                try {
                    Connection conn = connectDatabase.getConnection();
                    String selectedCourse = (String) jComboBox1.getSelectedItem();
                    String query = "SELECT student.studentID, student.firstName,student.lastName,student.age,student.birthday,student.phoneNumber FROM student INNER JOIN enroll ON student.studentID = enroll.studentID WHERE enroll.courseName = ? ORDER BY studentID";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, selectedCourse);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt("studentID");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");
                        int age = rs.getInt("age");
                        Date birthday = rs.getDate("birthday");
                        String phoneNumber = rs.getString("phoneNumber");
                        model.addRow(new Object[]{id, firstName +" "+lastName, age,birthday,phoneNumber,selectedCourse});
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Set model to table
                table.setModel(model);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(50, 80, 1430, 650);
                panel.add(scrollPane);
            }
        });


        jButton6.setText("Remove Enroll");
        jButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(scrollPane);
                String selectedCourse = (String) jComboBox1.getSelectedItem();
                String studentID = showInputDialog(panel, "Input student ID:");
                if (studentID == null){
                    return;
                }else if (studentID.isEmpty()){
                    showMessageDialog(panel, "Please enter a Student ID first.", "Error", ERROR_MESSAGE);
                }else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        String query = "SELECT * FROM student WHERE studentID = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentID);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()){
                            String firstName = rs.getString("firstName");
                            String lastName = rs.getString("lastName");
                            String id = rs.getString("StudentID");
                            int confirmation = JOptionPane.showConfirmDialog(
                                    panel, "Are you sure you want remove " + firstName + " " + lastName + " ID " + studentID + "\n From course : " + selectedCourse + " ?",
                                    "Confirmation",
                                    OK_CANCEL_OPTION
                            );
                            if (confirmation == JOptionPane.YES_OPTION) {
                                Connection con = connectDatabase.getConnection();
                                Statement stm = con.createStatement();
                                stm.executeUpdate("DELETE FROM enroll WHERE studentID ='" + studentID + "' AND courseName = '"+selectedCourse+"'" );
                                stm.executeUpdate("DELETE FROM score WHERE studentID ='" + studentID + "' AND courseName = '"+selectedCourse+"'" );
                                JOptionPane.showMessageDialog(panel, "Student removed enroll successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                con.close();

                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(CourseFrame.mainCourse());
                                frame.revalidate();
                                frame.repaint();
                            }
                        }else {
                            showMessageDialog(panel, "No student found with ID : " + studentID);
                        }
                    } catch (SQLException ex) {
                        showMessageDialog(panel, "Error removing course: " + ex.getMessage(), "Error", ERROR_MESSAGE);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        return panel;
    }
}
