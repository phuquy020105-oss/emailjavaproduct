<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your cart</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px 40px; color: #000; }
        h2 { color: #008080; font-size: 22px; margin-bottom: 15px; }
        p { font-size: 14px; margin-bottom: 12px; }
        table { border-collapse: collapse; width: 650px; margin-bottom: 10px; }
        th, td { border: 1px solid #000; padding: 6px 10px; font-size: 14px; }
        th { font-weight: bold; text-align: left; }
        .qty-input { width: 25px; text-align: center; }
        .btn-act { padding: 3px 8px; cursor: pointer; }
        .btn-row { margin-top: 15px; }
        .btn-row input { padding: 4px 10px; cursor: pointer; margin-right: 5px; }
    </style>
</head>
<body>
<h2>Your cart</h2>
<p>Customer: <b>${sessionScope.user.username != null ? sessionScope.user.username : 'sdsd'}</b> (${sessionScope.user.email != null ? sessionScope.user.email : 'phuquy020105@gmail.com'})</p>

<table>
    <tr>
        <th>Quantity</th>
        <th>Description</th>
        <th>Price</th>
        <th>Amount</th>
        <th></th>
    </tr>
    <c:forEach var="item" items="${sessionScope.cart.items}">
        <tr>
            <form action="cart" method="post">
                <input type="hidden" name="action" value="cart">
                <input type="hidden" name="productCode" value="${item.product.code}">
                <td>
                    <input type="text" name="quantity" value="${item.quantity}" class="qty-input">
                    <input type="submit" value="Update" class="btn-act">
                </td>
                <td>${item.product.description}</td>
                <td>$<fmt:formatNumber value="${item.product.price}" minFractionDigits="2"/></td>
                <td>$<fmt:formatNumber value="${item.total}" minFractionDigits="2"/></td>
                <td>
                    <input type="button" value="Remove Item" class="btn-act" onclick="window.location.href='cart?action=remove&productCode=${item.product.code}'">
                </td>
            </form>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="3" style="text-align: right; font-weight: bold;">Total:</td>
        <td colspan="2" style="font-weight: bold;">$<fmt:formatNumber value="${sessionScope.cart.total}" minFractionDigits="2"/></td>
    </tr>
</table>

<p style="font-size: 13px; font-weight: bold;">To change the quantity, enter the new quantity and click on the Update button.</p>

<div class="btn-row">
    <input type="button" value="Continue Shopping" onclick="window.location.href='index.jsp'">
    <input type="button" value="Checkout" onclick="window.location.href='cart?action=checkout'">
</div>
</body>
</html>