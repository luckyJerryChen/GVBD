package gvbd.data;

import gvbd.graph.Graph;
import gvbd.graph.Node;

import java.io.BufferedReader;
import java.io.IOException;

public class GraphData {

	private Graph graph;

	public GraphData() {
		this.graph = new Graph();
	}

	public void loadNodeData(BufferedReader nodeDataReader,
			NodeFormat nodeFormat,int vertexNum) {
		graph.createNodes(vertexNum);
		Node [] nodes=graph.getNodes();
		String nodeLine;
		int lineNo=0;
		try {
			nodeLine = nodeDataReader.readLine();
			if(lineNo == 0)System.out.println(nodeLine);
			while (nodeLine != null) {
				Node node = nodeFormat.stringToNode(nodeLine);
				nodes[lineNo++]=node;
				nodeLine = nodeDataReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
