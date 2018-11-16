Ext.onReady(function() {
	var selectedRecord ;
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
    //专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
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
                  data: [['0','新增未审核'],['1','启用'],['2','修改未审核'],['3','停用未审核'],['4','停用'],['5','恢复未审核'],['6','注销未审核'],['7','注销'],['8','新增审核拒绝']]
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
              },/*,{
        	    xtype: 'dynamicCombo',
   			    methodName: 'getAreaCode',
	            fieldLabel: '终端安装地地区代码',
	            name: 'cityCode',
	            id: 'cityCode',
	            displayField: 'displayField',
	            valueField: 'valueField'
           }*/{
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
       }   
           
           ],
        buttons: [{
            text: '查询',
            handler: function()  {
                termStore.load();
                queryWin.hide();
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
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recCrtOpr',mapping: 'recCrtOpr'},
			{name: 'recDelTs',mapping: 'recDelTs'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recUpdTs',mapping: 'recUpdTs'}
		])
	});
	
	termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            termSta: Ext.getCmp('state').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            province: topQueryPanel.getForm().findField('province').getValue(),
            city: topQueryPanel.getForm().findField('city').getValue(),
            area: topQueryPanel.getForm().findField('area').getValue(),
     //       cityCode: topQueryPanel.getForm().findField('cityCode').getValue(),
            termTpQ: topQueryPanel.getForm().findField('termTpQ').getValue(),
            termFactoryQ: topQueryPanel.getForm().findField('termFactoryQ').getValue(),
            connectModeQ: topQueryPanel.getForm().findField('connectModeQ').getValue(),
            propTpQ: topQueryPanel.getForm().findField('propTpQ').getValue(),
            CompQ: topQueryPanel.getForm().findField('CompQ').getValue(),
            tradeTp: topQueryPanel.getForm().findField('tradeTp').getValue(),
            termMccQ: topQueryPanel.getForm().findField('termMccQ').getValue(),
            
            UpdstartTime: topQueryPanel.getForm().findField('UpdstartTime').getValue(),
            UpdendTime: topQueryPanel.getForm().findField('UpdendTime').getValue(),
            
            mchnNo: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
	termStore.load();
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 70},
		{header: '商户编号',dataIndex: 'mchntName',width: 280},
		{header: '终端状态',dataIndex: 'termSta',width: 90,renderer: termSta},
		{header: '所属省',dataIndex: 'province',width: 90},
		{header: '所属市',dataIndex: 'city',width: 90},
		{header: '所属县/区',dataIndex: 'area',width: 90},
		{header: '所属地区代码',dataIndex: 'cityCode',width: 90,hidden:true},
		{header: '添加日期',dataIndex: 'recCrtTs',width: 100,renderer: formatTs},
		{header: '修改人',dataIndex: 'recCrtOpr',width: 90},
		{header: '修改时间',dataIndex: 'recDelTs',width: 120,renderer: formatTs},
		{header: '审核人',dataIndex: 'recUpdOpr',width: 90},
		{header: '审核时间',dataIndex: 'recUpdTs',width: 120,renderer: formatTs}
		/*,{header: '终端所属机构',dataIndex: 'termBranch'},
		{header: '终端库存编号',dataIndex: 'termIdId'},
		{header: '终端厂商',dataIndex: 'termFactory',width: 150},
		{header: '终端型号',dataIndex: 'termMachTp',width: 150}*/
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin1.show();
			termWin1.center();
		}
	};
	var termInfoStore1 = new Ext.data.Store({
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
            {name: 'termTpU',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'termPara2Upd',mapping: 'termPara2'},
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
            {name: 'misc2U',mapping: 'misc2'},
            {name: 'misc3U',mapping: 'misc3'},
            {name: 'runMainId1U',mapping: 'runMainId1'},
            {name: 'runMainId2U',mapping: 'runMainId1'},
            {name: 'othSvrIdU',mapping: 'othSvrId'},
            {name: 'provinceUpd',mapping: 'province'},
            {name: 'cityUpd',mapping: 'city'},
            {name: 'areaUpd',mapping: 'area'},
            {name: 'cityCodeUpd',mapping: 'cityCode'},
            {name: 'productCdUpd',mapping: 'productCd'},
            {name: 'agentNo',mapping: 'agentNo'},
            {name: 'acqInstId',mapping: 'acqInstId'},
            {name: 'operNm',mapping: 'operNm'},
            {name: 'engName',mapping: 'engName'}
        ]),
        autoLoad: false
    });
    
    function praseTermParam(val) {
        //var array = val.split("|");
       // if(array[4] == undefined)
       //  	return 0;
       // array[4] = array[4].substring(2,array[4].length);
       
        //substring(strat(int),length(int));
        updTermForm.getForm().findField("txn11NewU").setValue(val.substring(2,2+2));//POS终端应用类型
        updTermForm.getForm().findField("txn12NewU").setValue(val.substring(6,6+2).trim());//超时时间
        updTermForm.getForm().findField("txn13NewU").setValue(val.substring(10,11).trim());//重播次数
        updTermForm.getForm().findField("txn14Upd").setValue(val.substring(13,13+14).trim());//交易电话1
        updTermForm.getForm().findField("txn15Upd").setValue(val.substring(29,29+14).trim());//交易电话2
        updTermForm.getForm().findField("txn16Upd").setValue(val.substring(45,45+14).trim());//交易电话3
        updTermForm.getForm().findField("txn20NewU").setValue(val.substring(84,84+1));//是否支持手工输入卡号
        updTermForm.getForm().findField("txn21NewU").setValue(val.substring(87,87+1));//是否自动签退
        
        //商户名有中文,需对中文名特别计算长度,一个中文算2个长度
        var j=90;
        var n=0;
        while(n<40){
        	if(val.charCodeAt(j)>255)
        		n+=2;
        	else
        		n++;
        	j++;	
        }
        updTermForm.getForm().findField("txn22Upd").setValue(val.substring(90,j).trim());//商户名
        updTermForm.getForm().findField("txn23NewU").setValue(val.substring(j+2,2+j+1).trim());//冲正重发次数
        
        T30101.getTermParam(val.substring(j+8,j+10),function(ret){
        var termParam = ret;
        for(var i=0;i<=7;i++){
        	updTermForm.getForm().findField('txn25New'+(i+1)+'U').setValue(termParam.substring(i,i+1));
            //updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }});
        
        T30101.getTermParam(val.substring(j+12,j+20),function(ret){
            var termParam = ret;
        for(var i=0;i<=12;i++){
        	 
            updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }});
        updTermForm.getForm().findField("txn27NewU").setValue(val.substring(j+22,j+22+1));//热敏打印联数
        updTermForm.getForm().findField("txn28NewU").setValue(val.substring(j+25,j+25+1));//是否屏蔽卡号
        updTermForm.getForm().findField("txn29NewU").setValue(val.substring(j+28,j+28+1));//是否显示LOGO
        updTermForm.getForm().findField("txn30NewU").setValue(val.substring(j+31,j+31+2));//快捷交易类型
        
        /*updTermForm.getForm().findField("txn35Upd").setValue(array[12].substring(2,array[12].length).trim());
        updTermForm.getForm().findField("txn36Upd").setValue(array[13].substring(2,array[13].length).trim()/100);
        updTermForm.getForm().findField("txn37Upd").setValue(array[14].substring(2,array[14].length).trim()/100);
        updTermForm.getForm().findField("txn38Upd").setValue(array[15].substring(2,array[15].length).trim()/100);
        updTermForm.getForm().findField("txn39Upd").setValue(array[16].substring(2,array[16].length).trim()/100);
        updTermForm.getForm().findField("txn40Upd").setValue(array[17].substring(2,array[17].length).trim());*/
       // var value = Ext.getCmp('termTpU').getValue();
       /* if(value == '1')  {
           Ext.getCmp('accountBox2').show();
           Ext.getCmp('financeCard1Upd').allowBlank = false;
           updTermForm.getForm().findField("termTpU").setReadOnly(true);
       } else {
           Ext.getCmp('accountBox2').hide();
           Ext.getCmp('financeCard1Upd').allowBlank = true;
           updTermForm.getForm().findField("termTpU").setReadOnly(false);
       }*/
       // });
    }
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            
            termInfoStore1.load({
                params: {
                     storeId: 'getTermInfoEdit',
                     termId: selectedRecord.get('termId'),
                     recCrtTs: selectedRecord.get('recCrtTs')
                },
                callback: function(records, options, success){
                	
                    if(success){       
                       
                       updTermForm.getForm().loadRecord(records[0]);
                       var termPara = updTermForm.getForm().findField("termParaUpd").value;
//                       var cityUpd12 = records[0].data.cityUpd;
                       
                      /* var args = Ext.getCmp('propTpU').getValue();
                       if(args == '2') {
                            Ext.getCmp("termPara1Upd").show();
                            Ext.getCmp("flagBox2").show();
                        }else{
                            Ext.getCmp("termPara1Upd").hide();
                            Ext.getCmp("flagBox2").hide();
                        }*/
                       praseTermParam(termPara);
                        //初始化省、市、县信息
                      /* SelectOptionsDWR.getComboDataWithParameter('CITIES',updTermForm.getForm().findField('idcityUpd').getValue(),
							function(ret){
								citiesStore.loadData(Ext.decode(ret));
						});*/
								
                       /*SelectOptionsDWR.getComboDataWithParameter('AREAS',updTermForm.getForm().findField('idcityUpd').getValue(),
							function(ret){
								areasStore.loadData(Ext.decode(ret));
								Ext.getCmp('idareaUpd').setValue(updTermForm.getForm().findField('idareaUpd').getValue());
						});*/
                        //初始化终端厂商，终端型号信息
                       /*SelectOptionsDWR.getComboDataWithParameter('TERMMACHTP',termInfoStore1.getAt(0).data.termFactoryUpd,
   							function(ret){
   								Ext.getCmp('idtermMachTpUpd').getStore().loadData(Ext.decode(ret));
   								var rec = Ext.getCmp('idcity').getStore().getAt(0);
   								Ext.getCmp('idcity').setValue(rec.get('valueField'));
   								Ext.getCmp('idtermMachTpUpd').setValue(updTermForm.getForm().findField('idtermMachTpUpd').getValue());
   								Ext.getCmp('idtermMachTpUpd').parentP=termInfoStore1.getAt(0).data.termFactoryUpd;
   						});*/
                      	//Ext.getCmp("productCdUpdS").setValue(updTermForm.getForm().findField('productCdUpd').getValue());
                       
                      	updTermPanel.setActiveTab('info1Upd');
                      	Ext.getCmp('termPara2Upd').setValue(updTermForm.getForm().findField("termPara2Upd").value.trim());
                      	
                      	//终端下载参数默认置1
                      	Ext.getCmp('paramDownSignUpd').checked = true;
                      	//Ext.getCmp("paramDownSignUpd").getEl().dom.readOnly = false;
                      	updTermWin.show();
                      	T30101.getMchtSupp1(selectedRecord.get('mchntNo'),function(ret){
                            var mchtSupp1 = Ext.decode(ret.substring(1,ret.length-1));
                            	 var preAuthor = mchtSupp1.preAuthor;  //预授权功能  0-不可用  1-可用
                            	 var returnFunc = mchtSupp1.returnFunc;    //退货功能  0-不可用   1-可用
                                //预授权 param2New       checked:true
                            	 if( preAuthor == '0'){
                            		 Ext.getCmp('param2Upd').setValue(false);
                            		 Ext.getCmp('param3Upd').setValue(false);
                            		 Ext.getCmp('param4Upd').setValue(false);
                            		 Ext.getCmp('param5Upd').setValue(false);
                            		Ext.getCmp('param2Upd').disable();
                            		Ext.getCmp('param3Upd').disable();
                            		Ext.getCmp('param4Upd').disable();
                            		Ext.getCmp('param5Upd').disable();
                                }else if(preAuthor == '1'){
                                	Ext.getCmp('param2Upd').enable();
                                	Ext.getCmp('param3Upd').enable();
                                	Ext.getCmp('param4Upd').enable();
                                	Ext.getCmp('param5Upd').enable();
                              // 	   Ext.getCmp('param2Upd').setValue(true);
                            //   	Ext.getCmp('param3Upd').setValue(true);
                            //   	Ext.getCmp('param4Upd').setValue(true);
                              // 	Ext.getCmp('param5Upd').setValue(true);
                                }
                                //退货功能  param8New
                                if(returnFunc == '0'){
                                	Ext.getCmp('param8Upd').setValue(false);
                               	 Ext.getCmp('param8Upd').disable();
                                }else if(returnFunc == '1'){
                                	Ext.getCmp('param8Upd').enable();
                             //  	  Ext.getCmp('param8Upd').setValue(true);
                               	 
                                }
                        });
                      	
                    }else{
                       updTermWin.hide();
                    }
                }
            });
		}
	}
    var copyMenu = {
        text: '复制',
        width: 85,
        iconCls: 'copy',                                     
        disabled: true,
        handler:function() {
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null) {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            showConfirm('确定要复制吗？',grid,function(bt) {
                if(bt == 'yes') {
//                    showProcessMsg('正在提交信息，请稍后......');
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
    
    var qryMenu = {
        text: '详细信息',
        width: 85,
        iconCls: 'detail',
        disabled: true,
        handler:function() {
            var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            selectTermInfo(rec.get('termId'),rec.get('recCrtTs'));	
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
	
    // 注销
		var delMenu = {
			text : '注销',
			width : 85,
			iconCls : 'recycle',
			disabled : true,
			handler : function() {
				if (grid.getSelectionModel().hasSelection()) {
					var rec = grid.getSelectionModel().getSelected();

					showConfirm('确定要注销该条记录吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.requestNeedAuthorise( {
								url : 'T3010104Action.asp?method=delete',
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, grid);
										grid.getStore().reload();
										grid.getTopToolbar().items.items[3].disable();
									} else {
										showErrorMsg(rspObj.msg, grid);
									}
								},
								params : {
									recCrtTs: grid.getSelectionModel().getSelected().get('recCrtTs'),
									termId: grid.getSelectionModel().getSelected().get('termId'),
									termSta:grid.getSelectionModel().getSelected().get('termSta'),
									txnId: '30101',
									subTxnId: '08'
								}
							});
						}
					});
				}
			}
		};
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]
	menuArr.push(queryMenu);	//[1]
	menuArr.push(editMenu);		//[2]
	menuArr.push(qryMenu);      //[3]
    menuArr.push(delMenu);      //[4]
	
    // 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息维护',
		iconCls: 'T301',
		region:'center',
		frame: true,
		border: true,
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
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[3].disable();
		grid.getTopToolbar().items.items[4].disable();
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			if(rec.get('termSta') == '0' || rec.get('termSta') == '1' || rec.get('termSta') == '2' || rec.get('termSta') == '9'
				|| rec.get('termSta') == 'A'|| rec.get('termSta') == 'B' || rec.get('termSta') == 'C'|| rec.get('termSta') == 'D') {
				grid.getTopToolbar().items.items[2].enable();
			} else {
				grid.getTopToolbar().items.items[2].disable();
			}
			grid.getTopToolbar().items.items[3].enable();
