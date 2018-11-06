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
function toBatchActivate(){
	document.queryForm.action = "${ctx}/batchfile/tobatchActivate.action";
  	document.queryForm.submit();
	 
}

function view(batchNo,flag){
	if (flag == '激活'){
		window.open("${ctx}/batchfile/activateCardView.action?batchActivateDTO.batchNo=" + batchNo,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	}else{
		window.open("${ctx}/batchfile/activateView.action?batchActivateDTO.batchNo=" + batchNo,"","top=50,left=50,width=1000,toolbar=yes,scrollbars=yes");
	}
}
</script>

<title>不记名卡批充激活管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>不记名卡批充激活管理</span>
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
            <s:form id="queryForm" name="queryForm" action="batchfile/batchActivateListAction.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">批次号：</td>
                                    <td><s:textfield name="batchActivateDTO.batchNo"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">文件名：</td>
                                    <td><s:textfield name="batchActivateDTO.fileName"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">卡号：</td>
                                    <td><s:textfield name="batchActivateDTO.cardNo"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">开始时间：</td>
                                    <td><s:textfield name="batchActivateDTO.beginTime" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">结束时间：</td>
                                    <td><s:textfield name="batchActivateDTO.endTime" required="true"  onfocus="dateClick(this)"
												cssClass="Wdate"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                        	<table style="text-align: left; width: 100%">
                                <tr>
                                	<td style="width: 90px; text-align: right;" nowrap="nowrap">
											交易类型：
										</td>
									<td>
										<s:select name="batchActivateDTO.flag" id="batchActivateDTO.flag" list="#{'':'--请选择--','01':'首充','02':'激活','03':'再充值'}"/>
							        </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    	<table style="text-align: left; width: 100%">
                    		<tr>
		                    	<td style="width: 92px; text-align: right;" nowrap="nowrap">
										批处理状态：
									</td>
								<td>
									<s:select name="batchActivateDTO.activateState" id="batchActivateDTO.activateState" list="#{'':'--请选择--','00':'成功','01':'处理中','02':'失败'}"/>
						        </td>
						        
						        <td colspan="3" style="margin-left: 30px;">
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
                    </tr>
                    <tr>
                        
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
            <s:form id="batchActivateForm" name="batchActivateForm" action="batchfile/batchActivateListAction.action" method="post">
             <s:hidden name="batchActivateDTO.beginTime"/>
             <s:hidden name="batchActivateDTO.endTime"/>
              <s:hidden name="batchActivateDTO.batchNo"/>
              <s:hidden name="batchActivateDTO.fileName"/>
              <s:hidden name="batchActivateDTO.activateState"/>
              <s:hidden name="batchActivateDTO.flag"/>
              <s:hidden name="batchActivateDTO.cardNo"/>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="batchActivateForm"
                    action="${ctx}/batchfile/batchActivateListAction.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                      <ec:column property="batchNo" title="批次号" width="5%">
						<a href="javascript:view('${map.batchNo}','${map.flag }');">
							${map.batchNo}</a>
					  </ec:column>
					  <ec:column property="fileName" title="文件名" width="6%"  />
                      <ec:column property="activateNum" title="卡数" width="6%"  />
                      <ec:column property="createTime" escapeAutoFormat="true" title="批处理时间"  width="14%" cell="date" format="yyyy-MM-dd HH:mm:ss"  />
                      <ec:column property="createUser" title="操作人"  width="10%" />
                      <ec:column property="flag" title="交易类型"  width="10%" />
                      <ec:column property="activateState" title="批处理状态" width="10%" /> 
                      <ec:column property="failReason" title="交易描述" width="10%" /> 
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
												onclick="toBatchActivate()"
												value="导入" />
										</td>
										
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
</body>
</html>