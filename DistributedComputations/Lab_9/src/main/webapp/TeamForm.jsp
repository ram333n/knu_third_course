<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Lab 9</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div style="text-align: center;">
    <h1>Teams</h1>
    <h2>
        <a href="${pageContext.request.contextPath}/TeamNew">Add new team</a>
        &nbsp;&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/TeamList">List all teams</a>
        &nbsp;&nbsp;&nbsp;
        <a href="index.jsp">Back</a>
    </h2>
</div>
<div align="center">
    <c:if test="${team != null}">
    <form action="TeamUpdate" method="post">
        </c:if>
        <c:if test="${team == null}">
        <form action="TeamInsert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${team != null}">
                            Edit team
                        </c:if>
                        <c:if test="${team == null}">
                            Add New team
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${team != null}">
                    <input type="hidden" name="id" value="<c:out value='${team.id}' />"/>
                </c:if>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${team.name}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th>Country:</th>
                    <td>
                        <input type="text" name="country" size="45"
                               value="<c:out value='${team.country}' />"
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