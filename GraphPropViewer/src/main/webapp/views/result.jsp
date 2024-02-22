<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Graph Properties</title>
</head>
<body>
<h1>Graph Properties:</h1>

<jsp:useBean id="output" type="com.lab2.models.Output" scope="request" />

<c:if test="${output.order != null}">
    <p><strong>Order (Number of Vertices):</strong> ${output.order}</p>
</c:if>
<c:if test="${output.size != null}">
    <p><strong>Size (Number of Edges):</strong> ${output.size}</p>
</c:if>
<c:if test="${output.connectedComponents != null}">
    <p><strong>Number of Connected Components:</strong> ${output.connectedComponents}</p>
</c:if>
<c:if test="${not empty output.minDegree}">
    <p><strong>Min Degree:</strong> ${output.minDegree}</p>
</c:if>
<c:if test="${not empty output.maxDegree}">
    <p><strong>Max Degree:</strong> ${output.maxDegree}</p>
</c:if>

<a href="<c:url value='/'/>">Go back</a>

</body>
</html>
