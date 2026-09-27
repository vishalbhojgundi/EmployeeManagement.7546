<%@ page import="com.employee.Employee" %>
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


<%
    Employee employee = (Employee) request.getAttribute("employee");

    if (employee == null) {
        response.sendRedirect("viewEmployees");
        return;
    }

    String[] departments = {"IT", "HR", "Finance", "Marketing"};

    String[] states = {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa",
            "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala",
            "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
            "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
            "Uttar Pradesh", "Uttarakhand", "West Bengal"
    };
%>


<!-- TOP BAR -->

<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            👨‍💼
        </div>

        <div>

            <h2>Employee Management</h2>

            <span>
                Edit Employee
            </span>

        </div>

    </div>


    <div class="top-actions">

        <a href="viewEmployees"
           class="home-link">

            👥 Employee List

        </a>

        <a href="index.html"
           class="home-link">

            🏠 Home

        </a>

    </div>

</header>



<!-- MAIN -->

<main class="page-container">


    <div class="page-heading">

        <div>

            <h1>Edit Employee</h1>

            <p>
                Update the details below and save your changes.
            </p>

        </div>

    </div>



    <!-- FORM CARD -->

    <div class="form-card">


        <div class="form-title">

            <div class="form-title-icon">
                ✏️
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
                action="updateEmployee"
                method="post"
                novalidate>


            <input type="hidden" name="id" value="<%= employee.getId() %>">


            <!-- NAME -->

            <div class="form-group">

                <label for="name">
                    Employee Name *
                </label>

                <input
                        type="text"
                        id="name"
                        name="name"
                        value="<%= employee.getName() %>"
                        placeholder="Enter employee name"
                        autocomplete="off">

                <small
                        id="nameError"
                        class="error">
                </small>

            </div>



            <!-- AGE -->

            <div class="form-group">

                <label for="age">
                    Age *
                </label>

                <input
                        type="number"
                        id="age"
                        name="age"
                        value="<%= employee.getAge() %>"
                        placeholder="Enter age"
                        min="18"
                        max="65">

                <small
                        id="ageError"
                        class="error">
                </small>

            </div>



            <!-- EXPERIENCE -->

            <div class="form-group">

                <label for="experience">
                    Experience (Years) *
                </label>

                <input
                        type="number"
                        id="experience"
                        name="experience"
                        value="<%= employee.getExperience() %>"
                        placeholder="Example: 2.5"
                        min="0"
                        max="40"
                        step="0.1">

                <small
                        id="experienceError"
                        class="error">
                </small>

            </div>



            <!-- DEPARTMENT -->

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
                        for (String dept : departments) {
                            String selected = dept.equals(employee.getDepartment()) ? "selected" : "";
                    %>

                    <option value="<%= dept %>" <%= selected %>>
                        <%= dept %>
                    </option>

                    <%
                        }
                    %>

                </select>

                <small
                        id="departmentError"
                        class="error">
                </small>

            </div>



            <!-- STATE -->

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

                    <%
                        for (String st : states) {
                            String selected = st.equals(employee.getState()) ? "selected" : "";
                    %>

                    <option <%= selected %>><%= st %></option>

                    <%
                        }
                    %>

                </select>

                <small
                        id="stateError"
                        class="error">
                </small>

            </div>



            <!-- COUNTRY -->

            <div class="form-group">

                <label for="country">
                    Country
                </label>

                <input
                        type="text"
                        id="country"
                        name="country"
                        value="<%= employee.getCountry() %>"
                        readonly>

            </div>



            <!-- BUTTONS -->

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


<script src="validation.js"></script>

</body>

</html>
