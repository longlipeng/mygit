Ext.onReady(function() {
	var queryForm = new Ext.form.FormPanel({
		title: '备付金报表下载',
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		renderTo: 'report',
		iconCls: 'T80206',
		waitMsgTarget: true,
		defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},
		items: [{
			xtype: 'basecomboselect',
			labelStyle: 'padding-left: 5px',
			id: 'idreportName',
			hiddenName: 'reportName',
			fieldLabel: '报表文件名称',
			allowBlank: false,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','1-支付机构客户备付金信息统计报表'],['2','2-支付机构客户备付金出金业务明细表'],['3','3-支付机构客户备付金业务实际出金明细表'],['4','4-支付机构客户资金账户转账业务统计表'],['5','5-支付机构客户资金账户余额统计表'],['6','6-支付机构招商银行特殊业务明细表'],['7','7-支付机构现金购卡业务统计表'],['8','8-支付机构预付卡现金赎回业务统计表'],['9','9-支付机构招商银行客户备付金业务未达账项统计表'],['10','10-支付机构招商银行客户备付金业务未达账项分析表'],['11','11-支付机构客户资金账户余额变动调节表'],['12','12-支付机构客户资金账户余额试算表'],['13','13-预付卡发行企业备付金账户中售卡押金统计表'],['14','14-招商银行汇明商务服务有限公司支付机构备付金银行账户余额统计表'],['15','15-银行客户备付金入金业务调节表'],['16','16-分银行账户的表1-2'],['17','17-支付机构管理账户情况统计'],['18','18-预付卡商户白名单'],['19','19-支付机构合作行每日账户余额列表']],
				reader: new Ext.data.ArrayReader()
			})
			,listeners: {
				'select': function() {
					var value = Ext.getCmp("idreportName").getValue();
					if(value=='1'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(true); 
						Ext.getCmp('editBankAccount').allowBlank=false;
					} else if(value=='2'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='3'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='4'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='5'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='6'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(true); 
						Ext.getCmp('editBankAccount').allowBlank=false;
					} else if(value=='7'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='8'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='9'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(true); 
						Ext.getCmp('editBankAccount').allowBlank=false;
					} else if(value=='10'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(true); 
						Ext.getCmp('editBankAccount').allowBlank=false;
					} else if(value=='11'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='12'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='13'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='14'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='15'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='16'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(true); 
						Ext.getCmp('editBankAccount').allowBlank=false;
					} else if(value=='17'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='18'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					} else if(value=='19'){
						queryForm.findById("editBankAccount").getEl().up('.x-form-item').setDisplayed(false); 
						Ext.getCmp('editBankAccount').allowBlank=true;
					}
				}
			}
		},{
			xtype:'dynamicCombo',
			fieldLabel: '银行账户*',
			methodName: 'getBankNo',
			id: 'editBankAccount',
			hiddenName: 'bankAccount'
		},{
			xtype: 'datefield',
			id:'date',
			name: 'date',
			fieldLabel: '日期*',
			blankText: '请选择日期',
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				var reportName = queryForm.getForm().findField('idreportName').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表文件名称。',queryForm);
					return;
				}
				var date = queryForm.getForm().findField('date').getValue();
				if(reportName == ''){
					showErrorMsg('请选择报表日期。',queryForm);
					return;
				}
				queryForm.getForm().submit({
					url: 'T80229Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					success: function(form,action) {
					    window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
													action.result.msg;
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg,queryForm);
					}
				});
			}
		},
			{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	queryForm.getForm().findField('date').setValue(YESTERDAY);
})