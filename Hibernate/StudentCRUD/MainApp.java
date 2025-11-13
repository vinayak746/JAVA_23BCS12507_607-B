package Hibernate.StudentCRUD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        
        Session session = factory.getCurrentSession();
        
        try {
            // CREATE
            System.out.println("Creating new student...");
            session.beginTransaction();
            Student student = new Student("Alice", "Smith", "alice@example.com");
            session.save(student);
            session.getTransaction().commit();
            System.out.println("Student created: " + student);
            
            // READ
            System.out.println("\nReading student...");
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student retrievedStudent = session.get(Student.class, student.getId());
            System.out.println("Student retrieved: " + retrievedStudent);
            session.getTransaction().commit();
            
            // UPDATE
            System.out.println("\nUpdating student...");
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student studentToUpdate = session.get(Student.class, student.getId());
            studentToUpdate.setEmail("alice.smith@example.com");
            session.getTransaction().commit();
            System.out.println("Student updated: " + studentToUpdate);
            
            // DELETE
            System.out.println("\nDeleting student...");
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student studentToDelete = session.get(Student.class, student.getId());
            session.delete(studentToDelete);
            session.getTransaction().commit();
            System.out.println("Student deleted!");
            
        } finally {
            factory.close();
        }
    }
}
