<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aircraft Types Edit</title>        
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
                            <p class="textHeader">Aircraft Type Edit</p>
                        </div>

                        <form action="AircraftTypeController" method="post">
                            <table class="planeTbl">
                                <tr><td class="content">Name</td><td><input name="name" value="${requestScope.type.name}" required></td></tr>
                                <tr><td class="content">Number of Business seats</td><td><input type="number" min="1" name="numberBusinessSeats" value="${requestScope.type.numberBusinessSeats}" required></td></tr>	 
                                <tr><td class="content">Number of Economy seats</td><td><input type="number" min="1" name="numberEconomySeats" value="${requestScope.type.numberEconomySeats}" required></td></tr>	 
                            </table>
                            <div class="btnContent">
                                <input type="hidden" name="typeID" value="${requestScope.type.typeID}">
                                <button type="submit" name="action" value="${requestScope.nextaction}">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
