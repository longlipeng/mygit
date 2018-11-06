package com.huateng.framework.webservice;

import static com.allinfinance.framework.constant.ConstCode.CARDHOLDER_SERVICE_VIEW;
import static com.allinfinance.framework.constant.ConstCode.CARDMANAGEMENT_OPERATION;
import static com.allinfinance.framework.constant.ConstCode.CARDMANAGEMENT_QUERY;
import static com.allinfinance.framework.constant.ConstCode.CARDMANAGEMENT_SERVICE_INQUERYMEMBERINFO;
import static com.allinfinance.framework.constant.ConstCode.CARDMANAGEMENT_VIEW_ORDER;
import static com.allinfinance.framework.constant.ConstCode.CARD_QUERY_FORTRANSFER;
import static com.allinfinance.framework.constant.ConstCode.CHANGE_CARD_ORDER_ORIG_CARD_DEL;
import static com.allinfinance.framework.constant.ConstCode.CUSCARDHOLDER_SERVICE_VIEW;
import static com.allinfinance.framework.constant.ConstCode.CUSTOMER_BLACK_RISK_JUDGE;
import static com.allinfinance.framework.constant.ConstCode.CUSTOMER_DEPARTMENT_INQUERY;
import static com.allinfinance.framework.constant.ConstCode.CUSTOMER_SERVICE_VIEW;
import static com.allinfinance.framework.constant.ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_VIEW;
import static com.allinfinance.framework.constant.ConstCode.FRTCODE_SERVICE_SELL_ORDER_GET;
import static com.allinfinance.framework.constant.ConstCode.ISSUER_CONTRACT_QUERY;
import static com.allinfinance.framework.constant.ConstCode.ISSUER_CONTRACT_UPDATE;
import static com.allinfinance.framework.constant.ConstCode.MAKECARD_STATE_COMPLATE;
import static com.allinfinance.framework.constant.ConstCode.OPS_TRANS_QUERY_BY_ORDER;
import static com.allinfinance.framework.constant.ConstCode.ORDER_CARD_LIST_QUERY_FOR_CHECKCARD;
import static com.allinfinance.framework.constant.ConstCode.ORDER_INQUERY_AT_ORDER_PAYMENT;
import static com.allinfinance.framework.constant.ConstCode.ORDER_INQUERY_AT_SELL_INPUT;
import static com.allinfinance.framework.constant.ConstCode.ORDER_INQUERY_BY_SELLER;
import static com.allinfinance.framework.constant.ConstCode.ORDER_INQUERY_PRINT_CERTIFICATE;
import static com.allinfinance.framework.constant.ConstCode.PRODUCT_SERVICE_VIEW;
import static com.allinfinance.framework.constant.ConstCode.QUERY_ORDER_PAYMENTS;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_ACCEP_CARD_CHECK;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_ACCEP_INQUERY_LIST;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_CARD_BACK_CHECK;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_CARD_STATE_MODIFY;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_CARD_SUBMIT_ORDER;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_DELETE_ORIG_CARD;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_EDIT;
import static com.allinfinance.framework.constant.ConstCode.RANSOM_ORDER_INSERT_ORIG_CARD;
import static com.allinfinance.framework.constant.ConstCode.SELLER_CONTRACT_SERVICE_INQUERY;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_CANCEL_AT_INPUT;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_CHANGECARD_GET_STOCK;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_CONFIRM_AT_ORDER_PAYMENT;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_INQUERY_PRINT_PAYMENT;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_SUBMIT_AT_CONFIRM;
import static com.allinfinance.framework.constant.ConstCode.SELL_ORDER_SUBMIT_AT_ORDER_PAYMENT;
import static com.allinfinance.framework.constant.ConstCode.STOCK_ORDER_INQUERY;
import static com.allinfinance.framework.constant.ConstCode.STOCK_ORDER_READY_TO_CARD_LIST;
import static com.allinfinance.framework.constant.ConstCode.VALID_PERIOD_RULE_LIST;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.allinfinance.framework.constant.ConstCode;

public class ServiceTarget implements Serializable {

    /**
     * 新增加代码 请将操作类型更新到数据库中（tb_txn_code）
     */
    private static final long serialVersionUID = 1762577180254743096L;

