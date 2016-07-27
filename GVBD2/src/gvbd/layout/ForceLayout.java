package gvbd.layout;

import gvbd.config.ForceLayoutConfig;
import gvbd.config.LayoutConfig;
import gvbd.graph.Edge;
import gvbd.graph.Graph;
import gvbd.graph.Node;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ForceLayout implements Layout {
	private Graph graph;
	private boolean isDirected;
	private int times;
	private boolean layoutByTimes;
	private int width;
	private int height;
	private double resultantForceX;
	private double resultantForceY;
	private double forceThreshold; 
	private int speed;
	int k;

	public ForceLayout(Graph graph, LayoutConfig layoutConfig) {
		this.graph = graph;
		ForceLayoutConfig forceLayoutConfig = (ForceLayoutConfig) layoutConfig;
		this.height = forceLayoutConfig.getHeight();
		this.isDirected = forceLayoutConfig.isDirected();
		this.k = forceLayoutConfig.getK();
		this.layoutByTimes = forceLayoutConfig.isLayoutByTimes();
		this.speed = forceLayoutConfig.getSpeed();
		this.times = forceLayoutConfig.getTimes();
		this.width = forceLayoutConfig.getWidth();
		this.forceThreshold=forceLayoutConfig.getForceThreshold();
	}

	@Override
	public void initAlgo() {
		Node [] nodes=this.graph.getNodes();
		System.out.println("定点数=" + this.graph.getNodes().length);
		
		for(int i=0;i<nodes.length;++i) {
			Node node = nodes[i];
			node.getNodeLayoutData().setX(Math.random() * width);
			node.getNodeLayoutData().setY(Math.random() * height);
		}
	}

	@Override
	public void goAlgo() throws Exception {
		this.resultantForceX = 0;
		this.resultantForceY = 0;

		// 计算引力
		
		Node [] nodes=this.graph.getNodes();
		for (int i=0;i<nodes.length;++i) {
			double forceX;
			double forceY;
			Node node = nodes[i];
			List<Edge> edges = node.getEdges();
			Iterator<Edge> edgeIt = edges.iterator();
			double attractForceX = 0;
			double attractForceY = 0;
			// 计算引力
			while (edgeIt.hasNext()) {
				Edge edge = edgeIt.next();
				// 引力公式 力等于距离
				attractForceX += nodes[edge.getTarget()]
						.getNodeLayoutData().getX()
						- nodes[edge.getResource()].getNodeLayoutData()
								.getX();
				attractForceY += nodes[edge.getTarget()]
						.getNodeLayoutData().getY()
						- nodes[edge.getResource()].getNodeLayoutData()
								.getY();

			}
			// 计算斥力
			double repulsionForceX = 0;
			double repulsionForceY = 0;
			
			for (int j=0;j<nodes.length;++i) {
				Node node2 = nodes[j];
				if (node.getNodeId() != node2.getNodeId()) {
					repulsionForceX += k
							/ (node.getNodeLayoutData().getX() - node2
									.getNodeLayoutData().getX());
					repulsionForceY += k
							/ (node.getNodeLayoutData().getY() - node2
									.getNodeLayoutData().getY());
				}
			}
			forceX = attractForceX + repulsionForceX;
			forceY = attractForceY + repulsionForceY;

			this.resultantForceX += forceX;
			this.resultantForceY += forceY;

			node.getNodeLayoutData().setX(
					node.getNodeLayoutData().getX() + speed * forceX);
			node.getNodeLayoutData().setY(
					node.getNodeLayoutData().getY() + speed * forceY);
		}

	}

	@Override
	public void doLayout() {
		try {
			
			this.initAlgo();
			if (layoutByTimes) {
				for (int i = 0; i < times; ++i) {
					this.goAlgo();
					System.out.println("合力x=" + this.resultantForceX
							+ "   合力y=" + this.resultantForceY+"   合力="+Math.sqrt(this.resultantForceX*this.resultantForceX+this.resultantForceY+this.resultantForceY));
				}
			} else {
				this.goAlgo();
				int times=1;
				double force =Math.sqrt(this.resultantForceX*this.resultantForceX+this.resultantForceY+this.resultantForceY);
				System.out.println(force);
				while(this.forceThreshold<force){
					this.goAlgo();
					times++;
					System.out.println(force);
					force =Math.sqrt(this.resultantForceX*this.resultantForceX+this.resultantForceY+this.resultantForceY);
				}
				System.out.println("times="+times);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("暂时没实现这种类型的布局");
			e.printStackTrace();
		}

		// Iterator it=this.graph.getNodes().keySet().iterator();
		// while(it.hasNext()){
		// Node node=this.graph.getNodes().get(it.next());
		// System.out.println("nodeId="+node.getNodeId()+"  x="+node.getNodeLayoutData().getX()+"   y="+node.getNodeLayoutData().getY());
		//
		// }
	}

}
