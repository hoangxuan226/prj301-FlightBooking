<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Details</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/aircraftDetails.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <div class="mainContent">
                <div class="tableContent">
                    <div class="planeList" id="planeListContent">
                        <div class="listHeader">
                            <p class="textHeader">Member Detail</p>
                        </div>
                        <table class="planeTbl">
                            <tr><td class="content">Member Number</td><td>${requestScope.member.memberNumber}</td></tr>          
                            <tr><td class="content">First Name</td><td>${requestScope.member.firstname}</td></tr>          
                            <tr><td class="content">Last Name</td><td>${requestScope.member.lastname}</td></tr>          
                            <tr><td class="content">Birthday</td><td>${requestScope.member.birthday}</td></tr>          
                            <tr><td class="content">Gender</td><td>${requestScope.member.gender}</td></tr>          
                            <tr><td class="content">Email</td><td>${requestScope.member.email}</td></tr>          
                            <tr><td class="content">Phone Number</td><td>${requestScope.member.phoneno}</td></tr>          
                            <tr><td class="content">Nationality</td><td>${requestScope.member.nationality}</td></tr>          
                            <tr><td class="content">Number ID</td><td>${requestScope.member.numberID}</td></tr>       
                        </table>

                        <c:if test="${sessionScope.role eq 'member'}">
                            <div class="btnContent">
                                <form action="MemberController">
                                    <button type="submit" name="action" value="Edit">Edit</button>                               
                                    <button type="submit" name="action" value="Delete" onclick="return confirm('Are you sure you want to delete your account?')">Delete this account</button>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
