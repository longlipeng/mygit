/* ================================================================ 
This copyright notice must be kept untouched in the stylesheet at 
all times.

The original version of this script and the associated (x)html
is available at http://www.stunicholls.com/menu/pro_drop_1.html
Copyright (c) 2005-2007 Stu Nicholls. All rights reserved.
This script and the associated (x)html may be modified in any 
way to fit your requirements.
=================================================================== */


/*菜单*/
	function qiehuan(num){
		
		//if(num==5){用于判断组合风险当前位置显示的信息，0为显示首页信息，1为显示当前位置，其它为显示子页面链接；
			//pagebody.locat(3);
		//}
		 if (num==0){pagebody.locat(0);} else{pagebody.locat(1); } 
		//}
		
		for(var id = 0;id<=6;id++)
		{
			
			if(id==num)
			{
				document.getElementById("mynav"+id).className="up";
			}
			else
			{
				document.getElementById("mynav"+id).className="down";
			}
		}
	}

/*---控制弹出层显示/隐藏----*/
function mouseover(id){
	var obk=document.getElementById(id);

	obk.style.display="";
}
function mouseout(id){
	var obk=document.getElementById(id);

	obk.style.display="none";
}

/*---列表区鼠标移上去变色----*/
function OMOver(OMO){OMO.style.backgroundColor='#cbeaf2';}
function OMOut(OMO){OMO.style.backgroundColor='';}
/*---信息输入区鼠标移上去变色----*/
function OM2Over(OMO){OMO.style.backgroundColor='#f1f9fc';}
function OM2Out(OMO){OMO.style.backgroundColor='';}

/*---控制伸缩及更换背景图片（指定ID）----*/

function divToggle(e,id)
{
var obj=document.getElementById(id);
var imgd=document.getElementById(e);
 if (obj.style.display == 'none') {
obj.style.display = '';
if (id=="Headerdiv"){
	e.style.background="url(../commontheme/default/images/pic/nav_r.gif) no-repeat right top";	
	}else{
		
	imgd.style.src="../../../CommonTheme/Default/Images/dtree/nolines_plus.gif";	
	
		}

}
else {

obj.style.display = 'none';

if(id=="Headerdiv"){		
		e.style.background="url(../commontheme/default/images/pic/nav_r_down.gif) no-repeat right top";	
	}else{
		
	imgd.style.src="../../../CommonTheme/Default/Images/dtree/nolines_plus.gif";	
	
		}
	

 }
} 
/*---选项卡TAB区内容伸缩并更换背景图片（ID可变）----*/
function tabToggle(e,id)
{
var obj=document.getElementById(id);
 if (obj.style.display == 'none') {
     obj.style.display = '';
			
	e.style.background="url(../../commontheme/default/images/pic/tab_bgup.gif) no-repeat right top ";	
}
else {

obj.style.display = 'none';
		e.style.background="url(../../commontheme/default/images/pic/tab_bgdown.gif) no-repeat right top";	
}
} 

/*---控制指定ID内容伸缩----*/

function expand(id)
{
var obj=document.getElementById(id);

 if (obj.style.display == 'none') {
obj.style.display = '';
 }
else {

obj.style.display = 'none';

} 
}

/*---控制指定ID内容伸缩----*/

function dbexpand(ida,idb)
{
var obj=document.getElementById(ida);
var obc=document.getElementById(idb);

 if (obj.style.display == 'none') {
obj.style.display = '';obc.style.display='none';
 }
else {
obc.style.display='';
obj.style.display = 'none';

} 
}


/*---按钮跳转+提示信息----*/
function winconfirm(mess,url)
{ 
question = alert(mess) 	
if (question != "0")
{ 		
window.location.href=url; 	
} 
} 

/*---按钮跳转有确定、取消----*/
function wincancel(mess,url)
{ 

if(confirm(mess)){
 window.location.href=url;
}else{}

} 

/*---删除提示----*/
function windel(mess1,mess2)
{ 

if(confirm(mess1)){
alert(mess2);
}else{}

} 

//---含按钮区选项卡的隐藏和显示----
function m(m,total)
{
	for(var i=1; i<=total ; i++){
		if(i==m){
			document.getElementById(i).style.display='';
			document.getElementById('m_'+m).className = 'tab_up';
			document.getElementById('b_'+i).style.display='';
		}else{
			document.getElementById(i).style.display='none';
			document.getElementById('m_'+i).className = 'tab_down';
			document.getElementById('b_'+i).style.display='none';
		}
	}
}
//---纯选项卡的隐藏和显示----
function mm(m,total)
{
	for(var i=1; i<=total ; i++){
		if(i==m){
			document.getElementById(i).style.display='';
			document.getElementById('m_'+m).className = 'tab_up';
		}else{
			document.getElementById(i).style.display='none';
			document.getElementById('m_'+i).className = 'tab_down';
		}
	}
}

