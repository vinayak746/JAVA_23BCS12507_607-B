<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attendance Result</title>
</head>
<body>
    <h1>Attendance Result</h1>
    <p>Student Name: <%= request.getAttribute("studentName") %></p>
    <p>Status: <%= request.getAttribute("status") %></p>
    <a href="attendance.jsp">Back to Attendance Form</a>
</body>
</html>
