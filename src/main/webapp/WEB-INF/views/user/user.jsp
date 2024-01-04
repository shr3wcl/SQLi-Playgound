<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tài khoản</title>
    <style>
        body {
            font-family: 'Helvetica', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
            color: #333;
            text-align: center;
        }

        div {
            margin: 20px;
        }

        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        th, td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3498db;
            color: #fff;
        }

        h1 {
            color: #3498db;
        }

        span {
            font-weight: bold;
        }
    </style>
</head>
<body>
<div><span>${errMsg}</span></div>

<h1>Thông tin tài khoản</h1>

<table>
    <tr>
        <th>Thuộc tính</th>
        <th>Thông tin</th>
    </tr>
    <tr>
        <td>Tên</td>
        <td><span><%= request.getAttribute("name") %></span></td>
    </tr>
    <tr>
        <td>Tên đăng nhập</td>
        <td><span><%= request.getAttribute("userName") %></span></td>
    </tr>
    <tr>
        <td>Email</td>
        <td><span><%= request.getAttribute("userEmail") %></span></td>
    </tr>
</table>
</body>
</html>
