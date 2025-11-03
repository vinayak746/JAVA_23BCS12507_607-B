package Hibernate.SpringDI;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    
    // Constructor for Constructor-based DI
    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Default constructor
    public Student() {
    }
    
    // Setters for Setter-based DI
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getters
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
