package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/dashboardStats")
public class DashboardStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int totalEmployees = 0;
        int totalDepartments = 0;
        int totalStates = 0;
        double averageExperience = 0;

        String sql = """
                SELECT
                    COUNT(*) AS total_employees,
                    COUNT(DISTINCT department) AS total_departments,
                    COUNT(DISTINCT state) AS total_states,
                    COALESCE(AVG(experience), 0) AS average_experience
                FROM employee
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            if (resultSet.next()) {

                totalEmployees =
                        resultSet.getInt("total_employees");

                totalDepartments =
                        resultSet.getInt("total_departments");

                totalStates =
                        resultSet.getInt("total_states");

                averageExperience =
                        resultSet.getDouble("average_experience");
            }

            PrintWriter out = response.getWriter();

            out.print("{");

            out.print("\"totalEmployees\":"
                    + totalEmployees + ",");

            out.print("\"totalDepartments\":"
                    + totalDepartments + ",");

            out.print("\"totalStates\":"
                    + totalStates + ",");

            out.print("\"averageExperience\":"
                    + String.format("%.1f", averageExperience));

            out.print("}");

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            PrintWriter out = response.getWriter();

            out.print("{");
            out.print("\"error\":\"Database error\"");
            out.print("}");

            e.printStackTrace();
        }
    }
}