//			grid.getTopToolbar().items.items[4].enable();
			if(rec.get('termSta') == '1'){
				grid.getTopToolbar().items.items[4].enable();
			}else{
			    grid.getTopToolbar().items.items[4].disable();
			}
           /* if(rec.get('termSta') == '1')
            {
                grid.getTopToolbar().items.items[3].enable();
            } else {
                grid.getTopToolbar().items.items[3].disable();
            }*/
		}
	});
	
	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW',function(ret){
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

	
	//新增  表单
     var termPanel1 = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
        width: 650,
        labelWidth: 120,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                items: [{
                 columnWidth: .7,
                 layout: 'form',
                 items: [{
                    xtype: 'dynamicCombo',
                    fieldLabel: '商户编号*',
                    methodName: 'getMchntId',
                    hiddenName: 'mchnNoNew',
                    id: 'mchnNoN',
                    displayField: 'displayField',
                    valueField: 'valueField',
//                    blankText: '商户编号不能为空',
                    allowBlank: false,
//                    emptyText: '请选择商户编号',
                    width: 300,
                    callFunction:function() {
//	                	 if(Ext.getCmp("mchnNoN").getValue()!='' && Ext.getCmp("termMccNew").getValue()=='' 
//	  					&& Ext.getCmp("txn22New").getValue()=='' && Ext.getCmp("engName").getValue()==''){
	                         T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
	                             var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
	                             	Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);  //终端MCC码
	                             	Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);  //商户简称
	                             	Ext.getCmp("engName").setValue(mchntInfo.engName);   //商户英文名
	                             	Ext.getCmp("termBranchNew").setValue(mchntInfo.acqInstId);  //终端所属分行
//	                             	Ext.getCmp("agentNoInfo").setValue(mchntInfo.agentNo);//代理商信息
	                             	Ext.getCmp("acqInstIdInfo").setValue(mchntInfo.acqInstId);//分公司信息
//	                             	Ext.getCmp("operNmInfo").setValue(mchntInfo.operNm);//客户经理信息
	                             Ext.getCmp('accountBox1').hide();
	                             termForm1.getForm().findField("financeCard1New").setValue("");  //财务帐号1
	                             termForm1.getForm().findField("financeCard1New").setReadOnly(false);
	                             Ext.getCmp('financeCard1New').allowBlank = true;
	                         });
//	             	      }
	                         T30101.getMchtSupp1(Ext.getCmp("mchnNoN").getValue(),function(ret){
	                             var mchtSupp1 = Ext.decode(ret.substring(1,ret.length-1));
	                             	 var preAuthor = mchtSupp1.preAuthor;  //预授权功能  0-不可用  1-可用
	                             	 var returnFunc = mchtSupp1.returnFunc;    //退货功能  0-不可用   1-可用	                             	  
	                                 //预授权 param2New       checked:true
	                             	 if( preAuthor == '0'){
	                             		Ext.getCmp('param2New').setValue(false);
	                             		Ext.getCmp('param3New').setValue(false);
	                             		Ext.getCmp('param4New').setValue(false);
	                             		Ext.getCmp('param5New').setValue(false);
	                             		 Ext.getCmp('param2New').disable();
	                             		Ext.getCmp('param3New').disable();
	                             		Ext.getCmp('param4New').disable();
	                             		Ext.getCmp('param5New').disable();
	                                 }else if(preAuthor == '1'){
	                                	 Ext.getCmp('param2New').enable();
	                                	 Ext.getCmp('param3New').enable();
	                                	 Ext.getCmp('param4New').enable();
	                                	 Ext.getCmp('param5New').enable();
	                                	 Ext.getCmp('param2New').setValue(true);
	                                	 Ext.getCmp('param3New').setValue(true);
	                                	 Ext.getCmp('param4New').setValue(true);
	                                	 Ext.getCmp('param5New').setValue(true);
	                                 }
	                                 //退货功能  param8New
	                                 if(returnFunc == '0'){
	                                	 Ext.getCmp('param8New').setValue(false);
	                                	 Ext.getCmp('param8New').disable();
	                                 }else if(returnFunc == '1'){
	                                	 Ext.getCmp('param8New').enable();
	                                	 Ext.getCmp('param8New').setValue(true);
	                                	 
	                                 }
	                             
	                                /*  var value2 = Ext.getCmp('termTpN').getValue();
		                            if( value2 == '3'){
		                            	Ext.getCmp('txn14New').allowBlank=false;
		                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
		                            	label.dom.innerHTML ='绑定电话1*'; 
		                              //  showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
		                            }else{
		                            	Ext.getCmp('txn14New').allowBlank = true;
		                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
		                            	label.dom.innerHTML ='绑定电话1';
		                            }
		    				}*/
	                         });  
    				}
