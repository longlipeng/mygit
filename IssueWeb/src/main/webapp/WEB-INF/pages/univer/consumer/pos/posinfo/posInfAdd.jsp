<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端信息添加</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
			var isDisplay = false;
			var choooseMerchantId="";
			var chooseMerchantId="";
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
				var  result=window.showModalDialog('${ctx}/selectMerchant.action', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');				
				
				if(result!=null){			
					var resultMap=result.split(",");
					document.getElementById('posInfoDTO.mchntId').value=resultMap[1];
					document.getElementById('mchntEntityId').value=resultMap[2];
				}
			}
			
			function chooseShopId(){
				var merchantId=document.getElementById('mchntEntityId').value;
				if(merchantId==""){
					alert("请先选择商户!");
					return ;
				}
				var  shopIds=window.showModalDialog('${ctx}/inquiryShop.action?merchantId='+merchantId, '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');				
				if(shopIds!=null){
				var  shopId=shopIds.split(',');
					document.getElementById('posInfoDTO.shopId').value=shopId[1];
					document.getElementById('posInfoDTO.mchntName').value=shopId[2];
				}
			}
			
			function chooseTermBrand(){
				var  termBrandId=window.showModalDialog('${ctx}/choosePosBrandInf.action?', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
				if(termBrandId!=null){
					var  termBrandIdNew=termBrandId.split(',');
					document.getElementById('posInfoDTO.termBrandId').value=termBrandIdNew[0];
					document.getElementById('posBrandInfQueryDTO.brandName').value=termBrandIdNew[1];			
				}	
			}
			
			function getNumber(){
			 var termId=document.getElementById("posInfoDTO.termId").value;
				if(termId.search('^([0-9]{1}[0-9]{6,6})$')!=0){
						alert("请输入一个七位的数字");
						return;
				}
				luhnCheckSum(termId);		
			}
			
			
			//LUHN算法
			//1.由最低位起每隔一位取一个数字，将这个数字乘2；
			//2.将数字中余下的数字和上面结果中的数字直接相加，得到结果B；
			//生成校验码C=A-B，其中A是比B大的10倍数中最小的数字，如B=86，则A=90；如B=34，A=40；
			//6 = 70-64 
			function luhnCheckSum (sCardNum) {  
    			var iOddSum = 0;   
    			var iEvenSum = 0;   
    			var bIsOdd = false;   
   				 for (var i = sCardNum.length-1; i >= 0; i --) {  
       				 var iNum = parseInt(sCardNum.charAt(i));   
        			if(bIsOdd) {   
           				 iOddSum += iNum;   
       			 	}   
        			else {   
       					iNum = iNum * 2;   
       				
        				if (iNum > 9) {
    						//如果乘出来的数大于9，将该数拆分，将拆分的数字相加得到新的
    						var temp=0;
        					for (var j = iNum.toString().length-1; j >= 0; j--) {  
          					  temp +=parseInt(iNum.toString().charAt(j));   
        					}   
        					iOddSum +=temp;
    					}else{
    						iOddSum +=iNum;
    					}
				}
				bIsOdd = !bIsOdd;   
			}
			var result=parseInt(iOddSum%10);
			var resultStr=0;
			//如果不能被10整除反一个数，如果可以被10整除，返回0
			if(result!=0){
				 resultStr=parseInt(10-result);
			
			}
			 var buffer = new StringBuffer();
  			buffer.append(sCardNum.toString());
  			buffer.append(resultStr.toString());
  			var str = buffer.toString();
			document.getElementById("posInfoDTO.termId").value=str;
			}
			
 		 function StringBuffer()    
		{    
   		 this._strings = [];    
   		 if(arguments.length==1)    
    	{    
        	this._strings.push(arguments[0]);    
   		 }    
		}    
   
	  StringBuffer.prototype.append = function(str)    
		{    
    		this._strings.push(str);    
    		return this;    
		}    
   
		StringBuffer.prototype.toString = function()    
		{    
    		return this._strings.join("");    
		}    
   		
		/* 返回长度 */   
		StringBuffer.prototype.length = function()    
		{    
    	var str = this._strings.join("");    
    	return str.length;    
		}    
   
		/* 删除后几位字符 */   
		StringBuffer.prototype.del = function(num)    
		{    
   		var len = this.length();    
   	 	var str = this.toString();    
    		str     = str.slice(0,len-num);    
    	this._strings = [];    
    	this._strings.push(str);    
		}  
		
		
		
		 	
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增终端信息</span>
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
									<span class="TableTop">终端信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="addposInf.action"
								method="post">
								<s:hidden name=""></s:hidden>
								<table width="100%" style="table-layout: fixed;">
									<tr>
									<!-- 	<td>
											<table style="text-align: left; width: 100%;display: none;">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>记录编号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.Id" maxLength="8"
															readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																posInfoDTO.id
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td> -->
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>终端号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termId" maxLength="8"
															id="posInfoDTO.termId"></s:textfield>
														
														<s:fielderror>
															<s:param>
																posInfoDTO.termId
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
														<span class="no-empty">*</span>商户号：
													</td>
													<td>
														<s:hidden id="mchntEntityId" name="posInfoDTO.mchntEntityId"></s:hidden>
														<s:textfield id="posInfoDTO.mchntId"
															name="posInfoDTO.mchntId" cssClass="watch" readonly="true"
															onclick="chooseMerchant()" />
														<s:fielderror>
															<s:param>
																posInfoDTO.mchntId
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
														<span class="no-empty">*</span>门店名称：
													</td>
													<td>
														<s:textfield name="posInfoDTO.mchntName"
															id="posInfoDTO.mchntName" readonly="true" />
														<s:fielderror>
															<s:param>
																posInfoDTO.mchntName
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
														<span class="no-empty">*</span>门店号：
													</td>
													<td>
														
														<s:textfield name="posInfoDTO.shopId" id="posInfoDTO.shopId"
															cssClass="watch" readonly="true" onclick="chooseShopId()" />
														<s:fielderror>
															<s:param>
																posInfoDTO.shopId
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
														<span class="no-empty">*</span>传输标识：
													</td>
													<td>
														<s:select
															list="#{'A':'ADSL','C':'CDMA','D':'Dial up','G':'GPRS','T':'TCP/IP'}"
															name="posInfoDTO.termTransFlag" />
														<s:fielderror>
															<s:param>
																posInfoDTO.termTransFlag
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
																<span class="no-empty">*</span>终端型号：
															</td>
															<td>
																<s:textfield name="posInfoDTO.termModel"/>		
																<s:fielderror>
															<s:param>
																posInfoDTO.termModel
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
														<span class="no-empty">*</span>终端厂商编号：
													</td>
													<td>
														<s:textfield name="posInfoDTO.termBrandId"
															id="posInfoDTO.termBrandId" cssClass="watch"
															readonly="true" onclick="chooseTermBrand()" />
														<s:fielderror>
															<s:param>
																posInfoDTO.termBrandId
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
														<span class="no-empty">*</span>终端厂商名称：
													</td>
													<td>
														<s:textfield name="posBrandInfQueryDTO.brandName" id="posBrandInfQueryDTO.brandName" readonly="true"/>																							
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
														<span class="no-empty">*</span>消费允许标志：
													</td>
													<td>
														<s:select list="#{'1':'允许','0':'不允许'}"
															name="posInfoDTO.consumePerFlag" />
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>充值允许标志：
													</td>
													<td>
														<s:select list="#{'0':'不允许','1':'允许'}"
															name="posInfoDTO.reloadPerFlag" />
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
														<span class="no-empty">*</span>重打印控制标志：
													</td>
													<td>
														<s:select list="#{'0':'无','1':'有'}"
															name="posInfoDTO.reprintCtrlFlag" />
													</td>
												</tr>
											</table>
										</td>
                                        <td>&nbsp;
                                        <!-- 
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>积分允许标志：
													</td>
													<td>
														<s:select list="#{'0':'不允许','1':'允许'}"
															name="posInfoDTO.pointPerFlag" />
													</td>
												</tr>
											</table>
											-->
										</td>
									</tr>

									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>最大交易笔数：
													</td>
													<td>
														<s:textfield name="posInfoDTO.maxTxnCntInt" maxlength="10" />
														<s:fielderror>
															<s:param>
																posInfoDTO.maxTxnCntInt
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
														<span class="no-empty">*</span>终端状态：
													</td>
													<td>
														<s:select list="#{'1':'有效','0':'无效'}"
															name="posInfoDTO.termStat" />
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
														<span class="no-empty">*</span>终端持有者：
													</td>
													<td>
													<edl:entityDictList displayName="posInfoDTO.termOwner"  dictType="812" tagType="2"  />	
													<s:fielderror>
															<s:param>
																posInfoDTO.termOwner
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
														<span class="no-empty">*</span>终端通讯参数版本号：
													</td>
													<td>
														<s:select id="prmVersion1" name="posInfoDTO.prmVersion1"
															list="versionlist1" listKey="prmVersion"
															listValue="prmVersion" ></s:select>
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
														<span class="no-empty">*</span>终端卡BIN参数版本号：
													</td>
													<td>
														<s:select id="prmVersion2" name="posInfoDTO.prmVersion2"
															list="versionlist2" listKey="prmVersion"
															listValue="prmVersion"></s:select>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														终端IC卡参数版本号：
													</td>
													<td>
														<s:select id="prmVersion2" name="posInfoDTO.prmVersion3"
															list="versionlist3" listKey="prmVersion"
															listValue="prmVersion"></s:select>
													</td>
												</tr>
											</table>
										</td>
									</tr>

								</table>
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.location='inqueryTerinalManagement.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="this.disabled='disabled';newForm.submit();setTimeout(function (){ obj.removeAttribute('disabled');},'5000');">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
		</s:form>
	</body>
</html>
