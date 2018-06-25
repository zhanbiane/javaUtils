package com.sy.util.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.sun.mail.pop3.POP3Folder;

/**
 * @deccription 接收邮件
 *
 * @author zhanbiane
 * 2018年6月25日
 */
public class ReceiveEmailUtil {
	private MimeMessage mimeMessage = null;
	private String dateFormat = "yy-MM-dd HH:mm"; // 默认的日前显示格式
	private StringBuffer bodyText = new StringBuffer();//正文文本

	public ReceiveEmailUtil(){
		super();
	}
	
	public ReceiveEmailUtil(MimeMessage mimeMessage) {
		super();
		this.mimeMessage = mimeMessage;
		System.out.println("创建一个ReceiveEmail的message对象....");
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
		System.out.println("创建一个ReceiveEmail的message对象....");
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	
	/**
	 * @return 邮件主题
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getSubject() throws UnsupportedEncodingException, MessagingException {
		String subject = "";
		subject = MimeUtility.decodeText(mimeMessage.getSubject());
		return subject;
	}
	
	/**
	 * @return 发送时间
	 * @throws MessagingException
	 */
	public String getSentDate() throws Exception{
		Date sentDate = mimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String strSentDate = format.format(sentDate);
        return strSentDate;
	}
	
	/**
	 * @return 发件人及地址
	 * @throws MessagingException
	 */
	public String getFrom() throws MessagingException{
		InternetAddress[] address = (InternetAddress[])mimeMessage.getFrom();
		String from = address[0].getAddress();
		if(from==null)
			from = "";
		String personal = address[0].getPersonal();
		if(personal==null)
			personal = "";
		return personal + "<" + from + ">";
	}
	
	/**
	 * 
	 * @param type
	 * @return 获得收件人、抄送人、密送人的姓名地址
	 * @throws Exception
	 */
	public String getMailAddress(String type) throws Exception{
		String mailAddr="";
		type = type.toUpperCase();
		if(type.equals("TO")||type.equals("CC")||type.equals("BCC")){
			InternetAddress[] inteaddress = null;
			if(type.equals("TO")){
				inteaddress = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.TO);
			}else if(type.equals("CC")){
				inteaddress = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.CC);
			}else{
				inteaddress = (InternetAddress[])mimeMessage.getRecipients(Message.RecipientType.BCC);
			}
			if(inteaddress!=null){
				for(int i=0;i<inteaddress.length;i++){
					String personal = inteaddress[i].getPersonal();
					if(personal ==null)
						personal = "";
					String address = inteaddress[i].getAddress();
					if(address==null)
						address = "";
					mailAddr += personal + "<" + address + ">;";
				}
			}
		}else{
			throw new Exception("error type");
		}
		return mailAddr;
	}
	
	
	/**
	 * MimeType类型的不同执行不同的操作
	 * @param (Part) message
	 * @return 邮件正文
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	public String getBodyText(Part part) throws MessagingException, IOException {
		String contentType = part.getContentType();
		System.out.println("邮件的MimeType类型: " + contentType);
		
		boolean con = false;
		if(contentType.indexOf("name")!=-1)
			con = true;
		if (!con && part.isMimeType("text/plain")){
			// text/plain 类型
			bodyText.append((String) part.getContent());
		}else if(!con && part.isMimeType("text/html")){
			// text/html 类型
			bodyText.append((String) part.getContent());
		}if(part.isMimeType("multipart/*")){
			//multipart/*
			Multipart multipart = (Multipart)part.getContent();
			int count = multipart.getCount();
			for(int i=0;i<count;i++){
				getBodyText(multipart.getBodyPart(i));
			}
		}else if(part.isMimeType("message/rfc822")){
			//message/rfc822
			getBodyText((Part)part.getContent());
		}
		return bodyText.toString();
	}
	
	/**
	 * @return 获得MessageID
	 * @throws MessagingException
	 */
	public String getMessageId() throws MessagingException{
		return mimeMessage.getMessageID();
	}
	
	
	/**
	 * @param (Part) message
	 * @return 是否包含附件
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public boolean isContainAttach(Part part) throws MessagingException, IOException{
		boolean contain = false;
		if(part.isMimeType("multipart/*")){
			Multipart multipart = (Multipart)part.getContent();
			int count = multipart.getCount();
			for(int i=0;i<count;i++){
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disposition = bodyPart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					contain = true;
                else if (bodyPart.isMimeType("multipart/*")){
                	contain = isContainAttach((Part) bodyPart);
                }else{
                    String contentType = bodyPart.getContentType();
                    if (contentType.toLowerCase().indexOf("application") != -1)
                    	contain = true;
                    if (contentType.toLowerCase().indexOf("name") != -1)
                    	contain = true;
                }
			}
		}else if(part.isMimeType("message/rfc822")){
			contain = isContainAttach((Part)part.getContent());
		}
		return contain;
	}
	
	/**
	 * 
	 * @param part
	 * @param 保存路径
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void saveAttachMent(Part part,String savePath) throws UnsupportedEncodingException, MessagingException, IOException{
		String fileName = "";
		if(part.isMimeType("multipart/*")){
			Multipart multipart = (Multipart)part.getContent();
			int count = multipart.getCount();
			for(int i=0;i<count;i++){
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disposition = bodyPart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))){
					fileName = bodyPart.getFileName();
					if(fileName.toLowerCase().indexOf("gb2312")!=-1)
						fileName = MimeUtility.decodeText(fileName);
					//具体的保存文件	
					saveFile(fileName, savePath, bodyPart.getInputStream());
				}else if (bodyPart.isMimeType("multipart/*")){
					saveAttachMent(bodyPart,savePath);
                }else{
                	fileName = bodyPart.getFileName();
					if(fileName!=null&&fileName.toLowerCase().indexOf("gb2312")!=-1){
						fileName = MimeUtility.decodeText(fileName);
						//具体的保存文件
						saveFile(fileName, savePath, bodyPart.getInputStream());
					}
                }
			}
		}else if(part.isMimeType("message/rfc822")){
			saveAttachMent((Part)part.getContent(),savePath);
		}
	}
	
	/**
	 * 具体执行保存文件的方法
	 * @param fileName
	 * @param savePath
	 * @param in
	 * @throws IOException
	 */
	private void saveFile(String fileName,String savePath,InputStream in) throws IOException {
		String separator = "";
		String osName = System.getProperty("os.name");
		if(osName==null)
			osName = "";
		if(osName.toLowerCase().indexOf("win")!=-1){
			separator = "\\";
		}else{
			separator = "/";
		}
		File file = new File(savePath+separator+fileName);
		System.out.println("保存附件的路径"+file.getPath());
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bis = new BufferedInputStream(in);
			int c;
			while((c=bis.read())!=-1){
				bos.write(c);
				bos.flush();
			}
		} catch (IOException e) {
			throw new IOException("文件保存失败!");
		}finally{
			bos.close();
			bis.close();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String host = "pop.exmail.qq.com";
        String username = "shenyang@seassoon.com";
        String password = "Asy123456";

        Properties p = new Properties();
        p.setProperty("mail.pop3.host", "pop.qq.com");

        Session session = Session.getDefaultInstance(p, null);
        Store store = session.getStore("pop3");
        store.connect(host, username, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message messages[] = folder.getMessages();
        System.out.println("邮件数量:　" + messages.length);
        ReceiveEmailUtil re = null;
        if(folder instanceof POP3Folder){
			POP3Folder inbox = (POP3Folder)folder;
			for(int i=0;i<messages.length;i++){
				MimeMessage message = (MimeMessage)messages[i];
				String uid = inbox.getUID(message);
				re = new ReceiveEmailUtil(message);
				
				System.out.println("邮件　" + i + "　UId:　" + uid);
				System.out.println("邮件　" + i + "　主题:　" + re.getSubject());
				System.out.println("邮件　" + i + "　是否包含附件:　" + re.isContainAttach((Part) messages[i]));
				System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
				System.out.println("邮件　" + i + "　发送人地址:　" + re.getFrom());
				System.out.println("邮件　" + i + "　收信人地址:　" + re.getMailAddress("to"));
				System.out.println("邮件　" + i + "　抄送:　" + re.getMailAddress("cc"));
				System.out.println("邮件　" + i + "　暗抄:　" + re.getMailAddress("bcc"));
				re.setDateFormat("yyyy年MM月dd日");
				System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
				System.out.println("邮件　" + i + "　邮件ID:　" + re.getMessageId());
//				System.out.println("邮件　" + i + "　正文内容:　\r\n" + re.getBodyText((Part) messages[i]));
			}
			re = new ReceiveEmailUtil((MimeMessage)messages[8]);
			re.saveAttachMent((Part)messages[8], "D:\\tmp");
        }
        folder.close(false);
		store.close();
	}
	
	
}
