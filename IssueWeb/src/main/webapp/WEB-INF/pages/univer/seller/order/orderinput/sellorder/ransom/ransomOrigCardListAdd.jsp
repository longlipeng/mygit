<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加赎回订单原卡明细</title>
	<%@ include file="/commons/meta.jsp"%>
	<%@ include file="/commons/ajax.jsp"%>
	<base target="_self">
	<script type="text/javascript">
		function chooseQueryType(){
			var choose=$("#queryType").val();
			if(choose == 1){
				/*卡号段查询*/
				document.getElementById("cardNo").value="";
				document.getElementById("cardNoTr").style.display="none";
				document.getElementById("cardNoListTr").style.display="";
				
			}else if(choose == 2){
				/*卡号查询*/
				document.getElementById("startCardNo").value="";
				document.getElementById("endCardNo").value="";
				document.getElementById("cardNoListTr").style.display="none";
				document.getElementById("cardNoTr").style.display="";
			}
		}
		function addOrigCard(){
		var choose=$("#queryType").val();
			if(choose == 1){
				/*卡号段*/
				var startNo=document.getElementById("startCardNo").value;
				var endNo=document.getElementById("endCardNo").value;
				if(isNullOrEmpty(startNo) || isNullOrEmpty(endNo)){
					errorDisplay("起始卡号和结束卡号不能为空");
					return;
				}
			}else if(choose == 2){
				/*卡号*/
				var startNo=document.getElementById("cardNo").value;
				if(isNullOrEmpty(startNo)){
					errorDisplay("卡号不能为空");
					return;
				}
			}
			addOrigForm.submit();
		}
		function isNullOrEmpty(oValue){
			return oValue==null||oValue=="";
		}
	</script>
  </head>
  
  <body onload="chooseQueryType();">
    	<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加赎回订单原卡明细</span>
		</div>
		<div id="ContainBox">
		<s:form name="addOrigForm" id="addOrigForm" method="post" action="ransomOrderAction!insertOrigCard">
			<input type="hidden" name="sellOrderOrigCardListQueryDTO.orderId" value="${sellOrderOrigCardListQueryDTO.orderId}"/>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
					bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
							bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
								cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();" style="cursor: pointer;">
									<span class="TableTop">原卡添加</span>
								</td>
								<td class="TableTitleEnd">
										&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
						<table width="100%">
							<tr>
								<td align="right" width="15%" nowrap="nowrap">
									添加方式：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:select list="#{1:'卡号段添加',2:'卡号添加'}" name="queryType" id="queryType" onchange="chooseQueryType();"></s:select>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr id="cardNoTr">
								<td align="right" width="15%" nowrap="nowrap">
											<span style="color: red;">*</span>卡号：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderOrigCardListQueryDTO.cardNo"
										id="cardNo" size="20">
									</s:textfield>
									<s:fielderror>
										<s:param>
											sellOrderOrigCardListQueryDTO.cardNo
										</s:param>
									</s:fielderror>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr id="cardNoListTr">
								<td align="right" width="15%" nowrap="nowrap">
									起始卡号：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderOrigCardListQueryDTO.startCardNo"
										id="startCardNo" size="20">
									</s:textfield>
									<s:fielderror>
										<s:param>
											sellOrderOrigCardListQueryDTO.startCardNo
										</s:param>
									</s:fielderror>
								</td>
								<td align="right" width="15%" nowrap="nowrap">
									结束卡号：
								</td>
								<td width="35%" align="left" nowrap="nowrap">
									<s:textfield name="sellOrderOrigCardListQueryDTO.endCardNo"
										id="endCardNo" size="20">
									</s:textfield>
									<s:fielderror>
										<s:param>
											sellOrderOrigCardListQueryDTO.endCardNo
										</s:param>
									</s:fielderror>
								</td>
							</tr>
							<tr>
								<td colspan="4" align="right">
									<button class='bt' style="float: right; margin: 7px"
										onclick="addOrigCard();">
									添加
									</button>
								</td>
							</tr>
						</table>
						</div>			
					</td>	
				</tr>
			</table></s:form>
		</div>
  </body>
</html>
