<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片操作</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<style>
		#reasonDiv {  
		            position: absolute;  
		            height: 200px;  
		            width: 400px;  
		            margin: -100px 0px 0px -200px;  
		            top: 50%;  
		            left: 50%;  
		            text-align: left;  
		            padding: 0px;  
		            border: 1px dotted #000000;  
		            background-color: #FFFFFF;
		            overflow: auto;  
		        }  
		</style>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
		
			function selectSingleCardOperation() {
				//var t = document.getElementById('cardManagementDTO.transferOutCard').value;
				var t =$('#transferOutCard').val();
				newForm.action='${ctx}/cardManageExt/selectSingleCardOperation?cardManagementQueryDTO.cardNo=' + t;
				newForm.submit();
			}
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
			function doTest_DeviceKTL512() 
			{
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("passwordVerify").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
				
			}
			function doTest_DeviceKTL511(){
				cookie();
				var vData=DeviceKTL512.GetPinEx(p2);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('设备连接串口有误！');
			}
			}
			function a(){
				
				if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
					
					document.getElementById("a").style.display='none';
					
				}else{
					
					document.getElementById("a").style.display='';
				}
			}
			function b(){
				//if(document.getElementById("cardValidityPeriod").value!=null&&document.getElementById("cardValidityPeriod").value!=''){
					if(document.getElementById("passwordVerify").value==document.getElementById("password").value){
						document.getElementById("a").style.display='none';
						newForm.submit();
					}else{
						document.getElementById("a").style.display="";
					}
				//}
				
			}
			function doTest_DeviceKTL656H_Read() 
			{
				cookie();
				var vData="";
				if(p1==0){
					
					vData=DeviceKTL656H._CommOpen(1);
					vData=DeviceKTL656H._ReadCard(1);
				}else{
					
					vData=DeviceKTL656H._CommOpen(p1);
					vData=DeviceKTL656H._ReadCard(1);
				}
				if(vData!=-1){
					document.getElementById("cardNo").value=vData.substring(0,vData.indexOf('='))
					alert("读卡完毕");
				}else{
					alert('设备连接有误,串口可能被占用！');
				}
				vData=DeviceKTL656H._CommClose();
				
			}
			function doTest_DeviceKTL656() 
			{
				var vData=DeviceKTL656.GetCardNoEx(4);
				if(vData!=1){
					document.getElementById("cardNo").value=vData;
				}else{
					alert('设备连接有误,串口可能被占用！');
				}
			}
			function doTest_DevicePCSC() 
			{
				var vData="";
				vData=DevicePCSC.GetCardNo();
				if(vData!=-1){
					document.getElementById("cardNo").value=vData;
					alert("读卡完毕");
				}else{
					alert('设备连接有误,串口可能被占用！');
				}
				
			}
			function readCardNo(){
				var n=document.getElementById("typeName").value;
				if(n==1){
					
					doTest_DeviceKTL656H_Read(); 
				}else if(n==2){
					doTest_DevicePCSC();
				}else{
					doTest_DeviceKTL656();
				}
			}
			function changeCard(cardNO){
				//alert(cardNO.value);
				//document.getElementById("cardNo").value=cardNO.value;
				var kahao = document.getElementById("kahao").value;
				newForm.action='${ctx}/cardManageExt/cardQuerys.action?id='+kahao;
				//newForm.action='${ctx}/cardManageExt/cardQuerys.action';
				newForm.submit();
// 				alert(object.value);//选中的实际值
			}
			
			function query(){
			    if('${invalidFlag}'!=null||'${invalidFlag}'!=""){
			     document.getElementById('invalidFlag').value='${invalidFlag}';
			    }
				newForm.action='${ctx}/cardManageExt/cardQuery.action';
				newForm.submit();
			}
			function displaylory(){
			var check1=document.getElementById("check");
			var loryinfor=document.getElementById("loryinfor");
			if(check1.checked){
			loryinfor.style.display="none";
			}
			else{
			loryinfor.style.display="block";
			}
			}
			function cardIsRltHolder(){
			
		
		       newForm.action='${ctx}/cardManageExt/cardIsRltHolder.action';
      	       newForm.submit();
			}
			
			function getCardHolder(){
			newForm.action="${ctx}/cardholder/view.action?cardManagementDTO.transferOutCard=${cardManagementDTO.transferOutCard}&cardholderDTO.cardholderId=${cardManagementDTO.cardholderId}&callcenter=1&cardManagementFlag='1'";
      	       newForm.submit();
			
			}
			//卡作废新增部分
			function invalid() {
			    var area=document.getElementById("invalidReasonId").value.length;
			    if(area>2048){
			        errorDisplay("您输入的字数过多！");
			        return;
			    } 
			     if(area==0){
			        errorDisplay("请输入作废原因！");
			        return;
			    }
			    document.getElementById("invalidReason").value=document.getElementById("invalidReasonId").value;
			    newForm.action='${ctx}/cardManageExt/cardManageAction!invaild';
			    newForm.submit();
			}
			function closeWin(){
			 document.getElementById("reasonDiv").style.display='none';
		    }
		    function confirmInvalid(){
		     document.getElementById("reasonDiv").style.display='';
		     return false;
		    }
		    
		    function autoFillValueByReadCard(){
			  
					var name = "SCM Microsystems Inc. SDI011G Contactless Reader 0";
					var connectR =reader.PBOC_ICConnect(name);
				
					if(connectR=="ERROR"){
						alert("链接读卡器失败！");
						return;
					}
					var app =reader.PBOC_ICAppSelect(0,"0001");
					if(app!=0){
						reader.PBOC_ICDisConnect();
						alert("选择应用失败！");
						return;
					}
					var cardNo  = reader.PBOC_ICReadInfo(1);
					
						if(cardNo=="ERROR"){
							reader.PBOC_ICDisConnect();
							alert("读取卡号失败！");
							return;
						}else{
							document.getElementById("transferOutCard").value=cardNo;
							alert("读取卡号成功！");
						}
						var disConnect =reader.PBOC_ICDisConnect();
						if(disConnect == "ERROR"){
							alert("读卡器链接关闭失败！");
						};
				}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<!-- <OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
			CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>

		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656"
			CLASSID="clsid:E8B803A1-41C1-4FF3-9832-5E3A17B2B5AF" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>

		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656H"
			CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL" name="DevicePCSC"
			CLASSID="clsid:416B15E8-CE9A-4BF8-8D0A-D79625298E25" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT> -->
			
			<object id="reader" classid="clsid:4806AD0E-57B8-471F-B210-F3FABB9B3ABB"> </object> 
		<div class="TitleHref">
			<span>卡片操作  </span>
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
									<span class="TableTop">卡片操作</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="insertPassword.action?op=8" method="post">
								<s:hidden name="cardManagementDTO.balance"></s:hidden>
								<s:hidden name="cardManagementDTO.onymusState"></s:hidden>
								<s:hidden name="cardManagementDTO.invalidFlag" id="invalidFlag"></s:hidden>
                                <s:hidden name="invalidReason" id="invalidReason"></s:hidden>
                                <%-- <s:hidden name="cardManagementDTO.cardNo" id="cardNo"></s:hidden> --%>
                               <!--  <input type="hidden" name="cardManagementDTO.cardNo" id="cardNo"> -->
								<table width="100%"
									style="table-layout: fixed; border-bottom: 1px solid silver;"
									border="0">

									<tr style="text-align: left; width: 100%;">
										<td>
											<table style="text-align: left; width: 100%">
