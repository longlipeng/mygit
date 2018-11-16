Ext.onReady(function() {	

	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T10205Action.asp?method=upload',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.csv;*.CSV;*.txt;*.TXT;*.*',
		fileTypesDescription : '文件(*.csv;*.CSV;*.txt;*.TXT;*.*)',
		title: '银联卡表文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
		},
		postParams: {
			txnId: '10205',
			subTxnId: '04'
		}
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankBinInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ind',mapping: 'ind'},	
			{name: 'insIdCd',mapping: 'insIdCd'},	
			{name: 'cardDis',mapping: 'cardDis'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'acc1Offset',mapping: 'acc1Offset'},
			{name: 'acc1Len',mapping: 'acc1Len'},
			{name: 'acc1Tnum',mapping: 'acc1Tnum'},
			{name: 'binOffSet',mapping: 'binOffSet'},
			{name: 'binLen',mapping: 'binLen'},
			{name: 'binStaNo',mapping: 'binStaNo'},	
			{name: 'binEndNo',mapping: 'binEndNo'},	
			{name: 'binTnum',mapping: 'binTnum'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
	                                            sm,
			{header: '索引',dataIndex: 'ind',width: 60},
    		{header: '发卡行编号',dataIndex: 'insIdCd',width: 110},
    		{header: '发卡行名称',dataIndex: 'cardDis',width: 140,id:'cardDis'},
    		{header: '交易卡种',
	            dataIndex: 'cardTp',
	            width: 120,
	            editor: {
						xtype: 'basecomboselect',
				        store: cardStore,
						id: 'idCardTp',
						hiddenName: 'cardTp',
						width: 160
			    },
			    renderer:function(data){
			    	if(null == data) return '';
			    	var record = cardStore.getById(data);
			    	if(null != record){
			    		return record.data.displayField;
			    	}else{
			    		return '';
			    	}
			    }
			},
    		{header: '卡号偏移量',dataIndex: 'acc1Offset',width: 70,
          		 editor: new Ext.form.TextField({
          		 	maxLength: 1,
          			allowBlank: false,
          			vtype: 'isOverMax',
          			regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字',
					maskRe: /^[0-9]+$/
          		 })},
    		{header: '卡号长度',dataIndex: 'acc1Len',width: 70,
       		 editor: new Ext.form.TextField({
     		 	maxLength: 2,
     			allowBlank: false,
     			vtype: 'isOverMax',
     			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
     		 })},
    		{header: '卡号磁道号',dataIndex: 'acc1Tnum',width: 70,
          		 editor: new Ext.form.TextField({
          		 	maxLength: 1,
          			allowBlank: false,
          			vtype: 'isOverMax',
          			regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字',
					maskRe: /^[0-9]+$/
          		 })},    		
        	{header: '卡BIN偏移量',dataIndex: 'binOffSet',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 2,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN长度',dataIndex: 'binLen',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 2,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN起值',dataIndex: 'binStaNo',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 30,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN终值',dataIndex: 'binEndNo',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 30,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN磁道号',dataIndex: 'binTnum',width: 80,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 1,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })}
    	]);
	
	
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var modifiedRecords = oprGrid.getSelectionModel().getSelections();
					if(modifiedRecords.length == 0) {
						return;
					}
					var array = new Array();
					for(var index = 0; index < modifiedRecords.length; index++) {
						var record = modifiedRecords[index];
						var data = {
								ind: record.get('ind')
						};
						
						array.push(data);
					}
					showConfirm('确定要删除选中卡表信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T10205Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								
								params:{

									infList: Ext.encode(array),
										txnId: '10205',
										subTxnId: '02'
									
								}
							});
						}
					});
				}
			}
		};
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
				showProcessMsg('正在保存卡表信息，请稍后......');
				//存放要修改的机构信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						ind : record.get('ind'),
						cardTp: record.get('cardTp'),
						acc1Offset : record.get('acc1Offset'),
						acc1Len : record.get('acc1Len'),
						acc1Tnum: record.get('acc1Tnum'),
						binOffSet: record.get('binOffSet'),
						binLen: record.get('binLen'),
						binStaNo: record.get('binStaNo'),
						binEndNo: record.get('binEndNo'),
						binTnum: record.get('binTnum')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T10205Action.asp?method=update',
					method: 'post',
					params: {
					tblBankBinInfList: Ext.encode(array),
						txnId: '10205',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
						} else {
							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
						oprGrid.getTopToolbar().items.items[4].disable();
						oprGrid.getStore().reload();
						hideProcessMsg();
					}
				});
			}
		};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
		
	var upload = {
		text: '导入银联卡表文件',
		width: 85,
		id: 'upload',
		iconCls: 'upload',
		handler:function() {
			dialog.show();
		}
	};
	var Rdownload = {
			text: '下载银联卡表文件',
			width: 85,
			id: 'Rdownload',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后....",oprGrid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T10205_DwnAction.asp',
					params: {//查询的三个参数
						insIdCd:queryForm.getForm().findField('idinsIdCd').getValue(),
						CardTp:queryForm.getForm().findField('cardType').getValue(),
						binStaNo: queryForm.getForm().findField('idbinStaNo').getValue()
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,oprGrid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		};
	
	var addForm = new Ext.form.FormPanel( {// 添加表单
		frame : true,
		autoHeight : true,
		width : 600,
		labelWidth : 90,
		defaultType : 'textfield',
		waitMsgTarget : true,
		items : [{
			fieldLabel : '发卡行编号*',
			emptyText : '请输入发卡行编号',
			allowBlank : false,
			id : 'insIdCd',
			name : 'insIdCd',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '发卡行名称*',
			emptyText : '请输入发卡行名称*',
			allowBlank : false,
			id : 'cardDis',
			name : 'cardDis',
			width : 200,
			maxLength : 8
		},{
			xtype: 'basecomboselect',
			baseParams: 'CARD_TYPE',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡类型*',
			allowBlank: false,
			hiddenName: 'CardTp',
			anchor: '50%'
		},{
			fieldLabel : '卡偏移量*',
			emptyText : '请输入卡偏移量',
			allowBlank : false,
			id : 'acc1Offset',
			name : 'acc1Offset',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡号长度*',
			emptyText : '请输入卡号长度',
			allowBlank : false,
			id : 'acc1Len',
			name : 'acc1Len',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡号磁道号*',
			emptyText : '请输入卡号磁道号',
			allowBlank : false,
			id : 'acc1Tnum',
			name : 'acc1Tnum',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡bin偏移量*',
			emptyText : '请输入卡bin偏移量',
			allowBlank : false,
			id : 'binOffSet',
			name : 'binOffSet',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡bin长度*',
			emptyText : '请输入卡bin长度',
			allowBlank : false,
			id : 'binLen',
			name : 'binLen',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡bin起始值*',
			emptyText : '请输入卡bin起始值*',
			allowBlank : false,
			id : 'binStaNo',
			name : 'binStaNo',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡bin终止值*',
			emptyText : '请输入卡bin终止值',
			allowBlank : false,
			id : 'binEndNo',
			name : 'binEndNo',
			width : 200,
			maxLength : 8
		},{
			fieldLabel : '卡bin磁道号*',
			emptyText : '请输入卡bin磁道号',
			allowBlank : false,
			id : 'binTnum',
			name : 'binTnum',
			width : 200,
			maxLength : 8
		}]
	});
	
	// 添加窗口
	var addWin = new Ext.Window( {
			title : '信息添加',
			initHidden : true,
			header : true,
			frame : true,
			closable : false,
			modal : true,
			width : 600,
			autoHeight : true,
			layout : 'fit',
			items : [ addForm ],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [	{
					text : '确定',
					handler : function() {
						if (addForm.getForm().isValid()) {
							addForm.getForm().submit({
								url : 'T10205Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									showSuccessMsg(action.result.msg, addForm);
									// 重置表单
									addForm.getForm().reset();
									addWin.hide();
									// 重新加载列表
									oprGrid.getStore().reload();
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg,addForm);
								},
								params : {
									txnId : '10205',
									subTxnId : '01'
								}
							});
						}
					}
					}, {
						text : '重置',
						handler : function() {
							addForm.getForm().reset();
						}
					}, {
						text : '关闭',
						handler : function() {
							addWin.hide(oprGrid);
						}
					} ]
			});
	
	// 添加
	var addMenu = {
		text : '添加',
		width : 85,
		iconCls : 'add',
		handler : function() {
			addWin.show();
			addWin.center();
		}
	};
	var menuArr = new Array();
	menuArr.push(delMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(upMenu);		// [2]	
	menuArr.push('-');  //[3]
	menuArr.push(queryCondition);  //[4]
	menuArr.push('-');  //[5]
	menuArr.push(upload);  //[6]
	menuArr.push('-');  //[7]
	menuArr.push(Rdownload);  //[8]
	menuArr.push('-');  //[9]
	menuArr.push(addMenu);  //[10]
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '银联卡表信息',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: sm,
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		autoExpandColumn: 'cardDis',
		loadMask: {
			msg: '正在加载卡表信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[0].disable();
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [/*{
			xtype: 'basecomboselect',
			baseParams: 'BIN_STA_NO',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡bin',
			allowBlank: true,
			hiddenName: 'binStaNo',
			editable: true,
			anchor: '90%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'INS_ID_CD',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '发卡行',
			allowBlank: true,
			hiddenName: 'insIdCd',
			editable: true,
			anchor: '90%'
			
		}*/{
			fieldLabel : '卡bin',
			methodName:'getbinStaNo',
			xtype: 'dynamicCombo',
			id : 'idbinStaNo',
			hiddenName: 'binStaNo',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},{
			fieldLabel : '发卡行',
			methodName:'getinsIdCd',
			xtype: 'dynamicCombo',
			id : 'idinsIdCd',
			hiddenName: 'insIdCd',
			labelStyle: 'padding-left: 5px',
			anchor: '90%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'CARD_TYPE',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡类型',
			allowBlank: true,
			hiddenName: 'cardType',
			anchor: '90%'
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			insIdCd: queryForm.getForm().findField('idinsIdCd').getValue(),
			cardType: queryForm.getForm().findField('cardType').getValue(),
			binStaNo: queryForm.getForm().findField('idbinStaNo').getValue()
		});
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 450,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
});