package gvbd.util;

import gvbd.graph.Edge;
import gvbd.graph.Graph;
import gvbd.graph.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Output {
	public static void outputJson(Graph graph,String nre){
		JSONObject jo = new JSONObject();
		Node [] nodes=graph.getNodes();
		List<Map<String,String>> nodeList=new ArrayList<Map<String,String>>();
		List<Map<String,String>> linkList=new ArrayList<Map<String,String>>();
		for(int i=0;i<nodes.length;++i){
			Node node=nodes[i];
			Map<String,String> nodeMap=new HashMap<String,String>();
			nodeMap.put("name", node.getNodeName());
			nodeMap.put("value",node.getNodeValue());
			nodeMap.put("cx", Double.toString(node.getNodeLayoutData().getX()));
			nodeMap.put("cy", Double.toString(node.getNodeLayoutData().getY()));
			if(i == 0)System.out.println(nodeMap);
			nodeList.add(nodeMap);
			List<Edge> edges=node.getEdges();
			Iterator<Edge> edgesIt=node.getEdges().iterator();
			while(edgesIt.hasNext()){
				Map<String,String> linkMap=new HashMap<String,String>();
				Edge edge=edgesIt.next();
				int target=edge.getTarget();
				Node node2=nodes[target-1];
				linkMap.put("x1", Double.toString(node.getNodeLayoutData().getX()));
				linkMap.put("y1", Double.toString(node.getNodeLayoutData().getY()));
				linkMap.put("x2",Double.toString(node2.getNodeLayoutData().getX()));
				linkMap.put("y2", Double.toString(node2.getNodeLayoutData().getY()));
				linkMap.put("value", Double.toString(edge.getWeight()));
				linkList.add(linkMap);
			}
		}
		JSONArray jaNode = JSONArray.fromObject(nodeList);
		JSONArray jaLink = JSONArray.fromObject(linkList);
		jo.put("nodes", jaNode);
		jo.put("links", jaLink);
		
		File file=new File(nre);
		FileOutputStream outStream=null;
		try {
			outStream=new FileOutputStream(file);
			outStream.write(jo.toString().getBytes("utf-8"));
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	
	}

}