<!-- 												<tr> -->
<!-- 													<td style="width: 160px; text-align: left;"> -->
<!-- 														卡号： -->
<!-- 													</td> -->
<!-- 													<td> -->
<%-- 														<s:textfield name="applyAndBindCardDTO.idNo" --%>
<%-- 															id="idNo" size="23"></s:textfield> --%>
<%-- 														<s:fielderror> --%>
<%-- 															<s:param> --%>
<!-- 																applyAndBindCardDTO.idNo -->
<%-- 															</s:param> --%>
<%-- 														</s:fielderror> --%>
<!-- 														<input id="cardManagementQueryDTO.cardNo"  name="cardManagementQueryDTO.cardNo" type="hidden"></input> -->
<!-- 													</td> -->

<!-- 												</tr> -->
											</table>
										</td>

										<td>
<!-- 											<table style="text-align: left; width: 100%"> -->
<!-- 												<tr> -->
<!-- 													<td style="width: 200px; text-align: left;"> -->
<!-- 														<span class="no-empty"></span> -->
<!-- 													</td> -->
<!-- 													<td> -->
<!-- 														<select name="typeName" id="typeName"> -->
<!-- 															<option value="1"> -->
<!-- 																磁条卡机 -->
<!-- 															</option> -->
<!-- 															<option value="2"> -->
<!-- 																PC/SC读写卡器 -->
<!-- 															</option> -->
<!-- 															<option value="3"> -->
<!-- 																IC卡机 -->
<!-- 															</option> -->
<!-- 														</select> -->
<!-- 													</td> -->
<!-- 													<td> -->
<!-- 														<input class='bt' type="button" -->
<!-- 															style="float: left; margin: 5px 10px" -->
<!-- 															onclick="autoFillValueByReadCard()" value="读卡" /> -->
<!-- 															readCardNo() -->
<!-- 													</td> -->
<!-- 												</tr> -->
<!-- 											</table> -->
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
									
									</tr>
									<tr>
										<td>
											<div  id="holderMobile" style="display:none">
												<table style="text-align: left; width: 100%">
													<tr>
													<td>
															持卡人姓名：
														</td>
														<td>
															<s:textfield name="applyAndBindCardDTO.firstName" id="firstName"
																size="23"></s:textfield>
															<s:fielderror>
																<s:param>
																		applyAndBindCardDTO.firstName
																</s:param>
															</s:fielderror>
														</td>
													</tr>
												</table>
											</div>
										</td>
										<td>
											<div  id="certTypeNo">
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 200px; text-align: left;">
															证件号码：
														</td>
														<td>
															<s:textfield name="applyAndBindCardDTO.idNo" id="idNo" size="18"></s:textfield>
															<s:fielderror>
															<s:param>
																applyAndBindCardDTO.idNo
															</s:param>
															</s:fielderror>
														</td>
													</tr>
												</table>
											</div>
										</td>

										<td>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													</td>
													<td>

														<input  class='bt' type="button"
															style="float: left; margin: 0px 0px" onclick="query();"
															id="cardOperQuery" value="查询卡号"/>
															
