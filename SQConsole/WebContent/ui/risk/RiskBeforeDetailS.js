function showRiskBeforeDetailS(mcht_Nm,El){
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getRiskBefore'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'MCHT_NM'
		},[
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			{name: 'LICENSE_NO',mapping: 'LICENSE_NO'},
			{name: 'ORG_CODE',mapping: 'ORG_CODE'},
			{name: 'IDENTITY',mapping: 'IDENTITY'},
			{name: 'SCORE',mapping:'SCORE'},
			{name: 'GRADE',mapping: 'GRADE'},
			{name: 'MCHT_TYPE',mapping: 'MCHT_TYPE'},
			{name: 'MAIN_BUS_NUM',mapping: 'MAIN_BUS_NUM'},
			{name: 'RISK_INDUSTRY',mapping: 'RISK_INDUSTRY'},
			{name: 'REG_FUND',mapping: 'REG_FUND'},
			{name: 'PREMISES',mapping: 'PREMISES'},
			{name: 'PARAM1',mapping: 'PARAM1'},
			{name: 'PARAM2',mapping: 'PARAM2'},
			{name: 'PARAM3',mapping: 'PARAM3'},
			{name: 'PARAM4',mapping: 'PARAM4'},
			{name: 'PARAM5',mapping: 'PARAM5'},
			{name: 'PARAM6',mapping: 'PARAM6'},
			{name: 'PARAM7',mapping: 'PARAM7'},
			{name: 'PARAM8',mapping: 'PARAM8'},
			{name: 'PARAM9',mapping: 'PARAM9'},
			{name: 'PARAM10',mapping: 'PARAM10'},
			{name: 'PARAM11',mapping: 'PARAM11'},
			{name: 'PARAM12',mapping: 'PARAM12'},
			{name: 'PARAM13',mapping: 'PARAM13'},
			{name: 'PREMISESname',mapping: 'PREMISESname'},
			{name: 'rbauto15',mapping: 'rbauto15'},
			{name: 'rbauto16',mapping: 'rbauto16'},
			{name: 'rbauto17',mapping: 'rbauto17'},
			{name: 'rbauto18',mapping: 'rbauto18'},
			{name: 'PARAM27',mapping: 'PARAM27'},
			{name: 'PARAM15',mapping: 'PARAM15'},
			{name: 'PARAM16',mapping: 'PARAM16'},
			{name: 'PARAM17',mapping: 'PARAM17'},
			{name: 'PARAM18',mapping: 'PARAM18'},
			{name: 'PARAM19',mapping: 'PARAM19'},
			{name: 'PARAM20',mapping: 'PARAM20'},
			{name: 'PARAM21',mapping: 'PARAM21'},
			{name: 'PARAM22',mapping: 'PARAM22'},
			{name: 'PARAM23',mapping: 'PARAM23'},
			{name: 'PARAM24',mapping: 'PARAM24'},
			{name: 'PARAM25',mapping: 'PARAM25'}
		]),
		autoLoad: false
	});
 
	var fm = Ext.form;

	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 180,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
             	    columnWidth: .99,
            	    layout: 'form',
        		    items: [{
			        xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户名称*',
					id: 'idMCHT_NM',
					hiddenName: 'MCHT_NM',
					maxLength: 100,
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '营业执照号*',
					id: 'idLICENSE_NO',
					hiddenName: 'LICENSE_NO',
					maxLength: 100,
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '组织机构代码',
					id: 'idORG_CODE',
					hiddenName: 'ORG_CODE',
					maxLength: 100,
					anchor: '90%'
		        }]
        	},{
             	    columnWidth: .33,
            	    layout: 'form',
        		    items: [{
			        xtype: 'combofordispaly',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '法人身份证号码*',
					id: 'idIDENTITY',
					hiddenName: 'IDENTITY',
					maxLength: 100,
					anchor: '90%'
		        }]   		
        	}]
				},{
				xtype: 'panel',
			    layout: 'form',
			    autoScroll:true,
			    title: '风险分数',
			    labelWidth: 90,
                height: 400,
                items: [{
                	region: 'center',
                	layout: 'form',
                	items: [{
	        		columnWidth: .33,
	        		layout: 'form',
	       			xtype: 'radiogroup',
	       			 fieldLabel: '营业用地性质',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender',
                     name:'gender',
                      disabled:true,
                      columns: [250,.52,.33],
					items: [           
                           {boxLabel: '自有', name: 'MCHT_TYPE',inputValue: 1},       
                           {boxLabel: '租用', name: 'MCHT_TYPE', inputValue: 2}            
                           ]  
				},{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '营业面积',
	       			 labelStyle: 'padding-left: 5px',
	       			 layout: 'form',
	       			 id   : 'gender1',
                     name:'gender1',
                      columns: [250,.52,.33],
                       disabled:true,
					items: [           
                           {boxLabel: '20平米以下', name: 'MAIN_BUS_NUM', inputValue: 01},          
                           {boxLabel: '21-50平米', name: 'MAIN_BUS_NUM', inputValue: 02},
                           {boxLabel: '51-100平米 ', name: 'MAIN_BUS_NUM', inputValue: 03},
                           {boxLabel: '101-200平米', name: 'MAIN_BUS_NUM', inputValue: 04},
                           {boxLabel: '200平米以上', name: 'MAIN_BUS_NUM', inputValue: 05}
                           ]
                           },{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '主营业务数量',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender4',
                     name:'gender4',
                   columns: [250,.52,.48],
                    disabled:true,
					items: [           
                           {boxLabel: '数量(0–1)', name: 'PARAM1', inputValue: 01},          
                           {boxLabel: '数量(2–3)', name: 'PARAM1', inputValue: 02},
                           {boxLabel: '数量(4–5)', name: 'PARAM1', inputValue: 03},
                           {boxLabel: '数量(6–7)', name: 'PARAM1', inputValue: 04},
                           {boxLabel: '数量(大于7)', name: 'PARAM1', inputValue: 05}                    
                           ]
           }
           /*,{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '员工数量',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender5',
                     name:'gender5',
                     columns: [250,.52,.48],
                      disabled:true,
					items: [           
                           {boxLabel: '数量(0–5)', name: 'PARAM2', inputValue: 01},          
                           {boxLabel: '数量(6–20)', name: 'PARAM2', inputValue: 02},
                           {boxLabel: '数量(21 –50)', name: 'PARAM2', inputValue: 03},
                           {boxLabel: '数量(51–200)', name: 'PARAM2', inputValue: 04},
                           {boxLabel: '数量(大于200)', name: 'PARAM2', inputValue: 05}
                           ]        	
           }*/
           /*,{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '连锁门店数量',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender6',
                     name:'gender6',
                      disabled:true,
                    columns: [250,.52,.48],
					items: [           
                           {boxLabel: '数量(单店)', name: 'PARAM3', inputValue: 01},          
                           {boxLabel: '数量(2–10)', name: 'PARAM3', inputValue: 02},
                           {boxLabel: '数量(11–20)', name: 'PARAM3', inputValue:03},
                           {boxLabel: '数量(21–50)', name: 'PARAM3', inputValue: 04},
                           {boxLabel: '数量(大于50)', name: 'PARAM3', inputValue: 05}
                           ]        	
           }*/
           ,{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户来源',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender7',
                     name:'gender7',
                   columns: [250,.52,.48],
                    disabled:true,
					items: [           
                           {boxLabel: '自己发展', name: 'PARAM4', inputValue: 01},          
                           {boxLabel: '其他收单机构推荐', name: 'PARAM4', inputValue: 02},
                           {boxLabel: '其他第三方推荐', name: 'PARAM4', inputValue: 03},
                           {boxLabel: '主动上门要求装机', name: 'PARAM4', inputValue: 04}
                           ]        	
           
           },{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户交易类型',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender9',
                     name:'gender9',
                      disabled:true,
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '实地交易', name: 'PARAM6', inputValue: 01},          
                           {boxLabel: '电话购物', name: 'PARAM6', inputValue: 02},
                           {boxLabel: '邮购', name: 'PARAM6', inputValue: 03},
                           {boxLabel: '网上交易', name: 'PARAM6', inputValue: 04}
                           ]        	  
           },
           	/*{
          	        columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户付款方式',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender15',
                     name:'gender15',
                      disabled:true,
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '赊购', name: 'PARAM7', inputValue: 01},          
                           {boxLabel: '分期付款', name: 'PARAM7', inputValue: 02},
                           {boxLabel: '钱货两清', name: 'PARAM7', inputValue: 03},
                           {boxLabel: '预付款', name: 'PARAM7', inputValue: 04}
                         
                           ]        	  
           }*/
             {  
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户营业场所',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender2',
                     name:'gender2',
                      disabled:true,
                     //columns: [250,.50,.50,.38,.50,.33],
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '非封闭批发市场或街边商铺的批发类商户 ', name: 'RISK_INDUSTRY', inputValue: 01},          
                           {boxLabel: '非封闭小区居民楼服务类或贸易公司', name: 'RISK_INDUSTRY', inputValue: 02},
                           {boxLabel: '位于写字楼的零售或批发类商户 ', name: 'RISK_INDUSTRY', inputValue: 03},
                           {boxLabel: '封闭小区或商住两用楼的服务类商户 ', name: 'RISK_INDUSTRY', inputValue: 04},
                           {boxLabel: '封闭批发市场或封闭市场商铺的商户', name: 'RISK_INDUSTRY', inputValue: 05},
                           {boxLabel: '街边零售类商铺', name: 'RISK_INDUSTRY', inputValue: 06},
                           {boxLabel: '其他', name: 'RISK_INDUSTRY', inputValue: 07}
                           ]
              },{
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户服务类别码',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender8',
                     name:'gender8',
                      disabled:true,
                     columns: [250,.50,.50],
					items: [           
                           {boxLabel: '单店经营的建材批发单位', name: 'PARAM5', inputValue: 01},          
                           {boxLabel: '单店经营的五金器材及用品批发单位', name: 'PARAM5', inputValue: 02},
                           {boxLabel: '单店经营的花木栽种用品,苗木,花卉批发单位', name: 'PARAM5', inputValue: 03},
                           {boxLabel: '单店经营的电器零件和设备单位', name: 'PARAM5', inputValue: 04},
                           {boxLabel: '市场内经营的计算机,算机外围设备批发单位', name: 'PARAM5', inputValue: 05},
                           {boxLabel: '无航空运输销售代理业务经营批准证书复印件票务代理', name: 'PARAM5', inputValue: 06},
                           {boxLabel: '单店经营的贵重珠宝销售单位', name: 'PARAM5', inputValue: 07},
                           {boxLabel: '各类单店经营的杂货店,便利店', name: 'PARAM5', inputValue: 08},
                           {boxLabel: '单店经营电信服务单位本地长途电话(固话,移动缴费类)', name: 'PARAM5', inputValue: 10},
                           {boxLabel: '单店经营的旅行社', name: 'PARAM5', inputValue: 09},
                           {boxLabel: '单店经营的通讯设备(电话销售)销售单位', name: 'PARAM5', inputValue: 11},
                           {boxLabel: '单店经营的计算机网络/信息服务单位', name: 'PARAM5', inputValue: 12},
                           {boxLabel: '单店经营的计算机软件商店', name: 'PARAM5', inputValue: 13},
                           {boxLabel: '单店经营的文具店,办公室,学校用品商店', name: 'PARAM5', inputValue: 14},
                           {boxLabel: '单店经营的五金商店', name: 'PARAM5', inputValue: 15},
                           {boxLabel: '单店经营的美容,美发类单位', name: 'PARAM5', inputValue: 16},
                           {boxLabel: '以上都不是', name: 'PARAM5', inputValue: 17}
                           ]        	
           }
           /*,{
                   columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '商户受理类型',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender11',
                     name:'gender11',
                      disabled:true,
                      columns: [250,.52,.48],
					items: [           
                           {boxLabel: '受理人民币卡', name: 'PARAM8', inputValue: 01},          
                           {boxLabel: '受理外卡', name: 'PARAM8', inputValue: 02},
                           {boxLabel: '外卡人民卡兼收', name: 'PARAM8', inputValue: 03}
                           ]        	  
           },{
                   columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '收单经验',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender12',
                     name:'gender12',
                      disabled:true,
                       columns: [250,.52,.48],
					items: [           
                           {boxLabel: '有人民币卡及外卡收单经验', name: 'PARAM9', inputValue: 01},          
                           {boxLabel: '仅有外卡收单经验', name: 'PARAM9', inputValue: 02},
                           {boxLabel: '仅有人民币卡收单经验', name: 'PARAM9', inputValue: 03},
                           {boxLabel: '无任何收单经验', name: 'PARAM9', inputValue: 04}                        
                           ]        	            
           },{
                    columnWidth: .33,
	       			xtype: 'radiogroup',
	       			 fieldLabel: '是否曾被拒绝',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'gender13',
                     name:'gender13',
                      disabled:true,
                       columns: [250,.52,.48],
					items: [           
                           {boxLabel: '不曾被拒绝过', name: 'PARAM10', inputValue: 01},          
                           {boxLabel: '以前曾被拒', name: 'PARAM10', inputValue: 02}                    
                           ]        	                 
           }*/
           ,{    
	        		columnWidth: .33,
	       			xtype: 'radiogroup',
	       			fieldLabel: '商户注册资本',
	       			labelStyle: 'padding-left: 5px',
	       			id   : 'gender3',
                    name:'gender3',
                    disabled:true,
                    columns: [250,.52,.48],
					items: [
					       {boxLabel: '无 ', name: 'REG_FUND', inputValue: 07},
                           {boxLabel: '10万元以下 ', name: 'REG_FUND', inputValue: 01},          
                           {boxLabel: '10-20万元', name: 'REG_FUND', inputValue: 02},
                           {boxLabel: '20-50万元 ', name: 'REG_FUND', inputValue: 03},
                           {boxLabel: '50-100万元 ', name: 'REG_FUND', inputValue: 04},
                           {boxLabel: '100-500万元', name: 'REG_FUND', inputValue: 05},
                           {boxLabel: '500万以上', name: 'REG_FUND', inputValue: 06}]
           },{                     
                    columnWidth: .33,
	       			xtype: 'checkboxgroup',
	       			fieldLabel: '申请安装地区',
	       			labelStyle: 'padding-left: 5px',
	       			id   : 'gender14',
                    name:'gender14',
                    disabled:true,
                    columns: [250,.52,.48],
					items: [  {       
						xtype: 'checkboxgroup',
						items: [  
                             {boxLabel: '广东', name: 'PARAM27',id:'PARAM27', inputValue: 01,
                             listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM11').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}	},          
                             {boxLabel: '湖北', name: 'PARAM15',id:'PARAM15', inputValue: 02,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM15').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }},
                             {boxLabel: '河南', name: 'PARAM16',id:'PARAM16', inputValue: 03,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM16').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }} ,
                             {boxLabel: '海南', name: 'PARAM17',id:'PARAM17', inputValue: 04,
                              listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM17').checked==true){
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
							}
						}
                             }} 
                            ]
					},{
					xtype: 'checkboxgroup',
						items: [  
                            {boxLabel: '山东', name: 'PARAM18',id:'PARAM18', inputValue: 05, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM18').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}
                             }} ,
                            {boxLabel: '浙江', name: 'PARAM21',id:'PARAM21', inputValue: 06, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM21').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}} ,
                            {boxLabel: '湖南', name: 'PARAM19',id:'PARAM19', inputValue: 07, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM19').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
									}
						}}},
                            {boxLabel: '湖南', name: 'PARAM26',id:'PARAM26', inputValue: 08,hidden:true}  
                             ]
					},{
						xtype: 'checkboxgroup',
						items: [ 
                            {boxLabel: '河北', name: 'PARAM20',id:'PARAM20', inputValue: 09, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM20').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
										Ext.getCmp('PARAM25').setValue(false);
									}
						}}} ,
                            {boxLabel: '山西', name: 'PARAM22',id:'PARAM22', inputValue: 10, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM22').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
							}
						}}} ,
                            {boxLabel: '江西', name: 'PARAM23',id:'PARAM23', inputValue: 11, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM23').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM25').setValue(false);
							}
						}}},
                            {boxLabel: '安徽', name: 'PARAM24',id:'PARAM24', inputValue: 12, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM24').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false)
									Ext.getCmp('PARAM25').setValue(false);
							}
						}}} 
                            ]},
                            {
						xtype: 'checkboxgroup',
						items: [ 
                            {boxLabel: '其他地区', name: 'PARAM25',id:'PARAM25', inputValue: 13, listeners :{
                             'check':function(r,c){
                    		if(Ext.getCmp('PARAM25').checked==true){
									Ext.getCmp('PARAM27').setValue(false);
									Ext.getCmp('PARAM15').setValue(false);
									Ext.getCmp('PARAM16').setValue(false);
									Ext.getCmp('PARAM17').setValue(false);
									Ext.getCmp('PARAM18').setValue(false);
									Ext.getCmp('PARAM21').setValue(false);
									Ext.getCmp('PARAM19').setValue(false);
									Ext.getCmp('PARAM20').setValue(false);
									Ext.getCmp('PARAM22').setValue(false);
									Ext.getCmp('PARAM23').setValue(false);
									Ext.getCmp('PARAM24').setValue(false)
									}
						}}} 
                            ]}
                           ]        	                                       
           },{
             	columnWidth: .33,
	        	id: 'otherNoPanel',	
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'checkbox',
					fieldLabel: '业务人员是否上门核实',
					 labelStyle: 'padding-left: 5px',
					  disabled:true,
					id: 'idPARAM12',
					name: 'PARAM12'
				}]
           },{
             	    columnWidth: .33,
	       			xtype: 'checkboxgroup',
	       			 fieldLabel: '其它',
	       			 labelStyle: 'padding-left: 5px',
	       			 id   : 'idPARAM13',
                     name:'PARAM13',
                      disabled:true,
                     allowBlank:true,
					items: [           
                           {boxLabel: '连续经营3年以上', name: 'rbauto15',id:'idrbauto15',inputValue: 01} ,   
                           {boxLabel: '单日营业额2万元以上', name: 'rbauto16',id:'idrbauto16',inputValue: 01},
                           {boxLabel: '缴纳保证金', name: 'rbauto17',id:'idrbauto17',inputValue: 01} ,
                           {boxLabel: '缴纳机具押金', name: 'rbauto18',id:'idrbauto18',inputValue: 01}
                           ]}
              ,{
                     columnWidth: .33,
	        	     xtype: 'panel',
	        	     layout: 'form',
	       		     items: [{
		       			xtype: 'dynamicCombo',
			            methodName: 'getEnt',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业性质',
						editable: true,
						 disabled:true,
						id: 'idPREMISES',
						hiddenName:'PREMISES',
						anchor: '90%'
				}]
           }]
                }]	
		}]
    });
    
    var detailWin = new Ext.Window({
    	title: '事前风险控制详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1045,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			mcht_Nm: mcht_Nm
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
			//	var discCode = baseStore.getAt(0).data.feeRate;
			//	Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				detailWin.setTitle('事前风险控制详细信息[商户名称：' + mcht_Nm + ']');
				
			    //mchntForm.getForm().findField('gender').setValue(baseStore.data.items[0].data.MCHT_TYPE);
 	            mchntForm.getForm().findField('gender').setValue(baseStore.data.items[0].data.MCHT_TYPE);
 	            mchntForm.getForm().findField('gender1').setValue(baseStore.data.items[0].data.MAIN_BUS_NUM);
 	            mchntForm.getForm().findField('gender4').setValue(baseStore.data.items[0].data.PARAM1);
 	          //  mchntForm.getForm().findField('gender5').setValue(baseStore.data.items[0].data.PARAM2);
 	           // mchntForm.getForm().findField('gender6').setValue(baseStore.data.items[0].data.PARAM3);
 	            mchntForm.getForm().findField('gender7').setValue(baseStore.data.items[0].data.PARAM4);
 	            mchntForm.getForm().findField('gender9').setValue(baseStore.data.items[0].data.PARAM6);
 	          //   mchntForm.getForm().findField('gender15').setValue(baseStore.data.items[0].data.PARAM7);
 	              mchntForm.getForm().findField('gender2').setValue(baseStore.data.items[0].data.RISK_INDUSTRY);
 	               mchntForm.getForm().findField('gender8').setValue(baseStore.data.items[0].data.PARAM5);
 	              // mchntForm.getForm().findField('gender11').setValue(baseStore.data.items[0].data.PARAM8);
 	            //   mchntForm.getForm().findField('gender12').setValue(baseStore.data.items[0].data.PARAM9);
 	            //   mchntForm.getForm().findField('gender13').setValue(baseStore.data.items[0].data.PARAM10);
 	               mchntForm.getForm().findField('gender3').setValue(baseStore.data.items[0].data.REG_FUND);
 	              // mchntForm.getForm().findField('gender11').setValue(baseStore.data.items[0].data.PARAM8);
 	               mchntForm.getForm().findField('idPARAM12').setValue(baseStore.data.items[0].data.PARAM12);
 	                if(baseStore.data.items[0].data.PARAM27!='0'){
 	                	mchntForm.getForm().findField('gender14').items[0].items[0].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM15!='0'){
 	                	mchntForm.getForm().findField('gender14').items[0].items[1].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM16!='0'){
 	                	mchntForm.getForm().findField('gender14').items[0].items[2].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM17!='0'){
 	                	mchntForm.getForm().findField('gender14').items[0].items[3].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM18!='0'){
 	                mchntForm.getForm().findField('gender14').items[1].items[0].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM21!='0'){
 	                	mchntForm.getForm().findField('gender14').items[1].items[1].checked=true;
 	                }
 	                 if(baseStore.data.items[0].data.PARAM19!='0'){
 	                	 mchntForm.getForm().findField('gender14').items[1].items[2].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM20!='0'){
 	                	mchntForm.getForm().findField('gender14').items[2].items[0].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM22!='0'){
 	                	mchntForm.getForm().findField('gender14').items[2].items[1].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM23!='0'){
 	                	mchntForm.getForm().findField('gender14').items[2].items[2].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM24!='0'){
 	                	 mchntForm.getForm().findField('gender14').items[2].items[3].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.PARAM25!='0'){
 	                	  mchntForm.getForm().findField('gender14').items[3].items[0].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.rbauto15!='0'){
 	                	mchntForm.getForm().findField('idPARAM13').items[0].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.rbauto16!='0'){
 	                	 mchntForm.getForm().findField('idPARAM13').items[1].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.rbauto17!='0'){
 	                	mchntForm.getForm().findField('idPARAM13').items[2].checked=true;
 	                }
 	                if(baseStore.data.items[0].data.rbauto18!='0'){
 	                	mchntForm.getForm().findField('idPARAM13').items[3].checked=true;
 	                }
 	                mchntForm.getForm().findField('idPREMISES').setValue(baseStore.data.items[0].data.PREMISES);

				detailWin.show();
			}else{
				showErrorMsg("加载信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}