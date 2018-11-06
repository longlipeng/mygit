<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>卡信息编辑</title>
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
			 function getFullPath(obj) 
        	{ 
				 if(checkImg(obj)==false)
					{
					  return ;
					}
           		 if(obj) 
           		 { 
              	  //ie 
             	   if (window.navigator.userAgent.indexOf("MSIE")>=1) 
             	   { 
             	       obj.select(); 
             	       return document.selection.createRange().text; 
           		     } 
             	   //firefox 
             		   else if(window.navigator.userAgent.indexOf("Firefox")>=1) 
             		   { 
              		      if(obj.files) 
              		      { 
             		           return obj.files.item(0).getAsDataURL(); 
            		   	     } 
               		     return obj.value; 
            		    } 
             		   return obj.value; 
           		 } 
       		 } 
			 
			 function cardNamess()
			 {
				  
				var cardName=document.getElementById("cardName").value;
				 if(cardName.length<=0){
					 alert("卡名称不能为空");
				 } 
			 }
			   function checkImg(obj)
			    {
			    	var flag = true;
			    	var file=document.getElementById("picture");
			    	var i = file.value.lastIndexOf('.');
			    	var len= file.value.length;
			    	if(len!=0)
			    	{
				    	var extEndName=file.value.substring(i+1,len);
				    	var extName="GIF,BMP,JPG,JPEG";//首先对格式进行验证
				    	if(extName.indexOf(extEndName.toUpperCase())==-1){//filemessage.innerHTML="*您只能输入"+extName+"格式的文件"
				        	alert("*您只能输入"+extName+"格式的文件");
				        	flag = false;
				    	}
			    	}
			    	return flag;
			    }
			    function checketImagePreview(obj)
			    {
			    	//alert(checkImg(obj));
			    	if(checkImg(obj)==true)
					{
			    		newForm.submit();
					} 
			    }

		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>卡面信息编辑</span>
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
								<td class="TableTitleFront" onclick="displayServiceTable();"
									style="cursor: pointer;">
									<span class="TableTop">卡面信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="serviceTable">
							<s:form id="newForm" name="newForm"
								action="updateCardLayout" method="post"
								enctype="multipart/form-data">
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
														<s:textfield name="cardLayoutDTO.cardLayoutId" readonly="true"></s:textfield>
														
														
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
														<s:textfield name="cardLayoutDTO.cardName" id="cardName" onchange="cardNamess()" ></s:textfield>
														<s:fielderror>
															<s:param>
																cardLayoutDTO.cardName
															</s:param>
														</s:fielderror>
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
														<img name="img" id="img" src="${ctx }/product/getCardLayOutImg.action?id=${cardLayoutDTO.cardLayoutId}" height="90px"
															width="150px"/>
														<s:file name="picture" id="picture" onchange="document.getElementById('img').src=getFullPath(this);" ></s:file>
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
														<s:select list="#{1:'有效',0:'无效'}" name="cardLayoutDTO.validFlag"></s:select>
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
														   <edl:entityDictList displayName="cardLayoutDTO.backPanleInfo" dictValue="${cardLayoutDTO.backPanleInfo}" dictType="161" tagType="2" defaultOption="false" />					
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
														   <edl:entityDictList displayName="cardLayoutDTO.printFormat" dictValue="${cardLayoutDTO.printFormat}" dictType="159" tagType="2" defaultOption="false" />					
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
			<button class='bt' type="button" style="float: right; margin: 5px 10px"
				onclick="window.location='cardLayoutInquery.action'">
				返 回
			</button>
			<button class='bt' style="float: right; margin: 5px"
				onclick=" checketImagePreview(this);">
				提 交
			</button>
			<div style="clear: both"></div>
		</div>
	</body>
</html>
