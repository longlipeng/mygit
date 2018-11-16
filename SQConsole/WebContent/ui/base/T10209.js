Ext.onReady(function() {	
	// 文件上传窗口
//	var dialog = new UploadDialog({
//		uploadUrl : 'T10209Action.asp?method=upload',
//		filePostName : 'files',
//		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
//		fileSize : '10 MB',
//		fileTypes : '*.txt;*.TXT',
//		fileTypesDescription : '文本文件(*.txt;*.TXT)',
//		title: '节假日信息文件上传',
//		scope : this,
//		animateTarget: 'upload',
//		onEsc: function() {
//			this.hide();
//		},
//		exterMethod: function() {
//		},
//		postParams: {
//			txnId: '10205',
//			subTxnId: '04'
//		}
//	});
		
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=holidays'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'id',mapping: 'id'},
			{name: 'holidayStart',mapping: 'holidayStart'},
			{name: 'holidayEnd',mapping: 'holidayEnd'},
			{name: 'name',mapping: 'name'},
			{name: 'unionFlag',mapping: 'unionFlag'},
			{name: 'createOprId',mapping: 'createOprId'},
			{name: 'createTime',mapping: 'createTime'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTime',mapping: 'updTime'},
			{name: 'saState',mapping: 'saState'}
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		{header: '节假日开始日期',dataIndex: 'holidayStart',width: 100,
			editor:new Ext.grid.GridEditor(new Ext.form.DateField({format:"Ymd",allowBlank:false})), 
			renderer:renderer2
		},
		{header: '节假日结束日期',dataIndex: 'holidayEnd',width: 100,
			editor:new Ext.grid.GridEditor(new Ext.form.DateField({format:"Ymd",allowBlank:false})), 
			renderer:renderer2},
		{header: '节假日描述',dataIndex: 'name',width: 150
			,editor : new Ext.form.TextField( {
				maxLength : 50,
				allowBlank : false,
				vtype : 'isOverMax'
			})
		},
		{header: '是否清算',dataIndex: 'unionFlag',width: 150
			,editor : new Ext.form.ComboBox( {
            hiddenName:'unionFlag',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','否'],['1','是']],
				reader: new Ext.data.ArrayReader()
			})
		
			}),
			renderer:unionFlagVal
		}/*,
		{header: '修改人',dataIndex: 'createOprId',width: 100},    		
    	{header: '修改时间',dataIndex: 'createTime',width: 150,renderer: formatTs},
    	{header: '审核人',dataIndex: 'updOprId',width: 100},
    	{header: '审核时间',dataIndex: 'updTime',width: 150,renderer: formatTs},
    	{header: '审核状态',dataIndex: 'saState',width: 100,renderer: riskInfoState}*/
    ]);
	
	function unionFlagVal(val){
		if(val=='0'){
			return '否';
		}else if(val=='1'){
			return '是';
		}else{
			return val;
		}
	}
	
	function renderer2(value){ 
		if(value instanceof Date){ 
			return new Date(value).format("Ymd"); 
		}else{ 
			return value; 
		} 
	} 
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条节假日信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10209Action.asp?method=delete',
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
							params: { 
								id: rec.get('id'),								
								txnId: '10209',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}};
	
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
//				showProcessMsg('正在保存节假日信息，请稍后......');
				//存放要修改的节假日信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						id : record.get('id'),
						holidayStart : record.get('holidayStart'),
						holidayEnd : record.get('holidayEnd'),
						name: record.get('name'),
						unionFlag:record.get('unionFlag')
					};
					var start = record.get('holidayStart');
					var end = record.get('holidayEnd');
					if(start.length != 8){
						start = start.format('Ymd');
					}
					if(end.length != 8){
						end = end.format('Ymd');
					}
					if(start > end){
						 showAlertMsg("起始日期不能大于截止日期",oprGrid);
               			 return;
					}
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T10209Action.asp?method=update',
					method: 'post',
					params: {
					tblHolidayList: Ext.encode(array),
						txnId: '10209',
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
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            selectedRecord = oprGrid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            termInfoStore1.load({
                params: {
                     storeId: 'getHolidayInf',
                     id: selectedRecord.get('id')
                },
                callback: function(records, options, success){
                    if(success){
                       updTermForm.getForm().loadRecord(records[0]);
                       updTermWin.show();
                    }else{
                       updTermWin.hide();
                    }
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
		
//	var upload = {
//		text: '导入节假日文件',
//		width: 85,
//		id: 'upload',
//		iconCls: 'upload',
//		handler:function() {
//			dialog.show();
//		}
//	};
	//添加节假日表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 380,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '节假日开始日期*',
			xtype: 'datefield',
			id: 'holidayStart',
			name: 'holidayStart',
			width: 120,
			maxLength: 8,
			allowBlank: false,
			blankText: '节假日开始日期不能为空',
			emptyText: '格式：yyyymmdd',
			vtype: 'isOverMax',
			listeners: {
				'select': function() {
					/*var curDate = new Date().format('Ymd');
					if(Ext.getCmp('holidayStart').getValue().format('Ymd') < curDate){
						showAlertMsg("起始日期不能小于当前日期，请重新选择！",paramInfoForm);
						Ext.getCmp('holidayStart').setValue('');
					}*/
					if(Ext.getCmp('holidayEnd').getValue()!='' && 
							(Ext.getCmp('holidayStart').getValue().format('Ymd') > Ext.getCmp('holidayEnd').getValue().format('Ymd'))){
						showAlertMsg("起始日期不能大于截止日期，请重新选择！",paramInfoForm);
						Ext.getCmp('holidayStart').setValue('');
						Ext.getCmp('holidayEnd').setValue('');}
				}
			}
		},{
			fieldLabel: '节假日结束日期*',
			xtype: 'datefield',
			id: 'holidayEnd',
			name: 'holidayEnd',
			width: 120,
			maxLength: 8,
			allowBlank: false,
			blankText: '节假日结束日期不能为空',
			emptyText: '格式：yyyymmdd',
			vtype: 'isOverMax',
			listeners: {
				'select': function() {
					/*var curDate = new Date().format('Ymd');
					if(Ext.getCmp('holidayEnd').getValue().format('Ymd') < curDate){
						showAlertMsg("结束日期不能小于当前日期，请重新选择！",paramInfoForm);
						Ext.getCmp('holidayEnd').setValue('');
					}*/
					if(Ext.getCmp('holidayStart').getValue()!='' && 
							(Ext.getCmp('holidayStart').getValue().format('Ymd') > Ext.getCmp('holidayEnd').getValue().format('Ymd'))){
						showAlertMsg("开始日期不能大于结束日期，请重新选择！",paramInfoForm);
						Ext.getCmp('holidayStart').setValue('');
						Ext.getCmp('holidayEnd').setValue('');}
				}
			}
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id:'unionFlagId',
			hiddenName:'unionFlag',
			allowBlank: false,
			fieldLabel: '是否清算*',
			width: 150,
			value:'0',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','否'],['1','是']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			fieldLabel: '节假日名称*',
			id: 'name',
			name: 'name',
			width: 150,
			maxLength: 50,
			allowBlank: false,
			blankText: '节假日名称不能为空',
			emptyText: '请输入节假日名称',
			vtype: 'isOverMax'
		}]
	});
	
	//添加窗口
	var paramWin = new Ext.Window({
		title: '添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 380,
		autoHeight: true,
		layout: 'fit',
		items: [paramInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(paramInfoForm.getForm().isValid()) {
					paramInfoForm.getForm().submit({
						url: 'T10209Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10209',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				paramInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				paramWin.hide(oprGrid);
			}
		}]
	});
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
		}
	};
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(upMenu);		// [2]
	menuArr.push('-');  //[3]
	menuArr.push(queryCondition);  //[4]
