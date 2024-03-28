<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aircraft Types Page</title>        
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/aircraftTypeList.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>

            <div class="mainContent">
                <div class="tableContent">
                    <div class="planeList" id="planeListContent">
                        <div class="listHeader">
                            <p class="textHeader">Aircraft Types</p>
                            <div class="btnHeader">
                                <form action="AircraftTypeController">
                                    <input name="keyword" type="text" value="${requestScope.keyword}">
                                    <button type="submit" name="action" value="Search" >Search</button>
                                </form>        
                                <form action="AircraftTypeController">
                                    <button type="submit" name="action" value="Add">Add</button>
                                </form>
                                
                            </div>
                        </div>

                        <table class="planeTbl">
                            <thead>
                                <tr>
                                    <th>Type ID</th>
                                    <th>Name</th>
                                    <th>Number of Business seats</th>
                                    <th>Number of Economy seats</th>
                                    <th colspan="2">Functions</th>
                                </tr>
                            </thead>
                            <tbody class="planeTblBody">
                                <c:forEach var="type" items="${requestScope.list}">
                                    <tr class="planeTblRow">
                                        <td>${type.typeID}</td>
                                        <td>${type.name}</td>
                                        <td>${type.numberBusinessSeats}</td>
                                        <td>${type.numberEconomySeats}</td>
                                        <td>
                                            <form action="AircraftTypeController">
                                                <input type="hidden" name="typeID" value="${type.typeID}">
                                                <button type="submit" name="action" value="Detail">Detail</button>
                                            </form>
                                        </td>
                                        <td>
                                            <form action="AircraftTypeController">
                                                <input type="hidden" name="typeID" value="${type.typeID}">
                                                <button type="submit" name="action" value="Delete">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
