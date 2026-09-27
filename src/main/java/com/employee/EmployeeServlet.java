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

public class EmployeeServlet extends HttpServlet {

    /*
     * GET
     * Shows Add Employee page
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<String> departments =
                new ArrayList<>();


        String sql =
                "SELECT name FROM department " +
                        "ORDER BY name";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                departments.add(
                        rs.getString("name")
                );
            }


            request.setAttribute(
                    "departments",
                    departments
            );


            request.getRequestDispatcher(
                    "/addEmployee.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to load employee form."
            );
        }
    }


    /*
     * POST
     * Adds employee
     */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


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


        if (
                name == null ||
                        name.trim().isEmpty() ||

                        ageText == null ||
                        ageText.trim().isEmpty() ||

                        department == null ||
                        department.trim().isEmpty() ||

                        experienceText == null ||
                        experienceText.trim().isEmpty() ||

                        state == null ||
                        state.trim().isEmpty()
        ) {

            response.sendError(
                    400,
                    "All required fields are required."
            );

            return;
        }


        int age;
        float experience;


        try {

            age =
                    Integer.parseInt(
                            ageText
                    );

            experience =
                    Float.parseFloat(
                            experienceText
                    );

        } catch (NumberFormatException e) {

            response.sendError(
                    400,
                    "Age and experience must contain valid numbers."
            );

            return;
        }


        if (age < 18 || age > 65) {

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


        if (country == null ||
                country.trim().isEmpty()) {

            country = "India";
        }


        String sql =
                "INSERT INTO employee " +
                        "(name, age, department, experience, state, country) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    name.trim()
            );

            ps.setInt(
                    2,
                    age
            );

            ps.setString(
                    3,
                    department
            );

            ps.setFloat(
                    4,
                    experience
            );

            ps.setString(
                    5,
                    state
            );

            ps.setString(
                    6,
                    country
            );


            ps.executeUpdate();


            /*
             * PRG:
             * Post -> Redirect -> Get
             */

            response.sendRedirect(
                    "viewEmployees?success=added"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to add employee: " +
                            e.getMessage()
            );
        }
    }
}