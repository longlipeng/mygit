package com.suning.svc.datasync.service;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.constants.MDMConstCode;
import com.huateng.univer.synch.dto.MerchantSynchDTO;
import com.suning.framework.lang.Result;
import com.suning.rsc.mqservice.ei.annotation.EsbEIWired;
import com.suning.svc.datasync.common.SeamErrorCode;
import com.suning.svc.datasync.constants.MdmConstants;
import com.suning.svc.datasync.utils.RandomUtils;
import com.suning.svc.datasync.xmlbean.mdm.merchant.FeedbackBodyBean;
import com.suning.svc.datasync.xmlbean.mdm.merchant.FeedbackStatusInfo;
import com.suning.svc.datasync.xmlbean.mdm.merchant.Merchant;
import com.suning.svc.datasync.xmlbean.mdm.merchant.ReceivedBodyBean;

/**
 * 
 * 〈供应商（商户）数据处理〉<br>
 * 〈MDM通过ESB下发供应商（商户）数据〉<br>
 * 〈1、筛选满足预付卡系统的供应商（商户）数据〉<br>
 * 〈2、将数据传送给预付卡系统〉<br>
 * 〈3、将处理结果返回给MDM〉<br>
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdmMerchantRecvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmMerchantRecvService.class);

    /**
     * 03：表示接收MDM下发数据成功标志
     */
    private static final String RECEIVE_SUCCESS_CODE = "03";

    /**
     * 表示接收MDM下发数据成功的中文描述
     */
    private static final String RECEIVE_SUCCESS_DESC = "接收成功!";

    /**
     * 04：表示接收MDM下发数据失败标志
     */
    private static final String RECEIVE_FAIL_CODE = "04";

    /**
     * 表示接收MDM下发数据失败的中文描述
     */
    private static final String RECEIVE_FAIL_DESC = "接收失败!";

    /**
     * 供应商编码前缀R，用于判断是否为内部供应商，带R开头，除了RHDC、RFDC，为内部供应商
     */
    private static final String SUPPLIER_CODE_PREFIX_R = "R";

    /**
     * 供应商编码前缀RE，为内部供应商
     */
    private static final String SUPPLIER_CODE_PREFIX_RE = "RE";

    /**
     * 流水号长度，6位
     */
    private static final int SERIAL_LENGTH = 6;

    private MdmMerchantSendService merchantSendService;

    /**
     * 调用预付卡系统的hessian服务，用于传供应商（商户）数据给预付卡系统
     */
    private ManageHessianService manageHessianService;

    /**
     * 暂存MDM下发的所有供应商（商户）数据
     */
    private ArrayList<Merchant> merchants;

    /**
     * 暂存MDM下发的满足预付卡系统要求的供应商（商户）数据
     */
    private ArrayList<Merchant> merchantSvc;

    /**
     * 存放MDM下发的所有供应商（商户）数据对应的处理结果，用于返回给MDM
     */
    private ArrayList<FeedbackStatusInfo> feedbackStatusInfos;

    /**
     * 筛选满足预付卡系统的供应商（商户）数据<br>
     * 〈筛选结果暂存于merchantSvc，并将不满足预付卡系统要求其他数据的返回值设置为接收成功〉
     * 
     * @param bean 所有供应商（商户）数据的报文
     */
    private void selectMerchantForSvc(ReceivedBodyBean bean) {

        // 将接收到的所有数据暂存于merchants
        merchants = bean.getMerchant();
        merchantSvc = new ArrayList<Merchant>();
        feedbackStatusInfos = new ArrayList<FeedbackStatusInfo>();

        // 若集合merchants不为空，则进行相应的处理，若为空，则直接返回
        if (merchants != null && merchants.size() > 0) {
            // 循环，筛选满足预付卡系统的供应商（商户）数据
            for (Merchant merchant : merchants) {

                // 供应商编码以R打头的供应商（商户），RE打头的为内部供应商，非RE的，默认接收成功，不传给预付卡系统
                if (merchant.getSupplierCode().contains(SUPPLIER_CODE_PREFIX_R)) {

                    // 供应商编码以RE打头的供应商，暂存于merchantSvc，需转发给预付卡系统
                    if (merchant.getSupplierCode().contains(SUPPLIER_CODE_PREFIX_RE)) {
                        merchantSvc.add(merchant);
                        // 其他供应商编码以R打头的供应商（商户），直接默认接收成功，不转发给预付卡系统
                    } else {
                        FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();
                        // 返回给MDM，字段包括：供应商（商户）编码、版本号、成功标志、成功描述
                        feedbackStatusInfo.setSupplierCode(merchant.getSupplierCode());
                        feedbackStatusInfo.setVersionNo(merchant.getVersionNo());
                        feedbackStatusInfo.setProcessStat(RECEIVE_SUCCESS_CODE);
                        feedbackStatusInfo.setNotes(RECEIVE_SUCCESS_DESC);
                        feedbackStatusInfos.add(feedbackStatusInfo);
                    }

                    // 其他供应商编码非R打头的供应商，判断业务类型字段，若为“920预付卡”，即为满足预付卡系统的外部供应商，数据暂存于merchantSvc，需转发给预付卡系统
                    // 20130802 增加接收广场卡租赁、广场卡联营
                } else if (StringUtils.equals(merchant.getBizTypeCode(), MdmConstants.BIZ_TYPE_HXT)
                        || StringUtils.equals(merchant.getBizTypeCode(), MdmConstants.BIZ_TYPE_GCK_RENT)
                        || StringUtils.equals(merchant.getBizTypeCode(), MdmConstants.BIZ_TYPE_GCK_JOINT)) {
                    merchantSvc.add(merchant);
                    // 不满足预付卡系统要求其他数据，，直接默认接收成功，不转发给预付卡系统
                } else {
                    FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();
                    // 返回给MDM，字段包括：供应商（商户）编码、版本号、成功标志、成功描述
                    feedbackStatusInfo.setSupplierCode(merchant.getSupplierCode());
                    feedbackStatusInfo.setVersionNo(merchant.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_SUCCESS_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_SUCCESS_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                }
            }
        }

    }

    public Result processAtom(ReceivedBodyBean bean) {

        String serial = RandomUtils.generateString(SERIAL_LENGTH);

        // 流水号信息
        LOGGER.info("[processSerial:{}] - MDM MerchantService processAtom.start ", new Object[] { serial });

        // 筛选满足预付卡系统要求的供应商（商户）数据，设置其他数据接收成功
        selectMerchantForSvc(bean);

        // 返回给MDM报文的主体
        FeedbackBodyBean fbb = new FeedbackBodyBean();

        // 若集合merchantSvc不为空，则进行相应的处理，若为空，则直接返回
        if (merchantSvc != null && merchantSvc.size() > 0) {
            // 循环，调用hessian接口，一条一条数据发送给预付卡系统
            for (Merchant merchant : merchantSvc) {
                FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();

                // 转换成预付卡系统需要的数据，个字段如下
                MerchantSynchDTO dto = new MerchantSynchDTO();
                // 预付卡实体ID，对应MDM数据的供应商（商户）编码
                dto.setEntityId(merchant.getSupplierCode());
                // 预付卡商户名称，对应MDM数据的供应商（商户）名称
                dto.setMerchantName(merchant.getSupplierName());
                // 预付卡商户编码，对应MDM数据的搜索项1
                dto.setMerchantCode(merchant.getSearchItem1());
                // 预付卡公司注册地址，对应MDM数据的公司注册地址
                dto.setMerchantAddress(merchant.getRegAddress());
                // 预付卡数据状态，对应MDM数据的搜索项2
                dto.setDataState(merchant.getSearchItem2());
                // 20130802 增加根据bizTypeCode判断联营商户
                if(StringUtils.equals(merchant.getBizTypeCode(),MdmConstants.BIZ_TYPE_GCK_JOINT)){
                    dto.setMerchantType(MdmConstants.MERCHANT_TYPE_JOINT);
                }else{
                    dto.setMerchantType(MdmConstants.MERCHANT_TYPE_DEFAULT);
                }
                
                // seam准备传递给预付卡系统的供应商（商户）数据
                LOGGER.info(
                        " [seam准备传递给预付卡的供应商（商户）数据：--供应商编码（实体ID）：{} --供应商名称（商户名称）：{} " +
                        "--搜索项1（商户编码）：{} --公司注册地址（公司注册地址）：{} --搜索项2（数据状态）：{}]  ",
                        new Object[] { merchant.getSupplierCode(), merchant.getSupplierName(),
                                merchant.getSearchItem1(), merchant.getRegAddress(), merchant.getSearchItem2() });

                try {
                    // 开始调用hessian接口，传递数据给预付卡系统
                    LOGGER.info(" Send data to SVC. invokeBizRemotingInterface.invoke start.");
                    // 20130917 对搜索项数据进行预处理，防止数据存储的问题
                    preprocessDto(dto);
                    // 调用预付卡hessian接口，把一条数据传给预付卡系统
                    manageHessianService.sendService(MDMConstCode.MDM_MERCHANT_INFO_SYNCH, dto);
                    // 返回给MDM，字段包括：供应商（商户）编码、版本号、成功标志、成功描述
                    feedbackStatusInfo.setSupplierCode(merchant.getSupplierCode());
                    feedbackStatusInfo.setVersionNo(merchant.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_SUCCESS_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_SUCCESS_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                    // 调用hessian接口成功
                    LOGGER.info(" Send data to SVC success. [SupplierCode:{}] ",
                            new Object[] { merchant.getSupplierCode() });
                    // 远程调用异常，标记该条数据接收失败
                } catch (Exception e) {
                    LOGGER.error(" seam调用hessian接口失败。 [ ErrorCode:{} | Message:{} ] ", 
                            new Object[] { SeamErrorCode.SEAM_ERROR_004, e.getMessage() });
                    LOGGER.error(
                            " [传递失败的供应商（商户）数据：--供应商编码（实体ID）：{} --供应商名称（商户名称）：{} " +
                            "--搜索项1（商户编码）：{} --公司注册地址（公司注册地址）：{} --搜索项2（数据状态）：{}]  ",
                            new Object[] { merchant.getSupplierCode(), merchant.getSupplierName(),
                                    merchant.getSearchItem1(), merchant.getRegAddress(), merchant.getSearchItem2() });

                    // 返回给MDM，字段包括：供应商（商户）编码、版本号、失败标志、失败描述
                    feedbackStatusInfo.setSupplierCode(merchant.getSupplierCode());
                    feedbackStatusInfo.setVersionNo(merchant.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_FAIL_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_FAIL_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                }
            }
        }

        LOGGER.info("[processSerial:{}] - MDM MerchantService processAtom.end", new Object[] { serial });

        // 设置返回报文主体，分发系统为SVC，还有接收数据是否成功的标志
        fbb.setDistributeSys("SVC");
        fbb.setFeedbackStatusInfo(feedbackStatusInfos);
        // 组装报文，返回给MDM
        feeBack(fbb, serial);

        return null;
    }

    // 组装报文返回给ESB
    public Result feeBack(FeedbackBodyBean bean, String serial) {
        merchantSendService.asynSend(bean);
        return null;
    }

    /**
     * 
     * 针对搜索项的数据进行预处理
     * 
     * @param dto
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void preprocessDto(MerchantSynchDTO dto) {
        // 搜索项2处理：除了0，均视作有效
        String dataState = dto.getDataState();
        if (!StringUtils.equals(dataState, MdmConstants.DATA_STATE_DISABLED)) {
            dto.setDataState(MdmConstants.DATA_STATE_ENABLED);
        }
    }

    public ManageHessianService getManageHessianService() {
        return manageHessianService;
    }

    public void setManageHessianService(ManageHessianService manageHessianService) {
        this.manageHessianService = manageHessianService;
    }

    /**
     * @return the merchantSendService
     */
    public MdmMerchantSendService getMerchantSendService() {
        return merchantSendService;
    }

    /**
     * 由EsbEI注入
     */
    @EsbEIWired
    public void setMerchantSendService(MdmMerchantSendService merchantSendService) {
        this.merchantSendService = merchantSendService;
    }

}
