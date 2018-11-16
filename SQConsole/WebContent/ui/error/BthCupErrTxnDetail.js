
//详细信息，在外部用函数封装
function showErrTxnDetail(dateSettlmt,txnNum,txnSsn){
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getBthCupErrTxnDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
			//idProperty: 'id'
		},[
			{name: 'dateSettlmt',mapping: 'id.dateSettlmt'},
            {name: 'termId',mapping: 'termId'},
            {name: 'mchtId',mapping: 'mchtId'},
            {name: 'txnNum',mapping: 'id.txnNum'},
            {name: 'stlmFlg',mapping: 'stlmFlg'},
            {name: 'transDateTime',mapping: 'transDateTime'},
            {name: 'amtTrans',mapping: 'amtTrans'},
            {name: 'pan',mapping: 'pan'},
            {name: 'txnSsn',mapping: 'id.txnSsn'},
            {name: 'errTrigFlag',mapping: 'errTrigFlag'},
            {name: 'errFlag',mapping: 'errFlag'},
            {name: 'authorRspCd',mapping: 'authorRspCd'},
            {name: 'acqInsIdCd',mapping: 'acqInsIdCd'},
            {name: 'rcvInsIdCd',mapping: 'rcvInsIdCd'},
            {name: 'issInsIdCd',mapping: 'issInsIdCd'},
            {name: 'posEntryMode',mapping: 'posEntryMode'},
            {name: 'ipsNo',mapping: 'ipsNo'},
            {name: 'mchtTp',mapping: 'mchtTp'},
            {name: 'respCode',mapping: 'respCode'},
            {name: 'feeCredit',mapping: 'feeCredit'},
            {name: 'feeDebit',mapping: 'feeDebit'},
            {name: 'amtTransFee',mapping: 'amtTransFee'},
            {name: 'xferInInsCd',mapping: 'xferInInsCd'},
            {name: 'xferOutInsCd',mapping: 'xferOutInsCd'},
            {name: 'cardSeqNum',mapping: 'cardSeqNum'},
            {name: 'orgTransAmt',mapping: 'orgTransAmt'},
            {name: 'orgTxnSsn',mapping: 'orgTxnSsn'},
            {name: 'orgTermSsn',mapping: 'orgTermSsn'},
            {name: 'orgDateTime',mapping: 'orgDateTime'},
            {name: 'orgHostTxnSsn',mapping: 'orgHostTxnSsn'},
            {name: 'orgStlmDate',mapping: 'orgStlmDate'},
            {name: 'errCode',mapping: 'errCode'},
            {name: 'lstUpdTlr',mapping: 'lstUpdTlr'},
            {name: 'createTime',mapping: 'createTime'},
            {name: 'lstUpdTime',mapping: 'lstUpdTime'}
		]),
		autoLoad: false
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
					id: 'mchtId',
					hiddenName: 'mchtId',
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
						fieldLabel: '系统跟踪号',
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
						fieldLabel: '清分状态',
						id: 'stlmFlg',
						hiddenName: 'stlmFlg',
						anchor: '90%'
//						,renderer:errTxnStlmFlg
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
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易金额（元）',
						id: 'amtTrans',
						hiddenName: 'amtTrans',
						anchor: '90%',
						renderer:formatAmtTrans
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
						fieldLabel: '差错交易发起标识',
						id: 'errTrigFlag',
						hiddenName: 'errTrigFlag',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '差错交易标志',
						id: 'errFlag',
						hiddenName: 'errFlag',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '授权应答码',
						id: 'authorRspCd',
						hiddenName: 'authorRspCd',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '代理机构代码',
						id: 'acqInsIdCd',
						hiddenName: 'acqInsIdCd',
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '接收机构代码',
						id: 'rcvInsIdCd',
						hiddenName: 'rcvInsIdCd',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '发卡机构代码',
						id: 'issInsIdCd',
						hiddenName: 'issInsIdCd',
						anchor: '90%'		
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '服务点输入码',
						id: 'posEntryMode',
						hiddenName: 'posEntryMode',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: 'IPS商户号',
						id: 'ipsNo',
						hiddenName: 'ipsNo',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '商户类型',
						id: 'mchtTp',
						hiddenName: 'mchtTp',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '交易返回码',
						id: 'respCode',
						hiddenName: 'respCode',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '应收手续费',
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
						fieldLabel: '应付手续费',
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
						fieldLabel: '持卡人交易手续费',
						id: 'amtTransFee',
						hiddenName: 'amtTransFee',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '转入机构标识码',
						id: 'xferInInsCd',
						hiddenName: 'xferInInsCd',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '转出机构标识码',
						id: 'xferOutInsCd',
						hiddenName: 'xferOutInsCd',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '卡片序列号',
						id: 'cardSeqNum',
						hiddenName: 'cardSeqNum',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原始交易金额',
						id: 'orgTransAmt',
						hiddenName: 'orgTransAmt',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原始交易的系统跟踪号',
						id: 'orgTxnSsn',
						hiddenName: 'orgTxnSsn',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原始交易的终端流水号',
						id: 'orgTermSsn',
						hiddenName: 'orgTermSsn',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原交易日期',
						id: 'orgDateTime',
						hiddenName: 'orgDateTime',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原中心交易流水号',
						id: 'orgHostTxnSsn',
						hiddenName: 'orgHostTxnSsn',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '原始清算日期',
						id: 'orgStlmDate',
						hiddenName: 'orgStlmDate',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '差错原因',
						id: 'errCode',
						hiddenName: 'errCode',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '最近更新操作员',
						id: 'lstUpdTlr',
						hiddenName: 'lstUpdTlr',
						anchor: '90%'
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '创建时间',
						id: 'createTime',
						hiddenName: 'createTime',
						anchor: '90%'				
	       		}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	        	labelStyle: 'padding-left: 5px',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '最近更新时间',
						id: 'lstUpdTime',
						hiddenName: 'lstUpdTime',
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
			txnNum: txnNum,
			txnSsn: txnSsn
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