//                    listeners: {
//                     'select': function() {
////                	 if(Ext.getCmp("mchnNoN").getValue()!='' && Ext.getCmp("termMccNew").getValue()=='' 
////     					&& Ext.getCmp("txn22New").getValue()=='' && Ext.getCmp("engName").getValue()==''){
//                            T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
//                                var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
//                                	Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
//                                	Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);
//                                	Ext.getCmp("engName").setValue(mchntInfo.engName);
//                                	Ext.getCmp("termBranchNew").setValue(mchntInfo.acqInstId);
//                                Ext.getCmp('accountBox1').hide();
//                                termForm1.getForm().findField("financeCard1New").setValue("");
//                                termForm1.getForm().findField("financeCard1New").setReadOnly(false);
//                                Ext.getCmp('financeCard1New').allowBlank = true;
//                            });
////                	      }
//                        }
//                    }
                  }]
             },{
                 columnWidth: .8, 
                 layout: 'form',
                 hidden:true,
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '商户英文名',
                     id:'engName',
                     name: 'engName',
                     readOnly: true,
                     width: 300
                 }]
             },/*{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                	 xtype: 'basecomboselect',
                     fieldLabel: '代理商信息',
                     id:'agentNoInfo',
                     baseParams:'AGENT_NORMAL',
                     emptyText:' ',
             //        name: '',
                     readOnly: true,
                     width: 317
                 }]
             },*/{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                	 xtype: 'basecomboselect',
                     fieldLabel: '分公司信息',
                     baseParams:'BRANCH_NO',
                     emptyText:' ',
                     id:'acqInstIdInfo',
            //         name: '',
                     readOnly: true,
                     width: 317
                 }]
             },/*{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '客户经理信息',
                     id:'operNmInfo',
            //         name: '',
                     readOnly: true,
                     width: 300
                 }]
             },*/{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户简称*',
                        id: 'txn22New',
                        allowBlank: false,
                        name: 'txn22New',
                   //     readOnly: true,
                        width: 300
                    }]
            },/*{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'textfield',
                    fieldLabel: '终端号*',
                    name: 'termIdAdd',
                    id: 'termIdAdd',
                    emptyText:'请输入终端号',
                    allowBlank: false,
                    maxLength:8,
                    minLength:8,
                    regex:/^[0-9]+$/,
					//regexText:'必须是8位数字',
					vtype: 'isOverMax'
                 }]
            },*/{
                columnWidth: .5,
                layout: 'form',
                items: [{
        //            xtype: 'basecomboselect',
                	xtype:'dynamicCombo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
//                    emptyText:'请选择产权属性',
                    allowBlank: false,
                    hiddenName: 'propTpNew',
            //        baseParams:'PROP_TP',
                    methodName: 'getPROPTP'
                    /*store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','德益富支付'],['1','商户'],['2','第三方投入'],['3','个人']]
                    }),*/
                    /*listeners:{
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
                   }*/
                    /*callFunction : function() {
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
    				}*/
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
                 hidden: true,
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属分行*',
                    id: 'termBranchNew',
                    hiddenName: 'brhIdNew',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    width: 130,
                    blankText: '终端所属分行不能为空' 
                 }]
            }
            ,{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 30,
//                    emptyText:'请输入联系电话',
                    allowBlank: false,
                    type: 'isOverMax',
                    //regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelNew',
                    name: 'contTelNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype:'textfield',
                    fieldLabel: '联系人',
                    id: 'termPara2New',
                    allowBlank: true,
                    maxLength: 100,
					vtype: 'isOverMax',
                    hiddenName: 'termPara2New'
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
                //    xtype: 'combo',
                	xtype: 'dynamicCombo',
                    fieldLabel: '终端厂商*',
//                    emptyText:'请选择终端厂商',
                    id: 'idtermFactory',
                   // value: 2,
                    allowBlank: false,
               //     store:termFactoryStore,//在systemSuport.js 中定义的常量
                    methodName: 'getFACTORY',
                    hiddenName: 'termFactoryNew',
                 /*   store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['A1000000','A1000000-新大陆'],['A2000000','A2000000-新国都']]
                    })*/
                    callFunction : function() {
    					Ext.getCmp('idtermMachTp').reset();
    					Ext.getCmp('idtermMachTp').parentP=this.value;
    					Ext.getCmp('idtermMachTp').getStore().reload();
    				}
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'dynamicCombo',
                    fieldLabel: '终端型号*',
                    id: 'idtermMachTp',
              //      name:'termMachTp',
                    allowBlank: false,
                    methodName: 'getTermMachTp',
                    hiddenName: 'termMachTpNew'
                   
                }]
            },/*{
                columnWidth: .5,
                layout: 'form',
                items: [{
                //    xtype: 'basecomboselect',
                	xtype: 'dynamicCombo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeN',
                   // value: 2,
//                    emptyText:'请选择连接类型',
              //      allowBlank: false,
              //      baseParams:'CONNECT_MODE',
                    methodName: 'getCONNTYPE',
                    hiddenName: 'connectModeNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
                    })
                }]
            }*/{
                columnWidth: .5,
                layout: 'form',
                items: [{
                 //       xtype: 'basecomboselect',
                	    xtype: 'dynamicCombo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpN',
	                    allowBlank: false,
	                    hiddenName: 'termTpNew',
	                    width:150,
	            //        store: termTypeStore,
	                    methodName: 'getTermType'
	                   /* listeners: {
	                     'select': function() {
                            var value2 = Ext.getCmp('termTpN').getValue();
                            if( value2 == '3'){
                            	Ext.getCmp('txn14New').allowBlank=false;
                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
                            	label.dom.innerHTML ='绑定电话1*'; 
                              //  showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
                            }else{
                            	Ext.getCmp('txn14New').allowBlank = true;
                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
                            	label.dom.innerHTML ='绑定电话1';
                            }
	                    }
                    }*/
	                   /* callFunction : function() {
	                    	 var value2 = Ext.getCmp('termTpN').getValue();
	                            if( value2 == '3'){
	                            	Ext.getCmp('txn14New').allowBlank=false;
	                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
	                            	label.dom.innerHTML ='绑定电话1*'; 
	                              //  showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
	                            }else{
	                            	Ext.getCmp('txn14New').allowBlank = true;
	                            	var label =Ext.getCmp('txn14New').getEl().parent().parent().first();   
	                            	label.dom.innerHTML ='绑定电话1';
	                            }
	    				}*/
                 }]
           },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    //xtype: 'combo',
                	xtype:'textfield',
                    fieldLabel: '产权所属机构名称',
                   // store: organStore,
                    id: 'propInsNmNew',
                    //allowBlank: false,
                    maxLength: 40,
					vtype: 'isOverMax',
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
              //  	xtype:'textfield',
                	xtype: 'dynamicCombo',
                    fieldLabel: '安装维护机构',
             //       id: 'misc3',
