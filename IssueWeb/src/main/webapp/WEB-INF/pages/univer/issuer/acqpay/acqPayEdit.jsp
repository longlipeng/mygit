<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑收单网关配置</title>
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
    function edit(){
    	document.newForm.action="${ctx}/acqpay/edit.action";
			newForm.submit();
    
    }
     var entityId;
    var bankId;
   function addBank(){
   				entityId=document.getElementById('merchantId').value;
				var cardValue=window.showModalDialog('${ctx}/acqpay/chooseBank.action?acqPayDTO.entityId='+entityId, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(cardValue!=null){
					document.getElementById('newForm').action='reEdit.action?acqPayDTO.entityId='+entityId;
					document.getElementById('newForm').submit();
				}
			}
			
			function delBank(){
				var n=0;
				var checkbox=document.getElementsByName("banksBox");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
						bankId=checkbox[i].value;
					}
				}
				if(n==0){
					alert('请选择要删除的对象');
				}	
				if(n>0){
					confirm("确定删除吗?",operationdelBank);
				}
			}
			function operationdelBank(){
			    entityId=document.getElementById('merchantId').value;
				document.newForm.action='${ctx}/acqpay/delBank.action?acqPayDTO.entityId='+entityId+'&acqPayDTO.bank='+bankId;
				document.newForm.submit();
			}
   
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑收单网关配置</span>
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
                                                        <s:textfield id="merchantId" name="acqPayDTO.entityId" readonly="true"/>
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
														收单机构名称：
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
														证书文件：
													</td>
													<td>
														<s:label name="acqPayDTO.certs"></s:label>
														<s:file name="cert" ></s:file>
														<s:fielderror>
															<s:param>
																acqPayDTO.certs
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
														证书密码：
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
														短信网关：
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
														LOGO：
													</td>
													<td>
													    <s:label name="acqPayDTO.logo"></s:label>
														<s:file name="logo"></s:file>		
														<s:fielderror>
															<s:param>
																acqPayDTO.logo
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
														版权：
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
										<span class="TableTop">银行接口</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="TableBody">
								<ec:table tableId="cardLayout" items="acqPayDTO.banks"
									var="map" width="100%" form="newForm" action="cardLayoutList"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false" >
									<ec:row onclick="">
										<ec:column property="null" alias="cardLayoutbox" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="banksBox"
												value="${map.bankId}" />
										</ec:column>
										<ec:column property="bankName" title="银行名称" width="40%" />
										<ec:column property="mchantCode" title="商户号" width="40%" />
									</ec:row>
								</ec:table>



								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">

												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="delBank();">
													删除
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addBank()">
														添加
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
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
				onclick="edit();">
				确定
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>