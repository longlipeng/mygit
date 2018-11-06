<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
<title>结算数据清理</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/commons/messages.jsp"%>
	<div class="TitleHref">
		<span>结算数据清理</span>
	</div>

	<form action="${ctx}/coupon/common/sqlOperation.action" method="post">
        <label>操作：</label><select name="executor.operation">
            <option value="C">新增</option>
            <option value="U">更新</option>
            <option value="D">删除</option>
        </select>
		<label>对象：</label><input name="executor.table" type="text"></input>
		<label>其他内容：</label><input name="executor.others" type="text"></input>
		<button type="submit">提交</button>
	</form>
</body>
</html>