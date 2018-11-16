Ext.onReady(function() {
	
	var routChannel = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root:'data'
	});
	SelectOptionsDWR.getComboData('ROUTINGCHANNEL',function(ret){
		routChannel.loadData(Ext.decode(ret));
	});
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户划款汇总表',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80200',
		waitMsgTarget: true,
		items: [{
			xtype: 'datefield',
			id: 'instDateId',
			name: 'instDateId',
			labelStyle: 'padding-left: 5px',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '结算日期*',
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
			fieldLabel: '批次号*',
			allowBlank: false,
			id: 'idbatchNum',
			hiddenName: 'batchNum',
			width: 163
		}/*,{
			xtype: 'basecomboselect',
			id: 'iddestId',
			hiddenName:'destId',
			allowBlank: false,
			editable: false,
			fieldLabel: '通道*',
			width: 163,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['*','所有通道'],['1702','翰鑫通道'],['1708','快钱通道']],
				reader: new Ext.data.ArrayReader()
			})
		}*/,{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idAccFlag',
			hiddenName:'accFlag',
			allowBlank: false,
			editable: false,
			fieldLabel: '账户状态*',
			width: 163,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','对私账户'],['2','对公账户']],
				reader: new Ext.data.ArrayReader()
			})
		},{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '所有通道',
			inputValue: '*',
			id: 'destIdall',
			name: 'destIdall'
    	},{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '上汽通道',
			inputValue: '1708',
			id: 'destIdkuaiqian',
			name: 'destId'
    	},{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			inputValue: '1719',
			fieldLabel: '单用途卡通道',
			id: 'destIdhanxin',
			name: 'destId'
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
				if(!Ext.getCmp('destIdall').getValue()
				 &&!Ext.getCmp('destIdkuaiqian').getValue()
//				 &&!Ext.getCmp('destIdhanxin').getValue()
				 ){
					showErrorMsg("请选择通道",queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T80222Action.asp',
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