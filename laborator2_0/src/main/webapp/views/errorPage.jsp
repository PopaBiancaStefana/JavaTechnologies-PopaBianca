<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Error Processing Request</h1>

<c:if test="${errorMessage != null}">
    <p>${errorMessage}</p>
</c:if>

<a href="views/input.jsp">Go back</a>
</body>
</html>
