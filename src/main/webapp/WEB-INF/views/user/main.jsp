<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chính</title>
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

        p {
            margin: 0;
        }

        h2 {
            color: #3498db;
        }

        .button-link {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #3498db;
            border-radius: 5px;
            margin: 5px;
        }

        .button-link:hover {
            background-color: #1c70b6;
        }

        a {
            text-decoration: none;
            color: #3498db;
            margin-right: 15px;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div>
    <p><span>${msg}</span></p>
</div>
<div>
    <p>Xin chào <b><%= session.getAttribute("name") %></b></p>
</div>
<h2><%= session.getAttribute("username") %></h2>


<div>
    <a class="button-link" href="/articles">Danh sách các bài viết</a>
</div>
<div>
    <a class="button-link" href="/user?id=<%= session.getAttribute("id") %>">Thông tin cá nhân</a>
</div>
<%
    String username = (String) session.getAttribute("username");
    if ("admin".equals(username)) {
%>
<div>
    <a class="button-link" href="/users">Quản lý người dùng</a>
</div>
<%
    }
%>
<div>
    <a class="button-link" href="/logout">Đăng xuất</a>
</div>
</body>
</html>
