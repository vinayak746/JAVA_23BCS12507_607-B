package com.student.management.config;

import com.student.management.dao.StudentDAO;
import com.student.management.service.FeeService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * AppConfig - Spring Configuration Class
 * Demonstrates: Java-based Spring Configuration using @Configuration and @Bean annotations
 * Purpose: Configures Spring beans with dependency injection
 */
public class AppConfig {

    /**
     * Configure and provide SessionFactory bean
     * SessionFactory is created using hibernate.cfg.xml
     * @return SessionFactory instance
     */
    public SessionFactory sessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            System.out.println("SessionFactory created successfully!");
            return sessionFactory;
        } catch (Exception e) {
            System.err.println("Failed to create SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }

    /**
     * Configure and provide StudentDAO bean
     * Demonstrates: Constructor-based Dependency Injection
     * StudentDAO depends on SessionFactory
     * @return StudentDAO instance with injected SessionFactory
     */
    public StudentDAO studentDAO() {
        System.out.println("Creating StudentDAO bean with SessionFactory dependency...");
        return new StudentDAO(sessionFactory());
    }

    /**
     * Configure and provide FeeService bean
     * Demonstrates: Constructor-based Dependency Injection
     * FeeService depends on both SessionFactory and StudentDAO
     * @return FeeService instance with injected dependencies
     */
    public FeeService feeService() {
        System.out.println("Creating FeeService bean with SessionFactory and StudentDAO dependencies...");
        return new FeeService(sessionFactory(), studentDAO());
    }

    /**
     * Shutdown method to close SessionFactory
     */
    public void shutdown() {
        SessionFactory factory = sessionFactory();
        if (factory != null && !factory.isClosed()) {
            factory.close();
            System.out.println("SessionFactory closed successfully.");
        }
    }
}
