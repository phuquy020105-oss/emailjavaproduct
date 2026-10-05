package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MailUtilGmail {
    private static final String RESEND_API_KEY = "re_" + "UB32Q7HH_43yaFAEZMCXTbyeD3Wtt9p22";
    public static final String ADMIN_EMAIL = "phuquy020105@gmail.com";

    public static void sendOrderInvoice(String customerEmail, String customerName, String orderId, String method,
                                        double totalUSD, long totalVND, String itemsHtml, String timeStr) {
        new Thread(() -> {
            try {
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

                String safeBody = body.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
                String safeSubject = subject.replace("\\", "\\\\").replace("\"", "\\\"");

                // Gửi trực tiếp đến email người đăng ký
                String targetEmail = (customerEmail != null && !customerEmail.trim().isEmpty()) ? customerEmail.trim() : ADMIN_EMAIL;

                String jsonPayload = "{"
                        + "\"from\": \"Phu Quy Music Store <onboarding@resend.dev>\","
                        + "\"to\": [\"" + targetEmail + "\"],"
                        + "\"subject\": \"" + safeSubject + "\","
                        + "\"html\": \"" + safeBody + "\""
                        + "}";

                sendHttpRequest(jsonPayload, targetEmail, safeSubject, safeBody);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void sendHttpRequest(String jsonPayload, String targetEmail, String subject, String body) {
        try {
            URI uri = URI.create("https://api.resend.com/emails");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + RESEND_API_KEY.trim());
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("Gửi mail tới [" + targetEmail + "] - Response code: " + code);

            if (code >= 200 && code < 300) {
                System.out.println("Thành công gửi tới: " + targetEmail);
            } else {
                // Nếu Resend báo lỗi 403 (do tài khoản Free chưa add domain nên không cho gửi ra ngoài)
                StringBuilder err = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        err.append(line);
                    }
                }
                System.err.println("Resend Error (" + code + "): " + err);

                // Fallback: nếu bị 403, gửi về ADMIN_EMAIL để luôn nhận được thư thông báo
                if (code == 403 && !targetEmail.equals(ADMIN_EMAIL)) {
                    System.out.println("Tài khoản Resend Free bị giới hạn gửi ra ngoài domain. Chuyển tiếp bản sao về admin...");
                    String fallbackPayload = "{"
                            + "\"from\": \"Phu Quy Music Store <onboarding@resend.dev>\","
                            + "\"to\": [\"" + ADMIN_EMAIL + "\"],"
                            + "\"subject\": \"[Gửi cho " + targetEmail + "] " + subject + "\","
                            + "\"html\": \"" + body + "\""
                            + "}";
                    sendHttpRequest(fallbackPayload, ADMIN_EMAIL, subject, body);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}