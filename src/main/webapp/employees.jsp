<%@ page import="java.util.List" %>
<%@ page import="com.employee.Employee" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Employee List</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


    <!-- TOP BAR -->
<header class="topbar">

    <div class="brand">

        <div class="brand-icon">

            <svg width="24"
                 height="24"
                 viewBox="0 0 24 24"
                 fill="none"
                 stroke="currentColor"
                 stroke-width="2"
                 stroke-linecap="round"
                 stroke-linejoin="round">

                <path d="M20 21a8 8 0 0 0-16 0"></path>

                <circle cx="12"
                        cy="7"
                        r="4"></circle>

            </svg>

        </div>

        <div>

            <h2>Employee Management</h2>

            <span>Employee List</span>

        </div>

    </div>


    <div class="top-actions">

        <a href="index.html"
           class="header-action">

            <svg width="18"
                 height="18"
                 viewBox="0 0 24 24"
                 fill="none"
                 stroke="currentColor"
                 stroke-width="2"
                 stroke-linecap="round"
                 stroke-linejoin="round">

                <path d="M3 11.5L12 4l9 7.5"></path>

                <path d="M5 10v10h14V10"></path>

                <path d="M9 20v-6h6v6"></path>

            </svg>

            <span>Home</span>

        </a>


        <a href="addEmployee.html"
           class="header-action">

            <svg width="18"
                 height="18"
                 viewBox="0 0 24 24"
                 fill="none"
                 stroke="currentColor"
                 stroke-width="2"
                 stroke-linecap="round"
                 stroke-linejoin="round">

                <circle cx="12"
                        cy="12"
                        r="9"></circle>

                <path d="M12 8v8"></path>

                <path d="M8 12h8"></path>

            </svg>

            <span>Add Employee</span>

        </a>

    </div>

</header>

    <!-- MAIN -->

    <main class="page-container">


        <!-- HEADING -->

        <div class="page-heading">

            <h1>Employee List</h1>

            <p>
                View and manage all employee records.
            </p>

        </div>



        <!-- EMPLOYEE TABLE -->

        <div class="table-container">

            <%
                List<Employee> employees =
                        (List<Employee>) request.getAttribute("employees");
            %>


            <%
                if (employees == null || employees.isEmpty()) {
            %>

                <div class="empty">

                    <div style="font-size: 45px;">
                        👥
                    </div>

                    <h2>No Employees Found</h2>

                    <p>
                        There are currently no employee records.
                    </p>

                    <br>

                    <a href="addEmployee.html"
                       class="btn btn-primary">

                        ➕ Add Employee

                    </a>

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


                    </tr>

                    </thead>


                    <tbody>

                    <%
                        for (Employee employee : employees) {
                    %>

                    <tr>

                        <td>
                            <%= employee.getId() %>
                        </td>

                        <td>
                            <%= employee.getName() %>
                        </td>

                        <td>
                            <%= employee.getAge() %>
                        </td>

                        <td>
                            <%= employee.getDepartment() %>
                        </td>

                        <td>
                            <%= employee.getExperience() %> Years
                        </td>

                        <td>
                            <%= employee.getState() %>
                        </td>

                        <td>
                            <%= employee.getCountry() %>
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