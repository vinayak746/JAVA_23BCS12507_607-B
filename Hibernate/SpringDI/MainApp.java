package Hibernate.SpringDI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Constructor-based DI
        Student student1 = (Student) context.getBean("student1");
        System.out.println("Constructor DI: " + student1);
        
        // Setter-based DI
        Student student2 = (Student) context.getBean("student2");
        System.out.println("Setter DI: " + student2);
        
        ((ClassPathXmlApplicationContext) context).close();
    }
}
