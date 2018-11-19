<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端厂商管理</title>
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
		
		function choosePosBrand(){
		   var posBrandId;
     	   var PosBrandIdList = document.getElementsByName('termBrandId');
     	    var brandName= document.getElementById('posBrandInfQueryDTO.brandName').value;
      	  for (var i=0; i<PosBrandIdList.length; i++) {
            if (PosBrandIdList[i].checked) {
                posBrandId = PosBrandIdList[i].value;
                break;
            }
        	}
          if(posBrandId == null) {
            alert("请选择终端厂商！");
          }
       	 if (posBrandId != null) {
            window.returnValue=posBrandId+','+brandName;
			window.close();
        	}  	
		}
	
		
		
		
		
	</script>
		<style type="text/css">
body,table,td,p,font,select {
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
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>

		<div class="TitleHref">
			<span>终端厂商管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
							<s:form id="queryForm" name="queryForm"
								action="choosePosBrandInf.action" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="150" align=right>
											<span>终端厂商标识：</span>
										</td>
										<td width="160">
											<s:textfield name="posBrandInfQueryDTO.termBrandId"></s:textfield>
											<s:fielderror>
												<s:param>
													posBrandInfQueryDTO.termBrandId
												</s:param>
											</s:fielderror>
											&nbsp;
										</td>
										<td width="150" align=right>
											<span>厂商名称：</span>
										</td>
										<td width="160">
											<s:textfield name="posBrandInfQueryDTO.brandName" />
											&nbsp;
										</td>						
										<td align="center">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="btn" onclick="queryForm.submit();">
										</td>
										
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
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
						<div id="TableBody">
							<s:form id="EditForm" name="EditForm"
								action="choosePosBrandInf.action" method="post">
								    <s:hidden name="posBrandInfQueryDTO.termBrandId"/>		
									<s:hidden name="posBrandInfQueryDTO.brandName" id="posBrandInfQueryDTO.brandName"/>			
								<ec:table items="pageDataDTO.data" var="map" width="100%"
									form="EditForm" action="${ctx}/choosePosBrandInf.action"
									imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
									retrieveRowsCallback="limit" autoIncludeParameters="false">	
									<ec:row onclick="">
										<ec:column property="null" alias="choose" title="选择"
											width="10%" sortable="false" headerCell="selectAll">
											<input type="radio" name="termBrandId"  value="${map.termBrandId},${map.brandName}" onclick="document.getElementById('posBrandInfQueryDTO.brandName').value='${map.brandName}'"  />										
										</ec:column>
										<ec:column property="termBrandId" title="终端厂商编号" width="50%">											
										</ec:column>
										<ec:column property="brandName" title="厂商名称" width="50%" />											
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
													onclick="choosePosBrand()" value="确定" />
												<input type="button" class="bt" style="margin: 5px"
													onclick="window.close()" value="返回" />
												
													<div style="clear: both"></div>
												</div>
												
												
										
												
																
										</td>
									</tr>
								</table>
							</s:form>
						</div>
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>