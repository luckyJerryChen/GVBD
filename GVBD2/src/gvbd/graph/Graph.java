package gvbd.graph;

import java.util.Iterator;


public class Graph {
	Node[] nodes;

	public Node[] createNodes(int vertexNum){
		nodes=new Node[vertexNum];
		return this.nodes;
	}
	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}
	public void printEdges2(){
		System.out.println("8888");
		Node[] nodes=this.nodes;
		for(int i=0;i<nodes.length;++i){
			System.out.print(nodes[i].getNodeName()+" ");
			Iterator<Edge> edgesIt=nodes[i].getEdges2().iterator();
			while(edgesIt.hasNext()){
				Edge edge=edgesIt.next();
				System.out.print(edge.getTarget()+":"+edge.getWeight()+" ");
			}
			System.out.println();
		}
	}
	public void printNode(){
		for(int i=0;i<this.getNodes().length;++i){
			Node node=this.nodes[i];
			//System.out.println("id:"+node+" x:"+node.getNodeLayoutData().getX()+"  y:"+node.getNodeLayoutData().getY());
		}
	}

}
