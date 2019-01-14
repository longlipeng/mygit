<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">	
			function query(){
				 var queryForm = Ext.get("queryForm").dom;
				 //TODO 2014-12-11 修改 报错
		 		 //queryForm['ec_eti'].disabled=true;
		 		 queryForm.action='${ctx}/stockCard/cardListView.action';
		 		 queryForm.submit();
			}
			
			function updateStockState(newStockState){
				var cardNoList = new Array();
				for(i = 0; i < document.getElementsByName("choose").length; i++) {
					if (document.getElementsByName("choose").item(i).checked) {
						var info = document.getElementsByName("choose").item(i).value;
						var cardNo= info.split(",")[0];
						var stockState = info.split(",")[1];
						if(newStockState == stockState){
							alert("卡"+cardNo+"状态不能重复修改，请重新选择！");
							return ;
						}
						cardNoList.push(cardNo);
					}
				}
				
				document.getElementById("selectCardNoList").value=cardNoList;
				document.getElementById("stockState").value = newStockState;
				updateStockStateForm.action = "${ctx}/stockCard/updateStockState.action";
				updateStockStateForm.submit();
			}
			
		</script>
		<title>库存卡片管理</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>库存卡片管理</span>
		</div>
		<div id="query"
			style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">查询条件</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="queryTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="queryForm" name="queryForm"
					action="stockCard/cardListView.action" method="post">
				 <s:hidden name="stockCardNoDTO.functionRoleId"/>
				  <s:hidden name="stockCardNoDTO.productId" />
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											卡号：
										</td>
										<td>
											<s:textfield name="stockCardNoDTO.cardNo" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 120px; text-align: right;">
											卡片有效期：
										</td>
										<td>
											<input type="text" name="stockCardNoDTO.cardValidDate"
												onclick="dateClick(this)" class="Wdate" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="query();" value="查 询" />
										</td>
									</tr>
								</table>
							</td>
							<td >
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
		<div id="list"
			style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
			<div id="listTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">卡号列表</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="listTable"
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="stockcardForm" name="stockcardForm" action="stockCard/cardListView.action" method="post">
			    	<s:hidden name="stockCardNoDTO.productId" />
			    	<s:hidden name="stockCardNoDTO.functionRoleId" />
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="stockcardForm" 
						tableId="cardList" 
						action="stockCard/cardListView.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:row> 
							<ec:column property="null" alias="choose" title="选择"
									width="5%"  headerCell="selectAll"
									viewsAllowed="html">
								<s:if test="#attr['map'].stockState == 1">
                            		<input type="checkbox" name="choose" value="${map.cardNo},${map.stockState}" />
                            	</s:if>
                            	<s:if test="#attr['map'].stockState == 6">
                            		<input type="checkbox" name="choose" value="${map.cardNo},${map.stockState}" />
                            	</s:if>
							</ec:column>
							
							<ec:column property="cardNo" title="卡号" width="45%"  >
							${map.cardNo }
							</ec:column>
							<ec:column property="cardValidDate" title="卡有效期" width="30%" >
							${map.cardValidDate }
							</ec:column>
							<ec:column property="stockState" title="库存状态" width="20%" >
								<s:if test="#attr['map'].stockState == 6">
									锁定
								</s:if>
								<s:if test="#attr['map'].stockState != 6">
									<edl:entityDictList dictValue="${map.stockState}" dictType="300" tagType="1"/>
								</s:if>
							</ec:column>
						</ec:row>
					</ec:table>
				</s:form>	
			</div>
		</div>
		<s:form id="updateStockStateForm" name="updateStockStateForm" action="" method="post" >
			<s:hidden name="stockCardNoDTO.productId" />
			<s:hidden name="stockCardNoDTO.functionRoleId" />
			<s:hidden name="stockCardNoDTO.stockState" id="stockState"/>
			<s:hidden name="selectCardNoList" id="selectCardNoList" />
			<input type="button" class="bt" style="margin: 5px"
												onclick="updateStockState(6)" value="锁 定" />
			<input type="button" class="bt" style="margin: 5px"
												onclick="updateStockState(1)" value="解除锁定" />
		</s:form>
	</body>
</html>