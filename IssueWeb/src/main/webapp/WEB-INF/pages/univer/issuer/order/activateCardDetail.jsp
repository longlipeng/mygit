<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
		function backForm(){
			queryForm.action="${ctx}/batchfile/batchActivateListAction.action";
			document.queryForm.submit();
		}
</script>
<title>查看批次中卡片信息</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看批次中卡片信息</span>
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
        <div id="listTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
        <div id="listTable" style="background-color: #FBFEFF; padding: 0px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="batchActivateForm"  name="batchActivateForm" action="batchfile/activateView.action" method="post">
                  <s:hidden name="batchActivateDTO.batchNo"/>
                 <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="batchActivateForm"
                    action="${ctx}/batchfile/activateCardView.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:exportXls fileName="ActivateList.xls" tooltip="导出Excel" />
                    <ec:row>
                      	<ec:column property="cardNo" escapeAutoFormat="true" title="卡号" width="15%" />
                    </ec:row>
                </ec:table>
               
            </s:form>
        </div>
    </div>
</body>
</html>