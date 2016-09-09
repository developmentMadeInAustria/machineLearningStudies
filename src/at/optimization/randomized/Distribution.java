package at.optimization.randomized;

import java.util.Map;

public interface Distribution<E extends GivingVariables> {

	public E generateSample();
	public <T,V,E> Map<E, Double> generateSamples(int number, double threshold, SpanningTree<T,E> tree, 
			Map<E, Double> sampleMap, Map<V, Double> probability);
	
}
