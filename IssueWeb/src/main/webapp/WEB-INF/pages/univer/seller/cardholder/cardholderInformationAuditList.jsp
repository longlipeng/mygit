<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
function js_method(){	
	var cardholderState;
	var cardholderId;
	var customerType;
	var checkboxs = document.getElementsByName('cardholderIds');	
		 var count = 0;
		 var cardholderId="";
		 var type="";
	for (var i = 0; i < checkboxs.length; i++) {
         if (checkboxs[i].checked) {
        	 
        	var ids=checkboxs[i].value.split(",");
        	cardholderId=ids[0];
        	type=ids[1];
            count++;
         }
    }
	if(count<1){
     	errorDisplay("请选择一条记录操作！");
		return;
    }
	if(count>1) {
		errorDisplay("只能对一条记录操作！");
    	return;
	}
	if(type!='未审核') {
		errorDisplay("审核过的记录不能重复审核！");
    	return;
	}
	
	document.getElementById('cardholderDTO.cardholderId').value=cardholderId;
	document.queryForm.action = "${ctx}/cardholder/auditView.action";
 	document.queryForm.submit();
}
</script>

<title>补录信息审核</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>补录信息审核</span>
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
            <s:form id="queryForm" name="queryForm" action="cardholder/informationAudit.action" method="post">
            <s:hidden name="cardholderDTO.cardholderId" id="cardholderDTO.cardholderId"></s:hidden>
            
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人编号：</td>
                                    <td><s:textfield name="pictureInfoQueryDTO.cardholderId"/>
                                        
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人证件号：</td>
                                    <td>
                                        <s:textfield name="pictureInfoQueryDTO.idNo" />
                                       
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
                                    <td><s:textfield name="pictureInfoQueryDTO.firstName"/>
                                       
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">手机号码：</td>
                                    <td><s:textfield name="pictureInfoQueryDTO.cardholderMobile"/>
                                       
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人卡号：</td>
                                    <td>
                                        <s:textfield name="pictureInfoQueryDTO.cardNo" />
                                       
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                             <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width:100px; text-align: right;">持卡人证件类型：</td>
                                    <td>
                                        <edl:entityDictList  displayName="pictureInfoQueryDTO.idType" props="id=\"paymentType\""
												dictValue="${pictureInfoQueryDTO.idType}" dictType="140"
												tagType="2" defaultOption="true" />												
                                    </td>
                                </tr>
                            </table> 
                        </td> 
                    </tr>
                     <tr>
                        <td>
                             <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">上传时间：From</td>
                                    <td>
											 <s:textfield name="pictureInfoQueryDTO.startTime" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                        	<table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">To：</td>
                                    <td>
											 <s:textfield name="pictureInfoQueryDTO.endTime" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td> 
                    </tr>
                    <tr>
                        <td>
                        	<table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width:100px; text-align: right;">审核状态：</td>
                                    <td>
                                      	<s:select list="#{'':'---请选择---','0':'未审核','1':'通过','2':'未通过'}" name="pictureInfoQueryDTO.auditState"></s:select>								
                                    </td>
                                </tr>
                            </table> 
                        </td>       
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
            <s:form id="cardholderForm" name="cardholderForm" action="cardholder/informationAudit.action" method="post">
               <s:hidden name="pictureInfoQueryDTO.cardholderId"/>
	            <s:hidden name="pictureInfoQueryDTO.idNo"/>
	            <s:hidden name="pictureInfoQueryDTO.firstName"/>
	            <s:hidden name="pictureInfoQueryDTO.cardholderMobile"/>
	            <s:hidden name="pictureInfoQueryDTO.cardNo"/>
	            <s:hidden name="pictureInfoQueryDTO.idType"/>
	            <s:hidden name="pictureInfoQueryDTO.startTime"/>
	            <s:hidden name="pictureInfoQueryDTO.endTime"/>
	            <s:hidden name="pictureInfoQueryDTO.auditState"/>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="cardholderForm"
                    action="${ctx}/cardholder/informationAudit.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    
                    <ec:exportXls  fileName="InformationAuditList.xls" tooltip="导出Excel" />
                    <ec:row>
                        <ec:column property="null" alias="cardholderIds" title="选择" width="5%"   headerCell="selectAll">
                            <input type="checkbox" name="cardholderIds" value="${map.cardholderId},${map.auditState}" />
                        </ec:column>
                       <%--  <ec:column property="cardholderId" title="持卡人编号" width="10%"  >
                        <a href="#" onclick="js_method(${map.cardholderId},'${map.customerType}');return false;">
                           <s:property value="#attr['map'].cardholderId" />
                        </ec:column> --%>
                         <ec:column property="cardholderId" title="持卡人编号" width="15%"  />
                        <ec:column property="firstName" title="持卡人姓名" width="15%"  />
                        <ec:column property="cardNo"  title="持卡人卡号" width="15%"  escapeAutoFormat="true"/>
                        <ec:column property="idNo" title="持卡人证件号" width="15%"  escapeAutoFormat="true"/>
                        <ec:column property="cardholderMobile" title="持卡人手机号" width="15%"  />
                        <ec:column property="createTime" title="上传时间" width="15%" cell="date" format="yyyy-MM-dd HH:mm:ss"  />
                        <ec:column property="auditState" title="审核状态" width="15%"  />                       
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
									
									<td>
										<input type="button" class="btn"
											style="width: 50px; height: 20px; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 2px 5px; text-align: right"
											onclick="js_method()" value="审核" />
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