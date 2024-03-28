<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aircraft Edit</title>
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
                            <p class="textHeader">Aircraft Edit</p>
                        </div>
                        <form action="AircraftController" method="post">
                            <table class="planeTbl">                
                                <tr><td class="content">Registration Number</td><td><input name="registration" value="${requestScope.aircraft.registration}" required></td></tr>	 
                                <tr>
                                    <td class="content">Aircraft Type</td>
                                    <td>
                                        <select name="typeID" required>
                                            <option value="${requestScope.aircraft.typeID}" hidden selected>${requestScope.aircraft.typeName}</option>
                                            <c:forEach var="type" items="${requestScope.typeList}">
                                                <option value="${type.typeID}">${type.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>                
                            <div class="btnContent">
                                <input type="hidden" name="aircraftID" value="${requestScope.aircraft.aircraftID}">
                                <button type="submit" name="action" value="${requestScope.nextaction}">Save</button>                                
                            </div>
                        </form>                        
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