    private static Map<String, String> map = new HashMap<String, String>();
    static {
        // 服务信息 (accTypeService 未找到,暂时注释掉)
        // map.put(FEERULE_SERVICE_DELETE, "accTypeService!inqueryAccType");
        // 获取客户信息
        map.put("2001020201", "customerService!getCustomerById");

        /** 持卡人信息管理 */
        // 持卡人信息查询页面初始化
        map.put("2001030100", "cardholderService!initCardholder");
        // 持卡人信息查询
        map.put("2001030200", "cardholderService!inquery");
     // 持卡人信息审核查询
        map.put("2015091701", "cardholderService!inqueryByCheck");
        // 持卡人信息添加
        map.put("2001030300", "cardholderService!insert");
        // 记录记名库存卡的持卡人信息
        map.put("2001030302", "cardholderService!insertSignCardHolder");
        /**
         * 根据订单ID 查出订单支付信息表
         */
        map.put(QUERY_ORDER_PAYMENTS, "sellOrderPaymentService!queryOrderPayments");
       
        /**
         * 
         */
        map.put(CUSTOMER_BLACK_RISK_JUDGE, "blacklistAndriskService!judge");

        // 添加持卡人 返回持卡人信息
        map.put("2001030301", "cardholderService!insertCardholderDTO");
        // 持卡人信息查看
        map.put(CARDHOLDER_SERVICE_VIEW, "cardholderService!view");
        // 持卡人信息更新
        map.put("2001030500", "cardholderService!update");
        //个人持卡人信息删除
        map.put("2001030600", "cardholderService!delete");
        // 持卡人信息导入
        map.put("2001030700", "cardholderService!insertCardholder");
        // 订单编辑中快捷导入持卡人
        map.put("2001030710", "cardholderService!insertCardholderReturnIds");
        // (暂未添加)
        map.put("2001030800", "listAllCardService!getAllCard");
        // 持卡人商户关联
        map.put("2001030900", "cardholderService!updateCust");
        //按持卡人身份证查找持卡人
        map.put("2001030401", "cardholderService!selectByCardholderIdNo");

        /** 制卡商管理 */
        // 制卡商查询
        map.put("2004030100", "cardCompanyService!inquery");
        // 制卡商添加
        map.put("2004030200", "cardCompanyService!insert");
        // 制卡商更新
        map.put("2004030300", "cardCompanyService!update");
        // 制卡商查看
        map.put("2004030400", "cardCompanyService!view");
        // 制卡商状态修改
        map.put("2004030500", "cardCompanyService!modifyCardCompany");

        /** 角色权限 */
        // 查询菜单信息
        map.put("4004010000", "roleService!selectResource");
        // 取得所有菜单信息
        map.put("4004010099", "roleService!getAllResource");
        // 查询角色信息
        map.put("4004020000", "roleService!inqueryRole");
        // 插入角色信息
        map.put("4004030000", "roleService!insertRole");
        // 修改角色信息
        map.put("4004040000", "roleService!updateRole");
        // 查看角色信息
        map.put("4004050000", "roleService!viewRole");
        // 删除角色信息
        map.put("4004060000", "roleService!deleteRole");
        // 查询功能菜单的公共方法
        map.put("4004070000", "roleService!selectIssueResource");
        // 查找用户
        // (暂未添加)
        map.put("4005000001", "userService!findUser");
        // 用户信息查询
        map.put("4005010000", "userService!inquery");
        // 用户信息插入
        map.put("4005020000", "userService!insertUser");
        // 用户信息查看
        map.put("4005030000", "userService!viewUser");
        // 添加用户角色信息
        map.put("4005040000", "userService!addRole");
        // 删除用户角色信息
        map.put("4005050000", "userService!deleteRole");
        // 更新用户角色信息
        map.put("4005060000", "userService!updateUser");
        // 删除用户
        map.put("4005070000", "userService!deleteUser");
        // 修改用户密码
        map.put("4005080000", "userService!modifyPassword");
        // 检查用户名是否重复
        map.put("4005090000", "userService!checkUserName");
        /**
         * 系统参数
         */
        // 查询系统参数
        map.put("4006010000", "systemParameterService!inquerySystemParamter");
        // 查看系统参数
        map.put("4006020000", "systemParameterService!viewSystemParamter");
        // 更新系统参数
        map.put("4006030000", "systemParameterService!updateSystemParamter");

        /**
         * 机构系统参数
         */
        // 查询机构系统参数
        map.put("4006031000", "entitySystemParameterService!inqueryEntitySystemParamter");
        // 查看机构系统参数
        map.put(ENTITY_SYSTEMPARAMETER_SERVICE_VIEW, "entitySystemParameterService!viewEntitySystemParamter");
        // 更新机构系统参数
        map.put("4006033000", "entitySystemParameterService!updateEntitySystemParamter");

        // 系统日志
        map.put("4006040000", "systemLogService!inquerySystemLog");
        // 查看系统日志
        map.put("4006050000", "systemLogService!loadSysLog");
        // (暂未添加)
        map.put("4006050001", "systemLogService!inqueryCardStockOprLog");
        // 查询字典信息
        map.put("4001000000", "dictInfoService!getDictInfo");
        // 查询机构字典信息
        map.put("4001000010", "dictInfoService!getEntityDictInfo");
        // 获取用户信息
        map.put("4001000001", "userService!findUser");
        // 初始化系统参数
        map.put("4001000002", "systemParameterService!getSystemParameter");
        // 初始化机构系统参数
        map.put("4001000020", "entitySystemParameterService!getEntitySystemParameter");

        /** 数据字典 */
        // 查询数据字典信息
        map.put("2006001000", "dictInfoService!inquery");
        // 添加数据字典信息
        map.put("2006002000", "dictInfoService!add");
        // 查看数据字典信息
        map.put("2006003000", "dictInfoService!view");
        // 编辑数据字典信息
        map.put("2006004000", "dictInfoService!edit");
        // 删除数据字典信息
        map.put("2006005000", "dictInfoService!del");

        /** 机构数据字典 */
        // 查询机构数据字典信息
        map.put("2006005100", "entityDictInfoService!inquery");
        // 插入机构数据字典信息
        map.put("2006005200", "entityDictInfoService!insert");
        // 查看机构数据字典信息
        map.put("2006005300", "entityDictInfoService!view");
        // 编辑机构数据字典信息
        map.put("2006005400", "entityDictInfoService!edit");
        // 删除机构数据字典信息
        map.put("2006005500", "entityDictInfoService!del");

        /**
         * 卡面信息
         */
        // 查询卡面信息
        map.put("1002110000", "cardLayoutService!inqueryCardLayout");
        // 初始化卡面信息
        map.put("4008020000", "cardLayoutService!initCardLayout");
        // 插入卡面信息
        map.put("4008030000", "cardLayoutService!insertCardLayout");
        // 查看卡面信息
        map.put("4008060000", "cardLayoutService!viewCardLayout");
        // 编辑卡面信息
        map.put("4008040000", "cardLayoutService!updateCardLayout");
        // 删除卡面信息
        map.put("4008050000", "cardLayoutService!deleteCardLayout");
        // 调用卡面信息
        map.put("4008070000", "cardLayoutService!loadCardLayoutForEdit");

        /**
         * 包装信息
         */
        // 查询包装信息
        map.put(ConstCode.PACKAGE_SERVICE_INQUERY, "productPackageService!inqueryPackAge");
        // 初始化包装信息
        map.put("4007020000", "productPackageService!initPackage");
        // 添加包装信息
        map.put("4007030000", "productPackageService!insertPackage");
        // 编辑包装信息
        map.put("4007040000", "productPackageService!updatePackage");
        // 删除包装信息
        map.put("4007050000", "productPackageService!deletePackage");
        // 查看包装信息
        map.put("4007060000", "productPackageService!viewPackage");
        // 查看包装信息是否可以被编辑
        map.put("4007020118", "productPackageService!EditViewPackage");
        /**
         * 账户信息
         */
        // 查询账户类型
        map.put("1001010000", "accountService!inqueryAccType");
        // 初始化账户类型
        map.put("1001020000", "accountService!initAccType");
        // 添加账户类型
        map.put("1001030000", "accountService!insertAccType");
        // 查看账户类型
        map.put("1001040000", "accountService!viewAccType");
        // 编辑账户类型
        map.put("1001050000", "accountService!updateAccType");
        // 删除账户类型
        map.put("1001060000", "accountService!deleteAccType");
        // 账户中获取发行机构
        map.put("4004041000", "accountService!getIssuerInfoList");
        // 查看账户信息是否可以被编辑
        map.put("1001080000", "accountService!EditViewAccType");

        /**
         * 产品信息
         */
        // 查询产品信息
        map.put("1002010000", "productService!inqueryProduct");
        // 暂未记录
        map.put("1002010001", "productService!inqueryProductC");
        // 暂未记录
        map.put("1002010002", "productService!inqueryProductD");
        // 暂未记录
        map.put("1002010003", "productService!getProductsByEntityId");
        // 获取营销机构下产品
        map.put("1002010004", "productService!getProductsBySellId");
        // 添加产品信息
        map.put("1002030000", "productService!insertProduct");
        // 产品中获取发行机构信息
        map.put("4004051000", "productService!getProductIssuerInfoList");
        // 编辑产品信息
        map.put("1002040000", "productService!updateProduct");
        /**
         * 产品卡bin
         */
        // 产品选择卡bin信息
        map.put("1002040003", "productCardBinService!choiceProductCardBin");
        // 添加卡bin信息
        map.put("1002040004", "productCardBinService!insert");
        // 修改卡Bin状态
        map.put("1002040005", "productCardBinService!modifyDefaultState");
        // 删除卡Bin
        map.put("1002040006", "productCardBinService!delete");
        // 删除产品
        map.put("1002050000", "productService!deleteProduct");
        // 查看产品信息
        map.put(PRODUCT_SERVICE_VIEW, "productService!viewProduct");
        // 添加合同时查看产品
        map.put("1002061000", "productService!viewProductForContract");
        // 添加卡面信息
        map.put("1002120000", "productService!insertProdLayout");
        // 删除卡面信息
        map.put("1002130000", "productService!deleteProdLayout");
        // 添加面额信息
        map.put("1002140000", "productService!inserProdFaceValue");
        // 删除面额信息
        map.put("1002150000", "productService!deleteProdFaceValue");
        // 添加包装信息
        map.put("1002170000", "productService!insertProdPackage");
        // 删除包装信息
        map.put("1002180000", "productService!deleteProdPackage");
        // 添加产品账户类型
        map.put("1002070000", "productAccountService!inserProductAcctype");
        // 查看产品账户类型
        map.put("1002080000", "productAccountService!viewProductAcctype");
        // 编辑产品账户类型
        map.put("1002090000", "productAccountService!updateProductAcctype");
        // 删除产品账户类型
        map.put("1002100000", "productService!deleteAcctype");
        // 产品账户关联信息查询
        map.put("1001111000", "productService!inqueryProductRelAccount");

        /**
         * 发行机构信息
         */
        // 查询发行机构信息
        map.put("4003010000", "issuerService!inqueryIssuer");
        // 查询发行机构信息
        map.put("4003020000", "issuerService!listIssuer");
        // 添加发行机构信息
        map.put("4003030000", "issuerService!insertIssuer");
        // 查看发行机构信息
        map.put("4003060000", "issuerService!viewIssuer");
        // 查询卡BIN信息
        map.put("4003061000", "issuerService!getCardSerialNumberDTOs");
        // 添加发票公司信息
        map.put("4088880000", "issuerService!insertCompanyInfo");
        // 删除发票公司信息
        map.put("4088880001", "issuerService!deleteCompanyInfo");
        // 查看发票公司信息
        map.put("4088881000", "issuerService!viewCompanyInfo");
        // 编辑发票公司信息
        map.put("4088881001", "issuerService!updateCompanyInfo");
        // 添加客户发票地址信息
        map.put("4088880002", "issuerService!insertInvoiceAddress");
        // 查看客户发票地址信息
        map.put("2001022100", "issuerService!viewInvoiceAddress");
        // 编辑客户发票地址信息
        map.put("2001022200", "issuerService!updateInvoiceAddress");
        // 删除客户发票地址信息
        map.put("4088880003", "issuerService!deleteAdressInfo");
        // 添加联系人信息
        map.put("4088880005", "issuerService!insertContactInfo");
        // 删除联系人信息
        map.put("4088880006", "issuerService!deleteContactInfo");
        // 查看联系人信息
        map.put("4088881005", "issuerService!viewContactInfo");
        // 编辑联系人信息
        map.put("4088881006", "issuerService!updateContactInfo");
        // 添加部门信息
        map.put("4088880007", "issuerService!insertDepartMentInfo");
        // 删除部门信息
        map.put("4088880008", "issuerService!deleteDepartMentInfo");
        // 查看部门信息
        map.put("4088881007", "issuerService!viewDepartMentInfo");
        // 编辑部门信息
        map.put("4088881008", "issuerService!updateDepartMentInfo");
        // 删除发行机构信息
        map.put("4088880009", "issuerService!deleteIssuerInfo");
        // 添加投递点信息
        map.put("4088880010", "issuerService!insertDeliveryInfo");
        // 删除投递点信息
        map.put("4088880011", "issuerService!deleteDeliveryInfo");
        // 添加快递点联系人信息
        map.put("4088880012", "issuerService!insertDeliveryContractInfo");
        // 添加收货人信息
        map.put("4088880013", "issuerService!insertDeliveryReceptInfo");
        // 查询收货人信息
        map.put("4088880014", "issuerService!queryDeliveryReceptInfo");
        // 编辑收货人信息
        map.put("4088880015", "issuerService!updateDeliveryReceptInfo");
        // 查询投递点信息
        map.put("4088880016", "issuerService!deliveryReceptInfoQuery");
        // 删除发货人信息
        map.put("4088880017", "issuerService!deleteReceptInfo");
        // 编辑发行机构信息
        map.put("4088880018", "issuerService!updateIssuerInfo");
        // 添加卡Bin信息
        map.put("4003060010", "issuerService!insertCardBin");
        // 获取发行机构ID
        map.put("2001040880", "issuerService!queryEntityId");
        // 获取发行机构下的营销机构
        map.put("2001040881", "issuerService!configEntityId");
        // 验证发卡机构id前缀是否存在
        map.put("4003030111", "issuerService!checkIssuerIdPin");
        // 查询操作员所属营销机构的上级发行机构
        map.put("4003090000", "issuerService!inquerySelfIssuer");

        /**
         * 发行机构合同管理
         */
        // 查询发行机构合同
        map.put(ISSUER_CONTRACT_QUERY, "loyaltyContractService!inqueryIssuerContract");
        // 添加发行机构合同
        map.put("5088880001", "loyaltyContractService!insertIssuerContract");
        // 编辑发行机构合同
        map.put("5088880002", "loyaltyContractService!editIssuerContract");
        // 发行机构合同下添加产品和账户类型
        map.put("5088880003", "loyaltyContractService!insetProductAndService");
        // 发行机构合同管理产品编辑
        map.put("5088880004", "loyaltyContractService!editIssuerContractProduct");
        // 查看发行机构合同关联的产品
        map.put("5088880005", "loyaltyContractService!viewIssuerContractProduct");
        // 编辑发行机构合同管理账户
        map.put("5088880006", "loyaltyContractService!editIssuerContractService");
        // 编辑发行机构合同
        map.put(ISSUER_CONTRACT_UPDATE, "loyaltyContractService!updateIssuerContractService");

        /**
         * 收单机构
         */
        // 查询收单机构信息
        map.put("8008910000", "consumerService!inqueryConsumer");
        // 添加收单机构信息
        map.put("8008920000", "consumerService!insertConsumer");
        // 查看收单机构信息
        map.put("8008930000", "consumerService!viewConsumer");
        // 编辑收单机构信息
        map.put("8008940000", "consumerService!editConsumer");
        // 删除收单机构信息
        map.put("8008950000", "consumerService!deleteConsumer");
        // 查询收单机构所属发行机构
        map.put("8008960000", "consumerService!selectIssuer");
        // 获取收单机构ID
        map.put("2001060880", "consumerService!queryEntityId");
        // 获取收单机构信息
        map.put("2001040883", "consumerService!configEntityId");
        // 收单机构网站密码修改
        map.put("8008021188", "consumerService!loadForModifyWebPassowrd");
        // 收单机构部分更新
        map.put("2002021188", "consumerService!updatePart");
        // 查看收单机构网站登录名是否可用
        map.put("2002021111", "consumerService!checkWebName");
        // 核对web登录名是否可用
        map.put("8008960001", "consumerService!checkConsumer");

        // 收单机构合同管理
        map.put("8088050000", "consumerContractBaseService!selectContract");

        /**
         * (暂未添加)
         */
        map.put("2011122701", "acqPayService!inquery");
        map.put("2011122702", "acqPayService!choose");
        map.put("2011122703", "acqPayService!selectProd");
        map.put("2011122704", "acqPayService!insert");
        map.put("2011122705", "acqPayService!reEdit");
        map.put("2011122706", "acqPayService!edit");
        map.put("2011122707", "acqPayService!view");
        map.put("2011122708", "acqPayService!delete");
        map.put("2011122709", "acqPayService!selectBank");
        map.put("2011122710", "acqPayService!chooseBank");
        map.put("2011122711", "acqPayService!addBank");
        map.put("2011122712", "acqPayService!delBank");
        /**
         * 营销机构
         */
        // 查询营销机构信息
        map.put("8008030000", "sellerService!inquery");
        // 查询sellerbyentityID
        map.put("8008030001", "sellerService!getSellerByEntityId");
        // 显示有效的营销机构
        map.put("8008030100", "sellerService!inqueryForSelf");
        // 查看营销机构信息
        map.put("8008031000", "sellerService!viewSeller");
        // 添加营销机构信息
        map.put("8008032000", "sellerService!insertSeller");
        // 编辑营销机构信息
        map.put("8008033000", "sellerService!updateSeller");
        // 删除营销机构信息
        map.put("8008034000", "sellerService!delSeller");
        // 初始化营销机构信息
        map.put("8008035000", "sellerService!initSeller");
        // 获取营销机构ID
        map.put("2001050880", "sellerService!queryEntityId");
        // 获取营销机构信息
        map.put("2001040882", "sellerService!configEntityId");
        // 查询营销机构
        map.put("8008030600", "sellerService!inqueryorSelf");
        // 查询订单信息
        map.put("8008030700", "sellerService!selectOrder");

        /**
         * 营销机构合同
         */
        // 查询营销机构合同信息
        map.put(SELLER_CONTRACT_SERVICE_INQUERY, "sellerContractService!inquery");
        // 查看营销机构合同信息
        map.put("8008041000", "sellerContractService!view");
        // 添加营销机构合同信息
        map.put("8008042000", "sellerContractService!insert");
        // 编辑营销机构合同信息
        map.put("8008043000", "sellerContractService!update");
        // 删除营销机构合同信息
        map.put("8008044000", "sellerContractService!delete");
        // (暂未添加)
        map.put("8008045000", "sellerContractService!");
        // 选择产品
        map.put("8008046000", "sellerContractService!inqueryProduct");
        // 选择产品
        map.put("8008047000", "sellerContractService!inqueryProductForIssuerContract");
        // 查询产品信息
        map.put("8008048000", "sellerContractService!insertProduct");
        // 查询发行机构
        map.put("8008049000", "sellerContractService!insertIssuer");
        // 查询合同模板
        map.put("8008049001", "sellerContractService!inqueryMasterPlate");
        // 查询合同模板
        map.put("8008049002", "sellerContractService!inqueryContract");

        /**
         * 营销机构合同产品及服务
         */
        // 查询营销合同产品及账户
        map.put("8008050000", "sellerProductContractService!inquery");
        // 查看营销合同产品及账户
        map.put("8008051000", "sellerProductContractService!view");
        // 添加营销合同产品及账户
        map.put("8008052000", "sellerProductContractService!insert");
        // 编辑营销合同产品及账户
        map.put("8008053000", "sellerProductContractService!update");
        // 编辑合同明细信息
        map.put("8008054000", "sellerProductContractService!updateSellerService");
        // 删除合同明细信息
        map.put("8008055000", "sellerProductContractService!delete");
        // 查看营销合同服务明细
        map.put("8008056000", "sellerProductContractService!viewAcctypeContract");

        /**
         * 客户信息管理
         */
        // 客户信息添加页面初始化
        map.put("2001020100", "customerService!initCustomer");
        //查询即将过期的证件
        map.put("2017080401", "customerService!queryOutdateCertifincate");
        // 客户信息查询页面初始化
        map.put("2001020101", "customerService!initInquery");
        //根据证件类型判断公司是否重复
        map.put("20170803001","customerService!checkLicense");
        // 查询客户信息
        map.put("2001020200", "customerService!inqueryCustomer");
        // 添加客户信息
        map.put("2001020300", "customerService!insertCustomer");
        // 添加客户信息
        map.put("2001020302", "customerService!insertOrderCustomer");
        // 客户信息快速添加
        map.put("2001020301", "sellerContractService!insertByMasterplate");
        // 查看客户信息
        map.put(CUSTOMER_SERVICE_VIEW, "customerService!viewCustomer");
        // 编辑客户信息
        map.put("2001020500", "customerService!updateCustomer");
		// 客户信息CIM同步
		map.put("20170807002", "customerSynchCRM!syncToCRM");
        // 删除客户信息
        map.put("2001020600", "customerService!deleteCustomer");
        // 导入客户信息
        map.put("2001020610", "customerService!importFile");
        // 修改客户状态
        map.put("2001023900", "customerService!modifyState");
        // 查找散户客户，此版本屏蔽(暂未添加)
        map.put("2001020701", "customerService!selectRetailCustomer");
        // 获取客户和部门信息
        map.put(CUSTOMER_DEPARTMENT_INQUERY, "customerService!customerDepartmentInquery");
        /**
         * 机构信息管理
         */
        // 发票地址添加
        map.put("8008060000", "invoiceAddressService!insert");
        // 发票地址删除
        map.put("8008061000", "invoiceAddressService!delete");
        // 发票公司添加
        map.put("8008070000", "invoiceCompanyService!insert");
        // 删除发票公司信息
        map.put("8008071000", "invoiceCompanyService!delete");
        // 添加快递点信息
        map.put("8008080000", "deliveryPointService!insert");
        // 删除快递点信息
        map.put("8008081000", "deliveryPointService!delete");
        // 查看快递点信息
        map.put("8008082000", "deliveryPointService!view");
        // 编辑快递点信息
        map.put("8008083000", "deliveryPointService!update");

        /**
         * 快递点联系人
         */
        // 添加快递点联系人
        map.put("8008090000", "deliveryContactService!insert");
        // 删除快递点联系人
        map.put("8008091000", "deliveryContactService!delete");
        // 查看快递点联系人
        map.put("8008092000", "deliveryContactService!view");
        // 添加部门信息
        map.put("8008100000", "departmentService!insert");
        // 删除部门信息
        map.put("8008101000", "departmentService!delete");
        // 添加联系人信息
        map.put("8008110000", "contactService!insert");
        // 删除联系人信息
        map.put("8008111000", "contactService!deleteContact");
        // 查看联系人信息
        map.put("8008112000", "contactService!viewContact");
        // 编辑联系人信息
        map.put("8008113000", "contactService!updateContact");
        /**
         * 银行账户
         */
        // 添加银行账户
        map.put("8008120000", "bankService!insert");
        // 删除银行账户信息
        map.put("8008121000", "bankService!delete");
        // 编辑银行账户信息
        map.put("8008122000", "bankService!update");
        // 查看银行账户信息
        map.put("8008123000", "bankService!view");
        // 查询银行账户信息
        map.put("8008124000", "bankService!inquery");

        /**
         * 商户
         */
        // 初始化商户信息
        map.put("8008021800", "merchantService!initWebSiteName");
        // 查询商户信息
        map.put("8008021801", "merchantService!inquiryMerchant");
        // 查询外部商户信息
        map.put("2013050701", "merchantService!inquiryExternalMerchant");
        // 初始化商户信息
        map.put("8008021802", "merchantService!initMerchant");
        // 添加商户信息
        map.put("8008021803", "merchantService!insertMerchant");
        // 商户添加成功后查看商户信息
        map.put("800802180501", "merchantService!viewMerchantAfterAdd");
        // 加载商户信息
        map.put("8008021804", "merchantService!loadMerchant");
        // 添加联系人信息
        map.put("8008021805", "merchantService!insertContact");
        // 查看联系人信息
        map.put("8008021806", "merchantService!viewContact");
        // 编辑联系人信息
        map.put("8008021807", "merchantService!updateContact");
        // 删除联系人信息
        map.put("8008021808", "merchantService!deleteContact");
        // 编辑商户信息
        map.put("8008021809", "merchantService!updateMerchant");
        // 查看商户信息
        map.put("8008021810", "merchantService!viewMerchant");
        // 删除商户信息
        map.put("8008021811", "merchantService!deleteMerchant");
        // 验证商户网站登录名是否可用
        map.put("8008021812", "merchantService!checkWebName");
        // 修改商户网站密码
        map.put("8008021813", "merchantService!loadForModifyPassowrd");
        // 查询商户信息
        map.put("8008021814", "merchantService!getMerchantDTO");
        // 部分更新用户
        map.put("2002023100", "merchantService!updatePart");
        // 查询商户信息
        map.put("800888880001", "merchantService!selectMerchantByKey");
        // 商户交易查询
        map.put("201107770100", "merchantService!merchantTxnQuery");
        // 查询商户下的门店信息
        map.put("201107770110", "merchantService!getShopListByMerchantId");
        // 加载全部商户信息
        map.put("201107770200", "merchantService!loadAllMerchant");
        // 查询商户信息
        map.put("8008021815", "merchantService!inquiryMerchantVerifier");
        // 此处为调用(暂未添加)
        map.put("800888880002", "merchantService!updateConMerchant");
        /**
         * 门店
         */
        // 查询门店信息
        map.put("8008020000", "shopService!inquiryShop");
        // 添加门店信息
        map.put("8008020001", "shopService!insertShop");
        // 查看门店信息
        map.put("8008020002", "shopService!viewShop");
        // 编辑门店信息
        map.put("8008020003", "shopService!updateShop");
        // 删除门店信息
        map.put("8008020004", "shopService!deleteShop");
        // 添加门店联系人信息
        map.put("8008020005", "shopService!insertContact");
        // 查看门店联系人信息
        map.put("8008020006", "shopService!viewContact");
        // 编辑门店联系人信息
        map.put("8008020007", "shopService!updateContact");
        // 删除门店联系人信息
        map.put("8008020008", "shopService!deleteContact");
        /**
         * 商户合同
         */
        // 查询商户合同信息
        map.put("2002050100", "mchntContractService!inquiryMchntContract");
        // 添加合同信息
        map.put("2002050200", "consumerContractBaseService!insertContract");
        // 查询合同信息
        map.put("2002050300", "consumerContractBaseService!selectContract");
        // 添加合同账户信息
        map.put("2002051500", "accTypeContractService!addAccTypeContractDTO");
        // 查询合同账户信息
        map.put("2011111600", "mchantAccTypeContractService!selectAccTypeContractDTO");
        // 新增服务合同
        map.put("2002051600", "accTypeContractService!insertAccTypeContractDTO");
        // 查看合同信息
        map.put("2002051700", "consumerContractBaseService!viewConsumerContractDTO");
        // 查看合同信息
        map.put("20111116002", "consumerContractBaseService!viewMchntConsumerContractDTO");
        // 更新收单机构合同
        map.put("2002051701", "consumerContractBaseService!updateConsumerContract");
        // 删除收单机构合同
        map.put("2002051702", "consumerContractBaseService!deleteConsumerContract");
        // 查看服务合同
        map.put("2002051800", "accTypeContractService!viewAccTypeContractDTO");
        // 更新服务合同
        map.put("2002051900", "accTypeContractService!updateAccTypeContract");
        // 删除服务合同
        map.put("2002052100", "accTypeContractService!deleteAccTypeContract");

        /**
         * 终端管理
         */
        // 查询终端信息
        map.put("5005020000", "terminalManagementService!inquery");
        // 查询终端参数
        map.put("5005024001", "terminalManagementService!selectPrmVersion");
        // 终端厂商查询
        map.put("5005025000", "terminalManagementService!inqueryPosBrand");
        // 添加终端厂商
        map.put("5005025001", "terminalManagementService!addPosBrand");
        // 加载终端厂商
        map.put("5005026000", "terminalManagementService!loadPosBrand");
        // 编辑终端厂商
        map.put("5005027000", "terminalManagementService!updatePosBrand");
        // 查询终端厂商
        map.put("5005025000", "terminalManagementService!inqueryPosBrand");
        // 加载终端参数版本信息
        map.put("5005024001", "terminalManagementService!selectPrmVersion");
        // 添加终端信息
        map.put("5005021000", "terminalManagementService!insertposInf");
        // 查看终端信息
        map.put("5005022000", "terminalManagementService!viewposInf");
        // 查看外部终端信息
        map.put("5005031000", "terminalManagementService!viewOuterposInf");
        // 查询终端信息
        map.put("5005031002", "terminalManagementService!inqueryPos");
        // 查询外部终端信息
        map.put("5005031003", "terminalManagementService!inqueryOuterpos");
        // 编辑终端信息
        map.put("5005024000", "terminalManagementService!modifyposInf");
        // 编辑外部终端信息
        map.put("5005024002", "terminalManagementService!modifyOuterposInf");
        // 删除内部终端信息
        map.put("5005023000", "terminalManagementService!deleteposInf");
        // 删除外部终端信息
        map.put("5005023001", "terminalManagementService!deleteOuterposInf");
        // 添加外部终端信息
        map.put("5005030000", "terminalManagementService!insertOuterPosInf");
        // 查询终端参数
        map.put("5005028000", "posParameterService!inquery");
        // 检查版本下的通讯参数
        map.put("5005028530", "posParameterService!commPrm");
        // 添加终端参数
        map.put("5005028100", "posParameterService!insert");
        // 添加卡bin信息
        map.put("5005028510", "posParameterService!insertCardBin");
        // 查询卡bin信息
        map.put("5005028540", "posParameterService!queryCardBinList");
        // 添加IC卡参数
        map.put("5005028520", "posParameterService!insertIc");
        // 查看终端参数
        map.put("5005028200", "posParameterService!view");
        // 编辑终端参数
        map.put("5005028400", "posParameterService!update");

        /**
         * 交易系统信息管理
         */
        // 查询卡bin路由信息
        map.put("2012101700", "txnManagementService!inqueryCardRoute");
        // 查找本机构及本机构的外系统机构
        map.put("2012101701", "txnManagementService!getIssuerList");
        // 添加卡路由信息
        map.put("2012101702", "txnManagementService!cardRouteAdd");
        // 删除卡路由信息
        map.put("2012101703", "txnManagementService!cardRouteDel");
        /**
         * 卡片管理
         */
        // (未找到此方法，暂未记录)
        map.put("2006010100", "cardManageService!inqueryCardManagement");
        map.put("2006010200", "cardManageService!viewDelay");
        map.put("2006010300", "cardManageService!insertDelay");
        map.put("2006010400", "cardManageService!inqueryTxn");
        map.put("2006010500", "cardManageService!viewTxn");
        map.put("2006010600", "cardManageService!inqueryBelTxn");
        map.put("2006010700", "cardManageService!getCardNo");
        map.put("2006010800", "cardManageService!inqueryCardNo");
        map.put("2006010900", "cardManageService!inqueryHang");
        map.put("2006011000", "cardManageService!inqueryLock");
        map.put("2006012000", "cardManageService!loadAccount");
        map.put("2006013000", "cardManageService!viewBalance");
        map.put("2006013100", "cardManageService!loadMemberInfo");
        map.put("2006013200", "cardManageService!updateMemberInfo");
        // (以上方法未使用，暂未记录)
        // 查询全部会员信息
        map.put(CARDMANAGEMENT_SERVICE_INQUERYMEMBERINFO, "cardManageService!inqueryMemberInfo");
        // 查询会员详细信息
        map.put("2011102700", "cardManageService!inqueryMemberDetailInfo");
        // 查询会员关联的卡号
        map.put("2011102800", "cardManageService!inqueryMemberRltCardNo");
        // 查询卡的总金额,可用金额,冻结金额
        map.put("2011102801", "cardManageService!inqueryCardBalance");
        // 转账时转入卡查询
        map.put("2011120101", "cardManageService!queryInCardForTranfer");
        // (未找到以下方法，暂未记录)
        map.put("2006014000", "cardManageService!inqueryRisk");
        map.put("2006015000", "cardManageService!viewRisk");
        map.put("2006016000", "cardManageService!viewRiskSvcCtrl");
        map.put("2006017000", "cardManageService!setRiskSvcCtrl");
        map.put("2006018000", "cardManageService!inqueryCardInfo");
        map.put("2006019000", "cardManageService!viewCardInfo");
        map.put("2006021000", "cardManageService!inqueryCardFreeze");
        // 卡片交易信息查询
        map.put("2006021001", "cardManageService!cardSelect");
        // 卡片交易信息详细
        map.put("2006021002", "cardManageService!cardSelectDetail");
        // 查询卡安全信息
        map.put("2006020002", "cardManageService!cardSeuriyQuery");
        // 查询卡片是否出库
        map.put("2006020003", "cardManageService!checkCardIsAct");
        // 赎回
        map.put("2011120601", "cardManageService!redemptionCard");
        // 激活
        map.put("2011120603", "cardManageService!activateCard");
        // 查询卡片原订单信息
        map.put(CARDMANAGEMENT_VIEW_ORDER, "cardManageService!viewOrderByCardNo");
        // 记录卡操作信息
        map.put(CARDMANAGEMENT_OPERATION, "cardManageService!cardManageOperation");
        // 卡片查询
        map.put(CARDMANAGEMENT_QUERY, "cardManageService!queryCard");
        // 设置卡安全信息
        map.put("2006030000", "cardManageService!cardSecurityInit");
        // 查询卡操作
        map.put("2006030001", "cardManageService!selectCardManagement");
        // 查询单张卡操作
        map.put("2011112701", "cardManageService!selectSingleCardManagement");
        // 转账时转出卡查询
        map.put(CARD_QUERY_FORTRANSFER, "cardManageService!queryCardForTranfer");
        // 换卡时新卡信息查询
        map.put("2011128001", "cardManageService!queryNewCardForChange");

        /**
         * 结算周期规则
         */
        // 添加结算周期规则
        map.put("7777000001", "settlePeriodRuleService!insert");
        // 查询结算周期规则
        map.put("7777000002", "settlePeriodRuleService!query");
        // 编辑结算周期规则
        map.put("7777000003", "settlePeriodRuleService!editInit");
        // 更新结算周期规则
        map.put("7777000004", "settlePeriodRuleService!update");
        // 删除结算周期规则
        map.put("7777000005", "settlePeriodRuleService!delete");
        // 启用结算周期规则
        map.put("7777000006", "settlePeriodRuleService!modify");
        // 查看结算周期规则
        map.put("7777000007", "settlePeriodRuleService!view");

        /**
         * 计算规则
         */
        // 查询计算规则
        map.put("7777100001", "caclInfService!query");
        // 添加计算规则
        map.put("7777100002", "caclInfService!insert");
        // 编辑计算规则
        map.put("7777100003", "caclInfService!update");
        // 删除计算规则
        map.put("7777100004", "caclInfService!delete");
        // 查询计算规则
        map.put("7777100005", "caclInfService!queryBydiscCd");
        // 添加计算规则
        map.put("7777100006", "caclInfService!insertInf");
        // (此处未被调用，暂未添加)
        map.put("7777100007", "caclInfService!getCaclDspDTO");
        // 判断用户属于哪个机构
        map.put("7777100008", "caclInfService!getByEntityId");
        // (此处未被调用，暂未添加)
        map.put("7777100009", "caclInfService!isHave");

        /**
         * 库存卡片管理
         */
        // 查询库存卡片信息
        map.put("201202030000", "stockCardService!query");
        // 库存卡号段准备
        map.put("201204110001", "stockCardService!cardSegementReady");
        /**
         * 库存调拨
         */
        // 查询库存调拨记录
        map.put("201203240001", "stockCardService!allocateList");
        // 查询调拨机构
        map.put("201203240002", "stockCardService!selectAllocateOrgan");
        // 查询下一条库存卡ID
        map.put("201203240003", "stockCardService!getTableId");
        // 查询库存明细信息
        map.put("201203240004", "stockCardService!getStockAmount");
        // 准备库存卡
        map.put("201203240006", "stockCardService!cardArrayReady");
        // 准备库存卡号
        map.put("201203240007", "stockCardService!readyCardNo");
        // 库存调拨准备卡
        map.put("201203240008", "stockCardService!stockAllocate");
        // 获取卡列表
        map.put("201203240009", "stockCardService!getCardList");
        // 库存调拨删除已准备卡片
        map.put("201203240010", "stockCardService!stockAllocateReturn");
        // 库存调拨-删除卡片
        map.put("201203240011", "stockCardService!deleteCardList");
        /***
         * 订单部分
         */

        // 订单号查询 （可以考虑用下面的订单查询）
        map.put("2003050100", "sellOrderQuery!query");
        // 查询营销机构订单
        map.put(ORDER_INQUERY_BY_SELLER, "sellOrderQuery!query");
        /**
         * 发票管理初始化及查询
         */
        map.put("2003020123", "orderInvoiceService!initInvoiceInfo");
        /**
         * 获取机构下所有的销售人员姓名
         */
        map.put("2003020124", "orderInvoiceService!initSaleManName");
        /**
         * 获取机构下的所有营销机构
         */
        map.put("20118000041", "cardRechargeAndChargebackService!getAllSeller");
        /**
         * 获取营销机构下的所有销售人员
         */
        map.put("20118000042", "cardRechargeAndChargebackService!getSaleManOfSeller");
        /**
         * 编辑发票
         */
        map.put("2003020119", "orderInvoiceService!modifyInvoiceInfo");
        /**
         * 根据id查找发票信息
         */
        map.put("2003020120", "orderInvoiceService!queryInvoiceinfoById");
        /**
         * 订单激活
         */
        map.put("20118001001", "sellOrderQuery!queryOrderActivate");
        map.put("20118001002", "sellOrderQuery!queryOrderActivateEx");

        /**
         * 订单录入
         */
        // 查询订单信息
        map.put("20118000000", "orderService!queryOrder");
        // 查询销售订单信息
        map.put(ORDER_INQUERY_AT_SELL_INPUT, "sellOrderQuery!queryOrderAtSellInput");
        // 查询采购订单
        map.put("20118000002", "sellOrderQuery!queryOrderAtBuyInput");
        // 查询已付款订单
        map.put(ORDER_INQUERY_AT_ORDER_PAYMENT, "sellOrderQuery!queryOrderAtOrderPayment");
        // 销售订单初始化
        map.put("20118000010", "orderService!initAdd");
       /* //销售订单初始化（不含不记名卡）
        map.put("20118000011", "orderService!initAdd");*/
        // 添加订单信息
        map.put("20118000020", "orderService!orderInsert");
        // 记名订单获取持卡人
        map.put("20118000030", "orderService!getCardholderList");
        // 插入持卡人信息
        map.put("20118000040", "orderService!insertCardholderList");
        // 编辑销售订单
        map.put("20118000050", "orderService!editSellOrderDTO");
        // 更新销售订单信息
        map.put("20118000051", "orderService!updateSellOrder");
        /***************** 编辑采购订单开始 *****************/
        map.put("20118000052", "orderService!editBuyOrderDTO");
        map.put("20118000053", "orderService!updateBuyOrderDTO");
        /***************** 编辑采购订单结束 *****************/
        // 删除持卡人信息
        map.put("20118000060", "orderService!deleteOrderList");
        // 查询匿名卡库存
        map.put("20118000070", "orderService!getProductStock");
        // 插入匿名卡明细
        map.put("20118000080", "orderService!insertOrderListForSellOrderUnsign");
        // 删除匿名卡明细
        map.put("20118000090", "orderService!deleteOrderListForSellOrderUnsign");
        // 编辑匿名卡明细
        map.put("20118000100", "orderService!editOrderListForSellOrderUnsign");
        // 更新匿名卡明细
        map.put("20118000110", "orderService!updateOrderListForSellOrderUnsign");
        // 录入节点取消订单
        map.put(SELL_ORDER_CANCEL_AT_INPUT, "cancelOrderService!cancelOrderAtInput");
        // 订单录入提交
        map.put("20118000130", "submitOrderService!submitOrderAtInput");
        // 获取订单信息
        map.put("20118000134", "orderService!getSellOrderByKey");
        // 初始化采购订单信息
        map.put("20118000135", "orderService!initBuyOrderAdd");
        // 获取当前产品信息
        map.put("20118000136", "orderService!getCurrentStockDTOByEntity");
        // 获取客户卡片信息
        map.put("20118000137", "orderService!getCustomerCard");
        // 添加充值订单明细
        map.put("20118000138", "orderService!insertCreditCardlist");
        // 删除充值详查明细
        map.put("20118000139", "orderService!deleteCreditCardlist");
        // 获取当前产品信息
        map.put("20118000146", "orderService!getProdByContractForBuyOrderUnsign");
        // 导入充值订单的持卡人信息
        map.put("20118000147", "orderService!insertCreditCardholderList");
        // 检查卡片信息是否正确
        map.put("20118000148", "orderService!checkCreditCardholderCardList");

        /**
         * 卡余额变动明细表
         */
        map.put("20118000043", "cardBalanceDetailService!getAccCardInfo");

        /**
         * 卡生命周期查询
         */
        map.put("201304251430", "cardLifeCycleQueryService!inqueryCardLifeCycle");

        /**
         * 卡实时库存查询
         */
        map.put("201307221048", "cardCurrentTimeStockQueryService!inqueryCardCurrentTimeStock");
        /**
         * 查询营销机构及其下属机构
         * */
        map.put("201310250910", "sellerService!getSellerList");

        /**
         * 订单确认
         */
        // 订单配送准备
        map.put("20118000140", "sellOrderQuery!queryOrderAtReadyHandOut");
        // 查询待审核订单信息
        map.put("20118000141", "sellOrderQuery!queryOrderAtConfrim");
        // 查询待审核订单信息
        map.put("20118000142", "sellOrderQuery!queryOrderAtSellConfrim");
        // 查询待审核采购订单信息
        map.put("20118000143", "sellOrderQuery!queryOrderAtBuyConfrim");
        // 查询待充值的订单信息
        map.put("20118000144", "sellOrderQuery!queryOrderAtImmediatelyCredit");
        // 查询待配送订单
        map.put("20118000145", "sellOrderQuery!queryOrderAtSendConfirm");
        // 订单提交
        map.put(SELL_ORDER_SUBMIT_AT_CONFIRM, "submitOrderService!submitOrderAtConfirm");
        // 订单激活
        map.put("20118000181", "orderActActionImpl!submitOrderForAct");
        // 获取订单信息
        map.put(FRTCODE_SERVICE_SELL_ORDER_GET, "sellOrderQuery!getSellOrderDTO");
        // 取消订单
        map.put("20118000170", "cancelOrderService!cancelOrderAtConfirm");
        // 退回订单
        map.put("20118000180", "sendBackOrderService!sendBackAtConfirm");
        /**
         * 订单准备
         */
        // 提交订单
        map.put("20118000190", "submitOrderService!submitOrderAtReady");
        // 退回订单
        map.put("20118000191", "sendBackOrderService!sendBackAtReady");
        // map.put("20118000180","sendBackOrderService!submitOrderAtReady");
        // 准备订单
        map.put("20118000192", "orderReadyService!orderReady");
        // 获取库存卡
        map.put("20118000193", "sellOrderQuery!getCardForSellOrderReady");
        // 卡号准备
        map.put("20118000194", "orderReadyService!cardReady");
        // 匿名订单准备所有明细
        map.put("20118000839", "orderReadyService!orderListAllReady");
        // 删除订单明细
        map.put("20118000195", "orderReadyService!deleteRecord");
        // 删除所有的卡明细
        map.put("20118000196", "orderReadyService!deleteAllRecord");
        // 修改卡出库状态
        map.put("20118000197", "submitOrderService!submitOrderAtHandOut");
        // 退回订单
        map.put("20118000198", "sendBackOrderService!sendBackAtHandOut");
        // 卡号段准备
        map.put("20118000838", "orderReadyService!cardSegmentReady");
        // 更新采购订单
        map.put("20118000837", "orderReadyService!getSellOrder");
        // 订单配送确认
        map.put("20118000199", "orderSendConfirmActionImpl!submitOrderAtSendConfirm");
        // 退回订单
        map.put("20118000200", "sendBackOrderService!sendBackAtSendConfirm");
        /**
         * 订单立即充值
         */
        map.put("20118000210", "orderRechargeActionImpl!submitOrderImmdiatelyCredit");
        // 订单退回
        map.put("20118000220", "sendBackOrderService!sendBackAtOrderImmdeiatelyCredit");

        /**
         * 订单付款确认
         */
        map.put(SELL_ORDER_CONFIRM_AT_ORDER_PAYMENT, "submitOrderService!confirmAtOrderPayment");
        /**
         * 订单付款审核
         */
        map.put(SELL_ORDER_SUBMIT_AT_ORDER_PAYMENT, "orderPaymentActionImpl!submitOrderForPayment");

        /**
         * 订单合并付款
         */
        map.put("20118000232", "submitOrderService!combineAtOrderPayment");
        // (此处未调用，暂未定义)
        map.put("20118000240", "sendBackOrderService!sendBackOrderAtOrderPayment");

        /**
         * 采购订单
         */
        // 查询合同明细
        map.put("20118000300", "sellOrderQuery!getContractForBuyOrder");
        // 添加采购订单
        map.put("20118000301", "orderReadyService!insertBuyOrder");
        // 查询代发卡产品类型
        map.put("20118000302", "sellOrderQuery!getContractForBuySellOrder");
        // 添加代发卡采购订单
        map.put("20118000303", "orderReadyService!insertLoyaltyBuyOrder");

        /**
         * 订单接收
         */
        // 查询已接收订单
        map.put("20118000400", "sellOrderQuery!queryOrderForOrderAccept");
        // 提交订单
        map.put("20118000401", "submitOrderService!submitOrderAtAccept");
        // 退回订单
        map.put("20118000402", "submitOrderService!sendBackOrderAtAccept");

        /**
         * 制卡订单xxl
         */
        // 查询制卡订单
        map.put(STOCK_ORDER_INQUERY, "stockOrderService!inquery");
        // 初始化制卡订单
        map.put("20118110000", "stockOrderService!initAdd");
        // 添加制卡订单
        map.put("20118120000", "stockOrderService!insert");
        //添加订单制卡文件上传
        map.put("20150611001", "stockOrderService!uploadCardFile");
        // 更新制卡订单
        map.put("20118130000", "stockOrderService!update");
        // 查看制卡订单信息
        map.put("20118140000", "stockOrderService!view");
        // 制卡订单 订单流程信息查看
        map.put("20118141000", "stockOrderService!viewOrderFlow");
        // 制卡订单 加载订单信息
        map.put("20118142000", "stockOrderService!load");

        /**
         * 制卡订单提交、退回、取消
         */
        // 制卡订单-批量提交
        map.put("20118150000", "stockOrderConfirmService!batchSubmit");
        // 制卡订单-批量取消
        map.put("20118160000", "stockOrderConfirmService!batchCancel");
        // 订单取消
        map.put("20118170000", "stockOrderConfirmService!cancel");
        // 制卡订单-订单确认
        map.put("20118180000", "stockOrderConfirmService!confirm");
        // 订单退回
        map.put("20118190000", "stockOrderConfirmService!reject");
        // 退回到制卡文件下载
        map.put("20118191000", "stockOrderConfirmService!rejectOrderAcceptToFileMake");

        /**
         * 下载制卡文件
         */
        // map.put("20118200000", "orderMakeCardService!submitOrderForOpenAccount");
        // 制卡开户
        map.put("20118200000", "orderMakeCardActionImpl!submitOrderForOpenAccount");
        // 制卡文件生成
        map.put("20118210000", "orderMakeCardService!downMakeCardFile");
        // 选择制卡商
        map.put("20118220000", "orderMakeCardService!getCardCompanyList");
        // 下载制卡文件
        map.put("20118230000", "orderMakeCardService!makeMakeCardFile");
        // 下载卡pin文件
        map.put("20118240000", "orderMakeCardService!makeCardPINFile");

        /** 订单卡明细 */
        // 查询订单卡明细
        map.put("20118310000", "orderCardListService!getOrderCardListForCardState");
        // 验卡时查询订单卡明细
        map.put(ORDER_CARD_LIST_QUERY_FOR_CHECKCARD, "orderCardListService!getOrderCardListForCheckcard");
        // 开始验卡
        map.put("20118310002", "orderCardListService!checkCard");
        // 结束验卡
        map.put("20118310003", "orderCardListService!endCheckCard");
        // 判断卡片是否已经验卡
        map.put("20118310004", "orderCardListService!hadCheckCard");
        // 查看订单卡明细
        map.put("20118320000", "orderCardListService!view");
        // 更新订单卡明细
        map.put("20118330000", "orderCardListService!update");
        // 制卡完成
        map.put(MAKECARD_STATE_COMPLATE, "orderCardListService!makeCardComplate");
        /**
         * 制卡订单准备
         */
        // 订单准备
        map.put("20118340000", "stockOrderReadyService!getOrderReadyList");
        // 查看库存
        map.put("20118350000", "stockOrderReadyService!getStockCardForReady");
        // 订单准备 库存订单准备
        map.put(STOCK_ORDER_READY_TO_CARD_LIST, "stockOrderReadyService!readyStockCardToCardList");
        // 删除订单卡明细
        map.put("20118370000", "stockOrderReadyService!deleteReadCardList");

        /**
         * 结算单部分
         */
        // 查询结算单信息
        map.put("201106660000", "settleService!querySettleList");
        // 预览结算单信息
        map.put("201106660100", "settleService!previewSettle");
        // 生成结算单
        map.put("201106660200", "settleService!generateSettle");
        // (此处暂未添加)
        map.put("201106660300", "settleService!cancleSettle");
        // 结算单付款确认
        map.put("201106660400", "settleService!settlePaymentConfirm");
        // 查询结算单明细
        map.put("201106660500", "settleService!querySettleDetail");
        // 结算单确认
        map.put("201106660600", "settleService!confirmSettle");
        // 结算单付款确认
        map.put("201106660700", "settleService!settlePaymentConfirm");
        // 修改结算日期
        map.put("201106660800", "settleService!changeSettleDate");
        // 取消结算单
        map.put("201106660900", "settleService!cancelSettle");
        // 修改手续费
        map.put("201106661000", "settleService!settleChangeFee");
        // 预览结算单
        map.put("201106661100", "settleService!previewSettleInit");
        // 查询结算单明细信息
        map.put("201106661200", "settleService!settleDetail");

        /**
         * 有效期规则
         */
        // 查询有效期规则
        map.put("201107250000", "validPeriodRuleService!queryValidPeriodRule");
        // 添加有效期规则
        map.put("201107250001", "validPeriodRuleService!insert");
        // 查看有效期规则
        map.put("201107250002", "validPeriodRuleService!view");
        // 更新有效期规则
        map.put("201107250003", "validPeriodRuleService!update");
        // 操作有效期规则
        map.put("201107250004", "validPeriodRuleService!operate");
        // 获取有效期规则
        map.put(VALID_PERIOD_RULE_LIST, "productService!getValidPeriodRuleList");

        /**
         * 退货审核
         */
        // (暂未记录)(查询退货申请列表)
        map.put("201109220000", "refundService!refundList");
        // 查询退货申请详细信息
        map.put("201109220001", "refundService!refundDetail");
        // (暂未记录)
        map.put("201109220002", "refundService!findAllMerchant");
        // (暂未记录)
        map.put("201109220003", "refundService!refundVerify1");
        map.put("201109220004", "refundService!refundVerify2");
        map.put("201109220005", "refundService!refundVerify3");
        // 拒绝退货
        map.put("201109220006", "refundService!refundRefuse");
        // 更新退货状态
        map.put("201109220007", "refundService!refundTransLogQuery");
        // (暂未记录)
        map.put("201109220008", "refundService!refundUpdate");
        // 同意退货
        map.put("201109220009", "refundService!refundVerify");
        // 编辑换卡订单
        map.put("20118000054", "orderService!editChangeCardOrderDTO");
        // 换卡订单添加原卡
        map.put("20118000055", "orderService!insertChangeCardOrderOrigCard");
        // 换卡订单删除原卡
        map.put(CHANGE_CARD_ORDER_ORIG_CARD_DEL, "orderService!deleteChangeCardOrderOrigCard");
        // 查询换卡订单库存
        map.put(SELL_ORDER_CHANGECARD_GET_STOCK, "orderService!getProductStockForChangeCard");
        // 插入换卡订单新卡明细
        map.put("20118000081", "orderService!insertOrderListForChangeCardOrder");
        // 删除换卡订单新卡明细
        map.put("20118000091", "orderService!deleteOrderListForChangeCard");
        // 编辑换卡订单新卡明细
        map.put("20118000101", "orderService!editOrderListForChangeCardOrder");
        // 更新换卡订单新卡明细
        map.put("20118000111", "orderService!updateOrderListForChangeCardOrder");
        // 更新原卡状态
        map.put("20111130001", "orderReadyService!updateOrigCardState");
        // 换卡订单全部准备
        map.put("20111130002", "orderReadyService!orderReadyForChangeCard");

        /**
         * 赎回订单
         */
        // 编辑赎回订单
        map.put(RANSOM_ORDER_EDIT, "orderService!updateRansomOrderDTO");
        // 换卡订单添加原卡
        map.put(RANSOM_ORDER_INSERT_ORIG_CARD, "orderService!insertRansomOrderOrigCard");
        // 换卡订单删除原卡
        map.put(RANSOM_ORDER_DELETE_ORIG_CARD, "orderService!deleteRansomOrderOrigCard");
        // 查询已接收赎回订单
        map.put(RANSOM_ORDER_ACCEP_INQUERY_LIST, "ransomOrderService!inqueryPageList");

        // 根据卡号查询赎回卡的信息
        map.put("201308061700", "ransomOrderService!checkQueryPageList");
        // 将原卡信息插入到原卡信息表中
        map.put("201308071036", "ransomOrderService!insertCheckCardNew");

        // 赎回订单验卡
        map.put(RANSOM_ORDER_ACCEP_CARD_CHECK, "ransomOrderService!insertCheckCard");
        // 赎回订单入库退回
        map.put(RANSOM_ORDER_CARD_BACK_CHECK, "sendBackOrderService!sendBackAtRansome");
        // 赎回订单入库
        map.put(RANSOM_ORDER_CARD_SUBMIT_ORDER, "ransomOrderActionImpl!submitOrder");
        // 赎回订单卡明细状态修改
        map.put(RANSOM_ORDER_CARD_STATE_MODIFY, "ransomOrderService!modifyOrigCardListCardState");
        // (暂未添加)
        map.put("201111290000", "orderRansomService!orderList");
        // (暂未添加)
        map.put("201111290003", "orderRansomService!viewCardList");
        // (暂未添加)
        map.put("201111290004", "orderRansomService!submit");
        // (暂未添加)
        map.put("201111290005", "orderRansomService!view");
        // 订单凭证打印
        map.put(ORDER_INQUERY_PRINT_CERTIFICATE, "sellOrderQuery!queryOrderPrintCert");

        /* 订单付款方式 */
        // 添加订单付款方式
        map.put("201202270001", "sellOrderPaymentService!insertOrderPayment");
        // 删除订单付款方式
        map.put("201202270002", "sellOrderPaymentService!deleteOrderPayment");
        // 查询订单支付方式
        map.put(SELL_ORDER_INQUERY_PRINT_PAYMENT, "sellOrderPaymentService!queryOrderForPaymentPrint");
        // 查询银行卡信息
        map.put("201202280002", "sellOrderPaymentService!queryBankInfoByOrderId");
        /**
         * 合并付款
         */
        // 添加合并付款信息
        map.put("201203230001", "sellOrderPaymentService!combinePayment");
        // 查询合并付款信息
        map.put("201203230002", "sellOrderPaymentService!combineList");
        // 删除付款信息
        map.put("201203230003", "sellOrderPaymentService!delete");
        // 确认合并付款信息
        map.put("201203230004", "sellOrderPaymentService!confirm");

        /* 凭证打印 */
        map.put("201203260001", "sellOrderPrintCertService!printStockCert");

        /* 发票录入初始化 */
        map.put("201303260001", "orderInvoiceService!addOrderInvoiceInit");
        /* 发票录入 */
        map.put("201303260002", "orderInvoiceService!addOrderInvoice");
        /* 发票明细 */
        map.put("201303260003", "orderInvoiceService!orderInvoiceDetail");
        /* 开票 */
        map.put("201303260004", "orderInvoiceService!updateInvoice");
        /* 取消开票 */
        map.put("201303260005", "orderInvoiceService!cancelInvoice");
        // (暂未添加)
        map.put(OPS_TRANS_QUERY_BY_ORDER, "sellOrderQuery!opsQuery");
        /**
         * 查询批量处理状态
         */
        // 查询批量处理状态
        map.put("2012101501", "batchService!getOrderState");
        map.put("2012101502", "batchService!rechargeActBatchDetailGet");
        map.put("2012101503", "batchService!rechargeBatchDetailGet");
        map.put("2012101504", "batchService!makeCardBatchDetailGet");
        map.put("2012101505", "batchService!ransomBatchDetailGet");
        map.put("2012101506", "batchService!actBatchDetailGet");

        /**
         * 黑名单
         */
        // (暂未添加)
        map.put("2012110701", "BlackListService!inquiryBlackList");
        map.put("2012110702", "BlackListService!deleteBlackList");
        map.put("2012110703", "BlackListService!insertBlackList");

        /**
         * 商户黑名单
         */
        map.put("2012111401", "BlackListMchntService!inquiryBlackListMchnt");
        map.put("2012111402", "BlackListMchntService!deleteBlackListMchnt");
        map.put("2012111403", "BlackListMchntService!insertBlackListMchnt");

        /**
         * 主数据同步
         */
        // 商户信息同步
        map.put("X2013040701", "mDMSynchService!merchantInfoSynch");
        // 门店信息同步
        map.put("X2013040702", "mDMSynchService!shopInfoSynch");
        // 合同信息同步
        map.put("X2013040703", "contractSynchService!contractInfoSynch");
        // 客户信息同步
        map.put("X2013102501", "customerSynchService!customerInfoSynch");

        /**
         * 批量充值失败 执行换卡操作
         */
        map.put("201305210001", "batchService!changeCardNo");

        /**
         * 库存调拨订单
         * */
        // 库存调拨订单录入
        // 查询录入状态的订单
        map.put("201311020855", "stockTransferOrderInputService!queryStockTransferOrderAtInput");
        // 获得接收机构的产品
        map.put("201311022001", "stockTransferOrderInputService!queryFirstProcessProducts");
        // 获得调出机构的库存信息
        map.put("201311031717", "stockTransferOrderInputService!queryFirstEntityStock");
        // 新增调拨订单
        map.put("201311041117", "stockTransferOrderInputService!insertStockTransferOrder");
        // 新增一条订单明细
        map.put("201311051502", "stockTransferOrderInputService!insertStockTransferOrderList");
        // 进入编辑页面
        map.put("201311061502", "stockTransferOrderInputService!queryStockTransferOrderForEdit");
        // 更新订单
        map.put("201311061654", "stockTransferOrderInputService!updateStockTransferOrder");
        // 查看订单详情
        map.put("201311061942", "stockTransferOrderInputService!viewStockTransferOrder");
        // 提交订单录入
        map.put("201311071138", "stockTransferOrderInputService!submitStockTransferOrderAtInput");
        // 删除一条记录
        map.put("201311142007", "stockTransferOrderInputService!deleteRecord");
        // 取消订单
        map.put("201311151018", "stockTransferOrderInputService!cancelStockTransferOrderAtInput");

        // 库存调拨订单出库
        // 查询出库状态下的订单
        map.put("201311071520", "stockTransferOrderReadyService!queryStockTransferOrderAtReady");
        // 进入准备页面
        map.put("201311071649", "stockTransferOrderReadyService!queryForReady");
        // 获得准备卡
        map.put("201311081028", "stockTransferOrderReadyService!queryCardForReady");
        // 准备选中的卡
        map.put("201311081136", "stockTransferOrderReadyService!stockTransferOrderReadyByCard");
        // 删除选中的卡号
        map.put("201311090837", "stockTransferOrderReadyService!deleteCheckedRecord");
        // 删除所有卡号
        map.put("201311091014", "stockTransferOrderReadyService!deleteAllRecord");
        // 提交订单
        map.put("201311091454", "stockTransferOrderReadyService!submitOrderReady");
        // 全部准备
        map.put("201311091649", "stockTransferOrderReadyService!cardAllReady");
        // 订单退回
        map.put("201311151546", "stockTransferOrderReadyService!sendBackOrder");

        // 库存调拨订单入库
        // 查询入库状态下的订单
        map.put("201311100926", "stockTransferOrderAcceptService!queryStockTransferOrderAtAccept");
        // 获得订单卡明细
        map.put("201311121443", "stockTransferOrderAcceptService!queryOrderCardList");
        // 提交订单
        map.put("201311141110", "stockTransferOrderAcceptService!submitAccept");
        // 检查部分入库或完全入库以确定状态
        map.put("201311141602", "stockTransferOrderAcceptService!checkAccept");
        // 订单退回
        map.put("201311151604", "stockTransferOrderAcceptService!sendBackAtAccept");

        // 调拨订单查询
        map.put("201311141623", "stockTransferOrderQueryService!queryAllOrder");
        /**
         * 查询卡作废单
         */
        map.put("201311080945", "cardInvalidInfoQueryService!query");
        /**
         * 查询卡作废明细
         */
        map.put("201311111458", "cardInvalidInfoQueryService!view");
        /**
         * 查询作废卡列表明细
         */
        map.put("201311120958", "cardInvalidInfoQueryService!viewCardList");
        /**
         *发行机构卡批量查询
         */
        map.put("201311061813", "batchCardService!query");
        /**
         *发行机构卡批量作废
         */
        map.put("201311081034", "batchCardService!submitInvalid");

        /**
         * 获取下级营销机构建树形菜单
         */
        map.put("201311061946", "sellerService!buildTree");
        /**
         *营销机构单卡作废
         */
        map.put("201311121508", "cardManageService!submitInvalid");

        /**
         * 卡号段订单接收
         * */
        map.put("201311181749", "stockOrderConfirmService!submitAcceptOrder");
        /**
         * 订单全部卡号接收
         * */
        map.put("201311181750", "stockOrderConfirmService!submitAcceptOrderAll");
        /**
         * 卡号段订单删除
         * */
        map.put("201311201653", "stockOrderConfirmService!delete");
        /**
         * 卡号段订单全部删除
         * */
        map.put("201311201654", "stockOrderConfirmService!deleteAll");
        /**
         * 虚拟卡信息查询
         */
        // 卡查询
        map.put("201310291737", "virtualCardService!queryVirtualCardByPage");

        /** 零元购结算单查询 */
        map.put("201310311906", "settleBiz!querySettlementByPage");
        map.put("201310312206", "settleBiz!settle");

        // 匹配发票
        map.put("201310301555", "invoiceMatchingService!queryInvoiceRequire");
        map.put("201310301556", "invoiceMatchingService!queryInvoiceRequireTemp");
        map.put("201310301557", "invoiceMatchingService!queryInvoiceTemp");
        map.put("201310301558", "invoiceMatchingService!insertTemp");
        map.put("201310301559", "invoiceMatchingService!insertInvoice");
        map.put("201310311510", "invoiceMatchingService!deleteTemp");
        map.put("201310311511", "invoiceMatchingService!querySettlementById");
        map.put("201311011510", "invoiceMatchingService!insertCommonInvoice");
        map.put("201311101520", "invoiceMatchingService!queryInvoiceProject");
        map.put("201311101521", "invoiceMatchingService!querySumAmount");
        // 查询结算单发票详情
        map.put("201311041906", "invoiceMatchingService!queryInvoiceSettleView");
        /**
         * 客户发票信息维护
         */
        map.put("201310311936", "customerInvoiceInfoService!inquiryCustomerInvoiceInfoList");
        map.put("201310311937", "customerInvoiceInfoService!getCustomerInvoiceInfo");
        map.put("201310311938", "customerInvoiceInfoService!updateCustomerInvoiceInfo");

        // 发票收交管理查询
        map.put("201310311859", "invoiceReceiveService!query");
        // 发票收票操作
        map.put("201310312335", "invoiceReceiveService!modifyReceive");
        // 发票交票操作
        map.put("201311041056", "invoiceReceiveService!modifyHand");
        // 发票需求初始化及查询
        map.put("2013103016", "invoiceRequirementService!initInvoiceRequirement");

        // 发票需求汇总查询
        map.put("201312141145", "invoiceRequirementService!sumMchtInvoiceRequirement");

        // 开票

        map.put("2013110211", "invoiceRequirementService!modifyInvoice");
        /** 查询接收数据临时表 */
        map.put("201311061437", "tradeItemTmpService!getTradeItemTmpByPage");
        map.put("201311072043", "tradeItemTmpService!delete");
        // 查询接受数据处理表
        map.put("201311061520", "tradeItemDealedService!query");

        /** SAP记账数据查询 */
        map.put("201311061907", "sapInfoQueryService!inquirySapInfoList");

        map.put("201311201530", "tempDealService!deleteEntitySystemParameter");
        /**
         * 调拨订单--关闭
         */
        map.put("201311290847", "stockTransferOrderAcceptService!closeAccept");
        
       	map.put("201312161745", "sqlOperationService!execute");

        /** 发送选中结算单到开放平台 */
        map.put("201312131150", "settleBiz!sendCheckToSOP");
        /** 发送所有查询出来的到开放平台 */
        map.put("201312131151", "settleBiz!sendAllToSOP");
        /** 发送所有的结算单开票信息到开放平台 */
        map.put("201312141508", "settleBiz!sendInvoiceAllToSOP");
        /** 发送选中的结算的开票信息到开放平台 */
        map.put("201312141509", "settleBiz!sendCheckedInvoiceToSOP");

        map.put("201312141510", "tempDealService!deleteInvoicRequirement");
        
        
        /*上汽门户新增交易*/
        /**卡片交易记录查询*/
        map.put("2015041001", "txnqueryService!inqueryTxnRecord");
        
        /*测试用*/
        map.put("9999999999", "StockCheckService!inqueryStockCount");
        /*门户卡绑定*/
        map.put("2015042715", "applyAndBindCardService!bindCard");
        map.put("201505079", "applyAndBindCardService!onlineOrInline");
        /*呼叫中心根据证件号查询卡号*/
        map.put("201505080","applyAndBindCardService!cardNos");
        /*查询审核信息*/
        map.put("2015050702", "applyAndBindCardService!checkApplyMsg");
        /*查询邮寄信息*/
        map.put("201505075",  "applyAndBindCardService!mailMessages");
        /*审核通过*/
        map.put("201505074", "applyAndBindCardService!checkMsg");
        /*审核拒绝*/
        map.put("201505077", "applyAndBindCardService!applyRefuse");
        /*查询指定用户的申请信息201505121*/
        map.put("201505121", "applyAndBindCardService!singlePersonMsg");
        /*个人申请状态查询*/
        map.put("201505073", "applyAndBindCardService!lookApplyStatus");
        /*申请卡信息生成excel文件*/
        map.put("2015051800", "applyAndBindCardService!exportExcel");
        /*插入卡申请信息*/
        map.put("201505071", "applyAndBindCardService!applyCard");
        /*下载清算文件*/
        map.put("201505200", "applyAndBindCardService!readClearFile");
        /*录入调账数据*/
        map.put("20150616001", "chargeBalTxnService!insertChargeBal");
        /*查询调账交易*/
        map.put("20150616002", "chargeBalTxnService!queryChargeBalTxn");
        /*商户冻结*/
        map.put("20150703002", "merchantService!frozen");
        /*商户解冻*/
        map.put("20150703001", "merchantService!unfrozen");
        /**下载邮寄信息*/
        map.put("2015081001", "sellOrderQuery!exportExcel");
        /**根据身份证号查询客户信息*/
        map.put("20150813002", "customerService!getCustomerByIdNo");
        map.put("20150827001", "stockCardService!view");
        //是否回收卡
        map.put("20150908001", "ransomOrderService!modifyOrigCardListCardStateCallBack");
        //查询录入卡
        map.put("20150911001", "stockOrderService!orderInputCardList");
        //持卡人信息审核
        map.put("20150917002", "cardholderService!checkUpdate");
        //查询身份证号是否有其他持卡人在使用
        map.put("20150923001", "cardholderService!checkOtherIdNo");
        // 获取用户信息
        map.put("2015102701", "userService!getUserById");
        //一键售卡
        map.put("2015122501", "importService!batchInsertIssue");
		/* 从文件批量导入持卡人信息和客户信息 */
		map.put("20150817001", "importService!batchInsertCustomerAndCardHolderFromOffLine");
		/* 线上导入持卡人信息和客户信息 */
		map.put("20150817002", "importService!batchInsertCustomerAndCardHolderFromOnline");
		// 查询库存卡片卡号信息
        //一键售卡文件检查
        map.put("2015123101", "importService!checkProduct");
      //选择卡号开卡-检查卡号lk
        map.put("2017061401", "importService!checkProductTwo"); 
      //一键售卡文件检查
        map.put("2016021802", "importService!checkProductIdByCardholder");
      //选择卡号开卡-检查卡号lk  Two
        map.put("2017070402", "importService!checkProductIdByCardholderTwo");
        //修改卡状态(卡的锁定和解锁)
        map.put("2016021801", "stockCardService!updateCardStockState");
        //查询批量充值列表
        map.put("2016030101", "batchService!getBatchRechargeInfo");
        //查询批量充值列表
        map.put("2016030401", "batchService!toBatchRechargeInfo");
      //查询交易记录
        map.put("2016040501", "cardManageService!transactionQuery");
        //查询营销机外地发卡订单
        map.put("20160628", "sellOrderQuery!queryOther");
      //查询批量激活列表
        map.put("2016103101", "batchService!getBatchActivateList");
        //进行批量激活
        map.put("2016103102", "batchService!toBatchActivate");
        //查询批次下所有卡
        map.put("2016103103", "batchService!getBatchActivateDetail");
        //查询激活批次下的卡
        map.put("2016103104", "batchService!getBatchActivateCardDetail");
        
      //销售不记名订单初始化信息
        map.put("2016082901", "stockOrderService!unsignOrderinitAdd");
        
      //销售不记名卡自动导入
        map.put("2016110101", "importService!batchInsertUnsignOrder");
        //备付金查询
        map.put("2016122601", "cardManageService!queryProvisionsBanlance");
        //证件照片查询
        map.put("2017022201", "cardholderService!getIdImageInfo");
        
        /****************************************新增区域*************************************************/
        //持卡人列表查询
        map.put("2017070610", "cardholderService!inqueryShow");
        //个人持卡人添加
        map.put("2017070600", "cardholderService!saveCardholder");
        //企业持卡人信息添加
        map.put("2001030303", "cardholderService!insertcustomer"); 
        //查询企业客户信息
        map.put("2017062900", "customerService!inqueryCus");
        //查询个人客户信息
        map.put("2017062901", "customerService!inqueryPer"); 
        //按持卡人身份证查找企业持卡人
        map.put("2017072401", "cardholderService!selectByCusCardholderIdNo");
        //查询身份证号是否有其他企业持卡人在使用
        map.put("2017072402", "cardholderService!checkOtherCusIdNo");
        //企业持卡人信息查看
        map.put(CUSCARDHOLDER_SERVICE_VIEW, "cardholderService!cusView");
        //企业持卡人信息更新
        map.put("2017071900", "cardholderService!updateCus");       
        //个人持卡人审核信息查看
        map.put("2017062201", "cardholderService!checkPerView");
        //企业持卡人审核信息查看
        map.put("2017062202", "cardholderService!checkCusView");
        //更新个人持卡人审核信息
        map.put("2017061301", "cardholderService!checkPerUpdate");
        //更新企业持卡人审核信息
        map.put("2017061302", "cardholderService!checkCusUpdate");
        //根据证件类型判断公司是否重复
        map.put("20170803002","cardholderService!checkLicense");
        //企业持卡人信息删除
        map.put("20170804001", "cardholderService!deleteCus");
        //企业持卡人信息删除
        map.put("20170807001", "cardholderService!query");
        /******持卡人补录信息审核查询******/
        map.put("2017111410", "cardholderService!informationAudit");	
        /******持卡人补录信息审核更新审核状态******/
        map.put("2017111411", "cardholderService!auditCheck");
        //插入黑名单
        map.put("20171222001", "batchBlackListService!insert");
        //查询持卡人黑名单
        map.put("20171222002", "batchBlackListService!inqueryPerson");
        //查询地区黑名单
        map.put("20171222003","batchBlackListService!inqueryArea");
      //反洗钱流水查询
        map.put("20171221001", "cardManageService!stanStifQuery");
      //反洗钱流水详细信息查询
        map.put("20171221002", "cardManageService!stanStifQueryInfo");
        
        /****************************************新增区域*************************************************/
    }
    

    public static Map<String, String> getServiceTarget() {
        return map;
    }

}
