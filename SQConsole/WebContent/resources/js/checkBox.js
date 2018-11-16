/*
-------------------------------------------------------------------------------
文件名称：checkBox.js
说    明：JavaScript脚本，一些和复选筐有关的脚本
版    本：1.0
修改纪录:
---------------------------------------------------------------------------
时间		修改人		说明
2005-4-20	zhouming		创建
------------------------------------------------------------------------------- 	
*/

/*
用途：得到复选框被选中的数目
输入：checkboxID：复选框的名称或者ID
返回：返回该复选框中被选中的数目	
*/	
function checkBoxSelectCount( checkboxID ) {
	var check = 0;
	if( document.all(checkboxID) == null)
		return check;
    check=countSelNum(checkboxID);
	return check;
}

/*
用途：改变复选框的全选状态
输入：checkboxID：复选框的名称或者ID , 
            status : true 全选中
			         false 全未选中
返回：	
*/	
function checkBoxSelectAll( checkboxID,status )	{
	if( document.all(checkboxID) == null)
		return;

	if( document.all(checkboxID).length > 0 ){ 
		for(  i=0; i<document.all(checkboxID).length; i++ )	{
			document.all(checkboxID).item( i ).checked = status;
		}
	} else {
		document.all(checkboxID).checked = status;
	}
}



/********************************************************************************************************
* added by Chen Chun 
* checkBoxSelectAll,checkBoxSelectCount 两个函数的简洁版本 checkAll,countSelNum
*********************************************************************************************************/

