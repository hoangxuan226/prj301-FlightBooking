<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flight Selection</title>
        <link rel="stylesheet" href="./css/flights.css">
    </head>
    <body>
        <div class="container">
            <!-- upper -->
            <div class="header">
                <div class="detailedInfo">
                    <div class="left-col">
                        <div class="left-col-1">
                            <span class="txtLeftCol">${fromAirport.airportCode}</span>
                            <span>${fromAirport.city}</span>
                        </div>
                        <div class="left-col-2">
                            <h3>⟶</h3>
                        </div>
                        <div class="left-col-3">
                            <span class="txtLeftCol">${toAirport.airportCode}</span>
                            <span>${toAirport.city}</span>
                        </div>
                    </div>
                    <div class="mid-col">
                        <h3 style="font-size: 20px">Depart</h3>
                        <span>${date}</span>
                    </div>
                    <div class="right-col">
                        <div style="font-weight: bold; padding-top: 25px; font-size: 20px">
                            Passenger
                        </div>
                        <div class="passengerAmount">
                            <p>${numOfPassengers}</p>
                            <div class="iconPass">
                                <img src="./images/userIconPass.png" alt="" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- body -->
            <div class="body">
                <c:if test="${not empty flightList}">
                    <div class="flightList">
                        <!-- Flight List -->
                        <c:forEach var="flight" items="${flightList}">
                            <div class="detailedInfoList">
                                <div class="list-left-col">
                                    <div class="list-left-col-1">
                                        <span style="font-size: 13px">${flight.departureTime}</span>
                                        <span style="font-weight: bold">${flight.from}</span>
                                    </div>
                                    <div class="list-left-col-2">
                                        <span style="margin-top: 8px">Direct Flight</span>
                                        <h4>⭢</h4>
                                    </div>
                                    <div class="list-left-col-3">
                                        <c:if test="${flight.daysDifference gt 0}">
                                            <span style="font-size: 10px">+${flight.daysDifference} day</span>
                                        </c:if>                                        
                                        <span style="font-size: 13px">${flight.arrivalTime}</span>
                                        <span style="font-weight: bold">${flight.to}</span>
                                    </div>
                                </div>
                                <div class="list-mid-col" style="padding-top: 3rem">
                                    <span>🕓</span>
                                    Flight time <span class="duration">${flight.duration}</span>
                                </div>

                                <c:url var="selectEconomy" value="BookingController">
                                    <c:param name="action" value="SelectFlight"/>
                                    <c:param name="flightID" value="${flight.flightID}"/>
                                    <c:param name="numOfPassengers" value="${numOfPassengers}"/>
                                    <c:param name="seatType" value="economy"/>
                                </c:url>
                                <c:url var="selectBusiness" value="BookingController">
                                    <c:param name="action" value="SelectFlight"/>
                                    <c:param name="flightID" value="${flight.flightID}"/>
                                    <c:param name="numOfPassengers" value="${numOfPassengers}"/>
                                    <c:param name="seatType" value="business"/>
                                </c:url>
                                <c:set var="availEconomySeats" value="${numOfPassengers le flight.remainingEconomySeats}"/>
                                <c:set var="availBusinessSeats" value="${numOfPassengers le flight.remainingBusinessSeats}"/>

                                <div class="list-right-col">                                                            
                                    <a href="${selectEconomy}" class=${availEconomySeats?'clickable':'disabled'}>                            
                                        <p style="font-size: 12px">${flight.remainingEconomySeats} seats remain</p>
                                        <p class="txtRightCol">Economy</p>
                                        <p class="txtRightCol">from</p>
                                        <p class="currency">${flight.economyPrice * numOfPassengers}</p>                                       
                                    </a> 
                                    <a href="${selectBusiness}" class=${availBusinessSeats?'clickable':'disabled'}>
                                        <p style="font-size: 12px">${flight.remainingBusinessSeats} seats remain</p>
                                        <p class="txtRightCol">Business</p>
                                        <p class="txtRightCol">from</p>
                                        <p class="currency">${flight.businessPrice * numOfPassengers}</p>                        
                                    </a>
                                </div>
                            </div>
                        </c:forEach>                       
                    </div>
                </c:if>
                <!-- Not found Flight -->
                <c:if test="${empty flightList}">
                    <div>
                        <div class="notFoundFlightTxt">
                            Do not have any flights. Please find another flight.
                        </div>
                        <div class="findPlaneBtn">
                            <a href="./">Find another flight</a>
                        </div>
                    </div> 
                </c:if>
            </div>
        </div>
    </body>

    <script>
        const currencyElements = document.querySelectorAll('.currency');
        currencyElements.forEach(element => {
            const content = element.textContent;
            if (!isNaN(content)) {
                var value = parseInt(content);
                element.textContent = value.toLocaleString('vi', {style: 'currency', currency: 'VND'}).replace('₫', 'VND');
            }
        });

        const durationElements = document.querySelectorAll('.duration');
        durationElements.forEach(element => {
            const content = element.textContent;
            if (!isNaN(content)) {
                var minutes = parseInt(content);
                var hours = Math.floor(minutes / 60);
                var mins = minutes % 60;
                element.textContent = hours + "hrs " + mins + "mins";
            }
        });
    </script>
</html>
