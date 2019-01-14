<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增黑名单</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript"><!--
		
        function inserBlackList(){
        var startNo=document.getElementById('startNo').value;
        var endNo=document.getElementById('endNo').value;
         var reg = new RegExp("^[0-9]{1,30}$");
         if(!reg.test(startNo)){
        	alert("卡号只能是数字!");
        	return;
   		 }
        if(endNo!=null){
        if(!reg.test(endNo)){
        	alert("卡号只能是数字!");
        	return;
   		 }
        if(startNo.length!=endNo.length){
       		alert('卡号长度不一致');
       		return;
       	 }
       	if(startNo>endNo){
			alert("开始卡号不能大于结束卡号");
			return;
        }
        var sum=endNo-startNo;
        	if(sum>10000){
        	confirm("您添加的记录超过一万条，确认要添加吗？",send);
        	}
        	else{
        	send();
        	}
          }
        }
        function send(){
        newForm.action='blackList/insertBlackList.action';
        newForm.submit();
        }
		</script>
	</head>
	<body >
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增黑名单</span>
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
								<td class="TableTitleFront"
									onclick="displayTable('serviceTable');"
									style="cursor: pointer;">
									<span class="TableTop">黑名单信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="newForm" name="newForm">
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>起始卡号：
													</td>
													<td>
														<s:textfield id="startNo" name="blackListDTO.startNo" />
														<s:fielderror>
															<s:param>
																<!--
																shopDTO.entityId
															-->
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
														结束卡号：
													</td>
													<td>
														<s:textfield name="blackListDTO.endNo" id="endNo" />
														<s:fielderror>
															<s:param>
																<!--
																shopDTO.merchantName
															-->
															</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>


									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>备注：
													</td>
													<td>
														<s:textfield name="blackListDTO.meno" id="meno" cssStyle="size:10px; width:200px;height:60px"/>
														<s:fielderror>
															<s:param>
																<!--
																shopDTO.shopAddress
															-->
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
			<button class='bt' style="float: right; margin: 5px 10px"
				onclick="newForm.action='blackList/inquiry.action';newForm.submit();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="inserBlackList()">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>