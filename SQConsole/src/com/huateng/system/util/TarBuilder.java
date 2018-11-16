package com.huateng.system.util;
 
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;
 
import java.io.*;
import java.util.logging.Logger;
 
/**
 * Created by superman on 2014/9/16.
 */
 
public class TarBuilder {
    public static TarArchiveOutputStream tarArchiveOutputStream;
    public static File source;
    public static File dist;
    public static boolean deleteSource;
 
    /**
     * 将指定目录下的文件打成tar包
     * @param srcDir        要压缩的目录
     * @param distDir       输出目录
     * @param tarName       tar包的名称
     * @param deleteSource  压缩后是否删除源文件
     */
    public TarBuilder(String srcDir,String distDir,String tarName,boolean deleteSource){
        source=new File(srcDir);
        dist = new File(distDir, tarName);
        this.deleteSource=deleteSource;
        if (source.exists()) {
            try {
                tarArchiveOutputStream = new TarArchiveOutputStream(new FileOutputStream(dist));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void build() {
        action(source);
        if (tarArchiveOutputStream != null) {
            try {
                tarArchiveOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void action(File file) {
        if(tarArchiveOutputStream==null){
            System.out.println(source.getName()+" not found.");
            return;
        }
        if (file.isFile()) {
            append(tarArchiveOutputStream,file);
        }else if (file.isDirectory()) {
            File[] files=file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    action(f);
                }
            }
        }
    }
 
    private static void append(TarArchiveOutputStream tarArchiveOutputStream,File file){
        InputStream is=null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            TarArchiveEntry entry = new TarArchiveEntry(file);
            entry.setSize(file.length());
            entry.setName(file.getAbsolutePath().substring(source.getAbsolutePath().length()+1));
            tarArchiveOutputStream.putArchiveEntry(entry);
            IOUtils.copy(is,tarArchiveOutputStream);
            tarArchiveOutputStream.flush();
            tarArchiveOutputStream.closeArchiveEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(is);
            if(deleteSource){
                if(!file.delete()){
                	System.out.println("Delete source file "+file.getName()+" failed.");
                }
            }
        }
    }
 
    public static void main(String[] args) {
//        TarBuilder tarBuilder = new TarBuilder("E:\\EStruts2", "E:\\", "tt.tar",true);
//        tarBuilder.build();
    }
}