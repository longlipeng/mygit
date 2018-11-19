<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>门店管理</title>
        <%@ include file="/commons/meta.jsp"%>

        <script type="text/javascript">
        var isDisplayTableBody = false;
        var isDisplayQueryBody = false;
        var shopId ;
        function displayTableBody() {
            if (isDisplayTableBody) {
                display('TableBody');
                isDisplayTableBody = false;
            } else {
                undisplay('TableBody');
                isDisplayTableBody = true;
            }
        }
        function displayQueryBody() {
            if (isDisplayQueryBody) {
                display('QueryBody');
                isDisplayQueryBody = false;
            } else {
                undisplay('QueryBody');
                isDisplayQueryBody = true;
            }
        }

    function checkedCount() {
        var checkboxs = document.getElementsByName('shopIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
                shopId = checkboxs[i].value;
            }
        }
        return count;
    }
    function queryShop() {
        var shopForm = Ext.get("shopForm").dom;
        shopForm.action = '${ctx}/shopManagement/inquiry.action';
        shopForm.submit();
    }
    
    function sendSms(){
        var mobile=/^((\(\d{3}\))|(\d{3}\-))?13\d{9}|15\d{9}|18\d{9}/;
        var mobilePhone=document.getElementById("mobileId").value
        if(mobilePhone==''){
         document.getElementById("rexMobileId").innerHTML="手机号必须输入！";
         document.getElementById("rexMobileId").focus();
         return ;
        }else{
         if(mobilePhone.length>11){
             document.getElementById("rexMobileId").innerHTML="手机号不能超过11个字符！";
             document.getElementById("rexMobileId").focus();
             return ;
         }
         if(!mobile.test(mobilePhone)){
             document.getElementById("rexMobileId").innerHTML="请输入正确的手机号码！";
             document.getElementById("rexMobileId").focus();
             return ;
         }
        }
        document.getElementById("rexMobileId").innerHTML="";
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
    
        var shopForm = Ext.get("shopForm").dom;
        
        shopForm.action = '${ctx}/shopManagement/sendSms.action';
        shopForm.submit();
        //document.getElementById("mobileId").value="";
    }
    
    function addShop() {
        var shopForm = Ext.get("shopForm").dom;
        shopForm.action = '${ctx}/shopManagement/add.action';
        shopForm.submit();
    }

    function editShop() {
        var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        
        if( shopId != null && shopId.length == 4 ){
            alert("内部门店不能编辑");
            return;
        }
         
        var shopForm = Ext.get("shopForm").dom;
        shopForm.action = '${ctx}/shopManagement/edit.action';
        shopForm.submit();
    }

    function deleteShop() {
        var count = checkedCount();
        if (count < 1) {
            alert('请至少选择一条记录！');
            return;
        }
        
//        if( shopId != null && shopId.length == 4 ){
//            alert("内部门店不能删除");
//            return;
//        }
        
       confirm("确认要删除吗?",operDeleteShop);
    }
	function operDeleteShop(){
		 var shopForm = Ext.get("shopForm").dom;
        shopForm.action = '${ctx}/shopManagement/delete.action';
        shopForm.submit();
	}




    function loadPage() {
        city = ${city};
        changeCity(${shopQueryDTO.businessArea});
    }

    function changeCity(area) {
        var cityId = document.getElementById('city').value;
        var businessAreaData = city[cityId];
        var businessAreaSelect = document.getElementById('businessArea');
        businessAreaSelect.innerHTML = '';
        var opt = document.createElement('option');
        opt.value = '';
        opt.innerHTML = '--请选择--';
        businessAreaSelect.appendChild(opt);
        if (businessAreaData != null) {
            for (var i = 0; i < businessAreaData.length; i++) {
                var businessArea = businessAreaData[i];
                var opt = document.createElement('option');
                opt.value = businessArea['dictCode'];
                opt.innerHTML = businessArea['dictShortName'];
                if (area != null && area == opt.value) {
                    opt.selected = "selected";
                }
                businessAreaSelect.appendChild(opt);
            }
        }
        businessAreaSelect.disabled = false;
    }
    var city;
    </script>
    </head>
    <body onload="loadPage()">
        <%@ include file="/commons/messages.jsp"%>
        
        <div class="TitleHref">
            <span>门店信息管理</span>
        </div>
      <s:form id="shopForm" name="shopForm"
            action="shopManagement/inquiry" method="post">
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
                                            <s:textfield name="shopQueryDTO.merchantCode"></s:textfield>
                                        </td>
                                        <td width="90" align=right>
                                            <span>商户名称：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="shopQueryDTO.entityName"/>
                                        </td>
                                    </tr>
                                    <tr height="35">
                                        <td width="85" align=right>
                                            <span>门店号：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="shopQueryDTO.shopId"></s:textfield>
                                        </td>
                                        <td width="90" align=right>
                                            <span>门店名称：</span>
                                        </td>
                                        <td width="160">
                                            <s:textfield name="shopQueryDTO.shopName"/>
                                        </td>
                                    </tr>
                                    <tr height="35">
                                        <td width="85" align=right>
                                            <span>城市：</span>
                                        </td>
                                        <td width="160">
                                      <edl:entityDictList displayName="shopQueryDTO.shopCity" dictValue="${shopQueryDTO.shopCity}" dictType="405" tagType="2" defaultOption="true" props="id='city' onchange='changeCity()'"/>
                                              &nbsp;
                                        </td>
                                        <td width="90" align=right>
                                            <span>区域：</span>
                                        </td>
                                        <td width="160">
                                          <select name="shopQueryDTO.businessArea" id="businessArea" ></select>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr height="35">
                                        <td width="85" align=right>
                                            <span>门店状态：</span>
                                        </td>
                                        <td width="160">
                                          <s:select 
                                            list="#{'1':'有效','2':'门店切换','3':'无效'}" 
                                            name="shopQueryDTO.shopState" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
                                            &nbsp;
                                        </td>
                                         <td width="85" align=right>
                                            <span></span>
                                        </td>
                                        <td width="160">
                                         <input type="button" class="bt" style="margin: 5px" onclick="queryShop()" value="查 询"/>
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
                        </table>
                        
                        <div id="TableBody">    
                            <ec:table items="pageDataDTO.data" var="map" width="100%"
                                form="shopForm"
                                action="${ctx}/shopManagement/inquiry"
                                imagePath="${ctx}/images/extremecomponents/*.gif" 
                                view="html"
                                retrieveRowsCallback="limit"
                                autoIncludeParameters="false">
                                <ec:row onclick="">
                                    <ec:column property="null" alias="shopIdList" title="选择" width="10%" headerCell="selectAll">
                                        <input type="checkbox" name="shopIdList" value="${map.shopId}" />
                                        <input type="hidden" name="shopIdAll" value="${map.shopId}" />
                                        <input type="hidden" name="entityIdList" value="${map.entityId}" />
                                        <input type="hidden" name="stateList" value="${map.shopState}" /> 
                                    </ec:column>
                                    <ec:column property="shopId" title="门店号" width="10%" >
                                      <a href="shopManagement/view?shopDTO.shopId=${map.shopId}">${map.shopId}</a>
                                    </ec:column>
                                    <ec:column property="shopName" title="门店名称" width="10%" />
                                    <ec:column property="shopCity" title="城市"  width="15%" >
                                    	<edl:entityDictList dictValue="${map.shopCity}" dictType="405" tagType="1" defaultOption="false"/>
                                    </ec:column>
                                    <ec:column property="businessArea" title="区域"  width="15%" >
                                    	<edl:entityDictList dictValue="${map.businessArea}" dictType="408" tagType="1" defaultOption="false"/>
                                    </ec:column>
                                    <ec:column property="merchantCode" title="商户号" width="10%" />
                                    <ec:column property="merchantName" title="商户名称"  width="15%" />                                
                                    <ec:column property="shopState" title="门店状态" width="10%" />
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
                                                            onclick="deleteShop()">
                                                        	删除
                                                    </div>
                                                    </display:security>
                                             <display:security urlId="500032">
                                                    <div id="modifyBtn" class="btn"
                                                        style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
                                                            onclick="editShop()">
                                                       	编辑
                                                    </div>
                                                    </display:security>
                                               <display:security urlId="500031">
                                                    <div id="addBtn" class="btn"
                                                        style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
                                                            onclick="addShop()">
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