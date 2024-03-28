<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flight Edit</title>
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
                            <p class="textHeader">Flight Edit</p>
                        </div>
                        <form action="FlightController">
                            <table class="planeTbl">
                                <tr><td class="content">Flight Number</td><td><input name="flightNumber" value="${requestScope.flight.flightNumber}" required></td></tr>
                                <tr>
                                    <td class="content">Aircraft</td>
                                    <td>
                                        <select name="aircraftID" required>
                                            <option value="${requestScope.flight.aircraftID}" hidden selected>${requestScope.flight.aircraftReg}</option>
                                            <c:forEach var="aircraft" items="${requestScope.aircraftList}">
                                                <option value="${aircraft.aircraftID}">${aircraft.registration}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="content">From</td>
                                    <td>
                                        <select name="from" required>
                                            <option value="${requestScope.flight.from}" hidden selected>${requestScope.flight.from}</option>
                                            <c:forEach var="airport" items="${requestScope.airportList}">
                                                <option value="${airport.airportCode}">${airport.airportCode}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="content">To</td>
                                    <td>
                                        <select name="to" required>
                                            <option value="${requestScope.flight.to}" hidden selected>${requestScope.flight.to}</option>
                                            <c:forEach var="airport" items="${requestScope.airportList}">
                                                <option value="${airport.airportCode}">${airport.airportCode}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr><td class="content">Departure Date</td><td><input id="date" onclick="disablePastDates();" type="date" name="departureDate" value="${requestScope.flight.departureDate}" required></td></tr>
                                <tr><td class="content">Departure Time</td><td><input type="time" name="departureTime" value="${requestScope.flight.departureTime}" required></td></tr>
                                <tr><td class="content">Duration In Minutes</td><td><input type="number" min="1" name="duration" value="${requestScope.flight.duration}" required></td></tr>
                                <tr><td class="content">Economy Price</td><td><input type="number" min="1000" name="economyPrice" value="${requestScope.flight.economyPrice}" required></td></tr>
                                <tr><td class="content">Business Price</td><td><input type="number" min="1000" name="businessPrice" value="${requestScope.flight.businessPrice}" required></td></tr>                                
                            </table> 
                            <div class="btnContent">
                                <input type="hidden" name="flightID" value="${requestScope.flight.flightID}">
                                <button type="submit" name="action" value="${requestScope.nextaction}">Save</button>
                            </div>                
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function disablePastDates() {
                var arr = new Date().toLocaleDateString().split('/');
                if (arr[0].length < 2)
                    arr[0] = '0' + arr[0];
                if (arr[1].length < 2)
                    arr[1] = '0' + arr[1];
                var today = [arr[2], arr[0], arr[1]].join('-');
                document.getElementById("date").setAttribute("min", today);
            }
        </script>
    </body>
</html>
