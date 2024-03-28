<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/cart.css" />
    </head>
    <body>
        <div class="container">
            <!-- upper -->
            <%@include file="header.jsp" %>
            <!-- intro -->
            <div class="mainContent">
                <div class="introduction">
                    <p class="textIntro">Your Selection</p>
                </div>
                <!-- Your flight -->
                <div class="tableContent">
                    <p class="textInfo">Your Flight</p>
                    <div class="flightInfo">
                        <div class="basicInfo">
                            <p>
                                <span style="font-weight: bold">${fromAirport.city} to ${toAirport.city}</span>
                                -
                                <span style="color: rgb(111, 1, 61)">${date}</span
                                >
                            </p>
                        </div>
                        <div class="detailedInfo">
                            <div class="left-col">
                                <div class="left-col-1">
                                    <span style="font-size: 13px">${flight.departureTime}</span>
                                    <span style="font-weight: bold">${flight.from}</span>
                                </div>
                                <div class="left-col-2">
                                    <span style="margin-top: 8px">Direct Flight</span>
                                    <h4>⭢</h4>
                                </div>
                                <div class="left-col-3">
                                    <c:if test="${flight.daysDifference gt 0}">
                                        <span style="font-size: 10px">+${flight.daysDifference} day</span>
                                    </c:if>
                                    <span style="font-size: 13px">${flight.arrivalTime}</span>
                                    <span style="font-weight: bold">${flight.to}</span>
                                </div>
                            </div>
                            <div class="mid-col" style="padding-top: 2.5rem">
                                <span>🕓</span>
                                Flight time <span class="duration">${flight.duration}</span>
                            </div>
                            <div class="right-col">
                                <div style="font-weight: bold; margin-bottom: 5px">
                                    ${seatType eq 'business' ? 'Business':'Economy'}
                                </div>
                                <div class="currency">${price}</div> 
                            </div>
                        </div>
                    </div>

                    <!-- Passenger -->
                    <div class="passengerArea">
                        <p class="textInfo">Passenger</p>
                        <c:forEach var="passenger" items="${passengerList}" varStatus="counter">
                            <div class="passengerInfo">
                                <div class="iconPass">
                                    <img src="./images/userIconPass.png" alt="" />
                                </div>
                                <div class="txtPassengerInfo">
                                    <p style="font-weight: bold">${passenger.lastname} ${passenger.firstname}</p>
                                    <p>${passenger.email}</p>
                                    <p>${passenger.phoneno}</p>
                                    <p>${passenger.memberID gt 0 ? 'Membership' : ''}</p>
                                </div>
                                <form action="BookingController">
                                    <input type="hidden" name="passengerID" value="${passenger.passengerID}">                                                                      
                                    <input type="hidden" name="index" value="${counter.index}">                                                                      
                                    <button class="button" type="submit" name="action" value="Modify">
                                        <span>Modify</span>
                                    </button>
                                </form>
                            </div>
                        </c:forEach>
                        <!-- Seat -->
                        <div class="seatSelection">
                            <div class="iconSeat">
                                <img src="./images/flightSeat.png" alt="" />
                            </div>
                            <div class="txtSeat"><p>Seats</p></div>
                            <div class="amountSeat"><p>${numOfSeats}/${numOfPassengers} seat selected</p></div>
<!--                            <div>
                                <button class="button">
                                    <span>Change your seat</span>
                                </button>
                            </div>-->
                        </div> 
                    </div>
                    <!-- Payment -->
                    <form action="BookingController">
                        <div class="payment">
                            <p class="textInfo">Payment Method</p>
                            <div class="paymentInfo">
                                <input type="radio" checked style="color: rgb(111, 1, 61)" />
                                <span style="font-weight: bold">Pay later -</span>
                                [<span style="font-style: italic; opacity: 0.5; font-size: smaller">Hold the booking now, pay later</span>]
                            </div>
                        </div>
                        <!-- Checkout -->
                        <div class="checkout">
                            <div style="font-weight: bold" class="totalPrice">
                                <p>
                                    <span style="color: rgb(111, 1, 61)">Total price: </span>
                                    <span class="currency">${totalPrice}</span> 
                                </p>
                            </div>
                            <div class="btn-checkout">
                                <input type="hidden" name="price" value="${price}">
                                <input type="hidden" name="totalPrice" value="${totalPrice}">
                                <button type="submit" name="action" value="${nextAction}" >${nextAction eq 'checkout'?'Checkout':'Done'}</button>
                            </div>
                        </div>
                    </form>
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
