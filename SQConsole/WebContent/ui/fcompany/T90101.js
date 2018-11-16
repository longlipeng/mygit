Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选分公司数据集
	
	//级别  1 分公司   2办事处
	var brhLvlStore = new Ext.data.JsonStore({  
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRANCH_LEVEL_LIST',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	//上级分公司编号
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
//	SelectOptionsDWR.getComboData('BRANCH_NO',function(ret){
	SelectOptionsDWR.getComboData('BRH_BELOW_NORMAL',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	//地区代码数据集
	var cityCodeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('CITY_CODE_CODE',function(ret){
		cityCodeStore.loadData(Ext.decode(ret));
	});
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=branchInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'Id'
		},[
			{name: 'Id',mapping: 'Id'},
			{name: 'brancharea',mapping: 'brancharea'},
			{name: 'phonenum',mapping: 'phonenum'},
			{name: 'branchpos',mapping: 'branchpos'},
			{name: 'branchaddr',mapping: 'branchaddr'},
			{name: 'linkname',mapping: 'linkname'},
			{name: 'linkmanpho',mapping: 'linkmanpho'},
			{name: 'linkmanmail',mapping: 'linkmanmail'},
			{name: 'branchfax',mapping: 'branchfax'},
			{name: 'branchname',mapping: 'branchname'},
//			{name: 'branchname',mapping: 'branchname'},
			{name: 'state',mapping: 'state'},
			{name: 'branchlevel',mapping: 'branchlevel'},
			{name: 'parentbranchid',mapping: 'parentbranchid'}
		]),
		autoLoad: true
	});
	
