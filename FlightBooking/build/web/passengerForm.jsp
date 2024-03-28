<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Travellers</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/passengerForm.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <!-- intro -->
            <div class="mainContent">
                <div class="introduction">
                    <p class="textIntro">Enter Passenger Information</p>
                </div>

                <form class="" action="BookingController" method="post">
                    <div class="container-2">
                        <h3>Personal Information ${count}</h3>                        
                        <p class="txtRequired">*=required fields</p>
                        <p class="txtIntro">
                            Please fill in personal information as shown in your passport
                        </p>
                        <div class="inpInfo">                            
                            <h4>First name*</h4>
                            <input type="text" class="inputStyle personalInfo" name="firstname" value="${passenger.firstname}" required/>
                            <h4>Last Name*</h4>
                            <input type="text" class="inputStyle personalInfo" name="lastname" value="${passenger.lastname}" required/>
                            <h4>Date of Birth*</h4>
                            <input type="date" class="inputStyle personalInfo" id="date" name="birthday" value="${passenger.birthday}" onclick="disableFutureDates()" required/>
                            <h3>Frequent flyer cards</h3>
                            <h4>Membership number</h4>
                            <input type="text" class="inputStyle" name="memberNumber" value="${memberNumber}"/>
                            <div class="errorMsg">${error}</div>
                            <h3>Contact Information</h3>
                            <h4>E-mail*</h4>
                            <input type="email" class="inputStyle" name="email" value="${passenger.email}" required/>
                            <h4>Phone number*</h4>
                            <input type="text" class="inputStyle" name="phoneno" value="${passenger.phoneno}" required/>
                        </div>
                    </div>
                    <div class="confirm-btn">
                        <input type="hidden" name="passengerID" value="${passenger.passengerID}">      <!-- use for modify in booking process -->
                        <input type="hidden" name="index" value="${index}">                            <!-- use for modify in management -->
                        <input type="hidden" name="count" value="${count}">                            <!-- use for get Info in booking process -->
                        <button type="submit" name="action" value="${nextAction}">Confirm</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script>
        <c:if test="${not empty sessionScope.reservationCode}">
        const personalInfo = document.querySelectorAll('.inputStyle.personalInfo');
        personalInfo.forEach((element) => {
            element.setAttribute('readonly', true);
        });
        </c:if>

        function disableFutureDates() {
            var arr = new Date().toLocaleDateString().split('/');
            if (arr[0].length < 2)
                arr[0] = '0' + arr[0];
            if (arr[1].length < 2)
                arr[1] = '0' + arr[1];
            var today = [arr[2], arr[0], arr[1]].join('-');
            document.getElementById("date").setAttribute("max", today);
        }
    </script>
</html>
