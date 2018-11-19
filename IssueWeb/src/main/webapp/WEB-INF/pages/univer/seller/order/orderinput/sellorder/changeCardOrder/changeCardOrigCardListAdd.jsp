<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加换卡订单原卡明细</title>
	<%@ include file="/commons/meta.jsp"%>
	<%@ include file="/commons/ajax.jsp"%>
	<base target="_self">
	<script type="text/javascript">
		function addOrigCard(){			
			var startNo=document.getElementById("cardNo").value;
			if(startNo==null||startNo==""){
				errorDisplay("卡号不能为空");
				//alert("卡号不能为空");
				return false;
			}else{
			addOrigForm.action="changeCardOrderAction!insertOrigCard";
			addOrigForm.submit();
			 } 
		}
	</script>
  </head>
  
  <body>
    	<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>添加换卡订单原卡明细</span>
		</div>
		<div id="ContainBox">
		<s:form name="addOrigForm" id="addOrigForm" method="post" action="">
			<input type="hidden" name="sellOrderOrigCardListQueryDTO.orderId" value="${sellOrderOrigCardListQueryDTO.orderId}"/>
			<s:hidden id="sellOrderDTO.productId" name="sellOrderDTO.productId"></s:hidden>
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
									添加方式：卡号添加
								</td>								
								<td>&nbsp;</td>
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
							<tr>
								<td colspan="4" align="right">
									<!-- <button class='bt' style="float: right; margin: 7px"
										onclick="addOrigCard();">
									添加
									</button> -->
									<input type="button"   class='bt' style="float: right; margin: 7px"
										onclick="addOrigCard();" value="添加"/>
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
