<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Graph</title>
</head>
<body>
<h1>Upload a Graph in DIMACS format</h1>
<form action="graph-controller" method="post" enctype="multipart/form-data">
    <input type="file" name="dimacsFile" required>
    <br><br>

    <h3>Select desired Graph properties:</h3>
    <input type="checkbox" id="order" name="property" value="order">
    <label for="order">Order (Number of Vertices)</label><br>

    <input type="checkbox" id="size" name="property" value="size">
    <label for="size">Size (Number of Edges)</label><br>

    <input type="checkbox" id="connectedComponents" name="property" value="connectedComponents">
    <label for="connectedComponents">Number of Connected Components</label><br>

    <input type="checkbox" id="minDegree" name="property" value="minDegree">
    <label for="minDegree">Min Degree</label><br>

    <input type="checkbox" id="maxDegree" name="property" value="maxDegree">
    <label for="maxDegree">Max Degree</label><br>

    <br>
    <input type="submit" value="Upload and Analyze">
</form>

</body>
</html>