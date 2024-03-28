<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flight Details</title>
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
                            <p class="textHeader">Flight Detail</p>
                        </div>
                        <table class="planeTbl"> 
                            <tr><td class="content">ID</td><td>${requestScope.flight.flightID}</td></tr>
                            <tr><td class="content">Flight Number</td><td>${requestScope.flight.flightNumber}</td></tr>
                            <tr><td class="content">Aircraft</td><td>${requestScope.flight.aircraftReg}</td></tr>
                            <tr><td class="content">From</td><td>${requestScope.flight.from}</td></tr>
                            <tr><td class="content">To</td><td>${requestScope.flight.to}</td></tr>
                            <tr><td class="content">Departure Date</td><td>${requestScope.flight.departureDate}</td></tr>
                            <tr><td class="content">Departure Time</td><td>${requestScope.flight.departureTime}</td></tr>
                            <tr><td class="content">Duration In Minutes</td><td>${requestScope.flight.duration}</td></tr>
                            <tr><td class="content">Economy Price</td><td class="currency">${requestScope.flight.economyPrice}</td></tr>
                            <tr><td class="content">Business Price</td><td class="currency">${requestScope.flight.businessPrice}</td></tr>
                            <tr><td class="content">Remaining Economy Seats</td><td>${requestScope.flight.remainingEconomySeats}</td></tr>
                            <tr><td class="content">Remaining Business Seats</td><td>${requestScope.flight.remainingBusinessSeats}</td></tr>

                        </table>         

                        <form class="btnContent" action="FlightController">
                            <input type=hidden name="flightID" value="${requestScope.flight.flightID}">
                            <button type=submit name="action" value="Edit">Edit</button>
                            <button>Back</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        const currencyElements = document.querySelectorAll('.currency');
        currencyElements.forEach(element => {
            const content = element.textContent;
            if (!isNaN(content)) {
                var value = parseInt(content);
                element.textContent = value.toLocaleString('vi', {style: 'currency', currency: 'VND'});
            }
        });
    </script>
</html>
