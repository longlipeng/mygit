
//商户信息维护－－查看详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){
  var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	
	//代理商数据集
	var agentStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENT_NORMAL',function(ret){
		agentStore.loadData(Ext.decode(ret));
	});
	 //省数据集
	var provinceStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('PROVINCES',function(ret){
		provinceStore.loadData(Ext.decode(ret));
	});
	//市数据集
	var citiesStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    //区、县数据集
	var areasStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
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
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	var storeImg1 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getZipInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
//		region: 'north',
        store: storeImg,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName={fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"><input type="button" id="{btDw}" value="下载"></div>',
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
	
	var dataview1 = new Ext.DataView({
		frame: true,
//		region: 'south',
        store: storeImg1,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div>',
                        	'<img id="{id}" width="120" height="90" src="../../ext/resources/images/zip.jpg" alt="{fileName}"/>',
							'<div style=""><input type="button" id="{btDw}" value="下载"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones1',
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
			{name:'singleAmt',mapping:'singleAmt'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
			{name: 'openDays',mapping: 'openDays'},
			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name:'monthTotalAmt',mapping:'monthTotalAmt'},
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
			{name: 'custNo',mapping: 'custNo'},
			{name: 'riskGrade',mapping: 'riskGrade'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			
			{name: 'isSyncretic',mapping: 'isSyncretic'},
			{name: 'uscCode',mapping: 'uscCode'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'organizationType',mapping: 'organizationType'},
			
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
			{name: 'idOtherNo',mapping: 'idOtherNo'},
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
			{name: 'clearType',mapping: 'clearType'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
			{name: 'changeStlno',mapping: 'changeStlno'},
			{name: 'speSettleTp',mapping: 'speSettleTp'},
			{name: 'speSettleLv',mapping: 'speSettleLv'},
			{name: 'speSettleDs',mapping: 'speSettleDs'},
			{name: 'feeBackFlg',mapping: 'feeBackFlg'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'mchtNoHx',mapping: 'mchtNoHx'},
			{name: 'engNameAbbr',mapping: 'engNameAbbr'},
			{name:'tmpNo',mapping:'tmpNo'},
			{name:'depart',mapping:'depart'},
			{name:'nationality',mapping:'nationality'},
			{name:'foreignName',mapping:'foreignName'},
			{name:'busScope',mapping:'busScope'},
			{name:'busMain',mapping:'busMain'},
			{name:'mchtNoOld',mapping:'mchtNoOld'},
			{name:'mchtTypeCause',mapping:'mchtTypeCause'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'province',mapping:'province'},
			{name:'city',mapping:'city'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'area',mapping:'area'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'proxy',mapping:'proxy'},
			{name:'finManager',mapping:'finManager'},
			{name:'finPhone',mapping:'finPhone'},
			{name:'finTel',mapping:'finTel'},
			{name:'finFax',mapping:'finFax'},
			{name:'finEmail',mapping:'finEmail'},
			{name:'shopNum',mapping:'shopNum'},
			{name:'busArea',mapping:'busArea'},
			{name:'busZone',mapping:'busZone'},
			{name:'acreage',mapping:'acreage'},
			{name:'areaType',mapping:'areaType'},
			{name:'empNum',mapping:'empNum'},
			{name:'cashierNum',mapping:'cashierNum'},
			{name:'cashierDeskNum',mapping:'cashierDeskNum'},
			{name:'turnoverBefore',mapping:'turnoverBefore'},
			{name:'turnover',mapping:'turnover'},
			{name:'openDate',mapping:'openDate'},
			{name:'serLvl',mapping:'serLvl'},
			{name:'src',mapping:'src'},
			{name:'expander',mapping:'expander'},
			{name:'referrer',mapping:'referrer'},
			{name:'isWineshop',mapping:'isWineshop'},
			{name:'wineshopLvl',mapping:'wineshopLvl'},
			{name:'isMoreAcq',mapping:'isMoreAcq'},
			{name:'isAppOutSide',mapping:'isAppOutSide'},
			{name:'hasInnerPosExp',mapping:'hasInnerPosExp'},
			{name:'mcc2',mapping:'mcc2'},
			{name:'hasOurPosExp',mapping:'hasOurPosExp'},
			{name:'linkTel',mapping:'linkTel'},
			{name:'proxyTel',mapping:'proxyTel'},
			{name:'serType',mapping:'serType'},
			
			//基本信息-2 by zhangqy
			{name: 'engName',mapping: 'engName'},
			{name: 'busiRange',mapping: 'busiRange'},
			{name: 'postalCode',mapping: 'postalCode'},
			{name: 'insAddr',mapping: 'insAddr'},
			{name: 'belInd',mapping: 'belInd'},
			{name: 'ownerBusi',mapping: 'ownerBusi'},
			{name: 'ICPrecordNo',mapping: 'ICPrecordNo'},
			{name: 'ICPcompName',mapping: 'ICPcompName'},
			{name: 'mailAddr',mapping: 'mailAddr'},
			{name: 'contRate',mapping: 'contRate'},
			{name: 'sinDebAmount',mapping: 'sinDebAmount'},
			{name: 'dayDebAmount',mapping: 'dayDebAmount'},
			{name: 'monDebAmount',mapping: 'monDebAmount'},
			{name: 'sinCreAmount',mapping: 'sinCreAmount'},
			{name: 'dayCreAmount',mapping: 'dayCreAmount'},
			{name: 'monCreAmount',mapping: 'monCreAmount'},
			{name: 'contName',mapping: 'contName'},
			{name: 'contTel',mapping: 'contTel'},
			{name: 'busiTel',mapping: 'busiTel'},
			{name: 'appComm',mapping: 'appComm'},
			{name: 'agentName',mapping: 'agentName'},
			{name: 'agentType',mapping: 'agentType'},
			{name: 'regionBel',mapping: 'regionBel'},
			{name: 'agentNo',mapping: 'agentNo'},
			
			
			{name: 'uscCodeDate',mapping: 'uscCodeDate'},
			{name: 'licenceNoDate',mapping: 'licenceNoDate'},
			{name: 'shareholder',mapping: 'shareholder'},
			{name: 'idshareholderTpmd',mapping: 'idshareholderTpmd'},
			{name: 'idshareholder',mapping: 'idshareholder'},
			{name: 'shareholderDate',mapping: 'shareholderDate'},
			{name: 'identityNoDate',mapping: 'identityNoDate'},
			{name: 'contactmd',mapping: 'contactmd'},
			{name: 'linkTelDate',mapping: 'linkTelDate'},
			{name: 'operateAddr',mapping: 'operateAddr'},

			
			//附加信息
			{name:'preAuthor',mapping:'preAuthor'},
			{name:'returnFunc',mapping:'returnFunc'},
			
			{name:'mchntInd',mapping:'mchntInd'},
			
			//经营范围外建表字段
			{name:'busiRangeId',mapping:'busiRangeId'},
			{name:'mchtNo',mapping:'mchtNo'},
			{name:'busiRanges',mapping:'busiRanges'},
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
		columns: [/*{
            header: '交易卡种',
            dataIndex: 'txnNum',
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 150
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
		},*/{
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 100
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
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		autoWidth: true,
		frame: true,
		border: true,
		height: 210,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true
	});
    var custom;
	var customEl;
	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + rec.fileName;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
	
	function downPIC(id){
		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(4)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}
	function downPIC1(id){
	 	showConfirm('确定要下载吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg1.getAt(id.substring(5)).data;
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													rec.downPath;
			}
	 	});
 	}

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCdT'
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

	/*gridStore.on('load',function(){
		store.load({
			params: {
				start: 0,
				discCd: baseStore.getAt(0).data.feeRate
			}
		});
	});*/

	var gridColumnModel = new Ext.grid.ColumnModel([
		{header: '计费代码',dataIndex: 'discCd',width: 60},
		{header: '计费名称',dataIndex: 'discNm',id:'discNm',width:190},
		{header: '所属机构',dataIndex: 'discOrg',width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true
	});
		//商户费率信息 store
	
	var feeStore = new Ext.data.Store({
	
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfAlgo2'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'tmpNo',mapping: 'tmpNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'cityCode',mapping: 'cityCode'},
			{name: 'toBrchNo',mapping: 'toBrchNo'},
			{name: 'fkBrchNo',mapping: 'fkBrchNo'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'channelNo',mapping: 'channelNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'discId',mapping: 'discId'},
			{name: 'satute',mapping: 'satute'}
			
		]),
		autoLoad: false
	});
	//商户费率信息 列表
	var feeColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '终端号',dataIndex: 'termId',sortable: true,width: 60},
		{header: '地区码',dataIndex: 'cityCode',sortable: true,width:100},
		{header: '发卡机构',dataIndex: 'fkBrchNo',sortable: true,width:100},
		{header: '目的机构',dataIndex: 'toBrchNo',sortable: true,width:100},
		{header: '卡类型',dataIndex: 'cardType',sortable: true,width:100},
		{header: '交易渠道',dataIndex: 'channelNo',sortable: true,width:100},
		{header: '业务类型',dataIndex: 'busType',sortable: true,width:100},
		{header: '交易类型',dataIndex: 'txnNum',sortable: true,width:100},
		{header: '计费代码',dataIndex: 'feeType',sortable: true,width:100},
		{header: '状态',dataIndex: 'satute',sortable: true,width:100,renderer:statue}
		
	]);
	function statue(val){
		if(val=='0'){
			return '新增未审核'; 
		}
		if(val=='1'){
			return '注销';
		}
		if(val=='2'){
			return '正常';
		}
		if(val=='3'){
			return '修改未审核';
		}
		if(val=='4'){
			return '注销未审核';
		}
	}
	//商户费率信息 表格
	var feeGridPanel = new Ext.grid.GridPanel({
		title: '商户费率信息',
		frame: true,
		border: true,
		height: 190,
		columnLines: true,
		stripeRows: true,
		store: feeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户费率信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: feeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['终端号：',{
			xtype: 'textfield',
			id: 'term_ID',
			width: 60
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'searc',
			width: 60,
			handler: function(){
				feeStore.load({
				params: {
					start: 0,
					tmpNo: Ext.getCmp('tmpNo').getValue(),
					termId:Ext.getCmp('term_ID').getValue()
					}
				});
			}
		}]
	});
	
	feeStore.on('beforeload',function(){//20120924解决商户信息维护之查看详细信息页面中点击费率信息分页中刷新按钮信息会清空的bug
		Ext.apply(this.baseParams, {
			start: 0,
			tmpNo: Ext.getCmp('tmpNo').getValue(),
			termId:Ext.getCmp('term_ID').getValue()
		});
	});
	
	/**
	 * 双击弹出费率信息详情窗口
	 * 
	 */	
	var gridPanel_d = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		}/*,
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
	
	 var detailGrid_d = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		}/*,
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
	var detailForm = new Ext.form.FormPanel({
			labelWidth : 100,
			frame: true,
            layout: 'border',	
			items:[{
                	region: 'north',
                	height: 75,
                	layout: 'column',
                	items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			 xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					//id: 'fkBrchNo',
					name: 'fkBrchNo',
					maxLength: 8,
					width:150
		        }]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
	            	layout: 'form',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端代码',
						//id: 'termId',
						name: 'termId',
						maxLength: 8,
						width:100
			        }]
	        	},{
            		columnWidth: .33,
	        		xtype: 'panel',
	        		layout: 'form',
            		labelWidth: 70,
       				items: [{
       					xtype: 'displayfield',
       					labelStyle: 'padding-left: 5px',
       					fieldLabel: '计费代码',
						disabled:true,
						name: 'feeType',
						width:80
					}]
				},{
	        		columnWidth: .33,
	        		xtype: 'panel',
	        		labelWidth: 70,
			        layout: 'form',
		       		items: [{
		       			xtype: 'dynamicCombo',
		       			methodName: 'getAreaCode',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地区码',
						disabled:true,
						hiddenName: 'cityCode',
						width:150
		       		}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构',
					disabled:true,
					blankText: '请选择目的机构',
					//id: 'idTO_BRCH_NO_d',
					hiddenName: 'toBrchNo',
					//anchor: '90%',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						disabled:true,
						blankText: '请选择交易渠道',
						hiddenName: 'cardType',
						width:150
		        	}]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						blankText: '请输入交易渠道',
						disabled:true,
						hiddenName: 'channelNo',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						disabled:true,
						blankText: '请输入业务类型',
						//id: 'idBUSINESS_TYPE_d',
						hiddenName: 'busType',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						blankText: '请输入交易类型',
						hiddenName: 'txnNum',
						disabled:true,
						width:150
		        	}]
				}]
                },{
                	region: 'center',
                	items:[gridPanel_d]
                },{
                	region: 'east',
                	width:450,
                	items: [detailGrid_d]
                }]	
	});
	
		var detailWin1 = new Ext.Window({
					title : '费率详情',
				//	initHidden : true,
					header : true,
					frame : true,
					closable : false,
					modal : true,
					width : 800,
					height : 400,
					layout : 'fit',
					items : [ detailForm ],
					buttonAlign : 'center',
					closeAction : 'hide',
					iconCls : 'logo',
					resizable : false,
					buttons : [{
								text : '关闭',
								handler : function() {
									detailWin1.hide();
								}
							} ]
				});
		feeGridPanel.on({	//行双击事件
			'rowdblclick':function(){
				detailForm.getForm().loadRecord(feeGridPanel.getSelectionModel().getSelected());
				gridStore.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				store.load({
				params: {
					start: 0,
					discCd: feeGridPanel.getSelectionModel().getSelected().data.feeType
					}
				});
				detailWin1.show();	
		}
		}
	)
	
	/*************************************清算列表*********************************/
	
		//store
	
	var setStore = new Ext.data.Store({
	
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfsettle'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'termId',mapping: 'termId'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'currAccount',mapping: 'currAccount'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'compAccountBankCode',mapping: 'compAccountBankCode'},
			{name: 'compAccountBankName',mapping: 'compAccountBankName'},
			{name: 'companyNam',mapping: 'companyNam'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'bankAccountCode',mapping: 'bankAccountCode'},
			{name: 'corpBankName',mapping: 'corpBankName'},
			
			{name: 'legalNam',mapping: 'legalNam'},/*feeAcctNm*/
			
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'dirBankCode',mapping: 'dirBankCode'},
			{name: 'dirBankName',mapping: 'dirBankName'},
			{name: 'dirAccountName',mapping: 'dirAccountName'},
			{name: 'dirAccount',mapping: 'dirAccount'},
			{name: 'dirFlag',mapping: 'dirFlag'},
			{name: 'autoFlag',mapping: 'autoFlag'},
			{name: 'holidaySetFlag',mapping: 'holidaySetFlag'},
			{name: 'creFlag',mapping: 'creFlag'},
			{name: 'returnFeeFlag',mapping: 'returnFeeFlag'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'settleChn',mapping: 'settleChn'},
			//add
			{name: 'dirOpenBank',mapping: 'dirOpenBank'},
			{name: 'dirBankProvince',mapping: 'dirBankProvince'},
			{name: 'dirBankCity',mapping: 'dirBankCity'},
			{name: 'compOpenBank',mapping: 'compOpenBank'},
			{name: 'compBankProvince',mapping: 'compBankProvince'},
			{name: 'compBankCity',mapping: 'compBankCity'},
			{name: 'corpOpenBank',mapping: 'corpOpenBank'},
			{name: 'corpBankProvince',mapping: 'corpBankProvince'},
			{name: 'corpBankCity',mapping: 'corpBankCity'}
			
		]),
		autoLoad: false
	});
	
	var setColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '终端号',dataIndex: 'termId',sortable: true,width: 60},
		{header: '结算账户类型',dataIndex: 'settleRpt',sortable: true,width:60,renderer:settleRptVal},
		{header: '公司帐号开户行名称',dataIndex: 'compAccountBankName',sortable: true,width:100},
		{header: '公司帐号开户行机构代码',dataIndex: 'compAccountBankCode',sortable: true,width:100},
		{header : '对公账户名称',dataIndex : 'companyNam',sortable : true,width : 100},
		{header: '对公账号',dataIndex: 'settleAcct',sortable: true,width:100},
		{header: '法人账号开户行名称',dataIndex: 'corpBankName',sortable: true,width:100},
		{header: '法人账号开户行机构代码',dataIndex: 'bankAccountCode',sortable: true,width:100},
		
		{header : '对私账户名称',dataIndex : 'legalNam',sortable : true,width : 100},
		
		{header: '对私账号',dataIndex: 'feeAcct',sortable: true,width:100},
		{header: '定向委托帐号开户行名称',dataIndex: 'dirBankName',sortable: true,width:100},
        {header: '定向委托帐号帐号开户行机构代码',dataIndex: 'dirBankCode',sortable: true,width:100},		
		{header: '定向委托帐号开户名',dataIndex: 'dirAccountName',sortable: true,width:100},
		{header: '定向委托账号',dataIndex: 'dirAccount',sortable: true,width:100},
		{header: '自动提现标志',dataIndex: 'autoFlag',sortable: true,width:90,renderer:flagVal},
		{header: '节假日提现标志',dataIndex: 'holidaySetFlag',sortable: true,width:90,renderer:flagVal},
		{header: '信用卡刷卡功能标志',dataIndex: 'creFlag',sortable: true,width:110,renderer:flagVal},
		{header: '退货返还手续费标志',dataIndex: 'returnFeeFlag',sortable: true,width:110,renderer:flagVal},
		{header: '商户结算方式T+N',dataIndex: 'settleChn',sortable: true,width: 100},
		{header: '商户结算周期',dataIndex: 'settleType',sortable: true,width:100,renderer:settleTypeVal}
		//add
												,{
													header : '定向委托账号开户总行名称',
													dataIndex : 'dirOpenBank',
													sortable : true,
													width : 100
												}, {
													header : '定向委托账号开户行所在省',
													dataIndex : 'dirBankProvince',
													sortable : true,
													width : 100
												},{
													header : '定向委托账号开户行所在市',
													dataIndex : 'dirBankCity',
													sortable : true,
													width : 100
												},{
													header : '对公账号开户总行名称',
													dataIndex : 'compOpenBank',
													sortable : true,
													width : 100
												},{
													header : '对公账号开户行所在省',
													dataIndex : 'compBankProvince',
													sortable : true,
													width : 100
												},{
													header : '对公账号开户行所在市',
													dataIndex : 'compBankCity',
													sortable : true,
													width : 100
												},{
													header : '对私账号开户总行名称',
													dataIndex : 'corpOpenBank',
													sortable : true,
													width : 100
												},{
													header : '对私账号开户行所在省',
													dataIndex : 'corpBankProvince',
													sortable : true,
													width : 100
												},{
													header : '对私账号开户行所在市',
													dataIndex : 'corpBankCity',
													sortable : true,
													width : 100
												}
	]);
	
	function flagVal(val){
		if(val=='0'){
			return '否';
		}
		if(val=='1'){
			return '是';
		}
	}
	
	function settleTypeVal(val){
		if(val=='0'){
			return '日结';
		}
		if(val=='1'){
			return '日结累计';
		}
		if(val=='2'){
			return '月结单笔';
		}
		if(val=='3'){
			return '月结包月';
		}
		if(val=='4'){
			return '月金额结累计计算';
		}
	}
	function settleRptVal(val){
		if(val=='1'){
			return '对私账户'; 
		}
		if(val=='2'){
			return '对公账户';
		}if(val=='3'){
			return '定向委托账户';
		}
	}
	//商户费率信息 表格
	var setGridPanel = new Ext.grid.GridPanel({
		title: '商户清算信息',
		frame: true,
		border: true,
		height: 190,
		columnLines: true,
		stripeRows: true,
		store: setStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: setColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户清算信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: setStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['终端号：',{
			xtype: 'textfield',
			id: 'setTerm_ID',
			width: 60
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'sears',
			width: 60,
			handler: function(){
				setStore.load({
				params: {
					start: 0,
					mchtNo: mchntId,
					termId:Ext.getCmp('setTerm_ID').getValue()
					}
				});
			}
		}]
	});
	
	setStore.on('beforeload',function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: mchntId,
			termId:Ext.getCmp('setTerm_ID').getValue()
		});
	});	
	
	/************************end******************************/
	
	/*********************商户操作日志start*******************************/
	var auditStore = new Ext.data.Store({
		
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchntLogs'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'oprType',mapping: 'oprType'},
			{name: 'oprStatus',mapping: 'oprStatus'},
			{name: 'oprInfo',mapping: 'oprInfo'}
		]),
		autoLoad: false
	});
	//
	var auditColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '操作人',dataIndex: 'oprId',sortable: true,width: 150,renderer:getOpr},
		{header: '操作时间',dataIndex: 'txnTime',sortable: true,width:150,renderer: formatTs},
		{header: '操作类型',dataIndex: 'oprType',sortable: true,width:100},
		{header: '操作状态',dataIndex: 'oprStatus',sortable: true,width:100},
		{header: '备注',dataIndex: 'oprInfo',sortable: true,id:'oprInfo'}
		
	]);
	
	function getOpr(val){
		if(val!=null&&val!=''){
		return getRemoteTrans(val,"getOprNm");
		}else{
			return val;
		}
	}
	
	
	var auditGridPanel = new Ext.grid.GridPanel({
		title: '商户操作记录',
		frame: true,
		border: true,
		height: 220,
		columnLines: true,
		stripeRows: true,
		autoExpandColumn: 'oprInfo',
		store: auditStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: auditColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户操作信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: auditStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	auditStore.on('beforeload',function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntInd: Ext.getCmp('mchntInd').getValue()
		});
	});
	
