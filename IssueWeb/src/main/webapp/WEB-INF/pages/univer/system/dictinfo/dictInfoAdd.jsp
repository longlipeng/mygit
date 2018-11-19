<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参照添加</title>
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
		
			function choose(){
				var selectValue=document.getElementById("dictTypes").value;
				if(selectValue==108){
					document.getElementById("deliveryMeansTr").style.visibility="";
					document.getElementById("deliveryMeansTr1").style.visibility="hidden";
				}else if(selectValue==408){
					document.getElementById("deliveryMeansTr1").style.visibility="";
					document.getElementById("deliveryMeansTr").style.visibility="hidden";
				}
				else{
					document.getElementById("deliveryMeansTr").style.visibility="hidden";
					document.getElementById("deliveryMeansTr1").style.visibility="hidden";						
				}
			}
			function load(){
				
				document.getElementById("deliveryMeansTr").style.visibility="hidden";
				document.getElementById("deliveryMeansTr1").style.visibility="hidden";
							
			}
			
			function sub(){
				maskDocAll();
				newForm.submit();
			}
		</script>
	</head>
	<body onload="load()">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>系统参照编辑</span>
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
									<span class="TableTop">系统参照信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="dictInfo/addDictInfo"
								method="post">
								<s:token></s:token>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>数据编号：
													</td>
													<td>
														<s:textfield name="dictInfoDTO.dictCode" maxlength="5" id="dictCode"/><s:fielderror>
															<s:param>
																dictInfoDTO.dictCode
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
														<span class="no-empty">*</span>数据类型名称：
													</td>
													<td>
														
														<s:select list="#{'':'请选择','400':'客户行业', '103':'持卡人分类', '104':'持卡人称谓', '105':'持卡人职位', '106':'商户付款方式','107':'门店类型', '108':'门店类型详细','109':'门店等级','110':'支付类型','145':'客户职业','501':'客户类别','502':'卡片等级','503':'卡片类别','504':'卡片金额','160':'卡商','161':'底板', '181':'商户类型', '207':'支付节点','211':'付款方式',   '401':'证件类型',   '402':'性别', '403':'国家和地区', '404':'币种', '405':'城市', '407':'销售区域', '408':'区域','421':'联系人类型','422':'结算代理','810':'争议类型', '193':'企业证件类型',  '811':'争议级别', '182':'发票类型','812':'终端所有者','813':'终端型号','159':'打印格式','815':'产品种类','899':'部门名称','185':'商户行业','120':'开票项目','902':'支付渠道',''}" 
																onchange="choose()" name="dictInfoDTO.dictTypeCode" id="dictTypes">
														</s:select>
														<s:fielderror>
															<s:param>
																dictInfoDTO.dictTypeCode
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>																																																									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>名称：
													</td>
													<td>
														<s:textfield name="dictInfoDTO.dictName" />														
														<s:fielderror>
															<s:param>
																dictInfoDTO.dictName
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
														<span class="no-empty">*</span>简称：
													</td>
													<td>
														<s:textfield name="dictInfoDTO.dictShortName" />														
														<s:fielderror>
															<s:param>
																dictInfoDTO.dictShortName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
										</table>
									<td>
										
									</tr>
									<tr>																										
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														英文名称：
													</td>
													<td>
														<s:textfield name="dictInfoDTO.dictEnglishName" maxlength="32"/>														
														<s:fielderror>
															<s:param>
																dictInfoDTO.dictEnglishName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
										</table>
										</td>										
									</tr>
									<%--<tr>								
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>是否可以更新：
													</td>
													<td>
														<s:select id="updateFlag"
															list="#{1:'可以更新'}"
																name="dictInfoDTO.updateFlag"></s:select>
													</td>
												</tr>
										</table>
										<td>																
									</tr>	
									--%>
									<tr id="deliveryMeansTr">									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														门店父字典代码：
													</td>
													<td>
														 <edl:entityDictList displayName="dictInfoDTO.shopfatherDictId"  dictType="107" tagType="2" defaultOption="false"  />						
													</td>
												</tr>
											</table>
										</td>
									</tr>		
									
									
								 <tr id="deliveryMeansTr1">									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														区域父字典代码：
													</td>
													<td>
													 <edl:entityDictList displayName="dictInfoDTO.cityfatherDictId"  dictType="405" tagType="2" defaultOption="false"  />
													</td>
												</tr>
											</table>
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="window.location='dictInfo/inqueryDictInfo.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
