<%@ page import="java.util.List" %>
<%@ page import="com.employee.Employee" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Employees</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            👥
        </div>

        <div class="brand-text">

            <h2>Employee Management</h2>

            <span>Employee Records</span>

        </div>

    </div>


    <div class="top-actions">

        <a href="dashboard"
           class="header-action">

            📊 Dashboard

        </a>

        <a href="addEmployee"
           class="header-action">

            ➕ Add Employee

        </a>

        <a href="logout"
           class="header-action">

            🚪 Logout

        </a>

    </div>

</header>


<main class="page-container">


    <div class="page-heading">

        <h1>Employee Management</h1>

        <p>
            Search and manage employee records.
        </p>

    </div>


    <!-- SEARCH -->

    <div class="search-card">

        <form
                action="viewEmployees"
                method="get"
                class="search-form">

            <input
                    type="text"
                    name="search"
                    value="<%= request.getAttribute("search") == null ? "" : request.getAttribute("search") %>"
                    placeholder="Search by name, department or state...">

            <button
                    type="submit"
                    class="btn btn-primary">

                🔍 Search

            </button>


            <a
                    href="viewEmployees"
                    class="btn btn-clear">

                Reset

            </a>

        </form>

    </div>


    <div class="table-container">


        <%

            List<Employee> employees =
                    (List<Employee>)
                    request.getAttribute(
                            "employees"
                    );

        %>


        <%

            if (
                    employees == null ||
                    employees.isEmpty()
            ) {

        %>


        <div class="empty">

            <div style="font-size:45px;">
                👥
            </div>

            <h2>
                No Employees Found
            </h2>

            <p>
                No employee records match your search.
            </p>

        </div>


        <%

            } else {

        %>


        <table>

            <thead>

            <tr>

                <th>ID</th>

                <th>Name</th>

                <th>Age</th>

                <th>Department</th>

                <th>Experience</th>

                <th>State</th>

                <th>Country</th>

                <th>Actions</th>

            </tr>

            </thead>


            <tbody>


            <%

                for (
                        Employee employee :
                        employees
                ) {

            %>


            <tr>

                <td>
                    <%= employee.getId() %>
                </td>

                <td>
                    <strong>
                        <%= employee.getName() %>
                    </strong>
                </td>

                <td>
                    <%= employee.getAge() %>
                </td>

                <td>
                    <%= employee.getDepartment() %>
                </td>

                <td>
                    <%= employee.getExperience() %>
                    Years
                </td>

                <td>
                    <%= employee.getState() %>
                </td>

                <td>
                    <%= employee.getCountry() %>
                </td>


                <td>

                    <div class="actions-cell">

                        <a
                                href="updateEmployee?id=<%= employee.getId() %>"
                                class="action-btn action-edit">

                            ✏️ Edit

                        </a>


                        <a
                                href="deleteEmployee?id=<%= employee.getId() %>"
                                class="action-btn action-delete"
                                onclick="return confirm('Are you sure you want to delete this employee?');">

                            🗑️ Delete

                        </a>

                    </div>

                </td>

            </tr>


            <%

                }

            %>


            </tbody>

        </table>


        <%

            }

        %>


    </div>

</main>

</div>

</body>

</html>