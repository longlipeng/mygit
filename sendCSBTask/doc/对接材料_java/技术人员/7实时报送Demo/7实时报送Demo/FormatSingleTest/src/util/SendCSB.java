package util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpCallerException;
import com.alibaba.csb.sdk.HttpParameters;

public class SendCSB {
	// 本地调用CSB请求
	public static void sendCSBDevData(String uniqueNo, String symmetricKeyEncrpt, String jsonDataEncrypt, String url)
			throws IOException {
		URL url1 = new URL(url);
		URLConnection conn = url1.openConnection();
		HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
		httpUrlConnection.setDoInput(true);
		httpUrlConnection.setDoOutput(true);
		StringBuffer buffer = new StringBuffer();
		buffer.append("jsonDataEncrypt=").append(URLEncoder.encode(jsonDataEncrypt, "GBK")).append("&uniqueNo=")
				.append(URLEncoder.encode(uniqueNo, "GBK")).append("&symmetricKeyEncrpt=")
				.append(URLEncoder.encode(symmetricKeyEncrpt, "GBK"));
		byte[] data = buffer.toString().getBytes();
		httpUrlConnection.setRequestMethod("POST");
		httpUrlConnection.setRequestProperty("Content-Length", "" + data.length);
		httpUrlConnection.connect();
		OutputStream os = httpUrlConnection.getOutputStream();
		DataOutputStream dout = new DataOutputStream(os);
		dout.write(buffer.toString().getBytes());
		dout.flush();
		dout.write("\r\n".toString().getBytes());
		dout.flush();
		InputStream is = httpUrlConnection.getInputStream();
		byte[] buff = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = -1;
		while ((len = is.read(buff)) > -1) {
			bos.write(buff, 0, len);
		}
		String result = new String(bos.toByteArray());
		System.out.println("result:" + result);
	}

	// 测试环境调用CSB请求
	public static void sendCSBTestData(String uniqueNo, String symmetricKeyEncrpt, String jsonDataEncrypt, String api)
			throws HttpCallerException {
		// 测试环境CSB参数
		String ak = "e7ec2742cad4432d9700ecb8d56d28a0";
		String sk = "y8ONI4Ifx4iWreeOJGRBkK+Im2o=";
		String url = "http://101.132.39.129:8086/CSB";
		StringBuffer csbData = new StringBuffer("{\"dataMap\":");
		csbData.append("{\"uniqueNo\":\"").append(uniqueNo).append("\",");
		csbData.append("\"symmetricKeyEncrpt\":\"").append(symmetricKeyEncrpt).append("\",");
		csbData.append("\"jsonDataEncrypt\":\"").append(jsonDataEncrypt).append("\"}}");
		HttpParameters.Builder builder = new HttpParameters.Builder();
		builder.api(api).version("1.0.0").requestURL(url).method("POST").accessKey(ak).secretKey(sk)
				.putParamsMap("dataMap", csbData.toString());
		System.out.println("csbData:" + csbData.toString());
		String result = null;
		result = HttpCaller.invoke(builder.build());
		System.out.println("result==" + result);
	}

	// 生产环境调用CSB请求
	public static void sendCSBProductData(String uniqueNo, String symmetricKeyEncrpt, String jsonDataEncrypt,
			String api) throws HttpCallerException {
		// 生产环境CSB参数
		String ak = "40d34e4d03bb433796d7d6282410b4d9";
		String sk = "YMFZINeKOJeU3jcTVwf+lJw7cH4=";
		String url = "http://106.15.24.20:8086/CSB";
		StringBuffer csbData = new StringBuffer("{\"dataMap\":");
		csbData.append("{\"uniqueNo\":\"").append(uniqueNo).append("\",");
		csbData.append("\"symmetricKeyEncrpt\":\"").append(symmetricKeyEncrpt).append("\",");
		csbData.append("\"jsonDataEncrypt\":\"").append(jsonDataEncrypt).append("\"}}");
		HttpParameters.Builder builder = new HttpParameters.Builder();
		builder.api(api).version("1.0.0").requestURL(url).method("POST").accessKey(ak).secretKey(sk)
				.putParamsMap("dataMap", csbData.toString());
		System.out.println("csbData:" + csbData.toString());
		String result = null;
		result = HttpCaller.invoke(builder.build());
		System.out.println("result==" + result);
	}
}
