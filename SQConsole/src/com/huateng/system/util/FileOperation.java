package com.huateng.system.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperation {
 
 /**
  * 创建文件
  * @param fileName
  * @return
  */
 public static boolean createFile(File fileName)throws Exception{
  boolean flag=false;
  try{
   if(!fileName.exists()){
    fileName.createNewFile();
    flag=true;
   }
  }catch(Exception e){
   e.printStackTrace();
  }
  return true;
 } 
 
 /**
  * 读TXT文件内容
  * @param fileName
  * @return
  */
 public static String readTxtFile(File fileName)throws Exception{
  String result=null;
  FileReader fileReader=null;
  BufferedReader bufferedReader=null;
  try{
   fileReader=new FileReader(fileName);
   bufferedReader=new BufferedReader(fileReader);
   try{
    String read=null;
    while((read=bufferedReader.readLine())!=null){
     result=result+read+"\r\n";
    }
   }catch(Exception e){
    e.printStackTrace();
   }
  }catch(Exception e){
   e.printStackTrace();
  }finally{
   if(bufferedReader!=null){
    bufferedReader.close();
   }
   if(fileReader!=null){
    fileReader.close();
   }
  }
  System.out.println("读取出来的文件内容是："+"\r\n"+result);
  return result;
 }
 
 
 public static boolean writeTxtFile(String content,String  fileNameStr)throws Exception{
  RandomAccessFile mm=null;
  boolean flag=false;
  FileOutputStream o=null;
  File fileName = new File(fileNameStr);
  try {
	  if(!fileName.exists()){
		    fileName.createNewFile();
		   }
   o = new FileOutputStream(fileName);
      o.write(content.getBytes("GBK"));
      o.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
   flag=true;
  } catch (Exception e) {
   // TODO: handle exception
   e.printStackTrace();
  }finally{
   if(mm!=null){
    mm.close();
   }
  }
  return flag;
 }



public static void contentToTxt(String filePath, String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

//压缩单个文件
public static boolean zipSingleFile(String file, String zipFile)  
        throws IOException {  
    boolean bf = true;  
    File f = new File(file);  
    if (!f.exists()) {  
        System.out.println("文件不存在");  
        bf = false;  
    } else {  
        File ff = new File(zipFile);  
        if (!ff.exists()) {  
            ff.createNewFile();  
        }  
        // 创建文件输入流对象  
        FileInputStream in = new FileInputStream(file);  
        // 创建文件输出流对象  
        FileOutputStream out = new FileOutputStream(zipFile);  
        // 创建ZIP数据输出流对象  
        ZipOutputStream zipOut = new ZipOutputStream(out);  
        // 得到文件名称  
        String fileName = file.substring(file.lastIndexOf('/') + 1, file.length());  
        // 创建指向压缩原始文件的入口  
        ZipEntry entry = new ZipEntry(fileName);  
        zipOut.putNextEntry(entry);  
        // 向压缩文件中输出数据  
        int number = 0;  
        byte[] buffer = new byte[512];  
        while ((number = in.read(buffer)) != -1) {  
            zipOut.write(buffer, 0, number);  
        }  
        zipOut.close();  
        out.close();  
        in.close();  
    }  
    return bf;  
} 

//压缩多个文件
public static boolean zipFiles(String filesStr, String zipfile)  
        throws Exception {  
    boolean bf = true;  
    String[] files = filesStr.split(",");
    // 根据文件路径构造一个文件实例  
    File ff = new File(zipfile);  
    // 判断目前文件是否存在,如果不存在,则新建一个  
    if (!ff.exists()) {  
        ff.createNewFile();  
    }  
    // 根据文件路径构造一个文件输出流  
    FileOutputStream out = new FileOutputStream(zipfile);  
    // 传入文件输出流对象,创建ZIP数据输出流对象  
    ZipOutputStream zipOut = new ZipOutputStream(out);  

    // 循环待压缩的文件列表  
    for (int i = 0; i < files.length; i++) {  
        File f = new File(files[i]);  
        if (!f.exists()) {  
            bf = false;  
        }  
        try {  
            // 创建文件输入流对象  
            FileInputStream in = new FileInputStream(files[i]);  
            // 得到当前文件的文件名称  
            String fileName = files[i].substring(  
                    files[i].lastIndexOf('/') + 1, files[i].length());  
            // 创建指向压缩原始文件的入口  
            ZipEntry entry = new ZipEntry(fileName);  
            zipOut.putNextEntry(entry);  
            // 向压缩文件中输出数据  
            int nNumber = 0;  
            byte[] buffer = new byte[512];  
            while ((nNumber = in.read(buffer)) != -1) {  
                zipOut.write(buffer, 0, nNumber);  
            }  
            // 关闭创建的流对象  
            in.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            bf = false;  
        }  
    }  
    zipOut.close();  
    out.close();  
    return bf;  
}  

}