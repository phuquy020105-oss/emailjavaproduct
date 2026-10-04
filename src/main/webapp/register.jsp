<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px 40px; color: #000; }
        h2 { color: #008080; font-size: 22px; margin-bottom: 15px; }
        p { font-size: 14px; margin-bottom: 20px; }
        .form-row { display: flex; align-items: center; margin-bottom: 12px; }
        .form-row label { width: 90px; font-weight: bold; font-size: 14px; }
        .form-row input[type="text"], .form-row input[type="password"] {
            width: 180px; padding: 4px 6px; border: 1px solid #777; border-radius: 2px;
        }
        .btn-group input { padding: 4px 10px; margin-top: 8px; cursor: pointer; display: block; }
    </style>
</head>
<body>
<h2>User Registration</h2>
<p>To add items to your cart, please enter your name and email address below.</p>

<form action="cart" method="post">
    <input type="hidden" name="action" value="register">
    <div class="form-row">
        <label>Username:</label>
        <input type="text" name="username" value="sdsd" required>
    </div>
    <div class="form-row">
        <label>Password:</label>
        <input type="password" name="password" value="123456" required>
    </div>
    <div class="form-row">
        <label>Email:</label>
        <input type="text" name="email" value="phuquy020105@gmail.com" required>
    </div>
    <div class="btn-group">
        <input type="submit" value="Register & Continue">
        <input type="button" value="Back to CD List" onclick="window.location.href='index.jsp'">
    </div>
</form>
</body>
</html>