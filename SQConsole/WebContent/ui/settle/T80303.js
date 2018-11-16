Ext.onReady(function() {
	var queryForm = new Ext.form.FormPanel({
		title: '机构差错导入结果报表',
		frame: true,
		border: true,
		width: 380,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80303',
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getOrgcode',
			fieldLabel: '机构号*',
			hiddenName: 'instId',
			allowBlank: false,
			editable: false,
			width: 200
		},{
			xtype: 'datefield',
			id:'importDate',
			name: 'importDate',
			fieldLabel: '导入日期*',
			blankText: '请选择导入日期',
			allowBlank: false,
			width:103
		}
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
					url: 'T80303Action.asp',
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

})