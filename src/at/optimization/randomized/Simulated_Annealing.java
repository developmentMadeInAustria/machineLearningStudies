package at.optimization.randomized;

import java.util.List;
import java.util.Random;

public abstract class Simulated_Annealing {

	public static int left = -1;
	public static int right = 1;
	
	private static Random rnd = new Random();
	
	public static <T extends Comparable<T>> T simulatedAnnealing(List<T> list, double temperature, double coolDownRate){
		
		T actualSample = list.get(rnd.nextInt(list.size()-1));
		int direction;
		int searchSpace = 3;
		
		if(list.indexOf(actualSample) == 0)
			direction = left;
		else if(list.indexOf(actualSample) == list.size() - 1)
			direction = right;
		else
			direction = rnd.nextBoolean() ? left : right;
			
		while(temperature >= 0.05){
			T testSample = findSample(list, searchSpace, list.indexOf(actualSample), direction);
			
			if(testSample.compareTo(actualSample) > 0){
				actualSample = testSample;
				temperature = coolDown(temperature, coolDownRate);
				if(list.indexOf(actualSample) == 0)
					direction = left;
				else if(list.indexOf(actualSample) == list.size() - 1)
					direction = right;
			}else{
				if(Math.exp(testSample.compareTo(actualSample)/temperature) >= 0.5){
					actualSample = testSample;
					temperature = coolDown(temperature, coolDownRate);
					if(list.indexOf(actualSample) == 0)
						direction = left;
					else if(list.indexOf(actualSample) == list.size() - 1)
						direction = right;
				}else{
					temperature = coolDown(temperature, coolDownRate);
					direction = direction == left ? right: left;
				}
			}
		}
		
		return actualSample;
		
	}
	
	/*
	 * looks in the area of the searchSpace for a new sample
	 * 
	 * comingDirection: -1, equals left; 1 equals right
	 * 
	 */
	private static <T> T findSample(List<T> list, int searchSpace, int actualSample, int comingDirection){
		
		int walkingNumber = (rnd.nextInt(searchSpace + 1)) * (-1 * comingDirection);
		
		if(actualSample > 0 || actualSample < list.size() - 1){
			if(actualSample + walkingNumber <= 0)
				return list.get(0);
			if(actualSample + walkingNumber >= list.size() - 1)
				return list.get(list.size() - 1);
			else
				return list.get(actualSample + walkingNumber); 
		}else if(actualSample == 0){
			if(comingDirection == left)
				return list.get(actualSample + walkingNumber);
			else
				return list.get(0);
		}else{
			if(comingDirection == right)
				return list.get(actualSample + walkingNumber);
			else
				return list.get(list.size() - 1);
		}	
	}
	
	// coolDownRate has to be between 0 and 1
	private static double coolDown(double temperature, double coolDownRate){
		return temperature * (1 - coolDownRate);
	}
}
