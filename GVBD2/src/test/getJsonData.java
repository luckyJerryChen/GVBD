package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class getJsonData {
	public static void main(String[] args) throws IOException {
		int page=1,start=0,limit=15;
		String datafile = "D:\\Tomcat 7.0\\webapps\\GVBD\\data\\data56.json";
		String JsonContext = ReadFile(datafile);
		
		JSONArray jsonArray = JSONArray.fromObject(JsonContext);
		System.out.println(jsonArray);
        int size = jsonArray.size();
//        for(int  i = 0; i < size; i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            JSONArray jsonArray2 = JSONArray.fromObject(jsonObject.getString("public").toString());
//            int size1 = jsonArray2.size();
//            for(int  j = 0; j < size1; j++){
//                JSONObject jsonObject1 = jsonArray2.getJSONObject(j);
//                if(jsonObject1.getInt("version")==4){
//                    System.out.println("对外IPv4地址："+jsonObject1.getString("addr"));
//                }else{
//                    System.out.println("对外IPv6地址："+jsonObject1.getString("addr"));
//                }
//            }
//        }
		
		
		
		
		
		
		
	}
	public static String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
	
}
