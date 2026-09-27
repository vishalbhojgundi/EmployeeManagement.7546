package com.employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");


        // Validate empty fields
        if (username == null ||
                password == null ||
                username.trim().isEmpty() ||
                password.trim().isEmpty()) {

            response.sendRedirect(
                    "index.html?error=empty"
            );

            return;
        }


        /*
         * Direct password comparison.
         *
         * Database:
         * username = Vishal
         * password = Change@4559
         */

        String sql =
                "SELECT id, username, full_name " +
                        "FROM admin_user " +
                        "WHERE username = ? AND password = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    username.trim()
            );

            ps.setString(
                    2,
                    password
            );


            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    /*
                     * Login successful
                     */

                    HttpSession session =
                            request.getSession(true);


                    session.setAttribute(
                            "adminId",
                            rs.getInt("id")
                    );


                    session.setAttribute(
                            "adminUsername",
                            rs.getString("username")
                    );


                    session.setAttribute(
                            "adminName",
                            rs.getString("full_name")
                    );


                    response.sendRedirect(
                            "dashboard"
                    );

                } else {

                    /*
                     * Login failed
                     */

                    response.sendRedirect(
                            "index.html?error=invalid"
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "index.html?error=database"
            );
        }
    }
}