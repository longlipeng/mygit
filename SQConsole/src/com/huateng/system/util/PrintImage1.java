package com.huateng.system.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrintImage1 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    doPost(request, response);
	  }

	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    response.setContentType("image/jpeg");
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Expires", "0");
	    response.setDateHeader("Expires", 0L);

	    System.out.println("PrintImage1 invoked");
	    
	    String fileName = request.getParameter("fileName");
	    fileName.replace("\\", "/");
	    File file = new File(fileName);

	    InputStream fileInputStream = null;
	    if (!file.exists())
	      fileInputStream = request.getSession().getServletContext().getResourceAsStream("/ext/resources/images/nopic.gif");
	    else {
	      fileInputStream = new FileInputStream(file);
	    }

	    BufferedImage bufferedImage = ImageIO.read(file);
	    BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
	    JPEGImageEncoder imageEncoder = JPEGCodec.createJPEGEncoder(outputStream);
	    JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
	    encodeParam.setQuality(1.0F, true);
	    imageEncoder.encode(bufferedImage, encodeParam);
	    
	    byte[] data = new byte[8192];

	    int len = -1;

	    while ((len = fileInputStream.read(data, 0, 8192)) != -1) {
	      outputStream.write(data, 0, len);
	    }

	    outputStream.flush();
	    outputStream.close();
	    fileInputStream.close();
	  }
}
