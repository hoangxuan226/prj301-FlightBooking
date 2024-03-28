<%-- 
    Document   : header
    Created on : Mar 2, 2024, 8:38:54 AM
    Author     : phamx
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="user" value="${sessionScope.loginUser}" scope="page"/>
<div class="header">
    <div class="logoContent">                
        <div class="logo">
            <a href="./"><img src="./images/logo.png" alt=""/></a>
        </div>
        <div class="textLogo">
            <a href="./"><img src="./images/textLogo.png" alt=""/></a>
        </div>
    </div>
    <div class="userAction">
        <c:if test="${empty user}">
            <div class="login">
                <a href="login.jsp">Login</a>
            </div>
            <div class="register">
                <a href="register.jsp">Register</a>
            </div>
            <div class="icon">
                <img src="./images/userIcon.png" alt="" />
            </div>
        </c:if>
        <c:if test="${not empty user}">
            <div class="welcome">
                <span style="font-weight: bold">Welcome,</span>
                <span class="user-btn">
                    <c:if test="${sessionScope.role eq 'admin'}">
                        <a href="AircraftController">${user.name}</a>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'member'}">
                        <a href="MemberController">${user.lastname} ${user.firstname}</a>
                    </c:if>
                </span>
            </div>
            <div class="logout">
                <a href="LoginController?action=Logout">Logout</a>
            </div>
        </c:if>
    </div>
</div>
