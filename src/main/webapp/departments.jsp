<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    List<Map<String, Object>> departments =
            (List<Map<String, Object>>)
            request.getAttribute("departments");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Departments</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            🏢
        </div>

        <div class="brand-text">

            <h2>Employee Management</h2>

            <span>Departments</span>

        </div>

    </div>


    <div class="top-actions">

        <a
                href="dashboard"
                class="header-action">

            📊 Dashboard

        </a>

        <a
                href="logout"
                class="header-action">

            🚪 Logout

        </a>

    </div>

</header>


<main class="page-container">


<div class="page-heading">

    <h1>
        Department Management
    </h1>

    <p>
        Create and manage organization departments.
    </p>

</div>


<div class="form-card department-form">

    <div class="form-title">

        <div class="form-title-icon">
            🏢
        </div>

        <div>

            <h2>
                Add Department
            </h2>

            <p>
                Create a new department.
            </p>

        </div>

    </div>


    <form
            action="departments"
            method="post">

        <input
                type="hidden"
                name="action"
                value="add">


        <div class="form-group">

            <label>
                Department Name *
            </label>

            <input
                    type="text"
                    name="name"
                    placeholder="Example: Operations"
                    required>

        </div>


        <div class="form-group">

            <label>
                Description
            </label>

            <input
                    type="text"
                    name="description"
                    placeholder="Department description">

        </div>


        <div class="form-buttons">

            <button
                    type="submit"
                    class="btn btn-primary">

                ➕ Add Department

            </button>

        </div>

    </form>

</div>


<br>


<div class="table-container">

    <table>

        <thead>

        <tr>

            <th>ID</th>

            <th>Department</th>

            <th>Description</th>

            <th>Employees</th>

            <th>Actions</th>

        </tr>

        </thead>


        <tbody>


        <%

            if (
                    departments != null &&
                    !departments.isEmpty()
            ) {

                for (
                        Map<String,Object> dept :
                        departments
                ) {

        %>


        <tr>

            <td>
                <%= dept.get("id") %>
            </td>

            <td>
                <strong>
                    <%= dept.get("name") %>
                </strong>
            </td>

            <td>
                <%= dept.get("description") == null
                        ? ""
                        : dept.get("description") %>
            </td>

            <td>
                <%= dept.get("employeeCount") %>
            </td>

            <td>

                <a
                        class="action-btn action-delete"
                        href="departments?action=delete&id=<%= dept.get("id") %>"
                        onclick="return confirm('Delete this department?');">

                    🗑️ Delete

                </a>

            </td>

        </tr>


        <%

                }

            }

        %>


        </tbody>

    </table>

</div>


</main>

</div>

</body>

</html>