import java.sql.*;
import java.util.Scanner;

public class CRUD {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASS = "your_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Create Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                if (choice == 5) break;

                switch (choice) {
                    case 1: createProduct(conn, sc); break;
                    case 2: readProducts(conn); break;
                    case 3: updateProduct(conn, sc); break;
                    case 4: deleteProduct(conn, sc); break;
                    default: System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Create
    private static void createProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Product Name: "); sc.nextLine(); String name = sc.nextLine();
        System.out.print("Price: "); double price = sc.nextDouble();
        System.out.print("Quantity: "); int quantity = sc.nextInt();

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            conn.setAutoCommit(false);
            ps.executeUpdate();
            conn.commit();
            System.out.println("Product added.");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Failed to add product.");
        }
        conn.setAutoCommit(true);
    }

    // Read
    private static void readProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("ProductID\tProductName\tPrice\tQuantity");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%.2f\t%d\n",
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity"));
            }
        }
    }

    // Update
    private static void updateProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID to update: "); int id = sc.nextInt();
        sc.nextLine();
        System.out.print("New Product Name: "); String name = sc.nextLine();
        System.out.print("New Price: "); double price = sc.nextDouble();
        System.out.print("New Quantity: "); int quantity = sc.nextInt();

        String sql = "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setInt(4, id);
            conn.setAutoCommit(false);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Product updated.");
            } else {
                conn.rollback();
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Failed to update product.");
        }
        conn.setAutoCommit(true);
    }

    // Delete
    private static void deleteProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID to delete: "); int id = sc.nextInt();
        String sql = "DELETE FROM Product WHERE ProductID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            conn.setAutoCommit(false);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Product deleted.");
            } else {
                conn.rollback();
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Failed to delete product.");
        }
        conn.setAutoCommit(true);
    }
}
