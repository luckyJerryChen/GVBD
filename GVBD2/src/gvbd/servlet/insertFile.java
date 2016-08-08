package gvbd.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class insertFile {
	 public static void main(String[] args) throws IOException {  
		 String u=Class.class.getClass().getResource("/").getPath();
		  //str会得到这个函数所在类的路径  
		  String str = u.toString();
		  //截去一些前面6个无用的字符  
		  str=str.substring(1,str.length()); 
		  //将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）  
		  str=str.replaceAll("%20", " ");  
		  //查找“WEB-INF”在该字符串的位置  
		  int num = str.indexOf("WebRoot");  
		  //截取即可  
		  str=str.substring(0, num+"WebRoot".length());  
		  System.out.println(str);
	    }  
}
