Ext.onReady(function() {
	var selectedRecord;
    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
    //财务POS  终端类型数据集
    var termTypeStoreFin = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE_FIN',function(ret){
        termTypeStoreFin.loadData(Ext.decode(ret));
    });
    //专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
    //EPOS版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    
    function Unselectall(){
    	var array = Ext.getCmp('checkbox');
    	var length = array.items.getCount();
    	var all;
    	if (this.checked == true){
   		 	all = false;
    	}
    	for (i = 0; i <= length-1;i++){
   		 array.items.get(i).setValue(all);
   	 		}  
    	}
    
    var mchtGroupFlag;
    var mchtGroupFlag2;
    var resetValue;
    var hasSub = false;
    
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 90,
        items: [new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号'
            }),{
    			xtype: 'basecomboselect',
    			baseParams: 'BRH_BELOW_BRANCH',
    			fieldLabel: '商户收单机构*',
    			id: 'idacqInstIdNull',
    			hiddenName: 'acqInstIdNull',
    			value: BRHID,
    			editable: false,
    			anchor: '70%',
    			listeners: {
                    'select': function() {
                    	Ext.getCmp('mchnNoQ').reset();
                    	Ext.getCmp('mchnNoQ').setRawValue();
                    	Ext.getCmp('mchnNoQ').parentP=this.value;
                    	Ext.getCmp('mchnNoQ').getStore().reload();
                    }
    			}
    		},{
	        	xtype: 'dynamicComboMN',
	        	methodName: 'getMchntNoLimit',
                fieldLabel: '商户编号',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                parentP:BRHID,
                editable: true,
                width: 300
           },{ 
                xtype: 'combo',
                fieldLabel: '终端状态',
                id: 'state',
                name: 'state',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['1','启用'],['2','修改未审核'],['3','冻结未审核'],['4','冻结'],['5','恢复未审核'],['6','注销未审核'],['7','注销']]
                })
        },{
			xtype: 'basecomboselect',
			baseParams: 'TERM_TYPE',
			fieldLabel: '终端类型',
			id: 'idtermtpsearch',
			hiddenName: 'termtpsearch',
			anchor: '70%'
		},{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor :'60%'       
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
              anchor :'60%'       
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                termStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
                Ext.getCmp('mchnNoQ').reset();
            	Ext.getCmp('mchnNoQ').setRawValue();
            	Ext.getCmp('mchnNoQ').parentP=BRHID;
            	Ext.getCmp('mchnNoQ').getStore().reload();
            }
        }]
    });
    
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'mchntName',mapping: 'mchntName'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'termFactory',mapping: 'termFactory'},
			{name: 'termMachTp',mapping: 'termMachTp'},
			{name: 'termVer',mapping: 'termVer'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'termBranch',mapping: 'termBranch'},
			{name: 'termIns',mapping: 'termIns'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	
	termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            termSta: Ext.getCmp('state').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            termTp: Ext.getCmp('idtermtpsearch').getValue()
        });
    }); 
	termStore.load();
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{header: '商户号',dataIndex: 'mchntName',width: 150,id:'mchntName'},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
		{header: '终端所属机构',dataIndex: 'termBranch'},
		{header: '终端库存编号',dataIndex: 'termIdId'},
		{header: '终端厂商',dataIndex: 'termFactory',width: 150},
		{header: '终端型号',dataIndex: 'termMachTp',width: 150}
	]);
	
	
	
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termPanel.setActiveTab(0);
			termWin.show();
			termWin.center();
		}
	};
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdUpd',mapping: 'id.termId'},
            {name: 'recCrtTs',mapping: 'id.recCrtTs'},
            {name: 'mchnNoU',mapping: 'mchtCd'},
            {name: 'termMccUpd',mapping: 'termMcc'},
            {name: 'termBranchUpd',mapping: 'termBranch'},
            {name: 'termSignStaU',mapping: 'termSignSta'},
            {name: 'termFactoryUpd',mapping: 'termFactory'},
            {name: 'termMachTpUpd',mapping: 'termMachTp'},
            {name: 'termIdIdU',mapping: 'termIdId'},
            {name: 'termVerUpd',mapping: 'termVer'},
            {name: 'termVerTpUpd',mapping: 'termVerTp'},
            {name: 'termTpU',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'propTpU',mapping: 'propTp'},
            {name: 'propInsNmUpd',mapping: 'propInsNm'},
            {name: 'termBatchNmUpd',mapping: 'termBatchNm'},
            {name: 'termStlmDtUpd',mapping: 'termStlmDt'},
            {name: 'connectModeU',mapping: 'connectMode'},
            {name: 'financeCard1Upd',mapping: 'financeCard1'},
            {name: 'financeCard2Upd',mapping: 'financeCard2'},
            {name: 'financeCard3Upd',mapping: 'financeCard3'},
            {name: 'bindTel1Upd',mapping: 'bindTel1'},
            {name: 'bindTel2Upd',mapping: 'bindTel2'},
            {name: 'bindTel3Upd',mapping: 'bindTel3'},
            {name: 'termAddrUpd',mapping: 'termAddr'},
            {name: 'termPlaceUpd',mapping: 'termPlace'},
            {name: 'oprNmUpd',mapping: 'oprNm'},
            {name: 'termParaUpd',mapping: 'termPara'},
            {name: 'termPara1Upd',mapping: 'termPara1'},
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'recUpdTsUpd',mapping: 'recUpdTs'},
            {name: 'misc2Upd',mapping: 'misc2'}
        ]),
        autoLoad: false
    });
    var updFlag = false;	//false界面初始化,true修改界面值
    function praseTermParam(val,record)
    {
        var array = val.split("|");
        if(array[4] == undefined)
        	return 0;
        array[4] = array[4].substring(2,array[4].length);
        T30101.getTermParam(array[4],function(ret){
            var termParam = ret;
        for(var i=0;i<=26;i++){
            updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }
        updTermForm.getForm().findField("txn14Upd").setValue(array[0].substring(2,array[0].length).trim());
        updTermForm.getForm().findField("txn15Upd").setValue(array[1].substring(2,array[1].length).trim());
        updTermForm.getForm().findField("txn16Upd").setValue(array[2].substring(2,array[2].length).trim());
        updTermForm.getForm().findField("txn22Upd").setValue(array[3].substring(2,array[3].length));
        updTermForm.getForm().findField("txn27Upd").setValue(array[5].substring(2,array[5].length));
        updTermForm.getForm().findField("txn35Upd").setValue(array[12].substring(2,array[12].length).trim());
        updTermForm.getForm().findField("txn36Upd").setValue(array[13].substring(2,array[13].length).trim()/100);
        updTermForm.getForm().findField("txn37Upd").setValue(array[14].substring(2,array[14].length).trim()/100);
        updTermForm.getForm().findField("txn38Upd").setValue(array[15].substring(2,array[15].length).trim()/100);
        updTermForm.getForm().findField("txn39Upd").setValue(array[16].substring(2,array[16].length).trim()/100);
        updTermForm.getForm().findField("txn40Upd").setValue(array[17].substring(2,array[17].length).trim());
        updTermForm.getForm().findField("param27Upd").setDisabled(true);
        Ext.getCmp('accountBox2').hide();
 		Ext.getCmp('financeCard1Upd').allowBlank = true;
    	updTermPanel.get('info3Upd').setDisabled(false);
    	Ext.getCmp('txn14Upd').allowBlank = false;
 	   	//updTermForm.getForm().findField("reserveFlag1Upd").setValue(0);//修改操作无默认
 	   	Ext.getCmp('accountBox4').hide();
 	   	updTermForm.getForm().findField("termVersionU").setValue("");
 	   	Ext.getCmp('termVersionU').allowBlank = true;
 	   updTermForm.getForm().findField("termTpU").setReadOnly(false);
 	   	var value = updTermForm.getForm().findField("termTpU").getValue();
        switch(value){
        	case '0':
        		updTermForm.getForm().findField("param27Upd").setDisabled(false);
        		break;
        	case '1':
        		Ext.getCmp('accountBox2').show();
                Ext.getCmp('financeCard1Upd').allowBlank = false;
                break;
        	case '3':
        		//updTermForm.getForm().findField("reserveFlag1Upd").setValue(1);//修改操作无默认
         	   updTermPanel.get('info3Upd').setDisabled(true);
         	   Ext.getCmp('txn14Upd').allowBlank = true;
         	   Ext.getCmp('accountBox4').show();
         	   SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',updTermForm.getForm().findField("termBranchUpd").value,function(ret){
     				eposStore.loadData(Ext.decode(ret));
         	   });
         	   updTermForm.getForm().findField("termVersionU").setValue(Ext.getCmp('misc2Upd').getValue().substring(0,4));
         	   Ext.getCmp('termVersionU').allowBlank = false;
         	   Ext.getCmp('accountBox2').hide();
       		   Ext.getCmp('financeCard1Upd').allowBlank = true;
         	   break;
        	case '5':
        		updTermForm.getForm().findField("param27Upd").setDisabled(false);
        		break;
        	case '7':
        		updTermForm.getForm().findField("termTpU").setReadOnly(true);
        		break;
        	case '10':
        		updTermForm.getForm().findField("param26Upd").setDisabled(false);
        		break;
        	case '11':
        		Ext.getCmp('txn14Upd').allowBlank = true;
        		Ext.getCmp('bindTel1Upd').allowBlank = true;
        		break;
        }
       var args = Ext.getCmp('propTpU').getValue();
       if(args == '2')
       {
           Ext.getCmp("termPara1Upd").show();
           Ext.getCmp("flagBox2").show();
       }
       else
       {
           Ext.getCmp("termPara1Upd").hide();
           Ext.getCmp("flagBox2").hide();
       }
       var tmp = Ext.getCmp("termTpU").value;
       mchtGroupFlag2 = record.recUpdTsUpd;
       });
    }
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            } 
            
            termInfoStore.load({
                params: {
                     storeId: 'getTermInfo',
                     termId: selectedRecord.get('termId'),
                     recCrtTs: selectedRecord.get('recCrtTs')
                },
                callback: function(records, options, success){
                    if(success){
                       updTermForm.getForm().loadRecord(records[0]);
                       updTermPanel.setActiveTab(0);
                       var termPara = updTermForm.getForm().findField("termParaUpd").value;
                       praseTermParam(termPara,records[0].data);
                       updTermWin.show();
                       updFlag = false;
                    }else{
                       updTermWin.hide();
                    }
                }
            });
		}
	};
    var copyMenu = {
        text: '复制',
        width: 85,
        iconCls: 'copy',                                     
        disabled: true,
        handler:function() {
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
            showConfirm('确定要复制吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在复制信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                     Ext.Ajax.requestNeedAuthorise({
                        url: 'T3010103Action.asp',
                        params: {
                            termId: selectedRecord.get('termId'),
                            recCrtTs: selectedRecord.get('recCrtTs'),
                            txnId: '30101',
                            subTxnId: '03'
                        },
                        success: function(rsp,opt) {
                            var rspObj = Ext.decode(rsp.responseText);
                            termStore.reload();
                            if(rspObj.success) {
                                showSuccessMsg(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                        }
                    });
                    grid.getTopToolbar().items.items[3].disable();
                    hideProcessMsg();
                }
            });
        }
    };
    
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
    var qryMenu = {
            text: '详细信息',
            width: 85,
            iconCls: 'detail',
            disabled: true,
            handler:function(bt) {
                var selectedRecord = grid.getSelectionModel().getSelected();
                if(selectedRecord == null)
                {
                    showAlertMsg("没有选择记录",grid);
                    return;
                }
                bt.disable();
    			setTimeout(function(){bt.enable();},2000);
                selectTermInfoNew(selectedRecord.get('termId'),selectedRecord.get('recCrtTs'));	
            }
        };
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push(queryMenu);	//[1]
	menuArr.push(editMenu);		//[2]
    menuArr.push(copyMenu);     //[3]
    menuArr.push(qryMenu);      //[4]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息维护',
		iconCls: 'T301',
		region:'center',
		frame: true,
		border: true,
		autoExpandColumn: 'mchntName',
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
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
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('termSta') == '0' || rec.get('termSta') == '1' || rec.get('termSta') == '2' || rec.get('termSta') == '8' || rec.get('termSta') == '9'|| rec.get('termSta') == 'A'|| rec.get('termSta') == 'B'
            || rec.get('termSta') == 'C'|| rec.get('termSta') == 'D') {
				grid.getTopToolbar().items.items[2].enable();
			} else {
				grid.getTopToolbar().items.items[2].disable();
			}
            if(rec.get('termSta') == '1')
            {
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[3].disable();
            }
            grid.getTopToolbar().items.items[4].enable();
		}
	});
    
	


	
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	

	// 终端库存号
	var termIdIdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	// 终端厂商
	var manufacturerStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MANUFACTURER',function(ret){
		manufacturerStore.loadData(Ext.decode(ret));
	});
	
	//终端型号
	var terminalTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});


     var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 600,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                items: [{
                	columnWidth: .8,
                	layout: 'form',
                	items:[{
                		xtype: 'combofordispaly',
     			        baseParams: 'MCHT_GROUP_FLAG',
     					fieldLabel: '商户种类',
     					id: 'idmchtGroupFlag',
     					hiddenName: 'mchtGroupFlag'
                	}]
                },{
                 columnWidth: .8,
                 layout: 'form',
                 items: [{
                	xtype: 'dynamicComboMN',
     	        	methodName: 'getMchntNoLimit',
                    fieldLabel: '商户编号*',
                    hiddenName: 'mchnNoNew',
                    id: 'mchnNoN',
                    blankText: '商户编号不能为空',
                    allowBlank: false,
                    editable: true,
                    emptyText: '请选择商户号',
                    width: 300
                  }]
             },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户名',
                        id: 'txn22New',
                        name: 'txn22New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27New',
                        name: 'txn27New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccNew',
                        name: 'termMccNew',
                        readOnly: true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属分行*',
                    id: 'termBranchNew',
                    hiddenName: 'brhIdNew',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    width: 130,
                    blankText: '终端所属分行不能为空',
                    listeners:{
                        'select': function() {
                        	SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('termBranchNew').value,function(ret){
							        eposStore.loadData(Ext.decode(ret));
							    });
                        }
                    }
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 20,
                    allowBlank: false,
                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelNew',
                    name: 'contTelNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
                    allowBlank: false,
                    hiddenName: 'propTpNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我行产权'],['1','我行租赁'],['2','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpN').getValue();
	                        if(args == 2)
	                        {
	                            Ext.getCmp("termPara1New").show();
                                Ext.getCmp("flagBox1").show();
	                        }
                            else
                            {
                                Ext.getCmp("termPara1New").hide();
                                Ext.getCmp("flagBox1").hide();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单服务机构',
                    store: organStore,
                    id: 'propInsNmN',
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                id: "flagBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1New',
                    name: 'termPara1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeN',
                    value: 2,
                    allowBlank: false,
                    hiddenName: 'connectModeNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
                    })
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                id: 'info2New',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'combo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpN',
	                    allowBlank: false,
	                    hiddenName: 'termTpNew',
	                    width:150,
	                    store: termTypeStore,
	                    listeners: {
	                     'select': function() {
	                                var value1 = Ext.getCmp("termMccNew").getValue();
	                                var value2 = Ext.getCmp('termTpN').getValue();
	                                termForm.getForm().findField("param26New").setDisabled(true);
	                                //改为使用elseif判断
	                                if(mchtGroupFlag == '6' && value2 != '3'){
	                                	termForm.getForm().findField("termTpN").setValue(3);
		                                showAlertMsg("固话POS商户，终端类型只能选择固话POS",grid);
		                                return;
	                    	 		}else if(mchtGroupFlag != '6' && value2 == '3'){
	                    	 			if(mchtGroupFlag == '8'){
	                                		termForm.getForm().findField("termTpN").setValue(11);
	                                	}else if(mchtGroupFlag == '5'){
	                                		termForm.getForm().findField("termTpN").setValue(10);
	                                	}else{
	                                		termForm.getForm().findField("termTpN").setValue(0);
	                                	}
		                                showAlertMsg("非固话POS商户，终端类型不能选择固话POS",grid);
		                                return;
	                    	 		}else if( value1 != '0000' && value2 == '1'){
	                                	if(mchtGroupFlag == '8'){
	                                		termForm.getForm().findField("termTpN").setValue(11);
	                                	}else if(mchtGroupFlag == '5'){
	                                		termForm.getForm().findField("termTpN").setValue(10);
	                                	}else{
	                                		termForm.getForm().findField("termTpN").setValue(0);
	                                	}
		                                showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
		                                return;
	                                }else if(mchtGroupFlag == '8' && value2 != '11'){
	                                	termForm.getForm().findField("termTpN").setValue(11);
		                                showAlertMsg("行业合作商户，终端类型只能选择虚拟POS",grid);
		                                return;
	                    	 		}else if(mchtGroupFlag != '8' && value2 == '11'){
	                    	 			if(mchtGroupFlag == '5'){
	                                		termForm.getForm().findField("termTpN").setValue(10);
	                                	}else{
	                                		termForm.getForm().findField("termTpN").setValue(0);
	                                	}
		                                showAlertMsg("非行业合作商户，终端类型不能选择虚拟POS",grid);
		                                return;
	                    	 		}else if(mchtGroupFlag == '5' && value2 != '10'){
	                                	termForm.getForm().findField("termTpN").setValue(10);
	                                	termForm.getForm().findField("param26New").setDisabled(false);
		                                showAlertMsg("金融服务商户，终端类型只能选择金融服务POS",grid);
		                                return;
	                    	 		}else if(mchtGroupFlag != '5' && value2 == '10'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("非金融服务商户，终端类型不能选择金融服务POS",grid);
		                                return;
	                    	 		}else if(mchtGroupFlag != '1' && mchtGroupFlag != '9' && value2 == '12'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("只有普通商户或代缴费商户才能选择自助POS",grid);
		                                return;
	                    	 		}
	                                Ext.getCmp('accountBox1').hide();
	                                termForm.getForm().findField("financeCard1New").setValue("");
                                    termForm.getForm().findField("financeCard1New").setReadOnly(false);
                                    Ext.getCmp('financeCard1New').allowBlank = true;
                                    
                                    Ext.getCmp('accountBox3').hide();
	                                termPanel.get('info3New').setDisabled(false);
	                                Ext.getCmp('txn14New').allowBlank = false;
                                	Ext.getCmp('termVerN').allowBlank = true;
                                	
                 	                termForm.getForm().findField("param27New").setDisabled(true);
                                    for(var i=0;i<=26;i++){
                                    	termForm.getForm().findField('param'+(i+1)+'New').setValue(0);
                                    	if(i != 20 && i != 25 && i != 26){
                                    		termForm.getForm().findField('param'+(i+1)+'New').setDisabled(false);
                                    	}
                                    }
	                                switch(value2){
	                                	case '0':{
	                                		 //普通POS可勾选积分
	                    	                termForm.getForm().findField("param27New").setDisabled(false);
	                    	                break;
	                                	}
	                                	case '1':{
	                                		Ext.getCmp('accountBox1').show();
	                                		T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
	                                            var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
	                                            termForm.getForm().findField("financeCard1New").setValue(mchntInfo.updOprId);
		                                        termForm.getForm().findField("financeCard1New").setReadOnly(true);
		                                        Ext.getCmp('financeCard1New').allowBlank = false;
		                                        
		                                        termForm.getForm().findField("param1New").setValue(1);
		                                        termForm.getForm().findField("param11New").setValue(1);       
		                                        termForm.getForm().findField("param12New").setValue(1);       
		                                        termForm.getForm().findField("param13New").setValue(1);       
		                                        termForm.getForm().findField("param14New").setValue(1);       
		                                        termForm.getForm().findField("param15New").setValue(1);
	                                		});
	                                		break;
	                                	}
	                                	case '5':{
	                                		 //移动POS可勾选积分
	                    	                termForm.getForm().findField("param27New").setDisabled(false);
	                    	                break;
	                                	}
	                                	case '7':{
	                                        termForm.getForm().findField("param1New").setValue(1);
	                                        termForm.getForm().findField("param20New").setValue(1);
	                                        termForm.getForm().findField("param22New").setValue(1);
	                                        termForm.getForm().findField("param23New").setValue(1);
	                                        termForm.getForm().findField("param24New").setValue(1);
	                                        break;
	                                	}
	                                	case '3':{
			                                Ext.getCmp('accountBox3').show();
			                                termPanel.get('info3New').setDisabled(true);
			                                Ext.getCmp('txn14New').allowBlank = true;
//			                                termForm.getForm().findField("reserveFlag1New").setValue(1);
			                                Ext.getCmp('termVerN').allowBlank = false;
			                                break;
		                                }
	                                	case '10':{//金融服务POS
	                                        termForm.getForm().findField("param1New").setValue(1);
	                                        termForm.getForm().findField("param26New").setDisabled(false);
	                                        termForm.getForm().findField("param26New").setValue(1);
	                                        
	                                        termForm.getForm().findField("param2New").setValue(0);
	                                        termForm.getForm().findField("param3New").setValue(0);
	                                        termForm.getForm().findField("param4New").setValue(0);
	                                        termForm.getForm().findField("param5New").setValue(0);
	                                        termForm.getForm().findField("param6New").setValue(0);
	                                        termForm.getForm().findField("param7New").setValue(0);
	                                        termForm.getForm().findField("param8New").setValue(0);
	                                        termForm.getForm().findField("param11New").setValue(0);
	                                        termForm.getForm().findField("param12New").setValue(0);
	                                        termForm.getForm().findField("param13New").setValue(0);
	                                        termForm.getForm().findField("param14New").setValue(0);
	                                        termForm.getForm().findField("param15New").setValue(0);
	                                        termForm.getForm().findField("txn35New").setValue(0);
	                                        termForm.getForm().findField("txn36New").setValue(0);
	                                        termForm.getForm().findField("txn37New").setValue(0);
	                                        termForm.getForm().findField("txn38New").setValue(0);
	                                        termForm.getForm().findField("txn39New").setValue(0);
	                                        termForm.getForm().findField("txn40New").setValue(0);
	                                        termForm.getForm().findField("txn35New").setReadOnly(true);
	                                        termForm.getForm().findField("txn36New").setReadOnly(true);
	                                        termForm.getForm().findField("txn37New").setReadOnly(true);
	                                        termForm.getForm().findField("txn38New").setReadOnly(true);
	                                        termForm.getForm().findField("txn39New").setReadOnly(true);
	                                        termForm.getForm().findField("txn40New").setReadOnly(true);
	                                        termForm.getForm().findField("param2New").setDisabled(true);
	                                        termForm.getForm().findField("param3New").setDisabled(true);
	                                        termForm.getForm().findField("param4New").setDisabled(true);
	                                        termForm.getForm().findField("param5New").setDisabled(true);
	                                        termForm.getForm().findField("param6New").setDisabled(true);
	                                        termForm.getForm().findField("param7New").setDisabled(true);
	                                        termForm.getForm().findField("param8New").setDisabled(true);
	                                        termForm.getForm().findField("param11New").setDisabled(true);
	                                        termForm.getForm().findField("param12New").setDisabled(true);
	                                        termForm.getForm().findField("param13New").setDisabled(true);
	                                        termForm.getForm().findField("param14New").setDisabled(true);
	                                        termForm.getForm().findField("param15New").setDisabled(true);
	                                        break;
	                                	}
	                                	case '11':{//虚拟POS
			                                Ext.getCmp('txn14New').allowBlank = true;
			                                Ext.getCmp('bindTel1New').allowBlank = true;
			                                termForm.getForm().findField("param20New").setValue(1);
			        		            	termForm.getForm().findField("param24New").setValue(1);
			        		                termForm.getForm().findField("param25New").setValue(1);
			        		                termForm.getForm().findField("txn40New").setValue(0);
			                                break;
		                                }
	                                	case '12':{//自助POS
	                                		termForm.getForm().findField("param7New").setValue(0);
	                                		termForm.getForm().findField("param7New").setDisabled(true);
	                                		termForm.getForm().findField("param9New").setValue(0);
	                                		termForm.getForm().findField("param9New").setDisabled(true);
	                                		termForm.getForm().findField("param10New").setValue(0);
	                                		termForm.getForm().findField("param10New").setDisabled(true);
	                                		termForm.getForm().findField("param11New").setValue(0);
	                                		termForm.getForm().findField("param11New").setDisabled(true);
	                                		termForm.getForm().findField("param12New").setValue(0);
	                                		termForm.getForm().findField("param12New").setDisabled(true);
	                                		termForm.getForm().findField("param13New").setValue(0);
	                                		termForm.getForm().findField("param13New").setDisabled(true);
	                                		termForm.getForm().findField("param14New").setValue(0);
	                                		termForm.getForm().findField("param14New").setDisabled(true);
	                                		termForm.getForm().findField("param15New").setValue(0);
	                                		termForm.getForm().findField("param15New").setDisabled(true);
	                                		termForm.getForm().findField("param16New").setValue(0);
	                                		termForm.getForm().findField("param16New").setDisabled(true);
	                                		termForm.getForm().findField("param17New").setValue(0);
	                                		termForm.getForm().findField("param17New").setDisabled(true);
	                                		termForm.getForm().findField("param18New").setValue(0);
	                                		termForm.getForm().findField("param18New").setDisabled(true);
	                                		termForm.getForm().findField("param19New").setValue(0);
	                                		termForm.getForm().findField("param19New").setDisabled(true);
	                                		termForm.getForm().findField("param26New").setValue(0);
	                                		termForm.getForm().findField("param26New").setDisabled(true);
			                                break;
		                                }
	                                }
	                        }
                    }
                    }]
           },{
                columnWidth: .5,
                id: "accountBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '财务账号1*',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard1New',
                    name: 'financeCard1New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号2',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard2New',
                    name: 'financeCard2New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号3',
                    maxLength: 20,
                    vtype: 'isNumber', 
                    id: 'financeCard3New',
                    name: 'financeCard3New'
                }]
            },{
                id: 'accountBox3',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                	xtype: 'combo',
                    fieldLabel: '固话POS版本号*',
                    store: eposStore,
                    id: 'termVerN',
                    hiddenName: 'termVerNew',
                    anchor: '80%'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 200,
                    id: 'termAddrNew',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    vtype: 'isNumber',
                    id: 'txn14New',
                    name: 'txn14New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn15New',
                    name: 'txn15New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn16New',
                    name: 'txn16New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    vtype: 'isNumber',
                    id: 'bindTel1New',
                    name: 'bindTel1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel2New',
                    name: 'bindTel2New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel3New',
                    name: 'bindTel3New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignNew',
                    name: 'keyDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignNew',
                    name: 'paramDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignNew',
                    name: 'icDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
                    		r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    inputValue: '1'
//                    listeners :{
//                    'check':function(r,c){
//                    	if(Ext.getCmp('termTpN').value == '3')
//                    		r.setValue('1');
//						}
//                    }
                }]
            }]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款期数',
                        vtype: 'isNumber',
                        id: 'txn35New',
                        maxLength: 2,
                        name: 'txn35New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款限额',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn36New',
                        name: 'txn36New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '消费单笔上限 ',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn37New',
                        name: 'txn37New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货单笔上限',
                        id: 'txn38New',
                        vtype: 'isMoney',
                        maxLength: 12,
                        name: 'txn38New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '转帐单笔上限',
                        vtype: 'isMoney',
                        id: 'txn39New',
                        maxLength: 12,
                        name: 'txn39New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货时间跨度',
                        vtype: 'isNumber',
                        id: 'txn40New',
                        maxLength: 2,
                        name: 'txn40New',
                        value: 30
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1New',
                        name: 'param1New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2New',
                        name: 'param2New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3New',
                        name: 'param3New',
                        inputValue: '1',
                        listeners : {
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpN').getValue();
                        		if(termTp == '12' && checkbox.checked){
                        			showConfirm('为控制有卡自助交易风险,预授权撤销交易只能由商户发起.请确认是否在该自助终端上勾选预授权撤销交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						termForm.getForm().findField("param3New").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4New',
                        name: 'param4New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5New',
                        name: 'param5New',
                        inputValue: '1',
                        listeners : {
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpN').getValue();
                        		if(termTp == '12' && checkbox.checked){
                        			showConfirm('为控制有卡自助交易风险,预授权完成撤销交易只能由商户发起.请确认是否在该自助终端上勾选预授权完成撤销交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						termForm.getForm().findField("param5New").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6New',
                        name: 'param6New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7New',
                        name: 'param7New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8New',
                        name: 'param8New',
                        inputValue: '1',
                        listeners : {
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpN').getValue();
                        		if(termTp == '12' && checkbox.checked){
                        			showConfirm('为控制有卡自助交易风险,退货交易只能由商户发起.请确认是否在该自助终端上勾选退货交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						termForm.getForm().findField("param8New").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9New',
                        name: 'param9New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10New',
                        name: 'param10New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11New',
                        name: 'param11New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12New',
                        name: 'param12New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13New',
                        name: 'param13New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财务POS） ',
                        id: 'param14New',
                        name: 'param14New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财务POS） ',
                        id: 'param15New',
                        name: 'param15New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16New',
                        name: 'param16New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17New',
                        name: 'param17New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18New',
                        name: 'param18New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19New',
                        name: 'param19New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20New',
                        name: 'param20New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21New',
                        name: 'param21New',
                        disabled: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22New',
                        name: 'param22New',
                        inputValue: '1'
                        
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23New',
                        name: 'param23New',
                        inputValue: '1'
                     
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24New',
                        name: 'param24New',
                        inputValue: '1'
                       
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '脱机退货 ',
                        id: 'param25New',
                        name: 'param25New',
                        inputValue: '1'
                       
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '理财POS',
                        id: 'param26New',
                        name: 'param26New',
                        inputValue: '1',
                        disabled: true,
                        listeners: {
   	                     'check': function() {
   	                    	var value26 = termForm.getForm().findField("param26New").checked;
   	                    		if(value26 == true){
   	                    			termForm.getForm().findField("param2New").setValue(0);
                                    termForm.getForm().findField("param3New").setValue(0);
                                    termForm.getForm().findField("param4New").setValue(0);
                                    termForm.getForm().findField("param5New").setValue(0);
                                    termForm.getForm().findField("param6New").setValue(0);
                                    termForm.getForm().findField("param7New").setValue(0);
                                    termForm.getForm().findField("param8New").setValue(0);
                                    termForm.getForm().findField("param11New").setValue(0);
                                    termForm.getForm().findField("param12New").setValue(0);
                                    termForm.getForm().findField("param13New").setValue(0);
                                    termForm.getForm().findField("param14New").setValue(0);
                                    termForm.getForm().findField("param15New").setValue(0);
                                    termForm.getForm().findField("txn35New").setValue(0);
                                    termForm.getForm().findField("txn36New").setValue(0);
                                    termForm.getForm().findField("txn37New").setValue(0);
                                    termForm.getForm().findField("txn38New").setValue(0);
                                    termForm.getForm().findField("txn39New").setValue(0);
                                    termForm.getForm().findField("txn40New").setValue(0);
                                    termForm.getForm().findField("txn35New").setReadOnly(true);
                                    termForm.getForm().findField("txn36New").setReadOnly(true);
                                    termForm.getForm().findField("txn37New").setReadOnly(true);
                                    termForm.getForm().findField("txn38New").setReadOnly(true);
                                    termForm.getForm().findField("txn39New").setReadOnly(true);
                                    termForm.getForm().findField("txn40New").setReadOnly(true);
                                    termForm.getForm().findField("param2New").setDisabled(true);
                                    termForm.getForm().findField("param3New").setDisabled(true);
                                    termForm.getForm().findField("param4New").setDisabled(true);
                                    termForm.getForm().findField("param5New").setDisabled(true);
                                    termForm.getForm().findField("param6New").setDisabled(true);
                                    termForm.getForm().findField("param7New").setDisabled(true);
                                    termForm.getForm().findField("param8New").setDisabled(true);
                                    termForm.getForm().findField("param11New").setDisabled(true);
                                    termForm.getForm().findField("param12New").setDisabled(true);
                                    termForm.getForm().findField("param13New").setDisabled(true);             
                                    termForm.getForm().findField("param14New").setDisabled(true);
                                    termForm.getForm().findField("param15New").setDisabled(true);
   	                    		}else{
	   	                    		termForm.getForm().findField("txn35New").setReadOnly(false);
	                                termForm.getForm().findField("txn36New").setReadOnly(false);
	                                termForm.getForm().findField("txn37New").setReadOnly(false);
	                                termForm.getForm().findField("txn38New").setReadOnly(false);
	                                termForm.getForm().findField("txn39New").setReadOnly(false);
	                                termForm.getForm().findField("txn40New").setReadOnly(false);
                                    termForm.getForm().findField("param2New").setDisabled(false);
                                    termForm.getForm().findField("param3New").setDisabled(false);
                                    termForm.getForm().findField("param4New").setDisabled(false);
                                    termForm.getForm().findField("param5New").setDisabled(false);
                                    termForm.getForm().findField("param6New").setDisabled(false);
                                    termForm.getForm().findField("param7New").setDisabled(false);
                                    termForm.getForm().findField("param8New").setDisabled(false);
                                    termForm.getForm().findField("param11New").setDisabled(false);
                                    termForm.getForm().findField("param12New").setDisabled(false);
                                    termForm.getForm().findField("param13New").setDisabled(false);
                                    termForm.getForm().findField("param14New").setDisabled(false);
                                    termForm.getForm().findField("param15New").setDisabled(false);
   	                    		}
   	                        }
                       }
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '积分交易',
                        id: 'param27New',
                        name: 'param27New',
                        inputValue: '1',
                        disabled: true
                     }]
            }]
            }]
    });
     
    //外部加入监听
    Ext.getCmp("mchnNoN").on('select',function(){
    	T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
            var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
            termForm.getForm().findField("param26New").setDisabled(true);
            termForm.getForm().findField("param26New").setValue(0);
            Ext.getCmp('idmchtGroupFlag').setValue(mchntInfo.mchtGroupFlag);
            Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
            Ext.getCmp("termBranchNew").setValue(mchntInfo.acqInstId);
            Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);
            Ext.getCmp("txn27New").setValue(mchntInfo.engName);
            Ext.getCmp('txn14New').allowBlank = false;
    	 	Ext.getCmp('bindTel1New').allowBlank = false;
    	 	termForm.getForm().findField("param20New").setValue(0);
        	termForm.getForm().findField("param24New").setValue(0);
            termForm.getForm().findField("param25New").setValue(0);
            termForm.getForm().findField("txn40New").setValue(30);
            mchtGroupFlag = mchntInfo.mchtGroupFlag;
            SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',mchntInfo.acqInstId,function(ret){
		        eposStore.loadData(Ext.decode(ret));
		        if( mchntInfo.mcc == '0000' ){
	                Ext.getCmp('accountBox1').show();
	                termForm.getForm().findField("termTpNew").bindStore(termTypeStoreFin);
	                termForm.getForm().findField("termTpNew").setValue('1');
	                termForm.getForm().findField("financeCard1New").setValue(mchntInfo.updOprId);
	                termForm.getForm().findField("financeCard1New").setReadOnly(true);
	                Ext.getCmp('financeCard1New').allowBlank = false;
	                
	                termForm.getForm().findField("param1New").setValue(1);
	                termForm.getForm().findField("param11New").setValue(1);       
	                termForm.getForm().findField("param12New").setValue(1);       
	                termForm.getForm().findField("param13New").setValue(1);       
	                termForm.getForm().findField("param14New").setValue(1);       
	                termForm.getForm().findField("param15New").setValue(1); 
	             }else{
	                Ext.getCmp('accountBox1').hide();
	                termForm.getForm().findField("termTpNew").bindStore(termTypeStore);
	                termForm.getForm().findField("termTpNew").setValue('0');
	               
	                
	                termForm.getForm().findField("financeCard1New").setValue("");
	                termForm.getForm().findField("financeCard1New").setReadOnly(false);
	                Ext.getCmp('financeCard1New').allowBlank = true;
	                
	                termForm.getForm().findField("param1New").setValue(0);
	                termForm.getForm().findField("param11New").setValue(0);
	                termForm.getForm().findField("param12New").setValue(0);
	                termForm.getForm().findField("param13New").setValue(0);
	                termForm.getForm().findField("param14New").setValue(0);
	                termForm.getForm().findField("param15New").setValue(0);
	            }
	            termForm.getForm().findField("termTpN").setReadOnly(false);
	            switch (mchtGroupFlag)
	         	{
		            case '5':{//金融服务商户
		            	Ext.getCmp('accountBox3').hide();
		            	termForm.getForm().findField("termTpN").setValue('10');
		            	termForm.getForm().findField("param1New").setValue(1);
		            	termForm.getForm().findField("param26New").setDisabled(false);
                        termForm.getForm().findField("param26New").setValue(1);
                        
                        termForm.getForm().findField("param2New").setValue(0);
                        termForm.getForm().findField("param3New").setValue(0);
                        termForm.getForm().findField("param4New").setValue(0);
                        termForm.getForm().findField("param5New").setValue(0);
                        termForm.getForm().findField("param6New").setValue(0);
                        termForm.getForm().findField("param7New").setValue(0);
                        termForm.getForm().findField("param8New").setValue(0);
                        termForm.getForm().findField("param11New").setValue(0);
                        termForm.getForm().findField("param12New").setValue(0);
                        termForm.getForm().findField("param13New").setValue(0);
                        termForm.getForm().findField("param14New").setValue(0);
                        termForm.getForm().findField("param15New").setValue(0);
                        termForm.getForm().findField("txn35New").setValue(0);
                        termForm.getForm().findField("txn36New").setValue(0);
                        termForm.getForm().findField("txn37New").setValue(0);
                        termForm.getForm().findField("txn38New").setValue(0);
                        termForm.getForm().findField("txn39New").setValue(0);
                        termForm.getForm().findField("txn40New").setValue(0);
                        termForm.getForm().findField("txn35New").setReadOnly(true);
                        termForm.getForm().findField("txn36New").setReadOnly(true);
                        termForm.getForm().findField("txn37New").setReadOnly(true);
                        termForm.getForm().findField("txn38New").setReadOnly(true);
                        termForm.getForm().findField("txn39New").setReadOnly(true);
                        termForm.getForm().findField("txn40New").setReadOnly(true);
                        termForm.getForm().findField("param2New").setDisabled(true);
                        termForm.getForm().findField("param3New").setDisabled(true);
                        termForm.getForm().findField("param4New").setDisabled(true);
                        termForm.getForm().findField("param5New").setDisabled(true);
                        termForm.getForm().findField("param6New").setDisabled(true);
                        termForm.getForm().findField("param7New").setDisabled(true);
                        termForm.getForm().findField("param8New").setDisabled(true);
                        termForm.getForm().findField("param11New").setDisabled(true);
                        termForm.getForm().findField("param12New").setDisabled(true);
                        termForm.getForm().findField("param13New").setDisabled(true);
                        termForm.getForm().findField("param14New").setDisabled(true);
                        termForm.getForm().findField("param15New").setDisabled(true);
		            	break;
		            }
	             	case '6':{//固话POS商户
		            	Ext.getCmp('accountBox3').show();
		            	termForm.getForm().findField("termTpN").setValue('3');
		                termPanel.get('info3New').setDisabled(true);
		                Ext.getCmp('txn14New').allowBlank = true;
		                termForm.getForm().findField("reserveFlag1New").setValue(1);
		                Ext.getCmp('termVerN').allowBlank = false;
		                termForm.getForm().findField("termTpN").setReadOnly(false);
	                    break;
	             	}
	             	case '7':{//专用圈存POS商户
	             		Ext.getCmp('accountBox3').hide();
		            	termForm.getForm().findField("param1New").setValue(1);
		            	termForm.getForm().findField("param20New").setValue(1);
		                termForm.getForm().findField("param22New").setValue(1);
		                termForm.getForm().findField("param23New").setValue(1); 
		                termForm.getForm().findField("param24New").setValue(1); 
		                for(i=2;i<=19;i++){
		                	 termForm.getForm().findField("param"+i+"New").setValue('0');
		                }
		                termForm.getForm().findField("termTpN").setValue('7');
		                termForm.getForm().findField("termTpN").setReadOnly(true);
	                    break;
	             	}
	             	case '8':{//行业合作商户
		            	Ext.getCmp('accountBox3').hide();
		            	termForm.getForm().findField("termTpN").setValue('11');
		            	Ext.getCmp('txn14New').allowBlank = true;
                	 	Ext.getCmp('bindTel1New').allowBlank = true;
                	 	termForm.getForm().findField("param20New").setValue(1);
		            	termForm.getForm().findField("param24New").setValue(1);
		                termForm.getForm().findField("param25New").setValue(1);
		                termForm.getForm().findField("txn40New").setValue(0);
		            	break;
		            }
	             	case '9':{
	             		Ext.getCmp('accountBox3').hide();
	             		termForm.getForm().findField("param6New").setValue(1);
	             		break;
	             	}
		            default:{
		            	Ext.getCmp('accountBox3').hide();
		            	if(termForm.getForm().findField("termTpN").getValue()=='3'){
		            		termForm.getForm().findField("termTpN").setValue('0');
		            	}
		            	termForm.getForm().findField("termTpN").setReadOnly(false);
		                termPanel.get('info3New').setDisabled(false);
		                Ext.getCmp('txn14New').allowBlank = false;
		                termForm.getForm().findField("reserveFlag1New").setValue(0);
		            	Ext.getCmp('termVerN').allowBlank = true;
		            	termForm.getForm().findField("param24New").setValue(0); 
		            	termForm.getForm().findField("param23New").setValue(0); 
		            	termForm.getForm().findField("param22New").setValue(0); 
		            	termForm.getForm().findField("param20New").setValue(0);
		            	termForm.getForm().findField("param6New").setValue(0);
		            }
	             }
	            if(termForm.getForm().findField("termTpN").getValue()=='0' || termForm.getForm().findField("termTpN").getValue()=='5'){
		            //普通POS，移动POS可勾选积分
	                termForm.getForm().findField("param27New").setDisabled(false);
	            }else{
	            	termForm.getForm().findField("param27New").setValue(0);
	            	termForm.getForm().findField("param27New").setDisabled(true);
	            }
		    });
            
        });
    });
     
     
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 450,
		width: 600,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '终端添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				termPanel.setActiveTab("info3New");
				termPanel.setActiveTab("info2New"); 
				hasSub = true;
				if(termForm.getForm().isValid()) {
					termForm.getForm().submitNeedAuthorise({
						url: 'T30101Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							hasSub = false;
							showSuccessDtl(action.result.msg,termWin);
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
                            
                            termWin.hide();
						},
						failure: function(form,action) {
							hasSub = false;
							termPanel.setActiveTab('info3New');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							txnId: '30101',
							subTxnId: '01'
						}
					});
				}
                else
                {
                    var finded = true;
	                termForm.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if(tab == 'info1New' || tab == 'info2New' || tab == 'info3New' ){
	                             termPanel.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
					termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				hasSub = false;
				termWin.hide();
				termForm.getForm().reset();
			}
		}]
	});
	
	//为保证验证信息显示的正确，当切换tab时重新验证
	termPanel.on('tabchange',function(){
    	if(hasSub){
    		termForm.getForm().isValid();
		}else{
			termForm.getForm().clearInvalid();
		}
    });
