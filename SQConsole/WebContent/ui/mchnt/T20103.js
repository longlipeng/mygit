Ext.onReady(function() {
	
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'mchntNm',mapping: 'mchntNm'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '退回/拒绝时间',dataIndex: 'txnTime',sortable: true,width: 150,renderer: formatTs},
		{header: '商户编号',dataIndex: 'mchntId',width: 150},
		{header: '商户名称',dataIndex: 'mchntNm',width: 150},
		{header: '交易机构',dataIndex: 'brhId',width: 100},
		{header: '交易操作员',dataIndex: 'oprId'},
		{header: '交易类型',dataIndex: 'refuseType',width: 150},
		{header: '退回/拒绝原因',dataIndex: 'refuseInfo',width: 200,id:'refuseInfo'}
	]);
	
	var resumMcht = {
		text: '恢复商户',
		width: 85,
		id: 'resume',
		iconCls: 'resume',
		handler:function() {
			 var rec = grid.getSelectionModel().getSelected();
			 if(rec.get('refuseType')=='新增审核退回'){
			 	showConfirm('确定要恢复该条记录吗？', grid, function(bt) {
				// 如果点击了提示的确定按钮
					if (bt == "yes") {
						T20101.queryMchntStatus(rec.get('mchntId'),function(ret){
							if("2" == ret){
								window.location.href = Ext.contextPath + '/page/mchnt/T2010302.jsp?mchntId='+rec.get('mchntId');
								grid.getStore().reload();
				 			}else{
				 				showMsg('您所要恢复的商户当前不是新增退回状态，不可恢复',grid);
				 			}
		                });
					}
				});
			 }else{
			 	showMsg('您所要恢复的商户当前不是新增审核状态，不可恢复',grid);
			 }
		}
	};
	
	var menuArr = new Array();
	menuArr.push(resumMcht);  //[0]
	
	var grid = new Ext.grid.GridPanel({
		title: '商户拒绝原因查询',
		region: 'center',
		iconCls: 'T20103',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载商户退回（拒绝）信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			 var rec = grid.getSelectionModel().getSelected();
			 if(rec.get('refuseType')=='新增审核退回'){
			 	grid.getTopToolbar().items.items[0].enable();
			 }else{
			 	grid.getTopToolbar().items.items[0].disable();
			 }
		}
	});
/*	
	grid.on({
		'rowdblclick':function(){
			 var rec = grid.getSelectionModel().getSelected();
//			 if(e.record.get('saState')=='4'||e.record.get('saState')=='1'){
//					e.cancel=true;
//			 }
			 if(rec.get('refuseType')=='新增审核拒绝'){
			 	showConfirm('确定要恢复该条记录吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
							if (bt == "yes") {
								T20101.queryMchntStatus(rec.get('mchntId'),function(ret){
									if("2" == ret){
										window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+rec.get('mchntId');
										grid.getStore().reload();
						 			}else{
						 				showMsg('您所要恢复的商户当前不是新增拒绝状态，不可恢复',grid);
						 			}
				                });
//								window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+rec.get('mchntId');
//								grid.getStore().reload();
								
//								Ext.Ajax.requestNeedAuthorise( {
//									url : 'T201001Action.asp?method=recover',
//									success : function(rsp, opt) {
//										var rspObj = Ext.decode(rsp.responseText);
//										if (rspObj.success) {
//											showSuccessMsg(rspObj.msg, grid);
//											grid.getStore().reload();
//										} else {
//											showErrorMsg(rspObj.msg, grid);
//										}
//									},
//									params : {
//										mchtId : rec.get('mchntId'),
//										txnId : '201001',
//										subTxnId : '02'
//									}
//								});
							}
						});
			 }
		}
	});*/
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});