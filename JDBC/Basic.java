import java.sql.*;

public class Basic {
    public static void main(String[] args) {
        // Read from env with sensible defaults
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "3306");
        String dbName = System.getenv().getOrDefault("DB_NAME", "testdb");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "1234");

        String baseUrl = "jdbc:mysql://" + host + ":" + port + "/";
        String params = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String serverUrl = baseUrl + params;
        String dbUrl = baseUrl + dbName + params;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ensure database exists
            try (Connection serverCon = DriverManager.getConnection(serverUrl, user, password);
                 Statement serverSt = serverCon.createStatement()) {
                serverSt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            }

            // Work inside the database
            try (Connection con = DriverManager.getConnection(dbUrl, user, password);
                 Statement st = con.createStatement()) {

                st.executeUpdate("CREATE TABLE IF NOT EXISTS students(" +
                                 "id INT PRIMARY KEY, " +
                                 "name VARCHAR(50) NOT NULL)");

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO students(id, name) VALUES(?, ?) " +
                        "ON DUPLICATE KEY UPDATE name = VALUES(name)")) {
                    ps.setInt(1, 1);
                    ps.setString(2, "Vinayak");
                    ps.executeUpdate();
                }

                try (ResultSet rs = st.executeQuery("SELECT id, name FROM students")) {
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " " + rs.getString("name"));
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC driver not found in classpath.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}