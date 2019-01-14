<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title><s:text name="xxl.order.orderConfirm"/></title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">		 
		function addPayment(){
		var id=document.getElementById('id').value;
		if(null==id || id==""){
			alert("合并付款只能添加单一付款方式");
			return;
		}
			var returnValue = window.showModalDialog('${ctx}/orderPaymentAction!addOrderPayment.action?sellOrderDTO.paymentId='+id+'&sellOrderDTO.totalPrice=${sellOrderDTO.totalPrice}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			if(returnValue==1){
				combineForm.action="orderPaymentAction!combineList";
			  	combineForm.submit();
			  }	  
		 }
	
	
	function deletePayment(){
		var flag = true;
			for(i = 0; i < document.getElementsByName("orderPaymentListStr").length; i++) {
	                  if (document.getElementsByName("orderPaymentListStr").item(i).checked) {
	                              flag =false;
	                              break;
	                      }
	                  }   
	              if(flag){
	                  errorDisplay("请选择一条记录操作");
	                  return;
	              }
	              confirm("确定要删除吗?",delOpr);
	}
	function delOpr(){
			combineForm.action = "orderPaymentAction!deletePayment";
		  	combineForm.submit();			
		}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单合并付款</span>
		</div>
		<script type="text/javascript">
				function submitForm() {
				if(document.getElementsByName("orderPaymentListStr").length==0){
					alert("未添加支付节点不能提交");
					return;
				}				 
					combineForm.action = "orderPaymentAction!confirm?orderPaymentDTO.firstEntityId=${sellOrderDTO.firstEntityId}&orderPaymentDTO.paymentId=${sellOrderDTO.paymentId}";
					combineForm.submit();
				}
				function returnList(){
				combineForm.action = "orderPaymentAction!list";
					combineForm.submit();
				}
		</script>
		<s:form id="combineForm" name="combineForm" method="post">
		
		<div id="orderTable">
							<s:hidden name="sellOrderDTO.paymentId" id="id"></s:hidden>
							<s:hidden name="orderPaymentDTO.sellOrderPaymentDTOs"></s:hidden>
							<s:hidden name="sellOrderDTO.firstEntityId"></s:hidden>
							<s:hidden name="sellOrderDTO.paymentAmount"></s:hidden>
			<table width="100%" border=0>
				<tr>
					<td align="left" width="100%" 
						style="padding: 10px 0 0 0">
						<b>合并总金额：  ${sellOrderDTO.totalPrice } 元</b>
					</td>
				</tr>
				<tr>
					<div id="ContainBox" style="width: 100%" align="center">
								<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
									<tr>
										<td width="98%" height="10" align="center" valign="middle"
											bgcolor="#FFFFFF">
											<table width="100%" height="20" border="0" cellpadding="0"
											cellspacing="0">
												<tr>
													<td class="TableTitleFront" onclick="displayTableBody();"
														style="cursor: pointer;">
															<span class="TableTop">支付节点信息</span>
													</td>
													<td class="TableTitleEnd">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<s:hidden name="orderPaymentList" id="list"></s:hidden>								
								<div id="TableBody" style="width: 98%">
									<s:form id="paymentForm" name="paymentForm" action="orderPaymentAction!submitConfirm"
									method="post">
										<ec:table items="orderPaymentList" var="map" width="100%" form="confirmForm"
											action="${ctx}/orderPaymentAction!submitConfirm"
											imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
											retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderPayment">
											<ec:row>
												<ec:column property="null" alias="orderPaymentListStr" title="选择"
													width="10%" sortable="false" headerCell="selectAll"
													viewsAllowed="html">
													<input type="checkbox" name="orderPaymentListStr"
														value="${map.paymentId}" />
												</ec:column>
												<ec:column property="paymentType" title="支付渠道" width="10%" >
													<dl:dictList dictType="901" tagType="1" dictValue="${map.paymentType}"></dl:dictList>
												</ec:column>
												<ec:column property="paymentAmount" title="支付金额" width="15%" />
												<ec:column property="remark" title="备注信息" />
											</ec:row>
										</ec:table>
									</s:form>
									<table width="100%" height="20" border="0" cellpadding="0"
										cellspacing="0" style="border-top: 1px solid silver;">
										<tr>
											<td>
												<div id="buttonCRUD"
													style="text-align: right; width: 100%; height: 30px;">
													<div id="buttonCRUD" style="text-align: right; width: 100%">
														<div id="deleteBtn" class="btn"
															style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
															onclick="deletePayment();">
															删除
														</div>					
														<div id="addBtn" class="btn"
															style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
															onclick="addPayment();">
															添加
														</div>
														<b>总计:${sellOrderDTO.paymentAmount }&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
													</div>
													<div style="clear: both"></div>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
				</tr>
<!--				<tr>-->
<!--					<td align="right">-->
<!--						<div id="btnDiv" style="text-align: right; width: 300px;">-->
<!--							<button class='btn' style="float: right; margin: 40px 7px 2px"-->
<!--								onclick="window.open('${ctx}/${actionName}!list', '_self')">-->
<!--									返回-->
<!--							</button>-->
<!--							<button class='btn' style="float: right; margin: 40px 7px 2px"-->
<!--								onclick="submitForm();">-->
<!--									提交-->
<!--							</button>-->
<!--							<div style="clear: both"></div>-->
<!--						</div>-->
<!--					</td>-->
<!--				</tr>				-->
			</table>
		</div>
		<div>
			<p align="right">
				<button class='btn' onclick="returnList();"
					style="float: right; margin: 5px">
					返 回
				</button>
				<button class='btn' onclick="this.disabled='disabled';submitForm();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');"
					style="float: right; margin: 5px">
					提 交
				</button>
			</p>
		</div>
		</s:form>
	</body>
</html>
