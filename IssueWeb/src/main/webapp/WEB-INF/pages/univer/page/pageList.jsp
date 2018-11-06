<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>产品管理</title>
		<%@ include file="/commons/meta.jsp"%>

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
		
		function state(){
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
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
				EditForm['ec_eti'].disabled=true;
				EditForm.action='producte!state.action';
				EditForm.submit();
			}
		}
		function edit(){
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
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
				EditForm['ec_eti'].disabled=true;
				document.EditForm.action='load.action';
				document.EditForm.submit();
			}
		}
		function del(){
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
				}
			}
			if(n==0){
				alert('请选择要删除的对象');
			}
			
			if(n>0){
				confirm("确定删除吗?",operation)
			}
		}
		
		function operation(){
				EditForm['ec_eti'].disabled=true;
				EditForm.action='productDelete.action';
				EditForm.submit();
		}
		
		
		
		
		function export1(){ 
	     var n=0;
		 var checkbox=document.getElementsByName("choose"); 
		for(i=0;i<checkbox.length;i++){ 		 	 	
		if(checkbox[i].checked==true){ n++; } } 
		if(n==0)
		{ 
			alert('请选择要导出的对象');
	 	} 
		if(n>0){ 
			EditForm['ec_eti'].disabled=true; 				
			EditForm.action='prodDownload.action'; 
			EditForm.submit(); 
		}
	}
	</script>
		<style type="text/css">
body,table,td,p,font,select {
	font-size: 9pt;
}

.bt {
	background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0
		0;
	border: 1px solid #7DE7FD;
	font-size: 9pt;
	height: 22px;
	cursor: pointer;
}

.bt:HOVER {
	background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll
		0 0;
	border: 1px solid #C3A336;
}

.btn {
	cursor: pointer;
	border: #FFFFFF 1px solid;
}

.btn:HOVER {
	background-color: F0FFE1;
	border: #93B3CA 1px solid;
}
</style>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>产品管理</span>
		</div>
		<div id="ContainBox">
			<s:form id="EditForm" name="EditForm"
								action="pageAction!list" method="post">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
								
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="EditForm['ec_eti'].disabled=true;EditForm.submit();">
										</td>

									</tr>
								</table>
						
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTableBody();"
									style="cursor: pointer;">
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="TableBody">
							
								<ec:table tableId="product" items="pageDataDTO.data" var="map"
									width="100%" form="EditForm"
									action="${ctx}/pageAction!list"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:exportXls fileName="productList.xls" tooltip="导出Excel" />
									<ec:row>
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.productId}" />
										</ec:column>
										<ec:column property="productId" title="产品号" width="20%">
										    <a href="productView.action?productDTO.productId=${map.productId}">
                                               <s:property value="#attr['map'].productId" />
                                            </a>
										</ec:column>
										<ec:column property="productName" title="产品名称" width="10%" />
										<ec:column property="cardType" title="卡类型" width="15%">
										</ec:column>
										<ec:column property="productType" title="产品类型" width="15%">
										</ec:column>
										<ec:column property="prodStat" title="产品状态" width="10%">
										</ec:column>
										<ec:column property="entityName" title="发行机构"
											width="10%">
										</ec:column>

									</ec:row>
								</ec:table>

						</div>
						
								
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
			</s:form>
		</div>
		<br>
	</body>
</html>