<!--														 <input  class='bt' type="button"-->
<!--															style="float: left; margin: 0px 0px" onclick="confirmInvalid();"-->
<!--															id="cardOperQuery" value="作废"/>-->

													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>
									<td>
											&nbsp;
										</td>
										<td>
											<table style="text-align: centre; width: 100%">
												<tr>
													<td>
													<s:if test="#request.CardNos!=null">	<td style="width: 200px; text-align: left;">
															请选择卡号：
														</td>
														
														<td> <select name="kahao" id="kahao" class="select"  onChange="changeCard()">
  		<option>请选择</option>
      <s:iterator value="#request.CardNos" id="cardNo">
      	<option value="${cardNo}">${cardNo }</option>
      </s:iterator>
     
  </td> </s:if>
													</td>
												</tr>
											</table>
										</td>
										<td>
											&nbsp;
										</td>
									
									</tr>
									
								</table>
								</br>
								</br>
								<table width="100%"
									style="table-layout: fixed; border-bottom: 1px solid silver;"
									border="0">
									<tr style="text-align: right; width: 100%">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="text-align: left;">

														持卡人姓名：
													</td>
													<td>
														<s:if
															test="cardManagementDTO.cardholderName==null||cardManagementDTO.cardholderName==''">
															<s:label name="cardManagementDTO.cardholderName" />
														</s:if>
														<s:else>
															<s:hidden name="cardManagementDTO.cardholderId"></s:hidden>
															<a href="javascript:getCardHolder()"> ${cardManagementDTO.cardholderName } </a>
														</s:else>
													</td>
												</tr>
											</table>
										</td>

										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="text-align: right;">
														卡片有效期：

													</td>
													<td>
														<s:label name="cardManagementDTO.cardValidityPeriod" />
														<s:hidden name="cardManagementDTO.cardValidityPeriod"></s:hidden>
														<!-- id="cardValidityPeriod" value="<s:date name="cardManagementDTO.cardValidityPeriod" format="yyyy-MM-dd"/>" -->
													</td>

												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
														<tr>
															<td style="text-align: right;">
																卡号：
															</td>
															<td>
