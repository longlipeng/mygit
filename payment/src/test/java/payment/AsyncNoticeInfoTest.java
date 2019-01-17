package payment;

import org.junit.Test;

import com.allinfinance.biz.payment.entity.AsyncNoticeInfo;
import com.thoughtworks.xstream.XStream;
import com.util.Decoder;

public class AsyncNoticeInfoTest {

	
	@Test
	public void test() {
		String msg="<DATA><app_id>897102742140001</app_id><out_trade_no>pre_201808020000109467</out_trade_no><total_amount>0.01</total_amount><card_no>9702021100000070867</card_no><tran_code>00002001</tran_code><create_time>2018-07-30 11:40:08</create_time><trade_status>trade_fail</trade_status></DATA>";
		AsyncNoticeInfo info = (AsyncNoticeInfo) Decoder.parseXml(new XStream(), msg,"DATA",AsyncNoticeInfo.class);
		System.out.println(info);
	}
}
