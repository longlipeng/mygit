package com.webservice.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import com.webservice.util.JsonUtil;
import com.webservice.util.Singleton;



/**
 * Servlet implementation class ResultForPetroChina
 */
public class ResultForPetroChina extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultForPetroChina() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
		 String line = null;
		 StringBuilder sb = new StringBuilder(); 
		 while((line = br.readLine())!=null){
			 sb.append(line);
		 } 
		 String Str = sb.toString();
//		 try {
//			 String biaoshi=JsonUtil.getBiaoShi(Str);
//			 Singleton.getInstance().getMapStr().put(biaoshi, Str);
//			 
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
