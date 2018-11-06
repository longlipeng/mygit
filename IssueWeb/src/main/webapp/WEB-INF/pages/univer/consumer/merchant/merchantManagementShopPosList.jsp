<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>
		<title>查看Pos</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}
		</script>
	</head>
	
	<body>
	<%@ include file="/commons/messages.jsp"%>
		
	<div class="TitleHref">
		<span>查看Pos信息</span>
	</div>
	<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('posTable');"
									style="cursor: pointer;">
									<span class="TableTop">Pos信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="posTable">
					    <s:form id="posForm" name="posForm" action="" method="post">
                          <ec:table items="shopDTO.posList" var="shopPosDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="pos"
                              showPagination="false"
                              sortable="false">
              <ec:row>
               <ec:column property="termId" title="Pos编号" width="20%" />     
               <ec:column property="termStat" title="Pos状态" width="20%" />           
               <ec:column property="createTime" title="Pos创建时间" width="20%" />     
              </ec:row>
            </ec:table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="right">
					&nbsp;
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
            <button class='bt' style="float: right; margin: 5px 10px"
                onclick="window.close();">
                返 回
            </button>
        </div>
       </body>
</html>
