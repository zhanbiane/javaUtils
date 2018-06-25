package com.sy.util.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @deccription 发送邮件
 *
 * @author zhanbiane
 * 2018年6月25日
 */
public class SendEmail {
	
	private static final String from = "zhangwei@seassoon.com";//发件人
	private static final String password = "Root@123";//密码
	private static final String to = "shenyang@seassoon.com";//收件人
	private static final String cc = "shenyang@seassoon.com";//抄送人
	private static final String host = "smtp.exmail.qq.com";//发送邮件的服务器主机host
//	private static final String host = "smtp.cnfic.com.cn";
	
	
	public static void sendEmailNew(String subject, String text) throws MessagingException {
		System.out.println("开始发送邮件》》》");

		// 获得系统属性
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties, null);

		// try {
		// 创建邮件容器对象
		MimeMessage message = new MimeMessage(session);
		// 发件人
		message.setFrom(new InternetAddress(from));
		// 收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// 抄送人
		message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		// 标题
		message.setSubject(subject);

		// 邮件主体(多消息体)容器
		Multipart multipart = new MimeMultipart();

		// 消息部分
		MimeBodyPart bodypart = new MimeBodyPart();
		// 发送 HTML 消息, 可以插入html标签
		// bodypart.setContent("<a href='http://www.baidu.com'>百度</a>",
		// "text/html;charset=UTF-8");
		// 文字消息
		bodypart.setText(text);
		multipart.addBodyPart(bodypart);// 加入主体容器

		// 所有邮件部件放入邮件主体
		message.setContent(multipart);

		// 发送邮件
		Transport transport = session.getTransport("smtp");
		transport.connect(host, from, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("Sent message successfully....");

	}	
	
	public static void sendEmailOld(String subject, String text) throws AddressException, MessagingException {
		System.out.println("开始发送邮件》》》");
		
		//获得系统属性
		Properties properties = System.getProperties();
		//设置邮件服务器地址
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.ssl.trust", host);
		//认证请求
		properties.put("mail.smtp.auth", true);
		//使用默认session对象存储用户名密码
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		
		//创建邮件容器对象
		MimeMessage message = new MimeMessage(session);
		//发件人
		message.setFrom(new InternetAddress(from));
		//收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));	
		//抄送人
		message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		//标题
		message.setSubject(subject);
		
		//邮件主体(多消息体)容器
		Multipart multipart = new MimeMultipart();
		
		//消息部分
		MimeBodyPart bodypart = new MimeBodyPart();
		// 发送 HTML 消息, 可以插入html标签
//			bodypart.setContent("<a href='http://www.baidu.com'>百度</a>", "text/html;charset=UTF-8");
		//文字消息
		bodypart.setText(text);
		multipart.addBodyPart(bodypart);//加入主体容器
		
		//所有邮件部件放入邮件主体
		message.setContent(multipart);
		
		//发送邮件
		Transport.send(message);
		System.out.println("Sent message successfully....");

	}
}
