/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SoaContractRecvService.java
 * Author:   luwanchuan
 * Date:     2013-4-10 下午02:41:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.constants.MDMConstCode;
import com.huateng.univer.synch.dto.AcctypeContractSynchDTO;
import com.huateng.univer.synch.dto.MerchantContractSynchDTO;
import com.suning.framework.lang.Result;
import com.suning.svc.datasync.utils.RandomUtils;
import com.suning.svc.datasync.xmlbean.soa.contract.AcctypeInfo;
import com.suning.svc.datasync.xmlbean.soa.contract.ContractInfo;
import com.suning.svc.datasync.xmlbean.soa.contract.ReceivedBodyBean;

/**
 * 商户合同数据处理
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SoaContractRecvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaContractRecvService.class);

    /**
     * 流水号长度，6位
     */
    private static final int SERIAL_LENGTH = 6;

    /**
     * 最大重试调用hessian接口次数，3次
     */
    private static final int RETRY_TIMES = 3;

    /**
     * 调用预付卡系统的hessian服务，用于传商户合同数据给预付卡系统
     */
    private ManageHessianService manageHessianService;

    /**
     * 合同编号部分
     */
    private List<ContractInfo> contractInfos;
    
    /**
     * 账户号、结算扣率部分
     */
    private List<AcctypeInfo> acctypeInfos;

    /**
     * 处理SOA下发的商户合同数据，转发给预付卡系统
     * 
     * @param bean 接收的商户合同数据
     * @return
     */
    public Result processAtom(ReceivedBodyBean bean) {

        String serial = RandomUtils.generateString(SERIAL_LENGTH);
        // 存储每个合同里对应的账户号、结算扣率，用于打印日志
        StringBuilder strAccInfo = new StringBuilder();

        // 流水号信息
        LOGGER.info("[processSerial:{}] - SOA ContractService processAtom.start ", new Object[] { serial });

        contractInfos = bean.getContractInfo();
        acctypeInfos = bean.getAcctypeInfo();
        
        // 下发的商户合同数据不为空，则调用hessian接口，传递数据给预付卡系统
        if (contractInfos != null && acctypeInfos != null && contractInfos.size() > 0 && acctypeInfos.size() > 0) {
            
            ContractInfo contractInfo = contractInfos.get(0);
            List<AcctypeContractSynchDTO> acDtoList = new ArrayList<AcctypeContractSynchDTO>();
            
            for (AcctypeInfo acctypeInfo : acctypeInfos) {
                AcctypeContractSynchDTO acDto = new AcctypeContractSynchDTO();
                acDto.setAcctypeId(acctypeInfo.getAcctypeId());
                acDto.setCalculationRuleNo(acctypeInfo.getCalculationRuleNo());
                acDtoList.add(acDto);
                
                // 存储每个合同里对应的账户号、结算扣率，用于打印日志
                strAccInfo.append(" AcctypeId: ");
                strAccInfo.append(acctypeInfo.getAcctypeId());
                strAccInfo.append(", CalculationRuleNo: ");
                strAccInfo.append(acctypeInfo.getCalculationRuleNo());
                strAccInfo.append(";");
            }
            
            MerchantContractSynchDTO mcDto = new MerchantContractSynchDTO();
            mcDto.setConsumerContractId(contractInfo.getContractCode());
            mcDto.setContractBuyer(contractInfo.getSupplierCode());
            mcDto.setMerchantName(contractInfo.getSupplierName());
            mcDto.setPeriodRuleNo(contractInfo.getPeriodRuleNo());
            mcDto.setContractStartDate(contractInfo.getContStartDate());
            mcDto.setContractEndDate(contractInfo.getContEndDate());
            mcDto.setContractCreateDate(contractInfo.getContReDate());
            mcDto.setInitiatorCompanyCode(contractInfo.getComCode());
            mcDto.setInitiatorCompanyName(contractInfo.getComName());
            mcDto.setAcctypeContractSynchDTOs(acDtoList);

            // seam准备传递给预付卡系统的商户合同数据
            LOGGER.info(
                    " [seam准备传递给预付卡的商户合同数据：--合同编码（ContractId）：{} --商户编码（ContractBuyer）：{} --商户名称（MerchantName）：{} " +
                    "--结算周期规则号（PeriodRuleNo）：{} --合同开始日期（ContractStartDate）：{} --合同结束日期（ContractEndDate）：{}" +
                    " --合同签订日期（ContractReDate）：{} --发起公司代码（ComCode）：{} --发起公司名称（ComName）：{}" +
                    " --账户号（AcctypeId）与结算扣率（CalculationRuleNo）：{} ]  ",
                    new Object[] { contractInfo.getContractCode(), contractInfo.getSupplierCode(),
                            contractInfo.getSupplierName(), contractInfo.getPeriodRuleNo(),
                            contractInfo.getContStartDate(), contractInfo.getContEndDate(),
                            contractInfo.getContReDate(), contractInfo.getComCode(), 
                            contractInfo.getComName(), strAccInfo });

            invokeWithRetry(mcDto);

        }
        LOGGER.info("[processSerial:{}] - SOA ContractService processAtom.end", new Object[] { serial });
        return null;
    }

    /**
     * 若调用Hessian接口异常，重试3次调用
     * 
     * @param mcDto 商户合同数据，传给预付卡系统
     */
    private void invokeWithRetry(MerchantContractSynchDTO mcDto) {
        // 剩余重试次数
        int retryCounter = RETRY_TIMES;
        LOGGER.info(" Send data to SVC. invokeBizRemotingInterface.invoke start.");
        while (retryCounter > 0) {
            try {
                manageHessianService.sendService(MDMConstCode.MDM_MERCHANT_CONTRACT_SYNCH, mcDto);
                LOGGER.info(" Send data to SVC success. [合同编码（ContractId）：{}] ",
                        new Object[] { mcDto.getConsumerContractId() });
                break;
            } catch (Exception e) {
                retryCounter--;
                LOGGER.error(" [ Message:{} ] ", new Object[] { e.getMessage() });
                LOGGER.error("访问Hessian服务异常，剩余重试次数为{}", new Object[] { retryCounter });
            }
        }
        if (!(retryCounter > 0)) {
            LOGGER.error(" seam调用hessian接口失败. 传递失败的商户合同数据为：[合同编码（ContractId）：{}]",
                    new Object[] { mcDto.getConsumerContractId() });
        }
    }

    /**
     * @return the manageHessianService
     */
    public ManageHessianService getManageHessianService() {
        return manageHessianService;
    }

    /**
     * @param manageHessianService the manageHessianService to set
     */
    public void setManageHessianService(ManageHessianService manageHessianService) {
        this.manageHessianService = manageHessianService;
    }

}
