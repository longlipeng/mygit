Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选机构数据集
		
	var brhLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var upBrhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	//级别
	SelectOptionsDWR.getComboData('BRANCH_LEVEL_LIST',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	//上级营业网点编号
	SelectOptionsDWR.getComboData('BRANCH_NO',function(ret){
		upBrhStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=braStaInfo'
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
			{name: 'stateid',mapping: 'stateid'},
			{name: 'branchlevel',mapping: 'branchlevel'},
			{name: 'parentbranchid',mapping: 'parentbranchid'}
		]),
		autoLoad: true
	});
	
//进入页面就加载列表
//	gridStore.load({
//		params:{start: 0}
//	});

	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'Id',header: '编号',dataIndex: 'Id',sortable: true,width: 50},
		{header: '级别',dataIndex: 'branchlevel',renderer: brhLvlRender,width: 50,editor:true},
		{header: '分公司名称'/*,hidden:true*/,dataIndex: 'branchname',width: 70},
		{header: '上级分公司编号',dataIndex: 'parentbranchid',width: 70,editor:true},
		{header: '分公司所属地区',dataIndex: 'brancharea',width: 90},
		{header: '分公司电话',dataIndex: 'phonenum',width: 70},
		{header: '分公司邮编',dataIndex: 'branchpos',width: 67},
		{id:'brhAddr',header: '分公司地址',dataIndex: 'branchaddr',width: 108},
		{header: '联系人',dataIndex: 'linkname',width: 80},
		{header: '联系人电话',dataIndex: 'linkmanpho',width: 70},
		{header: '联系人邮箱',dataIndex: 'linkmanmail',width: 85},
		{header: '分公司传真',dataIndex: 'branchfax',width: 70},
		{header: '分公司名称'/*,hidden:true*/,dataIndex: 'branchname',width: 70},
		{header: '审核状态',dataIndex: 'state',width: 70},
		{header: '审核状态',dataIndex: 'stateid',width: 70,hidden:true}
	]);
	//通过
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = grid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T90102Action.asp?method=accept',
						params: {
							Id: rec.get('Id'),
							state:rec.get('stateid'),
							txnId: '90102',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载分公司待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	//拒绝
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',grid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			if(rec.data.stateid=='0'){
				    subTxnId='02'
			}
			if(rec.data.stateid=='2'){
				    subTxnId='03'
			}
			if(rec.data.stateid=='8'){
				    subTxnId='04'
			}
			Ext.Ajax.request({
				url: 'T90102Action.asp?method=refuse',
				params: {
					Id: rec.get('Id'),
					txnId: '90102',
					subTxnId: subTxnId,
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,grid);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
					// 重新加载商户待审核信息
					grid.getStore().reload();

				}
			});
			hideProcessMsg();
		}
	}
	//菜单添加
	var menuArr = new Array();
	menuArr.push(acceptMenu);
    menuArr.push('-');
   menuArr.push(refuseMenu);
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
		editable: false,
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
		items: [/*brhCombo*/{
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
			gridStore.load();
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
		title:'分公司审核',
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
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	//在编辑单元格后使保存按钮可用
		grid.on({
			'beforeedit':function(e){
				e.cancel=true;
			},
			//编辑后拒绝通过按钮可用
			'rowclick': function() {
			if(grid.getTopToolbar().items.items[0] != undefined) {
				grid.getTopToolbar().items.items[0].enable();
			}
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});
	
	/********************************主界面*************************************************/
	
	var leftPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'border',
		items:[grid]
	});
	
	var rightPanel = new Ext.Panel();
	
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
			brhId: queryForm.findById('ideditBrh').getValue(),
			branchname: queryForm.findById('searchBrhName').getValue()
		});
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[leftPanel]
	});
});
