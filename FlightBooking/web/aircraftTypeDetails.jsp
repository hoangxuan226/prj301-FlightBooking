<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aircraft Types Details</title>
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
                            <p class="textHeader">Aircraft Type Detail</p>
                        </div>
                        <table class="planeTbl">
                            <tr><td class="content">Type ID</td><td>${requestScope.type.typeID}</td></tr>
                            <tr><td class="content">Name</td><td>${requestScope.type.name}</td></tr>
                            <tr><td class="content">Number of Business seats</td><td>${requestScope.type.numberBusinessSeats}</td></tr>	 
                            <tr><td class="content">Number of Economy seats</td><td>${requestScope.type.numberEconomySeats}</td></tr>
                        </table>         

                        <form class="btnContent" action="AircraftTypeController">
                            <input type=hidden name="typeID" value="${requestScope.type.typeID}">
                            <button type=submit name="action" value="Edit">Edit</button>
                            <button type=submit>Back</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
