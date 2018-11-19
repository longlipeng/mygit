<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加赎回订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
		<script type="text/javascript">
		function loadSalesmanList() {
		onOperChange();
        var salesmanList = ${saleUserList};
        var store = new Ext.data.JsonStore({
            data: salesmanList,
            autoLoad: true,
            fields: [{name: 'id', mapping: 'userId'}, {name: 'name', mapping: 'userName'}]
        });
        var combo = new Ext.form.ComboBox({
        	width:120,
            store: store,
            displayField:'name',
            hiddenName: 'sellOrderDTO.saleMan',
            valueField: 'id',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText: '请选择销售人员',

            applyTo: 'saleUserId',
            forceSelection: true
        });
    }
		function orderSubmit(){
			document.newForm.action="${ctx}/ransomOrderAction!update";
			document.newForm.submit();
		}
		function onOperChange(){
			var chooseContact = document.getElementById("contactId").value;
			
				<s:iterator value="customerDTO.contractList" var="map">
					if(chooseContact==${map.contactId}){
						document.getElementById("operPhone").value="${map.contactTelephone}";
						document.getElementById("operCreType").value="${map.papersType}";
						document.getElementById("operCredId").value="${map.papersNo}";
						document.getElementById("operStarValidity").value="${map.starValidity}";
						document.getElementById("operEndValidity").value="${map.endValidity}";
					}			
			     </s:iterator>
		}
		function deleteOrigCard(){
		var flag = true;
		for(i = 0; i < document.getElementsByName("origCardListStr").length; i++) {
            if (document.getElementsByName("origCardListStr").item(i).checked) {
                        flag = false;
                        break;
                }
	    }   
        if(flag){
            errorDisplay("请选择一条记录");
            return;
        }
	    confirm("确定要删除吗?",delOpr);
	}
	function delOpr(){
		document.origCardListForm.action = "${ctx}/ransomOrderAction!deleteOrigCard?sellOrderOrigCardListQueryDTO.orderId=${sellOrderDTO.orderId}";
	  	document.origCardListForm.submit();			
	}
	function addOrigCard(orderId){
		var returnValue = window.showModalDialog('${ctx}/ransomOrderAction!addOrigCard?sellOrderOrigCardListQueryDTO.orderId=${sellOrderDTO.orderId}&sellOrderDTO.firstEntityId=${sellOrderDTO.firstEntityId}', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
		if(returnValue==1){			 
		  	document.newForm.action = "${ctx}/ransomOrderAction!edit";
		  	document.newForm.submit();
		}
	}
	/*
    var cardRead = true;
	function readOver(){
		cardRead = false;
	}
	
	function doTest_DeviceKTL656H_Read() {
		var vData = "";
		vData = DeviceKTL656H._CommOpen(1);
		vData = DeviceKTL656H._ReadCard(1);
		if (vData != -1) {
			document.getElementById("cardNo").value = vData.substring(0,vData.indexOf('='));
			document.getElementById("cvv").value = vData.substring(vData.length-3,vData.length);
			alert("读卡完毕");
		} else {
			alert('设备连接有误,串口可能被占用！');
		}
		vData = DeviceKTL656H._CommClose();
	}*/

	/*function openReadCard(){
		cardRead = true;
		readCardNo();
	}
	
	function readCardNo() {
		if(cardRead){
			doTest_DeviceKTL656H_Read();
		}else{
			return;
		}
		
		var cardNo = document.getElementById("cardNo").value;
		var cvv = document.getElementById("cvv").value;
		 
		if (cardNo == ''||cvv == '') {
			alert("读卡失败!");
			return;
		}
		var orderId = document.getElementById("orderId").value;
		
		
		var date = "sellOrderOrigCardListDTO.cvv"+ cvv + "&sellOrderOrigCardListDTO.cardNo="+cardNo+"&sellOrderOrigCardListDTO.orderId="+orderId;

		ajaxRemote("${ctx}/ransomOrderAction!checkCard" , date ,  function writeTable( date ){
			if(date["checkCardErr"]!='0'){
				alert('调用失败! '+ date["checkCardErr"]);
				return ;
			}
			var rownum=3;
			var chk="<input type='checkbox' name='origCardListStr' value="+ date["origCardListId"] +" />";
			
			var row="<tr class=\"highlight\" onmouseover=\"this.className='highlight'\" onmouseout=\"this.className='odd'\">" +
						"<td>"+ chk +"</td><td>"+  date["cardNo"]+"</td><td>"+ 
						date["productName"] +"</td><td>" +  (date["amount"]/100)+"</td><td>"+ 
						date["firstName"]  +"</td><td>" +  date["validityPeriod"]  +"</td><td>"+ 
						date["cardState"] +"</td></tr>";
			$("#origCardLists_table thead:first").append(row);

			$("#newForm_sellOrderDTO_origCardTotalAmt").val(date["origcardTotalamt"]/100);
			$("#totalPriceStr").val(date["totalPrice"]/100);
			$("#newForm_sellOrderDTO_packageFee").val(date["packageFee"]/100);

			
		} , 'json');
		setTimeout( readCardNo , 6000 );
	}*/
	
	
	function openCheckCardPage(){
       var orderId = document.getElementById("orderId").value;
       var firstEntityId = document.getElementById("sellOrderDTO.firstEntityId").value;
	   var returnValue = window.showModalDialog('${ctx}/randsomCheckAction!ransomCheckCardQuery.action?sellOrderOrigCardListDTO.orderId='+orderId+'&&sellOrderOrigCardListDTO.firstEntityId='+firstEntityId,
			'_blank',
			'center:yes;dialogHeight:400px;dialogWidth:700px;resizable:no');
	   if (returnValue == null || returnValue == undefined)
		 return;
       var cNo = returnValue.split(",")[0];
       var oId = returnValue.split(",")[1];
       var productName = returnValue.split(",")[2];
       var amount = returnValue.split(",")[3];
       var firstName= returnValue.split(",")[4];
       var validityPeriod = returnValue.split(",")[5];
       var cardholderId = returnValue.split(",")[6]
       var productId = returnValue.split(",")[7];
	   var cardSate = returnValue.split(",")[8];
	   var cancelSate = returnValue.split(",")[9];
	   var data = "&sellOrderOrigCardListDTO.cardNo="+cNo+"&sellOrderOrigCardListDTO.orderId="+oId+"&sellOrderOrigCardListDTO.productName="+productName
		+"&sellOrderOrigCardListDTO.amount="+amount+"&sellOrderOrigCardListDTO.firstName="+firstName+"&sellOrderOrigCardListDTO.validityPeriod="+validityPeriod
		+"&sellOrderOrigCardListDTO.cardholderId="+cardholderId+"&sellOrderOrigCardListDTO.productId="+productId
		+"&sellOrderOrigCardListDTO.cardSate="+cardSate+"&sellOrderOrigCardListDTO.cancelSate="+cancelSate;
		ajaxRemote("${ctx}/ransomOrderAction!insertCheckCardNew" , data ,  function writeTable( date ){
			if(date["checkCardErr"]!='0'){
				alert('调用失败! '+ date["checkCardErr"]);
				return ;
			}
			var rownum=3;
			var chk="<input type='checkbox' name='origCardListStr' value="+ date["origCardListId"] +" />";
			
			var row="<tr class=\"highlight\" onmouseover=\"this.className='highlight'\" onmouseout=\"this.className='odd'\">" +
						"<td>"+ chk +"</td><td>"+  date["cardNo"]+"</td><td>"+ 
						date["productName"] +"</td><td>" +  (date["amount"]/100)+"</td><td>"+ 
						date["firstName"]  +"</td><td>" +  date["validityPeriod"]  +"</td><td>"+ 
						date["cardState"] +"</td></tr>";
			$("#origCardLists_table thead:first").append(row);

			$("#newForm_sellOrderDTO_origCardTotalAmt").val(date["origcardTotalamt"]/100);
			$("#totalPriceStr").val(date["totalPrice"]/100);
			$("#newForm_sellOrderDTO_packageFee").val(date["packageFee"]/100);

			
		} , 'json');
	}
		

	function modifyCardState(){
		document.newForm.action = "${ctx}/ransomOrderAction!toOrderCardOperator.action";
	  	document.newForm.submit();
	}

		</script>
	</head>
	<body onload="loadSalesmanList()">
		<OBJECT ID="ACCOR_ATL"  name="DeviceKTL656H" CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" 
		HEIGHT=0 WIDTH=0 codebase="ACCOR_ATL.dll#version=1,0,0,1" viewastext></OBJECT>
		
		
		
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>赎回订单编辑</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="ransomOrderAction!update" method="post">
			<s:hidden name="sellOrderDTO.orderType"/>
			<s:hidden name="cardNo" id="cardNo"/>
			<s:hidden name="cvv" id="cvv"/>
			<s:hidden name="sellOrderDTO.serviceFee" id="sellOrderDTO.serviceFee"></s:hidden>
			<s:hidden name="sellOrderDTO.additionalFee" id="sellOrderDTO.additionalFee"></s:hidden>
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
												id="sellOrderDTO.orderDate" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
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
											<s:textarea name="sellOrderDTO.memo" onpropertychange="if(value.length>200) value=value.substr(0,200)" cols="70" rows="5"></s:textarea>
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
				
				
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront"  style="cursor: pointer;">
										<span class="TableTop">订单付款信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable2">

						<table width="100%"> 
						<tr>
						<td align="right" width="15%" nowrap="nowrap">
							<span style="color: red;">*</span>支付渠道:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<dl:dictList dictType="901" tagType="2" displayName="sellOrderDTO.payChannel" dictValue="${sellOrderDTO.payChannel}"></dl:dictList>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							支付明细:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="sellOrderDTO.payDetails" maxlength="40" size="20" />
							<s:fielderror>
								<s:param>
									sellOrderDTO.payDetails
								</s:param>
							</s:fielderror>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							开户银行:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:select name="sellOrderDTO.fromBankId" id="cusBankNames"
									list="customerDTO.bankList" listKey="bankId"
									listValue="bankName" onchange="onCusBankChange()"></s:select>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							账户名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusAccountName" id="cusAccountName"
								size="20" readonly="true" cssClass="lg_text_gray" />
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							银行账号:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="cusBankAccount" id="cusBankAccount"
								size="20" readonly="true" cssClass="lg_text_gray" />
						</td>
					</tr>
						
						
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人信息:
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpName" size="20"  readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										customerDTO.corpName
									</s:param>
								</s:fielderror>
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人联系电话：
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										customerDTO.corpPhone
									</s:param>
								</s:fielderror>
							</td>
						</tr>	
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件类型:
							</td>
							<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList displayName="customerDTO.corpCredType"
													dictValue="${customerDTO.corpCredType}" dictType="140"
													tagType="1" defaultOption="false" />
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件号:
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredId" size="20"  readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr>
							<td align="right" width="15%" nowrap="nowrap">
								法(个)人证件有效期:(起)
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredStaValidity" size="20"  readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
							<td align="right" width="15%" nowrap="nowrap">
								(至):
							</td>
							<td width="35%" align="left" nowrap="nowrap">
								<s:textfield name="customerDTO.corpCredEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
								<s:fielderror>
									<s:param>
										
									</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人名称:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
						<s:select list="customerDTO.contractList" name="sellOrderDTO.contactId" onchange="onOperChange()" id="contactId" listKey="contactId" listValue="contactName"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人联系电话:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operPhone"  id="operPhone" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件类型:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<edl:entityDictList props="id='operCreType' disabled='true'" displayName="customerDTO.operCredType"
												dictValue="${customerDTO.operCredType}" dictType="140"
												tagType="2" defaultOption="false" />
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件号码:
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.operCredId" size="20" id="operCredId" readonly="true" cssClass="lg_text_gray"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap="nowrap">
							经(代)办人证件有效期:(起)
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredStaValidity" id="operStarValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
						</td>
						<td align="right" width="15%" nowrap="nowrap">
							(至):
						</td>
						<td width="35%" align="left" nowrap="nowrap">
							<s:textfield name="customerDTO.corpCredEndValidity" id="operEndValidity" size="20" readonly="true" cssClass="lg_text_gray"/>
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
			<s:form action="orderInputAction!list" name="backForm"
				id="backForm">
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="orderSubmit();">
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
						<ec:table items="origCardLists" var="map" width="100%" form="origCardListForm"
									action="${ctx}/ransomOrderAction!edit"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false" tableId="origCardLists">
							<ec:row ondblclick="">
								<ec:column property="null" alias="origCardListStr" title="选择"
										width="5%" sortable="false" headerCell="selectAll"
										viewsAllowed="html">
									<input type="checkbox" name="origCardListStr"
										value="${map.origCardListId}" />
								</ec:column>
								<ec:column property="cardNo" title="卡号" width="15%" />
								<ec:column property="productName" title="产品名称" width="15%" />
								<ec:column property="amount" title="余额" width="15%" />
								<ec:column property="firstName" title="持卡人姓名" width="15%" />
								<ec:column property="validityPeriod" title="有效期" width="10%"  />
								<ec:column property="cardState" title="操作状态" width="20%" >
									<s:if test="#attr['map'].cardState ==2">
										验收通过-允许入库
									</s:if>
									<s:if test="#attr['map'].cardState == 3|| #attr['map'].cardState == 1">
										验收通过-只允许注销
									</s:if>
								</ec:column>
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
									<div id="deleteBtn" class="btn"
										style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="deleteOrigCard();">
										删除
									</div>
									<div id="addBtn" class="btn"
										style="width: 120px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="modifyCardState(${sellOrderDTO.orderId});">
										修改操作方式
									</div>
								<!--			<div id="addBtn" class="btn"
										style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="readOver();">
										结束验卡
									</div>-->
									<div id="addBtn" class="btn"
										style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="openCheckCardPage();">
										输入卡号
									</div>
<!--									<div id="addBtn" class="btn"-->
<!--										style="width: 80px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"-->
<!--										onclick="addOrigCard(${sellOrderDTO.orderId});">-->
<!--										批量添加-->
<!--									</div>-->
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