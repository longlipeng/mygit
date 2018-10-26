package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
/**
 * 文件处理类
 * @author taojiajun
 *
 */
public class FileUtil {
	/**
	 * 写文件
	 * @param file
	 * @param str
	 * @throws IOException
	 */
	public static void createFile(File file,String str) throws IOException  {
		OutputStream out=null;
		try{
		out=new FileOutputStream(file);
		byte[] words=str.getBytes();
		out.write(words);
		}catch (IOException e){
		throw e;
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	/**
	 * 追加文件内容
	 * @param file
	 * @param content
	 * @throws IOException 
	 */
	public static void appendFile(File file,String content) throws IOException {
		FileWriter fileWriter=null;
		try {
			fileWriter=new FileWriter(file,true);//以追加形式写文件
			fileWriter.write(content);
		} catch (IOException e) {			
			throw e;
		}finally{
			if(fileWriter!=null){
				fileWriter.close();
			}
		}
		
	}
}
