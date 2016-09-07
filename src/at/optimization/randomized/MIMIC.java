package at.optimization.randomized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class MIMIC {
	
	
	//a mimic algorithm, where the variables are boolean 
	public static <T extends Distribution<E>, E extends GivingVariables<V,Boolean>,V> E binaryMimicAlgorithm(T distribution, Function<E, Double> function){
		
		List<E> sampleList = new ArrayList<>();
		double threshold;
		
		for(int i = 0; i < 10000; i++){
			E nextSample = distribution.generateSample();
			if(!sampleList.contains(nextSample))
				sampleList.add(nextSample);
		}
		
		Collections.sort(sampleList, (e1, e2) -> {return function.apply(e1) > function.apply(e2) ? 1 : 
			function.apply(e1) == function.apply(e2) ? 0 : -1;});
		
		final int listSize = sampleList.size();
		for(int i = 0; i < listSize/2; i++){
			sampleList.remove(0);
		}
		threshold = generateThreshold(sampleList, function);
		
		
		
	}
	
	public static <E> double generateThreshold(List<E> list, Function<E, Double> function){
		
		double threshold = Double.MAX_VALUE;
		for(E element : list){
			double value = function.apply(element);
			if(value < threshold)
				threshold = value;
		}
		return threshold;
	}
	
}
