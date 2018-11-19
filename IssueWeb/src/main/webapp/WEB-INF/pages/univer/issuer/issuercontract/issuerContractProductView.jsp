<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<title>产品明细</title>

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
                            <td style="width: 140px; text-align: right;">卡费：</td>
                            <td>
								${map.cardFee/100}
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="text-align: left; width: 90%">
                        <tr>
                            <td style="width: 140px; text-align: right;">卡年费：</td>
                            <td>
								${map.annualFee / 100}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    
    <div id="service" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
    <div id="serviceTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="TableTitleFront" style="cursor: pointer">
                    <span class="TableTop">账户明细</span>
                </td>
                <td class="TableTitleEnd">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    <div id="" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
    <s:hidden id="loyaltyAcctypeContractDTO.id" name="loyaltyAcctypeContractDTO.id"/>
       	<ec:table items="map.accDTOs" var="account" width="95%"
            imagePath="${ctx}/images/extremecomponents/*.gif"
            view="html"
            autoIncludeParameters="false"
            showPagination="false"
            sortable="false">
            <ec:row>
               <ec:column property="id" title="账户合同号" width="15%"  />
               <ec:column property="productId" title="产品编号" width="15%" />
               <ec:column property="acctypeId" title="账户编号" width="15%"  />
               <ec:column property="ruleNo" title="账户计算规则" width="15%" />
            </ec:row>
        </ec:table>
      
    </div>
</div>
</div>




</s:form>