/**************** 终端修改 *************************/

	
    var updTermPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 600,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1Upd',
                layout: 'column',
                items: [{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端号*',
                    name: 'termIdUpd',
                    id: 'termIdUpd',
                    readOnly: true
                }]
             },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'recCrtTs',
                        name: 'recCrtTs'
                }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items: [{
                    xtype: 'textfield',
                    fieldLabel: '商户号*',
                    name: 'mchnNoUpd',
                    id: 'mchnNoU',
                    readOnly: true
                  }]
             },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户名',
                        id: 'txn22Upd',
                        name: 'txn22Upd',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27Upd',
                        name: 'txn27Upd',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccUpd',
                        name: 'termMccUpd',
                        readOnly: true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属分行*',
                    id: 'termBranchUpd',
                    hiddenName: 'brhIdUpd',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    width: 130,
                    blankText: '终端所属分行不能为空',
                    listeners:{
                        'select': function() {
                        }
                    }
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 20,
                    allowBlank: false,
                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelUpd',
                    name: 'contTelUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性',
                    id: 'propTpU',
                    hiddenName: 'propTpUpd',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我行产权'],['1','我行租赁'],['2','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
                            var args = Ext.getCmp('propTpU').getValue();
                            if(args == 2)
                            {
                                Ext.getCmp("termPara1Upd").show();
                                Ext.getCmp("flagBox2").show();
                            }
                            else
                            {
                                Ext.getCmp("termPara1Upd").hide();
                                Ext.getCmp("flagBox2").hide();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单服务机构',
                    store: organStore,
                    id: 'propInsNmU',
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                id: "flagBox2",
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1Upd',
                    name: 'termPara1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeU',
                    allowBlank: false,
                    hiddenName: 'connectModeUpd',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
                    })
                }]
            }]
            },{
                title: '附加信息',
                id: 'info2Upd',
                layout: 'column',
                items: [
                    {
                      columnWidth: .5,
                      layout: 'form',
                      items:[{
                        xtype: 'combo',
                        fieldLabel: '终端类型*',
                        id: 'termTpU',
                        allowBlank: false,
                        hiddenName: 'termTpUpd',
                        store: termTypeStore,
                        width:150,
                        listeners:{
                        	'beforeselect': function(c,r,i){
                        		resetValue = Ext.getCmp('termTpU').getValue();
                        	},
                            'select': function() {
                             var value = Ext.getCmp('termTpU').getValue();
                             updTermForm.getForm().findField("param26Upd").setDisabled(true);
                             T30101.getMchnt(Ext.getCmp("mchnNoU").getValue(),function(ret){
                                 var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
                                 mchtGroupFlag = mchntInfo.mchtGroupFlag;
	                             //合并判断,等待DWR返回结果后处理
                                 if(mchtGroupFlag == '6' && value != '3'){
                                	 updTermForm.getForm().findField("termTpU").reset();
		                             showAlertMsg("固话POS商户，终端类型只能选择固话POS",grid);
		                             return;
	                 	 		 }else if(Ext.getCmp("termMccUpd").getValue() == '0000' && 
	                 	 				 !(value == '1'|| value == '7')){
	                 	 			if(resetValue == '1'){
	                 	 				updTermForm.getForm().findField("termTpU").setValue(1);
	                 	 				 Ext.getCmp('accountBox2').show();
	                 	 				T30101.getMchnt(Ext.getCmp("mchnNoU").getValue(),function(ret){
	                                         var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
	                                         updTermForm.getForm().findField("financeCard1Upd").setValue(mchntInfo.updOprId);
	                                         updTermForm.getForm().findField("financeCard1Upd").setReadOnly(true);
		                                     Ext.getCmp('financeCard1Upd').allowBlank = false;
		                                        
		                                     updTermForm.getForm().findField("param1Upd").setValue(1);
		                                     updTermForm.getForm().findField("param11Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param12Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param13Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param14Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param15Upd").setValue(1);
	                             		});
	                 	 			}else{
	                 	 				updTermForm.getForm().findField("termTpU").setValue(7);
	                 	 				 Ext.getCmp('accountBox2').hide();
	                 	 				 updTermForm.getForm().findField("financeCard1Upd").setReadOnly(false);
	                                     Ext.getCmp('financeCard1Upd').allowBlank = true;
	                 	 				 updTermForm.getForm().findField("param1Upd").setValue(1);
	                                     updTermForm.getForm().findField("param20Upd").setValue(1);
	                                     updTermForm.getForm().findField("param22Upd").setValue(1);
	                                     updTermForm.getForm().findField("param23Upd").setValue(1);
	                                     updTermForm.getForm().findField("param24Upd").setValue(1);
	                 	 			}
	                 	 				
		                            showAlertMsg("财务POS商户，终端类型只能选择财务POS或者圈存POS",grid);
		                            return;
	                 	 		 }else if(mchtGroupFlag != '6' && value == '3'){
	                 	 			updTermForm.getForm().findField("termTpU").reset();
	                            	showAlertMsg("非固话POS商户，终端类型不能选择固话POS",grid);
	                            	return;
		                 	 	 }else if(Ext.getCmp("termMccUpd").getValue() != '0000' && value == '1'){
		                 	 		updTermForm.getForm().findField("termTpU").reset();
			                        showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
			                        return;
		                         }else if(mchtGroupFlag == '5' && value != '10'){
		                        	updTermForm.getForm().findField("termTpU").reset();
		                            showAlertMsg("金融服务商户，终端类型只能选择金融服务POS",grid);
		                            return;
	                    	 	}else if(mchtGroupFlag != '5' && value == '10'){
	                    	 		updTermForm.getForm().findField("termTpU").reset();
		                            showAlertMsg("非金融服务商户，终端类型不能选择金融服务POS",grid);
		                            return;
	                    	 	}else if(mchtGroupFlag == '8' && value != '11'){
		                        	updTermForm.getForm().findField("termTpU").reset();
		                            showAlertMsg("行业合作商户，终端类型只能选择虚拟POS",grid);
		                            return;
	                    	 	}else if(mchtGroupFlag != '8' && value == '11'){
	                    	 		updTermForm.getForm().findField("termTpU").reset();
		                            showAlertMsg("非行业合作商户，终端类型不能选择虚拟POS",grid);
		                            return;
	                    	 	}else if(mchtGroupFlag != '1' && mchtGroupFlag != '9' && value == '12'){
                                	updTermForm.getForm().findField("termTpU").setValue(0);
	                                showAlertMsg("只有普通商户或代缴费商户才能选择自助POS",grid);
	                                return;
                    	 		}

                                 Ext.getCmp('accountBox4').hide();
	                             Ext.getCmp('accountBox2').hide();
								 Ext.getCmp('txn14Upd').allowBlank = false;
								 updTermForm.getForm().findField("reserveFlag1Upd").setValue(0);
								 updTermPanel.get('info3Upd').setDisabled(false);
								 Ext.getCmp('termVersionU').allowBlank = true;
	                             for(var i=0;i<=26;i++){
	                            	 updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(0);
	                            	 if(i != 20 && i != 25 && i != 26){
	                            		 updTermForm.getForm().findField('param'+(i+1)+'Upd').setDisabled(false);
	                            	 }
                                 }
	                             updTermForm.getForm().findField("param27Upd").setDisabled(true);
	                             //POS判断
	                             switch(value){
	                             	case '0':{
	                             		updTermForm.getForm().findField("param27Upd").setDisabled(false);
	                                    break;
	                             	}
	                             	case '1':{
	                             		Ext.getCmp('accountBox2').show();
	                             		T30101.getMchnt(Ext.getCmp("mchnNoU").getValue(),function(ret){
	                                         var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
	                                         updTermForm.getForm().findField("financeCard1Upd").setValue(mchntInfo.updOprId);
	                                         updTermForm.getForm().findField("financeCard1Upd").setReadOnly(true);
		                                     Ext.getCmp('financeCard1Upd').allowBlank = false;
		                                        
		                                     updTermForm.getForm().findField("param1Upd").setValue(1);
		                                     updTermForm.getForm().findField("param11Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param12Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param13Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param14Upd").setValue(1);       
		                                     updTermForm.getForm().findField("param15Upd").setValue(1);
	                             		});
	                             		break;
	                             	}
	                             	case '5':{
	                             		updTermForm.getForm().findField("param27Upd").setDisabled(false);
	                                    break;
	                             	}
	                             	case '7':{
	                             		 updTermForm.getForm().findField("param1Upd").setValue(1);
	                                     updTermForm.getForm().findField("param20Upd").setValue(1);
	                                     updTermForm.getForm().findField("param22Upd").setValue(1);
	                                     updTermForm.getForm().findField("param23Upd").setValue(1);
	                                     updTermForm.getForm().findField("param24Upd").setValue(1);
	                                     break;
	                             	}
	                             	case '3':{
		                             	Ext.getCmp('accountBox4').show();
										SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION'
												,updTermForm.getForm().findField("termBranchUpd").value,function(ret){
											eposStore.loadData(Ext.decode(ret));
										});
										Ext.getCmp('txn14Upd').allowBlank = true;
										updTermForm.getForm().findField("reserveFlag1Upd").setValue(1);
										updTermPanel.get('info3Upd').setDisabled(true);
										Ext.getCmp('termVersionU').allowBlank = false;
			                            break;
	                             	}
	                             	case '10':{
	                             		 updTermForm.getForm().findField("param1Upd").setValue(1);
	                             		 updTermForm.getForm().findField("param26Upd").setDisabled(false);
	                                     updTermForm.getForm().findField("param26Upd").setValue(1);
	                                     
	                                     updTermForm.getForm().findField("param2Upd").setValue(0);
	                                     updTermForm.getForm().findField("param3Upd").setValue(0);
	                                     updTermForm.getForm().findField("param4Upd").setValue(0);
	                                     updTermForm.getForm().findField("param5Upd").setValue(0);
	                                     updTermForm.getForm().findField("param6Upd").setValue(0);
	                                     updTermForm.getForm().findField("param7Upd").setValue(0);
	                                     updTermForm.getForm().findField("param8Upd").setValue(0);
	                                     updTermForm.getForm().findField("param11Upd").setValue(0);
	                                     updTermForm.getForm().findField("param12Upd").setValue(0);
	                                     updTermForm.getForm().findField("param13Upd").setValue(0);
	                                     updTermForm.getForm().findField("param14Upd").setValue(0);
	                                     updTermForm.getForm().findField("param15Upd").setValue(0);
	                                     updTermForm.getForm().findField("param2Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param3Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param4Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param5Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param6Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param7Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param8Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param11Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param12Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param13Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param14Upd").setDisabled(true);
	                                     updTermForm.getForm().findField("param15Upd").setDisabled(true);    
	                                     break;
	                             	}
	                             	case '11':{
	                             		 Ext.getCmp('txn14Upd').allowBlank = true;
	                            	 	 Ext.getCmp('bindTel1Upd').allowBlank = true;
		        		                 updTermForm.getForm().findField("param20Upd").setValue(1);
		        		            	 updTermForm.getForm().findField("param24Upd").setValue(1);
		        		                 updTermForm.getForm().findField("param25Upd").setValue(1);
		        		                 updTermForm.getForm().findField("txn40Upd").setValue(0);
	                            		 break;
	                             	}
	                             	case '12':{//自助POS
                                		updTermForm.getForm().findField("param7Upd").setValue(0);
                                		updTermForm.getForm().findField("param7Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param9Upd").setValue(0);
                                		updTermForm.getForm().findField("param9Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param10Upd").setValue(0);
                                		updTermForm.getForm().findField("param10Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param11Upd").setValue(0);
                                		updTermForm.getForm().findField("param11Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param12Upd").setValue(0);
                                		updTermForm.getForm().findField("param12Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param13Upd").setValue(0);
                                		updTermForm.getForm().findField("param13Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param14Upd").setValue(0);
                                		updTermForm.getForm().findField("param14Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param15Upd").setValue(0);
                                		updTermForm.getForm().findField("param15Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param16Upd").setValue(0);
                                		updTermForm.getForm().findField("param16Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param17Upd").setValue(0);
                                		updTermForm.getForm().findField("param17Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param18Upd").setValue(0);
                                		updTermForm.getForm().findField("param18Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param19Upd").setValue(0);
                                		updTermForm.getForm().findField("param19Upd").setDisabled(true);
                                		updTermForm.getForm().findField("param26Upd").setValue(0);
                                		updTermForm.getForm().findField("param26Upd").setDisabled(true);
		                                break;
	                                }
                             }
	                         });
                            }
                        }
                      }]
                    },{
                id: 'accountBox2',
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '财务账号1*',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard1Upd',
                    name: 'financeCard1Upd'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号2',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard2Upd',
                    name: 'financeCard2Upd'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号3',
                    maxLength: 20,
                    vtype: 'isNumber',
                    id: 'financeCard3Upd',
                    name: 'financeCard3Upd'
                }]
            },{
                id: 'accountBox4',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '固话POS版本号*',
                    store: eposStore,
                    id: 'termVersionU',
                    hiddenName: 'termVersion',
                    anchor: '80%'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 175,
                    id: 'termAddrUpd',
                    name: 'termAddrUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    vtype: 'isNumber',
                    id: 'txn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn15Upd',
                    name: 'txn15Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'txn16Upd',
                    name: 'txn16Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1*',
                    maxLength: 15,
                    allowBlank: false,
                    vtype: 'isNumber',
                    id: 'bindTel1Upd',
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel2Upd',
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel3Upd',
                    name: 'bindTel3Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignUpd',
                    name: 'keyDownSignUpd',
                    inputValue: '1',
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignUpd',
                    name: 'paramDownSignUpd',
                    inputValue: '1',
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignUpd',
                    name: 'icDownSignUpd',
                    inputValue: '1',
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Upd',
                    name: 'reserveFlag1Upd',
                    inputValue: '1'
                }]
            }]
            },{
                title: '交易信息',
                id: 'info3Upd',
                layout: 'column',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款期数',
                        vtype: 'isNumber',
                        id: 'txn35Upd',
                        maxLength: 2,
                        name: 'txn35Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款限额',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn36Upd',
                        name: 'txn36Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '消费单笔上限 ',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn37Upd',
                        name: 'txn37Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货单笔上限',
                        vtype: 'isMoney',
                        id: 'txn38Upd',
                        maxLength: 12,
                        name: 'txn38Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '转帐单笔上限',
                        vvtype: 'isMoney',
                        id: 'txn39Upd',
                        maxLength: 12,
                        name: 'txn39Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货时间跨度',
                        vtype: 'isNumber',
                        id: 'txn40Upd',
                        maxLength: 2,
                        name: 'txn40Upd',
                        value: 30
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1Upd',
                        name: 'param1Upd',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2Upd',
                        name: 'param2Upd',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3Upd',
                        name: 'param3Upd',
                        inputValue: '1',
                        listeners : {
                        	focus : function(){
                				updFlag = true;
                			},
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpU').getValue();
                        		if(termTp == '12' && checkbox.checked && updFlag){
                        			showConfirm('为控制有卡自助交易风险,预授权撤销交易只能由商户发起.请确认是否在该自助终端上勾选预授权撤销交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						updTermForm.getForm().findField("param3Upd").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4Upd',
                        name: 'param4Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5Upd',
                        name: 'param5Upd',
                        inputValue: '1',
                        listeners : {
                        	focus : function(){
                				updFlag = true;
                			},
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpU').getValue();
                        		if(termTp == '12' && checkbox.checked && updFlag){
                        			showConfirm('为控制有卡自助交易风险,预授权完成撤销交易只能由商户发起.请确认是否在该自助终端上勾选预授权完成撤销交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						updTermForm.getForm().findField("param5Upd").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6Upd',
                        name: 'param6Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7Upd',
                        name: 'param7Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8Upd',
                        name: 'param8Upd',
                        inputValue: '1',
                        listeners : {
                        	focus : function(){
                				updFlag = true;
                			},
                        	check : function(checkbox){
                        		var termTp = Ext.getCmp('termTpU').getValue();
                        		if(termTp == '12' && checkbox.checked && updFlag){
                        			showConfirm('为控制有卡自助交易风险,退货交易只能由商户发起.请确认是否在该自助终端上勾选退货交易?',grid,function(bt) {
                    					//如果点击了提示的确定按钮
                    					if(bt != "yes") {
                    						updTermForm.getForm().findField("param8Upd").setValue(0);
                    					}
                    				});
                        		}
                        	}
                        }
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9Upd',
                        name: 'param9Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10Upd',
                        name: 'param10Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11Upd',
                        name: 'param11Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12Upd',
                        name: 'param12Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13Upd',
                        name: 'param13Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财务POS） ',
                        id: 'param14Upd',
                        name: 'param14Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财务POS） ',
                        id: 'param15Upd',
                        name: 'param15Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16Upd',
                        name: 'param16Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17Upd',
                        name: 'param17Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18Upd',
                        name: 'param18Upd',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19Upd',
                        name: 'param19Upd',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20Upd',
                        name: 'param20Upd',
                        inputValue: '1',
                        listeners :{
                            'check':function(r,c){
                            	if (mchtGroupFlag2 == '7'){                   		
                            		r.setValue('1');
                            	}
        						}
                            }
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21Upd',
                        name: 'param21Upd',
                        disabled: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22Upd',
                        name: 'param22Upd',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23Upd',
                        name: 'param23Upd',
                        inputValue: '1'                      
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24Upd',
                        name: 'param24Upd',
                        inputValue: '1'
                        
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '脱机退货',
                        id: 'param25Upd',
                        name: 'param25Upd',
                        inputValue: '1'
                        
                     }]
            },{               
            	columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '理财POS',
                        id: 'param26Upd',
                        name: 'param26Upd',
                        inputValue: '1',
                        disabled:true,
                        listeners: {
      	                     'check': function() {
      	                    	var value26 = updTermForm.getForm().findField("param26Upd").checked;
      	                    		if(value26 == true){
      	                    		   updTermForm.getForm().findField("param2Upd").setValue(0);
                                       updTermForm.getForm().findField("param3Upd").setValue(0);
                                       updTermForm.getForm().findField("param4Upd").setValue(0);
                                       updTermForm.getForm().findField("param5Upd").setValue(0);
                                       updTermForm.getForm().findField("param6Upd").setValue(0);
                                       updTermForm.getForm().findField("param7Upd").setValue(0);
                                       updTermForm.getForm().findField("param8Upd").setValue(0);
                                       updTermForm.getForm().findField("param11Upd").setValue(0);
                                       updTermForm.getForm().findField("param12Upd").setValue(0);
                                       updTermForm.getForm().findField("param13Upd").setValue(0);
                                       updTermForm.getForm().findField("param14Upd").setValue(0);
                                       updTermForm.getForm().findField("param15Upd").setValue(0);
                                       updTermForm.getForm().findField("txn35Upd").setValue(0);
                                       updTermForm.getForm().findField("txn36Upd").setValue(0);
                                       updTermForm.getForm().findField("txn37Upd").setValue(0);
                                       updTermForm.getForm().findField("txn38Upd").setValue(0);
                                       updTermForm.getForm().findField("txn39Upd").setValue(0);
                                       updTermForm.getForm().findField("txn40Upd").setValue(0);
                                       updTermForm.getForm().findField("txn35Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("txn36Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("txn37Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("txn38Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("txn39Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("txn40Upd").setReadOnly(true);
                                       updTermForm.getForm().findField("param2Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param3Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param4Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param5Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param6Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param7Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param8Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param11Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param12Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param13Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param14Upd").setDisabled(true);
                                       updTermForm.getForm().findField("param15Upd").setDisabled(true);
      	                    		}else{
      	                    		   updTermForm.getForm().findField("txn35Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("txn36Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("txn37Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("txn38Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("txn39Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("txn40Upd").setReadOnly(false);
                                       updTermForm.getForm().findField("param2Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param3Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param4Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param5Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param6Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param7Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param8Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param11Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param12Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param13Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param14Upd").setDisabled(false);
                                       updTermForm.getForm().findField("param15Upd").setDisabled(false);
      	                    		}
      	                        }
                          }
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '积分交易',
                        id: 'param27Upd',
                        name: 'param27Upd',
                        inputValue: '1',
                        disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'termParaUpd',
                        name: 'termParaUpd'
                },{
                        xtype: 'hidden',
                        id: 'misc2Upd',
                        name: 'misc2Upd'
                }]
            }]
            }]
    });
	
//	Ext.getCmp('mchnNoU').on('select' ,function(){
//		T30101.getMchnt(Ext.getCmp("mchnNoU").getValue(),function(ret){
//            var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
//            mchtGroupFlag2 = mchntInfo.mchtGroupFlag;
//			if(mchtGroupFlag2 == '7'){
//            	Ext.getCmp('termTpU').setReadOnly(true);
//            }
//		});
//	});
/*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 450,
        width: 600,
        labelWidth: 100,
        waitMsgTarget: true,
        layout: 'column',
        items: [updTermPanel]
    });
   
/*******************  终端修改信息 *********************/
    var updTermWin = new Ext.Window({
        title: '终端修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 600,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
                updTermPanel.setActiveTab("info2Upd"); 
                updTermPanel.setActiveTab("info3Upd");
                if(updTermForm.getForm().isValid()) {
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T3010102Action.asp',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                        	updFlag = false;
                            showSuccessMsg(action.result.msg,updTermForm);
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            updTermPanel.setActiveTab('info3Upd');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                            txnId: '30101',
                            subTxnId: '02'
                        }
                    });
                }
                else
                {
                    var finded = true;
                    updTermForm.getForm().items.each(function(f){
                        if(finded && !f.validate()){
                            var tab = f.ownerCt.ownerCt.id;
                            if(tab == 'info1Upd' || tab == 'info2Upd' || tab == 'info3Upd' ){
                                 updTermPanel.setActiveTab(tab);
                            }
                            finded = false;
                        }
                    });
                }
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
                updFlag = false;
                updTermForm.getForm().reset();
            }
        }]
    });
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});