
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
 
public class RPCClient {

	private static final String RPC_QUEUE_NAME = "saicOnlineCRMReq";

	private Connection connection;
	private Channel channel;
	private String replyQueueName;
	private QueueingConsumer consumer;

	// 创建用来测试的报文
	private String createMessage() {
		Element root = DocumentHelper.createElement("PACKAGE");
		Document document = DocumentHelper.createDocument(root);
		document.setXMLEncoding("gbk");
		// base域数据填充
		Element base = root.addElement("BASE");
		base.addElement("TXN_CODE").addText("1105");// 交易代码
		base.addElement("MSG_TYPE").addText("01");// 报文类型
		base.addElement("TXN_TIME").addText("20170809");// 交易时间
		base.addElement("REQSEQNO").addText("110");// 请求流水号
		base.addElement("RANDOM").addText("12345678");// 随机数
		base.addElement("CHANNEL").addText("1");// 渠道号
		base.addElement("ISSUER_ID").addText("123");// 机构ID
		base.addElement("TERM_ID").addText("4582");// 终端号
		base.addElement("MCHNT_CD").addText("110");// 商户号
		base.addElement("BRANCH_ID").addText("1234");
		// 公共域的数据填充
		Element data = root.addElement("DATA");
		/*
		 * data.addElement("msgHead").addText("msgHead");
		 * data.addElement("dataHead").addText("dataHead");
		 * data.addElement("txnType").addText("txnType");
		 * data.addElement("swtTxnDate").addText("201708");
		 * data.addElement("swtTxnTime").addText("201708");
		 * data.addElement("swtSettleDate").addText("201708");
		 * data.addElement("swtBatchNo").addText("swtBatchNo");
		 * data.addElement("swtFlowNo").addText("swtFlowNo");
		 * data.addElement("recTxnDate").addText("201708");
		 * data.addElement("recTxnTime").addText("201708");
		 * data.addElement("recBatchNo").addText("recBatchNo");
		 * data.addElement("recFlowNo").addText("recFlowNo");
		 * data.addElement("issChannel").addText("issChannel");
		 * data.addElement("innerMerchantNo").addText("inner");
		 * data.addElement("innerPosNo").addText("innerPosNo");
		 * data.addElement("track3").addText("track3");
		 */
		data.addElement("channel").addText("70000001");
		/*
		 * data.addElement("track2").addText("9602010200000000099=20022016870000000");
		 * data.addElement("pinTxn").addText("940BB65B5D151E81");
		 */
		data.addElement("cardNo").addText("9011111100000071474");
		/*
		 * data.addElement("cvv2").addText("cvv2");
		 * data.addElement("expDate").addText("201708");
		 * data.addElement("accType").addText("accType");
		 * data.addElement("txnAmount").addText("12");
		 * data.addElement("cardHolderFee").addText("cardHolderF");
		 * data.addElement("balance").addText("balance");
		 * data.addElement("pinQuiry").addText("pinQuiry");
		 * data.addElement("pinQuiryNew").addText("pinQuiryNew");
		 * data.addElement("pinTxnNew").addText("pinTxnNew");
		 * data.addElement("respCode").addText("res");
		 * data.addElement("authCode").addText("authCode");
		 * data.addElement("referenceNo").addText("referenceNo");
		 * data.addElement("oriSwtBatchNo").addText("oriSwtBa");
		 * data.addElement("oriSwtFlowNo").addText("oriSwtFlowNo");
		 * data.addElement("oriRecBatchNo").addText("oriRecBatch");
		 * data.addElement("oriRecFlowNo").addText("oriRecFlow");
		 * data.addElement("oriTxnAmount").addText("oriTxnAm");
		 * data.addElement("oriCardHolderFee").addText("oriCar");
		 * data.addElement("filepath").addText("filepath");
		 * data.addElement("resv1").addText("resv1");
		 * data.addElement("resv2").addText("resv2");
		 * data.addElement("resv3").addText("resv3");
		 * data.addElement("resv4").addText("resv4");
		 * data.addElement("saPackageNo").addText("saPackag");
		 * data.addElement("saResv5").addText("saResv5");
		 * data.addElement("saResv6").addText("saResv6");
		 * data.addElement("saResv7").addText("saResv7");
		 * data.addElement("otherData").addText("201708");
		 */
		root.addElement("MAC").addText("00000000");

		return root.asXML();
	}

	/**
	 * posp报文
	 * 
	 * @return
	 */
	private String createMessage2() {
		Element root = DocumentHelper.createElement("DATA");
		Document document = DocumentHelper.createDocument(root);
		document.setXMLEncoding("gbk");
		root.addElement("MSGTYPEID").addText("0200");
		root.addElement("CARDNO").addText("8712510000000000070");
		root.addElement("PROCCODE").addText("000000");
		root.addElement("TRANSAMT").addText("000000000001");
		root.addElement("SYSTRACENUM").addText("197009");
		root.addElement("POSENTRYCODE").addText("021");
		root.addElement("POSCONDCODE").addText("00");
		root.addElement("POSPINCODE").addText("12");
		root.addElement("TRACK2DATA").addText("8712510000000000070=20102018650000000");
		root.addElement("CARDACCTERMID").addText("10000027");
		root.addElement("CARDACCID").addText("897102754990001");
		root.addElement("TRANSCURRCODE").addText("156");
		root.addElement("PINDATA").addText("5E04B5FF7780B8BB");
		root.addElement("RESERV60").addText("22000002000601");
		root.addElement("MAC").addText("00000000");

		return root.asXML();
	}

