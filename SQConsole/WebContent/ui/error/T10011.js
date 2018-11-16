 
Ext.onReady(function(){
	var sm = new Ext.grid.CheckboxSelectionModel();
 var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=manualReturnCheck',
			timeout: 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtReturn',mapping: 'amtReturn'},
			{name: 'saState',mapping: 'saState'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'createDate',mapping: 'createDate'},
			{name: 'ID',mapping: 'ID'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'returnFee',mapping: 'returnFee'},
			{name: 'sysSsn',mapping: 'sysSsn'},
			{name: 'srcId',mapping:'srcId'},
			{name: 'potalSsn',mapping:'potalSsn'},
			{name: 'rspCode',mapping:'rspCode'}
		]),
		autoLoad: true
	});
 
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			saState:'0' 
		});
	})
	var manuColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '原交易日期',dataIndex: 'instDate',width: 100,renderer:formatTs},
//		{header: '原交易类型',dataIndex: 'txnNum',width: 80,renderer:funType},
		{header: '商户编号',dataIndex: 'mchtNo',width: 200, renderer:function(val){return getRemoteTrans(val, "mchtIdName");}},
		{header: '终端号',dataIndex: 'termId',width: 100},
		{header: '原交易卡号',dataIndex: 'pan',width: 150},	 
		{header: '参考号',dataIndex: 'retrivlRef',width: 100},
		{header: '退货手续费',dataIndex: 'returnFee',width: 100},
		{header: '退货金额/元',dataIndex:'amtReturn'},
		{header: '退货状态',dataIndex:'saState',renderer:amtState},
		{header: '操作员',dataIndex:'oprId'},
		{header: '退货时间',dataIndex:'createDate',width: 150,renderer:formatTs},
		{header: '流水号',dataIndex:'sysSsn',width: 150},
		{header: '退货渠道',dataIndex: 'srcId',width: 100 ,renderer:srcIdFormat},
		{header: '商城退货流水号',dataIndex: 'potalSsn',width: 100 ,renderer:potalSsnFormat},
		{header: '退货应答码',dataIndex: 'rspCode',width: 100 }
		
	]);
 	function srcIdFormat(val) {
		if(val=='1901'){
			return '控制台手工退货'; 
		}else if(val=='2901'){
			return "商城退货";
		}
	}
     function amtState(val){
		if(val=='0'){
			return '新增待审核'; 
		} else if(val=='1'){
			return "退货成功";
		}else if(val=='2'){
			return "修改待审核";
		}else if(val=='3'){
			return "删除待审核";
		}else if(val=='4'){
			return "审核拒绝";	
		}else if(val=='5'){
			return "后台退货成功，前台处理错误";
		}else if(val=='6'){
			return "退货失败";
		}
	}
	function potalSsnFormat(val) {
		if(!isNaN(val)){
   			return val;
		}else{
		   return "";
		}
	}
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',manuGrid,function(bt) {
				if(bt == 'yes') {
					//showProcessMsg('正在提交审核信息，请稍后......');
					showMask("正在提交审核信息，请稍后....",manuGrid);
					var modifyrecords = manuGrid.getSelectionModel().getSelections();
					if(modifyrecords.length == 0){
						return;
					}
					var array = new Array();
					for(var index = 0;index <modifyrecords.length;index++ ){
						var record = modifyrecords[index];
						var data = {
								ID : record.get('ID'),
						   instDate: record.get('instDate'),//原交易时间 INST_DATE
						 retrivlRef: record.get('retrivlRef'),//参考号 RETRIVL_REF
						     sysSsn: record.get('sysSsn'),//流水号  SYS_SEQ_NUM
						     termId: record.get('termId')//终端号    CARD_ACCP_TERM_ID
						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T10011Action.asp?method=accept',
						params: {
							 infList: Ext.encode(array),
							txnId: '10011',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,manuGrid);
							} else {
								showErrorMsg(rspObj.msg,manuGrid);
							}
							// 重新加载商户待审核信息
							manuGrid.getStore().reload();
						},
						failure: function(){
							hideMask();
						}
					});
					//hideProcessMsg();
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',manuGrid,function(bt) {
				if(bt == 'yes') {
//					showInputMsg('提示','请输入拒绝原因',true,refuse);
					showMask("正在提交审核信息，请稍后....",manuGrid);
					var modifyrecords = manuGrid.getSelectionModel().getSelections();
					if(modifyrecords.length == 0){
						return;
					}
					var array = new Array();
					for(var index = 0;index <modifyrecords.length;index++ ){
						var record = modifyrecords[index];
						var data = {
								ID : record.get('ID'),
						 instDate: record.get('instDate'),//原交易时间 INST_DATE
						 retrivlRef: record.get('retrivlRef'),//参考号 RETRIVL_REF
						 sysSsn: record.get('sysSsn'),//流水号  SYS_SEQ_NUM
						 termId: record.get('termId')//终端号    CARD_ACCP_TERM_ID

						};
						array.push(data);
					}
					Ext.Ajax.request({
						url: 'T10011Action.asp?method=refuse',
						params: {
							infList: Ext.encode(array),
							txnId: '10011',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,manuGrid);
							} else {
								showErrorMsg(rspObj.msg,manuGrid);
							}
							manuGrid.getStore().reload();
						},
						failure: function(){
							hideMask();
						}
					});
					//hideProcessMsg();
				
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt/*,text*/) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			//showProcessMsg('正在提交审核信息，请稍后......');
			showMask("正在提交审核信息，请稍后....",manuGrid);
			rec = manuGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T10011Action.asp?method=refuse',
				params: {
					ID: rec.get('ID'),
					txnId: '10011',
					subTxnId: '02',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					hideMask();
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,manuGrid);
					} else {
						showErrorMsg(rspObj.msg,manuGrid);
					}
					manuGrid.getStore().reload();
				},
				failure: function(){
					hideMask();
				}
			});
			//hideProcessMsg();
		}
	}
	var menuArr = new Array();
	menuArr.push(acceptMenu);
	menuArr.push('-');
	menuArr.push(refuseMenu)
	
	manuGrid = new Ext.grid.EditorGridPanel({
		title: '退货待审核信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		tbar: menuArr,
		cm: manuColModel,
//		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm: sm,
		clicksToEdit: true,
		forceValidation: true,
		bbar: new Ext.PagingToolbar({
			store: manuStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将按钮屏蔽
	manuGrid.getStore().on('beforeload',function() {
		manuGrid.getTopToolbar().items.items[0].disable();
		manuGrid.getTopToolbar().items.items[2].disable();
		//manuGrid.getStore().rejectChanges();
	});
	
	manuGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(manuGrid.getView().getRow(manuGrid.getSelectionModel().last)).frame();
		manuGrid.getTopToolbar().items.items[0].enable();
		manuGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
	
})