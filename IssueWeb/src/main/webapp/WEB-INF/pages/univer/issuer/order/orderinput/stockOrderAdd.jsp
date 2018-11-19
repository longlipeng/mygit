<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加库存订单</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>
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
	
	function productChange(select) {
		maskDocAll();
		newForm.action = "stockOrderInputAction!add";
		newForm.submit();
	}

	//function faceValueChange() {
		
	//	<s:iterator value="prodFaceValues" var="map">
	//		<s:if test="#map.faceValueType eq 1">
	//			if(document.getElementById("faceValueType").value==${map.faceValueId}){
	//				document.getElementById("faceValue").value=0;
	//				document.getElementById("sellOrderDTO.faceValueType").value=1;
	//			}	
	//		</s:if>
	//		<s:if test="#map.faceValueType eq 0">
	//			if(document.getElementById("faceValueType").value==${map.faceValueId}){
	//				document.getElementById("faceValue").value=${map.faceValue/100};
	//				document.getElementById("sellOrderDTO.faceValueType").value=0;
	//			}	
	//		</s:if>
	//	</s:iterator>
	//}
	function faceValueChange() {
			<s:iterator value="prodFaceValues" var="map">
				<s:if test="#map.faceValueType  eq 1">
					if(document.getElementById("faceValueId").value==${map.faceValue}){
						document.getElementById("sellOrderDTO.faceValue").value=0;
						document.getElementById("sellOrderDTO.faceValueType").value =1;
					}	
				</s:if>
				<s:if test="#map.faceValueType eq 0">
					if(document.getElementById("faceValueId").value==${map.faceValue}){
						document.getElementById("sellOrderDTO.faceValue").value=${map.faceValue/100};
						document.getElementById("sellOrderDTO.faceValueType").value =0;
					}	
				</s:if>
			</s:iterator>	
			selectInit();			
		}
		function selectInit(){
		var select = document.getElementById("faceValueId");
		for(var i=0;i<select.length;i++){
			var text = select.options[i].text;
			if(text.search(/固定/) != -1){
				var index = text.indexOf(".");
				if(index != -1){
					text = text.slice(0,index);
					select.options[i].text = text;
				}
			}
		}
	}
	function addOrderSubmit(){
		var binType = '<%=request.getAttribute("binType")%>';
		if(binType==1){
			if(!checkFileFormat()){
				return null;
			}
		}
		
		var cardValidityPeriod=document.getElementById("cardValidityPeriod").value;
		if(cardValidityPeriod != null){
			document.getElementById("sVP").value=cardValidityPeriod;
		}
		var cardQuantity=document.getElementById("sellOrderDTO.cardQuantity").value;
		var r =/^\+?[1-9][0-9]*$/;
		if(!r.test(cardQuantity)){  
			errorDisplay("制卡张数请输入非零正整数并不能大于5000!");  
	        return;
	    }
		if(parseInt(cardQuantity)>5000){
			errorDisplay("制卡张数不能大于5000!");  
	        return;
		}
		maskDocAll();
		newForm.action = "stockOrderInputAction!insert";
		newForm.submit();
	}
	
	//校验上传文件格式
	function checkFileFormat(){
		$("#path_msg").empty("");
		var fileName = document.getElementById("path").value;
		var suffix = fileName.substr(fileName.lastIndexOf("."));
		if(suffix.toLowerCase()!=".xls"){
			$("#path_msg").html('<ul id="newForm_" class="errorMessage"><li><span>请选择xls格式的文件！</span></li></ul>');
			return false;
		}
		return true;
	}
	
	function orderSubmit1(){
		var path=document.getElementById("path").value;
		newForm.action = "stockOrderInputAction!download?path="+path;
		newForm.submit();
	}
	function onProductChange(){
		var curProductId=document.getElementById("sellOrderDTO.productId").value;
		<s:iterator value="productDTOs" var="map">
			if(curProductId==${map.productId}){
				if(${map.onymousStat}==3){
					document.getElementById("cardValidityPeriod").value='2099-12-31';
					document.getElementById("validPeriod").disabled=false;
					document.getElementById("validPeriod").value='2099-12-31';
					document.getElementById("cardValidityPeriod").disabled=true;
				}else{
					document.getElementById("cardValidityPeriod").disabled=false;
					document.getElementById("validPeriod").value="";
					document.getElementById("validPeriod").disabled=true;
				}
			}
		</s:iterator>
	}
	
	function browseFolder(path) {
	    try {
	        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
	        var Shell = new ActiveXObject("Shell.Application");
	        var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
	        //var Folder = Shell.BrowseForFolder(0, Message, 0); //起始目录为：桌面
	        if (Folder != null) {
	            Folder = Folder.items(); // 返回 FolderItems 对象
	            Folder = Folder.item(); // 返回 Folderitem 对象
	            Folder = Folder.Path; // 返回路径
	            if (Folder.charAt(Folder.length - 1) != "\\") {
	                Folder = Folder + "\\";
	            }
	            document.getElementById(path).value = Folder;
	            return Folder;
	        }
	    }
	    catch (e) {
	        alert(e.message);
	    }
	}