/*****************************************受益人信息*****************************************/
	
	//受益人信息 store
	var feeStore1 = new Ext.data.Store({
		//proxy:用于从某个途径读取原始数据
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=TblMchtBeneficiaryInfTmp'
		}),
		//reader:用于将原始数据转换成Record实例
		reader: new Ext.data.JsonReader({
			//root:表示当前页显示信息队列
			root: 'data',
			//totalProperty:表示后台数据的记录总数
			totalProperty: 'totalCount'
			//idProperty: 'beneficiaryId'
		},[
			{name: 'beneficiaryId',mapping: 'beneficiaryId'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'beneficiaryName',mapping: 'beneficiaryName'},
			{name: 'beneficiaryAddress',mapping: 'beneficiaryAddress'},
			{name: 'beneficiaryIdType',mapping: 'beneficiaryIdType'},
			{name: 'beneficiaryIdCard',mapping: 'beneficiaryIdCard'},
			{name: 'beneficiaryExpiration',mapping: 'beneficiaryExpiration'},
		]),
		autoLoad: false
		//对数据进行排序
		//field:排序的字段                 direction:排序的方式
	 	//sortInfo: {field: 'name', direction: 'DESC'}  
	});
	
	//受益人信息 列表
	var feeColumnModel1 = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '受益人编号',dataIndex: 'beneficiaryId',sortable: true,hidden:true,width:100},
		{header: '商户编号',dataIndex: 'mchtNo',sortable: true,hidden:true,width:100},
		{header: '受益人姓名',dataIndex: 'beneficiaryName',sortable: true,width:100},
		{header: '地址',dataIndex: 'beneficiaryAddress',sortable: true,width:100},
		{header: '身份证件种类',dataIndex: 'beneficiaryIdType',renderer: beneficiaryTypeVal,sortable: true,width:100},
		{header: '号码',dataIndex: 'beneficiaryIdCard',sortable: true,width:100},
		{header: '有效期限',dataIndex: 'beneficiaryExpiration',sortable: true,width:100},
	]);
	
	function beneficiaryTypeVal(value){
		if(value=='1'){
			return '身份证';
		}else if(value=='2'){
			return '护照';
		}
	}
	
	//受益人信息 表格
	var feeGridPanel1 = new Ext.grid.GridPanel({
		title: '受益人信息',
		frame: true,
		border: true,
		height: 190,
		columnLines: true,
		stripeRows: true,
		store: feeStore1,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel1,
		forceValidation: true,
		loadMask: {
			msg: '正在加载受益人信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: feeStore1,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: [{
			xtype: 'button',
			width: 60,
			text: '预览',
			iconCls: 'query',
			id: 'images1',
			disabled: true,
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				imagesWin.show();
				imagesWin.center();
				/*picWin.show();
				for(var i = 0;i < storeImg2.getCount();i++){
					var rec = storeImg2.getAt(i).data;
					document.getElementById('bigPic').innerHTML = '<img width="120" height="90" src="' + 
					Ext.contextPath + '/PrintImage?fileName=' + rec.fileName + '&width=120&height=90" />';
				}*/
				
			}
		}]
	});
	
	//选中数据显示按钮
	feeGridPanel1.getSelectionModel().on({
		'rowselect':function(){
			//预览按钮显示
			Ext.getCmp("images1").enable();
		}
	});
	
	feeStore1.load({
		params:{
			start: 0,
			mchtNo: mchntId
		}
	});
	
	
	//******************图片处理部分**********开始********
	var storeImg2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInf1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	var storeImg3 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getZipInf1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btDw',mapping: 'btDw'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'fileName',mapping: 'fileName'},
			{name: 'downPath',mapping: 'downPath'}
		])
	});
	
	
	var dataview2 = new Ext.DataView({
		frame: true,
//		region: 'north',
        store: storeImg2,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div>',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage1?fileName={downPath}&width=120&height=90"/>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones2',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	var dataview3 = new Ext.DataView({
		frame: true,
//		region: 'south',
        store: storeImg3,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div>',
                        	'<img id="{id}" width="120" height="90" src="../../ext/resources/images/zip.jpg" alt="{fileName}"/>',
                        '</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones3',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	
	//******************图片处理部分**********结束********
	
	var imageForm = new Ext.FormPanel({
        frame: true,
        layout: 'border',
        items: [{
        	xtype: 'panel',
        	region: 'center',
			autoScroll: true,
			items : [dataview2,dataview3]
        }]
	});
	
	var imagesWin = new Ext.Window({
		title: '受益人图片预览',
		layout: 'fit',
		animateTarget: 'picPanel',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		draggable: false,
		width: 400,
		height: 300,
		items: [imageForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '刷新',
			handler:function() {
				//获取选中的第一条记录
				var rec = feeGridPanel1.getSelectionModel().getSelected();
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						beneficiaryId: rec.get('beneficiaryId'),
						beneficiaryExpiration: rec.get('beneficiaryExpiration')
					}
				});
			}
		},{
			text: '关闭',
			handler: function() {
				imagesWin.hide();
			}
		}]
	});
	
	
	
