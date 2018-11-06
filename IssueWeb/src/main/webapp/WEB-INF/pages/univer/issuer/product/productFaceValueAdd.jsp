<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>面额管理</title>
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
		function sub() {
			var faceValueTypeCount = ${faceValueTypeCount};
			/* var s = document.getElementById('faceValueType').value + ','
					+ document.getElementById('faceValue').value;
			var blance = ${productDTO.maxBalance};
			var onymousStat = ${productDTO.onymousStat};
	
			var faceValueTypeCount = ${faceValueTypeCount};
	
			var temp = document.getElementById('faceValue').value;
			var faceValueType = document.getElementById('faceValueType').value; */
			/* if (onymousStat != 2) {
				if (faceValueType == 0) {
					errorDisplay("记名卡不可以添加固定面额");
					return false;
				}
			}
			if (faceValueType == 0) { */
				/*
				if(temp.search(/^(([1-9]{1}[0-9]{0,7})|([0]))([.]\d{1,2})?$/)){
					errorDisplay("整数不超过8位(两位以上整数首位不能为零),小数位不超过2位!");
					return false;
				}*/
				/* if (temp.search(/^(([1-9]{1}[0-9]{0,9}))$/)) {
					errorDisplay("请输入整数,不过十位整数!");
					return false;
				}
				if (temp > blance) {
					errorDisplay("面额不能高于产品的最大余额");
					return false;
				}
			}
			if (document.getElementById('faceValueType').value == 1) {
	
				if (faceValueTypeCount >= 1) {
	
					errorDisplay("非固定面额只能添加一条");
					return false;
				}
			} */
			var onymousStat = ${productDTO.onymousStat};
			var s = "";
			if(onymousStat == 2){
				 var type = document.getElementById('faceValueType2').value;
				 var banlances =document.getElementsByName("dictType.cardBanlance");
				  var banlance = banlances[0].value;
				  if(banlance==null||banlance==''){
					  errorDisplay("请选择面额！");
					  return false;
				  }
				  s = type+","+banlance;
			}else{
				if (faceValueTypeCount >= 1) {
					
					errorDisplay("非固定面额只能添加一条");
					return false;
				}
				
				 var type = document.getElementById('faceValueType').value;
				 var banlance = document.getElementById('faceValue').value;
				 if(banlance==null||banlance==''){
					  errorDisplay("请选择面额！");
					  return false;
				  }
				 s = type+","+banlance;
			}
			window.returnValue = s;
			window.close();
	
		}
		 function choosemoeny() {
			var s = document.getElementById('faceValueType').value; 
			if (s == 1) {
				document.queryForm.faceValue.value = 0;
				document.queryForm.faceValue.readOnly = true;
			} else {
				document.queryForm.faceValue.value = "";
				document.queryForm.faceValue.readOnly = false;
			}
		} 
		
		 function checkProductType(){
			 var onymousStat = ${productDTO.onymousStat};
			 var queryCondition=document.getElementById("queryCondition");  
			 var queryCondition2=document.getElementById("queryCondition2");
			 if(onymousStat == 2){
				 queryCondition.style.display="none";
				 queryCondition2.style.display = "block";
        	    
			 }else{
				 queryCondition.style.display="block";
				 queryCondition2.style.display = "none";
			 }
		 }
	</script>
	
	<style type="text/css">
	body, table, td, p, font, select {
		font-size: 9pt;
	}
	
	.bt {
		background: transparent url(${ctx}/images/button_bg.gif) repeat scroll 0
			0;
		border: 1px solid #7DE7FD;
		font-size: 9pt;
		height: 22px;
		cursor: pointer;
	}
	
	.bt:HOVER {
		background: transparent url(${ctx}/images/button_bg2.gif) repeat scroll
			0 0;
		border: 1px solid #C3A336;
	}
	
	.btn {
		cursor: pointer;
		border: #FFFFFF 1px solid;
	}
	
	.btn:HOVER {
		background-color: F0FFE1;
		border: #93B3CA 1px solid;
	}
	</style>
	<base target="_self">
</head>
<body onload="checkProductType()">
	<div class="TitleHref">

		<span>添加面额明细</span>
	</div>
	<div id="query" style="border: 1px solid #B9B9B9; margin-top: 5px;">
		<div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront">
						<span class="TableTop">面额信息</span>
					</td>
					<td class="TableTitleEnd">
                            &nbsp;
                    </td>
				</tr>
			</table>
		</div>

		<div id="queryCondition"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;display:none;">
			<form id="queryForm" name="queryForm" action="" onsubmit="sub()"
				method="post" style="padding: 0px; margin: 0px;">
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<tr height="35">
						<td width="150" align=right><span>是否固定面额：</span></td>
							<td width="160"><s:select list="#{1:'不固定'}"
								id="faceValueType" /></td> 
						<td width="150" align=right><span>面额：</span></td>
						<td width="120"><s:textfield id="faceValue" value ='0' readonly="true"></s:textfield></td>
						<td>元</td>
					
					</tr>
				</table>
				<table width="100%" height="20" border="0" cellpadding="0"
					cellspacing="0" style="border-top: 1px solid silver;">
					<tr>
						<td>
							<div id="buttonCRUD"
								style="text-align: right; width: 100%; height: 30px;"
								valign="middle">

								<div id="deleteBtn" class="btn"
									style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
									onclick="window.close();">关闭</div>
								<div id="btnDiv" style="text-align: right; width: 100%">
									<div id="addBtn" class="btn"
										style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="sub()">确定</div>
									<div style="clear: both"></div>
								</div>
								</div>
						</td>
					</tr>
				</table>

			</form>
		</div>
		
		
			<div id="queryCondition2"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;display:none;">
			<form id="queryForm" name="queryForm2" action="" onsubmit="sub()"
				method="post" style="padding: 0px; margin: 0px;">
				<table width="100%" height="30" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<tr height="35">
						<td width="150" align=right><span>是否固定面额：</span></td>
							<td width="160"><s:select list="#{0:'固定'}"
								id="faceValueType2"  /></td> 
						<td width="150" align=right><span>面额：</span></td>
						<td width="120">
						<edl:entityDictList displayName="dictType.cardBanlance" dictValue="${dictType.cardBanlance}" dictType="504" tagType="2" defaultOption="true" />
						</td>
					</tr>
				</table>
				<table width="100%" height="20" border="0" cellpadding="0"
					cellspacing="0" style="border-top: 1px solid silver;">
					<tr>
						<td>
							<div id="buttonCRUD"
								style="text-align: right; width: 100%; height: 30px;"
								valign="middle">

								<div id="deleteBtn" class="btn"
									style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
									onclick="window.close();">关闭</div>
								<div id="btnDiv" style="text-align: right; width: 100%">
									<div id="addBtn" class="btn"
										style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
										onclick="sub()">确定</div>
									<div style="clear: both"></div>
								</div>
								</div>
						</td>
					</tr>
				</table>

			</form>
		</div>
	</div>
</body>
</html>