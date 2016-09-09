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
	public static <T extends Distribution<E>, E extends GivingVariables<V,Boolean>,V> List<SpanningTree<V,E>> binaryMimicAlgorithm(T distribution, Function<E, Double> function){
		
		Map<E, Double> sampleMap = new HashMap<>();
		List<SpanningTree<V,E>> spanningTrees = new ArrayList<>();
		double threshold;
		
		for(int i = 0; i < 10000; i++){
			E nextSample = distribution.generateSample();
			if(!sampleMap.containsKey(nextSample))
				sampleMap.put(nextSample, function.apply(nextSample));
		}
		
		List<E> lowestSamples = new ArrayList<>();
		final int mapSize = sampleMap.size();
		for(E element : sampleMap.keySet()){
			lowestSamples.add(element);
		}
		lowestSamples.sort((e1, e2) -> {return sampleMap.get(e1) > sampleMap.get(e2) ?  
				1 : -1;});
		for(int i = 0; i < mapSize/2; i++){
			lowestSamples.remove(lowestSamples.size() - 1);
		}
		sampleMap.remove(lowestSamples);
		threshold = generateThreshold(sampleMap);
		
		Map<E, Map<V,Boolean>> variables = new HashMap<>();
		for(E element : sampleMap.keySet()){
			variables.put(element, element.returnVariables());
		}
		Set<V> variablesList = sampleMap.keySet().iterator().next().returnVariables().keySet();
		Map<List<V>, Double> mutualInformation = mutualInformationBinary(variables);
		SpanningTree<V,E> tree = primAlgorithm(variablesList, mutualInformation);
		tree.setSampleMap(sampleMap);
		
		
		return spanningTrees;
	}
	
	public static <E> double generateThreshold(Map<E, Double> map){
		
		double threshold = Double.MAX_VALUE;
		for(E element : map.keySet()){
			double value = map.get(element);
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
	
	/*
	 * generates a minimum spanning tree with the prim algorithm
	 */
	public static <T,E> SpanningTree<T,E> primAlgorithm(Set<T> variables, Map<List<T>, Double> mutualInformation){
		
		T rootElement = variables.iterator().next();
		Map<T,T> parentMap = new HashMap<>();
		List<T> usedElements = new ArrayList<>();
		List<List<T>> usingKeys = new ArrayList<>();
		Map<List<T>, Double> removeMap = mutualInformation;
		usedElements.add(rootElement);
		for(List<T> elementList : mutualInformation.keySet()){
			if(elementList.contains(rootElement)){
				usingKeys.add(elementList);
				removeMap.remove(elementList);
			}	
		}
		variables.remove(rootElement);
		while(variables.size() > 0){
			T lowestVariable = variables.iterator().next();
			T parent = null;
			double lowestValue = Double.MAX_VALUE;
			for(List<T> elements : usingKeys){
				if(mutualInformation.get(elements) < lowestValue){
					lowestValue = mutualInformation.get(elements);
					lowestVariable = usedElements.contains(elements.get(0)) ? elements.get(1) : elements.get(0);
					parent = usedElements.contains(elements.get(0)) ? elements.get(0) : elements.get(1);
				}
			}
			for(List<T> list : usingKeys){
				if(list.contains(lowestVariable))
					usingKeys.remove(list);
			}
			for(List<T> elementList : removeMap.keySet()){
				if(elementList.contains(lowestVariable)){
					usingKeys.add(elementList);
					removeMap.remove(elementList);
				}
			}
			usedElements.add(lowestVariable);
			variables.remove(lowestVariable);
			parentMap.put(lowestVariable, parent);
		}
		
		return new SpanningTree<T,E>(rootElement, parentMap);
	}
	
}
