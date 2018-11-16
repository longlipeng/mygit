
//公共函数及框架控制函数

document.onkeydown=function(){
		var key=window.event.keyCode;
		if(key==116||key==0){
			//f5
			//alert('F5...key='+key);
			return false;
		}else if(key==8){
			//backspace
			//return false;
		}
}

document.varSelfWindowObject = self.window;

function createHttpObj(){
	if (window.ActiveXObject) {
		return new ActiveXObject("Microsoft.xmlHttp");
	} else if (window.xmlHttpRequest) {
		return new xmlHttpRequest();
	}
}
	
function showLoading(){
	  var   shtml   =   '<html><head></head><body><table width="100%" height="100%"  border="0"><tr><td><div align="center"><img src="'+appName+'/Webpic/images/images/loading.gif" width="263" height="59"></div></td></tr></table></body></html>';   
	  var   Win   =   document.frames["iframe_loading"];   
	  var   Layer   =   document.getElementById("Layer_loading");   
	
	  Win.document.write(shtml);   
	  Win.document.body.style.backgroundColor   =   "transparent";   
	  Win.document.body.style.borderWidth   =   0;   
	  Layer.style.visibility = "visible";
}
function writeProcessScroll(){
	document.write('<div id="Layer_loading" style="position:absolute; left: 1px; top: 2px; width:100%; height:100%; z-index:1; visibility:hidden">'
	+'<Iframe id="iframe_loading" width="100%" height="100%" allowtransparency="yes" frameborder="0" scrolling="no"></Iframe>'
	+'</div>');
}

function DeptPersonTree(objKey,objValue){
	if(typeof objValue == "object"){
		showSelectorBea(objKey, objValue, appName+'/page.do?targetPage=/Tree/DeptPerson/Multi_AllPerson.jsp?showNames=true', 300, 400);
	}else{
		showSelectorBea(objKey, '', appName+'/page.do?targetPage=/Tree/DeptPerson/Multi_AllPerson.jsp', 300, 400);
	}
}

