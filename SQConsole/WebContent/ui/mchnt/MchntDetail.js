

//商户详细信息，在外部用函数封装
function showMchntDetail(mchntId,El){
	
	//图片显示Store
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[															
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
	    store: storeImg,
	    tpl  : new Ext.XTemplate(
	        '<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath + 
								'/PrintImage?fileName={fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
	    ),
	    id: 'phones',
	    itemSelector: 'li.phone',
	    overClass   : 'phone-hover',
	    singleSelect: true,
	    multiSelect : true,
	    autoScroll  : true
	});
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'manuAuthFlag',mapping: 'manuAuthFlag'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'discConsFlg',mapping: 'discConsFlg'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
			{name: 'openDays',mapping: 'openDays'},
			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name: 'areaNo',mapping: 'areaNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'homePage',mapping: 'homePage'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'tcc',mapping: 'tcc'},
			{name: 'etpsAttr',mapping: 'etpsAttr'},
			{name: 'mngMchtId',mapping: 'mngMchtId'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtAttr',mapping: 'mchtAttr'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'mchtGroupId',mapping: 'mchtGroupId'},
			{name: 'mchtEngNm',mapping: 'mchtEngNm'},
			{name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
			{name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'psamNum',mapping: 'psamNum'},
			{name: 'cdMacNum',mapping: 'cdMacNum'},
			{name: 'posNum',mapping: 'posNum'},
			{name: 'connType',mapping: 'connType'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'mchtFunction',mapping: 'mchtFunction'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'enableDate',mapping: 'enableDate'},
			{name: 'preAudNm',mapping: 'preAudNm'},
			{name: 'confirmNm',mapping: 'confirmNm'},
			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
			{name: 'netTel',mapping: 'netTel'},
			{name: 'prolDate',mapping: 'prolDate'},
			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
			{name: 'operNo',mapping: 'operNo'},
			{name: 'operNm',mapping: 'operNm'},
			{name: 'procFlag',mapping: 'procFlag'},
			{name: 'setCur',mapping: 'setCur'},
			{name: 'printInstId',mapping: 'printInstId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'acqBkName',mapping: 'acqBkName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'orgnNo',mapping: 'orgnNo'},
			{name: 'subbrhNo',mapping: 'subbrhNo'},
			{name: 'subbrhNm',mapping: 'subbrhNm'},
			{name: 'openTime',mapping: 'openTime'},
			{name: 'closeTime',mapping: 'closeTime'},
			{name: 'visActFlg',mapping: 'visActFlg'},
			{name: 'visMchtId',mapping: 'visMchtId'},
			{name: 'mstActFlg',mapping: 'mstActFlg'},
			{name: 'mstMchtId',mapping: 'mstMchtId'},
			{name: 'amxActFlg',mapping: 'amxActFlg'},
			{name: 'amxMchtId',mapping: 'amxMchtId'},
			{name: 'dnrActFlg',mapping: 'dnrActFlg'},
			{name: 'dnrMchtId',mapping: 'dnrMchtId'},
			{name: 'jcbActFlg',mapping: 'jcbActFlg'},
			{name: 'jcbMchtId',mapping: 'jcbMchtId'},
			{name: 'cupMchtFlg',mapping: 'cupMchtFlg'},
			{name: 'debMchtFlg',mapping: 'debMchtFlg'},
			{name: 'creMchtFlg',mapping: 'creMchtFlg'},
			{name: 'cdcMchtFlg',mapping: 'cdcMchtFlg'},
			{name: 'reservedBase',mapping: 'reservedBase'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'internalNo',mapping: 'internalNo'},
			{name: 'reject',mapping: 'reject'},
			{name: 'mchntAttr',mapping: 'mchntAttr'},
			{name: 'linkPer',mapping: 'linkPer'},
			{name: 'SettleAreaNo',mapping: 'SettleAreaNo'},
			{name: 'MainTlr',mapping: 'MainTlr'},
			{name: 'CheckTlr',mapping: 'CheckTlr'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'rateFlag',mapping: 'rateFlag'},
			{name: 'settleChn',mapping: 'settleChn'},
			{name: 'batTime',mapping: 'batTime'},
			{name: 'autoStlFlg',mapping: 'autoStlFlg'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeFixed',mapping: 'feeFixed'},
			{name: 'feeMaxAmt',mapping: 'feeMaxAmt'},
			{name: 'feeMinAmt',mapping: 'feeMinAmt'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'feeDiv1',mapping: 'feeDiv1'},
			{name: 'feeDiv2',mapping: 'feeDiv2'},
			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
			{name: 'changeStlno',mapping: 'changeStlno'},
			{name: 'reserved',mapping: 'reserved'}
			
		]),
		autoLoad: false
	});
	
	var fm = Ext.form;
	
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});	
	
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'}
		]),
		sortInfo: {field: 'floorMount', direction: 'ASC'},
		autoLoad: false
	});
	
	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '交易卡种',
            dataIndex: 'txnNum',
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 90
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 90,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '回佣值',
            dataIndex: 'feeValue',
            width: 50
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 85
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 85
        }]
	})
	
    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 210,
		columnLines: true,	
