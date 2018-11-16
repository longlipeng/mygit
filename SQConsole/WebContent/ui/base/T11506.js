Ext.onReady(function() {

	
	// 当前选择记录
	var record;
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	//分润方式数据集
	var divideTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('DIVIDETYPE',function(ret){
		divideTypeStore.loadData(Ext.decode(ret));
	});
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	
	// 代理商分润数据集
	var agentDivideStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agentDivideToShenHe'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
            {name: 'uuid',mapping: 'uuid'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'divideType',mapping: 'divideType'},
			{name: 'discCd',mapping: 'discCd'},
			{name: 'profit',mapping: 'profit'},
			{name: 'minValue',mapping: 'minValue'},
			{name: 'maxValue',mapping: 'maxValue'},
			{name: 'state',mapping: 'state'},
			{name: 'crtPer',mapping: 'crtPer'},
			{name: 'crtDate',mapping: 'crtDate'},
			{name: 'upPer',mapping: 'upPer'},
			{name: 'upDate',mapping: 'upDate'}
			
		])
	});
	

	
	
	agentDivideStore.load({

	});
	

	
	var agentDivideCM = new Ext.grid.ColumnModel([
	              sm,     
	    {header: 'UUID',dataIndex: 'uuid',width: 200,hidden:true},
		{header: '代理商编号 - 代理商名称',dataIndex: 'agentNo',width: 200,sortable: true ,renderer:function(val){return getRemoteTrans(val, "getAgentNm");}},
		{header: '分润方式',dataIndex: 'divideType',width: 100,renderer:function(val){return getRemoteTrans(val, "divideType");}},
		{header: '代理商费率参数',dataIndex: 'discCd',width: 150,renderer:discCd },
	    {header: '代理商分润下限值(万元)',dataIndex: 'minValue',width: 150},
	    {header: '代理商分润上限值(万元)',dataIndex: 'maxValue',width: 150},
		{header: '代理商分润参数(单位：%)',dataIndex: 'profit',width: 150},
	     {header: '状态',dataIndex: 'state',width: 80,renderer:state},
	     {header: '申请人',dataIndex: 'crtPer',width: 90},
	     {header: '申请时间',dataIndex: 'crtDate',width: 120,renderer:formatTs}
		]);
	
	function state(val){
		if('0' == val)
			return '新增待审核';
		else if('1' == val)
			return '<font color="red">已删除</font>';
		else if('2' == val)
			return '<font color="green">正常</font>';
		else if('3' == val)
			return '修改待审核';
		else if('4' == val)
			return '删除待审核';
	}
	function discCd(val){    
		   if(val == '')
			   return '';
		   else{
			   return getRemoteTrans(val, "discCd");
		   }
			   
		}
	var menuArray = new Array();
	
		  
	
	var queryMenu = {
		text: '查询',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	
	//通过
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',grid,function(bt) {
				
				var selectRecords = grid.getSelectionModel().getSelections();
				if(selectRecords.length == 0){
					return;
				}
				var array = new Array();
				for(i=0;i<selectRecords.length;i++){
					var record = selectRecords[i];
					var data = {
					   uuid:record.get('uuid')							
					};
					
					array.push(data);
				}
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');					
					Ext.Ajax.request({
						url: 'T11506Action.asp?method=accept',
						params: {
							uuidList:Ext.encode(array),
							txnId: '11502',
							subTxnId: '06'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载待审核信息
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
		var selectRecords = grid.getSelectionModel().getSelections();
		if(selectRecords.length == 0){
			return;
		}
		var array = new Array();
		for(i=0;i<selectRecords.length;i++){
			var record = selectRecords[i];
			var data = {
			   uuid:record.get('uuid')							
			};
			
			array.push(data);
		}
		
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = grid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T11506Action.asp?method=refuse',
				params: {
				 //   id: rec.get('feeid'),
					uuidList:Ext.encode(array),
					txnId: '11502',
					subTxnId: '07',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,grid);
					} else {
						showErrorMsg(rspObj.msg,grid);
					}
					// 重新加载待审核信息
					grid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	menuArray.add(queryMenu); //0
    menuArray.add('-');       //1
    menuArray.add(acceptMenu);//2
    menuArray.add('-');       //3
    menuArray.add(refuseMenu);//4

	
	// 代理商分润grid
	var grid = new Ext.grid.EditorGridPanel({
		title: '代理商分润信息',
//		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: agentDivideStore,
//		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm: sm,
		cm: agentDivideCM,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载代理商分润信息......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: agentDivideStore,
			//pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/*grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});*/
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			grid.getTopToolbar().items.items[2].enable();
			grid.getTopToolbar().items.items[4].enable();
		}
	});
/*	
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('saState')!='4')
				grid.getTopToolbar().items.items[2].enable();
			else
				grid.getTopToolbar().items.items[2].disable();
		}
	});
	*/

	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [ 
		{
			//xtype: 'textfield',
			xtype:'dynamicCombo',
			methodName:'getAgentNo',
			id: 'idqueryAgentNo',
			hiddenName: 'queryAgentNo',
			fieldLabel: '代理商编号'
		},{
	    	xtype: 'basecomboselect',
	    	id:'queryDivideType',
            fieldLabel: '分润方式',
            hiddenName: 'DivideType',
            store:divideTypeStore,
            editable: true,
            width:300,
            anchor: '70%'
		},{
	    	xtype: 'combo',
            fieldLabel: '状态',
            hiddenName: 'state',
            id:'idstate',
            store:new Ext.data.ArrayStore({
            	 fields: ['valueField','displayField'],
                 data: [['0','新增待审核'],['1','已删除'],['2','正常'],['3','修改待审核'],['4','删除待审核']]
            }),
            width:300,
            anchor: '70%'
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
				agentDivideStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	agentDivideStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			agentNo_query: queryForm.findById('idqueryAgentNo').getValue(),
			divideType_query:queryForm.findById('queryDivideType').getValue(),
			state_query:queryForm.findById('idstate').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});