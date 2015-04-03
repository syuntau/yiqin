package com.yiqin.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 电邮类
 * 
 * @author liujun
 * 
 */
public class MailUtil {
	// 收件人显示名字
	private String displayName;
	// 收件人地址
	private String to;
	// 发件人地址
	private String from;
	// smtp服务器地址
	private String smtpServer;
	// 认证用户名
	private String username;
	// 认证密码
	private String password;
	// 邮件主题
	private String subject;
	// 邮件正文
	private Object content;
	// 服务器是否要身份认证
	private boolean ifAuth;
	// 用于保存发送附件的文件名的集合
	private Vector<String> file = new Vector<String>();

	// 认证类
	private class SmtpAuth extends javax.mail.Authenticator {
		private String username, password;

		public SmtpAuth(String username, String password) {
			this.username = username;
			this.password = password;
		}

		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
			return new javax.mail.PasswordAuthentication(username, password);
		}
	}

	private Session session;

	private void getSession() {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		// 服务器需要身份认证
		if (ifAuth) {
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(username, password);
			session = Session.getDefaultInstance(props, smtpAuth);
			System.out.println("============电邮服务器需要身份认证");
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
			System.out.println("============电邮服务器无需身份认证");
		}
	}

	private Message message;

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 设置服务器是否需要身份认证
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	/**
	 * 设置E-mail用户名
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void addAttachfile(String fname) {
		file.addElement(fname);
	}

	public MailUtil() {

	}

	/**
	 * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容
	 */
	public MailUtil(String smtpServer, String from, String displayName,
			String username, String password, String to, String subject,
			Object content) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = true;
		this.username = username;
		this.password = password;
		this.to = to;
		this.subject = subject;
		this.content = content;
		getSession();
	}

	/**
	 * 初始化SMTP服务器地址、发送者E-mail地址、接收者、主题、内容
	 */
	public MailUtil(String smtpServer, String from, String displayName,
			String to, String subject, Object content) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = false;
		this.to = to;
		this.subject = subject;
		this.content = content;
		getSession();
	}

	public void sendMessage() throws Exception {
		createMessage();
		if (ifAuth) {
			System.out.println("============发送邮件服务器进入身份认证");
			Transport trans = session.getTransport("smtp");
			trans.connect(smtpServer, username, password);
			trans.sendMessage(this.message, this.message.getAllRecipients());
			System.out.println("是否连接====="+trans.isConnected());
			trans.close();
		} else {
			Transport.send(message);
			System.out.println("============邮件服务器无需身份认证，直接发送完毕");
		}
	}

	private void createMessage() throws Exception {
		this.message = new MimeMessage(session);
		Multipart mp = new MimeMultipart();
		// 设置发件人地址
		setFrom();
		// 设置收件人地址
		setRecipients();
		// 设置主题
		this.message.setSubject(subject);
		// 设置正文
		setContent(mp);
		// 设置附件
		setFile(mp);
		// 设置信件头的发送日期
		this.message.setSentDate(new Date());
		this.message.setContent(mp);
		this.message.saveChanges();
		System.out.println("============设置邮件各项参数完成");
	}

	/**
	 * 设置收件人地址
	 * 
	 * @throws Exception
	 */
	private void setRecipients() throws Exception {
		// 设置收件人地址
		List<Address> address = new ArrayList<Address>();
		String[] arrayTo = to.split(",");
		for (int i = 0; i < arrayTo.length; i++) {
			address.add(new InternetAddress(arrayTo[i].trim()));
		}
		Address[] arrAddress = {};
		message.setRecipients(Message.RecipientType.TO, address
				.toArray(arrAddress));
		System.out.println("============设置收件人地址完成");
	}

	/**
	 * 设置发件人地址
	 * 
	 * @throws Exception
	 */
	private void setFrom() throws Exception {
		// 设置发件人地址
		Address from_address = new InternetAddress(from.trim(), displayName);
		message.setFrom(from_address);
		System.out.println("============设置发件人地址完成");
	}

	/**
	 * 设置正文
	 * 
	 * @param mp
	 * @throws Exception
	 */
	private void setContent(Multipart mp) throws Exception {
		BodyPart mbp = new MimeBodyPart();
		mbp.setContent(content, "text/html;charset=UTF-8");
		mp.addBodyPart(mbp);
		System.out.println("============设置邮件正文完成");
	}

	/**
	 * 设置附件
	 * 
	 * @param mp
	 * @throws Exception
	 */
	private void setFile(Multipart mp) throws Exception {
		// 有附件
		if (!file.isEmpty()) {
			Enumeration<String> efile = file.elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbp = new MimeBodyPart();
				// 选择出每一个附件名
				String filename = efile.nextElement().toString();
				// 得到数据源
				FileDataSource fds = new FileDataSource(filename);
				// 得到附件本身并至入BodyPart
				mbp.setDataHandler(new DataHandler(fds));
				// 得到文件名同样至入BodyPart
				mbp.setFileName(fds.getName()); 

				if (filename.endsWith(".doc")) {
					mbp.setHeader("Content-Type", "application/msword");
				} else if (filename.endsWith(".pdf")) {
					mbp.setHeader("Content-Type", "application/pdf");
				} else if (filename.endsWith(".htm") || filename.endsWith(".html")) {
					mbp.setHeader("Content-Type", "text/html");
				} else if (filename.endsWith(".jpg")) {
					mbp.setHeader("Content-Type", "image/jpeg");
				} else if (filename.endsWith(".gif")) {
					mbp.setHeader("Content-Type", "image/gif");
				}
				mbp.setContentID(fds.getName());
				mbp.setDisposition(MimeBodyPart.ATTACHMENT);
				mp.addBodyPart(mbp);
				System.out.println("============设置邮件附件完成");
			}
		}
	}
}
