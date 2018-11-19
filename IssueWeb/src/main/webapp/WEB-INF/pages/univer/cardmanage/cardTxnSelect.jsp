<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡片交易信息查询</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/cookie.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
			var isDisplay = false;
			function displayServiceTable() {
				if (isDisplay) {
					display('serviceTable');
					isDisplay = false;
				} else {
					undisplay('serviceTable');
					isDisplay = true;
				}
			}
			function add(){

					newForm.submit();
		
			}
			function doTest_DeviceKTL512() 
			{
				var vData=DeviceKTL512.GetPinEx(p1);
				if(vData!=-1&&vData.substring(0,4)!='Fail'){
				document.getElementById("password").value=vData;
			}else{
				alert('读密码失败');
			}
			}
		  function change2(){
		      var flag=document.getElementById("cardManagementDTO.queryFlage").value;
		      if(flag==2){
		         document.getElementById("box1").style.visibility="";
		      }else{
		      	 document.getElementById("box1").style.visibility="hidden";
		      }
		  }	
		</script>
		
	</head>
	<body onload="change2();">
	<OBJECT ID="ACCOR_ATL" name="DeviceKTL512"
			CLASSID="clsid:1E47232B-9D0E-41E4-A461-1A89FE964363" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		<OBJECT ID="ACCOR_ATL" name="DeviceKTL656H"
			CLASSID="clsid:41CBE8CA-EFBB-4942-9E0C-AB198EC30B9D" HEIGHT=0 WIDTH=0
			codebase="ACCOR_ATL.dll#version=1,0,0,1"viewastext"></OBJECT>
		
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡片交易信息查询</span>
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
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">查询</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<s:fielderror>
							<s:param>
								cardManagementDTO.cardValidityPeriod
							</s:param>
						</s:fielderror>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="cardManage/cardFreez.action" method="post">
								<s:hidden name="cardManagementDTO.cardNo"></s:hidden>
                                 <s:hidden name="cardManagementDTO.password"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cvv2"></s:hidden>
                                 <s:hidden name="cardManagementDTO.cardholderName"></s:hidden>
                                  <s:hidden name="cardManagementDTO.cardValidityPeriod" />
							     <s:hidden name="cardManagementDTO.cardstate"></s:hidden>
							     <s:hidden name="cardManagementDTO.cardBalanceDTOs"></s:hidden>								
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>查询条件：
													</td>
													<td>
														 <s:select list="#{'1':'近期','2':'历史'}"
															name="cardManagementDTO.queryFlag" 
															id="cardManagementDTO.queryFlag"
															emptyOption="false"
															 headerKey=""
															 headerValue="--请选择--"
															 onchange="change2();"
                                                              />
													</td>
												</tr>
											</table>
										</td>
								    </tr>
								    <tr id="box1">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>开始日期：


													</td>
													<td>

														<input type="text" name="cardManagementDTO.startDate"
															onclick="dateClick(this)" class="Wdate"
															value="<s:date name='cardManagementDTO.startDate' format='yyyy-MM-dd'/>"
															readonly="readonly" />
														<input type="hidden" name="cardManagementDTO.startDate"
															value="<s:date name="cardManagementDTO.startDate" format="yyyy-MM-dd"/>" />
														<br />
														<s:fielderror>
															<s:param>
																cardManagementDTO.startDate
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>结束日期：


													</td>
													<td>

														<input type="text" name="cardManagementDTO.endDate"
															onclick="dateClick(this)" class="Wdate"
															value="<s:date name='cardManagementDTO.endDate' format='yyyy-MM-dd'/>"
															readonly="readonly" />
														<input type="hidden" name="cardManagementDTO.endDate"
															value="<s:date name="cardManagementDTO.endDate" format="yyyy-MM-dd"/>" />
														<br />
														<s:fielderror>
															<s:param>
																cardManagementDTO.endDate
															</s:param>
														</s:fielderror>
													</td>
												
												</tr>
											</table>
										</td>
									</tr>
									
									
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="newForm.action='cardManage/comeback.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' type="button" style="float: right; margin: 5px"
				onclick="newForm.action='cardManage/cardTxnSelect.action';newForm.submit();">
				查询
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
