package com.ppstream.mt.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathParseException;

/**
 * A tool to wrap XML parsing and xpath access.
 * 
 * Two ways to create an object: using a byte array or an XML source file
 * 
 * Two ways to retrieve a string value or values: selectFirst() and selectAll.
 * 
 * If one would like to work on a subtree or a collection thereof ,
 * selectFirstXml() and selectAllXml() will help.
 * 
 * 
 * @author Bing Ran<bing_ran@hotmail.com>
 * 
 */
public class XmlGen {
	private VTDGen vg = new VTDGen();
	private VTDNav nav;
	private AutoPilot ap;
	private AutoPilot ap0;
	private byte[] ba;

	/**
	 * Construct an XmlGen from a byte array that contains the xml source
	 * 
	 * @param ba
	 */
	public XmlGen(byte[] ba) {
		vg.setDoc(ba);
		try {
			vg.parse(false);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		init();
	}

	public XmlGen(byte[] ba, int os, int len) {
		vg.setDoc(ba, os, len);
		try {
			vg.parse(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		init();
	}

	public XmlGen(String fileName) {
		vg.parseFile(fileName, false);
		init();
	}

	private void init() {
		nav = vg.getNav();
		ap = new AutoPilot();
		ap.bind(nav);
		ap0 = new AutoPilot();
		ap0.bind(nav);
		try {
			ap0.selectXPath(".");
		} catch (XPathParseException e) {
		}

		this.ba = nav.getXML().getBytes();
	}

	/**
	 * select the first node text value for an x path
	 * 
	 * @param xpathExpr
	 * @return
	 */
	public String selectFirst(String xpathExpr) {
		String ret = "";
		try {
			ap.selectXPath(xpathExpr);
			ret = ap.evalXPathToString();
			ap.resetXPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}

		return ret;
	}

	/**
	 * return the XML node of first match
	 * 
	 * @param xpathExpr
	 * @return
	 */
	public XmlGen selectFirstXml(String xpathExpr) {
		try {
			ap.selectXPath(xpathExpr);
			int p = ap.evalXPath();
			if (p != -1) {
				long l = nav.getElementFragment();
				int start = (int) l;
				int len = (int) ((l >> 32) & 0xffffffff);
				return new XmlGen(this.ba, start, len);
			}
			ap.resetXPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}
		return null;
	}

	/**
	 * select all the node text value for an x path
	 * 
	 * @param xpathExpr
	 * @return
	 */
	public List<String> selectAll(String xpathExpr) {
		List<String> ret = new ArrayList<String>();
		try {
			ap.selectXPath(xpathExpr);
			while (ap.evalXPath() != -1) {
				String e = ap0.evalXPathToString();
				ret.add(e);
			}
			ap.resetXPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}
		return ret;
	}

	public List<String> selectAllText(String xpathExpr) {
		List<String> ret = new ArrayList<String>();
		try {
			ap.selectXPath(xpathExpr);
			int i = -1;
			while ((i = ap.evalXPath()) != -1) {
				String e = nav.toString(i);
				ret.add(e);
			}
			ap.resetXPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}
		return ret;
	}

	public List<String> selectAllTextWithPreOrder(String xpathExpr) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<String> ret = new ArrayList<String>();
		try {
			ap.selectXPath(xpathExpr);
			int i = -1;
			while ((i = ap.evalXPath()) != -1) {
				map.put(i, nav.toString(i));
			}
			ap.resetXPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}
		Object[] array = map.keySet().toArray();
		Arrays.sort(array);
		for (Object obj : array) {
			ret.add(map.get(obj));
		}
		return ret;
	}

	/**
	 * 
	 */
	private void resetAp() {
		ap.resetXPath();
		ap0.resetXPath();
	}

	/**
	 * return the raw text of the node of first match
	 * 
	 * @param xpathExpr
	 * @return
	 */
	public List<XmlGen> selectAllXml(String xpathExpr) {
		List<XmlGen> ret = new ArrayList<XmlGen>();
		try {
			ap.selectXPath(xpathExpr);
			while (ap.evalXPath() != -1) {
				long l = nav.getElementFragment();
				int start = (int) l;
				int len = (int) ((l >> 32) & 0xffffffff);
				ret.add(new XmlGen(this.ba, start, len));
			}
			ap.resetXPath();
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			resetAp();
		}
	}

	/**
	 * recursively get all the text node from the current XML. Used with
	 * selectFirst(All)Xml(), it strips off tags from any part of an XML.
	 * 
	 * e.g.: allText = x.selectFirstXml("//b").getAllText();
	 * 
	 * @return
	 */
	public String getAllText() {
		List<String> all = selectAllText("//text()");
		StringBuilder sb = new StringBuilder();
		for (String s : all) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * @author mayan <xml>
	 *         <p>
	 *         1<span>2</span>3
	 *         </p>
	 *         </xml> text eg: allText =
	 *         x.selectFirstXml("//p").getAllTextWithPreOrder(); result: 123
	 */
	public String getAllTextWithPreOrder() {
		List<String> all = selectAllTextWithPreOrder("//text()");
		StringBuilder sb = new StringBuilder();
		for (String s : all) {
			sb.append(s);
		}
		return sb.toString();
	}
}
