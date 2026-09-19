package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String ageText = request.getParameter("age");
        String department = request.getParameter("department");
        String experienceText = request.getParameter("experience");
        String state = request.getParameter("state");
        String country = request.getParameter("country");

        /*
         * Basic server-side validation
         */

        if (name == null || name.trim().isEmpty()
                || ageText == null || ageText.trim().isEmpty()
                || department == null || department.trim().isEmpty()
                || experienceText == null || experienceText.trim().isEmpty()
                || state == null || state.trim().isEmpty()
                || country == null || country.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "All fields are required."
            );

            return;
        }

        int age;
        float experience;

        try {

            age = Integer.parseInt(ageText);
            experience = Float.parseFloat(experienceText);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Age and experience must contain valid numbers."
            );

            return;
        }

        /*
         * Range validation
         */

        if (age < 18 || age > 65) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Age must be between 18 and 65."
            );

            return;
        }

        if (experience < 0 || experience > 40) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Experience must be between 0 and 40 years."
            );

            return;
        }

        /*
         * SQL INSERT
         */

        String sql =
                "INSERT INTO employee " +
                        "(name, age, department, experience, state, country) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

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

            int result = ps.executeUpdate();

            if (result > 0) {

                /*
                 * Successful insertion
                 */

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println(
                        "<!DOCTYPE html>" +
                                "<html>" +
                                "<head>" +
                                "<meta charset='UTF-8'>" +
                                "<meta http-equiv='refresh' content='2;URL=addEmployee.html'>" +
                                "<title>Employee Added</title>" +

                                "<style>" +

                                "body {" +
                                "margin: 0;" +
                                "font-family: Arial, sans-serif;" +
                                "background: #f4f7fb;" +
                                "display: flex;" +
                                "justify-content: center;" +
                                "align-items: center;" +
                                "height: 100vh;" +
                                "}" +

                                ".success-box {" +
                                "background: white;" +
                                "padding: 45px;" +
                                "border-radius: 18px;" +
                                "box-shadow: 0 10px 35px rgba(0,0,0,0.12);" +
                                "text-align: center;" +
                                "width: 400px;" +
                                "}" +

                                ".success-icon {" +
                                "font-size: 60px;" +
                                "margin-bottom: 15px;" +
                                "}" +

                                "h1 {" +
                                "color: #198754;" +
                                "margin-bottom: 10px;" +
                                "}" +

                                "p {" +
                                "color: #555;" +
                                "font-size: 16px;" +
                                "}" +

                                ".small {" +
                                "font-size: 14px;" +
                                "color: #888;" +
                                "margin-top: 20px;" +
                                "}" +

                                "</style>" +

                                "</head>" +

                                "<body>" +

                                "<div class='success-box'>" +

                                "<div class='success-icon'>✅</div>" +

                                "<h1>Employee Added Successfully!</h1>" +

                                "<p>" +
                                "Employee <b>" +
                                escapeHtml(name) +
                                "</b> has been added to the database." +
                                "</p>" +

                                "<p class='small'>" +
                                "Returning to the employee form..." +
                                "</p>" +

                                "</div>" +

                                "</body>" +

                                "</html>"
                );

            } else {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Employee was not inserted."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2>Database Error</h2>" +
                            "<p>" +
                            escapeHtml(e.getMessage()) +
                            "</p>" +
                            "<br>" +
                            "<a href='addEmployee.html'>Go Back</a>"
            );
        }
    }

    /*
     * Prevent HTML injection in the success message
     */

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}