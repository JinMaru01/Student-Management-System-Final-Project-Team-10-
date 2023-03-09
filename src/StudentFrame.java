import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;


public class StudentFrame {

    public static JPanel studentInfo() throws Exception {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel();
        label.setText("List of Student ");
        label.setBounds(700,20,1000,50);
        label.setFont(new Font("AKbalthom HighSchool", Font.PLAIN,20));

        JButton update = new JButton();
        update.setFont(new Font("Times new roman",Font.PLAIN,17));
        update.setBounds(450,750,250,50);
        update.setBackground(Color.ORANGE);

        JButton add = new JButton();
        add.setFont(new Font("Times new roman",Font.PLAIN,17));
        add.setBounds(100,750,250,50);
        add.setBackground(Color.ORANGE);

        JButton delete = new JButton();
        delete.setFont(new Font("Times new roman",Font.PLAIN,17));
        delete.setBounds(800,750,250,50);
        delete.setBackground(Color.ORANGE);

        JButton back = new JButton();
        back.setFont(new Font("Times new roman",Font.PLAIN,17));
        back.setBounds(1150,750,250,50);
        back.setBackground(Color.ORANGE);

        JScrollPane jScrollPane = new JScrollPane();

        update.setText("Update Student Information");
        add.setText("Add New Student");
        delete.setText("Delete Student From List");
        back.setText("Back");

        // Connect to MySQL database and query data from student
        String query = "SELECT * FROM student ORDER BY studentID";
        ResultSet rs = null;
        try {
            Statement stmt = connectDatabase.getConnection().createStatement();
            connectDatabase.updateStudentTable();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JTable jTable = new JTable();
        jTable.setFont(new Font("Times new roman",Font.PLAIN,15));
        // Create a table model and populate it with the data from the result set
        DefaultTableModel model = new DefaultTableModel(new Object [][] {}, new String [] {"Student ID", "First Name", "Last Name","Gender", "Age", "Birthday", "Phone Number", "Email"});
        try {
            while (rs.next()) {
                int id = rs.getInt("studentID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                String birthday = rs.getString("birthday");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String gender = rs.getString("gender");

                model.addRow(new Object[] {id, firstName, lastName,gender, age, birthday, phoneNumber, email});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // Set the table model to the JTable
        jTable.setModel(model);

        // Add the JTable to the JScrollPane
        jScrollPane.setViewportView(jTable);
        jScrollPane.setBounds(25, 80, 1500, 650);
        jScrollPane.setFont(new Font("Times new roman",Font.PLAIN,17));

        panel.add(jScrollPane);
        panel.add(label);
        panel.add(back);
        panel.add(update);
        panel.add(delete);
        panel.add(add);

        JTable finalJTable = jTable;
        // action
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(Input.addStudent(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int selectedRow = finalJTable.getSelectedRow();
                if (selectedRow == -1) {
                    // Show warning dialog if no row is selected
                    JOptionPane.showMessageDialog(panel, "Please select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    String message = String.format("Are you sure you want to delete the following student?%n%n" +
                            "Name: %s %s%n" +
                            "Birthday: %s%n" +
                            "Age: %s%n" +
                            "Email: %s%n" +
                            "Phone Number : %s%n", model.getValueAt(selectedRow,1), model.getValueAt(selectedRow,2), model.getValueAt(selectedRow,4), model.getValueAt(selectedRow,3), model.getValueAt(selectedRow,6),model.getValueAt(selectedRow,5));
                    int option = JOptionPane.showConfirmDialog(panel, message, "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) finalJTable.getModel();
                        int studentID = (int) model.getValueAt(selectedRow, 0);
                        String query = "DELETE FROM student WHERE studentID = " + studentID;
                        try {
                            Statement stmt = connectDatabase.getConnection().createStatement();
                            int result = stmt.executeUpdate(query);
                            if (result == 1) {
                                model.removeRow(selectedRow);
                                JOptionPane.showMessageDialog(panel, "Student deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                                frame.setContentPane(StudentFrame.studentInfo());
                                frame.revalidate();
                                frame.repaint();
                            } else {
                                JOptionPane.showMessageDialog(panel, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(panel, "An error occurred while deleting the student.", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(MainFrame.homePage());
                frame.revalidate();
                frame.repaint();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(panel, "Enter student ID : ");
                if (id == null) {
                    // User clicked Cancel or closed dialog
                    return;
                } else if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter a student ID to search.");
                } else {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                    frame.setContentPane(Input.showUpdateDialog(frame, Integer.parseInt(id)));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
//        update.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String id = JOptionPane.showInputDialog(panel, "Enter student ID : ");
//                if (id == null) {
//                    // User clicked Cancel or closed dialog
//                    return;
//                } else if (id.isEmpty()) {
//                    JOptionPane.showMessageDialog(panel, "Please enter a student ID to search.");
//                } else {
//                    // Search for student with given ID and display information
//                    try {
//                        Connection conn = connectDatabase.getConnection();
//                        String query = "SELECT * FROM student WHERE studentID = ? ";
//                        PreparedStatement pstmt = conn.prepareStatement(query);
//                        pstmt.setString(1, id);
//                        ResultSet rs = pstmt.executeQuery();
//                        if (rs.next()) {
//                            String firstName = rs.getString("firstName");
//                            String lastName = rs.getString("lastName");
//                            String birthday = rs.getString("birthday");
//                            int age = rs.getInt("age");
//                            String phoneNumber = rs.getString("phoneNumber");
//                            String email = rs.getString("email");
//                            JOptionPane.showMessageDialog(panel, "Name: " + firstName + " " + lastName + "\nBirthday: " + birthday + "\nAge: " + age + "\nPhone: " + phoneNumber + "\nEmail: " + email, "Student Information", JOptionPane.INFORMATION_MESSAGE);
//
//                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
//
//                            try {
//                                frame.setContentPane(Input.updateStudent(Integer.parseInt(id),firstName,lastName,birthday,phoneNumber,email));
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                            frame.revalidate();
//                            frame.repaint();
//                        } else {
//                            JOptionPane.showMessageDialog(panel, "No student found with ID : " + id);
//                        }
//                        conn.close();
//                    } catch (SQLException ex) {
//                        JOptionPane.showMessageDialog(panel, "Error searching for student : " + ex.getMessage());
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            }
//        });

        return panel;
    }
    public static int calculateAge(String birthday) {
        LocalDate birthDate = LocalDate.parse(birthday);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
