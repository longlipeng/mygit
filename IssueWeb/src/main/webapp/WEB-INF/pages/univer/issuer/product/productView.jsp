<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>产品信息</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common3.js"></script>
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
			
			
		  function selectConsumerTime(){
              if(document.getElementById('consumerTimesId').selected=true){
                  var consumerTime=document.getElementById('consumerTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("comTimeId").style.display="block";
                    }else{
                    	document.getElementById("consumerTId").value="";
                    	document.getElementById("comTimeId").style.display="none";
                    }
                  }
			  }
           
		  function selectRechargeTime(){
              if(document.getElementById('rechargeTimesId').selected=true){
                  var consumerTime=document.getElementById('rechargeTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("recTimeId").style.display="block";
                    }else{
                    	document.getElementById("rechargeTId").value="";
                    	document.getElementById("recTimeId").style.display="none";
                    }
                  }
			  } 

		   function load(){
		    selectConsumerTime();
		    selectRechargeTime();

		  }
		</script>	
	</head>
	<body onload="load();">

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>查看产品信息</span>
		</div>
		<s:form id="newForm" name="newForm" action="updateProduct"
				method="post">
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
								<s:hidden name="acctypeValue" id="acctypeValue"/>
								<s:hidden name="packageId" id="packageId"/>
								<s:hidden name="cardLayoutDTO.cardLayoutId" id="cardId" />
								<s:hidden id="faceValue" name="faceValue" />
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>产品号：
													</td>
													<td>
														<s:label name="productDTO.productId" id="productId"></s:label>
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>产品名称：
													</td>
													<td>
														<s:label name="productDTO.productName"></s:label>
														
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
														<s:label name="productDTO.productEnglishName"></s:label>
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>发行机构：
													</td>
													<td>
														 <s:select id="entityId" 
												                   list="entityList"
												                   name="productDTO.entityId" 
												            listKey="entityId"
												            listValue="entityName"
											               disabled="true" ></s:select>

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
														<dl:dictList  displayName="productDTO.cardType"  dictType="102"  dictValue="${productDTO.cardType}"  tagType="1" /> 

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														产品类型：
													</td>
													<td>
														<s:hidden name="productDTO.productType" />
														<edl:entityDictList displayName="productDTO.productType"
															dictType="815" dictValue="${productDTO.productType}"
															tagType="1" />
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
														有效期月数：
													</td>
													<td>													
														<s:label name="productDTO.validityPeriod" />
													</td>
												</tr>
											</table>
										</td>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 110px; text-align: right;">--%>
<%--														产品发行起始日期：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--													<s:date format="yyyy-MM-dd" name="productDTO.startDateDate" />															--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
                                  <td>
<!--								<table style="text-align: left; width: 100%">-->
<!--												<tr>-->
<!--													<td style="width: 110px; text-align: right;">-->
<!--														有效期可变：-->
<!--													</td>-->
<!--													<td>-->
<!--														-->
<!--													<s:select list="#{0:'否',1:'是'}" id="changeValidPeriod" name="productDTO.changeValidPeriod" onchange="selectValidityPeriod();"  disabled="true" />-->
<!--												  -->
<!--													</td>-->
<!--												</tr>-->
<!--											</table>-->
										</td>	
						 	         </tr>
									<tr>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 150px; text-align: right;">--%>
