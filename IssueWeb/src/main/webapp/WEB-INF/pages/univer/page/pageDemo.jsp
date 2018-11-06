<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 TRansitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dTD">
<html>
	<head>
			<%@ include file="/commons/meta.jsp"%>
		<script src="${ctx}/widgets/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
		<title>demo</title>
		<link href="css/typography.css" type="text/css" rel="stylesheet">
		<link href="css/report20090923.css" type="text/css" rel="stylesheet">
		<style>
.clickBorder {
	border: 1px solid #818;
}

.blurBorder {
	border: 1px solid #7F9DB9;
}
</style>
	</head>
	<body>
		<form id="Form1" method="post" runat="server">
			<table id="QueryDataTable" cellspacing="1" summary="百克网新闻列表"
				style="width: 600px;">
				<caption>
					DEMO
				</caption>
				<thead>
					<tr>
						<th width="20%">
							名 称
						</th>
						<th width="20%">
							单 价
						</th>
						<th width="20%">
							数 量
						</th>
					</tr>
				</thead>
				<tbody>
					<tr class="odd">
						<td>
							<input type="text" name="txtGoodsName" value="测试1" />
						</td>
						<td>
							<input type="text" name="txtPrice" value="32.00" />
						</td>
						<td>
							<input type="text" name="txtQuantity" value="2" />
						</td>
					</tr>
					<tr class="even">
						<td>
							<input type="text" name="txtGoodsName" value="测试2" />
						</td>
						<td>
							<input type="text" name="txtPrice" value="45.00" />
						</td>
						<td>
							<input type="text" name="txtQuantity" value="8" />
						</td>
					</tr>
					<tr class="odd">
						<td>
							<input type="text" name="txtGoodsName" value="测试3" />
						</td>
						<td>
							<input type="text" name="txtPrice" value="12.00" />
						</td>
						<td>
							<input type="text" name="txtQuantity" value="" />
						</td>
					</tr>
				</tbody>
			</table>
	
			<script type="text/javascript">
				$(document).ready(
					function() {
						var className = "even", html, obj;
						$("#QueryDataTable tbody tr:last td:last input")
								.live(
										"click",
										function() {
											this.className = "clickBorder";
											className = ($(this).closest("tr")
													.get(0).className == "odd") ? "even"
													: "odd";
											html = [];
											html.push("<tr class='")
											html.push(className)
											html.push("'>");
											html
													.push("<td><input type='text' name='txtGoodsName' value='' /></td>");
											html
													.push("<td><input type='text' name='txtPrice' value='' /></td>");
											html
													.push("<td><input type='text' name='txtQuantity' value='' /></td>");
											html.push("</tr>");
											if (/^\d+$/.test(this.value)) {
												$(this).closest("tr").after(
														html.join(''));
											} else {
											}
											$(this).blur(function() {
												this.className = "blurBorder";
											});
										});
					});
			</script>
		</form>
	</body>
</html>