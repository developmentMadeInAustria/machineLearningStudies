package at.optimization.randomized;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class MIMIC {

	public static <T extends Distribution<E>, E extends GivingVariables> E mimicAlgorithm(T distribution, Function<E, Double> function){
		
		List<E> sampleList = new ArrayList<>();
		double treshold;
		
		for(int i = 0; i < 10000; i++){
			E nextSample = distribution.generateSample();
			if(!sampleList.contains(nextSample))
				sampleList.add(nextSample);
		}
		
		treshold = generateTreshold(sampleList, function);
		
	}
	
	public static <E> double generateTreshold(List<E> list, Function<E, Double> function){
		
		double threshold = Double.MAX_VALUE;
		for(E element : list){
			double value = function.apply(element);
			if(value < threshold)
				threshold = value;
		}
		return threshold;
	}
	
}