//                    emptyText:'请输入安装维护机构',
           //         allowBlank: false,
                    maxLength: 40,
                    methodName: 'getNormalAgentID',
					vtype: 'isOverMax',
                    hiddenName: 'misc3'
                    
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '安装维护员',
                    id: 'misc2',
                    allowBlank: true,
                    maxLength: 40,
					vtype: 'isOverMax',
                    hiddenName: 'misc2'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '运行维护方代码1',
                    id: 'idrunMainId1',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId1' 
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '运行维护方代码2',
                    id: 'idrunMainId2',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId2'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '其他服务方代码',
                    id: 'idothSvrId',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'othSvrId'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                  //  xtype: 'basecomboselect',
                	xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属省*',
                    id: 'idprovince',
                    allowBlank: false,
//                    emptyText:'请选择终端安装地所属省',
     //               store:provinceStore,
                    methodName: 'getProvinces',
                    hiddenName: 'province',
                   /* listeners: {
    					'select': function() {
							citiesStore.removeAll();
							Ext.getCmp('idcity').setValue('');
							
							areasStore.removeAll();
							Ext.getCmp('idarea').setValue('');
							
							SelectOptionsDWR.getComboDataWithParameter('CITIES',termForm1.getForm().findField('province').getValue(),
							function(ret){	
								citiesStore.loadData(Ext.decode(ret));
							});
    					}
    				}*/
                    callFunction : function() {
    					Ext.getCmp('idcity').reset();
    					Ext.getCmp('idarea').reset();
    					Ext.getCmp('idcity').parentP=this.value;
//    					Ext.getCmp('idprovince').getStore().reload();
    					Ext.getCmp('idcity').getStore().reload();
    					Ext.getCmp('idarea').getStore().reload();
    				}
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
          //          xtype: 'basecomboselect',
                	  xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属市*',
                    id:'idcity',
                    allowBlank: false,
//                    emptyText:'请选择终端安装地所属市',
      //              store:citiesStore,
                    methodName: 'getCitys',
                    hiddenName: 'city',
                    /*listeners: {
    					'select': function() {
            	            areasStore.removeAll();
							Ext.getCmp('idarea').setValue('');
							SelectOptionsDWR.getComboDataWithParameter('AREAS',termForm1.getForm().findField('city').getValue(),
								function(ret){
								areasStore.loadData(Ext.decode(ret));
							});
    					}
    				}*/
                    callFunction : function() {
    					Ext.getCmp('idarea').reset();
    					Ext.getCmp('idarea').parentP=this.value;
    					Ext.getCmp('idarea').getStore().reload();
    				}
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
               //     xtype: 'basecomboselect',
                    xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属区/县',
             //       store:areasStore,
                    methodName: 'getAreas',
                    id:'idarea',
                    hiddenName: 'area'
                   // width: 200
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址*',
                    maxLength: 100,
                    allowBlank:false,
                    id: 'termAddrNew',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                hidden:true,
                items: [{
						/*xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						methodName: 'getAreaCode',
						fieldLabel: '终端安装地地区代码*',
						id:'idcityCode',
						name:'cityCode',
						hiddenName: 'cityCode',
						allowBlank: false,
						editable: true,
						anchor: '90%'*/
                    xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地地区代码*',
//                    emptyText:'请选择终端安装地地区代码',
                    methodName: 'getAreaCode',
                    id:'idcityCode',
                    hiddenName: 'cityCode'
          //          allowBlank: false
                    //width: 200
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                id: 'info2New',
                items: [{
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
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1',
                    maxLength: 15,
                   // allowBlank: false,
                    vtype: 'isNumber',
                    id: 'txn14New',
                    name: 'txn14New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
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
                    fieldLabel: '绑定电话3',
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
                    fieldLabel: 'NAC 电话1',
                    maxLength: 15,
                    //allowBlank: false,
                    vtype: 'isNumber',
                    id: 'bindTel1New',
                    name: 'bindTel1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
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
                    fieldLabel: 'NAC 电话3',
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
                    checked:true/*,
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignNew',
                    name: 'paramDownSignNew',
                    disabled: true,
                    inputValue: '1',
                    checked:true/*,
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
//                hidden:true,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignNew',
                    name: 'icDownSignNew',
                    inputValue: '1',
                    checked:true/*,
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    inputValue: '1',
                    listeners :{
                    'check':function(r,c){
						//r.setValue('1');
                		if(Ext.getCmp('reserveFlag1New').checked==true)
							Ext.getCmp('txn14New').allowBlank=false;
						else
							Ext.getCmp('txn14New').allowBlank=true;
						}
                    }
                }]
            }/*,{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '中行终端主密钥*',
                    id: 'productCdNew',
                    name: 'productCdNew',
                    allowBlank: false,
                    maskRe: /^[0-9]|[a-f]|[A-F]$/,
                    maxLength:32,
                    minLength:32,
                    width:200
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '再次输入中行终端主密钥*',
                    id: 'productCdSec',
                    name: 'productCdSec',
                    allowBlank: false,
                    maskRe: /^[0-9]|[a-f]|[A-F]$/,
                    maxLength:32,
                    minLength:32,
                    width:200
                }]
            }*/]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                items: [{
		                hidden:true,
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
		                hidden:true,	
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
		                hidden:true,	
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
		                hidden:true,	
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
		                hidden:true,
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
		                hidden:true,
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
                },/*{     
                	   xtype: 'checkboxgroup',
                	   id:'info3New',
//                	   name:'info3NewGroup',
                	   name:'info3New',
                	//   layout: 'column',
                	   columns: 3,
//                	   allowBlank: false,
                	   fieldLabel: '交易信息',  
                	   items: [*/{
                
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        fieldLabel: '查询 ',
                	                        id: 'param1New',
                	                        name: 'param1New',
                	                        anchor: '90%',
                	                        inputValue: '1',
                	                        checked:true
                	           //             checked:true
                	                    }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '预授权 ',
                	                        fieldLabel: '预授权 ',
                	                        disabled: true,
                	                        id: 'param2New',
                	                        name: 'param2New',
                	                        inputValue: '1'
                	                    }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '预授权撤销 ',
                	                        fieldLabel: '预授权撤销 ',
                	                        disabled: true,
                	                        id: 'param3New',
                	                        name: 'param3New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '预授权完成联机 ',
                	                        disabled: true,
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
//                	                        fieldLabel: '预授权完成撤销 ',
                	                        disabled: true,
                	                        fieldLabel: '预授权完成撤销 ',
                	                        id: 'param5New',
                	                        name: 'param5New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        fieldLabel: '消费 ',
                	                        id: 'param6New',
                	                        name: 'param6New',
                	                        inputValue: '1',
                	                        checked:true
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        fieldLabel: '消费撤销 ',
                	                        id: 'param7New',
                	                        name: 'param7New',
                	                        inputValue: '1',
                	                        checked:true
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        disabled: true,
//                	                        fieldLabel: '退货 ',
                	                        fieldLabel: '退货 ',
                	                        id: 'param8New',
                	                        name: 'param8New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                hidden:true,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '分期付款消费 ',
                	                        fieldLabel: '分期付款消费 ',
                	                        id: 'param9New',
                	                        name: 'param9New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                hidden:true,
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '分期付款撤销 ',
                	                        fieldLabel: '分期付款撤销 ',
                	                        id: 'param10New',
                	                        name: 'param10New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                layout: 'form',
                	                hidden:true,
                	                items: [{
                	                        xtype: 'checkbox',
//                	                        fieldLabel: '分期付款退货',
                	                        fieldLabel: '分期付款退货',
                	                        id: 'param11New',
                	                        name: 'param11New',
                	                        inputValue: '1'
                	                     }]
                	                },{
                	                columnWidth: .3,
                	                hidden:true,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        fieldLabel: '电子现金消费',
                	                        id: 'param12New',
                	                        name: 'param12New',
                	                        inputValue: '1'
                	                     }]
                	                }  ,{
                	                columnWidth: .3,
                	                hidden:true,
                	                layout: 'form',
                	                items: [{
                	                        xtype: 'checkbox',
                	                        fieldLabel: '电子现金退货',
                	                        id: 'param13New',
                	                        name: 'param13New',
                	                        inputValue: '1'
                	                     }]
                	                }    ,
                //	       ]   
               // 	 },
