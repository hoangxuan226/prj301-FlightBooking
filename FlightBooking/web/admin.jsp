<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/planeManagement.css" />
    </head>
    <body>
        <c:if test="${sessionScope.role ne 'admin'}">
            <c:redirect url = "./"/>
        </c:if>
        <div class="container">
            <%@include file="header.jsp" %>
            <div class="mainContent">            
                <div class="btn-control">
                    <a href="AircraftController"><button class="btn-control-adjust" id="planeManagementBtn">Aircraft Management</button></a>
                    <a href="FlightController"><button class="btn-control-adjust" id="flightManagementBtn">Flight Management</button></a>
                    <a href="MemberController"><button class="btn-control-adjust" id="customerManagementBtn">Member Management</button></a>
                </div>
                <div class="tableContent">
                    <!--manage aircraft-->
                    <c:if test="${requestScope.manage eq 'aircraft'}">
                        <div class="planeList" id="planeListContent">
                            <div class="listHeader">
                                <div class="btn-type-list">
                                    <form action="AircraftTypeController">
                                        <button type="submit" name="action" value="List">Aircraft Types List</button>
                                    </form>
                                </div>
                                <p class="textHeader">Aircraft</p>
                                <div class="btnHeader">
                                    <form action="AircraftController">
                                        <input name="keyword" type="text" value="${requestScope.keyword}">
                                        <button type="submit" name="action" value="Search" >Search</button>
                                    </form>        
                                    <form action="AircraftController">
                                        <button type="submit" name="action" value="Add">Add</button>
                                    </form>
                                </div>
                            </div>
                            <table class="planeTbl">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Registration Number</th>
                                        <th>Aircraft Type</th>
                                        <th colspan="2">Functions</th>
                                    </tr>
                                </thead>
                                <tbody class="planeTblBody">
                                    <c:forEach var="aircraft" items="${requestScope.aircraftList}">
                                        <tr class="planeTblRow">
                                            <td>${aircraft.aircraftID}</td>
                                            <td>${aircraft.registration}</td>
                                            <td>${aircraft.typeName}</td>
                                            <td>
                                                <form action="AircraftController">
                                                    <input type="hidden" name="aircraftID" value="${aircraft.aircraftID}">
                                                    <button type="submit" name="action" value="Detail">Detail</button>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="AircraftController">
                                                    <input type="hidden" name="aircraftID" value="${aircraft.aircraftID}">
                                                    <button type="submit" name="action" value="Delete">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>    
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <!--manage flight-->
                    <c:if test="${requestScope.manage eq 'flight'}">
                        <div class="flightList" id="flightListContent">
                            <div class="listHeader">
                                <p class="textHeader">Flight List</p>
                                <div class="btnHeader">
                                    <form action="FlightController">
                                        <input name="keyword" type="text" value="${requestScope.keyword}">
                                        <button type="submit" name="action" value="Search" >Search</button>
                                    </form>
                                    <form action="FlightController">
                                        <button type="submit" name="action" value="Add">Add</button>
                                    </form>
                                </div>
                            </div>

                            <table class="flightTbl">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Flight Number</th>
                                        <th>Aircraft ID</th>
                                        <th>From</th>
                                        <th>To</th>
                                        <th>Departure Date</th>
                                        <th>Departure Time</th>
                                        <th>Duration In Minutes</th>
                                        <th>Economy Price</th>
                                        <th>Business Price</th>
                                        <th>Remaining Economy Seats</th>
                                        <th>Remaining Business Seats</th>
                                        <th colspan="3">Functions</th>
                                    </tr>
                                </thead>
                                <tbody class="flightTblBody">
                                    <c:forEach var="flight" items="${requestScope.flightList}">
                                        <tr class="flightTblRow">
                                            <td>${flight.flightID}</td>
                                            <td>${flight.flightNumber}</td>
                                            <td>${flight.aircraftID}</td>
                                            <td>${flight.from}</td>
                                            <td>${flight.to}</td>
                                            <td>${flight.departureDate}</td>
                                            <td>${flight.departureTime}</td>
                                            <td>${flight.duration}</td>
                                            <td class="currency">${flight.economyPrice}</td>
                                            <td class="currency">${flight.businessPrice}</td>
                                            <td>${flight.remainingEconomySeats}</td>
                                            <td>${flight.remainingBusinessSeats}</td>                        
                                            <td>
                                                <form action="FlightController">
                                                    <input type="hidden" name="flightID" value="${flight.flightID}">
                                                    <button type="submit" name="action" value="Detail">Detail</button>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="FlightController">
                                                    <input type="hidden" name="flightID" value="${flight.flightID}">
                                                    <button type="submit" name="action" value="Delete">Delete</button>
                                                </form>
                                            </td>
                                            <td>
                                                <form action="FlightController">
                                                    <input type="hidden" name="flightID" value="${flight.flightID}">
                                                    <button type="submit" name="action" value="ViewSeats">Booked Seats</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>   
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <!--manage members-->
                    <c:if test="${requestScope.manage eq 'member'}">
                        <div class="cusList" id="cusListContent">
                            <div class="listHeader">
                                <p class="textHeader">Member List</p>
                                <div class="btnHeader">
                                    <form action="MemberController">
                                        <input name="keyword" type="text" value="${requestScope.keyword}">
                                        <button type="submit" name="action" value="Search" >Search</button>
                                    </form>
                                </div>
                            </div>

                            <table class="cusTbl">
                                <thead>
                                    <tr>
                                        <th>Member Number</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Birthday</th>
                                        <th>Gender</th>
                                        <th>Email</th>
                                        <th>Phone Number</th>
                                        <th>Nationality</th>
                                        <th>Number ID</th>
                                        <th>Functions</th>
                                    </tr>
                                </thead>
                                <tbody class="cusTblBody">
                                    <c:forEach var="member" items="${requestScope.memberList}">
                                        <tr class="cusTblRow">
                                            <td>${member.memberNumber}</td>
                                            <td>${member.firstname}</td>
                                            <td>${member.lastname}</td>
                                            <td>${member.birthday}</td>
                                            <td>${member.gender}</td>
                                            <td>${member.email}</td>
                                            <td>${member.phoneno}</td>
                                            <td>${member.nationality}</td>
                                            <td>${member.numberID}</td>
                                            <td>
                                                <form action="MemberController" method="POST">
                                                    <input type="hidden" name="memberID" value="${member.memberID}">
                                                    <button type="submit" name="action" value="Detail">Detail</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                        </div>
                    </c:if>
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
