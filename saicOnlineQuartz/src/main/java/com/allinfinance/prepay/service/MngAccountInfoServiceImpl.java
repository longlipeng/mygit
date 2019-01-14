package com.allinfinance.prepay.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardRelAccountMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardholderRelAccMapper;
import com.allinfinance.prepay.mapper.svc_mng.MngAccountInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductCardBinMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.CardRelAccount;
import com.allinfinance.prepay.model.CardholderRelAcc;
import com.allinfinance.prepay.model.CardholderRelAccExample;
import com.allinfinance.prepay.model.MngAccountInfo;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductCardBin;
import com.allinfinance.prepay.model.ProductCardBinExample;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.utils.AccountCode;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.StringUtil;

@Service
public class MngAccountInfoServiceImpl implements MngAccountInfoService {
	private static Logger logger = Logger.getLogger(MngAccountInfoServiceImpl.class);
	@Autowired
	private MngAccountInfoMapper mngAccountInfoMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductCardBinMapper productCardBinMapper;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	@Autowired
	private CardholderRelAccMapper cardholderRelAccMapper;
	@Autowired
	private CardRelAccountMapper cardRelAccountMapper;
	/**
	 * ���˻���ϵ �󶨿���
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED ,rollbackFor={Exception.class,BizServiceException.class})
	public void insertAccInfoAndBinding(String id,String type) throws BizServiceException{
		// TODO Auto-generated method stub
		try {
			//������������
			CardholderRelAccExample cardholderAccEx=new CardholderRelAccExample();
			cardholderAccEx.createCriteria().andCardhodlerIdEqualTo(id)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int count=cardholderRelAccMapper.countByExample(cardholderAccEx);
			if(count>0){
				return;
			}
			String cardNo=MakeVirtualCardNo(id);
			String accountNo=insertAccInfo(type,cardNo);
			
			
			//�˻�����
			CardholderRelAcc cardholderRelAcc=new CardholderRelAcc();
			cardholderRelAcc.setAccountNo(accountNo);
			//�����˻���1���� 2��ҵ 3 �ڲ���
			cardholderRelAcc.setAccType(type);
			cardholderRelAcc.setCardhodlerId(id);
			cardholderRelAcc.setCreateTime(DateUtil.getCurrentTime());
			cardholderRelAcc.setCreateUser(Config.getUserId());
			cardholderRelAcc.setUpdateTime(DateUtil.getCurrentTime());
			cardholderRelAcc.setUpdateUser(Config.getUserId());
			cardholderRelAcc.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardholderRelAccMapper.insert(cardholderRelAcc);
			//��������
			CardRelAccount cardRelAccount=new CardRelAccount();
			cardRelAccount.setAccountNo(accountNo);
			cardRelAccount.setAccType(type);
			cardRelAccount.setCardNo(cardNo);
			cardRelAccount.setCreateTime(DateUtil.getCurrentTime());
			cardRelAccount.setCreateUser(Config.getUserId());
			cardRelAccount.setUpdateTime(DateUtil.getCurrentTime());
			cardRelAccount.setUpdateUser(Config.getUserId());
			cardRelAccount.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardRelAccountMapper.insert(cardRelAccount);
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage());
			throw new BizServiceException("����ʧ�ܣ�");
		}
		
	}
	
	/**
	 * ���ɸ����˻�
	 */
	@Override
	public String insertAccInfo(String accType,String cardNo) throws BizServiceException{
		try {
			//�˻����ɹ���
			/*�˻���ƹ淶
			AccountNo 19λ  

			AAAA          BBB           CCCCCCCC     0      156
			���У�
			��1��	AAAA  ��ʾ�˻���;����������;0001 ����������; 0002
			��2��	BBB    ��ʾ2λ˳���+1λ�˻����ͣ�˳��ų�ʼ��Ϊ01��
			�������CCCCCCCC������Խ�˳��ŵ�����1λ�˻������� 1-�����˻�  2-��ҵ�˻�  3-�ڲ��˻�
			��3��	CCCCCCCC  ��ʾ8λ�˻�˳���
			��4��	0 ��ΪУ��λ
			��5��	156 ����Ϊ���� �����*/

			String sequ=commonsDAO.getNextValueOfSequence("TBL_ACCOUNT_INFO");
			StringBuffer buffer = new StringBuffer();
			buffer.append(AccountCode.account_category);
			if(accType.equals("1")){
				//�����˻�
				buffer.append(AccountCode.account_personal_type);
			}else if (accType.equals("2")){
				//��ҵ�˻�
				buffer.append(AccountCode.account_company_type);
			}else if (accType.equals("3")){
				//�ڲ��˻�
				buffer.append(AccountCode.account_private_type);
			}else{
				throw new BizServiceException("�˻����ʹ�");
			}			
			buffer.append(sequ);
			buffer.append("0");
			buffer.append(AccountCode.account_currency);
			String accountNo = StringUtil.generate(buffer.toString());
			MngAccountInfo info=new MngAccountInfo();
			info.setAccountNo(accountNo);
			info.setAccBal("0");
			info.setAccStat("0");
			info.setAccType(accType);
			info.setHsmAccountNo(cardNo);
			info.setRsv1(DataBaseConstant.DATA_STATE_NORMAL);
			info.setRsv2(DataBaseConstant.DATA_STATE_NORMAL);
			info.setCreateTime(DateUtil.getCurrentTime());
			info.setCreateUser(Config.getUserId());
			info.setMaxBal("500000");
			info.setUpdateTime(DateUtil.getCurrentTime());
			info.setUpdateUser(Config.getUserId());
			info.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			mngAccountInfoMapper.insert(info);
			return accountNo;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw new BizServiceException("�����˻�ʧ�ܣ�");
		}
		
		
	}
	
