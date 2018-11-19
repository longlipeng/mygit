<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>开票项目选择</title>
<script type="text/javascript">
	function choose() {
		var sels = new Array();
		var objs = document.getElementsByName('itemIds');
		var ind = 0;
		for ( var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				sels[ind++] = objs[i].value;
			}
		}
		window.returnValue = sels;
		window.close();
	}
	function onload() {
		var ids = document.getElementById('invoiceItem').value.split(',');
		var objs = document.getElementsByName('itemIds');
		for ( var i = 0; i < objs.length; i++) {
			for ( var j = 0; j < ids.length; j++) {
				if (ids[j] == objs[i].value) {
					objs[i].checked = true;
				}
			}
		}
	}
</script>
</head>
<body onload="onload()">
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>开票项目选择</span>
	</div>
	<div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
		<div id="listTitle"
			style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="TableTitleFront"><span class="TableTop">记录列表</span>
					</td>
					<td class="TableTitleEnd">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="listTable"
			style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
			<s:hidden id="invoiceItem" name="invoiceItem" />
			<ec:table title="字典类型120中所有开票项目" items="pageDataDTO.data" var="map"
				action="${ctx}/customerInvoice/pickItem.action" method="post"
				width="100%" showPagination="false"
				imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
				retrieveRowsCallback="limit" autoIncludeParameters="false">
				<ec:row>
					<ec:column property="null" sortable="false" alias="itemIds"
						title="选择" headerCell="selectAll" width="10%">
						<input type="checkbox" name="itemIds" value="${map.dictCode}" />
					</ec:column>
					<ec:column property="dictCode" sortable="false" title="编号"
						width="10%" />
					<ec:column property="dictName" sortable="false" title="名称"
						width="30%" />
					<ec:column property="dictShortName" sortable="false" title="短名称"
						width="25%" />
					<ec:column property="dictEnglishName" sortable="false" title="英文名称"
						width="25%" />
				</ec:row>
			</ec:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><input type="button" class="btn"
									style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/save.gif) no-repeat; text-align: right"
									onclick="choose();" value="确 定" /></td>
								<td><input type="button" class="btn"
									style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
									onclick="window.close();" value="取 消" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>