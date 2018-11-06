<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端系统参数编辑</title>
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
				document.getElementById("posParameterDTO.prmName").value=name;
			}
			
			function commPrm(){
				var  prmVersion=document.getElementById("prmVersion").value;
				if(prmVersion.search('^([1-9]{1}[0-9]{0,4})$')!=0){
						alert("请输入一个五位内正整数!");
        				return false;
				}
				var prmType=document.getElementById("prmType").value;
				 ajaxRemote('terParameter/comPrm.action',{'prmType':prmType,'prmVersion':prmVersion},opertionSuccess,'html');
			
			}
			function opertionSuccess(data){
			if(data=="true"){
	    		window.location="../login.jsp";
	    		return;
	    	}
			
			document.getElementById("message").innerHTML=data;
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
									<span class="TableTop">终端参数信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="terParameter/addPrm"
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
														<s:textfield name="posParameterDTO.prmVersionInt" onblur="commPrm();" id="prmVersion"></s:textfield>
														<s:fielderror>
															<s:param>
																posParameterDTO.prmVersionInt
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
														<s:hidden name="posParameterDTO.prmType" value="10001" id="prmType"/>	
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
														<s:hidden name="posParameterDTO.prmName" id="posParameterDTO.prmName"/>								
														<s:select list="#{0:'请选择','11':'pos终端应用类型','12':'超时时间','13':'重试次数','14':'电话号码之一',
																'15':'电话号码之二','16':'主机地址一','17':'主机端口一','18':'主机地址二',
																'19':'主机端口二','23':'交易重发次数'}"   name="posParameterDTO.prmId" onchange="choose()" id="names"/>
														<s:fielderror>
															<s:param>
																posParameterDTO.prmName
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
														<s:textfield name="posParameterDTO.prmVal" maxlength="99"/>

														<s:fielderror>
															<s:param>
																posParameterDTO.prmVal
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
														<s:select id="prmStat" list="#{'0':'有效','1':'无效'}"
															name="posParameterDTO.prmStat"></s:select>
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
														<s:textarea name="posParameterDTO.prmDesc" rows="5"
															cols="50" />									
														<s:fielderror>
															<s:param>
																posParameterDTO.prmDesc
															</s:param>
														</s:fielderror>		
													</td>													
												</tr>
												<tr>
													<td>
													</td>
													<td>
													<div id="message" style="color:red"></div>
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
				onclick="this.disabled='disabled';newForm.submit();setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
