/**
 * Classname MDMSynchServicImpl.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		lfr		2013-4-7
 * =============================================================================
 */

package com.huateng.univer.synch.MDMSynch.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.MerchantTempDAO;
import com.huateng.framework.ibatis.dao.PosInfoDAO;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.MerchantKey;
import com.huateng.framework.ibatis.model.MerchantTemp;
import com.huateng.framework.ibatis.model.PosInfo;
import com.huateng.framework.ibatis.model.PosInfoExample;
import com.huateng.framework.ibatis.model.Shop;
import com.huateng.framework.ibatis.model.ShopExample;
import com.huateng.framework.util.Config;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.util.RandomUtils;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.consumer.shop.integration.dao.ShopServiceDAO;
import com.huateng.univer.synch.MDMSynch.MDMSynchService;
import com.huateng.univer.synch.dto.MerchantSynchDTO;
import com.huateng.univer.synch.dto.ShopSynchDTO;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.suning.svc.coupon.constants.OrgnizationConstants;
import com.suning.svc.datasync.constants.MdmConstants;

/**
 * @author lfr
 * 
 */
public class MDMSynchServiceImpl implements MDMSynchService, InitializingBean {

    Logger logger = Logger.getLogger(MDMSynchServiceImpl.class);

    /**
     * 发卡机构-商户编码前缀 键值对
     */
    private static final Map<String, String> ISSUER_MAPPER = new HashMap<String, String>();

    /**
     * 发卡机构表DAO
     */
    private IssuerDAO issuerDAO;

    private MerchantServiceDAO merchantServiceDAO;
    private CommonsDAO commonsDAO;
    private EntitySystemParameterService entitySystemParameterService;
    private ShopServiceDAO shopServiceDAO;
    private PosInfoDAO posInfoDAO;

    /**
     * 商户临时表DAO
     */
    private MerchantTempDAO merchantTempDAO;

