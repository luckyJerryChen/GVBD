package gvbd.evaluate;

import gvbd.config.ChengLayoutConfig;
import gvbd.config.LayoutConfig;
import gvbd.graph.Edge;
import gvbd.graph.Graph;
import gvbd.graph.Node;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Evaluate {

	public static double noack(Graph graph){
		Node[] nodes=graph.getNodes();
		
		double sumDistance=0;
		double sumDistanceEdge=0;
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			for(int j=0;j<nodes.length;++j){
				Node node2=nodes[j];
				double d=dist(node1,node2);
				sumDistance+=d;
			}
		}
		
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			Iterator<Edge> it=node1.getEdges().iterator();
			while(it.hasNext()){
				Node node2=nodes[it.next().getTarget()-1];
				double d=dist(node1,node2);
				sumDistanceEdge+=d;
			}
		}
	//	System.out.println(sumDistance);
	//	System.out.println(sumDistanceEdge);
		return (sumDistance/sumDistanceEdge);
	}
	
	public static double cheng(Graph graph){
		if(graph.getNodes()[0].getEdges2().size()==0){
			createEdges2(graph);		
		}

		Node[] nodes=graph.getNodes();
		
		int a=0;
		int b=0;
		double sumDistance=0;
		double sumDistanceEdge=0;
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			for(int j=0;j<nodes.length;++j){
				Node node2=nodes[j];
			    Edge edge=new Edge(node1.getNodeId(),node2.getNodeId());
				double d=0;
				if(node1.getEdges2().contains(edge)||node2.getEdges2().contains(edge)){
					d=dist(node1,node2);a++;
				}
				
				sumDistance+=d;
			}
		}
		
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			Iterator<Edge> it=node1.getEdges().iterator();
			while(it.hasNext()){
				Node node2=nodes[it.next().getTarget()-1];
				double d=dist(node1,node2);
				sumDistanceEdge+=d;b++;
			}
		}
	//	System.out.println(sumDistance);
	//	System.out.println(sumDistanceEdge);
		return ((sumDistance/a)/(sumDistanceEdge/b));
	}
