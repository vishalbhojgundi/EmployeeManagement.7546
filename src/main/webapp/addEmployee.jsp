<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Employee</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            👨‍💼
        </div>

        <div class="brand-text">

            <h2>Employee Management</h2>

            <span>Add Employee</span>

        </div>

    </div>


    <div class="top-actions">

        <a href="dashboard"
           class="header-action">

            🏠 Dashboard

        </a>

        <a href="logout"
           class="header-action">

            🚪 Logout

        </a>

    </div>

</header>


<main class="page-container">


    <div class="page-heading">

        <h1>Add Employee</h1>

        <p>
            Add a new employee to the organization.
        </p>

    </div>


    <div class="form-card">


        <div class="form-title">

            <div class="form-title-icon">
                👤
            </div>

            <div>

                <h2>Employee Information</h2>

                <p>
                    All fields marked with * are required.
                </p>

            </div>

        </div>


        <form
                id="employeeForm"
                action="addEmployee"
                method="post"
                novalidate>


            <div class="form-group">

                <label for="name">
                    Employee Name *
                </label>

                <input
                        type="text"
                        id="name"
                        name="name"
                        placeholder="Enter employee name">

                <small
                        id="nameError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label for="age">
                    Age *
                </label>

                <input
                        type="number"
                        id="age"
                        name="age"
                        min="18"
                        max="65"
                        placeholder="Enter age">

                <small
                        id="ageError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label for="experience">
                    Experience (Years) *
                </label>

                <input
                        type="number"
                        id="experience"
                        name="experience"
                        min="0"
                        max="40"
                        step="0.1"
                        placeholder="Example: 2.5">

                <small
                        id="experienceError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label for="department">
                    Department *
                </label>

                <select
                        id="department"
                        name="department">

                    <option value="">
                        Select Department
                    </option>

                    <%

                        List<String> departments =
                                (List<String>)
                                request.getAttribute(
                                        "departments"
                                );

                        if (departments != null) {

                            for (
                                    String department :
                                    departments
                            ) {

                    %>

                    <option value="<%= department %>">
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

                <label for="state">
                    State *
                </label>

                <select
                        id="state"
                        name="state">

                    <option value="">
                        Select State
                    </option>

                    <option>Andhra Pradesh</option>
                    <option>Arunachal Pradesh</option>
                    <option>Assam</option>
                    <option>Bihar</option>
                    <option>Chhattisgarh</option>
                    <option>Goa</option>
                    <option>Gujarat</option>
                    <option>Haryana</option>
                    <option>Himachal Pradesh</option>
                    <option>Jharkhand</option>
                    <option>Karnataka</option>
                    <option>Kerala</option>
                    <option>Madhya Pradesh</option>
                    <option>Maharashtra</option>
                    <option>Manipur</option>
                    <option>Meghalaya</option>
                    <option>Mizoram</option>
                    <option>Nagaland</option>
                    <option>Odisha</option>
                    <option>Punjab</option>
                    <option>Rajasthan</option>
                    <option>Sikkim</option>
                    <option>Tamil Nadu</option>
                    <option>Telangana</option>
                    <option>Tripura</option>
                    <option>Uttar Pradesh</option>
                    <option>Uttarakhand</option>
                    <option>West Bengal</option>

                </select>

                <small
                        id="stateError"
                        class="error">
                </small>

            </div>


            <div class="form-group">

                <label for="country">
                    Country
                </label>

                <input
                        type="text"
                        id="country"
                        name="country"
                        value="India"
                        readonly>

            </div>


            <div class="form-buttons">

                <a
                        href="viewEmployees"
                        class="btn btn-clear">

                    ✖ Cancel

                </a>


                <button
                        type="button"
                        class="btn btn-clear"
                        onclick="clearForm()">

                    🧹 Clear

                </button>


                <button
                        type="submit"
                        class="btn btn-primary">

                    ➕ Add Employee

                </button>

            </div>

        </form>

    </div>

</main>


</div>


<script src="validation.js"></script>

</body>

</html>