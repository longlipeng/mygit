var batchStore;
var batchGrid;
var array; 

Ext.onReady(function(){
	batchStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblalgodtljiagua'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
			//idProperty: 'batId'
		},[
			{name: 'mchtno',mapping: 'mchtno'},
			{name: 'termid',mapping: 'termid'},
			{name: 'txnnum',mapping: 'txnnum'},
			{name: 'transdate',mapping: 'transdate'},
			{name: 'pan',mapping: 'pan'},
			{name: 'transamt',mapping: 'transamt'},
			{name: 'txnssn',mapping: 'txnssn'},
			{name: 'termssn',mapping: 'termssn'},
			{name: 'txnssn1',mapping: 'txnssn1'},
			{name: 'transTime',mapping: 'transTime'}
		]),
		autoLoad: true
	});
	
	batchStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
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
	
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		{header: '商户编号',dataIndex: 'mchtno',width: 100},
		{header: '终端号',dataIndex: 'termid',width: 80},
		{header: '交易类型',dataIndex: 'txnnum',width: 100},
		{header: '交易日期',dataIndex: 'transdate',width: 100},
		{header: '交易卡号',dataIndex: 'pan',width: 150},
		{header: '交易金额',dataIndex: 'transamt',width: 100,renderer:formatAmtTrans},
		{header: '系统参考号',dataIndex: 'txnssn',width: 100},
		{header: '终端流水号',dataIndex: 'termssn',width: 100},
		{header: '系统流水号',dataIndex: 'txnssn1',width: 100,hidden:true}
	]);
	//挂账
	var delMenu = {
			text: '解挂',
			width: 85,
			iconCls: 'edit',
			disabled: true,
			handler:function() {
			var recs= batchGrid.getSelectionModel().getSelections();
			if(recs.length == 0) {
				return;
		    }
		    array=new Array(); 
		    for(var index = 0; index < recs.length; index++) {
				var record = recs[index];
				var data = {
					flownum: record.get('txnssn'),
					transdate: record.get('transdate'),
					mchtno:record.get('mchtno'),
					txnssn1:record.get('txnssn1'),
					termid:record.get('termid'),
					transTime:record.get('transTime'),
					txnnum:record.get('txnnum'),
					transamt:record.get('transamt')
				};
				array.push(data);
			}
			showConfirm('您确定解挂吗？',batchGrid,function(bt) {
			if(bt == 'yes') {
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
			}
		
			/*var store = batchGrid.getStore();
			var len = store.getCount();*/
			
//			showProcessMsg('正在保存机构信息，请稍后......');
			//存放要修改的机构信息
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
			//showProcessMsg('正在提交信息，请稍后......');
	      Ext.Ajax.request({
				url: 'T10041Action.asp?method=update',
				method: 'post',
				params: {
					TranDataList: Ext.encode(array),
					exMsg: text,
					txnId:'10041',
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
		title: '按交易解挂：',
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
		tbar: menuArr
	});
	
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