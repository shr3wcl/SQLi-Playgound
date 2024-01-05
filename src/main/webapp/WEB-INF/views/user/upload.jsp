<%--
  Created by IntelliJ IDEA.
  User: tripm
  Date: 1/5/2024
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>File Upload Form</title>
</head>
<body>
<h2>File Upload Form</h2>
<form action="/upload/avatar" method="post" enctype="multipart/form-data">
    <label for="file">Chọn file ảnh để upload:</label>
    <input type="file" name="file" id="file" accept=".png">
    <br>
    <label>
        <input type="text" name="username" hidden="" value="<%= session.getAttribute("username") %>">
    </label>
    <input type="submit" value="Upload">
</form>
</body>
</html>

