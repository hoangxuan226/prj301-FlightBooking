<%-- 
    Document   : successfulBooking
    Created on : Mar 1, 2024, 10:12:23 PM
    Author     : phamx
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notification</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/notification.css" />
    </head>
    <body>
        <div class="container">
            <!-- upper -->
            <%@include file="header.jsp" %>
            <!-- intro -->
            <div class="mainContent">
                <div class="introduction">
                    <div class="textIntro">
                        <c:forEach var="msg" items="${requestScope.mainMsg}">
                            <p>${msg}</p> 
                        </c:forEach>       
                    </div>
                </div>
            </div>

            <div class="container-2">                
                <c:forEach var="msg" items="${requestScope.extraMsg}">
                    <h4 class="row">${msg}</h4>
                </c:forEach>                
                <img src="./images/logoLike.png" alt="">
            </div>
        </div>
    </body>
</html>
