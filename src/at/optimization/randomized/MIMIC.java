package at.optimization.randomized;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class MIMIC {

	public static <T extends Distribution<E>, E extends GivingVariables> E mimicAlgorithm(T distribution, Function<E, Double> function){
		
		List<E> sampleList = new ArrayList<>();
		
	}
	
}
