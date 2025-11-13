package Hibernate.TransactionManagement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TransactionExample {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        
        Session session = factory.getCurrentSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Create new student
            Student student = new Student("John", "Doe", "john@example.com");
            session.save(student);
            
            // Commit transaction
            transaction.commit();
            System.out.println("Transaction committed successfully!");
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transaction rolled back!");
            }
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}
