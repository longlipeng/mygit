<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单付款信息打印</title>
			<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript">
			function　printWindow(){ 　
				factory.printing.header = "";
	            factory.printing.footer = "";
	             
				factory.printing.portrait　=　true;//设为false就是横向 　　
	
				factory.printing.leftMargin　=　24.8; 　　
	
				factory.printing.topMargin　=　3.5; 　　　
	
				factory.printing.rightMargin　=　0.0; 　　
				
				factory.printing.bottomMargin　=　0.0;
				
				factory.printing.Print(false);
		}
		</script>
		<style type="text/css" media="print"> 
			.noprint...{display : none } 
			td {
			font-size:14px;
			height:20px;	
			}
			.title{
			font-size:14px;
			font-weight:bold;
			}
		</style> 
		<style type="text/css">
		td {
			font-size:14px;
			height:30px;	
			}
		.title{
			font-size:14px;
			font-weight:bold;
		}
		</style>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
   		<OBJECT   ID="factory"  
                  CLASSID="clsid:1663ED61-23EB-11D2-B92F-008048FDD814"  HEIGHT=0 WIDTH=0
                  CODEBASE="smsx.cab#version=6,5,439,50"></OBJECT>
        <div style="width: 100%" align="center">
        	<table width="70%" >
         		<tr>
         			<td class="title" align="center">
         				${sellOrderDTO.firstEntityName}机构付款凭证
         			</td>
         		</tr>
         		<tr>
         			<td  class="title">客户信息</td>
         		</tr>
         		<tr>
         			<td>
	         			<table width="100%" >
	         				<tr>
	         					<td>
	         						客户名称:${sellOrderDTO.firstEntityName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						客户地址:${sellOrderDTO.invoiceAddresses}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						联系方式:${sellOrderDTO.cusContactWay}
	         					</td>
	         				</tr>
	         			</table>
         			</td>
         		</tr>
         		<tr>
         			<td class="title">订单信息</td>
         		</tr>
         		<tr>
         			<td>
	         			<table width="100%" >
	         				<tr>
	         					<td>
	         						订单号:${sellOrderDTO.orderId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						购卡日期:${sellOrderDTO.orderDate}
	         					</td>
	         				</tr>
	         			</table>
         			</td>
         		</tr>
         		<s:if test="getSellOrderDTO().getOrderType()==10000003">
         			<tr>
	         			<td>
		         			<table width="100%" >
		         				<tr>
		         					<td>
			         					充值数量(张):${sellOrderDTO.cardQuantity}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			         					充值总额(元):${sellOrderDTO.creditTotalAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			         					手续费(百分比):${sellOrderDTO.serviceFee}%
		         					</td>
		         				</tr>
		         			</table>
	         			</td>
         			</tr>
         			<tr>
	         			<td>
		         			<table width="100%" >
		         				<tr>
		         					<td>
		         						订单总金额(元):${sellOrderDTO.totalPrice}
		         					</td>
		         				</tr>
		         			</table>
	         			</td>
         			</tr>
         		</s:if>
         		<s:else>
         		<tr>
         			<td>
	         			<table width="100%" >
	         				<tr>
	         					<td>
	         						购卡数量(张):${sellOrderDTO.cardQuantity}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						卡费总额(元):${sellOrderDTO.cardIssueFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						年费总额(元):${sellOrderDTO.annualFee}
	         					</td>
	         				</tr>
	         			</table>
         			</td>
         		</tr>
         		<tr>
         			<td>
	         			<table width="100%" >
	         				<tr>
	         					<td>
	         						包装费(元):${sellOrderDTO.packageFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						送货费(元):${sellOrderDTO.deliveryFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         						附加费(元):${sellOrderDTO.additionalFee}
	         				</tr>
	         			</table>
         			</td>
         		</tr>
         		<tr>
         			<td>
	         			<table width="100%" >
	         				<tr>
	         					<td>
		         					折扣费(元):${sellOrderDTO.discountFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		         					订单总金额(元):${sellOrderDTO.totalPrice}
	         					</td>
	         				</tr>
	         			</table>
         			</td>
         		</tr>
         		</s:else>
         		<s:if test="getSellOrderDTO().getOrderType()!=10000006">
         		<tr>
         			<td class="title">付款明细</td>
         		</tr>
         		<tr>
         			<td>
        				
         				<table width="100%" >
         					<tr>
         						<td class="title">付款渠道</td>
         						<td class="title">付款金额</td>
         						<td class="title">备注信息</td>
         					</tr>
         					<s:iterator value="sellOrderDTO.orderPaymentDTOList" var="map">
         						<tr>
         							<td>
         								<dl:dictList dictType="901" tagType="1" dictValue="${map.paymentType}"/>
         							</td>
         							<td>${map.paymentAmount}</td>
         							<td>${map.remark}</td>
         						</tr>
         					</s:iterator>
         					<tr>
         						<td colspan="3" align="left">总计：${sellOrderDTO.paymentAmount}</td>
         					</tr>
         				</table>
         				
         			</td>
         		</tr>
         		</s:if>
         		<tr height="20px" valign="bottom">
         			<td align="right">
         				<input type="button"  class="noprint" value="打印" onclick="printWindow();" name="打印">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button"  class="noprint" value="返 回"  onclick="window.open('${ctx}/orderQueryAction!query', '_self')"/>
         			</td>
         		</tr>
         	</table>  
        </div>          
	</body>
</html>
	