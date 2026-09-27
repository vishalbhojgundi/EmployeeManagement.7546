<%
    if (session.getAttribute("adminUsername") == null) {

        response.sendRedirect("index.html");

        return;
    }

    String adminName =
            (String) session.getAttribute("adminName");
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admin Dashboard</title>

    <link rel="stylesheet"
          href="style.css">

</head>

<body>

<div class="dashboard">


<header class="topbar">

    <div class="brand">

        <div class="brand-icon">
            📊
        </div>

        <div class="brand-text">

            <h2>Employee Management</h2>

            <span>Admin Dashboard</span>

        </div>

    </div>


    <div class="top-actions">

        <span class="admin-badge">
            👤 <%= adminName %>
        </span>

        <a
                href="logout"
                class="header-action">

            🚪 Logout

        </a>

    </div>

</header>


<main class="dashboard-container">


<section class="welcome-section">

    <p class="welcome-small">
        Welcome back 👋
    </p>

    <h1>
        Admin Dashboard
    </h1>

    <p class="welcome-description">
        Manage employees, departments and reports
        from one central location.
    </p>

</section>


<section class="summary-grid">


    <div class="summary-card">

        <div class="summary-icon purple">
            👥
        </div>

        <div>

            <p>Total Employees</p>

            <h2 id="totalEmployees">
                Loading...
            </h2>

        </div>

    </div>


    <div class="summary-card">

        <div class="summary-icon blue">
            🏢
        </div>

        <div>

            <p>Departments</p>

            <h2 id="totalDepartments">
                Loading...
            </h2>

        </div>

    </div>


    <div class="summary-card">

        <div class="summary-icon green">
            🌎
        </div>

        <div>

            <p>States</p>

            <h2 id="totalStates">
                Loading...
            </h2>

        </div>

    </div>


    <div class="summary-card">

        <div class="summary-icon orange">
            📊
        </div>

        <div>

            <p>Average Experience</p>

            <h2 id="averageExperience">
                Loading...
            </h2>

        </div>

    </div>


</section>


<section class="section-heading">

    <h2>
        Management
    </h2>

    <p>
        Choose what you want to manage.
    </p>

</section>


<section class="actions-grid">


    <a
            href="addEmployee"
            class="action-card">

        <div class="action-icon add-icon">
            ➕
        </div>

        <div class="action-content">

            <h3>Add Employee</h3>

            <p>
                Add a new employee record.
            </p>

        </div>

        <span class="arrow">
            →
        </span>

    </a>


    <a
            href="viewEmployees"
            class="action-card">

        <div class="action-icon view-icon">
            👥
        </div>

        <div class="action-content">

            <h3>Employees</h3>

            <p>
                Search, edit and delete employees.
            </p>

        </div>

        <span class="arrow">
            →
        </span>

    </a>


    <a
            href="departments"
            class="action-card">

        <div class="action-icon add-icon">
            🏢
        </div>

        <div class="action-content">

            <h3>Departments</h3>

            <p>
                Add, update and manage departments.
            </p>

        </div>

        <span class="arrow">
            →
        </span>

    </a>


    <a
            href="reports"
            class="action-card">

        <div class="action-icon view-icon">
            📊
        </div>

        <div class="action-content">

            <h3>Reports</h3>

            <p>
                View employee statistics and summaries.
            </p>

        </div>

        <span class="arrow">
            →
        </span>

    </a>


</section>


<div class="info-card">

    <div class="info-icon">
        💡
    </div>

    <div>

        <h3>
            Employee Management System
        </h3>

        <p>
            Centralized employee management using
            Java, Servlets, JSP, JDBC and MySQL.
        </p>

    </div>

</div>


</main>


<footer class="footer">

    <p>
        © 2026 Employee Management System
    </p>

    <span>
        Java • JSP • Servlet • MySQL • Tomcat
    </span>

</footer>


<script>

    async function loadDashboardStats() {

        try {

            const response =
                await fetch("dashboardStats");

            const data =
                await response.json();


            document.getElementById(
                    "totalEmployees"
            ).textContent =
                data.totalEmployees;


            document.getElementById(
                    "totalDepartments"
            ).textContent =
                data.totalDepartments;


            document.getElementById(
                    "totalStates"
            ).textContent =
                data.totalStates;


            document.getElementById(
                    "averageExperience"
            ).textContent =
                data.averageExperience +
                " Years";


        } catch (error) {

            console.error(error);

        }

    }


    loadDashboardStats();

</script>


</div>

</body>

</html>