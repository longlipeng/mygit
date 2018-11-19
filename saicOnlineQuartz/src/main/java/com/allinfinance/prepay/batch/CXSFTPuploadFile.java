package com.allinfinance.prepay.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderOrigCardListMapper;
import com.allinfinance.prepay.model.SellCardOrder;
import com.allinfinance.prepay.model.SellOrderOrigCardList;
import com.allinfinance.prepay.model.SellOrderOrigCardListExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.FileUtil;
import com.allinfinance.prepay.utils.SFTPUtil;

public class CXSFTPuploadFile {
	transient	private  Logger log=LoggerFactory.getLogger(CXSFTPuploadFile.class);
	@Autowired
	SellOrderMapper sellOrderMapper;
	@Autowired
	SellOrderOrigCardListMapper sellOrderOrigCardListMapper;
	String SFTPFilePath;
	String SFTPHost;
	String SFTPPort;
	String SFTPUserName;
	String SFTPPassWord;
	String SFTPLocalFilePath;
	public void sftpJob(){
		String sftpPath=SFTPFilePath;
		String localPath=SFTPLocalFilePath;
		Map<String,Object> condition=new HashMap();
		condition.put("date",DateUtil.getCurrentDateToStr().substring(0,8));
		//10000011���˼����� 10000012���˲�������      10000001��ҵ������10000002��ҵ��������  60000001��������
		condition.put("orderType","'10000011','10000012','10000001','10000002','60000001'");
		List<SellCardOrder> list= sellOrderMapper.selectSellOrder(condition);
		StringBuffer cardInfo=new StringBuffer("");
		for (SellCardOrder order : list) {
			String orderType=order.getOrderType();
			if("10000012".equals(orderType)||"10000002".equals(orderType)){
				String cardNo=order.getCardNo();
				int faceValue=Integer.parseInt(cardNo.substring(6, 8))*100;//���
				String area=cardNo.substring(cardNo.length()-3,cardNo.length()-1);//�������ɹ���������λλ������
				String name=order.getName();
				String phone=order.getPhone();
				String cardStatus="1";//�Ѽ���
				String time=order.getTime();
				String cardType="2";//������
				String product=order.getProduct();
				String cardMedium=order.getCardMedium();//������
				String userId=order.getUserId();
				if(name==null)
					name="";
				if(phone==null)
					phone="";
				if(userId==null)
					userId="";
				cardInfo.append(cardNo).append(",").append(faceValue).append(",").append(area).append(",")
				.append(name).append(",").append(phone).append(",").append(userId).append(",").append(cardStatus)
				.append(",").append(time).append(",").append(cardType).append(",").append(product).append(",")
				.append(cardMedium).append("\r\n");				
			}
			if("10000011".equals(orderType)||"10000001".equals(orderType)){
				String cardNo=order.getCardNo();
				int faceValue=0;//���
				String area=cardNo.substring(cardNo.length()-3,cardNo.length()-1);//�������ɹ���������λλ������
				String name=order.getName();
				String phone=order.getPhone();
				String cardStatus="1";//�Ѽ���
				String time=order.getTime();
				String cardType="1";//����
				String product=order.getProduct();
				String cardMedium=order.getCardMedium();//������
				String userId=order.getUserId();
				cardInfo.append(cardNo).append(",").append(faceValue).append(",").append(area).append(",")
				.append(name).append(",").append(phone).append(",").append(userId).append(",").append(cardStatus)
				.append(",").append(time).append(",").append(cardType).append(",").append(product).append(",")
				.append(cardMedium).append("\r\n");			
			}
			if("60000001".equals(orderType)){
				String cardNo=order.getCardNo();
				int faceValue=0;//���
				String area=cardNo.substring(cardNo.length()-3,cardNo.length()-1);//�������ɹ���������λλ������
				String name=order.getName();
				String phone=order.getPhone();
				String cardStatus="1";//�Ѽ���
				String time=order.getTime();
				String cardType=null; //"1";//����
				String product=order.getProduct();
				String cardMedium=order.getCardMedium();//������
				String userId=order.getUserId();
				String onymousType=order.getOnymousType();//�������� 1  3��������   2 ������
				if("1".equals(onymousType)||"3".equals(onymousType)){
					cardType="1";
				}if("2".equals(onymousType)){
					if(name==null)
						name="";
					if(phone==null)
						phone="";
					if(userId==null)
						userId="";
					cardType="2";
				}
				
				cardInfo.append(cardNo).append(",").append(faceValue).append(",").append(area).append(",")
				.append(name).append(",").append(phone).append(",").append(userId).append(",").append(cardStatus)
				.append(",").append(time).append(",").append(cardType).append(",").append(product).append(",")
				.append(cardMedium).append("\r\n");	
				SellOrderOrigCardListExample example=new SellOrderOrigCardListExample();
				example.createCriteria().andOrderIdEqualTo(order.getOrderNo());
				 List<SellOrderOrigCardList> origCardList= sellOrderOrigCardListMapper.selectByExample(example);
				 if(origCardList!=null){
					 SellOrderOrigCardList origCard= origCardList.get(0);
					  cardNo=origCard.getCardNo();
					  faceValue=0;//���
					  area=cardNo.substring(cardNo.length()-3,cardNo.length()-1);//�������ɹ���������λλ������					
					  cardStatus="0";//��ע��
						cardInfo.append(cardNo).append(",").append(faceValue).append(",").append(area).append(",")
						.append(name).append(",").append(phone).append(",").append(userId).append(",").append(cardStatus)
						.append(",").append(time).append(",").append(cardType).append(",").append(product).append(",")
						.append(cardMedium).append("\r\n");						 
				 }				 
			}
		}	
		try {
			sftpPath=sftpPath+DateUtil.getCurrentDateToStr().substring(0,8)+"/";//�ϴ�Ŀ�������ļ���
			localPath=localPath+DateUtil.getCurrentDateToStr().substring(0,8)+"/";//���ر����ļ���
			SFTPUtil sftp = new SFTPUtil(SFTPUserName,SFTPPassWord,SFTPHost,Integer.parseInt(SFTPPort)); 
			sftp.login();
			if(sftp.dirIsExist(sftpPath)){//�ж��Ƿ����ԭ�ļ��� ��������ȡ���ϲ� 
			byte[] byteFileData= sftp.download(sftpPath,"card_info.txt");//���ض���;���ļ�
			cardInfo.append(new String(byteFileData,"utf-8"));//�ϲ���һ��
			}
			FileUtil.createFile(localPath,"card_info.txt",cardInfo.toString());
			Thread.sleep(7000);
			sftp.upload(sftpPath, localPath+"card_info.txt");
			sftp.logout();
		} catch (Exception e) {
			log.error("�ϴ��ļ�ʧ��:{}",e.getMessage());
			e.printStackTrace();
		}	
	}
	public String getSFTPFilePath() {
		return SFTPFilePath;
	}


	public void setSFTPFilePath(String sFTPFilePath) {
		SFTPFilePath = sFTPFilePath;
	}


	public String getSFTPHost() {
		return SFTPHost;
	}


	public void setSFTPHost(String sFTPHost) {
		SFTPHost = sFTPHost;
	}


	public String getSFTPPort() {
		return SFTPPort;
	}


	public void setSFTPPort(String sFTPPort) {
		SFTPPort = sFTPPort;
	}


	public String getSFTPUserName() {
		return SFTPUserName;
	}


	public void setSFTPUserName(String sFTPUserName) {
		SFTPUserName = sFTPUserName;
	}


	public String getSFTPPassWord() {
		return SFTPPassWord;
	}


	public void setSFTPPassWord(String sFTPPassWord) {
		SFTPPassWord = sFTPPassWord;
	}
	public String getSFTPLocalFilePath() {
		return SFTPLocalFilePath;
	}
	public void setSFTPLocalFilePath(String sFTPLocalFilePath) {
		SFTPLocalFilePath = sFTPLocalFilePath;
	}
public static void main(String[] args) {
	System.out.println(DateUtil.getCurrentDateToStr().substring(0,8));
}
}
