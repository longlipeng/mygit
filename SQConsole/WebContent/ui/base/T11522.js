Ext.onReady(function(){
	//MCC数据集
	var instMccStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCC2',function(ret){
		instMccStore.loadData(Ext.decode(ret));
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var manuStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblAgentFeeCheck',
			timeout: 120000
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'//,
		//	idProperty: 'batId'
		},[
			{name: 'uuId',mapping: 'uuId'},
			{name: 'agentNo',mapping: 'agentNo'},
			{name: 'mccCode',mapping: 'mccCode'},
			{name: 'agentNm',mapping: 'agentNm'},
			{name: 'feeMin',mapping: 'feeMin'},
			{name: 'feeMax',mapping: 'feeMax'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'state',mapping: 'state'},
			{name: 'crtPer',mapping: 'crtPer'},
			{name: 'crtDate',mapping: 'crtDate'},
			{name: 'upPer',mapping: 'upPer'},
			{name: 'upDate',mapping: 'upDate'},
			{name: 'extend1',mapping: 'extend1'},
			{name: 'extend2',mapping: 'extend2'},
			{name: 'extend3',mapping: 'extend3'},
			{name: 'extend4',mapping: 'extend4'},
			{name: 'extend5',mapping: 'extend5'}
		]),
		autoLoad: true
	});
	function instMccStr(val){
		if(instMccStore.query('valueField',val).first()==undefined){
			return val;
		}else{
			return instMccStore.query('valueField',val).first().data.displayField;
		}
	}
	
	function feeType(val){
		if(val == '00'){
			return '按比例';
		}else if (val == '01'){
			return '固定值';
		}
	}
	
	var manuColModel = new Ext.grid.ColumnModel([
 		sm,
 		{header: 'UUID',dataIndex: 'uuId',width: 100,hidden:true},
 		{header: '代理商编号',dataIndex: 'agentNo',width: 100},
// 		{header: '名称',dataIndex: 'agentNm',width: 100},
 		{header : '通道',dataIndex : 'extend1',width : 150,renderer:agencyInfo},
 		{header: '上限',dataIndex: 'feeMax',width: 150},
 		{header: '下限 ',dataIndex: 'feeMin',width: 150},	 
 		{header: '费率值',dataIndex: 'feeValue',width: 100},
 		{header: '费率方式',dataIndex: 'feeType',width: 100,renderer: feeType},
 		{header: 'MCC',dataIndex:'mccCode',renderer:instMccStr},
      	{	
			header : '创建时间',
			dataIndex : 'crtDate',
			width : 120
		},{
			header : '创建人',
			dataIndex : 'crtPer',
			width : 80
		},{
			header : '审核时间',
			dataIndex : 'upDate',
			width : 120
		},{
			header : '审核人',
			dataIndex : 'upPer',
			width : 80
		},{
			header : '状态',
			dataIndex : 'state',
			width : 80,
			renderer:amtState
		}]);
	function amtState(val){
		if(val=='0'){
			return '新增待审核'; 
		} else if(val=='1'){
			return "已删除";	
		}else if(val=='2'){
			return "正常";
		}
		else if(val=='3'){
			return "修改待审核";
		}else if(val=='4'){
			return "删除待审核";
		}
	}
	
	queryConditionMebu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
	
	/** *************************查询条件************************ */

	var queryForm = new Ext.form.FormPanel( {
		frame : true,
		border : true,
		width : 600,
		autoHeight : true,
		items : [
     {
            xtype: 'dynamicCombo',
            fieldLabel: '代理商编号',
            methodName: 'getAgentNo',
            hiddenName: 'agentNo',
            id: 'idagentNo',
            displayField: 'displayField',
            valueField: 'valueField',
            parentP:'',
            width: 300
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
		},{
          xtype: 'dynamicCombo',
          fieldLabel: '费率值',
          methodName: 'getFeeValue',
          hiddenName: 'feeValue',
          id: 'idfeeValue',
          displayField: 'displayField',
          valueField: 'valueField',
          width: 300
     },{
			xtype: 'basecomboselect',
			id: 'idextend1',
			fieldLabel: '通道',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1702','翰鑫通道'],['1708','上汽通道']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'basecomboselect',
			id: 'idfeeType',
			fieldLabel: '费率方式',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['00','按比例'],['01','固定值']],
				reader: new Ext.data.ArrayReader()
			})
		},{
			xtype: 'basecomboselect',
			fieldLabel: 'MCC码类别',
			baseParams: 'MCHNT_GRP_ALL',
			id:'idmccGrp',
			hiddenName: 'mccGrp',
			editable: true,
			width: 200
		},{
	           xtype: 'dynamicCombo',
	           fieldLabel: 'MCC',
	           methodName: 'getMchntMcc',
	           hiddenName: 'mccCode',
	           id: 'idmccCode',
	           displayField: 'displayField',
	           valueField: 'valueField',
	           width: 300
	      },{
			xtype: 'panel',
			html: '</br></br>'
	}]
	});
	
	var queryWin = new Ext.Window( {
		title : '查询条件',
		layout : 'fit',
		width : 600,
		autoHeight : true,
		items : [ queryForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true,
		animateTarget : 'query',
		tools : [{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10, Ext.getBody().getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				} ],
		buttons : [ {
			text : '查询',
			handler : function() {
			if(queryForm.getForm().isValid()){
        		manuStore.load();
        	}
		}
			
		}, {
			text : '清除查询条件',
			handler : function() {
				queryForm.getForm().reset();
			}
		} ]
	});
	
	manuStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			
			agentNo: queryForm.getForm().findField('idagentNo').getValue(),
			mccCode: queryForm.getForm().findField('idmccCode').getValue(),
			/*feeMin: queryForm.getForm().findField('idfeeMin').getValue(),
            feeMax: queryForm.getForm().findField('idfeeMax').getValue(),*/
			mccGrp: queryForm.getForm().findField('idmccGrp').getValue(),
			extend1: queryForm.getForm().findField('idextend1').getValue(),
            feeValue: queryForm.getForm().findField('idfeeValue').getValue(),
            feeType: queryForm.getForm().findField('idfeeType').getValue(),
            state_query:queryForm.findById('idstate').getValue()
		});
	});
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',manuGrid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						var modifyrecords = manuGrid.getSelectionModel().getSelections();
						if(modifyrecords.length == 0){
							return;
						}
						var array = new Array();
						for(var index = 0;index <modifyrecords.length;index++ ){
							var record = modifyrecords[index];
							var data = {
									uuId : record.get('uuId')
							};
							array.push(data);
						}
						Ext.Ajax.request({
							url: 'T11522Action.asp?method=accept',
							params: {
							   infList: Ext.encode(array),
								txnId: '11522',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,manuGrid);
								} else {
									showErrorMsg(rspObj.msg,manuGrid);
								}
								// 重新加载待审核信息
								manuGrid.getStore().reload();
							}
						});
						hideProcessMsg();
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
						showInputMsg('提示','请输入拒绝原因',true,refuse);
					}
				});
			}
		};
		
		// 拒绝按钮调用方法
		function refuse(bt,text) {
			if(bt == 'ok') {
				if(getLength(text)== null || getLength(text) == ''){
					alert('拒绝原因不能为空');
					showInputMsg('提示','请输入拒绝原因',true,refuse);
					return;
				}
				if(getLength(text) > 60) {
					alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入拒绝原因',true,refuse);
					return;
				}
				showProcessMsg('正在提交审核信息，请稍后......');
				var modifyrecords = manuGrid.getSelectionModel().getSelections();
				if(modifyrecords.length == 0){
					return;
				}
				var array = new Array();
				for(var index = 0;index <modifyrecords.length;index++ ){
					var record = modifyrecords[index];
					var data = {
							uuId : record.get('uuId')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T11522Action.asp?method=refuse',
					params: {
					   infList: Ext.encode(array),
					   txnId: '11522',
					   subTxnId: '02',
					   refuseInfo: text
					},
				/*rec = grid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T11016Action.asp?method=refuse',
					params: {
					    id: rec.get('id'),
						txnId: '11002',
						subTxnId: '02',
						refuseInfo: text
					},*/
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,manuGrid);
						} else {
							showErrorMsg(rspObj.msg,manuGrid);
						}
						// 重新加载待审核信息
						manuGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
	var menuarr = new Array();
	menuarr.push(queryConditionMebu);
	menuarr.push('-');
	menuarr.push(acceptMenu);
	menuarr.push('-');
	menuarr.push(refuseMenu);

	manuGrid = new Ext.grid.EditorGridPanel({
		title: '代理商成本费率信息',
		region:'center',
		iconCls: 'T301',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: manuStore,
		tbar: menuarr,
		cm: manuColModel,
		sm: sm,
		clicksToEdit: true,
		forceValidation: true,
		bbar: new Ext.PagingToolbar({
			store: manuStore,
	//		pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 20,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	manuGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(manuGrid.getView().getRow(manuGrid.getSelectionModel().last)).frame();
		manuGrid.getTopToolbar().items.items[2].enable();
		manuGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [manuGrid],
		renderTo: Ext.getBody()
	});
	
	
});