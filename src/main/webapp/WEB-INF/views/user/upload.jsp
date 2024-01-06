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
    <title>Cập nhập hình đại diện</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f8f8;
            margin: 20px;
        }

        h2 {
            color: #333;
        }

        form {
            max-width: 400px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="file"] {
            margin-bottom: 10px;
        }

        p {
            color: #008000;
            margin-top: 10px;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<h2>Upload Avatar Form</h2>
<form action="/upload/avatar" method="post" enctype="multipart/form-data">
    <label for="file">Chọn file ảnh PNG để upload:</label>
    <input type="file" name="file" id="file" accept=".png">
    <br>
    <label>
        <input type="text" name="username" hidden="" value="<%= session.getAttribute("username") %>">
    </label>
    <c:if test="${not empty msg}">
        <p>${msg}</p>
    </c:if>
    <input type="submit" value="Upload">
</form>
</body>
</html>

