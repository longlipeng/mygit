<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>添加收单机构</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
			.x-tree-node-icon {
				display: none;
			}
		</style>

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



	function back(data) {
		if (data.indexOf("在") != -1) {
			document.getElementById("consumerDTO.websiteUserName").value = "";
		}
		document.getElementById("message").innerHTML = data;
	}

	function display() {
		var selectValue = document.getElementById("channelId").value;
		if (selectValue == '') {
			document.getElementById("time").style.visibility = "hidden";

		} else {
			document.getElementById("time").style.visibility = "";
		}
	}
	function checkUserName(userName){
        if(IsSpace(userName)){
        	document.getElementById("message").innerHTML="请输入用户名！";
			//errorDisplay("请输入用户名！");
			return;
        }
	   	ajaxRemote('${ctx}/user/checkUserName.action',
	               {'userDTO.userName': userName},
	               successFn,
	               'json'
	    )
    }
    function successFn(userDTO){
		if(userDTO!=null){
			document.getElementById("spanmessage").innerHTML="用户名"+userDTO['userName']+"已存在！";
			document.getElementById("userName").value='';
		}else{
			document.getElementById("spanmessage").innerHTML="用户名可用！";
		}
    }
          function record(){
             var id=window.showModalDialog('${ctx}/consumer/record', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
              newForm.action='consumer/configEntiyId.action?consumerQueryDTO.entityId='+id;      
              newForm.submit();
          }
     function addConsumer(){
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
     	 newForm.action='consumer/insert.action';
     	 newForm.submit();
     }
          
   </script>
   		<script type="text/javascript" charset="UTF-8">
		//Ext.BLANK_IMAGE_URL = 'widgets/ext/resources/images/default/s.gif';
  		var menuTree;
  		Ext.onReady(function(){
			var menuList=${menuList};
			var nodeObject = new Object();
	
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

     
     
/*   var treeNodes = $(document.getElementById('mainTree')).find('a');
     treeNodes.each(function(){
         var id = $(this).parent().attr('ext:tree-node-id');
         var checkbox=$('<input type="checkbox" name="checkb" value="' + id + '" />');
         checkbox.bind('click',function(event){
         	event.stopPropagation();
         })
         $(this).before(checkbox);
     });*/
/*    new Ext.Button({
         text:"选中id",
         handler:function(){
            var b=menuTree.getChecked();
            var checkid=new Array;//存放选中id的数组
            for(var i=0;i<b.length;i++)
            {  
                 checkid.push(b[i].id);//添加id到数组
            }
            document.getElementById('resourceIds').value=checkid.toString();//checkid.toString()这个结果,我们可以传到服务器,想怎么处理就怎么处理
         }
     }).render('button');//.render(document.body,"btn");
*/    
    

     
    
    
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
  
/*	function loadissuerGroup(){
			issuerGroupSelect();
		}
	function issuerGroupSelect(){
			var issuerGroupId=document.getElementById("issuerGroup").value;
			var issuerSelect=document.getElementById('issuer');
			if(issuerGroupId!=""){
				ajaxRemote('${ctx}/issuerGroupselectAjax.action',
                    'id='+issuerGroupId,
                    function(issuerDTOs) {
                        var issuers = issuerDTOs;
                        issuerSelect.innerHTML='';
                        var opt = document.createElement('option');
          				opt.value = '0';
            			opt.innerHTML = "----";
            			for(var i=0;i<issuers.length;i++){
            				var issuer=issuers[i];
            				opt=document.createElement('option');
            				opt.value=issuer['issuerId'];
            				opt.innerHTML=issuer['issuerName'];
            				
            				issuerSelect.appendChild(opt);
            			}
            			issuerSelect.disabled = false;
                    },
                    'json');
			}else{
				issuerSelect.innerHTML='';
				issuerSelect.disabled=true;
			}
			
		}*/
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
		</script>
   </head>
   
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增收单机构</span>
		</div>
		<s:form id="newForm" name="newForm"
		  action="consumer/insert">
		  <s:hidden name="consumerDTO.resourceIds" id="resourceIds"/>
		  <div id="ContainBox">
		  <table width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
				<td width="80%" valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('serviceTable');"
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
													<td style="width: 110px; text-align: right;">
														收单机构号：
													</td>
													<td>
                                                      <s:textfield name="consumerDTO.entityId" readonly="true"></s:textfield>
                                                   <!--    <button class='bt' type="button" style="margin: 5px"
				                                              onclick="record();">
				                                                                                                                       录入
			                                            </button>
			                                             -->		
														<s:fielderror>
															<s:param>
                                                              consumerDTO.entityId
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
                                                      <span class="no-empty">*</span>收单机构名称：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerName"
															id="consumerName" maxlength="128"></s:textfield>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerName
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
														收单机构代码：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerCode" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerCode
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
														收单机构英文名称：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerEnglishName" id="consumerDTO.consumerEnglishName" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerEnglishName
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
														<span class="no-empty">*</span>收单机构地址：
													</td>
													<td>
										             	<s:textfield name="consumerDTO.consumerAddress" maxlength="128"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerAddress
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
														收单机构英文地址：
													</td>
													<td>
													   <s:textfield name="consumerDTO.consumerEnglishAddress" maxlength="128"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerEnglishAddress
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
														<span class="no-empty">*</span>收单机构邮编：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerPostcode" maxlength="128"/ >
														<s:fielderror>
															<s:param>
																consumerDTO.consumerPostcode
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
														<span class="no-empty">*</span>收单机构电话：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerTelephone" maxlength="11"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerTelephone
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
														<span class="no-empty">*</span>收单机构传真：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerFax" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerFax
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
														<span class="no-empty"></span>收单机构网站：
													</td>
													<td>
										               <s:textfield name="consumerDTO.consumerWebsite" maxlength="128"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerWebsite
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
														<span class="no-empty"></span>收单机构规模：
													</td>
													<td>
														<s:textfield name="consumerDTO.consumerSize" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.consumerSize
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
														外部系统代码：
													</td>
													<td>
														<s:textfield name="consumerDTO.externalId" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.externalId
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
														<span class="no-empty"></span>旧系统商户标识：
													</td>
													<td>
														<s:textfield name="consumerDTO.legacyMerchantId" maxlength="128"/>
														<s:fielderror>
															<s:param>
																consumerDTO.legacyMerchantId
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
														发票名称：
													</td>
													<td>
										               <s:textfield name="consumerDTO.invoiceName" maxlength="128"/>
                                                       <s:fielderror>
															<s:param>
																consumerDTO.invoiceName
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 110px; text-align: right;">--%>
<%--														<span class="no-empty"></span>结算代理：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--														<edl:entityDictList displayName="consumerDTO.settAgencyId" dictValue="${consumerDTO.settAgencyId}" dictType="422" tagType="2" defaultOption="false" />--%>
<%--														<s:fielderror>--%>
<%--															<s:param>--%>
<%--																consumerDTO.settAgencyId--%>
<%--															</s:param>--%>
<%--														</s:fielderror> --%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
<%--										<td>--%>
<%--											<table style="text-align: left; width: 100%">--%>
<%--												<tr>--%>
<%--													<td style="width: 110px; text-align: right;">--%>
<%--														<span class="no-empty">*</span>销售代表：--%>
<%--													</td>--%>
<%--													<td>--%>
<%--													  <s:select list="salesmanList" name="consumerDTO.salesmanId" listKey="userId" listValue="userName" headerKey="" headerValue="--请选择--"></s:select> --%>
<%--                                                       <s:fielderror>--%>
<%--															<s:param>--%>
<%--																consumerDTO.salesmanId--%>
<%--															</s:param>--%>
<%--														</s:fielderror> --%>
<%--													</td>--%>
<%--												</tr>--%>
<%--											</table>--%>
<%--										</td>--%>
									</tr>
									
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>加盟日期：
													</td>
													<td>
										              <s:textfield name="consumerDTO.joinDate" onclick="dateClick(this)" cssClass="Wdate" readonly="true" cssStyle="cursor:pointer">
										                <s:param name="value"><s:date name="consumerDTO.joinDate" format="yyyy-MM-dd" /></s:param>
										              </s:textfield>
										            </td>
										             <td>														
                                                       <s:fielderror>
															<s:param>
														consumerDTO.joinDate
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
														<span class="no-empty"></span>收单机构状态：
													</td>
													<td>
	    	             	                          <s:select 
                                                         list="#{'1':'有效','0':'无效'}" 
                                                         name="consumerDTO.consumerState" 
                                                         emptyOption="false"
                                                         label="收单机构状态"
                                                       />											
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerState
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
														<span class="no-empty">*</span>销售代表：
													</td>
													<td>
													  <s:select list="salesmanList" name="consumerDTO.salesmanId" listKey="userId" listValue="userName" headerKey="" headerValue="--请选择--"></s:select> 
                                                       <s:fielderror>
															<s:param>
																consumerDTO.salesmanId
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
														<span class="no-empty"></span>收单机构类型：
													</td>
													<td>
										               <edl:entityDictList displayName="consumerDTO.consumerType" dictValue="${consumerDTO.consumerType}" dictType="181" tagType="2" defaultOption="false" />
                                                       <s:fielderror>
															<s:param>
																consumerDTO.consumerType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>								
							     </table>
							     
							</div>
									
									 <table style="text-align: left; width: 100%"> 
									 <tr>
									
									        <td>
					                            <table style="text-align: left; width: 100%">
					                                    <tr>
					                                    <td style="width: 110px; text-align: right;">
					                                    	<span class="no-empty">*</span>用户名称：
					                                    </td>
					                                    <td>
					                                    		<s:textfield id="userName" name="consumerDTO.userName" maxlength="128" onblur="checkUserName(this.value)"></s:textfield>
					                                       	 	<span class="errorMessage" id="spanmessage" />
					                                       	 	<s:fielderror>
						                                            <s:param>consumerDTO.userName</s:param>
						                                        </s:fielderror>
					                                    </td>
					                                </tr>
					                            </table>
					                        </td>
					                        <td>
					                            <table style="text-align: left; width: 100%">
					                                <tr>
					                                    <td style="width: 110px; text-align: right;">
					                                    	<span class="no-empty">*</span>用户邮箱：
					                                    </td>
					                                    <td>
					                                       	<s:textfield id="consumerDTO.userEmail" name="consumerDTO.userEmail" maxlength="128"/>
															<s:fielderror>
																<s:param>
																	consumerDTO.userEmail
																</s:param>
															</s:fielderror>		
					                                    </td>
					                                </tr>
					                            </table>
					                        </td>
									
									 </tr>
									 </table>
									 <table style="text-align: left; width: 100%"> 
									 <tr>
									 <td>
					                            <table style="text-align: left; width: 100%">
					                                    <tr>
					                                    <td style="width: 110px; text-align: right;">
					                                    	<span class="no-empty">*</span>用户密码：
					                                    </td>
					                                    <td>
					                                    	<s:password name="password" maxlength="128"/>
					                                        <s:fielderror>
					                                            <s:param>password</s:param>
					                                        </s:fielderror>
					                                    </td>
					                                </tr>
					                            </table>
					                        </td>
					                        <td>
					                            <table style="text-align: left; width: 100%">
					                                <tr>
					                                    <td style="width: 110px; text-align: right;">
					                                    	<span class="no-empty">*</span>确认密码：
					                                    </td>
					                                    <td>
					                                       	<s:password id="repassword" name="repassword" maxlength="128"/>
															<s:fielderror>
																<s:param>
																	repassword
																</s:param>
															</s:fielderror>		
					                                    </td>
					                                </tr>
					                            </table>
					                        </td>
									 </tr>
									 </table>
									
							</td>
							</tr>  
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('paraTable');"
									style="cursor: pointer;">
									<span class="TableTop">参数设定</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="paraTable">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>结算手续费修改标志：
													</td>
													<td>													
														<s:radio name="consumerDTO.commissionFee" id="consumerDTO.commissionFee" list="#{1:'是',0:'否'}" ></s:radio> 		
														<s:fielderror>
														<s:param>
																consumerDTO.commissionFee
														</s:param>
														</s:fielderror>																																
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>直接结算而无需核对结算单：
													</td>
													<td>
													
													    <s:radio name="consumerDTO.reimburseWithoutCheck" id="consumerDTO.reimburseWithoutCheck" list="#{1:'是',0:'否'}" ></s:radio> 
													    <s:fielderror>
														<s:param>
																consumerDTO.reimburseWithoutCheck
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
													<td style="width: 170px; text-align: right;">
                                                      <span class="no-empty">*</span>收单机构消费暂停标志：
													</td>
													<td>											
													    <s:radio name="consumerDTO.purchasePause" id="consumerDTO.purchasePause" list="#{1:'是',0:'否'}" ></s:radio>
													    <s:fielderror>
														<s:param>
																consumerDTO.purchasePause
														</s:param>
														</s:fielderror>	
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 170px; text-align: right;">
														<span class="no-empty">*</span>收单机构结算暂停标志：
													</td>
													<td>
													    <s:radio name="consumerDTO.reimbursePause" id="consumerDTO.reimbursePause" list="#{1:'是',0:'否'}" ></s:radio>
													    <s:fielderror>
														<s:param>
																consumerDTO.reimbursePause
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
													<td style="width: 170px; text-align: right;">
														<span class="no-empty"></span>付款方式标志：
													</td>
													<td>
                                                      <edl:entityDictList displayName="consumerDTO.reimbursementType" dictValue="${consumerDTO.reimbursementType}" dictType="106" tagType="2" defaultOption="false" />
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
				    </td>
				    <td valign="top" width="20%">
				        <div id="tree"></div>
				    </td>
				    </tr>
				    </table>
       </div>
	    </s:form>

		<div id="btnDiv" style="text-align: right; width: 100%">
		 	<input type="button" class="bt" style="margin: 5px" onclick="this.disabled='disabled';addConsumer();" value="提 交"/>
			<input type="button" class="bt" style="margin: 5px" 
				onclick="submitForm('newForm', '${ctx}/consumer/inquery.action')" value="返 回"/>
			
			<div style="clear: both"></div>
		</div>
	</body>
</html>