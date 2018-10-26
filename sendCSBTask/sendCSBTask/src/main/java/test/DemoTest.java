package test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class DemoTest {
	public static void main(String[] args) { //replaceAll("[\r\n]","")
		System.out.println("20170402".substring(0, 4));
		System.out.println("20170402".substring(4, 6));
		String validDate="20170402";
		validDate=validDate.substring(0,4)+"/"+validDate.substring(4,6)+"/"+validDate.substring(6,8);
		System.out.println(validDate);
		//URL class_location = this.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println(System.getProperty("user.dir"));
	}

}
