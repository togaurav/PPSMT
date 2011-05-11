package com.ppstream.mt.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReceiveVerifyCode
 */
@WebServlet(description = "测试接收byte序列号", urlPatterns = { "/ReceiveVerifyCode.do" })
public class ReceiveVerifyCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiveVerifyCode() {
		super();
	}
	
	/**
	 * ERP:
	 * createtime (long),verifyCode,publicKey
	 * 传递：
	 * createtime = new Date().getTime()  long
	 * verifyCode = cipherText  公钥加密后post传输,string
	 * 
	 * other
	 * createtime (long),verifyCode,privateKey
	 * find privateKey by createtime
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String createtime = request.getParameter("createtime");
		String cipherTextStr = request.getParameter("cipherTextStr");
		/*** 此处应该是根据createtime查询记录。不排除createtime查询出多条记录，此处或许应迭代，再判断是否有与序列号相同的 **/
		String priKey = request.getParameter("priKey");      
		String newGuid = null;
		try {
			// 重新生成私钥
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpecPri = new PKCS8EncodedKeySpec(Codec.decodeBASE64(priKey));
			PrivateKey newPrivateKey = factory.generatePrivate(keySpecPri);
			// 使用私鈅解密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, newPrivateKey);
			byte[] newPlainText = cipher.doFinal(Codec.decodeBASE64(cipherTextStr));
			newGuid = new String(newPlainText, "UTF8");
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println("createtime:" + createtime);
		out.println("cipherTextStr:" + cipherTextStr);
		out.println("newGuid:" + newGuid);
		out.close();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
