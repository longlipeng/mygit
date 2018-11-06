<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端系统参数添加</title>
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
			function igid(){
				var sel=document.getElementById("issuerGroupId");
				var selectvalue=sel.value;
				if(selectvalue!=''){
				
					var groupvalue=sel.options[0].value;
					document.getElementById("issuerGroup").value=groupvalue.substring(1);
					
					if(selectvalue==groupvalue){
						
						document.getElementById("issuer").value=0;
					}else{
						
						document.getElementById("issuer").value=selectvalue.substring(1);
					}
				}
			}
			
			 
			function addFaceValue(){
				var faceValue=window.showModalDialog('${ctx}/producte!faceValueList.action?id=${productDTO.productId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(faceValue!=null){
					document.getElementById('faceValue').value=faceValue;
					document.getElementById('newForm').action='faceValueAdd.action';
					document.getElementById('newForm').submit();
				}
			}
			
			
			
			
			function chooseMerchant(){
				var merchantInfo=window.showModalDialog('${ctx}/selectMerchant.action', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(merchantInfo!=null){
				var merchantId=merchantInfo.split(",");
					document.getElementById('posInfDTO.mchntId').value=merchantId[0];
				}
			}
			
			function change(){
				var selectValue=document.getElementById("prmTypes").value;
				if(selectValue==10001){
				}else if(selectValue=10002){
				
				}else{
				
				
				}		
				
			}
			
			function choose(){
				var i=document.getElementById("names").selectedIndex;	
				var name=document.getElementById("names").options[i].text;
				document.getElementById("sysParameterDTO.prmName").value=name;
			}
			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>终端系统参数信息</span>
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
									<span class="TableTop">终端系统参数信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="terParameter/addIcCard"
								method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>版本号：
													</td>
													<td>
														<s:textfield name="sysParameterDTO.prmVersion" ></s:textfield>
														<s:fielderror>
															<s:param>
																sysParameterDTO.prmVersion
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
														<span class="no-empty">*</span>参数类型：
													</td>
													<td>
														公共参数
														<s:hidden name="sysParameterDTO.prmType" value="10003"/>									
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
														<span class="no-empty">*</span>参数名称：
													</td>
													<td>																																																																																	
														<s:select list="#{'':'请选择','9F06':'AID','9F08':'应用版本号','DF01':'ASI','DF11':'TAC-缺省','DF12':'TAC-联机','DF13':'TAC-拒绝','DF18':'终端联机PIN支持'}"  name="sysParameterDTO.prmName"/>				
														<s:fielderror>
															<s:param>
																sysParameterDTO.prmName
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
														<span class="no-empty">*</span>参数值：
													</td>
													<td>
														<s:textfield name="sysParameterDTO.prmVal" maxlength="99"/>

														<s:fielderror>
															<s:param>
																sysParameterDTO.prmVal
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
														<span class="no-empty">*</span>参数状态：
													</td>
													<td>
														<s:select id="prmStat" list="#{0:'有效',1:'无效'}"
															name="sysParameterDTO.prmStat"></s:select>
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
														参数描述：
													</td>
													<td>
														<s:textarea name="sysParameterDTO.prmDesc" rows="5"
															cols="50" />
															<s:fielderror>
															<s:param>
																sysParameterDTO.prmDesc
															</s:param>
														</s:fielderror>
														
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
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='terParameter/inqueryPrm.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="newForm.submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
