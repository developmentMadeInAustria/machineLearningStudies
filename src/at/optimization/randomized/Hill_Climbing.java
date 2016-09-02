package at.optimization.randomized;

import java.util.List;

public abstract class Hill_Climbing {

	/* 
	 * an easy hill climbing algorithm 
	 * 
	 * starts with a random element and goes step for step to left or right
	 * 
	 * until it reaches a maximum
	 * 
	 */
	public static <T extends Comparable<T>> T hillClimbing(List<T> list){		
		
		int workingNumber = (int) Math.random() * (list.size() - 1); 
		T workingObject = list.get(workingNumber);
		
		if(checkRight(list, workingNumber)){
			workingObject = list.get(++workingNumber);
			while(true){
				if(checkRight(list, workingNumber))
					workingObject = list.get(++workingNumber);
				else
					break;
			}
		}else if(checkLeft(list, workingNumber)){
			workingObject = list.get(--workingNumber);
			while(true){
				if(checkLeft(list, workingNumber))
					workingObject = list.get(--workingNumber);
				else
					break;
			}
		}
		
		return workingObject;
	}
	
	/*
	 * a randomized hill climbing algorithm
	 * 
	 * which restarts after every try for loops-times
	 * 
	 * uses hillClimbing
	 * 
	 */
	public static <T extends Comparable<T>> T randomizedHillClimbing(List<T> list, int loops){
		
		T workingObject = hillClimbing(list);
		
		for(int i = 1; i < loops; i++){
			T testObject = hillClimbing(list);
			if(testObject.compareTo(workingObject) > 0)
				workingObject = testObject;
		}
		
		return workingObject;
		
	}
	
	
	//if left side has a greater value, return true
	private static <T extends Comparable<T>> boolean checkLeft(List<T> list, int objectPosition){
		
		if(objectPosition - 1 < 0)
			return false;
		
		if(list.get(objectPosition - 1).compareTo(list.get(objectPosition)) > 0)
			return true;
		else
			return false;
		
	}
	
	
	//if right side has a greater value, return true
	private static <T extends Comparable<T>> boolean checkRight(List<T> list, int objectPosition){
		
		if(objectPosition + 1 >= list.size())
			return false;
		
		if(list.get(objectPosition + 1).compareTo(list.get(objectPosition)) > 0)
			return true;
		else
			return false;
			
	}
	
	
	
	
}
