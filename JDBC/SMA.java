import java.sql.*;
import java.util.*;

public class SMA {
    // Model class
    static class Student {
        private int studentID;
        private String name;
        private String department;
        private double marks;

        public Student(int studentID, String name, String department, double marks) {
            this.studentID = studentID;
            this.name = name;
            this.department = department;
            this.marks = marks;
        }
        // Getters
        public int getStudentID() { return studentID; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getMarks() { return marks; }
    }

    // Controller class with JDBC logic
    static class StudentController {
        private final Connection conn;

        public StudentController(Connection conn) {
            this.conn = conn;
        }

        public void addStudent(Student s) throws SQLException {
            String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, s.getStudentID());
                ps.setString(2, s.getName());
                ps.setString(3, s.getDepartment());
                ps.setDouble(4, s.getMarks());
                ps.executeUpdate();
            }
        }

        public List<Student> getAllStudents() throws SQLException {
            List<Student> list = new ArrayList<>();
            String sql = "SELECT * FROM Student";
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Student(
                        rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getDouble("Marks")
                    ));
                }
            }
            return list;
        }

        public void updateStudent(Student s) throws SQLException {
            String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, s.getName());
                ps.setString(2, s.getDepartment());
                ps.setDouble(3, s.getMarks());
                ps.setInt(4, s.getStudentID());
                ps.executeUpdate();
            }
        }

        public void deleteStudent(int studentID) throws SQLException {
            String sql = "DELETE FROM Student WHERE StudentID=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentID);
                ps.executeUpdate();
            }
        }
    }

    // View + Main program
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String pass = "your_password";

        try (
            Connection conn = DriverManager.getConnection(jdbcURL, user, pass);
            Scanner sc = new Scanner(System.in)
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load Driver
            StudentController controller = new StudentController(conn);

            while (true) {
                System.out.println("\nMenu:\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                if (choice == 5) break;

                switch (choice) {
                    case 1:
                        System.out.print("StudentID: "); int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Name: "); String name = sc.nextLine();
                        System.out.print("Department: "); String dept = sc.nextLine();
                        System.out.print("Marks: "); double marks = sc.nextDouble();
                        controller.addStudent(new Student(id, name, dept, marks));
                        System.out.println("Student added.");
                        break;
                    case 2:
                        List<Student> students = controller.getAllStudents();
                        System.out.println("StudentID | Name | Department | Marks");
                        for (Student s : students) {
                            System.out.printf("%d | %s | %s | %.2f%n",
                                s.getStudentID(), s.getName(), s.getDepartment(), s.getMarks());
                        }
                        break;
                    case 3:
                        System.out.print("StudentID to update: "); int upId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Name: "); String upName = sc.nextLine();
                        System.out.print("New Department: "); String upDept = sc.nextLine();
                        System.out.print("New Marks: "); double upMarks = sc.nextDouble();
                        controller.updateStudent(new Student(upId, upName, upDept, upMarks));
                        System.out.println("Student updated.");
                        break;
                    case 4:
                        System.out.print("StudentID to delete: "); int delId = sc.nextInt();
                        controller.deleteStudent(delId);
                        System.out.println("Student deleted.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
