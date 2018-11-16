Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集

	//卡类型数据集
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	var ActionStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('ACTION',function(ret){
		ActionStore.loadData(Ext.decode(ret));
	});
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=csttermfeeinf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'recCrtTs'
		},[
		    {name: 'mchtcdname',mapping: 'mchtcdname'},
		    {name: 'mchtcd',mapping: 'mchtcd'},
		    {name: 'termid',mapping: 'termid'},
			{name: 'channel',mapping: 'channel'},
			{name: 'daynum',mapping: 'daynum'},
			{name: 'dayamt',mapping: 'dayamt'},
			{name: 'daysingle',mapping: 'daysingle'},
			{name: 'sastatue',mapping: 'sastatue'},
			{name: 'saaction',mapping: 'saaction'},
			{name: 'creId',mapping: 'creId'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'upId',mapping: 'upId'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'monAmt',mapping: 'monAmt'}
		]),
		autoLoad: true
	});

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户编号',dataIndex: 'mchtcd',width: 100},
		{header: '商户名称',dataIndex: 'mchtcdname',width: 200},
		{header: '终端号',dataIndex: 'termid',width: 100},
		{header: '卡类型',dataIndex: 'cardType',width: 100,/*renderer:function(val){return cardStore.getById(val).displayField;}*/
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
		/*{header: '日累计笔数',dataIndex: 'daynum',width: 100,
			editor: new Ext.form.TextField({
				maxLength: 5,
				maskRe: /^[0-9]*$/,
				maskReText: '只能输入数字'
			})},*/
		{header: '日交易金额累计上限',dataIndex: 'dayamt',width: 120,editor:true},
		{header: '日交易单笔金额上限',dataIndex: 'daysingle',width: 120,editor:true},
		{header: '月交易金额累计上限',dataIndex: 'monAmt',width: 120,editor:true},
		{header: '审核状态',dataIndex: 'sastatue',sortable: true,width: 120,renderer:statue},

		/*{header: '受控状态',dataIndex: 'saaction',width: 100,
			renderer:action,
			editor: new Ext.form.ComboBox({
			 	store: ActionStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true})},*/
		
		{header: '修改人',dataIndex: 'creId',width: 90},
		{header: '修改时间',dataIndex: 'recCrtTs',width: 120,renderer: formatTs},
		{header: '审核人',dataIndex: 'upId',width: 90},
		{header: '审核时间',dataIndex: 'recUpdTs',width: 120,renderer: formatTs}	
	]);
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '删除';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '删除未审核';
		}
	}
	function action(val){
		if(val == '0'){
			return '警告'; 
		}else if(val == '1'){
			return '拒绝';
		}else{
			return '未知';
		}
	}
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
			var store = grid.getStore();
			var len = store.getCount();

			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				if(record.get('daynum').substr(0,1) == '-'){
		   			showErrorMsg('日累计笔数不能为负数，请重新输入',grid);
					return;
		   		}
				var reg = new RegExp("^\\d+$");
				if(!reg.test(record.get('daynum'))){
					showErrorMsg('日累计笔数只能为数字，请重新输入',grid);
					return;
				}
				if(record.get('daysingle').substr(0,1) == '-'){
		   			showErrorMsg('日交易单笔金额上限不能为负数，请重新输入',grid);
					return;
		   		}
				var data = {
					mchtcd: record.get('mchtcd'),
					termid: record.get('termid'),
					daynum: record.get('daynum'),
					dayamt: record.get('dayamt'),
					daysingle: record.get('daysingle'),
					saaction: record.get('saaction'),
					cardtype:record.get('cardType'),
					monamt:record.get('monAmt')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T30106Action.asp?method=update',
				method: 'post',
				params: {
					CstTermFeeList : Ext.encode(array),
					txnId: '30106',
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
	//删除
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				if (grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();
		
					showConfirm('您确定要删除该条终端交易限额记录吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.requestNeedAuthorise( {
								url : 'T30106Action.asp?method=delete',
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, grid);
										grid.getStore().reload();
										grid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg, grid);
									}
								},
								params : {
									mchtcd: grid.getSelectionModel().getSelected().get('mchtcd'),
									termid: grid.getSelectionModel().getSelected().get('termid'),
									cardType:grid.getSelectionModel().getSelected().get('cardType'),
									txnId : '30106',
									subTxnId : '02'
								}
							});
						}
					});
				}
			}
	};
	
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
	//添加表单
	var brhInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 350,
		defaultType: 'textfield',
		labelWidth: 130,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo', 
			fieldLabel: '商户编号*',
			allowBlank: false,
			emptyText: '请输入商户编号',
			methodName: 'getMchtcd',
			id: 'idMCHT_CD',
			hiddenName: 'MCHT_CD',
			lazyRender: true,
			width: 200,
			callFunction: function() {
				//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
				Ext.getCmp('idTERM_ID').reset();
				Ext.getCmp('idTERM_ID').parentP=this.value;
				Ext.getCmp('idTERM_ID').getStore().reload();
			}
		},{
			xtype: 'dynamicCombo',
			methodName: 'getTermIdNor',
			parentP:'',
			fieldLabel: '终端号*',
			allowBlank: false,
			editable: true,
			blankText: '该输入项只能包含数字',
			emptyText: '请输入终端号',
			id: 'idTERM_ID',
			hiddenName: 'TERM_ID',
			lazyRender: true,
			width: 200
		},{
			xtype: 'basecomboselect',
			baseParams: 'CARD_TYPE',
			fieldLabel: '卡类型*',
			allowBlank: false,
			emptyText: '请选择卡类型',
		//	id: 'idTERM_ID',
			hiddenName: 'cardType',
			width: 200
		}/*,{ 
			fieldLabel: '日累计笔数*',
			allowBlank: false,
			emptyText: '请输入日累计笔数',
			id: 'idDAY_NUM',
			name:'DAY_NUM',
			maxLength: 5,
			maskRe: /^[0-9]*$/,
			width: 200,
			blankText: '该输入项只能包含数字'
		}*/,{
			fieldLabel: '日交易金额累计*',
			allowBlank: false,
			blankText: '该输入日交易金额累计',
			emptyText: '请输入日交易金额累计',
			id: 'idDAY_AMT',
			name: 'DAY_AMT',
			maxLength: 15,
			width: 200,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			vtype: 'isMoney'
		},{
			fieldLabel: '日交易单笔金额上限*',
			allowBlank: false,
			blankText: '该输入日交易单笔金额上限',
			emptyText: '请输入日交易单笔金额上限',
			id: 'idDAY_SINGLE',
			name: 'DAY_SINGLE',
			width: 200,
			maxLength: 15,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			vtype: 'isMoney'
		}/*,{
			xtype: 'basecomboselect',
			baseParams: 'SAACTION',
			fieldLabel: '受控动作*',
			allowBlank: false,
			blankText: '该输入受控动作',
			emptyText: '请输入受控动作',
			id: 'idsaaction',
			hiddenName: 'saaction',
			width: 200,
			maskRe:/^[0-9]$/
		}*/,{
			fieldLabel: '月交易金额累计*',
			allowBlank: false,
			blankText: '该输入月交易金额累计',
			emptyText: '请输入月交易金额累计',
		//	id: 'idDAY_AMT',
			name: 'monAmt',
			maxLength: 15,
			width: 200,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			vtype: 'isMoney'
		}]
	});
	
	//添加窗口
	var brhWin = new Ext.Window({
		title: '终端交易限额添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 450,
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
				/*if(brhInfoForm.findById('idDAY_NUM').getValue().substr(0,1) == '-'){
		   			showErrorMsg('日累计笔数不能为负数，请重新输入',brhInfoForm);
					return;
		   		}*/
				if(brhInfoForm.getForm().isValid()) {
					brhInfoForm.getForm().submit({
						url: 'T30106Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,brhInfoForm);
							//重置表单
							brhInfoForm.getForm().reset();
							//重新加载列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,brhInfoForm);
						},
						params: {
							txnId: '30106',
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
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			id: 'idStatu',
			fieldLabel: '审核状态',
			width: 250,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','新增未审核'],['1','删除'],['2','正常状态'],['3','修改未审核'],['4','删除未审核']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchtcd',
			id:'idmtchid',
			hiddenName: 'mtchid',
			editable: true,
			width: 250,
			callFunction: function() {
					//选择商户编号重置终端的数据，并将商户号作为参数传给终端以便于查出该商户号下的终端信息
					Ext.getCmp('idtermid').reset();
					Ext.getCmp('idtermid').parentP=this.value;
					Ext.getCmp('idtermid').getStore().reload();
				}
		},{
			xtype: 'dynamicCombo',
			methodName: 'getTermId',
			parentP:'',
			fieldLabel: '终端号',
			id:'idtermid',
			hiddenName: 'termid',
			editable: true,
			width: 250
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var grid = new Ext.grid.EditorGridPanel({
		title:'终端交易限额维护',
		iconCls: 'T10108',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载终端交易限额信息列表......'
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
			//编辑后删除按钮可用
			/*'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},*/
		'beforeedit':function(e){
			if(e.record.get('sastatue')=='4'||e.record.get('sastatue')=='1'){
				e.cancel=true;
			}
		},
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
			if(rec.get('sastatue')!='3'){
				grid.getTopToolbar().items.items[4].enable();
			}else{
				grid.getTopToolbar().items.items[4].disable();
			}
				
			if(rec.get('sastatue')!='1'){
				grid.getTopToolbar().items.items[4].enable();}
			else{
				grid.getTopToolbar().items.items[4].disable();}
		}		
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			statue: queryForm.findById('idStatu').getValue(),
			mtchid: queryForm.findById('idmtchid').getValue(),
			termid: queryForm.findById('idtermid').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});