<%--														产品发行截止日期：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--													<s:date format="yyyy-MM-dd" name="productDTO.endTimeTime" />--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡费：
													</td>
													<td>
														<s:label name="productDTO.cardFee"></s:label>元
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
														<s:label name="productDTO.noPinLimit"></s:label>元
														<s:fielderror>
															<s:param>
																productDTO.noPinLimit
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
<!--										<div id="validRule" style="display:none;" >-->
<!--											-->
<!--											<table style="text-align: left; width: 100%">-->
<!--												<tr>-->
<!--													<td style="width: 110px; text-align: right;">-->
<!--														有效期规则：-->
<!--													</td>-->
<!--													<td>													-->
<!--														<s:label name="productDTO.validPeriodRule" />-->
<!--													</td>-->
<!--												</tr>-->
<!--											</table>-->
<!--											</div>-->
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														换卡费用：
													</td>
													<td>
														<s:label name="productDTO.replaceFee" />元
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														年服务费：
													</td>
													<td>
														<s:label name="productDTO.annualFee"></s:label>元
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
														最大余额：
													</td>
													<td>
														<s:label name="productDTO.maxBalance"/>元
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														附卡状态：
													</td>
													<td><s:property value="productDTO.supplStat==1?'有附卡':'没有附卡'"/>
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
													<td><s:property value="productDTO.pinStat==2?'使用':'不使用'"/>
													
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
													 <s:property value="productDTO.pinDelivMeans==1?'信封打印发送':'卡背面印刷'"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														短信服务状态：
													</td>
													<td>
													   <s:property value="productDTO.smsSvcStat==1?'可用':'不可用'"/>
											
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
													    <s:property value="productDTO.emailSvcStat==1?'可用':'不可用'"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														月报服务：
													</td>
													<td>
													   <s:property value="productDTO.monstmtSvcStat==1?'可用':'不可用'"/>
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
													     <s:property value="productDTO.webPayStat==1?'可用':'不可用'"/>
													
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														网上交易短信状态：
													</td>
													<td>
														 <s:property value="productDTO.webSmsSvcStatStr==1?'可用':'不可用'"/>
									
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
													 <s:property value="productDTO.webEmailSvcStat==1?'可用':'不可用'"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														预授权状态：
													</td>
													<td> <s:property value="productDTO.preAuthStat==1?'授权':'不授权'"/>
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
													<td><s:property value="productDTO.ivrSvcStat==1?'可用':'不可用'"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														是否实名：
													</td>
													<td><s:property value="productDTO.onymousStat==1?'是':'否'"/>
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
													<td><s:property value="productDTO.availableOrder==1?'可以':'不可以'"/>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														可否基于库存销售：
													</td>
													<td><s:property value="productDTO.availableStock==1?'可以':'不可以'"/>
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
														<s:select list="#{1:'不限',0:'限定'}" name="productDTO.consumerTimeFlag"
														  id="consumerTimesId"  onchange="selectConsumerTime();" disabled="true" >
														 </s:select>
													</td>
												</tr>
											</table>
										</td>

										<td>
										<div id="comTimeId" style="display:none">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														消费次数：
													</td>
													<td>
													    <s:textfield name="productDTO.consumerTimes" id="consumerTId" maxlength="5" />
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
														充值次数1：
													</td>
													<td>
														<s:select list="#{1:'不限',0:'限定'}" name="productDTO.rechargeTimeFlag"
														  id="rechargeTimesId"  onchange="selectRechargeTime();" disabled="true"  >
														 </s:select>
													</td>
												</tr>
											</table>
										  </td>
										  <td>
										    <div id="recTimeId" style="display:none">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														充值次数1：
													</td>
													<td>
													    <s:textfield name="productDTO.rechargeTimes" id="rechargeTId" maxlength="5"/>
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
										<s:if test="productDTO.onymousStat==2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right">
														激活当天交易限额（百分比）：
													</td>
													<td>
													<s:label name="productDTO.rsvData1" />%
													</td>
												</tr>
											</table>
											</s:if>
										</td>
									</tr>
								</table>
						</td>
					</tr>
			</table>
			</div>

			<div id="btnDiv" style="text-align: right; width: 100%">
				<button type="button" class='bt' style="float: right; margin: 5px 10px"
					onclick="window.location='productInquery';">
					返回
				</button>
				
				<div style="clear: both"></div>
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
										<span class="TableTop">账户明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="productDTO.services" var="map" width="100%"
									form="EditForm" action="${ctx}/issuer!inquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="acctypebox"
												value="${map.serviceId}" />
												</ec:column>
											<ec:column property="serviceId" title="账户号" width="10%" >
                                                 <s:property value="#attr['map'].serviceId" />
										  	     <s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />							
											</ec:column>
											<ec:column property="serviceName" title="账户名称" width="20%" />
											<ec:column property="entityName" title="发行机构" width="20%" />
											<ec:column property="defaultRate" title="默认服务费率  (%)" width="20%" />
										
									</ec:row>
								</ec:table>											
						</td>
					</tr>
				</table>
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
										<span class="TableTop">卡面明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">

								<ec:table items="productDTO.cardLayoutDTOs" var="map" width="100%"
									form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="cardLayoutbox"
												value="${map.relId}" />
												</ec:column>
											<ec:column property="cardLayoutId" title="卡面号" width="10%" />
											<ec:column property="cardName" title="卡面名称" width="50%" />
									</ec:row>
								</ec:table>



								
							</div>
						</td>
					</tr>
				</table>
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
										<span class="TableTop">面额明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="productDTO.prodFaceValueDTO" var="map"
									width="100%" form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="faceValuebox"
												value="${map.faceValueId}" />
												</ec:column>
											<ec:column property="faceValueType" title="是否固定面额"
												width="20%">
												<c:if test="${map.faceValueType eq 0}">是</c:if>
												<c:if test="${map.faceValueType eq 1}">否</c:if>
											</ec:column>
											<ec:column property="faceValue" title="面额  (单位:元)" width="80%" />
										
									</ec:row>
								</ec:table>
								
							</div>
						</td>
					</tr>
				</table>
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
										<span class="TableTop">包装明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="productDTO.packages" var="map" width="100%"
									form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="packagebox"
												value="${map.relId}" />
												</ec:column>
											<ec:column property="packageName" title="包装名称" width="20%" />

											<ec:column property="packageFee" title="包装费  (单位:元)" width="70%" />
										
									</ec:row>
								</ec:table>
								
							</div>
						</td>
					</tr>
				</table>

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
										<span class="TableTop">卡类别明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table items="productDTO.productCardBinDTOs" var="map" width="100%"
									form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
									
										<ec:column property="null" alias="packagebox" title="选择"
											width="10%" sortable="false" >
											<input type="radio" name="cardBinIds" value="${map.productId},${map.cardBin}" />
										</ec:column>
										<ec:column property="cardBin" title="卡类别" />

										<ec:column property="effect" title="是否生效"  >
											<s:property value="#attr['map'].effect == 1 ? '生效' : '未生效'"/>
										</ec:column>

									</ec:row>
								</ec:table>
								
							</div>
						</td>
					</tr>
				</table>

			</div>
		</s:form>
	</body>
</html>