//                 {
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '查询 ',
//                        id: 'param1New',
//                        name: 'param1New',
//                        inputValue: '1'
//                    }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '预授权 ',
//                        id: 'param2New',
//                        name: 'param2New',
//                        inputValue: '1'
//                    }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '预授权撤销 ',
//                        id: 'param3New',
//                        name: 'param3New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '预授权完成联机 ',
//                        id: 'param4New',
//                        name: 'param4New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '预授权完成撤销 ',
//                        id: 'param5New',
//                        name: 'param5New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '消费 ',
//                        id: 'param6New',
//                        name: 'param6New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '消费撤销 ',
//                        id: 'param7New',
//                        name: 'param7New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '退货 ',
//                        id: 'param8New',
//                        name: 'param8New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '分期付款消费 ',
//                        id: 'param9New',
//                        name: 'param9New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '分期付款撤销 ',
//                        id: 'param10New',
//                        name: 'param10New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '分期付款退货',
//                        id: 'param11New',
//                        name: 'param11New',
//                        inputValue: '1'
//                     }]
//                },
//                {
//                columnWidth: .3,
//                hidden:true,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '个人卡转公司卡（财务POS） ',
//                        id: 'param12New',
//                        name: 'param12New',
//                        inputValue: '1'
//                     }]
//                },{
//                columnWidth: .3,
//                hidden:true,
//                layout: 'form',
//                items: [{
//                        xtype: 'checkbox',
//                        fieldLabel: '卡卡转帐',
//                        id: 'param13New',
//                        name: 'param13New',
//                        inputValue: '1'
//                     }]
//                },
                	
                {
                columnWidth: .3,
                hidden:true,
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
                hidden:true,
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
                hidden:true,
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
                hidden:true,
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
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18New',
                        name: 'param18New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                hidden:true,
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
                hidden:true,
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
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21New',
                        name: 'param21New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                hidden:true,
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
                hidden:true,
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
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24New',
                        name: 'param24New',
                        inputValue: '1'
                     }]
            }]
            },{
                title: '终端参数',
                id: 'info4New',
                layout: 'column',
                items: [{ columnWidth: 1},
                {
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '超时时间*',
                        id: 'txn12New',
                        allowBlank: false,
                        name: 'txn12New',
                       	vtype: 'isNumber',
                       	value : '70',
                       	maxLength:2,
                    	minLength:2
	                    }]
	            },{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
	                    xtype: 'combo',
	                    fieldLabel: '拨号重拨次数*',
	                    id: 'idtxn13New',
	                    value : '3',
	                    allowBlank: false,
	                    hiddenName: 'txn13New',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['3','3'],['5','5']]
	                    })
	           
	                }]
	            },{
	                columnWidth: .5,
	                hidden:true,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '是否支持小费 ',
	                        id: 'txn18New',
	                        name: 'txn18New',
	                        inputValue: '1'
	                     }]
	            },{
		             columnWidth: .5,
		              hidden:true,
	                 layout: 'form',
	                 items:[{
	                    xtype: 'textfield',
	                    fieldLabel: '小费百分比',
	                    name: 'txn19New',
	                    id: 'txn19New',
	                    vtype: 'isNumber',
	                    maxLength:2,
						vtype: 'isOverMax'
	                 }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: '冲正重发次数*',
	                    id: 'idtxn23New',
	                    allowBlank: false,
	                    value : '3',
	                    hiddenName: 'txn23New',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['3','3'],['5','5']]
	                    })
	           
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: 'POS终端应用类型*',
	                    id: 'idtxn11New',
	                    allowBlank: false,
	                    value : '11',
	                    hiddenName: 'txn11New',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['10','磁条卡金融支付类'],['11','IC卡金融支付类']]
	                    })
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                	xtype:'textfield',
	                    fieldLabel: '打印联数*',
	                    id: 'txn27New',
	                    value : '2',
	                    allowBlank: false,
	                    maxLength: 1,
						vtype: 'isNumber',
	                    hiddenName: 'txn27New'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'basecomboselect',
	                    fieldLabel: '快捷交易类型',
	                    id: 'idtxn30New',
	                    value : '06',
	                    allowBlank: true,
	                    store:Txn_NumStore,//在systemSuport.js 中定义的常量
	                    hiddenName: 'txn30New'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '支持手工输入卡号 ',
	                        hideLabel:true,
	                        id: 'txn20New',
	                        name: 'txn20New',
	                        inputValue: '1'
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否自动签退 ',
	                        hideLabel:true,
	                        id: 'txn21New',
	                        name: 'txn21New',
	                        inputValue: '1'
	                     }]
	            }, {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel : '消费撤销交易是否需要刷卡 ',
	                        hideLabel:true,
	                        id: 'txn25New1',
	                        name: 'txn25New1',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '消费撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New2',
	                        name: 'txn25New2',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易是否需要刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New3',
	                        name: 'txn25New3',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New4',
	                        name: 'txn25New4',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易是否需要输入刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New5',
	                        name: 'txn25New5',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New6',
	                        name: 'txn25New6',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },
	            
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成交易是否需刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New7',
	                        name: 'txn25New7',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成是否需输入密码',
	                        hideLabel:true,
	                        id: 'txn25New8',
	                        name: 'txn25New8',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },
	            
	            
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否屏蔽卡号',
	                        hideLabel:true,
	                        id: 'txn28New',
	                        name: 'txn28New',
	                        inputValue: '1',
	                        checked:true
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否显示LOGO',
	                        hideLabel:true,
	                        id: 'txn29New',
	                        name: 'txn29New',
	                        inputValue: '1'
	                     }]
	            }]
            }]
    });
    /**************  终端表单  *********************/
	var termForm1 = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 650,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel1]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin1 = new Ext.Window({
		title: '终端添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 700,
		autoHeight: true,
		layout: 'fit',
		items: [termForm1],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				var mchntNo = termForm1.getForm().findField('mchnNoN').getValue();
				//var termIdAdd = termForm1.getForm().findField('termIdAdd').getValue();
				var contTelNew = termForm1.findById('contTelNew').getValue();
				//var termPara2New = termForm1.findById('termPara2New').getValue();
				var propTpN = termForm1.getForm().findField('propTpN').getValue();
		//		var connectModeN = termForm1.getForm().findField('connectModeN').getValue();
				var idtermFactory = termForm1.getForm().findField('idtermFactory').getValue();
	//			var misc3 = termForm1.findById('misc3').getValue();
				var idprovince = termForm1.getForm().findField('idprovince').getValue();
				var idcity = termForm1.getForm().findField('idcity').getValue();
				var idcityCode = termForm1.getForm().findField('idcityCode').getValue();
				
				termForm1.getForm().findField('mchnNoN').focus(true, true);
	    		//termForm1.getForm().findField('termIdAdd').focus(true, true);
	    		termForm1.getForm().findField('contTelNew').focus(true, true);
	    		termForm1.getForm().findField('termPara2New').focus(true, true);
	    		termForm1.getForm().findField('propTpN').focus(true, true);
	  //  		termForm1.getForm().findField('connectModeN').focus(true, true);
	    		termForm1.getForm().findField('idtermFactory').focus(true, true);
	    	//	termForm1.getForm().findField('misc3').focus(true, true);
	    		termForm1.getForm().findField('idprovince').focus(true, true);
	    		termForm1.getForm().findField('idcity').focus(true, true);
	    		termForm1.getForm().findField('idcityCode').focus(true, true);
	    		
				if(mchntNo.trim() == ''){
					showAlertMsg('商户编号不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}
				/*if(termIdAdd.trim() == ''){
					showAlertMsg('终端号不能为空或空字符串，请填写',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}*/
				if(contTelNew.trim() == ""){
					showAlertMsg('联系电话不能为空或空字符串，请填写',termWin1);
					termPanel1.setActiveTab('info1New');
					return ;
				}
				/*if(termPara2New.trim() == ""){
					showAlertMsg('联系人不能为空或空字符串，请填写',termWin1);
					termPanel1.setActiveTab('info1New');
					return ;
				}*/
				if(propTpN.trim() == ''){
					showAlertMsg('产权属性不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}
			/*	if(connectModeN.trim() == ''){
					showAlertMsg('连接类型不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}*/
				if(idtermFactory.trim() == ''){
					showAlertMsg('终端厂商不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}
				/*if(misc3.trim() == ""){
					showAlertMsg('安装维护机构不能为空或空字符串，请填写',termWin1);
					termPanel1.setActiveTab('info1New');
					return ;
				}*/
				if(idprovince.trim() == ''){
					showAlertMsg('终端安装地所属省不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}
				if(idcity.trim() == ''){
					showAlertMsg('终端安装地所属市不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info1New');
		    		return;
		    	}
//				if(idcityCode.trim() == ''){
//					showAlertMsg('终端安装地地区代码不能为空，请选择',termForm1);
//		    		termPanel1.setActiveTab('info1New');
//		    		return;
//		    	}
				
				termPanel1.setActiveTab("info2New");
				var termTpN = termForm1.getForm().findField('termTpN').getValue();
				//var productCdNew = termForm1.getForm().findField('productCdNew').getValue();
				//var productCdSec = termForm1.getForm().findField('productCdSec').getValue();
				var txn14New = termForm1.getForm().findField('txn14New').getValue();
				
				termForm1.getForm().findField('termTpN').focus(true, true);
				//termForm1.getForm().findField('productCdNew').focus(true, true);
				//termForm1.getForm().findField('productCdSec').focus(true, true);
				termForm1.getForm().findField('txn14New').focus(true, true);
				
				if(termTpN.trim() == ''){
					showAlertMsg('终端类型不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info2New');
		    		return;
		    	}
				/*if(productCdNew.trim() == ''){
					showAlertMsg('中行终端主密钥不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info2New');
		    		return;
		    	}
				if(productCdSec.trim() == ''){
					showAlertMsg('再次输入中行终端主密钥不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info2New');
		    		return;
		    	}*/
				/*if(Ext.getCmp('txn14New').allowBlank==false && txn14New.trim() == ''){
					showAlertMsg('绑定电话1不能为空，请选择',termForm1);
		    		termPanel1.setActiveTab('info2New');
		    		return;
		    	}*/
				termPanel1.setActiveTab("info3New");
				
				termPanel1.setActiveTab("info4New");
				
				//终端下载参数不可修改
				//Ext.getCmp("paramDownSignNew").getEl().dom.readOnly = false;
				
				var info3Sign = "";	//标记是否曾加载交易权限界面20130919
				//若未跳转则该页面的默认值未生效，需要另外设置
				try{
					info3Sign = Ext.getCmp('param1New').getValue();
				}catch(e){
					info3Sign = "other";
				}
//				if(Ext.getCmp("mchnNoN").getValue()!='' && Ext.getCmp("termMccNew").getValue()=='' 
//					&& Ext.getCmp("txn22New").getValue()=='' && Ext.getCmp("engName").getValue()==''){
//					termPanel1.setActiveTab('info1New');
//					T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(),function(ret){
//						var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
//			            Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
//			            Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);
//			            Ext.getCmp("engName").setValue(mchntInfo.engName);
//			            
//			            Ext.getCmp("termBranchNew").setValue(mchntInfo.acqInstId);
//			            Ext.getCmp('accountBox1').hide();
//			            termForm1.getForm().findField("financeCard1New").setValue("");
//			            termForm1.getForm().findField("financeCard1New").setReadOnly(false);
//			            Ext.getCmp('financeCard1New').allowBlank = true;
//			        });
//				}
				
				if(termForm1.getForm().isValid()) {
					/*if(Ext.getCmp('productCdNew').getValue()!=Ext.getCmp('productCdSec').getValue()){
						showErrorMsg("两次输入终端主密钥不一致！",termWin1);
						termPanel1.setActiveTab('info2New');
						return;
					}*/
					termForm1.getForm().submitNeedAuthorise({
						url: 'T30101Action.asp',
						waitMsg: '正在提交，请稍后......',
						submitEmptyText: false,
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin1);
							//重新加载列表
							grid.getStore().reload();
							//重置表单
							termForm1.getForm().reset();
							termPanel1.setActiveTab('info1New');
                            termWin1.hide();
						},
						failure: function(form,action) {
//							termPanel1.setActiveTab('info3New');
							showErrorMsg(action.result.msg,termWin1);
						},
						params: {
							info3Sign : info3Sign,
							txnId: '30101',
							subTxnId: '01'
						}
					});
				}else{
                    var finded = true;
	                termForm1.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if( tab == 'info1New' || tab == 'info2New' ){
	                        	termPanel1.setActiveTab(tab);
	                        }
	                        if( tab == 'ext-comp-1120'){
	                        	termPanel1.setActiveTab(tab);
	                        }
	                        if( tab=='info4New' ){
	                        	termPanel1.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
				termForm1.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin1.hide();
			}
		}]
	});
/**************** 终端修改 *************************/
    var updTermPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 650,
        labelWidth:120,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1Upd',
                layout: 'column',
                items: [{
                 columnWidth: 1,
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
                 columnWidth: 1,
                 layout: 'form',
                 items: [{
                    xtype: 'dynamicCombo',
                    fieldLabel: '商户编号*',
                    hiddenName: 'mchnNoUpd',
                    id: 'mchnNoU',
                    methodName: 'getMchntId',
                    readOnly: true,
                    width: 300
                  }]
             },{
                 columnWidth: .8, 
                 layout: 'form',
                 hidden:true,
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '商户英文名',
           //          id: 'txn27Upd',
                     name: 'engName',
                     readOnly: true,
                     width: 300
                 }]
         },/*{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                	 xtype: 'basecomboselect',
                     fieldLabel: '代理商信息',
              //       id:'agentNoInfo',
                     baseParams:'AGENT_NORMAL',
                     emptyText:' ',
                     hiddenName:'agentNo',
             //        name: '',
                     readOnly: true,
                     width: 317
                 }]
             },*/{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                	 xtype: 'basecomboselect',
                     fieldLabel: '分公司信息',
                     baseParams:'BRANCH_NO',
                     emptyText:' ',
                     hiddenName:'acqInstId',
             //        id:'acqInstIdInfo',
            //         name: '',
                     readOnly: true,
                     width: 317
                 }]
             },/*{
                 columnWidth: .8, 
                 layout: 'form',
                 items: [{
                     xtype: 'textfield',
                     fieldLabel: '客户经理信息',
              //       id:'operNmInfo',
            //         name: '',
                     name:'operNm',
                     readOnly: true,
                     width: 300
                 }]
             },*/{
                    columnWidth: .8, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户简称*',
                        id: 'txn22Upd',
                        name: 'txn22Upd',
           //             readOnly: true,
                        width: 300
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
               //     xtype: 'basecomboselect',
                	xtype: 'dynamicCombo',
                    fieldLabel: '产权属性',
                    id: 'propTpU',
                    hiddenName: 'propTpUpd',
             //       baseParams:'PROP_TP',
                    methodName: 'getPROPTP'
                    /*listeners:{
                        'select': function() {
                            var args = Ext.getCmp('propTpU').getValue();
                            if(args == 2) {
                                Ext.getCmp("termPara1Upd").show();
                                Ext.getCmp("flagBox2").show();
                            }else{
                                Ext.getCmp("termPara1Upd").hide();
                                Ext.getCmp("flagBox2").hide();
                            }
                        }
                   }*/
               /* callFunction : function() {
                	var args = Ext.getCmp('propTpU').getValue();
                    if(args == 2) {
                        Ext.getCmp("termPara1Upd").show();
                        Ext.getCmp("flagBox2").show();
                    }else{
                        Ext.getCmp("termPara1Upd").hide();
                        Ext.getCmp("flagBox2").hide();
                    }
				}*/
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
                 hidden:true,
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属分行*',
                    id: 'termBranchUpd',
                    hiddenName: 'brhIdUpd',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                   // allowBlank: false,
                    width: 130,
                    blankText: '终端所属分行不能为空'/*,
                    listeners:{
                        'select': function() {
                        }
                    }*/
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 30,
                    allowBlank: false,
                    //regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelUpd',
                    name: 'contTelUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype:'textfield',
                    fieldLabel: '联系人',
                    id: 'termPara2Upd',
                    allowBlank: true,
                    maxLength: 100,
					vtype: 'isOverMax',
                    hiddenName: 'termPara2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
              //      xtype: 'basecomboselect',
                	xtype: 'dynamicCombo',
                    fieldLabel: '终端厂商*',
                    id: 'idtermFactoryUpd',
                    allowBlank: false,
                //    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    methodName: 'getFACTORY',
                    hiddenName: 'termFactoryUpd' ,
                    callFunction : function() {
    					Ext.getCmp('idtermMachTpUpd').reset();
    					Ext.getCmp('idtermMachTpUpd').setValue('-');
    					Ext.getCmp('idtermMachTpUpd').parentP=this.value;
    					Ext.getCmp('idtermMachTpUpd').getStore().reload();
    			//		Ext.getCmp('idtermMachTpUpd').reset();
    					
    					/*Ext.getCmp('idarea').reset();
    					Ext.getCmp('idarea').setValue('-');
    					Ext.getCmp('idarea').parentP=this.value;
    					Ext.getCmp('idarea').getStore().reload();
    					Ext.getCmp('idarea').reset();*/
    				}
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'dynamicCombo',
         //       	xtype: 'basecomboselect',     
                    fieldLabel: '终端型号*',
                    id: 'idtermMachTpUpd',
              //      name:'termMachTpUpd',
                    allowBlank: false,
              //      baseParams:'TERMMACHTP',
                    methodName: 'getTermMachTp',
                   hiddenName: 'termMachTpUpd'
                    
                   
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel:'产权所属机构名称',
                   // store: organStore,
                    id: 'propInsNmUpd',
                    maxLength:40,
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                id: "flagBox2",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1Upd',
                    name: 'termPara1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                 //   xtype: 'basecomboselect',
                	xtype: 'dynamicCombo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeU',
                 //   baseParams:'CONNECT_MODE',
                    methodName: 'getCONNTYPE',
               //     allowBlank: false,
                    hiddenName: 'connectModeUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items:[{
               //  xtype: 'combo',
              	  xtype: 'dynamicCombo',
                  fieldLabel: '终端类型*',
                  id: 'termTpU',
                  allowBlank: false,
                  hiddenName: 'termTpUpd',
             //     store: termTypeStore,
                  methodName: 'getTermType',
                  width:150
                  /*listeners:{
//                      'select': function() {
              	   'change': function() {
                       var value = Ext.getCmp('termTpU').getValue();
                       if(value=='3'){
                       	 var label =Ext.getCmp('txn14Upd').getEl().parent().parent().first();  
                      	 label.dom.innerHTML ='绑定电话1*';
                      	 updTermForm.getForm().findField("txn14Upd").reset();
                      	 Ext.getCmp('txn14Upd').allowBlank = false;
                       }else{
                           var label =Ext.getCmp('txn14Upd').getEl().parent().parent().first();   
                           label.dom.innerHTML ='绑定电话1';
                           Ext.getCmp('txn14Upd').allowBlank = true;
                       }
                       if(value == '1')   
                       {
                       	var value1 = Ext.getCmp("termMccUpd").getValue();
                          if( value1 != '0000' ){
                          	updTermForm.getForm().findField("termTpU").reset();
	                            showAlertMsg("非财务POS商户，终端类型不能选择财务POS",grid);
                          }else{
                          	Ext.getCmp('accountBox2').show();
                              Ext.getCmp('financeCard1Upd').allowBlank = false;
                              updTermForm.getForm().findField("param1Upd").setValue(1);
                              updTermForm.getForm().findField("param11Upd").setValue(1);       
						        updTermForm.getForm().findField("param12Upd").setValue(1);       
						        updTermForm.getForm().findField("param13Upd").setValue(1);       
						        updTermForm.getForm().findField("param14Upd").setValue(1);       
						        updTermForm.getForm().findField("param15Upd").setValue(1);
                          }
                       }
                       else
                       {
                          Ext.getCmp('accountBox2').hide();
                          Ext.getCmp('financeCard1Upd').allowBlank = true;
                          updTermForm.getForm().findField("param11Upd").setValue(0);
                          updTermForm.getForm().findField("param12Upd").setValue(0);
                          updTermForm.getForm().findField("param13Upd").setValue(0);
                          updTermForm.getForm().findField("param14Upd").setValue(0);
                          updTermForm.getForm().findField("param15Upd").setValue(0);
                       }
                      }
                  }*/
                }]
              },{
                columnWidth: .5,
                layout: 'form',
                items: [{
             //   	xtype:'textfield',
                	xtype: 'dynamicCombo',
                    fieldLabel: '安装维护机构',
              //      id: 'misc3U',
              //      allowBlank: false,
                    maxLength: 40,
                    methodName: 'getNormalAgentID',
					vtype: 'isOverMax',
                    hiddenName: 'misc3U'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                	xtype:'textfield',
                    fieldLabel: '安装维护员',
                    id: 'misc2U',
                    allowBlank: true,
                    maxLength: 40,
					vtype: 'isOverMax',
                    hiddenName: 'misc2U'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '运行维护方代码1',
                    id: 'idrunMainId1U',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId1U' 
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '运行维护方代码2',
                    id: 'idrunMainId2U',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId2U'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'basecomboselect',
                    fieldLabel: '其他服务方代码',
                    id: 'idothSvrIdU',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'othSvrIdU'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                	xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属省*',
                    id: 'idprovinceUpd',
                    hiddenName: 'provinceUpd',
                    allowBlank: false,
                    methodName: 'getProvinces',
                    callFunction : function() {
    					Ext.getCmp('idcityUpd').reset();
    					Ext.getCmp('idareaUpd').reset();
    					Ext.getCmp('idcityUpd').parentP=this.value;
    					Ext.getCmp('idcityUpd').getStore().reload();
    					Ext.getCmp('idareaUpd').getStore().reload();
    				}
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                	  xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属市*',
                    id:'idcityUpd',
                    hiddenName: 'cityUpd',
                    allowBlank: false,
//                    store: citiesStore,
                    width: 150,
                    methodName: 'getCitys',
                    callFunction : function() {
	    				Ext.getCmp('idareaUpd').reset();
	   					Ext.getCmp('idareaUpd').parentP=this.value;
	   					Ext.getCmp('idareaUpd').getStore().reload();
    				}
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                    xtype: 'dynamicCombo',
                    fieldLabel: '终端安装地所属区/县',
                    methodName: 'getAreas',
                    id:'idareaUpd',
                    hiddenName: 'areaUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址*',
                    allowBlank:false,
                    maxLength: 100,
                    id: 'termAddrUpd',
                    name: 'termAddrUpd'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                hidden: true,
                items: [{
                	    xtype: 'dynamicCombo',
						methodName: 'getAreaCode',
						fieldLabel: '终端安装地地区代码*',
						id:'idcityCodeUpd',
						hiddenName: 'cityCodeUpd',
				//		allowBlank: false,
						editable: true
                }]
            }]
            },{
                title: '附加信息',
                id: 'info2Upd',
                layout: 'column',
                items: [{
                id: 'accountBox2',
                columnWidth: .5,
                layout: 'form',
                hidden:true,
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
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1',
                    maxLength: 15,
                   // allowBlank: false,
                    vtype: 'isNumber',
                    id: 'txn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
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
                    fieldLabel: '绑定电话3',
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
                    fieldLabel: 'NAC 电话1',
                    maxLength: 15,
                   // allowBlank: false,
                    vtype: 'isNumber',
                    id: 'bindTel1Upd',
//                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel2Upd',
//                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel3Upd',
//                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
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
                    inputValue: '1'/*,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignUpd',
                    name: 'paramDownSignUpd',
                    disabled: true,
                    //checked:true ,
                    inputValue: '1'/*,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignUpd',
                    name: 'icDownSignUpd',
                    inputValue: '1'/*,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }*/
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Upd',
                    name: 'reserveFlag1Upd',
                    inputValue: '1',
                    listeners :{
                    'check':function(r,c){
							//r.setValue('1');
                    		if(Ext.getCmp('reserveFlag1Upd').checked==true)
								Ext.getCmp('txn14Upd').allowBlank=false;
							else
								Ext.getCmp('txn14Upd').allowBlank=true;
						}
                    }
                }]
            }/*,{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '中行终端主密钥*',
                    id: 'productCdUpd',
                    name: 'productCdUpd',
                    allowBlank: false,
                    maskRe: /^[0-9]|[a-f]|[A-F]$/,
                    maxLength:32,
                    minLength:32,
                    width:200
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '再次输入终端主密钥*',
                    id: 'productCdUpdS',
                    name: 'productCdUpdS',
                    maskRe: /^[0-9]|[a-f]|[A-F]$/,
                    allowBlank: false,
                    maxLength:32,
                    minLength:32,
                    width:200
                }]
            }*/]
            },{
                title: '交易信息',
                id: 'info3Upd',
                layout: 'column',
                items: [{
                columnWidth: .5,
                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                        inputValue: '1'
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
	                        inputValue: '1'
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
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                layout: 'form',
	                hidden:true,
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '分期付款消费 ',
	                        id: 'param9Upd',
	                        name: 'param9Upd',
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                layout: 'form',
	                hidden:true,
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '分期付款撤销 ',
	                        id: 'param10Upd',
	                        name: 'param10Upd',
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                layout: 'form',
	                hidden:true,
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '分期付款退货 ',
	                        id: 'param11Upd',
	                        name: 'param11Upd',
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                layout: 'form',
	                hidden:true,
//	                hidden:true,
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '电子现金消费 ',
	                        id: 'param12Upd',
	                        name: 'param12Upd',
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                layout: 'form',
	                hidden:true,
//	                hidden:true,
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '电子现金退货',
	                        id: 'param13Upd',
	                        name: 'param13Upd',
	                        inputValue: '1'
	                     }]
                },{
	                columnWidth: .3,
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
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
	                hidden:true,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '电子现金 ',
	                        id: 'param20Upd',
	                        name: 'param20Upd',
	                        inputValue: '1'
	                 }]
                },{
	                columnWidth: .3,
	                hidden:true,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: 'IC现金充值 ',
	                        id: 'param21Upd',
	                        name: 'param21Upd',
	                        inputValue: '1'
	                }]
            },{
                columnWidth: .3,
                hidden:true,
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
                hidden:true,
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
                hidden:true,
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
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'termParaUpd',
                        name: 'termParaUpd'
                }]
            }]
            },{
                title: '终端参数',
                id: 'info4Upd',
                layout: 'column',
                items: [{ columnWidth: 1},
                {
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '超时时间*',
                        id: 'txn12NewU',
                        allowBlank: false,
                        name: 'txn12NewU',
                       	vtype: 'isNumber',
                       	maxLength:2,
                    	minLength:2
	                         
	                    }]
	            },{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
	                    xtype: 'combo',
	                    fieldLabel: '拨号重拨次数*',
	                    id: 'idtxn13NewU',
	                    allowBlank: false,
	                    hiddenName: 'txn13NewU',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['3','3'],['5','5']]
	                    })
	           
	                }]
	            },{
	                columnWidth: .5,
	                hidden:true,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '是否支持小费 ',
	                        id: 'txn18NewU',
	                        name: 'txn18NewU',
	                        inputValue: '1'
	                     }]
	            },{
		             columnWidth: .5,
		              hidden:true,
	                 layout: 'form',
	                 items:[{
	                    xtype: 'textfield',
	                    fieldLabel: '小费百分比',
	                    name: 'txn19NewU',
	                    id: 'txn19NewU',
	                    vtype: 'isNumber',
	                    maxLength:2,
						vtype: 'isOverMax'
	                 }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: '冲正重发次数*',
	                    id: 'idtxn23NewU',
	                    allowBlank: false,
	                    hiddenName: 'txn23NewU',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['3','3'],['5','5']]
	                    })
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: 'POS终端应用类型*',
	                    id: 'idtxn11NewU',
	                    allowBlank: false,
	                    hiddenName: 'txn11NewU',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['10','磁条卡金融支付类'],['11','IC卡金融支付类']]
	                    })
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                	xtype:'textfield',
	                    fieldLabel: '打印联数*',
	                    id: 'txn27NewU',
	                    allowBlank: false,
	                    maxLength: 1,
						vtype: 'isNumber',
	                    hiddenName: 'txn27NewU'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'basecomboselect',
	                    fieldLabel: '快捷交易类型',
	                    id: 'idtxn30NewU',
	                    allowBlank: true,
	                    store:Txn_NumStore,//在systemSuport.js 中定义的常量
	                    hiddenName: 'txn30NewU'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '支持手工输入卡号 ',
	                        hideLabel:true,
	                        id: 'txn20NewU',
	                        name: 'txn20NewU',
	                        inputValue: '1'
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否自动签退 ',
	                        hideLabel:true,
	                        id: 'txn21NewU',
	                        name: 'txn21NewU',
	                        inputValue: '1'
	                     }]
	            }, {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel : '消费撤销交易是否需要刷卡 ',
	                        hideLabel:true,
	                        id: 'txn25New1U',
	                        name: 'txn25New1U',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '消费撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New2U',
	                        name: 'txn25New2U',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易是否需要刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New3U',
	                        name: 'txn25New3U',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New4U',
	                        name: 'txn25New4U',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易是否需要输入刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New5U',
	                        name: 'txn25New5U',
	                        inputValue: '1'
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易是否需要输入密码',
	                        hideLabel:true,
	                        id: 'txn25New6U',
	                        name: 'txn25New6U',
	                        inputValue: '1'
	                     }]
	            },
	            
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成交易是否需刷卡/手输卡号',
	                        hideLabel:true,
	                        id: 'txn25New7U',
	                        name: 'txn25New7U',
	                        inputValue: '1'
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成是否需输入密码',
	                        hideLabel:true,
	                        id: 'txn25New8U',
	                        name: 'txn25New8U',
	                        inputValue: '1'
	                     }]
	            },
	            {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否屏蔽卡号',
	                        hideLabel:true,
	                        id: 'txn28NewU',
	                        name: 'txn28NewU',
	                        inputValue: '1'
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '是否显示LOGO',
	                        hideLabel:true,
	                        id: 'txn29NewU',
	                        name: 'txn29NewU',
	                        inputValue: '1'
	                     }]
	            }]
            }]
    });
