Ext.onReady(function() {
	//分公司数据集
	var fcompanyStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_NORMAL',function(ret){
		fcompanyStore.loadData(Ext.decode(ret));
	});
    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
   //省数据集
	var provinceStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('PROVINCES',function(ret){
		provinceStore.loadData(Ext.decode(ret));
	});
	//终端安装地所属地区代码
	var cityCodeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('TERMCITYCODE',function(ret){
		cityCodeStore.loadData(Ext.decode(ret));
	});
	//市数据集
	var citiesStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	//地区数据集
	var areasStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoScroll: true,
        //autoHeight: true,
        height : 400,
        labelWidth: 120,
        items: [/*new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号'
            }),*/{
                xtype: 'dynamicCombo',
                fieldLabel: '商户编号',
                //methodName: 'getMchntIdTerm',
                methodName: 'getMchntId',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 300,
				/*listeners: {
					'select': function() {
						Ext.getCmp('termNoQ').reset();
						Ext.getCmp('termNoQ').parentP=this.value;
						Ext.getCmp('termNoQ').getStore().reload();
					}
				}*/
                callFunction: function() {
					Ext.getCmp('termNoQ').reset();
					Ext.getCmp('termNoQ').parentP=this.value;
					Ext.getCmp('termNoQ').getStore().reload();
				}
           },{
               xtype: 'dynamicCombo',
               fieldLabel: '终端号',
               methodName: 'getTermIdAll',
               hiddenName: 'termNo',
               id: 'termNoQ',
               displayField: 'displayField',
               valueField: 'valueField',
               width: 300
          },{                                         
                xtype: 'combo',
                fieldLabel: '终端状态',
                id: 'state',
                name: 'state',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['2','修改未审核'],['3','停用未审核'],['5','恢复未审核'],['6','注销未审核']]
                })
        },
        {
            // xtype: 'basecomboselect',
         	xtype: 'dynamicCombo',
             fieldLabel: '终端类型',
           //  store:termTypeStore,
             methodName: 'getTermType',
             id:'termTpQ',
             name: 'termTpQ'
         },
         {
             //    xtype: 'basecomboselect',
             	xtype: 'dynamicCombo',
                 fieldLabel: '终端厂商',
             //    store:termFactoryStore,
                 methodName: 'getFACTORY',
                 id:'termFactoryQ',
                 name: 'termFactoryQ'
             },
             {
           	   // xtype: 'basecomboselect',
              	xtype: 'dynamicCombo',
                  fieldLabel: '连接类型',
                  id: 'connectModeQ',
              //    baseParams:'CONNECT_MODE',
                  methodName: 'getCONNTYPE',
                  hiddenName: 'connectModeQuery'
                  
              },
              {
                  //      xtype: 'basecomboselect',
                    	xtype: 'dynamicCombo',
                        fieldLabel: '产权属性',
                        id: 'propTpQ',
                    //    baseParams: 'PROP_TP',
                        methodName: 'getPROPTP',
                        hiddenName: 'propTpQuery'
                    },
                    {
                        //   xtype: 'basecomboselect',
                       	xtype: 'dynamicCombo',
                           fieldLabel: '终端所属分公司',
                        //   store:fcompanyStore,
                          methodName: 'getbrhId',
                           id:'CompQ',
                           name: 'CompQ'
                       },
                       {
                           //   xtype: 'combo',
                          	xtype: 'dynamicCombo',
                              fieldLabel: '支持交易',
                              id:'tradeTp',
                              name: 'tradeTp',
                              methodName: 'getTRADETP'
                              /*store: new Ext.data.ArrayStore({
                                  fields: ['valueField','displayField'],
                                  data: [['0','查询'],['1','预授权'],['2','预授权撤销'],['3','预授权完成联机'],['4','预授权完成撤销'],['5','消费'],['6','消费撤销 '],['7','退货']]
                              })*/
                          },
        {
            xtype: 'dynamicCombo',
            fieldLabel: '终端MCC',
            methodName: 'getAllMcc',
            displayField: 'displayField',
            valueField: 'valueField',
            editable: true,
            id: 'termMccQ',
            hiddenName: 'termMccQuery'
        },
        
        {
        	//   xtype: 'basecomboselect',
            	xtype: 'dynamicCombo',
               fieldLabel: '终端安装地所属省',
               id: 'province',
           //    store:provinceStore,
               methodName: 'getProvinces',
               name: 'province',
               /*listeners: {
					'select': function() {
						citiesStore.removeAll();
						Ext.getCmp('city').setValue('');
						areasStore.removeAll();
						Ext.getCmp('area').setValue('');
						
						SelectOptionsDWR.getComboDataWithParameter('CITIES',topQueryPanel.getForm().findField('province').getValue(),
						function(ret){	
							citiesStore.loadData(Ext.decode(ret));
						});
					}
				}*/
               callFunction : function() {
					Ext.getCmp('city').reset();
					Ext.getCmp('area').reset();
					Ext.getCmp('city').parentP=this.value;
					Ext.getCmp('city').getStore().reload();
					Ext.getCmp('area').getStore().reload();
				}
           },{
        	//   xtype: 'basecomboselect',
        	   xtype: 'dynamicCombo',
               fieldLabel: '终端安装地所属市',
               id:'city',
            //   store:citiesStore,
               methodName: 'getCitys',
               name: 'city',
              /* listeners: {
					'select': function() {
           	            areasStore.removeAll();
							Ext.getCmp('area').setValue('');
							SelectOptionsDWR.getComboDataWithParameter('AREAS',topQueryPanel.getForm().findField('city').getValue(),
								function(ret){
								areasStore.loadData(Ext.decode(ret));
							});
					}
				}*/
               callFunction : function() {
					Ext.getCmp('area').reset();
					Ext.getCmp('area').parentP=this.value;
					Ext.getCmp('area').getStore().reload();
				}
           },{
        //	   xtype: 'basecomboselect',
        	   xtype: 'dynamicCombo',
               fieldLabel: '终端安装地所属区/县',
         //      store:areasStore,
               methodName: 'getAreas',
               id:'area',
               name: 'area'
               /*listeners: {
					'select': function() {
						SelectOptionsDWR.getComboDataWithParameter('',topQueryPanel.getForm().findField('area').getValue(),
								function(ret){	});
					}
				}*/
           }/*,{
    	    xtype: 'dynamicCombo',
			    methodName: 'getAreaCode',
            fieldLabel: '终端安装地地区代码',
            name: 'cityCode',
            id: 'cityCode',
            displayField: 'displayField',
            valueField: 'valueField'
       }*/,{
           width: 150,
           xtype: 'datefield',
           fieldLabel: '添加起始时间',
           format : 'Y-m-d',
           name :'startTime',
           id :'startTime',
           anchor :'60%',
           listeners: {
				'select': function() {
					if(Ext.getCmp('endTime').getValue()!='' && 
							(Ext.getCmp('startTime').getValue().format('Ymd') > Ext.getCmp('endTime').getValue().format('Ymd'))){
						showAlertMsg("添加起始日期不能大于添加截止日期，请重新选择！",topQueryPanel);
						Ext.getCmp('startTime').setValue('');
						Ext.getCmp('endTime').setValue('');}
				}
			}
      },{                                         
           width: 150,
           xtype: 'datefield',
           fieldLabel: '添加截止时间',
           format : 'Y-m-d',
           name :'endTime',
           id :'endTime',
           anchor :'60%',
           listeners: {
				'select': function() {
					if(Ext.getCmp('startTime').getValue()!='' && 
							(Ext.getCmp('startTime').getValue().format('Ymd') > Ext.getCmp('endTime').getValue().format('Ymd'))){
						showAlertMsg("添加起始日期不能大于添加截止日期，请重新选择！",topQueryPanel);
						Ext.getCmp('startTime').setValue('');
						Ext.getCmp('endTime').setValue('');}
				}
			}
   }
      ,{
          width: 150,
          xtype: 'datefield',
          fieldLabel: '修改起始时间',
          format : 'Y-m-d',
          name :'UpdstartTime',
          id :'UpdstartTime',
          anchor :'60%',
          listeners: {
				'select': function() {
					if(Ext.getCmp('UpdendTime').getValue()!='' && 
							(Ext.getCmp('UpdstartTime').getValue().format('Ymd') > Ext.getCmp('UpdendTime').getValue().format('Ymd'))){
						showAlertMsg("修改起始日期不能大于修改截止日期，请重新选择！",topQueryPanel);
						Ext.getCmp('UpdstartTime').setValue('');
						Ext.getCmp('UpdendTime').setValue('');}
				}
			}
     },{                                         
          width: 150,
          xtype: 'datefield',
          fieldLabel: '修改截止时间',
          format : 'Y-m-d',
          name :'UpdendTime',
          id :'UpdendTime',
          anchor :'60%',
          listeners: {
				'select': function() {
					if(Ext.getCmp('UpdstartTime').getValue()!='' && 
							(Ext.getCmp('UpdstartTime').getValue().format('Ymd') > Ext.getCmp('UpdendTime').getValue().format('Ymd'))){
						showAlertMsg("修改起始日期不能大于修改截止日期，请重新选择！",topQueryPanel);
						Ext.getCmp('UpdstartTime').setValue('');
						Ext.getCmp('UpdendTime').setValue('');}
				}
			}
  }],
        buttons: [{
            text: '查询',
            handler: function() {
                termStore.load();
                queryWin.hide();
                grid.getTopToolbar().items.items[0].disable();
                grid.getTopToolbar().items.items[1].disable();
                grid.getTopToolbar().items.items[2].disable();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
                citiesStore.removeAll();
				Ext.getCmp('city').setValue('');
				areasStore.removeAll();
				Ext.getCmp('area').setValue('');
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
            {name: 'recCrtTs',mapping: 'recCrtTs'},
            {name: 'province',mapping: 'province'},
			{name: 'city',mapping: 'city'},
			{name: 'area',mapping: 'area'},
			{name: 'cityCode',mapping: 'cityCode'},
			{name: 'recCrtOpr',mapping: 'recCrtOpr'},
			{name: 'recDelTs',mapping: 'recDelTs'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recUpdTs',mapping: 'recUpdTs'}
        ]),
        autoLoad: true
    });
	
    termStore.on('beforeload', function() {
    	var selectedState = '012131516';
    	if(Ext.getCmp('state').getValue()!=null && Ext.getCmp('state').getValue()!=''){
    		selectedState = Ext.getCmp('state').getValue();
    	}
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            province: topQueryPanel.getForm().findField('province').getValue(),
            city: topQueryPanel.getForm().findField('city').getValue(),
            area: topQueryPanel.getForm().findField('area').getValue(),
    //        cityCode: topQueryPanel.getForm().findField('cityCode').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            
            termTpQ: topQueryPanel.getForm().findField('termTpQ').getValue(),
            termFactoryQ: topQueryPanel.getForm().findField('termFactoryQ').getValue(),
            connectModeQ: topQueryPanel.getForm().findField('connectModeQ').getValue(),
            propTpQ: topQueryPanel.getForm().findField('propTpQ').getValue(),
            CompQ: topQueryPanel.getForm().findField('CompQ').getValue(),
            tradeTp: topQueryPanel.getForm().findField('tradeTp').getValue(),
            termMccQ: topQueryPanel.getForm().findField('termMccQ').getValue(),
            
            UpdstartTime: topQueryPanel.getForm().findField('UpdstartTime').getValue(),
            UpdendTime: topQueryPanel.getForm().findField('UpdendTime').getValue(),
            
            termSta: selectedState
        });
    });
    
    var termColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {id: 'mchntNo',header: '商户编号',dataIndex: 'mchntName',width: 280},
        {header: '终端状态',dataIndex: 'termSta',width: 100,renderer: termSta},
        {header: '所属省',dataIndex: 'province',width: 90},
		{header: '所属市',dataIndex: 'city',width: 90},
		{header: '所属县/区',dataIndex: 'area',width: 90},
		{header: '所属地区代码',dataIndex: 'cityCode',width: 90,hidden:true},
		{header: '修改人',dataIndex: 'recCrtOpr',width: 90},
		{header: '修改时间',dataIndex: 'recDelTs',width: 120,renderer: formatTs}//,
		//{header: '审核人',dataIndex: 'recUpdOpr',width: 90},
		//{header: '审核时间',dataIndex: 'recUpdTs',width: 120,renderer: formatTs}
		/*,
        {header: '终端所属机构',dataIndex: 'termBranch'},
        {header: '终端库存编号',dataIndex: 'termIdId'},
        {header: '终端厂商',dataIndex: 'termFactory',width: 100},
        {header: '终端型号',dataIndex: 'termMachTp',width: 100}*/
    ]);
    
	var qryMenu = {
        text: '详细信息',
        width: 85,
        iconCls: 'edit',
        disabled: true,
        handler:function() {
            var rec = grid.getSelectionModel().getSelected();
            if(rec == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            selectTermInfo(rec.get('termId'),rec.get('recCrtTs'));	
        }	
    };
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认通过吗？',grid,function(bt) {
				if(bt == 'yes') {
					showMask('正在提交信息，请稍后......',grid);
					var rec = grid.getSelectionModel().getSelected();
                    if(rec == null) {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }  
					Ext.Ajax.requestNeedAuthorise({
						timeout: 600000,
						url: 'T30201Action.asp',
						params: {
							termId: rec.get('termId'),
                            recCrtTs: rec.get('recCrtTs'),
                            termSta: rec.get('termSta'),
                            state: rec.get('state'),
							txnId: '30201',
							subTxnId: '0'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
								hideMask();
							} else {
								showErrorMsg(rspObj.msg,grid);
								hideMask();
							}
							// 重新加载终端待审核信息
							grid.getStore().reload();
						}
					});
                    grid.getTopToolbar().items.items[0].disable();
	                grid.getTopToolbar().items.items[1].disable();
	                grid.getTopToolbar().items.items[2].disable();
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
            showConfirm('确认拒绝吗？',grid,function(bt) {
                if(bt == 'yes') {
                	showInputMsg('提示','请输入拒绝原因',true,refuse);
                }
            });
		}
	};
	
	// 终端拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
		    var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
		    Ext.Ajax.requestNeedAuthorise({
		        url: 'T30201Action.asp',
		        params: {
		            termId: rec.get('termId'),
		            recCrtTs: rec.get('recCrtTs'),
		            termSta: rec.get('termSta'),
		            txnId: '30201',
		            subTxnId: '1',
		            refuseInfo: text
		        },
		        success: function(rsp,opt) {
		            var rspObj = Ext.decode(rsp.responseText);
		            if(rspObj.success) {
		                showSuccessMsg(rspObj.msg,grid);
		            } else {
		                showErrorMsg(rspObj.msg,grid);
		            }
		            // 重新加载终端待审核信息
		            grid.getStore().reload();
		        }
		    });
		    hideProcessMsg();
            grid.getTopToolbar().items.items[0].disable();
            grid.getTopToolbar().items.items[1].disable();
            grid.getTopToolbar().items.items[2].disable();
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
	var menuArr = new Array();
	
	menuArr.push(acceptMenu);	//[0]
	menuArr.push(refuseMenu);	//[1]
	menuArr.push(qryMenu);      //[2]
	menuArr.push(queryMenu);    //[3]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端审核',
		iconCls: 'T30201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		//autoExpandColumn: 'mchntNo',
		forceValidation: true,
		tbar: menuArr,
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
			// 根据所选择的终端状态判断哪个按钮可用
			var rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[0].enable();
                grid.getTopToolbar().items.items[1].enable();
                grid.getTopToolbar().items.items[2].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
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
});