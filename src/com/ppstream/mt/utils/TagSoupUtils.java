package com.ppstream.mt.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class TagSoupUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	public static String getTagSoupContentByUrl(String url,String encoding) {

		String source = HttpClientUtils.getContentByUrl(url);
		if (source == null)
			source = "";
		String xml = null;
		try {
			xml = parse(source,encoding);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return xml;
	}

	private static String parse(String src, String encoding) throws IOException, SAXException {
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
	
	public static void main(String args[]){
		String content = getTagSoupContentByUrl("http://localhost:8080/PPSMT/ReadXML.do","UTF-8");
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
