import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Input {

    public static JPanel login(){

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton login ;
        JButton register ;

        panel.setLayout(null);

        JTextField textField ;
        JTextField passwordField ;

        label.setText("Welcome to Student Management System ");
        label.setBounds(360,100,1000,50);
        label.setFont(new Font("CADT Mono Display",Font.BOLD,30));
        panel.add(label);

        label = new JLabel("Login");
        label.setBounds(800,230,200,30);
        label.setFont(new Font("AKbalthom HighSchool",Font.PLAIN,25));
        panel.add(label);

        label = new JLabel("Username : ");
        label.setBounds(570,370,200,30);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        label = new JLabel("Password : ");
        label.setBounds(570,450,200,30);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        textField = new JTextField("");
        textField.setBounds(680,370,350,40);
        textField.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(textField);

        passwordField = new JPasswordField("");
        passwordField.setBounds(680,450,350,40);
        passwordField.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(passwordField);

        register = new JButton("Register");
        register.setBounds(650,550,125,45);
        register.setFont(new Font("Times new roman",Font.PLAIN,20));
        register.setBackground(Color.cyan);
        panel.add(register);

        login = new JButton("Login");
        login.setBounds(850,550,125,45);
        login.setFont(new Font("Times new roman",Font.PLAIN,20));
        login.setBackground(Color.cyan);
        panel.add(login);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(register());
                frame.revalidate();
                frame.repaint();
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usernameInput = textField.getText();
                String passwordInput = passwordField.getText();

                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                try {
                    Connection connection = connectDatabase.getConnection();
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, usernameInput);
                    pstmt.setString(2, passwordInput);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        boolean status = rs.getBoolean("status");
                        if (status) {
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                            frame.setContentPane(MainFrame.homePage());
                            frame.revalidate();
                            frame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(panel, "Your account needs to be approved by an admin.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "Incorrect username or password. Please try again.");
                        textField.setText("");
                        passwordField.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error connecting to the database.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error connecting to the database.");
                }
            }
        });

        return panel;
    }
    public static JPanel register(){

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        JButton login ;
        JButton register ;

        panel.setLayout(null);

        JTextField username ;
        JTextField email ;
        JPasswordField password ;
        JPasswordField confirmPassword ;

        label.setText("Welcome to Student Management System ");
        label.setBounds(360,100,1000,50);
        label.setFont(new Font("CADT Mono Display",Font.BOLD,30));
        panel.add(label);

        label = new JLabel("Register");
        label.setBounds(800,230,200,40);
        label.setFont(new Font("AKbalthom HighSchool",Font.PLAIN,25));
        panel.add(label);

        label = new JLabel("Username : ");
        label.setBounds(520,300,200,40);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        label = new JLabel("Email : ");
        label.setBounds(520,370,200,40);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        label = new JLabel("Password : ");
        label.setBounds(520,440,200,40);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        label = new JLabel("Confirm Password : ");
        label.setBounds(520,510,200,40);
        label.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(label);

        username = new JTextField("");
        username.setBounds(720,300,350,40);
        username.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(username);

        email = new JTextField("");
        email.setBounds(720,370,350,40);
        email.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(email);

        password = new JPasswordField("");
        password.setBounds(720,440,350,40);
        password.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(password);

        confirmPassword = new JPasswordField("");
        confirmPassword.setBounds(720,510,350,40);
        confirmPassword.setFont(new Font("Times new roman",Font.PLAIN,20));
        panel.add(confirmPassword);

        register = new JButton("Register");
        register.setBounds(870,650,125,45);
        register.setFont(new Font("Times new roman",Font.PLAIN,20));
        register.setBackground(Color.cyan);
        panel.add(register);

        login = new JButton("Login");
        login.setBounds(650,650,125,45);
        login.setFont(new Font("Times new roman",Font.PLAIN,20));
        login.setBackground(Color.cyan);
        panel.add(login);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = username.getText();
                String emailInput = email.getText();
                String passwordInput = password.getText();
                String confirmPasswordInput = confirmPassword.getText();

                if (usernameInput.equals("") || emailInput.equals("") || passwordInput.equals("") || confirmPasswordInput.equals("")) {
                    JOptionPane.showMessageDialog(panel, "All fields are required. Please fill them out.");
                } else if (!passwordInput.equals(confirmPasswordInput)) {
                    JOptionPane.showMessageDialog(panel, "Passwords do not match. Please try again.");
                } else {
                    try {
                        Connection conn = connectDatabase.getConnection();
                        String query = "INSERT INTO users (username, email, password ,confirmPassword, status) VALUES (?,?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, usernameInput);
                        pstmt.setString(2, emailInput);
                        pstmt.setString(3, passwordInput);
                        pstmt.setString(4, confirmPasswordInput);
                        pstmt.setBoolean(5, false);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Registration successful. Your account will need to be approved by an admin.");
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                        frame.setContentPane(login());
                        frame.revalidate();
                        frame.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Registration failed. Please try again.");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(login());
                frame.revalidate();
                frame.repaint();
            }
        });

        return panel;
    }
    public static Container showUpdateDialog(JFrame parentFrame, int studentID ) {

        try {
            Connection conn = connectDatabase.getConnection();

            // Get existing score for the student in the course
            String query = "SELECT * FROM student WHERE studentID = ? ";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentID);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(parentFrame, "No score found for student " + studentID , "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Create input fields for new score values
            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField firstNameField = new JTextField(rs.getString("firstName"));
            JTextField lastNameField = new JTextField(rs.getString("lastName"));
            JTextField birthdayField = new JTextField(rs.getString("Birthday"));
            JTextField emailField = new JTextField(rs.getString("email"));
            JTextField phoneField = new JTextField(rs.getString("phoneNumber"));


            panel.add(new JLabel("First Name :"));
            panel.add(firstNameField);
            panel.add(new JLabel("Last Name :"));
            panel.add(lastNameField);
            panel.add(new JLabel("Birthday :"));
            panel.add(birthdayField);
            panel.add(new JLabel("email : "));
            panel.add(emailField);
            panel.add(new JLabel("Phone Number : "));
            panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Update Student Information" , JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                Date birthday = Date.valueOf(birthdayField.getText());
                int age = StudentFrame.calculateAge(String.valueOf(birthday));

                // Update the score in the database
                query = "UPDATE student SET firstName = ?, lastName = ?, birthday = ?, age = ?, phoneNumber = ?, email = ? WHERE studentID = ? ";
                PreparedStatement pstmt2 = conn.prepareStatement(query);
                pstmt2.setString(1, firstName);
                pstmt2.setString(1+1, lastName);
                pstmt2.setDate(1+2, birthday);
                pstmt2.setInt(1+3, age);
                pstmt2.setString(1+4, phone);
                pstmt2.setString(1+5, email);
                pstmt2.setInt(1+6, studentID);

                int rowsUpdated = pstmt2.executeUpdate();
                if (rowsUpdated == 0) {
                    JOptionPane.showMessageDialog(parentFrame, "No Student found for student " + studentID , "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Student Information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    parentFrame.setContentPane(StudentFrame.studentInfo());
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
    public static Container addStudent(JFrame parentFrame) {
        try {
            Connection conn = connectDatabase.getConnection();

            JPanel panel = new JPanel(new GridLayout(6, 2));
            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JComboBox<String> genderField = new JComboBox<>(new String[]{"Male", "Female", "Other"});
            JTextField birthdayField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();

            panel.add(new JLabel("First Name:"));
            panel.add(firstNameField);
            panel.add(new JLabel("Last Name:"));
            panel.add(lastNameField);
            panel.add(new JLabel("Gender:"));
            panel.add(genderField);
            panel.add(new JLabel("Birthday (YYYY-MM-DD):"));
            panel.add(birthdayField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Phone Number:"));
            panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add a Student", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                // insert the student's information into the database
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String gender = (String) genderField.getSelectedItem();
                Date birthday = Date.valueOf(birthdayField.getText());
                String email = emailField.getText();
                String phone = phoneField.getText();
                int age = StudentFrame.calculateAge(birthdayField.getText());

                String query = "INSERT INTO student (firstName, lastName, gender, birthday, email, phoneNumber,age) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, gender);
                pstmt.setDate(4, birthday);
                pstmt.setString(5, email);
                pstmt.setString(6, phone);
                pstmt.setInt(7, age);

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(parentFrame, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                parentFrame.setContentPane(StudentFrame.studentInfo());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


}