</script>
	</head>
	<body onload="faceValueChange();onProductChange();">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加库存订单基本信息</span>
		</div>
		<s:form id="newForm" name="newForm"
			action="reloadableCardAction!insert" method="post"  enctype="multipart/form-data">
			<div id="ContainBox">
			<!--<s:hidden name="path" id="path"></s:hidden>-->
				<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
					<tr>
						<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
										<span class="TableTop">订单信息</span>
									</td>
									<td class="TableTitleEnd">
										&nbsp;
									</td>
								</tr>
							</table>
							<div id="serviceTable">
								<table width="100%">
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											订单号：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderId" id="orderId"
												size="20" readonly="true" required="true" cssClass="lg_text_gray" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderId
												</s:param>
											</s:fielderror>
										</td>

										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>订单日期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.orderDate"
												id="sellOrderDTO.orderDate" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>
													sellOrderDTO.orderDate
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>产品：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.productId" id="sellOrderDTO.productId"
												list="productDTOs" listKey="productId"
												listValue="productName" onchange="productChange(this);">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.productId
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>账户：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.serviceId" list="services"
															listKey="serviceId" listValue="serviceName"
														 	id="sellOrderDTO.serviceId">
														</s:select>
														<s:fielderror>
															<s:param>
																sellOrderDTO.serviceId
															</s:param>
														</s:fielderror>
										</td>
										
									</tr>
									
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡面：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.cardLayoutId"
												id="cardLayoutId" list="cardLayouts" listKey="cardLayoutId"
												listValue="cardName">
											</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardLayoutId
												</s:param>
											</s:fielderror>
										</td>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡片有效期：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										<s:hidden name="sellOrderDTO.validityPeriod"  id="validPeriod"></s:hidden>
											<s:textfield name="sellOrderDTO.validityPeriod"
												id="cardValidityPeriod" size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
											<s:param>
												sellOrderDTO.validityPeriod
											</s:param>
											</s:fielderror>
										</td>	
									</tr>
									<s:if test="sellOrderDTO.cardIssueFee !=null && sellOrderDTO.annualFee !=null">
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											<span style="color: red;">*</span>卡费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield  name="sellOrderDTO.cardIssueFee"
												id="sellOrderDTO.cardIssueFee" size="20" maxLength="10"   readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardIssueFee
												</s:param>
											</s:fielderror>
										</td>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>年费(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.annualFee"
												id="annualFee" size="20" maxLength="10"  readonly="true"  cssClass="lg_text_gray"/>
											<s:fielderror>
												<s:param>
													sellOrderDTO.annualFee
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									</s:if>
									<tr>
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>面额类型：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<!--<s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
											<s:select name="faceValueId" id="faceValueType" list="prodFaceValues"
												listKey="faceValueId" listValue="faceValueType==1?'非固定':'固定'"
												onchange="faceValueChange();" >	
											</s:select>-->
											<s:hidden name="sellOrderDTO.faceValueType" id="sellOrderDTO.faceValueType"/>
												<s:select name="faceValueId" id="faceValueId"
													list="prodFaceValues" listKey="faceValue"
													
													 listValue="#request.productType==2?'固定' +(faceValue/100):'非固定 '"
													onchange="faceValueChange();">
												</s:select>
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValueType
												</s:param>
											</s:fielderror>
										</td>
										
										<%--<td align="right" width="15%" nowrap="nowrap">
											面额值：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:select name="sellOrderDTO.faceValue" id="faceValueType" list="prodFaceValues"
												listKey="faceValue" listValue="faceValue/100" />	
												
											<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
										--%>
										<td align="right" width="15%" nowrap="nowrap">
											面额值：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
										
											<s:textfield name="sellOrderDTO.faceValue"  readonly="true" id="sellOrderDTO.faceValue" size="20" />
												<s:fielderror>
												<s:param>
													sellOrderDTO.faceValue
												</s:param>
											</s:fielderror>
										</td>
									</tr>
								
									<tr>
										<td width="15%" align="right" nowrap="nowrap">
											总费用(元)：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.totalPrice"
												id="totalPriceStr" size="10" readonly="true" cssClass="lg_text_gray"/>
										</td>
										
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>张数：
										</td>
										<td>
										<s:textfield name="sellOrderDTO.cardQuantity"
													id="sellOrderDTO.cardQuantity" size="20" maxLength="10" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.cardQuantity
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									
									<tr>
										
										<td align="right" width="15%" nowrap="nowrap">
											备注：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<s:textfield name="sellOrderDTO.memo" id="memo" size="20"
												maxLength="400" />
											<s:fielderror>
												<s:param>
													sellOrderDTO.memo
												</s:param>
											</s:fielderror>
										</td>
										
										<s:if test="#request.binType==1">
										<s:hidden name="uploadCardFileDTO.binType"  value="%{#request.binType}" />
										<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>文件路径：
										</td>
										<td width="35%" align="left" nowrap="nowrap">
											<%-- <s:file name="uploadCardFileDTO.filepath" id="path" size="20"
												maxLength="400"   onfocus="browseFolder('path')" /> --%>
												<s:file name="uploadCardFileDTO.cardNoFile" id="path" size="20"
												maxLength="400"   />
												<div id="path_msg">
													<s:fielderror>
														<s:param>
															uploadCardFileDTO.file
														</s:param>
													</s:fielderror>
												</div>
										</td>
										</s:if>
									</tr>
									
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<s:hidden name="svalidityPriod" id="sVP"/>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<s:form action="stockOrderInputAction!list" name="backForm"
				id="backForm">
				<s:hidden name="addType" value=""></s:hidden>
				<button class='bt' style="float: right; margin: 7px"
					onclick="backForm.submit();">
					取 消
				</button>
			</s:form>
			<button class='bt' style="float: right; margin: 7px"
				onclick="addOrderSubmit();">
				确 定
			</button>
			<div style="clear: both"></div>
		</div>
		<s:if test="repCard!=null&&repCard.size()">
		<button class='bt' style="float: right; margin: 7px"
			onclick="orderSubmit1();">
				导出重复卡号
			</button>
		</s:if>
		
		
	</body>
</html>