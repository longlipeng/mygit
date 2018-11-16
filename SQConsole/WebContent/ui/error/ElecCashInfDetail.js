
//详细信息，在外部用函数封装——补电子现金消费
function showElecCashDetail(key){
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getElecCashDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
			//idProperty: 'id'
		},[
			{name: 'dateSettlmt',mapping: 'dateSettlmt'},	//清算日期
            {name: 'termId',mapping: 'termId'},	//终端号
            {name: 'mchtCd',mapping: 'mchtCd'},	//商户号
            {name: 'txnNum',mapping: 'txnNum'},	//差错交易代码
            {name: 'txnSsn',mapping: 'txnSsn'},	//收单流水号
            {name: 'errType',mapping: 'errType'},	//差错交易类型
            {name: 'transDate',mapping: 'transDate'},	//交易日期
            {name: 'transTime',mapping: 'transTime'},	//交易时间
            {name: 'transAmt',mapping: 'transAmt'},		//交易金额
            {name: 'pan',mapping: 'pan'},	//主账号
            {name: 'settlmtAmt',mapping: 'settlmtAmt'},		//清算金额
            {name: 'tOFlag',mapping: 'tOFlag'},		//T+0入账标识
            {name: 'feeCredit',mapping: 'feeCredit'},	//应收手续费
            {name: 'feeDebit',mapping: 'feeDebit'},	//应付手续费
            {name: 'txnBatchNo',mapping: 'txnBatchNo'},	//终端批次号
            {name: 'txnCerNo',mapping: 'txnCerNo'},	//终端凭证号
            {name: 'icCert',mapping: 'icCert'},	//IC卡交易证书
            {name: 'tvr',mapping: 'tvr'},	//tvr
            {name: 'tsi',mapping: 'tsi'},	//tsi
            {name: 'aid',mapping: 'aid'},	//aid
            {name: 'atc',mapping: 'atc'},	//atc
            {name: 'appTag',mapping: 'appTag'},	//应用标签
            {name: 'appPreName',mapping: 'appPreName'}	//应用首选名称
		]),
		autoLoad: false
	});
	
	var elecForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
        	layout:'column',
        	items: [
        	  {
	        	columnWidth: .33,
	        	xtype: 'panel',
				labelStyle: 'padding-left: 5px',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					fieldLabel: '清算日期',
					id: 'dateSettlmt',
					hiddenName: 'dateSettlmt',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					fieldLabel: '终端号',
					id: 'termId',
					hiddenName: 'termId',
					anchor: '90%'
				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
        		labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
			        xtype: 'displayfield',
					fieldLabel: '商户编号',
					id: 'mchtCd',
					hiddenName: 'mchtCd',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
			        xtype: 'displayfield',
					fieldLabel: '差错交易代码',
					id: 'txnNum',
					hiddenName: 'txnNum',
					anchor: '90%'
		        }]
			},/*{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
						xtype: 'displayfield',
						fieldLabel: '收单流水号',
						id: 'txnSsn',
						hiddenName: 'txnSsn'
		        	}]
			},*/{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '差错交易类型',
						id: 'errType',
						hiddenName: 'errType',
						anchor: '90%'
	       		}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易日期',
						id: 'transDate',
						hiddenName: 'transDate',
						anchor: '90%'
		        	}]
			},/*{
				columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易时间',
						id: 'transTime',
						hiddenName: 'transTime',
						anchor: '90%'
		        	}]
			}, */{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易金额（元）',
						id: 'transAmt',
						hiddenName: 'transAmt',
						anchor: '90%',
						renderer: formatAmtTrans
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '主帐号',
						id: 'pan',
						hiddenName: 'pan',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '清算金额（元）',
						id: 'settlmtAmt',
						hiddenName: 'settlmtAmt',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'T+0入账标识',
						id: 'tOFlag',
						hiddenName: 'tOFlag',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '应收手续费（元）',
						id: 'feeCredit',
						hiddenName: 'feeCredit',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '应付手续费（元）',
						id: 'feeDebit',
						hiddenName: 'feeDebit',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '终端批次号',
						id: 'txnBatchNo',
						hiddenName: 'txnBatchNo',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '终端凭证号',
						id: 'txnCerNo',
						hiddenName: 'txnCerNo',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'IC卡交易证书',
						id: 'icCert',
						hiddenName: 'icCert',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'TVR',
						id: 'tvr',
						hiddenName: 'tvr',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'TSI',
						id: 'tsi',
						hiddenName: 'tsi',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'AID',
						id: 'aid',
						hiddenName: 'aid',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'ATC',
						id: 'atc',
						hiddenName: 'atc',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '应用标签',
						id: 'appTag',
						hiddenName: 'appTag',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '应用首选名称',
						id: 'appPreName',
						hiddenName: 'appPreName',
						anchor: '90%'
	       		}]
			},{
				columnWidth: 1,
				xtype: 'panel',
				html:'<br/>'
			}
		]	
        }]
    });
	
    var detailWin = new Ext.Window({
    	title: '差错详细信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		autoHeight: true,
		items: [elecForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    })
    
    elecForm.getForm().clearInvalid();
				
	detailWin.show();
				
	baseStore.load({
		params: {
			id : key
		},
		callback: function(records, options, success){
			if(success){
				elecForm.getForm().loadRecord(baseStore.getAt(0));
				elecForm.getForm().clearInvalid();
				
				detailWin.show();
				
				/*SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
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
				customEl.hide(true);*/
			}else{
				showErrorMsg("加载差错信息失败，请刷新数据后重试",elecForm);
			}
		}
	});
}
   
	Ext.override(Ext.form.DisplayField, {
	  getValue: function () {
	    return this.value;
	  },
	  setValue: function (v) {
	    this.value = v;
	    this.setRawValue(this.formatValue(v));
	    return this;
	  },
	  formatValue: function (v) {
	    if (this.dateFormat && Ext.isDate(v)) {
	      return v.dateFormat(this.dateFormat);
	    }
	    if (this.numberFormat && typeof v == 'number') {
	      return Ext.util.Format.number(v, this.numberFormat);
	    }
	    return v;
	  }
	});
