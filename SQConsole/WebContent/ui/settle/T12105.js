/*Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户反洗钱黑名单结果文件下载',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80206',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		buttonAlign: 'center',
		buttons: [{
			text: '下载反洗钱黑名单结果',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T40202Action.asp?method=downloadBlackListFile',
					waitMsg: '正在下载反洗钱黑名单，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+ action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		}]
	});
})*/
/*Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '黑名单报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T40202',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '报表文件名称',
			methodName: 'getReport',
			parentP:"('12')",
			id:'idreportName',
			hiddenName: 'reportName'
		},{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '清算日期*',
			blankText: '请选择清算日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('idreportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var date = queryForm.getForm().findField('date').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表日期。',queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T40202Action.asp?method=downloadBlackListFile',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},

			{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//queryForm.getForm().findField('brhId').setValue(BRHID);
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})*/

Ext.onReady(function() {
	var queryForm = new Ext.form.FormPanel({
		title: '黑名单报表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T40202',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idreportName',
			hiddenName: 'reportName',
			fieldLabel: '报表文件名称',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','商户反洗钱黑名单结果文件'],],
				reader: new Ext.data.ArrayReader()
			})
	
		},{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '日期*',
			blankText: '请选择日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('idreportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var date = queryForm.getForm().findField('date').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表日期。',queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T80206Action_downloadBlackListFile.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg,queryForm);
					}
				});
			}
		},
			{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})