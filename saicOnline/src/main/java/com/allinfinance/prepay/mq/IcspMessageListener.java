package com.allinfinance.prepay.mq;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.IllegalFieldDataException;
import com.allinfinance.prepay.message.MessageMinResp;
import com.allinfinance.prepay.message.MessageSyncP001Req;
import com.allinfinance.prepay.message.MessageSyncP004Req;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.prepay.utils.XmlName;
import com.allinfinance.prepay.utils.XmlUtil;

public class IcspMessageListener implements MessageListener, ApplicationContextAware
{
	private RabbitTemplate template;
	private static Logger logger = Logger.getLogger(IcspMessageListener.class);
	//private static final String region = "com.allinfinance.icService.message";
	private ApplicationContext ctx;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message)
	{
		long t1 = System.currentTimeMillis();
		long t2=0,t3=0,t4=0;
		String msg = new String(message.getBody());
		MessageProperties reqProps = message.getMessageProperties();
		MessageProperties respProp = new MessageProperties();
		respProp.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
		respProp.setCorrelationId(message.getMessageProperties().getCorrelationId());
		logger.info("�յ���Ϣ: " + msg);
		logger.debug("reply-to:" + message.getMessageProperties().getReplyTo());
		logger.debug("correlation-id:" + new String(message.getMessageProperties().getCorrelationId()));
		Message resp = new Message("".getBytes(), respProp);
		MessageMinResp minResp = new MessageMinResp();
		BasicMessage req = null;
		MessageConverter convertor = template.getMessageConverter();
		MessageSyncP001Req reqData=new MessageSyncP001Req();
		MessageSyncP004Req reqDataP004=new MessageSyncP004Req();
		try
		{
			//Ӧ����ˮ
			CommonsDAO commonsDAO = (CommonsDAO)ctx.getBean("commonDAOImpl");
			req = (BasicMessage)template.getMessageConverter().fromMessage(message);
			String transCode = req.getTXN_CODE();
			IProcessor processor = (IProcessor)ctx.getBean("sync"+transCode+"Processor");
			String RESPSEQ_NO = commonsDAO.getNextValueOfSequence("TB_MERCHANT");
			req.setRESPSEQNO(RESPSEQ_NO);
			logger.info("������ˮ: " + req.getREQSEQNO()+"<----->Ӧ����ˮ��"+req.getRESPSEQNO());
			if(processor == null)
			{
				logger.error("["+req.getRESPSEQNO()+"]"+"δ�ҵ���Ӧ���ף�" + XmlName.TRANCODE);
				resp = convertor.toMessage(req.createMinResp(new String[]{ResponseCode.INVALID_TRANSACTION,"��Ч����"}), respProp);
				template.send(reqProps.getReplyTo(), resp);
				return;
			}
			
			req.checkData();
			t2 = System.currentTimeMillis();
			
			logger.debug("["+req.getRESPSEQNO()+"]"+"stress���Ľ�����ʱ:" + (t2-t1) + "reqid=" + req.getREQSEQNO());
			BasicMessage basicMsg=(BasicMessage)template.getMessageConverter().fromMessage(message);
			basicMsg.setRESPSEQNO(RESPSEQ_NO);
			if(transCode.equals("P001")){
				reqData= (MessageSyncP001Req)basicMsg;
				if(XmlUtil.cardMap.containsKey(reqData.getCARD_NO())){
					resp =  convertor.toMessage(req.createMinResp(new String[]{ResponseCode.SUBMIT_DUPLICATE}), respProp);
					template.send(reqProps.getReplyTo(), resp);
					logger.info("["+req.getRESPSEQNO()+"]"+"���͵��Է�����Ϣ:" + new String(resp.getBody()));
					return;
				}
				XmlUtil.cardMap.put(reqData.getCARD_NO(), reqData.getRANDOM());
			}
			if(transCode.equals("P004")){
				reqDataP004= (MessageSyncP004Req)basicMsg;
				if(XmlUtil.cardMap.containsKey(reqDataP004.getOLD_CARD_NO())){
					resp =  convertor.toMessage(req.createMinResp(new String[]{ResponseCode.SUBMIT_DUPLICATE}), respProp);
					template.send(reqProps.getReplyTo(), resp);
					logger.info("["+req.getRESPSEQNO()+"]"+"���͵��Է�����Ϣ:" + new String(resp.getBody()));
					return;
				}
				XmlUtil.cardMap.put(reqDataP004.getOLD_CARD_NO(), reqDataP004.getCARD_NO());
			}
			BasicMessage result = processor.process(basicMsg);
			t3 = System.currentTimeMillis();
			logger.debug("["+req.getRESPSEQNO()+"]"+"stress���״�����ʱ:" + (t3-t2) + "reqid=" + req.getREQSEQNO());
			resp = convertor.toMessage(result, respProp);
		}
		catch (IllegalFieldDataException ifde) {
			logger.error("["+req.getRESPSEQNO()+"]"+ifde.getMessage());
			if(req != null)
				resp =  convertor.toMessage(req.createMinResp(new String[]{ResponseCode.ISNULL,ifde.getMessage()}), respProp);
			template.send(reqProps.getReplyTo(), resp);
			logger.info("["+req.getRESPSEQNO()+"]"+"���͵��Է�����Ϣ:" + new String(resp.getBody()));
			throw new AmqpRejectAndDontRequeueException(ifde.getMessage(), ifde);
		}
		catch (MessageConversionException me) {
			logger.error("["+req.getRESPSEQNO()+"]"+"���Ľ�������", me);
			if(req != null)
				resp =  convertor.toMessage(req.createMinResp(new String[]{ResponseCode.MESSAGE_FORMAT_ERROR,"���ĸ�ʽ����"}), respProp);
			else
			{
				minResp.setRESP_CODE(ResponseCode.MESSAGE_FORMAT_ERROR);
//				minResp.setRESP_TEXT("���ĸ�ʽ����");
				resp = convertor.toMessage(minResp, respProp);
			}
			logger.info("["+req.getRESPSEQNO()+"]"+"���͵��Է�����Ϣ:" + new String(resp.getBody()));
			template.send(reqProps.getReplyTo(), resp);
			throw new AmqpRejectAndDontRequeueException(me.getMessage(), me);
		}
		catch (Exception e) {
			logger.error("["+req.getRESPSEQNO()+"]"+"���״������", e);
			if(req != null)
				resp =  convertor.toMessage(req.createMinResp(new String[]{ResponseCode.SYSTEM_ERROR,"ϵͳ����"}), respProp);
			else
			{
				minResp = req.createMinResp(new String[]{ResponseCode.SYSTEM_ERROR,"ϵͳ����"});
//				minResp.setRESP_TEXT("ϵͳ����");
				resp = convertor.toMessage(minResp, respProp);
			}
			logger.info("["+req.getRESPSEQNO()+"]"+"���͵��Է�����Ϣ:" + new String(resp.getBody()));
			template.send(reqProps.getReplyTo(), resp);
			throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
		}finally{
			if(null!=reqData.getCARD_NO()&&XmlUtil.cardMap.containsKey(reqData.getCARD_NO())){
				XmlUtil.cardMap.remove(reqData.getCARD_NO());
			}
			if(null!=reqDataP004.getOLD_CARD_NO()&&XmlUtil.cardMap.containsKey(reqDataP004.getOLD_CARD_NO())){
				XmlUtil.cardMap.remove(reqDataP004.getOLD_CARD_NO());
			}
		}
		
		logger.info("���͵��Է�����Ϣ:" + new String(resp.getBody()));
		t4 = System.currentTimeMillis();
		logger.debug("["+req.getRESPSEQNO()+"]"+"stress����������:" + (t4-t3) + "reqid=" + req.getREQSEQNO());
		template.send(reqProps.getReplyTo(), resp);
		logger.debug("["+req.getRESPSEQNO()+"]"+"stress�������ʱ��:" + (t4-t1) + "reqid=" + req.getREQSEQNO());
	}

	public RabbitTemplate getTemplate()
	{
		return template;
	}

	public void setTemplate(RabbitTemplate template)
	{
		this.template = template;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		this.ctx = applicationContext;
		
	}


}
