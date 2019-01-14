<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>发行机构添加</title>
		<%@ include file="/commons/meta.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
			.x-tree-node-icon {
				display: none;
			}
		</style>
		<script type="text/javascript" >
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
            function addInvoiceCompany(){
            	var faceValue=window.showModalDialog('${ctx}/invoiceCompanyList.action?id=${issuerDTO.entityId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(faceValue!=null){
					document.getElementById('invoiceCompanyId').value=faceValue;
					document.getElementById('companyForm').action='invoiceCompanyAdd.action';
					document.getElementById('companyForm').submit();
				}
             }
           function deleteInvoiceCompany(){
        		var n=0;
				var checkbox=document.getElementsByName("chooseId");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					errorDisplay('请选择要删除的对象');
				}	
				if(n>0){
				confirm("确定删除吗？",operdelCompany);
				}
			}
			function operdelCompany(){
				document.companyForm.action='companyDel';
				document.companyForm.submit();
			}

			 function toAddressList(){
	            	var addressValue=window.showModalDialog('${ctx}/invoiceAddressList.action?id=${issuerDTO.entityId}', '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
					if(faceValue!=null){
						document.getElementById('addressId').value=addressValue;
						document.getElementById('addressForm').action='invoiceAddressAdd.action';
						document.getElementById('addressForm').submit();
					}
	             }
             
			 function addDetail(detailDivId, url, inqueryUrl) {
			        var customerId = document.getElementById('entityId').value;
			        url = url + '?customerId=' + customerId;
			        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
			        if (returnValue == 'success') {
			            maskDocAll();
			            document.getElementById('addressForm').action='reLoad.action';
						document.getElementById('addressForm').submit();
			        }
			    }
			function deleteBank(){
            	var n=0;
				var checkbox=document.getElementsByName("bankIdList");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					errorDisplay('请选择要删除的对象');
				}	
				if(n>0){
					confirm("确定删除吗？",delBank);
				}
			}
			function delBank(){
				document.bankForm.action='${ctx}/bankDel.action';
				document.bankForm.submit();
			
			}
			
            function deleteAddress(){
            	var n=0;
				var checkbox=document.getElementsByName("invoiceAddressIdList");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					errorDisplay('请选择要删除的对象');
				}	
				if(n>0){
					confirm("确定删除吗？",delIssuerAddress);
				}
			}
			function delIssuerAddress(){
				document.addressForm.action='addressDelete';
				document.addressForm.submit();
			}

			function addDetail1(detailDivId, url, inqueryUrl) {
		        var customerId = document.getElementById('entityId').value;
		        url = url + '?customerId=' + customerId;
		        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		        if (returnValue == 'success') {
		            maskDocAll();
		            document.getElementById('contractForm').action='reLoad.action';
					document.getElementById('contractForm').submit();
		        }
		    }  

            function deleteContact(){
            	var n=0;
				var checkbox=document.getElementsByName("contactIdList");
				for(i=0;i<checkbox.length;i++){
					if(checkbox[i].checked==true){
						n++;
					}
				}
				if(n==0){
					errorDisplay('请选择要删除的对象');
				}	
				if(n>0){
				confirm("确定删除吗？",operdelAddress);
				}
			}
			
			function operdelAddress(){
				document.contractForm.action='contractDel';
				document.contractForm.submit();
			}
			    
			function addDetail2(detailDivId, url, inqueryUrl) {
		        var customerId = document.getElementById('entityId').value;
		        url = url + '?customerId=' + customerId;
		        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		        if (returnValue == 'success') {
		            maskDocAll();
		            document.getElementById('contractForm').action='reLoad.action';
					document.getElementById('contractForm').submit();
		        }
		    }  
			
			function addDetail4(detailDivId, url, inqueryUrl) {
		        var customerId = document.getElementById('entityId').value;
		        url = url + '?customerId=' + customerId;
		        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
		        if (returnValue == 'success') {
		            maskDocAll();
		            document.getElementById('bankForm').action='reLoad.action';
					document.getElementById('bankForm').submit();
		        }
		    }  
			
           function deleteDepart(){
           	var n=0;
			var checkbox=document.getElementsByName("departmentIdList");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要删除的对象');
			}	
			if(n>0){
			confirm("确定删除吗？",operdelDepart);
			}
		}
		
		function operdelDepart(){
			document.departForm.action='departMentDel';
			document.departForm.submit();
		}

		function addDetail3(detailDivId, url, inqueryUrl) {
	        var customerId = document.getElementById('entityId').value;
	        url = url + '?customerId=' + customerId;
	        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
	        if (returnValue == 'success') {
	            maskDocAll();
	            document.getElementById('deliveryPointForm').action='reLoad.action';
				document.getElementById('deliveryPointForm').submit();
	        }
	    }  
		
		 function deliveryPoint(){
           	var n=0;
			var checkbox=document.getElementsByName("deliveryPointIdList");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				errorDisplay('请选择要删除的对象');
			}	
			if(n>0){
			confirm("确定删除吗？",operdelDelivery);
			}
		}
		
		function operdelDelivery(){
			document.deliveryPointForm.action='deliveryPointDel';
			document.deliveryPointForm.submit();
		}


		function update(){
			var n=0;
			var checkbox=document.getElementsByName("deliveryPointIdList");
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
				var acctypeValue=window.showModalDialog('${ctx}/deliveryInfoEdit.action?deliveryId='+id+'&entityId='+document.getElementById('entityId').value, '_ blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
				if(acctypeValue == 'success'){
					document.getElementById("newForm").action="reLoad.action";
		 			document.getElementById('newForm').submit();
		 		}
			}
			
		}

		function addCardBin(){
			var cardPin=window.showModalDialog('${ctx}/addCardBin.action?issuerDTO.entityId=${issuerDTO.entityId}&issuerDTO.issuerCode=${issuerDTO.issuerCode}', '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
			if(cardPin == 'sucess'){
				document.getElementById("newForm").action="reLoad.action";
	 			document.getElementById('newForm').submit();
	 		}
		}

		function back(){
			document.getElementById("newForm").action="listIssuer.action";
			document.getElementById("newForm").submit(); 
        }
        function updateParameter(code){
            var id=document.getElementById("entityId").value;
            var systemparameter=window.showModalDialog('${ctx}/loadKey.action?entitySystemParameterDTO.parameterCode='+code+"&entitySystemParameterDTO.entityId="+id, '_ blank', 'center:yes;dialogHeight:600px;dialogWidth:800px;resizable:no');
            if(systemparameter=='success'){
            	document.getElementById("newForm").action="reLoad.action";
	 			document.getElementById('newForm').submit();
            }
        }
        
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
					document.getElementById("newForm").action="reLoad.action";
		 			document.getElementById('newForm').submit();
		 		}
			}
			
		}
        
        
        
		  
		  </script>
		 <script type="text/javascript" charset="UTF-8">
		//Ext.BLANK_IMAGE_URL = 'widgets/ext/resources/images/default/s.gif';
  		var menuTree;
  		Ext.onReady(function(){
			var menuList=${menuList};
			var nmenuList=${nmenuList};
			var nodeObject = new Object();
			var n=0;
  			for(var i=0;i<menuList.length;i++){
  				if(parseInt(menuList[i]['fatherResourceId'])==0){
  					nodeObject[menuList[i]['resourceId']]=new Ext.tree.TreeNode({
            	    		id:menuList[i]['resourceId'],
            	    		text:menuList[i]['resourceName'],
            	    		checked:false,
            	    		listeners:{
          						"checkchange":function(node){//选中是否切换激发事件
                				 	mytoggleChecked(node);//自定义级联选择函数
                				 	//checkedParentNodes(node);
          						 },
       						expanded:true
       						}
            	    		
            	    });
  				}else{
  					switch (parseInt(menuList[i]['resourceType'])) {
            	   		case 1:
            	   	 		
  						case 2:
  						
							nodeObject[menuList[i]['resourceId']]=new Ext.tree.TreeNode({
            	   	 			id:menuList[i]['resourceId'],
            	   	 			text:menuList[i]['resourceName'],
            	   	 			checked:false,
            	   	 			listeners:{
          						"checkchange":function(node){//选中是否切换激发事件
                				 	mytoggleChecked(node);//自定义级联选择函数
                				 	//checkedParentNodes(node);
          						 },
       						expanded:true
       						}
       						});
       						nodeObject[menuList[i]['fatherResourceId']].appendChild(nodeObject[menuList[i]['resourceId']]);
  							break;
  						case 3:
  							nodeObject[menuList[i]['resourceId']]=new Ext.tree.TreeNode({
            	   	 			id:menuList[i]['resourceId'],
            	   	 			text:menuList[i]['resourceName'],
            	   	 			checked:false,
            	   	 			leaf:true,
            	   	 			listeners:{
          						"checkchange":function(node){//选中是否切换激发事件
                				 	
                				 	//checkedParentNodes(node);
          						 },
       							expanded:true
       							}
            	   	 			});
  								nodeObject[menuList[i]['fatherResourceId']].appendChild(nodeObject[menuList[i]['resourceId']]);
  					}
  				}
  					
  				for(var j=0;j<nmenuList.length;j++){
  					if(nodeObject[menuList[i]['resourceId']].id==nmenuList[j]['resourceId']){
  						
  						nodeObject[menuList[i]['resourceId']].attributes.checked=true;
  					}
  				}
  				
  			}
  		
  		
      //生成树形面板
      menuTree = new Ext.tree.TreePanel({
//      				id:'mainTree',
                         split : true,

                         autoScroll : true,
                         collapsible : true,
                         collapseFirst : false,
                         title: '商业卡管理平台',
                         width:220,
                         root : nodeObject[menuList[0]['resourceId']]
                         
                        });
     menuTree.render('tree');
     menuTree.animate = false;
     menuTree.expandAll();
     menuTree.animate = true; 
    function mytoggleChecked(node)
    {
        //迭代复选=>父节点影响子节点选择,子节点不影响父节点
        if(node.hasChildNodes())
        {
           var tree=node.getOwnerTree();
           tree.suspendEvents();
           //eachChild(fn),遍历函数
           node.eachChild(function(child){
                     child.getUI().toggleCheck(node.attributes.checked);   
                     mytoggleChecked(child);
           })
           tree.resumeEvents();
        }
    }
  });
		var parentids=new Array();
		 function checkedParentNodes(node){
	//取得本节点的所有父节点,父节点中包括其自己
		var tree=node.getOwnerTree();
        tree.suspendEvents();
        var allParentNodes = getAllParentNodes(node);
        if (allParentNodes.length > 1) {
            for ( var i = 0; i < allParentNodes.length; i++) {
                if (allParentNodes[i].id != node.id) {
                    if (!allParentNodes[i].getUI().isChecked()) {
                        if(parentids.indexOf(allParentNodes[i].id)<0){
                        	parentids.push(allParentNodes[i].id);
                        }
                    }
                }
            }
        }
        tree.resumeEvents();
	}
	function getAllParentNodes(node){
		
		var parentNodes=[];
		parentNodes.push(node);
		if(node.parentNode){
			parentNodes = parentNodes.concat(getAllParentNodes(node.parentNode));
		}
		return parentNodes;
	}
	
	function sub(){
		var a=menuTree.getChecked();
			for(var i=0;i<a.length;i++){
				
					checkedParentNodes(a[i]);
				
			}
			var b=menuTree.getChecked();
            var checkid=new Array();//存放选中id的数组
            for(var i=0;i<b.length;i++)
            {  
                checkid.push(b[i].id);//添加id到数组
            }
            if(checkid!=null){
            	document.getElementById('resourceIds').value=checkid.toString()+','+parentids.toString();
            }
            newForm.submit();
	}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>编辑发行机构</span>
		</div>
		<s:form id="newForm" name="newForm" action="updateIssuer" method="post">
		<s:hidden name="issuerDTO.resourceIds" id="resourceIds"/>
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
									<span class="TableTop">机构信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						
							<div id="serviceTable">
								
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														发行机构ID号：
													</td>
													<td>
														<s:textfield name="issuerDTO.entityId" id="entityId"
															readonly="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>发行机构名称：

													</td>
													<td>
														<s:textfield name="issuerDTO.issuerName" readonly="true"></s:textfield>
														<br />
														<s:fielderror>
															<s:param> 
																issuerDTO.issuerName
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
													<td style="width: 120px; text-align: right;">
														发行机构英文名称：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerEnglishName"
															readonly="true" />
														<br />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerEnglishName
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 110px; text-align: right;">--%>
<%--														<span class="no-empty">*</span>发行机构编号：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														<s:textfield name="issuerDTO.issuerCode"  readonly="true"/>--%>
<%--														<s:fielderror>--%>
<%--															<s:param>--%>
<%--																issuerDTO.issuerCode--%>
<%--															</s:param>--%>
<%--														</s:fielderror>--%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>发行机构电话：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerTelephone"
															/>
														<s:fielderror>
															<s:param>
																issuerDTO.issuerTelephone
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
														<span class="no-empty">*</span>发行机构传真：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerFax"  />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerFax
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
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>发行机构地址：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerAddress" />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerAddress
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
														发行机构英文地址：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerEnglishAddress" />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerEnglishAddress
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
													<td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>发行机构邮编：
													</td>
													<td>
														<s:textfield name="issuerDTO.issuerPostcode" />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerPostcode
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
														<span class="no-empty">*</span>发行机构状态：
													</td>
													<td>
														<s:select list="#{1:'有效',0:'无效'}"
															name="issuerDTO.dataState" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
				                        <td>
				                            <table style="text-align: left; width: 100%">
				                            	<tr>
				                                    <td style="width: 120px; text-align: right;">
														<span class="no-empty">*</span>用户名称：
													</td>
													<td>
														<s:textfield id="userName" name="issuerDTO.userName" readonly="true"/>
														<s:fielderror>
															<s:param>
																issuerDTO.userName
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
														<span class="no-empty">*</span>用户邮箱：
													</td>
													<td>
														<s:textfield id="issuerDTO.userEmail" name="issuerDTO.userEmail"/>
														<s:fielderror>
															<s:param>
																issuerDTO.userEmail
															</s:param>
														</s:fielderror>
													</td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
									<tr>
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 120px; text-align: right;">
														描述信息：
													</td>
													<td>
														<s:textarea name="issuerDTO.issuerComment" cols="70"
															rows="5" />
														<s:fielderror>
															<s:param>
																issuerDTO.issuerComment
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>

									</tr>
									
								</table>

							</div>
					</td>
					<td valign="top">
						<div id="tree">
					</td>
				</tr>
				<s:hidden name="issuerDTO.userId"></s:hidden>
			</table>
		</div>

		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="back();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="maskDocAll();sub();">
				提交
			</button>
			<div style="clear: both"></div>
		</div>
		</s:form>
		
				
		
		<div id="bankTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('bankTable')">
						<span class="TableTop">银行信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		 <s:form id="bankForm" name="bankForm" action="" method="post">
		 <s:hidden name="issuerDTO.entityId" />
		<div id="bankTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="issuerDTO.bankDTOList" var="map" width="100%"
				action="${ctx}/bankManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" form ="bankForm"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="bankIdList" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="bankIdList"
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
										onclick="addDetail4('bank', '${ctx}/bankAdd.action', '');"
										value="添加" />
								</td>
								<!-- --><td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('bankIdList','${ctx}/bankEdit.action');"
										value="编辑" />
								</td> <!-- -->
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deleteBank();"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	<div id="invoiceCompanyTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer">
						<span class="TableTop">卡BIN信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
	</div>
	<s:form id="cardPinForm" name="cardPinForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<div id="invoiceCompanyTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
             <ec:table tableId="cardSerialtable" items="issuerDTO.cardSerialNumberDTOList"
				var="map" width="100%" form="EditForm"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" showPagination="false"
				sortable="false">
				<ec:row>
					<ec:column property="cardBin" title="卡BIN" width="50%"></ec:column>
					<ec:column property="serialNumber" title="当前流水" width="50%"></ec:column>
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<input type="button" class="btn"
										style="width: 100px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
										onclick="addCardBin()" value="添加卡BIN信息" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		<s:form id="departForm" name="departForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<div id="departmentTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('departmentTable')">
						<span class="TableTop">部门信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div id="departmentTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="issuerDTO.departmentDTO" var="map" width="100%"
				action="${ctx}/departmentManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="department"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="departmentIdList" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="departmentIdList"
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
										onclick="addDetail2('department', '${ctx}/departmentInsertInit.action', '')"
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('departmentIdList', '${ctx}/toDepartmentEdit.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deleteDepart();"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	   <s:form id="contractForm" name="contractForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<div id="contactTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('contactTable')">
						<span class="TableTop">联系人信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div id="contactTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="issuerDTO.contractDTO" var="map" width="100%"
				action="${ctx}/contactManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="contact"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="contactIdList" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="contactIdList"
							value="${map.contactId}" />
					</ec:column>
					<ec:column property="contactName" title="联系人姓名" width="30%">
						<s:property value="#attr['map'].contactName" />
						<s:property value="#attr['map'].defaultFlag == 1 ? ' (默认)' : ''" />
					</ec:column>
					<ec:column property="contactFunction" title="联系人职位" width="20%" />
					<ec:column property="contactMobilePhone" title="联系人电话" width="20%" />
					<ec:column property="validFlag" title="有效状态" width="20%">
						<s:if test="#attr['map'].validityFlag == 1">有效</s:if>
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
										onclick="addDetail1('contact', '${ctx}/contractList.action', '')"
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('contactIdList', '${ctx}/toContactEdit.action')"
										value="编辑" />
							</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deleteContact();"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</s:form>	
	  <s:form id="deliveryPointForm" name="deliveryPointForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<div id="deliveryPointTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront" style="cursor: pointer"
						onclick="showOrHideDiv('deliveryPointTable')">
						<span class="TableTop">快递点信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>

		<div id="deliveryPointTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="issuerDTO.deliveryPointDTO" var="map" width="100%"
				action="${ctx}/deliveryPointManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="deliveryPoint"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="deliveryPointIdList" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="deliveryPointIdList"
							value="${map.deliveryPointId}" />
					</ec:column>
					<ec:column property="deliveryName" title="快递点名称" width="40%">
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
										onclick="addDetail3('deliveryPoint', '${ctx}/deliveryPointInit.action', '')"
										value="添加" ${disabled}/>
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="update();"
										value="编辑" ${disabled}/>
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deliveryPoint();"
										value="删除" ${disabled}/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		<div id="invoiceAddressTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFrontBig" style="cursor: pointer"
						onclick="showOrHideDiv('invoiceAddressTable')">
						<span class="TableTop">发票地址信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	
		<s:form id="addressForm" name="addressForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<s:hidden id="addressId" name="addressId" />
		<div id="invoiceAddressTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<ec:table items="issuerDTO.invoiceAddressDTO" var="map" width="100%"
				action="${ctx}/invoiceAddressManagement"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" tableId="invoiceAddress"
				showPagination="false" sortable="false">
				<ec:row>
					<ec:column property="null" alias="invoiceAddressIdList" title="选择"
						width="10%" sortable="false" headerCell="selectAll"
						viewsAllowed="html">
						<input type="checkbox" name="invoiceAddressIdList"
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
										onclick="addDetail('invoiceAddress', '${ctx}/invoiceAddressList.action', '${ctx}/reLoad.action')" 
										value="添加" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('invoiceAddressIdList', '${ctx}/toInvoiceAddressEdit.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deleteAddress();"
										value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div id="invoiceCompanyTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFrontBig" style="cursor: pointer"
						onclick="showOrHideDiv('invoiceCompanyTable')">
						<span class="TableTop">发票公司信息</span>
					</td>
					<td class="TableTitleEnd">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</s:form>	
	<s:form id="companyForm" name="companyForm" action="" method="post">
		<s:hidden name="issuerDTO.entityId" />
		<s:hidden id="invoiceCompanyId" name="invoiceCompanyId" />
		<div id="invoiceCompanyTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
             <ec:table tableId="acctype" items="issuerDTO.invoiceCompanyDTO"
				var="map" width="100%" form="EditForm"
				action="${ctx}/issuer!inquery.action"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				autoIncludeParameters="false" showPagination="false"
				sortable="false">
				<ec:row>
				   <ec:column property="null" alias="chooseId" title="选择"
					   width="10%" sortable="false" headerCell="selectAll">
					    <input type="checkbox" name="chooseId" value="${map.invoiceCompanyId}" />
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
										onclick="addInvoiceCompany()" value="添加" />
								</td>
								
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
										onclick="updateInfo('chooseId', '${ctx}/toInvoiceCompanyEdit.action')"
										value="编辑" />
								</td>
								<td>
									<input type="button" class="btn"
										style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
										onclick="deleteInvoiceCompany()" value="删除" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	</body>
	
</html>
