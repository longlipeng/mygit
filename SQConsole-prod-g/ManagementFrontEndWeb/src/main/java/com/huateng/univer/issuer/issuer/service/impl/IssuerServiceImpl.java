package com.huateng.univer.issuer.issuer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardserialnumber.CardSerialNumberDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerQueryDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardSerialNumberDAO;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.DeliveryContactDAO;
import com.huateng.framework.ibatis.dao.EntityContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.EntityDepartmentDAO;
import com.huateng.framework.ibatis.dao.EntityInvoiceAddressDAO;
import com.huateng.framework.ibatis.dao.InsInfDAO;
import com.huateng.framework.ibatis.dao.InvoiceCompanyDAO;
import com.huateng.framework.ibatis.dao.IssueResourceDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.CardSerialNumber;
import com.huateng.framework.ibatis.model.CardSerialNumberExample;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerKey;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.DeliveryContactExample;
import com.huateng.framework.ibatis.model.EntityContact;
import com.huateng.framework.ibatis.model.EntityContactExample;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityDepartmentExample;
import com.huateng.framework.ibatis.model.EntityInvoiceAddress;
import com.huateng.framework.ibatis.model.EntityInvoiceAddressExample;
import com.huateng.framework.ibatis.model.InsInf;
import com.huateng.framework.ibatis.model.InsInfExample;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.InvoiceCompanyExample;
import com.huateng.framework.ibatis.model.IssueResource;
import com.huateng.framework.ibatis.model.IssueResourceExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.IssuerKey;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.PosParamenter;
import com.huateng.framework.ibatis.model.PosParamenterExample;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.SellerKey;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.DeliveryPointService;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.issuer.entityBaseInfo.dao.EntityBaseInfoServiceDao;
import com.huateng.univer.issuer.entityBaseInfo.service.EntityBaseInfoService;
import com.huateng.univer.issuer.issuer.service.IssuerService;
import com.huateng.univer.servicefeerule.biz.service.CaclInfService;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;
import com.huateng.univer.system.dictinfo.biz.service.EntityDictInfoService;
import com.huateng.univer.system.role.biz.service.RoleService;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.huateng.univer.system.user.biz.service.UserService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class IssuerServiceImpl implements IssuerService {

    Logger logger = Logger.getLogger(IssuerServiceImpl.class);
    /**
     * 实体基础信息service
     */
    private EntityBaseInfoService entityBaseInfoService;

    private EntityBaseInfoServiceDao entityBaseInfoServiceDAO;

    private EntityInvoiceAddressDAO invoiceAddressDAO;

    private EntityContactDAO contractDAO;

    private EntityDeliveryDAO deliveryDAO;

    private EntityDepartmentDAO departmentDAO;

    private DeliveryContactDAO deliveryContractDAO;

    private LoyaltyContractDAO loyaltyContractDAO;
    private PosParamenterDAO terParameterDAO;
    private CardSerialNumberDAO cardSerialNumberDAO;
    private EntityDictInfoService entityDictInfoService;
    private EntitySystemParameterService entitySystemParameterService;
    private UserService userService;
    private SellerDAO sellerDAO;
    private ConsumerDAO consumerDAO;
    private UserServiceDAO userServiceDAO;
    private BaseDAO baseDAO;
    private RoleService roleService;
    private RoleServiceDAO roleDAO;
    private IssueResourceDAO issueResourceDAO;
    private CaclInfService caclInfService;
    private SettlePeriodRuleService settlePeriodRuleService;
    private CustomerDAO customerDAO;
    private SellContractDAO sellerContractDAO;
    private InsInfDAO insInfDAO;

    // 实体service
    private ContactService contactService;
    private DeliveryPointService deliveryPointService;
    private DeliveryContactService deliveryContactService;
    private DepartmentService departmentService;
    private InvoiceAddressService invoiceAddressService;
    private InvoiceCompanyService invoiceCompanyService;

    public PosParamenterDAO getTerParameterDAO() {
        return terParameterDAO;
    }

    public void setTerParameterDAO(PosParamenterDAO terParameterDAO) {
        this.terParameterDAO = terParameterDAO;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public RoleServiceDAO getRoleDAO() {
        return roleDAO;
    }

    public IssueResourceDAO getIssueResourceDAO() {
        return issueResourceDAO;
    }

    public void setIssueResourceDAO(IssueResourceDAO issueResourceDAO) {
        this.issueResourceDAO = issueResourceDAO;
    }

    public void setRoleDAO(RoleServiceDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LoyaltyContractDAO getLoyaltyContractDAO() {
        return loyaltyContractDAO;
    }

    public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
        this.loyaltyContractDAO = loyaltyContractDAO;
    }

    public DeliveryContactDAO getDeliveryContractDAO() {
        return deliveryContractDAO;
    }

    public void setDeliveryContractDAO(DeliveryContactDAO deliveryContractDAO) {
        this.deliveryContractDAO = deliveryContractDAO;
    }

    public EntityDeliveryDAO getDeliveryDAO() {
        return deliveryDAO;
    }

    public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

    public EntityDepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public void setDepartmentDAO(EntityDepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    /**
     * 发卡机构DAO
     */
    private IssuerDAO issuerDAO;
    /**
     * 分页查询DAO
     */
    private PageQueryDAO pageQueryDAO;
    /**
     * 公共工具类DAO
     */
    private CommonsDAO commonsDAO;

    private InvoiceCompanyDAO invoiceCompanyDAO;

    public InvoiceCompanyDAO getInvoiceCompanyDAO() {
        return invoiceCompanyDAO;
    }

    public void setInvoiceCompanyDAO(InvoiceCompanyDAO invoiceCompanyDAO) {
        this.invoiceCompanyDAO = invoiceCompanyDAO;
    }

    public EntityBaseInfoServiceDao getEntityBaseInfoServiceDAO() {
        return entityBaseInfoServiceDAO;
    }

    public void setEntityBaseInfoServiceDAO(EntityBaseInfoServiceDao entityBaseInfoServiceDAO) {
        this.entityBaseInfoServiceDAO = entityBaseInfoServiceDAO;
    }

    public EntityBaseInfoService getEntityBaseInfoService() {
        return entityBaseInfoService;
    }

    public void setEntityBaseInfoService(EntityBaseInfoService entityBaseInfoService) {
        this.entityBaseInfoService = entityBaseInfoService;
    }

    public IssuerDAO getIssuerDAO() {
        return issuerDAO;
    }

    public void setIssuerDAO(IssuerDAO issuerDAO) {
        this.issuerDAO = issuerDAO;
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public EntityInvoiceAddressDAO getInvoiceAddressDAO() {
        return invoiceAddressDAO;
    }

    public void setInvoiceAddressDAO(EntityInvoiceAddressDAO invoiceAddressDAO) {
        this.invoiceAddressDAO = invoiceAddressDAO;
    }

    public EntityContactDAO getContractDAO() {
        return contractDAO;
    }

    public void setContractDAO(EntityContactDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    public PageDataDTO inqueryIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException {
        try {
            issuerQueryDTO.setSort("desc");
            issuerQueryDTO.setSortFieldName("entityId");
            PageDataDTO pdd = pageQueryDAO.query("ISSUER.selectIssuer", issuerQueryDTO);
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发行机构信息失败~！");
        }
    }

    /**
     * 仅用于发卡机构管理列表页面
     * */
    public PageDataDTO listIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException {
        try {
            issuerQueryDTO.setSort("desc");
            issuerQueryDTO.setSortFieldName("entityId");
            PageDataDTO pdd = pageQueryDAO.query("ISSUER.issuerList", issuerQueryDTO);
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发行机构信息失败~！");
        }
    }

    // 查询包含自身的发行机构
    public PageDataDTO inquerySelfIssuer(IssuerQueryDTO issuerQueryDTO) throws BizServiceException {
        try {
            // issuerQueryDTO.setSort("desc");
            PageDataDTO pdd = pageQueryDAO.query("ISSUER.issuerSelect", issuerQueryDTO);
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询发行机构信息失败~！");
        }
    }

    public IssuerDTO insertIssuer(IssuerDTO issuerDTO) throws BizServiceException {
        try {/*
              * IssuerExample issuerExample = new IssuerExample(); issuerExample.createCriteria().andDataStateEqualTo(
              * DataBaseConstant.DATA_STATE_NORMAL).andIssuerNameEqualTo( issuerDTO.getIssuerName()); List<Issuer>
              * issuers = issuerDAO.selectByExample(issuerExample); if (issuers != null && issuers.size() > 0) { throw
              * new BizServiceException(" 发行机构名称:" + issuerDTO.getIssuerName() + "已存在!"); }
              */
            Issuer is = new Issuer();
            ReflectionUtil.copyProperties(issuerDTO, is);
            String id = issuerDTO.getEntityId();

            /*
             * 2011-3-24,经余裕确认，发卡机构id统一为从sequence取8位 不再区分是华数机构，还是子级机构
             */
            // 如果是华数用户的话 add zengfenghua 2011-3-8
            // if("00000000".equals(issuerDTO.getDefaultEntityId())){
            // id+=commonsDAO.getNextValueOfSequence("TB_ENTITY");
            // }
           // if (issuerDTO.getEntityId() == null || issuerDTO.getEntityId().trim().equals("")) {
                /*
                 * 2011-3-24,经余裕确认，发卡机构id统一为从sequence取8位 不再区分是华数机构，还是子级机构
                 */
                // id =
                // issuerDTO.getDefaultEntityId().substring(0,4)+commonsDAO.getNextValueOfSequence("TB_ENTITY");
            /* wanglei 注释 id 为手动输入 begin*/
               // id += commonsDAO.getNextValueOfSequence("TB_ENTITY");
            /* wanglei 注释 id 为手动输入 end*/
                // 默认插入部门信息
                EntityDepartment entityDepartment = new EntityDepartment();
                entityDepartment.setEntityId(id);
                entityDepartment.setDepartmentName(issuerDTO.getIssuerName());
                entityDepartment.setCreateUser(issuerDTO.getCreateUser());
                entityDepartment.setModifyUser(entityDepartment.getCreateUser());
                entityDepartment.setDefaultFlag("1");
                entityBaseInfoService.insertDepartMentInfo(entityDepartment);

                // 默认插入联系人信息
                EntityContact entityContact = new EntityContact();
                entityContact.setEntityId(id);
                entityContact.setContactName(issuerDTO.getIssuerName());
                entityContact.setCreateUser(issuerDTO.getCreateUser());
                entityContact.setModifyUser(entityContact.getCreateUser());
                entityContact.setDefaultFlag("1");
                entityContact.setValidityFlag("1");
                entityContact.setPapersNo("1");
                entityContact.setPapersType("1");
                entityBaseInfoService.insertContactInfo(entityContact);

                // 默认插入快递点信息
                EntityDelivery entityDelivery = new EntityDelivery();
                entityDelivery.setEntityId(id);
                entityDelivery.setDeliveryName(issuerDTO.getIssuerName());
                entityDelivery.setDeliveryAddress(issuerDTO.getIssuerAddress());
                entityDelivery.setCreateUser(issuerDTO.getCreateUser());
                entityDelivery.setModifyUser(entityDelivery.getCreateUser());
                entityDelivery.setDefaultFlag("1");
                entityBaseInfoService.insertDeliveryInfo(entityDelivery);

                // 默认插入发票地址信息
                EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
                invoiceAddress.setEntityId(id);
                invoiceAddress.setInvoiceAddress(issuerDTO.getIssuerAddress());
                invoiceAddress.setAddressPostcode(issuerDTO.getIssuerPostcode());
                invoiceAddress.setCreateUser(issuerDTO.getCreateUser());
                invoiceAddress.setModifyUser(invoiceAddress.getCreateUser());
                invoiceAddress.setInvoiceRecipient(issuerDTO.getIssuerName());
                invoiceAddress.setDeliveryOption("1");
                invoiceAddress.setDefaultFlag("1");
                entityBaseInfoService.insertInvoiceAddressInfo(invoiceAddress);

                // 默认插入发票公司信息
                InvoiceCompany invoiceCompany = new InvoiceCompany();
                invoiceCompany.setEntityId(id);
                invoiceCompany.setInvoiceCompanyName(issuerDTO.getIssuerName());
                invoiceCompany.setCreateUser(issuerDTO.getCreateUser());
                invoiceCompany.setModifyUser(invoiceCompany.getCreateUser());
                invoiceCompany.setDefaultFlag("1");
                entityBaseInfoService.insertInvoiceCompanyInfo(invoiceCompany);

                // FIXME 在添加发行机构的时候，系统自动的添加一个相应的实体，之前说的需求不太明确，暂时放缓，特标记。

                // 添加实体数据字典
                entityDictInfoService.insertEntityDictInfo(id);
                // 添加实体系统参数
                if ("00000000".equals(issuerDTO.getDefaultEntityId())) {
                 entitySystemParameterService.insertEntitySysParam(id,
                issuerDTO.getLoginUserId());
                } else {
                 entitySystemParameterService.insertEntitySystemParameter(
                 id, issuerDTO.getDefaultEntityId(), issuerDTO
                .getLoginUserId());
                 }

          //  }

            // 添加初始计算规则
            caclInfService.insertInit(id);

            // 添加默认用户
            String userId = commonsDAO.getNextValueOfSequence("TB_USER");
            userService.insertUserForEntity(id, userId, issuerDTO.getUserName(), issuerDTO.getUserPassword(),
                    issuerDTO.getUserEmail(), issuerDTO.getLoginUserId());

            is.setEntityId(id);
            is.setUserId(userId);
            is.setFatherEntityId(issuerDTO.getDefaultEntityId());
            is.setCreateTime(DateUtil.getCurrentTime());
            is.setModifyUser(is.getCreateUser());
            is.setModifyTime(is.getCreateTime());
            is.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            issuerDAO.insert(is);

            // 添加机构线路数据
            InsInf insInf = new InsInf();
            if (is.getEntityId().length() < 10) {
                insInf.setInsIdCd("0" + is.getEntityId().length() + is.getEntityId());
            } else {
                insInf.setInsIdCd(is.getEntityId().length() + is.getEntityId());
            }

            insInf.setInsTp("03");// (暂默认为03 -内部机构)
            insInf.setOpenSignSt("1");// 默认为1
            insInf.setLineSt((short) 1);
            insInf.setComSvc("vcom");
            insInf.setPackSvc("Switch");
            insInf.setMaxTimeOut((short) 40);
            insInf.setMaxSafNo((short) 10);
            insInf.setMaxSafTime((short) 40);
            insInf.setInsNm(is.getIssuerName());
            insInf.setLicNo("0");
            insInf.setArtifNm("0");
            insInf.setCertifExpireDt("0");
            insInf.setRecUpdUsrId(is.getCreateUser());
            insInf.setRecCrtTs(DateUtil.getCurrentDateAndTime());
            insInf.setRecUpdTs(DateUtil.getCurrentDateAndTime());
            insInf.setRelInsIdCd(insInf.getInsIdCd());
            insInfDAO.insert(insInf);

            // 添加 周期规则初始数据
            settlePeriodRuleService.initInsert(id, userId);

            IssuerDTO issDTO = new IssuerDTO();
            ReflectionUtil.copyProperties(is, issDTO);
            if ("00000000".equals(issuerDTO.getDefaultEntityId()) && issuerDTO.getResourceIds().indexOf("40000") != -1) {
                // 营销机构添加
                Seller seller = new Seller();
                ReflectionUtil.copyProperties(is, seller);
                seller.setFatherEntityId(is.getEntityId());
                seller.setSellerName(is.getIssuerName());
                seller.setSellerCode("00");
                seller.setSellerState("1");
                seller.setBusinessAreaId("1");
                seller.setCreateUser(userId);
                seller.setCreateTime(DateUtil.getCurrentTime());
                seller.setModifyUser(userId);
                seller.setModifyTime(seller.getCreateTime());
                sellerDAO.insert(seller);
                // 添加散户客户
                // Customer customer = new Customer();
                // customer.setEntityId(commonsDAO
                // .getNextValueOfSequence("TB_ENTITY"));
                // customer.setFatherEntityId(is.getEntityId());
                // customer.setCustomerName("散户");
                // customer.setCusState("2");
                // customer.setCustomerType("3");
                // customer.setCustomerAddress("00");
                // customer.setCustomerFax("00");
                // customer.setCustomerPostcode("000000");
                // customer.setCustomerTelephone("0000");
                // customer.setSalesmanId(userId);
                // customer.setCreateTime(DateUtil.getCurrentTime());
                // customer.setCreateUser(userId);
                // customer.setModifyTime(DateUtil.getCurrentTime());
                // customer.setModifyUser(userId);
                // customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                // customerDAO.insert(customer);

                // 添加客户合同模板
                SellContract sellContract = new SellContract();
                sellContract.setSellContractId(commonsDAO.getNextValueOfSequence("TB_SELL_CONTRACT"));
                sellContract.setContractSeller(is.getEntityId());
                sellContract.setContractBuyer("0");
                sellContract.setDeliveryFee("0");
                sellContract.setContractState(DataBaseConstant.DATA_STATE_NORMAL);
                sellContract.setExpiryDate("29991231");
                sellContract.setCreateTime(DateUtil.getCurrentTime());
                sellContract.setCreateUser(userId);
                sellContract.setModifyTime(DateUtil.getCurrentTime());
                sellContract.setModifyUser(userId);
                sellContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                sellContract.setContractType(DataBaseConstant.CONTRACT_TEMPLATE);
                sellerContractDAO.insert(sellContract);
                if (issuerDTO.getResourceIds().indexOf("50000") != -1) {
                    // 添加收单机构
                    Consumer consumer = new Consumer();
                    ReflectionUtil.copyProperties(is, consumer);
                    consumer.setFatherEntityId(is.getEntityId());
                    consumer.setConsumerName(is.getIssuerName());
                    consumer.setConsumerCode("00");
                    consumer.setConsumerState("1");
                    consumer.setCreateUser(userId);
                    consumer.setCreateTime(DateUtil.getCurrentTime());
                    consumer.setModifyUser(userId);
                    consumer.setModifyTime(seller.getCreateTime());
                    consumerDAO.insert(consumer);
                    // 添加实体系统参数
                    // entitySystemParameterService.insertEntitySystemParameter(
                    // id, is.getEntityId(), userId);
                    // 添加POS公共参数
                    PosParamenter posParamenter = new PosParamenter();
                    posParamenter.setConsumerId(id);
                    terParameterDAO.insert_default(posParamenter);
                }
            }
            // 添加功能管理权限
            issuerDTO.setEntityId(id);
            issuerDTO.setUserId(userId);
            issuerDTO.setCreateTime(is.getCreateTime());
            insertResources(issuerDTO);

            return issDTO;
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }

    }

    public void insertResources(IssuerDTO issuerDTO) throws BizServiceException {
        List<String> issueResources = Arrays.asList(issuerDTO.getResourceIds().split(","));
        List<IssueResource> issueResourceList = new ArrayList<IssueResource>();
        for (String resource : issueResources) {
            IssueResource issueResource = new IssueResource();
            issueResource.setIssueId(issuerDTO.getEntityId());
            issueResource.setRank("1");
            issueResource.setResourceId(resource);
            issueResource.setCreateUser(issuerDTO.getCreateUser());
            issueResource.setCreateTime(DateUtil.getCurrentTime());
            issueResource.setModifyUser(issuerDTO.getCreateUser());
            issueResource.setModifyTime(issueResource.getCreateTime());
            issueResource.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            issueResourceList.add(issueResource);
        }
        commonsDAO.batchInsert("TB_REL_ISSUE_RESOURCE.abatorgenerated_insert", issueResourceList);
    }

    public void deleteResource(IssuerDTO issuerDTO) throws BizServiceException {
        IssueResourceExample example = new IssueResourceExample();
        example.createCriteria().andIssueIdEqualTo(issuerDTO.getEntityId()).andRankEqualTo("1");
        issueResourceDAO.deleteByExample(example);
    }

    public IssuerDTO viewIssuer(IssuerDTO issuerDTO) throws BizServiceException {
        try {
            IssuerKey key = new IssuerKey();
            key.setEntityId(issuerDTO.getEntityId());
            key.setFatherEntityId(issuerDTO.getDefaultEntityId());
            Issuer issuer = issuerDAO.selectByPrimaryKey(key);
            IssuerDTO issuerDto = new IssuerDTO();
            if (issuer != null) {
                ReflectionUtil.copyProperties(issuer, issuerDto);

                // 加载发行机构关联的基础信息
                // 部门信息
                issuerDto.setDepartmentDTO(entityBaseInfoServiceDAO.getDepartmentInfo(issuerDto));
                // 发票地址
                issuerDto.setInvoiceAddressDTO(entityBaseInfoServiceDAO.getInvoiceAddressInfo(issuerDto));
                // 发票公司
                issuerDto.setInvoiceCompanyDTO(entityBaseInfoServiceDAO.getInvoiceCompanyInfo(issuerDto));
                // 联系人
                issuerDto.setContractDTO(entityBaseInfoServiceDAO.getContractInfo(issuerDto));
                // 快递点
                issuerDto.setDeliveryPointDTO(entityBaseInfoServiceDAO.getDeliveryPointInfo(issuerDto));
                issuerDto.setBankDTOList(entityBaseInfoServiceDAO.getBankInfo(issuerDto));
                // 密钥
                // if ("00000000".equals(issuerDTO.getDefaultEntityId())) {
                // EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
                // entitySystemParameterDTO.setEntityId(issuerDTO
                // .getEntityId());
                // entitySystemParameterDTO.setParameterRole("1");
                // issuerDto
                // .setEntitySystemParameterDTOs(entityBaseInfoServiceDAO
                // .getEntitySystemParameter(entitySystemParameterDTO));
                // } else {
                issuerDto.setEntitySystemParameterDTOs(null);
                // }
                User user = userServiceDAO.selectByPrimaryKey(issuer.getUserId());
                if (user != null) {
                    issuerDto.setUserName(user.getUserName());
                    issuerDto.setUserEmail(user.getEmail());

                }
                /**
                 * add by dawn 添加卡PIN前缀
                 */

                issuerDto.setCardSerialNumberDTOList(getCardSerialNumberByIssuerId(issuerDto.getEntityId()));
            }
            // 得到此机构权限管理
            UserDTO userDTO = new UserDTO();
            List<String> flist = new ArrayList<String>();
            flist.add("0");
            flist.add("2");
            if ("00000000".equals(issuerDTO.getDefaultEntityId())) {
                flist.add("3");
                flist.add("4");
            }
            userDTO.setFunctionRoleId(flist);
            userDTO.setEntityId(issuerDTO.getDefaultEntityId());
            userDTO.setIsSaleFlage("1");
            List<ResourceDTO> resourceDTOlist = roleService.selectIssueResource(userDTO);
            // 获取已设置的权限
            userDTO.setEntityId(issuerDTO.getEntityId());
            userDTO.setIsSaleFlage("1");
            List<ResourceDTO> nresourceDTOlist = roleDAO.getIssueResourceDTOs(userDTO);
            issuerDto.setResourceDTOs(resourceDTOlist);
            issuerDto.setNresourceDTOs(nresourceDTOlist);

            return issuerDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("显示信息失败~！");
        }
    }

    /**
     * 通过实体ID 随机取一个
     * 
     * @param issuerDTO
     * @return
     * @throws BizServiceException
     */
    public IssuerDTO getIssuerByEntityId(IssuerDTO issuerDTO) throws BizServiceException {
        try {
            IssuerExample issuerExample = new IssuerExample();
            issuerExample.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                    .andEntityIdEqualTo(issuerDTO.getEntityId());
            List<Issuer> issuerList = issuerDAO.selectByExample(issuerExample);
            Issuer issuer = new Issuer();
            if (issuerList.size() > 0) {
                issuer = issuerList.get(0);
            }
            ReflectionUtil.copyProperties(issuer, issuerDTO);
            // 加载发行机构关联的基础信息
            // 部门信息
            issuerDTO.setDepartmentDTO(entityBaseInfoServiceDAO.getDepartmentInfo(issuerDTO));
            // 发票地址
            issuerDTO.setInvoiceAddressDTO(entityBaseInfoServiceDAO.getInvoiceAddressInfo(issuerDTO));
            // 发票公司
            issuerDTO.setInvoiceCompanyDTO(entityBaseInfoServiceDAO.getInvoiceCompanyInfo(issuerDTO));
            // 联系人
            issuerDTO.setContractDTO(entityBaseInfoServiceDAO.getContractInfo(issuerDTO));
            // 快递点
            issuerDTO.setDeliveryPointDTO(entityBaseInfoServiceDAO.getDeliveryPointInfo(issuerDTO));
            return issuerDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("显示信息失败~！");
        }
    }

    public void insertCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException {
        try {
            InvoiceCompany inCompany = new InvoiceCompany();
            ReflectionUtil.copyProperties(invoiceCompanyDTO, inCompany);
            entityBaseInfoService.insertInvoiceCompanyInfo(inCompany);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }
    }

    public InvoiceCompanyDTO viewCompanyInfo(InvoiceCompanyDTO dto) throws BizServiceException {
        try {
            InvoiceCompany invoiceCompany = invoiceCompanyDAO.selectByPrimaryKey(dto.getInvoiceCompanyId());
            InvoiceCompanyDTO resultDto = new InvoiceCompanyDTO();
            ReflectionUtil.copyProperties(invoiceCompany, resultDto);
            return resultDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看发票公司失败！");
        }

    }

    public void updateCompanyInfo(InvoiceCompanyDTO dto) throws BizServiceException {
        try {
            InvoiceCompany entityInvoiceCompany = new InvoiceCompany();
            ReflectionUtil.copyProperties(dto, entityInvoiceCompany);

            entityInvoiceCompany.setCreateTime(DateUtil.getCurrentTime());
            entityInvoiceCompany.setModifyTime(DateUtil.getCurrentTime());
            if ("1".equals(entityInvoiceCompany.getDefaultFlag())) {
                entityInvoiceCompany.setDefaultFlag("1");
                invoiceCompanyDAO.updateByPrimaryKeySelective(entityInvoiceCompany);
                InvoiceCompanyExample ex = new InvoiceCompanyExample();
                List<String> s = new ArrayList<String>();
                s.add(entityInvoiceCompany.getInvoiceCompanyId());
                InvoiceCompany invoiceCompany = new InvoiceCompany();
                invoiceCompany.setDefaultFlag("0");
                ex.createCriteria().andInvoiceCompanyIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                invoiceCompanyDAO.updateByExampleSelective(invoiceCompany, ex);
            } else {
                entityInvoiceCompany.setDefaultFlag("0");
                invoiceCompanyDAO.updateByPrimaryKeySelective(entityInvoiceCompany);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新发票公司失败！");
        }
    }

    public void deleteCompanyInfo(InvoiceCompanyDTO invoiceCompanyDTO) throws BizServiceException {
        try {
            List<InvoiceCompanyDTO> companyDTOs = invoiceCompanyDTO.getInvoiceCompanyDTO();
            for (InvoiceCompanyDTO cld : companyDTOs) {
                InvoiceCompany plo = new InvoiceCompany();
                plo.setInvoiceCompanyId(cld.getInvoiceCompanyId().toString());
                plo.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                invoiceCompanyDAO.updateByPrimaryKeySelective(plo);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除发票公司信息失败~！");
        }

    }

    public void insertInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException {
        try {
            EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
            ReflectionUtil.copyProperties(dto, invoiceAddress);
            invoiceAddress.setAddressPostcode(dto.getAddressPostcode());
            invoiceAddress.setInvoiceRecipient(dto.getInvoiceRecipient());
            entityBaseInfoService.insertInvoiceAddressInfo(invoiceAddress);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }
    }

    public InvoiceAddressDTO viewInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException {
        try {
            EntityInvoiceAddress invoiceAddress = invoiceAddressDAO.selectByPrimaryKey(dto.getInvoiceAddressId());
            InvoiceAddressDTO resultDto = new InvoiceAddressDTO();
            ReflectionUtil.copyProperties(invoiceAddress, resultDto);
            return resultDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看发票地址失败！");
        }

    }

    public void updateInvoiceAddress(InvoiceAddressDTO dto) throws BizServiceException {
        try {
            EntityInvoiceAddress entityInvoiceAddress = new EntityInvoiceAddress();
            ReflectionUtil.copyProperties(dto, entityInvoiceAddress);

            entityInvoiceAddress.setCreateTime(DateUtil.getCurrentTime());
            entityInvoiceAddress.setModifyTime(DateUtil.getCurrentTime());
            if ("1".equals(entityInvoiceAddress.getDefaultFlag())) {
                entityInvoiceAddress.setDefaultFlag("1");
                invoiceAddressDAO.updateByPrimaryKeySelective(entityInvoiceAddress);
                EntityInvoiceAddressExample ex = new EntityInvoiceAddressExample();
                List<String> s = new ArrayList<String>();
                s.add(entityInvoiceAddress.getInvoiceAddressId());
                EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
                invoiceAddress.setDefaultFlag("0");
                ex.createCriteria().andInvoiceAddressIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                invoiceAddressDAO.updateByExampleSelective(invoiceAddress, ex);
            } else {
                entityInvoiceAddress.setDefaultFlag("0");
                invoiceAddressDAO.updateByPrimaryKeySelective(entityInvoiceAddress);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新发票地址失败！");
        }
    }

    public void deleteAdressInfo(InvoiceAddressDTO dto) throws BizServiceException {
        try {
            List<InvoiceAddressDTO> addressDTOs = dto.getInvoiceAddressDTO();
            for (InvoiceAddressDTO cld : addressDTOs) {
                EntityInvoiceAddress plo = new EntityInvoiceAddress();
                plo.setInvoiceAddressId(cld.getInvoiceAddressId());
                plo.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                invoiceAddressDAO.updateByPrimaryKeySelective(plo);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除发票地址信息失败~！");
        }

    }

    public void insertContactInfo(ContactDTO dto) throws BizServiceException {
        try {
            EntityContact entityContact = new EntityContact();
            ReflectionUtil.copyProperties(dto, entityContact);
            entityBaseInfoService.insertContactInfo(entityContact);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }

    }

    public ContactDTO viewContactInfo(ContactDTO dto) throws BizServiceException {
        try {
            EntityContact contact = contractDAO.selectByPrimaryKey(dto.getContactId());
            ContactDTO resultDto = new ContactDTO();
            ReflectionUtil.copyProperties(contact, resultDto);
            return resultDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看联系人信息失败！");
        }

    }

    public void updateContactInfo(ContactDTO dto) throws BizServiceException {
        try {
            EntityContact entityContact = new EntityContact();
            ReflectionUtil.copyProperties(dto, entityContact);

            entityContact.setCreateTime(DateUtil.getCurrentTime());
            entityContact.setModifyTime(DateUtil.getCurrentTime());
            if ("1".equals(entityContact.getDefaultFlag())) {
                entityContact.setDefaultFlag("1");
                contractDAO.updateByPrimaryKeySelective(entityContact);
                EntityContactExample ex = new EntityContactExample();
                List<String> s = new ArrayList<String>();
                s.add(entityContact.getContactId());
                EntityContact contract = new EntityContact();
                contract.setDefaultFlag("0");
                ex.createCriteria().andContactIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                contractDAO.updateByExampleSelective(contract, ex);
            } else {
                entityContact.setDefaultFlag("0");
                contractDAO.updateByPrimaryKeySelective(entityContact);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新联系人信息失败！");
        }
    }

    public void deleteContactInfo(ContactDTO dto) throws BizServiceException {
        try {
            List<ContactDTO> contactDTOs = dto.getContactDTOList();
            for (ContactDTO cld : contactDTOs) {
                EntityContact plo = new EntityContact();
                plo.setContactId(cld.getContactId());
                plo.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                contractDAO.updateByPrimaryKeySelective(plo);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除联系人信息失败~！");
        }

    }

    public void insertDepartMentInfo(DepartmentDTO dto) throws BizServiceException {
        try {
            EntityDepartment entityDepartment = new EntityDepartment();
            ReflectionUtil.copyProperties(dto, entityDepartment);
            entityBaseInfoService.insertDepartMentInfo(entityDepartment);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }

    }

    public DepartmentDTO viewDepartMentInfo(DepartmentDTO dto) throws BizServiceException {
        try {
            EntityDepartment department = departmentDAO.selectByPrimaryKey(dto.getDepartmentId());
            DepartmentDTO resultDto = new DepartmentDTO();
            ReflectionUtil.copyProperties(department, resultDto);
            return resultDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看部门信息失败！");
        }

    }

    public void updateDepartMentInfo(DepartmentDTO dto) throws BizServiceException {
        try {
            EntityDepartment entityDepartment = new EntityDepartment();
            ReflectionUtil.copyProperties(dto, entityDepartment);
            // department.setModifyUser(dto.getLoginUserId());
            // department.setModifyTime(DateUtil.getCurrentTime());
            // //updateAccountFlag(bank);
            // department.setAccountFlag(dto.getAccountFlag());

            entityDepartment.setCreateTime(DateUtil.getCurrentTime());
            entityDepartment.setModifyTime(DateUtil.getCurrentTime());
            if ("1".equals(entityDepartment.getDefaultFlag())) {
                entityDepartment.setDefaultFlag("1");
                departmentDAO.updateByPrimaryKeySelective(entityDepartment);
                EntityDepartmentExample ex = new EntityDepartmentExample();
                List<String> s = new ArrayList<String>();
                s.add(entityDepartment.getDepartmentId());
                EntityDepartment department = new EntityDepartment();
                department.setDefaultFlag("0");
                ex.createCriteria().andDepartmentIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                departmentDAO.updateByExampleSelective(department, ex);
            } else {
                entityDepartment.setDefaultFlag("0");
                departmentDAO.updateByPrimaryKeySelective(entityDepartment);
            }

            // departmentDAO.updateByPrimaryKeySelective(department);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新部门信息失败！");
        }
    }

    /**
     * 所更新或新增的银行账户如果是默认账户，则把其他银行账户设为非默认
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public void updateDefaultFlag(EntityDepartment department) {
        // 如果提交的银行账户是设为默认的，则把已有的默认银行账户更新为非默认
        if ("1".equals(department.getDefaultFlag())) {
            department.setDefaultFlag("0");
            // departmentDAO.updateDefaultFlag(department);
        } else {
            EntityDepartmentExample be = new EntityDepartmentExample();
            be.createCriteria().andEntityIdEqualTo(department.getEntityId())
                    .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
            if (departmentDAO.countByExample(be) == 0) {
                department.setDefaultFlag("1");
            }
        }
    }

    public void deleteDepartMentInfo(DepartmentDTO dto) throws BizServiceException {
        try {
            List<DepartmentDTO> departmentDTOs = dto.getDepartmentDTO();
            for (DepartmentDTO cld : departmentDTOs) {
                EntityDepartment entityDepartment = new EntityDepartment();
                entityDepartment.setDepartmentId(cld.getDepartmentId().toString());
                entityDepartment.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                departmentDAO.updateByPrimaryKeySelective(entityDepartment);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除部门信息失败~！");
        }

    }

    public void deleteIssuerInfo(IssuerDTO issuerDTO) throws BizServiceException {
        try {
            // 如果该机构与合同关联，则不能删除
            // LoyaltyContractExample exanple=new LoyaltyContractExample();
            // exanple.createCriteria().andContractBuyerEqualTo(issuerDTO.getEntityId()).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
            // if(loyaltyContractDAO.countByExample(exanple)>0){
            // throw new BizServiceException("对不起，该发行机构跟合同关联，不能删除！");
            // }

            int i = commonsDAO.isDeleteIssuer(issuerDTO);
            if (i == 0) {
                throw new BizServiceException("删除失败");
            }

            String fathserEntityId = issuerDTO.getDefaultEntityId();
            for (IssuerDTO issuersDTO : issuerDTO.getIssuerDTO()) {
                /**
                 * 
                 * 删除用户
                 */
                UserExample userExample = new UserExample();
                User user = new User();
                user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                userExample.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                userServiceDAO.updateByExampleSelective(user, userExample);

                /**
                 * 删除跟该发行机构关联的联系人信息
                 */
                EntityContactExample ex = new EntityContactExample();
                EntityContact contact = new EntityContact();
                contact.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                ex.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                contractDAO.updateByExampleSelective(contact, ex);
                /**
                 * 删除跟该发行机构关联的快递点信息
                 */
                EntityDeliveryExample dex = new EntityDeliveryExample();
                EntityDelivery delivery = new EntityDelivery();
                delivery.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                dex.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                deliveryDAO.updateByExampleSelective(delivery, dex);
                /**
                 * 删除跟该发行机构关联的发票公司信息
                 */
                InvoiceCompanyExample cex = new InvoiceCompanyExample();
                InvoiceCompany invc = new InvoiceCompany();
                invc.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                cex.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                invoiceCompanyDAO.updateByExampleSelective(invc, cex);
                /**
                 * 删除跟该发行机构关联的发票公司地址信息
                 */
                EntityInvoiceAddressExample aex = new EntityInvoiceAddressExample();
                EntityInvoiceAddress Address = new EntityInvoiceAddress();
                Address.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                aex.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                invoiceAddressDAO.updateByExampleSelective(Address, aex);
                /**
                 * 删除跟该发行机构关联的部门信息
                 */
                EntityDepartmentExample depex = new EntityDepartmentExample();
                EntityDepartment department = new EntityDepartment();
                department.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                depex.createCriteria().andEntityIdEqualTo(issuersDTO.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                departmentDAO.updateByExampleSelective(department, depex);

                /**
                 * 删除发行机构的信息
                 */
                IssuerKey key = new IssuerKey();
                key.setEntityId(issuersDTO.getEntityId());
                key.setFatherEntityId(fathserEntityId);
                Issuer issuer = issuerDAO.selectByPrimaryKey(key);
                issuer.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                issuer.setModifyTime(DateUtil.getCurrentTime());
                issuer.setModifyUser(issuer.getModifyUser());
                // issuerDAO.deleteByPrimaryKey(issuer);
                issuerDAO.updateByPrimaryKeySelective(issuer);

                /**
                 * 删除机构线路信息
                 */
                InsInf insInf = new InsInf();
                if (issuer.getEntityId().length() < 10) {
                    insInf.setInsIdCd("0" + issuer.getEntityId().length() + issuer.getEntityId());
                } else {
                    insInf.setInsIdCd(issuer.getEntityId().length() + issuer.getEntityId());
                }
                insInf.setInsIdCd(StringUtils.blankFillString(insInf.getInsIdCd(), 13));
                InsInfExample example = new InsInfExample();
                example.createCriteria().andInsIdCdEqualTo(insInf.getInsIdCd());
                insInfDAO.deleteByExample(example);
                // 删掉权限控制
                // deleteResource(issuersDTO);
            }
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除发行机构信息失败~！");
        }

    }

    public void insertDeliveryInfo(DeliveryPointDTO dto) throws BizServiceException {
        try {
            EntityDelivery entityDelivery = new EntityDelivery();
            ReflectionUtil.copyProperties(dto, entityDelivery);
            entityDelivery.setDeliveryAddress(dto.getDeliveryAddress());
            entityDelivery.setDeliveryName(dto.getDeliveryName());
            entityDelivery.setDeliveryPostcode(dto.getDeliveryPostcode());
            entityDelivery.setDeliveryState(dto.getDeliveryState());
            entityDelivery.setDeliveryComment(dto.getDeliveryComment());
            entityBaseInfoService.insertDeliveryInfo(entityDelivery);

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }

    }

    public void deleteDeliveryInfo(DeliveryPointDTO dto) throws BizServiceException {
        try {
            List<DeliveryPointDTO> deDTOs = dto.getDeliveryPointDTOs();
            for (DeliveryPointDTO cld : deDTOs) {
                EntityDelivery entityDelivery = new EntityDelivery();
                entityDelivery.setDeliveryId(cld.getDeliveryId().toString());
                entityDelivery.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                deliveryDAO.updateByPrimaryKeySelective(entityDelivery);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除投递点信息失败~！");
        }

    }

    public DeliveryPointDTO insertDeliveryContractInfo(DeliveryPointDTO dto) throws BizServiceException {
        try {
            EntityDelivery entityDelivery = new EntityDelivery();
            ReflectionUtil.copyProperties(dto, entityDelivery);
            String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DELIVERY");
            entityDelivery.setDeliveryId(id);
            entityDelivery.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            entityDelivery.setCreateTime(DateUtil.getCurrentTime());
            entityDelivery.setModifyTime(DateUtil.getCurrentTime());
            entityDelivery.setDeliveryAddress(dto.getDeliveryAddress());
            entityDelivery.setDeliveryName(dto.getDeliveryName());
            entityDelivery.setDeliveryPostcode(dto.getDeliveryPostcode());
            entityDelivery.setDeliveryState(dto.getDeliveryState());
            entityDelivery.setDeliveryComment(dto.getDeliveryComment());

            if (entityDelivery.getDefaultFlag().equals("1")) {
                entityDelivery.setDefaultFlag("1");
                deliveryDAO.insert(entityDelivery);
                EntityDeliveryExample ex = new EntityDeliveryExample();
                List<String> s = new ArrayList<String>();
                s.add(id);
                EntityDelivery delivery = new EntityDelivery();
                delivery.setDefaultFlag("0");
                ex.createCriteria().andDeliveryIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                deliveryDAO.updateByExampleSelective(delivery, ex);
            } else {
                entityDelivery.setDefaultFlag("0");
                deliveryDAO.insert(entityDelivery);
            }

            // 查询实体信息,返回DTO
            EntityDelivery delivry = deliveryDAO.selectByPrimaryKey(id);
            DeliveryPointDTO deldto = new DeliveryPointDTO();
            deldto.setDeliveryAddress(delivry.getDeliveryAddress());
            deldto.setDeliveryName(delivry.getDeliveryName());
            deldto.setDeliveryPostcode(delivry.getDeliveryPostcode());
            deldto.setDeliveryState(delivry.getDeliveryState());
            deldto.setDefaultFlag(delivry.getDefaultFlag());
            deldto.setDeliveryComment(delivry.getDeliveryComment());
            deldto.setDeliveryId(delivry.getDeliveryId());
            return deldto;

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }
    }

    public void insertDeliveryReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException {
        try {
            DeliveryContact entityDelivery = new DeliveryContact();
            ReflectionUtil.copyProperties(dto, entityDelivery);
            entityDelivery.setDeliveryContactId(commonsDAO.getNextValueOfSequence("TB_DELIVERY_CONTACT"));
            entityDelivery.setDeliveryPointId(dto.getDeliveryPointId());
            entityDelivery.setContactPhone(dto.getContactPhone());
            entityDelivery.setDeliveryContact(dto.getDeliveryContact());
            entityDelivery.setCreateTime(DateUtil.getCurrentTime());
            entityDelivery.setModifyTime(DateUtil.getCurrentTime());
            entityDelivery.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            deliveryContractDAO.insert(entityDelivery);

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加信息失败~！");
        }

    }

    public DeliveryPointDTO queryDeliveryReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException {
        try {
            DeliveryPointDTO deldto = new DeliveryPointDTO();
            EntityDelivery delivry = (EntityDelivery) deliveryDAO.selectByPrimaryKey(dto.getDeliveryPointId()
                    .toString());
            deldto.setDeliveryAddress(delivry.getDeliveryAddress());
            deldto.setDeliveryName(delivry.getDeliveryName());
            deldto.setDeliveryPostcode(delivry.getDeliveryPostcode());
            deldto.setDeliveryState(delivry.getDeliveryState());
            deldto.setDefaultFlag(delivry.getDefaultFlag());
            deldto.setDeliveryComment(delivry.getDeliveryComment());
            deldto.setDeliveryId(delivry.getDeliveryId());
            deldto.setRecipientList(entityBaseInfoServiceDAO.getDeliveryReceptInfo(dto));

            return deldto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询信息失败~！");
        }
    }

    public void updateDeliveryReceptInfo(DeliveryPointDTO dto) throws BizServiceException {

        try {
            EntityDelivery delivry = new EntityDelivery();
            ReflectionUtil.copyProperties(dto, delivry);
            delivry.setDeliveryId(dto.getDeliveryId());
            delivry.setDeliveryAddress(dto.getDeliveryAddress());
            delivry.setDeliveryName(dto.getDeliveryName());
            delivry.setDeliveryPostcode(dto.getDeliveryPostcode());
            delivry.setDefaultFlag(dto.getDefaultFlag());
            delivry.setDeliveryState(dto.getDeliveryState());
            delivry.setDeliveryComment(dto.getDeliveryComment());
            delivry.setModifyTime(DateUtil.getCurrentTime());
            if ("1".equals(delivry.getDefaultFlag())) {
                delivry.setDefaultFlag("1");
                deliveryDAO.updateByPrimaryKeySelective(delivry);
                EntityDeliveryExample ex = new EntityDeliveryExample();
                List<String> s = new ArrayList<String>();
                s.add(delivry.getDeliveryId());
                EntityDelivery entityDelivery = new EntityDelivery();
                entityDelivery.setDefaultFlag("0");
                ex.createCriteria().andDeliveryIdNotIn(s).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                deliveryDAO.updateByExampleSelective(entityDelivery, ex);
            } else {
                delivry.setDefaultFlag("0");
                deliveryDAO.updateByPrimaryKeySelective(delivry);
            }
            // deliveryDAO.updateByPrimaryKeySelective(delivry);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("修改产品信息失败~！");
        }
    }

    public void deleteReceptInfo(DeliveryRecipientDTO dto) throws BizServiceException {
        try {
            DeliveryContact record = new DeliveryContact();
            record.setDataState(DataBaseConstant.DATA_STATE_DELETE);
            record.setModifyTime(DateUtil.getCurrentTime());
            record.setModifyUser(dto.getModifyUser());
            DeliveryContactExample example = new DeliveryContactExample();
            example.createCriteria().andDeliveryContactIdEqualTo(dto.getDeliveryContactId())
                    .andDeliveryPointIdEqualTo(dto.getDeliveryPointId())
                    .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
            deliveryContractDAO.updateByExampleSelective(record, example);

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除收件人信息失败~！");
        }
    }

    public void updateIssuerInfo(IssuerDTO dto) throws BizServiceException {
        try {
            // 如果该机构与合同关联，则不能把状态设为无效
            if (dto.getDataState().equals("0")) {
                LoyaltyContractExample exanple = new LoyaltyContractExample();
                exanple.createCriteria().andContractBuyerEqualTo(dto.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                        .andExpiryDateGreaterThan(DateUtil.getCurrentDateStr());
                if (loyaltyContractDAO.countByExample(exanple) > 0) {
                    throw new BizServiceException("该发行机构跟有效合同关联，不能把发行机构状态设为无效！");
                }
                /**
                 * 删除用户
                 */
                UserExample userExample = new UserExample();
                User user = new User();
                user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                userExample.createCriteria().andEntityIdEqualTo(dto.getEntityId())
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
                userServiceDAO.updateByExampleSelective(user, userExample);
            } else if (dto.getDataState().equals("1")) {
                /**
                 * 恢复用户
                 */
                UserExample userExample = new UserExample();
                User user = new User();
                user.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                userExample.createCriteria().andEntityIdEqualTo(dto.getEntityId());
                userServiceDAO.updateByExampleSelective(user, userExample);
            }

            Issuer issuer = new Issuer();
            ReflectionUtil.copyProperties(dto, issuer);
            issuer.setModifyTime(DateUtil.getCurrentTime());
            issuer.setFatherEntityId(dto.getDefaultEntityId());
            issuerDAO.updateByPrimaryKeySelective(issuer);
            User user = new User();
            user.setUserId(dto.getUserId());
            user.setUserName(dto.getUserName());
            user.setEmail(dto.getUserEmail());
            userServiceDAO.updateByPrimaryKeySelective(user);
            // 删除旧的权限控制
            deleteResource(dto);
            // 添加新的权限控制
            dto.setCreateUser(dto.getUserId());
            insertResources(dto);

            // 如果有营销机构权限 则添加营销机构 否则修改营销机构状态为无效
            if ("00000000".equals(dto.getDefaultEntityId()) && dto.getResourceIds().indexOf("40000") != -1) {
                // 已存在营销机构 则修改营销机构状态为有效 否则添加营销机构及散户
                insertSeller(dto);
            } else {
                Seller seller = new Seller();
                seller.setEntityId(dto.getEntityId());
                seller.setFatherEntityId(dto.getEntityId());
                seller.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                sellerDAO.updateByPrimaryKeySelective(seller);
            }
            // 如果有收单机构权限 则添加收单机构 否则修改收单机构状态为无效
            if ("00000000".equals(dto.getDefaultEntityId()) && dto.getResourceIds().indexOf("50000") != -1) {
                // 已存在收单机构 则修改收单机构状态为有效 否则添加收单机构及POS参数
                insertConsumer(dto);
            } else {
                Consumer consumer = new Consumer();
                consumer.setEntityId(dto.getEntityId());
                consumer.setFatherEntityId(dto.getEntityId());
                consumer.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                consumerDAO.updateByPrimaryKeySelective(consumer);
            }

        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新发行机构信息失败~！");
        }

    }

    private void insertSeller(IssuerDTO dto) throws BizServiceException {
        try {
            // 营销机构添加
            SellerKey sk = new SellerKey();
            sk.setEntityId(dto.getEntityId());
            sk.setFatherEntityId(dto.getEntityId());
            if (sellerDAO.selectByPrimaryKey(sk) == null) {
                Seller seller = new Seller();
                ReflectionUtil.copyProperties(dto, seller);
                seller.setFatherEntityId(dto.getEntityId());
                seller.setSellerName(dto.getIssuerName());
                seller.setSellerCode("00");
                seller.setSellerState("1");
                seller.setBusinessAreaId("1");
                seller.setCreateUser(dto.getUserId());
                seller.setCreateTime(DateUtil.getCurrentTime());
                seller.setModifyUser(dto.getUserId());
                seller.setModifyTime(seller.getCreateTime());
                sellerDAO.insert(seller);

                // 添加客户合同模板
                SellContract sellContract = new SellContract();
                sellContract.setSellContractId(commonsDAO.getNextValueOfSequence("TB_SELL_CONTRACT"));
                sellContract.setContractSeller(dto.getEntityId());
                sellContract.setContractBuyer("0");
                sellContract.setDeliveryFee("0");
                sellContract.setContractState(DataBaseConstant.DATA_STATE_NORMAL);
                sellContract.setExpiryDate("29991231");
                sellContract.setCreateTime(DateUtil.getCurrentTime());
                sellContract.setCreateUser(dto.getUserId());
                sellContract.setModifyTime(DateUtil.getCurrentTime());
                sellContract.setModifyUser(dto.getUserId());
                sellContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                sellContract.setContractType(DataBaseConstant.CONTRACT_TEMPLATE);
                sellerContractDAO.insert(sellContract);

                // 添加散户客户
                // String entityId = commonsDAO.getNextValueOfSequence("TB_ENTITY");
                /** 此版本屏蔽散户客户 */
                // Customer customer = new Customer();
                // customer.setEntityId(entityId);
                // customer.setFatherEntityId(dto.getEntityId());
                // customer.setCustomerName("散户");
                // customer.setCusState("2");
                // customer.setCustomerType("3");
                // customer.setCustomerAddress("00");
                // customer.setCustomerFax("00");
                // customer.setCustomerPostcode("000000");
                // customer.setCustomerTelephone("0000");
                // customer.setSalesmanId(dto.getUserId());
                // customer.setCreateTime(DateUtil.getCurrentTime());
                // customer.setCreateUser(dto.getUserId());
                // customer.setModifyTime(DateUtil.getCurrentTime());
                // customer.setModifyUser(dto.getUserId());
                // customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                // customerDAO.insert(customer);

                // 默认插入部门信息
                // DepartmentDTO entityDepartment = new DepartmentDTO();
                // entityDepartment.setEntityId(entityId);
                // entityDepartment.setDepartmentName(customer.getCustomerName());
                // entityDepartment.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // entityDepartment.setCreateUser(dto.getUserId());
                // entityDepartment.setModifyUser(dto.getUserId());
                // departmentService.insert(entityDepartment);

                // 默认插入联系人信息
                // ContactDTO entityContact = new ContactDTO();
                // entityContact.setEntityId(entityId);
                // entityContact.setContactName(customer.getCustomerName());
                // entityContact.setContactType("1");
                // entityContact.setContactGender("1");
                // entityContact.setContactTelephone(customer.getCustomerTelephone());
                // entityContact.setCreateUser(dto.getUserId());
                // entityContact.setModifyUser(dto.getUserId());
                // entityContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // entityContact.setValidityFlag("1");
                // entityContact.setPapersNo("1");
                // entityContact.setPapersType("1");
                // contactService.insert(entityContact);

                // 默认插入快递点信息
                // DeliveryPointDTO entityDelivery = new DeliveryPointDTO();
                // entityDelivery.setEntityId(entityId);
                // entityDelivery.setDeliveryName(customer.getCustomerName());
                // entityDelivery.setDeliveryPostcode(customer.getCustomerPostcode());
                // entityDelivery.setDeliveryAddress(customer.getCustomerAddress());
                // entityDelivery.setCreateUser(dto.getUserId());
                // entityDelivery.setModifyUser(dto.getUserId());
                // entityDelivery.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // entityDelivery = deliveryPointService.insert(entityDelivery);

                // 默认快递点联系人（依赖于快递点）
                // DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
                // deliveryContact.setDeliveryPointId(entityDelivery.getDeliveryId());
                // deliveryContact
                // .setDeliveryContact(entityDelivery.getDeliveryName());
                // deliveryContact.setContactPhone(customer.getCustomerTelephone());
                // deliveryContact.setCreateUser(dto.getUserId());
                // deliveryContact.setModifyUser(dto.getUserId());
                // deliveryContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // deliveryContactService.insert(deliveryContact);

                // 默认插入发票地址信息
                // InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
                // invoiceAddress.setEntityId(entityId);
                // invoiceAddress.setInvoiceAddress(customer.getCustomerAddress());
                // invoiceAddress.setAddressPostcode(customer.getCustomerPostcode());
                // invoiceAddress.setCreateUser(dto.getUserId());
                // invoiceAddress.setModifyUser(dto.getUserId());
                // invoiceAddress.setInvoiceRecipient(customer.getCustomerName());
                // invoiceAddress
                // .setDeliveryOption(DataBaseConstant.DELIVERY_OPERATION_SEND);
                // invoiceAddress.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // invoiceAddressService.insert(invoiceAddress);

                // 默认插入发票公司信息
                // InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
                // invoiceCompanyDTO.setEntityId(entityId);
                // invoiceCompanyDTO.setInvoiceCompanyName(customer.getCustomerName());
                // invoiceCompanyDTO.setCreateUser(dto.getUserId());
                // invoiceCompanyDTO.setModifyUser(dto.getUserId());
                // invoiceCompanyDTO.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
                // invoiceCompanyService.insert(invoiceCompanyDTO);
            } else {
                Seller seller = new Seller();
                seller.setEntityId(dto.getEntityId());
                seller.setFatherEntityId(dto.getEntityId());
                seller.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                sellerDAO.updateByPrimaryKeySelective(seller);
            }
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加营销机构失败");
        }
    }

    private void insertConsumer(IssuerDTO dto) throws BizServiceException {
        ConsumerKey ck = new ConsumerKey();
        ck.setEntityId(dto.getEntityId());
        ck.setFatherEntityId(dto.getEntityId());
        if (consumerDAO.selectByPrimaryKey(ck) == null) {
            // 添加收单机构
            Consumer consumer = new Consumer();
            ReflectionUtil.copyProperties(dto, consumer);
            consumer.setFatherEntityId(dto.getEntityId());
            consumer.setConsumerName(dto.getIssuerName());
            consumer.setConsumerCode("00");
            consumer.setConsumerState("1");
            consumer.setCreateUser(dto.getUserId());
            consumer.setCreateTime(DateUtil.getCurrentTime());
            consumer.setModifyUser(dto.getUserId());
            consumer.setModifyTime(DateUtil.getCurrentTime());
            consumerDAO.insert(consumer);
            // 添加实体系统参数
            // entitySystemParameterService
            // .insertEntitySystemParameter(dto.getEntityId(),
            // dto.getEntityId(), dto.getUserId());
            // 添加POS公共参数
            PosParamenter posParamenter = new PosParamenter();
            posParamenter.setConsumerId(dto.getEntityId());
            PosParamenterExample posParamenterExample = new PosParamenterExample();
            posParamenterExample.createCriteria().andConsumerIdEqualTo(dto.getEntityId());
            if (terParameterDAO.countByExample(posParamenterExample) <= 0) {
                terParameterDAO.insert_default(posParamenter);
            }
        } else {
            Consumer consumer = new Consumer();
            consumer.setEntityId(dto.getEntityId());
            consumer.setFatherEntityId(dto.getEntityId());
            consumer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            consumerDAO.updateByPrimaryKeySelective(consumer);
        }
    }

    /**
     * 通过发卡机构编辑获取发卡机构下的卡PIN信息
     * 
     * @param issuerId
     * @return
     * @throws Exception
     */
    public List<CardSerialNumberDTO> getCardSerialNumberByIssuerId(String issuerId) throws Exception {
        CardSerialNumberExample example = new CardSerialNumberExample();
        example.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL).andIssuerIdEqualTo(issuerId);
        List<CardSerialNumber> cardSerialNumberList = cardSerialNumberDAO.selectByExample(example);
        List<CardSerialNumberDTO> dtoList = new ArrayList<CardSerialNumberDTO>();
        for (CardSerialNumber cardSerialNumber : cardSerialNumberList) {
            CardSerialNumberDTO dto = new CardSerialNumberDTO();
            ReflectionUtil.copyProperties(cardSerialNumber, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private Integer checkRepeatCardBin(CardSerialNumberDTO cardSerialNumberDTO) {
        return (Integer) baseDAO.queryForObject("ISSUER.checkRepeatCardBin", cardSerialNumberDTO);
    }

    public void insertCardBin(CardSerialNumberDTO cardSerialNumberDTO) throws Exception {
        try {

            /**
             * 先检索一下卡BIN是否存在
             */
            // CardSerialNumberExample example = new CardSerialNumberExample();
            // example.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
            // .andIssuerIdEqualTo(cardSerialNumberDTO.getIssuerId()).andCardBinEqualTo(cardSerialNumberDTO.getCardBin());

            // if(cardSerialNumberDAO.countByExample(example)>0){
            // throw new
            // BizServiceException("当前实体下已存在"+cardSerialNumberDTO.getCardBin()+"卡PIN!");
            // }

            if (checkRepeatCardBin(cardSerialNumberDTO) > 0) {
                throw new BizServiceException("已存在" + cardSerialNumberDTO.getCardBin() + "卡BIN!");
            }
            CardSerialNumber cardSerialNumber = new CardSerialNumber();

            ReflectionUtil.copyProperties(cardSerialNumberDTO, cardSerialNumber);

            cardSerialNumber.setCreateTime(DateUtil.getCurrentTime());
            cardSerialNumber.setCreateUser(cardSerialNumberDTO.getLoginUserId());

            cardSerialNumber.setModifyTime(DateUtil.getCurrentTime());

            cardSerialNumber.setModifyUser(cardSerialNumberDTO.getLoginUserId());

            cardSerialNumber.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            cardSerialNumberDAO.insert(cardSerialNumber);
        } catch (BizServiceException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    public PageDataDTO queryEntityId(IssuerQueryDTO issuerQueryDTO) throws BizServiceException {
        PageDataDTO pageDataDTO = new PageDataDTO();
        try {
            if (issuerQueryDTO.getEntityFlag() == null || "".equals(issuerQueryDTO.getEntityFlag())) {
                pageDataDTO = pageQueryDAO.query("ISSUER.selectEntityId", issuerQueryDTO);
            } else if ("1".equals(issuerQueryDTO.getEntityFlag())) {
                pageDataDTO = pageQueryDAO.query("ISSUER.selectEntityIdFromSeller", issuerQueryDTO);
            } else {
                pageDataDTO = pageQueryDAO.query("ISSUER.selectEntityIdFromConsumer", issuerQueryDTO);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询实体失败");
        }
        return pageDataDTO;
    }

    public CardSerialNumberDAO getCardSerialNumberDAO() {
        return cardSerialNumberDAO;
    }

    public void setCardSerialNumberDAO(CardSerialNumberDAO cardSerialNumberDAO) {
        this.cardSerialNumberDAO = cardSerialNumberDAO;
    }

    public EntityDictInfoService getEntityDictInfoService() {
        return entityDictInfoService;
    }

    public void setEntityDictInfoService(EntityDictInfoService entityDictInfoService) {
        this.entityDictInfoService = entityDictInfoService;
    }

    public EntitySystemParameterService getEntitySystemParameterService() {
        return entitySystemParameterService;
    }

    public void setEntitySystemParameterService(EntitySystemParameterService entitySystemParameterService) {
        this.entitySystemParameterService = entitySystemParameterService;
    }

    public IssuerDTO configEntityId(IssuerQueryDTO issuerQueryDTO) throws BizServiceException {
        IssuerDTO issuerDTO = new IssuerDTO();
        try {
            SellerExample example1 = new SellerExample();
            example1.createCriteria().andEntityIdEqualTo(issuerQueryDTO.getEntityId())
                    .andFatherEntityIdEqualTo(issuerQueryDTO.getFatherEntityId()).andDataStateEqualTo("1");
            List<Seller> sellerList = sellerDAO.selectByExample(example1);
            if (sellerList != null && sellerList.size() > 0) {
                Seller seller = sellerList.get(0);
                issuerDTO.setFatherEntityId(seller.getFatherEntityId());
                issuerDTO.setEntityId(seller.getEntityId());
                issuerDTO.setIssuerName(seller.getSellerName());
                issuerDTO.setIssuerEnglishName(seller.getSellerEnglishName());
                issuerDTO.setIssuerAddress(seller.getSellerAddress());
                issuerDTO.setIssuerEnglishAddress(seller.getSellerEnglishAddress());
                issuerDTO.setIssuerFax(seller.getSellerFax());
                issuerDTO.setIssuerTelephone(seller.getSellerTelephone());
                issuerDTO.setIssuerPostcode(seller.getSellerPostcode());
                return issuerDTO;
            }
            ConsumerKey key = new ConsumerKey();
            key.setEntityId(issuerQueryDTO.getEntityId());
            key.setFatherEntityId(issuerQueryDTO.getFatherEntityId());
            Consumer consumer = consumerDAO.selectByPrimaryKey(key);
            if (consumer != null) {
                issuerDTO.setFatherEntityId(consumer.getFatherEntityId());
                issuerDTO.setEntityId(consumer.getEntityId());
                issuerDTO.setIssuerName(consumer.getConsumerName());
                issuerDTO.setIssuerEnglishName(consumer.getConsumerEnglishName());
                issuerDTO.setIssuerAddress(consumer.getConsumerAddress());
                issuerDTO.setIssuerEnglishAddress(consumer.getConsumerEnglishAddress());
                issuerDTO.setIssuerFax(consumer.getConsumerFax());
                issuerDTO.setIssuerTelephone(consumer.getConsumerTelephone());
                issuerDTO.setIssuerPostcode(consumer.getConsumerPostcode());
                return issuerDTO;
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("选择实体失败");
        }
        return null;
    }

    public IssuerDTO getCardSerialNumberDTOs(IssuerDTO issuerDTO) throws BizServiceException {
        try {
            issuerDTO.setCardSerialNumberDTOList(getCardSerialNumberByIssuerId(issuerDTO.getDefaultEntityId()));

            return issuerDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡BIN失败！");
        }

    }

    public UserServiceDAO getUserServiceDAO() {
        return userServiceDAO;
    }

    public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
        this.userServiceDAO = userServiceDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public CaclInfService getCaclInfService() {
        return caclInfService;
    }

    public void setCaclInfService(CaclInfService caclInfService) {
        this.caclInfService = caclInfService;
    }

    public IssuerDTO checkIssuerIdPin(IssuerDTO issuerDTO) throws BizServiceException {
        try {
            String issuerIdPin = issuerDTO.getIssuerIdPin();
            IssuerExample example = new IssuerExample();
            example.createCriteria().andFatherEntityIdEqualTo("00000000")
                    .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
            List<Issuer> issuerList = issuerDAO.selectByExample(example);
            if (issuerList != null && issuerList.size() > 0) {
                for (Issuer iss : issuerList) {
                    if (iss.getEntityId().substring(0, 4).equals(issuerIdPin)) {
                        return issuerDTO;
                    }
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查找发卡机构ID前缀失败");
        }
        return null;
    }

    public SettlePeriodRuleService getSettlePeriodRuleService() {
        return settlePeriodRuleService;
    }

    public void setSettlePeriodRuleService(SettlePeriodRuleService settlePeriodRuleService) {
        this.settlePeriodRuleService = settlePeriodRuleService;
    }

    public SellContractDAO getSellerContractDAO() {
        return sellerContractDAO;
    }

    public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
        this.sellerContractDAO = sellerContractDAO;
    }

    public InsInfDAO getInsInfDAO() {
        return insInfDAO;
    }

    public void setInsInfDAO(InsInfDAO insInfDAO) {
        this.insInfDAO = insInfDAO;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    public DeliveryPointService getDeliveryPointService() {
        return deliveryPointService;
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

    public DeliveryContactService getDeliveryContactService() {
        return deliveryContactService;
    }

    public void setDeliveryContactService(DeliveryContactService deliveryContactService) {
        this.deliveryContactService = deliveryContactService;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public InvoiceAddressService getInvoiceAddressService() {
        return invoiceAddressService;
    }

    public void setInvoiceAddressService(InvoiceAddressService invoiceAddressService) {
        this.invoiceAddressService = invoiceAddressService;
    }

    public InvoiceCompanyService getInvoiceCompanyService() {
        return invoiceCompanyService;
    }

    public void setInvoiceCompanyService(InvoiceCompanyService invoiceCompanyService) {
        this.invoiceCompanyService = invoiceCompanyService;
    }

}
