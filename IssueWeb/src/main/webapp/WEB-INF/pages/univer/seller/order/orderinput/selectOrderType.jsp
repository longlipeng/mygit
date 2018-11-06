<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新增服务</title>
		<%@ include file="/commons/meta.jsp"%>

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
			
			function addOrder(){
		        var orderTypeObject=document.getElementsByName("orderType");
		        var value = 1;
		        for(i = 0; i <orderTypeObject.length;i++){
		            if(orderTypeObject[i].checked) {
		                value = orderTypeObject[i].value;
		                break;
		            }
		        }
		        switch (value) {
                	case "10000001" : AddForm.action = "sellOrderSignAction!add";document.getElementById("perFlag").value='con'//销售记名卡订单（企业）
                			 break;
                	case "10000002" : AddForm.action = "sellOrderUnsignAction!add";document.getElementById("perFlag").value='con'//销售不记名卡订单（企业）
                			 break;
					//销售记名卡订单（个人）
                	case "10000011" : AddForm.action = "sellOrderSignAction!add";document.getElementById("perFlag").value='per';
                			 break;
                	//销售不记名卡订单（个人）
                    case "10000012" : AddForm.action = "sellOrderUnsignAction!add";document.getElementById("perFlag").value='per';
                             break;

                	case "10000005" : AddForm.action = "sellOrderRetailSignAction!add";
                			 break;
                	case "10000006" : AddForm.action = "sellOrderRetailUnsignAction!add";
                			 break;

                	case "3" : AddForm.action = "giftStockOrderAction!add";
                			 break;
                	case "60000001" : AddForm.action = "changeCardOrderAction!add";
                			 break;
                	case "70000001" : AddForm.action = "ransomOrderAction!add";
                			 break;
                	case "4" : AddForm.action = "giftSaleOrderAction!add";
                			 break;
                	case "5" : AddForm.action = "giftChangeOrderAction!readpwd";
                			 break;
                	case "6" : AddForm.action = "reloadableCardChangeOrderAction!query";
                			 break;
                	case "7" : AddForm.action = "giftSendBackOrderAction!query";
                			 break;
                	case "8" : AddForm.action = "reloadableCardDelayOrderAction!add";
                			 break;
                   case "9" : AddForm.action = "dispatchOrderAction!add";
                			 break;
                	
                }
		        AddForm.submit();
		    }
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>订单管理>录入订单>选择订单类型</span>
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
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">订单类型列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
							<s:form id="AddForm" name="AddForm" action="customerOrderAction!add">
								<s:hidden name="sellOrderDTO.perFlag" id="perFlag"></s:hidden>
								<table border="1"
									style="border-collapse: collapse; border: 1px solid #8DB2E3; width: 100%">
									<s:iterator value="orderTypeList" status="status" var="map">
										<tr height="25px" class="even" onclick="labelClick(event,<s:property value='#status.index'/>)" onmouseover="this.style.color='blue';"  onmouseout="this.style.color='black'">
											<td>
												<s:if test='#status.index == 0'>
													&nbsp;<input type="radio" name="orderType" value="${map.dictCode }" checked/>
														<span style="cursor:default">${map.dictShortName }</span>
												</s:if><s:else>
													&nbsp;<input type="radio" name="orderType" value="${map.dictCode }"/>
														<span style="cursor:default">${map.dictShortName }</span>
												</s:else>
												<script type="text/javascript">
													function labelClick(event,index) {
														event = (event)?event:window.event;
														var src = event.srcElement||event.target;
    													if(src.type=='radio')return;
														if (!document.getElementsByName('orderType').item(index).checked)
															document.getElementsByName('orderType').item(index).checked = true;
													}
												</script>
											</td>
											
										</tr>
									</s:iterator> 
									
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 7px" type="button"
				onclick="window.open('${ctx}/orderInputAction!list','_self');">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 7px 5px" type="button"
				onclick="addOrder();">
				确 定
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>