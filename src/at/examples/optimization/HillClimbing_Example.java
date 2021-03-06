package at.examples.optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.optimization.randomized.Hill_Climbing;

public class HillClimbing_Example {

	public static void main(String[] args) {
		
		List<Integer> sampleList = new ArrayList<>();
		Integer[] sampleArray = {2,4,6,7,6,4,5,7,9,11,10,11,16,15,14,16,17,20,18,17,15,16,13,12,15,13,9,7,6,5,9,7,5,3,4};
		sampleList = Arrays.asList(sampleArray);
		
		System.out.println(Hill_Climbing.hillClimbing(sampleList));
		System.out.println(Hill_Climbing.randomizedHillClimbing(sampleList, 10));
		
	}

}
