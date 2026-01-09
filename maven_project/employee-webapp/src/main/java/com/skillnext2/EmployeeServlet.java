package com.skillnext2;

import com.skillnext2.EmployeeDAO;
import com.skillnext2.Employee;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class EmployeeServlet extends HttpServlet {
	 @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.getWriter().println("<h2>Employee Servlet is WORKING</h2>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        double salary = Double.parseDouble(req.getParameter("salary"));

        Employee emp = new Employee();
        emp.setName(name);
        emp.setEmail(email);
        emp.setSalary(salary);

        EmployeeDAO.save(emp);

        res.sendRedirect("success.jsp");
    }
}
