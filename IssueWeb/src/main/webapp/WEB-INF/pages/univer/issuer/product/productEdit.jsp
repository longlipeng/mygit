<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>产品编辑</title>
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
			
			var isDisplayPackageTable = false;
			function displayPackageTable() {
				if (isDisplayPackageTable) {
					display('packageTable');
					isDisplayPackageTable = false;
				} else {
					undisplay('packageTable');
					isDisplayPackageTable = true;
				}
			}
			
			var isDisplayAccountTable = false;
			function displayAccountTable() {
				if (isDisplayAccountTable) {
					display('accountTable');
					isDisplayAccountTable = false;
				} else {
					undisplay('accountTable');
					isDisplayAccountTable = true;
				}
			}
			
			var isDisplayCardLayoutTable = false;
			function displayCardLayoutTable() {
				if (isDisplayCardLayoutTable) {
					display('cardLayoutTable');
					isDisplayCardLayoutTable = false;
				} else {
					undisplay('cardLayoutTable');
					isDisplayCardLayoutTable = true;
				}
			}
			
			var isDisplayFaceValueTable = false;
			function displayFaceValueTable() {
				if (isDisplayFaceValueTable) {
					display('faceValueTable');
					isDisplayFaceValueTable = false;
				} else {
					undisplay('faceValueTable');
					isDisplayFaceValueTable = true;
				}
			}
			
			var isDisplayCardBinTable = false;
			function displayCardBinTable() {
				if (isDisplayCardBinTable) {
					display('cardBinTable');
					isDisplayCardBinTable = false;
				} else {
					undisplay('cardBinTable');
					isDisplayCardBinTable = true;
				}
			}
			
			
			
			function update(){
				var n=0;
				var checkbox=document.getElementsByName("acctypebox");
				var id='';
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						id=checkbox[i].value;
						n++;
					}
				}
				
				if(n==0){
					alert('请选择要编辑的对象');
				}
				if(n>1){
					alert('编辑对象只能是一个');
				}
				if(n==1){
					var acctypeValue=window.showModalDialog('${ctx}/accountEdit.action?acctypeId='+id+'&pid='+document.getElementById('productId').value+'&productDTO.maxBalance=${productDTO.maxBalance}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
					if(acctypeValue!=null){
						document.getElementById("newForm").action="load.action";
			 			document.getElementById('newForm').submit();
			 		}
				}
				
			}
			function addCard(){
				var cardValue=window.showModalDialog('${ctx}/cardLayoutList.action?id=${productDTO.productId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(cardValue!=null){
					document.getElementById('cardId').value=cardValue;
					document.getElementById('newForm').action='cardLayoutAdd.action';
					document.getElementById('newForm').submit();
				}
			}
			
			function delCard(){
				var n=0;
				var checkbox=document.getElementsByName("cardLayoutbox");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					alert('请选择要删除的对象');
				}	
				if(n>0){
					confirm("确定删除吗?",operationdelCard);
				}
			}
			function operationdelCard(){
				document.newForm.action='productCardLayoutDel.action';
				document.newForm.submit();
			}
			
			
			function addFaceValue(){
			   var array = document.getElementsByName("faceValuebox");	
			   var faceValueTypeCount=0;
			  
			for(var i=0;i<array.length;i++){
			
			   var faceValueType=array[i].value.split(",")[1];
			  
			  
			   if(1==faceValueType){ 
			  
			       faceValueTypeCount=faceValueTypeCount+1;
			        
			   }
			
			}
		
		        var faceValue=window.showModalDialog('${ctx}/faceValueList.action?id=${productDTO.productId}&productDTO.maxBalance=${productDTO.maxBalance}&productDTO.onymousStat=${productDTO.onymousStat}&faceValueTypeCount='+faceValueTypeCount, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(faceValue!=null){
					document.getElementById('faceValue').value=faceValue;
					document.getElementById('newForm').action='faceValueAdd.action';
					document.getElementById('newForm').submit();
				}
			}
			function delFaceValue(){
				var n=0;
				var checkbox=document.getElementsByName("faceValuebox");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					alert('请选择要删除的对象');
				}	
				if(n>0){
				confirm("确定删除吗？",operdelFaceValue);
				}
			}
			function operdelFaceValue(){
				document.newForm.action='prodFaceValueDel';
				document.newForm.submit();
			}
			
			
			function addPackage(){
				var packageId=window.showModalDialog('${ctx}/productPackageList.action?id=${productDTO.productId}&ver='+Math.random(), '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(packageId!=null){
					
					document.getElementById('packageId').value=packageId;
					document.getElementById('newForm').action='packageAdd.action';
					document.getElementById('newForm').submit();
				}
			}
			
			function delPackage(){
				var n=0;
				var checkbox=document.getElementsByName("packagebox");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					alert('请选择要删除的对象');
				}	
				if(n>0){
					confirm("确定删除吗？",operdelPackage);
				}
			}
			function operdelPackage(){
				document.newForm.action='packageDel';
				document.newForm.submit();
			}
			
			
			function addAcctype(){
				
				/*控制如果有账户的不能添加账户 */  
				 var serviceId = document.getElementById("serviceIdForCheck");
				 if(serviceId!=null&&serviceId!=""){
					 alert("产品已有账户，不可添加！");
					 return;
				 }
				var acctype=window.showModalDialog('${ctx}/productAccountInquery.action?id=${productDTO.productId}&productDTO.maxBalance=${productDTO.maxBalance}', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:800px;resizable:no');
				<%-- FIXME window.showModalDialog这是一个坑爹的方法. Micrrosoft 你敢再坑点嘛.谷歌浏览器不支持模式对话框内表单提交后再次返回值. --%>
				if(acctype == undefined || acctype != null){
					document.getElementById("newForm").action="load.action";
					document.getElementById('newForm').submit();
				}
			}
             
            			
			function delAcctype(){
				var n=0;
				var defaultFlagId;
				var checkbox=document.getElementsByName("acctypebox");
				for(i=0;i<checkbox.length;i++){
					if(checkbox.item(i).checked==true){
						n++;
						defaultFlagId=document.getElementsByName("defaultFlagId").item(i).value;
						if(defaultFlagId==1){
		                    alert("对不起，该账户为默认账户，如需删除，请先设置其他账户为默认账户！");   
		                    return;
		                }
					}
				}
				if(n==0){
					alert('请选择要删除的对象');
				}
                if(n>0){
					confirm("确定删除吗？",operdelAcctype);
				}
			}
			function operdelAcctype(){
                 
				
				document.newForm.action='accountDel';
				document.newForm.submit();
			}


			function addCardBin(){
				//var cardBin=window.showModalDialog('${ctx}/choiceCardBin?productQueryDTO.productId=${productDTO.productId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				var cardBin=window.showModalDialog('${ctx}/choiceCardBin?productDTO.productId=${productDTO.productId}&productDTO.onymousStat=${productDTO.onymousStat}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(cardBin!=null){
					var index=cardBin.indexOf("!");
					var realCardBin;
					if(index!=-1){
						realCardBin=cardBin.substring(0,index);
					}else{
						alert("卡bin错误！");
						return ;
					}
					
					document.getElementById('realCardBin').value=realCardBin;
					document.getElementById('cardBin').value=cardBin.replace("!","");
					document.getElementById('newForm').action='${ctx}/insertCardBinForProduct.action';
					document.getElementById('newForm').submit();
				}
			}

			 function modifyDefault() {
				 var n=0;
					var checkbox=document.getElementsByName("cardBinIds");
					for(i=0;i<checkbox.length;i++){
						if(checkbox[i].checked==true){
							n++;
						}
					}
					if(n!=1){
						alert('请选择一条记录');
						return;
					}else{
						document.newForm.action='${ctx}/modifyCardBinDefault.action';
						document.newForm.submit();
					}
					
			    }
			
			function delCardBin(){
				var n=0;
				var checkbox=document.getElementsByName("cardBinIds");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n!=1){
					alert('请选择一条要删除的对象');
					return;
				}	
				if(n==1){
					confirm("确定删除吗？",operdelCardBin);
				}
			}
			function operdelCardBin(){
				document.newForm.action='${ctx}/deleteCardBin.action';
				document.newForm.submit();
			}
			
			
			
			/*function select(){
	var s=document.getElementById("ruleId")
	var op = document.createElement("option"); // 新建OPTION (op) 
	s.insertBefore(op,s.options[0])
	//s.appendChild(op);
	op.setAttribute("value", ""); // 设置OPTION的 VALUE 
	op.appendChild(document.createTextNode("---请选择---")); // 设置OPTION的 TEXT
	}*/
	
	</script>
	</head>
	<body onload="load();">

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>产品信息编辑</span>
		</div>
		<s:form id="newForm" name="newForm" action="updateProduct" method="post">
		<s:token></s:token>
			<div id="ContainBox">
				<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
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
							       	<s:hidden name="productDTO.cardLayoutId" id="cardId" />
							        <s:hidden id="faceValue" name="faceValue" />
							        <s:hidden name="packageId" id="packageId" />
							       
							        <s:hidden name="productCardBinDTO.cardBin" id="cardBin"/>
							        <s:hidden name="productCardBinDTO.realCardBin" id="realCardBin"/>
							        <s:hidden name="productDTO.startDate" id="startDate" />
							        <s:hidden name="productDTO.endTime" id="endTime" />
							        <s:hidden id="productDTO.initActStat" name="productDTO.initActStat"></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>产品号：
													</td>
													<td>
														<s:textfield name="productDTO.productId" id="productId" readonly="true" />
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
														<s:textfield name="productDTO.productName" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																productDTO.productName
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
														产品英文名称：
													</td>
													<td>
														<s:textfield name="productDTO.productEnglishName"
															readonly="true"></s:textfield>
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
														发行机构：
													</td>
													<td>
													 <s:select id="entityId" 
												                   list="entityList"
												                   name="productDTO.entityId" 
												            listKey="entityId"
												            listValue="entityName"
											                disabled="true"></s:select>
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
														<s:hidden name="productDTO.cardType" />
														<dl:dictList displayName="productDTO.cardType"
															dictType="102" dictValue="${productDTO.cardType}"
															tagType="1" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														产品类别：
													</td>
													<td>
