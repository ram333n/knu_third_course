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
    <c:if test="${player != null}">
    <form action="PlayerUpdate" method="post">
        </c:if>
        <c:if test="${player == null}">
        <form action="PlayerInsert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${player != null}">
                            Edit player
                        </c:if>
                        <c:if test="${player == null}">
                            Add new player
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${player != null}">
                    <input type="hidden" name="id" value="<c:out value='${player.id}' />"/>
                </c:if>
                <tr>
                    <th>TeamID:</th>
                    <td>
                        <input type="text" name="teamId" size="45"
                               value="<c:out value='${player.teamId}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${player.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Price:</th>
                    <td>
                        <input type="text" name="price" size="45"
                               value="<c:out value='${player.price}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>