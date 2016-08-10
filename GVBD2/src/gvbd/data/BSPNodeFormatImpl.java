package gvbd.data;

import gvbd.graph.Edge;
import gvbd.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class BSPNodeFormatImpl implements NodeFormat{
	@Override
	public Node stringToNode(String nodeStr){
		int nodeId;
		String nodeValue;
		List<Edge> edges=new ArrayList<Edge>();
		String temp[]=nodeStr.split("\t");
		String nodeTemp[]=temp[0].split(":");
		//System.out.println("aaaaaaaaaaaaaa"+temp[0]);
		String edgesTemp[]=temp[1].split(" ");
		if(nodeTemp[0].startsWith("?"))
			nodeTemp[0]=nodeTemp[0].substring(1);
		nodeId=Integer.parseInt(nodeTemp[0]);
		nodeValue=nodeTemp[1];
		if(nodeId == 1)System.out.println(nodeValue);
		for(int i=0;i<edgesTemp.length;++i){
			int resource;
			int target;
			float weight;
			resource=nodeId;
			String edgeTemp[]=edgesTemp[i].split(":");
			target=Integer.parseInt(edgeTemp[0]);
			weight =Float.parseFloat(edgeTemp[1]);
			edges.add(new Edge(resource,target,weight));
		}
		return new Node(nodeId,Integer.toString(nodeId), null, nodeValue, edges);
		
	}

}
