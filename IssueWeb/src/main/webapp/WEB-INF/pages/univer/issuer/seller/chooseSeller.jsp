<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>上级营销机构查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/css/zTreeStyle/zTreeStyle.css" type="text/css">
	    <script type="text/javascript" src="${ctx}/js/jquery-1.4.4.min.js"></script>
	    <script type="text/javascript" src="${ctx}/js/jquery.ztree.core-3.5.js"></script>
	    <script type="text/javascript" src="${ctx}/js/jquery.ztree.excheck-3.5.js"></script>
		<base target="_self">
		<script type="text/javascript">
			var isDisplayTableBody = false;
			var isDisplayQueryBody = false;
			function displayTableBody() {
				if (isDisplayTableBody) {
					display('TableBody');
					isDisplayTableBody = false;
				} else {
					undisplay('TableBody');
					isDisplayTableBody = true;
				}
			}
			function displayQueryBody() {
				if (isDisplayQueryBody) {
					display('QueryBody');
					isDisplayQueryBody = false;
				} else {
					undisplay('QueryBody');
					isDisplayQueryBody = true;
				}
			}
			
			function submit() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				checkCount = zTree.getCheckedNodes(true).length;
				if(checkCount > 1) {
					errorDisplay("请选择一条记录！");
					return;
				}
			    nodes = zTree.getChangeCheckedNodes();
			    if(checkCount > 0) {
			    	if(nodes[0].level == 2) {
		    			errorDisplay("此机构为三级营销机构，不能添加！");
						return;
			    		
			    	}
			    	window.returnValue=nodes[0].id;
			    }
				window.close();	
			}
			
		    var setting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
	
		    var zNodes =${sellerList};
			
			$(document).ready(function(){
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.setting.check.chkboxType = { "Y":'', "N":''};
			});

			
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>营销机构</span>
		</div>
		<div id="ContainBox">
			<div>
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px"
				onclick="window.close()">
				关 闭
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="submit();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>