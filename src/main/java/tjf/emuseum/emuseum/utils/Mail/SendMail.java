/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 13:54:37
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 14:12:47
 */
package tjf.emuseum.emuseum.utils.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/2/28 21:49
 * @description:
 */
@Component
public class SendMail {
    @Qualifier("shiroMD5")
    @Autowired
    ShiroMD5 shiroMD5;

    String send_number;

    public SendMail() {
    }

    public ResponseEntity<?> sendNumber(String userMail, String salt) {

        // 收件人电子邮箱
        String to = userMail;

        // 发件人电子邮箱
        String from = "2034298621@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com"; // QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {

                // 发件人邮件用户名、授权码
                // 我的授权码ztjuwiygslvveija（写你自己）
                return new PasswordAuthentication("2034298621@qq.com", "ztjuwiygslvveija");
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            Date date = new Date();
            SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormatter.format(date);
            message.setSubject(time);

            // 设置消息体

            int number = (int) (Math.random() * 9000 + 1000);
            String mail_send_number = String.valueOf(number);
            message.setText("欢迎注册E-MUSEUM:(介绍)" + "您的注册验证码为:" + mail_send_number);
            this.send_number = mail_send_number;

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....Harmony");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        Map<String, String> responseData = new HashMap<>();
        responseData.put("salt", salt.replaceAll("-", ""));
        responseData.put("result", shiroMD5.encryptPassword(send_number, salt, 1024));
        return ResponseEntity.ok(responseData);
    }
}
