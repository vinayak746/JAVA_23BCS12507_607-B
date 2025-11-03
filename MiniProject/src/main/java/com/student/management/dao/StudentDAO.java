package com.student.management.dao;

import com.student.management.model.Course;
import com.student.management.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * StudentDAO - Data Access Object for Student Entity
 * Demonstrates: CRUD Operations using Hibernate
 * - Create: Add new student
 * - Read: Get student by ID, get all students
 * - Update: Update student details
 * - Delete: Remove student
 */
public class StudentDAO {

    private SessionFactory sessionFactory;

    // Constructor injection of SessionFactory
    public StudentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * CREATE - Add a new student
     */
    public void addStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            System.out.println("Student added successfully: " + student.getName());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * READ - Get student by ID
     */
    public Student getStudentById(Long studentId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, studentId);
        } catch (Exception e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * READ - Get all students
     */
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving students: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * UPDATE - Update student details
     */
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
            System.out.println("Student updated successfully: " + student.getName());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * DELETE - Remove student
     */
    public void deleteStudent(Long studentId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
                System.out.println("Student deleted successfully: " + student.getName());
            } else {
                System.out.println("Student not found with ID: " + studentId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get students by course
     */
    public List<Student> getStudentsByCourse(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery(
                "FROM Student s WHERE s.course = :course", Student.class);
            query.setParameter("course", course);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving students by course: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add a course to database
     */
    public void addCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            System.out.println("Course added successfully: " + course.getCourseName());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error adding course: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get course by ID
     */
    public Course getCourseById(Long courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        } catch (Exception e) {
            System.err.println("Error retrieving course: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all courses
     */
    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
