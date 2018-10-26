package basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * 获取文件MD5值     如果压缩包最外层包名被修改，或要被检测的文件名被修改，单但是文件并无改动，那么他们生成的MD5值相同。
 *
 */
public class GetFileMD5 {
	//计算文件md5值
		public  String getFileMD5(String fileName) {
			File file =new File(fileName);
	        if (!file.isFile()) {  
	            return null;  
	        }          
	        MessageDigest digest = null;  
	        FileInputStream in = null;  
	        byte buffer[] = new byte[1024];  
	        int len;  
	        try {
	            digest = MessageDigest.getInstance("MD5"); 
	            in = new FileInputStream(file);  
	            while ((len = in.read(buffer, 0, 1024)) != -1) {  
	                digest.update(buffer, 0, len);  
	            }  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();
	            return null;
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();
	            return null;
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return null;
	        }  finally{
	        	if (in!=null){
	        		try{
	        			in.close();
	        		}catch(Exception e){
	        			return null;
	        		}
	        	}
	        }      
	        BigInteger bigInt = new BigInteger(1, digest.digest()); 
	        String fileMD5=bigInt.toString(16); 
	       System.out.println("file name +"+fileName+" md5:"+fileMD5);
	        return fileMD5;  
	    }  
}
