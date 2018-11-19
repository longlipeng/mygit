<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>查看服务合同</title>
        <%@ include file="/commons/meta.jsp"%>
        <base target="_self"></base>
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
            /*
            function changeType() {
                if (document.getElementById('accountType').value=='0') {
                    document.getElementById('RateDiv').style.display='none';
                    document.getElementById('fixRateDiv').style.display='block';
                } else {
                    document.getElementById('RateDiv').style.display='block';
                    document.getElementById('fixRateDiv').style.display='none';
                }
            }*/
        </script>
    </head>
    <body>
        <%@ include file="/commons/messages.jsp"%>
        <div class="TitleHref">
            <span>查看服务合同</span>
        </div>
    <s:form id="newForm" name="newForm"
        action="mchntContractManagement/edit">
        <div id="ContainBox">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('acctypeContractTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">服务合同信息</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>

                        <div id="acctypeContractTable">
                                <table width="100%" style="table-layout: fixed;">
                                    <tr>
                                      <td>
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 110px; text-align: right;">
                                                        合同号：
                                                    </td>
                                                    <td>
                                                        <s:label name="feeDetDTO.merchantContractId"/>
                                                        <s:hidden name="mchntContractDTO.merchantContractId" value="%{feeDetDTO.merchantContractId}"/>
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 110px; text-align: right;">
                                                        服务号：
                                                    </td>
                                                    <td>
                                                        <s:label name="feeDetDTO.accTypeId"/>
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
                                                        账户合同号：
                                                    </td>
                                                    <td>
                                                        <s:label name="feeDetDTO.seq"/>
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
                                                               费用计算规则编号：
                                                    </td>
                                                    <td>
                                                        <s:label name="feeDetDTO.ruleNo"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 110px; text-align: right;">
                                                         费用计算规则名称：
                                                    </td>
                                                    <td>
                                                        <s:label name="feeDetDTO.ruleName"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    
                                    
                                </table>
                      </div>
                    </td>
                </tr>
            </table>
        </div>
        
        
        <div id="btnDiv" style="text-align: right; width: 100%">
            <button class='bt' style="float: right; margin: 5px 10px"
                onclick="document.newForm.submit();">
                <s:text name="xxl.button.back2"/>
            </button>
            <div style="clear: both"></div>
        </div>
    </s:form>
    </body>
</html>