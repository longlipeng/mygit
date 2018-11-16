var batchStore;
var batchGrid;
var task = {
	run: function() {
		batchStore.reload();
	},
	interval: 2000
};

function missionConsole(value){
	/*if("B0004"==value){
				Ext.TaskMgr.stopAll();	
			}*/
	Ext.get(value).hide();
	HandleOfBatch.sendMsg(value,predate,date,function(ret){
		if("S"==ret.substr(0,1)){
			if("K0005"==value){
				showSuccessAlert("启动任务成功！"+ret.substr(2),batchGrid);
			}else{
				showSuccessMsg("启动任务成功！",batchGrid);
			}
			/*if("B0004"==value){
				batchStore.reload();
			}
			else{
				Ext.TaskMgr.start(task);
			}*/
		}else{
			showErrorMsg(ret,batchGrid);
			/*if("B0004"==value){
				batchStore.reload();
			}*/
		}
		Ext.get(value).show();
		batchStore.reload();
	});
}

Ext.onReady(function(){
	//对账任务启动控制中心
	batchStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=batchMission'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'batId'
		},[
			{name: 'batId',mapping: 'batId'},
			{name: 'batDsp',mapping: 'batDsp'},
			{name: 'batState',mapping: 'batState'},
			{name: 'batNote',mapping: 'batNote'},
			{name: 'oprate',mapping: 'oprate'}
		]),
		autoLoad: true
	});
	
	batchStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			date: date
		});
	});
	
	function oprate(val,metadata,record,rowIndex){
		if(val == "0" || val == "3"){
                var value = record.get('batId');
				return "<button class=btn_2k3 id="+value+" onclick='javascript:missionConsole(this.id)'>启动任务</button>";
			}
	}
	
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '任务编号',dataIndex: 'batId',width: 100},
		{header: '任务描述',dataIndex: 'batDsp',width: 150,id: 'batDsp'},
		{header: '运行状态',dataIndex: 'batState',width: 100,renderer:function(val){
			if("0" == val){return "未运行"}
			else if("1" == val){return "运行中"}
			else if("2" == val){return "<font color='#00FF00'>运行成功</font>"}
			else if("3" == val){return "<font color='red'>运行失败</font>"}
			else return val;
		}},
		/*{header: '任务备注',dataIndex: 'batNote',width: 500},*/
		{header: '操作',dataIndex: 'oprate',width: 100,renderer:oprate}
	]);
	
	var menuArr = new Array();
	
	var refresh = {
			text : '刷新',
			width : 85,
			id : 'query',
		//  iconCls : 'query',
			handler : function() {
				batchStore.load();
			}
		};
	menuArr.add('-');
	menuArr.add(refresh);
	menuArr.add('-');
	
	batchGrid = new Ext.grid.GridPanel({
		title: '对账：' + date,
		region: 'center',
		iconCls: 'T80101',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'batDsp',
		stripeRows: true,
		store: batchStore,
		tbar:menuArr,
		cm: colModel,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		clicksToEdit: true,
		forceValidation: true
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [batchGrid],
		renderTo: Ext.getBody()
	});
	
});