var batchStore;
var batchGrid;
var array; 

Ext.onReady(function(){
	   
	batchStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblalgodtl',
			timeout : 600000000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
			//idProperty: 'batId'
		},[
			{name: 'mchtno',mapping: 'mchtno'},
			{name: 'mchtnm',mapping: 'mchtnm'},
			{name: 'termid',mapping: 'termid'},
			{name: 'txnnum',mapping: 'txnnum'},
			{name: 'transdate',mapping: 'transdate'},
			{name: 'pan',mapping: 'pan'},
			{name: 'transamt',mapping: 'transamt'},
			{name: 'txnssn',mapping: 'txnssn'},
			{name: 'termssn',mapping: 'termssn'},
			{name: 'txnssn1',mapping: 'txnssn1'},
			{name: 'transtime',mapping: 'transtime'}
		]),
		autoLoad: true
	});
	
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		{header: '商户编号',dataIndex: 'mchtno',width: 100},
		{header: '商户名称',dataIndex: 'mchtnm',width: 120},
		{header: '终端号',dataIndex: 'termid',width: 80},
		{header: '交易类型',dataIndex: 'txnnum',width: 80},
		{header: '交易金额',dataIndex: 'transamt',width: 100,renderer:formatAmtTrans},
		{header: '交易日期',dataIndex: 'transdate',width: 100},
		{header: '交易卡号',dataIndex: 'pan',width: 130},
		{header: '系统参考号',dataIndex: 'txnssn',width: 100},
		{header: '终端流水号',dataIndex: 'termssn',width: 90},
		{header: '系统流水号',dataIndex: 'txnssn1',width: 90}
	]);
	//挂账
	var delMenu = {
		text: '挂账',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			showConfirm('您确定挂账吗？',batchGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				}
				/*var store = batchGrid.getStore();
				var len = store.getCount();
				
				showProcessMsg('正在保存机构信息，请稍后......');
				存放要修改的机构信息*/
			});
			
			var recs = batchGrid.getSelectionModel().getSelections();
			if(recs.length == 0) {
				return;
		    }
		    array = new Array(); 
		    for(var index = 0; index < recs.length; index++) {
				var record = recs[index];
				var data = {
					flownum: record.get('txnssn'),
					transdate: record.get('transdate'),
					mchtno:record.get('mchtno'),
					txnssn1:record.get('txnssn1'),
					termid:record.get('termid'),
					transTime:record.get('transtime'),
					txnnum:record.get('txnnum'),
					transamt:record.get('transamt')
				};
				array.push(data);
			}
		    
		}
	};
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			//showProcessMsg('正在提交信息，请稍后......');
	      Ext.Ajax.request({
				url: 'T10020Action.asp?method=add',
				method: 'post',
				params: {
					TranDataList: Ext.encode(array),
					exMsg: text,
					txnId:'10020',
					subTxnId: '01'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,batchGrid);
						batchGrid.getTopToolbar().items.items[0].disable();
					} else {
						showErrorMsg(rspObj.msg,batchGrid);
					}
					// 重新加载信息
					batchGrid.getStore().reload();
					hideProcessMsg();
				}
			});	
		}
	}
	var menuArr = new Array();
    menuArr.push(delMenu);
    
    batchGrid = new Ext.grid.GridPanel({
		title: '挂账：',
		region: 'center',
		iconCls: 'T80101',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: batchStore,
		cm: colModel,
		sm: new Ext.grid.CheckboxSelectionModel ({singleSelect: false}),
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载挂账列表......'
		}
//		,bbar: new Ext.PagingToolbar({
//			store: batchStore,
//			pageSize: System[QUERY_RECORD_COUNT],
//			displayInfo: true,
//			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
//			emptyMsg: '没有找到符合条件的记录'
//		})
	});

	batchStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtno: mchtno,
			termid:termid,
			txnnum:txnnum,
			transdatestart:transdatestart,
			transdateend:transdateend,
			pan:pan,
			transamtsmall:transamtsmall,
			transamtbig:transamtbig,
			txnssn:txnssn,
			termssn:termssn
		});
	})
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [batchGrid],
		renderTo: Ext.getBody()
	});
	batchGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			batchGrid.getTopToolbar().items.items[0].enable();
		}
	});
})