//进入页面就加载列表
//	gridStore.load({
//		params:{start: 0}
//	});
	function branchLevel(branch) {
		switch(branch) {
			case '0': return '总公司';
			case '1': return '分公司';
			case '2': return '办事处'
		}
	}

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'Id',header: '编号',dataIndex: 'Id',sortable: true,width: 50},
		{header: '级别',dataIndex: 'branchlevel',renderer: branchLevel,width: 50,
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
		{header: '上级分公司编号',dataIndex: 'parentbranchid',width: 130,
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
		{header: '分公司名称'/*,hidden:true*/,dataIndex: 'branchname',width: 70,editor:true},
		{header: '所属地区编号',dataIndex: 'brancharea',width: 90,
		 editor: new Ext.form.ComboBox({
		 	store: cityCodeStore,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		 })},
		{header: '分公司电话',dataIndex: 'phonenum',width: 70,editor:true},
		{header: '分公司邮编',dataIndex: 'branchpos',width: 67,editor:true},
		{id:'brhAddr',header: '分公司地址',dataIndex: 'branchaddr',width: 108,
			editor: new Ext.form.TextField({
				maxLength: 60
		})},
		{header: '联系人',dataIndex: 'linkname',width: 80,editor:true},
		{header: '联系人电话',dataIndex: 'linkmanpho',width: 70,editor:true},
		{header: '联系人邮箱',dataIndex: 'linkmanmail',width: 85,editor:true},
		{header: '分公司传真',dataIndex: 'branchfax',width: 70,editor:true},
		{header: '分公司名称'/*,hidden:true*/,dataIndex: 'branchname',width: 70,editor:true},
		{header: '审核状态',dataIndex: 'state',width: 70}, //0新增未审核  1正常 2修改未审核 3新增拒绝 4修改拒绝 6注销拒绝 7注销 8注销未审核  12468
		{header: '审核状态',dataIndex: 'stateid',width: 70,hidden:true}
		
	]);
	//添加菜单
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			brhWin.show();
			brhWin.center();
		}
	};
	//修改菜单
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
//			showProcessMsg('正在保存分公司信息，请稍后......');
			//存放要修改的机构信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					Id: record.get('Id'),
					branchlevel:record.get('branchlevel'),
					parentbranchid:record.get('parentbranchid'),
					brancharea: record.get('brancharea'),
					phonenum: record.get('phonenum'),
					branchpos: record.get('branchpos'),
					branchaddr: record.get('branchaddr'),
					linkname: record.get('linkname'),
					linkmanpho: record.get('linkmanpho'),
					linkmanmail: record.get('linkmanmail'),
					branchfax: record.get('branchfax'),
					branchname: record.get('branchname'),
					state: record.get('stateid')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10108Action.asp?method=update',
				method: 'post',
				params: {
					branchDataList : Ext.encode(array),
					Id: record.get('Id'),
					brancharea: record.get('brancharea'),
					txnId: '90101',
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
					grid.getTopToolbar().items.items[2].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	//注销
	var delMenu = {
		text: '注销',
		width: 85,
		iconCls: 'recycle',
		disabled: true,
		handler:function() {
			showConfirm('您确定注销分公司信息吗？',grid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				}
			});
		}
	};
	
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10108Action.asp?method=delete',
				params: {
					Id: grid.getSelectionModel().getSelected().get('Id'),
					//stateid:grid.getSelectionModel().getSelected().get('stateid'),
					txnId: '90101',
					subTxnId: '02',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,grid);
						grid.getTopToolbar().items.items[2].disable();
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
					// 重新加载分公司信息
					grid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	//菜单添加
	var menuArr = new Array();
    menuArr.push(addMenu);
    menuArr.push('-');
    menuArr.push(upMenu)
    menuArr.push('-');
    menuArr.push(delMenu);
    
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
	menuArr.push(queryCondition);  //[1]
	//分公司添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 90,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '分公司编号*',
			allowBlank: false,
			emptyText: '请输入分公司编号',
			id: 'Id',
			name: 'Id',
			width: 200,
			regex:/^[0-9]{4}$/,
			regexText:'请输入4位数字',
			vtype: 'isNumber',
			blankText: '该输入项只能包含数字'
		},{
			xtype: 'combo',
			store: brhLvlStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'branchlevel',
			emptyText: '请选择级别',
			fieldLabel: '级别*',
			mode: 'local',
			width: 200,
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择级别'
		},{
			xtype:'dynamicCombo',
			fieldLabel: '上级分公司*',
			allowBlank: false,
			blankText: '请选择上级分公司',
			emptyText: '请选择上级分公司',
			methodName: 'getBranchList',
			id: 'idparentbranchid',
			hiddenName: 'parentbranchid',
			width: 200
		},{
			xtype:'dynamicCombo',
			fieldLabel: '分公司所属地*',
			allowBlank: false,
			blankText: '分公司所属地区不能为空',
			emptyText: '请输入分公司地区',
			methodName: 'getAreaCodecode',
			id: 'idbrancharea',
			hiddenName: 'brancharea',
			width: 200
		},{
			fieldLabel: '分公司电话*',
			allowBlank: false,
			emptyText: '请输入分公司电话',
			id: 'phonenum',
			name: 'phonenum',
			maxLength: 30,
			width: 200,
			regex:/^[0-9]*$/,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '分公司邮编*',
			allowBlank: false,
			blankText: '分公司邮编不能为空',
			emptyText: '请输入分公司邮编',
			id: 'branchpos',
			name: 'branchpos',
			maxLength: 6,
			regex:/^[0-9]*$/,
			regexText:'请输入6位邮编',
			blankText: '该输入项只能包含数字',
			width: 200
		},{
			fieldLabel: '分公司地址*',
			allowBlank: false,
		//	emptyText: '请输入分公司地址',
			maxLength: 60,
			id: 'branchaddr',
			name: 'branchaddr',
			width: 200
		},{
			fieldLabel: '联系人',
			allowBlank: true,
		//	emptyText: '请输入联系人',
			id: 'linkname',
			name: 'linkname',
			maxLength: 15,
			width: 200
		},{
			fieldLabel: '联系人电话',
			//emptyText: '请输入联系人电话',
			id: 'linkmanpho',
			name: 'linkmanpho',
			width: 200,
			//maskRe:/^[0-6]$/,
			maxLength: 30,
			blankText: '该输入项只能包含数字',
			regex:/^[0-9]*$/,
			regexText:'请输入数字'
		},{
			fieldLabel: '联系人邮箱',
			//emptyText:'请输入联系人邮箱',
			allowBlank: true,
			id: 'linkmanmail',
			name: 'linkmanmail',
			maxLength: 60,
			width: 200,
			regex:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			regexText:'邮箱格式输入有误，请重新输入'
	},{
			fieldLabel: '分公司传真',
		//	emptyText:'请输入分公司传真',
			allowBlank: true,
			id: 'branchfax',
			name: 'branchfax',
			width: 200,
			maxLength: 15,
			regex:/^[0-9]*$/,
			regexText:'请输入数字',
		//	maskRe:/^[0-6]$/,
			blankText: '该输入项只能包含数字'
	},{
			fieldLabel: '分公司名称*',
			emptyText:'请输入分公司名称',
			allowBlank: false,
			id: 'branchname',
			name: 'branchname',
//			hidden:true,
			maxLength: 25,
			width: 200
	},{
			fieldLabel: '分公司描述*',
		//	emptyText:'请输入分公司名称',
			allowBlank: false,
			id: 'branchdes',
			name: 'branchdes',
			maxLength: 60,
			width: 200
	}]});
	//分公司添加窗口
	var brhWin = new Ext.Window({
		title: '分公司添加',
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
						url: 'T10108Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,brhWin);
							//重置表单
							brhInfoForm.getForm().reset();
							brhWin.hide();
							//重新加载分公司列表
							grid.getStore().reload();
							
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhWin);
						},
						params: {
							txnId: '90101',
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
	
	// 可选分公司下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: upBrhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择分公司',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: true,
		allowBlank: false,
		blankText: '请选择一个分公司',
		fieldLabel: '分公司编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [/*brhCombo,*/
			{
				xtype: 'dynamicCombo',
				methodName: 'geteditBrh',
				fieldLabel: '分公司编号',
				allowBlank: true,
				hiddenName: 'editBrh',
				id : 'ideditBrh',
				editable: true,
				blankText: '分公司编号不能为空',
				emptyText: '请选择分公司编号',
				width:160
			},{
			xtype: 'textfield',
			id: 'searchBrhName',
			width: 160,
			name: 'searchBrhName',
			hidden:true
//			fieldLabel: '分公司名称'
		}]
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
			gridStore.load({
				params : {
				start : 0,
				brhId: queryForm.findById('ideditBrh').getValue(),
				branchname: queryForm.findById('searchBrhName').getValue()
				}
			});
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});

	//分公司列表
	var grid = new Ext.grid.EditorGridPanel({
		title:'分公司信息查询',
		iconCls: 'T10108',
		region: 'center',
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
			msg: '正在加载分公司信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//保存按钮初始化不可用
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	//在编辑单元格后使保存按钮可用
		grid.on({
			//删除 待审核状态 禁止修改
			'beforeedit':function(e){
				if(e.record.get('state')=="注销" ||e.record.get('state')=="注销未审核"){
//					grid.getTopToolbar().items.items[4].disable();
					e.cancel=true;
				}
			},
			//编辑后删除按钮可用
//			'rowclick': function() {
//			if(grid.getTopToolbar().items.items[4] != undefined) {
//				grid.getTopToolbar().items.items[4].enable();
//			}
//		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get("state")=="删除未审核" || rec.get("state")=="修改未审核")
				grid.getTopToolbar().items.items[4].disable();
			else
				grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	/********************************主界面*************************************************/
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
			
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});
