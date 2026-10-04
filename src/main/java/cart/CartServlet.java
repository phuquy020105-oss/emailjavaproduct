package cart;

import business.*;
import data.ProductIO;
import util.MailUtilGmail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "cart";
        }

        String url = "/index.jsp";

        if (action.equals("shop")) {
            url = "/index.jsp";

        } else if (action.equals("cart")) {
            String productCode = request.getParameter("productCode");
            String quantityString = request.getParameter("quantity");

            if (productCode != null) {
                Product product = ProductIO.getProduct(productCode);
                int quantity = 1;
                try {
                    if (quantityString != null) {
                        quantity = Integer.parseInt(quantityString);
                    }
                } catch (NumberFormatException nfe) {
                    quantity = 1;
                }

                if (quantity > 0 && product != null) {
                    LineItem lineItem = new LineItem(product, quantity);
                    cart.addItem(lineItem);
                } else if (quantity <= 0 && product != null) {
                    LineItem lineItem = new LineItem(product, 0);
                    cart.removeItem(lineItem);
                }
            }
            url = "/cart.jsp";

        } else if (action.equals("remove")) {
            String productCode = request.getParameter("productCode");
            Product product = ProductIO.getProduct(productCode);
            if (product != null) {
                cart.removeItem(new LineItem(product, 0));
            }
            url = "/cart.jsp";

        } else if (action.equals("checkout")) {
            url = "/checkout.jsp";

        } else if (action.equals("register")) {
            String u = request.getParameter("username");
            String p = request.getParameter("password");
            String e = request.getParameter("email");
            Account acc = new Account(u, p, e);
            session.setAttribute("user", acc);
            url = "/cart.jsp";

        } else if (action.equals("process_payment")) {
            String paymentMethod = request.getParameter("paymentMethod");
            Account user = (Account) session.getAttribute("user");
            if (user == null) {
                user = new Account("sdsd", "123456", "phuquy020105@gmail.com");
                session.setAttribute("user", user);
            }

            if ("vnpay".equals(paymentMethod)) {
                // Giả lập trang báo hết hạn giao dịch VNPay Sandbox
                response.sendRedirect("https://sandbox.vnpayment.vn/paymentv2/Payment/Error.html?code=15");
                return;
            } else {
                // Test Simulator (Instant)
                String orderId = "SIM_" + System.currentTimeMillis();
                String methodName = "Hệ thống giả lập thanh toán tức thì (Test Simulator)";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String timeStr = sdf.format(new Date());

                double totalUSD = cart.getTotal();
                long totalVND = Math.round(totalUSD * 25000);

                StringBuilder itemsHtml = new StringBuilder();
                for (LineItem item : cart.getItems()) {
                    itemsHtml.append("+ ")
                            .append(item.getProduct().getDescription())
                            .append(" | SL: ").append(item.getQuantity())
                            .append(" | Đơn giá: $").append(String.format("%.2f", item.getProduct().getPrice()))
                            .append(" | Thành tiền: $").append(String.format("%.2f", item.getTotal()))
                            .append("<br>");
                }

                // Gửi hóa đơn trực tiếp vào email người mua
                MailUtilGmail.sendOrderInvoice(
                        user.getEmail(),
                        user.getUsername(),
                        orderId,
                        methodName,
                        totalUSD,
                        totalVND,
                        itemsHtml.toString(),
                        timeStr
                );

                cart.clear();
                request.setAttribute("successMessage", "Thanh toán thành công! Email cảm ơn đã được gửi tới " + user.getEmail() + ".");
                url = "/index.jsp";
            }
        }

        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);
    }
}