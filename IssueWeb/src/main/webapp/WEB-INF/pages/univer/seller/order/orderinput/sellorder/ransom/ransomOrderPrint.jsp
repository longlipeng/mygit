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
</head>
<body>
<%@ include file="/commons/messages.jsp"%>
   <OBJECT   ID="factory"  
                  CLASSID="clsid:1663ED61-23EB-11D2-B92F-008048FDD814"  HEIGHT=0 WIDTH=0
                  CODEBASE="smsx.cab#version=6,5,439,50"></OBJECT>  

<div style="" align="center"><font style="line-height:20px;font-size=15px" >${sellOrderDTO.processEntityName}营销机构客户卡片赎回凭证</font></div>
<font style="line-height:20px;font-size=15px" >
<b>客户信息</b>
<br/>
客户名称:${sellOrderDTO.firstEntityName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
客户地址:${sellOrderDTO.invoiceAddresses}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
客户联系方式:${sellOrderDTO.cusContactWay}
<br/>
<b>经（代）办人信息:</b>
<br/>
经（代）办人:${sellOrderDTO.contactDTO.contactName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
证件类型:<dl:dictList dictType="140" tagType="1" dictValue="${sellOrderDTO.contactDTO.papersType}"></dl:dictList>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
证件号码:${sellOrderDTO.contactDTO.papersNo}
<br/>
<b>订单信息:</b>
<br/>
订单号:${sellOrderDTO.orderId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
赎回日期:${sellOrderDTO.orderDate}
<br/>
订单总金额(元):${sellOrderDTO.totalPrice}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
赎回卡数量(张):${sellOrderDTO.origCardQuantity}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
赎回总金额(元):${sellOrderDTO.origCardTotalAmt}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<br/>
赎回服务费(元):${sellOrderDTO.packageFee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
其他退回费用(元):${sellOrderDTO.additionalFee}
<b>原卡信息（如果为连续卡号，则只显示卡号段，如果卡号不连续显示卡号信息）</b>
<br/>
卡号段：
<br/>
<s:iterator value="sellOrderDTO.origCardNoSections" id="cardNO">
	<s:property value='cardNO'/> <br/>
</s:iterator>




<div id="button" align="center">
<br/>
<input type="button"  class="noprint" value="打印" onclick="printWindow();" name="打印">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button"  class="noprint" value="返 回"  onclick="window.open('${ctx}/orderQueryAction!query', '_self')"/>
</div>
</body>