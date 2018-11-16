Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '银行出款汇总表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80200',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idBank',
			hiddenName:'bank',
			allowBlank: false,
			editable: false,
			fieldLabel:'出款银行*',
			width: 163,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
//				data: [['1','中国农业银行'],['2','中国 民生银行'],['3','中国招商银行']],
				data: [['3','中国招商银行']],
				reader: new Ext.data.ArrayReader()
			}),
			listeners:{
				'select':function(){
					if(Ext.getCmp('idBank').getValue()=='2'){
						Ext.getCmp('singleAmt').disable();
					}
				}
			}
		},{
			xtype: 'datefield',
			id: 'instDateId',
			labelStyle: 'padding-left: 5px',
			name: 'instDateId',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel:'结算日期*',
			width:163,
			allowBlank: false,
			editable: false,
			listeners: {
				'select': function() {
			        Ext.getCmp('idbatchNum').reset();
					Ext.getCmp('idbatchNum').parentP=typeof(Ext.getCmp('instDateId').getValue()) == 'string' ? '' : Ext.getCmp('instDateId').getValue().dateFormat('Ymd');
					Ext.getCmp('idbatchNum').getStore().reload();
				}
			}
		},{
			xtype: 'dynamicCombo',
			methodName: 'getBatchNum',
			labelStyle: 'padding-left: 5px',
			fieldLabel:'批次号*',
			allowBlank: false,
			id: 'idbatchNum',
			hiddenName: 'batchNum',
			width: 163
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idAccFlag',
			hiddenName:'accFlag',
			allowBlank: false,
			editable: false,
			fieldLabel:'账户状态*',
			width: 163,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','对私账户'],['2','对公账户']],
				reader: new Ext.data.ArrayReader()
			})
		},{

        	xtype: 'textfield',
        	labelStyle: 'padding-left: 5px',
			fieldLabel:'单笔最大金额',
			name: 'singleAmt',
			id: 'singleAmt',
			vtype: 'isMoney',
			maxLength:12,
			allowBlank:true,
			width:163
    	
		},{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel:'所有通道*',
			inputValue: '*',
			id: 'destIdall',
			allowBlank:false,
			name: 'destIdall'
    	},/*{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '上汽通道',
			inputValue: '1708',
			id: 'destIdkuaiqian',
			name: 'destId'
    	},*/{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idFormat',
			hiddenName:'idFormats',
			allowBlank: false,
			editable: false,
			fieldLabel:'格式*',
			width: 163,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','.txt'],['2','.xls']],
				reader: new Ext.data.ArrayReader()
			})}/*,{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			inputValue: '1702',
			fieldLabel: '翰鑫通道',
			id: 'destIdhanxin',
			name: 'destId'
    	}*/
	],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				
				/*if(!Ext.getCmp('destIdall').getValue()
						 &&!Ext.getCmp('destIdkuaiqian').getValue()
						 &&!Ext.getCmp('destIdhanxin').getValue()){
							showErrorMsg("请选择通道",queryForm);
							return;
				}*/
				
				queryForm.getForm().submit({
					url: 'T80223Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					params: {
					instDate:typeof(Ext.getCmp('instDateId').getValue()) == 'string' ? '' : Ext.getCmp('instDateId').getValue().dateFormat('Ymd')					
						},
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