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
           
             <s:form id="queryForm" name="queryForm" action="cardManage/stanStifQuery.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">卡号：</td>
                                    <td><s:textfield name="stanStifQueryDTO.CTAC" id="stanStifQueryDTO.CTAC" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">主体姓名/名称：</td>
                                    <td>
										<s:textfield name="stanStifQueryDTO.CTNM" id="stanStifQueryDTO.CTNM" />		
												
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
                                    <td><s:textfield name="stanStifQueryDTO.startDate" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:5%">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">TO：</td>
                                    <td><s:textfield name="stanStifQueryDTO.endDate" required="true"  onfocus="dateClick(this)"
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
            <s:form id="stanStifQueryForm" name="stanStifQueryForm" action="cardManage/stanStifQuery.action" method="post">
             <s:hidden name="stanStifQueryDTO.startDate"/>
             <s:hidden name="stanStifQueryDTO.CTNM"/>
              <s:hidden name="stanStifQueryDTO.endDate"/>
               <s:hidden name="stanStifQueryDTO.CTAC"/>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="stanStifQueryForm"
                    action="${ctx}/cardManage/stanStifQuery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:exportXls fileName="TransactionList.xls" tooltip="导出Excel" />
                    <ec:row>
                        <ec:column property="TICD" escapeAutoFormat="true" title="业务标识号" width="10%"  >
                        	<a href="${ctx}/cardManage/stanStifView.action?stanStifQueryDTO.TICD=${map.TICD}">
                        	${map.TICD}
                            </a>
                        </ec:column>
                        <ec:column property="CTNM" escapeAutoFormat="true" title="主体姓名/名称" width="15%"  />
                        <ec:column property="CTID" escapeAutoFormat="true" title="主体身份证件/证明文件号码" width="25%"  />
                         <ec:column property="CTAC" escapeAutoFormat="true" title="主体的交易账号" width="25%"  />
                        <ec:column property="CRAT" escapeAutoFormat="true" title="交易金额" width="10%"  />
                        <ec:column property="TSTM" escapeAutoFormat="true" title="交易时间"  width="15%" cell="date" format="yyyy-MM-dd HH:mm:ss"  />
                    	
                        
                    </ec:row>
                </ec:table>
               
            </s:form>
        </div>
        
    </div>
     
</body>
</html>