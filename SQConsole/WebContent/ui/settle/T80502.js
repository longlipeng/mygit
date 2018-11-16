Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '先结后算',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80101',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '监控日期*',
			allowBlank: false,
			blankText: '请选择监控日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				window.location.href = Ext.contextPath + '/page/settle/T8050201.jsp?date='+Ext.getCmp('date').getValue().format('Y-m-d');
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})