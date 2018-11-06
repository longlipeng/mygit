<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title></title>
		<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
			function sub() {
				var billingSubject=document.getElementById("orderInvoiceInfoDTO.billingSubject").value;
				var invoiceDate=document.getElementById("orderInvoiceInfoDTO.invoiceDate").value;
				var invoiceCode=document.getElementById("orderInvoiceInfoDTO.invoiceCode").value;
				var invoiceNumber=document.getElementById("orderInvoiceInfoDTO.invoiceNumber").value;		
				if(IsSpace(billingSubject)){
					errorDisplay("开票 主体必须输入!");
					return;
				}
				if(IsSpace(invoiceDate)){
					errorDisplay("发票日期必须输入!");
					return;
				}
				if(IsSpace(invoiceCode)){
					errorDisplay("发票代码 必须输入!");
					return;
				}
				if(IsSpace(invoiceNumber)){
					errorDisplay("发票号码必须输入!");
					return;
				}
			    var addOrderInvoiceConfirmForm = document.getElementById("addOrderInvoiceConfirmForm");
		        addOrderInvoiceConfirmForm.action = '${ctx}/orderInvoice/addOrderInvoiceConfirm.action';
	        	addOrderInvoiceConfirmForm.submit();
			}
			
			//响应回车事件
            function bindEnter(obj) {
                if(obj.keyCode == 13){
                    sub();
                }
            }
		</script>
	</head>
	<body onkeyDown="bindEnter(event)">
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发票管理界面-->确认开票</span>
		</div>
		<div id="addOrderInvoiceConfirm"
			style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			<div id="addOrderInvoiceConfirmTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="TableTitleFront">
							<span class="TableTop">确认开票</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="addOrderInvoiceConfirmTable"
				style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="addOrderInvoiceConfirmForm" name="addOrderInvoiceConfirmForm" action="orderInvoice/addOrderInvoiceConfirm"
				method="post">
					<s:hidden name="orderInvoiceInfoDTO.invoiceId"></s:hidden>
					<table width="100%" style="table-layout: fixed;" >
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>开票主体:
										</td>
										<td>
											<s:textfield id="orderInvoiceInfoDTO.billingSubject" name="orderInvoiceInfoDTO.billingSubject"  />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>发票日期:
										</td>
										<td>
											<input type="text" id="orderInvoiceInfoDTO.invoiceDate" name="orderInvoiceInfoDTO.invoiceDate" 
												onclick="dateClick(this)" class="Wdate"   
												value="<s:date format="yyyy-MM-dd" name="orderInvoiceInfoDTO.invoiceDate" />"/>
										</td>
									</tr>
								</table>
								
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>发票代码:
										</td>
										<td>
											<s:textfield id="orderInvoiceInfoDTO.invoiceCode" name="orderInvoiceInfoDTO.invoiceCode" />
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 100px; text-align: right;">
											<span style="color: red">*</span>发票号码:
										</td>
										<td>
											<s:textfield id="orderInvoiceInfoDTO.invoiceNumber" name="orderInvoiceInfoDTO.invoiceNumber"  />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</s:form>
				</table>
			</div>
		</div>
	
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px"
				onclick="window.close();">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px" onclick="sub();">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>