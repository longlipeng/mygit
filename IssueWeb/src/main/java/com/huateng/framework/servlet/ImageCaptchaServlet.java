package com.huateng.framework.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成登录页面验证码图片
 * 
 * @author liming.feng
 *
 */
public class ImageCaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 4537700975395296949L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		
		ServletOutputStream responseOutputStream = httpServletResponse
				.getOutputStream();
		try {
			int width = 60, height = 20;
			BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);

			// 获取图形上下文 
			Graphics g = image.getGraphics();

			Random random = new Random();

			g.setColor(new Color(206,237,239));
			g.fillRect(0, 0, width, height);
		
			g.setFont(new Font("Times New Roman", Font.LAYOUT_NO_START_CONTEXT, 18));

			g.setColor(new Color(1));
			g.drawRect(0, 0, width - 1, height - 1);

			// 取随机产生的认证码 (4 位数字 ) 
			String sRand = "";
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(random.nextInt(10));
				sRand += rand;
			
				g.setColor(new Color(20 + random.nextInt(110), 20 + random
								.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(rand, 13 * i + 6, 16);
			}

			httpServletRequest.getSession().setAttribute("randCode", sRand);
			// 图象生效 
			g.dispose();
			ImageIO.write(image, "png", httpServletResponse.getOutputStream());
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (Exception e) {
			httpServletResponse
					.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		// 刷新response
		httpServletResponse.setHeader("Cache-Control",
				"no-store,private,post-check=0,pre-check=0,max-age=0");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", -1);
		httpServletResponse.setContentType("image/png");
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
