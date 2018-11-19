<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>交易查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			
			function change(){
				var flag=document.getElementById("selectFlag").value;
				if(flag==0){
					document.getElementById("hisStartTime").style.display ="none";
					document.getElementById("hisStopTime").style.display ="none";;
				}else{
					document.getElementById("hisStartTime").style.display = "block";;
					document.getElementById("hisStopTime").style.display = "block";
				
				}
			}
			function inquiry(){				
    			cardselectForm.submit();
			}
		 function loadMerchantList(){
            var merchantDTOList = ${merchantDTOList};
            var store = new Ext.data.JsonStore({
            data: merchantDTOList,
            autoLoad: true,
            fields: [{name: 'id', mapping: 'merchantCode'}, {name: 'name', mapping: 'merchantName'}]
            });
            var combo = new Ext.form.ComboBox({
                store: store,
                displayField:'name',
                hiddenName: 'merchantTxnQueryDTO.merchantCode',
                valueField: 'id',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText: '请选择商户',
                forceSelection: true,
                applyTo: 'merchantCode',
                listeners:{                                                                
                select:{                                                       
                    fn:function(combo,record,index) {                       
            			 ajaxRemote ("${ctx}/merchantManagement/getShopListByMerchantId.action",{'merchantTxnQueryDTO.merchantCode':record.get('id')},successFn,"json");                                       
                   }                                                           
                }                                                              
           			 }                                                                  
          });

           ajaxRemote ("${ctx}/merchantManagement/getShopListByMerchantId.action",{'merchantTxnQueryDTO.merchantCode':combo.getValue()},successFn,"json");    
            
           
        } 

		function successFn(shopList){
			<c:if test="${merchantTxnQueryDTO.shopId!=''}">
				var shopId= ${merchantTxnQueryDTO.shopId};
			</c:if>
			var optionInnerHTML="<select name='merchantTxnQueryDTO.shopId' id='merchantTxnQueryDTO.shopId'>";
			optionInnerHTML +="<option value=''>--请选择--</option>"
			
			var deliveryPoint=document.getElementById("shopId").value;
			for (var i=0;i<shopList.length;i++){
					if(shopId ==shopList[i]['shopId']){
		    			optionInnerHTML=optionInnerHTML+"<option value="+shopList[i]['shopId']+" selected>"+ shopList[i]['shopName']+"</option>";
					}else{
						optionInnerHTML=optionInnerHTML+"<option value="+shopList[i]['shopId']+">"+ shopList[i]['shopName']+"</option>";
					}
			}
			optionInnerHTML=optionInnerHTML+"</select>";
			document.getElementById("shopId").innerHTML=optionInnerHTML;
		}
		function check(key){
				if((key.keyCode>=48 && key.keyCode<=57) || key.keyCode==8)
					return true;
				else
					return false;
		}
		 function choiceSeller() {
		    	var sellerDTO=window.showModalDialog('${ctx}/ireport/merchantChoose.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		         if (sellerDTO != null) {		             
		        	 var string=sellerDTO.split(",");
		        	document.getElementById('merchantCode').value = string[0];
		            document.getElementById('merchantName').value = string[1];
		            ajaxRemote ("${ctx}/merchantManagement/getShopListByMerchantId.action",{'merchantTxnQueryDTO.merchantCode':string[0]},successFn,"json");    
		        }
		 }

		</script>
	</head>
<%-- 	<body onload="loadMerchantList()">--%>
	<body onload="change()">
