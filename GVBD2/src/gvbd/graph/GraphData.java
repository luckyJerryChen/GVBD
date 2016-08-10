package gvbd.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphData {

//	public static void main(String[] args) {
//		try {
//			String filename = "C:/Users/Administrator/Desktop/undirectwuminxia.txt";
//			InputStreamReader isr = new InputStreamReader(new FileInputStream(
//					filename), "utf-8");
//			BufferedReader br = new BufferedReader(isr);
//
//			List<String> keyList = new ArrayList<String>();
//			List<String> nodeCountList = new ArrayList<String>();
//
//			String line = "";
//			while ((line = br.readLine()) != null) {
//				String[] keyArr = line.split("\t");
//				String[] arr = line.split("\t")[1].split(" ");
//				String record = keyArr[0] + "\t" + arr.length;
//				nodeCountList.add(record);
//			}
//
//			Comparator comparator = new ComparatorListSort();
//			Collections.sort(nodeCountList, comparator);
//			Iterator it3 = nodeCountList.iterator();
//
//			FileOutputStream writerStream = new FileOutputStream(
//					"C:/Users/Administrator/Desktop/count1.json");
//			BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(
//					writerStream, "UTF-8"));
//			HashMap<String, Integer> hash = new HashMap<String, Integer>();
//			int counter = 0;
//			while (it3.hasNext()) {
//				String[] jsondata = it3.next().toString().split("\t");
//
//				if (Integer.parseInt(jsondata[1]) > 4) {
//					hash.put(jsondata[0], Integer.parseInt(jsondata[1]));
//				} else {
//					counter++;
//				}
//			}
//			hash.put("其他", counter);
//			String data = hashMapToJson(hash);
//			buff.write(data);
//			buff.close();
//			System.out.println("写入ok");
//			br.close();
//
//		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println("没有指定文件");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	public static String hashMapToJson(HashMap map) {
		String string = "{data:[";
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry e = (Entry) it.next();
			string += "{name:'" + e.getKey() + "',";
			string += "data:" + e.getValue() + "},";
		}
		string = string.substring(0, string.lastIndexOf(","));
		string += "]}";
		return string;
	}

	public static void UndirectGraphTopK(String pathin,String realPath, String filename) {
		try {
			//String filename = "C:/Users/Administrator/Desktop/undirectwuminxia.txt";
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					pathin), "utf-8");
			BufferedReader br = new BufferedReader(isr);

			List<String> keyList = new ArrayList<String>();
			List<String> nodeCountList = new ArrayList<String>();

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] keyArr = line.split("\t");
				String[] arr = line.split("\t")[1].split(" ");
				String record = keyArr[0] + "\t" + arr.length;
				nodeCountList.add(record);
			}

			Comparator comparator = new ComparatorListSort();
			Collections.sort(nodeCountList, comparator);
			Iterator it3 = nodeCountList.iterator();

			FileOutputStream writerStream = new FileOutputStream(realPath+"\\dataTopK\\"+filename+".json");
			BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(
					writerStream, "UTF-8"));
			HashMap<String, Integer> hash = new HashMap<String, Integer>();
			int counter = 0,number=0;
			while (it3.hasNext()) {
				String[] jsondata = it3.next().toString().split("\t");
				
				if (number<10) {
					hash.put(jsondata[0], Integer.parseInt(jsondata[1]));
					number++;
				} else {
					counter++;
				}
			}
			hash.put("其他", counter);
			String data = hashMapToJson(hash);
			buff.write(data);
			buff.close();
			System.out.println("写入ok");
			br.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("没有指定文件");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
