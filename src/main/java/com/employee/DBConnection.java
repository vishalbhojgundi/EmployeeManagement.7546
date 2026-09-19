package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = getEnvOrDefault(
            "DB_URL",
            "jdbc:mysql://shuttle.proxy.rlwy.net:44790/railway" +
                    "?useSSL=true&requireSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );

    private static final String USER =
            getEnvOrDefault("DB_USER", "root");

    private static final String PASSWORD =
            getEnvOrDefault("DB_PASSWORD", "IggWNkpQwWVHQpYazUPpOzTgODnBVVxT");

    private static String getEnvOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        return (value != null && !value.trim().isEmpty()) ? value : fallback;
    }

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}