<%-- 														<s:label name="cardManagementDTO.transferOutCard" /> --%>
														<s:textfield name="cardManagementDTO.cardNo"
															readonly="true" id="transferOutCard" size="23"></s:textfield> 
<%-- 														<s:fielderror> --%>
<%-- 															<s:hidden name="cardManagementDTO.transferOutCard" --%>
<%-- 															id="transferOutCard" size="23"/> --%>
<%-- 															<s:param> --%>
<!-- 																cardManagementDTO.transferOutCard -->
<%-- 															</s:param> --%>
<%-- 														</s:fielderror> --%>
															</td>
														</tr>
													</table>&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<s:if test="cardManagementDTO.cardBalanceDTOs!=null">

										<s:iterator value="cardManagementDTO.cardBalanceDTOs"
											var="cardBalanceDTO">
											<tr>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 100px; text-align: left;">
																账户类型：

															</td>
															<td>
																<s:label name="#cardBalanceDTO.accountType" />

															</td>
														</tr>
													</table>
												</td>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 110px; text-align: right;">
																当前余额(元)：

															</td>
															<td>
																<s:label name="#cardBalanceDTO.total" />

															</td>
														</tr>
													</table>
												</td>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 110px; text-align: right;">
																可用余额(元)：

															</td>
															<td>
																<s:label name="#cardBalanceDTO.balance" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</s:iterator>
									</s:if>
									<s:else>
										<tr>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: left;">
															账户类型：

														</td>
														<td>
															<s:label name="#cardBalanceDTO.accountType" />

														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															当前余额(元)：

														</td>
														<td>
															<s:label name="#cardBalanceDTO.total" />

														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="text-align: left; width: 100%">
													<tr>
														<td style="width: 110px; text-align: right;">
															可用余额(元)：

														</td>
														<td>
															<s:label name="#cardBalanceDTO.balance" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:else>

									<tr style="text-align: right; width: 100%">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														激活状态：

														<s:select list="#{'0':'否','1':'是'}"
															name="cardManagementDTO.active"
															id="cardManagementDTO.active" label="激活状态："
															disabled="true" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														锁卡状态：

														<s:select list="#{'0':'否','1':'是'}"
															name="cardManagementDTO.lock" id="cardManagementDTO.lock"
															label="锁卡状态：" disabled="true" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														注销状态：

														<s:select list="#{'0':'否','1':'是'}"
															name="cardManagementDTO.off" id="cardManagementDTO.off"
															disabled="true" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														挂失状态：

														<s:select list="#{'0':'否','1':'是'}"
															name="cardManagementDTO.hang" id="cardManagementDTO.hang"
															disabled="true" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td>
														在库状态：

														<s:select list="#{'3':'否','1':'是'}"
															name="cardManagementDTO.stockState"
															id="cardManagementDTO.stockState" disabled="true" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								</br>
								</br>
								<div style="display:${isDisplay}" id="displayLor">
									<s:hidden name="isDisplay"></s:hidden>
									<input type="checkbox" onclick="displaylory();"
										name="cardManagementDTO.owner" value="1" id="check"
										<s:if test="cardManagementDTO.owner==1">checked</s:if> />
									本人办理
									<div
										style="display:<s:if test='cardManagementDTO.owner==2'>block;</s:if><s:else>none;</s:else>"
										id="loryinfor">
										<table width="100%" style="table-layout: fixed;" border="0">

											<tr>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 110px; text-align: left;">
																<h3>
																	代理人信息
																</h3>

															</td>
															<td>


															</td>
														</tr>
													</table>
												</td>

											</tr>

											<tr>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 110px; text-align: left;">
																<span class="no-empty">*</span>代理人姓名：

															</td>
															<td>
																<s:textfield name="cardManagementDTO.agentrName" />
																<s:fielderror>
																<s:param>
																	cardManagementDTO.agentrName
																</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
												<td>
													<table style="text-align: left; width: 100%">
														<tr>
															<td style="width: 110px; text-align: right;">
																<span class="no-empty">*</span>证件类型：

															</td>
															<td>
																<s:select
																	list="#{'00':'身份证','01':'护照','10':'出生证','11':'工作证'}"
																	name="cardManagementDTO.agentrCertType"
																	id="agentrCertType"></s:select>

																<s:fielderror>
																	<s:param>
																cardManagementDTO.agentrCertType
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
															<td style="width: 110px; text-align: left;">
																<span class="no-empty">*</span>证件号码：

															</td>
															<td>
																<s:textfield name="cardManagementDTO.agentrCertTypeNo"
																	maxLength="18" />

																<s:fielderror>
																	<s:param>	
																cardManagementDTO.agentrCertTypeNo
																</s:param>
																</s:fielderror>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr style="text-align: right; width: 100%">
												<td colspan="2">
													<table style="text-align: left; width: 100%">
														<tr>
															<td>
																<span class="no-empty">*</span>证件有效期：from
																<input type="text" name="cardManagementDTO.startDate"
																	id="startDate" onclick="dateClick(this)" class="Wdate"
																	value="${cardManagementDTO.startDate}" />
																<s:fielderror>
																	<s:param>
																cardManagementDTO.startDate
															</s:param>
																</s:fielderror>
																to
																<input type="text" name="cardManagementDTO.endDate"
																	id="endDate" onclick="dateClick(this)" class="Wdate"
																	value="${cardManagementDTO.endDate}" />
																<s:fielderror>
																	<s:param>
																cardManagementDTO.endDate
															</s:param>
																</s:fielderror>
															</td>
															<td>
																&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</div>
								</div>
								<br />
								<br />
														<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>

										</td>
									</tr>

								</table>

							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<s:if test="cardManagementDTO.lock==1">
															<display:security urlId="3101514,4030114">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	安全信息设置
																</button>
															</display:security>
															<display:security urlId="3101513,4030113">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	密码重置
																</button>
															</display:security>
															<display:security urlId="3101512,4030112">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	业务记录
																</button>
															</display:security>
															<display:security urlId="3101511,4030111">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	交易记录
																</button>
															</display:security>
															<display:security urlId="3101510,4030110">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	赎回
																</button>
															</display:security>
