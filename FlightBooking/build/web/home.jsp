<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HVH Airways</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/home.css">
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <div class="container-2">
                <div class="fromBooking">
                    <div class="rowButton">
                        <button id="bookTicketBtn" class="active">Book Ticket</button>
                        <button id="myReservationBtn">My Reservation</button>
                    </div>
                    <div class="content">
                        <form action="BookingController" id="bookTicketContent" class="bookTicket">
                            <div class="rowBooking">
                                <div class="from">
                                    <p style="font-weight: bold">From</p>
                                    <select class="selectInput" id="from" name="from" onchange="compareOptions()" required>
                                        <option value="" hidden disabled selected></option>
                                        <c:forEach var="airport" items="${requestScope.airportList}">                                       
                                            <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="to">
                                    <p style="font-weight: bold">To</p>
                                    <select class="selectInput" id="to" name="to" onchange="compareOptions();" required>
                                        <option value="" hidden disabled selected></option>
                                        <c:forEach var="airport" items="${requestScope.airportList}">                                            
                                            <option value="${airport.airportCode}">${airport.city} (${airport.airportCode})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="passenger">
                                    <p style="font-weight: bold">Passenger</p>
                                    <input class="selectInput" type="number" min="1" value="1" name="numOfPassengers">
                                </div>
                            </div>
                            <div class="rowConfirm">
                                <div class="date">
                                    <p style="font-weight: bold">Date of Department</p><p id="test"></p>
                                    <input class="selectInput" id="date" type="date" min="" name="date" onclick="disablePastDates()" required>
                                </div>
                                <div class="btn-book">
                                    <button type="submit" name="action" value="FindFlight">Find Flight</button>
                                </div>
                            </div>
                        </form>
                        <form action="BookingController" id="myReservationContent" class="reservation-hidden hidden">
                            <h4 class="mt-2">Reservation Code/TicketNumber</h4>
                            <input type="text" class="input-code" name="reservationCode" value="${reservationCode}" required>
                            <h4 class="mt-2">Lastname</h4>
                            <div class="btn-search">
                                <input type="text" name="lastname" value="${lastname}" required>
                                <button type="submit" name="action" value="SearchReservation">Search</button>
                            </div>
                            <div style="color:red; padding-top: 15px">${error}</div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <script>
            const bookTicketBtn = document.getElementById("bookTicketBtn");
            const myReservationBtn = document.getElementById("myReservationBtn");
            const bookTicketContent = document.getElementById("bookTicketContent");
            const myReservationContent = document.getElementById("myReservationContent");

            bookTicketBtn.addEventListener("click", function () {
                bookTicketBtn.classList.add("active");
                myReservationBtn.classList.remove("active");
                bookTicketContent.classList.remove("hidden");
                myReservationContent.classList.add("hidden");
            });

            myReservationBtn.addEventListener("click", function () {
                myReservationBtn.classList.add("active");
                bookTicketBtn.classList.remove("active");
                bookTicketContent.classList.add("hidden");
                myReservationContent.classList.remove("hidden");
            });

            <c:if test="${page eq 'reservation'}">
            myReservationBtn.classList.add("active");
            bookTicketBtn.classList.remove("active");
            bookTicketContent.classList.add("hidden");
            myReservationContent.classList.remove("hidden");
            </c:if>

            const from = document.getElementById("from");
            const to = document.getElementById("to");

            function compareOptions() {
                if (from.value === to.value) {
                    to.value = "";
                }
            }

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
