package org.gxz.znrl.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.gxz.znrl.common.baseaction.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/** 
 * @description: 验证码
 * @version 1.0
 * @author vincent
 * @createDate 2014-1-25;上午11:02:48
 */
@Controller  
@RequestMapping("/") 
public class CaptchaController   extends BaseAction {

	@Autowired
	private Producer captchaProducer;
	
	@RequestMapping("/captcha")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		 HttpSession session = request.getSession();
//         String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
//         System.out.println("******************验证码是: " + code);

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
        ImageIO.write(bi, "png", out);
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
        //encoder.encode(bi);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

}