/********************前端数据类型合法性的自动检查函数********************/
function autoCheck(objForm)
{
  var count = objForm.elements.length;

  if(typeof initPageFormValuesBeforeSubmit != 'undefined')
    initPageFormValuesBeforeSubmit();

  for (var i = 0 ; i <count; i++)
  {
  	 /* 只检查:type=text的input、textarea、select、file  */
	 if( typeof objForm[i]!='object'
	 		|| !(
	 		objForm[i].tagName=='INPUT'
	 		   && (objForm[i].type.toUpperCase()=='TEXT'
	 		       || objForm[i].type.toUpperCase()=='FILE')
	 		|| objForm[i].tagName=='TEXTAREA'
	 		|| objForm[i].tagName=='SELECT'
	 		))
	 	continue;

	 /* 只读、失效、设置为非自动检查的不检查 */
	 if( objForm[i].readOnly && !((typeof objForm[i].autocheck) != "undefined" && (""+objForm[i].autocheck) == "true")  || /* 只读  */
	 	  objForm[i].disabled || /* 失效 */
	 	  ((typeof objForm[i].autocheck) != "undefined" && objForm[i].autocheck == "false") ) /* 设置为非自动检查 */
	 	continue;

	 var varTitle = objForm[i].title;
	 if(typeof varTitle == "undefined")
	   varTitle="";

    /* 检查title */
    if(varTitle == "")
    {
      //alert("系统错误！当前输入域没有定义title 属性！");
      //if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      //return false;
    }

	 /* 数据类型 */
	 var dataType = objForm[i].className;
	 if(typeof dataType == "undefined")
	   dataType="";
	 var varValue = objForm[i].value;

   /* 浮点数(小数) */
	if(dataType=='decimal' || dataType=='decimal0' || dataType=='decimal1')
	{
	   varValue = varValue.replace(/,/gi,'');
	   //if(trim(varValue)!='')
	      //objForm[i].value=toDecimal2(varValue);
	   objForm[i].value = varValue;

	   if(!isDecimal(varValue))
	   {
	 	   alert("输入域 "+varTitle+" 只能输入浮点数，格式为：####.## 或 ##,###.## 并且整数位数不能大于18位.");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }

	   if(dataType=='decimal0' && (varValue-0)<0)
	   {
	      alert("输入域 "+varTitle+" 不能输入负数，只能输入0或正数，格式为：####.## 或 ##,###.## 并且整数位数不能大于18位.");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	   else if(dataType=='decimal1' && (varValue-0)<=0)
      {
         alert("输入域 "+varTitle+" 不能输入负数和0，只能输入正数，格式为：####.## 或 ##,###.## 并且整数位数不能大于18位.");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
      }

      /* 大于等于给定输入域的值 */
   	if(typeof objForm[i].ge != 'undefined')
   	{
   	   var objTarget = getOtherTarget(objForm,objForm[i].ge);
   	   var varTargetValue = objTarget.value;
   	   varTargetValue = varTargetValue.replace(/,/gi,'');
   	   if((objForm[i].minLength>'0' || varValue.length>0) && (varValue-0)<(varTargetValue-0))
   	   {
   	 	   alert("输入域 "+varTitle+" 应该大于等于 "+objTarget.title + " ！");
   	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
   	      return false;
   	   }
   	}
   	/* 小于等于给定输入域的值 */
   	if(typeof objForm[i].le != 'undefined')
   	{
   	   var objTarget = getOtherTarget(objForm,objForm[i].le);
   	   var varTargetValue = objTarget.value;
   	   varTargetValue = varTargetValue.replace(/,/gi,'');
   	   if((objForm[i].minLength>'0' || varValue.length>0) && (varValue-0)>(varTargetValue-0))
   	   {
   	 	   alert("输入域 "+varTitle+" 应该小于等于 "+objTarget.title + " ！");
   	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
   	      return false;
   	   }
   	}
	}

    /**检查输入内容不能包含'"<>**/
    if((objForm[i].tagName=='INPUT' && objForm[i].type.toUpperCase()=='TEXT')
         || objForm[i].tagName=='TEXTAREA')
    {
      var varValue = objForm[i].value;
      if(!(typeof objForm[i].autoChange!='undefined' && ''+objForm[i].autoChange=='false'))
      {
         if(varValue.indexOf("'")>-1)
            objForm[i].value=objForm[i].value.replace(/'/gi,'＇');
         if(varValue.indexOf('"')>-1)
            objForm[i].value=objForm[i].value.replace(/"/gi,'＂');
         if(varValue.indexOf('<')>-1)
            objForm[i].value=objForm[i].value.replace(/</gi,'〈');
         if(varValue.indexOf('>')>-1)
            objForm[i].value=objForm[i].value.replace(/>/gi,'〉');
         /*
         if(varValue.indexOf("'")>-1)
         {
            alert(varTitle+" 输入有误。\r\n\r\n它包含字符(')，\r\n不能包含的字符有:\r\n \'  \"  <  > \r\n");
            return false;
         }
         else if(varValue.indexOf('"')>-1)
         {
            alert(varTitle+" 输入有误。\r\n\r\n它包含字符(\")，\r\n不能包含的字符有:\r\n \'  \"  <  > \r\n");
            return false;
         }
         else if(varValue.indexOf("<")>-1)
         {
            alert(varTitle+" 输入有误。\r\n\r\n它包含字符(<)，\r\n不能包含的字符有:\r\n \'  \"  <  > \r\n");
            return false;
         }
         else if(varValue.indexOf(">")>-1)
         {
            alert(varTitle+" 输入有误。\r\n\r\n它包含字符(>)，\r\n不能包含的字符有:\r\n \'  \"  <  > \r\n");
            return false;
         }
         */
      }
    }

    /* 检查minLength属性 */
    if( (typeof objForm[i].minLength) == "undefined" || objForm[i].minLength == "" )
    {
      alert("系统错误！\r\n"+varTitle+" 没有定义minLength属性！");
      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      return false;
    }
    if( objForm[i].minLength>0 && getLength( trim(objForm[i].value) )==0)
    {
    	alert("输入域 "+varTitle+" 不能为空！");
      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      return false;
    }
    if( objForm[i].minLength>0 && getLength( trim(objForm[i].value) )< objForm[i].minLength)
    {
    	alert('输入域 '+varTitle+' 长度不足，不能少于'+objForm[i].minLength + '个英文字符或者' + (objForm[i].minLength/2) + '个汉字！');
      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      return false;
    }

    /* 检查maxLength 属 性  */
    if( !(dataType=='decimal' || dataType=='decimal0' || dataType=='decimal1')
      && (typeof objForm[i].maxLength) == "undefined" || objForm[i].maxLength == "" || objForm[i].maxLength==2147483647)
    {
      alert("系统错误！\r\n"+varTitle+" 没有定义maxLength属性！");
      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      return false;
    }
    if( !(dataType=='decimal' || dataType=='decimal0' || dataType=='decimal1')
         && getLength(trim(objForm[i].value)) > objForm[i].maxLength )
    {
    	alert("输入域 "+varTitle+" 长度过长，目前字数为"+getLength(trim(objForm[i].value))/2+"。\r\n\r\n 不能多于"+objForm[i].maxLength+"个英文字符或者"+(objForm[i].maxLength/2)+"个汉字！");
      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
      return false;
    }

    /* 检查输入域数据类型 */
    if( dataType == "" || dataType=='text')
     	continue;

	/* 整型 */
	if(dataType=='integer' && !isInteger(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入整数(范围:[-2147483648,2147483647])！");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 不为0的数字 */
	if(dataType=='integer0' && !(isPstInteger(varValue) || trim(varValue)=='0'))
	{
	 	alert("输入域 "+varTitle+" 不能输入负数，只能输入0或正整数(范围:[0,2147483647])！");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 不为0的数字 */
	if(dataType=='integer1' && !isPstInteger(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入正整数(范围:[1,2147483647])！");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 大于等于给定输入域的值 */
	if( typeof objForm[i].ge!='undefined'
	    && (dataType=='integer' || dataType=='integer0' || dataType=='integer1') && typeof objForm[i].ge !='undefined')
	{
	   var objTarget = getOtherTarget(objForm,objForm[i].ge);
	   if((objForm[i].minLength>'0' || varValue.length>0) && (varValue-0)<(objTarget.value-0))
	   {
	 	   alert("输入域 "+varTitle+" 应该大于等于 "+objTarget.title + " ！");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	}
	/* 小于等于给定输入域的值 */
	if( typeof objForm[i].le!='undefined'
	   && (dataType=='integer' || dataType=='integer0' || dataType=='integer1') && typeof objForm[i].le !='undefined')
	{
	   var objTarget = getOtherTarget(objForm,objForm[i].le);
	   if((objForm[i].minLength>'0' || varValue.length>0) && (varValue-0)>(objTarget.value-0))
	   {
	 	   alert("输入域 "+varTitle+" 应该小于等于 "+objTarget.title + " ！");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	}

	/* 日期 */
	if(dataType=='date' && !isDate(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入大于等于19000101的日期，格式为：yyyymmdd");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 和当前日期比较，大于等于当前日期 */
	if( dataType=='todayAsc' && (!isDate(varValue) || varValue<getCurrentDate()) )
	{
	 	alert("输入域 "+varTitle+" 应该大于等于当前日期或日期输入不合法！");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 和当前日期比较，小于于等于当前日期 */
	if( dataType=='todayDesc' && (!isDate(varValue) || varValue>getCurrentDate()) )
	{
	 	alert("输入域 "+varTitle+" 应该小于等于当前日期或日期输入不合法！");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* 大于等于给定日期 */
	if( dataType=='date' && typeof objForm[i].ge !='undefined')
	{
	   var objTarget = getOtherTarget(objForm,objForm[i].ge);
	   if((objForm[i].minLength>'0' || varValue.length>0) && varValue<objTarget.value)
	   {
	 	   alert("输入域 "+varTitle+" 应该大于等于 "+objTarget.title + " ！");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	}
	/* 小于等于给定日期 */
	if( dataType=='date' && typeof objForm[i].le !='undefined')
	{
	   var objTarget = getOtherTarget(objForm,objForm[i].le);
	   if(varValue>objTarget.value)
	   {
	 	   alert("输入域 "+varTitle+" 应该小于等于 "+objTarget.title + " ！");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	}

	/* 时间 */
	if(dataType=='time')
	{
//	   if(!(typeof objForm[i].autoChange!='undefined' && ''+objForm[i].autoChange=='false'))
      var oldValue = varValue;
	 	varValue = varValue.replace(/:/gi,'');
	   if(!isTime(varValue))
	   {
	 	   alert("输入域 "+varTitle+" 只能输入时间，格式为：hhmmss或hh:mm:ss.");
	      if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	      return false;
	   }
	   else
	   {
	      if(oldValue.indexOf(":")>-1)
	         objForm[i].value=varValue.substring(0,2)+":"+varValue.substring(2,4)+":"+varValue.substring(4,6);
//	      if(typeof objForm[i].oldName == 'undefined')
//	      {
//	         objForm[i].oldName = objForm[i].name;
//	         objForm[i].name = objForm[i].name+"_Old";
//	         var objTmp = document.createElement("<input type=hidden name='"+objForm[i].oldName+"' value='"+varValue+"'>");
//	         objForm.appendChild(objTmp);
//	      }
//	      else
//	         objForm[objForm[i].oldName][1].value=varValue;
	   }
	}

	/* 电话 */
	if(dataType=='phone' && !isPhone(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入电话号码，格式为：(###)-######## 或 ###-########");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* zipcode */
	if(dataType=='zipcode' && !isZipcode(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入邮编，格式为：######");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
	/* email */
	if(dataType=='email' && !isEmail(varValue))
	{
	 	alert("输入域 "+varTitle+" 只能输入E-mail，格式为：xxx@xxx或xxx@xxx.xx");
	   if(!objForm[i].readOnly) obj_Focus(objForm[i]);
	   return false;
	}
 }

 //页面内的合法性检查
 if(typeof pageValidCheck != 'undefined')
	 if(!pageValidCheck()) return false;

 return true;
}

//进行对象值比较时，获取其他目标对象 (ge le)
function getOtherTarget(objForm,varName)
{
   var objTarget;
   var ss=varName.indexOf('[');
   var ee=varName.indexOf(']');
   if(ss>-1)
   {
      if(typeof objForm[varName.substring(0,ss)].length=='undefined')
         objTarget = objForm[varName.substring(0,ss)];
      else
         objTarget = objForm[varName.substring(0,ss)][varName.substring(ss+1,ee)-0];
   }
   else
      objTarget = objForm[varName];
   return objTarget;
}

/**自动复制值*/
function autoCopyValue(objForm)
{
   var count = objForm.elements.length;
   for (var i = 0 ; i <count; i++)
   {
      //自动调用其onclick事件进行初始化
      if(objForm.elements[i].tagName=='BUTTON' && objForm.elements[i].value=='…'
         && typeof objForm.elements[i].onclick!='undefined')
         typeof objForm.elements[i].click();

      if(objForm[i].readOnly || ( (typeof objForm[i]=='object') && objForm[i].tagName=='SELECT' && objForm[i].disabled==true ) )
         objForm[i].style.backgroundColor='#F1F1F1';
      /* 只检查:type=text的input、textarea、select、file  */
      if( typeof objForm[i]!='object'
      	|| !(
      	objForm[i].tagName=='INPUT'
      	   && (objForm[i].type.toUpperCase()=='TEXT'
      	       || objForm[i].type.toUpperCase()=='FILE')
      	|| objForm[i].tagName=='TEXTAREA'
      	|| objForm[i].tagName=='SELECT'
      	))
         continue;

      /* 失效的不检查 */
      if(objForm[i].disabled)
         continue;
      objForm[i].lgb_initValue=objForm[i].value;
   }
}

/**设置输入域背景色*/
function setInputAreaColor(varObj)
{
   if(varObj.readOnly)
      varObj.style.backgroundColor='#f1f1f1';//#666666 #E6F2C8 #f1f1f1
   else
      varObj.style.backgroundColor='#FFFFFF';
}

/**自动比较*/
function autoCompare(objForm)
{
   var count = objForm.elements.length;

   for (var i = 0 ; i <count; i++)
   {
      /* 只检查:type=text的input、textarea、select、file  */
      if( typeof objForm[i]!='object'
      	|| !(
      	objForm[i].tagName=='INPUT'
      	   && (objForm[i].type.toUpperCase()=='TEXT'
      	       || objForm[i].type.toUpperCase()=='FILE')
      	|| objForm[i].tagName=='TEXTAREA'
      	|| objForm[i].tagName=='SELECT'
      	))
         continue;
      /* 失效的不检查 */
      if(objForm[i].disabled)
         continue;

      if(objForm[i].lgb_initValue!=objForm[i].value)
         return true;
   }
   return false;
}

/* 获取字符的长度,考虑中文 */
function getLength(inputValue)
{
  var j = 0;
  for (var i = 0;i< inputValue.length;i++)
  {
    if (inputValue.charAt(i) >"~")
      j = j + 2
    else
      j = j + 1
  }
  return j;
}
//去掉空格
function trim(str)
{
 return rTrim(lTrim(str));
}
//去掉左空格
function lTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);   

    if (whitespace.indexOf(s.charAt(0)) != -1){
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1){
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}
//去掉右空格
function rTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str); 
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1){
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1){
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}

function isInteger(inputVal)
{
  inputStr = inputVal;
  /* 如果为空串，认为是合法的整数    */
  if(inputVal.length==0)
    return true;
  for (var i = 0;i < inputStr.length;i++)
  {
    var oneChar = inputStr.charAt(i)
    /* 检查是否有除数字及“-”以外的字符 */
    if ((oneChar < "0" && oneChar != "-")|| (oneChar > "9" && oneChar != "-"))
        return false
   }
   /* 防止用户输入以下形式数据: - */
   if(inputVal.length == 1 && inputVal.charAt(0) == "-")
     return false;
   /* 防止用户输入以下形式数据：-***- */
   for(var i = 1;i < inputVal.length;i++)
   {
     if(inputVal.charAt(i) == "-")
       return false;
   }

   if((inputVal-0)>2147483647 || (inputVal-0)<-2147483648)
      return false;
   return true;
}

/* 是否是不为0的 */
function isPstInteger(inputVal)
{
  if(inputVal.length==0)
   return true;
  if(!isInteger(inputVal))
    return false;
  if(inputVal <= 0)
    return false
  return true;
}

/* 获取当前日期 */
function getCurrentDate()
{
	var dt = new Date();
	var yy = dt.getYear();
	var mm = dt.getMonth()+1;
	if(mm<10) mm = "0"+mm;
	var dd = dt.getDate();
	if(dd<10) dd = "0"+dd;
	return yy+mm+dd;
}

/* 是否是时间 */
function isTime(inputStr)
{
  if(inputStr.length==0)
    return true;
  if(!(inputStr.length==6 || inputStr.length==8))
    return false;
  if(inputStr.length==6)
  {
    if(inputStr.substring(0,2)-0>23) return false;
    if(inputStr.substring(2,3)>'5') return false;
    if(inputStr.substring(3,4)>'9') return false;
    if(inputStr.substring(4,5)>'5') return false;
    if(inputStr.substring(5,6)>'9') return false;
  }
  if(inputStr.length==8)
  {
    if(inputStr.substring(0,2)-0>23) return false;
    if(inputStr.substring(2,3)!=':') return false;
    if(inputStr.substring(3,4)>'5') return false;
    if(inputStr.substring(4,5)>'9') return false;
    if(inputStr.substring(5,6)!=':') return false;
    if(inputStr.substring(6,7)>'5') return false;
    if(inputStr.substring(7,8)>'9') return false;
  }
  return true;
}

/* 是否是日期 */
function isDate(inputStr)
{
  if(inputStr.length==0)
    return true;
  if(inputStr.length!=8)
    return false;
  for(i=0;i<inputStr.length;i++)
  {
    charI = inputStr.charAt(i);
    if(charI<"0" || charI>"9")
      return false;
  }
  if(inputStr<'19000101')
    return false;
  var year = parseFloat(inputStr.substring(0,4))
  var month = parseFloat(inputStr.substring(4,6))
  var day = parseFloat(inputStr.substring(6,8))
  if (month < 1 || month > 12 || day < 1 || day > 31)
    return false;
  else if ((month == 4 || month == 6 || month == 9 || month ==11) && (day > 30))
    return false;
  else if (isRYear(year) && month == 2 && day > 29 || !isRYear(year) && month == 2 && day > 28)
    return false;
  else
    return true;
}

/* 是否是闰年 */
function isRYear(inputInt)
{
  if (inputInt % 100 == 0 && inputInt % 400 == 0 || inputInt % 100 != 0 && inputInt % 4 == 0)
	return true;
  else
	return false;
}

/* 是否decimal型数 */
function isDecimal(inputVal)
{
	if(inputVal.length==0)
		return true;
	for(i=0;i<inputVal.length;i++)
	{
		var tmpChar = inputVal.charAt(i);
		if((tmpChar != ".") && (tmpChar != "-") && (tmpChar>'9'||tmpChar<'0'))
			return false;
	}
	var tmp=inputVal;
	if(tmp.charAt(0)=='-')
	   tmp=tmp.substring(1);
	if(tmp.indexOf(".")>-1)
	   tmp = tmp.substring(0,tmp.indexOf("."));
	if(tmp.length>18)
	   return false;

	if(isNaN(inputVal))
		return false;
	/* 防止用户输入以下格式：.## */
	if(inputVal.charAt(0)==".")
		return false;
	/* 防止用户输入以下格式：-.## */
	if(inputVal.length > 2 && inputVal.charAt(0) == "-" && inputVal.charAt(1) == ".")
		return false;
	/* 防止用户输入如下格式：##. */
	if(inputVal.charAt(inputVal.length-1)=='.')
		return false;
	return true;
}

/* 电话 */
function isPhone(ph)
{
  if(ph.length==0)
    return true;
  var flag = ph.search(/[^0-9,\(,\),-]/);
  if (flag < 0) /* 格式正确 */
    return true;
  return false;
}

/* 判断是否为邮政编码 */
function isZipcode(srcstr)
{
  if(srcstr.length==0)
    return true;
  if(srcstr.length!=6)
    return false;
  for(var i=0;i<srcstr.length;i++)
  {
    var charI=srcstr.charAt(i);
    if(charI<"0"||charI>"9")
      return false;
  }
  return true;
}

/* 是否email */
function isEmail(theStr)
{
  if(theStr.length==0) return true;
  var atIndex = theStr.indexOf('@');
//  var dotIndex = theStr.indexOf('.',atIndex);
  var flag = true;
//  theSub = theStr.substring(0,dotIndex+1);
  if ((atIndex<1)
      || (atIndex != theStr.lastIndexOf('@'))
//      || (dotIndex < atIndex + 2)
//      || (theStr.length <= theSub.length)
     )
    flag = false;
  else
    flag = true ;
  return(flag);
}


/********************公共函数********************/

//全是/全否
function selectAll(objButton,objKey)
{
   var isChecked = true;
   if(objButton.innerText=='全是')
   {
      objButton.innerText='全否';
      isChecked = false;
   }
   else
      objButton.innerText='全是';

   if(typeof objKey=='undefined')
      return;
   if(typeof objKey.length == 'undefined')
      objKey.click();
   else
   {
      for(var i=0;i<objKey.length;i++)
      {
         if(!isChecked && objKey[i].checked)
            objKey[i].click();
         else if(!objKey[i].checked && isChecked)
            objKey[i].click();
      }
   }
}

//设置Cookie
function setCookie(sKey,sValue)
{
   var expires = new Date();
   expires.setTime(expires.getTime() +  365*24*60*60*1000);
   document.cookie= sKey + "=" + sValue + ";expries=" + expires.toGMTString();
}
//获取Cookie
function getCookie(sKey)
{
  var startPoint = 0;
  var endPoint   = 0;
  var isFound = false;
  var cookieStr = document.cookie;
  var i = 0;

  sKey = sKey + "=";
  while (i<cookieStr.length)
  {
     startPoint = i;
     endPoint = startPoint + sKey.length;
     if(cookieStr.substring(startPoint,endPoint) == sKey)
     {
        isFound = true;
        break;
     }
     i++;
  }

  if (isFound == true)
  {
     startPoint = endPoint;
     endPoint = cookieStr.indexOf(";",startPoint);
     if(endPoint < startPoint) endPoint = cookieStr.length;
     return unescape(cookieStr.substring(startPoint,endPoint));
  }
}


//排序列
var objSortColumn;
function jsSort(objThis,isNumber)
{
   //排序列序号
   var j=objThis.cellIndex;

   if(typeof objSortColumn!='undefined')
      objSortColumn.innerHTML=objSortColumn.innerHTMLOld;
   objSortColumn = objThis;

   var objTb = objThis.parentElement;
   while(true)
   {
      objTb = objTb.parentElement;
      if(objTb.tagName=='TABLE') break;
   }
   if(typeof objTb.srcTableObject!='undefined')
      objTb = objTb.srcTableObject;

   //若当前列尚未进行过排序，缺省为升序
   if(typeof objThis.sortType=='undefined')
   {
      objThis.sortType = "ASC";
      objThis.innerHTMLOld = objThis.innerHTML;
   }
   else if(objThis.sortType=="DESC")
      objThis.sortType = "ASC";
   else
      objThis.sortType = "DESC";

   if(objThis.sortType=='ASC')
      objThis.innerHTML=objThis.innerHTMLOld+"<font color=#0000ff size=2>↑</font>";
   else
      objThis.innerHTML=objThis.innerHTMLOld+"<font color=#0000ff size=2>↓</font>";

   //冒泡法（多次循环）
   for(var goon=true;goon;)
   {
      goon=false;
      if(objTb.rows.length<=2) break; //若表格行数（含表头）少于等于2，无需排序

      for(var i=2;i<objTb.rows.length;i++)
      {
         //当前行列值
         var txtNow   = objTb.rows[i  ].cells[j].innerText;
         //上一行列值
         var txtFront = objTb.rows[i-1].cells[j].innerText;
         //数值型

         if(isNumber)
         {
            txtNow  = txtNow.replace(/,/gi,'');    //去除,
            txtNow  = txtNow-0;                    //类型转换
            txtFront= txtFront.replace(/,/gi,'');
            txtFront= txtFront-0;
         }

         //若为升序
         if(objThis.sortType=='ASC')
         {
            if(txtNow<txtFront) //若当前值小于上一行值
            {
               objTb.rows[i].swapNode(objTb.rows[i-1]); //交换行
               goon=true;
            }
         }
         else
         {
            if(txtNow>txtFront)
            {
               objTb.rows[i].swapNode(objTb.rows[i-1]);
               goon=true;
            }
         }
      }
   }

   //修改此处需要注意修改Style.js中的颜色定义
   for(var i=0;i<objTb.rows.length;i++)
   {
      //奇偶行颜色
      if(i%2==0)
         objTb.rows[i].bgColor = "#EBF3FD"; //偶数行颜色
      else
         objTb.rows[i].bgColor = "#FFFFFF"; //奇数行颜色
   }
}

//匹配查找
function matchSearch(objTxt,objItem)
{
   var sPos = objItem.selectedIndex;
   if(sPos<0) sPos=0;
   objItem.options[sPos].selected=false;
   if(event.keyCode==8) sPos=0;

   for(;sPos<objItem.options.length;sPos++)
   {
      var varOption = objItem.options[sPos];
      if(varOption.value.indexOf(objTxt.value)==0)
      {
         var lastPos = sPos+(objItem.size/2);
         if(lastPos>objItem.options.length)
            lastPos = objItem.options.length-1;
         objItem.options[lastPos].selected=true;
         objItem.options[lastPos].selected=false;
         varOption.selected = true;
         break;
      }
   }
   if(sPos==objItem.options.length)
      objItem.options[0].selected=true;
   if(objItem.options[0].value.indexOf(objTxt.value)!=0)
      objItem.options[0].selected=false;
}

//将数字字符串转换成保留2位小数的字符串
function toDecimal2(tmpNum)
{
	var tmpNumNew = ''+tmpNum;
	var pos = tmpNumNew.indexOf('.');
	if(pos>-1)
	{
		var tmp2 = tmpNumNew.substring(pos+2,pos+3);
		if(tmp2=='')
			tmpNumNew = tmpNumNew + '0';
		var tmp3 = tmpNumNew.substring(pos+3,pos+4);
		if(tmp3!='')
		{
			if(tmp3>='5')
			{
				tmpNumNew = tmpNumNew.substring(0,pos+3)*1000+10;
				tmpNumNew = ''+(Math.round(tmpNumNew)/1000);
			}
			else
				tmpNumNew = tmpNumNew.substring(0,pos+3)
		}
	}
	else
		tmpNumNew = tmpNumNew+".00";

	pos = tmpNumNew.indexOf('.');
	if(pos==-1)
		tmpNumNew=toDecimal2(tmpNumNew);
	if(pos>-1 && tmpNumNew.substring(pos+2,pos+3)=='')
		tmpNumNew=toDecimal2(tmpNumNew);

	return tmpNumNew;
}
//货币单位转换
function alterCurrency(iUnit,args)
{
   if(1==1) return; //暂时不执行以下代码

	for(var i=0;i<args.length;i++)
	{
	   var tmpObj = args[i];

	   if(typeof tmpObj == 'undefined') continue;

	   var aObj = new Array();
	   if(typeof tmpObj.length == 'undefined')
	      aObj[0] = tmpObj;
	   else
	      aObj = tmpObj;
	   for(var j=0;j<aObj.length;j++)
	   {
	      var tmpValue = (""+aObj[j].value).replace(/,/gi,'');
	      if(iUnit==0)
		      aObj[j].value = toDecimal2((tmpValue-0)*10000);
		   else
		      aObj[j].value = toDecimal2((tmpValue-0)/10000);
		}
	}
}

//获取选中的radio
function getCheckedRadio(radioObj)
{
   if(typeof radioObj=='undefined')
      return null;
   else if(typeof radioObj.length=='undefined')
      return radioObj;
   else
   {
      for(var i=0;i<radioObj.length;i++)
      {
         if(radioObj[i].checked)
            return radioObj[i];
      }
   }
   return null;
}

//获取radio值
function getRadioValue(radioObj)
{
   if(typeof radioObj=='undefined')
      return "";
   else if(typeof radioObj.length=='undefined')
      return radioObj.value;
   else
   {
      for(var i=0;i<radioObj.length;i++)
      {
         if(radioObj[i].checked)
            return radioObj[i].value;
      }
   }
   return "";
}


//获取CheckBox值   ----修改版本有待测试  modify by ccq
function getCheckBoxValue(checkboxObj,cnt,message)
{
   var sValue = "";
   if(typeof cnt=='undefined') cnt=2;
   if(typeof checkboxObj=='undefined')
      return "";
   else if(typeof checkboxObj.length=='undefined')
   {
      checkboxObj.checked=true;
      return checkboxObj.value;
   }
   else
   {
      for(var i=0;i<checkboxObj.length;i++)
      {
         if(checkboxObj[i].checked)
            sValue += ","+checkboxObj[i].value;
      }
   }
   if(sValue.length>0 && sValue.charAt(0)==',')
      sValue = sValue.substring(1);
   if(cnt==1 && sValue.indexOf(',')>-1)
   {
      alert("只能选择一条记录！");
      return "";
   }
   if(sValue=="")
   {
   	  if(typeof message!='undefined')
   	  {
   	  	alert(message);
   	  }
   	  else
   	  {
      	alert("请至少选择一条记录！");
      }
   }

   return sValue;
}

//获取CheckBox值
/*  这是原版本
function getCheckBoxValue(checkboxObj,cnt)
{
   var sValue = "";
   if(typeof cnt=='undefined') cnt=2;
   if(typeof checkboxObj=='undefined')
      return "";
   else if(typeof checkboxObj.length=='undefined')
   {
      checkboxObj.checked=true;
      return checkboxObj.value;
   }
   else
   {
      for(var i=0;i<checkboxObj.length;i++)
      {
         if(checkboxObj[i].checked)
            sValue += ","+checkboxObj[i].value;
      }
   }
   if(sValue.length>0 && sValue.charAt(0)==',')
      sValue = sValue.substring(1);
   if(cnt==1 && sValue.indexOf(',')>-1)
   {
      alert("只能选择一条记录！");
      return "";
   }
   if(sValue=="")
      alert("请至少选择一条记录！");

   return sValue;
}

*/






/********************以下是页面框架控制函数********************/

//打开一个窗口，该窗口打开一个框架，该框架同系统基本框架
function openFrame(varTarget,tmpURL,w,h)
{
   openWindow(appName+'/page.do?targetPage=../Public/OutFrame.html&targetFrame='+varTarget+'&tmpURL='+tmpURL,w,h);
}

/* 打开一个新窗口，指定其url、width、height */
function openWindow(varURL,w,h)
{
	if((typeof w) =="undefined") w = 780;
	if((typeof h) =="undefined") h = 540;
   //随机数
//   var rnd = ''+Math.random()*1000000/1; var pp = rnd.indexOf('.');if(pp==-1) pp=1; rnd=rnd.substring(0,pp);
//   var target="_new"+rnd;
   var vLeft = (window.screen.availWidth-w)/2;
   var vTop = (window.screen.availHeight-h)/2;vTop = vTop - 50;if(vTop<0) vTop = 0;
   var tmp="&";if(varURL.indexOf('?')==-1) tmp="?";

   var objWindow = window;
   if(typeof parent.varSelfWindowObject!='undefined')
      objWindow = parent.varSelfWindowObject;

	var newWindow = objWindow.open(varURL+tmp+"isOpenWindow=true",'',"width="+w+",Height="+h+",top="+vTop+",left="+vLeft+",status=yes,resizable=yes,scrollbars=yes");
}

/* 打开一个新窗口，指定其url、width、height */
function openDialog(varURL,w,h)
{
	if((typeof w) =="undefined") w = 780;
	if((typeof h) =="undefined") h = 540;

//   var rnd = ''+Math.random()*1000000/1; var pp = rnd.indexOf('.');if(pp==-1) pp=1; rnd=rnd.substring(0,pp);
//   var target="_new"+rnd;
   var vLeft = (window.screen.availWidth-w)/2;
   var vTop = (window.screen.availHeight-h)/2;vTop = vTop - 50;if(vTop<0) vTop = 0;
   var tmp="&";if(varURL.indexOf('?')==-1) tmp="?";

   var objButton = event.srcElement;
   objButton.tmpURL = varURL+tmp+"isOpenWindow=true";
	var objReturn = window.showModalDialog(appName+'/page.do?targetPage=../Public/SelectFrame.html',objButton,'dialogWidth:'+w+'px;dialogHeight:'+h+'px;dialogLeft:'+vLeft+';dialogTop:'+vTop+';scroll:auto;status:no;resizable:yes;help:no;');
	return objReturn;
}


/* 显示日期选择框 */
function showCalendar(objButton,objInput,datestyle)
{
   if(objInput.disabled || objInput.readOnly)
   {
      alert(objInput.title + "  不可修改，不能选择日期！");
      return;
   }
   if(typeof datestyle == 'undefined')
   		datestyle=3;
   objButton.screenX = event.screenX;
   objButton.screenY = event.screenY;
   objButton.objInput= objInput;
   objButton.datestyle=datestyle;
   window.showModelessDialog(appName+'/page.do?targetPage=/Public/DateFrame.jsp',objButton,'dialogWidth:175px;dialogHeight:200px;status:no;help:no');
}


//打开一个Form提交的窗口
function openFormWindow(formName,w,h)
{
   var objWindow = window;
   if(typeof parent.varSelfWindowObject!='undefined')
      objWindow = parent.varSelfWindowObject;
   if(typeof formName=='string')
      objWindow.openedFormObject = window[formName];
   else
      objWindow.openedFormObject = formName;
   openWindow(appName+'/page.do?targetPage=/Public/FormFrame.html&varOpenedFormName='+formName,w,h);
}


/**打开一个下载窗口*/
function openDownLoad(tmpURL,varW,varH)
{
   if(typeof varW == 'undefined')
      {varW = 335; varH = 260;}
   var x = (window.screen.width-varW)/2+"px";
   var y = (window.screen.height-varH)/2+"px";
   var objReturn = window.showModalDialog(appName+"/page.do?targetPage=/Public/DownLoadFrame.jsp",tmpURL,'dialogWidth:380px;dialogHeight:260px;dialogLeft:'+x+';dialogTop:'+y+';scroll:auto;status:no;resizable:yes;help:no;');
   return objReturn;
}



//框架内函数

//打开左页面
function openLeft(tmpURL,isReserve)
{
   if(typeof isReserve!='undefined' && isReserve==true)
      parent.leftFrame.isNeedClearMultiPageSessionInfo=false;
   closeTop();
   if(parent.allFrame.cols=="0,100%") parent.allFrame.cols="100%,0";
   if(typeof parent.leftFrame.pageLoadData!='undefined')
      parent.leftFrame.pageLoadData.style.display="";
   
   parent.leftFrame.navigate(tmpURL);
   parent.setTimeout('try{leftFrame.pageLoadData.style.display="none";}catch(ex){}',3000);
}
//关闭左页面
function closeLeft()
{
   closeTop();
   if(isInOutFrame) parent.parent.close();
   parent.allFrame.cols="100%,0";
   if(typeof parent.leftFrame.isBlankPage=='undefined')
      parent.leftFrame.navigate(blankPage);
}
//打开上页面
function openTop(tmpURL,isReserve)
{
   
   if(typeof isReserve!='undefined' && isReserve==true)
      parent.topFrame.isNeedClearMultiPageSessionInfo=false;
   closeDown();
   if((''+parent.allFrame.cols).indexOf(",0")>-1)
      parent.allFrame.cols="0,0,*";
   if(typeof parent.topFrame.pageLoadData!='undefined')
      parent.topFrame.pageLoadData.style.display="";
   parent.topFrame.navigate(tmpURL);
   parent.setTimeout('try{topFrame.pageLoadData.style.display="none";}catch(ex){}',3000);
}
function openTop2(tmpURL,isReserve, vWidth)
{
   if(typeof isReserve!='undefined' && isReserve==true)
      parent.topFrame.isNeedClearMultiPageSessionInfo=false;
   closeDown();
   var oWidth = 150;
   if(typeof vWidth!='undefined'){
   		oWidth = vWidth;
   }

   if((''+parent.allFrame.cols).indexOf(",0")>-1)
      parent.allFrame.cols= oWidth + ",10,*";
   if(typeof parent.topFrame.pageLoadData!='undefined')
      parent.topFrame.pageLoadData.style.display="";
   parent.topFrame.navigate(tmpURL);
   parent.setTimeout('try{topFrame.pageLoadData.style.display="none";}catch(ex){}',3000);
}
//关闭上页面
function closeTop(tmpURL)
{
   closeDown();
   if(isInOutFrame && typeof parent.leftFrame.isBlankPage!='undefined') parent.parent.close();
   parent.allFrame.cols="100%,0";
   if(typeof parent.topFrame.isBlankPage=='undefined')
      parent.topFrame.navigate(blankPage);
}
//打开下页面
function openDown(tmpURL)
{
	
	location=tmpURL;
	return;
   if((''+parent.rightFrame.rows).indexOf(",0")>-1)
      parent.rightFrame.rows="0,100%";
   if(typeof parent.downFrame.pageLoadData!='undefined')
      parent.downFrame.pageLoadData.style.display="";

   parent.downFrame.navigate(tmpURL);
   parent.setTimeout('try{downFrame.pageLoadData.style.display="none";}catch(ex){}',3000);
   
}
//关闭下页面
function closeDown(tmpURL)
{
/*   var iFrames =document.body.all.tags("IFRAME");
   for(findex=0;findex<iFrames.length;findex++)
   {
      iFrames(findex).isNeedClearMultiPageSessionInfo=false;
      alert(iFrames(findex).name);
   }
   */
   if(isInOutFrame
      && typeof parent.leftFrame.isBlankPage!='undefined'
      && typeof parent.topFrame.isBlankPage!='undefined')
      parent.parent.close();
   parent.rightFrame.rows="100%,0";
   if(typeof parent.downFrame.isBlankPage=='undefined')
      parent.downFrame.navigate(blankPage);
}
//关闭页面
function closeSelf()
{
   var iFrames =document.body.all.tags("IFRAME");
   for(findex=0;findex<iFrames.length;findex++)
   {
      iFrames(findex).isNeedClearMultiPageSessionInfo=false;
      //alert(iFrames(findex).name);
   }
   if(self.name=='downFrame')
      parent.topFrame.body_onFocus();
   else if(self.name=='topFrame')
      parent.leftFrame.body_onFocus();

   var objSelf = self;
   if(self.name=='messageFrame')
      objSelf = self.parent;
   if(objSelf.name=='mainFrame')
      objSelf.navigate(appName+'/page.do?targetPage=/Public/Blank.jsp');
   else if(objSelf.name=='leftFrame')
      closeLeft();
   else if(objSelf.name=='topFrame')
      closeTop();
   else if(objSelf.name=='downFrame')
      closeDown();
   else if(objSelf.name=='selectFrame')
      parent.close();
   else if(objSelf.name=='')
      parent.close();
   else if((''+objSelf.name).indexOf('_new')>-1)
      parent.close();

   if(event!=null)
   {
      event.returnValue=false;
      event.cancelBubble=true;
   }
}

//最大化按钮事件处理
function ShowOrHideLeftTopDown()
{
   if(self.name=='leftFrame')
   {
      if(parent.allFrame.cols.indexOf(",0")>-1)
         parent.allFrame.cols = parent.parent.tdWorkArea.clientWidth*0.22+","+parent.parent.tdWorkArea.clientWidth*0.78;
      else
         parent.allFrame.cols = parent.parent.tdWorkArea.clientWidth+",0";
   }
   else if(self.name=='topFrame')
   {
      if(parent.rightFrame.rows.indexOf(",0")>-1)
         parent.rightFrame.rows = parent.parent.tdWorkArea.clientHeight*0.4+","+parent.parent.tdWorkArea.clientHeight*0.6;
      else
         parent.rightFrame.rows = parent.parent.tdWorkArea.clientHeight+",0";
   }
   else if(self.name=='downFrame')
   {
      if(parent.rightFrame.rows.indexOf("0,100%")>-1)
         parent.rightFrame.rows = parent.parent.tdWorkArea.clientHeight*0.4+","+parent.parent.tdWorkArea.clientHeight*0.6;
      else
      	 parent.rightFrame.rows = "0,100%";
         
   }
}

function objValue_onKeydown()
{
   if(event.keyCode==8 || event.keyCode==46)
   {
      var objThis = event.srcElement;
      objThis.objKey.value='';
      objThis.value='';
      event.returnValue=false;
      event.keyCode=0;
   }
}
//打开一般的模式窗口
function showSelectorBea(objKey,objValue,tmpURL,varW,varH){
	
	if(typeof varW == 'undefined')
  	{varW = 150*2; varH = 100/2*3;}
	var varLeftTop = '';
	if(event != null){
		varLeftTop = ';dialogLeft:'+event.screenX+';dialogTop:'+event.screenY;
	}
	
	var objReturn=window.showModalDialog(tmpURL, '', 'dialogWidth:'+varW+'px;dialogHeight:'+varH+'px" + varLeftTop + ";scroll:auto;status:no;resizable:yes;help:no;');
	if(typeof objReturn != 'undefined'){
		if(typeof objKey == 'object'){
			objKey.value = objReturn[0];
		}
		if(typeof objValue!='string')
			objValue.value = objReturn[1];
		//调用其onchange事件
	  if(objKey.onchange!=null)
	     objKey.onchange();
	}
}

//打开对话框页面
function showSelector(objKey,objValue,tmpURL,varW,varH,isModal)
{
   showModalSelector(objKey,objValue,tmpURL,varW,varH,true);
}
function showModalSelector(objKey,objValue,tmpURL,varW,varH,isModal)
{
   //初始化键盘事件
   if(varInit==1)
   {
      if(typeof objValue=='string') objValue=objKey;
      if(objValue.readOnly)
         objValue.onkeydown = objValue_onKeydown;
      objValue.objKey = objKey;
      return;
   }

   //按钮对象，事件源
   var objButton = event.srcElement;
   if(typeof varW == 'undefined')
      {varW = 150*2; varH = 100/2*3;}
   objButton.tmpURL    = tmpURL;      //地址
   objButton.objKey    = objKey;      //key对象
   objButton.objValue  = objValue;    //value对象
   objButton.screenX  = event.screenX;//屏幕位置x
   objButton.screenY  = event.screenY;//屏幕位置y
   objButton.isModal = isModal;       //模式对话框
   //对话框关闭光标定位点
   if(typeof fm !='undefined'
   		&&typeof fm.chAuditorCode !='undefined'){
  		objButton.focusObj=fm.chAuditorCode;
   }
   if(typeof objButton.isModal=='undefined')
      objButton.isModal = true;

   var objReturn;
   if(objButton.isModal)
      objReturn=window.showModalDialog(appName+"/page.do?targetPage=/Public/SelectFrame.jsp",objButton,'dialogWidth:'+varW+'px;dialogHeight:'+varH+'px;dialogLeft:'+objButton.screenX+';dialogTop:'+objButton.screenY+';scroll:auto;status:no;resizable:yes;help:no;');
   else
      objReturn=window.showModelessDialog(appName+"/page.do?targetPage=/Public/SelectFrame.jsp",objButton,'dialogWidth:'+varW+'px;dialogHeight:'+varH+'px;dialogLeft:'+objButton.screenX+';dialogTop:'+objButton.screenY+';scroll:auto;status:no;resizable:yes;help:no;');
   return objReturn;
}

/* 关闭模式选择器 */
function closeSelector(sKeys,sValues,sSp1)
{
  if(typeof sValues=='undefined' 
      || sValues=='undefined')
  {
     var aKeyValue = getKeyValue(sKeys);    　　　　　
     sKeys   = aKeyValue[0];
     sValues = aKeyValue[1];
  }
  if(typeof sKeys=='undefined') sKeys='';
  if(typeof sValues=='undefined') sValues='';

  if(typeof sSp1 != 'undefined') 
     sKeys = sKeys.replace(/,/gi,sSp1);
     
  

  var objButton = parent.window.dialogArguments;
  objButton.objKey.value   = sKeys;
  objButton.objValue.value = sValues;

  //调用其onchange事件
  if(objButton.objKey.onchange!=null)
     objButton.objKey.onchange();
  //对话框关闭光标定位点
   if(typeof objButton.focusObj !='undefined'){
  		//objButton.focusObj.focus();
   }
  parent.close();
}


/* 获取键和值 key=x,x,x value=x；x；x */
function getKeyValue(objTmp)
{
  var sKeys   = ''; var sValues = '';
  var aKeyValue = new Array(2);
  aKeyValue[0] = sKeys;
  aKeyValue[1] = sValues;

  if(typeof objTmp =='undefined')
     return aKeyValue;
  //若是下拉选择框
  if(objTmp.tagName=='SELECT')
  {
    if(!objTmp.multiple)
    {
      var aKeyValue = new Array(2);
      if(objTmp.selectedIndex>-1)
      {
         aKeyValue[0] = objTmp.value;
         aKeyValue[1] = objTmp.options[objTmp.selectedIndex].alias;
      }
      return aKeyValue;
    }
    objTmp = objTmp.options;
  }

  //只有一条记录
  if(typeof objTmp.length=='undefined')
  {
    if(objTmp.checked)
    {
      sKeys   = objTmp.value;
      sValues = objTmp.alias;
    }
  }
  else
  {
    if(objTmp[0].tagName=='OPTION')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].selected)
        {
          sKeys    = sKeys   + objTmp[i].value + ",";
          sValues  = sValues + objTmp[i].alias + "；";
        }
      }
      sKeys   = sKeys.substring(0,sKeys.length-1);
      sValues = sValues.substring(0,sValues.length-1);
    }
    else if(objTmp[0].type=='radio')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].checked && !objTmp[i].disabled)
        {
          sKeys   = objTmp[i].value;
          sValues = objTmp[i].alias;
          break;
        }
      }
    }
    else if(objTmp[0].type=='checkbox')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].checked && !objTmp[i].disabled)
        {
          if(objTmp[i].value!='')
          {
	          sKeys    = sKeys     + objTmp[i].value + ",";
	          sValues  = sValues + objTmp[i].alias + "；";
          }
        }
      }
      sKeys   = sKeys.substring(0,sKeys.length-1);
      sValues = sValues.substring(0,sValues.length-1);
    }
    else
    {
      alert('参数对象的类型错误，只能是radio、checkbox或option！')
      return;
    }
  }
  aKeyValue[0] = sKeys;
  aKeyValue[1] = sValues;
  return aKeyValue;
}

//双击取消只读，必须同时按下ctrl+alt
function dblClickToCancelReadOnly()
{
   if(event.ctrlKey && event.altKey
      && event.srcElement!=null
      && event.srcElement.readOnly)
   {
      event.srcElement.readOnly=false;
      event.srcElement.style.backgroundColor="#ffffff";
      if((''+event.srcElement.onkeydown).indexOf("objValue_onKeydown")>-1)
         event.srcElement.onkeydown=null;
   }
}

function obj_Focus(varObj)
{
   try{varObj.focus();}catch(ex){};
}

/* 关闭模式选择器   专用于基础指标,指标code后面加下划线用于区别派生指标*/
function basiccloseSelector(sKeys,addchar,sValues,sSp1)
{
  if(typeof sValues=='undefined'  
      || sValues=='undefined')
  {
   
     var aKeyValue = basicgetKeyValue(sKeys,addchar);
     sKeys   = aKeyValue[0];
     sValues = aKeyValue[1];
  }
  if(typeof sKeys=='undefined') sKeys='';
  if(typeof sValues=='undefined') sValues='';

  if(typeof sSp1 != 'undefined') 
     sKeys = sKeys.replace(/,/gi,sSp1);

  var objButton = parent.window.dialogArguments;  
  objButton.objKey.value   = sKeys;
  objButton.objValue.value = sValues;

  //调用其onchange事件
  if(objButton.objKey.onchange!=null)
     objButton.objKey.onchange();

  parent.close();
}


/* 关闭模式选择器   专用于基础指标,在表达式后面加再次选取的基础指标*/
function basiccloseAddSelector(sKeys,addchar,sValues,sSp1)
{
  if(typeof sValues=='undefined'  
      || sValues=='undefined')
  {
   
     var aKeyValue = basicgetKeyValue(sKeys,addchar);
     sKeys   = aKeyValue[0];
     sValues = aKeyValue[1];
  }
  if(typeof sKeys=='undefined') sKeys='';
  if(typeof sValues=='undefined') sValues='';

  if(typeof sSp1 != 'undefined') 
     sKeys = sKeys.replace(/,/gi,sSp1);

  var objButton = parent.window.dialogArguments;
  objButton.objKey.value   = objButton.objKey.value+sKeys;
  objButton.objValue.value = sValues;

  //调用其onchange事件
  if(objButton.objKey.onchange!=null)
     objButton.objKey.onchange();

  parent.close();
}


/* 获取键和值 key=x,x,x value=x；x；x   专用于基础指标,指标code后面加下划线用于区别派生指标*/
function basicgetKeyValue(objTmp,addchar)
{
  var sKeys   = ''; var sValues = '';
  var aKeyValue = new Array(2);
  aKeyValue[0] = sKeys;
  aKeyValue[1] = sValues;

  if(typeof objTmp =='undefined')
     return aKeyValue;
  //若是下拉选择框
  if(objTmp.tagName=='SELECT')
  {
    if(!objTmp.multiple)
    {
      var aKeyValue = new Array(2);
      if(objTmp.selectedIndex>-1)
      {
         aKeyValue[0] = addchar+objTmp.value;
         aKeyValue[1] = objTmp.options[objTmp.selectedIndex].alias;
      }
      return aKeyValue;
    }
    objTmp = objTmp.options;
  }
 
  //只有一条记录
  if(typeof objTmp.length=='undefined')
  {
    if(objTmp.checked)
    {
      sKeys   = addchar+objTmp.value;
      sValues = objTmp.alias;
    }
  }
  else
  {
    if(objTmp[0].tagName=='OPTION')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].selected)
        {
          sKeys    = sKeys   + addchar + objTmp[i].value + ",";
          sValues  = sValues + objTmp[i].alias + "；";
          //alert('sKeys======'+sKeys);
        }
      }
      sKeys   = sKeys.substring(0,sKeys.length-1);
      sValues = sValues.substring(0,sValues.length-1);
    }
    else if(objTmp[0].type=='radio')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].checked && !objTmp[i].disabled)
        {
          sKeys   = addchar+objTmp[i].value;
          sValues = objTmp[i].alias;
          break;
        }
        //alert('radiosKeys======'+sKeys);
      }
    }
    else if(objTmp[0].type=='checkbox')
    {
      for(var i=0;i<objTmp.length;i++)
      {
        if(objTmp[i].checked && !objTmp[i].disabled)
        {
          sKeys    = sKeys     + addchar+objTmp[i].value + ",";
          sValues  = sValues + objTmp[i].alias + "；";
        }
        //alert('sKeys======'+sKeys);
      }
      sKeys   = sKeys.substring(0,sKeys.length-1);
      sValues = sValues.substring(0,sValues.length-1);
    }
    else
    {
      alert('参数对象的类型错误，只能是radio、checkbox或option！')
      return;
    }
  }
  aKeyValue[0] = sKeys;
  aKeyValue[1] = sValues;
  return aKeyValue;
}

