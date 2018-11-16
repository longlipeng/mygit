Ext.onReady(function() {	
	
	//交易类型
	/*var txnNumStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_LIMIT',function(ret){
		txnNumStore.loadData(Ext.decode(ret));
	});*/
	//卡类型
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtFeeInfCheck'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'dayNum',mapping: 'dayNum'},
			{name: 'dayAmt',mapping: 'dayAmt'},
			{name: 'daySingle',mapping: 'daySingle'},
			{name: 'monNum',mapping: 'monNum'},
			{name: 'monAmt',mapping: 'monAmt'},
			{name: 'saState',mapping:'saState'},
			/*{name: 'saAction',mapping:'saAction'}*/
		])
	});
	
	mchntStore.load({
		params:{start: 0}
	});
	
	var oprColModel = new Ext.grid.ColumnModel([
    		new Ext.grid.RowNumberer(),
    		{id: 'mchtCd',header: '商户编号',dataIndex: 'mchtCd',sortable: true},
    		{id: 'mchtNm',header: '商户名称',dataIndex: 'mchtNm',sortable: true},
/*    		{header: '交易名称',dataIndex: 'txnNum',width: 100,renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnNumStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }},*/
    		{header: '卡类型',dataIndex: 'cardType',width: 80,renderer: cardTypeT},
    		/*{header: '单日累计交易笔数',dataIndex: 'dayNum',width: 110},*/
    		{header: '单日单笔交易金额',dataIndex: 'daySingle',width: 110},
    		 {header: '单日累计交易金额',dataIndex: 'dayAmt',width: 110},
    		 {header: '单月累计交易金额',dataIndex: 'monAmt',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 22,
        		 	maskRe: /^[0-9\\.]+$/,
        			allowBlank: false,
        			vtype: 'isMoney'
        	})},
        	/* {header:'受控动作',dataIndex:'saAction',width:110,renderer:action},*/
        	 {header: '状态',dataIndex: 'saState',width: 120,renderer: riskInfoState}
    		 /*{header: '单月累计交易笔数',dataIndex: 'monNum',width: 110,
        		 editor: new Ext.form.TextField({
        		 	maxLength: 6,
        			allowBlank: false,
					maskRe: /^[0-9]+$/
        		 })},*/

    	]);
	
	function action(val){
	if(val=='0'){
		return '提示'; 
		}
		else{
		return '拒绝';
		}
	}
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
	};
	
	//转译卡种类
	function cardTypeT(val) {
		if(val == '00'){
			return '贷记卡';
		}else if (val == '01'){
			return '借记卡';
		}else if (val == '02'){
			return '准贷记卡';
		}else if (val == '03'){
			return '预付卡';
		}else {
			return '未知卡种类';
		}
	}
	
	/*******************************交易限额操作记录****************************/
	var logStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskRefuseInfo4MchtFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'brhID',mapping: 'brhID'},
			{name: 'oprID',mapping: 'oprID'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'},
			{name: 'param1',mapping: 'param1'},
			{name: 'param2',mapping: 'param2'},
			{name: 'param3',mapping: 'param3'},
			{name: 'param4',mapping: 'param4'},
			{name: 'param5',mapping: 'param5'},
			{name: 'param6',mapping: 'param6'},
			{name: 'reserve1',mapping: 'reserve1'}
		])
	});
	logStore.on('beforeload', function() {
		logStore.removeAll();
		Ext.apply(this.baseParams, {
            start: 0,
            flag:"9",
            mchnNoQ: oprGrid.getSelectionModel().getSelected().data.mchtCd,
            cardTypeQ:oprGrid.getSelectionModel().getSelected().data.cardType
        });
	});
	
	var logColModel = new Ext.grid.ColumnModel([
	     new Ext.grid.RowNumberer(),
	     {header: '操作人',dataIndex: 'oprID',sortable: true,width: 90,renderer:getOpr},
	     {header: '操作时间',dataIndex: 'txnTime',sortable: true,width: 100,renderer: formatTs},
	     {header: '操作类型',dataIndex: 'param6',sortable: true,width:70},
		 {header: '操作状态',dataIndex: 'reserve1',sortable: true,width:55},
		 {header: '备注',dataIndex: 'refuseInfo',sortable: true,width:120,id:'refuseInfo'}
	     ]);
	
	function getOpr(val){
		if(val!=null&&val!=''){
		return getRemoteTrans(val,"getOprNm");
		}else{
			return val;
		}
	}
	
	
	logGrid = new Ext.grid.GridPanel({
		title: '操作记录',
		region: 'east',
		width: 400,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
//		autoExpandColumn: 'refuseInfo',
		stripeRows: true,
		store: logStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: logColModel,
		clicksToEdit: true,
		forceValidation: true,
//		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载操作记录列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: logStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	/*******************************交易限额操作记录end****************************/
	
		
	/******************************限额信息添加******************************/
	var cardTypeCombo = new Ext.form.ComboBox({
		hidden:true,
		store: cardTypeStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择卡类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个卡类型',
		fieldLabel: '卡类型*',
		hiddenName: 'cardType'
	});
	/*var txnNumCombo = new Ext.form.ComboBox({
		hidden:true,
		store: txnNumStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择交易类型',
		mode: 'local',
		width: 400,
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: true,
		blankText: '请选择一个交易类型',
		fieldLabel: '交易类型*',
		id: 'txnNumId',
		hiddenName: 'txnNum'
	});
	

	
	
	 
	
	var oprInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 140,
		waitMsgTarget: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'idMchtCd',
			allowBlank: false,
			editable: true,
			width: 400
    	},txnNumCombo,
		cardTypeCombo,
		{
			fieldLabel: '单日累计交易笔数*',
			allowBlank: false,
			blankText: '单日累计交易笔数不能为空',
			emptyText: '请输入单日累计交易笔数',
			maxLength: 5,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'dayNum',
			name: 'dayNum',
			vtype: 'alphanum',
			width: 400
		},
		{
			fieldLabel: '单日累计交易金额*',
			allowBlank: false,
			blankText: '单日累计交易金额不能为空',
			emptyText: '请输入单日累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'dayAmt',
			name: 'dayAmt',
			vtype: 'isMoney',
			width: 400
		},
		{
			fieldLabel: '单日单笔交易金额*',
			allowBlank: false,
			blankText: '单日单笔交易金额不能为空',
			emptyText: '请输入单日单笔交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'daySingle',
			name: 'daySingle',
			vtype: 'isMoney',
			width: 400
		},
		{
			fieldLabel: '单月累计交易笔数*',
			allowBlank: false,
			blankText: '单月累计交易笔数不能为空',
			emptyText: '请输入单月累计交易笔数',
			maxLength: 6,
			maskRe: /^[0-9]+$/,
			vtype: 'isOverMax',
			id: 'monNum',
			name: 'monNum',
			vtype: 'alphanum',
			width: 400
		},
		{
			fieldLabel: '单月累计交易金额*',
			allowBlank: false,
			blankText: '单月累计交易金额不能为空',
			emptyText: '请输入单月累计交易金额',
			maxLength: 22,
			maskRe: /^[0-9\\.]+$/,
			vtype: 'isOverMax',
			id: 'monAmt',
			name: 'monAmt',
			vtype: 'isMoney',
			width: 400
		}]
	});*/
	mchntStore.on('beforeload',function(){
		this.rejectChanges();
	})
	
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
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',oprGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = oprGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20305Action.asp?method=accept',
						params: {
							mchtCd: rec.get('mchtCd'),
							cardType : rec.get('cardType'),
							txnId: '20305',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,oprGrid);
							} else {
								showErrorMsg(rspObj.msg,oprGrid);
							}
							// 重新加载商户待审核信息
							oprGrid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',oprGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入审核备注',true,accept);
					}
				});
			}
		};
	
	// 拒绝按钮调用方法
	function accept(bt,text) {
		if(bt == 'ok') {
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('审核备注不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,accept);
				return;
			}
			
			if(getLength(text) > 60) {
				alert('审核备注最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入审核备注',true,accept);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = oprGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20305Action.asp?method=accept',
				params: {
					mchtCd: rec.get('mchtCd'),
					cardType : rec.get('cardType'),
					txnId: '20305',
					subTxnId: '01',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,oprGrid);
					} else {
						showErrorMsg(rspObj.msg,oprGrid);
					}
					// 重新加载商户待审核信息
					oprGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',oprGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text.replace(/[ ]/g,"")) == 0) {
				alert('拒绝原因不能为空');
				showInputMsg('提示','请输入进行该操作的原因',true,refuse);
				return;
			}
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = oprGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20305Action.asp?method=refuse',
				params: {
					mchtCd: rec.get('mchtCd'),
					cardType : rec.get('cardType'),
					txnId: '20305',
					subTxnId: '03',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,oprGrid);
					} else {
						showErrorMsg(rspObj.msg,oprGrid);
					}
					// 重新加载商户待审核信息
					oprGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	var childWin;
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	menuArr.push(acceptMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(refuseMenu);  //[2]
	menuArr.push('-');         //[3]
	menuArr.push('单位金额(人民币)：元');  

	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '商户限额信息',
		region: 'center',
		iconCls: 'cityCode',
		autoExpandColumn: 'mchtNm',
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
			msg: '正在加载商户限额信息列表......'
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
	
	oprGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = oprGrid.getSelectionModel().getSelected().data.mchtCd;
		var cardTypeQ = oprGrid.getSelectionModel().getSelected().data.cardType;
		logStore.load({
			params: {
				start: 0,
				flag:"9",
	            mchnNoQ: id,
	            cardTypeQ:cardTypeQ
			}
		});
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[0].enable();
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			labelStyle: 'padding-left: 5px',
			methodName: 'getMchntId',
			fieldLabel: '商户编号*',
			hiddenName: 'mchtCd',
			id: 'mchtCdId',
			allowBlank: true,
			editable: true,
			width: 400
    	}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 560,
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
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.findById('mchtCdId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid,logGrid],
		renderTo: Ext.getBody()
	});
});