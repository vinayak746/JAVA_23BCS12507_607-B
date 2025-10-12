

import java.util.*;

class Employee {
    String name;
    int age;
    double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String toString() {
        return name + " - Age: " + age + ", Salary: " + salary;
    }
}

public class Lambda {
    public static void main(String[] args) {
        List<Employee> empList = new ArrayList<>();
        empList.add(new Employee("Arjun", 28, 70000));
        empList.add(new Employee("Bhavna", 35, 90000));
        empList.add(new Employee("Chirag", 24, 60000));
        empList.add(new Employee("Deepa", 28, 80000));

        // Sort by name (alphabetically)
        empList.sort((e1, e2) -> e1.name.compareTo(e2.name));
        System.out.println("Sorted by Name:");
        empList.forEach(System.out::println);

        // Sort by age (ascending)
        empList.sort(Comparator.comparingInt(e -> e.age));
        System.out.println("\nSorted by Age:");
        empList.forEach(System.out::println);

        // Sort by salary (descending)
        empList.sort((e1, e2) -> Double.compare(e2.salary, e1.salary));
        System.out.println("\nSorted by Salary (Descending):");
        empList.forEach(System.out::println);
    }
}
