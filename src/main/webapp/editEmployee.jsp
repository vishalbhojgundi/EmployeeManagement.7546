<%@ page import="com.employee.Employee" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<%
    Employee employee =
            (Employee) request.getAttribute("employee");

    List<String> departments =
            (List<String>)
            request.getAttribute("departments");

    if (employee == null) {
        response.sendRedirect("viewEmployees");
        return;
    }
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Edit Employee</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            ✏️
        </div>

        <div class="brand-text">

            <h2>Employee Management</h2>

            <span>Edit Employee</span>

        </div>

    </div>


    <div class="top-actions">

        <a href="viewEmployees"
           class="header-action">

            👥 Employees

        </a>

        <a href="logout"
           class="header-action">

            🚪 Logout

        </a>

    </div>

</header>


<main class="page-container">


    <div class="page-heading">

        <h1>Edit Employee</h1>

        <p>
            Update employee information.
        </p>

    </div>


    <div class="form-card">


        <div class="form-title">

            <div class="form-title-icon">
                ✏️
            </div>

            <div>

                <h2>Employee Information</h2>

                <p>
                    Update the required information.
                </p>

            </div>

        </div>


        <form
                id="employeeForm"
                action="updateEmployee"
                method="post"
                novalidate>


            <input
                    type="hidden"
                    name="id"
                    value="<%= employee.getId() %>">


            <div class="form-group">

                <label>
                    Employee Name *
                </label>

                <input
                        type="text"
                        id="name"
                        name="name"
                        value="<%= employee.getName() %>"
                        placeholder="Employee name">

                <small
                        id="nameError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label>
                    Age *
                </label>

                <input
                        type="number"
                        id="age"
                        name="age"
                        value="<%= employee.getAge() %>"
                        min="18"
                        max="65">

                <small
                        id="ageError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label>
                    Experience *
                </label>

                <input
                        type="number"
                        id="experience"
                        name="experience"
                        value="<%= employee.getExperience() %>"
                        min="0"
                        max="40"
                        step="0.1">

                <small
                        id="experienceError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label>
                    Department *
                </label>

                <select
                        id="department"
                        name="department">

                    <option value="">
                        Select Department
                    </option>

                    <%

                        if (departments != null) {

                            for (
                                    String department :
                                    departments
                            ) {

                                String selected =
                                        department.equals(
                                                employee.getDepartment()
                                        )
                                                ? "selected"
                                                : "";

                    %>

                    <option
                            value="<%= department %>"
                            <%= selected %>>

                        <%= department %>

                    </option>

                    <%

                            }
                        }

                    %>

                </select>

                <small
                        id="departmentError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label>
                    State *
                </label>

                <select
                        id="state"
                        name="state">

                    <option value="">
                        Select State
                    </option>

                    <%

                        String[] states = {

                            "Andhra Pradesh",
                            "Arunachal Pradesh",
                            "Assam",
                            "Bihar",
                            "Chhattisgarh",
                            "Goa",
                            "Gujarat",
                            "Haryana",
                            "Himachal Pradesh",
                            "Jharkhand",
                            "Karnataka",
                            "Kerala",
                            "Madhya Pradesh",
                            "Maharashtra",
                            "Manipur",
                            "Meghalaya",
                            "Mizoram",
                            "Nagaland",
                            "Odisha",
                            "Punjab",
                            "Rajasthan",
                            "Sikkim",
                            "Tamil Nadu",
                            "Telangana",
                            "Tripura",
                            "Uttar Pradesh",
                            "Uttarakhand",
                            "West Bengal"
                        };


                        for (String state :
                                states) {

                            String selected =
                                    state.equals(
                                            employee.getState()
                                    )
                                            ? "selected"
                                            : "";

                    %>

                    <option
                            value="<%= state %>"
                            <%= selected %>>

                        <%= state %>

                    </option>

                    <%

                        }

                    %>

                </select>

                <small
                        id="stateError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label>
                    Country
                </label>

                <input
                        type="text"
                        name="country"
                        value="<%= employee.getCountry() %>"
                        readonly>

            </div>


            <div class="form-buttons">

                <a
                        href="viewEmployees"
                        class="btn btn-clear">

                    ✖ Cancel

                </a>


                <button
                        type="submit"
                        class="btn btn-primary">

                    💾 Save Changes

                </button>

            </div>


        </form>

    </div>

</main>

</div>


<script src="validation.js"></script>

</body>

</html>