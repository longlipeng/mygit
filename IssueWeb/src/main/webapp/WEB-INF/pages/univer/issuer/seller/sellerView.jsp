<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>查看营销机构信息</title>
<script type="text/javascript">
    function choiceCustomer() {
        var sellerDTO = window.showModalDialog('customerManagement/choice.action', '_blank', 'center:yes;dialogHeight:600px;dialogWidth:700px;resizable:no');
        if (sellerDTO != null) {
            document.getElementById('groupId').value = sellerDTO['customerId'];
        }
    }

    function changePaymentTerm() {
        var paymentTermSelectedId = document.getElementById('paymentTerm').value;

        // 选中无需实时支付时，可以设置延期天数
        if (paymentTermSelectedId == 4) {
            document.getElementById('paymentDelay').disabled = false;
        } else {
            document.getElementById('paymentDelay').value = '';
            document.getElementById('paymentDelay').disabled = true;
        }
    }

    function loadPage() {
        salesRegions = ${salesRegions};
        changeBusinessCity();
        changePaymentTerm();
    }

    function changeBusinessCity() {
    	//默认的营业区域
    	var businessAreaId = ${sellerDTO.businessAreaId};
        if (salesRegions != null) {
            var salesRegionSelectedId = document.getElementById('businessCity').value;
            var businessAreaData = salesRegions[salesRegionSelectedId];
            var businessAreaSelect = document.getElementById('businessArea');
            businessAreaSelect.innerHTML = '';
            for (var i = 0; i < businessAreaData.length; i++) {
                var businessArea = businessAreaData[i];
                var opt = document.createElement('option');
                opt.value = businessArea['dictId'];
                opt.innerHTML = businessArea['dictShortName'];
                if(businessAreaId==opt.value){
                	opt.selected = " selected ";
                }
                
                businessAreaSelect.appendChild(opt);
            }
            businessAreaSelect.disabled = false;
        }
    }

    function changeSaleMan(){
    var issuerId=document.getElementById("issuerId").value;
    }
    
    function display(){
    	var selectValue=document.getElementById("channelId").value;
				if(selectValue==''){
					document.getElementById("time").style.visibility="hidden";
					
				}else{
					document.getElementById("time").style.visibility="";
				}
    }
</script>
</head>
<body onload="loadPage()">
     <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>查看营销机构信息</span>
    </div>
    <s:form id="sellerForm" name="sellerForm" action="" method="post">
    	<s:hidden name="sellerDTO.entityId"></s:hidden>
    	<s:hidden name="sellerDTO.fatherEntityId"></s:hidden>
        <div id="sellerBase" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
            <div id="sellerBaseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">营销机构信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="sellerBaseTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构名称：</td>
                                    <td>
                                    	<s:label name="sellerDTO.sellerName"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                        	<table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">英文名称：</td>
                                    <td><s:label name="sellerDTO.sellerEnglishName"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构地址：</td>
                                    <td><s:label name="sellerDTO.sellerAddress"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">英文地址：</td>
                                    <td><s:label name="sellerDTO.sellerEnglishAddress"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                    	<td>
                             <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>上级营销机构号:</td>
                                    <td>
                                    	<s:label name="sellerDTO.fatherEntityId"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构传真：</td>
                                    <td>
                                    	<s:label name="sellerDTO.sellerFax"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        
                    </tr>
                    
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构邮编：</td>
                                    <td><s:label name="sellerDTO.sellerPostcode"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构电话：</td>
                                    <td><s:label name="sellerDTO.sellerTelephone"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                    	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构规模：</td>
                                    <td><s:label name="sellerDTO.sellerSize"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                  
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">营销机构网站：</td>
                                    <td><s:label name="sellerDTO.sellerWebsite"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">外部系统代码：</td>
                                    <td><s:label name="sellerDTO.externalId"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">老系统客户号：</td>
                                    <td><s:label name="sellerDTO.legCusId"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%--
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">销售渠道：</td>
                                    <td>
                                        <s:select list="sellerDTO.channelList" listKey="channelId" listValue="channelName" name="sellerDTO.channelId" headerKey="" headerValue="无" onchange="display();" id="channelId"></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.channelId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td id="time">
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">渠道开始时间：</td>
                                    <td>
                                    	<s:textfield name="sellerDTO.chanBegDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                        	<s:param name="value">
                                        			<s:date name="sellerDTO.chanBegDate" format="yyyy-MM-dd" />
                                        	</s:param>
                                        </s:textfield>
                                        <s:fielderror>
                                            <s:param>sellerDTO.chanBegDate</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    --%>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>卡片打印名称：</td>
                                    <td><s:label name="sellerDTO.printName"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">额外打印信息：</td>
                                    <td><s:label name="sellerDTO.extPrintInfo"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    
                    <tr>
                        
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>失效时间：</td>
                                    <td>
                                        <s:date name="sellerDTO.closeDateDate" format="yyyy-MM-dd" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>销售区域：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.salesRegionId" dictValue="${sellerDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="false" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>缺省支付节点：</td>
                                    <td>
                                        <dl:dictList displayName="sellerDTO.paymentTerm" dictValue="${sellerDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="false" props="id=\"paymentTerm\" onchange=\"changePaymentTerm()\" disabled=\"true\""/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">延期天数：</td>
                                    <td><s:label name="sellerDTO.paymentDelay" id="paymentDelay" disabled="true"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营业城市：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.businessCity" dictValue="${sellerDTO.businessCity}" dictType="405" tagType="2" defaultOption="false" props="id=\"businessCity\" disabled=\"true\""/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营业区域：</td>
                                    <td>
                                        <select name="sellerDTO.businessAreaId" id="businessArea" disabled="disabled"> </select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>销售人员：</td>
                                    <td>
                                        <s:select list="salesmanList" name="sellerDTO.salesmanId" listKey="userId" listValue="userName" headerKey="" headerValue=""></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>营销机构行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="sellerDTO.activitySector" dictValue="${sellerDTO.activitySector}" dictType="400" tagType="2" defaultOption="false"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <%--<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">营销机构状态：</td>
                                    <td>
                                    	<s:select list="#{'1':'有效', '0':'无效'}" name="sellerDTO.sellerState"></s:select>
                                        <s:fielderror>
                                            <s:param>sellerDTO.sellerState</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        --%><td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;"><span class="no-empty">*</span>是否发送DM：</td>
                                    <td>
                                        <s:select list="#{'1':'是', '0':'否'}" name="sellerDTO.hasDm" disabled="true"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                    	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 100px; text-align: right;">注释信息：</td>
                                    <td>
                                    	<s:textarea name="sellerDTO.sellerComment" cols="15" rows="3"></s:textarea>
                                    </td>
                                </tr>
                            </table>
                        </td>
                  		
                       
                    </tr>
                </table>
            </div>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/issuerSeller/inquery.action'" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
</body>
</html>