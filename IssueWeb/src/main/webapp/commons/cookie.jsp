<%@page pageEncoding="UTF-8" %>
<script type="text/javascript">
		var p1=0;
		var p2=0;
		function cookie(){
			var cookieString = new String(document.cookie);
		
　			var magnetic= "magneticCard=";　
　			var beginPosition = cookieString.indexOf(magnetic)　　
　			if (beginPosition != -1) //cookie已经设置值，应该 不显示提示框　　
  			{	
  			
  				p1=cookieString.substring(magnetic.length,cookieString.indexOf(":"));
  				p2=cookieString.substring(cookieString.indexOf(":")+1,cookieString.indexOf(";"));
  			}else{
  				alert('请设置外设！');
			}
		}
	
</script>