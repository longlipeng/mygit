<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>收单机构管理</title>
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
        var checkboxs = document.getElementsByName('consumerIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }
   
    function addConsumer() {    	
        document.consumerForm.action = '${ctx}/consumer/reAdd.action';
        document.consumerForm.submit();
    }

    function editConsumer() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        document.consumerForm.action = '${ctx}/consumer/reEdit.action';
        document.consumerForm.submit();
    }
    function modifyPassword(){
	    var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
       
       document.consumerForm.action='consumer/consumerModifyPassword.action';
	   document.consumerForm.submit();
	}
	 function modifyUserState(){
	    var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
       document.consumerForm.action='consumer/modifyUserState.action';
	   document.consumerForm.submit();
	}
    function deleteConsumer() {
        var count = checkedCount();
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
		confirm("确定删除吗?",operation);
    }
    function operation(){
    	consumerForm.action = '${ctx}/consumer/delete.action';
    	consumerForm.submit();
	}
    function changePassword(){
			var n=0;
			var consumerId;
			var checkbox=document.getElementsByName("consumerIdList");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					n++;
					consumerId=checkbox[i].value;
				}
			}
			if(n==0){
				alert('请选择要修改密码的对象');
			}
			if(n>1){
				alert('修改密码的对象只能是一个');
			}
			if(n==1){
				document.consumerForm.action='consumer/loadForModifyPassowrd.action?consumerDTO.entityId='+consumerId;
				document.consumerForm.submit();
			}
		}
		
		function query(){
				document.consumerForm.action='${ctx}/consumer/inquery.action';
				document.consumerForm.submit();
			}
    
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>收单机构信息管理</span>
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
							<s:form id="consumerForm" name="consumerForm"
								action="consumer/inquery" method="post">
								<table>
									<tr height="35">
										<td width="85" align=right>
											<span>收单机构号：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.entityId"></s:textfield>
											<s:fielderror>
												<s:param>
                                            consumerQueryDTO.entityId
						                  </s:param>
											</s:fielderror>
										</td>
										<td width="160" align=right>
											<span>收单机构名称：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.consumerName" />
											<s:fielderror>
												<s:param>
                                            consumerQueryDTO.consumerName
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
								
									<tr height="35">
										
										<td width="85" align=left>
											<span>外部系统代码：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.externalId" />
											<s:fielderror>
												<s:param>
                                            consumerQueryDTO.externalId
						                  </s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
										<td width="85" align=right>
											<span>收单机构状态：</span>
										</td>
										<td width="120">
											<s:select list="#{'1':'有效','0':'无效'}"
												name="consumerQueryDTO.consumerState" emptyOption="false"
												label="收单机构状态" headerKey="" headerValue="--请选择--" />
										</td>
										<td width="160" align="right">
											<input type="button" class="bt" style="margin: 5px" onclick="query();" value="查 询"/>
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
								form="consumerForm"
								action="consumer/inquery.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row>
									<ec:column property="null" alias="consumerIdList" title="选择"
										width="10%"  headerCell="selectAll">
										<input type="checkbox" name="consumerIdList"
											value="${map.entityId}"/>
										<input type="hidden" name="consumerState" value="${map.consumerState}" />
										<input type="hidden" name="userIdList" value="${map.userId}" />
										<input type="hidden" name="merchantIdAll"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="entityId" title="收单机构号" width="10%"
										escapeAutoFormat="true" >
										<a
											href="${ctx}/consumer/view?consumerQueryDTO.entityId=${map.entityId}">${map.entityId}</a>
									</ec:column>
									<ec:column property="consumerName" title="收单机构名称" width="10%"  />
									<ec:column property="consumerEnglishName" title="收单机构英文名称" width="15%"  />
									<ec:column property="externalId" title="外部系统代码" width="15%"  />
									<ec:column property="consumerState" title="收单机构状态" width="10%"  />
								</ec:row>
							</ec:table>


							<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0" style="border-top: 1px solid silver;">
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;"
											valign="middle">
												<display:security urlId="30904">
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="deleteConsumer()">
													删除
												</div>
												</display:security>
										<display:security urlId="30903">
												<div id="modifyBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="editConsumer()">
													编辑
												</div>
												</display:security>
											<display:security urlId="30902">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="addConsumer()">
													添加
												</div>
												</display:security>
                                                 <div id="ppBtn" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="modifyPassword();">
													修改用户密码
												 </div>
												 <div id="muBtn" class="btn"
													style="width: 100px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="modifyUserState();">
													编辑机构用户
												 </div>
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
		 <s:form name="updatePasswordForm" id="updatePasswordForm">
            <s:hidden id="userDTO.userId" name="userDTO.userId"></s:hidden>
        </s:form>
	</body>
</html>