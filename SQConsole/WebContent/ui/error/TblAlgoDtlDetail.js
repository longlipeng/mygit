
//详细信息，在外部用函数封装
function showTblAlgoDtlDetail(dateSettlmt,txnKey,txnSsn,transDateTime){
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getTblAlgoDtlDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
			//idProperty: 'id'
		},[
			{name: 'dateSettlmt',mapping: 'id.dateSettlmt'},
            {name: 'termId',mapping: 'termId'},
            {name: 'mchtCd',mapping: 'mchtCd'},
            {name: 'txnNum',mapping: 'txnNum'},
            {name: 'stlmFlg',mapping: 'stlmFlg'},
            {name: 'transDateTime',mapping: 'transDateTime'},
            {name: 'orgTxnNum',mapping: 'orgTxnNum'},
            {name: 'pan',mapping: 'pan'},
            {name: 'txnSsn',mapping: 'txnSsn'},
            {name: 'termSsn',mapping: 'termSsn'},
            {name: 'feeDOut',mapping: 'feeDOut'},
            {name: 'transDate',mapping: 'transDate'},
            {name: 'transAmt',mapping: 'transAmt'},
            {name: 'settlAmt',mapping: 'settlAmt'},
            {name: 'settlDate',mapping: 'settlDate'},
            {name: 'tOFlag',mapping: 'tOFlag'},
            {name: 'feeCOut',mapping: 'feeCOut'},
            {name: 'buyDate',mapping: 'buyDate'},
            {name: 'stlmInsIdCd',mapping: 'stlmInsIdCd'}
		]),
		autoLoad: false
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
        	items: [
        	  {
	        	columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '清算日期',
					id: 'dateSettlmt',
					hiddenName: 'dateSettlmt',
//					id: 'settlDate',
//					hiddenName: 'settlDate',//20121206被要求修改成和结算日期取同一字段的值
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
			},{
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
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
						xtype: 'displayfield',
						fieldLabel: '差错交易类型',
						id: 'stlmFlg',
						hiddenName: 'stlmFlg',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
		        layout: 'form',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易时间',
						id: 'transDateTime',
						hiddenName: 'transDateTime',
						anchor: '90%'
		        	}]
			}, {
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
			},{
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
						id: 'settlAmt',
						hiddenName: 'settlAmt',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '结算日期',
						id: 'settlDate',
						hiddenName: 'settlDate',
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
						id: 'feeCOut',
						hiddenName: 'feeCOut',
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
						id: 'feeDOut',
						hiddenName: 'feeDOut',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原始交易代码',
						id: 'orgTxnNum',
						hiddenName: 'orgTxnNum',
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
						fieldLabel: '挂账日期',
						id: 'buyDate',
						hiddenName: 'buyDate',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '受理机构',
						id: 'stlmInsIdCd',
						hiddenName: 'stlmInsIdCd',
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
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    })
    
	baseStore.load({
		params: {
			dateSettlmt: dateSettlmt,
			txnSsn: txnSsn,
			transDateTime: transDateTime,
			txnKey: txnKey
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				
				detailWin.show();
				
				/*SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					Ext.getCmp('idmcc').setValue(baseStore.getAt(0).data.mcc);
				});*/
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
			}else{
				showErrorMsg("加载差错信息失败，请刷新数据后重试",mchntForm);
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
