<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map" %>

<%
    int totalEmployees =
            (Integer)
            request.getAttribute(
                    "totalEmployees"
            );

    double averageAge =
            (Double)
            request.getAttribute(
                    "averageAge"
            );

    double averageExperience =
            (Double)
            request.getAttribute(
                    "averageExperience"
            );


    Map<String,Integer>
            departmentCounts =
            (Map<String,Integer>)
            request.getAttribute(
                    "departmentCounts"
            );


    Map<String,Integer>
            stateCounts =
            (Map<String,Integer>)
            request.getAttribute(
                    "stateCounts"
            );
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Reports</title>

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

            <span>Reports & Statistics</span>

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


<main class="dashboard-container">


<section class="welcome-section">

    <p class="welcome-small">
        Analytics
    </p>

    <h1>
        Employee Reports
    </h1>

    <p class="welcome-description">
        Summary of employee information.
    </p>

</section>


<section class="summary-grid">


    <div class="summary-card">

        <div class="summary-icon purple">
            👥
        </div>

        <div>

            <p>Total Employees</p>

            <h2>
                <%= totalEmployees %>
            </h2>

        </div>

    </div>


    <div class="summary-card">

        <div class="summary-icon blue">
            🎂
        </div>

        <div>

            <p>Average Age</p>

            <h2>
                <%= String.format(
                        "%.1f",
                        averageAge
                ) %>
            </h2>

        </div>

    </div>


    <div class="summary-card">

        <div class="summary-icon orange">
            💼
        </div>

        <div>

            <p>Average Experience</p>

            <h2>
                <%= String.format(
                        "%.1f",
                        averageExperience
                ) %>
                Years
            </h2>

        </div>

    </div>


</section>


<section class="section-heading">

    <h2>
        Employees by Department
    </h2>

</section>


<div class="report-list">


<%

    for (
            Map.Entry<String,Integer> entry :
            departmentCounts.entrySet()
    ) {

%>

<div class="report-row">

    <div>

        <strong>
            <%= entry.getKey() %>
        </strong>

    </div>

    <div class="report-count">
        <%= entry.getValue() %>
    </div>

</div>

<%

    }

%>

</div>


<br>


<section class="section-heading">

    <h2>
        Employees by State
    </h2>

</section>


<div class="report-list">


<%

    for (
            Map.Entry<String,Integer> entry :
            stateCounts.entrySet()
    ) {

%>

<div class="report-row">

    <div>

        <strong>
            <%= entry.getKey() %>
        </strong>

    </div>

    <div class="report-count">
        <%= entry.getValue() %>
    </div>

</div>

<%

    }

%>


</div>


</main>

</div>

</body>

</html>