//		autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		}
	});
    var custom;
	var customEl;
	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src
 		
 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
    
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});
	
	gridStore.on('load',function(){
		store.load({
			params: {
				start: 0,
				discCd: baseStore.getAt(0).data.feeRate
			}
		});
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([							
		{header: '计费代码',dataIndex: 'discCd',width: 60},
		{header: '计费名称',dataIndex: 'discNm',id:'discNm',width:180},
		{header: '所属机构',dataIndex: 'discOrg',width:100,renderer:function(val){return getRemoteTrans(val, "brh")}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,	
//		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		}
	});

	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
        			xtype: 'ux-radiogroup',
           			horizontal:true,
           			isInGroup: true,
            		id: 'idmchtLvl',
            		name: 'mchtLvl',
            		fieldLabel: '商户种类',
            		allowBlank: false,
					blankText: '请选择商户种类',
            		radios: [
            	   		{boxLabel: '普通商户', name: 'GroupFlag', value: 1, checked: true},
            	    	{boxLabel: '二级商户', name: 'GroupFlag', value: 2,
            	    		listeners:{
								'check':function(r,c){
									if(c){
										mchntForm.getForm().findField("idmchtGroupId").enable();
									}else{
										mchntForm.getForm().findField("idmchtGroupId").disable();
									}
								}
						}}
            		]
        		}]
        	},{
             	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户类型*',
					id: 'idconnType',
					hiddenName: 'connType',
					disabled: true,
					value: 'J',
					anchor: '90%'
				}]
        	},{
             	columnWidth: .33,
	        	id: 'otherNoPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '是否MIS商户',
					id: 'idmchtMngMode',
					hiddenName: 'mchtMngMode'
				}]
        	},{
     	        	columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否集团商户',
						id: 'idmchtGroupFlag',
						name: 'mchtGroupFlag'
		        	}]
				
        	},{
        	    columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			          xtype: 'basecomboselect',
			        baseParams: 'MCHT_GROUP',
					fieldLabel: '集团商户号',
					id: 'idmchtGroupId',
					hiddenName: 'mchtGroupId',
					disabled: true,
					anchor: '90%'
		        }]
        	},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW',
					fieldLabel: '所属分公司',
					disabled: true,
					hiddenName: 'acqInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					fieldLabel: '中文名称',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},{
				columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
			       xtype: 'displayfield',
