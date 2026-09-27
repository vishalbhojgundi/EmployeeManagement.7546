package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardStatsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );


        int totalEmployees = 0;
        int totalDepartments = 0;
        int totalStates = 0;
        double averageExperience = 0;


        String sql =
                "SELECT " +
                        "(SELECT COUNT(*) FROM employee) AS employees, " +
                        "(SELECT COUNT(*) FROM department) AS departments, " +
                        "(SELECT COUNT(DISTINCT state) FROM employee) AS states, " +
                        "(SELECT COALESCE(AVG(experience),0) FROM employee) AS avg_exp";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                totalEmployees =
                        rs.getInt("employees");

                totalDepartments =
                        rs.getInt("departments");

                totalStates =
                        rs.getInt("states");

                averageExperience =
                        rs.getDouble("avg_exp");
            }


            PrintWriter out =
                    response.getWriter();


            out.print("{");

            out.print(
                    "\"totalEmployees\":" +
                            totalEmployees +
                            ","
            );

            out.print(
                    "\"totalDepartments\":" +
                            totalDepartments +
                            ","
            );

            out.print(
                    "\"totalStates\":" +
                            totalStates +
                            ","
            );

            out.print(
                    "\"averageExperience\":" +
                            String.format(
                                    "%.1f",
                                    averageExperience
                            )
            );

            out.print("}");


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(500);

            response.getWriter().print(
                    "{\"error\":\"Database error\"}"
            );
        }
    }
}