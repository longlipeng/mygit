package com.webservice.util;

public class Constants {
	//public static final String textxml="82014001000140000000000000000001400000000000000140000120140723120001000000000000000000000000000000201407231201";
	//public static final String textxml ="<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlTime>20140723120001</ChanlTime><BatchNo>000000</BatchNo><KeyRsp>0000000000000000</KeyRsp><KeyRspValue>00000000</KeyRspValue></Body><Mac>201407231201</Mac></Envelope>";
	public static final String textxml ="<Envelope><Body><SCode>1601</SCode><ChanlTxnNum>111111</ChanlTxnNum><ChanlTime>20150710143133</ChanlTime><BatchNo>000001</BatchNo><TermCard1>0000</TermCard1><TermCard2>0000</TermCard2><MsgType>0200</MsgType><PrimaryAcctNum>9502010023000000238</PrimaryAcctNum><ProcessingCode>000000</ProcessingCode><AmtTrans>000000000100</AmtTrans><SysTraceAuditNum>111111</SysTraceAuditNum><PosEntryModeCode>051</PosEntryModeCode><CardSeqId>000</CardSeqId><PosCondCode>00</PosCondCode><CardAccptrTermnlId>11122233</CardAccptrTermnlId><CardAccptrId>123456789654321</CardAccptrId><CurrcyCodeTrans>156</CurrcyCodeTrans><sPinData>000000</sPinData><SecRelatdCtrlInfo>2600000000000000</SecRelatdCtrlInfo><EmvVal>0000000000</EmvVal><TermCode>11122233</TermCode><MechtCode>123456789654321</MechtCode><TxnTlr>1111111111111111</TxnTlr></Body></Envelope>";
	//商户对账文件查询
//	public static final String textxml ="<Envelope><Body><SCode>6111</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><StlmDate>20150423</StlmDate><FileNo>00</FileNo></Body><Mac>C67165CA</Mac></Envelope>";
	
