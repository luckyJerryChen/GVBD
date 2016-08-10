package gvbd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class kMeans {
	public static String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
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


	
	/**
	 * @param args
	 * @throws IOException
	 */

	public static List<ArrayList<ArrayList<Double>>> initHelpCenterList(
			List<ArrayList<ArrayList<Double>>> helpCenterList, int k) {
		for (int i = 0; i < k; i++) {
			helpCenterList.add(new ArrayList<ArrayList<Double>>());
		}
		return helpCenterList;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void doKmeans(int k,String filePath,String outFilePath) throws IOException {

		List<ArrayList<Double>> centers = new ArrayList<ArrayList<Double>>();
		List<ArrayList<Double>> newCenters = new ArrayList<ArrayList<Double>>();
		List<ArrayList<ArrayList<Double>>> helpCenterList = new ArrayList<ArrayList<ArrayList<Double>>>();

		// 读入原始数据
/*		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("kmeanstestdata.txt")));*/
		String data = null;
		List<ArrayList<Double>> dataList = new ArrayList<ArrayList<Double>>();
		
		
		
		

		// TODO Auto-generated method stub
		String JsonContext = ReadFile(filePath);
		JSONObject jsonObject2 = JSONObject.fromObject(JsonContext);
		//JSONObject jo=new JSONObject(JsonContext); 
		System.out.println(jsonObject2.get("nodes"));
		JSONArray jsonArray = (JSONArray)jsonObject2.get("nodes");
		int size = jsonArray.size();
		System.out.println("Size: " + size);
		Map<String,JSONObject> map=new HashMap<String,JSONObject>();
		for (int i = 0; i < size; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			System.out.println("[" + i + "]name=" + jsonObject.get("name"));
			System.out.println("[" + i + "]value="
					+ jsonObject.get("value"));
			System.out.println("[" + i + "]cx="
					+ jsonObject.get("cx"));
			System.out.println("[" + i + "]cy="
					+ jsonObject.get("cy"));
			
			String[] fields = new String[2];
			fields[0]=Double.toString(Double.parseDouble(jsonObject.get("cx").toString()));
			fields[1]=Double.toString(Double.parseDouble(jsonObject.get("cy").toString()));
		
			map.put(fields[0]+" "+fields[1],jsonObject);
			System.out.println(fields[0]+" "+fields[1]);
			List<Double> tmpList = new ArrayList<Double>();
			for (int j = 0; j < fields.length; j++)
				tmpList.add(Double.parseDouble(fields[j]));
			dataList.add((ArrayList<Double>) tmpList);
		}
	
		
		
		
		
		
		
/*		while ((data = br.readLine()) != null) {
			System.out.println(data);
			String[] fields = data.split(" ");
			List<Double> tmpList = new ArrayList<Double>();
			for (int i = 0; i < fields.length; i++)
				tmpList.add(Double.parseDouble(fields[i]));
			dataList.add((ArrayList<Double>) tmpList);
		}
		br.close();*/

		// 随机确定K个初始聚类中心
		Random rd = new Random();

/*		int[] initIndex = { 59, 71, 48, 50 };
		int[] helpIndex = { 0, 59, 13, 40 };
		int[] givenIndex = { 0, 1, 2, 10 };*/
		rd.nextInt(size/k);
		int[] initIndex = new int[size];
		int[] helpIndex = new int[size];
		int lastrandom=0;
		for(int i=0;i<size;++i){
			initIndex[i]=rd.nextInt(size/k)+1;
			//System.out.println("size/k"+size/k);
			System.out.println("8888"+initIndex[i]);
			System.out.println("8888"+helpIndex[i]);
			helpIndex[i]=lastrandom;
			lastrandom=initIndex[i];
		}

		
		
		System.out.println("random centers' index");
		for (int i = 0; i < k; i++) {
			int index = rd.nextInt(initIndex[i]) + helpIndex[i];
			// int index = givenIndex[i];
			System.out.println("index " + index);
			centers.add(dataList.get(index));
			helpCenterList.add(new ArrayList<ArrayList<Double>>());
		}

		/*
		 * //注释掉的这部分目的是，取测试数据集最后稳定的三个类簇的聚类中心作为初始聚类中心 centers = new
		 * ArrayList<ArrayList<Double>>(); for(int i=0;i<59;i++)
		 * helpCenterList.get(0).add(dataList.get(i)); for(int i=59;i<130;i++)
		 * helpCenterList.get(1).add(dataList.get(i)); for(int i=130;i<178;i++)
		 * helpCenterList.get(2).add(dataList.get(i)); for(int i=0;i<k;i++){
		 * 
		 * ArrayList<Double> tmp = new ArrayList<Double>();
		 * 
		 * for(int j=0;j<dataList.get(0).size();j++){ double sum=0; for(int
		 * t=0;t<helpCenterList.get(i).size();t++)
		 * sum+=helpCenterList.get(i).get(t).get(j);
		 * tmp.add(sum/helpCenterList.get(i).size()); } centers.add(tmp); }
		 */

		// 输出k个初始中心
		System.out.println("original centers:");
		for (int i = 0; i < k; i++)
			System.out.println(centers.get(i));

		while (true) {// 进行若干次迭代，直到聚类中心稳定

			for (int i = 0; i < dataList.size(); i++) {// 标注每一条记录所属于的中心
				double minDistance = 99999999;
				int centerIndex = -1;
				for (int j = 0; j < k; j++) {// 离0~k之间哪个中心最近
					double currentDistance = 0;
					for (int t = 0; t < centers.get(0).size(); t++) {// 计算两点之间的欧式距离
						currentDistance += ((centers.get(j).get(t) - dataList
								.get(i).get(t)) / (centers.get(j).get(t) + dataList
								.get(i).get(t)))
								* ((centers.get(j).get(t) - dataList.get(i)
										.get(t)) / (centers.get(j).get(t) + dataList
										.get(i).get(t)));
					}
					if (minDistance > currentDistance) {
						minDistance = currentDistance;
						centerIndex = j;
					}
				}
				helpCenterList.get(centerIndex).add(dataList.get(i));
			}

			// System.out.println(helpCenterList);

			// 计算新的k个聚类中心
			for (int i = 0; i < k; i++) {

				ArrayList<Double> tmp = new ArrayList<Double>();

				for (int j = 0; j < centers.get(0).size(); j++) {
					double sum = 0;
					for (int t = 0; t < helpCenterList.get(i).size(); t++)
						sum += helpCenterList.get(i).get(t).get(j);
					tmp.add(sum / helpCenterList.get(i).size());
				}

				newCenters.add(tmp);

			}
			System.out.println("\nnew clusters' centers:\n");
			for (int i = 0; i < k; i++)
				System.out.println(newCenters.get(i));
			// 计算新旧中心之间的距离，当距离小于阈值时，聚类算法结束
			double distance = 0;

			for (int i = 0; i < k; i++) {
				for (int j = 1; j < centers.get(0).size(); j++) {// 计算两点之间的欧式距离
					distance += ((centers.get(i).get(j) - newCenters.get(i)
							.get(j)) / (centers.get(i).get(j) + newCenters.get(
							i).get(j)))
							* ((centers.get(i).get(j) - newCenters.get(i)
									.get(j)) / (centers.get(i).get(j) + newCenters
									.get(i).get(j)));
				}
				// System.out.println(i+" "+distance);
			}

			System.out.println("\ndistance: " + distance + "\n\n");

			if (distance == 0)// 小于阈值时，结束循环
				break;
			else// 否则，新的中心来代替旧的中心，进行下一轮迭代
			{
				centers = new ArrayList<ArrayList<Double>>(newCenters);
				// System.out.println(newCenters);
				newCenters = new ArrayList<ArrayList<Double>>();
				helpCenterList = new ArrayList<ArrayList<ArrayList<Double>>>();
				helpCenterList = initHelpCenterList(helpCenterList, k);
			}
		}
		
		File file=new File(outFilePath);
		FileOutputStream outStream=null;
		try {
			outStream=new FileOutputStream(file);
			


		
		
		
		jsonObject2.remove("nodes");
		JSONArray jaNode=new JSONArray();
		
		// 输出最后聚类结果
		for (int i = 0; i < k; i++) {
			System.out.println("\n\nCluster: " + (i + 1) + "   size: "
					+ helpCenterList.get(i).size() + " :\n\n");
			for (int j = 0; j < helpCenterList.get(i).size(); j++) {
				System.out.println(helpCenterList.get(i).get(j));
				JSONObject JSObject=map.get(helpCenterList.get(i).get(j).get(0)+" "+helpCenterList.get(i).get(j).get(1));
				JSObject.element("color", i+1);
				jaNode.add(JSObject);
				System.out.println(JSObject.toString());
				
			}
		}
		jsonObject2.put("nodes", jaNode);
		outStream.write(jsonObject2.toString().getBytes("utf-8"));
		outStream.close();
		System.out.println(file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}