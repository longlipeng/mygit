var xmlHttp;

//创建
function createXMLHttpRequest(){
    if(window.XMLHttpRequest){ //Mozilla 浏览器
        xmlHttp = new XMLHttpRequest();
    }else if(window.ActiveXObject) { //IE浏览器
    	try{
        	xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }catch(e){
        	try {
            	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        	}catch(e){}
        }
    }
    if(xmlHttp == null){
    	alert("不能创建XMLHttpRequest对象");
    	return false;
    }
}

function sendRequest(url,parameter,callback){
	createXMLHttpRequest();	 
	if(parameter == null){
		//配置一个事件触发器
		//xmlHttp每次状态发生变化都会调用这个触发器。
		//然后由他去调用callback指向的函数
		xmlHttp.onreadystatechange = callback;
		//建立对服务器端的调用。
		xmlHttp.open("GET",url,true);
		xmlHttp.send(null);
	}else{
		xmlHttp.onreadystatechange = callback;
		xmlHttp.open("POST",url,true);
		xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
		//发出异步请求的方法
		xmlHttp.send(parameter);
	}
}


//异步调用示例
//处理xml的回调函数
function getXml(url){
		sendRequest(url,null,getXmlCallBack);

}
function getXmlCallBack(){

	if(xmlHttp.readyState==4){
	
		if(xmlHttp.status==200){
			//返回一个xml文档
			var  xmlDoc =  xmlHttp.responseXML;
	
			var student = xmlDoc.getElementsByTagName("student")[0];
				if(student!=null)
				{
					//获得student下面所有的子节点
					var nodes = student.childNodes;
					//遍历所有的子节点
					var userinfo = "<h3>User Info</h3><table border='1'>";
					for(var i=0;i<nodes.length;i++)
					{
						var nodename = nodes[i].nodeName;
						var nodevalue =nodes[i].firstChild.nodeValue;
						userinfo = userinfo +  "<tr><td>" + nodename + "</td><td>" + nodevalue +"</td></tr>";
					}					
					userinfo = userinfo + "</table>";
					alert(userinfo);
					var xmlDiv = document.getElementById("xmlDiv");
					xmlDiv.innerHTML = userinfo;
				}
			
		}else{
			//返回404出错信息
			alert("请求的页面异常");
		}
	}
}
