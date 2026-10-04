<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CD List</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px 40px; color: #000; }
        .header-bar { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #ddd; padding-bottom: 8px; margin-bottom: 20px; font-size: 14px; }
        .header-bar a { color: #008080; text-decoration: none; font-weight: bold; }
        h2 { color: #008080; font-size: 22px; margin-bottom: 15px; }
        table { border-collapse: collapse; width: 600px; }
        th, td { border: 1px solid #000; padding: 8px 12px; font-size: 14px; }
        th { font-weight: bold; text-align: left; }
        .price-col { width: 80px; }
        .btn-add { padding: 4px 8px; cursor: pointer; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; padding: 12px 20px; border-radius: 4px; margin-bottom: 20px; font-size: 14px; }
    </style>
</head>
<body>

<div class="header-bar">
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                Xin chào, <b>${sessionScope.user.username}</b> (${sessionScope.user.email})
            </c:when>
            <c:otherwise>
                Khách vãng lai | <a href="register.jsp">Đăng ký tài khoản</a>
            </c:otherwise>
        </c:choose>
    </div>
    <div>
        🛒 <a href="cart?action=cart">Xem giỏ hàng (${sessionScope.cart != null ? sessionScope.cart.count : 0})</a>
    </div>
</div>

<c:if test="${not empty successMessage}">
    <div class="alert-success">
        ✓ Thông báo: ${successMessage}
    </div>
</c:if>

<h2>CD list</h2>
<table>
    <tr>
        <th>Description</th>
        <th class="price-col">Price</th>
        <th></th>
    </tr>
    <tr>
        <td>86 (the band) - True Life Songs and Pictures</td>
        <td>$14.95</td>
        <td>
            <form action="cart" method="post" style="margin:0;">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="productCode" value="8601">
                <input type="submit" value="Add To Cart" class="btn-add">
            </form>
        </td>
    </tr>
    <tr>
        <td>Paddlefoot - The first CD</td>
        <td>$12.95</td>
        <td>
            <form action="cart" method="post" style="margin:0;">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="productCode" value="pf01">
                <input type="submit" value="Add To Cart" class="btn-add">
            </form>
        </td>
    </tr>
    <tr>
        <td>Paddlefoot - The second CD</td>
        <td>$14.95</td>
        <td>
            <form action="cart" method="post" style="margin:0;">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="productCode" value="pf02">
                <input type="submit" value="Add To Cart" class="btn-add">
            </form>
        </td>
    </tr>
    <tr>
        <td>Joe Rut - Genuine Wood Grained Finish</td>
        <td>$14.95</td>
        <td>
            <form action="cart" method="post" style="margin:0;">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="productCode" value="jr01">
                <input type="submit" value="Add To Cart" class="btn-add">
            </form>
        </td>
    </tr>
</table>

</body>
</html>