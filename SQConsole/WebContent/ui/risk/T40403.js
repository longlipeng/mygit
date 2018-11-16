Ext.onReady(function() {
	//事前风险控制数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskbeforeshenhe'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mcht_Nm'
		},[
			{name: 'mcht_Nm',mapping: 'mcht_Nm'},
			{name: 'LICENSE_NO',mapping: 'LICENSE_NO'},
			{name: 'ORG_CODE',mapping: 'ORG_CODE'},
			{name: 'IDENTITY',mapping: 'IDENTITY'},
			{name: 'score',mapping: 'score'},
			{name: 'grade',mapping: 'grade'},
			{name: 'reserve',mapping: 'reserve'},
			{name: 'sastatue',mapping: 'sastatue'}
		])
	});

	var mchntColModel = new Ext.grid.ColumnModel([
	    {id: 'mcht_Nm',header: '商户名称',dataIndex: 'mcht_Nm',width: 200,sortable: true},
	    {header: '营业执照号',dataIndex: 'LICENSE_NO',width: 100},
	    {header: '组织机构代码',dataIndex: 'ORG_CODE',width: 100},
	    {header: '法人身份证号码',dataIndex: 'IDENTITY',width: 120},
		{header: '商户得分',dataIndex: 'score',width: 70},
	    {header: '商户风险等级',dataIndex: 'grade',width: 100},
	    {header: '商户风险描述',dataIndex: 'reserve',width: 700},
	    {header: '审核状态',dataIndex: 'sastatue',width: 100,renderer: riskInfoState}
	]);
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable()},2000);
			showRiskBeforeDetailS(mchntGrid.getSelectionModel().getSelected().get('mcht_Nm'),mchntGrid)
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

	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
				showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showProcessMsg('正在提交审核信息，请稍后......');
						rec = mchntGrid.getSelectionModel().getSelected();
						Ext.Ajax.request({
							url: 'T40403Action.asp?method=accept',
							params: {
								mcht_Nm: rec.get('mcht_Nm'),
								txnId: '40403',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,mchntGrid);
								} else {
									showErrorMsg(rspObj.msg,mchntGrid);
								}
								// 重新加载终端待审核信息
								mchntGrid.getStore().reload();
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
				showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
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
				rec = mchntGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T40403Action.asp?method=refuse',
					params: {
					    mcht_Nm: rec.get('mcht_Nm'),
						txnId: '40403',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,mchntGrid);
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
						// 重新加载事前风险待审核信息
						mchntGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
		
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	menuArr.push('-');
	menuArr.push(acceptMenu);	
	menuArr.push('-');
	menuArr.push(refuseMenu);
	var mchntGrid = new Ext.grid.GridPanel({
		title: '事前风险信息审核',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载事前风险信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.agenid

	});
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[4].enable();
			mchntGrid.getTopToolbar().items.items[6].enable();
		}
	});

	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		labelWidth: 80,
		items: [{
//			xtype: 'dynamicCombo',
//			methodName: 'getMchntNameAll',
//			hiddenName: 'mchtNm',
			id: 'mchtname',
			xtype: 'textfield',
			name: 'mchtname',
			maxLength: 100,
			fieldLabel: '商户名称',
			width: 250
		},{
			xtype: 'basecomboselect',
			id: 'idgrade',
			fieldLabel: '风险等级',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','低风险'],['2','一般风险'],['3','风险商户'],['4','较高风险'],['5','限制商户']],
				reader: new Ext.data.ArrayReader()
			})
		},{  
//        	columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	items:[{
                xtype: 'datefield',
                fieldLabel: '起始日期',
                format : 'Y-m-d',
                hiddenName :'startDate',
                id :'startDate',
                listeners: {
    				'select': function() {
    					if(Ext.getCmp('endDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
    						showAlertMsg("起始日期不能大于结束日期，请重新选择！",queryForm);
    						Ext.getCmp('startDate').setValue('');
    					}
    				}
    			}
        	}]
        },{ 
//        	columnWidth: .5,
        	xtype: 'panel',
        	layout: 'form',
        	items:[{
                xtype: 'datefield',
                fieldLabel: '结束日期',
                format : 'Y-m-d',
                hiddenName :'endDate',
                id :'endDate',
                listeners: {
    				'select': function() {
    					if(Ext.getCmp('startDate').getValue()!='' && (Ext.getCmp('startDate').getValue() > Ext.getCmp('endDate').getValue())){
    						showAlertMsg("结束日期不能小于起始日期，请重新选择！",queryForm);
    						Ext.getCmp('endDate').setValue('');
    					}
    				}
    			}
        	}]
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
				var startDateTemp = queryForm.findById('startDate').getValue();
				var endDateTemp = queryForm.findById('endDate').getValue();
			
		    	if(queryForm.findById('startDate').getValue()!=null && queryForm.findById('startDate').getValue()!=''){
		    		startDateTemp = queryForm.findById('startDate').getValue().format('Ymd');
				}
		    	if(queryForm.findById('endDate').getValue()!=null && queryForm.findById('endDate').getValue()!=''){
		    		endDateTemp = queryForm.findById('endDate').getValue().format('Ymd');
				}
				mchntStore.load({
				params : {
					start : 0,
					mchtname: queryForm.getForm().findField('mchtname').getValue(),
					grade: queryForm.findById('idgrade').getValue(),
					startDate:startDateTemp,
					endDate:endDateTemp
					}
				});
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		var startDateTemp = queryForm.findById('startDate').getValue();
		var endDateTemp = queryForm.findById('endDate').getValue();
	
    	if(queryForm.findById('startDate').getValue()!=null && queryForm.findById('startDate').getValue()!=''){
    		startDateTemp = queryForm.findById('startDate').getValue().format('Ymd');
		}
    	if(queryForm.findById('endDate').getValue()!=null && queryForm.findById('endDate').getValue()!=''){
    		endDateTemp = queryForm.findById('endDate').getValue().format('Ymd');
		}
		Ext.apply(this.baseParams, {
			start: 0,
			mchtname: queryForm.getForm().findField('mchtname').getValue(),
			grade: queryForm.findById('idgrade').getValue(),
			startDate:startDateTemp,
			endDate:endDateTemp
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});