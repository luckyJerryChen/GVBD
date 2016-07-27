package gvbd.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Label {
	private Set<Integer> route;
	private float value;

	public Label(Set route, float value) {
		this.route = route;
		this.value = value;
	}

	public Label() {
		this.route = new HashSet<Integer>();
		this.value = 1;
	}
    public boolean addRoute(int newRoute){
    	this.route.add(newRoute);
    	return true;
    }
    public static Label conbine(Label label1,Label label2){
    	Label newLabel=new Label();
    	Iterator<Integer> it=label2.getRoute().iterator();
    	while(it.hasNext()){
    		newLabel.addRoute(it.next());
    	}
    	it=label1.getRoute().iterator();
    	while(it.hasNext()){
    		newLabel.addRoute(it.next());
    	}
    	newLabel.setValue(label1.getValue()*0.5f+label2.getValue());
    	return newLabel;
    }
	public Set getRoute() {
		return route;
	}

	public void setRoute(Set route) {
		this.route = route;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
