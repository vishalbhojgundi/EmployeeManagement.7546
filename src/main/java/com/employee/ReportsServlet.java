package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int totalEmployees = 0;
        double averageAge = 0;
        double averageExperience = 0;

        Map<String, Integer>
                departmentCounts =
                new LinkedHashMap<>();

        Map<String, Integer>
                stateCounts =
                new LinkedHashMap<>();


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            String summarySql =
                    "SELECT COUNT(*) AS total, " +
                            "COALESCE(AVG(age),0) AS avg_age, " +
                            "COALESCE(AVG(experience),0) AS avg_exp " +
                            "FROM employee";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    summarySql
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    totalEmployees =
                            rs.getInt("total");

                    averageAge =
                            rs.getDouble("avg_age");

                    averageExperience =
                            rs.getDouble("avg_exp");
                }
            }


            String departmentSql =
                    "SELECT department, COUNT(*) AS total " +
                            "FROM employee " +
                            "GROUP BY department " +
                            "ORDER BY total DESC";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    departmentSql
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    departmentCounts.put(
                            rs.getString("department"),
                            rs.getInt("total")
                    );
                }
            }


            String stateSql =
                    "SELECT state, COUNT(*) AS total " +
                            "FROM employee " +
                            "GROUP BY state " +
                            "ORDER BY total DESC";


            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    stateSql
                            );

                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    stateCounts.put(
                            rs.getString("state"),
                            rs.getInt("total")
                    );
                }
            }


            request.setAttribute(
                    "totalEmployees",
                    totalEmployees
            );

            request.setAttribute(
                    "averageAge",
                    averageAge
            );

            request.setAttribute(
                    "averageExperience",
                    averageExperience
            );

            request.setAttribute(
                    "departmentCounts",
                    departmentCounts
            );

            request.setAttribute(
                    "stateCounts",
                    stateCounts
            );


            request.getRequestDispatcher(
                    "/reports.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to generate report."
            );
        }
    }
}