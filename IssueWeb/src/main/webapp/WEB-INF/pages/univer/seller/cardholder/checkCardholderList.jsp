<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript" >
function applyCustomer(mark){
	var cardholderState;
	var cardholderId;
	var customerType;
	var checkboxs = document.getElementsByName('cardholderIds');	
		 var count = 0;
	for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
            	var ids=checkboxs[i].value.split(",");
            	cardholderState=ids[1];
            	cardholderId=ids[0];
            	customerType=ids[2];
                count++;
            }
        }
	 if(count<1){
        	errorDisplay("请选择一条记录操作！");
			return;
        }else if(count==1){
        	if(mark==2){
        		if(customerType=='企业持卡人'){
        			submitForm('queryForm', '${ctx}/cardholder/checkCusView.action?sellOrderCardListQueryDTO.cardholderId='+cardholderId+','+mark+'', 'edit', 'cardholderIds');
        		}else{
        			submitForm('queryForm', '${ctx}/cardholder/checkPerView.action?sellOrderCardListQueryDTO.cardholderId='+cardholderId+','+mark+'', 'edit', 'cardholderIds');
        		}        		
        	}else{
        		if(cardholderState=="1"|| cardholderState=="3"){
            		errorDisplay("持卡人信息不能重复审核！");
    				return;
            	}
        		if(customerType=='企业持卡人'){
        			submitForm('queryForm', '${ctx}/cardholder/checkCusView.action?sellOrderCardListQueryDTO.cardholderId='+cardholderId+','+mark+'', 'edit', 'cardholderIds');
        		}
        		else{
        			submitForm('queryForm', '${ctx}/cardholder/checkPerView.action?sellOrderCardListQueryDTO.cardholderId='+cardholderId+','+mark+'', 'edit', 'cardholderIds');
        		}            	
        	}        	
        }else{
        	errorDisplay("请选择一条记录操作！");
			return;
        }	 
}

function js_method(id,type){
	if(type == '个人持卡人'){
		document.getElementById('cardholderDTO.cardholderId').value=id;
		document.queryForm.action = "${ctx}/cardholder/checkPerView.action";
	}else{
		document.getElementById('companyInfoDTO.relationNo').value=id;
		document.queryForm.action = "${ctx}/cardholder/checkCusView.action";
	}	
  	document.queryForm.submit();
}
</script>

<title>持卡人信息审核管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>持卡人信息审核管理</span>
    </div>
    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">查询条件</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="queryTable" style="width:100%; #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="queryForm" name="queryForm" action="cardholder/inqueryBycheck.action" method="post">
            <s:hidden name="cardholderDTO.cardholderId" id="cardholderDTO.cardholderId"></s:hidden>
            <s:hidden name="companyInfoDTO.relationNo" id="companyInfoDTO.relationNo"></s:hidden>
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户号：</td>
                                    <td><s:textfield name="cardholderQueryDTO.entityId"/>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.entityId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:textfield name="cardholderQueryDTO.customerName"/>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.customerName</s:param>
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
                                    <td style="width: 90px; text-align: right;">状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'有效', '0':'无效'}" name="cardholderQueryDTO.cardholderState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人编号：</td>
                                    <td><s:textfield name="cardholderQueryDTO.cardholderId"/>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.cardholderId</s:param>
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
                                    <td style="width: 90px; text-align: right;">持卡人姓名：</td>
                                    <td><s:textfield name="cardholderQueryDTO.firstName"/>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.firstName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">手机号码：</td>
                                    <td><s:textfield name="cardholderQueryDTO.cardholderMobile"/>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.cardholderMobile</s:param>
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
                                    <td style="width: 90px; text-align: right;">持卡人证件号：</td>
                                    <td>
                                        <s:textfield name="cardholderQueryDTO.idNo"  />
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.idNo</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">审核状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'已审核', '0':'审核中','3':'审核未通过','2':'未审核'}" name="cardholderQueryDTO.checkState"></s:select>
                                        <s:fielderror>
                                            <s:param>cardholderQueryDTO.checkState</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
        <div id="listTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">记录列表</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="listTable" style="background-color: #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="cardholderForm" name="cardholderForm" action="cardholder/inqueryBycheck.action" method="post">
                <s:hidden name="cardholderQueryDTO.entityId"></s:hidden>
                <s:hidden name="cardholderQueryDTO.customerName"></s:hidden>
                <s:hidden name="cardholderQueryDTO.externalId"></s:hidden>
                <s:hidden name="cardholderQueryDTO.cardholderId"></s:hidden>
                <s:hidden name="cardholderQueryDTO.firstName"></s:hidden>
                <s:hidden name="cardholderQueryDTO.cardholderMobile"></s:hidden>
                <s:hidden name="cardholderQueryDTO.cardholderSegment"></s:hidden>
                <s:hidden name="cardholderQueryDTO.cardholderState"></s:hidden>
                <s:hidden name="cardholderQueryDTO.checkState"></s:hidden>
                <s:hidden name="cardholderQueryDTO.idNo"></s:hidden>
                <s:hidden name="companyInfo.relationNo"></s:hidden>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="cardholderForm"
                    action="${ctx}/cardholder/inqueryBycheck.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="cardholderIds" title="选择" width="5%"   headerCell="selectAll">
                            <input type="checkbox" name="cardholderIds" value="${map.cardholderId},${map.checkState},${map.customerType}" />
                        </ec:column>
                        <ec:column property="cardholderId" title="持卡人编号" width="10%"  >
                            <a href="#" onclick="js_method(${map.cardholderId},'${map.customerType}');return false;">                           
                                <s:property value="#attr['map'].cardholderId" />
                            </a>                           
                        </ec:column>
                        <ec:column property="firstName" title="持卡人姓名" width="10%"  />
                        <ec:column property="customerName" title="客户名称" width="10%"  />
                        <ec:column property="cardholderMobile" title="手机号码" width="10%"  />
                        <ec:column property="cardholderState" title="状态" width="5%" cell="select" >                         
                        </ec:column>
                        <ec:column property="checkState" title="审核状态" width="10%"  >
                         	<s:if test="#attr['map'].checkState==1">已审核</s:if>
                         	<s:elseif test="#attr['map'].checkState==0">审核中</s:elseif>
                         	<s:elseif test="#attr['map'].checkState==3">审核未通过</s:elseif>
                         	<s:elseif test="#attr['map'].checkState==2">未审核</s:elseif>
                        </ec:column>
                        <ec:column property="customerType" title="客户类型" width="10%"  /> 
                        <ec:column property="riskGrade" title="风险级别" width="8%">
                         	<edl:entityDictList dictValue="${map.riskGrade}" dictType="197" tagType="1"/>
                       	</ec:column>
                        <ec:column property="isblacklist" title="黑名单标识" width="8%">
                     		<edl:entityDictList dictValue="${map.isblacklist}" dictType="196" tagType="1"/>
                        </ec:column>                       
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
									<%-- <td>
										<input type="button" class="btn"
											style="width: 65px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
											onclick="applyCustomer(2)"
											value="资料更新" />
									</td> --%>
									<td>
										<input type="button" class="btn"
											style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
											onclick="applyCustomer(1)"
											value="审核" />
									</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
</body>
</html>