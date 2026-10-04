<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CheckOut</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px 40px; color: #000; }
        h2 { color: #008080; font-size: 22px; margin-bottom: 15px; }
        table { border-collapse: collapse; width: 650px; margin-bottom: 10px; }
        th, td { border: 1px solid #000; padding: 6px 10px; font-size: 14px; }
        th { font-weight: bold; text-align: left; }
        .total-info { font-size: 15px; margin-bottom: 20px; font-weight: bold; }
        .total-info span { font-weight: normal; color: #555; }
        .customer-card { border: 1px solid #add8e6; padding: 15px; width: 500px; margin-bottom: 20px; }
        .customer-card h4 { margin: 0 0 10px 0; color: #008080; }
        .customer-card p { margin: 4px 0; font-size: 14px; }
        .payment-methods { margin-bottom: 20px; font-size: 14px; font-weight: bold; }
        .payment-methods label { font-weight: normal; margin-right: 25px; cursor: pointer; }
        .btn-row input { padding: 4px 10px; cursor: pointer; margin-right: 5px; }
    </style>
</head>
<body>
<h2>CheckOut</h2>

<table>
    <tr>
        <th>Description</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Amount</th>
    </tr>
    <c:forEach var="item" items="${sessionScope.cart.items}">
        <tr>
            <td>${item.product.description}</td>
            <td><fmt:formatNumber value="${item.product.price}" minFractionDigits="2"/></td>
            <td>${item.quantity}</td>
            <td><fmt:formatNumber value="${item.total}" minFractionDigits="2"/></td>
        </tr>
    </c:forEach>
</table>

<div class="total-info">
    Total: $<fmt:formatNumber value="${sessionScope.cart.total}" minFractionDigits="2"/>
    <span>(Quy đổi thanh toán: <fmt:formatNumber value="${sessionScope.cart.total * 25000}" pattern="#,###"/> VND)</span>
</div>

<div class="customer-card">
    <h4>Customer Information</h4>
    <p><b>Username:</b> ${sessionScope.user.username != null ? sessionScope.user.username : 'sdsd'}</p>
    <p><b>Email to receive receipt:</b> ${sessionScope.user.email != null ? sessionScope.user.email : 'phuquy020105@gmail.com'}</p>
</div>

<form action="cart" method="post">
    <input type="hidden" name="action" value="process_payment">
    <div class="payment-methods">
        Payment Method: &nbsp;
        <label><input type="radio" name="paymentMethod" value="vnpay"> VNPay Sandbox Gateway</label>
        <label><input type="radio" name="paymentMethod" value="simulator" checked> Test Simulator (Instant)</label>
    </div>

    <div class="btn-row">
        <input type="submit" value="Proceed to Payment">
        <input type="button" value="Back to Cart" onclick="window.location.href='cart?action=cart'">
        <input type="button" value="Continue Shopping" onclick="window.location.href='index.jsp'">
    </div>
</form>
</body>
</html>