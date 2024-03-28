<%-- 
    Document   : login
    Created on : Mar 1, 2024, 9:37:49 PM
    Author     : phamx
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/login.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <div class="mainContent">
                <div class="imgLogin">
                    <img src="./images/imgLogin.jpg" alt="" />
                </div>
                <div class="loginForm">
                    <div class="logoForm">
                        <img src="./images/logo.png" alt="">
                    </div>
                    <h1>Login to HVH Airways</h1><br>
                    <br>
                    <form action="LoginController" method="post">
                        <div class="inpInfo">
                            <h2>Email / Membership Number</h2>
                            <input type="text" placeholder="Your email / Your Member Number" name="userIdentity" required><br>
                            <br>
                            <h2>Password</h2>
                            <input type="password" placeholder="Your password" name="password" required></div>
                        <br>
                        <div class="errorMsg">${requestScope.error}</div>
                        <br>
                        <div class="btn-login">
                            <input type="submit" name="action" value="Login" >
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
