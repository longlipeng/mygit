<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<style>
		 #inhalt {  
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
		var box;
		var userChoice="";
		
	    function submitForm(){

		var startDateString = document.getElementById('startDate').value;
	
		var endDateString = document.getElementById('endDate').value;
		var startDate=new Date(startDateString.replace(/-/g, '/'));
		var endDate= new Date(endDateString.replace(/-/g, '/ '));
		if(startDate > endDate){
			errorDisplay("开始日期不能大于结束日期");
			return;
		}
		document.queryForm.submit();
    }
	      
         function receiveInvoice(id) {
			 document.getElementById("hidInvoiceId").value=id;
			 document.getElementById("inhalt").style.display='';
			 document.getElementById("popupForm").action='${ctx}/orderInvoice/receiveInvoice.action';
			 
		 }
		 
		 function handInvoice(id) {
			 document.getElementById("hidInvoiceId").value=id;
			 var popupForm = document.getElementById("popupForm");
			 popupForm.action='${ctx}/orderInvoice/handInvoice.action';
			 popupForm.submit();
			 
		 }

		function closeWin(){
			 document.getElementById("inhalt").style.display='none';
		}
		 
		 
	  	  	
		</script>
		<title>发票管理界面</title>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>发票管理界面</span>
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
					action="invoiceReceive.action" method="post">
					<s:hidden name="type"></s:hidden>
					<table width="100%" style="table-layout: fixed;">

						<tr height="35">
							<td width="85" align=right>
								<span>发票名称：</span>
							</td>
							<td width="160">
								<s:textfield name="invoiceReceiveQueryDTO.name" />
								<s:fielderror>
									<s:param>invoiceReceiveQueryDTO.name</s:param>
								</s:fielderror>
							</td>
							<td width="85" align=right>
								<span>发票类型：</span>
							</td>
							<td width="160">
								<s:select id="invoiceReceiveQueryDTO.type" name="invoiceReceiveQueryDTO.type" headerKey="" headerValue="--请选择--"
							list="#{'1':'增值税专用发票','2':'地税票'}"></s:select>
							</td>
						</tr>
						<tr height="35">
							<td width="85" align=right>
								<span>客户编码：</span>
							</td>
							<td width="160">
								<s:textfield name="invoiceReceiveQueryDTO.customerEntityId" />
								<s:fielderror>
									<s:param>invoiceReceiveQueryDTO.customerEntityId</s:param>
								</s:fielderror>
							</td>
							<td width="85" align=right>
								<span>开票项目：</span>
							</td>
							<td width="160">
									<edl:entityDictList dictType="120" tagType="2" defaultOption="true"
									displayName="invoiceReceiveQueryDTO.invoiceProject"
									dictValue="${invoiceReceiveQueryDTO.invoiceProject}"></edl:entityDictList>
								<s:fielderror>
									<s:param>invoiceReceiveQueryDTO.invoiceProject</s:param>
								</s:fielderror>
							</td>
						</tr>
						<tr height="35">
							<td width="85" align=right>
								<span>商户编码：</span>
							</td>
							<td width="160">
								<s:textfield name="invoiceReceiveQueryDTO.mchtEntityId" />
								<s:fielderror>
									<s:param>invoiceReceiveQueryDTO.mchtEntityId</s:param>
								</s:fielderror>
							</td>
							<td width="90" align=right>
								<span>发票状态：</span>
							</td>
							<td width="160">
								<dl:dictList dictType="456" tagType="2" defaultOption="true"
									displayName="invoiceReceiveQueryDTO.status"
									dictValue="${invoiceReceiveQueryDTO.status}"></dl:dictList>
								<s:fielderror>
									<s:param>invoiceReceiveQueryDTO.status</s:param>
								</s:fielderror>
							</td>
						</tr>
							<tr>
										<td style="width: 140px; text-align: right;">
											<span >开始时间：</span>
										</td>
										<td>
											<s:textfield name="invoiceReceiveQueryDTO.startDate" id="startDate"
												 size="20" onfocus="dateClick(this)"
												cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>dto.startDate</s:param>
											</s:fielderror>
										</td>
										<td style="width: 190px; text-align: right;">
											<span >结束时间：</span>
										</td>
										<td>
											<s:textfield name="invoiceReceiveQueryDTO.endDate" id="endDate"
												 size="20" onfocus="dateClick(this)" cssClass="Wdate">
											</s:textfield>
											<s:fielderror>
												<s:param>dto.endDate</s:param>
											</s:fielderror>
										</td>
									</tr>

							</td>
								</table>


						</tr>
							<td align="center" colspan="2">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="submitForm();" value="查 询" />
										</td>
									</tr>
								</table>
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
							<span class="TableTop">记录列表</span>
						</td>
						<td class="TableTitleEnd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="listTable" 
				style="width:100%;background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
				<s:form id="invoiceReceiveForm" name="invoiceReceiveForm"
					action="invoiceReceive.action" method="post">
					<s:hidden name="invoiceReceiveQueryDTO.name"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.type"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.customerEntityId"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.invoiceProject"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.openInvoicer"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.status"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.startDate"></s:hidden>
					<s:hidden name="invoiceReceiveQueryDTO.endDate"></s:hidden>
					<ec:table items="invoiceInfos" var="map" width="100%" style="width:100%"
						tableId="invoiceInfo" form="invoiceReceiveForm"
						action="invoiceReceive.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="orderInvoiceList.xls" tooltip="导出Excel" />
						<ec:row>
						    <ec:column property="name" title="发票名称" width="18%"/>
						    <ec:column property="customerEntityId" title="收票方" width="6%"/>
							<ec:column property="mchtEntityId"  title="开票方" width="6%"/>
							<ec:column property="customerName" title="商户名称" width="6%"/>
							<ec:column property="amount"  title="发票金额(单位：分)"  width="12%"/>
							<ec:column property="invoiceProject"  title="开票项目" 
							cell="entityDictInfo" alias="120" width="10%"/>
								
							<ec:column property="type"  title="发票类型" width="10%">
							<c:choose>
								<c:when test="${map.type=='1'}">增值税专用发票</c:when>
								<c:when test="${map.type=='2'}">地税票</c:when>
							</c:choose>
							</ec:column>
							<ec:column property="createdTime"  title="发票录入日期" cell="date"
								format="yyyy-MM-dd"  width="10%" />
							<ec:column property="status"  title="发票状态"   
								cell="dictInfo" alias="456" width="6%"/>
							<ec:column property="invoiceNO"  title="发票号" style="word-break:break-all;width:100px;" />
							
							<ec:column property="null" title="开票按钮"  width="10%" >
                                <c:if test="${map.status =='0'}">
	                                <display:security urlId="8033010">
	                                     <input type="button" class="btn"
	                                         style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
	                                         onclick="receiveInvoice(${map.id});" value="收票" />
	                                 </display:security>
                                 </c:if>
                                 <c:if test="${map.status =='1'}">
                                 	<display:security urlId="8033020">
										<input type="button" class="btn"
											style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
											onclick="handInvoice(${map.id});" value="交票" />
									</display:security>
                                 </c:if>
                               </ec:column>
						</ec:row>
					</ec:table>
				</s:form>
			</div>
		</div>
		<div id="inhalt" style = "display: none">
		<center>
			<form id="popupForm" action="">
			<br/><br/><br/><br/>
			  <input type="hidden" id="hidInvoiceId" name="invoiceId" value=""/>
			  <lable>发票号</lable><textarea name="invoiceNO" cols="50" rows="3"></textarea><br/><br/>
			  <input type="submit" value="提 交"/>
			  <input type="button" value="关 闭 " onclick="closeWin()" />
			</form>
		</center>
		</div>
	</body>
</html>