	/**
	 * ���ɵ��ӿ���
	 */
	@Override
	public String MakeVirtualCardNo(String id)
			throws BizServiceException {
		// TODO Auto-generated method stub
		
		try {
			// TODO Auto-generated method stub
			//����Ʒ��ѯ������cardType��������Ϊ���ӿ�����������Ϊ����
			//����ʵΪ�˲�ѯ�������ӿ� ����Ʒ��
			ProductExample proEx=new ProductExample();
			proEx.createCriteria().andCardTypeEqualTo("3")
			.andOnymousStatNotEqualTo("2").andDataStateEqualTo(DataBaseConstant.DATA_TYPE_YES);
			List<Product> product=productMapper.selectByExample(proEx);
			if(product==null||product.size()==0){
				throw new BizServiceException("�����ü������ӿ�����Ʒ");
			}
			
			ProductCardBinExample proBinEx=new ProductCardBinExample();
			proBinEx.createCriteria().andProductIdEqualTo(product.get(0).getProductId());
			List<ProductCardBin> productCardBinList =productCardBinMapper.selectByExample(proBinEx);
			if (productCardBinList.size() == 0) {
			  
				throw new BizServiceException("��Ʒ��" +product.get(0).getProductId()
						+ "û�п�BIN,��Ϊ��Ʒ��ӿ�BIN��");
			} 
			ProductCardBin productCardBin=productCardBinList.get(0);
			String cardBin = productCardBin.getCardBin();
			String serialNumber = productCardBin.getSerialNumber();
			if (StringUtils.isEmpty(serialNumber)) {
				serialNumber = "0";
			}
			Integer serialNumberAdd = new Integer(serialNumber);
			serialNumberAdd += 1;
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(cardBin);
			buffer.append(StringUtil.getFormatStr(serialNumberAdd.toString(), "0",8,true));
			String cardNo = StringUtil.generate(buffer.toString());
			ProductCardBin proCardBin=new ProductCardBin();
			proCardBin.setSerialNumber(serialNumberAdd.toString());
			productCardBinMapper.updateByExampleSelective(proCardBin, proBinEx);
			AccCardInfo accCardInfo=new AccCardInfo();
			accCardInfo.setCardNo(cardNo);
			accCardInfo.setCvvDate("20991231");
			accCardInfo.setValidDate("20991231");
			accCardInfo.setSvcCode("201");
			accCardInfo.setCvv2ErrTimes("0");
			accCardInfo.setCardStat("1");
			accCardInfo.setActDate(DateUtil.getCurrentDateStr());
			accCardInfo.setLockStat("0");
			accCardInfo.setCancelStat("0");
			accCardInfo.setLostStat("0");
			accCardInfo.setPinStat("2");
			accCardInfo.setNopinTxnAmt("0");
			accCardInfo.setPinErrTimes("0");
			accCardInfo.setProductId(product.get(0).getProductId());
			accCardInfo.setCardholderId(id);
			accCardInfo.setIssuerId(product.get(0).getEntityId());
			accCardInfo.setIssueDate(DateUtil.getCurrentDateStr());
			accCardInfo.setCreateDate(DateUtil.getCurrentDateStr());
			accCardInfo.setChangeStat("0");
			accCardInfo.setRechargeNum("99999");
			accCardInfo.setConsumNum("99999");
			accCardInfo.setFreezeStat("0");
			accCardInfo.setShortMsgInfmIn("0");
			accCardInfo.setEmailInfmIn("0");
			accCardInfo.setReclaim("0");
			accCardInfoMapper.insert(accCardInfo);
			return cardNo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw new BizServiceException("���ɵ��ӿ���ʧ��");
		}
		
		
		
	}

}
