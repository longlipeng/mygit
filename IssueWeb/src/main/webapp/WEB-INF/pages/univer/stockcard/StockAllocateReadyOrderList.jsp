<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>库存调拨</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayServiceTable = false;
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBody');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBody');
					isDisplayTableBody = true;
				}
			}
			function displayServiceTable() {
				if (isDisplayServiceTable) {
					display('serviceTable');
					isDisplayServiceTable = false;
				} else {
					undisplay('serviceTable');
					isDisplayServiceTable = true;
				}
			}
			
			function cardReady(){
			    var productId="";
			    var stockAmount="";
			    var cardId="";
			    var faceValueType="";
			     var faceValue="";
			     var cardValidDate="";
			    var amountPatrn= /^([0]|([1-9]{1}[0-9]{0,6})){1}([.][0-9]{1,2})?$/;
			    var count=0;
				for(i = 0; i < document.getElementsByName("ec_choose").length; i++) {
					if (document.getElementsByName("ec_choose").item(i).checked) {
						var pro=document.getElementsByName("ec_choose").item(i).value.split(',');
						productId=pro[0];
						stockAmount=pro[1];	
						cardId=pro[2];	
						faceValueType=pro[3];		
						faceValue=pro[4];	
						cardValidDate=pro[5];					
						count++;
					}
				}
				if("非固定"==faceValueType){
				faceValueType="1";				
			}else{
				faceValueType="0";		
			}
				if(count==0){
					errorDisplay("请选择一条订单明细信息!");
					return;
				}
				if(count!=1){
					errorDisplay("只能选择一条订单明细信息!");
					return;
				}
				var cardAmount=document.getElementById('cardAmount');
				if(cardAmount.value==null || cardAmount.value==""){
					errorDisplay("调出数量不能为空!!!");
					return;
				}
				if (!amountPatrn.exec(cardAmount.value)) {
					errorDisplay("调出数量必须为数字");
					cardAmount.value= "";
					amountMaxs[i].focus();
					return;
				}				
					if (parseFloat(cardAmount.value) > parseFloat(stockAmount)) {
						errorDisplay("调出数量必须小于库存数量！！");
						return;
					}
				var returnValue =window.showModalDialog('${ctx}/stockAllocateAction!cardReady.action?orderReadyDTO.orderId=${stockAllocateDTO.allocateId}&orderReadyDTO.firstEntityId=${stockAllocateDTO.allocateIn}&orderReadyDTO.processEntityId=${stockAllocateDTO.allocateOut}&orderReadyDTO.realAmount='+cardAmount.value+'&orderReadyDTO.loginUserId=${stockAllocateDTO.allocateUser}&orderReadyDTO.productId='+productId+'&orderReadyDTO.cardLayoutId='+cardId+'&orderReadyDTO.faceValueType='+faceValueType+'&orderReadyDTO.faceValue='+faceValue+'&orderReadyDTO.cardValidDate='+cardValidDate, '_blank', 'center:yes;dialogHeight:600px;dialogWidth:1200px;resizable:no');
				if(returnValue==1){			 
				  	document.forms[1].action = "stockAllocateAction!toAddOrderList.action";
				  	document.forms[1].submit();
				  }	  
			}
			
			
			function returnCard(){
			var cardNoList=new Array();
				for(i = 0; i < document.getElementsByName("orderListStr").length; i++) {
					cardNoList[i]=document.getElementsByName("orderListStr").item(i).value;
				}
				orderCardListForm.action="stockAllocateAction!returnAllocate?stockAllocateDTO.cardNoList="+cardNoList;
				orderCardListForm.submit();
			}
			
			function closeCard(){
					returnCard();
					window.close();
			}
			
			function submitForm(){
			
				orderCardListForm.action="stockAllocateAction!stockAllocate";
				orderCardListForm.submit();
			}
			function deleteRecord(cardNo){
				orderCardListForm.action="stockAllocateAction!deleteCardList?stockAllocateDTO.cardNo="+cardNo;
				orderCardListForm.submit();
			}
			
			//function  window.onbeforeunload() {
			//	alert("a");
			//	returnCard();
			//}
		</script>
	</head>

	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>添加库存调拨明细信息</span>
		</div>
		<div style="width: 100%" align=center>
			<span style="widht: 98%" style="color: red; align:left;">请注意卡库存数量，如果库存不够请及时下库存订单。</span>
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
									<span class="TableTop">库存信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
							<ec:table items="stockAllocateDTO.orderList.data" var="map" width="100%"
								action="${ctx}/giftSaleOrderAction!addOrderList"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false"
								showPagination="false" showStatusBar="false" tableId="orderList">
								<ec:row>
									<ec:column property="null" alias="ec_choose" title="选择"
									width="5%" sortable="false" headerCell="selectAll"
									viewsAllowed="html">
									<input type="radio" name="ec_choose" value="${map.productId},${map.stockAmount},${map.cardId},${map.faceValueType},${map.faceValue},${map.cardValidDate}" />
									
									</ec:column>								
									<ec:column property="productName" title="产品名称"></ec:column>
									<ec:column property="cardName" title="卡面" width="15%" />
									<ec:column property="faceValueType" title="面额" width="18%">
									</ec:column>
									<ec:column property="faceValue" title="面额值" width="18%">
									</ec:column>
									<ec:column property="cardValidDate" title="有效期" width="20%"
										cell="date" format="yyyy-MM-dd" />
									<ec:column property="stockAmount" title="当前库存" />
								</ec:row>
							</ec:table>
						</div>
					</td>
				</tr>
			</table>
			<br>
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();"
										style="cursor: pointer;">
										<span class="TableTop">订单明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>				
				<div id="serviceTable">
					<s:form id="newForm" name="newForm"
						action="/changeCardOrderAction!insertOrderList" method="post">	
						<s:hidden name="stockAllocateDTO.allocateId" ></s:hidden>
						<s:hidden name="stockAllocateDTO.allocateIn" ></s:hidden>
						<s:hidden name="stockAllocateDTO.allocateOut" ></s:hidden>	
						<s:hidden name="stockAllocateDTO.allocateUser" ></s:hidden>
						<s:hidden name="stockAllocateDTO.allocateDate" ></s:hidden>
						<table width="100%">
							<tr>
								<td align="right" width="15%" nowrap="nowrap">
									<span style="color: red;">*</span>调出数量：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderListDTO.cardAmount" 
										id="cardAmount" size="20"/>
									<s:fielderror>
										<s:param>
											sellOrderListDTO.cardAmount
										</s:param>
									</s:fielderror>
								</td>
							</tr>
						</table>
						
					</s:form>
							</div>
			</div>
		</div>
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
										<span class="TableTop">卡明细信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="orderCardListBody">
								<s:form id="orderCardListForm" name="orderCardListForm" action=""
									method="post">		
									<s:hidden name="stockAllocateDTO.allocateIn" ></s:hidden>
							<s:hidden name="stockAllocateDTO.allocateOut" ></s:hidden>	
							<s:hidden name="stockAllocateDTO.allocateId" ></s:hidden>	
							<s:hidden name="stockAllocateDTO.allocateUser" ></s:hidden>					
								<ec:table items="stockAllocateDTO.orderCardList.data" var="map" width="100%" form="orderCardListForm"
									action="${ctx}/${actionName}!${actionMethodName}"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="orderCard" >
									<ec:row ondblclick="">
										<ec:column property="null" alias="orderListStr" title="选择"
											width="10%" sortable="false" headerCell="selectAll"
											viewsAllowed="html">
											<input type="checkbox" name="orderListStr"
												value="${map.cardNo}" />
										</ec:column>
										<ec:column property="cardNo" title="卡号" width="15%" />
										<ec:column property="faceValue" title="面额" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="null" sortable="false" title="操作" width="15%" sortable="false">
												  <a href="javascript:deleteRecord('${map.cardNo }');"> 删除 </a>
										</ec:column>
									</ec:row>
								</ec:table>
								</s:form>
							</div>
						</td>
					</tr>
				</table>
			<div style="width: 100%" align=center>

			<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						
							<s:form id="EditForm" name="EditForm"
								action="sellOrderAction!edit">
								<s:hidden name="sellOrderDTO.orderId" value="" id="orderId"/>
							</s:form>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											
												<div id="deleteBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="returnCard();">
														删除全部
												</div>	
												
												<div id="buttonCRUD" style="text-align: right; width: 100%">
													  <div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="cardReady();">
														准备
													</div>
													
													<!--
													<div id="addBtn" class="btn"
														style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="orderListAllReady();">
														全部准备
													</div>
													-->
												</div>
												<div style="clear: both"></div>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
			</div>
		<div>
			<p align="right">
				<button class='btn' onclick="closeCard();"
					style="float: right; margin: 5px">
					返 回
				</button>
				<button class='btn' onclick="this.disabled='disabled';submitForm();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000');"
					style="float: right; margin: 5px">
					提 交
				</button>
			</p>
		</div>
	</body>
</html>