/*	public static int cheng(Graph graph,LayoutConfig layoutConfig){
		Map<Integer,Node> nodesMap=graph.getNodes();
		Iterator it =graph.getNodes().keySet().iterator();
		int wrongSum=0;
		int deep=((ChengLayoutConfig)layoutConfig).getDeep();
		for (int i=0;i<deep;++i){
			double maxLength=0d;
			while()
		}
	}
	public static int getWrongSum(){
		
	}*/
	public static double standardLength(Graph graph,LayoutConfig layoutConfig){
		LayoutConfig forceLayoutConfig = layoutConfig;
		int height = forceLayoutConfig.getHeight();
		int width = forceLayoutConfig.getWidth();
		int edgeNum=0;
		int area = height * width;
		double k = Math.sqrt(area / graph.getNodes().length);
			//Map<Integer, Node> nodeMap = this.graph.getNodes();
			Node[] nodes = graph.getNodes();
			
			for(int i=0;i<nodes.length;++i){
				Node node1=nodes[i];
				Queue<Node> queue=new LinkedBlockingQueue<Node>();
				Queue<Integer> deepQueue=new LinkedBlockingQueue<Integer>();
				Set<Node> visited=new HashSet<Node>();
				List<Edge> edges2=new LinkedList<Edge>();	
				Iterator<Edge> it2=node1.getEdges().iterator();
				queue.offer(node1);
				visited.add(node1);
				deepQueue.offer(1);
				int curDeep;
				while(!queue.isEmpty()){
					Node node=queue.poll();
					curDeep=deepQueue.poll();
			//		System.out.println(node.getNodeId()+"  深度="+curDeep);
					if(curDeep!=1)
					edges2.add(new Edge(node1.getNodeId(),node.getNodeId(),curDeep-1));
					Iterator<Edge> edgeIt=node.getEdges().iterator();
					while(edgeIt.hasNext()){
						Edge edge=edgeIt.next();
						Node target=nodes[edge.getTarget()-1];				
							if(curDeep<Integer.MAX_VALUE&&!visited.contains(target)){
							visited.add(target);
							queue.offer(target);
							deepQueue.offer(curDeep+1);

						}
					}
				}
			//	System.out.println();
				node1.setEdges2(edges2);
				//System.out.println(edges2.size());
				//assert false;
			}
		
		double score =0;
		
		for(int i=0;i<nodes.length;++i){
			Node node=nodes[i];
			Iterator<Edge> it2=node.getEdges2().iterator();
			while(it2.hasNext()){
				Edge edge=it2.next();
				Node node2=nodes[edge.getTarget()-1];
				double dist=dist(node,node2);
				double differ=Math.abs(dist-k*edge.getWeight());
				score+=differ;
				edgeNum++;
			}
		}
		//System.out.println(edgeNum);
		return score/edgeNum;
	}
	private static double dist(Node node1, Node node2) {
		double dist = Math.sqrt((node1.getNodeLayoutData().getOldX() - node2
				.getNodeLayoutData().getOldX())
				* (node1.getNodeLayoutData().getOldX() - node2.getNodeLayoutData()
						.getOldX())
				+ (node1.getNodeLayoutData().getOldY() - node2.getNodeLayoutData()
						.getOldY())
				* (node1.getNodeLayoutData().getOldY() - node2.getNodeLayoutData()
						.getOldY()));
		return dist;
	}
	public static void  createEdges2(Graph graph){
		//Map<Integer, Node> nodeMap = this.graph.getNodes();
		Node[] nodes = graph.getNodes();
		for(int i=0;i<nodes.length;++i){
			/*Node node=nodes[i];
			Queue<Node> queue=new LinkedBlockingQueue<Node>();
			Map<Integer,Label> labelMap=new HashMap<Integer,Label>();
			queue.offer(node);
			Label label=new Label();
			label.addRoute(node.getNodeId());
			label.setValue(1f);
			labelMap.put(node.getNodeId(),label);
			while(!queue.isEmpty()){
				node=queue.poll();
				label=labelMap.get(node.getNodeId());
				Iterator<Edge> edgeIt=node.getEdges().iterator();
				while(edgeIt.hasNext()){
					Node neigh=nodes[edgeIt.next().getTarget()-1];
					if(labelMap.containsKey(neigh.getNodeId())){
						Label label2=labelMap.get(neigh.getNodeId());
						Label newLabel=Label.conbine(label, label2);
						newLabel.addRoute(node.getNodeId());
						labelMap.put(neigh.getNodeId(), Label.conbine(label, label2));
					}
					else{
						Label newLabel=new Label();
						newLabel.setRoute(label.getRoute());
						newLabel.addRoute(node.getNodeId());
						labelMap.put(neigh.getNodeId(), newLabel);
					}
					if(!label.getRoute().contains(neigh.getNodeId())){
						queue.offer(neigh);
					}
				}
				
			}
			//做测试，之后还需要把label变成边
			*/
			
			
			Node node1=nodes[i];
			Queue<Node> queue=new LinkedBlockingQueue<Node>();
			Queue<Integer> deepQueue=new LinkedBlockingQueue<Integer>();
			Set<Node> visited=new HashSet<Node>();
			List<Edge> edges2=new LinkedList<Edge>();	
			Iterator<Edge> it2=node1.getEdges().iterator();
			queue.offer(node1);
			visited.add(node1);
			deepQueue.offer(1);
			int curDeep;
			while(!queue.isEmpty()){
				Node node=queue.poll();
				curDeep=deepQueue.poll();
		//		System.out.println(node.getNodeId()+"  深度="+curDeep);
				if(curDeep!=1)
				edges2.add(new Edge(node1.getNodeId(),node.getNodeId(),curDeep-1));
				Iterator<Edge> edgeIt=node.getEdges().iterator();
				while(edgeIt.hasNext()){
					Edge edge=edgeIt.next();
					Node target=nodes[edge.getTarget()-1];				
						if(curDeep<3&&!visited.contains(target)){
						visited.add(target);
						queue.offer(target);
						deepQueue.offer(curDeep+1);

					}
				}
			}
		//	System.out.println();
			node1.setEdges2(edges2);
			//System.out.println(edges2.size());
			//assert false;
		}
	}
}
