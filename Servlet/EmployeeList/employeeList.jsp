<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee List</title>
</head>
<body>
    <h1>Employee List</h1>
    <ul>
    <%
        List<String> employees = (List<String>) request.getAttribute("employeeList");
        if (employees != null) {
            for (String employee : employees) {
    %>
                <li><%= employee %></li>
    <%
            }
        }
    %>
    </ul>
</body>
</html>
