package gvbd.servlet;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DefaultFileItemFactory;  
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;  
import org.apache.commons.fileupload.FileItemIterator;  
import org.apache.commons.fileupload.FileItemStream;  
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;  
import org.apache.commons.fileupload.util.Streams;  
public class importData extends HttpServlet {
	 File tmpDir = null;//初始化上传文件的临时存放目录  
	 File saveDir = null;//初始化上传文件后的保存目录  
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	public importData(){
		super();
	}
     
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  request.setCharacterEncoding("utf-8");  //设置编码  
          
	        //获得磁盘文件条目工厂  
//	        DiskFileItemFactory factory = new DiskFileItemFactory();  
//	        //获取文件需要上传到的路径  
//	        @SuppressWarnings("deprecation")
//			String path = request.getRealPath("/upload");  
//	         System.out.println(path);
//	        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，  
//	        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同  
//	        /** 
//	         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，  
//	         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的  
//	         * 然后再将其真正写到 对应目录的硬盘上 
//	         */  
//	        factory.setRepository(new File(path));  
//	        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
//	        factory.setSizeThreshold(1024*1024) ;  
//	          
//	        //高水平的API文件上传处理  
//	        ServletFileUpload upload = new ServletFileUpload(factory);  
//	        List<FileItem> list;
//			try {
//				list = (List<FileItem>)upload.parseRequest(request);
//				 System.out.println(list);
//			} catch (FileUploadException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//           
	        
	        
	        
	        
	    String filepath=request.getParameter("uploadfile");
	    
	    File file=new File(filepath);//错误发生地
      String filename = file.getName();//获取上传的文件路径  
      System.out.println(filename);


	
	    BufferedReader reader=new BufferedReader(new FileReader(file));
	    BufferedWriter writer=new BufferedWriter(new FileWriter("/dataSimple/"+filename));
	    int index;
	    while((index=reader.read ())!=-1){
	      writer.write (index);
	    }
	    reader.close();
	    writer.close();
	}
	    

		/*response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();*/


	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
