<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách người dùng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style>
        body {
            font-family: 'Helvetica', sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f7f7f7;
            color: #333;
        }

        div {
            margin-bottom: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #3498db;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background-color: #1c70b6;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3498db;
            color: white;
        }

        a {
            text-decoration: none;
            color: #3498db;
            margin-right: 15px;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .error{
            color: red;
        }

        .success{
            color: forestgreen;
        }
    </style>
</head>
<body>
<div>
    <form action="<c:url value='/users/delete'/>" method="post" modelAttribute="user">
        <div>
            <label for="username">Nhập Username cần xóa</label>
            <input type="text" id="username" name="username" required>
            <div><span>${errMsg}</span></div>
            <div><span>${sucMsg}</span></div>
        </div>
        <button type="submit">Xóa</button>
    </form>

    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>E-mail</th>
            <th>Tên</th>
            <th>Tên đăng nhập</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.name}</td>
                <td>${user.username}</td>
                <td>
                    <a class="btn btn-primary btn-sm mr-2"
                       href="<c:url value='/users/edit/'/>${user.id}">Edit</a>
                    <!-- Delete link is commented out for safety reasons -->
                    <!-- <a class="btn btn-danger btn-sm"
                       href="<c:url value='/users/delete/'/>${user.id}">Delete</a> -->
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