//	menuArr.push('-');  //[5]
//	menuArr.push(upload);  //[6]
	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '节假日信息',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载节假日信息列表......'
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
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getTopToolbar().items.items[3].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		/*'beforeedit':function(e){
			if(e.record.get('saState')=='4'||e.record.get('saState')=='1'){
				e.cancel=true;
			}
		},*/
		//在编辑单元格后保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[0].enable();
		oprGrid.getTopToolbar().items.items[2].enable();
		oprGrid.getTopToolbar().items.items[4].enable();
			/*rec = oprGrid.getSelectionModel().getSelected();
			
			if(rec.get('saState')=='4'){
				oprGrid.getTopToolbar().items.items[2].disable();删除 待审核状态 禁修改删除			}else if(rec.get('saState')=='3'){
				oprGrid.getTopToolbar().items.items[2].disable();修改待审核的状态禁止删除			}else if(rec.get('saState')=='1'){
				oprGrid.getTopToolbar().items.items[2].disable();已删除状态 禁止删除			}else if(rec.get('saState')=='0'){
				oprGrid.getTopToolbar().items.items[2].disable();新增待审核状态也禁止删除			}else{
				oprGrid.getTopToolbar().items.items[2].enable();
			}*/
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日开始日期',
			id:'idholidayStart',
			name: 'holidayStart',
			emptyText: '格式：yyyymmdd',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('idholidayEnd').getValue()!='' && 
							(Ext.getCmp('idholidayStart').getValue().format('Ymd') > Ext.getCmp('idholidayEnd').getValue().format('Ymd'))){
						showAlertMsg("起始日期不能大于截止日期，请重新选择！",queryForm);
						Ext.getCmp('idholidayStart').setValue('');
						Ext.getCmp('idholidayEnd').setValue('');}
				}
			}
		},{
			xtype: 'datefield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日结束日期',
			emptyText:'格式：yyyymmdd',
			id:'idholidayEnd',
			name: 'holidayEnd',
			width: 120,
			listeners: {
				'select': function() {
					if(Ext.getCmp('idholidayStart').getValue()!='' && 
							(Ext.getCmp('idholidayStart').getValue().format('Ymd') > Ext.getCmp('idholidayEnd').getValue().format('Ymd'))){
						showAlertMsg("开始日期不能大于结束日期，请重新选择！",queryForm);
						Ext.getCmp('idholidayStart').setValue('');
						Ext.getCmp('idholidayEnd').setValue('');}
				}
			}
		},{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idUnionFlag',
			fieldLabel: '是否清算',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','否'],['1','是']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '节假日名称',
			id:'idname',
			name: 'name',
			width: 150
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			unionFlag: queryForm.getForm().findField('idUnionFlag').getValue(),
			holidayStart: queryForm.getForm().findField('idholidayStart').getValue(),
			holidayEnd: queryForm.getForm().findField('idholidayEnd').getValue(),
			name: queryForm.getForm().findField('idname').getValue()
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