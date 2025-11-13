package Servlet.EmployeeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Create list of employees
        List<String> employees = new ArrayList<>();
        employees.add("John Doe - Manager");
        employees.add("Jane Smith - Developer");
        employees.add("Bob Johnson - Designer");
        employees.add("Alice Williams - Analyst");
        
        // Set attribute and forward to JSP
        request.setAttribute("employeeList", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeList.jsp");
        dispatcher.forward(request, response);
    }
}
