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
		function selectConsumer(){
			var id='';
			var n=0;
			var checkbox=document.getElementsByName("consumerIdList");
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				alert('请选择一个收单机构！');
				return;
			}
			window.returnValue=id;
			window.close();  
		}
     
	</script>
	</head>
	<body>
	<s:form id="consumerForm" name="consumerForm"
								action="consumer/chooseConsumer" method="post">
		
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
										<td width="85" align=right>
											<span>收单机构编号：</span>
										</td>
										<td width="160">
											<s:textfield name="consumerQueryDTO.consumerCode" />
											 <s:fielderror>
												<s:param>
                                                   consumerQueryDTO.consumerCode
						                        </s:param>
											</s:fielderror>
										</td>
										<td width="160" align=right>
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
											<input type="submit" class="bt" style="margin: 5px" value="查 询" />
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
								action="consumer/chooseConsumer"
								imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
								retrieveRowsCallback="limit" autoIncludeParameters="false">
								<ec:row>
									<ec:column alias="consumerIdList" title="选择"
										width="10%" sortable="false" headerCell="">
										<input type="radio" name="consumerIdList"
											value="${map.entityId},${map.consumerName}" />
										<input type="hidden" name="consumerState" value="${map.consumerState}" />
										<input type="hidden" name="merchantIdAll"
											value="${map.entityId}" />
									</ec:column>
									<ec:column property="entityId" title="收单机构号" width="10%"
										escapeAutoFormat="true">
									</ec:column>
									<ec:column property="consumerName" title="收单机构名称" width="10%" />
									<ec:column property="consumerEnglishName" title="收单机构英文名称" width="15%" />
									<ec:column property="consumerCode" title="收单机构编号" width="15%" />
									<ec:column property="externalId" title="外部系统代码" width="15%" />
									<ec:column property="consumerState" title="收单机构状态" width="10%" />
								</ec:row>
							</ec:table>


					<table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="selectConsumer();" value="提 交"/>
                                        </td>
                                        <td>
                                            <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="window.close();" value="关 闭"/>
                                        </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
							
						</div>
					</td>
				</tr>
			</table>
			
		</div>
		<br>
		</s:form>
	</body>
</html>