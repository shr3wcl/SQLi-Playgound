<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký</title>
    <style>
        body {
            font-family: 'Helvetica', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
            color: #333;
            text-align: center;
        }

        form {
            max-width: 400px;
            margin: 50px auto;
            background-color: #3498db;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            color: white;
        }

        div {
            margin-bottom: 15px;
        }

        span {
            display: block;
            margin-bottom: 5px;
            color: white;
            text-align: left;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #2980b9;
            border-radius: 5px;
            font-size: 16px;
        }

        button, .loginBtn {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #2980b9;
            color: white;
            font-size: 18px;
            cursor: pointer;
            margin: 10px;
        }

        button:hover {
            background-color: #1c70b6;
        }
    </style>
</head>
<body>
<h2>Đăng ký</h2>
<form action="/processRegister" method="post">
    <div>
        <span>Tên:</span>
        <input type="text" name="name" required>
    </div>
    <div>
        <span>Email:</span>
        <input type="email" name="email" required>
    </div>
    <div>
        <span>Tên đăng nhập:</span>
        <input type="text" name="username" required>
    </div>
    <div>
        <span>Mật khẩu:</span>
        <input type="password" name="password" required>
    </div>
    <p>${errMsg}</p>
    <button type="submit">Đăng ký</button>
    <button onclick="window.location.href='/login'" class="loginBtn">Đăng nhập</button>
</form>
</body>
</html>
