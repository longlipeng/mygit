	var selectTermInfoNamespace = {}; 
    
    selectTermInfoNamespace.termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
        width: 900,
        frame: true,
        items: [{
                title: '基本信息',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth: 1, 
                    layout: 'form',
                    hidden:true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '商户英文名',
                //        id: 'idtxn27Upd',
                        name: 'engName'
                    }]
            },{
                columnWidth: .8, 
                layout: 'form',
                items: [{
               	 xtype: 'combofordispaly',
                    fieldLabel: '代理商信息',
                    id:'idagentNoInfo',
                    baseParams:'AGENT_NORMAL',
                    hiddenName:'agentNo',
                    emptyText:' ',
                    readOnly: true,
                    width: 317
                }]
            },{
                columnWidth: .8, 
                layout: 'form',
                items: [{
               	 xtype: 'combofordispaly',
                    fieldLabel: '分公司信息',
                    baseParams:'BRANCH_NO',
                    hiddenName:'acqInstId',
                    emptyText:' ',
                    id:'idacqInstIdInfo',
                    readOnly: true,
                    width: 317
                }]
            },
            /*{
                columnWidth: .8, 
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '客户经理信息',
                    id:'idoperNmInfo',
                    hiddenName:'operNm',
                    readOnly: true,
                    width: 300
                }]
            },*/
            {
                columnWidth: 1, 
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
		            fieldLabel: '商户简称',
		            id: 'idtxn22Upd',
		            name: 'txn22Upd'
         }]
       },{
           columnWidth: .5,
           layout: 'form',
           items: [{
               xtype: 'combofordispaly',
               fieldLabel: '产权属性',
               baseParams: 'PROP_TP',
               id:'idproptpu',
               hiddenName: 'propTpU'
           }]
       },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '终端MCC码',
                        id: 'idtermMccUpd',
                        name: 'termMccUpd'
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 hidden:true,
                 items:[{
                 	xtype: 'combofordispaly',
                    fieldLabel: '终端所属分行',
                    baseParams: 'BRH_BELOW',
                    id:'idtermBra',
                    hiddenName: 'termBranchUpd'
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '联系电话',
                    id: 'idcontTelUpd',
                    name: 'contTelUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '联系人',
                    id: 'idtermPara2Upd',
                    name: 'termPara2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                   // fieldLabel: '收单服务机构',
                    fieldLabel:'产权所属机构名称',
          //          store: organStore,
                    id: 'propInsNmD',
                    hiddenName: 'propInsNmD'
                }]
            },{
                columnWidth: .5,
                id: "flagBox2",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '第三方分成比例',
                    id: 'idtermPara1Upd',
                    name: 'termPara1Upd',
                    readOnly: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '连接类型',
                    baseParams: 'CONNECT_MODE',
                    id:'IDCondeU',
                    hiddenName: 'connectModeU'
                }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                 	xtype: 'combofordispaly',
                    fieldLabel: '终端厂商',
                    baseParams: 'MANUFACTURER',
                    store: termFactoryStore,
                    id:'idtermFactoryU',
                    hiddenName: 'termFactoryUpd'
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '终端型号',
               //     id: 'idtermMachTp',
                    baseParams: 'TERMMACHTP',
              //      name:'termMachTp',
               //     methodName: 'getTermMachTp',
                    hiddenName: 'termMachTpUpd'
                   
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items:[{
                  xtype: 'combofordispaly',
                  fieldLabel: '终端类型',
                  baseParams: 'TERM_TYPE',
                  id: 'IDTermTpU',
                  hiddenName: 'termTpU'
                }]
              },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype:'combofordispaly',
                    fieldLabel: '安装维护机构',
                    id: 'misc3D',
                    baseParams: 'MISC3U',
           //         allowBlank: false,
                    maxLength: 40,
					vtype: 'isOverMax'
			//		 methodName: 'getNormalAgentID',
              //     hiddenName: 'misc3D'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                	xtype:'displayfield',
                    fieldLabel: '安装维护员',
                    id: 'misc2D',
                    allowBlank: true,
                    maxLength: 40,
					vtype: 'isOverMax',
                    hiddenName: 'misc2D'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '运行维护方代码1',
                    id: 'IDRun1',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId1U' 
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '运行维护方代码2',
                    id: 'IDRunId2',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'runMainId2U'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '其他服务方代码',
                    id: 'IDOthSvr',
                    allowBlank: true,
                    store:termFactoryStore,//在systemSuport.js 中定义的常量
                    hiddenName: 'othSvrIdU'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '终端安装地所属省*',
                    id: 'provinceD',
                    baseParams: 'PROVINCES',
                    hiddenName: 'provinceD'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '终端安装地所属市*',
                    id:'idcityD',
                    hiddenName: 'cityD',
                    baseParams: 'CITIES'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '终端安装地所属区/县',
                    id:'idareaD',
                    baseParams: 'AREAS',
                    hiddenName:'areaD'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '终端安装地址',
                    readOnly: true,
                    id: 'IDtermAddrUpd',
                    name: 'termAddrUpd'
                }]
            },{
                columnWidth: .5, 
                layout: 'form',
                hidden:true,
                items: [{
                	xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
//						baseParams: 'CITY_CODE',
						fieldLabel: '终端安装地地区代码*',
						baseParams: 'TERMCITYCODE',
						hiddenName: 'cityCodeD'
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                frame: true,
                items: [{
                id: 'accountBox2',
                hidden:true,
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号1',
                    id: 'IDfinanceCard1Upd',
                    name: 'financeCard1Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号2',
                    id: 'IDfinanceCard2Upd',
                    name: 'financeCard2Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号3',
                    id: 'IDfinanceCard3Upd',
                    name: 'financeCard3Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '绑定电话1',
                    id: 'IDtxn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '绑定电话2',
                    id: 'IDtxn15Upd',
                    name: 'txn15Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '绑定电话3',
                    id: 'IDtxn16Upd',
                    name: 'txn16Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话1',
                    id: 'IDbindTel1Upd',
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话2',
                    id: 'IDbindTel2Upd',
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话3',
                    id: 'IDbindTel3Upd',
                    name: 'bindTel3Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'IDkeyDownSignUpd',
                    name: 'keyDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'IDparamDUpd',
                    name: 'paramDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'IDicDonUpd',
                    name: 'icDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'IDreselag1Upd',
                    name: 'reserveFlag1Upd',
                    disabled: true
                }]
            }/*,{
                columnWidth: .8,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '中行终端主密钥',
                    id: 'IdproductCdUpd',
                    name: 'productCdUpd',
                    width:250
                }]
            }*/]
            },{
                title: '交易信息',
                layout: 'column',
                frame: true,
                items: [{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款支持期数',
                        id: 'IDtxn35Upd',
                        name: 'txn35Upd'
                    }]
                },{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款限额',
                        id: 'IDtxn36Upd',
                        name: 'txn36Upd'
                    }]
                },{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '消费单笔上限 ',
                        id: 'IDtxn37Upd',
                        name: 'txn37Upd'
                    }]
                },{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货单笔上限 ',
                        id: 'IDtxn38Upd',
                        name: 'txn38Upd'
                    }]
                },{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '转帐单笔上限 ',
                        id: 'IDtxn39Upd',
                        name: 'txn39Upd'
                    }]
                },{
                columnWidth: .5,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货时间跨度 ',
                        id: 'IDtxn40Upd',
                        name: 'txn40Upd'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'IDparam1Upd',
                        name: 'param1Upd',
                        inputValue: '1',
                    	disabled: true
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'IDparam2Upd',
                        name: 'param2Upd',
                        inputValue: '1',
                    	disabled: true
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'IDparam3Upd',
                        name: 'param3Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'IDparam4Upd',
                        name: 'param4Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'IDparam5Upd',
                        name: 'param5Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'IDparam6Upd',
                        name: 'param6Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'IDparam7Upd',
                        name: 'param7Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'IDparam8Upd',
                        name: 'param8Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款消费 ',
                        id: 'IDparam9Upd',
                        name: 'param9Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'IDparam10Upd',
                        name: 'param10Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款退货 ',
                        id: 'IDparam11Upd',
                        name: 'param11Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
//                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金消费 ',
                        id: 'IDparam12Upd',
                        name: 'param12Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
//                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金退货',
                        id: 'IDparam13Upd',
                        name: 'param13Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财物POS） ',
                        id: 'IDparam14Upd',
                        name: 'param14Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财物POS） ',
                        id: 'IDparam15Upd',
                        name: 'param15Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'IDparam16Upd',
                        name: 'param16Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'IDparam17Upd',
                        name: 'param17Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'IDparam18Upd',
                        name: 'param18Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'IDparam19Upd',
                        name: 'param19Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'IDparam20Upd',
                        name: 'param20Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'IDparam21Upd',
                        name: 'param21Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'IDparam22Upd',
                        name: 'param22Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'IDparam23Upd',
                        name: 'param23Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                hidden:true,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付  ',
                        id: 'IDparam24Upd',
                        name: 'param24Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'IDtermParaUpd',
                        name: 'termParaUpd'
                }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'IDrecCrtTs',
                        name: 'recCrtTs'
                }]
            }]
            },{
                title: '终端参数',
                frame: true,
                layout: 'column',
                items: [{ columnWidth: 1},
                {
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '超时时间',
                        id: 'txn12NewD',
                        name: 'txn12NewD',
                       	vtype: 'isNumber',
                       	maxLength:2,
                    	minLength:2
	                         
	                    }]
	            },{
	                    columnWidth: .5, 
	                    layout: 'form',
	                    items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: '拨号重拨次数*',
	                    id: 'idtxn13NewD',
	                    allowBlank: false,
	                    hiddenName: 'txn13NewD',
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
	                        fieldLabel: '支持小费 ',
	                        id: 'txn18NewD',
	                        name: 'txn18NewD',
	                        disabled: true,
	                        inputValue: '1'
	                     }]
	            },{
		             columnWidth: .5,
		              hidden:true,
	                 layout: 'form',
	                 items:[{
	                    xtype: 'displayfield',
	                    fieldLabel: '小费百分比',
	                    name: 'txn19NewD',
	                    id: 'txn19NewD',
	                    vtype: 'isNumber',
	                    maxLength:2,
						vtype: 'isOverMax'
	                 }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: '冲正重发次数*',
	                    id: 'idtxn23NewD',
	                    allowBlank: false,
	                    hiddenName: 'txn23NewD',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['3','3'],['5','5']]
	                    })
	           
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: 'POS终端应用类型*',
	                    id: 'idtxn11NewD',
	                    allowBlank: false,
	                    hiddenName: 'txn11NewD',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['10','磁条卡金融支付类'],['11','IC卡金融支付类']]
	                    })
	           
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                	xtype:'displayfield',
	                    fieldLabel: '打印联数*',
	                    id: 'txn27NewD',
	                    allowBlank: true,
	                    maxLength: 1,
						vtype: 'isNumber',
	                    hiddenName: 'txn27NewD'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: '快捷交易类型',
	                    id: 'idtxn30NewD',
	                    allowBlank: true,
	                    store:Txn_NumStore,//在systemSuport.js 中定义的常量
	                    hiddenName: 'txn30NewD'
	                }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '支持手工输入卡号 ',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn20NewD',
	                        name: 'txn20NewD',
	                        inputValue: '1'
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '自动签退 ',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn21NewD',
	                        name: 'txn21NewD',
	                        inputValue: '1'
	                     }]
	            }, {
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel : '消费撤销交易需要刷卡 ',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New1D',
	                        name: 'txn25New1D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '消费撤销交易需要输入密码',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New2D',
	                        name: 'txn25New2D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易需要刷卡/手输卡号',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New3D',
	                        name: 'txn25New3D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成撤销交易需要输入密码',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New4D',
	                        name: 'txn25New4D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易需要输入刷卡/手输卡号',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New5D',
	                        name: 'txn25New5D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权撤销交易需要输入密码',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New6D',
	                        name: 'txn25New6D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成交易需刷卡/手输卡号',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New7D',
	                        name: 'txn25New7D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '预授权完成需输入密码',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn25New8D',
	                        name: 'txn25New8D',
	                        inputValue: '1' 
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '屏蔽卡号',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn28NewD',
	                        name: 'txn28NewD',
	                        inputValue: '1'
	                     }]
	            },{
	                columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        boxLabel: '显示LOGO',
	                        hideLabel:true,
	                        disabled: true,
	                        id: 'txn29NewD',
	                        name: 'txn29NewD',
	                        inputValue: '1'
	                     }]
	            }]
            }]
    });
    
    selectTermInfoNamespace.termForm = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        labelWidth: 120,
        waitMsgTarget: true,
        layout: 'form',
        items: [{
             xtype: 'displayfield',
             fieldLabel: '终端库存编号',
             labelStyle: 'padding-left: 5px',
             name: 'termIdUpd',
             id: 'IDtermIdUpd'
         },{
             xtype: 'displayfield',
             fieldLabel: '商户编号',
             labelStyle: 'padding-left: 5px',
             name: 'mchnNoU',
             id: 'IDmchnNoU'
           },selectTermInfoNamespace.termPanel]
    });
    
    selectTermInfoNamespace.termWin = new Ext.Window({
        title: '终端信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: true,
        modal: true,
        width: 900,
        autoHeight: true,
        layout: 'fit',
        items: [selectTermInfoNamespace.termForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false
    });
    
    function praseTermParam(val) {
         selectTermInfoNamespace.termForm.getForm().findField("txn11NewD").setValue(val.substring(2,2+2));//POS终端应用类型
         selectTermInfoNamespace.termForm.getForm().findField("txn12NewD").setValue(val.substring(6,6+2).trim());//超时时间
         selectTermInfoNamespace.termForm.getForm().findField("txn13NewD").setValue(val.substring(10,11).trim());//重播次数
         selectTermInfoNamespace.termForm.getForm().findField("txn14Upd").setValue(val.substring(13,13+14).trim());//交易电话1
         selectTermInfoNamespace.termForm.getForm().findField("txn15Upd").setValue(val.substring(29,29+14).trim());//交易电话2
         selectTermInfoNamespace.termForm.getForm().findField("txn16Upd").setValue(val.substring(45,45+14).trim());//交易电话3
         selectTermInfoNamespace.termForm.getForm().findField("txn20NewD").setValue(val.substring(84,84+1));//是否支持手工输入卡号
         selectTermInfoNamespace.termForm.getForm().findField("txn21NewD").setValue(val.substring(87,87+1));//是否自动签退
        
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
         selectTermInfoNamespace.termForm.getForm().findField("txn22Upd").setValue(val.substring(90,j).trim());//商户名
         selectTermInfoNamespace.termForm.getForm().findField("txn23NewD").setValue(val.substring(j+2,2+j+1).trim());//冲正重发次数
        
        T30101.getTermParam(val.substring(j+8,j+10),function(ret){
            var termParam = ret;
        for(var i=0;i<=7;i++){
        	 selectTermInfoNamespace.termForm.getForm().findField('txn25New'+(i+1)+'D').setValue(termParam.substring(i,i+1));
        }});
        
        T30101.getTermParam(val.substring(j+12,j+20),function(ret){
            var termParam = ret;
        for(var i=0;i<=12;i++){
             selectTermInfoNamespace.termForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }}); 
              
       /* T30101.getTermParam(val.substring(j+8,j+10),function(ret){
            var termParam = ret;
        for(var i=0;i<=4;i++){
        	updTermForm.getForm().findField('txn25New'+(i+1)+'D').setValue(termParam.substring(i,i+1));
            //updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }});
        
        T30101.getTermParam(val.substring(j+12,j+20),function(ret){
            var termParam = ret;
        for(var i=0;i<=10;i++){
        	 
            updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }});*/
        
         selectTermInfoNamespace.termForm.getForm().findField("txn27NewD").setValue(val.substring(j+22,j+22+1));//热敏打印联数
         selectTermInfoNamespace.termForm.getForm().findField("txn28NewD").setValue(val.substring(j+25,j+25+1));//是否屏蔽卡号
         selectTermInfoNamespace.termForm.getForm().findField("txn29NewD").setValue(val.substring(j+28,j+28+1));//是否显示LOGO
         selectTermInfoNamespace.termForm.getForm().findField("txn30NewD").setValue(val.substring(j+31,j+31+2));//快捷交易类型
    
    	/*
    	
        var array = val.split("|");
        if(array[4] == undefined)
        	return 0;
        array[4] = array[4].substring(2,array[4].length);
        T30101.getTermParam(array[4],function(ret){
            var termParam = ret;
        for(var i=0;i<=23;i++){
            selectTermInfoNamespace.termForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }
        
        selectTermInfoNamespace.termForm.getForm().findField("txn14Upd").setValue(array[0].substring(2,array[0].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn15Upd").setValue(array[1].substring(2,array[1].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn16Upd").setValue(array[2].substring(2,array[2].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn22Upd").setValue(array[3].substring(2,array[3].length));
        selectTermInfoNamespace.termForm.getForm().findField("txn27Upd").setValue(array[5].substring(2,array[5].length));
        selectTermInfoNamespace.termForm.getForm().findField("txn35Upd").setValue(array[12].substring(2,array[12].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn36Upd").setValue(array[13].substring(2,array[13].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn37Upd").setValue(array[14].substring(2,array[14].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn38Upd").setValue(array[15].substring(2,array[15].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn39Upd").setValue(array[16].substring(2,array[16].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn40Upd").setValue(array[17].substring(2,array[17].length).trim());

        //var value = Ext.getCmp('termTpU').getValue();
        if(value == '1')   
        {
           Ext.getCmp('accountBox2').show();
           Ext.getCmp('financeCard1Upd').allowBlank = false;
        }else
	    {
	       Ext.getCmp('accountBox2').hide();
           Ext.getCmp('financeCard1Upd').allowBlank = true;
	    }
        });
    */};
    	 selectTermInfoNamespace.citiesStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	 selectTermInfoNamespace.areasStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    selectTermInfoNamespace.termInfoStore = new Ext.data.Store({
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
            {name: 'termPara2Upd',mapping: 'termPara2'},	//保存联系人
            {name: 'propTpU',mapping: 'propTp'},
         
            {name: 'propInsNmD',mapping: 'propInsNm'},
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
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'misc2D',mapping: 'misc2'},
            {name: 'misc3D',mapping: 'misc3'},
            {name: 'runMainId1U',mapping: 'runMainId1'},
            {name: 'runMainId2U',mapping: 'runMainId1'},
            {name: 'othSvrIdU',mapping: 'othSvrId'},
          	{name: 'provinceD',mapping: 'province'},
            {name: 'cityD',mapping: 'city'},
            {name: 'areaD',mapping: 'area'},
            {name: 'cityCodeD',mapping: 'cityCode'},
            {name: 'productCdUpd',mapping: 'productCd'},
            {name: 'agentNo',mapping: 'agentNo'},
            {name: 'acqInstId',mapping: 'acqInstId'},
            {name: 'operNm',mapping: 'operNm'},
            {name: 'engName',mapping: 'engName'}
            
            
            
        ]),
        autoLoad: false
    });

	function selectTermInfo(termId,recCrtTs) {
		selectTermInfoNamespace.termInfoStore.load({
	        params: {
	             storeId: 'getTermInfo',
	             termId: termId,
	             recCrtTs: recCrtTs
	        },
	        callback: function(records, options, success){
//	        	Ext.MessageBox.alert("Result",selectTermInfoNamespace.termInfoStore.getAt(0).data.areaD);
	            if(success){
	            selectTermInfoNamespace.termForm.getForm().loadRecord(selectTermInfoNamespace.termInfoStore.getAt(0));	
					SelectOptionsDWR.getComboDataWithParameter('CITIES',selectTermInfoNamespace.termInfoStore.getAt(0).data.provinceD,
							function(ret){
								 selectTermInfoNamespace.citiesStore.loadData(Ext.decode(ret));
								Ext.getCmp('idcityD').setValue(selectTermInfoNamespace.termInfoStore.getAt(0).data.cityD);
							});
						
					if(selectTermInfoNamespace.termInfoStore.getAt(0).data.areaD == null ||
							selectTermInfoNamespace.termInfoStore.getAt(0).data.areaD=='')
						Ext.getCmp('idareaD').setValue(null);
					else{
		                SelectOptionsDWR.getComboDataWithParameter('AREAS',selectTermInfoNamespace.termInfoStore.getAt(0).data.cityD,
								function(ret){
									 selectTermInfoNamespace.areasStore.loadData(Ext.decode(ret));
									Ext.getCmp('idareaD').setValue(selectTermInfoNamespace.termInfoStore.getAt(0).data.areaD);
								});
					    }
	            	
	            	var termPara = selectTermInfoNamespace.termForm.getForm().findField("termParaUpd").value;
	            	praseTermParam(termPara);
	            	selectTermInfoNamespace.termPanel.setActiveTab(0);
	            	selectTermInfoNamespace.termWin.show();
	            	selectTermInfoNamespace.termWin.center();
	            }else{
	            	selectTermInfoNamespace.termWin.hide();
	            	alert("载入终端信息失败，请稍后再试!")
	            }
	        }
	    });
	};