/********************商户操作日志end*****************************/
	
	
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'BRH_BELOW',
					fieldLabel: '所属分公司',
					labelStyle: 'padding-left: 5px',
//					id: 'idacqInstId',
					hiddenName: 'acqInstId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'combofordispaly',
	       			baseParams: 'MCHNT_GRP_ALL',
					fieldLabel: '商户名称',
					labelStyle: 'padding-left: 5px',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},
			{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
//			        	xtype: 'displayfield',
	       				xtype: 'combofordispaly',
	       				baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						labelStyle: 'padding-left: 5px',
						id:'mchtGrp',
						hiddenName: 'mchtGrp',
						anchor: '90%'
		        	}]
			},{
			    columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'MCC2',
						fieldLabel: '商户MCC',
						labelStyle: 'padding-left: 5px',
						id: 'mcc',
						hiddenName: 'mcc',
						anchor: '90%'
		        	}]
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 320,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
                autoScroll: true,
				layout:'column',
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '商户简称',
						labelStyle: 'padding-left: 5px',
						id: 'mchtCnAbbr'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'MCHNT_ATTR',
				        labelStyle: 'padding-left: 5px',
						fieldLabel: '商户性质',
						id: 'etpsAttr',
						hiddenName: 'etpsAttr'
				}]		
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '注册地址',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'regAddr',
						hiddenName: 'regAddr'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '经营地址',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'operateAddr',
						hiddenName: 'operateAddr'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				       xtype: 'displayfield',
						fieldLabel: '邮政编号',
						labelStyle: 'padding-left: 5px',
						id: 'postalCode',
						hiddenName: 'postalCode'
				}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '电子邮件',
						labelStyle: 'padding-left: 5px',
						id: 'commEmail',
						hiddenName: 'commEmail'
					}]
				},{
					columnWidth: .33,
			       	xtype: 'panel',
			       	layout: 'form',
					items: [{
			        xtype: 'combofordispaly',
					fieldLabel: '是否三证合一',
					labelStyle: 'padding-left: 5px',
					id: 'idSyncretic',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','是'],['1','否']],
						reader: new Ext.data.ArrayReader()
					}),
					hiddenName: 'isSyncretic'
					}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '统一社会信用代码',
						labelStyle: 'padding-left: 5px',
						id: 'uscCode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
					id: 'idUscCodeDate',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
					    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '统一社会信用代码证有效期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
						id: 'uscCodeDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '客户号',
						labelStyle: 'padding-left: 5px',
						id: 'custNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '风险等级',
						labelStyle: 'padding-left: 5px',
						id: 'riskGrade'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						labelStyle: 'padding-left: 5px',
						id: 'licenceNo'
		        	}]
				}/*,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idLicenceNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'licenceNoDate'
		        	}]
				}*/,{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业组织机构代码',
						labelStyle: 'padding-left: 5px',
						id: 'bankLicenceNo',
						hiddenName: 'bankLicenceNo'
				}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						labelStyle: 'padding-left: 5px',
						id: 'faxNo',
						hiddenName: 'faxNo'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '经营范围',
						labelStyle: 'padding-left: 5px',
						id: 'busiRanges',
						hiddenName: 'busiRanges'
				}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                		xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '主营业务',
