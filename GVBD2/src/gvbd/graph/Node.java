package gvbd.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

	private int nodeId;
	private String nodeName;
	private String nodeLabel;
	private String nodeValue;
	private NodeLayoutData nodeLayoutData;
	private List<Edge> edges;
	private List<Edge> edges2;
	private Map<Edge,Float> edge3;
	
	

	public Node(int nodeId, String nodeName, String nodeLabel,String nodeValue, List<Edge> edges ) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeLabel = nodeLabel;
		this.nodeValue=nodeValue;
		this.edges=edges;
		this.nodeLayoutData=new NodeLayoutData();
		this.edges2=new LinkedList<Edge>();
	}
	
	@Override
	public int hashCode(){
		return new Integer(this.nodeId).hashCode();
	}
	@Override
	public boolean equals(Object obj){
		Node node=(Node)obj;
		if(this.nodeId==node.nodeId){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeLabel() {
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	public String getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	public NodeLayoutData getNodeLayoutData() {
		return nodeLayoutData;
	}
	public void setNodeLayoutData(NodeLayoutData nodeLayoutData) {
		this.nodeLayoutData = nodeLayoutData;
	}

	public List<Edge> getEdges2() {
		return edges2;
	}

	public void setEdges2(List<Edge> edges2) {
		this.edges2 = edges2;
	}

	public Map<Edge, Float> getEdge3() {
		return edge3;
	}

	public void setEdge3(Map<Edge, Float> edge3) {
		this.edge3 = edge3;
	}


}
