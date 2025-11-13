package com.student.management.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Course Entity Class
 * Represents the 'courses' table in the database
 * Demonstrates: Entity mapping and One-to-Many relationship
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name", nullable = false, length = 100, unique = true)
    private String courseName;

    @Column(name = "duration", nullable = false)
    private Integer duration; // Duration in months

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    // Constructors
    public Course() {
    }

    public Course(String courseName, Integer duration) {
        this.courseName = courseName;
        this.duration = duration;
    }

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // Helper method to add student
    public void addStudent(Student student) {
        students.add(student);
        student.setCourse(this);
    }

    // Helper method to remove student
    public void removeStudent(Student student) {
        students.remove(student);
        student.setCourse(null);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration + " months" +
                '}';
    }
}
