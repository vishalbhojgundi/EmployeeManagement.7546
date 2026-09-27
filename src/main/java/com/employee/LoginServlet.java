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


        if (username == null ||
                password == null ||
                username.trim().isEmpty() ||
                password.trim().isEmpty()) {

            response.sendRedirect("index.html?error=empty");

            return;
        }


        String passwordHash =
                PasswordUtil.hashPassword(password);


        String sql =
                "SELECT id, username, full_name " +
                        "FROM admin_user " +
                        "WHERE username = ? AND password_hash = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username.trim());
            ps.setString(2, passwordHash);


            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

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