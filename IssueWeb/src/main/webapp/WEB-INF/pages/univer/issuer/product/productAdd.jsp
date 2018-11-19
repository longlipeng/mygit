<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>产品添加</title>
		<%@ include file="/commons/meta.jsp"%>
	
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
			function displayInfo(){
				
			}
		
			function select() {
				var s = document.getElementById("ruleId")
				var op = document.createElement("option"); // 新建OPTION (op) 
				s.insertBefore(op, s.options[0])
				op.setAttribute("selected", "selected");
				op.setAttribute("value", ""); // 设置OPTION的 VALUE
				op.appendChild(document.createTextNode("---请选择---")); // 设置OPTION的 TEXT
			}
		
			function changeOnymousStat() {
				var paymentTermSelectedId = document.getElementById('onymousStat').value;
				// 选中无需实时支付时，可以设置延期天数记名
				if (paymentTermSelectedId == 2) {
					document.getElementById('validityPeriod').style.display = "block";
					document.getElementById('limitInfo').style.display = "block";
		
				} else if (paymentTermSelectedId != 2) {
					document.getElementById('validityPeriod').style.display = "none";
					document.getElementById('limitInfo').style.display = "none";
				}
		
			}
		
			function checkvalidityPeriod() {
				var validityPeriod = document
						.getElementById('productDTO.validityPeriod').value;
				if (validityPeriod.match(/\D+/g)) {
					alert("有效期月数必须为数字!");
					document.getElementById('productDTO.validityPeriod').value = "";
					return;
				}
				if (validityPeriod < 36) {
					alert("有效期月数不能少于36个月");
					document.getElementById('productDTO.validityPeriod').value = "";
					return;
				}
			}
			function sub() {
				
				if(checkProductName()==false){
					return;
				}
				var validity = document.getElementById('validityPeriod');
				var limitInfo = document.getElementById('limitInfo');
				var noPinLimit = document.getElementById('noPinLimit').value;
				var cardFee = document.getElementById('cardFee').value;
				var annualFee = document.getElementById('annualFee').value;
				var replaceFee = document.getElementById('replaceFee').value;
				var maxBalance = document.getElementById('maxBalance').value;
				var maxBalReg=/^(([1-9]{1}[0-9]{0,7})|([0]))([.]\d{1,2})?$/;
				if (cardFee.match(/\D+/g)) {
					alert("卡费必须为数字!");
					document.getElementById('cardFee').value = "";
					return;
				}
				if (annualFee.match(/\D+/g)) {
					alert("年服务费必须为数字!");
					document.getElementById('annualFee').value = "";
					return;
				}
				if (replaceFee.match(/\D+/g)) {
					alert("换卡费必须为数字!");
					document.getElementById('replaceFee').value = "";
					return;
				}
				if (maxBalance.match(/\D+/g)) {
					alert("最大余额必须为数字!");
					document.getElementById('maxBalance').value = "";
					return;
				}
				if (maxBalance > 1000000 || maxBalance < 0) {
					alert("最大余额非法！");
					return;
				} 
				if (noPinLimit.match(/\D+/g)) {
					alert("无PIN限额必须为数字!");
		
					document.getElementById('noPinLimit').value = "";
					return;
				}
				if (validity.style.display == "block") {
					var validityPeriod = document
							.getElementById('productDTO.validityPeriod').value;
					if (validityPeriod.match(/\D+/g)) {
						alert("有效期月数必须为数字!");
						document.getElementById('productDTO.validityPeriod').value = "";
						return;
					}
					if (validityPeriod < 36) {
						alert("有效期月数不能少于36个月");
						document.getElementById('productDTO.validityPeriod').value = "";
						return;
					}
				}
				if (limitInfo.style.display == "block") {
					var rsvData = document
					.getElementById('productDTO.rsvData1').value;
					if (validityPeriod.match(/\D+/g)) {
						alert("激活当天限额必须为数字!");
						document.getElementById('productDTO.rsvData1').value = "";
						return;
					}
					if (validityPeriod > 100) {
						alert("激活当天限额不能大于100%");
						document.getElementById('productDTO.rsvData1').value = "";
						return;
					}
				}
				var signType = document.getElementById('onymousStat').value;
				//不记名卡
				if(signType==2) {
					if(!maxBalReg.test(maxBalance)||maxBalance>1000){
						alert("最大余额：请输入0-1000(两位以上整数首位不能为零),小数位不超过2位");
						document.getElementById('maxBalance').value="";
						return;
					}
				}else{
					//记名卡
					if(!maxBalReg.test(maxBalance)||maxBalance>5000){
						alert("最大余额：请输入0-5000(两位以上整数首位不能为零),小数位不超过2位");
						document.getElementById('maxBalance').value="";
						return;
					}
				}
				maskDocAll();
				newForm.submit();
			}
			
			
			function checkProductName(){
				$("#productName_msg").empty("");
				var productName = document.getElementById("productName").value;
				if(productName.length>80){
					$("#productName_msg").html('<ul class="errorMessage"><li><span>请填写80位内产品名称！</span></li></ul>');
					return false;
				}
				return true;
			}
			
		</script>
	</head>
	<body onload="load();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增产品</span>
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
									<span class="TableTop">产品信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="insertProduct" method="post">
								<s:token></s:token>
								<s:hidden id="productDTO.changeValidPeriod" name="productDTO.changeValidPeriod" value='0'></s:hidden>
								<s:hidden id="productDTO.initActStat" name="productDTO.initActStat" value="1"></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>产品号：
													</td>
													<td>
														<s:textfield name="productDTO.productId" disabled="true"></s:textfield>
														<s:fielderror>
															<s:param>
																productDTO.productId
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>产品名称：
													</td>
													<td>
														<s:textfield id="productName" name="productDTO.productName" onchange="checkProductName()"></s:textfield>
														<div id="productName_msg">
															<s:fielderror>
																<s:param>
																	productDTO.productName
																</s:param>
															</s:fielderror>
														</div>
													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														产品英文名称：
													</td>
													<td>
														<s:textfield name="productDTO.productEnglishName" maxlength="128"></s:textfield>
														<s:fielderror>
															<s:param>
																productDTO.productEnglishName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>发行机构：
													</td>
													<td>
													
														  <s:select id="entityId" 
												                   list="entityList"
												                   name="productDTO.entityId" 
												            listKey="entityId"
												            listValue="entityName"
											                ></s:select>
														<s:fielderror>
															<s:param>
																productDTO.entityId
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														介质类型：
													</td>
													<td>
														<dl:dictList displayName="productDTO.cardType"
															dictType="102" dictValue="2"
															tagType="2" />

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>产品类别：
													</td>
													<td>
														<edl:entityDictList displayName="productDTO.productType"
															dictType="815" dictValue="${productDTO.productType}"
															tagType="2" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
									<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>署名类型：
													</td>
													<td>
														<dl:dictList displayName="productDTO.onymousStat" dictValue="2" defaultOption="false" dictType="816" tagType="2"   props="id=\"onymousStat\" onchange=\"changeOnymousStat()\""></dl:dictList>
														<s:fielderror>
															<s:param>
																productDTO.onymousStat
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										
										<td>
										<div id="validityPeriod" style="display:${isDisplay}">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right">
														<span class="no-empty">*</span>有效期月数：
													</td>
													<td>
														<s:textfield id="productDTO.validityPeriod" name="productDTO.validityPeriod" onchange="checkvalidityPeriod()" />
														<s:fielderror>
															<s:param>
																productDTO.validityPeriod
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
											</div>
										</td>
									</tr>
									

										
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>换卡费用：
													</td>
													<td>
														<s:textfield name="productDTO.replaceFee" id="replaceFee"/> 元
														<s:fielderror>
															<s:param>
																productDTO.replaceFee
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>年服务费：
													</td>
													<td>
														<s:textfield name="productDTO.annualFee" id="annualFee"></s:textfield> 元
														<s:fielderror>
															<s:param>
																productDTO.annualFee
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
			
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>卡费：
													</td>
													<td>
														<s:textfield name="productDTO.cardFee" id="cardFee"></s:textfield> 元
														<s:fielderror>
															<s:param>
																productDTO.cardFee
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>	
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>无PIN限额：
													</td>
													<td>
														<s:textfield name="productDTO.noPinLimit" id="noPinLimit"></s:textfield> 元
														<s:fielderror>
															<s:param>
																productDTO.noPinLimit
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>最大余额：
													</td>
													<td>
														<s:textfield name="productDTO.maxBalance" id="maxBalance" /> 元
														<s:fielderror>
															<s:param>
																productDTO.maxBalance
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														附卡状态：
													</td>
													<td>
														<s:select list="#{0:'没有附卡',1:'有附卡'}"
															name="productDTO.supplStat" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														PIN状态：
													</td>
													<td>
														<s:select list="#{2:'使用',0:'不使用'}"
															name="productDTO.pinStat" />
													</td>
												</tr>
											</table>
										</td>										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														PIN发送方式：
													</td>
													<td>
														<s:select list="#{0:'卡背面印刷',1:'信封打印发送',2:'短信发送'}"
															name="productDTO.pinDelivMeans" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														短信服务状态：
													</td>
													<td>
													   <s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.smsSvcStat" />
														<s:fielderror>
															<s:param>
																productDTO.smsSvcStat
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														Email提醒：
													</td>
													<td>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.emailSvcStat" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														月报服务：
													</td>
													<td>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.monstmtSvcStat" />
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														网上支付：
													</td>
													<td>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webPayStat" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														网上交易短信状态：
													</td>
													<td>
														 <s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webSmsSvcStat" />
														<s:fielderror>
															<s:param>
																productDTO.webSmsSvcStat
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														网上交易Email提醒：
													</td>
													<td>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webEmailSvcStat" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														预授权状态：
													</td>
													<td>
														<s:select list="#{1:'授权',0:'不授权'}"
															name="productDTO.preAuthStat" />
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														IVR服务：
													</td>
													<td>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.ivrSvcStat" />
													</td>
												</tr>
											</table>
										</td>
									


									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														可否基于订单销售：
													</td>
													<td>
														<s:select list="#{1:'可以',0:'不可以'}"
															name="productDTO.availableOrder" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														可否基于库存销售：
													</td>
													<td>
														<s:select list="#{1:'可以',0:'不可以'}"
															name="productDTO.availableStock" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														消费次数：
													</td>
													<td>
														<s:select list="#{1:'不限',0:'限定'}"
														  id="consumerTimesId"   name="consumerTimesId" onchange="selectConsumerTime();"  />
													</td>
												</tr>
											</table>
										</td>
					
										<td>
										  
											<div id="consumerId" style="display:none;" >
											<table style="text-align: left; width: 100%" >
												<tr>
													<td style="width: 120px; text-align: right;">
														请输入消费次数：
													</td>
													<td>
													  
														<s:textfield id="consumerTimes" name="productDTO.consumerTimes" maxlength="5"></s:textfield>
														<s:fielderror>
															<s:param>
																productDTO.consumerTimes
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
											</div>
										</td>
									
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														充值次数：
													</td>
													<td>
														<s:select list="#{1:'不限',0:'限定'}"
														  id="rechargeTimesId"  name="rechargeTimesId" onchange="selectRechargeTime();"  />
													</td>
												</tr>
											</table>
										</td>
					
										<td>
										  
											<div id="rechageId" style="display:none;" >
											<table style="text-align: left; width: 100%" >
												<tr>
													<td style="width: 120px; text-align: right;">
														请输入充值次数：
													</td>
													<td>
												
														<s:textfield id="rechargeTimes" name="productDTO.rechargeTimes" maxlength="5"></s:textfield>
														<s:fielderror>
															<s:param>
																productDTO.rechargeTimes
															</s:param>
														</s:fielderror>
													</td>
												</tr>
												
											</table>
											</div>
										</td>
									
									</tr>
									<tr>
									  <td>
									  	<div id="limitInfo" style="display:${isDisplay}">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right">
														<span class="no-empty">*</span>激活当天交易限额（百分比）：
													</td>
													<td>
														<s:textfield id="productDTO.rsvData1" name="productDTO.rsvData1" onchange="checkvalidityPeriod()" />
														<s:fielderror>
															<s:param>
																productDTO.rsvData1
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
											</div>
									  </td>
									</tr>
								
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="window.location='productInquery.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		<script type="text/javascript">
		  function selectConsumerTime(){
              if(document.getElementById('consumerTimesId').selected=true){
                  var consumerTime=document.getElementById('consumerTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("consumerId").style.display="block";
                    }else{
                    	document.getElementById("consumerId").style.display="none";
                    }
                  }
			  }
		  function selectRechargeTime(){
              if(document.getElementById('rechargeTimesId').selected=true){
                  var consumerTime=document.getElementById('rechargeTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("rechageId").style.display="block";
                    }else{
                    	document.getElementById("rechageId").style.display="none";
                    }
                  }
			  } 
		/*   function optionRechargeTime(){
			  var consumerTime=document.getElementById('rechargeTimes').value;
			  if(consumerTime==""){
				  consumerTimes=document.getElementById('rechargeTimesId').value="1";
			  }
			  selectRechargeTime();
		  }
		  function optionConsumerTime(){
			  var consumerTime=document.getElementById('rechargeTimes').value;
			  if(consumerTime==""){
				  consumerTimes=document.getElementById('rechargeTimesId').value="1";
			  }
			  selectConsumerTime();
		  } */
		  function load(){
		  
		   // select();
		  	selectConsumerTime();
		  	selectRechargeTime();
		  	//changeOnymousStat;
		  	//optionRechargeTime();
		  	//optionConsumerTime()
              document.getElementById('validityPeriod').style.display = "block";
              document.getElementById('limitInfo').style.display = "block";
		  }
		</script>
		
	</body>
</html>
