<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端管理</title>
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
				document.EditForm.action='producte!state.action';
				document.EditForm.submit();
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
				document.EditForm.action='loadposInf.action';
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
				confirm("确定删除吗？",operation);
			}
		}
		
		function operation(){
			document.EditForm.action='delposInf.action';
			document.EditForm.submit();
		}
		function inquiry(){
			var str=/^[0-9]+(.[0-9]{1,2})?$/;
			var strShopId=document.getElementById("shopId").value;
			if(!isNullOrEmpty(strShopId) && !str.test(strShopId)){
				document.getElementById("shopId").value="";
				alert("门店号必须为数字");
				return;
			}
			document.EditForm.submit();
		}
		
		function isNullOrEmpty(oValue){
  			return oValue==null||oValue=="";
  		}

		function setParameter(prmType){
			var n=0;
			var termType="";
			
			var checkbox=document.getElementsByName("choose");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					termType=checkbox[i].value.split(",")[2];
				}
			}
			if(n==0){
				alert('请选择记录');
			}
			if(n>0 && termType!=2){
	
				document.EditForm.action='updateParameter.action?prmType='+prmType;
				document.EditForm.submit();
			}else{
				return false;
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
			<span>终端管理</span>
		</div>
		<s:form id="EditForm" name="EditForm"
								action="inqueryOuterposOrPos.action" method="post">
		<s:hidden name="posInfQueryDTO.termType" value='1'></s:hidden>
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
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						
						<div id="QueryBody">
							
								<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>终端号：</span>
										</td>
										<td width="160">
											<s:textfield name="posInfQueryDTO.termId"></s:textfield>
											<s:fielderror>
												<s:param>
													posInfQueryDTO.termId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										</tr>
										<tr height="35">
										<td width="150" align=right id="t3">
											<span>终端状态：</span>
										</td>
										<td width="160">
									
											<s:select list="#{1:'有效',0:'无效'}" name="posInfQueryDTO.termStat"  id="termStat" ></s:select>
											
											&nbsp;
										</td>
										<td>
										</td>
										<td>
										</td>
										</tr>
										
										<tr height="35">
										<td width="150" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="posInfQueryDTO.mchntId" id='merchantId'></s:textfield>
											<s:fielderror>
												<s:param>
													posInfQueryDTO.mchntId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>商户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="posInfQueryDTO.mchntName" />
											&nbsp;
										</td>
										
										
												
										
									</tr>
									
									<tr height="35">
								
									<td width="150" align=right id="t1">
										
											<span>门店号：</span>
										</td>
										<td width="120"  id="t2">
											<s:textfield name="posInfQueryDTO.shopId" id="shopId" />
											
										</td>
							
									<td width="150" align=right id="t5">
											<span>门店名称：</span>
										</td>
										<td width="120" id="t6">											
											<s:textfield name="posInfQueryDTO.shopName"/>													
										</td>
									<td>
									</td>
									
									</tr>
									
									<tr>
									<td>
									</td>
									<td>
									</td>
									<td>
									</td>
									<td>
									</td>
									<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="inquiry();">
										</td></tr>
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
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/inqueryOuterposOrPos.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="1%"  headerCell="selectAll">
											<input type="checkbox" name="choose" value="${map.termId},${map.mchntId},${map.termType}" />											
										</ec:column>
										<ec:column property="termId" title="终端号" width="10%" escapeAutoFormat="true" >
											<a href="viewposInf.action?posInfoDTO.mchntId=${map.mchntId}&posInfoDTO.termId=${map.termId}&termType=${map.termType}">${map.termId}</a>
										</ec:column>
										
									 	<ec:column property="termTypeName" title="终端类型" width="8%" />
									 	<ec:column property="shopId" title="门店号" width="8%" />
										<ec:column property="shopName" title="门店名称" width="10%" />
										<ec:column property="mchntId" title="商户号" width="10%" />
										<ec:column property="mchntName" title="商户名称" width="10%" />
										<ec:column property="prmChangeFlag1" title="公共参数下载" width="8%" />
										<ec:column property="prmChangeFlag2" title="卡BIN参数下载" width="8%" />
										<ec:column property="termStat" title="状态" width="5%" />										
										<ec:column property="phone" title="门店电话" width="10%" />	
									   		<%//  <ec:column property="createT" /> title="创建时间" width="13%" />	 %>
																	
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">		
																			
												<display:security urlId="500043">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="del()">
													删除
												</div>
												</display:security>
												
												<display:security urlId="500045">
												<div id="modifyBtn2" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="setParameter(10002)">
													卡BIN参数下载
												</div>
												</display:security>
												
												<display:security urlId="500044">
												<div id="modifyBtn1" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="setParameter(10001)">
													公共参数下载
												</div>
												</display:security>	
												
												<display:security urlId="500042">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="edit()">
													编辑
												</div>
												</display:security>

												<display:security urlId="500041">
													<div id="addBtn" class="btn"
														style="width: 105px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
														onclick="EditForm.action='redircetAddposInf.action';EditForm.submit();">
														添加内部终端
													</div>
													</display:security>
											</div>
										</td>
									</tr>
								</table>
							
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		</s:form>
		<br>
	</body>
</html>