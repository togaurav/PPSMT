package com.ppstream.mt.utils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.tool.hbm2x.StringUtils;

/**
 * Servlet implementation class Log4jInitServlet
 */
@WebServlet(urlPatterns={"/Log4jInitServlet"},initParams={@WebInitParam(name="log4jLocation",value="/WEB-INF/log4j.properties")}, 
		asyncSupported = false,loadOnStartup=0)
public class Log4jInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log4jInitServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {

		String root = config.getServletContext().getRealPath("/");
		String log4jLocation = config.getInitParameter("log4jLocation");
		System.setProperty("webRoot", root);
		if (!StringUtils.isEmpty(log4jLocation)) {
			PropertyConfigurator.configure(root + log4jLocation);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
