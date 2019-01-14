<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>POS机明细表</title>
		<%@ include file="/commons/meta.jsp"%>
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

	  function choiceSeller() {
	    	var sellerDTO=window.showModalDialog('${ctx}/ireport/merchantChoose.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
	         if (sellerDTO != null) {
	        	 var string=sellerDTO.split(",");
	            document.getElementById('merchantCode').value = string[0];
	            document.getElementById('merchantName').value = string[1];
	        }
	    }
      function chooseShop(){
     	var shopDTO=window.showModalDialog('${ctx}/inquiryShop.action','_blank','center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
    	 if (shopDTO != null) {
	        	 var string=shopDTO.split(",");
	            document.getElementById('shopCode').value = string[0];
	            document.getElementById('shopName').value = string[2];
	        }
    }
	function chooseConsumer() {
        var consumerDTO = window.showModalDialog('${ctx}/consumer/chooseConsumer', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (consumerDTO != null) {
        	var array = consumerDTO.split(',');
			document.getElementById('consumerId').value = array[0];
			document.getElementById('consumerName').value = array[1];
        }
    }
    
    function submitForm(){
    	//var consumerId = document.getElementById('consumerId').value;
		//if(consumerId == null || consumerId == ""){
		//	errorDisplay("收单机构必须选择");
		//	return;
		//} 
		document.queryForm.submit();
    }	
		
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>POS机明细表</span>
		</div>
		<s:form id="queryForm" name="queryForm"
			action="/ireport/queryPOSdetailAction!viewReport.action" method="post">
				                <s:hidden name="posDetailDTO.reportName"></s:hidden>
								<s:hidden name="posDetailDTO.reportType"></s:hidden>
								<s:hidden name="posDetailDTO.issuerId"></s:hidden>
								<s:hidden name="posDetailDTO.issuerName"></s:hidden>
								<s:hidden name="posDetailDTO.reportFileName"></s:hidden>
			<div id="base"
				style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
				<div id="baseTitle"
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
				<div id="baseTable"
					style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<table width="80%" style="table-layout: fixed; text-align:center">
						

						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span >商户号：</span>
										</td>
										<td align="left">
											<s:textfield 
												name="posDetailDTO.mchntId"  id="merchantCode" />
										</td>
										</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span>商户名称：</span>
										</td>
										<td>
											<s:textfield
												name="posDetailDTO.mchntName" id="merchantName"/>
										</td>
									</tr>
								</table>
							</td>

						</tr>
						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span>门店号：</span>
										</td>
										<td align="left">
											<s:textfield 
												name="posDetailDTO.shopId" id="shopCode" />
										</td>
									</tr>
								</table>
							</td>
						
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span>门店名称：</span>
										</td>
										<td align="left">
										<s:textfield
												name="posDetailDTO.shopName"  id="shopName"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: right; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span >POS号：</span>
										</td>
										<td align="left">
											<s:textfield name="posDetailDTO.termId" id="termId"/>
										</td>
										</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 50%; text-align: right;">
											<span>厂商名称：</span>
										</td>
										<td>
											<s:textfield name="posDetailDTO.brandName" id="brandName" />
										</td>
									</tr>
								</table>
							</td>

						</tr>
					</table>
				</div>
			</div>
			<div id="buttonDiv" style="margin: 5px 8px 0px;">
				<table border="0" cellpadding="0" cellspacing="0" width="90%">
					<tr>
						<td align="right">
							<table border="0" cellpadding="0" cellspacing="0">
								<tr height="35">
									
									    <td align="right" colspan="3">
											 <input type="button" class="bt" style="margin: 5px" onclick="submitForm();" value="下 载 报 表"/></td>
									</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:form>
	</body>
</html>