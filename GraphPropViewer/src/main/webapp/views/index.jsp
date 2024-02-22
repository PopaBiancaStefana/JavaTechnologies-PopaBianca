
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Graph properties</title>
</head>
<body>
    <h1>Upload a Graph in DIMACS format</h1>
    <%
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/graph-controller");
    %>
</body>
</html>