<%--														<s:hidden name="productDTO.productType" />--%>
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
														<span class="no-empty">*</span>署名类型：
													</td>
													<td>
													<s:hidden name="productDTO.onymousStat" ></s:hidden>
														<dl:dictList displayName="productDTO.onymousStat" dictValue="${productDTO.onymousStat}" defaultOption="false" dictType="816" tagType="1" ></dl:dictList>
														<s:fielderror>
															<s:param>
																productDTO.onymousStat
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<s:if test="productDTO.onymousStat==2">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>有效期月数：
													</td>
													<td>
														<s:textfield name="productDTO.validityPeriod" readonly="true"/>
														<s:fielderror>
															<s:param>
																productDTO.validityPeriod
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										</s:if>
									</tr>
<%--									<tr>--%>
<%--									<s:if test="productDTO.onymousStat==2">--%>
<%--									<td>--%>
<%--								<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 150px; text-align: right;">--%>
<%--														有效期可变：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														--%>
<%--													<s:select list="#{0:'否',1:'是'}" id="changeValidPeriod" name="productDTO.changeValidPeriod" onchange="selectValidityPeriod();"/>--%>
<%--												  --%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
<%--									</s:if>--%>
<%--									<td>--%>
<%--									<div id="validRule" style="display:none;" >--%>
<%--									<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 120px; text-align: right;">--%>
<%--														<span class="no-empty">*</span>有效期规则：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--													--%>
<%--														  <s:select id="ruleId" --%>
<%--												                   list="validRuleList"--%>
<%--												                   name="productDTO.validPeriodRule" --%>
<%--												            listKey="ruleId"--%>
<%--												            listValue="ruleName"--%>
<%--											                ></s:select>--%>
<%--														<s:fielderror>--%>
<%--															<s:param>--%>
<%--																productDTO.validPeriodRule--%>
<%--															</s:param>--%>
<%--														</s:fielderror>--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--											</div>--%>
<%--										</td>--%>
<%--									</tr>--%>
<%--									<tr>--%>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 120px; text-align: right;">--%>
<%--														<span class="no-empty">*</span>产品发行起始日期：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														--%>
<%--														<input type="text" name="productDTO.startDateDate"--%>
<%--															onclick="dateClick(this)" class="Wdate"--%>
<%--															value="<s:date format="yyyy-MM-dd" name="productDTO.startDateDate" />" />--%>
<%--														<s:fielderror>--%>
<%--															<s:param>--%>
<%--																productDTO.startDateDate--%>
<%--															</s:param>--%>
<%--														</s:fielderror>--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 150px; text-align: right;">--%>
<%--														产品发行截止日期：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														<input type="text" name="productDTO.endTimeTime"--%>
<%--															onclick="dateClick(this)" class="Wdate"--%>
<%--															value="<s:date format="yyyy-MM-dd" name="productDTO.endTimeTime" />" />--%>
<%--														<s:fielderror>--%>
<%--															<s:param>--%>
<%--																productDTO.endTimeTime--%>
<%--															</s:param>--%>
<%--														</s:fielderror>--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
<%--										--%>
<%--									</tr>--%>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>换卡费用：
													</td>
													<td>
														<s:textfield name="productDTO.replaceFee" readonly="true"/>
														元
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
														<s:textfield name="productDTO.annualFee" readonly="true"></s:textfield>
														元
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
														<s:textfield name="productDTO.cardFee" readonly="true"></s:textfield> 元
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
														<s:textfield name="productDTO.noPinLimit" readonly="true"></s:textfield> 元
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
														<s:textfield name="productDTO.maxBalance" id="max" readonly="true"/>
														元
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
														<s:hidden name="productDTO.supplStat"/>
														<s:select list="#{0:'没有附卡',1:'有附卡'}"
															name="productDTO.supplStat"  disabled="true" />
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
														<s:hidden name="productDTO.pinStat"/>
														<s:select list="#{2:'使用',0:'不使用'}"
															name="productDTO.pinStat"   disabled="true"/>
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
														<s:hidden name="productDTO.pinDelivMeans"/>
														<s:select list="#{0:'卡背面印刷',1:'信封打印发送',2:'短信发送'}"
															name="productDTO.pinDelivMeans"  disabled="true"/>
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
													<s:hidden name="productDTO.smsSvcStat"/>
													 <s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.smsSvcStat" disabled="true"/>
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
														<s:hidden name="productDTO.emailSvcStat"/>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.emailSvcStat" disabled="true"/>
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
														<s:hidden name="productDTO.monstmtSvcStat"/>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.monstmtSvcStat" disabled="true"/>
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
														<s:hidden name="productDTO.webPayStat"/>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webPayStat"  disabled="true"/>
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
													  <s:hidden name="productDTO.webSmsSvcStat"/>
													   <s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webSmsSvcStat"  disabled="true"/>
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
														<s:hidden name="productDTO.webEmailSvcStat"/>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.webEmailSvcStat"  disabled="true"/>
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
														<s:hidden name="productDTO.preAuthStat"/>
														<s:select list="#{1:'授权',0:'不授权'}"
															name="productDTO.preAuthStat" disabled="true"/>
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
														<s:hidden name="productDTO.ivrSvcStat"/>
														<s:select list="#{1:'可用',0:'不可用'}"
															name="productDTO.ivrSvcStat" disabled="true"/>
													</td>
												</tr>
											</table>
										</td>
										
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 120px; text-align: right;">--%>
<%--														是否实名：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														--%>
<%--														<s:select list="#{1:'是',0:'否'}"--%>
<%--															name="productDTO.onymousStat"  />--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>

									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														可否基于订单销售：
													</td>
													<td>
														<s:hidden name="productDTO.availableOrder"/>
														<s:select list="#{1:'可以',0:'不可以'}"
															name="productDTO.availableOrder"  disabled="true"/>
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
													    <s:hidden name="productDTO.availableStock"/>
														<s:select list="#{1:'可以',0:'不可以'}"
															name="productDTO.availableStock" disabled="true" />
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
														充值次数：
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
														充值次数：
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
														<span class="no-empty">*</span>激活当天交易限额（百分比）：
													</td>
													<td>
														<s:textfield id="productDTO.rsvData1" name="productDTO.rsvData1" maxlength="5" readonly="true"  />
														<s:fielderror>
															<s:param>
																productDTO.rsvData1
															</s:param>
														</s:fielderror>
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
				<button type="button" class='bt'
					style="float: right; margin: 5px 10px"
					onclick="window.location='productInquery.action'">
					返 回
				</button>
				<button class='bt' style="float: right; margin: 5px"
					onclick="newForm.submit();">
					提 交
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
									<td class="TableTitleFront" onclick="displayAccountTable();"
										style="cursor: pointer;">
										<span class="TableTop">账户明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="accountTable">
								<ec:table tableId="acctype" items="productDTO.services"
									var="map" width="100%" form="EditForm"
									action="${ctx}/issuer!inquery.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="acctypebox" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="acctypebox" 
												value="${map.serviceId}" />
											<input type="hidden" name="serviceIdForCheck"  id ="serviceIdForCheck"  value="${map.serviceId}" >
											<input type="hidden" name="defaultFlagId" id="defaultFlagId" value="${map.defaultFlag}"/>
										</ec:column>
										<ec:column property="序号" title="序号">
												${ROWCOUNT }
										</ec:column>
										<ec:column property="serviceId" title="账户号" width="10%" >
										    <s:property value="#attr['map'].serviceId" />
										  	<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
										</ec:column>
										<ec:column property="serviceName" title="账户名称" width="20%" />
										<ec:column property="entityName" title="发行机构" width="20%" />
										<ec:column property="defaultRate" title="默认服务费率 (%)" width="20%" />
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
													${productDTO.updateFlag}
													onclick="delAcctype();">
													删除
												</div>
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													${productDTO.updateFlag}
													onclick="update();">
													修改
												</div>
												<div id="btnDiv" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													${productDTO.updateFlag}
													onclick="addAcctype();">
													添加
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">

													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>

								<!-- div id=TableBody -->
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
									<td class="TableTitleFront" onclick="displayCardLayoutTable();"
										style="cursor: pointer;">
										<span class="TableTop">卡面明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="cardLayoutTable">
								<ec:table tableId="cardLayout" items="productDTO.cardLayoutDTOs"
									var="map" width="100%" form="newForm" action="cardLayoutList"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false" >
									
									<ec:row onclick="">
										<ec:column property="null" alias="cardLayoutbox" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="cardLayoutbox"
												value="${map.relId}" />
										</ec:column>
										<ec:column property="cardLayoutId" title="卡面号" width="40%" />
										<ec:column property="cardName" title="卡面名称" width="40%" />
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
													${productDTO.updateFlag}
													onclick="delCard();">
													删除
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addCard()">
														添加
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
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
									<td class="TableTitleFront" onclick="displayFaceValueTable();"
										style="cursor: pointer;">
										<span class="TableTop">面额明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="faceValueTable">
								<ec:table tableId="faceValue" items="productDTO.prodFaceValueDTO"
									var="map" width="100%" form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="faceValuebox" title="选择"
										width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="faceValuebox"
												value="${map.faceValueId},${map.faceValueType}" />
										</ec:column>
										<ec:column property="faceValueType" title="是否固定面额" width="20%">
											<c:if test="${map.faceValueType eq 0}">是</c:if>
											<c:if test="${map.faceValueType eq 1}">否</c:if>
										</ec:column>
										<ec:column property="faceValue" title="面额  (单位:元)" width="80%" />

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
													onclick="delFaceValue();">
													删除
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addFaceValue()">
														添加
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
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
									<td class="TableTitleFront" onclick="displayPackageTable();"
										style="cursor: pointer;">
										<span class="TableTop">包装明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="packageTable">
								<ec:table tableId="package" items="productDTO.packages"
									var="map" width="100%" form="EditForm" action=""
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" alias="packagebox" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="packagebox"
												value="${map.relId}" />
										</ec:column>
										<ec:column property="packageName" title="包装名称" width="20%" />

										<ec:column property="packageFee" title="包装费  (单位:元)"
											width="70%" />

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
													${productDTO.updateFlag}
													onclick="delPackage();">
													删除
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn"
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addPackage();">
														添加
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
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
									<td class="TableTitleFront" onclick="displayCardBinTable();"
										style="cursor: pointer;">
										<span class="TableTop">卡类别明细</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="cardBinTable">
								<ec:table tableId="package" items="productDTO.productCardBinDTOs"
									var="map" width="100%" form="EditForm" action=""
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
								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">

												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="delCardBin();">
													删除
												</div>
												<div id="deleteBtn" class="btn"
													style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="modifyDefault();">
													设为生效
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
													<div id="addBtn" class="btn" 
														style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="addCardBin();">
														添加
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>

			</div>
		</s:form>
		<script type="text/javascript">
		
		
		  function selectConsumerTime(){
                  var consumerTime=document.getElementById('consumerTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("comTimeId").style.display="block";
                    }else{
                    	document.getElementById("consumerTId").value="";
                    	document.getElementById("comTimeId").style.display="none";
                    }
			  }
           
		  function selectRechargeTime(){
                  var consumerTime=document.getElementById('rechargeTimesId').value;
                   if(consumerTime=='0'){
                      document.getElementById("recTimeId").style.display="block";
                    }else{
                    	document.getElementById("rechargeTId").value="";
                    	document.getElementById("recTimeId").style.display="none";
                    }
			  } 
		  function optionRechargeTime(){
			  var consumerTime=document.getElementById('rechargeTId').value;
			  if(consumerTime==""){
				  consumerTimes=document.getElementById('rechargeTimesId').value="1";
			  }
			  selectRechargeTime();
		  }
		  function optionConsumerTime(){
			  var consumerTime=document.getElementById('consumerTId').value;
			  if(consumerTime==""){
				  consumerTimes=document.getElementById('consumerTimesId').value="1";
			  }
			 selectConsumerTime();
		  }
		   function load(){
		   /*  selectConsumerTime();
		    selectRechargeTime(); */
			   optionRechargeTime();
		    	optionConsumerTime();
		  }
		</script>
	</body>
</html>