	//商户对账文件下载
//	public static final String textxml ="<Envelope><Body><SCode>6121</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><StlmDate>20150423</StlmDate><FileName>充值文件</FileName><FileType>01</FileType></Body><Mac>C67165CA</Mac></Envelope>";
	/**渠道签到交易*///不需测
//	public static final String textxml ="<Envelope><Body><SCode>8201</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><KeyRsp>131F584CB15FA5BC</KeyRsp><KeyRspValue>131F584CB15FA5BC</KeyRspValue></Body><Mac>C67165CA</Mac></Envelope>";
	/** 根据卡面号查卡号*/
//	public static final String textxml ="<Envelope><Body><SCode>1801</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><CardFaceNo>1232476667000000</CardFaceNo></Body><Mac>C67165CA</Mac></Envelope>";
	/**个人记名信息修改*/
//	public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015000   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
		//账户交易明细查询
//	public static final String textxml ="<Envelope><Body><SCode>6021</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><CardNo>4000000006465305   </CardNo><TxnPwd>131F584CB15FA5BC</TxnPwd><TxnType>1101</TxnType><StartDate>20150420</StartDate><EndDate>20150417</EndDate><ApplyStartNum>十三三啊</ApplyStartNum></Body><Mac>A6A0386A</Mac></Envelope>";
	//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6021</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><CardNo>4000000006465305   </CardNo><TxnPwd>131F584CB15FA5BC</TxnPwd><TxnType>1071</TxnType><StartDate>20150420</StartDate><EndDate>20150417</EndDate><ApplyStartNum>0001</ApplyStartNum></Body><Mac>A6A0386A</Mac></Envelope>";
	//挂失/解挂失
//	public static final String textxml ="<Envelope><Body><SCode>6061</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><CardNo>4000000006465305   </CardNo><CardPass>131F584CB15FA5BC</CardPass><OperFlag>1</OperFlag></Body><Mac>123456</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6061</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><BatchNo>400001</BatchNo><ChanlTime>400001</ChanlTime><ChanlTxnNum>400001</ChanlTxnNum><CardNo>4000000006465000   </CardNo><CardPass>131F584CB15FA5BC</CardPass><OperFlag>1</OperFlag></Body><Mac>123456</Mac></Envelope>";
		//卡,账户信息查询
//	public static final String textxml ="<Envelope><Body><SCode>6051</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><QueryMode>00</QueryMode><CardNo>4000000006465305</CardNo><CardPass>131F584CB15FA5BC</CardPass><AccNo></AccNo><AccPass></AccPass><Resv1>fsfjjj</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6051</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><QueryMode>01</QueryMode><CardNo></CardNo><CardPass></CardPass><AccNo>5000000100000000432</AccNo><AccPass></AccPass><Resv1>fsfjjj</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//个人记名信息查询
//	public static final String textxml ="<Envelope><Body><SCode>6011</SCode><TermCode>60520001</TermCode><MechtCode>6052000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150527093352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000006465305</CardNo><CardPass>131F584CB15FA5BC</CardPass><IdType>01</IdType><IdNo></IdNo><CustNo></CustNo><Resv1></Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6011</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150327093352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000006465305</CardNo><CardPass>131F584CB15FA555</CardPass><IdType>01</IdType><IdNo></IdNo><CustNo></CustNo><Resv1></Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//IC卡黑名单下载
//	public static final String textxml ="<Envelope><Body><SCode>6001</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150120163352</ChanlTime><BatchNo>000000</BatchNo><BlackVer>20150122092222</BlackVer><DoldBlackVer>20150112110101</DoldBlackVer><BlackNum>0000</BlackNum></Body><Mac>A6A0386A</Mac></Envelope>";
		//public static final String textxml ="<Envelope><Body><SCode>6001</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150120163352</ChanlTime><BatchNo>000000</BatchNo><BlackVer>20150423000000</BlackVer><DoldBlackVer>20150423125959</DoldBlackVer><BlackNum>双击双色</BlackNum></Body><Mac>A6A0386A</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="";
		//个人账户充值
//	public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo></CardNo><TxnAmt></TxnAmt><Resv1>zhongguo</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="";
		//个人记名信息录入
//	public static final String textxml ="<Envelope><Body><SCode>6071</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><BusnesType>1</BusnesType><CustCName>吴尊</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>522628199001271544</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>吴尊</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6071</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><BusnesType>1</BusnesType><CustCName>无极</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>03</IdType><IdNo>522628199001271544</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
		//个人记名信息修改
//	public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5BC</CardPass><CustCName>zhouhanfei</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>500233198704201111</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
		//异常报文
//		public static final String textxml ="<Envelope><Body><SCode>6031</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000112015052   </CardNo><CardPass>131F584CB15FA5B</CardPass><CustCName>无极</CustCName><Sex>1</Sex><BirthDay>19870420</BirthDay><CustCny>001</CustCny><CustLevel>02</CustLevel><IdType>01</IdType><IdNo>522628199001271544</IdNo><MobileNo>13333422233</MobileNo><HomeAdd>chongqingxian</HomeAdd><PostAdd>xietaiz</PostAdd><Resv1>zhongguo</Resv1></Body><Mac>089204FA</Mac></Envelope>";
		//个人记名信息开户
//	public static final String textxml ="<Envelope><Body><SCode>6081</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CustNo>SC000000000000291809</CustNo><CustCName>吴尊</CustCName><IdType>01</IdType><IdNo>522628199001271544</IdNo><MainCardQueryPass>131F584CB15FA5BC</MainCardQueryPass><MainCardTxnPass>131F584CB15FA5BC</MainCardTxnPass><MainCardNo>4000000112016053   </MainCardNo><CardType>0008</CardType><Resv1>吴尊</Resv1></Body><Mac>089204FA</Mac></Envelope>";
		//个人账户充值冲正
//	public static final String textxml ="<Envelope><Body><SCode>2071</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><OriCardNo>4000000112015052   </OriCardNo><OriTxnTime>20150203163352</OriTxnTime><OriTxnNum>465810</OriTxnNum><OriTxnAmt>000000002000</OriTxnAmt><OriBatchNo>000000</OriBatchNo></Body><Mac>B16736D5</Mac></Envelope>";
	//个人账户充值
//	public static final String textxml ="<Envelope><Body><SCode>1071</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f55400000000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150203163352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000910000241   </CardNo><TxnAmt>000000002000</TxnAmt><Resv1>zhongguo</Resv1></Body><Mac>08B8D37C</Mac></Envelope>";
	//个人补卡\换卡6151
//	public static final String textxml ="<Envelope><Body><SCode>1171</SCode><TermCode>60280001</TermCode><MechtCode>6028000000000000    </MechtCode><TxnTlr>0000000000000001</TxnTlr><ChanlTxnNum>000000</ChanlTxnNum><ChanlTime>20150416143829</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000001110000057   </CardNo><CardSafeInfo>000000000000000000000000006F817F76</CardSafeInfo><CardType>0001</CardType><CardPhyType>20</CardPhyType><CardAts>107733A002868801678100016F817F76</CardAts><CityCode>0000</CityCode><CardUid>00000000</CardUid><CardAuthCode>00000000</CardAuthCode><CardTransNum>0000</CardTransNum><BefTxnAmt>00000000</BefTxnAmt><TxnTypeFlag>06</TxnTypeFlag><BusnesType>1002</BusnesType><TermNo>400001002091</TermNo><TermTxn>0000</TermTxn><TxnTime>20150416121212</TxnTime><Tac>DDBB7942</Tac><SuspcsTxnFlag>00</SuspcsTxnFlag><TermCard1>400001002091</TermCard1><TermCard2>400010050179</TermCard2><RecTxnAmt>00000000</RecTxnAmt><RealTxnAmt>00000000</RealTxnAmt><ConsumSpeData></ConsumSpeData><CashFileFlg>3F01</CashFileFlg><TxnAmt>000000000100</TxnAmt></Body><Mac>D64CCD0B</Mac></Envelope>";
	//个人账户退卡销户
//	public static final String textxml ="<Envelope><Body><SCode>6141</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlTime>20140723120001</ChanlTime><BatchNo>400001</BatchNo><CardNo>4000000006465305   </CardNo><CardPass>111111</CardPass><ReturnOrCancleFlg>1</ReturnOrCancleFlg></Body><Mac>123456</Mac></Envelope>";
	//个人账户副卡解绑定
//	public static final String textxml ="<Envelope><Body><SCode>6101</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><MainCardNo>4000004410003859</MainCardNo><MainCardTxnPass>131F584CB15FA5BC</MainCardTxnPass><FllowCardNo>4000004410003859</FllowCardNo><Resv1>fsfjjj</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
	//个人账户副卡添加
//	public static final String textxml ="<Envelope><Body><SCode>6091</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><MainCardNo>4000004410003859</MainCardNo><MainCardTxnPass>131F584CB15FA5BC</MainCardTxnPass><FllowCardNo>4000000112017052</FllowCardNo><FllowCardQueryPass>131F584CB15FA5BC</FllowCardQueryPass><FllowCardTxnPass>131F584CB15FA5BC</FllowCardTxnPass><CardType>0008</CardType></Body><Mac>A6A0386A</Mac></Envelope>";
	//个人记名开户
//	public static final String textxml ="<Envelope><Body><SCode>6081</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150209153352</ChanlTime><BatchNo>000000</BatchNo><CustNo>SC000000000000291797</CustNo><CustCName>中了一百万</CustCName><IdType>01</IdType><IdNo>500233198704201111</IdNo><MainCardQueryPass>131F584CB15FA5BC</MainCardQueryPass><MainCardTxnPass>131F584CB15FA5BC</MainCardTxnPass><MainCardNo>4000000112015052</MainCardNo><CardType>0001</CardType></Body><Mac>A6A0386A</Mac></Envelope>";
	//副卡列表信息查询
//	public static final String textxml ="<Envelope><Body><SCode>6041</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000812021113</CardNo><CardPass>131F584CB15FA5BC</CardPass><Resv1>fsfjjj</Resv1></Body><Mac>A6A0386A</Mac></Envelope>";
//	public static final String textxml ="<Envelope><Body><SCode>6041</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150210123352</ChanlTime><BatchNo>000000</BatchNo><CardNo>4000000812021113</CardNo><CardPass>131F584CB15FA555</CardPass></Body><Mac>A6A0386A</Mac></Envelope>";
	//个人账户转账
//	public static final String textxml ="<Envelope><Body><SCode>1061</SCode><TermCode>63380001</TermCode><MechtCode>6338000000000000</MechtCode><TxnTlr>28cfdaf1f5540000</TxnTlr><ChanlTxnNum>465810</ChanlTxnNum><ChanlTime>20150120163352</ChanlTime><BatchNo>000000</BatchNo><TermCard1>400080090066</TermCard1><TermCard2>400070090053</TermCard2><OutCardOrAccNo>4000000910000241</OutCardOrAccNo><OutCardOrAccFlg>0</OutCardOrAccFlg><InCardOrAccNo>4000000112015052</InCardOrAccNo><InCardOrAccFlg>0</InCardOrAccFlg><CustCName>中了一百万</CustCName><PassTestMode>1</PassTestMode><TxnPass>131F584CB15FA5BC</TxnPass><TxnAmt>000000000100</TxnAmt></Body><Mac>FFD40102</Mac></Envelope>";
	//String acceptXmlFormSd = "<Envelope><Body><SCode>6112</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>105252</ChanlTime><FrontRspCode>0000</FrontRspCode><ReplyMsg>测试专用</ReplyMsg><RetrivlRef>111111111</RetrivlRef><OutCardOrAccNo>4000000310003123</OutCardOrAccNo><InCardOrAccNo>4000000310003935</InCardOrAccNo><CustCName>小刘</CustCName><OutAccBalance>21432</OutAccBalance><FeeAmt>5</FeeAmt><TxnAmt>100000</TxnAmt></Body></Envelope>";
	//public static final String textxml ="<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>120001</ChanlTime></Body></Envelope>";
	//public static final String textxml = "<Envelope><Body><SCode>1001</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlTime>20140723120001</ChanlTime><BatchNo>400001</BatchNo><CardNo>10000001</CardNo><CardSafeInfo>08</CardSafeInfo><CardType>01</CardType><CardPhyType>01</CardPhyType><CardAts>88888888</CardAts><TermCard1>11111</TermCard1><TermCard2>2222222</TermCard2><PswdCheckWay>55</PswdCheckWay><TxnPswd>111111</TxnPswd><Mac>123456</Mac></Body></Envelope>";
	// public static final String resultXml_1000 =
	// "0383"+Constants.XMLBAOWENHEADER+"<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>120001</ChanlTime><FrontRspCode>00</FrontRspCode><ReplyMsg>1231312</ReplyMsg><RetrivlRef>1234567890</RetrivlRef><CardStat>01</CardStat></Body><Mac>1234567890</Mac></Envelope>";
	// public static final String resultXml_1001 =
	// "0383"+Constants.XMLBAOWENHEADER+"<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>120001</ChanlTime><FrontRspCode>00</FrontRspCode><ReplyMsg>1231312</ReplyMsg><RetrivlRef>1234567890</RetrivlRef><CardNo>40000000000000000001</CardNo><IssuingDate>20140802</IssuingDate><VaildDate>20202020</VaildDate><AcctAmt>200000</AcctAmt><AvaiableAmt>100000</AvaiableAmt></Body><Mac>1234567890</Mac></Envelope>";
	// public static final String resultXml_1002 =
	// "0383"+Constants.XMLBAOWENHEADER+"<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>120001</ChanlTime><FrontRspCode>00</FrontRspCode><ReplyMsg>1231312</ReplyMsg><RetrivlRef>1234567890</RetrivlRef><CardNo>40000000000000000001</CardNo><TxnAmt>200000</TxnAmt><TxnTime>20140730202020</TxnTime><Sectors>2</Sectors><KeyB>1234567890123456789</KeyB><Mac2>1234567890</Mac2></Body><Mac>1234567890</Mac></Envelope>";
	// public static final String resultXml_1003 =
	// "0383"+Constants.XMLBAOWENHEADER+"<Envelope><Body><SCode>8201</SCode><TermCode>40010001</TermCode><MechtCode>40000000000000000001</MechtCode><TxnTlr>4000000000000001</TxnTlr><ChanlTxnNum>400001</ChanlTxnNum><ChanlDate>0723</ChanlDate><ChanlTime>120001</ChanlTime><FrontRspCode>00</FrontRspCode><ReplyMsg>1231312</ReplyMsg><RetrivlRef>1234567890</RetrivlRef></Body><Mac>1234567890</Mac></Envelope>";
	public static final String PARAERR = "E1";// 参数错误
	public static final String SCODEERR = "E2";// 服务代码错误
	public static final String SYSERR = "E3";// 系统错误
	public static final String XMLENCODING = "GBK";// xml编码
	public static final String KEY_SOCKET_IP = "socket.ip";// 通信IP
	public static final String KEY_SOCKET_PORT = "socket.port";// 通信端口
	public static final String KEY_SOCKET_PORT2 = "socket.port2";// 绑卡通信端口
	public static final String KEY_SOCKET_IP_FORZHXT = "socket.ZHXT_ip";// 通信IP
	public static final String KEY_SOCKET_PORT_FORZHXT = "socket.ZHXT_port";// 通信端口
	public static final String KEY_HTTP_URL = "postUrl.url";// postIP
	public static final String KEY_SOCKET_LISTENER_PORT = "socket.listener_port";// 监听端口
	public static final String KEY_SOCKET_TIMEOUT = "socket.timeout";// 通信超时
	public static final String XMLBAOWENHEADER = "<?xml version=\"1.0\" encoding=\"GBK\"?>";// XML头// 服务码
	public static final String SCODE_CARDTXNSTAQUERY = "1031";// 卡状态查询
	public static final String SCODE_ACCOUNTBALANCEQUERY = "1021";// 预充值账户余额查询
	public static final String SCODE_CASHRECHARGE = "1051";// 电子钱包现金充值
	public static final String SCODE_CASHRECHARGEFLUSHES = "2051";// 电子钱包现金充值冲正
	public static final String SCODE_RECHARGENOTIFY = "1151";// 充值成功通知
	public static final String SCODE_ACCOUNTCONSUM = "1101";// 预充值账户消费
	public static final String SCODE_CASHCONSUM = "1171";// 电子钱包消费
	public static final String SCODE_ACCOUNTCONSUMFLUSHES = "2101";// 预充值账户消费冲正
	public static final String SCODE_ACCOUNTCONSUMCANCLE = "3101";// 预充值账户消费撤销
	public static final String SCODE_ACCOUNTCONSUMCANCLEFLUSHES = "4101";// 预充值账户消费撤销冲正
	public static final String SCODE_ACCOUNTCONSUMRETURNED = "5151";// 预充值消费退货
	public static final String SCODE_CASHRETURNED = "5171";// 电子钱包退货
	public static final String SCODE_SPEACCOUNTTRANSFER = "1081";// 电子钱包指定账户圈存
	public static final String SCODE_SPEACCOUNTTRANSFERNOTIFY = "1181";// 电子钱包指定账户圈存成功通知
	public static final String SCODE_SPEACCOUNTTRANSFERFLUSHES = "2081";// 电子钱包指定账户圈存冲正
	public static final String SCODE_NOSPEACCOUNTTRANSFER = "1091";// 电子钱包非指定账户圈存
	public static final String SCODE_NOSPEACCOUNTTRANSFERNOTIFY = "1191";// 电子钱包非指定账户圈存成功通知
	public static final String SCODE_NOSPEACCOUNTTRANSFERFLUSHES = "2091";// 电子钱包非指定账户圈存冲正
	public static final String SCODE_ACCOUNTPWDCHANGE = "7011";// 预充值账户密码修改
	public static final String SCODE_ACCOUNTPWDRESET = "1019";//预充值账户密码重置
	public static final String SCODE_CHANLSIGNIN = "8201";// 渠道签到交易
	public static final String SCODE_PERINFINQ = "8301";// 个人信息查询

