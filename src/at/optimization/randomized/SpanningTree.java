package at.optimization.randomized;

import java.util.Map;

public class SpanningTree<T,E> {

	private T root;
	private Map<T,T> parentMap;
	private Map<E, Double> sampleMap;
	
	public SpanningTree(T root, Map<T,T> parentMap){
		this.root = root;
		this.parentMap = parentMap;
	}
	
	public T getRoot(){
		return root;
	}
	public Map<T,T> getParentMap(){
		return parentMap;
	}
	public void setSampleMap(Map<E, Double> sampleMap){
		this.sampleMap = sampleMap;
	}
	public Map<E, Double> getSampleMap(){
		return sampleMap;
	}
	
}
