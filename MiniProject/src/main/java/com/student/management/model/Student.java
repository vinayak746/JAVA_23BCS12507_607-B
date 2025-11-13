package com.student.management.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Student Entity Class
 * Represents the 'students' table in the database
 * Demonstrates: Entity mapping with Hibernate annotations
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    // Constructors
    public Student() {
        this.balance = BigDecimal.ZERO;
    }

    public Student(String name, Course course, BigDecimal balance) {
        this.name = name;
        this.course = course;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Business methods
    public void addBalance(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
        }
    }

    public void deductBalance(BigDecimal amount) throws Exception {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            if (this.balance.compareTo(amount) >= 0) {
                this.balance = this.balance.subtract(amount);
            } else {
                throw new Exception("Insufficient balance. Current balance: " + this.balance);
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", course=" + (course != null ? course.getCourseName() : "None") +
                ", balance=" + balance +
                '}';
    }
}