//-----------蓝色选项卡样式切换--------

function mt(m,total)
{
		for(var i=1; i<=total ; i++){
					document.getElementById(i).style.display='none';
			}

	for(var i=1; i<=total ; i++){
		if(i==m){
			document.getElementById('m_'+m).className = 'tab2_up';
						document.getElementById(i).style.display='';

		}else{
			document.getElementById('m_'+i).className = 'tab2_down';
						document.getElementById(i).style.display='none';

		}
	}
}
//-----------用于首页展示--------

function mdd(m,total,id)
{
		

	for(var i=1; i<=total ; i++){
		if(i==m){
			document.getElementById(id+'_'+i).className = 'but_d_hover';
						document.getElementById(id+'v_'+i).style.display='';

		}else{
			document.getElementById(id+'_'+i).className = '';
						document.getElementById(id+'v_'+i).style.display='none';

		}
	}
}
function mhh(m,total,id)
{
		

	for(var i=1; i<=total ; i++){
		if(i==m){
			document.getElementById(id+'_'+i).className = 'but_b_hover';
						document.getElementById(id+'v_'+i).style.display='';

		}else{
			document.getElementById(id+'_'+i).className = '';
						document.getElementById(id+'v_'+i).style.display='none';

		}
	}
}
//---搜索区查询按钮函数----
function winsearch()
{
var abj=document.getElementById('tab_search');
var bbj=document.getElementById('hint');
var cbj=document.getElementById('tab_mess');
var dbj=document.getElementById('tab_css');

abj.style.display='';
bbj.style.display='none';
cbj.style.display='';
//dbj.style.background="url(../../commontheme/default/images/pic/tab_bgdown.gif) no-repeat right top";	
	}
	
	
//---sidebar菜单伸缩函数----

function onedisplay(m,total)
{
	for(var i=1; i<=total ; i++){
		if(i==m){
			if (document.getElementById('menu_'+i).style.display == 'none') {
				
				
			//document.getElementById('op_'+i).style.background="url(../../commontheme/default/images/pic/sidebar_openmenu.gif) ";
			document.getElementById('menu_'+i).style.display='';
			
			}else{
				document.getElementById('menu_'+i).style.display='none';
				}
     	}
		else{
			
			     document.getElementById('menu_'+i).style.display='none';
			//document.getElementById('op_'+i).style.background="url(../../commontheme/default/images/pic/sidebar_closemenu.gif) ";
		
			}
	}
}

//---用于控制菜单链接，同时改变sidebar框架和mainbody框架的链接 ----
function dbclick(a,b)
{
	pagebody.sidebar.location.href=a;
	pagebody.mainbody.location.href=b;

	}

//--------

function showdialog(theURL,winName) { //v2.0

window.showModalDialog(theURL,winName,"dialogWidth=500px;dialogHeight=220px;status:no;scrollbars:no;resizable:no;scroll:no;");

}
function showdialog2(theURL,winName) { //v2.0

window.showModalDialog(theURL,winName,"dialogWidth=600px;dialogHeight=400px;status:no;scrollbars:no;resizable:no;scroll:no;");

}


//-----------用于首页展示--------
function mtv(m,total) 
{
	for(var i=1; i<=total ; i++){
		document.getElementById(i).style.display='none';
		
		if(i<=3){document.getElementById('m_'+i).className = 'tab2_down';
		

		if(i==m){
			document.getElementById(i).style.display='';
			document.getElementById('m_'+m).className = 'tab2_up';

		}else{
			document.getElementById(i).style.display='none';
						document.getElementById('m_'+i).className = 'tab2_down';

			}
		}else{
			if(i==m){
			document.getElementById(i).style.display='';
						document.getElementById('mt_'+i).className = 'but_r_hover';

		}else{
			document.getElementById(i).style.display='none';	
									document.getElementById('mt_'+i).className = '';

			}
			}
	}
				

}

////////////////////鼠标及键盘事件控制////////////////////////////
function OverKey(){
	   if ((event.ctrlKey)&&(event.keyCode==67)){ // 屏蔽 Ctrl+c
		   event.keyCode=0;
		   event.returnValue = false;
		   return false;
	   }

	   if ((window.event.altKey)&&
	     ((window.event.keyCode==37)|| // 屏蔽 Alt+ 方向键 ←
	     (window.event.keyCode==39))){ // 屏蔽 Alt+ 方向键 →
	  event.returnValue=false;
	  return false;
	}
}

document.onkeydown=OverKey;

/*
 * 阻止网页上的右键操作
 * 
 */
function Click(){
 	window.event.returnValue=false;
 }

document.oncontextmenu=Click;// 阻止网页右键的脚本

