package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ViewEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Employee> employees = new ArrayList<>();

        String sql =
                "SELECT id, name, age, department, experience, state, country " +
                        "FROM employee ORDER BY id DESC";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setAge(rs.getInt("age"));
                employee.setDepartment(rs.getString("department"));
                employee.setExperience(rs.getInt("experience"));
                employee.setState(rs.getString("state"));
                employee.setCountry(rs.getString("country"));

                employees.add(employee);
            }

            request.setAttribute("employees", employees);

            request.getRequestDispatcher("employees.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2>Unable to load employees</h2>" +
                            "<p>" +
                            e.getMessage() +
                            "</p>"
            );
        }
    }
}