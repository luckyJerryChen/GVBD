package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

public class testyyj {

	static String getString(String str){
		int num=str.lastIndexOf('\\');
		int lastnum=str.indexOf('.');
		String str1 = str.substring(num+1, lastnum);
		return str1;
	}
	
	
	public  String getData() {
		
		File  f =  new File("../webapps/GVBD/data");
/*		int idx=f.getCanonicalFile().toString().indexOf(f.getCanonicalPath().toString().split("\\")[f.getCanonicalPath().toString().split("\\").length]);
		File f2=new File(f.getCanonicalPath().toString().substring(0,idx)+)
		System.out.println(f.exists());*/
//		try {
//			System.out.println(f.getCanonicalPath());
//			System.out.println(f.getAbsolutePath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		File[] tempList = f.listFiles();
		HashMap<Integer, String> hash = new HashMap<Integer ,String >();
		List list = new ArrayList();
		for(int i=0;i<tempList.length;i++){
			if(tempList[i].isFile()){
				if(!tempList[i].getName().startsWith("kmeans_")){
					hash.put(i, getString(tempList[i].getName()));
					list.add(getString(tempList[i].getName()));
					
				}
				
			}
		}
		String json =hashMapToJson(hash);
		JSONArray jsonArray2 = JSONArray.fromObject( list );
		//System.out.println(json);
		return json;
	}
    public static String hashMapToJson(HashMap map) {  
        String string = "{data:[";  
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {  
            Entry e = (Entry) it.next();  
            string += "{id:" + e.getKey() + ",";  
            string += "value:'" + e.getValue() + "'},";  
        }  
        string = string.substring(0, string.lastIndexOf(","));  
        string += "]}";  
        return string;  
    }  
	
	
//	public static void main(String[] args) {
//	  	getData();
//	}
}
