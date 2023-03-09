import java.sql.*;


public class connectDatabase{
    static Statement stmt;
    public static Connection getConnection() throws Exception {

         // Connect to MySQL database
         String url = "jdbc:mysql://localhost:3306/students";
         String username = "root";
         String password = "";
         Connection conn = null;
         try {
             conn = DriverManager.getConnection(url, username, password);
             stmt = conn.createStatement();
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
         return conn;
     }
    public static void updateStudentTable() {
        try {
            // Set the counter to 0
            stmt.executeUpdate("SET @counter = 0");

            // Update the student table with the new student IDs
            stmt.executeUpdate("UPDATE student SET studentID = @counter:=@counter+1");

            // Reset the auto-increment value to 1
            stmt.executeUpdate("ALTER TABLE student AUTO_INCREMENT = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateCourseTable() {
        try {
            // Set the counter to 0
            stmt.executeUpdate("SET @counter = 0");

            // Update the student table with the new student IDs
            stmt.executeUpdate("UPDATE course SET courseID = @counter:=@counter+1;\n");

            // Reset the auto-increment value to 1
            stmt.executeUpdate("ALTER TABLE course AUTO_INCREMENT = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateEnrollTable() {
        try {
            // Set the counter to 0
            stmt.executeUpdate("SET @counter = 0");

            // Update the student table with the new student IDs
            stmt.executeUpdate("UPDATE enroll SET enrollID = @counter:=@counter+1;\n");

            // Reset the auto-increment value to 1
            stmt.executeUpdate("ALTER TABLE enroll AUTO_INCREMENT = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateScoreTable() {
        try {
            // Set the counter to 0
            stmt.executeUpdate("SET @counter = 0");

            // Update the student table with the new student IDs
            stmt.executeUpdate("UPDATE score SET scoreID = @counter:=@counter+1;\n");

            // Reset the auto-increment value to 1
            stmt.executeUpdate("ALTER TABLE score AUTO_INCREMENT = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 }
