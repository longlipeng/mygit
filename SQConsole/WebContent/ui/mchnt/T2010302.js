Ext.onReady(function() {
	//保存是否验证成功的变量
	//修改时默认为true,当信息变动时为false
	var verifyResult = true;
	
	//******************图片处理部分**********开始********
	var hasUpload = "0";
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
	var custom = new Ext.Resizable('showBigPic', {
		    wrap:true,
		    pinned:true,
		    minWidth: 50,
		    minHeight: 50,
		    preserveRatio: true,
		    dynamic:true,
		    handles: 'all',
		    draggable:true
		});
	var customEl = custom.getEl();
	document.body.insertBefore(customEl.dom, document.body.firstChild);
	customEl.on('dblclick', function(){
		customEl.puff();
	});
	customEl.hide(true);
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
 	function delPIC(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(5)).data;
		 		T20100.deleteImgFile(rec.fileName,function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	storeImg.on('load',function(){
		for(var i=0;i<storeImg.getCount();i++){
			var rec = storeImg.getAt(i).data;
        	Ext.get(rec.btBig).on('click', function(obj,val){
        		showPIC(val.id);
        	});
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC(val.id);
        	});
		}
	});
	
	storeImg1.on('load',function(){
		for(var i=0;i<storeImg1.getCount();i++){
			var rec = storeImg1.getAt(i).data;
        	Ext.get(rec.btDw).on('click', function(obj,val){
        		downPIC1(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC1(val.id);
        	});
		}
	});
	
	function delPIC1(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg1.getAt(id.substring(6)).data;
		 		T20100.deleteImgFile(rec.fileName,function(ret){
		 			if("S" == ret){
		 				storeImg1.reload({
							params: {
								start: 0,
								imagesId: imagesId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
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
							'<div style=""><input type="button" id="{btBig}" value="放大"><input type="button" id="{btDw}" value="下载"><input type="button" id="{btDel}" value="删除"></div>',
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
							'<div style=""><input type="button" id="{btDw}" value="下载">&nbsp;<input type="button" id="{btDel}" value="删除"></div>',
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
	
	 // 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20100Action_upload.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.jpg;*.png;*.jpeg;*.zip;*.rar',
		fileTypesDescription : '图片文件',
		title: '证书影印文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId
				}
			});
			storeImg1.reload({
				params: {
					start: 0,
					imagesId: imagesId
				}
			});
		},
		postParams: {
			txnId: '20101',
			subTxnId: '06',
			imagesId: imagesId
		}
	});

	//******************图片处理部分**********结束********
	
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

	//渠道数据集
	var feeDiscStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('FEE_DISC_NORMAL',function(ret){
		feeDiscStore.loadData(Ext.decode(ret));
	});
	
	var clearTypeStore = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行结算账户1账户'],['P','本行结算账户2(S)或单位卡'],['O','他行结算账户1'],['S','他行结算账户2(S)']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore1 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['A','本行结算账户1'],['P','本行结算账户2(S)或单位卡'],['O','他行结算账户1'],['S','他行结算账户2(S)']],
		reader: new Ext.data.ArrayReader()
	});
	
	var clearTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['P','本行结算账户1或单位卡'],['S','他行结算账户2(S)']],
		reader: new Ext.data.ArrayReader()
	});
	
    var AgenStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('AGENCODE',function(ret){
		AgenStore.loadData(Ext.decode(ret));
	});
	
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

	var hasSub = false;
	var fm = Ext.form;

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
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 80,
            sortable: true
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
            width: 70
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 90
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 90
        }]
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
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    var detailGrid = new Ext.grid.GridPanel({
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
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

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
	gridStore.load({
		params: {
			start: 0
		}
	});
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 60},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	
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
	
	var gridPanel = new Ext.grid.GridPanel({
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
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['计费代码：',{
			xtype: 'textfield',
			id: 'serdiscCd',
			width: 60
		},'-','计费名称：',{
			xtype: 'textfield',
			id: 'serdiscNm',
			width: 110
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'widfalg',
			width: 60,
			handler: function(){
				gridStore.load();
			}
		}]
	});
	gridPanel.getStore().on('beforeload',function() {
		//Ext.getCmp('setup').disable();
	});

	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
	//	Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	store.on('beforeload', function() {
		if (gridPanel.getSelectionModel().hasSelection()) {
			Ext.apply(this.baseParams, {
	            start: 0,
	            discCd: gridPanel.getSelectionModel().getSelected().data.discCd
	        });
		}else{
			showErrorMsg('请选择计费算法',groupWin);
		}
		
	});
	gridStore.on('beforeload', function() {
		store.removeAll();
	});

	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf4Add'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNoInput',mapping: 'mchtNo'},
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
			{name: 'discCode',mapping: 'feeRate'},
			{name: 'feeDiv1',mapping: 'feeDiv1'},
			{name: 'feeDiv2',mapping: 'feeDiv2'},
			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},		
			
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'currAccount',mapping: 'currAccount'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'compAccountBankCode',mapping: 'compAccountBankCode'},
			{name: 'compAccountBankName',mapping: 'compAccountBankName'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'bankAccountCode',mapping: 'bankAccountCode'},
			{name: 'corpBankName',mapping: 'corpBankName'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
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
			{name:'mchtNoHx',mapping:'mchtNoHx'},  //北京银行商户号
			{name: 'mchtSXNoHx',mapping: 'mchtSXNoHx'}, //随行付商户号
			//{name: 'engNameAbbr',mapping: 'engNameAbbr'},
			{name:'tmpNo',mapping:'tmpNo'},
			
			{name:'province',mapping:'province'},
			{name:'city',mapping:'city'},
			{name:'depart',mapping:'depart'},
			{name:'mchtTypeCause',mapping:'mchtTypeCause'},
			{name:'countryCode',mapping:'countryCode'},
			{name:'busScope',mapping:'busScope'},
			{name:'busMain',mapping:'busMain'},
			{name:'linkTel',mapping:'linkTel'},
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
			{name:'isWineshop',mapping:'isWineshop'},
			{name:'wineshopLvl',mapping:'wineshopLvl'},
			{name:'isMoreAcq',mapping:'isMoreAcq'},
			{name:'isAppOutSide',mapping:'isAppOutSide'},
			{name:'hasInnerPosExp',mapping:'hasInnerPosExp'},
//			{name:'serType',mapping:'serType'},
			{name:'serLvl',mapping:'serLvl'},
			{name:'src',mapping:'src'},
			{name:'referrer',mapping:'referrer'},
			{name:'mchtNoOld',mapping:'mchtNoOld'},
			{name:'mcc2',mapping:'mcc2'},
			{name:'proxy',mapping:'proxy'},
			{name:'proxyTel',mapping:'proxyTel'},
			{name:'expander',mapping:'expander'},
			{name:'area',mapping:'area'},
			{name:'hasOurPosExp',mapping:'hasOurPosExp'},
			{name:'nationality',mapping:'nationality'},
			//====By Mike====用于显示“商户评估信息”页面的值==================
			{name:'feeDiffer',mapping:'feeDiffer'},
//			{name:'managerYears',mapping:'managerYears'},
			{name:'singleAmt',mapping:'singleAmt'},
			{name:'monthTotalAmt',mapping:'monthTotalAmt'},
			{name:'evalWayFlag',mapping:'evalWayFlag'},
			{name:'evalLevel',mapping:'evalLevel'},
			{name:'evalEndTime',mapping:'evalEndTime'},
			//====By Mike======================
			
			//基本信息-2 by zhangqy
			{name: 'mchtCNAbbr',mapping: 'mchtCNAbbr'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtENAbbr',mapping: 'mchtENAbbr'},
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
			
			{name:'preAuthor',mapping:'preAuthor'},
			{name:'returnFunc',mapping:'returnFunc'}
		]),
		autoLoad: false
	});

	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();

				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var settleType =  baseStore.getAt(0).data.settleType;
				/*if(baseStore.getAt(0).data.feeRate!=0){
					//Ext.getCmp('discCode').setValue(baseStore.getAt(0).data.feeRate);
				}*/
				//2011/12/19变更
				/*if("" == baseStore.getAt(0).data.idOtherNo) {
					Ext.getCmp('idOtherNo').disable();
					Ext.getCmp('selectOtherNo').setValue(false);
				} else {
					Ext.getCmp('idOtherNo').enable();
					Ext.getCmp('selectOtherNo').setValue(true);
				}*/
				//end
				/*if(settleType>'1'){
						Ext.getCmp('idfeeCycle').show();
			            Ext.getCmp('feeCycle').show();
			            Ext.getCmp('feeCycle').allowBlank=false;
				}else{
			            Ext.getCmp('feeCycle').hide();
			            Ext.getCmp('idfeeCycle').hide();
			            Ext.getCmp('feeCycle').allowBlank=true;
				}*/
