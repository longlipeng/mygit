var grid;
var actNo;
Ext.onReady(function() {
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'actNoQ',
                name: 'actNoQ',
                fieldLabel: '活动编号'
            }),new Ext.form.TextField({
                id: 'actNameQ',
                name: 'actNameQ',
                fieldLabel: '活动名称'
            }),{
				width: 130,
				xtype: 'datefield',
				fieldLabel: '起始时间*',
				allowBlank: false,
				format : 'Ymd',
				name :'startDateQ',
				id :'startDateQ'
		  },{											
				width: 130,
				xtype: 'datefield',
				fieldLabel: '截止时间*',
				allowBlank: false,
				format : 'Ymd',
				name :'endDateQ',
				id :'endDateQ'
		   }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                actStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=marketAct'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'state',mapping: 'state'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'actCycle',mapping: 'actCycle'},
			{name: 'organNo',mapping: 'organNo'},
			{name: 'organType',mapping: 'organType'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'remarks',mapping: 'remarks'},
			{name: 'flag01',mapping: 'flag01'},
			{name: 'crtDt',mapping: 'crtDt'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'updDt',mapping: 'updDt'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'recOpr',mapping: 'recOpr'},
			{name: 'misc1',mapping: 'misc1'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            state: 1,
            actNo: Ext.getCmp('actNoQ').getValue(),
            actName: Ext.getCmp('actNameQ').getValue(),
            startDate: topQueryPanel.getForm().findField('startDateQ').getValue(),
            endDate: topQueryPanel.getForm().findField('endDateQ').getValue()
        });
    }); 
	
	actStore.load();
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>新增柜员：{crtOpr}</p>',
			'<p>新增时间：{crtDt}</p>',
			'<p>修改柜员：{updOpr}</p>',
			'<p>修改时间：{updDt}</p>',
			'<p>审核柜员：{recOpr}</p>',
			'<p>审核时间：{misc1}</p>'
		)
	});
	
	var actColModel = new Ext.grid.ColumnModel([
	rowExpander,
		{header: '所属机构',dataIndex: 'bankNo',width: 80},
		{id: 'actNo',header: '活动编号',dataIndex: 'actNo',width: 100},
		{header: '活动名称',dataIndex: 'actName',id: 'actName'},
		{header: '操作',dataIndex: 'flag01',renderer: actOprState,width: 80},
		{header: '活动状态',dataIndex: 'state',renderer: actState},
		{header: '起始日期',dataIndex: 'startDate'},
		{header: '截止日期',dataIndex: 'endDate'},
		{header: '自动清算单位编号',dataIndex: 'organNo',width: 120},
		{header: '自动清算业务种类编号',dataIndex: 'organType',width: 120}
	]);
	
	


	
	
	var editMenu = {
		text: '审核',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
            var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            actNo = selectedRecord.get('actNo');	
            if(selectedRecord.get('flag01') == '0')
            {
            	addWin(selectedRecord);
            }else if(selectedRecord.get('flag01') == '1'
            		||selectedRecord.get('flag01') == '2') {
            
           	 	updateWin(selectedRecord,selectedRecord.get('actNo'));
            }
		}
	}
   
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
    
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    
    var refuseMenu = {
    	text: '拒绝',
    	width: 85,
    	id: 'refuse',
    	iconCls: 'refuse',
    	disabled: true,
		handler: function() {
		   var record = grid.getSelectionModel().getSelected();
           if(record == null)
           {
           		showAlertMsg("请选择要拒绝的记录",activityForm);
           		return;
           }
           showConfirm('确定要拒绝这条营销活动吗？',grid,function(bt) {
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T70204Action_refuse.asp',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[1].disable();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								actNo: record.get('actNo'),
								txnId: '70204',
								subTxnId: '03'
							}
						}
						);
					}
				});
		}
    };
    
	var menuArr = new Array();
	
	menuArr.push(queryMenu);	//[0]
	menuArr.push(editMenu);		//[1]
	menuArr.push(refuseMenu);	//[2]
	
	// 终端信息列表
	grid = new Ext.grid.GridPanel({
		title: '商户营销活动审核',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: actColModel,
		autoExpandColumn: 'actName',
		clicksToEdit: true,
		plugins: rowExpander,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载营销活动信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: 200,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('state') == '1' ) {
				grid.getTopToolbar().items.items[1].enable();
				grid.getTopToolbar().items.items[2].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
				grid.getTopToolbar().items.items[2].disable();
			}
		}
	});
   
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})

