Ext.onReady(function() { 
	var queryForm = new Ext.form.FormPanel({
		title:'代理商成本费率导入',
		frame: true,
		border: true,
		width: 380,
		autoHeight: true,
		iconCls: 'T11018',
		waitMsgTarget: true,
		renderTo: 'reportPanel',
//		items: [{
//			xtype: 'panel',
//			html: '<br/><br/>'
//		}],
		buttonAlign: 'center',
		buttons: [{
			text: '代理商成本费率导入',
			iconCls: 'upload',
			id: 'upload',
			handler: function(bt) {
				// 文件上传窗口
				var dialog = new UploadDialog({
					uploadUrl : 'T11524Action.asp?method=upload',
					filePostName : 'files',
					flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
					fileSize : '10 MB', 
					fileTypes : '*.CSV;*.csv',
					fileTypesDescription : '文件',
					title: '上传信息',
					scope : this,
					animateTarget: 'upload',
					onEsc: function() {
						this.hide();
					},
					postParams: {
						AOwner : owner,
						txnId: '11524',
						subTxnId: '01'
					}
				});
				dialog.show();
			}
		} 
		]
	});
});