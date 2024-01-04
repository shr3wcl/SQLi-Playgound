<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <style>
        body {
            font-family: 'Helvetica', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
            color: #333;
            text-align: center;
        }

        header {
            background-color: #3498db;
            color: white;
            padding: 20px;
            font-size: 32px;
        }

        nav {
            background-color: #2980b9;
            padding: 20px 10px;
        }

        nav a {
            text-decoration: none;
            color: white;
            margin: 0 15px;
            font-size: 18px;
            padding: 10px 15px;
            border-radius: 5px;
            transition: background-color 0.3s;
            background-color: indianred;
        }

        nav a:hover {
            background-color: #1c70b6;
        }

        section {
            padding: 40px;
        }

        footer {
            background-color: #3498db;
            color: white;
            padding: 10px;
        }
    </style>
</head>
<body>
<header>
    Xin chào, đây là trang chủ
</header>
<nav>
    <a href="/login">Đăng nhập</a>
    <a href="/register">Đăng ký</a>
</nav>
<section>
    <p>Chào mừng bạn đến với trang web. Trang web này có lưu lại request</p>
</section>
<footer>
    &copy; 2023 Trang chủ
</footer>
</body>
</html>
