<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lab 9</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div style="text-align: center;">
    <h1>Players</h1>
    <h2>
        <a href="${pageContext.request.contextPath}/PlayerNew">Add new player</a>
        &nbsp;&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/PlayerList">List all players</a>
        &nbsp;&nbsp;&nbsp;
        <a href="index.jsp">Back</a>
    </h2>
</div>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of players</h2></caption>
        <tr>
            <th>ID</th>
            <th>Team ID</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <c:forEach var="player" items="${listPlayer}">
            <tr>
                <td><c:out value="${player.id}"/></td>
                <td><c:out value="${player.teamId}"/></td>
                <td><c:out value="${player.name}"/></td>
                <td><c:out value="${player.price}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/PlayerEdit?id=<c:out value='${player.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath}/PlayerDelete?id=<c:out value='${player.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>