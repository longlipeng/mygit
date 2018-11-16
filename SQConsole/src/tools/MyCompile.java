package tools;   
  
import net.sf.jasperreports.engine.JRException;   
import net.sf.jasperreports.engine.JasperCompileManager;   
  
public class MyCompile {   
  
    /**  
     * @param args  
     * @throws Exception   
     *   
     * 使用JasperReport提供的JasperCompileManager工具把*.jrxml文件编译成*.jasper文件  
     *   
     */ 
    public static void main(String[] args){   
        compile();   
    }   
       
    public static void compile(){   
        try {   
            JasperCompileManager.compileReportToFile("D:\\T80212.jrxml",
            		"D:\\T80212.jasper");   
            System.out.println("成功编译成jasperReport文件 *.jasper");   
        } catch (JRException e) {   
            e.printStackTrace();
        }   
    }   
       
}  
