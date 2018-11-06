package com.allinfinance.prepay.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.CommonsMapper;
import com.allinfinance.prepay.mapper.svc_mng.TableSerialNumberMapper;
import com.allinfinance.prepay.model.TableSerialNumber;
import com.allinfinance.prepay.model.TableSerialNumberExample;

@Repository
public class CommonDAOImpl extends SqlSessionDaoSupport implements CommonsDAO    {

	/**
	 * 推荐的批次大小，这个大小需要根据几个因素来决定：对象(dto或者Entity)的大小，相关资源锁占用情况等
	 */
	final public static int RECOMMENT_BATCH_SIZE = 500;

	private DataSourceTransactionManager transactionManager;
	@Autowired
	private  TableSerialNumberMapper tableSerialNumberMapper;
	@Autowired
	private  CommonsMapper commonsMapper;
	

	/*private TableSerialNumberDAO tableSerialNumberDAO;
	private ProductDAO productDAO;
	private LoyaltyContractDAO loyaltyContractDAO;
	private IssuerDAO issuerDAO;
	private SellerDAO sellerDAO;
	private ConsumerDAO consumerDAO;
	private MerchantServiceDAO merchantServiceDAO;
	private CustomerDAO customerDAO;
	private ShopServiceDAO shopServiceDAO;
	private PosInfoDAO posInfoDAO;
	private UserServiceDAO userServiceDAO;
	private RoleServiceDAO roleDAO;
	private ServiceDAO serviceDAO;
	private CardLayoutDAO cardLayoutDAO;
	private PackageDAO packageDAO;
	private CaclDspDAO caclDspDAO;
	private SettlePeriodRuleDAO settlePeriodRuleDAO;
	private ConsumerContractDAO consumerContractDAO;
	private SellContractDAO sellerContractDAO;*/
	private int i = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.common.dao.CommonsDAO#getNextValueOfSequence(java.lang
	 * .String)
	 */
	// public String getNextValueOfSequence(String sequenceName) {
	// return
	// (String)getSqlMapClientTemplate().queryForObject("Commons.getNextValueOfSequence",
	// sequenceName);
	// }

	/*public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public ConsumerContractDAO getConsumerContractDAO() {
		return consumerContractDAO;
	}

	public void setConsumerContractDAO(ConsumerContractDAO consumerContractDAO) {
		this.consumerContractDAO = consumerContractDAO;
	}

	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}

	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public ShopServiceDAO getShopServiceDAO() {
		return shopServiceDAO;
	}

	public void setShopServiceDAO(ShopServiceDAO shopServiceDAO) {
		this.shopServiceDAO = shopServiceDAO;
	}

	public PosInfoDAO getPosInfoDAO() {
		return posInfoDAO;
	}

	public void setPosInfoDAO(PosInfoDAO posInfoDAO) {
		this.posInfoDAO = posInfoDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public RoleServiceDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleServiceDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}

	public CardLayoutDAO getCardLayoutDAO() {
		return cardLayoutDAO;
	}

	public void setCardLayoutDAO(CardLayoutDAO cardLayoutDAO) {
		this.cardLayoutDAO = cardLayoutDAO;
	}

	public PackageDAO getPackageDAO() {
		return packageDAO;
	}

	public void setPackageDAO(PackageDAO packageDAO) {
		this.packageDAO = packageDAO;
	}

	public CaclDspDAO getCaclDspDAO() {
		return caclDspDAO;
	}

	public void setCaclDspDAO(CaclDspDAO caclDspDAO) {
		this.caclDspDAO = caclDspDAO;
	}

	public SettlePeriodRuleDAO getSettlePeriodRuleDAO() {
		return settlePeriodRuleDAO;
	}

	public void setSettlePeriodRuleDAO(SettlePeriodRuleDAO settlePeriodRuleDAO) {
		this.settlePeriodRuleDAO = settlePeriodRuleDAO;
	}

	public LoyaltyContractDAO getLoyaltyContractDAO() {
		return loyaltyContractDAO;
	}

	public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
		this.loyaltyContractDAO = loyaltyContractDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
*/	
//	public String getNextValue(String tableName)throws BizServiceException{
//		tableName=tableName.toUpperCase();
//		/*add by wanglei 数据库的tableName存的大写在此要转化为大写 end */
//			long serialNumber = (Long) (this.getSqlMapClientTemplate()
//					.queryForObject("tableSerialNumber.selectSerialNumber",
//							tableName));
//			return serialNumber+"";
//			
//	}
	public String getNextValueOfSequence(String tableName)
			throws BizServiceException {

		Long serialNumber = 0L;
		try {
		/*add by wanglei 数据库的tableName存的大写在此要转化为大写 begein */
			tableName=tableName.toUpperCase();
		/*add by wanglei 数据库的tableName存的大写在此要转化为大写 end */
			/*serialNumber = (Long) (this.getSqlMapClientTemplate()
					.queryForObject("tableSerialNumber.selectSerialNumber",
							tableName));*/
			TableSerialNumberExample ex=new TableSerialNumberExample();
			ex.createCriteria().andTableNameEqualTo(tableName);
			List<TableSerialNumber> tableSerialNumbers=tableSerialNumberMapper.selectByExample(ex);
			serialNumber=Long.parseLong(tableSerialNumbers.get(0).getSerialNumber()); 
			/***
			 * 获取当前流水ID
			 */
			serialNumber++;
			TableSerialNumber tableSerialNumber = new TableSerialNumber();
			tableSerialNumber.setSerialNumber(serialNumber + "");
			tableSerialNumberMapper.updateByExampleSelective(tableSerialNumber,ex);
			return (serialNumber - 1) + "";
		} catch (Exception e) {
//			this.logger.error(e.getMessage());
			throw new BizServiceException("获取流水号失败!");
		}
	}

	public String getNextValueOfSequenceBySequence(String sequence)
			throws BizServiceException {
		Long serialNumber = 0L;
		
		try {
			Map<String, String> map = new HashMap();
			map.put("sequenceName", sequence);
			serialNumber =commonsMapper.getNextValueOfSequence(map);
			return serialNumber.toString();
		} catch (Exception e) {
			throw new BizServiceException("获取流水号失败!");
		}
		
	}
	public int batchInsert(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlSession().insert(statementId,parameters);
		return result.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.common.dao.CommonsDAO#batchUpdate(java.lang.String,
	 * java.util.List)
	 */
	/*@SuppressWarnings("unchecked")
	public int batchUpdate(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.update(statementId, it.next());
						}
						int result = executor.executeBatch();
						return new Integer(result);
					}
				});
		return result.intValue();
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.common.dao.CommonsDAO#batchInsert(java.lang.String,
	 * java.util.List)
	 */
	/*@SuppressWarnings("unchecked")
	public int batchInsert(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.insert(statementId, it.next());
						}
						int result = executor.executeBatch();
						return new Integer(result);
					}
				});
		return result.intValue();
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.common.dao.CommonsDAO#batchDelete(java.lang.String,
	 * java.util.List)
	 */
	/*@SuppressWarnings("unchecked")
	public int batchDelete(final String statementId, final List parameters) {
		Integer result = (Integer) this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (Iterator it = parameters.iterator(); it.hasNext();) {
							executor.delete(statementId, it.next());
						}
						int result = executor.executeBatch();
						return new Integer(result);
					}
				});
		return result.intValue();
	}*/

