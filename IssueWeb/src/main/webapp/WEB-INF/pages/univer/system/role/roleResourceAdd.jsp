<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		
		<title>角色管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
			.x-tree-node-icon {
				display: none;
			}
		</style>
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
           });
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
            maskDocAll();
            newForm.submit();
		}
 </script>




	</head>
	<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
			<span>角色信息新增</span>
		</div>
	
	
	<div id="ContainBox">
			<s:form id="newForm" name="newForm"
								action="roleResource/roleAdd.action" method="post">
								<s:token></s:token>
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
									<span class="TableTop">角色信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							
								<s:hidden id="resourceIds" name="resourceIds" value=""/>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>角色编号：
													</td>
													<td>
														<s:textfield name="roleDTO.roleId" readonly="true"></s:textfield>
														
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>角色名称：
													</td>
													<td>
														<s:textfield name="roleDTO.roleName" maxlength="50"></s:textfield>
														<s:fielderror>
															<s:param>
																roleDTO.roleName
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
													<td style="width: 110px; text-align: right;">
														角色描述：
													</td>
													<td>
														<s:textarea name="roleDTO.roleComment" cols="70" rows="5"></s:textarea>
														<s:fielderror>
															<s:param>
																roleDTO.roleComment
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
						<div id="tree"></div>
						
					</td>
					</tr>
					<tr>
					<td>
					</td>
					
				</tr>
			</table>
			</s:form>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="window.location='roleResource/roleList.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="sub()">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>	
	</body>
</html>