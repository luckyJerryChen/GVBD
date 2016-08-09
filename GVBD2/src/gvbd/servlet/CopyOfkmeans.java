package gvbd.servlet;

import gvbd.config.ChengLayoutConfig;
import gvbd.config.DataConfig;
import gvbd.config.FRLayoutConfig;
import gvbd.data.BSPNodeFormatImpl;
import gvbd.data.GraphData;
import gvbd.layout.ChengLayout;
import gvbd.layout.FRForceLayout;
import gvbd.layout.Layout;
import gvbd.util.Output;
import gvbd.util.kMeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CopyOfkmeans extends HttpServlet {
	File tmpDir = null;// 初始化上传文件的临时存放目录
	File saveDir = null;// 初始化上传文件后的保存目录

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public CopyOfkmeans() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		
		
		String nre = getServletContext().getRealPath("/data/");
		String nre2 = getServletContext().getRealPath("/kmeans/");

		
		System.out.println(request.getParameter("number"));// 文件名值
		System.out.println(request.getParameter("jsonname"));
		System.out.println(2323523523);
		
		int k=Integer.parseInt(request.getParameter("number"));
		String fileName=request.getParameter("jsonname");
		kMeans.doKmeans(k, nre+"\\"+fileName,nre2+"\\"+fileName);

	
	}

	/*
	 * response.setContentType("text/html"); PrintWriter out =
	 * response.getWriter(); out.println(
	 * "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
	 * out.println("<HTML>");
	 * out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
	 * out.println("  <BODY>"); out.print("    This is ");
	 * out.print(this.getClass()); out.println(", using the POST method");
	 * out.println("  </BODY>"); out.println("</HTML>"); out.flush();
	 * out.close();
	 */

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}