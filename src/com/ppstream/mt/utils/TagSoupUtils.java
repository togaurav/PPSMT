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
	
}
