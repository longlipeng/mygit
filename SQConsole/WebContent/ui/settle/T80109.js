Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '对账文件下载',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80109',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [/*{
			xtype: 'dynamicCombo',
			fieldLabel: '通道名称',
			methodName: 'getChannelReportSettle',
			parentP:"('00000002','00000005','00000019')",
			id:'idreportName',
			hiddenName: 'reportName'
		},*/{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '对账日期*',
			blankText: '请选择对账日期',
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
				/*var reportName = queryForm.getForm().findField('idreportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}*/
				var date = queryForm.getForm().findField('date').getValue();
				if(date == ''){
					showErrorMsg('请选择报表日期。',queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T80109Action_report.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+ action.result.msg;
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg,queryForm);
					}
//					url: 'T80206Action_downloadchannelreportExecl.asp',
//					url: 'T80109Action.asp',
//					waitMsg: '正在下载报表，请稍等......',
//					success: function(form,action) {
//					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
//													action.result.msg;
//					},
//					failure: function(form,action) {
//						showAlertMsg(action.result.msg,queryForm);
//					}
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
//	queryForm.getForm().findField('date').setValue(YESTERDAY);
})