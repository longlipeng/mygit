Ext.onReady(function() {	
	var queryForm = new Ext.form.FormPanel({
		title: '日终批处理回滚',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'middlediv',
		iconCls: 'T80216',
		waitMsgTarget: true,
		defaults:{
			style: 'padding-left: 5px',
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'datefield',
			name: 'dateSettlmt',
			id: 'dateSettlmt',
			fieldLabel: '回滚日期',
			allowBlank: false,
			blankText: '请选择回滚日期'
		},{
			xtype: 'panel',
			html: '</br></br>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '回滚',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				showConfirm('确定要执行所选日期的批处理回滚吗？', queryForm, function(bt) {
					// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.requestNeedAuthorise( {
								url : 'T80216Action.asp?method=rollback',
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessAlert(rspObj.msg, queryForm);
										queryForm.getForm().reset();
									} else {
										showErrorMsg(rspObj.msg, queryForm);
									}
								},
								params : {
									dateSettlmt:queryForm.findById('dateSettlmt').getValue().format('Ymd'),
									txnId: '80216',
									subTxnId: '01'
								}
							});
						}
					});
//				queryForm.getForm().submit({
//					url:'T80216Action.asp?method=rollback',
//					waitTitle : '请稍候',
//					waitMsg : '正在进行日终批处理回滚,请稍候...',
//					success : function(form, action) {
//						showSuccessAlert(action.result.msg,queryForm);
//						queryForm.getForm().reset();
//					},
//					failure : function(form,action) {
//						showErrorMsg(action.result.msg,queryForm);	
//					}
//				});
			}
		}]
	});
//	queryForm.getForm().findField('date').setValue(YESTERDAY);

})