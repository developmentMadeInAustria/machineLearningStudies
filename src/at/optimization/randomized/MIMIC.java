package at.optimization.randomized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
		
		Map<E, Map<V,Boolean>> variables = new HashMap<>();
		for(E element : sampleList){
			variables.put(element, element.returnVariables());
		}
		Map<V, Double> probabilityOfVariables = calculateProbabilitiesBinary(variables);
		
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
	
	/*
	 * calculates the probability of a binary variable in the given Map
	 * 
	 * returns a Map with a probability for each variable
	 */
	public static <V,E> Map<V, Double> calculateProbabilitiesBinary(Map<E, Map<V,Boolean>> variablesMap){
		
		Map<V, Double> probabilityOfVariables = new HashMap<>();
		Set<E> keys = variablesMap.keySet();
		Set<V> variables = variablesMap.get(keys.iterator().next()).keySet();
		for(V key : variables){
			probabilityOfVariables.put(key, 0.0);
		}
		Map<V, Double> summedValues = new HashMap<>();
		for(E keyItem : keys){
			for(V variable : variables){
				double value = variablesMap.get(keyItem).get(variable) ? 1 : 0;
				if(summedValues.containsKey(variable)){
					summedValues.replace(variable, summedValues.get(variable) + value);
				}else{
					summedValues.put(variable, value);
				}	
			}
		}
		for(V key : probabilityOfVariables.keySet()){
			probabilityOfVariables.replace(key, summedValues.get(key)/variablesMap.size());
		}
		
		return probabilityOfVariables;
		
	}
	
	/*
	 * calculates the probability of two binary variables, both being true
	 * 
	 * returns the probability of both being true
	 */
	public static <T, V, E> double calculateProbabilitiesBinary(Map<E, Map<V,Boolean>> variablesMap, T[] variables){
		
		double summedValues = 0.0;
		Set<E> keys = variablesMap.keySet();
		for(E keyItem : keys){
			summedValues += variablesMap.get(keyItem).get(variables[0]) && 
					variablesMap.get(keyItem).get(variables[1]) ? 1 : 0;
		}
		return summedValues/variablesMap.size();
		
	}
	
}
