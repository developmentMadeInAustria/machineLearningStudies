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
		
		//puts every sample into a single cluster and puts it into clusterList
		for(T sample : samples){
			List<T> list = new ArrayList<>();
			list.add(sample);
			clusterList.add(new Cluster<T>(list));
		}
		
		//generates distances for all elements
		Iterator<T> tIterator = samples.iterator();
		for(T sample : samples){
			int i = 0;
			while(tIterator.next() != sample){i++;}
			for(;i<samples.size(); i++){
				List<T> list = new ArrayList<>();
				list.add(sample);
				T next = tIterator.next();
				list.add(next);
				distances.put(list, sample.distance(next));
			}
			tIterator = samples.iterator();
		}
		
		//merges always two cluster to one, until you have only the number of clusters, you want
		for(int i = samples.size(); i > clusters; i--){
			
			double smallestDistance = Double.MAX_VALUE;
			List<T> key = new ArrayList<>();
			Cluster<T> newCluster = new Cluster<>(new ArrayList<>());
			
			//finds smallest distance and saves it in key and smallestDistance
			for(List<T> list : distances.keySet()){
				if(distances.get(list) < smallestDistance){
					smallestDistance = distances.get(list);
					key = list;
				}
			}
			
			//merges Clusters 
			first:
			for(int j = 0; i < clusterList.size(); j++){
				Cluster<T> cluster = clusterList.get(j);
				if(cluster.contains(key.get(0))){
					second:
					for(; j < clusterList.size(); j++){
						Cluster<T> _cluster = clusterList.get(j);
						if(_cluster.contains(key.get(1)));{
							clusterList.remove(cluster);
							clusterList.remove(_cluster);
							List<T> list  = new ArrayList<>();
							list.addAll(cluster.getPoints());
							list.addAll(_cluster.getPoints());
							newCluster = new Cluster<>(list);
							clusterList.add(newCluster);
							break second;
						}
					}
					break first;
				}else if(cluster.contains(key.get(1))){
					second:
					for(; j < clusterList.size(); j++){
						Cluster<T> _cluster = clusterList.get(j);
						if(_cluster.contains(key.get(0)));{
							clusterList.remove(cluster);
							clusterList.remove(_cluster);
							List<T> list  = new ArrayList<>();
							list.addAll(cluster.getPoints());
							list.addAll(_cluster.getPoints());
							newCluster = new Cluster<>(list);
							clusterList.add(newCluster);
							break second;
						}
					}
					break first;
				}
			}
			//removes unnecessary distances
			for(List<T> list : distances.keySet()){
				if(newCluster.contains(list.get(0)) && newCluster.contains(list.get(1))){
					distances.remove(list);
				}
			}
		}
		
		return clusterList;
	}
	
}
