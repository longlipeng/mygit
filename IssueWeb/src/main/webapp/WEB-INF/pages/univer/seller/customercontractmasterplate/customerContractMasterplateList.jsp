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
	function submitForm(method) { 
		var flag = true;
		for(i = 0; i < document.getElementsByName("sellerContractIds").length; i++) {
			if (document.getElementsByName("sellerContractIds").item(i).checked) {
				if (method=="edit") {
					if (flag) {
						flag = false;
						
					} else {
						flag = true;
						errorDisplay("请选择一条记录操作！");
						return;
					}
				}
			}
		}
		if (method=="edit" && !flag) {
			document.customerContractForm.action="${ctx}/customerContract/editMasterplate.action";
			document.customerContractForm.submit();
			return;
		}
		errorDisplay("请选择一条记录！");
	}
</script>
<title>客户合同信息管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>客户合同模板</span>
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
        <div id="listTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="customerContractForm" name="customerContractForm" action="sellerConstract/inquery.action" method="post">
                <s:hidden name="sellerContractQueryDTO.contractBuyer"></s:hidden>
                <s:hidden name="sellerContractQueryDTO.sellContractId"></s:hidden>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="customerContractForm"
                    action="${ctx}/customerContract/masterPlatelist.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="sellerContractIds" title="选择" width="10%"  headerCell="selectAll">
                            	<input type="checkbox" name="sellerContractIds" value="${map.sellContractId}" />
                        </ec:column> 
                        <ec:column property="sellContractId" title="合同号" width="10%"  >
                            <a href="${ctx}/customerContract/viewMasterplate.action?sellerContractDTO.sellContractId=${map.sellContractId}">
                                <s:property value="#attr['map'].sellContractId" />
                            </a>
                        </ec:column>
                        <ec:column property="contractBuyer" title="客户" width="10%"  />
                        <ec:column property="expiryDate" title="失效时间" width="10%" cell="date" format="yyyy-MM-dd"  />
                        <ec:column property="deliveryFee" title="快递费" width="5%" >${map.deliveryFee / 100}</ec:column>
                        <ec:column viewsAllowed="false"  property="webPayStat" title="是否支持网上交易" width="10%">
                            <s:if test="#attr['map'].webPayStat == 1">是</s:if>
                            <s:else>否</s:else>
                        </ec:column>
                         <ec:column property="contractState" title="有效状态" width="10%" >
                         	<s:if test="#attr['map'].contractState == 1">有效</s:if>
                            <s:else>无效</s:else>
                        </ec:column>
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr> 
                                        <td>
											<display:security urlId="40261">
                                            <input type="button" class="btn" style="width: 50px; height: 20px; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 2px 5px; text-align: right" onclick="submitForm('edit')" value="编辑"/>
                                        	</display:security>	
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