function addWin(record){
	
	/**************  商户营销活动表单  *********************/
	var actForm = new Ext.form.FormPanel({
		frame: true,
		height: 300,
		width: 460,
		labelWidth: 150,
		region: 'west',
		waitMsgTarget: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '所属分行*',
			id: 'bankNoU',
			hiddenName: 'bankNo',
			readOnly: true
        },{
		    xtype: 'textfield',
			fieldLabel: '商业营销活动编号*',
			id: 'actNo',
			name: 'actNo',
			allowBlank: false,
			readOnly: true
		},{	
			xtype: 'textfield',
			fieldLabel: '商业营销活动名称*',
			id: 'actName',
			name: 'actName',
			allowBlank: false,
			readOnly: true
		},{
			xtype: 'combo',
			fieldLabel: '状态*',
			allowBlank: false,
			hiddenName :'state',
			id :'stateUpd',
			displayField: 'displayField',
           valueField: 'valueField',
			store: new Ext.data.ArrayStore({
		       fields: ['valueField','displayField'],
		       data: [['0','正常'],['1','未审核'],['2','关闭']]
		   }),
			readOnly: true
		},{
		   xtype: 'datefield',
			fieldLabel: '活动起始时间*',
			allowBlank: false,
			format: 'Ymd',
			name :'startDateChk',
			id :'startDateChk'
		},{
			xtype: 'datefield',
			fieldLabel: '活动截止时间*',
			allowBlank: false,
			format: 'Ymd',
			name :'endDateChk',
			id :'endDateChk'
		},{
	       xtype: 'textfield',
	       fieldLabel: '自动清算单位编号 *',
	       id: 'organNoChk',
	       name: 'organNoChk',
	       allowBlank: false,
	       maxLength: 8,
	       vtype: 'isNumber'
	    },{
	       xtype: 'textfield',
	       fieldLabel: '自动清算业务种类编号* ',
	       id: 'organTypeChk',
	       name: 'organTypeChk',
	       allowBlank: false,
	       maxLength: 4,
	       vtype: 'isNumber'
	    },{
	    	width: 200,
	        xtype: 'combo',
		    fieldLabel: '营销活动算法',
		    id: 'actContentA',
		    hiddenName: 'actContent',
		    readOnly: true,
	        store: new Ext.data.ArrayStore({
	            fields: ['valueField','displayField'],
	            data: [['0','算法1：应收商户手续费*费率'],['1','算法2：应付银联手续费*费率'],['2','算法3：手续费净额*费率']]
	        })
	         },{	
		     	xtype: 'textarea',
				fieldLabel: '备注描述',
				id: 'remarks',
				name: 'remarks',
				width: 250,
				maxLength: 40,
		 		readOnly: true
				}]
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'mchntNo',header: '商户编号',dataIndex: 'mchntNo',width: 100},
		{header: '商户名称',dataIndex: 'mchtNm',width: 150},
		{header: '活动名称',dataIndex: 'actName'},
		{header: '费率',dataIndex: 'checkFee',editor: new Ext.form.NumberField({
		 	allowBlank: false,
			emptyText: '费率不能为空',
			maxValue: 100
		 })}
	]);
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntParticipat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'state',mapping: 'state'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'actFee',mapping: 'actFee'},
			{name: 'flag01',mapping: 'flag01'}
		])
	});
	
	mchntStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            flag: 0,
            actNo: actNo
        });
    }); 
	
	var mchntGrid = new Ext.grid.EditorGridPanel({
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载参与营销活动的商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: 200,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	/***********  商户营销活动窗口  *****************/
	var actWin = new Ext.Window({
		title: '商户营销活动审核',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 1000,
		height: 400,
		layout: 'border',
        autoScroll:true,
		items: [actForm,{
			xtype: 'panel',
			region: 'center',
			layout: 'border',
			items:[mchntGrid]
		}],
		buttonAlign: 'center',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '审核',
			handler: function() {
				var selectedRecord = grid.getSelectionModel().getSelected();
				var mchntNos = new Array();
				var actFees = new Array();
				var subTxnId;
				var url;
				if(selectedRecord.get('flag01') == '0')
				{
					subTxnId = '01';
					url = 'T70204Action_check.asp';
					for(var i=0;i<mchntStore.getTotalCount();i++)
					{
						var tmp = mchntStore.getAt(i);
						mchntNos[i] = tmp.get('mchntNo');
						actFees[i] = tmp.get('checkFee');
					}
				}
				else
				{
					subTxnId = '02';
					url = 'T70204Action_modifyCheck.asp';
				}
				if(actForm.getForm().isValid()) {
					actForm.getForm().submitNeedAuthorise({
						url: url,
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,grid);
							grid.getStore().reload();
							actForm.getForm().reset();
                            actWin.close();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '70204',
							subTxnId: subTxnId,
							mchntNos: mchntNos,
							actFees: actFees
						}
					});
				}
			}
		}]
	});
	actForm.getForm().loadRecord(record);
	mchntStore.load();
	actWin.show();
	
}
function updateWin(record,actNo,mchntNo){
	
	
	var actForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		labelWidth: 150,
		waitMsgTarget: true,
		region: 'west',
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '所属分行',
			id: 'bankNoU',
			hiddenName: 'bankNo',
			readOnly: true
	      },{	
			    xtype: 'textfield',
				fieldLabel: '商业营销活动编号*',
				id: 'actNo',
				name: 'actNo',
				allowBlank: false,
				readOnly: true
			},{	
			    xtype: 'textfield',
				fieldLabel: '商业营销活动名称*',
				id: 'actName',
				name: 'actName',
				allowBlank: false,
				readOnly: true
			},{
				xtype: 'combo',
				fieldLabel: '状态*',
				allowBlank: false,
				hiddenName :'state',
				id :'stateUpd',
				displayField: 'displayField',
	         	valueField: 'valueField',
				store: new Ext.data.ArrayStore({
		         	fields: ['valueField','displayField'],
		         	data: [['0','正常'],['1','未审核'],['2','关闭']]
		        }),
				readOnly: true
			},{	
			    xtype: 'datefield',
				fieldLabel: '活动起始时间*',
				allowBlank: false,
				format: 'Ymd',
				name :'startDate',
				id :'startDate',
				readOnly: true
			},{
				xtype: 'datefield',
				fieldLabel: '活动截止时间*',
				allowBlank: false,
				format: 'Ymd',
				name :'endDateChk',
				id :'endDateChk'
			},{
	             xtype: 'textfield',
	             fieldLabel: '自动清算单位编号 *',
	             id: 'organNoChk',
	             name: 'organNoChk',
	             allowBlank: false,
	             maxLength: 8,
	             vtype: 'isNumber'
	          },{
	             xtype: 'textfield',
	             fieldLabel: '自动清算业务种类编号* ',
	             id: 'organTypeChk',
	             name: 'organTypeChk',
	             allowBlank: false,
	             maxLength: 4,
	             vtype: 'isNumber'
	          },{
	     		width: 200,
	             xtype: 'combo',
		         fieldLabel: '营销活动算法',
		         id: 'actContentA',
		         hiddenName: 'actContent',
		         readOnly: true,
		         store: new Ext.data.ArrayStore({
		            fields: ['valueField','displayField'],
		            data: [['0','算法1：应收商户手续费*费率'],['1','算法2：应付银联手续费*费率'],['2','算法3：手续费净额*费率']]
		        })
	          },{	
			     	xtype: 'textarea',
					fieldLabel: '备注描述',
					id: 'remarks',
					name: 'remarks',
					width: 250,
					maxLength: 40,
			 		readOnly: true
		 		}]
	});
	
	var chkForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 150,
		waitMsgTarget: true,
		region: 'center',
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '所属分行*',
			id: 'chkBankNoU',
			hiddenName: 'chkbankNo',
			readOnly: true
	             },{	
			         	xtype: 'textfield',
			 			fieldLabel: '商业营销活动编号*',
			 			id: 'chkActNo',
			 			name: 'chkActNo',
			 			allowBlank: false,
			 			readOnly: true
				},{	
			         	xtype: 'textfield',
			 			fieldLabel: '商业营销活动名称*',
			 			id: 'chkActName',
			 			name: 'chkActName',
			 			allowBlank: false,
			 			readOnly: true
				},{
						xtype: 'combo',
						fieldLabel: '状态*',
						allowBlank: false,
						hiddenName :'chkState',
						id :'chkStateUpd',
						displayField: 'displayField',
	                	valueField: 'valueField',
						store: new Ext.data.ArrayStore({
		                    fields: ['valueField','displayField'],
		                    data: [['0','正常'],['1','未审核'],['2','关闭']]
		                }),
			 			readOnly: true
			    },{	
			         	xtype: 'datefield',
						fieldLabel: '活动起始时间*',
						allowBlank: false,
						format: 'Ymd',
						name :'chkStartDate',
						id :'chkStartDate',
						readOnly: true
				},{
						xtype: 'datefield',
						fieldLabel: '活动截止时间*',
						allowBlank: false,
						format: 'Ymd',
						name :'chkEndDate',
						id :'chkEndDate',
						readOnly: true
			    },{
	                    xtype: 'textfield',
	                    fieldLabel: '自动清算单位编号 *',
	                    id: 'chkOrganNo',
	                    name: 'chkOrganNo',
	                    allowBlank: false,
	                    readOnly: true
	                 },{
	                    xtype: 'textfield',
	                    fieldLabel: '自动清算业务种类编号* ',
	                    id: 'chkOrganType',
	                    name: 'chkOrganType',
	                    allowBlank: false,
	                    readOnly: true
	                 },{
	            		width: 200,
	                    xtype: 'combo',
		                fieldLabel: '营销活动算法',
		                id: 'chkActContentA',
		                hiddenName: 'chkActContent',
		                readOnly: true,
				        store: new Ext.data.ArrayStore({
				            fields: ['valueField','displayField'],
				            data: [['0','算法1：应收商户手续费*费率'],['1','算法2：应付银联手续费*费率'],['2','算法3：手续费净额*费率']]
				        })
	                 },{	
			         	xtype: 'textarea',
			 			fieldLabel: '备注描述',
			 			id: 'chkRemarks',
			 			name: 'chkRemarks',
			 			width: 250,
			 			maxLength: 40,
				 		readOnly: true
		 				}]
	});
	
	
	var actWin = new Ext.Window({
		title: '商户营销活动审核',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 1000,
		height: 400,
		layout:'border',
        autoScroll:true,
		items: [actForm,chkForm],
		buttonAlign: 'center',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '审核',
			handler: function() {
				var	url = 'T70204Action_modifyCheck.asp';
				if(actForm.getForm().isValid()) {
					actForm.getForm().submitNeedAuthorise({
						url: url,
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,grid);
							actForm.getForm().reset();
                            actWin.close();
							grid.getStore().load();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '70204',
							subTxnId: '02'
						}
					});
				}
			}
		}]
	});
	
	var actStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'chkActNo',mapping: 'actNo'},
            {name: 'chkbankNo',mapping: 'bankNo'},
            {name: 'chkActName',mapping: 'actName'},
            {name :'chkState',mapping: 'state'},
            {name :'chkStartDate',mapping: 'startDate'},
            {name :'chkEndDate',mapping: 'endDate'},
            {name: 'chkActContent',mapping: 'actContent'},
            {name: 'chkOrganNo',mapping: 'organNo'},
            {name: 'chkOrganType',mapping: 'organType'},
            {name: 'chkRemarks',mapping: 'remarks'}
        ]),
        autoLoad: false
    });

    actStore.load({
	        params: {
	             storeId: 'getActInfo',
	             actNo: actNo
	        },
	        callback: function(records, options, success){
	            if(success){
	            	chkForm.getForm().loadRecord(actStore.getAt(0));
	            	actForm.getForm().loadRecord(record);
	            	actWin.show();
	            }else{
	            	alert("加载信息失败，请刷新后再试")
	            }
	        }
	    });
}