//	public String queryCurrentDate() {
//		
//		String currentDate = String.valueOf((Long) this
//				.getSqlMapClientTemplate().queryForObject(
//						"BAT_CRTL.selectCurrentDate"));
//
//		return currentDate;
//	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	

	/*public int isDeleteIssuer(IssuerDTO issuerDTO) throws BizServiceException {
		i = 1;
		// 判断是否创建产品 或代发卡产品
		try {
			ProductExample productExample = new ProductExample();
			productExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			ProductExample.Criteria pCriteria = productExample.createCriteria();
			pCriteria.andProductDefineIssuerEqualTo(issuerDTO.getEntityId());
			productExample.or(pCriteria);
			List<Product> products = productDAO.selectByExample(productExample);
			if (null != products && products.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "存在产品信息，不能修改状态或删除");
			}
			// 判断是否有代发卡合同
			LoyaltyContractExample loyaltyContractExample = new LoyaltyContractExample();
			loyaltyContractExample.createCriteria().andContractBuyerEqualTo(
					issuerDTO.getEntityId());
			LoyaltyContractExample.Criteria lCriteria = loyaltyContractExample
					.createCriteria();
			lCriteria.andContractSellerEqualTo(issuerDTO.getEntityId());
			loyaltyContractExample.or(lCriteria);
			List<LoyaltyContract> loyaltyContracts = loyaltyContractDAO
					.selectByExample(loyaltyContractExample);
			if (null != loyaltyContracts && loyaltyContracts.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "存在代发卡合同，不能修改状态或删除");
			}
			// 判断是否定义了下级发卡机构
			IssuerExample issuerExample = new IssuerExample();
			issuerExample.createCriteria().andFatherEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Issuer> issuers = issuerDAO.selectByExample(issuerExample);
			if (null != issuers && issuers.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "定义了下级发卡机构，不能修改状态或删除");
			}

			// 判断是否定义了下级收单机构
			this.consumer(issuerDTO.getEntityId());
			// 判断是否定义了下级营销机构
			this.seller(issuerDTO.getEntityId());
			// 服务
			ServiceExample serviceExample = new ServiceExample();
			serviceExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Service> services = serviceDAO.selectByExample(serviceExample);
			if (null != services && services.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "定义了帐户信息，不能修改状态或删除");
			}
			// 卡面
			CardLayoutExample cardLayoutExample = new CardLayoutExample();
			cardLayoutExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<CardLayout> cardLayouts = cardLayoutDAO
					.selectByExampleWithBLOBs(cardLayoutExample);
			if (null != cardLayouts && cardLayouts.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "定义了卡面信息，不能修改状态或删除");
			}
			// 包
			PackageExample packageExample = new PackageExample();
			packageExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Package> packages = packageDAO.selectByExample(packageExample);
			if (null != packages && packages.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "定义了包信息，不能修改状态或删除");
			}
			this.issystem(issuerDTO.getEntityId());
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}
		return i;
	}*/

	/*public int isDeleteConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		i = 1;
		// 是否跟上级签订了合同
		try {
			ConsumerContractExample consumerContractExample = new ConsumerContractExample();
			consumerContractExample.createCriteria().andContractBuyerEqualTo(
					consumerDTO.getEntityId());
			ConsumerContractExample.Criteria conCriteria = consumerContractExample
					.createCriteria();
			conCriteria.andContractSellerEqualTo(consumerDTO.getEntityId());
			consumerContractExample.or(conCriteria);
			List<ConsumerContract> consumerContracts = consumerContractDAO
					.selectByExample(consumerContractExample);
			if (null != consumerContracts && consumerContracts.size() > 0) {
				i = 0;
				throw new BizServiceException(consumerDTO.getEntityId()
						+ "与机构签订了合同，不能修改状态或删除");
			}
			// 下级收单
			this.consumer(consumerDTO.getEntityId());
			// 商户
			this.merchant(consumerDTO.getEntityId());
			this.issystem(consumerDTO.getEntityId());
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}
		return i;
	}

	public int isDeleteSeller(SellerDTO sellerDTO) throws BizServiceException {
		i = 1;
		// 判断是否跟上级或下级机构签订了合同，需判断合同的状态和有效期
		try {
			SellContractExample sellContractExample = new SellContractExample();
			sellContractExample.createCriteria().andContractBuyerEqualTo(
					sellerDTO.getEntityId()).andContractStateEqualTo(DataBaseConstant.CONTRACT_STATE_ACTIVE)
					.andExpiryDateGreaterThanOrEqualTo(DateUtil.date2String(DateUtil.getCurrentDate()))
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
//			SellContractExample.Criteria sCriteria = sellContractExample.createCriteria();
//			sCriteria.andContractSellerEqualTo(sellerDTO.getEntityId())
//					.andContractStateEqualTo(DataBaseConstant.CONTRACT_STATE_ACTIVE)
//					.andExpiryDateGreaterThanOrEqualTo(DateUtil.date2String(DateUtil.getCurrentDate()))
//					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
//			sellContractExample.or(sCriteria);
			List<SellContract> sellContracts = sellerContractDAO
					.selectByExample(sellContractExample);
			if (null != sellContracts && sellContracts.size() > 0) {
				i = 0;
				throw new BizServiceException("机构:" + sellerDTO.getEntityId()	+ "与上级机构签订了合同,不能修改状态或删除");
			}
			// 判断是否定义了下级营销机构，需判断下级机构的状态
			this.seller(sellerDTO.getEntityId());
			// 判断是否定义了客户信息，需判断客户的状态
			CustomerExample customerExample = new CustomerExample();
			customerExample.createCriteria().andFatherEntityIdEqualTo(
					sellerDTO.getEntityId()).andCustomerNameNotEqualTo("散户")
					.andCusStateEqualTo(DataBaseConstant.CUST_STATE_ACTIVE)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Customer> customers = customerDAO
					.selectByExample(customerExample);
			if (null != customers && customers.size() > 0) {
				i = 0;
				throw new BizServiceException(sellerDTO.getEntityId() + "定义了客户信息,不能修改状态或删除");
			}
			// 判断是否定义了操作员和角色信息，需判断操作员的状态和角色状态
			// this.issystem(sellerDTO.getEntityId());
		} catch (BizServiceException b) {
			i = 0;
			throw b;
		} catch (Exception e) {
			i = 0;
			this.logger.error(e.getMessage());
		}

		return i;
	}

	public int isDeleteMerchant(MerchantDTO merchantDTO)
			throws BizServiceException {
		try {
			i = 1;
			ConsumerContractExample consumerContractExample = new ConsumerContractExample();
			consumerContractExample.createCriteria().andContractBuyerEqualTo(
					merchantDTO.getEntityId());
			ConsumerContractExample.Criteria conCriteria = consumerContractExample
					.createCriteria();
			conCriteria.andContractSellerEqualTo(merchantDTO.getEntityId());
			consumerContractExample.or(conCriteria);
			List<ConsumerContract> consumerContracts = consumerContractDAO
					.selectByExample(consumerContractExample);
			if (null != consumerContracts && consumerContracts.size() > 0) {
				i = 0;
				throw new BizServiceException(merchantDTO.getEntityId()
						+ "与上级机构签订了合同，不能修改状态或删除");
			}
			this.shop(merchantDTO.getEntityId());
			this.posInfo(merchantDTO.getEntityId());
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}
		return i;
	}

	public void consumer(String id) throws BizServiceException {
		// 判断是否定义了下级收单机构
		try {
			ConsumerExample consumerExample = new ConsumerExample();
			consumerExample.createCriteria().andFatherEntityIdEqualTo(id);
			List<Consumer> consumers = consumerDAO
					.selectByExample(consumerExample);
			if (null != consumers && consumers.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "定义了下级收单机构,不能修改状态或删除");
			}
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}

	}

	public void seller(String id) throws BizServiceException {
		// 判断是否定义了下级营销机构
		try {
			SellerExample sellerExample = new SellerExample();
			sellerExample.createCriteria().andFatherEntityIdEqualTo(id)
				.andSellerStateEqualTo(DataBaseConstant.SELLER_STATE_ACTIVE)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Seller> sellers = sellerDAO.selectByExample(sellerExample);
			if (null != sellers && sellers.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "定义了下级营销机构,不能修改状态或删除");
			}
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}
	}

	public void merchant(String id) throws BizServiceException {
		// 商户
		try {
			MerchantExample merchantExample = new MerchantExample();
			merchantExample.createCriteria().andFatherEntityIdEqualTo(id);
			List<Merchant> merchants = merchantServiceDAO
					.selectByExample(merchantExample);
			if (null != merchants && merchants.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "定义了商户信息,不能修改状态或删除");
			}
		} catch (BizServiceException b) {
			i = 0;
			throw b;
			
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());

		}
	}

	public void shop(String id) throws BizServiceException {
		// 门店
		ShopExample shopExample = new ShopExample();
		shopExample.createCriteria().andConsumerIdEqualTo(id);
		ShopExample.Criteria shCriteria = shopExample.createCriteria();
		shCriteria.andEntityIdEqualTo(id);
		shopExample.or(shCriteria);
		List<Shop> shops = shopServiceDAO.selectByExample(shopExample);
		if (null != shops && shops.size() > 0) {
			i = 0;
			throw new BizServiceException(id + "定义了门店信息，不能修改状态或删除");
		}
	}

	public void posInfo(String id) throws BizServiceException {
		// 终端
		PosInfoExample posInfoExample = new PosInfoExample();
		posInfoExample.createCriteria().andMchntIdEqualTo(id);
		List<PosInfo> posInfos = posInfoDAO.selectByExample(posInfoExample);
		if (null != posInfos && posInfos.size() > 0) {
			i = 0;
			throw new BizServiceException(id + "定义了终端信息，不能修改状态或删除");
		}
	}

	*//**
	 * 通过流水号和渠道标志查询退货申请信息
	 *//*
	public RefundDTO selectBySeqIdFlag(RefundDTO refundDTO) throws BizServiceException{
		refundDTO = (RefundDTO)this.getSqlMapClientTemplate()
		.queryForObject("REFUND.selectBySeqIdFlag", refundDTO);
		return refundDTO;
	}
	
	//更新WEB退货审核状态
	public int updateHsaRefundState(RefundDTO refundDTO) throws BizServiceException{
		int a =this.getSqlMapClientTemplate()
		.update("REFUND.updateHsaRefundState", refundDTO);
		return a;
	}
	
	//更新pos退货审核状态
	public int updateTxnRefundState(RefundDTO refundDTO) throws BizServiceException{
		int a =this.getSqlMapClientTemplate()
		.update("REFUND.updateTxnRefundState", refundDTO);
		return a;
	}
	
	public void issystem(String id) throws BizServiceException {
		// 判断是否定义了用户
		try {
			UserExample userExample = new UserExample();
			userExample.createCriteria().andEntityIdEqualTo(id)
				.andUserStateEqualTo(DataBaseConstant.USER_STATE_ACTIVE)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<User> users = userServiceDAO.selectByExample(userExample);
			if (null != users && users.size() > 1) {
				i = 0;
				throw new BizServiceException(id + "定义了用户信息,不能修改状态或删除");
			}
			// 角色
			RoleExample roleExample = new RoleExample();
			roleExample.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Role> roles = roleDAO.selectByExample(roleExample);
			if (null != roles && roles.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "定义了角色信息,不能修改状态或删除");
			}
//			// 计算规则
//			CaclDspExample caclDspExample = new CaclDspExample();
//			caclDspExample.createCriteria().andRecUpdUsrIdEqualTo(id);
//			List<CaclDsp> caclDsps = caclDspDAO.selectByExample(caclDspExample);
//			if (null != caclDsps && caclDsps.size() > 0) {
//				i = 0;
//				throw new BizServiceException(id + "定义了计算规则信息,不能修改状态或删除");
//			}
//			// 计算周期
//			SettlePeriodRuleExample settlePeriodRuleExample = new SettlePeriodRuleExample();
//			settlePeriodRuleExample.createCriteria().andEntityIdEqualTo(id);
//			List<SettlePeriodRule> settlePeriodRules = settlePeriodRuleDAO
//					.selectByExample(settlePeriodRuleExample);
//			if (null != settlePeriodRules && settlePeriodRules.size() > 0) {
//				i = 0;
//				throw new BizServiceException(id + "定义了计算周期信息,不能修改状态或删除");
//			}
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}

	}

	*//** 查询用户详细信息 **//*
	public MemberQueryDTO selectMemberDetailInf(MemberQueryDTO dto)
			throws BizServiceException {
		MemberQueryDTO memberQueryDTO;
		memberQueryDTO = (MemberQueryDTO) this.getSqlMapClientTemplate()
				.queryForObject("MEMBER_INF.selectMemberDetail", dto);
		return memberQueryDTO;
	}

	*//** 查询用户关联的所有卡号 **//*
	@SuppressWarnings("unchecked")
	public List<MemberQueryDTO> selectMemberRltCard(MemberQueryDTO dto)
			throws BizServiceException {
		List<MemberQueryDTO> memberQueryDTOs;
		memberQueryDTOs = (List<MemberQueryDTO>) this.getSqlMapClientTemplate()
				.queryForList("MEMBER_INF.selectMemberRltCard", dto);
		return memberQueryDTOs;
	}

	*//** 查询服务合同信息 **//*
	public List<AccTypeContractDTO> selectAccTypeContract(
			String consumerContractId) {
		List<AccTypeContractDTO> accDTOList = this.getSqlMapClientTemplate()
				.queryForList(
						"ACCTYPE_CONTRACT.selectAccTypeByConsumerContraId",
						consumerContractId);
		return accDTOList;

	}

	*//** 查询卡BIN列表 **//*
	public List<posParameterValueDTO> queryCardBinList(
			posParameterValueDTO posParameterValueDTO)
			throws BizServiceException {
		List<posParameterValueDTO> cardBinList = this.getSqlMapClientTemplate()
				.queryForList("term_parameter.queryCardBinList",
						posParameterValueDTO);
		return cardBinList;

	}

	*//** 通过卡号查询产品信息 **//*
	public PageDataDTO selectProductInfByCardNo(
			CardManagementDTO cardManagementDTO) {
		PageDataDTO pageDataDTO = new PageDataDTO();
		pageDataDTO = pageQueryDAO.query("PRODUCT.selectProductInf",
				cardManagementDTO);
		return pageDataDTO;

	}
//	*/
//	/**
//	 * 通用查询方法
//	 */
//	public List<?> queryForList(final String statementName, final Object parameterObject){
//		List<?> result = this.getSqlMapClientTemplate().queryForList(statementName,parameterObject);
//		return result;	
//	}

	/*@Override
	public void updateCardInfo(SellOrderCardList sellOrderCardList) throws BizServiceException {
		this.getSqlMapClientTemplate().update("RANSOM.updateCardInfo", sellOrderCardList);
		
	}

	@Override
	public void updateCardList(SellOrderCardList  SellOrderCardList) throws BizServiceException {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update("TB_SELL_ORDER_CARD_LIST.updateCardListStateByCardNo", SellOrderCardList);
	}

	@Override
	public String getLastestOrderId(SellOrderCardList  SellOrderCardList) throws BizServiceException {
		List<SellOrderCardList> queryForList =(List<SellOrderCardList>) this.getSqlMapClientTemplate().queryForList("TB_SELL_ORDER_CARD_LIST.getAllOrderIdBycardNo",SellOrderCardList);
		String latestOrderId =null;
		if(queryForList!=null&&queryForList.size()>0){
			latestOrderId=queryForList.get(0).getOrderId();
		}
		return latestOrderId;
	}*/
	
}