//				imagesId = baseStore.getAt(0).data.mchtNo;
				Ext.getCmp('verifyButton').disable();
				//清算信息
				var etpsAttr = baseStore.getAt(0).data.etpsAttr;
				var setRpt = baseStore.getAt(0).data.settleRpt;
				if(setRpt=='1'){//对私
					//法人账户必填
				    Ext.getCmp('corpBankName').allowBlank=false;
				  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称*');
				  	Ext.getCmp('feeAcct').allowBlank=false;
				  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号*'); 
				  	
				  //定向委托账户非必填
				    Ext.getCmp('dirBankName').allowBlank=true;
				  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
				  	Ext.getCmp('dirAccountName').allowBlank=true;
				  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
				  	Ext.getCmp('dirAccount').allowBlank=true;
				  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
                	if(etpsAttr=='02'){//公司制企业
                		//公司账户必填
						    Ext.getCmp('compAccountBankName').allowBlank=false;
						  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称*');
						  	Ext.getCmp('settleAcct').allowBlank=false;
						  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号*'); 
                	}else{
                		//公司账户非必填
						 Ext.getCmp('compAccountBankName').allowBlank=true;
						  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称');
						  	Ext.getCmp('settleAcct').allowBlank=true;
						  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号'); 												 
                   
                	}
                }else if(setRpt=='2'){//对公
                	//公司账户必填
				    Ext.getCmp('compAccountBankName').allowBlank=false;
				  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称*');
				  	Ext.getCmp('settleAcct').allowBlank=false;
				  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号*'); 
				  	 //定向委托账户非必填
				    Ext.getCmp('dirBankName').allowBlank=true;
				  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
				  	Ext.getCmp('dirAccountName').allowBlank=true;
				  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
				  	Ext.getCmp('dirAccount').allowBlank=true;
				  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
				  //法人账户非必填
				    Ext.getCmp('corpBankName').allowBlank=true;
				  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称');
				  	Ext.getCmp('feeAcct').allowBlank=true;
				  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号'); 	
                }else{//定向委托账户
                	//法人账户非必填
				    Ext.getCmp('corpBankName').allowBlank=true;
				  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称');
				  	Ext.getCmp('feeAcct').allowBlank=true;
				  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号'); 
				  	
				  //定向委托账户必填
				    Ext.getCmp('dirBankName').allowBlank=false;
				  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称*');
				  	Ext.getCmp('dirAccountName').allowBlank=false;
				  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名*'); 
				  	Ext.getCmp('dirAccount').allowBlank=false;
				  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*'); 
				  	if(etpsAttr=='02'){//公司制企业
                		//公司账户必填
						    Ext.getCmp('compAccountBankName').allowBlank=false;
						  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称*');
						  	Ext.getCmp('settleAcct').allowBlank=false;
						  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号*'); 
                	}else{
                		//公司账户非必填
						 Ext.getCmp('compAccountBankName').allowBlank=true;
						  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称');
						  	Ext.getCmp('settleAcct').allowBlank=true;
						  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号'); 												 
                   
                	}
                }
				
				//初始化授权人、授权人身份证
				var flag =  baseStore.getAt(0).data.nationality;
				 if(flag!='800'){//非外籍 则证件类型为身份证
//					  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
					  	Ext.getCmp('idartifCertifTp').readOnly=true;
					  	//联系人授权人非必填
					  	Ext.getCmp('contact').allowBlank=true;
					  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/授权人姓名');
					  	Ext.getCmp('linkTel').allowBlank=true;
					  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('授权人身份证号');
					  	
					  }else{//非中国国籍的则为 护照
//					  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
					  	Ext.getCmp('idartifCertifTp').readOnly=false;
					  //联系人授权人必填
					  	Ext.getCmp('contact').allowBlank=false;
					  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/授权人姓名*');
					  	Ext.getCmp('linkTel').allowBlank=false;
					  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('授权人身份证号*');
					  }

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					Ext.getCmp('idmcc').setValue(baseStore.getAt(0).data.mcc);
				});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: imagesId
					}
				});
				storeImg1.reload({
					params: {
						start: 0,
						imagesId: imagesId
					}
				});
				/*mchntForm.getForm().findField('clearType').setValue(settleAcct.substring(0,1));
				mchntForm.getForm().findField('settleAcct').setValue(settleAcct.substring(1));*/
				
				//财务POS商户受控
				/*if(baseStore.getAt(0).data.mchtGroupFlag == '3'){
					mchntForm.getForm().findField("clearType").readOnly=true;
             		mchntForm.getForm().findField("settleType").readOnly=true;
				}*/
				   //初始化省、市、县信息
                      SelectOptionsDWR.getComboDataWithParameter('CITIES',baseStore.getAt(0).data.province,
  							function(ret){
							Ext.getCmp('idcity').getStore().loadData(Ext.decode(ret));
							/*var rec = Ext.getCmp('idcity').getStore().getAt(0);
							Ext.getCmp('idcity').setValue(rec.get('valueField'));*/
							Ext.getCmp('idcity').setValue(mchntForm.getForm().findField('idcity').getValue());
							Ext.getCmp('idcity').parentP=baseStore.getAt(0).data.province;
					});
								
                       SelectOptionsDWR.getComboDataWithParameter('AREAS',baseStore.getAt(0).data.province,
   							function(ret){
							Ext.getCmp('idarea').getStore().loadData(Ext.decode(ret));
							/*var rec1 = Ext.getCmp('idarea').getStore().getAt(0);
							Ext.getCmp('idarea').setValue(rec1.get('valueField'));*/
							Ext.getCmp('idarea').setValue(mchntForm.getForm().findField('idarea').getValue());
							Ext.getCmp('idarea').parentP=baseStore.getAt(0).data.city;
					});
				//加载商户费率信息
				feeStore.reload({
					params:{
						start:0,
						tmpNo: baseStore.getAt(0).data.tmpNo
					}
				});
				
			}else{
				showErrorMsg("加载商户信息失败，请返回刷新数据后重试",mchntForm,function(){
					window.location.href = Ext.contextPath + '/page/mchnt/T20103.jsp';
				});
			}
		}
	});
	
	//计费算法的 Form表单
	var feeDiscForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:450,
                	items: [detailGrid]
                }]
		}]
	});
	//弹出的 计费算法窗口
	var groupWin = new Ext.Window({
		title: '计费算法',
		initHidden: true,
		header: true,
		frame: true,
		closable: true,
		modal: true,
		width: 870,
		autoHeight: true,
		items: [feeDiscForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if (gridPanel.getSelectionModel().hasSelection()) {
					 Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd);
					 groupWin.hide();
				}else{
					showErrorMsg('请选择计费算法',groupWin);
				}
				
			}
		},{
			text: '取消',
			handler: function() {
				groupWin.hide();
			}
		}]
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
		{header: '计费代码',dataIndex: 'feeType',sortable: true,width:100,
			editor : new Ext.form.ComboBox( {
				store: feeDiscStore,
				displayField: 'displayField',
				valueField: 'valueField',
				mode: 'local',
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true
			})},
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
	var feeGridPanel = new Ext.grid.EditorGridPanel({
		title: '商户费率信息',
		frame: true,
		border: true,
		height: 170,
		columnLines: true,
		//autoExpandColumn: 'discNm',
		clicksToEdit: true,
		stripeRows: true,
		store: feeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户信息列表......'
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
		},'-',{
			xtype: 'button',
			text:'删除',
			iconCls: 'delete',
			//disabled: true,
			handler: function() {
				if(feeGridPanel.getSelectionModel().hasSelection()) {
					var rec = feeGridPanel.getSelectionModel().getSelected();
					showConfirm('确定要删除该条费率信息吗？',feeGridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T201001Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,feeGridPanel);
										feeGridPanel.getStore().reload();
										feeGridPanel.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,feeGridPanel);
									}
								},
								params: { 
									discId: rec.get('discId'),								
									txnId: '201001',
									subTxnId: '01'
								}
							});
						}
					});
				}
			}
		}]
	});
	
	feeStore.on('beforeload',function(){//20120924解决商户信息维护之修改商户信息页面中点击费率信息分页中刷新按钮信息会清空的bug
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
	
	var detailWin = new Ext.Window({
					title : '费率详情',
					initHidden : true,
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
					buttons : [ {
								text : '关闭',
								handler : function() {
									detailWin.hide();
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
				detailWin.show();	
		}
		}
	)

//	//重置账号验证
//	function resetVerify(){
//		verifyResult = false;
//	    Ext.getCmp('verifyName').setValue('');
//	    Ext.getCmp('verifySta').setValue('<font color="red">未验证</font>');
//	}
	
	var hasSub = false;

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var mchntForm = new Ext.FormPanel({
        title: '修改商户信息[商户编号：'+mchntId+']' ,
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{	
				columnWidth: .33,
				xtype: 'panel',
				layout: 'form',
					items: [{
						xtype: 'textnotnull',
				        labelStyle: 'padding-left: 5px',
				        fieldLabel: '15位商户编号*',
						regex: /^\d{15}$/,
						regexText: '该输入框只能输入15位的数字',
						readOnly: true,  
						allowBlank: false,
				        width:150,
				        id: 'mchtNoInput'
			    }]
         	},{
	        	columnWidth: .33,
		        layout: 'form',
		        xtype: 'panel',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW_NORMAL',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属分公司*',
					allowBlank: false,
					blankText: '请选择所属分公司',
					id: 'idacqInstId',
					hiddenName: 'acqInstId'/*,
					anchor: '90%'*/
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户名称*',
					maxLength: '120',
					vtype: 'isOverMax',
					//readOnly:true,
					id: 'mchtNm',
					name:'mchtNm'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
//						readOnly: true,
						anchor: '90%',
						listeners: {
							'select': function() {
								Ext.getCmp('idmcc').reset();
								Ext.getCmp('idmcc').parentP=this.value;
								Ext.getCmp('idmcc').getStore().reload();
							}
						}
		        	}]
			},{
	        	columnWidth: .33,
		        layout: 'form',
	       		items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: false,
						lazyRender: true,
						blankText: '请选择商户MCC',
						id: 'idmcc',
//						readOnly: true,
						hiddenName: 'mcc',
						anchor: '90%',
						listeners: {
    					'select': function() {
								Ext.getCmp('mchtNoInput').setValue('');
								Ext.getCmp('verifyButton').enable();
							}
						}
		        	}]
			},{
	        	columnWidth: .33,
		        layout: 'form',
		        hidden:true,
	       		items: [{xtype: 'dynamicCombo',
				         labelStyle: 'padding-left: 5px',
				         fieldLabel: '行政区号码*',
				         id: 'idareaNo',
				         allowBlank: false,
				         methodName: 'getXingZheng',
				         hiddenName: 'areaNo',
				         width:130/*,
    					callFunction : function() {
								Ext.getCmp('mchtNoInput').setValue('');
								Ext.getCmp('verifyButton').enable();
							}*/
						
		        	}]
			},{
	        	columnWidth: .33,
		        layout: 'form',
	       		items: [{
					        xtype: 'button',
					        text: '生成商户号',
					        id: 'verifyButton',
//					        disable:true,
					        handler: function() {
					        	var bt = this;
					        	bt.disable();
					        	if(mchntForm.getForm().findField('idmcc').getValue()==''){
					            	showErrorMsg("生成商户号前请选择商户MCC。",mchntForm);
					            	bt.enable();
					            	return;
					            }else if( !Ext.getCmp('idareaNo').isValid() ){
					            	showErrorMsg("生成商户号前请选择行政商户所属市、县",mchntForm);
					            	bt.enable();
					            	return;
					        	}
					        	
					          	bt.enable();
				            	mchntForm.getForm().findField('idmcc').disable();
				            	mchntForm.getForm().findField('idareaNo').disable();
				            	T20100.createMchtNo(Ext.getCmp("idmcc").getValue(),Ext.getCmp('idareaNo').getValue(),function(ret){
				            		var sta = '';
				            		var msg = '';
				            		var err = '';
				            		var bSta = '';
				            		
		            		
				            		for(var key in ret){
				            			if(key=='result'){
				            				sta = ret[key];
				            			}else if(key=='bResult'){
				            				bSta = ret[key];
				            			}else if(key=='err'){
				            				err = ret[key];
				            			}else if(key=='msg'){
				            				msg = ret[key];
				            			}else if(key=='mchtNoInPut'){
				            				Ext.getCmp('mchtNoInput').setValue(ret[key]);
				            			}else if(key==''){
				            				
				            			}
				            		}
				            		if(sta == '00' && bSta == '00'){
				            			Ext.MessageBox.show({
											msg: "商户号生成成功",
											title: '成功',
											animEl: Ext.get(mchntForm.getEl()),
											buttons: Ext.MessageBox.OK,
											icon: 'message-success',
											modal: true,
											width: 300
										});
				            		}else if(bSta == '01'){
				            			Ext.MessageBox.show({
											msg: "该MCC商户号序列所剩不多，请尽快申请新号段",
											title: '成功',
											animEl: Ext.get(mchntForm.getEl()),
											buttons: Ext.MessageBox.OK,
											icon: 'message-success',
											modal: true,
											width: 300
										});	
				            		}else{
				            			showErrorMsg(err,mchntForm);
				            		}
				                	mchntForm.getForm().findField('idmcc').enable();
				                	mchntForm.getForm().findField('idareaNo').enable();
				                	bt.enable();
				                });
								}
							}]
			},{
				columnWidth: .1,
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
        	items:[
        		{
                    title:'基本信息',
                    id: 'basic',
                    frame: true,
    				layout:'column',
                    items: [{
    	        		columnWidth: .33,
    	       			xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户简称*',
    						allowBlank: false,
    						maxLength:'60',
    						id: 'mchtCnAbbr',
    						name: 'mchtCnAbbr'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'dynamicCombo',
    			        	methodName: 'getEnt',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户性质*',
    						allowBlank: false,
    						width:130,
    						id:'idEtpsAttr',
    						hiddenName: 'etpsAttr',
    						 callFunction : function() {
    							 if(this.value=='02'){//公司账户必填
    								 Ext.getCmp('compAccountBankName').allowBlank=false;
    								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司帐号开户行名称*');
    								  	Ext.getCmp('settleAcct').allowBlank=false;
    								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号*'); 
    							 }else if(mchntForm.getForm().findField('idsettleRpt').getValue()!='2'){
    								//公司账户非必填
    								 Ext.getCmp('compAccountBankName').allowBlank=true;
    								  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司帐号开户行名称');
    								  	Ext.getCmp('settleAcct').allowBlank=true;
    								  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号'); 
    							  
    							 }
    						 }
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '注册地址*',
    						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
    						maxLength: 100,
    						allowBlank: false,
//    						vtype: 'isOverMax',
    						id: 'regAddr',
    						name: 'regAddr',
    						validator : function(value) {  
    					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
    					        if(length>100){  
    					            return '该输入项不能超过100个字符或50个汉字';  
    					        }  
    					        return true;  
    					    }
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '邮政编号*',
    						maxLength: '6',
    						allowBlank: false,
    						regex: /^[1-9]\d{5}$/,
    						id: 'postalCode',
    						name: 'postalCode'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '电子邮件*',
    						maxLength: 40,
    						vtype: 'isOverMax',
    						id: 'commEmail',
    						name: 'commEmail',
    						vtype: 'email'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '营业执照编号*',
    						maxLength: 20,
    						vtype:'is6Alphanum', 
    		                vtypeText:'只能输入字母或数字',
    						id: 'licenceNo'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '企业组织机构代码',
    						maxLength: 20,
//    						allowBlank: false,
    						vtype: 'is4Alphanum',
    						id: 'bankLicenceNo',
//    						allowBlank: false,20120727deleted
    						name: 'bankLicenceNo'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
//    						xtype: 'textnotnull',
    	       				xtype: 'textfield',
//    	       				allowBlank: false,
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '税务登记证号码',
    						minLength:15,
    						maxLength: 15,
    						//vtype: 'is3Alphanum',
    						id: 'faxNo'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '经营范围*',
    						maxLength: '60',
    						allowBlank: false,
    						id: 'busiRange',
    						name: 'busiRange'
    		        	}]
    				},{
                    	columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
                    		xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '主营业务*',
    						maxLength: 50,
    						allowBlank: false,
    						vtype: 'isOverMax',
    						width:130,
    						id: 'busMain',
    						name: 'busMain'
    	       			}]
                    },{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '所属行业',
    						maxLength: '60',
//    						allowBlank: false,
    						id: 'belInd',
    						name: 'belInd'
    		        	}]
    				},{
    					columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'datefield',
    						name: 'applyDate',
    						labelStyle: 'padding-left: 5px',
    						id: 'applyDate',
    						fieldLabel: '注册日期*',
    						allowBlank: false,
    						width:128
    					}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '注册资金(万元)*',
    						regex: /^(([1-9]{1}\d*)|([0]{1}))(\.(\d*[1-9]|[1-9]))?$/,//20120823修改的正则表达式
    						//regex: /^(([0-9]+\.[0-9]*[0-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
    						regexText: '注册资金必须是正确的金额格式',
    						maxLength: 12,
