<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<head>
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
			//document.getElementById("button").style.display="none";
			factory.printing.Print(false);
			window.close();
		}
</script>
<style type="text/css" media="print"> 
.noprint...{display : none } 
</style> 
<style type="text/css">
	.title...{
	font-size: 15px;
	font-weight: bold;
	height:20px;
	vertical-align: top;
	text-align: center;
	}
	td...{
	font-size: 15px;
	height:20px;
	}
	br...{
	line-height:15px;
	}
</style>
</head>
<body>
<%@ include file="/commons/messages.jsp"%>
   <OBJECT   ID="factory"  
                  CLASSID="clsid:1663ED61-23EB-11D2-B92F-008048FDD814"  HEIGHT=0 WIDTH=0
                  CODEBASE="smsx.cab#version=6,5,439,50"></OBJECT>  
<br/>
<br/>
<br/>
<div  align="center"><font style="line-height:30px;font-size=22px;font-weight:bold;" >${sellOrderDTO.processEntityName}客户购卡凭证（不记名卡）</font> 打印日期:${sellOrderDTO.printDate}</div>
<div align="center"><table width="80%" border="1px solid">
	<tr>
		<td class="title">客户信息</td>
		<td>
			名称:${sellOrderDTO.firstEntityName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			客户联系方式:${sellOrderDTO.cusContactWay}
			<br/>
			地址:${sellOrderDTO.invoiceAddresses}
			<br/>
			经/代办人:${sellOrderDTO.contactDTO.contactName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			证件类型:<dl:dictList dictType="140" tagType="1" dictValue="${sellOrderDTO.contactDTO.papersType}"></dl:dictList> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			证件号码:${sellOrderDTO.contactDTO.papersNo}
		</td>
	</tr>
	<tr>
		<td class="title">订单信息</td>
		<td>
			订单号:${sellOrderDTO.orderId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			订单日期:${sellOrderDTO.orderDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			产品名称:${sellOrderDTO.productName}
			<br/>
			订单总金额(元):${sellOrderDTO.totalPrice}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			购卡总金额(元):${sellOrderDTO.newCardTotalAmt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			购卡数量(张):${sellOrderDTO.cardQuantity}
			<br/>
			<s:if test="sellOrderDTO.cardIssueFee != 0">
			卡费总额(元):${sellOrderDTO.cardIssueFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</s:if>
			<s:if test="sellOrderDTO.annualFee != 0">
			年费总额(元):${sellOrderDTO.annualFee}
			</s:if>
			<br/>
			<s:if test="sellOrderDTO.packageFee != 0">
			包装费总额(元):${sellOrderDTO.packageFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</s:if>
			<s:if test="sellOrderDTO.deliveryFee != 0">
			送货费(元):${sellOrderDTO.deliveryFee}
			</s:if>
			<br/>
			<s:if test="sellOrderDTO.additionalFee != 0">
			其他费用1(元):${sellOrderDTO.additionalFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</s:if>
			<s:if test="sellOrderDTO.discountFee != 0">
			其他费用2(元):${sellOrderDTO.discountFee}
			</s:if>
		</td>
	</tr>
	<tr>
		<td class="title">
		详细信息
		</td>
		<td>
			<table width="100%">
				<tr>
					<td>
						<s:iterator value="sellOrderDTO.cardNoSectionDTOList" var="cardSection">
							面额${cardSection.faceValue }元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cardSection.cardQuantity}张
							<br/>
							<s:iterator value="#cardSection.cardNoSection" var="cardNo">
								<s:property value="#cardNo" /> 
								<br/>
							</s:iterator>
							<br/>
							<br/>
						</s:iterator>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="title">付款信息</td>
		<td>
			<table width="100%">
				<tr>
					<td>付款渠道</td>
					<td>付款金额(元)</td>
					<td>备注信息</td>
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
	<tr>
		<td class="title">备注</td>
		<td>${sellOrderDTO.buyStatement}</td>
	</tr>
	<tr>
		<td class="title">确认栏</td>
		<td> 
			 □  已确认相关购卡信息及备注
			 <br/>
			 □  客户签名及盖章：
			<br/>
	    	<br/>
	     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;  
	     	  日期：
		</td>
	</tr>
</table>
</div>
<div id="button" align="center">
<br/>
<input type="button"  class="noprint" value="打印" onclick="printWindow();" name="打印">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button"  class="noprint" value="返 回"  onclick="window.open('${ctx}/orderQueryAction!query', '_self')"/>
</div>




</body>
