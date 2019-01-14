<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>会员信息管理</title><script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
		<script type="text/javascript">
    	function edit(){
			var n=0;
			var id;
			var checkbox=document.getElementsByName('ra');
			for(i=0;i<checkbox.length;i++){
				if(checkbox[i].checked==true){
					id=checkbox[i].value;
					n++;
				}
			}
			if(n==0){
				alert('请选择要修改的账户号');
			}
			if(n>1){
				alert('编辑对象只能是一个');
			}
			if(n==1){
				
				document.queryForm.action='cardManagement!memberInfoLoad.action?email='+id+'&n=a';
				document.queryForm.submit();
			}
		}
</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>会员信息管理</span>
		</div>
		<s:form id="queryForm" name="queryForm"
					action="member/memberInfoInquery.action" method="post">
		<div id="query"
			style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
			
			<div id="queryTitle"
				style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
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
				
					<table width="100%" style="table-layout: fixed;">
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											会员姓名：
										</td>
										<td>
											<s:textfield name="memberQueryDTO.realNm" size="23"/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											电子邮箱：
										</td>
										<td>
											<s:textfield name="memberQueryDTO.email"/>
										</td>
									</tr>
								</table>
							</td>
							</tr>
							<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											证件类型：
										</td>
										<td>
										<s:select name="memberQueryDTO.certTp" list="#{'':'请选择','00':'身份证','01':'护照','10':'出生证','11':'工作证'}"></s:select>
											
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											证件号码：
										</td>
										<td>
										<s:textfield name="memberQueryDTO.certNo"/>	
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											手机号码：
										</td>
										<td>
											<s:textfield name="memberQueryDTO.mobile"/>
										</td>
									</tr>
								</table>
							</td>
						<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											公司名称：
										</td>
										<td>
											<s:textfield name="memberQueryDTO.corpNm"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											卡号：
										</td>
										<td>
											<s:textfield name="memberQueryDTO.cardNo"/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="text-align: left; width: 100%">
									<tr>
										<td style="width: 90px; text-align: right;">
											注册类型：
										</td>
										<td>
										<s:select name="memberQueryDTO.regCardTp" list="#{'':'请选择','02':'在线卡注册','01':'实体卡注册'}"></s:select>
										</td>
									</tr>
								</table>
							</td>
									</tr>
									<tr>
									<td align="right" colspan="3">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="button" class="bt" style="margin: 5px"
												onclick="javascript:document.forms.queryForm.ec_eti.value='';queryForm.submit();" value="查 询" />
										</td>
									
									</tr>
								</table>
							</td>
						</tr>
					</table>
				
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
				style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
					<ec:table items="pageDataDTO.data" var="map" width="100%"
						form="queryForm"
						action="member/memberInfoInquery.action"
						imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
						retrieveRowsCallback="limit" autoIncludeParameters="false">
						<ec:exportXls fileName="memberInfoList.xls" tooltip="导出Excel"/>
						<ec:row>
							<ec:column title="姓名" property="memName">
							<a href="member/memberDetailInfoInquery.action?memberQueryDTO.email=${map.memEmail}">
                            	${map.memName}
                            </a>
                            </ec:column>
							<ec:column title="性别" property="memGener"/>
							<ec:column title="出生日期" property="memBirthday"/>
							<ec:column title="电子邮箱" property="memEmail"/>
							<ec:column title="证件类型" property="certTp"/>
							<ec:column title="证件号码" property="certNo"/>
							<ec:column title="手机号码" property="mobile"/>
							<ec:column title="注册类型" property="regCardTp"/>
							<ec:column title="注册时间" property="creatDate" cell="date" format="yyyy/MM/dd HH:mm:ss"/>
						</ec:row>
					</ec:table>
				
			</div>
		</div>
		</s:form>
	</body>
</html>