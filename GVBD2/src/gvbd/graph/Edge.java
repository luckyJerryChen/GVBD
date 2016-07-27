package gvbd.graph;

public class Edge {
	private int resource;
	private int target;
	private float weight;
	public Edge(int resource, int target, float weight) {
		super();
		this.resource = resource;
		this.target = target;
		this.weight = weight;
	}
	public Edge(int resource, int target) {
		super();
		this.resource = resource;
		this.target = target;
		this.weight = 1;
	}

	@Override
	public int hashCode(){
		return new Integer(this.resource+this.target).hashCode();
	}
	@Override
	public boolean equals(Object obj){
		Edge edge=(Edge)obj;
		if(this.resource==edge.resource&&this.target==edge.target){
			return true;
		}
		else{
			return false;
		}
	}
	public int getResource() {
		return resource;
	}
	public void setResource(int resource) {
		this.resource = resource;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}

}
