<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
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
		
			function checkLength() {
				var obj = document.getElementById("newForm_cardLayoutDTO_cardName");
				var str = obj.value;
				console.log(str)
				///<summary>获得字符串实际长度，中文2，英文1</summary>
				///<param name="str">要获得长度的字符串</param>
				var realLength = 0, len = str.length, charCode = -1;
				for (var i = 0; i < len; i++) {
					charCode = str.charCodeAt(i);
					if (charCode >= 0 && charCode <= 128)
						realLength += 1;
					else
						realLength += 2;
				}
				if (realLength > 128) {
					return false;
				}
				return true;
			};
			function setImagePreview(obj) {
		
				if (checkImg(obj) == false) {
					return;
				}
				var objDivFilter = document.getElementById("divFilter")
				if (obj) {
					if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
		
						obj.select();
						objDivFilter.style.width = "90px";
						objDivFilter.style.height = "150px"; //这个设置初始大小是必须的 
		
						objDivFilter.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = image)";
						objDivFilter.filters
								.item("DXImageTransform.Microsoft.AlphaImageLoader").src = document.selection
								.createRange().text;
		
						// get width and height 
					}
		
					//firefox 
					else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
						if (obj.files) {
							obj.files.item(0).getAsDataURL();
						}
						// return obj.value; 
					}
					// return obj.value; 
				}
			}
			function checkImg(obj) {
				var flag = true;
				var _file = document.getElementById("picture");
				var i = _file.value.lastIndexOf('.');
				var len = _file.value.length;
				if (len == 0) {
					alert("请添加图片")
					return false;
				}
				var extEndName = _file.value.substring(i + 1, len);
				var extName = "GIF,BMP,JPG,JPEG,PNG";//首先对格式进行验证
				if (extName.indexOf(extEndName.toUpperCase()) == -1) {
					//filemessage.innerHTML="*您只能输入"+extName+"格式的文件"
					alert("*您只能添加" + extName + "格式的文件");
					flag = false;
				}
				return flag;
			}
			function checketImagePreview(obj) {
				//alert(checkImg(obj));
				if(checkCardName()){
					if (checkImg(obj) == true) {
						newForm.submit();
					}
				}
		
			}
			
			
			function checkCardName(){
				$("#cardName_msg").empty("");
				var cardName = document.getElementById("cardName").value;
				if(cardName==""){
					$("#cardName_msg").html('<ul class="errorMessage"><li><span>请填写卡面名称(80位内)</span></li></ul>');
					return false;
				}
				if(cardName.length>80){
					$("#cardName_msg").html('<ul class="errorMessage"><li><span>请填写80位内卡面名称！</span></li></ul>');
					return false;
				}
				return true;
			}
			
		</script>
		
		
		<title>卡信息添加</title>
		<%@ include file="/commons/meta.jsp"%>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>新增卡面信息</span>
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
								<td class="TableTitleFront" onclick=displayServiceTable();;
									style="cursor: pointer;">
									<span class="TableTop">卡面信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm" action="cardLayoutSave"
								method="post" enctype="multipart/form-data">
								<s:token></s:token>
								<table width="100%" style="table-layout: fixed;">
									<tr>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														卡面号：
													</td>
													<td>
														<s:textfield name="cardLayoutDTO.cardLayoutId"
															readonly="true"></s:textfield>

													</td>
												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>卡面名称：
													</td>
													<td>
														<s:textfield name="cardLayoutDTO.cardName" id="cardName" onchange="checkCardName()"></s:textfield>
														<div id="cardName_msg">
															<s:fielderror>
																<s:param>
																	cardLayoutDTO.cardName
																</s:param>
															</s:fielderror>
														</div>
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
														图片：
													</td>

													<td>
														<div id="divFilter">
														</div>
														<!-- <img name="image" id="img"  src="D:\My Documents\My Pictures\40de96e2c9fe28a130add106.jpg" height="90px" width="150px" style="display: none" >  -->
														<s:file name="picture" id="picture"
															onchange="document.getElementById('divFilter').style.display='';setImagePreview(this); "></s:file>
													</td>

												</tr>
											</table>
										</td>
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>有效标记：
													</td>
													<td>
														<s:select list="#{1:'有效',0:'无效'}"
															name="cardLayoutDTO.validFlag"></s:select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>

										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														描述信息：
													</td>
													<td>

														<s:textarea name="cardLayoutDTO.memo" cols="70" rows="5"></s:textarea>
													</td>
												</tr>
											</table>
										</td>

									</tr>
									<tr>

										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														底板信息：
													</td>
													<td>
														<edl:entityDictList
															displayName="cardLayoutDTO.backPanleInfo"
															dictValue="${cardLayoutDTO.backPanleInfo}" dictType="161"
															tagType="2" defaultOption="false" />
													</td>
												</tr>
											</table>
										</td>

									</tr>

									<tr>
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														打印格式：
													</td>
													<td>
														<edl:entityDictList
															displayName="cardLayoutDTO.printFormat"
															dictValue="${cardLayoutDTO.printFormat}" dictType="159"
															tagType="2" defaultOption="false" />
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
			<button class='bt' type="button"
				style="float: right; margin: 5px 10px" onclick="window.location= 'cardLayoutInquery.action';">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick="checketImagePreview(this);">
				提 交
			</button><!-- maskDocAll(); -->
			<div style="clear: both"></div>
		</div>
	</body>
</html>




