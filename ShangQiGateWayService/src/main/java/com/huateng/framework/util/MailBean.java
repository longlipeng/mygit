package com.huateng.framework.util;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MailBean {
	private static Logger logger = Logger.getLogger(MailBean.class);
    private String saSMTPHost;
    private String saTitle;
    private String saMailFrom;
    private ArrayList mailTo;
    private ArrayList mailCC;
    private String saContent;

    public MailBean(){

    }

    public void sendMail() {

        try {

            // 创建 properties ，里面包含了发送邮件服务器的地址。

            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", this.getSaSMTPHost()); //"mail.smtp.host"随便叫啥都行，"192.0.0.1"必须是真实可用的。
            //mailProps.put("mail.smtp.auth", "true");
            // 创建 session

            Session mailSession = Session.getDefaultInstance(mailProps);

            // 创建 邮件的message，message对象包含了邮件众多有的部件，都是封装成了set方法去设置的

            MimeMessage message = new MimeMessage(mailSession);

            // 设置发信人
            message.setFrom(new InternetAddress(this.getSaMailFrom()));

            //收信人
            Address addrTo[] = new Address[mailTo.size()];
            for (int i = 0; i < mailTo.size(); i++){
                String tmp = (String)mailTo.get(i);
                addrTo[i] = new InternetAddress(tmp);
            }
            message.addRecipients(Message.RecipientType.TO, addrTo);

            //CC
            if (!(mailCC == null || mailCC.size() == 0)){
	            Address addrCC[] = new Address[mailCC.size()];
	            for (int i = 0; i < mailCC.size(); i++){
	                String tmp = (String)mailCC.get(i);
	                addrCC[i] = new InternetAddress(tmp);
	            }
	            message.addRecipients(Message.RecipientType.CC, addrCC);
            }


            // 邮件标题
            message.setSubject(this.getSaTitle()); //haha，恐吓人

            // 创建 Mimemultipart，这是包含多个附件是必须创建的。假如只有一个内容，没有附件，可以直接用message.setText(String str)
            //去写信的内容，比较方便。附件等于是要创建多个内容，往下看更清楚。

            MimeMultipart multi = new MimeMultipart();

            // 创建 BodyPart，主要作用是将以后创建的n个内容加入MimeMultipart.也就是可以发n个附件。我这里有2个BodyPart.

            BodyPart textBodyPart = new MimeBodyPart(); //第一个BodyPart.主要写一些一般的信件内容。

            textBodyPart.setText(this.getSaContent());

            // 压入第一个BodyPart到MimeMultipart对象中。
            multi.addBodyPart(textBodyPart);

            /*/ 创建第二个BodyPart,是一个FileDAtaSource
                   FileDataSource fds = new FileDataSource("c:\\myattachment.txt"); //必须存在的文档，否则throw异常。

            BodyPart fileBodyPart = new MimeBodyPart(); //第二个BodyPart
            fileBodyPart.setDataHandler(new DataHandler(fds)); //字符流形式装入文件
            fileBodyPart.setFileName("report.xls"); //设置文件名，可以不是原来的文件名。

                  /*
             * 以下是我用另一种方式写入附件，但不成功，附件总是0K字节。请高手点解，以上的方式我是参照demo的。
             *
             * FileInputStream in = new FileInputSteam("c:\myattachment.txt");
             * BodyPart fileBodyPart = new MimeBodyPart(in);
             * fileBodyPart.setFileName("report.xls"); //奶奶的折腾我2天，搞不定。
             **
                    /

                   //不讲了，同第一个BodyPart.
                     multi.addBodyPart(fileBodyPart);
                     // MimeMultPart作为Content加入message
             */
            message.setContent(multi);

            // 所有以上的工作必须保存。
            message.saveChanges();
            Transport.send(message);
        } catch (Exception exc) {
            logger.error(exc.getMessage());
        }
    }	

    public String getSaContent() {
        return saContent;
    }

    public String getSaMailFrom() {
        return saMailFrom;
    }

    public String getSaSMTPHost() {
        return saSMTPHost;
    }

    public String getSaTitle() {
        return saTitle;
    }

    public ArrayList getMailCC() {
        return mailCC;
    }

    public ArrayList getMailTo() {
        return mailTo;
    }

    public void setSaContent(String saContent) {
        this.saContent = saContent;
    }

    public void setSaMailFrom(String saMailFrom) {
        this.saMailFrom = saMailFrom;
    }

    public void setSaSMTPHost(String saSMTPHost) {
        this.saSMTPHost = saSMTPHost;
    }

    public void setSaTitle(String saTitle) {
        this.saTitle = saTitle;
    }

    public void setMailCC(ArrayList mailCC) {
        this.mailCC = mailCC;
    }

    public void setMailTo(ArrayList mailTo) {
        this.mailTo = mailTo;
    }

    public String toString(){
        String sep = "\r\n";
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName()).append(sep);
        sb.append("[").append("saSMTPHost").append(" = ").append(saSMTPHost).append("]").append(sep);
        sb.append("[").append("saTitle").append(" = ").append(saTitle).append("]").append(sep);
        sb.append("[").append("saMailFrom").append(" = ").append(saMailFrom).append("]").append(sep);
        if (!(mailTo == null || mailTo.size() == 0)){
	        for (int i = 0; i < mailTo.size(); i++){
	            sb.append("[").append("mailTo(").append(i).append(") = ").append(mailTo.get(i)).append("]").append(sep);
	        }
        }
        if (!(mailCC == null || mailCC.size() == 0)){
	        for (int i = 0; i < mailCC.size(); i++){
	            sb.append("[").append("mailCC(").append(i).append(") = ").append(mailCC.get(i)).append("]").append(sep);
	        }
        }
        sb.append("[").append("saContent").append(" = ").append(saContent).append("]").append(sep);
        return sb.toString();
    }
    public  static String correctDisplay(String temp){ 
    	try {   
    		temp = new String(temp.getBytes("ISO-8859-1"),"UTF-8");  
    	} catch (UnsupportedEncodingException e) {
    			logger.error(e.getMessage());  
    	}  
    	return temp;
    }

    
}
