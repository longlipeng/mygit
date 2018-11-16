Ext.onReady(function() {
	
//	var cityStore = new Ext.data.JsonStore({
//		fields: ['valueField','displayField'],
//		root: 'data'
//	});
//	
	var brhLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	//机构级别
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	
//	//机构级别
//	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
//		cityStore.loadData(Ext.decode(ret));
//	});
	
	//上级营业网点编号
	SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhLvl',mapping: 'brhLvl'},
			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'cupBrhId',mapping: 'cupBrhId'},
			{name: 'resv1',mapping: 'resv1'}
		])
	});
	
	gridStore.load();
	
	function brhLvlRender(brhLvl) {
		switch(brhLvl) {
			case '0': return '总行';
			case '1': return '分行';
			case '2': return '支行';
			case '3':return '网点';
		}
	}
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '编号',dataIndex: 'brhId',sortable: true,width: 50},
		{header: '级别',dataIndex: 'brhLvl',renderer: brhLvlRender,width: 50,
		 editor: new Ext.form.ComboBox({
		 	store: brhLvlStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			lazyRender: true
		 })},
		{header: '上级编号',dataIndex: 'upBrhId',width: 60,
		 editor: new Ext.form.ComboBox({
		 	store: upBrhStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		 })},
		{header: '机构名称',dataIndex: 'brhName',width: 100,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '机构名称不能为空',
			emptyText: '请输入机构名称',
			maxLength: 40,
			maxLengthText: '机构名称最多可以输入40个汉字'
		 })},
		{id:'brhAddr',header: '机构地址',dataIndex: 'brhAddr',width: 108,id: 'brhAddr',
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '机构地址不能为空',
			emptyText: '请输入机构地址',
			maxLength: 40,
			maxLengthText: '机构地址最多可以输入40个汉字'
		 })},
		{header: '联系方式',dataIndex: 'brhTelNo',width: 80,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '机构联系电话不能为空',
			emptyText: '请输入机构联系电话',
			maxLength: 20,
			maxLengthText: '机构联系电话最多可以输入20个数字',
			vtype: 'isNumber',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		{header: '邮政编码',dataIndex: 'postCode',width: 60,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '机构邮政编码不能为空',
			emptyText: '请输入机构邮政编码',
			minLength: 6,
			minLengthText: '机构邮政编码必须是6个数字',
			maxLength: 6,
			maxLengthText: '机构邮政编码必须是6个数字',
			vtype: 'isNumber',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		{header: '联系人',dataIndex: 'brhContName',width: 60,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			emptyText: '请输入负责人姓名',
			maxLength: 20,
			maxLengthText: '负责人姓名最多可以输入20个汉字'
		 })},
		 {header: '地区编码',dataIndex: 'resv1',width: 80,
		 editor:new Ext.form.TextField({
		 	allowBlank: false,
			emptyText: '请输入地区编码',
			minLength: 4,
			maxLength: 4,
			maxLengthText: '地区编码最多可以输入4个数字',
			vtype: 'isNumber',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })},
		{header: '银联编号',dataIndex: 'cupBrhId',width: 60,
		editor:new Ext.form.TextField({
		 	allowBlank: false,
			emptyText: '请输入银联编号',
			minLength: 8,
			minLengthText: '银联编号必须是8个数字',
			maxLength: 8,
			maxLengthText: '银联编号必须是8个数字',
			vtype: 'isNumber',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		 })}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var brhId = rec.get('brhId');				
				showConfirm('确定要删除该机构吗？机构编号：' + brhId,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10101Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
										upBrhStore.loadData(Ext.decode(ret));
									});
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								brhId: brhId,
								txnId: '10101',
								subTxnId: '02'
							}
						}
						);
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
				if(record.get('brhId') == record.get('upBrhId')) {
					showAlertMsg('上级机构编号不能与本机构编号相同',grid);
					grid.getSelectionModel().selectRow(i);
					return false;
				}
			}
