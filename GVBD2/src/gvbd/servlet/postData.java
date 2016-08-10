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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class postData extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
		doPost(request, response);
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nre = getServletContext().getRealPath("/data/");
		String nre2 = getServletContext().getRealPath("/dataSimple/");
		System.out.println(request.getParameter("kvalue"));// k值
		System.out.println(request.getParameter("title"));// 布局方式
		System.out.println(request.getParameter("speed"));// 速度值
		System.out.println(request.getParameter("isDirected"));// 是否是有向图
		System.out.println(request.getParameter("cool"));// cool值
		System.out.println(request.getParameter("forceThreshold"));// forceThreshold值
		System.out.println(request.getParameter("temperature"));// temperature值
		System.out.println(request.getParameter("deep"));// deep值
		System.out.println(request.getParameter("filename"));// 文件名值
		System.out.println(request.getParameter("times"));
		
		System.out.println(request.getParameter("filenumber"));
		
		
		DataConfig.setDataPath(nre2+"\\"+request.getParameter("filename")+".txt");
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						DataConfig.getDataPath()), "utf-8"));
		DataConfig.setDataReader(br);
		DataConfig.setNodeFormat(new BSPNodeFormatImpl());
		DataConfig.setNodeNum(Integer.parseInt(request.getParameter("filenumber")));
		
		
		
		float k = Float.parseFloat(request.getParameter("kvalue"));
		String layoutMethod = request.getParameter("title");

		if (layoutMethod.equals("ChengLayout")) {
			ChengLayoutConfig layoutConfig = new ChengLayoutConfig();
			if (request.getParameter("isDirected").equals("false")) {
				layoutConfig.setDirected(false);
			} else if (request.getParameter("isDirected").equals("true")) {
				layoutConfig.setDirected(true);
			} else {
				System.out.println("没有这种选择");
			}
			
			layoutConfig.setLayoutByTimes(true);
			
/*			layoutConfig.setDirected(false);
			layoutConfig.setHeight(5000);
			layoutConfig.setK(1);
			
			layoutConfig.setWidth(5000);
			layoutConfig.setDeep(3);
			layoutConfig.setTimes(200);
			layoutConfig.setCool(0.95f);
			layoutConfig.setTemperature(140);*/

			
			
			
			layoutConfig.setWidth(5000);
			layoutConfig.setHeight(5000);
			layoutConfig.setLayoutByTimes(true);
			layoutConfig.setK(Float.parseFloat(request.getParameter("kvalue")));
			layoutConfig
					.setDeep(Integer.parseInt(request.getParameter("deep")));
			layoutConfig.setTimes(Integer.parseInt(request
					.getParameter("times")));
			layoutConfig
					.setCool(Float.parseFloat(request.getParameter("cool")));
			layoutConfig.setTemperature(Integer.parseInt(request
					.getParameter("temperature")));

			GraphData graphData = new GraphData();
			
			graphData.loadNodeData(DataConfig.getDataReader(),
					DataConfig.getNodeFormat(), DataConfig.getNodeNum());

			Layout layout = new ChengLayout(graphData.getGraph(), layoutConfig);
			layout.doLayout();
			
			Output.outputJson(graphData.getGraph(),nre+"\\"+request.getParameter("filename")+".json");
		}
		if (layoutMethod.equals("FRLayout")) {
			 
			System.out.println("FRLayout");
			FRLayoutConfig layoutConfig = new FRLayoutConfig();
			
			
			layoutConfig.setLayoutByTimes(true);
			if (request.getParameter("isDirected").equals("false")) {
				layoutConfig.setDirected(false);
			} else if (request.getParameter("isDirected").equals("true")) {
				layoutConfig.setDirected(true);
			} else {
				System.out.println("没有这种选择");
			}
			layoutConfig.setWidth(5000);
			layoutConfig.setHeight(5000);
			layoutConfig.setLayoutByTimes(true);
			layoutConfig.setK(Float.parseFloat(request.getParameter("kvalue")));
	
			layoutConfig.setTimes(Integer.parseInt(request
					.getParameter("times")));
			layoutConfig
					.setCool(Float.parseFloat(request.getParameter("cool")));
			layoutConfig.setTemperature(Integer.parseInt(request
					.getParameter("temperature")));

			GraphData graphData = new GraphData();
			graphData.loadNodeData(DataConfig.getDataReader(),
					DataConfig.getNodeFormat(), DataConfig.getNodeNum());

			Layout layout = new FRForceLayout(graphData.getGraph(), layoutConfig);
			layout.doLayout();
			
			Output.outputJson(graphData.getGraph(),nre+"\\"+request.getParameter("filename")+".json");
			System.out.println("FRLayout--end");
			
		}
	}

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