<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>商户交易信息查询</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">查询信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="cardselectForm" name="cardselectForm"
								action="/merchantManagement/queryMerchantTxn.action" method="post">
								<table width="100%" style="table-layout: fixed;" border="0">
									<tr>
										<td>
											<table style="text-align: left; width: 100%" border="0">
												<tr>
										<%--		<td style="width: 166px; text-align: right;">
														商户：
													</td>
													<td>
														<s:textfield name="merchantTxnQueryDTO.merchantCode" id="merchantCode">
														</s:textfield>
														<s:fielderror>
															<s:param>
																merchantTxnQueryDTO.merchantCode
															</s:param>
														</s:fielderror>

													</td> --%>	
													
													<s:hidden id="merchantCode" name="merchantTxnQueryDTO.merchantCode"></s:hidden>
													<td>
														<table style="text-align: right; width: 100%">
															<tr>
																<td style="width: 166px; text-align: right;">
																	<span style="color: red">*</span>商户：
																</td>
																<td align="left">
																	<s:textfield cssClass="watch" id="merchantName" name="merchantTxnQueryDTO.merchantName"
																		readonly="true" onclick="choiceSeller()" />

																</td>
															</tr>
														</table>
													</td><tr>
											</table>
										</td>
								    
										<td>
											<table style="text-align: left; width: 100%" border="0">
												<tr>
													<td style="width: 60px; text-align: right;">
														&nbsp;门店：
													</td>
													<td>
														<div id="shopId">
														<s:select id="shopId" 
												                   list="shopList"
												                   name="merchantTxnQueryDTO.shopId" 
												            listKey="shopId"
												            listValue="shopName"
												            headerValue="--请选择--" headerKey=""
											                ></s:select>
														
															
														</div>
													</td>
													<tr>
											</table>
										</td>
									</tr>
								 <tr>	
										<td id="hisStartTime">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>历史开始时间：
													</td>
													<td>
														<input type="text" name="merchantTxnQueryDTO.startDate"
															onclick="dateClick(this)" class="Wdate" value="${merchantTxnQueryDTO.startDate}"/>
															<s:fielderror>
															<s:param>
																merchantTxnQueryDTO.startDate
															</s:param>
														</s:fielderror>
													</td>
													
													</tr>
											</table>
										</td>

										<td id="hisStopTime">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>历史结束时间：
													</td>
													<td>
														<input type="text" name="merchantTxnQueryDTO.endDate"
															onclick="dateClick(this)" class="Wdate" value="${merchantTxnQueryDTO.endDate}"/>
															<s:fielderror>
															<s:param>
																merchantTxnQueryDTO.endDate
															</s:param>
														</s:fielderror>
													</td>
													
													</tr>
											</table>
										</td>
									 </tr>
									  <tr>
									  <td width="20%">	
									 	 <table style="text-align: left; width: 100%" border="0">
												<tr>
													<td style="width: 166px; text-align: right;">
													终端：
													</td>
													<td>
														<s:textfield name="merchantTxnQueryDTO.posId" size="22" onkeypress="return check(event);" maxlength="19">
														</s:textfield>
														<s:fielderror>
															<s:param>
																merchantTxnQueryDTO.posId
															</s:param>
														</s:fielderror>

													</td>
													</tr>
											</table>
										</td>
									  	<td>
											<table style="text-align: left; width: 100%" border="0">
												<tr>
													<td style="width: 60px; text-align: right;">
													卡号：
													</td>
													<td>
														<s:textfield name="merchantTxnQueryDTO.cardNo" size="25" onkeypress="return check(event);" maxlength="19">
														</s:textfield>
														<s:fielderror>
															<s:param>
																merchantTxnQueryDTO.cardNo
															</s:param>
														</s:fielderror>

													</td>
													</tr>
											</table>
										</td>
									  </tr>
									  <tr>
									  <td>
									  	 <table style="text-align: left; width: 100%" border="0">
												<tr>
													<td style="width: 166px; text-align: right;">
													交易类型：
													</td>
													<td>
														<s:select name="merchantTxnQueryDTO.transType" list="txnTypeMap" listKey="key" listValue="value"></s:select>
													</td>
													</tr>
											</table>
									  </td>
									  <td>
									 	 <table style="text-align: left; width: 100%" border="0">
												<tr>
													<td style="width: 60px; text-align: right;">
														查询记录：
													</td>
													<td>
														<s:select list="#{0:'当天',1:'历史'}"
															name="merchantTxnQueryDTO.selectFlag"   id="selectFlag" onchange="change()"/>
													</td>
													</tr>
											</table>
									  </td>
									  </tr>
									 <tr>	
									 <td>&nbsp;</td>
										<td>
											<table style="text-align:center; width: 100%" border="0">
												<tr>
													<td>
														<button class='bt' style="float: center; margin: 0px, 0px"
															onclick="inquiry();">
															交易查询
														</button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	&nbsp;
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
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="listTable"
							style="background-color: #FBFEFF;border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
							<ec:table items="pageDataDTO.data" var="map" width="100%"
							form="cardselectForm"
							action="${ctx}/merchantManagement/queryMerchantTxn.action"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row>
									<ec:column property="tranCode" title="交易系统号" width="10%"
										escapeAutoFormat="true" />
									<ec:column property="cardNo" title="卡号" width="20%"
										escapeAutoFormat="true" />
									<ec:column property="merchantName" title="商户名称" width="10%" />
									<ec:column property="shopName" title="门店名称" width="10%" />
									<ec:column property="postcode" title="终端号" width="10%"
										escapeAutoFormat="true" />
									<ec:column property="tranType" title="交易类型" width="10%" />	
									<ec:column property="tranFee" title="交易金额" width="10%"
										escapeAutoFormat="true" cell="amt" />
									<ec:column property="respCode" title="交易状态" width="10%" >
									</ec:column>
									<ec:column property="tranTime" title="交易时间" width="20%" />
								</ec:row>
							</ec:table>
							</s:form>
						</div>

						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>

	</body>
</html>

