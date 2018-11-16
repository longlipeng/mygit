Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '清算报表',
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
		items: [{
			xtype: 'dynamicCombo',
			fieldLabel: '报表文件名称',
			methodName: 'getReportSettle',
			parentP:"('1','11','2','21','3','4','5','7')",
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
					url: 'T80206Action_downloadreportDYF.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+ action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},
//			{
//			text: '打包下载',
//			iconCls: 'icon_download',
//			handler: function() {
//				if(!queryForm.getForm().isValid()) {
//					return;
//				}
//				queryForm.getForm().submit({
//					url: 'T80206Action_downloadAll.asp',
//					waitMsg: '正在下载报表，请稍等......',
//					success: function(form,action) {
//					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
//													action.result.msg;
//					},
//					failure: function(form,action) {
//						showAlertMsg(action.result.msg,queryForm);
//					}
//				});
//			}
//		},
			{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//queryForm.getForm().findField('brhId').setValue(BRHID);
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})