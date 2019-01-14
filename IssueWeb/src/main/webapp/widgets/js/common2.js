
// 添加标题的点击事件
Ext.EventManager.on(
    window,
    'load',
    function() {
        var divs = Ext.select('div[id$=Title]');
        divs.each(
            function(div) {
                var divId = div.dom.id.slice(0, -5) + 'Table';
                div.select('.TableTitleFront').setStyle('cursor', 'pointer');
                div.select('.TableTitleFront').first().dom.onclick = null;
                div.select('.TableTitleFront').on(
                    'click',
                    function(event) {
                        showOrHideDiv(divId);
                    }
                );
            }
        );

        // 去掉div上的padding
        if (Ext.isIE6) {
            var divs = Ext.select('div[id$=Title], div[id$=Table]');
            divs.setStyle('width', '100%');
        }
    }
);

// 显示或隐藏Div
function showOrHideDiv(TableDivId) {
    if (Ext.fly(TableDivId).isDisplayed()) {
        undisplay(TableDivId);
    } else {
        display(TableDivId);
    }
}

// 得到选中复选框个数
function checkedCount(checkboxName) {
    var checkboxs = document.getElementsByName(checkboxName);
    var count = 0;
    for (var i = 0; i < checkboxs.length; i++) {
        if (checkboxs[i].checked) {
            count++;
        }
    }
    return count;
}

// 只能同时编辑一条数据
function editSubmit(checkboxName) {
    var count = checkedCount(checkboxName);
    if (count < 1) {
        alert('请选择一条记录！');
        return false;
    } else if (count > 1) {
        alert('只能编辑一条记录！');
        return false;
    }
    return true;
}

// 可以删除多条数据
function deleteSubmit(checkboxName) {
    var count = checkedCount(checkboxName);
    if (count < 1) {
        alert('请至少选择一条记录！');
        return false;
    }
    return true;
}

// 提交form
function submitForm(formName, action, submitType, checkboxName) {
    var form = document.forms[formName];
    Ext.get(form).select('[name=ec_eti]').each(function () {
        this.dom.value='';
    });
    if (form == null) {
        alert('提交表单不存在！');
        return;
    }
    form.action = action;
    if (submitType == null) {
        maskDocAll();
        form.submit();
        return;
    }
    switch (submitType) {
        case 'add':
            maskDocAll();
            window.location = action;
            
            break;
        case 'edit':
            if (editSubmit(checkboxName)) {
                maskDocAll();
                form.submit();
            }
            break;
        case 'valid':
        	 if(deleteSubmit(checkboxName)){
             	Ext.MessageBox.confirm("确 认", "确定要启用吗?",function(btn) {
 								if (btn == "yes") {
 									form.submit();
 								}
 							});
             }
        	break;
        case 'delete':
            if(deleteSubmit(checkboxName)){
            	Ext.MessageBox.confirm("确 认", "确定要删除或注销吗?",function(btn) {
								if (btn == "yes") {
									form.submit();
								}
							});
            }
            break;
        default:
            maskDocAll();
            form.submit();
    }
}

// 将字符传串转化为JSON对象
function toJSONObject(JSONStr) {
    if (JSONStr != null && JSONStr != '') {
        return Ext.util.JSON.decode(JSONStr);
    } else {
        return null;
    }
}

//用于报表中的日期输入控制，获取当前日期的函数初始化起始日期
function getCurrentDate()
 {
  var d,s;
  d = new Date();
  s = d.getFullYear() + "-";             //取年份
  s = s + (appendZero(d.getMonth()+1)) + "-";//取月份
  s += appendZero(d.getDate()) + "";         //取日期
  
  document.getElementById("startDate").value=s; 
  document.getElementById("endDate").value=s; 
 
 } 

//用于报表中的日期输入控制，获取当前日期的函数初始化起始日期
function getCurrentDate2()
 {
  var d,s;
  d = new Date();
  s = d.getFullYear() + "-";             //取年份
  s = s + (appendZero(d.getMonth()+1)) + "-";//取月份
  s += appendZero(d.getDate()) + "";         //取日期
  
  document.getElementById("expireBeginDate").value=s;  
 } 

function getCurrentDate3()
{
 var d,s;
 d = new Date();
 s = d.getFullYear() + "-";             //取年份
 s = s + (appendZero(d.getMonth()+1)) + "-";//取月份
 s += appendZero(d.getDate()) + "";         //取日期
 
 document.getElementById("inputDate").value=s;  
} 

function getCurrentDate4()
{
 var d,s;
 d = new Date();
 s = d.getFullYear() + "-";             //取年份
 s = s + (appendZero(d.getMonth()+1));//取月份

 
 document.getElementById("inputDate").value=s;  
} 


function   appendZero(s){return   ("00"+   s).substr((s+"").length);}


function customerOperation(form){
 form.submit();
}