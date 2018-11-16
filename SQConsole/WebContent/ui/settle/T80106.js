Ext.onReady(function() { 
	var queryForm = new Ext.form.FormPanel({
		title:'上传对账文件',
		frame: true,
		border: true,
		width: 380,
		autoHeight: true,
		iconCls: 'T80106',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		 
		items: [
//		{
//			xtype: 'dynamicCombo',
//			fieldLabel: '机构编号',
//			methodName: 'getAgenID',
//			id:'idagenid',
//			hiddenName: 'agenid',
//			editable: true,
//			width: 200
//		},
		{
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '对账日期',
			allowBlank: false,
			width: 200 
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id:'idChannel',
			hidden:true,
			hideLabel:true,
			hiddenName: 'channel',
			fieldLabel: '文件来源',
			width: 200,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
			//	data: [['CCB','建行对账文件'],['BOC','中行对账文件']],
				data: [['CCB','建行对账文件']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '上传对账文件',
			iconCls: 'upload',
			id: 'upload',
			handler: function(bt) {
				// 文件上传窗口
//				agenid=Ext.getCmp('idagenid').getValue();
				if (queryForm.getForm().isValid()) {
					var dialog = new UploadDialog({
					uploadUrl : 'T80106Action_upload.asp',
//					uploadUrl : 'T11018Action.asp?method=uploadFile',
						filePostName : 'files',
						flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
						fileSize : '10 MB', 
						fileTypes : '',
						fileTypesDescription : '文件',
						title: '上传信息',
						scope : this,
						animateTarget: 'upload',
						onEsc: function() {
							this.hide();
						},
						postParams: {
//								agenid: agenid,
							date: Ext.getCmp('date').getValue().format('Ymd'),
							channel: Ext.getCmp('idChannel').getValue(),
							txnId: '80106',
							subTxnId: '01'
						}
					});
					dialog.show();
				}
			}
		}]
	});
})