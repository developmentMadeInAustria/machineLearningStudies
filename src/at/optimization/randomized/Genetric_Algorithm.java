package at.optimization.randomized;

import java.util.Collections;
import java.util.List;

public abstract class Genetric_Algorithm {

	public static <T extends Crossoverable<T> & Comparable<T>> List<T> genetricAlgorithm(List<T> list, int generations){
		
		for(int i = 0; i < generations; i++){
			Collections.sort(list);
			Collections.reverse(list);
			List<T> nextGenerationList = list.subList(0, (int) (list.size() - 1) / 2);
			if(nextGenerationList.size() % 2 != 0){
				nextGenerationList.remove(list.size() - 1);
			}
			list = nextGenerationList;
			for(int j = 0; j < 2; j++){
				Collections.shuffle(nextGenerationList);
				for(int k = 0; k < nextGenerationList.size(); k++){
					list.add(nextGenerationList.get(k).crossover(nextGenerationList.get(++k)));
				}
			}
		}
		
		return list;
	}
}
