<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose Seat</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/seats.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>

            <div class="mainContent">
                <form action="BookingController">
                    <div class="introduction">
                        <p class="textIntro">Choose your seat</p>
                    </div>
                    <div class="noti">
                        <div class="notiUpper">
                            <span class="imgNoti"><img src="./images/exclamationMark.png" alt=""/></span>
                            <span class="txtNoti">How to choose a seat</span>
                        </div>
                        <div class="notiLower"><p>Select your desired seat below</p></div>
                    </div>
                    <div class="pasName">
                        <table class="pasNameTbl">
                            <tr>
                                <c:forEach var="pax" items="${sessionScope.passengerList}">
                                    <td>
                                        <h1>${pax.lastname} ${pax.firstname}</h1>
                                        <input type="text" class="outputText" readonly required name="seats" value=""><br>
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>
                    </div>
                    <div class="discript"><p>↑ Front of the plane ↑</p></div>
                    <div class="seat-choose">
                        <div class="left-side-choose">
                            <div class="plane">
                                <ol class="cabin">
                                    <c:forEach begin="${requestScope.startRow}" end="${requestScope.startRow + requestScope.rows - 1}" var="index">
                                        <li class="row">
                                            <ol class="seats">
                                                <li class="seat">
                                                    <input type="checkbox" class="check" id="${index}A" autocomplete="off"/>
                                                    <label for="${index}A">${index}A</label>
                                                </li>
                                                <c:if test="${sessionScope.seatType eq 'economy'}">
                                                    <li class="seat">
                                                        <input type="checkbox" class="check" id="${index}B" autocomplete="off"/>
                                                        <label for="${index}B">${index}B</label>
                                                    </li>
                                                </c:if>
                                                <li class="seat">
                                                    <input type="checkbox" class="check" id="${index}C" autocomplete="off"/>
                                                    <label for="${index}C">${index}C</label>
                                                </li>
                                                <li class="seat">
                                                    <p>${index}</p>
                                                </li>
                                                <li class="seat">
                                                    <input type="checkbox" class="check" id="${index}D" autocomplete="off"/>
                                                    <label for="${index}D">${index}D</label>
                                                </li>
                                                <c:if test="${sessionScope.seatType eq 'economy'}">
                                                    <li class="seat">
                                                        <input type="checkbox" class="check" id="${index}E" autocomplete="off"/>
                                                        <label for="${index}E">${index}E</label>
                                                    </li>
                                                </c:if>
                                                <li class="seat">
                                                    <input type="checkbox" class="check" id="${index}F" autocomplete="off"/>
                                                    <label for="${index}F">${index}F</label>
                                                </li>
                                            </ol>
                                        </li>
                                    </c:forEach>
                                </ol>
                            </div>
                        </div>
                        <div class="right-side-choose">
                            <div class="seat-note">
                                <p>Seatmap Legend</p>
                                <table>
                                    <tr>
                                        <td>
                                    <li class="seat">
                                        <input type="checkbox" disabled />
                                        <label></label>
                                    </li>
                                    </td>
                                    <td>Occupied</td>
                                    </tr>
                                    <tr>
                                        <td>
                                    <li class="seat">
                                        <input type="checkbox" />
                                        <label></label>
                                    </li>
                                    </td>
                                    <td>Available Seat</td>
                                    </tr>
                                    <tr>
                                        <td>
                                    <li class="seat">
                                        <input type="checkbox" checked />
                                        <label></label>
                                    </li>
                                    </td>
                                    <td>Selected Seat</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="footer">
                        <div class="btn"><button type="submit" name="action" value="selectSeats">Confirm</button></div>
                    </div>
                </form>
            </div>
        </div>
    </body>


    <script>
        var allSeats = [];
        <c:forEach var="id" items="${requestScope.allSeats}">
        allSeats.push("${id}");
        </c:forEach>

        allSeats.forEach((id) => {
            const seat = document.getElementById(id);
            if (seat) {
                seat.parentNode.style.visibility = 'visible';
            }
        });

        var occupiredSeats = [];
        <c:forEach var="id" items="${requestScope.occupiredSeats}">
        occupiredSeats.push("${id}");
        </c:forEach>

        occupiredSeats.forEach((id) => {
            const seat = document.getElementById(id);
            if (seat) {
                seat.setAttribute('disabled', '');
            }
        });

        const max = ${sessionScope.numOfPassengers};
        document.querySelectorAll(".check").forEach((checkbox) => {
            checkbox.addEventListener("click", function (event) {
                if (document.querySelectorAll(".check:checked").length > max) {
                    event.preventDefault();
                }
            });
        });

        const outputText = document.querySelectorAll(".outputText");
        document.querySelectorAll(".check").forEach((checkbox) => {
            checkbox.addEventListener("change", function () {
                if (this.checked) {
                    for (var i = 0; i < outputText.length; i++) {
                        if (outputText[i].value === "") {
                            outputText[i].value = this.id;
                            break;
                        }
                    }
                } else {
                    for (var i = 0; i < outputText.length; i++) {
                        if (outputText[i].value === this.id) {
                            outputText[i].value = "";
                            break;
                        }
                    }
                }
            });
        });
    </script>
</html>
