<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Graph</title>
</head>
<body>
<h1>Upload a Graph in DIMACS format</h1>

<% String userProperties = (String) request.getAttribute("userProperties"); %>

<form action="graph-controller" method="post" enctype="multipart/form-data">
    <input type="file" name="dimacsFile" required>
    <br><br>

    <h3>Select desired Graph properties:</h3>
    <input type="checkbox" id="order" name="property" value="order"
        <% if (userProperties != null && userProperties.contains("order")) { %>
           checked="checked"
        <% } %>>
    <label for="order">Order (Number of Vertices)</label><br>


    <input type="checkbox" id="size" name="property" value="size"
        <% if (userProperties != null && userProperties.contains("size")) { %>
           checked="checked"
        <% } %>>
    <label for="size">Size (Number of Edges)</label><br>

    <input type="checkbox" id="connectedComponents" name="property" value="connectedComponents"
        <% if (userProperties != null && userProperties.contains("connectedComponents")) { %>
           checked="checked"
        <% } %>>
    <label for="connectedComponents">Number of Connected Components</label><br>

    <input type="checkbox" id="minDegree" name="property" value="minDegree"
        <% if (userProperties != null && userProperties.contains("minDegree")) { %>
           checked="checked"
        <% } %>>
    <label for="minDegree">Min Degree</label><br>

    <input type="checkbox" id="maxDegree" name="property" value="maxDegree"
        <% if (userProperties != null && userProperties.contains("maxDegree")) { %>
           checked="checked"
        <% } %>>
    <label for="maxDegree">Max Degree</label><br>

    <br>
    <input type="submit" value="Upload and Analyze">
</form>

</body>
</html>