<%-- 															<display:security urlId="3101508,4030108"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	转账 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101507,4030107">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	延期
																</button>
															</display:security>
<%-- 															<display:security urlId="3101506,4030106"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	充值 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101515,4030115">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	激活
																</button>
															</display:security>



<!--															 解锁 -->
															<display:security urlId="3101501,4030101">
																<input type="button" class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定解锁吗？',function(){newForm.action='${ctx}/cardManageExt/nolockInit';newForm.submit();});" value="解锁"/>
															</display:security>
														</s:if>
														
														<s:if test="cardManagementDTO.off==1">
															<display:security urlId="3101514,4030114">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	安全信息设置
																</button>
															</display:security>
															<display:security urlId="3101513,4030113">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	密码重置
																</button>
															</display:security>
															<display:security urlId="3101512,4030112">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	业务记录
																</button>
															</display:security>
															<display:security urlId="3101511,4030111">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	交易记录
																</button>
															</display:security>
<%-- 															<display:security urlId="3101510,4030110"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	赎回 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
<%-- 															<display:security urlId="3101508,4030108"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	转账 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101507,4030107">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	延期
																</button>
															</display:security>
<%-- 															<display:security urlId="3101506,4030106"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	充值 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>

															<!-- 解锁 -->
															<display:security urlId="3101501,4030101">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	解锁
																</button>
															</display:security>
															<!-- 解挂 -->
															<display:security urlId="3101504,4030104">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	解挂
																</button>
															</display:security>
														</s:if>
													
														<s:if test="cardManagementDTO.hang==1">
															<display:security urlId="3101514,4030114">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	安全信息设置
																</button>
															</display:security>
<%-- 															<display:security urlId="3101513,4030113"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	密码重置 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101512,4030112">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	业务记录
																</button>
															</display:security>
															<display:security urlId="3101511,4030111">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	交易记录
																</button>
															</display:security>
<%-- 															<display:security urlId="3101510,4030110"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	赎回 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
<%-- 															<display:security urlId="3101508,4030108"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	转账 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101507,4030107">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	延期
																</button>
															</display:security>
