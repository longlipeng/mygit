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
	function sub(){
		var startDate=document.getElementById('startDate').value;
		var endDate=document.getElementById('endDate').value;
		if(startDate==null||startDate==""){
			errorDisplay("开始时间不能为空！");
			return;
		}
		if(endDate==null||endDate==""){
			errorDisplay("结束时间不能为空！");
			return;
		}
		
		queryForm.action="cardManage/transactionBalanceQuery.action";
		queryForm.submit();
	}
</script>

<title>备付金查询</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>备付金查询</span>
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
           
             <s:form id="queryForm" name="queryForm" action="cardManage/transactionBalanceQuery.action" method="post">
               
                <table width="100%" style="table-layout: fixed;">
                    
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">查询时间From：</td>
                                    <td><s:textfield name="transactionQueryDTO.startDate" id="startDate" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">TO：</td>
                                    <td><s:textfield name="transactionQueryDTO.endDate" id="endDate" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                        <td>
                        <table  style="width: 100%">
                                <tr>
                                    <td align="right">
                                        <input type="button" class="bt" style="margin-right:200px " onclick="sub();" value="查 询"/>
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
            <s:form id="transactionQueryForm" name="transactionQueryForm" action="cardManage/transactionBalanceQuery.action" method="post">
             <s:hidden name="transactionQueryDTO.startDate"/>
              <s:hidden name="transactionQueryDTO.endDate"/>
               <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="transactionQueryForm"
                    action="${ctx}/cardManage/transactionBalanceQuery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:exportXls fileName="TransactionList.xls" tooltip="导出Excel" />
                    <ec:row>
                           
                        <ec:column property="dataDate" escapeAutoFormat="true" title="日期"  width="14%" cell="date" format="yyyy-MM-dd"  >  ${map.dataDate}</ec:column>
                        <ec:column property="totalBal" escapeAutoFormat="true" title="账户总额"  width="10%" />
                        <ec:column property="sumAmtC" escapeAutoFormat="true" title="贷 (入金)"  width="10%" />
                        <ec:column property="sumAmtD" escapeAutoFormat="true" title="借(出金)"  width="10%" />
                        
                    </ec:row>
                </ec:table>
               
            </s:form>
        </div>
        
    </div>
     
</body>
</html>