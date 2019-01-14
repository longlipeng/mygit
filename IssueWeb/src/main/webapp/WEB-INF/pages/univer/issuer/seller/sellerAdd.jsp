<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加营销机构信息</title>
<%@ include file="/commons/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<style type="text/css">
			.x-tree-node-icon {
				display: none;
			}
		</style>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		
<script type="text/javascript">
    function choiceCustomer() {
        var sellerDTO = window.showModalDialog('customerManagement/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (sellerDTO != null) {
            document.getElementById('groupId').value = sellerDTO['customerId'];
        }
    }

    function changePaymentTerm() {
        var paymentTermSelectedId = document.getElementById('paymentTerm').value;

        // 选中无需实时支付时，可以设置延期天数
        if (paymentTermSelectedId == 4) {
            document.getElementById('paymentDelay').disabled = false;
        } else {
            document.getElementById('paymentDelay').value = '';
            document.getElementById('paymentDelay').disabled = true;
        }
    }

    function loadPage() {
        salesRegions = ${salesRegions};
        changeBusinessCity();
        changePaymentTerm();
    }

    function changeBusinessCity() {
        if (salesRegions != null) {
            var salesRegionSelectedId = document.getElementById('businessCity').value;
            var businessAreaData = salesRegions[salesRegionSelectedId];
            var businessAreaSelect = document.getElementById('businessArea');
            businessAreaSelect.innerHTML = '';
            for (var i = 0; i < businessAreaData.length; i++) {
                var businessArea = businessAreaData[i];
                var opt = document.createElement('option');
                opt.value = businessArea['dictCode'];
                opt.innerHTML = businessArea['dictShortName'];
                businessAreaSelect.appendChild(opt);
            }
            businessAreaSelect.disabled = false;
        }
    }

    function changeSaleMan(){
    	var issuerId=document.getElementById("issuerId").value;
    }
              var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('sellerBaseTable');
					isDisplay = false;
				} else {
					undisplay('sellerBaseTable');
					isDisplay = true;
				}
			} 
         
    /*function display(){
    	var selectValue=document.getElementById("channelId").value;
				if(selectValue==''){
					document.getElementById("time").style.visibility="hidden";
					
				}else{
					document.getElementById("time").style.visibility="";
				}
    }
*/
    function checkUserName(userName){
		if(isOutOfLength(userName)){
			document.getElementById("message").innerHTML="请输入64位以内的用户名！";
			return;
		}
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
    //用户名称长度校验
    function isOutOfLength(userName){
    	if(userName.length>64){
    		return true;
    	}
    	return false;
    }
    function IsSpace(userName){
    	if(userName==""){
    		return true;
    	}
    	return false;
    }
    function successFn(userDTO){
		if(userDTO!=null){
			document.getElementById("message").innerHTML="用户名"+userDTO['userName']+"已存在！";
			document.getElementById("userName").value='';
		}else{
			document.getElementById("message").innerHTML="用户名可用！";
		}
    }

    function submitAdd(){
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
		var userName =  document.getElementById("userName").value;
		document.sellerForm.action='${ctx}/issuerSeller/insert.action';
		document.sellerForm.submit();
   	}
   	 function record(){
             var id=window.showModalDialog('${ctx}/issuerSeller/record', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
              sellerForm.action='issuerSeller/configEntiyId.action?sellerQueryDTO.entityId='+id;      
              sellerForm.submit();
     }
     
     
     </script>
     
		<script type="text/javascript" charset="UTF-8">
		//Ext.BLANK_IMAGE_URL = 'widgets/ext/resources/images/default/s.gif';
  		var menuTree;
  		Ext.onReady(function(){
			var menuList=${menuList};
			var resourceId = document.getElementById('resourceIds').value;
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
  				if(resourceId !=""){
  					nmenuList = resourceId.split(',');
  	  				for ( var j = 0; j < nmenuList.length; j++) {
  						if (nodeObject[menuList[i]['resourceId']].id == nmenuList[j]) {

  							nodeObject[menuList[i]['resourceId']].attributes.checked = true;
  						}
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
	
	
	function openSellerPage() {
		var returnValue = window.showModalDialog('${ctx}/issuerSeller/toChooseSeller.action',
				'_blank',
				'center:yes;dialogHeight:500px;dialogWidth:400px;resizable:no');
		if (returnValue == null || returnValue == undefined)
			return;
		sellerForm.action = "${ctx}/issuerSeller/add.action?faEntityId="+returnValue;
		sellerForm.submit();
	}
	
	</script>
</head>
<body onload="loadPage()">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>添加营销机构信息</span>
    </div>
    <s:form id="sellerForm" name="sellerForm" action="customerManagement/insert.action" method="post">
    	<s:token></s:token>
        <s:hidden name="sellerDTO.functionRoleId" id="functionRoleId" value="2"/>
    	<s:hidden name="sellerDTO.resourceIds" id="resourceIds"/>
        <div id="sellerBase" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="sellerBaseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront" onclick="displayServiceTable();">
                            <span class="TableTop">机构信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="sellerBaseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            	<table width="100%" style="table-layout: fixed;">
            	<tr>
            	<td valign="top" width="72%">
                <table width="100%">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构号：</td>
                                    <td>
                                    	<s:textfield name="sellerDTO.entityId" maxLength="8" readonly="true"/>
                                    	<s:fielderror>
                                            <s:param>sellerDTO.entityId</s:param>
                                        </s:fielderror>
                                        <!--<span style="color:red" >请录入机构所属公司的公司代码(最长8位)</span>
                                     <button class='bt' type="button" style="margin: 5px"
				                                              onclick="record();">
				                                                                                                                       录入
			                                            </button>		
                                         -->	
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构名称：</td>
                                    <td>
                                    	<s:textfield name="sellerDTO.sellerName" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerName</s:param>
                                        </s:fielderror>
                                    <!--<span style="color:red" >请录入机构所属公司的公司名称</span>-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                        	<table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">英文名称：</td>
                                    <td><s:textfield name="sellerDTO.sellerEnglishName" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerEnglishName</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构地址：</td>
                                    <td><s:textfield name="sellerDTO.sellerAddress" />
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerAddress</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">英文地址：</td>
                                    <td><s:textfield name="sellerDTO.sellerEnglishAddress" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerEnglishAddress</s:param>
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
                                   <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>上级营销机构号:</td>
                                   <td>
                                       <s:textfield  name="sellerDTO.fatherEntityId" size="20" readonly="true" />
                                   	   <input type="button" value="选择营销机构" onclick="openSellerPage();"/>
                                       <s:fielderror>
                                          <s:param>sellerDTO.fatherEntityId</s:param>
                                       </s:fielderror>
                                  </td>
                              </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构传真：</td>
                                    <td>
                                    	<s:textfield name="sellerDTO.sellerFax" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerFax</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构邮编：</td>
                                    <td><s:textfield name="sellerDTO.sellerPostcode" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerPostcode</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构电话：</td>
                                    <td><s:textfield name="sellerDTO.sellerTelephone" maxlength="12"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerTelephone</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构规模：</td>
                                    <td><s:textfield name="sellerDTO.sellerSize" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerSize</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                  
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">营销机构网站：</td>
                                    <td><s:textfield name="sellerDTO.sellerWebsite" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerWebsite</s:param>
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
                                    <td style="width: 100px; text-align: right;">外部系统代码：</td>
                                    <td><s:textfield name="sellerDTO.externalId" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.externalId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">老系统客户号：</td>
                                    <td><s:textfield name="sellerDTO.legCusId" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.legCusId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%--
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">销售渠道：</td>
                                    <td>
                                        <s:select list="sellerDTO.channelList" listKey="channelId" listValue="channelName" name="sellerDTO.channelId" headerKey="" headerValue="无" onchange="display();" id="channelId"></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.channelId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td id="time">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">渠道开始时间：</td>
                                    <td>
                                    	<s:textfield name="sellerDTO.chanBegDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                        	<s:param name="value">
                                        			<s:date name="sellerDTO.chanBegDate" format="yyyy-MM-dd" />
                                        	</s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerDTO.chanBegDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    --%>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">卡片打印名称：</td>
                                    <td><s:textfield name="sellerDTO.printName" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.printName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">额外打印信息：</td>
                                    <td><s:textfield name="sellerDTO.extPrintInfo" maxlength="128"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.extPrintInfo</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    
                    <tr>
                        
<%--                        <td>--%>
<%--                            <table style="text-align: left; width: 100%">--%>
<%--                                <tr>--%>
<%--                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>失效时间：</td>--%>
<%--                                    <td>--%>
<%--                                    	<s:textfield name="sellerDTO.closeDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">--%>
<%--                                            <s:param name="value">--%>
<%--                                            	<s:date name="sellerDTO.closeDateDate" format="yyyy-MM-dd" />--%>
<%--                                            </s:param>--%>
<%--                                        </s:textfield>--%>
<%--                                        <s:fielderror>--%>
<%--                                            <s:param>sellerDTO.closeDateDate</s:param>--%>
<%--                                        </s:fielderror>--%>
<%--                                    </td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>销售区域：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.salesRegionId" dictValue="${sellerDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="false" />
                                        <s:fielderror>
                                            <s:param>sellerDTO.salesRegionId</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>缺省支付节点：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.paymentTerm" dictValue="${sellerDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="false" props="id=\"paymentTerm\" onchange=\"changePaymentTerm()\""/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.paymentTerm</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">延期天数：</td>
                                    <td><s:textfield name="sellerDTO.paymentDelay" id="paymentDelay" disabled="true"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.paymentDelay</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营业城市：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.businessCity" dictValue="${sellerDTO.businessCity}" dictType="405" tagType="2" defaultOption="false" props="id=\"businessCity\" onchange=\"changeBusinessCity()\""/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.businessCity</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营业区域：</td>
                                    <td>
                                        <select name="sellerDTO.businessAreaId"  id="businessArea"></select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.businessAreaId</s:param>
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
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>销售人员：</td>
                                    <td>
                                        <s:select list="salesmanList" name="sellerDTO.salesmanId" listKey="userId" listValue="userName" ></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.salesmanId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.activitySector" dictValue="${sellerDTO.activitySector}" dictType="400" tagType="2" defaultOption="false"/>
                                        <s:fielderror>
                                            <s:param>sellerDTO.activitySector</s:param>
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
                                    <td style="width: 100px; text-align: right;">营销机构状态：</td>
                                    <td>
                                    	<s:select list="#{'1':'有效', '0':'无效'}" name="sellerDTO.sellerState"></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerState</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>是否发送DM：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerDTO.hasDm"></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.hasDm</s:param>
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
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>用户名称：
                                    </td>
                                    <td>
                                    	<s:textfield id="userName" name="sellerDTO.userName" maxlength="128" onblur="checkUserName(this.value)"></s:textfield>
                                       	<span class="errorMessage" id="message" />
                                    	<s:fielderror>
                                            <s:param>sellerDTO.userName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>用户邮箱：
                                    </td>
                                    <td>
                                       	<s:textfield id="sellerDTO.userEmail" name="sellerDTO.userEmail"/>
										<s:fielderror>
											<s:param>
												sellerDTO.userEmail
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
                                    <td style="width: 100px; text-align: right;">
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
                                    <td style="width: 100px; text-align: right;">
                                    	<span class="no-empty">*</span>确认密码：
                                    </td>
                                    <td>
                                       	<s:password id="repassword" name="repassword"/>
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
                    <tr>
                    	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">注释信息：</td>
                                    <td>
                                    	<s:textarea name="sellerDTO.sellerComment" cols="60" rows="5" maxlength="256"></s:textarea>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerComment</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
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
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="this.disabled='disabled';submitAdd();" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/issuerSeller/inquery.action'" value="返 回"/>
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