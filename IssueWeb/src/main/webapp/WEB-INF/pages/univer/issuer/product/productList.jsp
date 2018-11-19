<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品管理</title>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
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
				errorDisplay('请选择要编辑的对象');
			}
			if(n>1){
				errorDisplay('编辑对象只能是一个');
			}
			if(n==1){
				if (document.forms.EditForm['ec_eti'] != null) {
					document.forms.EditForm['ec_eti'].disabled=true;
	        	}
				document.forms.EditForm.action='${ctx}/producte!state.action';
				document.forms.EditForm.submit();
			}
		}
		function edit(){
			if (document.forms.EditForm['ec_eti'] != null) {
				document.forms.EditForm['ec_eti'].disabled=true;
	        }
			var n=0;
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
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
				document.forms.EditForm.action='load.action';
				document.forms.EditForm.submit();
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
				errorDisplay('请选择要删除的对象');
			}
			
			if(n>0){
				confirm("确定删除吗?",operation)
			}
		}
		
		function operation(){
			if (document.forms.EditForm['ec_eti'] != null) {
				document.forms.EditForm['ec_eti'].disabled=true;
	        }
			document.forms.EditForm.action='${ctx}/productDelete.action';
			document.forms.EditForm.submit();
		}
		
		
		function queryProduct(){
			if (document.forms.EditForm['ec_eti'] != null) {
				document.forms.EditForm['ec_eti'].disabled=true;
        	}
			
			document.forms.queryForm.submit();
		}
		function add(){
			if (document.forms.EditForm['ec_eti'] != null) {
				document.forms.EditForm['ec_eti'].disabled=true;
	        }
			document.forms.EditForm.action='${ctx}/productReAdd.action';
			document.forms.EditForm.submit();
		}
	</script>

</head>
<body>
	<%@ include file="/commons/messages.jsp"%>

	<div class="TitleHref">
		<span>产品管理</span>
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
							<td class="TableTitleFront" onclick="displayQueryBody();"
								style="cursor: pointer;"><span class="TableTop">查询条件</span>
							</td>
							<td class="TableTitleEnd">&nbsp;</td>
						</tr>
					</table>
					<div id="QueryBody">
						<s:form id="queryForm" name="queryForm" action="productInquery"
							method="post">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="0">
								<tr height="35">
									<td width="150" align=right><span>产品号：</span></td>
									<td width="160"><s:textfield
											name="productQueryDTO.productId"></s:textfield> <s:fielderror>
											<s:param>
													productQueryDTO.productId
												</s:param>
										</s:fielderror> &nbsp;</td>
									<td width="150" align=right><span>产品名称：</span></td>
									<td width="160"><s:textfield
											name="productQueryDTO.productName" /> &nbsp;</td>
									<td width="150" align=right><span>介质类型：</span></td>
									<td width="120"><dl:dictList
											displayName="productQueryDTO.cardType" dictType="102"
											dictValue="${productQueryDTO.cardType}" tagType="2"
											defaultOption="true" /></td>

								</tr>
								<tr height="35">

									<td width="150" align=right><span>产品类别：</span></td>
									<td width="120"><edl:entityDictList
											displayName="productQueryDTO.productType" dictType="815"
											dictValue="${productQueryDTO.productType}" tagType="2"
											defaultOption="true" /></td>
									<td width="150" align=right><span>产品状态：</span></td>
									<td width="120"><s:select id="issuerGroupId"
											list="#{'':'-请选择-',1:'可用',0:'不可用'}"
											name="productQueryDTO.prodStat"></s:select></td>

									<td width="150" align=right><span>发行机构：</span></td>
									<td width="120"><s:select id="entityId" list="entityList"
											name="productQueryDTO.entityId" listKey="entityId"
											listValue="entityName" headerKey="" headerValue="--请选择--"></s:select>
									</td>

									<td align="center"><img src="${ctx}/images/table/cx.GIF"
										width="50" height="19" class="btn" onclick="queryProduct();">
									</td>
								</tr>
							</table>
						</s:form>
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
								style="cursor: pointer;"><span class="TableTop">记录列表</span>
							</td>
							<td class="TableTitleEnd">&nbsp;</td>
						</tr>
					</table>
					<div id="TableBody">
						<s:form id="EditForm" name="EditForm" action="productInquery"
							method="post">
							<s:token></s:token>
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="EditForm" action="${ctx}/productInquery.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:exportXls fileName="productList.xls" tooltip="导出Excel" />
								<ec:row>
									<ec:column property="null" alias="choose" title="选择"
										width="10%" headerCell="selectAll">
										<input type="checkbox" name="choose"
											value="${map.productId},${map.defineIssuerId}" />
									</ec:column>
									<ec:column property="productId" title="产品号" width="10%">
										<a
											href="productView.action?productDTO.productId=${map.productId}">
											<s:property value="#attr['map'].productId" />
										</a>
									</ec:column>
									<ec:column property="productName" title="产品名称" width="15%" />
									<ec:column property="cardType" title="介质类型" width="15%">
									</ec:column>
									<ec:column property="productType" title="产品类别" width="10%">
									</ec:column>
									<ec:column property="prodStat" title="产品状态" width="10%">
									</ec:column>
									<ec:column property="defineIssuerName" title="产品定义机构"
										width="10%">
									</ec:column>
									<ec:column property="entityName" title="发行机构" width="10%">
									</ec:column>
								</ec:row>
							</ec:table>
						</s:form>
					</div> <!-- div id=TableBody -->

				</td>
			</tr>
		</table>
	</div>
	<div id="buttonCRUD"
		style="text-align: right; width: 100%; height: 30px;">
		<display:security urlId="30404">
			<div id="deleteBtn" class="btn"
				style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
				onclick="del()">删除</div>
		</display:security>
		<display:security urlId="30403">
			<div id="modifyBtn" class="btn"
				style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
				onclick="edit()">编辑</div>
		</display:security>
		<display:security urlId="30402">
			<div id="addBtn" class="btn"
				style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
				onclick="add();">添加</div>
		</display:security>
		<div style="clear: both"></div>
	</div>
	<br>
</body>
</html>