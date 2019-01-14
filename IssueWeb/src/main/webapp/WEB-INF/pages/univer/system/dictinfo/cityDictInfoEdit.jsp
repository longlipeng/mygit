<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统参照编辑</title>
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
		
			
			
		</script>
	</head>
	<body>
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
							<s:form id="newForm" name="newForm" action="dictInfo/editDictInfo"
								method="post">
								<s:token></s:token>
								<s:hidden name="dictInfoDTO.dictId"></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>数据编号：
													</td>
													<td>
														<s:textfield name="dictInfoDTO.dictCode" maxlength="5" id="dictCode" readonly="true"/><s:fielderror>
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
														
														<s:hidden name="dictInfoDTO.dictTypeCode"  id="dictInfoDTO.dictTypeCode"/>
														<s:textfield name="dictInfoDTO.dictTypeName"	readonly="true"/>																		
												
													</td>
												</tr>
											</table>
										</td>																																																									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
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
														简称：
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
									<tr>								
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>是否可以更新：
													</td>
													<td>
														<s:select id="updateFlag"
															list="#{1:'可以更新',0:'不可更新'}"
																name="dictInfoDTO.updateFlag"></s:select>
													</td>
												</tr>
										</table>
										<td>	
										<td>
										<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>是否有效：
													</td>
													<td>
														<s:select id="updateFlag"
															list="#{1:'有效',0:'无效'}"
																name="dictInfoDTO.dictState"></s:select>
													</td>
												</tr>
										</table>
										</td>																		
									</tr>																									
								 <tr id="deliveryMeansTr1">									
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														区域父字典代码：
													</td>
													<td>
													 <edl:entityDictList displayName="dictInfoDTO.fatherDictId"  dictValue="${dictInfoDTO.fatherDictId}"  dictType="405" tagType="2" defaultOption="false"  />
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
			<button class='bt' style="float: right; margin: 5px" ${updateFlag }
				onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
