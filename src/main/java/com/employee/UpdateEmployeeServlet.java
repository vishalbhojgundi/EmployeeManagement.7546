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

public class UpdateEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idText =
                request.getParameter("id");


        if (
                idText == null ||
                        idText.trim().isEmpty()
        ) {

            response.sendError(
                    400,
                    "Employee ID is required."
            );

            return;
        }


        int id;


        try {

            id =
                    Integer.parseInt(
                            idText
                    );

        } catch (NumberFormatException e) {

            response.sendError(
                    400,
                    "Invalid employee ID."
            );

            return;
        }


        String employeeSql =
                "SELECT id, name, age, department, " +
                        "experience, state, country " +
                        "FROM employee WHERE id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                employeeSql
                        )
        ) {

            ps.setInt(1, id);


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (!rs.next()) {

                    response.sendError(
                            404,
                            "Employee not found."
                    );

                    return;
                }


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


                List<String> departments =
                        new ArrayList<>();


                String departmentSql =
                        "SELECT name FROM department " +
                                "ORDER BY name";


                try (
                        PreparedStatement dps =
                                con.prepareStatement(
                                        departmentSql
                                );

                        ResultSet drs =
                                dps.executeQuery()
                ) {

                    while (drs.next()) {

                        departments.add(
                                drs.getString("name")
                        );
                    }
                }


                request.setAttribute(
                        "employee",
                        employee
                );

                request.setAttribute(
                        "departments",
                        departments
                );


                request.getRequestDispatcher(
                        "/editEmployee.jsp"
                ).forward(
                        request,
                        response
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to load employee."
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        String idText =
                request.getParameter("id");

        String name =
                request.getParameter("name");

        String ageText =
                request.getParameter("age");

        String department =
                request.getParameter("department");

        String experienceText =
                request.getParameter("experience");

        String state =
                request.getParameter("state");

        String country =
                request.getParameter("country");


        int id;
        int age;
        float experience;


        try {

            id =
                    Integer.parseInt(idText);

            age =
                    Integer.parseInt(ageText);

            experience =
                    Float.parseFloat(
                            experienceText
                    );

        } catch (Exception e) {

            response.sendError(
                    400,
                    "Invalid employee information."
            );

            return;
        }


        if (
                age < 18 ||
                        age > 65
        ) {

            response.sendError(
                    400,
                    "Age must be between 18 and 65."
            );

            return;
        }


        if (
                experience < 0 ||
                        experience > 40
        ) {

            response.sendError(
                    400,
                    "Experience must be between 0 and 40."
            );

            return;
        }


        String sql =
                "UPDATE employee SET " +
                        "name = ?, " +
                        "age = ?, " +
                        "department = ?, " +
                        "experience = ?, " +
                        "state = ?, " +
                        "country = ? " +
                        "WHERE id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, name.trim());
            ps.setInt(2, age);
            ps.setString(3, department);
            ps.setFloat(4, experience);
            ps.setString(5, state);
            ps.setString(6, country);
            ps.setInt(7, id);


            ps.executeUpdate();


            response.sendRedirect(
                    "viewEmployees?success=updated"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to update employee."
            );
        }
    }
}