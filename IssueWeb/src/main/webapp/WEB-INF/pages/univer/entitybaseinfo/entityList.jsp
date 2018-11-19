<%@page contentType="text/html; charset=UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
<!--

.phone { BORDER-RIGHT: #000000 0px solid; BORDER-TOP: #000000 0px solid; FONT-SIZE: 9pt; BACKGROUND: none transparent scroll repeat 0% 0%; OVERFLOW: hidden; BORDER-LEFT: #000000 0px solid; COLOR: black;font-weight:bold; BORDER-BOTTOM: #000000 0px solid}
-->
</style>
<script type="text/javascript" >
 function updateInfo(checkedId, url){
			var n=0;
			var checkbox=document.getElementsByName(checkedId);
			var id='';
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			
			if(n==0){
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				var acctypeValue=window.showModalDialog(url+'?checkedId='+id+'&entityId='+document.getElementById('entityId').value, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(acctypeValue == 'success'){
					reload();
					//document.sellerForm.action = "${ctx}/seller/edit.action";
					//document.sellerForm.submit();	
					//document.getElementById("newForm").action="reLoad.action";
		 			//document.getElementById('newForm').submit();
		 		}
			}
			
		}
          </script>

<body>
	<div>
		<div id="bankInfo" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="bankForm" name="bankForm" action="" method="post">
				<s:hidden name="entityId" />
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<div id="bankInfoTitle"
					style="font-weight: bold; height: 20px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer" >
								<span class="TableTop">银行信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="bankInfoTable"
					style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="bankList" var="map" width="100%"
						action="${ctx}/bankManagement"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" form ="bankForm"
						showPagination="false" sortable="false">
						<ec:row>
							<ec:column property="null" alias="chooseBankId" title="选择"
								width="10%" sortable="false" headerCell="selectAll"
								viewsAllowed="html">
								<input type="checkbox" name="chooseBankId"
									value="${map.bankId}" />
							</ec:column>
							<ec:column property="bankName" title="银行名称" width="30%">
								<s:property value="#attr['map'].bankName" />
								<s:property value="#attr['map'].accountFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
							<ec:column property="bankAccount" title="银行账户" width="30%"/>
							<ec:column property="bankAccountName" title="银行账户名称" width="30%"/>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addBank.action?entityId=${entityId}')"
												value="添加" />
										</td>
										 <td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateInfo('chooseBankId','${ctx}/${nameSpace}/editBank.action');"
												value="编辑" />
										</td> 
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delBank.action','bankForm','chooseBankId')"
												value="删除" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
		<div id="department" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="depForm" name="depForm" action="" method="post">
				<s:hidden name="entityId" />
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<div id="departmentTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer">
								<span class="TableTop">部门信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="departmentTable"
					style="background-color: #FBFEFF;  border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="departmentList" var="map" width="100%"
						action="${ctx}/departmentManagement"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" form ="depForm"
						showPagination="false" sortable="false">
						<ec:row>
							<ec:column property="null" alias="chooseDepartmentId" title="选择"
								width="10%" sortable="false" headerCell="selectAll"
								viewsAllowed="html">
								<input type="checkbox" name="chooseDepartmentId"
									value="${map.departmentId}" />
							</ec:column>
							<ec:column property="departmentId" title="部门号" width="40%" />
							<ec:column property="departmentName" title="部门名称" width="50%">
								<s:property value="#attr['map'].departmentName" />
								<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addDepartment.action?entityId=${entityId}')"
												value="添加" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateInfo('chooseDepartmentId', '${ctx}/${nameSpace}/editDepartment.action')"
												value="编辑" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delDepartment.action','depForm','chooseDepartmentId')"
												value="删除" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
		<div id="contract" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="contractForm" name="contractForm" action="" method="post">
				<s:hidden name="entityId" />
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<div id="contactTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer">
								<span class="TableTop">联系人信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="contactTable"
					style="background-color: #FBFEFF;  border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">		
					<ec:table items="contractList" var="map" width="100%"
						action=""
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" tableId="contact"
						showPagination="false" sortable="false">
						<ec:row>
							<ec:column property="null" alias="chooseContactId" title="选择"
								width="10%" sortable="false" headerCell="selectAll"
								viewsAllowed="html">
								<input type="checkbox" name="chooseContactId"
									value="${map.contactId}" />
							</ec:column>
							<ec:column property="contactName" title="姓名" width="30%">
								<s:property value="#attr['map'].contactName" />
								<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
							<ec:column property="contactFunction" title="职位" width="20%" />
							<ec:column property="contactMobilePhone" title="移动电话" width="20%" />
							<ec:column property="validFlag" title="有效状态" width="20%">
								<s:if test="#attr['map'].validityFlag ==1">有效
								</s:if>
								<s:else>无效</s:else>
							</ec:column>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addContact.action?entityId=${entityId}')"
												value="添加" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateInfo('chooseContactId','${ctx}/consumer/editContact.action');"
												value="编辑" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delContact.action','contractForm','chooseContactId')"
												value="删除" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>	
		</div>
		<div id="deliveryPoint" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		  	<s:form id="deliveryPointForm" name="deliveryPointForm" action="" method="post">
				<s:hidden name="entityId" />
				<s:hidden name="nameSpace"/>
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<div id="deliveryPointTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer">
								<span class="TableTop">快递点信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
		
				<div id="deliveryPointTable"
					style="background-color: #FBFEFF;  border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="deliveryPointList" var="map" width="100%"
						action="${ctx}/deliveryPointManagement"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" tableId="deliveryPoint"
						showPagination="false" sortable="false">
						<ec:row>
							<ec:column property="null" alias="choosePointId" title="选择"
								width="10%" sortable="false" headerCell="selectAll"
								viewsAllowed="html">
								<input type="checkbox" name="choosePointId"
									value="${map.deliveryId}" />
							</ec:column>
							<ec:column property="deliveryPointName" title="快递点名称" width="40%">
								<s:property value="#attr['map'].deliveryName" />
								<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
							<ec:column property="deliveryAddress" title="地址" width="50%" />
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addDeliveryPoint.action?entityId=${entityId}&nameSpace=${nameSpace}')"
												value="添加" ${disabled}/>
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateEntity('deliveryPointForm','choosePointId');"
												value="编辑" ${disabled}/>
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delDeliveryPoint.action','deliveryPointForm','choosePointId');"
												value="删除" ${disabled}/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
		<div id="address" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="addressForm" name="addressForm" action="" method="post">
				<s:hidden id="addressId" name="addressId" />
				<s:hidden name="entityId" />
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<div id="invoiceAddressTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer">
								<span class="TableTop">发票地址信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="invoiceAddressTable"
					style="background-color: #FBFEFF; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="invoiceAddressList" var="map" width="100%"
						action="${ctx}/invoiceAddressManagement"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" tableId="invoiceAddress"
						showPagination="false" sortable="false">
						<ec:row>
							<ec:column property="null" alias="chooseAddressId" title="选择"
								width="10%" sortable="false" headerCell="selectAll"
								viewsAllowed="html">
								<input type="checkbox" name="chooseAddressId"
									value="${map.invoiceAddressId}" />
							</ec:column>
							<ec:column property="invoiceAddress" title="发票地址" width="30%">
								<s:property value="#attr['map'].invoiceAddress" />
								<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
							<ec:column property="addressPostcode" title="邮编" width="20%" />
							<ec:column property="invoiceRecipient" title="收件人" width="20%" />
							<ec:column property="deliveryOption" title="订送方式" width="20%">
								<s:if test="#attr['map'].deliveryOption == 1">送货上门</s:if>
								<s:else>上门取货</s:else>
							</ec:column>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addAddress.action?entityId=${entityId}')" 
												value="添加" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateInfo('chooseAddressId', '${ctx}/${nameSpace}/editAddress.action')"
												value="编辑" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delAddress.action','addressForm','chooseAddressId');"
												value="删除" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
		<div id="address" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<s:form id="companyForm" name="companyForm" action="" method="post">
				<s:hidden name="entityId" />
				<s:hidden name="consumerDTO.entityId"></s:hidden>
		    	<s:hidden name="consumerDTO.fatherEntityId"></s:hidden>
				<s:hidden name="customerDTO.entityId"></s:hidden>
		    	<s:hidden name="customerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.entityId"></s:hidden>
		    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
		    	<s:hidden name="sellerDTO.newFatherEntityId"></s:hidden>
				<s:hidden id="invoiceCompanyId" name="invoiceCompanyId" />
				<div id="invoiceCompanyTitle"
					style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="TableTitleFront" style="cursor: pointer">
								<span class="TableTop">发票公司信息</span>
							</td>
							<td class="TableTitleEnd">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="invoiceCompanyTable"
					style="background-color: #FBFEFF;  border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		             <ec:table tableId="acctype" items="invoiceCompanyList"
						var="map" width="100%" form="EditForm"
						action="${ctx}/issuer!inquery.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						autoIncludeParameters="false" showPagination="false"
						sortable="false">
						<ec:row>
						   <ec:column property="null" alias="chooseCompanyId" title="选择"
							   width="10%" sortable="false" headerCell="selectAll">
							    <input type="checkbox" name="chooseCompanyId" value="${map.invoiceCompanyId}" />
						   </ec:column>
							<ec:column property="invoiceCompanyName" title="发票公司" width="40%">
							    <s:property value="#attr['map'].invoiceCompanyName" />
								<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
							</ec:column>
						</ec:row>
					</ec:table>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="addEntity('${ctx}/${nameSpace}/addCompany.action?entityId=${entityId}')" value="添加" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="updateInfo('chooseCompanyId', '${ctx}/${nameSpace}/editCompany.action')"
												value="编辑" />
										</td>
										<td>
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="delEntity('${ctx}/${nameSpace}/delCompany.action','companyForm','chooseCompanyId')" value="删除" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
		