///////////////////////////////////////新增/////////////////////////////////
//通用查询系统检查
function  constructWhere(){
var where=" where 1=1 ", temp="",filedName="",strValue="";
var number=0;
for(var i=0;i<searchfm.length;i++){
 temp=searchfm[i].name.substring(0,1);
 if(temp=="@" || temp=="Y"){
	  temp=searchfm[i].value;
	  filedName=searchfm[i].name.substring(1,searchfm[i].name.length);
	  if(temp!=""){
	       if(number==0){where+=" and ";number=1; };
		    where+=filedName+" = '"+temp+"' and ";
	  } 
}	  
}
 where=where.substring(0,where.length-4);
 searchfm.wheres.value=where;
 alert(where);
 }

//查询条件置空事件
function Reset(formName)
{
	var allObject=formName.elements;
	for(index =0;index<allObject.length;index++)
	{
		//alert(allObject(index).tagName);
		if(allObject(index).tagName=='INPUT')
			allObject(index).value='';	
		if(allObject(index).tagName=='SELECT')
			allObject(index).selectedIndex=0;
	}
}

	//提交查询信息
	var checkSubmitFlg = false; 
  function checkSubmit(){
    	  //alert('checkSubmit checkSubmitFlg='+checkSubmitFlg); 
	      if (checkSubmitFlg == true) { 
	         return false; 
	      } 
	      checkSubmitFlg = true; 
	      return true;
  } 
	/** comeback **/
	//window.history.forward();
	/**right click **/
	document.oncontextmenu=function stop(){
		 //alert('right click..');
	   //return false;
	}
	/**left double click **/
	document.ondblclick = function docondblclick() { 
   	  //alert('ondbclick checkSubmitFlg'); 
      window.event.returnValue = false; 
  }
	function Search_Submit(){
		//constructWhere();
		if(!checkSubmit())return;
		searchfm.submit();
	}

		/*
	*得到指定日期之后若干天后的日期
	*getNextDate(oldDate,diffDate)
	* oldDate:YYYYMMDD
	* diffDate:日期差
	*/
	function getNextDate(oldDate,diffDate){
		 var tempstr=oldDate.substring(4,6);
        if(tempstr.charAt(0)=='0')tempstr=tempstr.substring(1);
        var oy=parseInt(oldDate.substring(0,4));
	    var om=parseInt(tempstr);
	    tempstr=oldDate.substring(6);
	    if(tempstr.charAt(0)=='0')tempstr=tempstr.substring(1);
	    var od=parseInt(tempstr);
		 
		 var dt=new Date(oy,om-1,od);
		 dt.setDate(dt.getDate()+diffDate);
		 var y=dt.getYear(); 
		 var m=dt.getMonth()+1;
		 var d=dt.getDate();
		 if(m<10)m='0'+m;
		 if(d<10)d='0'+d;
		 //alert(y+''+m+''+d);
		 return y+''+m+''+d;
	}
	/*
	*no userless
	*/
	function getNextDate_bak(oldDate,dayadd)
   {
       var tempstr=oldDate.substring(4,6);
       if(tempstr.charAt(0)=='0')tempstr=tempstr.substring(1);
       var oy=parseInt(oldDate.substring(0,4));
	   var om=parseInt(tempstr);
	   tempstr=oldDate.substring(6);
	   if(tempstr.charAt(0)=='0')tempstr=tempstr.substring(1);
	   var od=parseInt(tempstr);
	   var sumday=od+dayadd;
		while(sumday>29||sumday>30||sumday>31){
		   if(om==1||om==3||om==5||om==7||om==8||om==10||om==12){
		   		sumday-=31;
		   		om++;
		   }else if(om==2){
		   		sumday-=28;
		   		om++;
		   }else if(om==4||om==6||om==9||om==11){
		      sumday-=30;
		      om++;
		   }else{
		      break;
		   }
		   if(om>=13){
			    om=om-12;
			    oy=oy+1;
		   }
		}
	    var dt=new Date(oy,om-1,sumday);    
	    //alert('year='+dt.getYear()+'===month='+(dt.getMonth())+'====date='+dt.getDate());
        var y=dt.getYear(); 
		var m=dt.getMonth()+1;
		var d=dt.getDate();
		if(m<10)m='0'+m;
		if(d<10)d='0'+d;
		return y+''+m+''+d;      
   }
   /*
	*计划日期大小比较判断
	*/
	function isPlanEarly(sDate,bDate){
		if(!isDateType(sDate)
			||!isDateType(bDate))
			return false;
		if(sDate>bDate){
			alert('开始日期不能大于结束日期!');
			return false;
		}
		return true;
	}
	/*
	*日期有效性判断
	*/
	function isDateType(value,canEmpty){
		var isOk=false;
		if (value == "") {
			isOk=(canEmpty != undefined && canEmpty != null && canEmpty == false) ? false : true;
			if(isOk)
        		return true;
        	alert('日期不能为空,格式:YYYYMMDD!');
        	return false;
    	}
	    var pattern = /^(\d{4})(\d{2})(\d{2})$/g;
	    if (!pattern.test(value)) {
	        alert('请检验日期格式:YYYYMMDD!');
        	return false;
	    }
	    var arrDate = new Array(3);
    	arrDate[0]=value.substring(0,4);
    	arrDate[1]=value.substring(4,6);
    	arrDate[2]=value.substring(6);
	    if (parseInt(arrDate[0], 10) < 100) {
	        arrDate[0] = 2000 + parseInt(arrDate[0], 10) + "";
	    }
	    var date = new Date(arrDate[0], (parseInt(arrDate[1], 10) - 1) + "", arrDate[2]);
	    if (date.getFullYear() == arrDate[0] && date.getMonth() == (parseInt(arrDate[1], 10) - 1) + "" && date.getDate() == arrDate[2]) {
	        return true;
	    } else {
	        alert('请检验日期格式:YYYYMMDD!');
        	return false;
	    }
	}
	/*
	*关系日期大小判断
	*/
	function isRelationEarly(sDate,bDate){
		if(!isDateType(sDate)
			||!isDateType(bDate))
			return false;
		if(sDate >= bDate){
			alert('关系建立日期要小于结束日期');
			return false;
		}
		return true;
	}
	/*
	*关系登记部分分润精度的处理
	*/
	function setPrecision(divValueFiled){
		if(divValueFiled.value.indexOf('.')==-1)return;
		var divValue=parseFloat(divValueFiled.value.replace(/\s/g,""));
		divValue=Math.round(divValue*100)/100.00;
		divValueFiled.value=divValue;
	}
	/*
	*判断指定日期格式的有效性
	*value：要交验的日期字符串
	*patternType:1- yyyymmdd 2- yyyy-mm-dd 3- yyyy/mm/dd
	*canEmpty:是否为空
	*/

	function isDateByPattern(value,patternType,FieldCNName,canEmpty){
		var isOk=false;
		if(FieldCNName==undefined)
			FieldCNName='';
		if (value == "") {
			isOk=(canEmpty != undefined && canEmpty != null && canEmpty == false) ? false : true;
			if(isOk)
        		return true;
        	alert(FieldCNName+'日期不能为空!');
        	return false;
    	}
	    var pattern = /^(\d{4})(\d{2})(\d{2})$/g;
	    var patternStyle='YYYYMMDD';
	    if(patternType=='2'){
	    		pattern=/^(\d{4})-(\d{2})-(\d{2})$/g;
	    		patternStyle='YYYY-MM-DD';
	    }else if(patternType=='3'){
	    		pattern=/^(\d{4})(\/)(\d{2})(\/)(\d{2})$/g;
	    		patternStyle='YYYY/MM/DD';
	   	}
	   	
	    if (!pattern.test(value)) {
	        alert('请检验'+FieldCNName+'日期格式:'+patternStyle);
        	return false;
	    }
	    if(patternType=='2'){
	    		value=value.replace(/[-]/gi,'');
	    }else if(patternType=='3'){
	    	     	//value=value.replace(/[/]/gi,'');
	   	}
	    var arrDate = new Array(3);
    	arrDate[0]=value.substring(0,4);
    	arrDate[1]=value.substring(4,6);
    	arrDate[2]=value.substring(6);
	    if (parseInt(arrDate[0], 10) < 100) {
	        arrDate[0] = 2000 + parseInt(arrDate[0], 10) + "";
	    }
	    var date = new Date(arrDate[0], (parseInt(arrDate[1], 10) - 1) + "", arrDate[2]);
	    if (date.getFullYear() == arrDate[0] && date.getMonth() == (parseInt(arrDate[1], 10) - 1) + "" && date.getDate() == arrDate[2]) {
	        return true;
	    } else {
	        alert('输入的'+FieldCNName+'日期不合法!');
        	return false;
	    }
	}
	/*
	*通用的判断表单字段有效性的方法
	*FiledCNName:要交验字段的中文名称
	*fieldValue： 交验的字符串信息
	*judgeType：  要判断的字段的数据类型 1-非空 2-数字 3-日期
	*patternType：日期类型 1- yyyymmdd 2- yyyy-mm-dd 3- yyyy/mm/dd
	*
	*/
	function validateFiledType(FieldCNName,fieldValue,judgeType,patternType){
					fieldValue=fieldValue.replace(/\s/g,"");
					if(fieldValue==''){
							//为空判断
							if(judgeType=='1'){
								alert(FieldCNName+'信息不能为空!');
								return false;
							}
					}else{
							if(judgeType=='2'){
									//数字校验
									if(!isDecimal(fieldValue)){
											alert(FieldCNName+'应当是数字类型!');
											return false;
									}
							}else if((patternType=='1'||patternType=='2'||judgeType=='3')
										 &&!isDateByPattern(fieldValue,patternType,FieldCNName)){
									//日期校验
									return false;
							}
					}
					return true;
			}
	