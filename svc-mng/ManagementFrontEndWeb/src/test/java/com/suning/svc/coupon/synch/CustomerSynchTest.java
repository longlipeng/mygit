/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: CustomerSynchTest.java
 * Author:   12073942
 * Date:     2013-8-12 下午9:12:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.synch;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.univer.synch.ManageHessianService;
import com.huateng.univer.synch.dto.BankInfoDTO;
import com.huateng.univer.synch.dto.ContactInfoDTO;
import com.huateng.univer.synch.dto.CustomerSynchDTO;

/**
 * 客户同步测试
 * 
 * @author 12073942
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
public class CustomerSynchTest {

    @Autowired
    @Qualifier("manageHessianServiceTarget")
    private ManageHessianService manageHessianService;

    @Test
    public void test() {

        String[] entityIds = { "0070000001", "0070000008", "0070000012", "0070000017", "0070000018", "0070000019",
                "0070000023", "0070000027", "0070000033", "0070000039", "0070000043", "0070000057", "0070000058",
                "0070000078", "0070000098", "0070000099", "0070000108", "0070000123", "0070000135", "0070000177",
                "0070000178", "0070000179", "0070000234", "0070043000", "0070043210", "0070051200", "0070051250",
                "0070060013", "0070061003", "0070061010", "0070061200", "0070065000", "0071001112", "0071001113",
                "0071001114", "0071001115", "0071001116", "0071001117", "0071001118", "0071001119", "0071001120",
                "0071001121", "0071001122", "0071001136", "0071001200", "0071001330", "0071008521", "0071231000",
                "0072001088", "0072001234", "0072001235", "0072002388", "0072002488", "0072004521", "0072004800",
                "0072004801", "0072004811", "0072004912", "0072004913", "0072004914", "0072004915", "0072004916",
                "0072004917", "0072004918", "0072008741", "0072008815", "0072008836", "0072008916", "0072009942",
                "0072009948", "0074001256", "0074101003", "0075000250", "0075000251", "0075000777", "0075000778",
                "0075100035", "0075221000", "0075221008", "0075221018", "0075432550", "0078000001", "0078000011",
                "0078000012", "0078000021", "0078000031", "0078000041", "0078000050", "0078000051", "0078000052",
                "RE1001", "RE1002", "RE1003", "RE1004", "RE1005", "RE1006", "RE1007", "RE1008", "RE1009", "RE1010",
                "RE1011", "RE1012", "RE1013", "RE1014", "RE1015", "RE1016", "RE1017", "RE1018", "RE1019", "RE1020",
                "RE1021", "RE1022", "RE1023", "RE1033", "RE1034", "RE1035", "RE1036", "RE1052", "RE1061", "RE1087",
                "RE1120" };
        for (String entityId : entityIds) {
            CustomerSynchDTO cdto = new CustomerSynchDTO();
            // 共测试26个字段
            cdto.setEntityId(entityId);
            cdto.setCustomerName("苏宁电器");
            cdto.setCustomerAddress("Nanjing");
            cdto.setCustomerFax("Fax");
            cdto.setCustomerPostcode("210000");
            cdto.setCustomerTelephone("02566996699");
            cdto.setTaxCode("TaxCode");
            cdto.setTaxNoType("1234");
            cdto.setTaxTypeCode("1234");
            cdto.setTaxTypeDesc("TaxTypeDesc");
            cdto.setRegAddress("注册地址");
            cdto.setCorpCredId("法人id");
            cdto.setCorpName("法人代表");
            cdto.setCoTypeCode("公司类型");
            cdto.setCityPostCode("213334");
            cdto.setMailBox("1234.cn");
            cdto.setLicenseEndValidity("2014-11-11");
            cdto.setLicenseId("254851145745134154");
            cdto.setLicenseStaValidity("2012-11-11");
            cdto.setPaymentTypeDesc("银行转账方式");
            cdto.setRegiCapital("300亿美金");

            // 复制银行列表到DTO
            List<BankInfoDTO> bankInfoDTOs = cdto.getBankInfoList();
            BankInfoDTO bankInfoDTO = new BankInfoDTO();
            // 取银行名称和银行账户2个字段
            bankInfoDTO.setBranchBankName("银行名称1");
            bankInfoDTO.setBankAcctCode("");
            bankInfoDTOs.add(bankInfoDTO);

            bankInfoDTO = new BankInfoDTO();
            // 取银行名称和银行账户2个字段
            bankInfoDTO.setBranchBankName("银行名称2");
            bankInfoDTO.setBankAcctCode("银行账号2");
            bankInfoDTOs.add(bankInfoDTO);

            // 复制联系人列表到DTO
            List<ContactInfoDTO> contactInfoDTOs = cdto.getContactInfoList();
            ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
            // 取联系人名称、联系人电话、联系人手机3个字段
            contactInfoDTO.setContactName("张三");
            contactInfoDTO.setContactMobilePhone("手机");
            contactInfoDTO.setContactTelephone("电话");
            contactInfoDTOs.add(contactInfoDTO);

            contactInfoDTO = new ContactInfoDTO();
            // 取联系人名称、联系人电话、联系人手机3个字段
            contactInfoDTO.setContactName("李四");
            contactInfoDTO.setContactMobilePhone("手机2");
            contactInfoDTO.setContactTelephone("电话2");
            contactInfoDTOs.add(contactInfoDTO);

            OperationResult crlt = manageHessianService.sendService("X2013102501", cdto);
            if (crlt.getErrMessage() == null) {
                System.out.println("供应商同步到华夏通客户成功");
            } else {
                System.out.println("供应商同步到华夏通客户失败：" + crlt.getErrMessage());
            }

        }
    }

}
