<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aircraft Details</title>        
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
                            <p class="textHeader">Aircraft Detail</p>
                        </div>
                        <table class="planeTbl">
                            <tr><td class="content">ID</td><td>${requestScope.aircraft.aircraftID}</td></tr>
                            <tr><td class="content">Registration Number</td><td>${requestScope.aircraft.registration}</td></tr>
                            <tr><td class="content">Aircraft Type</td><td>${requestScope.aircraft.typeName}</td></tr>            
                        </table>         
                    </div>
                    <form class="btnContent" action="AircraftController">
                        <input type=hidden name="aircraftID" value="${requestScope.aircraft.aircraftID}">
                        <button type=submit name="action" value="Edit">Edit</button>
                        <button>Back</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
