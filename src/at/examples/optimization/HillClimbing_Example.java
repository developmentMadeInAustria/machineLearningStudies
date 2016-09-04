package at.examples.optimization;

import java.util.ArrayList;
import java.util.List;

import at.optimization.randomized.Hill_Climbing;

public class HillClimbing_Example {

	public static void main(String[] args) {
		
		List<Integer> sampleList = new ArrayList<>();
		
		sampleList.add(2);
		sampleList.add(4);
		sampleList.add(5);
		sampleList.add(6);
		sampleList.add(3);
		sampleList.add(2);
		sampleList.add(0);
		sampleList.add(1);
		sampleList.add(2);
		sampleList.add(4);
		sampleList.add(5);
		sampleList.add(6);
		sampleList.add(7);
		sampleList.add(9);
		sampleList.add(11);
		sampleList.add(10);
		sampleList.add(9);
		sampleList.add(12);
		sampleList.add(10);
		sampleList.add(11);
		sampleList.add(13);
		sampleList.add(14);
		sampleList.add(16);
		sampleList.add(17);
		sampleList.add(16);
		sampleList.add(14);
		sampleList.add(13);
		sampleList.add(11);
		
		System.out.println(Hill_Climbing.hillClimbing(sampleList));
		System.out.println(Hill_Climbing.randomizedHillClimbing(sampleList, 10));
		
	}

}
