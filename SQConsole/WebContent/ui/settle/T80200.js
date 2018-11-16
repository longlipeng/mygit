Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户对账明细表',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80200',
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
			hiddenName: 'mchtNo',
			allowBlank: true,
			editable: true,
			width: 350
		},{
			xtype: 'datefield',
			width:163,
			id: 'stlmStartDate',
			name: 'stlmStartDate',
			vtype: 'daterange',
			endDateField: 'stlmEndDate',
			fieldLabel: '清算开始日期',
			editable: false
		},{
			xtype: 'datefield',
			width:163,
			id: 'stlmEndDate',
			name: 'stlmEndDate',
			vtype: 'daterange',
			startDateField: 'stlmStartDate',
			fieldLabel: '清算结束日期',
			editable: false
		}
//		,reportType
	],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80200Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
//	queryForm.getForm().findField('brhId').setValue(BRHID);
})