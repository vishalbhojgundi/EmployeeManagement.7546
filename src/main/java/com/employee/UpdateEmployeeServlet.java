package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateEmployeeServlet extends HttpServlet {

    /*
     * GET -> loads one employee's current data and shows the edit form
     * (called when the user clicks "Edit" in the employee list)
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee id is required.");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee id.");
            return;
        }

        String sql =
                "SELECT id, name, age, department, experience, state, country " +
                        "FROM employee WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found.");
                    return;
                }

                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setAge(rs.getInt("age"));
                employee.setDepartment(rs.getString("department"));
                employee.setExperience(rs.getInt("experience"));
                employee.setState(rs.getString("state"));
                employee.setCountry(rs.getString("country"));

                request.setAttribute("employee", employee);

                request.getRequestDispatcher("editEmployee.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load employee: " + e.getMessage()
            );
        }
    }

    /*
     * POST -> saves the changes submitted from the edit form
     */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idText = request.getParameter("id");
        String name = request.getParameter("name");
        String ageText = request.getParameter("age");
        String department = request.getParameter("department");
        String experienceText = request.getParameter("experience");
        String state = request.getParameter("state");
        String country = request.getParameter("country");

        if (idText == null || idText.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || ageText == null || ageText.trim().isEmpty()
                || department == null || department.trim().isEmpty()
                || experienceText == null || experienceText.trim().isEmpty()
                || state == null || state.trim().isEmpty()
                || country == null || country.trim().isEmpty()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        int id;
        int age;
        float experience;

        try {

            id = Integer.parseInt(idText);
            age = Integer.parseInt(ageText);
            experience = Float.parseFloat(experienceText);

        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Age and experience must contain valid numbers.");
            return;
        }

        if (age < 18 || age > 65) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Age must be between 18 and 65.");
            return;
        }

        if (experience < 0 || experience > 40) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Experience must be between 0 and 40 years.");
            return;
        }

        String sql =
                "UPDATE employee SET " +
                        "name = ?, age = ?, department = ?, experience = ?, state = ?, country = ? " +
                        "WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, name.trim());
            ps.setInt(2, age);
            ps.setString(3, department);
            ps.setFloat(4, experience);
            ps.setString(5, state);
            ps.setString(6, country);
            ps.setInt(7, id);

            ps.executeUpdate();

            /*
             * Redirect (not forward) back to the list so a page refresh
             * does not re-submit the update.
             */

            response.sendRedirect("viewEmployees");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to update employee: " + e.getMessage()
            );
        }
    }
}