//						fieldLabel: '中文名称简写',
						fieldLabel: '简称',
						id: 'mchtCnAbbr',
						anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						disabled: true,
						hiddenName: 'mchtGrp'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'displayfield',
						fieldLabel: '所在地区码',
						id: 'areaNo',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combo',
						fieldLabel: '商户MCC',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						disabled: true,
						lazyRender: true,
						id: 'idmcc',
						hiddenName: 'mcc'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '英文名称',
						id: 'engName',
						anchor: '90%'
		        	}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}]	
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 340,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						id: 'idlicenceNo',
						hiddenName:'licenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	layout: 'form',
					xtype: 'panel',
		        	items: [{
						xtype: 'displayfield',
						fieldLabel: '注册期限',
						id: 'idlicenceEndDate',
						hiddenName:'licenceEndDate'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '企业组织机构代码',
						id: 'bankLicenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						id: 'faxNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_ATTR',
						fieldLabel: '商户性质',
						hiddenName: 'etpsAttr',
						disabled: true
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'RISKLVL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户风险等级*',
					    id:'rislLvl',
						hiddenName: 'rislLvl'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CRE_LVL',
						fieldLabel: '企业资质等级',
						hiddenName: 'mchtCreLvl',
						disabled: true
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHT_CRE_LVL',
						fieldLabel: '注册资金/万元',
						id:'busAmt',
						hiddenName: 'busAmt',
						disabled: true
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '电子邮件',
						id: 'commEmail'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '公司网址',
						id: 'homePage',
						name: 'homePage',
						maxLength: 50
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户地址',
						maxLength: 80,
						vtype: 'isOverMax',
						id: 'addr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '邮政编码',
						regex: /^[0-9]{6}$/,
						regexText: '邮政编码必须是6位数字',
						id: 'postCode',
						name: 'postCode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人代表',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'manager'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						fieldLabel: '法人代表证件类型',
						hiddenName: 'artifCertifTp',
						disabled: true
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人代表证件号码',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人姓名',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人电话',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'commTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '企业传真',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'fax'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人电话', 
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'managerTel',
						name: 'managerTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户营业开始时间',
						id: 'openTime'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户营业结束时间',
						id: 'closeTime'
		        	}]
				}]
            },{
                title:'附加信息',
                layout:'column',
                id: 'append',
                frame: true,
                items: [{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持无磁无密交易',
						disabled: true,
						id: 'passFlag',
						name: 'passFlag'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持人工授权',
						disabled: true,
						id: 'manuAuthFlag',
						name: 'manuAuthFlag'
		        	}]
				},{
	        		columnWidth: 1,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持折扣消费',
						disabled: true,
						id: 'discConsFlg',
						name: 'discConsFlg'
		        	}]
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	width: 380,
						fieldLabel: '注册地址',
						id: 'regAddr',
						name: 'regAddr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield'
	        		},
	       			items: [{
						fieldLabel: '经营单位',
						id: 'prolTlr',
						name: 'prolTlr'
					},{
						fieldLabel: '协议编号',
						id: 'protocalId',
						name: 'protocalId'
		        	},{
						fieldLabel: '签约网点',
						id: 'agrBr',
						name: 'agrBr'
		        	},{
						fieldLabel: '客户经理工号',
						id: 'operNo',
						name: 'operNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
						fieldLabel: '批准人',
						id: 'confirmNm',
						name: 'confirmNm'
					},{
						xtype: 'basecomboselect',
						fieldLabel: '收单机构',
						baseParams: 'SING_INST_ID',
						id: 'signInstId',
						name: 'signInstId'
		        	},{
						fieldLabel: '客户经理电话',
						id: 'netTel',
						name: 'netTel'
		        	},{
						fieldLabel: '客户经理姓名',
						id: 'operNm',
						name: 'operNm'
		        	}]
				}]
			},{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                frame: true,
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	        		      columnWidth: .5,
		        	      xtype: 'panel',
		        	      layout: 'form',
	       			       items: [{
			        	   xtype: 'displayfield',
						   fieldLabel: '15商户编号',
						   id: 'mchtNoInPut'
		        	}]
				}]	
	       			},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SET_CUR',
						disabled: true,
						fieldLabel: '外卡入账币种',
						id: 'idsetCur',
						hiddenName: 'setCur',
						width: 165
		        	},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SETTLE_TYPE',
						disabled: true,
						fieldLabel: '商户结算方式',
						allowBlank: false,
						hiddenName: 'settleChn',
						width: 165
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '入账凭单打印机构',
						id: 'InstId',
						value:'03100000'
		        	},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'SETTLE_CHN',
						fieldLabel: '商户结算渠道',
						disabled: true,
						hiddenName: 'settleChn',
						width: 165
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户结算帐户开户行名',
						id: 'settleBankNm'
		        	},{
			        	xtype: 'displayfield',
						fieldLabel: '商户结算帐户开户名',
						id: 'settleAcctNm'
		        	},{
			        	xtype: 'displayfield',
						fieldLabel: '商户开户行同城清算号',
						id: 'openStlno'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户账户开户行代码',
						id: 'settleBankNo'
		        	},{
			        	xtype: 'displayfield',
						fieldLabel: '商户结算帐户号',
						id: 'settleAcct'
		        	},{
			        	xtype: 'displayfield',
						fieldLabel: '商户开户行同城交换号',
						id: 'changeStlno'
		        	}]
				}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 40,
                	layout:'hbox',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
		        		labelWidth: 80,
	       				items: [{
			        		xtype: 'numberfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '计费代码',
							allowNegative: false,
							maxValue: 100000000,
							id: 'discCode',
							name: 'discCode',
							readOnly: true
		        		}]
					},{
		        		xtype: 'panel',
		        		layout: 'form',
		        		style: 'padding-left: 25px',
	       				items: [{
	       					xtype: 'button',
							iconCls: 'detail',
							text:'计费算法配置说明',
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>' +
											'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
											'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
											'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
											'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。',
									title: '计费算法配置说明',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.OK,
									modal: true,
									width: 650
								});
							}
						}]
					}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:600,
                	items: [detailGrid]
                }]
		    },{
				title:'证书影像',
                id: 'images',
                frame: true,
                layout: 'border',
                items: [{
                	xtype: 'panel',
                	region: 'center',
//                	buttonAlign: 'left',
//                	layout: 'fit',
        			items : dataview,
        			frame: true,
        			tbar: [{
        				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view',
						handler:function() {
							storeImg.reload({
								params: {
									start: 0,
									imagesId: mchntId
								}
							})
						}
					}]
                }]
		    }]
    });
	
    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 980,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    })
    
	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var discCode = baseStore.getAt(0).data.feeRate;
				Ext.getCmp('discCode').setValue(discCode);
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				if('A' == settleAcct.substring(0,1)) {
					Ext.getCmp('settleType1').setValue('对公账户');
					Ext.getCmp('settleAcct').setValue(settleAcct.substring(1,settleAcct.lenght));
				}else{
					Ext.getCmp('settleType1').setValue('对私账户');
					Ext.getCmp('settleAcct').setValue(settleAcct.substring(1,settleAcct.lenght));
				}

//	            	Ext.getCmp('feeSelfBLId').setValue(baseStore.getAt(0).data.feeRate);		
				detailWin.setTitle('商户详细信息[商户编号：' + mchntId + ']');
				detailWin.show();
				
				gridStore.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});
				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					Ext.getCmp('idmcc').setValue(baseStore.getAt(0).data.mcc);
				});
				custom = new Ext.Resizable('showBigPic', {
					    wrap:true,
					    pinned:true,
					    minWidth: 50,
					    minHeight: 50,
					    preserveRatio: true,
					    dynamic:true,
					    handles: 'all',
					    draggable:true
					});
				customEl = custom.getEl();
				customEl.on('dblclick', function(){
					customEl.puff();
				});
				customEl.hide(true);
				
				storeImg.on('load',function(){
					for(var i=0;i<storeImg.getCount();i++){
						var rec = storeImg.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(val.id)
				    	});
					}
				})
				storeImg.reload({
		params: {
			start: 0,
			imagesId: mchntId
		}
	})
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}
    