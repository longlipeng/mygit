<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单流程信息</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/ajax.jsp"%>

	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单流程信息</span>
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
								<td class="TableTitleFront" onclick="displayFlowTableBody();"
									style="cursor: pointer;">
									<span class="TableTop">订单流程信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="flowTable">
							<table width="100%">
								<tr>
									<td align="right" width="15%" nowrap="nowrap">
										订单流程号：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:label name="sellOrderFlowDTO.orderFlowId"></s:label>
									</td>
									<td align="right" width="15%" nowrap="nowrap">
										操作员：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<s:label  name="sellOrderFlowDTO.createUser"></s:label>
									</td>
								</tr>
								
								<tr>
									<td align="right" width="15%" nowrap="nowrap">
										操作种类：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<dl:dictList dictType="312" dictValue="${sellOrderFlowDTO.operateType}"
										tagType="1" />
									</td>
									<td align="right" width="15%" nowrap="nowrap">
										流程节点：
									</td>
									<td width="35%" align="left" nowrap="nowrap">
										<dl:dictList dictType="310" dictValue="${sellOrderFlowDTO.orderFlowNode}"
										tagType="1" />
									</td>
								</tr>
								<tr>
									<td align="right" width="15%" nowrap="nowrap">
										操作时间：
									</td>
									<td width="35%" align="left" nowrap="nowrap" colspan="3">
										<s:date name="sellOrderFlowDTO.createTime" format="yyyy-MM-dd"/>
									</td>
								</tr>
				
								<tr>
									<td align="right" width="15%" nowrap="nowrap">
										操作描述：
									</td>
									<td width="35%" align="left" nowrap="nowrap" colspan="3">
										<s:textarea name="sellOrderFlowDTO.memo" cols="50" rows="3" readonly="true" ></s:textarea>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			
			<div id="btnDiv" style="text-align: right;">
			<button class='btn' style="float: right; margin: 7px"
				onclick="window.close();">
				关闭
			</button>
		</div>
	</body>
</html>