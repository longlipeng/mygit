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
function check(){
	var idNo=document.getElementById('idNo').value;
	if(idNo==""){
		alert("证件号不能为空！");
		return;
	}
	queryCardForm.action='${ctx}/cardManage/transactionQueryCardNoByIdNo.action';
	queryCardForm.submit();
	
}

function changeCard(object){
	var kahao = document.getElementById('kahao').value;
	document.getElementById('transactionQueryDTO.cardNo').value=kahao;
	queryForm.action="cardManage/transactionQuery.action";
	queryForm.submit();
//		alert(object.value);//选中的实际值
}


</script>

<title>卡交易记录查询</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>卡交易记录查询</span>
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
            <s:form id="queryCardForm" name="queryCardForm" action="cardManage/transactionQuery.action" method="post">
             <table width="100%" style="table-layout: fixed;border-bottom: 0.01cm #ccc solid;">
                <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件号：</td>
                                    <td><s:textfield name="applyAndBindCardDTO.idNo" id="idNo"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件类型：</td>
                                    <td>
												
												<s:select list="#{'1':'身份证', '2':'护照','3':'其他'}" name="applyAndBindCardDTO.idType" id="idType"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                       
                </tr>
                
               
                 <tr>
                 <td>
                 <table style="text-align: left; width: 100%">
                                <tr>
                                <s:if test="#request.CardNos!=null">
                                    <td style="width: 90px; text-align: right;">请选择卡号：</td>
                                    <td><select name="kahao" id="kahao" class="select"  onChange="changeCard(this)">
  		<option value="">请选择</option>
      <s:iterator value="#request.CardNos" id="cardNo">
      	<option value="${cardNo}">${cardNo }</option>
      </s:iterator></select>
                                    </td>
                                    </s:if>
                                </tr>
                            </table>
                 </td>
                        <td align="right" >
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                          <input type="button" class="bt"  onclick="check();" value="查询卡号"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
             </table>
            </s:form>
             <s:form id="queryForm" name="queryForm" action="cardManage/transactionQuery.action" method="post">
                 <s:hidden name="applyAndBindCardDTO.idNo"/>
               <s:hidden name="applyAndBindCardDTO.idType"/>
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">卡号：</td>
                                    <td><s:textfield name="transactionQueryDTO.cardNo" id="transactionQueryDTO.cardNo" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">交易名称：</td>
                                    <td>
												
												<s:select list="#{'':'--请选择--', '1105':'消费','3105':'消费撤销','5205':'退货','1115':'食堂转充','1105_1':'中石油转充','7125':'protal充值','7225':'批量充值','0004':'订单充值(卡已激活)','0005':'订单充值(卡未激活)','0002':'售卡充值','1305':'调账(长款)','1306':'调账(短款)'}" name="transactionQueryDTO.txnType"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:5%"></td>
                    </tr>
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">交易时间From：</td>
                                    <td><s:textfield name="transactionQueryDTO.startDate" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:5%">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">TO：</td>
                                    <td><s:textfield name="transactionQueryDTO.endDate" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                        <table style="text-align: left; width: 100%">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <!-- <tr>
                        <td align="right" colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr> -->
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
            <s:form id="transactionQueryForm" name="transactionQueryForm" action="cardManage/transactionQuery.action" method="post">
             <s:hidden name="transactionQueryDTO.startDate"/>
             <s:hidden name="transactionQueryDTO.cardNo"/>
              <s:hidden name="transactionQueryDTO.endDate"/>
               <s:hidden name="transactionQueryDTO.txnType"/>
               <s:hidden name="applyAndBindCardDTO.idNo"/>
               <s:hidden name="applyAndBindCardDTO.idType"/>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="transactionQueryForm"
                    action="${ctx}/cardManage/transactionQuery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:exportXls fileName="TransactionList.xls" tooltip="导出Excel" />
                    <ec:row>
                        <ec:column property="cardNo" escapeAutoFormat="true" title="卡号" width="15%"  />
                        <ec:column property="transType" escapeAutoFormat="true" title="交易名称" width="10%"  />
                        <ec:column property="debtOrCredit" escapeAutoFormat="true" title="借贷" width="5%"  />
                        <ec:column property="amt" escapeAutoFormat="true" title="交易金额" width="10%"  />
                        <ec:column property="fee" escapeAutoFormat="true" title="交易服务费" width="9%"  />
                        <ec:column property="accBal" title="卡内余额" width="10%"  />              
                        <ec:column property="dateTxn" escapeAutoFormat="true" title="交易时间"  width="14%" cell="date" format="yyyy-MM-dd HH:mm:ss"  />
                        <ec:column property="transState" escapeAutoFormat="true" title="交易状态"  width="10%" />
                        <%-- <ec:column property="无" escapeAutoFormat="true" title="商户名称" width="8%" sortable="false" /> --%>
                        <ec:column property="mchntCD" escapeAutoFormat="true" title="商户号" width="10%"  />
                        <ec:column property="termId" escapeAutoFormat="true" title="终端号" width="7%" >
                        </ec:column>
                        
                    </ec:row>
                </ec:table>
               
            </s:form>
        </div>
        
    </div>
     
</body>
</html>