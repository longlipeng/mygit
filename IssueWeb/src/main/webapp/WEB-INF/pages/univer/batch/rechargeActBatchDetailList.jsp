<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<title>批量处理明细</title>
		<script type="text/javascript">
function onload(){
if(${flag}==1){
		TimeOperation(${order_id},
				'rechargeAct',
				"${ctx}/batchfile/getOrderState.action",
				"${ctx}/batchfile/getRechargeActBatchDetail.action?order_id="+${order_id}+"&batch_id="+${batch_id},
				'充值中,请耐心等候...','5s');
	} else if (${flag}==2){
		location.href="${ctx}/batchfile/getRechargeActBatchDetail.action?order_id="+${order_id}+"&batch_id="+${batch_id};
	}
}

</script>
	</head>
	<body onload="onload();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>批量处理明细</span>
		</div>
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
			<div id="listTable"
				style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="batchForm" name="batchForm"
					action="batchfile/getBatchDetail.action" method="post">
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="batchForm" action="${ctx}/batchfile/getBatchDetail.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false"
						showPagination="false">
						<ec:row>
							<ec:column property="cardNo" title="卡号" width="60%" sortable="false"/>
							<ec:column property="txnAmt" title="金额(元)" width="20%" sortable="false"/>
							<ec:column property="respCode" title="错误信息" width="20" sortable="false" >
								<errorMsg:errorMsg respCode="${map.respCode}"></errorMsg:errorMsg>
							</ec:column>
						</ec:row>
					</ec:table>

					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div id="btnDiv" style="text-align: right; width: 100%">
												<button class='bt' type="button"
													style="float: right; margin: 5px 10px"
													onclick="window.close();">
													返 回
												</button>
											</div>
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