package com.huateng.common.XmlObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;



public class XStreamTest {
    private XStream xstream = null;
    private ObjectOutputStream  out = null;
    private ObjectInputStream in = null;
    
    private Student bean = null;

    public void init() {
        try {
            xstream = new XStream();
            //xstream = new XStream(new DomDriver()); // 需要xpp3 jar
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean = new Student();
        bean.setAddress("china");
        bean.setEmail("jack@email.com");
        bean.setId(1);
        bean.setName("jack");
        Birthday day = new Birthday();
        day.setBirthday("2010-11-22");
        bean.setBirthday(day);

    }
 
    public void destory() {
        xstream = null;
        bean = null;
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();

    }
    
    public final void fail(String string) {
        System.out.println(string);
    }
    
    public final void failRed(String string) {
        System.err.println(string);
    }
    
    public void writeList2XML() {
    	
        try {
            //修改元素名称
//            xstream.alias("beans", Student.class);
            xstream.alias("student", Student.class);
//            fail("----------List-->XML----------");
            Student listBean = new Student();
            listBean.setName("this is a List Collection");
            
            List<Object> list = new ArrayList<Object>();
            list.add(bean);
            list.add(bean);//引用bean
            //list.add(listBean);//引用listBean，父元素
            
            bean = new Student();
            bean.setAddress("china");
            bean.setEmail("tom@125.com");
            bean.setId(2);
            bean.setName("tom");
            Birthday day = new Birthday("2010-11-22");
            bean.setBirthday(day);
            
            list.add(bean);
//            listBean.setList(list);
            
            //将ListBean中的集合设置空元素，即不显示集合元素标签
            //xstream.addImplicitCollection(ListBean.class, "list");
            
            //设置reference模型
            //xstream.setMode(XStream.NO_REFERENCES);//不引用
            xstream.setMode(XStream.ID_REFERENCES);//id引用
            //xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);//绝对路径引用
              
            //将name设置为父类（Student）的元素的属性
            xstream.useAttributeFor(Student.class, "name");
            xstream.useAttributeFor(Birthday.class, "birthday");
            //修改属性的name
            xstream.aliasAttribute("姓名", "name");
            xstream.aliasField("生日", Birthday.class, "birthday");
          
            fail(xstream.toXML(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


