<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>商户管理</title>
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

    function checkedCount() {
        var checkboxs = document.getElementsByName('entityIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
                
            }
            
        }
        return count;
    }
    function queryMerchant() {
       	var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/inquiry.action';
        merchantForm.submit();
    }
    function addMerchant() {
        var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/add.action';
        merchantForm.submit();
    }

    function editMerchant() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/edit.action';
        merchantForm.submit();
    }

    function deleteMerchant() {
    	var checkboxs = document.getElementsByName('entityIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
            	var ids=checkboxs[i].value.split(",");
            	if(ids[1]=="审核中" || ids[1]=="已审核"){
            		errorDisplay("商户信息审核状态无法编辑与删除！");
    				return;
            	}
                count++;
                
            }   
        }
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
		confirm("确定删除吗?",operation);
    }
    function operation(){
		var merchantForm = Ext.get("merchantForm").dom;
        merchantForm.action = '${ctx}/merchantManagement/delete.action';
        merchantForm.submit();
	}
    function changePassword(){
			var n=0;
			var merchantId;
			var checkbox=document.getElementsByName("entityIdList");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					merchantId=checkbox[i].value.split(",")[0];
				}
			}
			if(n==0){
				alert('请选择要修改密码的对象');
			}
			if(n>1){
				alert('修改密码的对象只能是一个');
			}
			if(n==1){
				document.merchantForm.action='merchantManagement/loadForModifyPassowrd.action?merchantDTO.entityId='+merchantId;
				document.merchantForm.submit();
			}
		}
    function editMhtWithState(){
		var merchantId;
		var checkboxs = document.getElementsByName('entityIdList');	
		var mhtState;
		var count = 0;
		for (var i = 0; i < checkboxs.length; i++) {
	            if (checkboxs[i].checked) {
	            	var ids=checkboxs[i].value.split(",");
	                count++;
	                merchantId=ids[0];
	                mhtState=ids[1];
	            }
	        }
		 if(count<1){
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }else if(count==1){
	        	if(mhtState=="审核中" || mhtState=="已审核"||mhtState=="已冻结"){
	        		errorDisplay("商户信息审核状态无法编辑！");
					return;
	        	}
/* 	        	var merchantForm = Ext.get("merchantForm").dom;
	        	merchantForm = '${ctx}/merchantManagement/edit.action'; */
	        	
	        	document.merchantForm.action='merchantManagement/edit.action?merchantDTO.entityId='+merchantId;
				document.merchantForm.submit();
	        }else{
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }
	}
    function frozen(){
    	var merchantId;
		var checkboxs = document.getElementsByName('entityIdList');	
		var mhtState;
		var count = 0;
		for (var i = 0; i < checkboxs.length; i++) {
	            if (checkboxs[i].checked) {
	            	var ids=checkboxs[i].value.split(",");
	                count++;
	                merchantId=ids[0];
	                mhtState=ids[1];
	            }
	        }
		 if(count!=1){
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }else if(count==1){
	        	
	        	if(mhtState !="已审核"){
	        		errorDisplay("只有已审核状态才能冻结！");
					return;
	        	}
/* 	        	var merchantForm = Ext.get("merchantForm").dom;
	        	merchantForm = '${ctx}/merchantManagement/edit.action'; */
	        	
	        	document.merchantForm.action='merchantManagement/frozen.action?merchantDTO.entityId='+merchantId;
				document.merchantForm.submit();
	        }else{
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }
    }
 	function unfrozen(){
 		var merchantId;
		var checkboxs = document.getElementsByName('entityIdList');	
		var mhtState;
		var count = 0;
		for (var i = 0; i < checkboxs.length; i++) {
	            if (checkboxs[i].checked) {
	            	var ids=checkboxs[i].value.split(",");
	                count++;
	                merchantId=ids[0];
	                mhtState=ids[1];
	            }
	        }
		 if(count!=1){
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }else if(count==1){
	        	if(mhtState !="已冻结"){
	        		errorDisplay("只有已冻结状态才能解冻！");
					return;
	        	}
/* 	        	var merchantForm = Ext.get("merchantForm").dom;
	        	merchantForm = '${ctx}/merchantManagement/edit.action'; */
	        	
	        	document.merchantForm.action='merchantManagement/unfrozen.action?merchantDTO.entityId='+merchantId;
				document.merchantForm.submit();
	        }else{
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }
    }
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>商户信息管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
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
							<s:form id="merchantForm" name="merchantForm"
								action="merchantManagement/inquiry" method="post">
								<table>
									<tr height="35">
										<td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantCode"
												id='merchantCode'></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.entityId
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户注册名称：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantName" />
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.merchantName
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>商户实际店名：</span>
										</td>
										<td width="160">
											<s:textfield name="merchantQueryDTO.merchantRealityName"></s:textfield>
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.merchantRealityName
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户属性：</span>
										</td>
										<td width="160">
											<edl:entityDictList displayName="merchantQueryDTO.merchantAttribute" dictValue="${merchantQueryDTO.merchantAttribute}" dictType="184" tagType="2" defaultOption="true" />
											<s:fielderror>
												<s:param> merchantQueryDTO.merchantAttribute </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="90" align=right>
											<span>注销状态：</span>
										</td>
										<td width="160">
											<s:select list="#{'1':'未注销','0':'已注销'}"
												name="merchantQueryDTO.dataState" emptyOption="false"
												label="注销状态" headerKey="" headerValue="--请选择--" />
											<s:fielderror>
												<s:param>
                                            merchantQueryDTO.dataState
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="102" align=right>
											<span>商户状态：</span>
										</td>
										<td width="120">
											<s:select list="#{'2':'未审核','4':'审核中','1':'已审核','3':'审核未通过','0':'无效'}"
												name="merchantQueryDTO.merchantState" emptyOption="false"
												label="商户状态" headerKey="" headerValue="--请选择--" />
										</td>
										<td align="center">
											<input type="button" class="bt" style="margin: 5px"
												onclick="queryMerchant()" value="查 询" />
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

							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="merchantForm"
								action="${ctx}/merchantManagement/inquiry.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row ondblclick="view('merchantManagement/view',{'merchantDTO.entityId':'${map.entityId}'},'');">
									<ec:column property="null" alias="entityIdList" title="选择"
										width="10%"  headerCell="selectAll">
										<input type="checkbox" name="entityIdList"
											value="${map.entityId},${map.merchantState}" />
										<input type="hidden" name="stateList" value="${map.merchantState}" />
										<input type="hidden" name="entityIdAll"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="10%" escapeAutoFormat="true" >
										<a href="merchantManagement/view?merchantDTO.entityId=${map.entityId}">${map.merchantCode}</a>
									</ec:column>
									<ec:column property="merchantName" title="商户注册名称" width="12%"  />
									<ec:column property="merchantRealityName" title="商户实际店名" width="12%"  />
									<ec:column property="englishName" title="商户英文名称" width="12%"  />
									<ec:column property="merchantState" title="商户状态" width="8%"  />
									<ec:column property="merchantAttribute" title="商户属性" width="10%"  />
									<ec:column property="dataState" title="注销状态" width="8%"  />
								</ec:row>
							</ec:table>

							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
											<display:security urlId="500013">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="deleteMerchant()">
													删除
												</div>
											</display:security>
											<display:security urlId="500012">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="editMhtWithState()">
													编辑
												</div>
												</display:security>
											<display:security urlId="500011">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="addMerchant()">
													添加
												</div>
												</display:security>
												<!-- <button class='bt' style="float: right; margin: 5px"
													onclick="changePassword();">
													商户网站重置密码
												</button> -->
												<display:security urlId="500014">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="frozen()">
													冻结
												</div>
												</display:security>
												<display:security urlId="500015">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="unfrozen()">
													解冻
												</div>
												</display:security>
											<div style="clear: both"></div>
										</div>
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
	</body>
</html>