//改变复选框的全选状态
function checkAll(target)
{
	var cks = document.getElementsByName(target);
	var ck = window.event.srcElement;

	if(ck.checked==true)
	{
		for(var i=0;i<cks.length;i++)
		{
			if(cks[i].checked == false) cks[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<cks.length;i++)
		{
			if(cks[i].checked == true) cks[i].checked = false;
		}
	}
}
//被选中复选框个数
function countSelNum(target)
{
	var cks = document.getElementsByName(target);
	var sel = 0;
	for(var i=0;i<cks.length;i++)
	{
		if(cks[i].checked == true) sel++;
	}
	return sel;
}

//更新分页条件
function updatePageCond(fm)
{
	if(typeof(parent.searchform.elements['PageCond/begin'])!='undefined'&&typeof(fm.elements['PageCond/begin'])!='undefined')
	{
		parent.searchform.elements['PageCond/begin'].value=fm.elements['PageCond/begin'].value;
		//alert("aa");
	}
	
	if(typeof(parent.searchform.elements['PageCond/count'])!='undefined'&&typeof(fm.elements['PageCond/count'])!='undefined')
	{
		parent.searchform.elements['PageCond/count'].value=fm.elements['PageCond/count'].value;
	}
	
	if(typeof(parent.searchform.elements['PageCond/length'])!='undefined'&&typeof(fm.elements['PageCond/length'])!='undefined')
	{
		parent.searchform.elements['PageCond/length'].value=fm.elements['PageCond/length'].value;
	}
	
}

//添加记录的提交函数
function prepareInsert(form, action, subtarget){
	var frm = form;
	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();
}

//修改记录的提交函数
function prepareUpdateRow(form, action, target, subtarget){
	var frm = form;
	if(checkBoxSelectCount(target) < 1){
	alert("必须选择一行！");
	return ;
	}else if(checkBoxSelectCount(target) > 1){
	alert("只能选择一行, 不允许多选！");
	return ;
	}
	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();    
}
//传单一主键去修改
function prepareUpdateRowByPks(form, action, target, subtarget){
	var frm = form;
	if(checkBoxSelectCount(target) < 1){
	alert("必须选择一行！");
	return ;
	}else if(checkBoxSelectCount(target) > 1){
	alert("只能选择一行, 不允许多选！");
	return ;
	}
	//传主键
	var pks=new Array();
	pks=splitCheckBox(frm.pk,'&');
	frm.elements["PK"].value=pks[0];
	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();
}
//删除记录的提交函数
function deleteRows(form, action, target, subtarget){
	var frm = form;
	if(checkBoxSelectCount(target)<1){
	alert("至少必须选择一行！");
	return ;
	}

	if(confirm("是否确定删除指定记录？")==false){
	return ;
	}      

	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();
}
//传主键删除
function deleteRowsByPks(form, action, target, subtarget){
	var frm = form;
	if(checkBoxSelectCount(target)<1){
	alert("至少必须选择一行！");
	return ;
	}
	//传主键
	var pks=new Array();
	pks=splitCheckBox(frm.pk,'&');
	frm.elements["PKS"].value=pks[0];
	if(confirm("是否确定删除指定记录？")==false){
		return ;
	}
	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();
}
//审核记录的提交函数
function checkRows(form, action, target, subtarget){
	var frm = form;
	if(checkBoxSelectCount(target)<1){
	alert("至少必须选择一行！");
	return ;
	}

	frm.action = action;
	if (subtarget != null) frm.target=subtarget;
	frm.submit();
}
//分隔checkbox含有的多个值
function splitCheckBox(chk,swd)
{
		var chkvalue=new Array();
		var spchkvalue=new Array();
		var rearr= new Array();
		var onevalue;
		var idx=0;
		var numb="";
		var eyear="";
		if(typeof(chk)=="undefined") return rearr;
		if(typeof(chk.length)=="undefined")
		{
			onevalue=chk.value;
			rearr=onevalue.split(swd);
		}
		else
		{
			for(i=0;i<chk.length;i++)
			{
				if(chk[i].checked==true)
				{
					chkvalue[idx]=chk[i].value;
					idx=idx+1;
				}
			}
		
			for(i=0;i<idx;i++)
			{
				spchkvalue=chkvalue[i].split(swd);
				for(j=0;j<spchkvalue.length;j++)
				{
					//第一行需要初始化
					if(i==0)
					{
						rearr[j]="";
						rearr[j]=rearr[j]+spchkvalue[j];
					}
					else
					{
						rearr[j]=rearr[j]+","+spchkvalue[j];
					}
				}
			}
		}
		return rearr;
}
//页面返回用
function back_onclick(url)
{
	//location.href=url;
	history.back(-1);
}

function backcond_onclick(fm,url)
{
	fm.action=url;
	fm.submit();
}
//右键菜单（code：机构编号，name：机构名称，seni_id：高管编号，seni_name：高管姓名，busi_code:银行业务编号）
	var code='';
	var name='';
	var seni_id="";
	var seni_name="";
	var busi_code="";
	//对审计机构
	function showMenu(id,na,framehight,hight){
		
		event.returnValue=false;
　　　 	//event.cancelBubble=true;
		code=id;
		name=na;
		
		document.getElementById("menu").style.display="none";
		document.getElementById("menu").style.left='';
		document.getElementById("menu").style.right='';
		document.getElementById("menu").style.top='';
		document.getElementById("menu").style.bottom='';
		
				
		//alert('x='+window.event.x+','+'y='+window.event.y);
		
		if(hight>100){
			document.getElementById("menu").style.left=window.event.x;
			
			if((framehight-window.event.y)>hight){
				document.getElementById("menu").style.top=window.event.y;
				
			}
			else{
				document.getElementById("menu").style.top=window.event.y%(framehight-hight);
			}
		}
		else{
			document.getElementById("menu").style.left=window.event.x;
			document.getElementById("menu").style.top=window.event.y;
		}
		//alert('right='+document.getElementById("menu").style.right+','+'bottom='+document.getElementById("menu").style.bottom+','+'left='+document.getElementById("menu").style.left+','+'top='+document.getElementById("menu").style.top);
		document.getElementById("menu").style.position="absolute";
		
		document.getElementById("menu").style.display="";
		return false;
	}
	function menuAction(frm,act,tag,para1,para2){
		document.getElementById("menu").style.display="none";
		frm.action=act+'?'+para1+'='+code+'&'+para2+'='+name;
		frm.target=tag;
		//alert(frm.action);
		frm.submit();
	}
	
	//对高管
	function showMenuSeni(org_id,org_na,sname,framehight,hight){
		
		event.returnValue=false;
　　　 	//event.cancelBubble=true;
		code=org_id;
		name=org_na;
		seni_name=sname;
		
		//alert("code="+code+",name="+name+",seni_name="+seni_name);
		
		document.getElementById("menu").style.display="none";
		document.getElementById("menu").style.left='';
		document.getElementById("menu").style.right='';
		document.getElementById("menu").style.top='';
		document.getElementById("menu").style.bottom='';
				
		//alert('x='+window.event.x+','+'y='+window.event.y);
		
		if(hight>100){
			document.getElementById("menu").style.left=window.event.x;
			
			if((framehight-window.event.y)>hight){
				document.getElementById("menu").style.top=window.event.y;
				
			}
			else{
				document.getElementById("menu").style.top=window.event.y%(framehight-hight);
			}
		}
		else{
			document.getElementById("menu").style.left=window.event.x;
			document.getElementById("menu").style.top=window.event.y;
		}
		//alert('right='+document.getElementById("menu").style.right+','+'bottom='+document.getElementById("menu").style.bottom+','+'left='+document.getElementById("menu").style.left+','+'top='+document.getElementById("menu").style.top);
		document.getElementById("menu").style.position="absolute";
		
		document.getElementById("menu").style.display="";
		return false;
	}
	function menuActionSeni(frm,act,tag,para1,para2,para3){
		document.getElementById("menu").style.display="none";
		frm.action=act+'?'+para1+'='+code+'&'+para2+'='+name+'&'+para3+'='+seni_name;
		frm.target=tag;
		//alert(frm.action);
		frm.submit();
	}
	//对银行业务
	function showMenuBank(bank_code,framehight,hight){
		
		event.returnValue=false;
　　　 	//event.cancelBubble=true;
		busi_code=bank_code;
		
		//alert("busi_code="+busi_code);
		
		document.getElementById("menu").style.display="none";
		document.getElementById("menu").style.left='';
		document.getElementById("menu").style.right='';
		document.getElementById("menu").style.top='';
		document.getElementById("menu").style.bottom='';
				
		//alert('x='+window.event.x+','+'y='+window.event.y);
		
		if(hight>100){
			document.getElementById("menu").style.left=window.event.x;
			
			if((framehight-window.event.y)>hight){
				document.getElementById("menu").style.top=window.event.y;
				
			}
			else{
				document.getElementById("menu").style.top=window.event.y%(framehight-hight);
			}
		}
		else{
			document.getElementById("menu").style.left=window.event.x;
			document.getElementById("menu").style.top=window.event.y;
		}
		//alert('right='+document.getElementById("menu").style.right+','+'bottom='+document.getElementById("menu").style.bottom+','+'left='+document.getElementById("menu").style.left+','+'top='+document.getElementById("menu").style.top);
		document.getElementById("menu").style.position="absolute";
		
		document.getElementById("menu").style.display="";
		return false;
	}
	function menuActionBank(frm,act,tag,para1){
		document.getElementById("menu").style.display="none";
		frm.action=act+'?'+para1+'='+busi_code;
		frm.target=tag;
		//alert(frm.action);
		frm.submit();
	}
	