package at.optimization.randomized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
		Map<List<V>, Double> mutualInformation = mutualInformationBinary(variables);
		
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
	public static <T, V, E> double calculateProbabilitiesBinary(Map<E, Map<V,Boolean>> variablesMap, List<T> variables){
		
		double summedValues = 0.0;
		Set<E> keys = variablesMap.keySet();
		for(E keyItem : keys){
			summedValues += variablesMap.get(keyItem).get(variables.get(0)) && 
					variablesMap.get(keyItem).get(variables.get(1)) ? 1 : 0;
		}
		return summedValues/variablesMap.size();
		
	}
	
	/*
	 *an algorithm, which calculates the mutualInformation for a Map of elements with boolean variables
	 *
	 * returns the mutual information of the variables in a Map with a List<two variables> as key and 
	 * their mutual information (Double)
	 */
	public static <V,E> Map<List<V>, Double> mutualInformationBinary(Map<E, Map<V,Boolean>> variables){
		
		Map<List<V>, Double> mutualInformationMap = new HashMap<>();
		Map<V, Double> singleProbabilities = calculateProbabilitiesBinary(variables);
		Iterator<V> variableIterator = singleProbabilities.keySet().iterator();
		
		for(V variable : singleProbabilities.keySet()){
			while(variableIterator.next() != variable){}
			while(variableIterator.hasNext()){
				V next = variableIterator.next();
				List<V> bothVariables = new ArrayList<>();
				bothVariables.add(variable);
				bothVariables.add(next);
				double probabilityOfBoth = calculateProbabilitiesBinary(variables, bothVariables);
				double mutualInformation = Math.log(probabilityOfBoth/(singleProbabilities.get(variable)
						* singleProbabilities.get(next)));
				mutualInformationMap.put(bothVariables, mutualInformation);
				variableIterator = singleProbabilities.keySet().iterator();
			}
		}
		
		return mutualInformationMap;	
	}
	
}
