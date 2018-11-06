/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: EmailSenderImpl.java
 * Author:   秦伟
 * Date:     2013-4-25 下午7:40:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.email.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanqinet.redm.suning.esb.ws.EmailRequest;
import com.hanqinet.redm.suning.esb.ws.EmailService;
import com.hanqinet.redm.suning.esb.ws.EmailServiceProxy;
import com.hanqinet.redm.suning.esb.ws.MbfAddEmailReqBody;
import com.hanqinet.redm.suning.esb.ws.MbfReqHeader;
import com.hanqinet.redm.suning.esb.ws.MbfRequest;
import com.hanqinet.redm.suning.esb.ws.MbfResponse;
import com.suning.framework.lang.Result;
import com.suning.svc.email.EmailSender;
import com.suning.svc.email.EmailTemplate;

/**
 * 
 * 邮件服务实现
 * 
 * @author 秦伟
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmailSenderImpl implements EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

    private static final String APP_CODE = "SVC";
    private static final String OPERATION = "sendEmail";
    private static final String SERVICE_CODE = "EmailMgmt";
    // private static final String UID = "";
    private static final String PARAM_SPLITER = ",";

    // @Value("${email.username}")
    private String username;

    // @Value("${email.passwd}")
    private String passwd;

    // @Value("${email.esb.server}")
    private String esbServer;

    // @Value("${email.sender.nickname}")
    private String senderNickname;

    @Override
    public Result sendEmail(EmailTemplate template, String receiver, String params) {
        EmailServiceProxy proxy;

        proxy = new EmailServiceProxy();
        proxy.setEndpoint(esbServer);
        EmailService emailService = proxy.getEmailService();
        MbfRequest mbfRequest = new MbfRequest();

        MbfReqHeader mbHeader = new MbfReqHeader();
        mbHeader.setAppCode(APP_CODE);
        mbHeader.setOperation(OPERATION);
        mbHeader.setServiceCode(SERVICE_CODE);
        String uuid = generateUUID();
        mbHeader.setUId(uuid);

        MbfAddEmailReqBody mbfBody = new MbfAddEmailReqBody();
        EmailRequest request = new EmailRequest();
        request.setData(params);
        request.setDataSplit(PARAM_SPLITER);
        request.setEmail(receiver);
        // request.setEmailTag("AAAAAAAAAAAAA");
        // request.setEncode(false);
        request.setGroupId(1);
        request.setPriority(1);

        request.setFromNickname(senderNickname);
        request.setNickname(receiver);
        request.setTemplateId(template.getId());
        request.setTest(false);
        request.setWsPwd(passwd);
        request.setWsUser(username);

        mbfBody.setRequest(request);
        mbfRequest.setMbfHeader(mbHeader);
        mbfRequest.setMbfBody(mbfBody);

        Result result = new Result();
        try {
            MbfResponse mbfResponse = emailService.addEmail(mbfRequest);
            // logger.info(mbfResponse.getMbfHeader().getServiceResponse().getCode());
            LOGGER.info(uuid + " Response : response.success = " + mbfResponse.getMbfBody().getResponse().isSuccess()
                    + ", response.msg = " + mbfResponse.getMbfBody().getResponse().getMsg() + ", response.emailId = "
                    + mbfResponse.getMbfBody().getResponse().getEmailId());
            result.setSuccess(mbfResponse.getMbfBody().getResponse().isSuccess());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result.setSuccess(false);
        }
        // System.out.println("email send completely!");
        return result;
    }

    /**
     * 
     * 生成UUID（小写48位）
     * 
     * @return 48位小写UUID
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String generateUUID() {
        // 拼接2个UUID
        String str = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        // 去除连接符后截取48位
        return str.replace("-", "").substring(0, 48);
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd the passwd to set
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * @return the esbServer
     */
    public String getEsbServer() {
        return esbServer;
    }

    /**
     * @param esbServer the esbServer to set
     */
    public void setEsbServer(String esbServer) {
        this.esbServer = esbServer;
    }

    /**
     * @return the senderNickname
     */
    public String getSenderNickname() {
        return senderNickname;
    }

    /**
     * @param senderNickname the senderNickname to set
     */
    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

}
