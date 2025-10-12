import java.sql.*;

public class SQL_Connection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database"; // update with your DB name
        String user = "your_username"; // update with your username
        String password = "your_password"; // update with your password

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            // Create statement
            Statement stmt = conn.createStatement();
            // Execute query
            ResultSet rs = stmt.executeQuery("SELECT EmpID, Name, Salary FROM Employee");

            // Display results
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println("EmpID: " + empId + ", Name: " + name + ", Salary: " + salary);
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
