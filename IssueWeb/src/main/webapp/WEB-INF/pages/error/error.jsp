<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="xxl.common.prompt"/></title>
		<%@ include file="/commons/meta.jsp"%>
		
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
		

		<div class="TitleHref">
			<span></span>
		</div>
		<div id="ContainBox">		
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="B5B8BF" align="center">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop"><s:text name="xxl.common.prompt"/></span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>								
							</tr>
							
						</table>
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td>						
								<%@ include file="/commons/messages.jsp"%>
								</td>
							</tr>
	
						</table>
						
		<br>
		<br>
		
						<!-- div id=TableBody -->
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>