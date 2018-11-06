<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>商户合同管理</title>
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
        var checkboxs = document.getElementsByName('consumerContractIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }

    function query() {
        var consumerContractForm = Ext.get("consumerContractForm").dom;
        consumerContractForm.action = '${ctx}/mchntContractManagement/inquiry.action';
        consumerContractForm.submit();
    }

    function addMchntContract() {
        var consumerContractForm = Ext.get("consumerContractForm").dom;
        consumerContractForm.action = '${ctx}/mchntContractManagement/add.action';
        consumerContractForm.submit();
    }

    function editMchntContract() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        var consumerContractForm = Ext.get("consumerContractForm").dom;
        consumerContractForm.action = '${ctx}/mchntContractManagement/edit.action';
        consumerContractForm.submit();
    }

    function deleteMchntContract() {
        var count = checkedCount();
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
		confirm("确定删除吗？",operation);
        
    }
    function operation(){
		var consumerContractForm = Ext.get("consumerContractForm").dom;
        consumerContractForm.action = '${ctx}/mchntContractManagement/delete.action';
        consumerContractForm.submit();
	}
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		
		<div class="TitleHref">
			<span>商户合同管理</span>
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
						<s:form id="consumerContractForm" name="consumerContractForm" action="/mchntContractManagement/inquiry" method="post">
			                <table>
                                    <tr height="35">
                                        <td width="85" align=right>
                                            <span>合同号：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="consumerContractQueryDTO.consumerContractId"></s:textfield>
                                            <s:fielderror>
                                                <s:param>
                                                    consumerContractQueryDTO.consumerContractId
                                                </s:param>
                                            </s:fielderror> 
                                        </td>
                                    </tr>
									 <tr height="35">
                                        <td width="85" align=right>
                                            <span>商户号：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="consumerContractQueryDTO.merchantCode"></s:textfield> 
                                        </td>
                                        <td width="85" align=right>
                                            <span>商户名称：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="consumerContractQueryDTO.merchantName"></s:textfield>
                                            <s:fielderror>
                                                <s:param>
                                                    consumerContractQueryDTO.merchantName
                                                </s:param>
                                            </s:fielderror> 
                                        </td>
                                        <td width="85" align=right>
                                            <span>结算规则：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="consumerContractQueryDTO.ruleNo"></s:textfield>
                                            <s:fielderror>
                                                <s:param>
                                                    consumerContractQueryDTO.ruleNo
                                                </s:param>
                                            </s:fielderror> 
                                        </td>
                                        <td width="85" align=right>
                                            <span>结算规则名称：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="consumerContractQueryDTO.ruleName"></s:textfield>
                                            <s:fielderror>
                                                <s:param>
                                                    consumerContractQueryDTO.ruleName
                                                </s:param>
                                            </s:fielderror> 
                                        </td>
                                    </tr>
									<tr height="35">
										<td width="85" align=right>
											<span>合同状态</span>
										</td>
										<td width="160">
									      <s:select 
                                            list="#{'1':'已生效','2':'未生效','0':'已过期'}" 
                                            name="consumerContractQueryDTO.state" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
											&nbsp;
										</td>
                                        <td align="right">
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
								form="consumerContractForm"
								action="${ctx}/mchntContractManagement/inquiry.action"
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:row onclick="">
									<ec:column property="null" alias="consumerContractIdList" title="选择" width="10%" sortable="false">
										<input type="radio" name="consumerContractIdList" value="${map.consumerContractId},${map.merchantId},${map.contractType}" />
									</ec:column>
									<ec:column property="consumerContractId" title="合同号" width="10%" >
									  <a href="mchntContractManagement/view.action?consumerContractDTO.consumerContractId=${map.consumerContractId}">${map.consumerContractId}</a>
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="15%" escapeAutoFormat="true" >
									</ec:column>
									<ec:column property="merchantName" title="商户名称" width="10%"  />
									<ec:column property="ruleNo" title="结算规则" width="10%" />
									<ec:column property="ruleName" title="结算规则名称" width="10%" />
									<ec:column property="startDate" title="有效期开始时间" width="15%" />
									<ec:column property="endDate" title="有效期结束时间" width="15%" />															
								</ec:row>
							</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												
												<!-- <div id="addBtn" class="btn"
													    style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													        onclick="deleteMchntContract()">
												       删除
												</div> 
												-->
												<display:security urlId="500022">
												 <div id="editBtn" class="btn"
													    style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													        onclick="editMchntContract()">
													    编辑
												    </div>
												    </display:security>
												    <display:security urlId="500021">
												    <div id="addBtn" class="btn"
													    style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													        onclick="addMchntContract()">
													    添加
												    </div>
												</display:security>
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	</body>
</html>