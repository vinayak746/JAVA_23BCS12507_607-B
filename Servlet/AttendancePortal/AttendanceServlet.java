package Servlet.AttendancePortal;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String studentName = request.getParameter("studentName");
        String status = request.getParameter("status");
        
        // Set attributes to forward to result page
        request.setAttribute("studentName", studentName);
        request.setAttribute("status", status);
        
        // Forward to result JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("attendanceResult.jsp");
        dispatcher.forward(request, response);
    }
}