    /**
     * 商户信息同步
     * 
     * @param MerchantSynchDTO
     * @return
     * @throws BizServiceException
     */
    public String merchantInfoSynch(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {

        // 供应商编码处理
        String entityId = merchantSynchDTO.getEntityId();
        if (StringUtils.isBlank(entityId)) {
            logger.error("商户编号（供应商编码）为空。");
            throw new BizServiceException("商户编号（供应商编码）为空。");
        }

        if (merchantSynchDTO.getFatherEntityId() == null) {
            // 不存在收单机构号，作为供应商基本层信息下发处理
            handleSuppilerInfo(merchantSynchDTO);
        } else {
            // 存在收单机构号，作为供应商公司层信息下发处理
            handleCompanyLayerInfo(merchantSynchDTO);
        }

        return "succ";
    }

    /**
     * 供应商基本层下发处理<BR>
     * 1.立即同步类型，取华夏通作为收单机构，同步到系统商户表<BR>
     * 2.其它类型新增/更新到商户临时表
     * 
     * @param merchantSynchDTO 商户同步DTO
     * @throws BizServiceException 业务异常
     */
    private void handleSuppilerInfo(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {
        try {

            String entityId = merchantSynchDTO.getEntityId();
            if (StringUtils.equals(merchantSynchDTO.getSynchFlag(), MdmConstants.SYNCH_FLAG_HXT)) {
                // 对于立即同步到华夏通的类型，取5101作为上级机构ID
                merchantSynchDTO.setFatherEntityId(OrgnizationConstants.HXT_ENTITY_ID);
                // 同步到系统
                doMerchantSynch(merchantSynchDTO);
            } else {
                // 从DTO构建临时表记录
                MerchantTemp merchantTemp = new MerchantTemp();
                merchantTemp.setEntityId(entityId);
                merchantTemp.setMerchantCode(merchantSynchDTO.getMerchantCode());
                merchantTemp.setMerchantName(merchantSynchDTO.getMerchantName());
                merchantTemp.setMerchantType(merchantSynchDTO.getMerchantType());
                merchantTemp.setMerchantAddress(merchantSynchDTO.getMerchantAddress());
                merchantTemp.setDataState(merchantSynchDTO.getDataState());

                // 查询
                MerchantTemp extantMerchant = merchantTempDAO.selectByPrimaryKey(entityId);
                if (extantMerchant == null) {
                    // 新增
                    merchantTempDAO.insert(merchantTemp);
                } else {
                    // 更新
                    merchantTempDAO.updateByPrimaryKey(merchantTemp);
                }
            }

        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("供应商:" + merchantSynchDTO.getEntityId() + "基本层信息同步失败！" + e.getMessage());
        }

    }

    /**
     * 
     * 公司层信息下发处理<BR>
     * 1.根据entityId查临时表<BR>
     * 2.与临时表字段合并成完整DTO<BR>
     * 3.除fatherEntityId外沿用原同步逻辑
     * 
     * @param merchantSynchDTO 商户同步DTO
     * @throws BizServiceException 业务处理异常
     */
    private void handleCompanyLayerInfo(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {
        try {

            String entityId = merchantSynchDTO.getEntityId();
            if (StringUtils.startsWith(entityId, MdmConstants.SUPPLIER_CODE_PREFIX_INNER)
                    && StringUtils.equals(merchantSynchDTO.getFatherEntityId(), OrgnizationConstants.HXT_ENTITY_ID)) {
                // 对于RE开头供应商（SN10账务组子集）同步到华夏通的类型，在供应商基本层已同步过，此处什么也不做
            } else {
                // 查询临时表
                MerchantTemp extantMerchant = merchantTempDAO.selectByPrimaryKey(entityId);
                if (extantMerchant == null) {
                    throw new BizServiceException("供应商：" + entityId + "在临时表中不存在。");
                }

                // 根据临时表记录补全DTO
                merchantSynchDTO.setDataState(extantMerchant.getDataState());
                merchantSynchDTO.setMerchantAddress(extantMerchant.getMerchantAddress());
                merchantSynchDTO.setMerchantCode(extantMerchant.getMerchantCode());
                merchantSynchDTO.setMerchantName(extantMerchant.getMerchantName());
                merchantSynchDTO.setMerchantType(extantMerchant.getMerchantType());

                // 同步到系统
                doMerchantSynch(merchantSynchDTO);
            }

        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("供应商:" + merchantSynchDTO.getEntityId() + "公司层信息同步失败！" + e.getMessage());
        }
    }

    /**
     * 
     * 同步商户到系统商户表
     * 
     * @param merchantSynchDTO 字段完整的DTO
     * @throws BizServiceException 业务异常
     */
    private void doMerchantSynch(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {
        try {
            String entityId = merchantSynchDTO.getEntityId();

            // 20130802 有公司层信息后不再从配置文件读取收单机构号
            String fatherEntityId = merchantSynchDTO.getFatherEntityId();

            // 查询商户是否已经存在
            MerchantKey merchantKey = new MerchantKey();
            merchantKey.setEntityId(entityId);
            merchantKey.setFatherEntityId(fatherEntityId);
            Merchant merchant = merchantServiceDAO.selectByPrimaryKey(merchantKey);

            // 新增商户时未下发商户号，分配商户号
            String merchantCode = merchantSynchDTO.getMerchantCode();
            if (merchant == null && merchantCode == null) {
                // 没有商户号的均生成商户号
                String prefixCode = ISSUER_MAPPER.get(fatherEntityId);
                merchantSynchDTO.setMerchantCode(prefixCode + commonsDAO.getNextValueOfSequence("MERCHANT_CODE"));
            }

            // 查询在该表里是否有商户重名
            if (chkMchntName(merchantSynchDTO) > 0) {
                throw new BizServiceException("供应商:" + entityId + "此商户名（供应商名称）" + merchantSynchDTO.getMerchantName()
                        + "已存在");
            }

            // 查询在该表里是否有商户号重复
            if (chkMchntCode(merchantSynchDTO) > 0) {
                throw new BizServiceException("供应商:" + entityId + "此商户号（搜索项1）" + merchantSynchDTO.getMerchantCode()
                        + "已存在");
            }

            if (merchant == null) {
                // 新增商户信息
                insertMerchant(merchantSynchDTO);

            } else {
                // 更新商户信息
                updateMerchant(merchantSynchDTO);
            }

        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("供应商:" + merchantSynchDTO.getEntityId() + "商户信息同步失败！" + e.getMessage());
        }
    }

    /**
     * 新增商户信息
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public MerchantSynchDTO insertMerchant(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {
        try {
            Merchant merchant = new Merchant();
            ReflectionUtil.copyProperties(merchantSynchDTO, merchant);

            // 商户网站用户名、密码生成
            merchant.setEnableWebsite("1");
            // merchant.setWebsiteUserName("a"
            // + String.valueOf(commonsDAO
            // .getNextValueOfSequence("TB_MERCHANT")));
            // 商户网站用户名为MW+商户号
            merchant.setWebsiteUserName("MW" + merchantSynchDTO.getMerchantCode());
            String websitePassword = RandomUtils.generateString(6);
            String websitePasswordMD5 = MD5EncryptAlgorithm.md5(websitePassword);
            merchant.setWebsitePassword(websitePasswordMD5);

            // 商户默认信息
            merchant.setMerchantState("1");
            merchant.setCommissionFee("1");
            merchant.setReimburseWithoutCheck("0");
            merchant.setPurchasePause("0");
            merchant.setReimbursePause("0");
            merchant.setJoinDate(DateUtil.getStringDate());
            // 使用默认用户admin
            merchant.setCreateUser("0");
            merchant.setCreateTime(DateUtil.getCurrentTime());
            // 使用默认用户admin
            merchant.setModifyUser("0");
            merchant.setModifyTime(DateUtil.getCurrentTime());
            merchant.setMerchantAttribute("1");
            merchant.setIsMovePost("0");
            merchant.setShopMaxCode("0000");

            // 添加实体系统参数
           //entitySystemParameterService.insertEntitySystemParameter(merchant.getEntityId(),
                   // merchantSynchDTO.getFatherEntityId(), "0");

            merchantServiceDAO.insert(merchant);

       // } catch (BizServiceException b) {
//            throw b;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("供应商:" + merchantSynchDTO.getEntityId() + "新增商户信息失败。" + e.getMessage());
        }
        return merchantSynchDTO;
    }

    /**
     * 更新商户信息
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public MerchantSynchDTO updateMerchant(MerchantSynchDTO merchantSynchDTO) throws BizServiceException {

        try {
            Merchant merchant = new Merchant();
            MerchantKey merchantKey = new MerchantKey();
            merchantKey.setEntityId(merchantSynchDTO.getEntityId());
            merchantKey.setFatherEntityId(merchantSynchDTO.getFatherEntityId());
            ReflectionUtil.copyPropertiesNotNull(merchantSynchDTO, merchant);
            // 使用默认用户admin
            merchant.setModifyUser("0");
            merchant.setModifyTime(DateUtil.getCurrentTime());
            merchantServiceDAO.updateByPrimaryKeySelective(merchant);

            return merchantSynchDTO;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("更新商户信息失败。" + e.getMessage());
        }
    }

    /**
     * 商户号重复校验
     * 
     * @param MerchantSynchDTO
     * @return
     * @throws BizServiceException
     */
    public int chkMchntCode(MerchantSynchDTO dto) {
        int ret = 0;

        if (dto.getMerchantCode() == null) {
            return ret;
        }

        // 查询相同商户号的记录
        MerchantExample merchantExample = new MerchantExample();
        merchantExample.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andMerchantCodeEqualTo(dto.getMerchantCode());

        List<Merchant> merchantList = merchantServiceDAO.selectByExample(merchantExample);
        if (merchantList != null && merchantList.size() != 0) {
            // 排除dto对应的商户
            Iterator<Merchant> it = merchantList.iterator();
            // 需要从list中除去元素，故使用迭代器操作
            while (it.hasNext()) {
                Merchant m = it.next();
                if (StringUtils.equals(m.getEntityId(), dto.getEntityId())
                        && StringUtils.equals(m.getFatherEntityId(), dto.getFatherEntityId())) {
                    it.remove();
                }
            }
            // 处理完成后再取list的大小
            ret = merchantList.size();
        }
        return ret;
    }

    /**
     * 商户名重名校验
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public int chkMchntName(MerchantSynchDTO dto) throws BizServiceException {
        int ret = 0;

        // 商户名不能为空
        String merchantName = dto.getMerchantName();
        if (StringUtils.isBlank(merchantName)) {
            throw new BizServiceException("供应商:" + dto.getEntityId() + "商户名（地点所属公司代码）为空。");
        }

        // 20131104 屏蔽商户名必须不同的限制，最大限度接收双11商户
        // 查询在该表里是否有商户重名
        // MerchantExample merchantExample = new MerchantExample();
        // merchantExample.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
        // .andMerchantNameEqualTo(dto.getMerchantName()).andEntityIdNotEqualTo(dto.getEntityId())
        // .andFatherEntityIdEqualTo(dto.getFatherEntityId());
        // List<Merchant> merchantList = merchantServiceDAO.selectByExample(merchantExample);
        // if (merchantList != null && merchantList.size() != 0) {
        // ret = merchantList.size();
        // }
        return ret;
    }

    /**
     * 门店信息同步
     * 
     * @param MerchantSynchDTO
     * @return
     * @throws BizServiceException
     */
    public String shopInfoSynch(ShopSynchDTO shopSynchDTO) throws BizServiceException {
        try {

            String entityId = shopSynchDTO.getEntityId();
            if (entityId == null || "".equals(entityId.trim())) {
                throw new BizServiceException("地点代码：" + entityId + "商户编码（地点所属公司代码）为空。");
            } else {
                shopSynchDTO.setEntityId(entityId.trim());
            }

            // 收单机构号
            String fatherEntityId = Config.getConsumerEntityId();
            if (fatherEntityId == null) {
                throw new BizServiceException("地点代码：" + entityId + "配置文件accor.properties中没有配收单机构。");
            }
            shopSynchDTO.setConsumerId(fatherEntityId);

            // 判断门店是否是预付费系统门店
            MerchantKey merchantKey = new MerchantKey();
            merchantKey.setEntityId(shopSynchDTO.getEntityId());
            merchantKey.setFatherEntityId(shopSynchDTO.getConsumerId());
            Merchant merchant = merchantServiceDAO.selectByPrimaryKey(merchantKey);
            if (merchant == null) {
                throw new BizServiceException("地点代码：" + entityId + "商户" + shopSynchDTO.getEntityId() + "不是本系统商户。");
            }

            String shopId = shopSynchDTO.getShopId();
            if (shopId == null || "".equals(shopId.trim())) {
                throw new BizServiceException("地点代码：" + entityId + "门店号（地点代码）为空。");
            } else {
                shopSynchDTO.setShopId(shopId.trim());
            }

            String shopState = shopSynchDTO.getShopState();
            if (shopState == null || "".equals(shopState.trim())) {
                throw new BizServiceException("地点代码：" + entityId + "门店状态（地点状态）为空。");
            } else if (shopState.trim().length() > 1) {
                throw new BizServiceException("地点代码：" + entityId + "门店状态" + shopState.trim() + "长度过长。");
            } else {
                shopSynchDTO.setShopState(shopState.trim());
            }

            String shopName = shopSynchDTO.getShopName();
            if (shopName == null || "".equals(shopName.trim())) {
                throw new BizServiceException("地点代码：" + entityId + "门店名称（地点名称）为空。");
            } else {
                shopSynchDTO.setShopName(shopName.trim());
            }

            // 查询是否有门店名称重复（由于MDM会做相应限制，并且此验证在特定情况下会过滤掉正常下发的门店）
            // if (chkShopName(shopSynchDTO) > 0) {
            // throw new BizServiceException("此门店名称（地点名称）"+shopSynchDTO.getShopName()+"已存在，同一商户下不能有相同门店名称。");
            // }

            // 查询门店是否已经存在
            Shop shop = shopServiceDAO.selectByPrimaryKey(shopId);

            if (shop == null) {
                // 新增门店信息
                insertShop(shopSynchDTO);
            } else {
                // 更新门店信息
                updateShop(shopSynchDTO);
            }
            return "succ";
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("地点代码：" + shopSynchDTO.getEntityId() + "门店同步失败。" + e.getMessage());
        }
    }

    /**
     * 添加门店
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public ShopSynchDTO insertShop(ShopSynchDTO shopSynchDTO) throws BizServiceException {
        try {

            Shop shop = new Shop();
            ReflectionUtil.copyProperties(shopSynchDTO, shop);

            // 获取商户下最大shopCode
            MerchantKey merchantKey = new MerchantKey();
            merchantKey.setEntityId(shop.getEntityId());
            merchantKey.setFatherEntityId(shop.getConsumerId());
            Merchant merchant = merchantServiceDAO.selectByPrimaryKey(merchantKey);
            String shopCode = String.valueOf((Integer.parseInt(merchant.getShopMaxCode()) + 1));
            while (shopCode.length() < merchant.getShopMaxCode().length()) {
                shopCode = "0" + shopCode;
            }
            shop.setShopCode(shopCode);
            // 使用默认用户admin
            shop.setCreateUser("0");
            shop.setCreateTime(DateUtil.getCurrentTime());
            // 使用默认用户admin
            shop.setModifyUser("0");
            shop.setModifyTime(DateUtil.getCurrentTime());
            shop.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            shopServiceDAO.insert(shop);

            merchant.setShopMaxCode(shop.getShopCode());
            merchantServiceDAO.updateByPrimaryKey(merchant);

            // 添加实体系统参数
        // entitySystemParameterService.insertEntitySystemParameter(shopSynchDTO.getShopId(),
                  //shopSynchDTO.getConsumerId(), "0");

            return shopSynchDTO;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("地点代码：" + shopSynchDTO.getEntityId() + "新增门店失败。" + e.getMessage());
        }

    }

    /**
     * 更新门店
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public void updateShop(ShopSynchDTO shopSynchDTO) throws BizServiceException {

        try {
            // 门店状态
            if (!DataBaseConstant.DATA_TYPE_YES.equals(shopSynchDTO.getShopState())) {
                PosInfoExample posInfoExample = new PosInfoExample();
                posInfoExample.createCriteria().andShopIdEqualTo(Long.parseLong(shopSynchDTO.getShopId()))
                        .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

                List<PosInfo> posInfList = posInfoDAO.selectByExample(posInfoExample);
                if (null != posInfList && posInfList.size() > 0) {
                    throw new BizServiceException("地点代码：" + shopSynchDTO.getEntityId() + "门店："
                            + shopSynchDTO.getShopName() + "下面还有POS，不能改状态为无效！");
                }
            }

            Shop shop = new Shop();
            shop.setShopId(shopSynchDTO.getShopId());
            shop.setShopState(shopSynchDTO.getShopState());
            shop.setEntityId(shopSynchDTO.getEntityId());
            shop.setShopName(shopSynchDTO.getShopName());
            // 使用默认用户admin
            shop.setModifyUser("0");
            shop.setModifyTime(DateUtil.getCurrentTime());
            shopServiceDAO.updateByPrimaryKeySelective(shop);

        } catch (BizServiceException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error(e);
            throw new BizServiceException("地点代码：" + shopSynchDTO.getEntityId() + "更新门店失败！" + e.getMessage());
        }
    }

    /**
     * 门店名称重名校验
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     */
    public int chkShopName(ShopSynchDTO dto) {
        int ret = 0;

        // 门店状态非有效时，不检查门店名重名
        if (!DataBaseConstant.DATA_STATE_NORMAL.equals(dto.getShopState())) {
            return ret;
        }

        // 查询在该表里是否有商户重名
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andEntityIdEqualTo(dto.getEntityId()).andConsumerIdEqualTo(dto.getConsumerId())
                .andShopNameEqualTo(dto.getShopName()).andShopIdNotEqualTo(dto.getShopId());
        List<Shop> shopList = shopServiceDAO.selectByExample(shopExample);
        if (shopList != null && shopList.size() != 0) {
            ret = shopList.size();
        }
        return ret;
    }

    public MerchantServiceDAO getMerchantServiceDAO() {
        return merchantServiceDAO;
    }

    public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
        this.merchantServiceDAO = merchantServiceDAO;
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public EntitySystemParameterService getEntitySystemParameterService() {
        return entitySystemParameterService;
    }

    public void setEntitySystemParameterService(EntitySystemParameterService entitySystemParameterService) {
        this.entitySystemParameterService = entitySystemParameterService;
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

    /**
     * @param merchantTempDAO the merchantTempDAO to set
     */
    public void setMerchantTempDAO(MerchantTempDAO merchantTempDAO) {
        this.merchantTempDAO = merchantTempDAO;
    }

    /**
     * @param issuerDAO the issuerDAO to set
     */
    public void setIssuerDAO(IssuerDAO issuerDAO) {
        this.issuerDAO = issuerDAO;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initIssuerMapper();
    }

    /**
     * 
     * 初始化ISSUER_MAPPER
     * 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void initIssuerMapper() {
        // 查询发卡机构表并将映射关系载入内存
        List<Issuer> allIssuers = issuerDAO.selectByExample(null);
        for (Issuer issuer : allIssuers) {
            // 建立 5101-->111 等键值对
            ISSUER_MAPPER.put(issuer.getEntityId(), issuer.getIssuerCode());
        }
        logger.info("初始化完成：从发卡机构表读取并装载了" + ISSUER_MAPPER.size() + "组键值对");
    }

}