	public static final String SCODE_CARDTXNDETAILINQ = "6021";// 账户交易明细查询
	public static final String SCODE_BLACKLISTDOWNLOAD = "8701";// IC卡黑名单下载
	
	public static final String SCODE_BLALISTDOWNLOAD = "6001";//IC卡黑名单下载
	public static final String SCODE_PERINF = "6011";//个人记名信息查询
	public static final String SCODE_CARDACCINFINQ = "6051";//卡,账户信息查询
	public static final String SCODE_CARDHANGUP = "6061";// 卡片挂失/接挂失
	public static final String SCODE_ACCOUNTTXNDETAILINQ = "6021";//账户交易明细查询
	public static final String SCODE_PERSONCUSTINFOADDINPUT = "6071";//个人记名信息录入
	public static final String SCODE_PERSONCUSTINFOADDAMEND = "6031";//个人记名信息修改
	public static final String SCODE_PERACCRECHARGE = "1071";//个人账户充值

	public static final String SCODE_ACCTRANSFERMONEY = "1061";// 个人账户转账
	public static final String SCODE_OPENPERSONACCWITHNAME = "6081";// 个人记名开户
	public final static String SCODE_ADDFLOWCARD = "6091";// 个人账户副卡添加
	public final static String  SCODE_UNBINDFLOWCARD= "6101";// 个人账户副卡解绑绑定
	public final static String  SCODE_PERSONCASHRECHARGEFLUSHES= "2071";// 个人账户充值冲正
	public final static String  SCODE_PERFCARDINFINQLIST= "6041";// 副卡列表信息查询
	public final static String SCODE_REPLACECARD = "6151";// 个人补卡\换卡
	public final static String SCODE_CANCLEACCORRETURNCARD = "6141";// 个人账户退卡销户
	public final static String SCODE_CARDFACECHECKCARD = "1801";//根据卡面号查卡号
	public final static String SCODE_CONTACTCHECKINGFILEQUERY = "6111";// 商户对账文件查询
	public final static String SCODE_CONTACTCHECKINGFILEDOWN = "6121";// 商户对账文件下载
	public final static String SCODE_ACCOUNTBALANCEQUERYS = "1021";// 余额查询  
	public final static String SCODE_ACCOUNTCONSUMS = "1601";// 账户转充 
	
	public static final String BatchNo = "000001";// 批次号
	public static final String MsgType = "0200";//消息类型
	public static final String ProcessingCode = "310000";// 交易处理码
	public static final String PosEntryModeCode = "051";// 服务点输入方式码
	public static final String CardSeqId = "000";// 卡片序列号
	public static final String PosCondCode = "00";// 服务点条件码
	public static final String CurrcyCodeTrans = "156";// 交易货币代码
	public static final String SecRelatdCtrlInfo = "2600000000000000";// 安全控制信息
	public static final String EmvVal = "0000000000";//IC卡数据域
	
	public static final String Method_BindingCard="BindingCards";//中石油 绑卡
	public static final String Method_GetCardNoInfo="GetCardInfoByTypeSs";//中石油 获取卡简要信息
	public static final String Method_GetOrderInfo="GetOrderInfos";//获得预充值信息
	public static final String Method_CardSpareMoneyRecharge="CardSpareMoneyRecharges";//获得预充值信息
	public static final String Method_OrederQuery="OrderQuerys";//查询充值状态接口
	public static final String Method_ReconQuery="ReconQuerys";//对账文件通知接口
	public static final String Method_DELCARDS="DelCards";//解绑

}
