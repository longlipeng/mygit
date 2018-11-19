<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<s:form id="productForm" name="productForm" method="post">
	<div id="product" style="background-color: #FBFEFF; border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
		<div id="${map.id}productTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
		
	    <div id="${map.id}productTable" style="padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
	        <table width="98%" style="table-layout: fixed;">
	        	<tr>
	                        <td>
	                            <table style="text-align: left; width: 90%">
	                                <tr>
	                                    <td style="width: 140px; text-align: right;">产品合同号：</td>
	                                    <td>
	                                    ${map.sellContractId}
	                                    </td>
	                                </tr>
	                            </table>
	                        </td>
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
	                    <td>
	            		 <table style="text-align: left; width: 90%">
	                                <tr>
	                                    <td style="width: 140px; text-align: right;">产品名称：</td>
	                                    <td>
	                                     ${map.accDTOs[0].productName}
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
	               <!--  <td align="right">
				                        <table border="0" cellpadding="0" cellspacing="0">
				                            <tr>
				                                <td>
				                                    <input type="button" class="bt" style="margin: 5px" 
				                                    onclick="updateProduct('${map.id}');" value="编辑"/>
				                                </td>
				                            </tr>
				                        </table>
				                    </td> -->
	            </tr>
	        </table>
	    </div>
	
		<div id="service" >
		    <div id="${map.id}serviceTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
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
		    <div id="${map.id}serviceTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
		    	<s:hidden id="sellerAcctypeContractDTO.id" name="sellerAcctypeContractDTO.id"/>
		       	<ec:table items="map.accDTOs" var="account" width="98%"
		            imagePath="${ctx}/images/extremecomponents/*.gif"
		            view="html"
		            autoIncludeParameters="false"
		            showPagination="false"
		            sortable="false">
		            <ec:row>
		               <ec:column property="sellContractId" title="账户合同号" width="15%"  />
		               <ec:column property="acctypeId" title="帐户号" width="15%" />
		               <ec:column property="serviceName" title="账户名称" width="15%"  />
		               <ec:column property="ruleNo" title="计算规则号" width="15%"  />
		               <ec:column property="ruleName" title="计算规则名称" width="15%" />
		            </ec:row>
		        </ec:table>
		    </div>
		</div>
	</div>




</s:form>
<br/><br/>
