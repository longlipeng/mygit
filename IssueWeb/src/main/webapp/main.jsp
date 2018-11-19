﻿<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html>
	<head>
		<title>商业卡管理平台</title>
		<%@ include file="/commons/meta.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
		<script type="text/javascript" charset="UTF-8">
		    Ext.BLANK_IMAGE_URL = 'widgets/ext/resources/images/default/s.gif';
		  
		    var menuList = ${jsonList};
		    
		    Ext.onReady(function(){
		    function successFn(result){
		    	if(result.sum>0){
		    		alert('有'+result.sum+'位客户的证件有效期需要处理，请前往:客户管理->证件有效期管理  处理！');
		    	}
		    }	    	
		    ajaxRemote('${ctx}/customer/judgeOutdateLience.action',{}, successFn,'json'); 
		    Ext.get('loading-mask').setOpacity(0.5,{duration:0.5,callback:function(){this.hide();}});  
			var nodeObject = new Array();
			var rootNode;
			if (menuList.length > 0) {
				for(var i=0;i<menuList.length;i++){
					if(parseInt(menuList[i]['fatherResourceId'])==0){
						nodeObject[menuList[i]['resourceId']]=new Ext.tree.TreeNode({
			        	    		id:menuList[i]['resourceId'],
			        	    		text:menuList[i]['resourceName']
			        	    });
			        	rootNode = nodeObject[menuList[i]['resourceId']];
					}else{
						switch (parseInt(menuList[i]['resourceType'])) {
			        	   	case 1:
							case 2: nodeObject[menuList[i]['resourceId']]=new Ext.tree.TreeNode({
				        	   	 			id:menuList[i]['resourceId'],
				        	   	 			text:menuList[i]['resourceName'],
				        	   	 			URL:menuList[i]['resourceUrl'],
				        	   	 			TYPE:menuList[i]['resourceType'],
				        	   	 			listeners:{
					      						"click":function(node){
					      							if (node.attributes.TYPE == 2) {
						            				 	maskDocAll();
									                    var Cmp = Ext.getDom('mainFrame');
									                    Cmp.src="${ctx}"+node.attributes.URL;
									                }
					      						 }
			   							}
			   						});
			   						nodeObject[menuList[i]['fatherResourceId']].appendChild(nodeObject[menuList[i]['resourceId']]);
									break;
						}
					}
				}
			} else {
				rootNode=new Ext.tree.TreeNode({
			        	    		id:-1,
			        	    		text:"没有可查看的列表"
			        	    		
			        	    });
			}
	        //生成树形面板
	        var menuTree = new Ext.tree.TreePanel({
	                         split : true,
	                         region : 'west',
	                         autoScroll : true,
	                         collapsible : true,
	                         collapseFirst : false,
	                         title: '功能菜单',
	                         width:220,
	                         root : rootNode
	                        });
		    var bodyContent = new Ext.Viewport({
			                    layout : 'border',
			                    items : [menuTree, new Ext.BoxComponent({
					                        region : 'north',
					                        height : 70,
					                        el : 'north'
					                    }), new Ext.BoxComponent({
					                        region : 'south',
					                        height : 20,
					                        el : 'south'
					                    }),{region:'center',
					                        deferredRender:false,
					                        activeTab:0,
					                        contentEl:'mainFrame',
					                        margins:'0 0 0 0',
					                        autoScroll:true,
					                        title:'管理界面'
					                        },menuTree],
			                    renderTo : Ext.getBody()
			                  });
		     menuTree.animate = false;
		     menuTree.expandAll();
		     menuTree.animate = true;
		     var rm=document.getElementById("resetMark").value;
		     if(rm!=null&&rm!=""){
		    	 if(rm=="1"){
		    		 errorDisplay("密码已重置，请修改密码！");
		    	 }
		     }
		  })
		 </script>
		<iframe src="" width="100%" height="100%" frameborder="0" scrolling="auto" name="mainFrame" id="mainFrame" onload="unmaskDocAll()"></iframe>
	</head>
	<body>
		<div id="loading-mask">
			<div id="loading">
				<div class="loading-indicator">
					<img src="${ctx}/images/extanim32.gif" width="32" height="32"
						style="margin-right: 8px; float: left; vertical-align: top;" />
					<br />
					<span id="loading-msg">Loading ...</span>
					<input type="hidden" name="resetMark" id="resetMark" value="${ resetMark}"  />
				</div>
			</div>
		</div>
		<div id="north">
			<div class=guide
				style="background: url(images/header.png) no-repeat; height: 60px">
				<div>
					<span class=s3>&nbsp;欢迎您:</span>
					<span class=s3 style="FONT-WEIGHT: bold"> <security:authentication
							property="name" /> </span>&nbsp; 当前时间：
					<span id="dateSpan">&nbsp;</span>&nbsp;|
					<a onclick="SetHome(this,window.location);" href="javascript:">设为首页</a>|
					<a onclick="AddFavorite(window.location,document.title)"
						href="javascript:">加入收藏</a>|
					<a href="#" onclick="Ext.getDom('mainFrame').src='instal.jsp'">设置外设</a>|
					<a href="${ctx}/j_spring_security_logout">重新登录</a> |
					<a href="javascript:saveLogout();">安全退出</a>|
					<a href="#" onclick="modify();">修改密码</a>
				</div>
				<br />
				
			</div>
		</div>
		<div id="south" align=center>
			<table width=100% border=0 style="filter: alpha(opacity =   0);">
				<tr>
					<td width=20%>
						<span id="timeconsume" style="position: relative"></span>
					</td>
					<td width=60% align=center>
						<span style="position: relative"> 版权所有&copy; 2016:上汽赛可预付费卡管理平台
							</span>
					</td>
					<td width=20%></td>
				</tr>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	<!--//
		setInterval("Refresh()",1000);
		window.onload = function() {
			document.getElementById('dateSpan').innerHTML=new Date().toLocaleString();
		}
		
		function Refresh() {
			document.getElementById("dateSpan").innerHTML=new Date().toLocaleString();
		}
		
		function AddFavorite(sURL, sTitle) {
	        try {
	            window.external.addFavorite(sURL, sTitle);
	        } catch (e) {
	            try {
	                window.sidebar.addPanel(sTitle, sURL, "");
	            } catch (e) {
	                alert("加入收藏失败，请使用Ctrl+D进行添加");
	            }
	        }
	    }
	    
    	function SetHome(obj,vrl) {
	        try {
	            obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl);
	        } catch(e){
	           if(window.netscape) {
                   try {
                       netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
                   } catch (e) { 
                       alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'"); 
                   }
                   var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                   prefs.setCharPref('browser.startup.homepage',vrl);
	            }
	        }
	    }
	    
	    function saveLogout() {
		    try {
		    	Ext.getDom('mainFrame').src="${ctx}/j_spring_security_logout";
		    	window.opener=null;
		    	window.open('','_self');
		    	window.close();
	    	} catch (e) {
	    	}
	    }

	    function changeIssuer() {
	        var issuerIds=document.getElementById("issuerSelect");
	        var	issuerId;
	            for(var i=0;i<issuerIds.length;i++){
	                  if(issuerIds[i].selected==true){
	                      issuerId=issuerIds[i].value;
	              }
	          }
	        ajaxRemote('mainMenu!changeIssuer.action',{'issuerId':issuerId},changeIssuerSuccess,'html');
	    }
	    
	    function changeIssuerSuccess() {
            var Cmp = Ext.getDom('mainFrame');
            if (Cmp.src.indexOf("mainMenu!initMenu.action") == -1) {
		    	maskDocAll();
	            Cmp.src=Cmp.src;
	        }
	    }
	    
	    function addBookmark(bookmarkurl,bookmarktitle) {
			//bookmarkurl="http://www.yagao.com";
			//bookmarktitle="";
			//window.external.AddFavorite(bookmarkurl,bookmarktitle);
		}
		function modify(){
			maskDocAll();
			var Cmp = Ext.getDom('mainFrame');
			Cmp.src="${ctx}/user/selfload.action";
		}
		
		function test(){
			maskDocAll();
			var Cmp = Ext.getDom('mainFrame');
			Cmp.src="${ctx}/initSystemLog.action";
		}
	//-->
	</script>
</html>