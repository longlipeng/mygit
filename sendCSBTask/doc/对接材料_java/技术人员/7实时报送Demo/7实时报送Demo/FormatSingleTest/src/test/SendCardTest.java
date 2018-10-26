package test;

import service.SendCardsService;

/**
 * 单笔时时报送DEMO
 * 
 * @author zhuang 2018下午3:12:49
 */
public class SendCardTest {
	public static void main(String[] args) throws Exception {
		SendCardsService sendcard = new SendCardsService();
		// 联网发行唯一标识
		String uniqueNo = "310104F5201889100033";
		// 私钥
		String submissionpass = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALI6G+lzFj1aGym4iq9JY53dyAFBRrRlvo8VnDhcIyMXrIko20lZIAL95Hvbk6Bs1suoURU0Y8+jAd/YH/jGHww6AXYljFC5PSf3lPd3YXoGrxk+iDZz60Q1K7xqsrtbHVQzySiXyMBjQ4tZbsIqD5c4UXkccp/JSwd4HNxZXiEFAgMBAAECgYEAgGzO4BFF3T+ogw+vH0/KsF63V/ApeqQ2A/SWdSYvS4IrmUoPeXL3VjCNC5LVdav3uxi2FImDwoK7PwkFQMXCaF97ZvAZ3S6x+D+aubkizc4b3TXQ84hwV6LGOMZjvnKXjNhUHd6gLc0OYGzziwkoLa1KcoxyZrZC5IbehlRoHwECQQDtPU7x/LajoC0yydjSzw9bBjIUI8t30pSHsgYq0YxR/WzDc+TVL2afzKeHPu8KQoaLXRhMBQGSCkBqJUSJGJ3ZAkEAwFIl1ohfy4uueeyt3Z6QvhUAORqZQCmvngxRsrFo2poS+w+2aOG1HG61AljOrNvnOs9XzLcQ4GwLIeZZC2PlDQJBAI5VBQMrwgvDMrrQ3NQFREoxGmR44T6/STtsNEUGOXCLYfCVnInGiYSADVaYDGQUa5I7RTN+oWWT3veP6mFyMmkCQQCLd4clCoSdsU/37yEuxBynG8eroZRdKV3HuZtNgMZPMMhu9LgNWxDh646sgwZt6JLI3TAIrwE4HmH8VXVhgzHJAkBlHu3Xm4ffPPEoO0ByBUEkS8VreRo6Eqt4WtRIPbXbQoL5W192XeR+CqFeeb8K+s3fzdjWt1q1b71vl20XGb/H";
		// sendCardsService为发卡；addRechargeService为充值；addConsumptionService为消费
		String api = "sendCardsService";
		// false为测试环境，ture为生产环境
		boolean isFormat = true;
		sendcard.card(uniqueNo, submissionpass, api, isFormat);
	}

}
