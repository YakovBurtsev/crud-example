<%--
  Created by IntelliJ IDEA.
  User: Burtsev
  Date: 08.08.2016
  Time: 18:45
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

<br/>
<br/>

<h1>User List</h1>

<c:if test="${!empty listUsers}">
    <table class="tg">
        <tr>
            <th width="80">id</th>
            <th width="120">name</th>
            <th width="80">age</th>
            <th width="60">isAdmin</th>
            <th width="120">createdDate</th>
            <th width="60">edit</th>
            <th width="60">delete</th>
        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.isAdmin}</td>
                <td><fmt:formatDate value="${user.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><a href="<c:url value='/edit/${user.id}'/>">edit</a></td>
                <td><a href="<c:url value='/remove/${user.id}'/>">delete</a></td>
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
                        <td><a href="<c:url value="/users"><c:param name="page" value="1"/>1</c:url>">&lt;&lt;</a></td>
                    </c:otherwise>
                </c:choose>
                <c:forEach begin="${startPage}" end="${endPage}" var="p">
                    <td>
                        <c:choose>
                            <c:when test ="${p==currentPage}">
                                <a href="<c:url value="/users"><c:param name="page" value="${p}"/>${p}</c:url>"><strong>${p}</strong></a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/users"><c:param name="page" value="${p}"/>${p}</c:url>">${p}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
                <c:choose>
                    <c:when test="${currentPage == lastPage}">
                        <td class="disabled"><a href="#">&gt;&gt;</a></td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="<c:url value="/users"><c:param name="page" value="${lastPage}"/>${lastPage}</c:url>">&gt;&gt;</a></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </tbody>
    </table>
</div>

<h1>Add/Edit a User</h1>

<c:url var="addAction" value="/users/add"/>

<form:form action="${addAction}" commandName="user">
    <table>
        <c:if test="${!empty user.name}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="name">
                    <spring:message text="Name"/>
                </form:label>
            </td>
            <td>
                <form:input path="name"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="age">
                    <spring:message text="Age"/>
                </form:label>
            </td>
            <td>
                <form:input path="age"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="isAdmin">
                    <spring:message text="Is Admin"/>
                </form:label>
            </td>
            <td>
                <form:input path="isAdmin"/>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <c:if test="${!empty user.name}">
                    <input type="submit"
                           value="<spring:message text="Edit User"/>"/>
                </c:if>
                <c:if test="${empty user.name}">
                    <input type="submit"
                           value="<spring:message text="Add User"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>
<br/>
<br/>
<h1>Search users by name</h1>
<form method="get" action="searchingResult">
    <table>
        <tr>
            <td>
                <label for="search">Name</label>
            </td>
            <td>
                <input type="text" id="search" name="name"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Search">
            </td>
        </tr>
    </table>
</form>
</body>
</html>

