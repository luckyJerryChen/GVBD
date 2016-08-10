package gvbd.servlet;

import gvbd.config.DataConfig;
import gvbd.data.BSPNodeFormatImpl;
import gvbd.graph.GraphData;

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

public class importData extends HttpServlet {
	File tmpDir = null;// 初始化上传文件的临时存放目录
	File saveDir = null;// 初始化上传文件后的保存目录

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public importData() {
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
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setContentType("text/html;charset=utf-8");
		int maxPostSize = 1000 * 1024 * 1024;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
		servletFileUpload.setSizeMax(maxPostSize);
		List fileItems;
		try {
			fileItems = servletFileUpload.parseRequest(request);
			Iterator iter = fileItems.iterator();
			String numbervalue = "";
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {// 是文件
					String realPath1 = request.getSession().getServletContext()
							.getRealPath("");
					String imgPath = "/dataSimple/";
					String realPath = realPath1 + imgPath;
					String fileName = item.getName();
					File file = new File(realPath + fileName);
					
					if (file.exists()) {
						file.delete();
					}
					try {
						item.write(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					GraphData graphData = new GraphData();
					graphData.UndirectGraphTopK(realPath+fileName,realPath1,fileName.substring(0, fileName.lastIndexOf(".")));
					//System.out.println(numbervalue);
/*					DataConfig.setDataPath(realPath + fileName);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(
									DataConfig.getDataPath()), "utf-8"));
					DataConfig.setDataReader(br);
					DataConfig.setNodeFormat(new BSPNodeFormatImpl());
					DataConfig.setNodeNum(Integer.parseInt(numbervalue));*/
					PrintWriter out = response.getWriter();
					out.print("{success:true,msg:'" + imgPath + "',fileName:'"
							+ fileName + "'}");
					out.flush();
					out.close();

				} else {//识别的值
					numbervalue= item.getString();
					numbervalue = new String(numbervalue.getBytes());
				}

			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String nre =
		// request.getSession().getServletContext().getRealPath("/dataSimple/");
		//
		// String filepath = request.getParameter("uploadfile");//上传路径
		// System.out.println(filepath);
		// String number = request.getParameter("number");//顶点数目
		//
		// File file = new File(filepath);// 错误发生地
		// String filename = file.getName();// 获取上传的文件路径
		//
		// BufferedReader reader = new BufferedReader(new FileReader(file));
		// BufferedWriter writer = new BufferedWriter(new
		// FileWriter(nre+"/"+filename));
		// int index;
		// while ((index = reader.read()) != -1) {
		// writer.write(index);
		// }
		// reader.close();
		// writer.close();
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
