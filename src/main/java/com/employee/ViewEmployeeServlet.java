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

        List<Employee> employees =
                new ArrayList<>();


        String search =
                request.getParameter("search");


        if (search == null) {
            search = "";
        }


        String sql =
                "SELECT id, name, age, department, " +
                        "experience, state, country " +
                        "FROM employee " +
                        "WHERE name LIKE ? " +
                        "OR department LIKE ? " +
                        "OR state LIKE ? " +
                        "OR country LIKE ? " +
                        "ORDER BY id ASC";


        String keyword =
                "%" + search.trim() + "%";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    Employee employee =
                            new Employee();

                    employee.setId(
                            rs.getInt("id")
                    );

                    employee.setName(
                            rs.getString("name")
                    );

                    employee.setAge(
                            rs.getInt("age")
                    );

                    employee.setDepartment(
                            rs.getString("department")
                    );

                    employee.setExperience(
                            rs.getFloat("experience")
                    );

                    employee.setState(
                            rs.getString("state")
                    );

                    employee.setCountry(
                            rs.getString("country")
                    );


                    employees.add(employee);
                }
            }


            request.setAttribute(
                    "employees",
                    employees
            );

            request.setAttribute(
                    "search",
                    search
            );


            request.getRequestDispatcher(
                    "/employees.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to load employees."
            );
        }
    }
}