<%-- 															<display:security urlId="3101506,4030106"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	充值 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101515,4030115">
																<button class='bt' style="float: right; margin: 5px"
																	disabled>
																	激活
																</button>
															</display:security>
															<!-- 解锁 -->
<%-- 															<display:security urlId="3101501,4030101"> --%>
<!-- 																<button class='bt' style="float: right; margin: 5px" -->
<!-- 																	disabled> -->
<!-- 																	解锁 -->
<!-- 																</button> -->
<%-- 															</display:security> --%>
															<!-- 解挂 -->
															<display:security urlId="3101504,4030104">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定解挂吗？',function(){newForm.action='${ctx}/cardManageExt/nohangInit';newForm.submit();});" value="解挂"/>
															</display:security>
														</s:if>
												
														<s:if test="cardManagementDTO.lock==0&&cardManagementDTO.hang==0&&cardManagementDTO.off==0&&cardManagementDTO.active!=0">
														    
															<%-- <display:security urlId="3101514,4030114">
																<input type="button" class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定安全信息设置吗？',function(){newForm.action='${ctx}/cardManageExt/setCardSecurityInfoInit';newForm.submit();});" value="安全信息设置"/>
																	
															</display:security> --%>
															<display:security urlId="3101513,4030113"> 
 																<input type="button"  class='bt' style="float: right; margin: 5px" 
 																	onclick="confirm('确定密码重置吗？',function(){newForm.action='${ctx}/cardManageExt/passwordInit';newForm.submit();});" value="密码重置"/> 
																
															</display:security>
															<display:security urlId="3101512,4030112">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="selectSingleCardOperation();" value="业务记录"/>
															</display:security>
															<display:security urlId="3101511,4030111">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="newForm.action='${ctx}/cardManageExt/cardTxnSelectInit';newForm.submit();" value="交易记录"/>
															</display:security>
<%-- 															<display:security urlId="3101510,4030110"> --%>
<!-- 																<input type="button" class='bt' style="float: right; margin: 5px" -->
<!-- 																	onclick="confirm('确定赎回吗？',function(){newForm.action='cardManageExt/retrieveCardInit';newForm.submit();});" value="赎回"/> -->
<%-- 															</display:security> --%>
<%-- 															<display:security urlId="3101508,4030108"> --%>
<!-- 																<input type="button"  class='bt' style="float: right; margin: 5px" -->
<!-- 																	onclick="confirm('确定转账吗？',function(){newForm.action='cardManageExt/transferAccountInit';newForm.submit();});" value="转账"/> -->
<%-- 															</display:security> --%>
															<display:security urlId="3101507,4030107">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定延期吗？',function(){newForm.action='cardManageExt/cardDelayInit';newForm.submit();});" value="延期"/>
															</display:security>
<%-- 															<display:security urlId="3101506,4030106"> --%>
<!-- 																<input type="button"  class='bt' style="float: right; margin: 5px" -->
<!-- 																	onclick="confirm('确定充值吗？',function(){newForm.action='cardManageExt/rechargeInit';newForm.submit();});" value="充值"/> -->
																	
<%-- 															</display:security> --%>
															<display:security urlId="3101502,4030102">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定锁定吗？',function(){newForm.action='${ctx}/cardManageExt/lockInit';newForm.submit();});" value="锁定"/>
															</display:security>
															<display:security urlId="3101503,4030103">
																<input type="button"  class='bt' style="float: right; margin: 5px"
																	onclick="confirm('确定挂失吗？', function(){newForm.action='${ctx}/cardManageExt/hangInit';newForm.submit();});" value="挂失"/>
															</display:security>
														</s:if>
		<div id="reasonDiv" style = "display: none">
		<center>
			<form id="reasonForm" action="">
			<br/><br/><br/><br/>
			  <input type="hidden" id="reasonId" name="reason" value=""/>
			  <lable>作废原因</lable><textarea name="invalidReasonName" cols="50" rows="3" id="invalidReasonId"></textarea><br/><br/>
			  <input type="button" value="确认作废 " onclick="invalid()"/>
			  <input type="button" value="关 闭 " onclick="closeWin()" />
			</form>
		</center>
		</div>
	</body>
</html>

