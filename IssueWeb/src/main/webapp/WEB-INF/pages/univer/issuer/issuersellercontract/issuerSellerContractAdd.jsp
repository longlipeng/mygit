<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>添加营销机构合同</title>
<script type="text/javascript">

    function choiceSeller() {
    	  var sellerDTO = window.showModalDialog('${ctx}/issuerSeller/choice.action?'+Math.random(), '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
          if (sellerDTO != null) {
              document.getElementById('contractBuyer').value = sellerDTO['entityId'];
              document.getElementById('sellerName').value = sellerDTO['sellerName'];
          }
    }

    function choiceSettleRule() {
        var contractBuyer = document.getElementById('contractBuyer').value;
        if (contractBuyer == null || contractBuyer == '') {
        	errorDisplay('请先选择营销机构！');
            return;
        }
        var ruleResult = window.showModalDialog('${ctx}/sellerConstract/settleRuleChoice.action?'+Math.random(), '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if(ruleResult != null){
			var ruleArr = ruleResult.split(',');
			document.getElementById('ruleNo').value = ruleArr[0];
			document.getElementById('ruleName').value = ruleArr[1];
        }
    }
    
    Date.prototype.format = function(format){ 
		var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
		} 

		if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		} 

		for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
		} 
		return format; 
		} 

    
    function insertContract(){
    	var fee=document.getElementById("fee").value;
        if(fee==''){
       	 errorDisplay('快递费不能为空！');
			 return;
         }else{
        	 var str=/^\d+(\.\d+)?$/; 
             if(!str.test(fee)) {
       	        alert("快递费输入格式错误，必须在0-99999999.99的数字");
				return;
             }
         }
        var ltime=document.getElementById("cardValidityPeriod").value;
        var time2 = new Date().format("yyyy-MM-dd");  
        if(ltime=='ltime'){
          	 errorDisplay('失效时间不能为空！');
   			 return;
            }else{; 
                if(ltime<time2) {
               	 errorDisplay("失效时间要大于当前时间！");
   				return;
                }
            }
    	document.getElementById('deliveryFee').value=fee*100;
    	document.getElementById("contractForm").action ='${ctx}/issuerSellerConstract/insert.action';
    	document.getElementById("contractForm").submit();
    }
    
</script>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>添加营销机构合同信息</span>
    </div>
    <s:form id="contractForm" name="contractForm" action="insertIssuerSellerContract/insertIssuerSellerContract.action" method="post">
        <s:token></s:token>
        <div id="base" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="baseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">基本信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="baseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>营销机构号：</td>
                                    <td><s:textfield id="contractBuyer" name="sellerContractDTO.contractBuyer" cssClass="watch" readonly="true" onclick="choiceSeller()"/>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.contractBuyer</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">营销机构名称：</td>
                                    <td><s:textfield id="sellerName" name="sellerContractDTO.contractBuyerName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>结算周期规则：</td>
                                    <td><s:textfield id="ruleNo" name="sellerContractDTO.settlePeriodRule" cssClass="watch" readonly="true" onclick="choiceSettleRule()"/>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.settlePeriodRule</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">结算周期规则名称：</td>
                                    <td><s:textfield id="ruleName" name="sellerContractDTO.settlePeriodRuleName" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>失效时间：</td>
                                    <td>
                                       	<s:textfield name="sellerContractDTO.expiryDate"
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
										</s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.expiryDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>合同状态：</td>
                                    <td>
                                        <s:select name="sellerContractDTO.contractState" list="#{'1':'有效', '0':'无效'}" />
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.contractState</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费：</td>
                                    <td><s:textfield id="fee" name="deliveryFeeShow" maxlength="128" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee && sellerContractDTO.deliveryFee!=0) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>
                                        <s:hidden name="sellerContractDTO.deliveryFee" id="deliveryFee"></s:hidden>
                                        <s:fielderror>
                                            <s:param>sellerContractDTO.deliveryFee</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 140px; text-align: right;">
											<span class="no-empty">*</span>结算类型：
										</td>
										<td>
											<s:select list="#{'1':'汇总结算'}"
												name="sellerContractDTO.clearTp"
												id="sellerContractDTO.clearTp" label="规则类型：" />											
											<s:fielderror>
												<s:param>sellerContractDTO.clearTp</s:param>
											</s:fielderror>
										</td>
									</tr>
								</table>
							</td>
                    </tr>
                </table>
            </div>
        </div>
        
<%--        <div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">--%>
<%--            <div id="specialtyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">--%>
<%--                <table width="100%" border="0" cellpadding="0" cellspacing="0">--%>
<%--                    <tr>--%>
<%--                        <td class="TableTitleFront">--%>
<%--                            <span class="TableTop">特殊信息</span>--%>
<%--                        </td>--%>
<%--                        <td class="TableTitleEnd">--%>
<%--                            &nbsp;--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </table>--%>
<%--            </div>--%>
<%--            <div id="specialtyTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">--%>
<%--                <table width="100%" style="table-layout: fixed;">--%>
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>快递费：</td>--%>
<%--                                    <td><s:textfield name="deliveryFeeShow" value="%{(sellerContractDTO != null && null != sellerContractDTO.deliveryFee ) ? (sellerContractDTO.deliveryFee / 100) : 0}" onchange="document.getElementById('deliveryFee').value = (this.value != null ? (this.value * 100) : 0)"  readonly="sensitive"></s:textfield>--%>
<%--                                        <s:hidden name="sellerContractDTO.deliveryFee" id="deliveryFee"></s:hidden>--%>
<%--                                        <s:fielderror>--%>
<%--                                            <s:param>sellerContractDTO.deliveryFee</s:param>--%>
<%--                                        </s:fielderror>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        --%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否支持网上支付：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webPayStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否短信通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.smsSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>交易是否邮件通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.emailSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否短信通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webSmsSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>网上支付是否邮件通知：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.webEmailSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <td colspan="2">--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 140px; text-align: right;"><span style="color: red">*</span>是否开通月报服务：</td>--%>
<%--                                    <td>--%>
<%--                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerContractDTO.monstmtSvcStat"></s:select>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </table>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        --%>
        <%--
        <div id="productInformation">
            <s:if test="sellerContractDTO.productId != null">
                <s:include value="productInput.jsp"></s:include>
            </s:if>
        </div>
        --%>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="insertContract();" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/issuerSellerConstract/issuerSellerInquery.action';" value="返 回"/>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
</body>
</html>