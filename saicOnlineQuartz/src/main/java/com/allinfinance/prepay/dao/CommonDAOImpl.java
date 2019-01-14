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
	 * �Ƽ������δ�С�������С��Ҫ���ݼ�������������������(dto����Entity)�Ĵ�С�������Դ��ռ�������
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
//		/*add by wanglei ���ݿ��tableName��Ĵ�д�ڴ�Ҫת��Ϊ��д end */
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
		/*add by wanglei ���ݿ��tableName��Ĵ�д�ڴ�Ҫת��Ϊ��д begein */
			tableName=tableName.toUpperCase();
		/*add by wanglei ���ݿ��tableName��Ĵ�д�ڴ�Ҫת��Ϊ��д end */
			/*serialNumber = (Long) (this.getSqlMapClientTemplate()
					.queryForObject("tableSerialNumber.selectSerialNumber",
							tableName));*/
			TableSerialNumberExample ex=new TableSerialNumberExample();
			ex.createCriteria().andTableNameEqualTo(tableName);
			List<TableSerialNumber> tableSerialNumbers=tableSerialNumberMapper.selectByExample(ex);
			serialNumber=Long.parseLong(tableSerialNumbers.get(0).getSerialNumber()); 
			/***
			 * ��ȡ��ǰ��ˮID
			 */
			serialNumber++;
			TableSerialNumber tableSerialNumber = new TableSerialNumber();
			tableSerialNumber.setSerialNumber(serialNumber + "");
			tableSerialNumberMapper.updateByExampleSelective(tableSerialNumber,ex);
			return (serialNumber - 1) + "";
		} catch (Exception e) {
//			this.logger.error(e.getMessage());
			throw new BizServiceException("��ȡ��ˮ��ʧ��!");
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
			throw new BizServiceException("��ȡ��ˮ��ʧ��!");
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
		// �ж��Ƿ񴴽���Ʒ ���������Ʒ
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
						+ "���ڲ�Ʒ��Ϣ�������޸�״̬��ɾ��");
			}
			// �ж��Ƿ��д�������ͬ
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
						+ "���ڴ�������ͬ�������޸�״̬��ɾ��");
			}
			// �ж��Ƿ������¼���������
			IssuerExample issuerExample = new IssuerExample();
			issuerExample.createCriteria().andFatherEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Issuer> issuers = issuerDAO.selectByExample(issuerExample);
			if (null != issuers && issuers.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "�������¼����������������޸�״̬��ɾ��");
			}

			// �ж��Ƿ������¼��յ�����
			this.consumer(issuerDTO.getEntityId());
			// �ж��Ƿ������¼�Ӫ������
			this.seller(issuerDTO.getEntityId());
			// ����
			ServiceExample serviceExample = new ServiceExample();
			serviceExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Service> services = serviceDAO.selectByExample(serviceExample);
			if (null != services && services.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "�������ʻ���Ϣ�������޸�״̬��ɾ��");
			}
			// ����
			CardLayoutExample cardLayoutExample = new CardLayoutExample();
			cardLayoutExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<CardLayout> cardLayouts = cardLayoutDAO
					.selectByExampleWithBLOBs(cardLayoutExample);
			if (null != cardLayouts && cardLayouts.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "�����˿�����Ϣ�������޸�״̬��ɾ��");
			}
			// ��
			PackageExample packageExample = new PackageExample();
			packageExample.createCriteria().andEntityIdEqualTo(
					issuerDTO.getEntityId());
			List<Package> packages = packageDAO.selectByExample(packageExample);
			if (null != packages && packages.size() > 0) {
				i = 0;
				throw new BizServiceException(issuerDTO.getEntityId()
						+ "�����˰���Ϣ�������޸�״̬��ɾ��");
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
		// �Ƿ���ϼ�ǩ���˺�ͬ
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
						+ "�����ǩ���˺�ͬ�������޸�״̬��ɾ��");
			}
			// �¼��յ�
			this.consumer(consumerDTO.getEntityId());
			// �̻�
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
		// �ж��Ƿ���ϼ����¼�����ǩ���˺�ͬ�����жϺ�ͬ��״̬����Ч��
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
				throw new BizServiceException("����:" + sellerDTO.getEntityId()	+ "���ϼ�����ǩ���˺�ͬ,�����޸�״̬��ɾ��");
			}
			// �ж��Ƿ������¼�Ӫ�����������ж��¼�������״̬
			this.seller(sellerDTO.getEntityId());
			// �ж��Ƿ����˿ͻ���Ϣ�����жϿͻ���״̬
			CustomerExample customerExample = new CustomerExample();
			customerExample.createCriteria().andFatherEntityIdEqualTo(
					sellerDTO.getEntityId()).andCustomerNameNotEqualTo("ɢ��")
					.andCusStateEqualTo(DataBaseConstant.CUST_STATE_ACTIVE)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Customer> customers = customerDAO
					.selectByExample(customerExample);
			if (null != customers && customers.size() > 0) {
				i = 0;
				throw new BizServiceException(sellerDTO.getEntityId() + "�����˿ͻ���Ϣ,�����޸�״̬��ɾ��");
			}
			// �ж��Ƿ����˲���Ա�ͽ�ɫ��Ϣ�����жϲ���Ա��״̬�ͽ�ɫ״̬
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
						+ "���ϼ�����ǩ���˺�ͬ�������޸�״̬��ɾ��");
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
		// �ж��Ƿ������¼��յ�����
		try {
			ConsumerExample consumerExample = new ConsumerExample();
			consumerExample.createCriteria().andFatherEntityIdEqualTo(id);
			List<Consumer> consumers = consumerDAO
					.selectByExample(consumerExample);
			if (null != consumers && consumers.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "�������¼��յ�����,�����޸�״̬��ɾ��");
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
		// �ж��Ƿ������¼�Ӫ������
		try {
			SellerExample sellerExample = new SellerExample();
			sellerExample.createCriteria().andFatherEntityIdEqualTo(id)
				.andSellerStateEqualTo(DataBaseConstant.SELLER_STATE_ACTIVE)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Seller> sellers = sellerDAO.selectByExample(sellerExample);
			if (null != sellers && sellers.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "�������¼�Ӫ������,�����޸�״̬��ɾ��");
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
		// �̻�
		try {
			MerchantExample merchantExample = new MerchantExample();
			merchantExample.createCriteria().andFatherEntityIdEqualTo(id);
			List<Merchant> merchants = merchantServiceDAO
					.selectByExample(merchantExample);
			if (null != merchants && merchants.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "�������̻���Ϣ,�����޸�״̬��ɾ��");
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
		// �ŵ�
		ShopExample shopExample = new ShopExample();
		shopExample.createCriteria().andConsumerIdEqualTo(id);
		ShopExample.Criteria shCriteria = shopExample.createCriteria();
		shCriteria.andEntityIdEqualTo(id);
		shopExample.or(shCriteria);
		List<Shop> shops = shopServiceDAO.selectByExample(shopExample);
		if (null != shops && shops.size() > 0) {
			i = 0;
			throw new BizServiceException(id + "�������ŵ���Ϣ�������޸�״̬��ɾ��");
		}
	}

	public void posInfo(String id) throws BizServiceException {
		// �ն�
		PosInfoExample posInfoExample = new PosInfoExample();
		posInfoExample.createCriteria().andMchntIdEqualTo(id);
		List<PosInfo> posInfos = posInfoDAO.selectByExample(posInfoExample);
		if (null != posInfos && posInfos.size() > 0) {
			i = 0;
			throw new BizServiceException(id + "�������ն���Ϣ�������޸�״̬��ɾ��");
		}
	}

	*//**
	 * ͨ����ˮ�ź�������־��ѯ�˻�������Ϣ
	 *//*
	public RefundDTO selectBySeqIdFlag(RefundDTO refundDTO) throws BizServiceException{
		refundDTO = (RefundDTO)this.getSqlMapClientTemplate()
		.queryForObject("REFUND.selectBySeqIdFlag", refundDTO);
		return refundDTO;
	}
	
	//����WEB�˻����״̬
	public int updateHsaRefundState(RefundDTO refundDTO) throws BizServiceException{
		int a =this.getSqlMapClientTemplate()
		.update("REFUND.updateHsaRefundState", refundDTO);
		return a;
	}
	
	//����pos�˻����״̬
	public int updateTxnRefundState(RefundDTO refundDTO) throws BizServiceException{
		int a =this.getSqlMapClientTemplate()
		.update("REFUND.updateTxnRefundState", refundDTO);
		return a;
	}
	
	public void issystem(String id) throws BizServiceException {
		// �ж��Ƿ������û�
		try {
			UserExample userExample = new UserExample();
			userExample.createCriteria().andEntityIdEqualTo(id)
				.andUserStateEqualTo(DataBaseConstant.USER_STATE_ACTIVE)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<User> users = userServiceDAO.selectByExample(userExample);
			if (null != users && users.size() > 1) {
				i = 0;
				throw new BizServiceException(id + "�������û���Ϣ,�����޸�״̬��ɾ��");
			}
			// ��ɫ
			RoleExample roleExample = new RoleExample();
			roleExample.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Role> roles = roleDAO.selectByExample(roleExample);
			if (null != roles && roles.size() > 0) {
				i = 0;
				throw new BizServiceException(id + "�����˽�ɫ��Ϣ,�����޸�״̬��ɾ��");
			}
//			// �������
//			CaclDspExample caclDspExample = new CaclDspExample();
//			caclDspExample.createCriteria().andRecUpdUsrIdEqualTo(id);
//			List<CaclDsp> caclDsps = caclDspDAO.selectByExample(caclDspExample);
//			if (null != caclDsps && caclDsps.size() > 0) {
//				i = 0;
//				throw new BizServiceException(id + "�����˼��������Ϣ,�����޸�״̬��ɾ��");
//			}
//			// ��������
//			SettlePeriodRuleExample settlePeriodRuleExample = new SettlePeriodRuleExample();
//			settlePeriodRuleExample.createCriteria().andEntityIdEqualTo(id);
//			List<SettlePeriodRule> settlePeriodRules = settlePeriodRuleDAO
//					.selectByExample(settlePeriodRuleExample);
//			if (null != settlePeriodRules && settlePeriodRules.size() > 0) {
//				i = 0;
//				throw new BizServiceException(id + "�����˼���������Ϣ,�����޸�״̬��ɾ��");
//			}
		} catch (BizServiceException b) {
			i = 0;
			
			throw b;
		} catch (Exception e) {
			i = 0;
			
			this.logger.error(e.getMessage());
		}

	}

	*//** ��ѯ�û���ϸ��Ϣ **//*
	public MemberQueryDTO selectMemberDetailInf(MemberQueryDTO dto)
			throws BizServiceException {
		MemberQueryDTO memberQueryDTO;
		memberQueryDTO = (MemberQueryDTO) this.getSqlMapClientTemplate()
				.queryForObject("MEMBER_INF.selectMemberDetail", dto);
		return memberQueryDTO;
	}

	*//** ��ѯ�û����������п��� **//*
	@SuppressWarnings("unchecked")
	public List<MemberQueryDTO> selectMemberRltCard(MemberQueryDTO dto)
			throws BizServiceException {
		List<MemberQueryDTO> memberQueryDTOs;
		memberQueryDTOs = (List<MemberQueryDTO>) this.getSqlMapClientTemplate()
				.queryForList("MEMBER_INF.selectMemberRltCard", dto);
		return memberQueryDTOs;
	}

	*//** ��ѯ�����ͬ��Ϣ **//*
	public List<AccTypeContractDTO> selectAccTypeContract(
			String consumerContractId) {
		List<AccTypeContractDTO> accDTOList = this.getSqlMapClientTemplate()
				.queryForList(
						"ACCTYPE_CONTRACT.selectAccTypeByConsumerContraId",
						consumerContractId);
		return accDTOList;

	}

	*//** ��ѯ��BIN�б� **//*
	public List<posParameterValueDTO> queryCardBinList(
			posParameterValueDTO posParameterValueDTO)
			throws BizServiceException {
		List<posParameterValueDTO> cardBinList = this.getSqlMapClientTemplate()
				.queryForList("term_parameter.queryCardBinList",
						posParameterValueDTO);
		return cardBinList;

	}

	*//** ͨ�����Ų�ѯ��Ʒ��Ϣ **//*
	public PageDataDTO selectProductInfByCardNo(
			CardManagementDTO cardManagementDTO) {
		PageDataDTO pageDataDTO = new PageDataDTO();
		pageDataDTO = pageQueryDAO.query("PRODUCT.selectProductInf",
				cardManagementDTO);
		return pageDataDTO;

	}
//	*/
//	/**
//	 * ͨ�ò�ѯ����
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

