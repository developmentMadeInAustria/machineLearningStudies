package at.optimization.randomized;

import java.util.Map;

public class SpanningTree<T> {

	private T root;
	private Map<T,T> parentMap;
	
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
	
}
