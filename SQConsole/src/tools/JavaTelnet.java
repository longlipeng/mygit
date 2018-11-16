package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

public class JavaTelnet    {  
    private static TelnetClient tc = null;  
    private InputStream in;  
    private PrintStream out;  
    public static void main(String args[]){   
        new JavaTelnet("192.168.1.179",23,"posp","posp@ips.0420");  
    }  
    public JavaTelnet(String host, int port,String username,String password) {  
        if(intconnect(host,port)){ 
        	writescript("top");
//            writescript("sh /home/posp/online/sbin/see");  
            closeconnect();  
        }  
    }  
  
    private void writescript(String filename) {  
        try {  
            if(new File(filename).exists()){  
                FileReader f = new FileReader(filename);  
                BufferedReader reader = new BufferedReader(f);  
                String command = "";  
                while(command !=null){  
                    command = reader.readLine();  
                    write(command);  
                }  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    
    public  void  write(String command) {  
        try{  
            System.out.println("command:>>>>>>>>>"+command);  
            out.println(command);  
            out.flush();  
            printresponse();  
            //for(int i=0;i<800000000;i++);  
            //应该使用线程同步使程序在接受到对方相应后再执行下一条命令这里使用for循环来模拟对方的相应时间     
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
  
    private boolean intconnect(String host, int port) {  
        try {  
            tc = new TelnetClient();  
            TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler(  
                    "VT100", false, false, true, false);  
            EchoOptionHandler echoopt = new EchoOptionHandler(true, false,  
                    true, false);  
            SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true,  
                    true, true, true);  
  
            tc.addOptionHandler(ttopt);  
            tc.addOptionHandler(echoopt);  
            tc.addOptionHandler(gaopt);  
            tc.connect(host, port);  
            in = tc.getInputStream();  
            out = new PrintStream(tc.getOutputStream());  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            try {  
                tc.disconnect();  
                in.close();  
                out.close();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            return false;  
        }         
    }  
    private void closeconnect(){  
        try {  
            tc.disconnect();  
            in.close();  
            //out.close();  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
    }  
    private String printresponse(){  
        try {  
            byte[] buff = new byte[1024];  
            int ret_read = 0;  
            do {  
                ret_read = in.read(buff); 
                System.out.println("length:"+ret_read);
                String a = new String(buff, 0, ret_read);  
                System.out.print(a);
                if (a.endsWith(":")|a.endsWith(">")|a.endsWith("]")) {  
                    System.out.print(a);  
                    return null; 
                }  
            } while (ret_read >= 0);  
        } catch (Exception e) {  
            System.err.println("Exception while reading socket:"  + e.getMessage());  
        }  
        try {  
            tc.disconnect();  
        } catch (Exception e) {  
            System.err.println("Exception while closing telnet:"  + e.getMessage());  
        }  
        return null;  
    }  
}  
