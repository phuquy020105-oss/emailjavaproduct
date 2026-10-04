package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtilGmail {

    public static final String ADMIN_EMAIL = "phuquy020105@gmail.com";
    // Mật khẩu ứng dụng Gmail (viết liền không khoảng trắng)
    private static final String APP_PASSWORD = "pfhbcqooeabkojvq";

    public static void sendMail(String toEmail, String subject, String bodyHtml) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ADMIN_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADMIN_EMAIL, "Phú Quý Music Store"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(bodyHtml, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Đã gửi email thành công tới: " + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendOrderInvoice(String customerEmail, String customerName, String orderId, String method,
                                        double totalUSD, long totalVND, String itemsHtml, String timeStr) {
        String subject = "[Phú Quý Music Store] Cảm ơn bạn đã mua hàng - Đơn hàng #" + orderId;

        String body = "<div style='font-family: Arial, sans-serif; font-size: 15px; line-height: 1.6; color: #222;'>"
                + "<p>Xin chào " + customerName + ",</p>"
                + "<p>Cảm ơn bạn đã mua hàng tại <b>Phú Quý Music Store</b>!<br>"
                + "Đơn hàng của bạn đã được ghi nhận và thanh toán thành công qua hệ thống.</p>"
                + "<p>--- THÔNG TIN ĐƠN HÀNG ---<br>"
                + "Mã đơn hàng: #" + orderId + "<br>"
                + "Phương thức thanh toán: " + method + "<br>"
                + "Thời gian giao dịch: " + timeStr + "<br>"
                + "Trạng thái: Đã thanh toán thành công<br>"
                + "Email nhận thông báo: " + customerEmail + "</p>"
                + "<p>--- CHI TIẾT SẢN PHẨM ---<br>"
                + itemsHtml + "</p>"
                + "<p>Tổng thanh toán (USD): $" + String.format("%.2f", totalUSD) + "<br>"
                + "Tổng quy đổi (VND): " + String.format("%,d", totalVND) + " VND</p>"
                + "<p>Đơn hàng của bạn đang được đóng gói và chuẩn bị bàn giao cho đơn vị vận chuyển sớm nhất.<br>"
                + "Nếu bạn có bất kỳ thắc mắc nào, vui lòng phản hồi trực tiếp email này hoặc liên hệ hotline: 1900 6868.</p>"
                + "<p>Trân trọng cảm ơn,<br>Đội ngũ Phú Quý Music Store</p>"
                + "</div>";

        // Gửi trực tiếp đến email thật của người mua
        sendMail(customerEmail, subject, body);

        // Đồng thời gửi một bản sao về email của bạn để lưu trữ/theo dõi
        if (!customerEmail.equalsIgnoreCase(ADMIN_EMAIL)) {
            sendMail(ADMIN_EMAIL, "[Quản Trị] Bản sao đơn hàng #" + orderId + " - Khách: " + customerName, body);
        }
    }
}