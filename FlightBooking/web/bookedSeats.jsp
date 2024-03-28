<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booked Seats</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/bookedSeats.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>

            <div class="mainContent">
                <div class="tableContent">
                    <div>
                        <div class="listHeader">
                            <p class="textHeader">Booked Seats</p>                            
                        </div>

                        <table class="planeTbl">
                            <thead>
                                <tr>
                                    <th>Seat</th>
                                    <th>Seat Type</th>
                                    <th>Price</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Birthday</th>
                                    <th>Email</th>
                                    <th>Phone Number</th>
                                    <th>Member ID</th>                                    
                                </tr>
                            </thead>
                            <tbody class="planeTblBody">                                
                                <c:forEach var="detail" items="${requestScope.detailList}">
                                    <c:set var="passenger" value="${passengerList[detail.passengerID]}"/>
                                    <tr class="planeTblRow">
                                        <td>${detail.seat}</td>
                                        <td>${detail.seatType}</td>
                                        <td class="currency">${detail.price}</td> 
                                        <td>${passenger.firstname}</td>
                                        <td>${passenger.lastname}</td>
                                        <td>${passenger.birthday}</td>
                                        <td>${passenger.email}</td>
                                        <td>${passenger.phoneno}</td>
                                        <td>${passenger.memberID gt 0 ? passenger.memberID : ''}</td>                                                                                                                     
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
                element.textContent = value.toLocaleString('vi', {style: 'currency', currency: 'VND'}).replace('₫', 'VND');
            }
        });
    </script>
</html>