//    						vtype: 'isOverMax',
//    						vtype: 'isMoney',
    						id: 'busAmt',
    						name: 'busAmt',
    						allowBlank: false,
    						width:128
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'basecomboselect',
    			        	baseParams: 'COUNTRYS',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '法人/负责人国籍*',
    						allowBlank: false,
    						width:130,
    						hiddenName: 'nationality',
    						listeners: {
        					'select': function() {
    							  var flag = mchntForm.getForm().findField('nationality').getValue();
    							/*  if(flag=='86'){//中国国籍 则证件类型为身份证
    							  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
    							  	Ext.getCmp('idartifCertifTp').readOnly=true;
    							  }else{//非中国国籍的则为 护照
    							  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
    							  	Ext.getCmp('idartifCertifTp').readOnly=false;
    							  }*/
    							  if(flag!='800'){//非外籍 则证件类型为身份证
    								  	mchntForm.getForm().findField('artifCertifTp').setValue("01");
    								  	Ext.getCmp('idartifCertifTp').readOnly=true;
    								  	//联系人授权人非必填
    								  	Ext.getCmp('contact').allowBlank=true;
    								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/授权人姓名');
    								  	Ext.getCmp('linkTel').allowBlank=true;
    								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('授权人身份证号');
    								  	
    								  }else{//非中国国籍的则为 护照
    								  	mchntForm.getForm().findField('artifCertifTp').setValue("06");
    								  	Ext.getCmp('idartifCertifTp').readOnly=false;
    								  //联系人授权人必填
    								  	Ext.getCmp('contact').allowBlank=false;
    								  	mchntForm.getForm().findField('contact').getEl().up('.x-form-item').down('.x-form-item-label').update('联系人/授权人姓名*');
    								  	Ext.getCmp('linkTel').allowBlank=false;
    								  	mchntForm.getForm().findField('linkTel').getEl().up('.x-form-item').down('.x-form-item-label').update('授权人身份证号*');
    								  }
        					}
        				}
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    				        	xtype: 'textnotnull',
    							labelStyle: 'padding-left: 5px',
    							fieldLabel: '法人/负责人*',
    							allowBlank: false,
    							maxLength: 30,
    							vtype: 'isOverMax',
    							id: 'manager',
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
    						fieldLabel: '法人/负责人证件类型*',
    						width:130,
    						allowBlank: false,
    						id:'idartifCertifTp',
    						hiddenName: 'artifCertifTp'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '法人/负责人证件号码*',
    						maxLength: 18,
    						vtype: 'is5Alphanum',
    						id: 'identityNo'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '联系人/授权人姓名*',
    						maxLength: 30,
    						vtype: 'isOverMax',
    						id: 'contact'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    	       				xtype: 'textfield',
    	       				allowBlank: true,
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '授权人身份证号',
    						maxLength: 20,
    						vtype: 'is7Alphanum',
    						id: 'linkTel'
    		        	}]
    				},{
    	                columnWidth: .33, 
    	                xtype: 'panel',
    	                layout: 'form',
    	                items: [{
    	                    xtype: 'dynamicCombo',
    	                    labelStyle: 'padding-left: 5px',
    	                    fieldLabel: '商户所属省*',
    	                    id: 'idprovince',
    	                    allowBlank: false,
    	                    methodName: 'getProvinces',
    	                    hiddenName: 'province',
    	                    width:130,
    	                    callFunction : function() {
    	            			Ext.getCmp('idcity').reset();
    	            			Ext.getCmp('idcity').setValue('-');
    	            			Ext.getCmp('idarea').reset();
    	            			Ext.getCmp('idcity').parentP=this.value;
    	            			Ext.getCmp('idcity').getStore().reload();
    	            			Ext.getCmp('idarea').parentP='';
    	            			Ext.getCmp('idarea').getStore().reload();
    	            			Ext.getCmp('idcity').reset();
    	            		}
    	                    /*listeners: {
    	    					'select': function() {
    	    							citiesStore.removeAll();
    									SelectOptionsDWR.getComboDataWithParameter('CITIES',mchntForm.getForm().findField('idprovince').getValue(),
    										function(ret){
    										citiesStore.loadData(Ext.decode(ret));
    										var rec = citiesStore.getAt(0);
    										Ext.getCmp('idcity').setValue(rec.get('valueField'));
    									});
    			    					}
    			    				}*/
    			                }]
    			            },{
    			                columnWidth: .33, 
    			                xtype: 'panel',
    			                layout: 'form',
    			                items: [{
    			                    xtype: 'dynamicCombo',
    			                    labelStyle: 'padding-left: 5px',
    			                    fieldLabel: '商户所属市*',
    			                    id:'idcity',
    			                    hiddenName: 'city',
    			                    allowBlank: false,
    			                    methodName: 'getCitys',
    			                    width:130,
    			                    callFunction : function() {
    			    					Ext.getCmp('idarea').reset();
    			    					Ext.getCmp('idarea').setValue('-');
    			    					Ext.getCmp('idarea').parentP=this.value;
    			    					Ext.getCmp('idarea').getStore().reload();
    			    					Ext.getCmp('idarea').reset();
    			    					Ext.getCmp('idareaNo').setValue(this.value);
    			    					Ext.getCmp('verifyButton').enable();
    			    				}
    			                    /*listeners: {
    			    					'select': function() {
    			                	            areasStore.removeAll();
    			                	            Ext.getCmp('mchtNoInPut').setValue('');
    											SelectOptionsDWR.getComboDataWithParameter('AREAS',mchntForm.getForm().findField('idcity').getValue(),
    													function(ret){
    												areasStore.loadData(Ext.decode(ret));
    													var rec = areasStore.getAt(0);
    												Ext.getCmp('idarea').setValue(rec.get('valueField'));
    											});
    			    					}
    			    				}*/
    	                }]
    	            },{
                    columnWidth: .33, 
                    xtype: 'panel',
                    layout: 'form',
                    items: [{
                        xtype: 'dynamicCombo',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户所属区/县',
                        id:'idarea',
                        hiddenName:'area',
                        width:130,
                        methodName: 'getAreas',
                        callFunction : function() {
        					Ext.getCmp('idareaNo').setValue(this.value);
        					Ext.getCmp('verifyButton').enable();
        				}
                       /* listeners: {
        					'select': function() {
        						SelectOptionsDWR.getComboDataWithParameter('',mchntForm.getForm().findField('idarea').getValue(),
        								function(ret){	});
        					}
        				}*/
                    }]
                },{
            		columnWidth: .33,
    	        	xtype: 'panel',
    	        	layout: 'form',
           			items: [{
    		        	xtype: 'textfield',
    					labelStyle: 'padding-left: 5px',
    					fieldLabel: '营业面积/平方米*',
    					name: 'acreage',
    					id: 'acreage',
    					vtype: 'isArea',
    					maxLength:10,
    					allowBlank: false,
    					width:130
    	        	}]
    			},{
            		columnWidth: .33,
    	        	xtype: 'panel',
    	        	layout: 'form',
           			items: [{
    					xtype: 'dynamicCombo',
    					labelStyle: 'padding-left: 5px',
    					fieldLabel: '经营场所权属*',
    					maxLength: '60',
    					allowBlank: false,
    					width:130,
    					id: 'idOwnerBusi',
    					methodName: 'getOwnerBusi',
    	                hiddenName: 'ownerBusi'
    	        	}]
    			},{
            		columnWidth: .33,
    	        	xtype: 'panel',
    	        	layout: 'form',
           			items: [{
    		        	xtype: 'textfield',
    					labelStyle: 'padding-left: 5px',
    					fieldLabel: '员工人数*',
    					name: 'empNum',
    					id: 'empNum',
    					regex: /^[0-9]*$/,
    					allowBlank: false,
    					maxLength:8,
    					width:130
    	        	}]
    			},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '公司网址',
    						regex:/^[a-zA-z]+:/,
    						regexText:'必须是正确的地址格式，如http://www.xxx.com',
                            maxLength: 60,
    						vtype: 'isOverMax',
    						id: 'homePage',
    						name: 'homePage',
    						width:128
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: 'ICP备案号',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'ICPrecordNo',
    						name: 'ICPrecordNo'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: 'ICP备案公司名称',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'ICPcompName',
    						name: 'ICPcompName'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户调单联系人名称*',
    						maxLength: '60',
    						allowBlank: false,
    						id: 'contName',
    						name: 'contName'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户调单联系人电话*',
    						maxLength: '60',
    						allowBlank: false,
    						id: 'contTel',
    						name: 'contTel'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户固定电话',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'busiTel',
    						name: 'busiTel'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '企业传真',		 
    						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
    						maxLength: 20,
    						vtype: 'isOverMax',
    						id: 'fax',
    						name: 'fax'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '法人电话*',
    						maxLength: 30,
    						vtype: 'isOverMax',
    						id: 'managerTel',
    						name: 'managerTel'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '联系人电话',		 
    						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
    						maxLength: 30,
    						vtype: 'isOverMax',
    						id: 'commTel',
    						width:128
    		        	}]
    				}]
            	},{
                    title:'申请信息',
                    layout:'column',
                    id: 'basic1',
                    frame: true,
                    items: [{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单笔借记卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'sinDebAmount',
    						name: 'sinDebAmount'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单日借记卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'dayDebAmount',
    						name: 'dayDebAmount'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单月借记卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'monDebAmount',
    						name: 'monDebAmount'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单笔预付费卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'sinCreAmount',
    						name: 'sinCreAmount'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单日预付费卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'dayCreAmount',
    						name: 'dayCreAmount'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '客户申请单月预付费卡交易金额',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'monCreAmount',
    						name: 'monCreAmount'
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
    					anchor: '90%'
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
    			name: 'returnFunc'
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            	xtype: 'textnotnull',
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '拓展人电话*',
    			vtype: 'isOverMax',
    			name: 'netTel',
    			id: 'netTel',
    			regex: /^[0-9]*$/,
    			maxLength:30,
    			width:150
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            	xtype: 'textfield',
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '拓展人*',
    			name: 'expander',
    			id: 'expander',
    		 	allowBlank:false,
    			maxLength:50,//20121102修改
    			width:130
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            	xtype: 'timefieldsp',
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '商户营业开始时间*',
    			id: 'openTime',
    			name: 'openTime',
    			minValue: '00:00:00',
    			maxValue: '23:59:59',
    			altFormats: 'H:i:s',
    			format: 'H:i:s',
    			allowBlank: false,
    			editable: true,
    			increment: 1,
    			anchor: '72%'
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            	xtype: 'timefieldsp',
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '商户营业结束时间*',
    			id: 'closeTime',
    			name: 'closeTime',
    			minValue: '00:00:00',
    			maxValue: '23:59:59',
    			altFormats: 'H:i:s',
    			allowBlank: false,
    			format: 'H:i:s',
    			editable: true,
    			increment: 1,
    			anchor: '72%'
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            		xtype: 'textnotnull',
    				fieldLabel: '所属业务人员*',
    				maxLength: 10,
    				vtype: 'isOverMax',
    				readOnly:true,
    				width:130,
    				id: 'operNm',
    				name: 'operNm'
            	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
            xtype: 'dynamicCombo',
            methodName: 'getAgentNo',
    		labelStyle: 'padding-left: 5px',
    		fieldLabel: '代理商*',
    		allowBlank: false,
    		blankText: '请选择代理商',
    		id: 'idagentNo',
    		hiddenName: 'agentNo',
    		width:130,
    		 callFunction : function() {
    			 T20100.getBusiPerson(this.value,function(ret){
    				Ext.getCmp('operNm').setValue(ret); 
    			 });
    		 }
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
    			name: 'hasInnerPosExp'
    			 
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
    			name: 'hasOurPosExp'
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
    			name: 'isAppOutSide'
        	}]
    	},{
    		columnWidth: .33,
        	xtype: 'panel',
        	layout: 'form',
    			items: [{
    			xtype: 'textfield',
    			labelStyle: 'padding-left: 5px',
    			fieldLabel: '申请人留言',
    			maxLength: '60',
    			allowBlank: true,
    			id: 'appComm',
    			name: 'appComm'
        	}]
    	},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户英文名称',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'engName',
    						regex: /^[A-Za-z]+$/,
    						name: 'engName'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户英文简称',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'mchtEnAbbr',
    						regex: /^[A-Za-z]+$/,
    						name: 'mchtEnAbbr'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '装机地址',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'insAddr',
    						name: 'insAddr'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '合同邮寄地址',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'mailAddr',
    						name: 'mailAddr'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户签约费率',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'contRate',
    						name: 'contRate'
    		        	}]
    				},/*{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '代理商名称',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'agentName',
    						name: 'agentName'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '代理商类型',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'agentType',
    						name: 'agentType'
    		        	}]
    				},*/{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '所属大区',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'regionBel',
    						name: 'regionBel'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    		        	defaults: {
    	        			xtype: 'textfield',
    	        			labelStyle: 'padding-left: 5px'
    	        		},
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '商户地址*',
    						maxLength: 100,
    						allowBlank: false,
    						width:150,
    						id: 'addr',
    						name: 'addr',
    						value:'-',
    						validator : function(value) {  
    					        var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
    					        if(length>100){  
    					            return '该输入项不能超过100个字符或50个汉字';  
    					        }  
    					        return true;  
    					    }
    		        	},{
    		        		xtype: 'textnotnull',
    						fieldLabel: '商务经理工号*',
    						width:150,
    						id: 'operNo',
    						name: 'operNo',
    						value:'0',
    						vtype: 'isNumber',
    						maxLength: 8
    		        	}]
    				},{
                    	columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '经营店名*',
    						maxLength: 100,
    						vtype: 'isOverMax',
    						allowBlank: false,
    						value:'-',
    						width:130,
    						id: 'busScope',
    						name: 'busScope'
    						}]
                    },{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    		        	defaults: {
    	        			xtype: 'textfield',
    	        			labelStyle: 'padding-left: 5px'
    	        		},
    	       			items: [{
    	       				xtype: 'textnotnull',
    						fieldLabel: '终端数量*',
    						maxLength: 30,
    						//regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
    						vtype: 'isOverMax',
    						width:150,
    						maxLength:8,
    						value:0,
    						id: 'cashierDeskNum',
    						name: 'cashierDeskNum'
    		        	}]
    				},{
    	        		columnWidth: .33,
    	       			xtype: 'panel',
    	       			hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '直营连锁店数量*',
    						allowBlank: false,
    						maxLength:4,
    						regex: /^[0-9]*$/,
    						value:0,
    						id: 'shopNum',
    						name: 'shopNum'
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textnotnull',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '月消费总额*',
    						name: 'monthTotalAmt',
    						id: 'monthTotalAmt',
//    						maskRe: /^[0-9\\.]+$/,
    						vtype: 'isNumber',
    						maxLength:12,
    						value:0,
    						width:130
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '单笔消费金额*',
    						name: 'singleAmt',
    						id: 'singleAmt',
//    						maskRe: /^[0-9\\.]+$/,
    						vtype: 'isNumber',
//    						vtype: 'isMoney',
    						maxLength:12,
    						value:0,
    						allowBlank: false,
    						width:130
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'basecomboselect',
    			        	baseParams: 'SER_LVL',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '服务级别*',
    						hiddenName: 'serLvl',
    						value:'0',
    						allowBlank: false,
    						width:130
    		        	}]
    				},{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'datefield',
    						name: 'openDate',
    						labelStyle: 'padding-left: 5px',
    						id: 'openDate',
    						fieldLabel: '开业日期*',
    						value:'20000101',
    						allowBlank: false,
    						width:128
    					}]
    				},/*{
    	        		columnWidth: .33,
    	       			xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '预授权功能',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'preAuthor',
    						name: 'preAuthor'
    		        	}]
    				},{
    	        		columnWidth: .33,
    	       			xtype: 'panel',
    		        	layout: 'form',
    	       			items: [{
    						xtype: 'textfield',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '退货功能',
    						maxLength: '60',
    						allowBlank: true,
    						id: 'returnFunc',
    						name: 'returnFunc'
    		        	}]
    				},*/{
    	        		columnWidth: .33,
    		        	xtype: 'panel',
    		        	hidden:true,
    		        	layout: 'form',
    	       			items: [{
    			        	xtype: 'basecomboselect',
    			        	baseParams: 'WINESHOP_LVL',
    						labelStyle: 'padding-left: 5px',
    						fieldLabel: '酒店星级',
    						hiddenName: 'wineshopLvl',
    						width:130
    		        	}]
    				},{
    		        		columnWidth: .33,
    		       			xtype: 'panel',
    		       			hidden:true,
    			        	layout: 'form',
    		       			items: [{
    				        	xtype: 'checkbox',
    							labelStyle: 'padding-left: 5px',
    							fieldLabel: '是否酒店行业',
    							id: 'isWineshop',
    							name: 'isWineshop',
    							anchor: '90%'
    			        	}]
    				}]
    			},{
	                title:'清算信息',
	                layout:'column',
	                id: 'settle',
	                frame: true,
	                items: [{
			        		columnWidth: .33,
			        		//columnWidth: .33,
			            	layout: 'form',
			            	xtype: 'panel',
			        		items: [{
			        			xtype: 'basecomboselect',
						        baseParams: 'SETTLE_ACCT_TYPE',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '结算账户类型*',
								id: 'idsettleRpt',
								hiddenName: 'settleRpt',
								allowBlank: false,
								width:150,
								//anchor: '30%',
						        listeners: {
						                    'select': function() {
							                    var value = mchntForm.getForm().findField('idsettleRpt').getValue();							                   
							                    var etpsAttrVal = mchntForm.getForm().findField('idEtpsAttr').getValue();
							                    if(value=='2'){//对公
							                    	//公司账户必填
													 Ext.getCmp('compAccountBankName').allowBlank=false;
													  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称*');
													  	Ext.getCmp('settleAcct').allowBlank=false;
													  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号*'); 
														 //定向委托账户非必填
													    Ext.getCmp('dirBankName').allowBlank=true;
													  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
													  	Ext.getCmp('dirAccountName').allowBlank=true;
													  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
													  	Ext.getCmp('dirAccount').allowBlank=true;
													  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
													  //法人账户非必填
													    Ext.getCmp('corpBankName').allowBlank=true;
													  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称');
													  	Ext.getCmp('feeAcct').allowBlank=true;
													  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号'); 	
							                    }else if(value=='1'){//对私
							                    	    if(etpsAttrVal!='02'){
									                    	//公司账户非必填
															 Ext.getCmp('compAccountBankName').allowBlank=true;
															  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称');
															  	Ext.getCmp('settleAcct').allowBlank=true;
															  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号'); 												 
									                    
							                    	    }
								                    	//法人账户必填
														    Ext.getCmp('corpBankName').allowBlank=false;
														  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称*');
														  	Ext.getCmp('feeAcct').allowBlank=false;
														  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号*'); 
														  	
														  //定向委托账户非必填
														    Ext.getCmp('dirBankName').allowBlank=true;
														  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称');
														  	Ext.getCmp('dirAccountName').allowBlank=true;
														  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名'); 
														  	Ext.getCmp('dirAccount').allowBlank=true;
														  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号');
														  
								                    }else if(value=='3'){//定向
								                    	if(etpsAttrVal!='02'){
									                    	//公司账户非必填
															 Ext.getCmp('compAccountBankName').allowBlank=true;
															  	mchntForm.getForm().findField('compAccountBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账户开户行名称');
															  	Ext.getCmp('settleAcct').allowBlank=true;
															  	mchntForm.getForm().findField('settleAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户公司账号'); 												 
									                    
							                    	    }
								                    	//法人账户非必填
													    Ext.getCmp('corpBankName').allowBlank=true;
													  	mchntForm.getForm().findField('corpBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('法人账号开户行名称');
													  	Ext.getCmp('feeAcct').allowBlank=true;
													  	mchntForm.getForm().findField('feeAcct').getEl().up('.x-form-item').down('.x-form-item-label').update('商户法人账号'); 
													  	
													  //定向委托账户必填
													    Ext.getCmp('dirBankName').allowBlank=false;
													  	mchntForm.getForm().findField('dirBankName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户行名称*');
													  	Ext.getCmp('dirAccountName').allowBlank=false;
													  	mchntForm.getForm().findField('dirAccountName').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账户开户名*'); 
													  	Ext.getCmp('dirAccount').allowBlank=false;
													  	mchntForm.getForm().findField('dirAccount').getEl().up('.x-form-item').down('.x-form-item-label').update('定向委托账号*'); 
													  
							                    }							                    
						                    }
					                    }
									
								}]
				        },{
			        		columnWidth: .33,
				        	xtype: 'panel',
				        	hidden:true,
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户当前使用账号',
								maxLength: '60',
								allowBlank: true,
								id: 'currAccount',
								name: 'currAccount'
				        	}]
						},{
			        		columnWidth: .33,
				        	xtype: 'panel',
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户公司账户开户行名称*',
								maxLength: '60',
								allowBlank: false,
								id: 'compAccountBankName',
								name: 'compAccountBankName'
				        	}]
						},{
			        		columnWidth: .33,
				        	xtype: 'panel',
				        	layout: 'form',
			       			items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户公司账户开户行机构代码',
								maxLength: '60',
								allowBlank: true,
								id: 'compAccountBankCode',
								name: 'compAccountBankCode'
				        	}]
						},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '商户公司账号开户名',
						maxLength: 60,
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'settleAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '商户公司账号*',
						maxLength: 40,	
						vtype: 'isNumber',
						//width:150,
						id: 'settleAcct'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人账号开户行名称*',
						maxLength: '60',
						allowBlank: false,
						id: 'corpBankName',
						name: 'corpBankName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户法人账号开户行机构代码',
						maxLength: '60',
						allowBlank: true,
						id: 'bankAccountCode',
						name: 'bankAccountCode'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						//fieldLabel: '结算账户1账户户名*',
						fieldLabel: '商户法人账号开户名',
						maxLength: 60,	
						value:0,
						vtype: 'isOverMax',
						//width:150,
						id: 'feeAcctNm'
		        	}]
				},{
	        		//columnWidth: .5,
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						//fieldLabel: '结算账户1账户户号*',
						fieldLabel: '商户法人账号*',
						maxLength: 40,	
						vtype: 'isNumber',
						//width:150,
						id: 'feeAcct'
		        	}]
				},{
		        		columnWidth: 1,
		       			xtype: 'panel',
		       			hidden:true,
			        	layout: 'form',
		       			items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '定向委托付款标志',
							id: 'dirFlag',
							name: 'dirFlag',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户开户行名称',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankName',
						name: 'dirBankName'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户账户开户行机构代码',
						maxLength: '60',
						allowBlank: true,
						id: 'dirBankCode',
						name: 'dirBankCode'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账户开户名',
						maxLength: '60',
						allowBlank: true,
						id: 'dirAccountName',
						name: 'dirAccountName'
		        	}]
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '定向委托账号',
						maxLength: '60',
						allowBlank: true,
						id: 'dirAccount',
						name: 'dirAccount'
		        	}]
				},{
					columnWidth: .33,
					xtype: 'panel',
				layout: 'form',
					items: [{
			    	xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '自动提现标志',
					id: 'autoFlag',
					name: 'autoFlag',
					checked:'true',
					anchor: '90%'
				}]
			},{
	    		columnWidth: .33,
	   			xtype: 'panel',
	        	layout: 'form',
	   			items: [{
		        	xtype: 'checkbox',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '节假日提现标志',
					id: 'holidaySetFlag',
					name: 'holidaySetFlag',
					anchor: '90%'
	        	}]
		},{
    		columnWidth: .33,
   			xtype: 'panel',
        	layout: 'form',
   			items: [{
	        	xtype: 'checkbox',
				labelStyle: 'padding-left: 5px',
				fieldLabel: '信用卡刷卡功能标志',
				id: 'creFlag',
				name: 'creFlag',
				checked:'true',
				anchor: '90%'
        	}]
	},{
	columnWidth: .33,
		xtype: 'panel',
	layout: 'form',
		items: [{
    	xtype: 'checkbox',
		labelStyle: 'padding-left: 5px',
		fieldLabel: '退货返还手续费标志',
		id: 'returnFeeFlag',
		name: 'returnFeeFlag',
		anchor: '90%'
	}]
},{
	        		//columnWidth: .5,
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						fieldLabel: '商户结算方式T+N*',
						maxLength: 2,
						maskRe: /^[0-9]{1,2}/,
						vtype: 'isOverMax',
						//width:150,
						id: 'settleChn'
		        	}]
				},{
	        		//columnWidth: .5,
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'basecomboselect',
						fieldLabel: '商户结算周期*',
						baseParams: 'SETTLE_TYPE',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'settleType',
						anchor: '60%',
						//value: '1',
						value:'0',
                        listeners: {
		                    'select': function() {

		                    }
	                    }
					}]
				}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	id:'feeForm',
                	//xtype:'form',
                	region: 'north',
                	height: 75,
                	layout: 'column',
                	items: [{
	        	columnWidth: .33,
	        	labelWidth: 70,
	        	layout: 'form',
	        	xtype: 'panel',
	        	hidden:true,
	       		items: [{
		       			xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '发卡机构',
						id: 'idFK_BRCH_NO',
						name: 'FK_BRCH_NO',
						maxLength: 8,
						allowBlank: true,
						value:'*',
						width:150
		        	}]
                	},{
		        		columnWidth: .2,
		        		labelWidth: 70,
		            	layout: 'form',
		            	hidden:true,
		        		items: [{
					        xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '终端代码',
							id: 'idTERM_ID',
							name: 'TERM_ID',
							maxLength: 8,
							allowBlank: true,
							value:'*',
							width:100
				        }]
		        	}
//		        	,{
//                		columnWidth: .2,
//		        		xtype: 'panel',
//		        		layout: 'form',
//                		labelWidth: 70,
//	       				items: [{
//	       					xtype: 'textnotnull',
//	       					labelStyle: 'padding-left: 5px',
//	       					fieldLabel: '费率点差',
//							id: 'feeDiffer',
//							name: 'feeDiffer',
//							vtype:'is2Number', 
//							width:80,
//							maxLength: 12
//						}]
//					}
					,{
                		columnWidth: .2,
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'textnotnull',
	       					labelStyle: 'padding-left: 5px',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
//							disabled:true,
							readOnly: true,
							width:80
						}]
					},{			
						columnWidth: .15,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'选择计费代码',
							id: 'feeBt',
							width: 60,
							handler: function(){
								groupWin.show();
							}
                		}]
					},{			
						columnWidth: .1,
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'添加',
							id: 'addbu',
							handler: function(){
								saveDiscAlgo2();
							}
                		}]
					},{	 
		        		xtype: 'panel',
		        		hidden:true,
		        		layout: 'form',
	       				items: [{
                			xtype: 'textfield',
							iconCls: 'recover',
							text:'临时编号',
							id: 'tmpNo',
							name:'tmpNo',
							width: 60
                		}]
					},{
		        		columnWidth: .33,
		        		xtype: 'panel',
		        		labelWidth: 70,
				        layout: 'form',
				        hidden:true,
			       		items: [{
			       			xtype: 'dynamicCombo',
			       			methodName: 'getAreaCodecode',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '地区码',
							editable: true,
							id: 'idCITY_CODE',
							hiddenName: 'CITY_CODE',
							allowBlank: true,
							value:'*',
							width:150
			       		}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
			        layout: 'form',
			        hidden:true,
		       		items: [{
				        xtype: 'basecomboselect',
				        store:AgenStore,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '目的机构',
						allowBlank: true,
						blankText: '请选择目的机构',
						id: 'idTO_BRCH_NO',
						hiddenName: 'TO_BRCH_NO',
						value:'*',
						//anchor: '90%',
						width:150
			        }]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
			        layout: 'form',
			        hidden:true,
		       		items: [{
			        	xtype: 'basecomboselect',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '卡类型',
						baseParams: 'CARD_STYLE',
						allowBlank: true,
						//anchor: '90%',
						blankText: '请选择交易渠道',
						id: 'idCARD_TYPE',
						hiddenName: 'CARD_TYPE',
						value:'*',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
	        		labelWidth: 70,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getChannel',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易渠道',
						allowBlank: true,
						blankText: '请输入交易渠道',
						id: 'idCHANNEL_NO',
						hiddenName: 'CHANNEL_NO',
						value:'*',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'dynamicCombo',
						methodName: 'getBusinType_as',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '业务类型',
						vtype: 'isOverMax',
						allowBlank: true,
						blankText: '请输入业务类型',
						id: 'idBUSINESS_TYPE',
						hiddenName: 'BUSINESS_TYPE',
						value:'*',
						width:150
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	labelWidth: 70,
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
						xtype: 'dynamicCombo',
		                methodName: 'getTxnClt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '交易类型',
						allowBlank: true,
						blankText: '请输入交易类型',
						id: 'idTXN_NUM',
						hiddenName: 'TXN_NUM',
						value:'*',
						width:150
		        	}]
				}]
                },{
                	region: 'center',
                	items:[feeGridPanel]
                }]
			}
			/*,
			{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'textnotnull',
	       					fieldLabel: '计费代码*',
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'重置',
							id: 'resetbu',
							width: 60,
							handler: function(){
								Ext.getCmp('discCode').reset();
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'accept',
							text:'设为该商户计费算法',
							id: 'setup',
							width: 60,
							disabled: true,
							handler: function(){
								Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd);
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
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
		    }*/,{
				title:'证书影像',
                id: 'images',
                frame: true,
                hidden:true,
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
									imagesId: imagesId
								}
							});
							storeImg1.reload({
								params: {
									start: 0,
									imagesId: imagesId
								}
							});
						}
					},{
						xtype: 'button',
						width: '80',
						text: '上传',
						id: 'upload',
						handler:function() {
							hasUpload = "1";
							dialog.show();
						}
					}]
                }]
		    }]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
				subSave();
            }
        },{
            text: '返回',
            handler: function() {
            	var tmpNo=Ext.getCmp('tmpNo').getValue();
            	 
            	T20100.rebackDiscAlgo2(tmpNo,mchntId,function(ret){  	});
            	window.location.href = Ext.contextPath + '/page/mchnt/T20103.jsp';
			}
        }]
    });
	
	//外部加入监听
  /*  Ext.getCmp("settleBankNmI").on('select',function(){
    	T20401.getInfo(Ext.getCmp("settleBankNmI").getValue(),function(ret){
    		if(ret=='0'){
    				showErrorMsg("找不到相应信息",grid);
    				mchntForm.getForm().findField('settleBankNmI').reset();
    				return;
    		}
    		
            var mbfBankInfo = Ext.decode(ret.substring(1,ret.length-1));
            Ext.getCmp("settleBankNmI").setValue(mbfBankInfo.bankname);
  			Ext.getCmp("settleBankNo").setValue(mbfBankInfo.id);
        });
    });*/
    
    function saveDiscAlgo2(){
    	var btn = Ext.getCmp('addbu');
    	var tmpNo=Ext.getCmp('tmpNo').getValue();
    	var TERMID=Ext.getCmp('idTERM_ID').getValue();
    	var CITY_CODE=Ext.getCmp('idCITY_CODE').getValue();
    	var TO_BRCH_NO=Ext.getCmp('idTO_BRCH_NO').getValue();
    	var FK_BRCH_NO=Ext.getCmp('idFK_BRCH_NO').getValue();
    	var CARD_TYPE=Ext.getCmp('idCARD_TYPE').getValue();
    	var CHANNEL_NO=Ext.getCmp('idCHANNEL_NO').getValue();
    	var BUSINESS_TYPE=Ext.getCmp('idBUSINESS_TYPE').getValue();
    	var TXN_NUM=Ext.getCmp('idTXN_NUM').getValue();
    	var discCode=Ext.getCmp('discCode').getValue();
    	/*if(mchtNo==''||mchtNo.length!=15){
    		showErrorMsg('请输入15位商户编号',mchntForm);
    		return;
    	}*/
    	if(FK_BRCH_NO==''){
    		showErrorMsg('请选择发卡机构',mchntForm);
    		return;	
    	}
    	if(TERMID==''){
    		showErrorMsg('请输入终端代码',mchntForm);
    		return;
    	}
    	if(discCode==''){
    		showErrorMsg('请选择计费代码',mchntForm);
    		return;
    	}
    	if(CITY_CODE==''){
    		showErrorMsg('请选择地区码',mchntForm);
    		return;	
    	}
    	if(TO_BRCH_NO==''){
    		showErrorMsg('请选择目的机构',mchntForm);
    		return;	
    	}
    	if(CARD_TYPE==''){
    		showErrorMsg('请选择卡类型',mchntForm);
    		return;	
    	}
    	if(CHANNEL_NO==''){
    		showErrorMsg('请选择交易渠道',mchntForm);
    		return;	
    	}
    	if(BUSINESS_TYPE==''){
    		showErrorMsg('请选择业务类型',mchntForm);
    		return;	
    	}
    	if(TXN_NUM==''){
    		showErrorMsg('请选择交易类型',mchntForm);
    		return;	
    	}
    	T20100.addDiscAlgo2(tmpNo,discCode,TERMID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM,function(ret){
    		var sta = '';
			var msg = '';
			var tmpNo = '';
    		for(var key in ret){
    			if(key=='result'){
    				sta = ret[key];
    			}else if(key=='msg'){
    				msg=ret[key];	
    			}else if(key=='tmpNo'){
    				tmpNo=ret[key];
    			}
    		}
    		if(sta=='N'){
    			Ext.getCmp('tmpNo').setValue(tmpNo);
    			feeStore.load({
				params: {
					start: 0,
					tmpNo: tmpNo
					}
				});
    		}else{
    			showErrorMsg(msg,mchntForm);	
    		}
    	});
    }
	var checkIds = "T";
	
    function subSave(){
    	var btn = Ext.getCmp('save')
		var frm = mchntForm.getForm();
		hasSub = true;
		if(feeStore.getCount()<1){
				showErrorMsg('请添加商户计费信息!',mchntForm);
				return;
			}
		if (frm.isValid()) {
//			var flag = mchntForm.getForm().findField('clearType').getValue();
			var settleChn=mchntForm.getForm().findField('settleChn').getValue();
			var settleType2=mchntForm.getForm().findField('settleType').getValue();
			
			//201200904添加的验证条件
			if(settleChn>31){
				showErrorMsg('商户结算方式应小于T+31!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
				return;
			}else if((settleType2=='0' || settleType2=='1')&& settleChn > 15){
				showErrorMsg('日结商户结算方式应小于T+15!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
				return;
			}
		/*	if(!verifyResult&&(flag == 'A'||flag == 'P')){
				//额外验证
	            if(flag == 'A'||flag == 'P'){
	                Ext.getCmp('verifyButton').enable();
					showErrorMsg('保存商户信息之前请验证账户账号!',mchntForm);
	            }
			}else{*/
//				btn.disable();20120815注销，关闭验证窗口保存按钮才能显示出来供使用
				
		 var modifiedRecords = feeGridPanel.getStore().getModifiedRecords();
				/*if (modifiedRecords.length == 0) {
					//return;
				}*/
				/*var store = feeGridPanel.getStore();
				var len = store.getCount();
				for ( var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}*/
				// 存放要修改的参数信息
			var array = new Array();
			for ( var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					discId:record.get('discId'),
					feeType : record.get('feeType')
				};
				array.push(data);
			}
			if(Ext.getCmp('contact').getValue()==''){
	    		Ext.getCmp('contact').setValue(' ');
	    	}
			frm.submitNeedAuthorise({
//			frm.submit({
				url: 'T20101Action_update4Add.asp',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					hasSub = false;
					showSuccessAlert(action.result.msg,mchntForm,function(){
						window.location.href = Ext.contextPath + '/page/mchnt/T20103.jsp';
					});
				},
				failure : function(form,action) {
					btn.enable();
					hasSub = false;
					if (action.result.msg.substr(0,2) == 'CZ') {
						Ext.MessageBox.show({
							msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
							title: '确认提示',
							animEl: Ext.get(mchntForm.getEl()),
							buttons: Ext.MessageBox.YESNO,
							icon: Ext.MessageBox.QUESTION,
							modal: true,
							width: 500,
							fn: function(bt) {
								if(bt == 'yes') {
									checkIds = "F";
									subSave();
								}
							}
						});
					} else {
						showErrorMsg(action.result.msg,mchntForm);
					}
				},
				params: {
					txnId: '20101',
					subTxnId: '01',
					mchtNo: mchntId,
					checkIds: checkIds,
					algo2List : Ext.encode(array)/*,
					mchtLvl:Ext.getCmp('idmchtLvl').getValue()*/
				}
			});
//		}
	}else{
		//自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic'||tab == 'basic1' || tab == 'append'||tab == 'append1' || tab == 'settle' || tab == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	});
	}
    }
    
    
    function subLevel(){
		var frm = mchntForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
			if(feeStore.getCount()<1){
				showErrorMsg('请添加商户计费信息!',mchntForm);
				return;
			}
			var flag = mchntForm.getForm().findField('clearType').getValue();
			var settleType=mchntForm.getForm().findField('settleType').getValue();
			var settleChn=mchntForm.getForm().findField('settleChn').getValue();
			var licenceNo = Ext.getCmp('licenceNo').getValue();
			
			if(licenceNo.trim() == ''){
	    		showErrorMsg('营业执照号不能为空或空字符串',mchntForm);
	    		return;
	    	}else if(licenceNo.indexOf('O') != '-1'){
	   			showErrorMsg('营业执照号不能包含大写字母O',mchntForm);
				return;
	   		}
	    	if(mchntForm.findById('settleChn').getValue().substr(0,1) == "-"){
				showErrorMsg('商户结算方式T+N不能为负数，请重新填写',mchntForm);
				mchntForm.findById('settleChn').setValue('');
				return ;
			}
	    	if(mchntForm.findById('feeCycle').getValue()!='' && mchntForm.findById('feeCycle').getValue().substr(0,1) == "-"){
				showErrorMsg('手续费结算方式T+N不能为负数，请重新填写',mchntForm);
				mchntForm.findById('feeCycle').setValue('');
				return ;
			}
			if(settleChn>31){
//				showErrorMsg('商户结算方式应小于T+31!',mchntForm);
//				Ext.getCmp("tab").setActiveTab('settle');
			}else if((settleType=='0' || settleType=='1')&& settleChn > 15){//20120822添加的验证条件
//				showErrorMsg('日结商户结算方式应小于T+15!',mchntForm);
//				Ext.getCmp("tab").setActiveTab('settle');
			}else if(!verifyResult){
				//额外验证
                Ext.getCmp('verifyButton').enable();
				showErrorMsg('保存商户信息之前请验证商户号!',mchntForm);
				Ext.getCmp("tab").setActiveTab('settle');
			}else{
				
//				btn.disable();
				frm.submit({
					url: 'T20100Action_subEvaLevel.asp',
					waitTitle : '请稍候',
					waitMsg : '正在提交表单数据,请稍候...',
					success : function(form, action) {
					},
					failure : function(form,action) {
						if (action.result.msg.substr(0,2) == 'No') {
							showErrorMsg("评估等级低于最低等级",mchntForm);
							return;
						}
						if (action.result.msg.substr(0,2) == 'Fa') {
							showErrorMsg("查询数据失败",mchntForm);
							return;
						}
						if(action.result.msg.length>5){
							showErrorMsg("查询数据失败",mchntForm);
							return;
						}
						mchntForm.findById('idevalLevel').setValue(action.result.msg);
					},
					params: {
						txnId: '20101',
						subTxnId: '07'
//						hasUpload: hasUpload,
//						imagesId: imagesId
					//	licenceEndDate:Ext.getCmp('licenceEndDate').getValue().format('Ymd'),
//						checkIds: checkIds,
						//正常流程
//						txType : '00'
					}
			});
		}
	}else{
		//自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic'||tab == 'basic1' || tab == 'append' || tab == 'append1' || tab == 'settle' || tab == 'feeamt'||tab=='standardConfig'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	}
    );
	}}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    })

   
    
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue()
		});
	});

	//移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 1
		});
	};
	hideMainUIMask.defer(1000);

});