//						width:130,
						id: 'busMain',
						name: 'busMain'
	       			}]
                },{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '所属行业',
						labelStyle: 'padding-left: 5px',
						id: 'belInd',
						hiddenName: 'belInd'
						}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						hiddenName: 'applyDate',
						labelStyle: 'padding-left: 5px',
						id: 'applyDate',
						fieldLabel: '注册日期'
		        	}]
				
				}
				,{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				       xtype: 'displayfield',
						fieldLabel: '注册资金(万元)',
						labelStyle: 'padding-left: 5px',
						id: 'busAmt',
						hiddenName: 'busAmt'
				}]
				},{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属省',
	                    id: 'idprovince',
	                    allowBlank: false,
	                    store:provinceStore,
	                    hiddenName: 'province',
	                    width:130
	                }]
	            },{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属市',
	                    id:'idcity',
	                    hiddenName: 'city',
	                    allowBlank: false,
	                    store:citiesStore,
	                    width:130
	                }]
	            },{
	                columnWidth: .33, 
	                xtype: 'panel',
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    labelStyle: 'padding-left: 5px',
	                    fieldLabel: '商户所属区/县',
	                    id:'idareaD',
	                    hiddenName:'area',
	                    width:130,
	                    store:areasStore
	                }]
	            },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业面积/平方米',
						name: 'acreage',
						id: 'acreage',
						maskRe: /^[0-9\\.]+$/,
						maxLength:10,
						width:130
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{

				        xtype: 'combofordispaly',
						fieldLabel: '经营场所权属',
						baseParams: 'OWNER_BUSI',
						labelStyle: 'padding-left: 5px',
						id: 'idOwnerBusi',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','自有'],['2','租赁']],
							reader: new Ext.data.ArrayReader()
						}),
						hiddenName: 'ownerBusi'
						}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '员工人数',
						name: 'empNum',
						id: 'empNum',
						regex: /^[0-9]*$/,
						maxLength:8,
						width:130
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '公司网址',
						labelStyle: 'padding-left: 5px',
						id: 'homePage',
						hiddenName: 'homePage'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案号',
						labelStyle: 'padding-left: 5px',
						id: 'ICPrecordNo',
						hiddenName: 'ICPrecordNo'
						}]				
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: 'ICP备案公司名称',
						labelStyle: 'padding-left: 5px',
						id: 'ICPcompName',
						hiddenName: 'ICPcompName'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户调单联系人名称',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'contName',
						hiddenName: 'contName'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户调单联系人电话',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'contTel',
						hiddenName: 'contTel'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '商户固定电话',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'busiTel',
						hiddenName: 'busiTel'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '企业传真',
						labelStyle: 'padding-left: 5px',
						id: 'fax',
						hiddenName: 'fax'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '法定代表人电话',
						labelStyle: 'padding-left: 5px',
						id: 'managerTel',
						hiddenName: 'managerTel'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '联系人电话',
						labelStyle: 'padding-left: 5px',
						id: 'commTel',
						hiddenName: 'commTel'
					}]
					
				},{
					columnWidth: .33,
			       	xtype: 'panel',
			       	layout: 'form',
					items: [{
			        xtype: 'combofordispaly',
					fieldLabel: '商户级别',
					labelStyle: 'padding-left: 5px',
					id: 'idRislLvl',
					store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','正常商户'],['1','中级风险商户'],['2','高级风险商户'],['3','复审商户']],
					reader: new Ext.data.ArrayReader()
					}),
					hiddenName: 'rislLvl'
					}]
					},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户机构类型',
						id: 'idOrganizationType',
						hiddenName: 'organizationType',
    					store: new Ext.data.ArrayStore({
    	                    fields: ['valueField','displayField'],
    	                    data: [['2A','2A:农、林、牧、渔业'],['2B','2B:采矿业'],['2C','2C:制造业'],['2D','2D:电力、燃气及水的生产和供应业'],['2E','2E:建筑业'],['2F','2F:交通运输、仓储和邮政业'],['2G','2G:信息传输、计算机服务和软件业'],['2F','2F:交通运输、仓储和邮政业'],['2H','2H:批发和零售业'],['2I','2I:住宿和餐饮业'],['2J','2J:银行业'],['2K','2K:房地产业'],['2L','2L:租赁和商务服务业'],['2M','2M:科学研究、技术服务和地质勘查业'],['2N','2N:水利、环境和公共设施管理业'],['2O','2O:居民服务和其他服务业'],['2P','2P:教育'],['2Q','2Q:卫生、社会保障和社会福利业'],['2R','2R:文化、体育和娱乐业'],['2S','2S:公共管理和社会组织'],['2T','2T:国际组织']]
    	                })
		        	}]	
				}
				]
            },{
                title:'申请信息',
                id: 'basic1',
                frame: true,
				layout:'column',
                items: [{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单笔借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						id: 'sinDebAmount',
						hiddenName: 'sinDebAmount'
					}]
					
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单日借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						id: 'dayDebAmount',
						hiddenName: 'dayDebAmount'
						}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单月借记卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'monDebAmount',
						hiddenName: 'monDebAmount'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单笔预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'sinCreAmount',
						hiddenName: 'sinCreAmount'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单日预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'dayCreAmount',
						hiddenName: 'dayCreAmount'
					}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '客户申请单月预付费卡交易金额',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'monCreAmount',
						hiddenName: 'monCreAmount'
					}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '预授权功能',
						id: 'preAuthor',
						name: 'preAuthor',
						anchor: '90%',
						 disabled: true
		        	}]
			},{
        		columnWidth: .33,
       			xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '退货功能',
					id: 'returnFunc',
					name: 'returnFunc',
					anchor: '90%',
					 disabled: true
	        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '拓展人电话',
				name: 'netTel',
				id: 'netTel',
			 	allowBlank:false,
				maxLength:50,
				width:130
        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '拓展人',
				name: 'expander',
				id: 'expander',
			 	allowBlank:false,
				maxLength:50,
				width:130
        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '商户营业开始时间',
				id: 'openTime',
				name: 'openTime',
				minValue: '00:00:00',
				maxValue: '23:59:59',
				altFormats: 'H:i:s',
				format: 'H:i:s',
				increment: 10,
				anchor: '55%'
        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'displayfield',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '商户营业结束时间',
				id: 'closeTime',
				name: 'closeTime',
				minValue: '00:00:00',
				maxValue: '23:59:59',
				altFormats: 'H:i:s',
				format: 'H:i:s',
				increment: 10,
				anchor: '55%'
        	}]
		},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
        	defaults: {
    			xtype: 'displayfield',
    			labelStyle: 'padding-left: 5px'
    		},
   			items: [
	        {
        		xtype: 'displayfield',
				fieldLabel: '所属业务人员*',
				width:150,
				id: 'operNm',
				name: 'operNm'
        	}]
		},{
            columnWidth: .33, 
            xtype: 'panel',
            layout: 'form',
            items: [{
                xtype: 'combofordispaly',
                labelStyle: 'padding-left: 5px',
                fieldLabel: '代理商',
                id: 'idagentNo',
                allowBlank: false,
                store:agentStore,
                hiddenName: 'agentNo',
                width:130
            }]
        },{
    		columnWidth: .33,
   			xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'checkbox',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '是否有POS内卡受理经验',
				id: 'hasInnerPosExp',
				name: 'hasInnerPosExp',
			   disabled: true
        	}]
	},{
    		columnWidth: .33,
   			xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'checkbox',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '是否有POS外卡受理经验',
				id: 'hasOurPosExp',
				name: 'hasOurPosExp',
			   disabled: true
        	}]
	},{
		columnWidth: .33,
			xtype: 'panel',
    	layout: 'form',
			items: [{
        	xtype: 'checkbox',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '是否申请外卡',
			id: 'isAppOutSide',
			name: 'isAppOutSide',
			 disabled: true
    	}]
},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        xtype: 'displayfield',
						fieldLabel: '申请人留言',
						labelStyle: 'padding-left: 5px',
						maxLength: 100,//20121102修改
						id: 'appComm',
						hiddenName: 'appComm'
					}]
				}]		
            },{
            	title:'基本信息扩充',
                id: 'standardConfig',
                frame: true,
                autoScroll: true,
				layout:'column',
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '控股股东或实际控制人姓名*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'shareholder',
							disabled: true,
							name: 'shareholder'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'ididshareholder',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATES',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件种类*',
						width:130,
						allowBlank: false,
						id:'idshareholderTp',
						disabled: true,
						hiddenName: 'idshareholderTpmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件号码*',
						allowBlank: false,
						maxLength: 25,
						vtype: 'is5Alphanum',
						disabled: true,
						id: 'idshareholder'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'idshareholderDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '控股股东或实际控制人身份证件有效期*',
						maxLength: 8,
						minLength:8,  
						disabled: true,
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
						id: 'shareholderDate'
		        	}]
				},{
			        	xtype: 'basecomboselect',
			        	baseParams: 'COUNTRYS',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人国籍*',
						allowBlank: false,
						width:130,
						disabled: true,
						hiddenName: 'nationality',
						listeners: {
    					'select': function() {
							  var flag = mchntForm.getForm().findField('nationality').getValue();
							  if(flag=='86'){//中国国籍 则证件类型为身份证
							  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
							  	Ext.getCmp('idartifCertifTp').readOnly=true;
							  }else{//非中国国籍的则为 护照
							  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
							  	Ext.getCmp('idartifCertifTp').readOnly=false;
							  }
							  if(flag!='800'){//非外籍 则证件类型为身份证
								  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
								  	Ext.getCmp('idartifCertifTp').readOnly=true;
								  	//联系人授权人非必填
								  	Ext.getCmp('contact').allowBlank=false;
								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
								  	Ext.getCmp('linkTel').allowBlank=false;
								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
								  	
								  }else{//非中国国籍的则为 护照
								  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
								  	Ext.getCmp('idartifCertifTp').readOnly=false;
								  //联系人授权人必填
								  	Ext.getCmp('contact').allowBlank=false;
								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人姓名*');
								  	Ext.getCmp('linkTel').allowBlank=false;
								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('经(代)办人身份证号*');
								  }
    					}
    				}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       					labelStyle: 'padding-left: 5px',
							xtype:'dynamicCombo',
							fieldLabel: '外籍名*',
							width:130,
							allowBlank: false,
							disabled: true,
							methodName: 'getForeignName',
							id: 'idForeignName',
							hiddenName: 'foreignName'
			        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
				        	xtype: 'textnotnull',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法定代表人/负责人*',
							allowBlank: false,
							maxLength: 30,
							vtype: 'isOverMax',
							id: 'manager',
							disabled: true,
							name: 'manager'
			        	}
		        	]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件类型*',
						width:130,
						allowBlank: false,
						id:'idartifCertifTp',
						disabled: true,
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码*',
						maxLength: 18,
						disabled: true,
						vtype: 'is5Alphanum',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id: 'ididentityNoDate',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法定代表人/负责人证件号码有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
		                disabled: true,
						id: 'identityNoDate'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人姓名*',
						maxLength: 30,
						disabled: true,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	id:'ididcontact',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人证件类型*',
						width:130,
						allowBlank: false,
						id:'idcontact',
						disabled: true,
						hiddenName: 'contactmd'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
	       				allowBlank: true,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证号',
						maxLength: 20,
						vtype: 'is7Alphanum',
						disabled: true,
						id: 'linkTel'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '经(代)办人身份证号有效日期*',
						maxLength: 8,
						minLength:8,  
						vtype: 'isNumber',
		                vtypeText:'只能输入数字',
		                disabled: true,
						id: 'linkTelDate'
		        	}]
				}
				]
            },{
				title:'清算信息',
                id: 'settle',
                frame: true,
                layout: 'border',
                items: [{
			       xtype: 'panel',
			      html: '<br/><br/><br/><br/><br/><br/>'
                },{
                	region: 'center',
                	items:[setGridPanel]
                }
                ]
		    },{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                id:'feeForm',
                region: 'north',
//                height: 75,
                layout: 'column',
                items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	hidden:true,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			 xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '发卡机构',
					id: 'idFK_BRCH_NO',
					name: 'FK_BRCH_NO',
					maxLength: 8,
					width:150
		        	}]
			},{
		        		columnWidth: .33,
		        		labelWidth: 70,
		        		hidden:true,
		            	layout: 'form',
		        		items: [{
					        xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端代码',
							id: 'idTERM_ID',
							name: 'TERM_ID',
							maxLength: 8,
						    width:100
							 
				        }]
		        	},{
                		columnWidth: .33,
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
							disabled:true,
							readOnly: true,
							width:80
						}]
					},{	 
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
	       				items: [{
                			xtype: 'displayfield',
							iconCls: 'recover',
							text:'临时编号',
							id: 'tmpNo',
							width: 60
                		}]
					},{
        		    columnWidth: .33,
        	     	xtype: 'panel',
        	     	hidden:true,
        		    labelWidth: 70,
		            layout: 'form',
	       		    items: [{
	       			xtype: 'combofordispaly',
	       			methodName: 'getAreaCodecode',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '地区码',
					editable: true,
					id: 'idCITY_CODE',
					hiddenName: 'CITY_CODE',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	hidden:true,
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        store:AgenStore,
					labelStyle: 'padding-left: 5px',
					fieldLabel: '目的机构',
					blankText: '请选择目的机构',
					id: 'idTO_BRCH_NO',
					hiddenName: 'TO_BRCH_NO',
					width:150
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	hidden:true,
	        	labelWidth: 70,
		        layout: 'form',
	       		items: [{
			        	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						blankText: '请选择交易渠道',
						id: 'idCARD_TYPE',
						hiddenName: 'CARD_TYPE',
						width:150
		        	}]
			},{
	        		columnWidth: .33,
	        		labelWidth: 70,
	        		hidden:true,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combofordispaly',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						blankText: '请输入交易渠道',
						id: 'idCHANNEL_NO',
						hiddenName: 'CHANNEL_NO',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'combofordispaly',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						blankText: '请输入业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	labelWidth: 70,
		        	layout: 'form',
	       			items: [{
						xtype: 'combofordispaly',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						blankText: '请输入交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						width:150
		        	}]
				}]
                },{
			       xtype: 'panel',
			      html: '<br/><br/><br/><br/><br/><br/>'
                },{
                	region: 'center',
                	items:[feeGridPanel]
                }
               // ,{
              //  	region: 'center',
              // 	items:[gridPanel]
              //  },{
              //  	region: 'east',
              //  	width:600,
              //  	items: [detailGrid]
              //  }
                ]
		    },{
				title:'证书影像',
                id: 'images',
                frame: true,
                
                layout: 'border',
                items: [{
                	xtype: 'panel',
                	region: 'center',
                	autoScroll: true,
        			items : [dataview,dataview1],
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
							});
							storeImg1.reload({
								params: {
									start: 0,
									imagesId: mchntId
								}
							});
						}
					}]
                }]
		    },{
		    	title:'受益人信息',
                id: 'settle2',
                frame: true,
				layout:'column',
                items: [{
					region: 'center',
					items:[feeGridPanel1]
        		}]
		    },{

				title:'操作记录',
                id: 'audit',
                frame: true,
                layout: 'border',
                autoScroll: true,
                items: [{
                	region: 'center',
                	items:[auditGridPanel]
                }/*,{
 			       xtype: 'panel',
 			      html: '<br/><br/><br/><br/><br/><br/>'
                 }*/,{
                     id:'auditForm',
                     region: 'south',
//                     height: 75,
                     autoHeight: true,
                     layout: 'column',
                     items: [{	 
     		        		xtype: 'panel',
     		        		hidden:true,
     		        		layout: 'form',
     		        		hidden:true,
     	       				items: [{
                     			xtype: 'displayfield',
     							iconCls: 'recover',
     							text:'商户索引',
     							id: 'mchntInd',
     							width: 60
                     		}]
     					}]     					
                     }]
		    
		    }]
        }]
    });

    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 980,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
	 
				var feeTypeValue = baseStore.getAt(0).data.feeType;
 
				var settleType=baseStore.getAt(0).data.settleType;
				
				
				detailWin.setTitle('商户详细信息[商户编号：' + mchntId + ']');
				detailWin.show();

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					mchntForm.getForm().findField('mcc').setValue(baseStore.getAt(0).data.mcc);
//					mchntForm.getForm().findField('idmcc2').setValue(baseStore.getAt(0).data.mcc2);
				});
				//初始化省、市、县信息
                SelectOptionsDWR.getComboDataWithParameter('CITIES',baseStore.getAt(0).data.province,
						function(ret){
							citiesStore.loadData(Ext.decode(ret));
							Ext.getCmp('idcity').setValue(baseStore.getAt(0).data.city);
						});
								
                SelectOptionsDWR.getComboDataWithParameter('AREAS',baseStore.getAt(0).data.city,
						function(ret){
							areasStore.loadData(Ext.decode(ret));
							//mchntForm.getForm().findField('idareaD').setValue(baseStore.getAt(0).data.area);
							Ext.getCmp('idareaD').setValue(baseStore.getAt(0).data.area);
						});
				//加载商户费率信息
				feeStore.reload({
					params:{
						start:0,
						tmpNo: baseStore.getAt(0).data.tmpNo
					}
				});
				
				//加载商户清算信息
				setStore.reload({
					params:{
						start:0,
						mchtNo:mchntId
					}
				});
				
				//加载商户操作信息
				auditStore.reload({
					params:{
						start:0,
						mchntInd: baseStore.getAt(0).data.mchntInd
					}
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
				    		showPIC(val.id);
				    	});
				    	Ext.get(rec.btDw).on('click', function(obj,val){
        		        downPIC(val.id);
        	            });
					}
				});
				
				storeImg1.on('load',function(){
		for(var i=0;i<storeImg1.getCount();i++){
			var rec = storeImg1.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC1(val.id);
        	});
		}
	});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: mchntId
					}
				});
				storeImg1.reload({
					params: {
						start: 0,
						imagesId: mchntId
					}
				});
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}