	public RPCClient() {
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ所在主机ip或者主机名
		factory.setHost("10.250.3.85");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		// 创建一个连接
		try {
			connection = factory.newConnection();
			// 创建一个频道
			channel = connection.createChannel();

			// 声明队列
			channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null);

			// 为每一个客户端获取一个随机的回调队列
			replyQueueName = channel.queueDeclare().getQueue();
			// 为每一个客户端创建一个消费者（用于监听回调队列，获取结果）
			consumer = new QueueingConsumer(channel);
			// 消费者与队列关联
			channel.basicConsume(replyQueueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		channel.close();
		connection.close();

	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String call(String message) throws Exception {
		String response = null;
		// 设置replyTo和correlationId属性值
		// BasicProperties props = new
		// BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
		String corrId = java.util.UUID.randomUUID().toString();
		System.out.println("before:" + corrId);
		BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();
		// 发送消息到rpc_queue队列
		channel.basicPublish("", RPC_QUEUE_NAME, (com.rabbitmq.client.AMQP.BasicProperties) props, message.getBytes());
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response = new String(delivery.getBody(), "gbk");
				System.out.println("resp_corrId=" + delivery.getProperties().getCorrelationId());
				System.out.println("info:" + response);
				break;
			}
		}

		return response;
	}

	public static void main(String[] args) throws Exception {
		RPCClient client = new RPCClient();
		// String message =client.createMessage2();
//		BizMessageObj obj = createObj();
String P027Message="<?xml version=\"1.0\" encoding=\"gbk\"?><PACKAGE><BASE><TXN_CODE>P026</TXN_CODE><MSG_TYPE>1</MSG_TYPE><TXN_TIME>20180626110650</TXN_TIME><REQSEQNO>20180626110650000001</REQSEQNO><RANDOM>64604214</RANDOM><CHANNEL>1000000002</CHANNEL><ISSUER_ID>00000002</ISSUER_ID><TERM_ID>test1</TERM_ID><BRANCH_ID>111</BRANCH_ID><MCHNT_CD>201706290001</MCHNT_CD></BASE><DATA><area_code>53</area_code><saicOnlineBatchInfoDTO><user_id>990118</user_id><user_name>王鑫楷</user_name><mobile>13601860795</mobile><id_no>310109198310280864</id_no><id_type>1</id_type><id_validity>20230916</id_validity><birthday>19831028</birthday><gender>1</gender><occupation>6</occupation><nationality>CHN</nationality><address>上海市崇明县北门二村3号105室</address><card_product>1000</card_product></saicOnlineBatchInfoDTO><saicOnlineBatchInfoDTO><user_id>990217</user_id><user_name>胡玉君</user_name><mobile>13818866546</mobile><id_no>659001197001240514</id_no><id_type>1</id_type><id_validity>20261010</id_validity><birthday>19700124</birthday><gender>1</gender><occupation>6</occupation><nationality>CHN</nationality><address>上海市长宁区定西路9981号</address><card_product>1000</card_product></saicOnlineBatchInfoDTO></DATA><MAC>null</MAC></PACKAGE>";		
		String message = "<?xml version=\"1.0\" encoding=\"gbk\"?><PACKAGE><BASE><TXN_CODE>P027</TXN_CODE><MSG_TYPE>1</MSG_TYPE><TXN_TIME>20180626074646</TXN_TIME><REQSEQNO>22</REQSEQNO><RANDOM>24809120</RANDOM><CHANNEL>1000000002</CHANNEL><ISSUER_ID>00000002</ISSUER_ID><TERM_ID>test1</TERM_ID><BRANCH_ID>111</BRANCH_ID><MCHNT_CD>22</MCHNT_CD></BASE><DATA><defult>0</defult></DATA><MAC>null</MAC></PACKAGE>";
String 	P024Message="<?xml version=\"1.0\" encoding=\"gbk\"?><PACKAGE><BASE><TXN_CODE>P024</TXN_CODE><MSG_TYPE>1</MSG_TYPE><TXN_TIME>20180626105921</TXN_TIME><REQSEQNO>20180626105921000001</REQSEQNO><RANDOM>73327887</RANDOM><CHANNEL>1000000002</CHANNEL><ISSUER_ID>00000002</ISSUER_ID><TERM_ID>test1</TERM_ID><BRANCH_ID>111</BRANCH_ID><MCHNT_CD>201706290001</MCHNT_CD></BASE><DATA><user_id>990118</user_id><user_name>王鑫楷</user_name><mobile>13601860795</mobile><id_no>310109198310280864</id_no><id_type>1</id_type><id_validity>20230916</id_validity><birthday>19831028</birthday><gender>1</gender><occupation>6</occupation><nationality>CHN</nationality><address>上海市崇明县北门二村3号105室</address><area_code>53</area_code><card_product>1000</card_product></DATA><MAC>null</MAC></PACKAGE>";	
		client.call(message);
		client.close();

	}

}