<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:form id="productForm" name="productForm" method="post">
<div id="specialty" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
	<div id="baseTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer">
                    <span class="TableTop">产品明细</span>
                </td>
                <td class="TableTitleEnd">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
	
    <div id="" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
        <table width="95%" style="table-layout: fixed;">
        	<tr>
                        <td>
                            <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">合同产品明细号：</td>
                                    <td>
                                    ${map.id}
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <%--<td>
                            <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品名称：</td>
                                    <td>
									</td>
                                </tr>
                            </table>
                        </td>
            		--%>
            		<td>
            		 <table style="text-align: left; width: 90%">
                                <tr>
                                    <td style="width: 140px; text-align: right;">产品号：</td>
                                    <td>
                                     ${map.productId}
									</td>
                                </tr>
                            </table>
                        </td>
            </tr>
            <tr>
                <td>
                    <table style="text-align: left; width: 90%">
                        <tr>
                            <td style="width: 140px; text-align: right;">卡费(元)：</td>
                            <td>
								${map.cardFee/100}
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="text-align: left; width: 90%">
                        <tr>
                            <td style="width: 140px; text-align: right;">卡年费(元)：</td>
                            <td>
								${map.annualFee / 100}
                            </td>
                        </tr>
                    </table>
                </td>
                <td align="right">
			                        <table border="0" cellpadding="0" cellspacing="0">
			                            <tr>
			                                <td>
			                                    <input type="button" class="bt" style="margin: 5px" 
			                                    onclick="updateProduct('${map.id}');" value="编辑"/>
			                                </td>
			                            </tr>
			                        </table>
			                    </td>
            </tr>
        </table>
    </div>
    
    <div id="service" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
    <div id="" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
    <s:hidden id="sellerAcctypeContractDTO.id" name="sellerAcctypeContractDTO.id"/>
       	<ec:table items="map.accDTOs" var="account" width="95%"
            imagePath="${ctx}/images/extremecomponents/*.gif"
            view="html"
            autoIncludeParameters="false"
            showPagination="false"
            sortable="false">
            <ec:row>
               <ec:column property="acctypeId" title="账户编号" width="20%" />
               <ec:column property="serviceName" title="账户名称" width="30%"  />
               <ec:column property="fee" title="默认费率" width="20%"  />
               <ec:column property="ruleNo" title="计算规则" width="20%" />
               <ec:column property="null" title="编辑" width="10%" >
                   <a href="javascript:updateAccount('${account.id}');">编辑</a>
               </ec:column>
            </ec:row>
        </ec:table>
      
    </div>
</div>
</div>




</s:form>

