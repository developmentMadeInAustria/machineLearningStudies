package at.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class SingleLinkageClustering {
	
	public static <T extends Distancable<T>> List<Cluster<T>> singleLinkageClustering(List<T> samples, int clusters){
		
		List<Cluster<T>> clusterList = new ArrayList<>();
		Map<List<T>, Double> distances = new HashMap<>();
		for(T sample : samples){
			List<T> list = new ArrayList<>();
			list.add(sample);
			clusterList.add(new Cluster<T>(list));
		}
		
		Iterator<T> tIterator = samples.iterator();
		for(T sample : samples){
			int i = 1;
			while(tIterator.next() != sample){i++;}
			for(;i<samples.size(); i++){
				List<T> list = new ArrayList<>();
				list.add(sample);
				T next = tIterator.next();
				list.add(next);
				distances.put(list, sample.distance(next));
			}
		}
		while(clusterList.size() > clusters){
			/*
			 * Here will be the code, where the clusters merge together 
			 * 
			 * until the size of the clusterList fits
			 * 
			 */
		}
		
		return clusterList;
	}
	
}
