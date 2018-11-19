<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>设置外设</title>
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
			function setCookie()
			{
			    var exp = new Date();   
 				exp.setTime(exp.getTime() + 356*24*60*60*1000);  
				document.cookie="magneticCard=" +document.getElementById("magneticCard").value+":"+document.getElementById("passKeyboard").value+";expires=" + exp.toGMTString();;
			}
			
			function sub(){
				var magnetic=document.getElementById("magneticCard").value;
				var keyboard=document.getElementById("passKeyboard").value;
				var str = /^[0-9]{1,2}$/;
				if(!str.test(magnetic)||!str.test(keyboard)){
					alert('串口号应为两位数字');
					return;
				}else if(magnetic==keyboard){
					alert('磁卡机和密码键盘串口号不能一样');
					return;
				}
				setCookie();
				alert("设置成功");
				window.location="a.jsp";
			}
		</script>
	</head>
	<body>
		<OBJECT   ID="accorObj"  
                  CLASSID="clsid:FA98011A-77C1-4D9E-BC61-1F670FB75DF7"  HEIGHT=0 WIDTH=0
                  CODEBASE="cab/accor.CAB#version=1,0,0,1">  
  		</OBJECT>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>设置外设</span>
		</div>

		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0" align="center">
							<tr>
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">外设设置</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="" method="post">

								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>读写磁条卡机串口号：
													</td>
													<td>
														<input type="text" id="magneticCard" name="magneticCard" />
													</td>

												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>密码键盘串口号：
													

													</td>
													<td>
														<input type="text" name="passKeyboard" id="passKeyboard" />

													</td>
												</tr>
											</table>
										</td>
									</tr>



								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>


		<div id="btnDiv" style="text-align: right; width: 100%">
			<button class='bt' style="float: right; margin: 5px" onclick="sub();">
				确 定
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
