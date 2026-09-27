package com.employee;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        HttpServletResponse res =
                (HttpServletResponse) response;


        HttpSession session =
                req.getSession(false);


        boolean loggedIn =
                session != null &&
                        session.getAttribute("adminUsername") != null;


        String uri =
                req.getRequestURI();

        String contextPath =
                req.getContextPath();

        String path =
                uri.substring(
                        contextPath.length()
                );


        /*
         * Public resources
         */

        boolean publicResource =
                path.equals("/") ||
                        path.equals("/index.html") ||
                        path.equals("/login") ||
                        path.equals("/style.css") ||
                        path.equals("/validation.js");


        if (loggedIn || publicResource) {

            chain.doFilter(
                    request,
                    response
            );

        } else {

            res.sendRedirect(
                    contextPath +
                            "/index.html"
            );
        }
    }
}