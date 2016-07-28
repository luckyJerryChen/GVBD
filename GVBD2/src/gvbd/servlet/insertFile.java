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
		 System.out.println(u);
		//str会得到这个函数所在类的路径  
		  String str = u.toString();
		  System.out.println(str);
		//截去一些前面6个无用的字符  
		  str=str.substring(1,str.length()); 
		  System.out.println(str);
		//将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）  
		  str=str.replaceAll("%20", " ");  
		  System.out.println(str);
		//查找“WEB-INF”在该字符串的位置  
		  int num = str.indexOf("WebRoot");  
		  System.out.println(num);
		  //截取即可  
		  str=str.substring(0, num+"WebRoot".length());  
		  System.out.println(str);
		 
		 
		 
		 
		 
		 
		 
//         
//	        File file = new File(str+"/json.json");  
//	     //   String jsonStr = "[{a:1,b:{b1:[{a:2},{a:1}]},c:3},{a:1},{b:1},{s:1}]";  
//	      String jsonStr = "{nodes:[{name:1,value:0.0,cy:865.9056171793043,cx:563.7841315851558},{name:2,value:0.0,cy:544.799639010535,cx:877.9846633757394},{name:3,value:0.0,cy:457.15497859587066,cx:146.03362296833893},{name:4,value:0.0,cy:1001.9673094972259,cx:1024.0},{name:5,value:0.0,cy:0.0,cx:0.0},{name:6,value:0.0,cy:136.03335381573208,cx:460.24044125210713}],links:[{value:0.0,y1:865.9056171793043,y2:544.799639010535,x2:877.9846633757394,x1:563.7841315851558},{value:0.0,y1:865.9056171793043,y2:457.15497859587066,x2:146.03362296833893,x1:563.7841315851558},{value:0.0,y1:865.9056171793043,y2:1001.9673094972259,x2:1024.0,x1:563.7841315851558},{value:0.0,y1:544.799639010535,y2:865.9056171793043,x2:563.7841315851558,x1:877.9846633757394},{value:0.0,y1:544.799639010535,y2:1001.9673094972259,x2:1024.0,x1:877.9846633757394},{value:0.0,y1:544.799639010535,y2:136.03335381573208,x2:460.24044125210713,x1:877.9846633757394},{value:0.0,y1:457.15497859587066,y2:865.9056171793043,x2:563.7841315851558,x1:146.03362296833893},{value:0.0,y1:457.15497859587066,y2:0.0,x2:0.0,x1:146.03362296833893},{value:0.0,y1:457.15497859587066,y2:136.03335381573208,x2:460.24044125210713,x1:146.03362296833893},{value:0.0,y1:1001.9673094972259,y2:865.9056171793043,x2:563.7841315851558,x1:1024.0},{value:0.0,y1:1001.9673094972259,y2:544.799639010535,x2:877.9846633757394,x1:1024.0},{value:0.0,y1:0.0,y2:457.15497859587066,x2:146.03362296833893,x1:0.0},{value:0.0,y1:0.0,y2:136.03335381573208,x2:460.24044125210713,x1:0.0},{value:0.0,y1:136.03335381573208,y2:544.799639010535,x2:877.9846633757394,x1:460.24044125210713},{value:0.0,y1:136.03335381573208,y2:457.15497859587066,x2:146.03362296833893,x1:460.24044125210713},{value:0.0,y1:136.03335381573208,y2:0.0,x2:0.0,x1:460.24044125210713}]}";  
////	      String jsonStr1 = "{nodes:[{name:1,value:0.0,cy:865.9056171793043,cx:563.7841315851558},{name:2,value:0.0,cy:544.799639010535,cx:877.9846633757394},{name:3,value:0.0,cy:457.15497859587066,cx:146.03362296833893},links:[{value:0.0,y1:865.9056171793043,y2:544.799639010535,x2:877.9846633757394,x1:563.7841315851558}]}";  
//	      
////	        JSONArray jsonObj = JSONArray.fromObject(jsonStr);  
//          JSONObject jsonObj = JSONObject.fromObject(jsonStr);  
//	          
//	        char[] stack = new char[1024]; // 存放括号，如 "{","}","[","]"  
//	        int top = -1;  
//	          
//	        String string = jsonObj.toString();  
//	        StringBuffer sb = new StringBuffer();  
//	        char[] charArray = string.toCharArray();  
//	        for (int i = 0; i < charArray.length; i++) {  
//	            char c = charArray[i];  
//	            if ('{' == c || '[' == c) {  
//	                stack[++top] = c; // 将括号添加到数组中，这个可以简单理解为栈的入栈  
//	                sb.append(charArray[i] + "\n");  
//	                for (int j = 0; j <= top; j++) {  
//	                    sb.append("\t");  
//	                }  
//	                continue;  
//	            }  
//	            if ((i + 1) <= (charArray.length - 1)) {  
//	                char d = charArray[i+1];  
//	                if ('}' == d || ']' == d) {  
//	                    top--; // 将数组的最后一个有效内容位置下标减 1，可以简单的理解为将栈顶数据弹出  
//	                    sb.append(charArray[i] + "\n");  
//	                    for (int j = 0; j <= top; j++) {  
//	                        sb.append("\t");  
//	                    }  
//	                    continue;  
//	                }  
//	            }  
//	            if (',' == c) {  
//	                sb.append(charArray[i] + "\n");  
//	                for (int j = 0; j <= top; j++) {  
//	                    sb.append("\t");  
//	                }  
//	                continue;  
//	            }  
//	            sb.append(c);  
//	        }  
//	          
//	        Writer write = new FileWriter(file);  
//	        write.write(string.toString());  
//	        write.flush();  
//	        write.close();  
	    }  

	
	
}
