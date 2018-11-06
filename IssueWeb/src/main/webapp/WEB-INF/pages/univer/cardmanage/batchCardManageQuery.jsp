<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡作废</title>
		<%@ include file="/commons/meta.jsp"%>
		<style>
		#reasonDiv {  
		            position: absolute;  
		            height: 200px;  
		            width: 400px;  
		            margin: -100px 0px 0px -200px;  
		            top: 50%;  
		            left: 50%;  
		            text-align: left;  
		            padding: 0px;  
		            border: 1px dotted #000000;  
		            background-color: #FFFFFF;
		            overflow: auto;  
		        }  
		        </style>
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
			function check(){
			    var startCardNo=document.getElementById("startCardNo").value;
				var endCardNo=document.getElementById("endCardNo").value;
			    if(startCardNo==null ||startCardNo==""){
					errorDisplay('请输入起始卡号');
					return false;
				}
				if(endCardNo==null ||endCardNo==""){
					errorDisplay('请输入结束卡号');
					return false;
				}
				return true;
			}
			function queryCardDatail(){
				invalidForm.action='${ctx}/cardManage/batchCardManageAction!queryCardDatail';	 
				invalidForm.submit();
				}
			function invalid() {
			    var area=document.getElementById("invalidReasonId").value.length;
			    if(area>2048){
			        errorDisplay("您输入的字数过多！");
			        return;
			    }
			     if(area==0){
			        errorDisplay("请输入作废原因！");
			        return;
			    }
			    document.getElementById("invalidReason").value=document.getElementById("invalidReasonId").value;
				invalidForm.action='${ctx}/cardManage/batchCardManageAction!invaild';
				invalidForm.submit();
			}
			function closeWin(){
			 document.getElementById("reasonDiv").style.display='none';
		    }
		    function confirmInvaliid(){
		    var flag = true;
				for(i = 0; i < document.getElementsByName("cardNoArray").length; i++) {
					if (document.getElementsByName("cardNoArray").item(i).checked) {
						flag = false;
					}
				}	
				if(flag){
					errorDisplay("请选择至少一条记录操作！");
					return false;
				}
		     document.getElementById("reasonDiv").style.display='';
		     return false;
		    }
		</script>
	</head>
	<body>

		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片管理-->卡作废</span>
		</div>
		
		<!-- 查询条件 -->
		<s:form id="invalidForm" name="invalidForm" action="" method="post">
		<s:hidden name="invalidReason" id="invalidReason"/>
		<div id="queryBox" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF" align="center">
			<tr>
				<td width="100%" height="10" align="left" valign="top" bgcolor="#FFFFFF">
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
				<table width="80%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr height="35">
								<td width="" align="right">
									<span>起始卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="batchCardDTO.startCardNo" id="startCardNo" size="30" maxlength="19"/>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											batchCardDTO.startCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td width="" align="right">
									<span>结束卡号：</span>
								</td>
								<td width="" align="left">
									<s:textfield name="batchCardDTO.endCardNo" id="endCardNo" size="30" maxlength="19"></s:textfield>
								</td>
								<td width="" align="left">
									<s:fielderror>
										<s:param>
											batchCardDTO.endCardNo
										</s:param>
									</s:fielderror>
								</td>
								
								<td align="right">
									<input type="button" class="bt" style="margin: 5px" onclick="queryCardDatail()" value="查 询" />
								</td>
							</tr>
							
						</table>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<br/>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="B5B8BF">
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
						<ec:table items="pageDataDTO.data" var="map" width="100%" form="invalidForm"
							action="${ctx}/cardManage/batchCardManageAction!queryCardDatail"
							imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
							retrieveRowsCallback="limit" tableId="batchCardList" autoIncludeParameters="false" >
							<ec:row ondblclick="">
								<ec:column property="null" alias="cardNoArray" title="选择" headerCell="selectAll" width="5%" sortable="false"  viewsAllowed="html">
									<input type="checkbox" name="cardNoArray" value="${map.cardNo}" />
								</ec:column>
								<ec:column property="cardNo" title="卡号" width="10%" />
								<ec:column property="activable" title="激活状态" width="10%" />
								<ec:column property="allowable" title="是否允许作废" width="10%" />
							</ec:row>
						</ec:table>
						</div>
					</td>
				</tr>
			</table>
			<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="0" bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle" bgcolor="#FFFFFF">
							<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
										<div id="buttonCRUD"
											style="text-align: right; width: 100%; height: 30px;">
											<button class='btn' style="float: right; margin: 7px" onclick="return confirmInvaliid();">
												作废
											</button>
										</div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</s:form>
		<div id="reasonDiv" style = "display: none">
		<center>
			<form id="reasonForm" action="">
			<br/><br/><br/><br/>
			  <input type="hidden" id="reasonId" name="reason" value=""/>
			  <lable>作废原因</lable><textarea name="invalidReasonName" cols="50" rows="3" id="invalidReasonId"></textarea><br/><br/>
			  <input type="button" value="确认作废 " onclick="invalid()"/>
			  <input type="button" value="关 闭 " onclick="closeWin()" />
			</form>
		</center>
		</div>
	</body>
</html>