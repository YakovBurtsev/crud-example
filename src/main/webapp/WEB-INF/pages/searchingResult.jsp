<%--
  Created by IntelliJ IDEA.
  User: Burtsev
  Date: 11.08.2016
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Users Page</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>

<a href="users">back to Users Page</a>

<br/>
<br/>

<h1>Searching Result</h1>

<c:if test="${!empty listUsersByName}">
    <table class="tg">
        <tr>
            <th width="80">id</th>
            <th width="120">name</th>
            <th width="80">age</th>
            <th width="60">isAdmin</th>
            <th width="120">createdDate</th>
        </tr>
        <c:forEach items="${listUsersByName}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.isAdmin}</td>
                <td><fmt:formatDate value="${user.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<div class="pagination">
    <table>
        <tbody>
        <tr>
            <c:choose>
                <c:when test="${currentPage == 1}">
                    <td class="disabled"><a href="#">&lt;&lt;</a></td>
                </c:when>
                <c:otherwise>
                    <td><a href="<c:url value="/searchingResult"><c:param name="page" value="1"/><c:param name="name" value="${searchName}"/>1</c:url>">&lt;&lt;</a></td>
                </c:otherwise>
            </c:choose>
            <c:forEach begin="${startPage}" end="${endPage}" var="p">
                <td>
                    <c:choose>
                        <c:when test ="${p==currentPage}">
                            <a href="<c:url value="/searchingResult"><c:param name="page" value="${p}"/><c:param name="name" value="${searchName}"/>${p}</c:url>"><strong>${p}</strong></a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/searchingResult"><c:param name="page" value="${p}"/><c:param name="name" value="${searchName}"/>${p}</c:url>">${p}</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
            <c:choose>
                <c:when test="${currentPage == lastPage}">
                    <td class="disabled"><a href="#">&gt;&gt;</a></td>
                </c:when>
                <c:otherwise>
                    <td><a href="<c:url value="/searchingResult"><c:param name="page" value="${lastPage}"/><c:param name="name" value="${searchName}"/>${lastPage}</c:url>">&gt;&gt;</a></td>
                </c:otherwise>
            </c:choose>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>

