
/**
 * 得到上下文路径
 */
function getAppPath() {
	var path = "";
	var pathname = document.location.pathname;
	if (pathname.length > 0) {
		if (pathname.charAt(0) != "/") {
			path += "/";
		}
		var index = pathname.indexOf("/", 1);
		if (index > 0) {
			path += pathname.substring(0, index);
		} else {
			path += pathname;
		}
		return path;
	} else {
		return "";
	}
}
/**
 * 显示正在加载中
 */
function maskDocAll() {
	var msg = "Loading...";
	maskDocAllWithMessage(msg);
}
/**
 * 显示一些mask信息
 */
function maskDocAllWithMessage(mess) {
	var path = getAppPath();
	var maskAlert = "&nbsp;&nbsp;<image src='" + path + "/widgets/ext/resources/images/default/grid/loading.gif' style='width: 14px; height: 14px;' align='absmiddle'/>&nbsp;&nbsp;" + mess + "&nbsp;&nbsp;&nbsp;&nbsp;";
	var body = Ext.getBody();
	body.mask(maskAlert, "page-loading");
	Ext.fly(Ext.query("div[class^=ext-el-mask-msg]")[0]).center();
}

/**
 * 解除mask信息的显示
 */
function unmaskDocAll() {
	var body = Ext.getBody();
	body.unmask();
}

/**
 * 显示id容器，效果为向上收起
 *  tl     左上角(默认)
 *	t      上居中
 *	tr     右上角
 *	l      左边界的中央
 *	c      居中
 *	r      右边界的中央
 *	bl     左下角
 *	b      下居中
 *	br     右下角
 */
function display(id,s) {
	if (s==undefined){ s="t";}
	Ext.get(id).slideIn(s, {easing: 'easeOut',
							duration: .5,
							remove: false,
   							useDisplay: true});
}

/**
 * 隐藏id容器，效果为从上展开
 */
function undisplay(id,s) {
	if (s==undefined){ s="t";}
	Ext.get(id).slideOut(s, {easing: 'easeOut',
					    	 duration: .5,
					    	 remove: false,
 							 useDisplay: true});
}
/**
 * 高亮显示id容器，效果为从上展开
 */
function highlight(id) {
	try {
		Ext.get(id).highlight();
	} catch (e) {
	}
}

/**
 * 弹出日期选择菜单
 */
function dateClick(elemId) {

	window.WdatePicker({el:document.getElementById(elemId),
						  readOnly:true,
						  dateFmt:'yyyy-MM-dd',
						  realDateFmt:'yyyy-MM-dd',
						  isShowWeek:true,
						  isShowClear:true,
						  isShowToday:true,
						  isShowOthers:true,
						  autoPickDate:true,
						  qsEnabled:true,
						  skin:'ext'
						  });
}

/**
 *弹出年月选择菜单
**/
function dateClick2(elemId) {

	window.WdatePicker({el:document.getElementById(elemId),
						  readOnly:true,
						  dateFmt:'yyyy-MM',
						  realDateFmt:'yyyy-MM',
						  isShowWeek:true,
						  isShowClear:true,
						  isShowToday:true,
						  isShowOthers:true,
						  autoPickDate:true,
						  qsEnabled:true,
						  skin:'ext'
						  });
}
/**
 *弹出天选择菜单
**/
function dateClick3(elemId) {

	window.WdatePicker({el:document.getElementById(elemId),
						  readOnly:true,
						  dateFmt:'dd',
						  realDateFmt:'dd',
						  isShowWeek:false,
						  isShowClear:true,
						  isShowToday:false,
						  isShowOthers:true,
						  autoPickDate:false,
						  qsEnabled:false,
						  skin:'ext',
						  minDate:'01',
						  maxDate:'27'
						  });
}

/**
 * 是否是整数（包括负整数）的 check
 */
function IsInteger(value) {

	if (/^(\+|-)?\d+$/.test(value)) {
		return true;
	}
	return false;
}

/**
 * 是否是数字（包括四位小数点）的 check
 */
function IsBigdecimal(value) {
	
	if (/^[0-9]+(.[0-9]{0,4})?$/.test(value) 
		|| /^(\+|-)?\d+$/.test(value)) {
		return true;
	}
	return false;
}

/**
 * 是否一个有效面额
 * @return
 */
function IsFaceValue(value){
	if (/^[0-9]+(.[0-9]{0,2})?$/.test(value) 
			|| /^(\+|-)?\d+$/.test(value)) {
			return true;
		}
		return false;
}

/**
 * 是否是空串的 check
 */
function IsSpace(value) {
	
	if (value.length == 0) return true;

	var astr = "";
	var dstr = "";
	var flag = 0;
	for (i = 0; i < value.length; i++) {
		if ((value.charAt(i) != " ") || (flag != 0)) {
			dstr += value.charAt(i);
			flag=1;
		}
	}
	flag = 0;
	for (i = dstr.length - 1; i >= 0; i--) {
		if ((dstr.charAt(i) != " ") || (flag != 0)) {
			astr += dstr.charAt(i);
			flag=1;
		}
	}
	dstr = "";
	for (i = astr.length - 1; i >= 0; i--) {
		dstr += astr.charAt(i);
	}
	if(dstr.length <= 0) return true;
	
	return false;
}

/**
 * 消息弹出框
 */
function messageDisplay(title,msg,components,width,closeFn) {

	if (width == undefined) width=400;

	Ext.MessageBox.show({
	     title:title,
	     msg:msg,
	     buttons:{"ok":"OK"},
	     animEl:components,
	     width:width,
	     icon:Ext.MessageBox.INFO,
	     fn:closeFn,
	     multiline:false,
	     closable:true
	});
}
/**
 * 错误弹出框
 */
function errorDisplay(msg,title,width,closeFn,components) {

	if (width == undefined) widht=300;
	if (title == undefined) title="错误";
	
	Ext.MessageBox.show({
	     title:title,
	     msg:msg,
	     buttons:{"ok":"OK"},
	     animEl:components,
	     width:width,
	     icon:Ext.MessageBox.ERROR,
	     fn:closeFn,
	     multiline:false,
	     closable:true
	});
}

/**
 * 列表上有全选按钮，点击每一行选中或取消
 */
function rowClick(event,index) {
	event = (event)?event:window.event;
	var src = event.srcElement||event.target;
	if(src.type!=undefined)return;
	document.getElementsByName("ec_choose").item(index-1).checked
		=!document.getElementsByName("ec_choose").item(index-1).checked;
}
/**
 * 列表上radiobox，点击每一行选中
 */
function rowRadioClick(event,index) {
	event = (event)?event:window.event;
	var src = event.srcElement||event.target;
    if(src.type!=undefined)return;
    if (!document.getElementsByName("ec_choose").item(index-1).checked)
    	document.getElementsByName("ec_choose").item(index-1).checked = true;
}
/**
*二次确认
*/
function confirm(context,operation){
Ext.MessageBox.confirm("确 认", context,function(btn) {
								if (btn == "yes") {
									operation();
								}
							});
}

function view(action,parameters,size){
		var tempParameter='?';
		if(parameters!=null){
			for(var key in parameters){
				tempParameter+=key+"="+parameters[key]+"&";
			}
			if(size==''){
				window.open(action+tempParameter,'myPage','height=500,width=900,top=50,left=60,toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,status=no,location=no');
			}else{
				window.open(action+tempParameter,'myPage',size);
			}
		}
		
	
	}

