<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Error</title>
</head>
<body>
<c:if test="${errorMessage != null}">
    <H1>${errorMessage}</H1>
</c:if>

<a href="<c:url value='/'/>">Go back</a>

</body>
</html>
