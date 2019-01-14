<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增收单网关配置</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}

    function chooseConsumer() {
        var consumerDTO = window.showModalDialog('${ctx}/acqpay/chooseConsumer', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (consumerDTO != null) {
        	var array = consumerDTO.split(',');
			document.getElementById('merchantId').value = array[0];
			document.getElementById('merchantName').value = array[1];
			newForm.action="${ctx}/acqpay/selectProd";
			newForm.submit();
        }
    }
    function getPath(obj){
    if(obj){
    	  if (window.navigator.userAgent.indexOf("MSIE")>=1){
    	      obj.select();         
    	     return document.selection.createRange().text;        
    	  }else if(window.navigator.userAgent.indexOf("Firefox")>=1)
    	  {        
    	  		if(obj.files){
    	  		  return obj.files.item(0).getAsDataURL();
         		 }  
				return obj.value;        
		  }     
		      return obj.value;      
	   }  
    
    }
    function add(){
    	document.getElementById("certPath").value=getPath(document.getElementById("cert"));
    	document.getElementById("logoPath").value=getPath(document.getElementById("logo"));
    	document.newForm.action="${ctx}/acqpay/add";
			newForm.submit();
    
    }

</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增收单网关配置</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">配置信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="acqpay/add" enctype="multipart/form-data">
								<table width="100%" style="table-layout: fixed;">
								<tr>&nbsp;</tr>
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>收单机构号：
													</td>
													<td>
                                                        <s:textfield id="merchantId" name="acqPayDTO.entityId" cssClass="watch" onclick="chooseConsumer();" readonly="true"/>
														<s:fielderror>
															<s:param>
																acqPayDTO.entityId
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
										  </table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>收单机构名称：
													</td>
													<td>
														<s:textfield name="acqPayDTO.consumerName" id="merchantName" readonly="true"/>
														
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								<br/>
								<br/>
								<table width="95%" height="20" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td>
									<span class="TableTop">网关证书</span>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="certTable">
							<table width="100%" style="table-layout: fixed;">
							<tr>&nbsp;</tr>
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>证书文件：
													</td>
													<td>
														<s:file name="cert" id="cert"></s:file>
														<s:hidden name="certPath" id="certPath"></s:hidden>
														<s:fielderror>
															<s:param>
																cert
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
										  </table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>证书密码：
													</td>
													<td>
														<s:password name="acqPayDTO.certsPwd"></s:password>
														<s:fielderror>
															<s:param>
																acqPayDTO.certsPwd
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
						</div>
						<br/>
						<table width="95%" height="20" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td>
									<span class="TableTop">其他信息</span>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="otherTable">
							<table width="100%" style="table-layout: fixed;">
									<tr>&nbsp;</tr>
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>短信网关：
													</td>
													<td>
														<dl:dictList displayName="acqPayDTO.sms"
															dictType="903" dictValue="${acqPayDTO.sms}"
															tagType="2" />
														<s:fielderror>
															<s:param>
																acqPayDTO.sms
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
										  </table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>LOGO：
													</td>
													<td>
														<s:file name="logo" id="logo"/>
														<s:hidden id="logoPath" name="logoPath"></s:hidden>
														<s:fielderror>
															<s:param>
																logo
															</s:param>
														</s:fielderror> 	
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>版权：
													</td>
													<td>
														<s:textfield name="acqPayDTO.copyright"/>	
														<s:fielderror>
															<s:param>
																acqPayDTO.copyright
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
														<span class="no-empty">*</span>收单网关名称：
													</td>
													<td>
														<s:textfield id="acqPayDTO.insNm" name="acqPayDTO.insNm"></s:textfield>
														<s:fielderror>
															<s:param>
																acqPayDTO.insNm
															</s:param>
														</s:fielderror> 	
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>收单网关URL：
													</td>
													<td>
														<s:textfield id="acqPayDTO.domainUrl" name="acqPayDTO.domainUrl"></s:textfield>
														<s:fielderror>
															<s:param>
																acqPayDTO.domainUrl
															</s:param>
														</s:fielderror> 	
													</td>
												</tr>
											</table>
										</td>
										</tr>
								</table>
						</div>
						<br/>
						<table width="95%" height="20" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td >
									<span class="TableTop">网关产品</span>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="prodTable">
							<table width="100%" style="table-layout: fixed;">
									<s:iterator value="acqPayDTO.productDTOs" id="map">
										<tr>
										<td style="width: 150px; text-align: right;">
											<s:label name="productId" />
										</td>
										<td style="width: 150px; text-align: right;">
											<s:label name="productName" />
										</td>
										</tr>
									</s:iterator>
								</table>
						</div>
							</s:form>
					  </div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='acqpay/inquery';newForm.submit();">
				返回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="add();">
				下一步
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>