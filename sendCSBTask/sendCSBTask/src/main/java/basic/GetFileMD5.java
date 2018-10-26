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
 * ��ȡ�ļ�MD5ֵ     ���ѹ���������������޸ģ���Ҫ�������ļ������޸ģ��������ļ����޸Ķ�����ô�������ɵ�MD5ֵ��ͬ��
 *
 */
public class GetFileMD5 {
	//�����ļ�md5ֵ
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
