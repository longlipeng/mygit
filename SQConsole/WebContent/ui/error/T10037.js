Ext.onReady(function() { 
	var queryForm = new Ext.form.FormPanel({
		title:'上传差错文件',
		frame: true,
		border: true,
		width: 380,
		autoHeight: true,
		iconCls: 'T80106',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
		 
		items: [{/*
			xtype: 'dynamicCombo',
			fieldLabel: '机构编号',
			methodName: 'getAgenID',
			id:'idagenid',
			hiddenName: 'agenid',
			editable: true,
			width: 200
		*/},{/*
			xtype: 'datefield',
			name: 'date',
			id: 'date',
			fieldLabel: '原交易日期',
			width: 200 
		*/},{
			xtype: 'panel',
			html: '<br/><br/>'
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '上传差错文件',
			iconCls: 'upload',
			id: 'upload',
			handler: function(bt) {
				// 文件上传窗口
				var dialog = new UploadDialog({
				//uploadUrl : 'T10037Action_uploaderr.asp',
					uploadUrl : 'T10037Action.asp?method=upload',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB', 
					fileTypes : '',
					fileTypesDescription : '文件',
					title: '上传信息',
					scope : this,
					animateTarget: 'upload',
//					onEsc: function() {
//						this.hide();
//					},
					postParams: {
						txnId: '80106',
						subTxnId: '01'
					}
				});
				dialog.show();
			}
		} 
		]
	});
});