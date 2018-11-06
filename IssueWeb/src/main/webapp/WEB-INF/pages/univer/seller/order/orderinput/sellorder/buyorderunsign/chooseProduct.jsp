<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>库存信息</title>
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

	function chooseProduct() {
        var productId;
        var productIdList = document.getElementsByName('productId');    
        for (var i=0; i<productIdList.length; i++) {
            if (productIdList[i].checked) {
            	productId = productIdList[i].value;
            }
        }
        if (productId == null) {
            alert("请选择产品信息!");
        }
        if (productId != null) {
            window.returnValue=productId;
			window.close();
        }  
    }
    
    
    
    
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>库存信息</span>
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
						<s:form id="stockForm" name="stockForm"
							method="post">
							<div id="TableBody">
								<ec:table items="entityStockList" var="entityStockDTO" width="100%"
									form="merchantxForm"
									action="${ctx}/selectMerchant"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									 autoIncludeParameters="false" showPagination="false"
									sortable="false">
									<ec:row onclick="">
										<ec:column property="null" title="选择"
											width="10%" sortable="false">
											<input type="radio" name="productId"
												value="${entityStockDTO.productId},${entityStockDTO.productName}" />
										</ec:column>
										<ec:column property="productId" title="产品编号" width="10%"></ec:column>											
										<ec:column property="productName" title="产品名称" width="10%" />
										<ec:column property="cardLayoutName" title="卡面名称" width="15%" />
										<ec:column property="faceValueType" title="面额类型" width="15%" />
										<ec:column property="faceValue" title="面额值" width="10%" />
										<ec:column property="cardQuantity" title="库存数量" width="10%" />
									</ec:row>
								</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<input type="button" class="bt" style="margin: 5px"
													onclick="chooseProduct()" value="确定" />
												<input type="button" class="bt" style="margin: 5px"
													onclick="window.close()" value="返回" />
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</s:form>
					</td>
				</tr>
			</table>

		</div>

		<br>
	</body>
</html>