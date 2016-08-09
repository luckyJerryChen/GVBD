package gvbd.layout;

import gvbd.config.ChengLayoutConfig;
import gvbd.config.LayoutConfig;
import gvbd.graph.Edge;
import gvbd.graph.Graph;
import gvbd.graph.Label;
import gvbd.graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class ChengLayout implements Layout {
	private Graph graph;
	private boolean isDirected;
	private int times;
	private boolean layoutByTimes;
	private int width;
	private int height;
	private double resultantForceX;
	private double resultantForceY;
	private double forceThreshold;
	private int area;
	private double k;
	private float cool;
	private float temperature;
	private int edgeDeep;
	private float minP;

	public ChengLayout(Graph graph, LayoutConfig layoutConfig) {
		this.graph = graph;
		ChengLayoutConfig forceLayoutConfig = (ChengLayoutConfig) layoutConfig;
		this.height = forceLayoutConfig.getHeight();
		this.width = forceLayoutConfig.getWidth();
		this.isDirected = forceLayoutConfig.isDirected();
		this.area = this.height * this.width;
		System.out.println("面积="+this.area);
		this.k = Math.sqrt(this.area / this.graph.getNodes().length)*forceLayoutConfig.getK();
		this.layoutByTimes = forceLayoutConfig.isLayoutByTimes();
		this.times = forceLayoutConfig.getTimes();
		this.forceThreshold = forceLayoutConfig.getForceThreshold();
		this.cool=forceLayoutConfig.getCool();
		this.temperature=forceLayoutConfig.getTemperature();
		this.edgeDeep=forceLayoutConfig.getDeep();
		this.minP=0.01f;
		System.out.println("k="+this.k);
	}

	@Override
	public void initAlgo() {
		
		System.out.println("定点数=" + this.graph.getNodes().length);
		Node [] nodes = this.graph.getNodes();
		for (int i=0;i<nodes.length;++i) {
			Node node = nodes[i];
			//node.getNodeLayoutData().setX(Math.random() * width);
			//node.getNodeLayoutData().setY(Math.random() * height);
			node.getNodeLayoutData().setX(Math.random() * width);
			node.getNodeLayoutData().setY(Math.random() * height);
			node.getNodeLayoutData().setOldX(node.getNodeLayoutData().getX());
			node.getNodeLayoutData().setOldY(node.getNodeLayoutData().getY());
		}
		
		this.graph.printNode();
		
		createEdges2(this.graph);
		//this.graph.printEdges2();

	}

	@Override
	public void goAlgo() throws Exception {
		this.resultantForceX = 0;
		this.resultantForceY = 0;
		// 计算引力
		Node[] nodes = this.graph.getNodes();
		for (int i=0 ;i<nodes.length;++i) {

			double forceX;
			double forceY;
			Node node1 = nodes[i];
			List<Edge> edges = node1.getEdges2();
			Iterator<Edge> edgeIt = edges.iterator();
			double attractForceX = 0;
			double attractForceY = 0;
			double repulsionForceX = 0;
			double repulsionForceY = 0;
			
        //	System.out.println("计算顶点"+node1.getNodeId());
			// 计算斥力
			while (edgeIt.hasNext()) {
				Edge edge = edgeIt.next();
				Node node2=nodes[edge.getTarget()-1];
				//防止两个顶点重叠
				if(node1.getNodeLayoutData().getOldX()==node2.getNodeLayoutData().getOldY()&&node1.getNodeLayoutData().getOldX()==node2.getNodeLayoutData().getOldY()){
					if(node1.getNodeId()<node2.getNodeId()){
						node1.getNodeLayoutData().setOldX(node1.getNodeLayoutData().getOldX()+0.00001d);
					}
					else{
						node1.getNodeLayoutData().setOldX(node1.getNodeLayoutData().getOldX()-0.00001d);
					}
				}
		//		System.out.println("与"+node2.getNodeId()+"的斥力 ");
				if (node1.getNodeId() != node2.getNodeId()) {
					double dist=dist(node1,node2);
				//	System.out.println("距离 "+dist);
				//	System.out.println("斥力"+repulsiveForce(dist));
					repulsionForceX += (node1.getNodeLayoutData().getOldX()-node2.getNodeLayoutData().getOldX())/(dist+0.00001d)*repulsiveForce(dist);
					repulsionForceY += (node1.getNodeLayoutData().getOldY()-node2.getNodeLayoutData().getOldY())/(dist+0.00001d)*repulsiveForce(dist);
				//	System.out.println("x方向上斥力"+(node1.getNodeLayoutData().getOldX()-node2.getNodeLayoutData().getOldX()+0.00001d)/dist*repulsiveForce(dist));
				//.out.println("y方向上斥力"+(node1.getNodeLayoutData().getOldY()-node2.getNodeLayoutData().getOldY()+0.00001d)/dist*repulsiveForce(dist));
				}

			}
			edges=node1.getEdges();
			edgeIt = edges.iterator();
			// 计算引力
			while (edgeIt.hasNext()) {
				Edge edge = edgeIt.next();
				Node node2=nodes[edge.getTarget()-1];
				
				//System.out.println("与"+node2.getNodeId()+"的引力");
				double dist=dist(node1,node2);
				// 引力公式 力等于距离
			//	System.out.println("引力"+attractiveForce(dist));
				attractForceX += (node2.getNodeLayoutData().getOldX()-node1.getNodeLayoutData().getOldX())/(dist+0.00001d)*attractiveForce(dist);
				attractForceY += (node2.getNodeLayoutData().getOldY()-node1.getNodeLayoutData().getOldY())/(dist+0.00001d)*attractiveForce(dist);
			//	System.out.println("x方向上引力"+(node2.getNodeLayoutData().getOldX()-node1.getNodeLayoutData().getOldX())/(dist+0.00001d)*attractiveForce(dist));
			//	System.out.println("y方向上引力"+(node2.getNodeLayoutData().getOldY()-node1.getNodeLayoutData().getOldY())/(dist+0.00001d)*attractiveForce(dist));
			}
			
			
			//防止很多顶点重叠在一点，导致负无穷加正无穷等于nan
			if(Double.isNaN(attractForceX)){
				attractForceX=Math.random();
			}
			if(Double.isNaN(attractForceY)){
				attractForceY=Math.random();
			}
			if(Double.isNaN(repulsionForceX)){
				repulsionForceX=Math.random();
			}
			if(Double.isNaN(repulsionForceY)){
				repulsionForceY=Math.random();
			}
			forceX = attractForceX + repulsionForceX;
			forceY = attractForceY + repulsionForceY;
			double force=Math.sqrt(forceX*forceX+forceY*forceY);
			this.resultantForceX += Math.abs(forceX);
			this.resultantForceY += Math.abs(forceY);
			

		//	System.out.println("温度="+this.temperature);
		//	System.out.println("x方向合力"+forceX);
		//	System.out.println("x方向合力"+forceY);
			double disp=Math.sqrt(forceX*forceX+forceY*forceY);
			
		      if(disp>this.temperature) {
		          if (Double.isInfinite(forceX) && Double.isInfinite(forceY)) {
		        	forceX = 1.414D * this.temperature;
		        	forceX = 1.414D * this.temperature;
		          }
		          else {
		            if (Double.isInfinite(forceX))
		            	forceX = this.temperature;
		            else
		            	forceX = forceX / disp * this.temperature;
		            if (Double.isInfinite(forceY))
		            	forceY = this.temperature;
		            else
		            	forceY = forceY / disp * this.temperature;
		          }
		        }

			node1.getNodeLayoutData().setX(
					node1.getNodeLayoutData().getOldX() + forceX );
			node1.getNodeLayoutData().setY(
					node1.getNodeLayoutData().getOldY() +  forceY);
		//	System.out.println("x方向位移"+Math.abs(forceX));
		//	System.out.println("y方向位移"+Math.abs(forceY));
		//	System.out.println("oldx"+node1.getNodeLayoutData().getOldX());
		//	System.out.println("oldy"+node1.getNodeLayoutData().getOldY());
		//	System.out.println("x"+node1.getNodeLayoutData().getX());
//			System.out.println("y"+node1.getNodeLayoutData().getY());
			

			if(node1.getNodeLayoutData().getX()<0){
				node1.getNodeLayoutData().setX(0);
			}
			if(node1.getNodeLayoutData().getX()>width){
				node1.getNodeLayoutData().setX(width);
			}
			if(node1.getNodeLayoutData().getY()<0){
				node1.getNodeLayoutData().setY(0);
			}
			if(node1.getNodeLayoutData().getY()>height){
				node1.getNodeLayoutData().setY(height);
			}
			
		
		}

		for(int i=0;i<nodes.length;++i){
			Node node=nodes[i];
			node.getNodeLayoutData().setOldX(node.getNodeLayoutData().getX());
			node.getNodeLayoutData().setOldY(node.getNodeLayoutData().getY());
		}


		
	}
/*	public void goAlgo() throws Exception {
		this.resultantForceX = 0;
		this.resultantForceY = 0;
		// 计算引力
		Node[] nodes = this.graph.getNodes();
		for (int i=0 ;i<nodes.length;++i) {
			//long start=System.currentTimeMillis();
			double forceX;
			double forceY;
			Node node1 = nodes[i];
		//	System.out.println("顶点"+node1.getNodeId()+"位置  x="+node1.getNodeLayoutData().getOldX()+"  y="+node1.getNodeLayoutData().getOldY());
			List<Edge> edges = node1.getEdges2();
		//	System.out.println("edge size="+edges.size());
			Iterator<Edge> edgeIt = edges.iterator();
			double attractForceX = 0;
			double attractForceY = 0;
			double repulsionForceX = 0;
			double repulsionForceY = 0;
			// 计算引力斥力
			while (edgeIt.hasNext()) {
				Edge edge = edgeIt.next();
				Node node2=nodes[edge.getTarget()-1];
				double dist=dist(node1,node2);
				float weight =edge.getWeight();
		//		System.out.println("顶点"+node2.getNodeId()+"位置  x="+node2.getNodeLayoutData().getOldX()+"  y="+node2.getNodeLayoutData().getOldY());
		//		System.out.println("距离="+dist);
		//		System.out.println("引力="+attractiveForce(edge)+"   x方向="+(node2.getNodeLayoutData().getOldX()-node1.getNodeLayoutData().getOldX())/(dist+0.00001d)*attractiveForce(edge)+"   y方向="+(node2.getNodeLayoutData().getOldY()-node1.getNodeLayoutData().getOldY())/(dist+0.00001d)*attractiveForce(edge));

				// 引力公式 力等于距离
				attractForceX += (node2.getNodeLayoutData().getOldX()-node1.getNodeLayoutData().getOldX())/(dist+0.00001d)*attractiveForce(dist,weight);
				attractForceY += (node2.getNodeLayoutData().getOldY()-node1.getNodeLayoutData().getOldY())/(dist+0.00001d)*attractiveForce(dist,weight);
				repulsionForceX += -(node2.getNodeLayoutData().getOldX()-node1.getNodeLayoutData().getOldX())/(dist+0.00001d)*repulsiveForce(dist,weight);
				repulsionForceY += -(node2.getNodeLayoutData().getOldY()-node1.getNodeLayoutData().getOldY())/(dist+0.00001d)*repulsiveForce(dist,weight);
		//		System.out.println("引力="+attractiveForce(edge)+"   x方向="+attractForceX+"   y方向="+attractForceY);
		//		System.out.println("斥力="+repulsiveForce(edge)+"   x方向="+repulsionForceX+"   y方向="+repulsionForceY);

				
			//	System.out.println(repulsiveForce(edge));


			}
		//	long end=System.currentTimeMillis();
		//	System.out.println(end-start);
		//	assert false;
			
	//		System.out.println("引力合力         x="+attractForceX+"             y="+attractForceY);
			// 计算斥力
			double repulsionForceX = 0;
			double repulsionForceY = 0;
			Iterator it2 = this.graph.getNodes().keySet().iterator();
			while (it2.hasNext()) {
				Node node2 = nodeMap.get(it2.next());
				
				
				
				
				
				if (node1.getNodeId() != node2.getNodeId()) {
					
	//				System.out.println("顶点"+node2.getNodeId()+"位置  x="+node2.getNodeLayoutData().getOldX()+"  y="+node2.getNodeLayoutData().getOldY());
					
					double dist=dist(node1,node2);
					repulsionForceX += (node1.getNodeLayoutData().getOldX()-node2.getNodeLayoutData().getOldX()+0.00001d)/dist*repulsiveForce(dist);
					repulsionForceY += (node1.getNodeLayoutData().getOldY()-node2.getNodeLayoutData().getOldY()+0.00001d)/dist*repulsiveForce(dist);
					
		//			System.out.println("斥力="+repulsiveForce(dist)+"   x方向="+(node1.getNodeLayoutData().getOldX()-node2.getNodeLayoutData().getOldX()+0.00001d)/dist*repulsiveForce(dist)+"   y方向="+(node1.getNodeLayoutData().getOldY()-node2.getNodeLayoutData().getOldY()+0.00001d)/dist*repulsiveForce(dist));
					
					
				}
			}
			
	//		System.out.println("斥力合力         x="+repulsionForceX+"             y="+repulsionForceY);
			
			
			forceX = attractForceX + repulsionForceX;
			forceY = attractForceY + repulsionForceY;
			double force=Math.sqrt(forceX*forceX+forceY*forceY);
		//	System.out.println(forceX);
			
			
		//	System.out.println("合力   x="+ forceX+"   y="+forceY);
			
			

			this.resultantForceX += Math.abs(forceX);
			this.resultantForceY += Math.abs(forceY);
			


//			node1.getNodeLayoutData().setX(
//					node1.getNodeLayoutData().getOldX() +  20*forceX/Math.abs(forceX));
//			node1.getNodeLayoutData().setY(
//					node1.getNodeLayoutData().getOldY() + 20*forceY/Math.abs(forceY));

	//		System.out.println("温度="+this.temperature);
			node1.getNodeLayoutData().setX(
					node1.getNodeLayoutData().getOldX() +  (Math.abs(forceX)>this.temperature?((forceX>0?1:-1)*this.temperature):forceX));
			node1.getNodeLayoutData().setY(
					node1.getNodeLayoutData().getOldY() +  (Math.abs(forceY)>this.temperature?((forceY>0?1:-1)*this.temperature):forceY));
			
	//		System.out.println("位移    x="+(Math.abs(forceX)>this.temperature?((forceX>0?1:-1)*this.temperature):forceX)+"    y="+(Math.abs(forceY)>this.temperature?((forceY>0?1:-1)*this.temperature):forceY));
		//	node1.getNodeLayoutData().setX(0>(this.width<node1.getNodeLayoutData().getX()?this.width:node1.getNodeLayoutData().getX())?0:(this.width<node1.getNodeLayoutData().getX()?this.width:node1.getNodeLayoutData().getX()));
		//	node1.getNodeLayoutData().setY(0>(this.height<node1.getNodeLayoutData().getY()?this.width:node1.getNodeLayoutData().getY())?0:(this.width<node1.getNodeLayoutData().getY()?this.width:node1.getNodeLayoutData().getY()));

			if(node1.getNodeLayoutData().getX()<0){
				node1.getNodeLayoutData().setX(0);
			}
			if(node1.getNodeLayoutData().getX()>width){
				node1.getNodeLayoutData().setX(width);
			}
			if(node1.getNodeLayoutData().getY()<0){
				node1.getNodeLayoutData().setY(0);
			}
			if(node1.getNodeLayoutData().getY()>height){
				node1.getNodeLayoutData().setY(height);
			}
			
	//		System.out.println("顶点新位置    x="+node1.getNodeLayoutData().getX()+"    y="+node1.getNodeLayoutData().getY());
		
		}

		for(int i=0;i<nodes.length;++i){
			Node node=nodes[i];
			node.getNodeLayoutData().setOldX(node.getNodeLayoutData().getX());
			node.getNodeLayoutData().setOldY(node.getNodeLayoutData().getY());
		}


		
	}*/

	@Override
	public void doLayout() {
		try {

			this.initAlgo();
			if (layoutByTimes) {
				for (int i = 0; i < times; ++i) {
					this.goAlgo();
/*					System.out.println("!!!!!!!!!合力x="
							+ this.resultantForceX
							+ "   合力y="
							+ this.resultantForceY
							+ "   合力="
							+ Math.sqrt(this.resultantForceX
									* this.resultantForceX
									+ this.resultantForceY
									+ this.resultantForceY));
					System.out.println();*/
					this.temperature=cool(this.temperature);
				}
			} else {
				this.goAlgo();
				int times = 1;
				double force = Math.sqrt(this.resultantForceX
						* this.resultantForceX + this.resultantForceY
						+ this.resultantForceY);
//				System.out.println(force);
				while (this.forceThreshold < force) {
					this.goAlgo();
					times++;
//					System.out.println(force);
					force = Math.sqrt(this.resultantForceX
							* this.resultantForceX + this.resultantForceY
							+ this.resultantForceY);
				}
				System.out.println("times=" + times);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("暂时没实现这种类型的布局");
			e.printStackTrace();
		}


	}

	private double dist(Node node1, Node node2) {
		double dist = Math.sqrt((node1.getNodeLayoutData().getOldX() - node2
				.getNodeLayoutData().getOldX())
				* (node1.getNodeLayoutData().getOldX() - node2.getNodeLayoutData()
						.getOldX())
				+ (node1.getNodeLayoutData().getOldY() - node2.getNodeLayoutData()
						.getOldY())
				* (node1.getNodeLayoutData().getOldY() - node2.getNodeLayoutData()
						.getOldY()));
		
		/*		System.out.println("x1="+node1.getNodeLayoutData().getOldX()+"  x2=" + node2
				.getNodeLayoutData().getOldX()+"       "+node2.getNodeId());
		System.out.println("x="+(node1.getNodeLayoutData().getOldX() - node2
				.getNodeLayoutData().getOldX())+"   y="+(node1.getNodeLayoutData().getOldY() - node2.getNodeLayoutData()
						.getOldY()));*/
		return dist;
	}
	private double attractiveForce(Edge edge){
		Node[] nodes=this.graph.getNodes();
		Node node1=nodes[edge.getResource()];
		Node node2=nodes[edge.getTarget()];
		double force=dist(node1,node2)*dist(node1, node2)/(this.k*edge.getWeight());
		//System.out.println(force+"    "+dist(node1, node2));
		//assert !Double.isNaN(force);

		return force;
	}
	private double repulsiveForce(double dist,float weight){
		double force=(this.k*weight)*(this.k*weight)/dist;
		//System.out.println(force+"    "+dist(node1, node2)+"   "+edge.getWeight());
		//assert !Double.isNaN(force);
		
		return force;
	}
	private double attractiveForce(double dist){
		double force=dist*dist/this.k;
		return force;
	}
	private double repulsiveForce(double dist){
		double force=this.k*this.k/dist;
		return force;
	}
	private double attractiveForce(double dist ,float weight){
		double force=dist*dist/(this.k*weight);
		//System.out.println(force+"    "+dist(node1, node2));
		//assert !Double.isNaN(force);

		return force;
	}
	private double repulsiveForce(Edge edge){
		Node[] nodes=this.graph.getNodes();
		Node node1=nodes[edge.getResource()];
		Node node2=nodes[edge.getTarget()];
		double force=(this.k*edge.getWeight())*(this.k*edge.getWeight())/dist(node1,node2);
		//System.out.println(force+"    "+dist(node1, node2)+"   "+edge.getWeight());
		//assert !Double.isNaN(force);
		
		return force;
	}
	private float cool(float t){
		return t*cool;
	}
	public void createEdges2(Graph graph){
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
						if(curDeep<this.edgeDeep&&!visited.contains(target)){
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
	public static void createEdges2V2(Graph graph){
		//Map<Integer, Node> nodeMap = this.graph.getNodes();
		Node[] nodes = graph.getNodes();
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			Queue<Node> queue=new LinkedBlockingQueue<Node>();
			Queue<Integer> deepQueue=new LinkedBlockingQueue<Integer>();
			Map<Node,Integer> visited=new HashMap<Node,Integer>();
			Map<Edge,Float> edges3=new HashMap<Edge,Float>();	
			Iterator<Edge> it2=node1.getEdges().iterator();
			queue.offer(node1);
			visited.put(node1,1);
			deepQueue.offer(1);
			int curDeep;
			//System.out.println("当前"+node1.getNodeId());
			while(!queue.isEmpty()){
				Node node=queue.poll();
				curDeep=deepQueue.poll();
				System.out.println(node.getNodeId()+"  深度="+curDeep);
				Edge newEdge=new Edge(node1.getNodeId(),node.getNodeId());
				if(curDeep!=1&&edges3.containsKey(newEdge)){
					edges3.put(newEdge, edges3.get(newEdge)+(float)Math.pow(0.5f, curDeep-1));
					System.out.println(edges3.get(newEdge)+(float)Math.pow(0.5f, curDeep-1));
				}
				if(curDeep!=1&&!edges3.containsKey(newEdge)){
					edges3.put(newEdge,(float)Math.pow(0.5f, curDeep-1));
					System.out.println((float)Math.pow(0.5f, curDeep-1));
				}

				Iterator<Edge> edgeIt=node.getEdges().iterator();
				while(edgeIt.hasNext()){
					Edge edge=edgeIt.next();
					Node target=nodes[edge.getTarget()-1];				
						if(curDeep<5&&!visited.keySet().contains(target)){
						visited.put(target,curDeep+1);
						queue.offer(target);
						deepQueue.offer(curDeep+1);
					}
						if(curDeep<5&&visited.get(target)==curDeep){
						queue.offer(target);
						deepQueue.offer(curDeep+1);
					}
				}
			}
		//	System.out.println();
			node1.setEdge3(edges3);
			//System.out.println(edges2.size());
			//assert false;
		}
	}
	public static void createEdges2V3(Graph graph){
		//Map<Integer, Node> nodeMap = this.graph.getNodes();
		Node[] nodes = graph.getNodes();
		for(int i=0;i<nodes.length;++i){
			Node node1=nodes[i];
			Stack<Node> stack=new Stack<Node>();
			Stack<Float> pStack=new Stack<Float>();
			Set<Node> visited=new HashSet<Node>();
			Map<Edge,Float> edges2=new HashMap<Edge,Float>();	
			Iterator<Edge> it2=node1.getEdges().iterator();
			stack.push(node1);
			visited.add(node1);
			pStack.push(1f);
			System.out.println(node1.getNodeId());
			float  p;
			while(!stack.isEmpty()){
				Node node=stack.pop();
				p=pStack.pop();
				System.out.println(node.getNodeId()+"出栈");
				//visited.remove(node);
		//		System.out.println(node.getNodeId()+"  深度="+curDeep);

				Iterator<Edge> edgeIt=node.getEdges().iterator();
				while(edgeIt.hasNext()){
					Edge edge=edgeIt.next();
					Node target=nodes[edge.getTarget()];				
						if(p>=0.01f&&!visited.contains(target)){
						stack.push(target);
						pStack.push(p*0.5f);
						visited.add(target);
						System.out.println(target.getNodeId()+"       "+visited.size());
						if(p!=1f){
							Edge edge2=new Edge(node1.getNodeId(),node.getNodeId());
							if(!edges2.containsKey(edge2)){
								edges2.put(edge2,p);
							}
							else{
								edges2.put(edge2,p+edges2.get(edge2));
							}
						}
						break;
					}
				}
			}
			System.out.println();
			node1.setEdge3(edges2);
			//System.out.println(edges2.size());
			//assert false;
		}
	}

}