/*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 450,
        width: 680,
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
        width: 650,
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
                /*if(Ext.getCmp('termTpU').getValue()=='3' && updTermForm.findById("txn14Upd").getValue()==''){
	               	 updTermPanel.setActiveTab('info2Upd');
	               	 updTermForm.getForm().findField("txn14Upd").allowBlank=false;
	                 showErrorMsg("请输入绑定电话！",updTermForm);
	               	 return;
                }*/
                updTermPanel.setActiveTab("info3Upd");
                updTermPanel.setActiveTab("info4Upd");
                
                if(updTermForm.getForm().isValid()) {
                	if(updTermForm.findById('contTelUpd').getValue()==null || updTermForm.findById('contTelUpd').getValue().trim() == ""){
    					showErrorMsg('联系电话不能为空或空字符串',updTermWin);
    					updTermForm.findById('contTelUpd').setValue('');
    					return ;
    				}
    				/*if(updTermForm.findById('termPara2Upd').getValue()==null || updTermForm.findById('termPara2Upd').getValue().trim() == ""){
    					showErrorMsg('联系人不能为空或空字符串',updTermWin);
    					updTermForm.findById('termPara2Upd').setValue('');
    					return ;
    				}*/
    				/*if(updTermForm.findById('misc3U').getValue()==null || updTermForm.findById('misc3U').getValue().trim() == ""){
    					showErrorMsg('安装维护机构不能为空或空字符串',updTermWin);
    					updTermForm.findById('misc3U').setValue('');
    					return ;
    				}*/
                	/*if(Ext.getCmp('productCdUpd').getValue()!=Ext.getCmp('productCdUpdS').getValue()){
						showErrorMsg("两次输入终端主密钥不一致！",updTermForm);
						updTermPanel.setActiveTab('info2Upd');
						return;
					}*/
                    
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T3010102Action.asp',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessDtl(action.result.msg,updTermForm);
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            //updTermPanel.setActiveTab('info3Upd');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                            txnId: '30101',
                            subTxnId: '02'
                        }
                    });
                }else{
                    var finded = true;
                    updTermForm.getForm().items.each(function(f){
                        if(finded && !f.validate()){
                            var tab = f.ownerCt.ownerCt.id;
                            if(tab == 'info1Upd' || tab == 'info2Upd' ||tab=='info4Upd' ){
                                 updTermPanel.setActiveTab(tab);
                            }
                            if(tab == 'info3Upd'){
	                        	termPanel1.setActiveTab("info3Upd");
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
            }
        }]
    });
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})