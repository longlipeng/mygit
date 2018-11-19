<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<title></title>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
</head>

<script type="text/javascript">
    function viewDetail(mchntSettleId, accType){
    	window.location='${ctx}/mchntSettleManagement/getSettleDetail2.action?mchntSettleId=' + mchntSettleId + '&accType=' + accType;
    }
</script>

<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span></span>
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
            <s:form id="channelForm" name="channelForm" action="channelManagement/inquery.action" method="post">
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="channelForm"
                    action="${ctx}/mchntsettle/inquery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html">
                    <ec:row>                   
                        <ec:column property="settleId" title="结算单号" width="10%" />
                        <ec:column property="merchantId" title="商户号" width="10%" >
                        </ec:column>
                        <ec:column property="merchantName" title="商户名称" width="10%" />
                        <ec:column property="settleStartDate" title="结算起始日期" width="20%" />
                        <ec:column property="settleEndDate" title="结算截止日期" width="20%" />
                        <ec:column property="txnAmt" title="本金" width="10%" />
                   <!-- ec:column property="serviceFee" title="手续费" width="10%" /     
                                    <ec:column property="orderId" title=" " width="10%" >
										<a href="javascript:viewDetail('${map.mchntSettleId}','${map.accType}');">
											查看明细
										</a>
									</ec:column> -->  
                    </ec:row>
                </ec:table>
				<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="history.go(-1);">
													返回
												</div>
											</div>
										</td>
									</tr>
								</table>
            </s:form>
        </div>
    </div>
</body>
</html>