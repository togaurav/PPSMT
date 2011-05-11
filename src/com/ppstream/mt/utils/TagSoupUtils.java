package com.ppstream.mt.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class TagSoupUtils {

	private static Logger logger = LoggerFactory
			.getLogger(HttpClientUtils.class);

	public static String getTagSoupContentByUrl(String url, String encoding) {

		String source = HttpClientUtils.getContentByUrl(url);
		if (source == null)
			source = "";
		String xml = null;
		try {
			xml = parse(source, encoding);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return xml;
	}

	private static String parse(String src, String encoding)
			throws IOException, SAXException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		XMLWriter x = new XMLWriter(new OutputStreamWriter(os, encoding));
		x.setOutputProperty(XMLWriter.ENCODING, "UTF-8");

		InputSource s = new InputSource();
		s.setCharacterStream(new StringReader(src));
		s.setEncoding(encoding);

		XMLReader r = new Parser();
		r.setProperty(Parser.schemaProperty, new HTMLSchema());
		r.setFeature(Parser.ignorableWhitespaceFeature, true);
		r.setContentHandler(x);
		r.parse(s);
		return new String(os.toByteArray(), encoding);
	}

	// 测试
	private static void parseLaShouApi() {
		String content = getTagSoupContentByUrl("http://localhost:8080/PPSMT/ReadXML.do", "UTF-8");
		try {
			XmlGen x = new XmlGen(content.getBytes("UTF-8"));
			List<XmlGen> sites = x.selectAllXml("//urlset/url");
			for (XmlGen xg : sites) {
				String loc = xg.selectFirst("loc/text()");
				System.out.println(loc);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 传递公钥加密对象
	 * @param text 传输对象
	 */
	public static void transferEncryptText(String text){
		System.out.println("text:" + text);
		try {
			byte[] plainText = text.getBytes("UTF8");  
			
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			SecureRandom ran1 = new SecureRandom();
			// 创建‘密匙对’生成器
			kpg.initialize(1024,ran1);
			KeyPair kp = kpg.genKeyPair();
			
			PublicKey public_key = kp.getPublic(); // 获得公匙
			PrivateKey private_key = kp.getPrivate(); // 获得私匙
			// 保存在数据库当中
			String pubKey = Codec.encodeBASE64(public_key.getEncoded());  
			String priKey = Codec.encodeBASE64(private_key.getEncoded()); 
			// 用时取出转换，重新生成公钥
			KeyFactory factory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec  keySpecPub = new X509EncodedKeySpec(Codec.decodeBASE64(pubKey));
	        PublicKey newPublicKey = factory.generatePublic(keySpecPub);   
	        
			// 使用公鈅加密
			Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE,newPublicKey);
			byte[] cipherText=cipher.doFinal(plainText);               
			String cipherTextStr = Codec.encodeBASE64(cipherText);     
			
			// URL以及参数
			String postUrl = "http://localhost:8080/PPSMT/ReceiveVerifyCode.do";
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("createtime",String.valueOf(System.currentTimeMillis())));
			nvps.add(new BasicNameValuePair("cipherTextStr",cipherTextStr));
			nvps.add(new BasicNameValuePair("priKey",priKey));
			// 返回结果
			String result = HttpClientUtils.getInstance().post(postUrl, nvps);
			System.out.println("result:" + result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		transferEncryptText(RandomGUID.getInstance());
	}
	
	
}