//			showProcessMsg('正在保存机构信息，请稍后......');
			//存放要修改的机构信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('brhId'),
					brhLevel: record.get('brhLvl'),
					upBrhId: record.get('upBrhId'),
					brhName: record.get('brhName'),
					brhAddr: record.get('brhAddr'),
					brhTelNo: record.get('brhTelNo'),
					postCd: record.get('postCode'),
					brhContName: record.get('brhContName'),
					cupBrhId: record.get('cupBrhId'),
					resv1:record.get('resv1')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10101Action.asp?method=update',
				method: 'post',
				params: {
					brhDataList : Ext.encode(array),
					txnId: '10101',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
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
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);	
	menuArr.push('-');
	menuArr.push(queryCondition);
	
	// 可选机构下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择机构',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择一个机构',
		fieldLabel: '机构编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [brhCombo,{
			xtype: 'textfield',
			id: 'searchBrhName',
			name: 'searchBrhName',
			maxLength: 40,
			width: 160,
			fieldLabel: '机构名称'
		}]
	});

	//机构列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '机构信息维护',
		iconCls: 'T10101',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载机构信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
				gridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//查询机构下操作员信息
			oprGridPanel.getStore().removeAll();
			oprGridPanel.getStore().reload();
			//查询机构下商户信息
			mchntGridPanel.getStore().removeAll();
			mchntGridPanel.getStore().reload();
			//查询机构下终端信息
			termGridPanel.getStore().removeAll();
			termGridPanel.getStore().reload();
		}
	});
	
	//机构添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '行内机构编码*',
			allowBlank: false,
			emptyText: '请输入行内机构编号',
			id: 'brhId',
			name: 'brhId',
			width: 200,
			maxLength: 4,
			maxLengthText: '行内机构编号最多可以输入4个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			xtype: 'combo',
			store: brhLvlStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'brhLvl',
			emptyText: '请选择机构级别',
			fieldLabel: '机构级别*',
			mode: 'local',
			width: 200,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个机构级别'
		},{
			xtype: 'combo',
			store: upBrhStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'upBrhId',
			emptyText: '请选择上级机构编码',
			fieldLabel: '上级机构编码*',
			mode: 'local',
			width: 200,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个上级机构编码'
		},{
			fieldLabel: '机构名称*',
			allowBlank: false,
			blankText: '机构名称不能为空',
			emptyText: '请输入机构名称',
			id: 'brhName',
			name: 'brhName',
			width: 200,
			maxLength: 40,
			maxLengthText: '机构名称最多可以输入40个汉字'
		},{
			fieldLabel: '机构地区码*',
			allowBlank: false,
			emptyText: '请输入机构地区码',
			id: 'resv1',
			name: 'resv1',
			maxLength: 4,
			maxLengthText: '机构地区码最多可以输入4位数',
			width: 200,
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '机构地址*',
			allowBlank: false,
			blankText: '机构地址不能为空',
			emptyText: '请输入机构地址',
			id: 'brhAddr',
			name: 'brhAddr',
			maxLength: 40,
			maxLengthText: '机构地址最多可以输入40个汉字',
			width: 200
		},{
			fieldLabel: '机构联系电话*',
			allowBlank: false,
			emptyText: '请输入机构联系电话',
			id: 'brhTelNo',
			name: 'brhTelNo',
			width: 200,
			maxLength: 20,
			maxLengthText: '机构联系电话最多可以输入20个数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '邮政编码*',
			allowBlank: false,
			emptyText: '请输入机构邮政编码',
			id: 'postCode',
			name: 'postCode',
			width: 200,
			minLength: 6,
			minLengthText: '机构邮政编码必须是6个数字',
			maxLength: 6,
			maxLengthText: '机构邮政编码必须是6个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '负责人姓名',
			emptyText: '请输入负责人姓名',
			id: 'brhContName',
			name: 'brhContName',
			width: 200,
			maxLength: 20,
			maxLengthText: '负责人姓名最多可以输入20个汉字'
		},{
			fieldLabel: '银联机构号*',
			emptyText:'请输入银联机构号',
			allowBlank: false,
			minLength: 8,
			minLengthText: '银联机构号必须是8个数字',
			maxLength: 8,
			maxLengthText: '银联机构号必须是8个数字',
			id: 'cupBrhId',
			name: 'cupBrhId',
			width: 200,
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
	}]
	});
	
	//机构添加窗口
	var brhWin = new Ext.Window({
		title: '机构添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [brhInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(brhInfoForm.getForm().isValid()) {
					brhInfoForm.getForm().submit({
						url: 'T10101Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,brhInfoForm);
							//重置表单
							brhInfoForm.getForm().reset();
							//重新加载机构列表
							grid.getStore().reload();
							//重新加载上级机构信息
							SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret){
								upBrhStore.loadData(Ext.decode(ret));
							});
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							txnId: '10101',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				brhInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				brhWin.hide(grid);
			}
		}]
	});
	
	/*****************************************操作员信息*****************************************/
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoWithBrh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'}
		])
	});
	
	oprGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			brhId: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var oprTpl = new Ext.Template(
			'<p title="操作员姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{oprName}</p>',
			'<p title="操作员性别"><img src="' + Ext.contextPath + '/ext/resources/images/gender_16.png"/>：{oprGender:this.gender}</p>',
			'<p title="注册日期"><img src="' + Ext.contextPath + '/ext/resources/images/calendar_16.png"/>：{registerDt:this.date}</p>',
			'<p title="操作员联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{oprTel}</p>',
			'<p title="操作员移动电话"><img src="' + Ext.contextPath + '/ext/resources/images/mobile_16.png"/>：{oprMobile}</p>'
	);
	
	oprTpl.gender = function(val) {
		if(val == '0') {
			return '男';
		} else {
			return '女';
		}
	};
	
	oprTpl.date = function(val) {
		if(val.length == 8) {
			return val.substring(0,4) + '年' +
				   val.substring(4,6) + '月' + 
				   val.substring(6,8) + '日';
		} else {
			return val;
		}
	};
	
	var oprRowExpander = new Ext.ux.grid.RowExpander({
		tpl: oprTpl
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
		oprRowExpander,
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'center',sortable: true},
		{header: '所在机构编号',align: 'center',dataIndex: 'brhId'}
	]);
	
	var oprGridPanel = new Ext.grid.GridPanel({
		id: 'oprPanel',
		title: '操作员信息',
		frame: true,
		iconCls: 'T104',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		collapsible: true,
		plugins: oprRowExpander,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	/******************************************商户信息***********************************************/
	
	var mchntGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchntId'
		},[
			{name: 'mchntId',mapping: 'mchtNo'},
			{name: 'mchntCnName',mapping: 'mchtNm'},
			{name: 'mchntEnName',mapping: 'mchntEnName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'certificate',mapping: 'certificate'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'email',mapping: 'email'},
			{name: 'legalName',mapping: 'legalName'},
			{name: 'contactName',mapping: 'contactName'},
			{name: 'contactTel',mapping: 'contactTel'},
			{name: 'registerDate',mapping: 'applyDate'},
			{name: 'mchntSt',mapping: 'mchtStatus'}
		])
	});
	
	mchntGridStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			brhId: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var mchntTpl = new Ext.Template(
			'<p title="商户中文名称"><img src="' + Ext.contextPath + '/ext/resources/images/cn_16.png"/>：{mchntCnName}</p>',
			'<p title="商户英文名称"><img src="' + Ext.contextPath + '/ext/resources/images/en_16.png"/>：{mchntEnName}</p>',
			'<p title="营业执照号码"><img src="' + Ext.contextPath + '/ext/resources/images/certificate_16.png"/>：{certificate}</p>',
			'<p title="商户地址"><img src="' + Ext.contextPath + '/ext/resources/images/branch.png"/>：{addr}</p>',
			'<p title="邮政编码"><img src="' + Ext.contextPath + '/ext/resources/images/post_16.png"/>：{postCode}</p>',
			'<p title="电子邮件地址"><img src="' + Ext.contextPath + '/ext/resources/images/email_16.png"/>：{email}</p>',
			'<p title="法人姓名"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{legalName}</p>',
			'<p title="联系人姓名"><img src="' + Ext.contextPath + '/ext/resources/images/male.png"/>：{contactName}</p>',
			'<p title="联系电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{contactTel}</p>'
	);	
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: mchntTpl
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchntId',header: '商户编号',dataIndex: 'mchntId',sortable: true},
		{header: '商户MCC',dataIndex: 'mcc'}
	]);
	
	var mchntGridPanel = new Ext.grid.GridPanel({
		id: 'mchntPanel',
		title: '商户信息',
		frame: true,
		iconCls: 'T201',
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: mchntGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		collapsible: true,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/*******************************************终端信息**********************************************/
	
	// 数据集
	var termTypeMap = new DataMap('TERM_TYPE');
	
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSt',mapping: 'termSt'},
			{name: 'isSuppIc',mapping: 'isSuppIc'},
			{name: 'isSuppCredit',mapping: 'isSuppCredit'},
			{name: 'termType',mapping: 'termType'},
			{name: 'callType',mapping: 'callType'},
			{name: 'callNo',mapping: 'callNo'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'sequenceNo',mapping: 'sequenceNo'},
			{name: 'kbSequenceNo',mapping: 'kbSequenceNo'},
			{name: 'vTeller',mapping: 'vTeller'},
			{name: 'encType',mapping: 'encType'},
			{name: 'bindNo',mapping: 'bindNo'}
		])
	});
	
	termStore.on('beforeload',function() {
		Ext.apply(this.baseParams,{
			start: 0,
			termBranch: grid.getSelectionModel().getSelected().get('brhId')
		});
	});
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p title="是否支持IC卡"><img src="' + Ext.contextPath + '/ext/resources/images/ic_16.png"/>：{isSuppIc:this.isSuppIc}</p>',
			//'<p title="是否支持信用卡"><img src="' + Ext.contextPath + '/ext/resources/images/credit_16.png"/>：{isSuppCredit:this.isSuppCredit}</p>',
			'<p title="终端类型"><img src="' + Ext.contextPath + '/ext/resources/images/pos_16.png"/>：{termType:this.termType}</p>',
			'<p title="拨入类型"><img src="' + Ext.contextPath + '/ext/resources/images/right_16.png"/>：{callType:this.callType}</p>',
			//'<p title="加解密类型"><img src="' + Ext.contextPath + '/ext/resources/images/key_16.png"/>：{encType:this.encType}</p>',
			//'<p title="拨入号码"><img src="' + Ext.contextPath + '/ext/resources/images/remote_16.png"/>：{callNo}</p>',
			'<p title="所属机构"><img src="' + Ext.contextPath + '/ext/resources/images/branch.png"/>：{brhId}</p>',
			'<p title="终端序列号"><img src="' + Ext.contextPath + '/ext/resources/images/computer_16.png"/>：{sequenceNo}</p>',
			//'<p title="密码键盘序列号"><img src="' + Ext.contextPath + '/ext/resources/images/keyboard_16.png"/>：{kbSequenceNo}</p>',
			//'<p title="设备虚拟柜员"><img src="' + Ext.contextPath + '/ext/resources/images/user.png"/>：{vTeller}</p>',
			'<p title="绑定电话"><img src="' + Ext.contextPath + '/ext/resources/images/phone_16.png"/>：{bindNo}</p>',
			{
				termType: function(val) {
					return termTypeMap[val];
				},
				isSuppIc: function isSuppIc(val) {
					if(val == '1') 
						return '是';
					else if(val == '0')
						return '否';
					return val;
				},
				isSuppCredit: function(val) {
					if(val == '1') 
						return '是';
					else if(val == '0')
						return '否';
					return val;
				},
				callType: function(val) {
					if(val == '00') 
						return 'GPRS拨入';
					else if(val == '01')
						return 'CDMA拨入';
					else if(val == '02') {
						return '其他拨入';
					}
					return val;
				},
				encType: function(val) {
					if(val == '1') 
						return '软件加密';
					else if(val == '0')
						return '加密机加密';
					return val;
				}
			}
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		termRowExpander,
		{id: 'termId',header: '终端编号',dataIndex: 'termId',width: 100},
		{header: '所属商户编号',dataIndex: 'mchntNo',width: 150},
		{header: '终端状态',dataIndex: 'termSt',width: 150,renderer: termSt}
	]);	
	
	// 终端状态
	function termSt(val) {
		if(val == '0') {
			return '<font color="green">正常</font>';
		} else if(val == '1') {
			return '<font color="gray">添加待审核</font>';
		} else if(val == '2') {
			return '<font color="gray">添加审核退回</font>';
		} else if(val == '3') {
			return '<font color="gray">修改待审核</font>';
		} else if(val == '4') {
			return '<font color="gray">修改审核退回</font>';
		} else if(val == '5') {
			return '<font color="gray">冻结待审核</font>';
		} else if(val == '6') {
			return '<font color="red">冻结</font>';
		} else if(val == '7') {
			return '<font color="gray">恢复待审核</font>';
		}
		return val;
	}
	
	// 终端信息列表
	var termGridPanel = new Ext.grid.GridPanel({
		id: 'termGridPanel',
		title: '终端信息',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		plugins: termRowExpander,
		collapsible: true,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var rightPanel = new Ext.Panel({
		region: 'east',
		split: true,
		width: 265,
		frame: true,
		collapsible: true,
		autoScroll: true,
		title: '机构关联信息',
		items:[oprGridPanel,mchntGridPanel,termGridPanel]
	});
	
	oprGridPanel.on('render',function() {
		new Ext.dd.DragSource(oprGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	mchntGridPanel.on('render',function() {
		new Ext.dd.DragSource(mchntGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	termGridPanel.on('render',function() {
		new Ext.dd.DragSource(termGridPanel.getEl(), {
			ddGroup: 'dd'
		});
	});
	
	var items;
	var draggedPanel;
	var positionY;
	var itemY;
	var index;
	rightPanel.on('render',function() {
		var rightPanelTarget = new Ext.dd.DropTarget(rightPanel.getEl(),{
			ddGroup: 'dd',
			notifyDrop: function(source,e) {
				items = rightPanel.items.items;
				draggedPanel = Ext.getCmp(source.getEl().id);
				positionY = e.getPageY();
				heigh = 0;
				index = 10;
				for(var i = 0; i < draggedPanel.el.dom.parentNode.childNodes.length; i++) {
					itemY = Ext.getCmp(draggedPanel.el.dom.parentNode.childNodes[i].id).getPosition()[1];
					if(itemY > positionY) {
						index = i;
						break;
					}
				}
				if(index == 10) {
					draggedPanel.el.dom.parentNode.appendChild(draggedPanel.el.dom);
				} else {
					draggedPanel.el.dom.parentNode.insertBefore(draggedPanel.el.dom, draggedPanel.el.dom.parentNode.childNodes[index]);
				}
				
			}
		});		
	});

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			brhId: queryForm.findById('editBrh').getValue(),
			brhName: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel,rightPanel]
	});
});