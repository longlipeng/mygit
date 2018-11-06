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
function toBatchRecharge(){
	document.queryForm.action = "${ctx}/batchfile/tobatchRecharge.action";
  	document.queryForm.submit();
	 
}
</script>

<title>批量充值信息管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>批量充值信息管理</span>
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
            <s:form id="queryForm" name="queryForm" action="batchfile/batchRechargeListAction.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">卡号：</td>
                                    <td><s:textfield name="batchRechargeDTO.cardNo"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">充值时间：</td>
                                    <td><s:textfield name="batchRechargeDTO.createTime" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
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
            <s:form id="batchRechargeForm" name="batchRechargeForm" action="batchfile/batchRechargeListAction.action" method="post">
             <s:hidden name="batchRechargeDTO.createTime"/>
              <s:hidden name="batchRechargeDTO.cardNo"/>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="batchRechargeForm"
                    action="${ctx}/batchfile/batchRechargeListAction.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                      <ec:column property="bacthNo" title="批次号" width="20%"  />
                        <ec:column property="cardNo" title="卡号" width="25%"  >
                        </ec:column>
                        <ec:column property="rechargeSum" title="充值金额" width="10%"  />
                        <ec:column property="rechargeFee" title="服务费" width="10%"  />
                        <ec:column property="createTime" title="充值时间"  width="15%" cell="date" format="yyyy-MM-dd hh:mm:ss"  />
                        <ec:column property="createUser" title="操作人"  width="10%" />
                        <ec:column property="rechargeState" title="充值状态" width="10%" value="已充值"  /> 
                        
                    </ec:row>
                </ec:table>
               
            </s:form>
        </div>
        
    </div>
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
										<td>
											
											<input type="button" class="btn"
												style="width: 50px; height: 20px; background: url(${ctx}/images/icon/input.gif) no-repeat; margin: 2px 5px; text-align: right"
												onclick="toBatchRecharge()"
												value="导入" />
										</td>
										
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
</body>
</html>