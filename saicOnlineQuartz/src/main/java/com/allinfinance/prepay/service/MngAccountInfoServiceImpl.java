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
	 * 绑定人户关系 绑定卡户
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED ,rollbackFor={Exception.class,BizServiceException.class})
	public void insertAccInfoAndBinding(String id,String type) throws BizServiceException{
		// TODO Auto-generated method stub
		try {
			//开过户，跳过
			CardholderRelAccExample cardholderAccEx=new CardholderRelAccExample();
			cardholderAccEx.createCriteria().andCardhodlerIdEqualTo(id)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int count=cardholderRelAccMapper.countByExample(cardholderAccEx);
			if(count>0){
				return;
			}
			String cardNo=MakeVirtualCardNo(id);
			String accountNo=insertAccInfo(type,cardNo);
			
			
			//人户关联
			CardholderRelAcc cardholderRelAcc=new CardholderRelAcc();
			cardholderRelAcc.setAccountNo(accountNo);
			//个人账户（1个人 2企业 3 内部）
			cardholderRelAcc.setAccType(type);
			cardholderRelAcc.setCardhodlerId(id);
			cardholderRelAcc.setCreateTime(DateUtil.getCurrentTime());
			cardholderRelAcc.setCreateUser(Config.getUserId());
			cardholderRelAcc.setUpdateTime(DateUtil.getCurrentTime());
			cardholderRelAcc.setUpdateUser(Config.getUserId());
			cardholderRelAcc.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardholderRelAccMapper.insert(cardholderRelAcc);
			//卡户关联
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
			throw new BizServiceException("开户失败！");
		}
		
	}
	
	/**
	 * 生成个人账户
	 */
	@Override
	public String insertAccInfo(String accType,String cardNo) throws BizServiceException{
		try {
			//账户生成规则
			/*账户设计规范
			AccountNo 19位  

			AAAA          BBB           CCCCCCCC     0      156
			其中：
			（1）	AAAA  表示账户用途，上汽多用途0001 ，上汽单用途 0002
			（2）	BBB    表示2位顺序号+1位账户类型，顺序号初始设为01，
			后续如果CCCCCCCC用完可以将顺序号递增，1位账户类型种 1-个人账户  2-企业账户  3-内部账户
			（3）	CCCCCCCC  表示8位账户顺序号
			（4）	0 作为校验位
			（5）	156 设置为币种 人民币*/

			String sequ=commonsDAO.getNextValueOfSequence("TBL_ACCOUNT_INFO");
			StringBuffer buffer = new StringBuffer();
			buffer.append(AccountCode.account_category);
			if(accType.equals("1")){
				//个人账户
				buffer.append(AccountCode.account_personal_type);
			}else if (accType.equals("2")){
				//企业账户
				buffer.append(AccountCode.account_company_type);
			}else if (accType.equals("3")){
				//内部账户
				buffer.append(AccountCode.account_private_type);
			}else{
				throw new BizServiceException("账户类型错");
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
			throw new BizServiceException("新增账户失败！");
		}
		
		
	}
	
	/**
	 * 生成电子卡号
	 */
	@Override
	public String MakeVirtualCardNo(String id)
			throws BizServiceException {
		// TODO Auto-generated method stub
		
		try {
			// TODO Auto-generated method stub
			//卡产品查询条件，cardType介质类型为电子卡，署名类型为记名
			//（其实为了查询记名电子卡 卡产品）
			ProductExample proEx=new ProductExample();
			proEx.createCriteria().andCardTypeEqualTo("3")
			.andOnymousStatNotEqualTo("2").andDataStateEqualTo(DataBaseConstant.DATA_TYPE_YES);
			List<Product> product=productMapper.selectByExample(proEx);
			if(product==null||product.size()==0){
				throw new BizServiceException("请设置记名电子卡卡产品");
			}
			
			ProductCardBinExample proBinEx=new ProductCardBinExample();
			proBinEx.createCriteria().andProductIdEqualTo(product.get(0).getProductId());
			List<ProductCardBin> productCardBinList =productCardBinMapper.selectByExample(proBinEx);
			if (productCardBinList.size() == 0) {
			  
				throw new BizServiceException("产品：" +product.get(0).getProductId()
						+ "没有卡BIN,请为产品添加卡BIN！");
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
			throw new BizServiceException("生成电子卡号失败");
		}
		
		
		
	}

}
