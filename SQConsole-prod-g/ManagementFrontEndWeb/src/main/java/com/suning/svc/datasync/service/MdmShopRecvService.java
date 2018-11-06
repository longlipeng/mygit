/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: MdmShopRecvService.java
 * Author:   陆万川
 * Date:     2013-4-25 下午7:40:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.datasync.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.constants.MDMConstCode;
import com.huateng.univer.synch.dto.ShopSynchDTO;
import com.suning.framework.lang.Result;
import com.suning.rsc.mqservice.ei.annotation.EsbEIWired;
import com.suning.svc.datasync.common.SeamErrorCode;
import com.suning.svc.datasync.utils.RandomUtils;
import com.suning.svc.datasync.xmlbean.mdm.shop.FeedbackBodyBean;
import com.suning.svc.datasync.xmlbean.mdm.shop.FeedbackStatusInfo;
import com.suning.svc.datasync.xmlbean.mdm.shop.ReceivedBodyBean;
import com.suning.svc.datasync.xmlbean.mdm.shop.Shop;

/**
 * 
 * 〈门店数据处理〉<br>
 * 〈MDM通过ESB下发地点（含中心仓、门店和网点）数据〉<br>
 * 〈1、筛选满足预付卡系统的门店数据〉<br>
 * 〈2、将数据传送给预付卡系统〉<br>
 * 〈3、将处理结果返回给MDM〉<br>
 * 
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MdmShopRecvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmShopRecvService.class);

    /**
     * 门店代码：用于判断地点（含中心仓、门店和网点）数据中满足预付卡系统要求的门店数据的代码
     */
    private static final String SHOP_CODE = "2";

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
     * 流水号长度，6位
     */
    private static final int SERIAL_LENGTH = 6;

    private MdmShopSendService shopSendService;

    /**
     * 调用预付卡系统的hessian服务，用于传地点（含中心仓、门店和网点）数据给预付卡系统
     */
    private ManageHessianService manageHessianService;

    /**
     * 暂存MDM下发的所有地点（含中心仓、门店和网点）数据
     */
    private ArrayList<Shop> shops;

    /**
     * 暂存MDM下发的满足预付卡系统要求的门店数据
     */
    private ArrayList<Shop> shopSvc;

    /**
     * 存放MDM下发的所有地点（含中心仓、门店和网点）数据对应的处理结果，用于返回给MDM
     */
    private ArrayList<FeedbackStatusInfo> feedbackStatusInfos;

    /**
     * 筛选满足预付卡系统的地点（含中心仓、门店和网点）数据<br>
     * 〈筛选结果暂存于shops，并将不满足预付卡系统要求其他数据的返回值设置为接收成功〉
     * 
     * @param bean 所有地点（含中心仓、门店和网点）数据的报文
     */
    private void selectShopForSvc(ReceivedBodyBean bean) {

        // 将接收到的所有数据暂存于shops
        shops = bean.getShop();
        shopSvc = new ArrayList<Shop>();
        feedbackStatusInfos = new ArrayList<FeedbackStatusInfo>();

        // 若集合shops不为空，则进行相应的处理，若为空，则直接返回
        if (shops != null && shops.size() > 0) {
            // 循环，筛选满足预付卡系统的门店数据
            for (Shop shop : shops) {
                // 满足预付卡系统的数据暂存于shopSvc
                if (SHOP_CODE.equals(shop.getPlantTypeCode())) {
                    shopSvc.add(shop);

                    // 不满足预付卡系统要求其他数据的返回值设置为接收成功
                } else {
                    FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();
                    // 返回给MDM，字段包括：地点（门店）编码、版本号、成功标志、成功描述
                    feedbackStatusInfo.setPlantCode(shop.getPlantCode());
                    feedbackStatusInfo.setVersionNo(shop.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_SUCCESS_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_SUCCESS_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                }
            }
        }

    }

    public Result processAttom(ReceivedBodyBean bean) {

        String serial = RandomUtils.generateString(SERIAL_LENGTH);

        // 流水号信息
        LOGGER.info("[processSerial:{}] - MDM ShopService processAtom.start ", new Object[] { serial });

        // 筛选满足预付卡系统要求的地点（门店）数据，设置其他数据接收成功
        selectShopForSvc(bean);

        // 返回给MDM报文的主体
        FeedbackBodyBean fbb = new FeedbackBodyBean();

        // 若集合shopSvc不为空，则进行相应的处理，若为空，则直接返回
        if (shopSvc != null && shopSvc.size() > 0) {
            // 循环，调用hessian接口，一条一条数据发送给预付卡系统
            for (Shop shop : shopSvc) {
                FeedbackStatusInfo feedbackStatusInfo = new FeedbackStatusInfo();

                // 转换成预付卡系统需要的数据，个字段如下
                ShopSynchDTO dto = new ShopSynchDTO();
                // 预付卡门店Id，对应MDM数据的地点代码
                dto.setShopId(shop.getPlantCode());
                // 预付卡商户实体Id，对应MDM数据的地点所属公司代码
                dto.setEntityId("RE" + shop.getCoCode());
                // 预付卡门店名称，对应MDM数据的地点名称（含中心仓、门店和网点）
                dto.setShopName(shop.getPlantName());
                // 预付卡门店状态，对应MDM数据的地点状态
                dto.setShopState(shop.getPlantStatCode());

                // seam准备传递给预付卡系统的地点（门店）数据
                LOGGER.info(
                        " [seam准备传递给预付卡的地点（门店）数据：" +
                        "--地点代码（门店ID）：{} " +
                        "--地点所属公司代码（商户实体Id）：{} " +
                        "--地点名称（门店名称）：{} " +
                        "--地点状态（门店状态）：{} ]  ",
                        new Object[] { shop.getPlantCode(), "RE" + shop.getCoCode(), 
                                shop.getPlantName(), shop.getPlantStatCode() });

                try {
                    // 开始调用hessian接口，传递数据给预付卡系统
                    LOGGER.info(" Send data to SVC. invokeBizRemotingInterface.invoke start.");
                    // 调用预付卡hessian接口，把一条数据传给预付卡系统
                    manageHessianService.sendService(MDMConstCode.MDM_SHOP_INFO_SYNCH, dto);
                    // 返回给MDM，字段包括：地点（门店）编码、版本号、成功标志、成功描述
                    feedbackStatusInfo.setPlantCode(shop.getPlantCode());
                    feedbackStatusInfo.setVersionNo(shop.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_SUCCESS_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_SUCCESS_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                    // 调用hessian接口成功
                    LOGGER.info(" Send data to SVC success. [PlantCode:{}] ", new Object[] { shop.getPlantCode() });
                    // 远程调用异常，标记该条数据接收失败
                } catch (Exception e) {
                    LOGGER.error(" seam调用hessian接口失败。 [ ErrorCode:{} | Message:{} ] ", 
                            new Object[] { SeamErrorCode.SEAM_ERROR_004, e.getMessage() });
                    LOGGER.error(
                            " [传递失败的地点（门店）数据：" +
                            "--地点代码（门店ID）：{} " +
                            "--地点所属公司代码（商户实体Id）：{} " +
                            "--地点名称（门店名称）：{} " +
                            "--地点状态（门店状态）：{} ]  ",
                            new Object[] { shop.getPlantCode(), "RE" + shop.getCoCode(), 
                                    shop.getPlantName(), shop.getPlantStatCode() });

                    // 返回给MDM，字段包括：地点（门店）编码、版本号、失败标志、失败描述
                    feedbackStatusInfo.setPlantCode(shop.getPlantCode());
                    feedbackStatusInfo.setVersionNo(shop.getVersionNo());
                    feedbackStatusInfo.setProcessStat(RECEIVE_FAIL_CODE);
                    feedbackStatusInfo.setNotes(RECEIVE_FAIL_DESC);
                    feedbackStatusInfos.add(feedbackStatusInfo);
                }
            }
        }

        LOGGER.info("[processSerial:{}] - MDM ShopService processAtom.end", new Object[] { serial });

        // 设置返回报文主体，分发系统为SVC，还有接收数据是否成功的标志
        fbb.setDistributeSys("SVC");
        fbb.setFeedbackStatusInfo(feedbackStatusInfos);
        // 组装报文，返回给MDM
        feeBack(fbb, serial);

        return null;
    }

    // 组装报文返回给ESB
    public Result feeBack(FeedbackBodyBean bean, String serial) {
        shopSendService.asynSend(bean);
        return null;
    }

    public MdmShopSendService getShopSendService() {
        return shopSendService;
    }

    /**
     * 由EsbEI注入
     */
    @EsbEIWired
    public void setShopSendService(MdmShopSendService shopSendService) {
        this.shopSendService = shopSendService;
    }

    public ManageHessianService getManageHessianService() {
        return manageHessianService;
    }

    public void setManageHessianService(ManageHessianService manageHessianService) {
        this.manageHessianService = manageHessianService;
    }

}
