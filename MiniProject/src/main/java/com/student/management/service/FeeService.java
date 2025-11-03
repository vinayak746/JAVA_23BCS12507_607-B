package com.student.management.service;

import com.student.management.dao.StudentDAO;
import com.student.management.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;

/**
 * FeeService - Service layer for fee management
 * Demonstrates: Transaction Management for payment and refund operations
 * Ensures atomicity and rollback on failure
 */
public class FeeService {

    private SessionFactory sessionFactory;
    private StudentDAO studentDAO;

    // Constructor injection
    public FeeService(SessionFactory sessionFactory, StudentDAO studentDAO) {
        this.sessionFactory = sessionFactory;
        this.studentDAO = studentDAO;
    }

    /**
     * Process fee payment for a student
     * Demonstrates transaction management with rollback on failure
     * @param studentId ID of the student making payment
     * @param amount Amount to be paid
     * @return true if payment successful, false otherwise
     */
    public boolean processPayment(Long studentId, BigDecimal amount) {
        Transaction transaction = null;
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Get student
            Student student = session.get(Student.class, studentId);
            
            if (student == null) {
                System.err.println("Student not found with ID: " + studentId);
                return false;
            }
            
            // Validate amount
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("Invalid payment amount: " + amount);
                return false;
            }
            
            // Deduct balance (will throw exception if insufficient funds)
            student.deductBalance(amount);
            
            // Update student in database
            session.update(student);
            
            // Commit transaction
            transaction.commit();
            
            System.out.println("Payment processed successfully!");
            System.out.println("Student: " + student.getName());
            System.out.println("Amount Paid: $" + amount);
            System.out.println("Remaining Balance: $" + student.getBalance());
            
            return true;
            
        } catch (Exception e) {
            // Rollback transaction on failure
            if (transaction != null) {
                transaction.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Process refund for a student
     * Demonstrates transaction management with rollback on failure
     * @param studentId ID of the student receiving refund
     * @param amount Amount to be refunded
     * @return true if refund successful, false otherwise
     */
    public boolean processRefund(Long studentId, BigDecimal amount) {
        Transaction transaction = null;
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Get student
            Student student = session.get(Student.class, studentId);
            
            if (student == null) {
                System.err.println("Student not found with ID: " + studentId);
                return false;
            }
            
            // Validate amount
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("Invalid refund amount: " + amount);
                return false;
            }
            
            // Add balance
            student.addBalance(amount);
            
            // Update student in database
            session.update(student);
            
            // Commit transaction
            transaction.commit();
            
            System.out.println("Refund processed successfully!");
            System.out.println("Student: " + student.getName());
            System.out.println("Amount Refunded: $" + amount);
            System.out.println("New Balance: $" + student.getBalance());
            
            return true;
            
        } catch (Exception e) {
            // Rollback transaction on failure
            if (transaction != null) {
                transaction.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Transfer fees from one student to another
     * Demonstrates complex transaction with multiple operations
     * @param fromStudentId ID of student paying
     * @param toStudentId ID of student receiving
     * @param amount Amount to transfer
     * @return true if transfer successful, false otherwise
     */
    public boolean transferFees(Long fromStudentId, Long toStudentId, BigDecimal amount) {
        Transaction transaction = null;
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            // Get both students
            Student fromStudent = session.get(Student.class, fromStudentId);
            Student toStudent = session.get(Student.class, toStudentId);
            
            if (fromStudent == null || toStudent == null) {
                System.err.println("One or both students not found");
                return false;
            }
            
            // Deduct from sender (will throw exception if insufficient funds)
            fromStudent.deductBalance(amount);
            
            // Add to receiver
            toStudent.addBalance(amount);
            
            // Update both students
            session.update(fromStudent);
            session.update(toStudent);
            
            // Commit transaction
            transaction.commit();
            
            System.out.println("Transfer completed successfully!");
            System.out.println("From: " + fromStudent.getName() + " (Balance: $" + fromStudent.getBalance() + ")");
            System.out.println("To: " + toStudent.getName() + " (Balance: $" + toStudent.getBalance() + ")");
            
            return true;
            
        } catch (Exception e) {
            // Rollback entire transaction on any failure
            if (transaction != null) {
                transaction.rollback();
                System.err.println("Transaction rolled back - transfer failed: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Get student balance
     * @param studentId ID of the student
     * @return Balance or null if student not found
     */
    public BigDecimal getStudentBalance(Long studentId) {
        Student student = studentDAO.getStudentById(studentId);
        return student != null ? student.getBalance() : null;
    }
}
