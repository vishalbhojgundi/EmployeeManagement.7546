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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");


        if ("delete".equals(action)) {

            deleteDepartment(
                    request,
                    response
            );

            return;
        }


        loadDepartments(
                request,
                response
        );
    }


    private void loadDepartments(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> departments =
                new ArrayList<>();


        String sql =
                "SELECT d.id, d.name, d.description, " +
                        "(SELECT COUNT(*) FROM employee e " +
                        "WHERE e.department = d.name) AS employee_count " +
                        "FROM department d " +
                        "ORDER BY d.name";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Map<String, Object> row =
                        new HashMap<>();

                row.put(
                        "id",
                        rs.getInt("id")
                );

                row.put(
                        "name",
                        rs.getString("name")
                );

                row.put(
                        "description",
                        rs.getString("description")
                );

                row.put(
                        "employeeCount",
                        rs.getInt("employee_count")
                );

                departments.add(row);
            }


            request.setAttribute(
                    "departments",
                    departments
            );


            request.getRequestDispatcher(
                    "/departments.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    500,
                    "Unable to load departments."
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action =
                request.getParameter("action");


        String name =
                request.getParameter("name");

        String description =
                request.getParameter("description");


        if (name == null ||
                name.trim().isEmpty()) {

            response.sendRedirect(
                    "departments?error=required"
            );

            return;
        }


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if ("add".equals(action)) {

                String sql =
                        "INSERT INTO department " +
                                "(name, description) " +
                                "VALUES (?, ?)";

                try (PreparedStatement ps =
                             con.prepareStatement(sql)) {

                    ps.setString(
                            1,
                            name.trim()
                    );

                    ps.setString(
                            2,
                            description
                    );

                    ps.executeUpdate();
                }


                response.sendRedirect(
                        "departments?success=added"
                );


            } else if ("update".equals(action)) {

                String idText =
                        request.getParameter("id");

                int id =
                        Integer.parseInt(idText);


                String sql =
                        "UPDATE department " +
                                "SET name = ?, description = ? " +
                                "WHERE id = ?";


                try (PreparedStatement ps =
                             con.prepareStatement(sql)) {

                    ps.setString(
                            1,
                            name.trim()
                    );

                    ps.setString(
                            2,
                            description
                    );

                    ps.setInt(
                            3,
                            id
                    );

                    ps.executeUpdate();
                }


                response.sendRedirect(
                        "departments?success=updated"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "departments?error=duplicate"
            );
        }
    }


    private void deleteDepartment(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idText =
                request.getParameter("id");


        try {

            int id =
                    Integer.parseInt(idText);


            String findSql =
                    "SELECT name FROM department " +
                            "WHERE id = ?";


            String departmentName = null;


            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(findSql)
            ) {

                ps.setInt(1, id);

                try (ResultSet rs =
                             ps.executeQuery()) {

                    if (rs.next()) {

                        departmentName =
                                rs.getString("name");
                    }
                }
            }


            if (departmentName == null) {

                response.sendRedirect(
                        "departments?error=notfound"
                );

                return;
            }


            String countSql =
                    "SELECT COUNT(*) FROM employee " +
                            "WHERE department = ?";


            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(countSql)
            ) {

                ps.setString(
                        1,
                        departmentName
                );

                try (ResultSet rs =
                             ps.executeQuery()) {

                    rs.next();

                    int count =
                            rs.getInt(1);


                    if (count > 0) {

                        response.sendRedirect(
                                "departments?error=used"
                        );

                        return;
                    }
                }
            }


            String deleteSql =
                    "DELETE FROM department WHERE id = ?";


            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(deleteSql)
            ) {

                ps.setInt(1, id);

                ps.executeUpdate();
            }


            response.sendRedirect(
                    "departments?success=deleted"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "departments?error=delete"
            );
        }
    }
}