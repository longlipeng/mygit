<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>导入持卡人信息</title>
		<%@ include file="/commons/meta.jsp"%>
		<base target="_self">
		<script type="text/javascript">
			
			function submit() {
				importForm.action = "creditOrderAction!savaImportFile";
				importForm.check.value = "0";
				importForm.submit();
			}
			
			function check() {
				importForm.action = "creditOrderAction!savaImportFile";
				importForm.check.value = "1";
				importForm.submit();
			}
			
			function back() {			
				window.open('${ctx}/customerOrderAction!list','_self');
			}
		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<br>
		<s:form action="creditOrderAction!savaImportFile" name="importForm"
			id="importForm" method="POST" enctype="multipart/form-data">
			<div id="inputDiv" style="text-align:left; width:400px;">
				<s:hidden name="check" value="0"></s:hidden>
				<s:hidden name="orderId"></s:hidden>
				<s:hidden name="customerOrderDTO.customerName"></s:hidden>
				<s:hidden name="type"></s:hidden>
				<span>&nbsp;&nbsp;选择文件：</span><s:file name="cardholderFile" id="cardholderFile" />
			</div>
		</s:form>
		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px"
				onclick="back();">
				返 回
			</button>
		
			<button class='bt' style="float: right; margin: 5px"
				onclick="this.disabled='disabled';submit();var obj= this; setTimeout(function (){ obj.removeAttribute('disabled');},'5000'); ">
				提 交
			</button>	
			<!-- 
			<button class='bt' style="float: right; margin: 5px"
				onclick="check();">
				检 查
			</button>
			 -->
			<div style="clear: both"></div>
		</div>
	</body>
</html>