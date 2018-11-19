<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>商户黑名单管理</title>
        <%@ include file="/commons/meta.jsp"%>
  <script type="text/javascript">
    function checkedCount() {
        var checkboxs = document.getElementsByName('mchntNoList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }
        
   function queryBlackListMchnt() {
        var blackListMchntForm = Ext.get("blackListMchntForm").dom;
        blackListMchntForm.action = '${ctx}/blackListMchnt/inquiry.action';
        blackListMchntForm.submit();
    }
  
  function deleteBlackListMchnt() {
        var count = checkedCount();
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
       confirm("确认要删除吗?",operDeleteShop);
    }
	function operDeleteShop(){
		var blackListMchntForm = Ext.get("blackListMchntForm").dom;
        blackListMchntForm.action = '${ctx}/blackListMchnt/delete.action';
        blackListMchntForm.submit();
	}
	
	function addBlackListMchnt(){
        var blackListMchntForm = Ext.get("blackListMchntForm").dom;
        blackListMchntForm.action = '${ctx}/blackListMchnt/add.action';
        blackListMchntForm.submit();
    }
  </script>
      
    </head>
    <body >
        <%@ include file="/commons/messages.jsp"%>
        
        <div class="TitleHref">
            <span>黑名单信息管理</span>
        </div>
      <s:form id="blackListMchntForm" name="blackListMchntForm"
            action="blackListMchnt/inquiry" method="post">
        <div id="ContainBox">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayQueryBody();"
                                    style="cursor: pointer;">
                                    <span class="TableTop">查询条件</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        <div id="QueryBody">
                            <table>
                                    <tr height="35">
                                        <td width="85" align=right>
                                            <span>商户号：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="blackListMchntQueryDTO.mchntNo" ></s:textfield>
                                        </td>
                                        <td width="90" align=right>
                                            <span>备注：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="blackListMchntQueryDTO.meno"/>
                                        </td>
                                    </tr>

                                         <td width="85" align=right>
                                            <span></span>
                                        </td>
                                        <td width="160">
                                         <input type="button" class="bt" style="margin: 5px" onclick="queryBlackListMchnt()" value="查 询"/>
                                        </td> 
                                        
                                       
                                      
                                                                               
                                    </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

        </div>
        <br>
        <br>
        <div style="width: 100%" align=center>
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="98%" height="10" align="center" valign="middle"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTableBody();"
                                    style="cursor: pointer;">
                                    <span class="TableTop">记录列表</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                           
                            <tr>
                        </table>
                        
                        <div id="TableBody">   
                       
                            <ec:table items="pageDataDTO.data" var="map" width="100%"
                                form="blackListMchntForm"
                                action="${ctx}/blackListMchnt/listAll"
                                imagePath="${ctx}/images/extremecomponents/*.gif" 
                                view="html"
                                retrieveRowsCallback="limit"
                                autoIncludeParameters="false">
                                <ec:row onclick="">
                                    <ec:column property="null" alias="mchntNoList" title="选择" width="10%" sortable="false" headerCell="selectAll">
                                        <input type="checkbox" name="mchntNoList" value="${map.mchntNo}" />
                                    </ec:column>
                                    <ec:column property="mchntNo" title="商户号" width="10%"><!--
                                     <edl:entityDictList dictValue="${map.mchntNo}" dictType="403" tagType="1" defaultOption="false" /> -->
                                     ${map.mchntNo}
                                   </ec:column>
                                    <ec:column property="meno" title="备注" width="10%"/>
                                    <ec:column property="createUser" title="创建者"  width="15%">
                                    	<edl:entityDictList dictValue="${map.createUser}" dictType="405" tagType="1" defaultOption="false" />
                                    </ec:column>
                                    <ec:column property="createTime" title="创建时间"  width="15%">
                                    	<edl:entityDictList dictValue="${map.createTime}" dictType="408" tagType="1" defaultOption="false" />
                                    </ec:column>
                                    <ec:column property="modifyUser" title="修改人" width="10%"/>
                                    <ec:column property="modifyTime" title="修改时间"  width="15%"/>                                
                                    <ec:column property="dataState" title="数据状态" width="10%"/>
                                </ec:row>
                            </ec:table>
                                <table width="100%" height="20" border="0" cellpadding="0"
                                    cellspacing="0" style="border-top: 1px solid silver;">
                                    <tr>
                                        <td>
                                            <div id="buttonCRUD"
                                                style="text-align: right; width: 100%; height: 30px;"
                                                valign="middle">
                                               <display:security urlId="500033">
                                                    <div id="deleteBtn" class="btn"
                                                        style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
                                                            onclick="deleteBlackListMchnt()">
                                                        	删除
                                                    </div>
                                                    </display:security>
                                            
                                               <display:security urlId="500031">
                                                    <div id="addBtn" class="btn"
                                                        style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
                                                            onclick="addBlackListMchnt()">
                                                        	添加
                                                    </div>
                                              </display:security>
                                                <div style="clear: both"></div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <br>
      </s:form>
    </body>
</html>