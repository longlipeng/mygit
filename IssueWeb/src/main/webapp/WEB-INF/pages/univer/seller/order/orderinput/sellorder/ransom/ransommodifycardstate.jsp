<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>赎回订单 修改赎回状态</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
	function check(){
	
		var flag = true;
		for(i = 0; i < document.getElementsByName("origCardListStr").length; i++) {
	        if (document.getElementsByName("origCardListStr").item(i).checked) {
	                    flag = false;
	                    break;
	            }
	    }   
	    if(flag){
	        errorDisplay("请选择一条记录");
	        return true;
	    }
	    return false;
	}
	function modifyCardState(dataState){
		if(check()){
			return;
		}
		$('#sellOrderOrigCardListDTO\\.dataState').val(dataState);
		document.origCardListForm.action = "${ctx}/ransomOrderAction!orderCardOperator.action";
	  	document.origCardListForm.submit();
	}

	function updateOrder(){
		document.newForm.action="${ctx}/ransomOrderAction!update.action";
		document.newForm.submit();
	}
		</script>
	</head>
	<body >
		
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>赎回订单编辑</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="ransomOrderAction!update" method="post">
			<s:hidden name="sellOrderDTO.orderType"/>
			
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront"  style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%">
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20" readonly="true" required="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderDate"
												id="sellOrderDTO.orderDate" size="20">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityId" size="20" id="sellOrderDTO.firstEntityId"
												readonly="true"  cssClass="lg_text_gray"/>
										
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>客户名称：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="customerDTO.customerName" size="20"
												readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.firstEntityName
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>销售人员：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.saleMan" id="saleUserId"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.saleMan
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>营销机构：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.firstEntityName"
												size="20" readonly="true" cssClass="lg_text_gray" />
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡余额总计（元）：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.origCardTotalAmt" size="20" maxLength="10" cssClass="lg_text_gray" readonly="true"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.origCardTotalAmt
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>其他退回费用（元）：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.additionalFee"
												 size="20"  />
											<s:fielderror>
												<s:param>
													sellOrderDTO.additionalFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPriceStr" size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
									</tr>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											创建人：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.createUserName" />
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											创建日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:label name="sellOrderDTO.createTime"/>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单状态：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:if test="sellOrderDTO.orderState == 1">
												草稿
											</s:if>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											订单来源：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:hidden name="sellOrderDTO.orderSource"/>
											<s:if test="sellOrderDTO.orderSource == 1">
												系统录入
											</s:if>
											<s:if test="sellOrderDTO.orderSource == 2">
												订单合并
											</s:if>
										</td>
										
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textarea name="sellOrderDTO.memo" cols="70" rows="5"></s:textarea>
											<s:fielderror>
												<s:param>
													sellOrderDTO.memo
												</s:param>
											</s:fielderror>
										</td>
									
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				
			</div>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="ransomOrderAction!edit" name="backForm"
				id="backForm"><s:hidden name="sellOrderDTO.orderId"/><s:hidden name="sellOrderDTO.orderType"/>
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					返回
				</button>
			</s:form>
				<button class='bt' style="float: right; margin: 7px"
					onclick="updateOrder();">
					保存
				</button>
			<div style="clear: both"></div>
		</div>
		
			<!-- 原有卡列表 -->
	<div style="width: 100%" align=center>
		<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
			<tr>
				<td width="98%" height="10" align="center" valign="middle"
					bgcolor="#FFFFFF">
					<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="TableTitleFront" onclick="displayTableBody();"
								style="cursor: pointer;">
								<span class="TableTop">原有卡信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
					<div id="TableBody">
						<s:form id="origCardListForm" name="origCardListForm" action=""
									method="post">
									<input type="hidden" name="sellOrderDTO.orderId" value="${sellOrderDTO.orderId}"/>
									<input type="hidden" name="sellOrderOrigCardListDTO.dataState" id="sellOrderOrigCardListDTO.dataState" value=""/>
									<input type="hidden" name="sellOrderOrigCardListDTO.callBack" id="sellOrderOrigCardListDTO.callBack" value=""/>
						<ec:table items="origCardLists" var="map" width="100%" form="origCardListForm"
									action="${ctx}/ransomOrderAction!toOrderCardOperator"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="origCardLists">
							<ec:row ondblclick="">
								<ec:column property="null" alias="origCardListStr" title="选择"
										width="5%" sortable="false" headerCell="selectAll"
										viewsAllowed="html">
									<s:if test="#attr['map'].cardState == 1|| #attr['map'].cardState == 2">
										<input type="checkbox" name="origCardListStr" value="${map.origCardListId}" />
									</s:if>
								</ec:column>
								<ec:column property="cardNo" title="卡号" width="15%" />
								<ec:column property="productName" title="产品名称" width="15%" />
								<ec:column property="amount" title="余额" width="15%" />
								<ec:column property="firstName" title="持卡人姓名" width="15%" />
								<ec:column property="validityPeriod" title="有效期" width="10%" />
								<ec:column property="cardState" title="操作状态" width="20%" >
									<s:if test="#attr['map'].cardState ==2">
										验收通过-允许入库
									</s:if>
									<s:if test="#attr['map'].cardState == 3|| #attr['map'].cardState == 1">
										验收通过-只允许注销
									</s:if>
								</ec:column>
								<%-- <ec:column property="callBack" title="是否回收" width="15%" >
								  <div>
								        <s:select list="#{'0':'否','1':'是'}"
															name="callBack"
															id="callBack"  />
								</div>
								</ec:column> --%>
							</ec:row>
					</ec:table>
				</s:form>
				<table width="100%" height="20" border="0" cellpadding="0"
						cellspacing="0" style="border-top: 1px solid silver;">
					<tr>
						<td>
							<div id="buttonCRUD"
								style="text-align: right; width: 100%; height: 30px;"
								align="center" >
								<div id="buttonCRUD" style="text-align: right; width: 100%">
									<div id="addBtn" class="btn"
										style="width: 140px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="modifyCardState(2);">
										修改操作方式-允许入库
									</div>
									<div id="addBtn" class="btn"
										style="width: 120px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="modifyCardState(1);">
										修改操作方式-注销
									</div>
								</div>
								<div style="clear: both"></div>
							</div>
						</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
	</div>
	</body>
</html>