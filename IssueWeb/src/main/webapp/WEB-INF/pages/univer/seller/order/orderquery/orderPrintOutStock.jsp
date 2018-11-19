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
<div  align="center"><font style="line-height:30px;font-size=22px;font-weight:bold;" >出库凭证</font> 打印日期:${sellOrderDTO.printDate}</div>
<div align="center"><table width="80%" border="1px solid">
	<tr>
		<td class="title">卡片去向</td>
		<td>
			机构名称:${sellOrderDTO.firstEntityName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			联系人:${sellOrderDTO.createUser}
		</td>
	</tr>
	<tr>
		<td class="title">订单信息</td>
		<td>
			订单号:${sellOrderDTO.orderId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			订单日期:${sellOrderDTO.orderDate}
			<br/>
			产品名称:${sellOrderDTO.productName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			数量(张):${sellOrderDTO.cardQuantity}
		</td>
	</tr>
	<tr>
		<td class="title">库存操作信息</td>
		<td>
			操作员:${sellOrderDTO.orderFlowDTO.createUser}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			操作时间:${sellOrderDTO.orderDate}
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
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最短有效期：${cardSection.validPeriod}
							<br/>
							<s:iterator value="#cardSection.cardNoSection" var="cardNo">
								<s:property value="#cardNo" /> <br/>
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
<input type="button"  class="noprint" value="返 回"  onclick="window.open('${ctx}/${actionName}!${actionMethodName}', '_self')"/>
</div>




</body>
