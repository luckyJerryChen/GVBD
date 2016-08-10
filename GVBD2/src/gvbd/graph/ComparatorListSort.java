package gvbd.graph;

import java.util.Comparator;  

public class ComparatorListSort implements Comparator {  
    @Override  
    public int compare(Object o1, Object o2) {  
    	String[] arr1 = o1.toString().split("\t");
    	String[] arr2 = o2.toString().split("\t");
    	
    	if(Integer.parseInt(arr1[1]) > Integer.parseInt(arr2[1]))
    	{
    		 return -1;  
    	}else if(Integer.parseInt(arr1[1]) < Integer.parseInt(arr2[1]))
    	{
    		return 1;  
    	}else {
    		return 0;  
    	}
    }  
}  