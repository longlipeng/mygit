package com.huateng.framework.util;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.Logger;

public class SendMail {
	private static Logger logger = Logger.getLogger(SendMail.class);
    private List<String> fujian;
    private List<String> fujianContentType;
    private List<String> fujianFileName;
    private String to_mailAddress;// 收件人
    private String subject;// 主题
    private String content;// 信件内容
    private String from_mailAddress;// 发件人
    private String Msg;
    Multipart mm = new MimeMultipart();

    // 发送邮件

    public String sendmail() {
        try {
            Properties props = new Properties();
           // props.put("mail.smtp.host", mailconfig.getSendHost());// 存储发送邮件服务器
            props.put("mail.smtp.auth", "true");// 是否通过验证
            Session s = Session.getInstance(props);
            // s.setDebug(true);
            MimeMessage message = new MimeMessage(s);
            // --给消息对象设置发件人、收件人、主题、发信时间--
            // 设置发件人
            this.from_mailAddress = "chen_yang@huateng.com";
            message.setFrom(new InternetAddress(this.from_mailAddress));
            this.to_mailAddress= "yu_yu@huateng.com";
            InternetAddress to = new InternetAddress(this.to_mailAddress);
            message.setRecipient(Message.RecipientType.TO, to);// 设置收件人,并设置其接收类型为TO
			// sun.misc.BASE64Encoder enc = new
			// sun.misc.BASE64Encoder();
            message.setSubject("=?GB2312?B?" + "中文" + "?=");// 设置主题
            message.setSentDate(new Date());// 设置信件日期
            TianJiaNeiRong();
            //TianJiaFuJian();
            message.setContent(mm);// 把mm作为消息对象的内容
            message.saveChanges();
            Transport transport = s.getTransport("smtp");

            // 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码

            // transport.connect(mailconfig.getSendHost(),yh.getName()+"%"+yh.getDomain(),yh.getPassword());

            transport.connect("mail.huateng.com" ,"yang.chen","chenyang");        
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            Msg = "邮件已发送";
            return "success";

        } catch (Exception e){
        	logger.error(e.getMessage());
            Msg = "发送失败：" + e.getMessage();
            return "failed";
        }
    }

 

    // 添加邮件内容

    private void TianJiaNeiRong() throws MessagingException
    {
        BodyPart mdp = new MimeBodyPart();
        //mdp.setText(this.content);    
        mdp.setContent("内容","text/html;charset=\"UTF-8\"");
        mm.addBodyPart(mdp);

    }

 

    // 添加多个附件

//	private void TianJiaFuJian() throws MessagingException, IOException {
//
//		for (int i = 0; i < this.fujian.size(); i++) {
//
//			if (fujianFileName.get(i) != "") {
//
//				BodyPart mdp = new MimeBodyPart();
//
//				String path = fujian.get(i).toString();
//
//				FileDataSource fds = new FileDataSource(path);
//
//				DataHandler dh = new DataHandler(fds);
//
//				mdp.setDataHandler(dh);
//
//				// mailCaoZuo.base64encode()的作用是防止文件名乱码
//
//				mdp.setFileName(MimeUtility.encodeText(fujianFileName.get(i)));// 加上文件名将作为附件发送,否则将作为信件的文本内容;MimeUtility.encodeText()函数是把中文的文件名转换为base64编码,可以用
//
//				MimeUtility.decodeText("");
//
//				mm.addBodyPart(mdp);// 将含有附件的BodyPart加入到MimeMultipart对象中
//
//			}
//
//		}
//
//	}

 

    public void setTo_mailAddress(String to_mailAddress) {

        this.to_mailAddress = to_mailAddress;

    }

 

    public void setSubject(String subject) {
        if ("".equals(subject)) {
            subject = "无标题";
        }        
        try {
            this.subject=MimeUtility.encodeText( subject,"UTF-8","B");
                         //MimeUtility.decodeText（）邮件头的解码
        } catch (UnsupportedEncodingException e) {
        	logger.error(e.getMessage());
        } 
    }

    public void setContent(String content) {
        if ("".equals(content)) {
            content = " ";
        }    
            this.content = content;    
            //邮件体在javamail里面是不需要编码的，javamail会自动编码
    }

/*   public static void main(String args[])
   {
            SendMail a=new SendMail();
            a.sendmail();
   }*/
}

 
