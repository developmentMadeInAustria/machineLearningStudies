package at.clustering;

import java.util.List;

public class Cluster<T> {

	private List<T> points;
	private T center;
	
	public Cluster(List<T> points){
		this(points, null);
	}
	public Cluster(List<T> points, T center){
		this.points = points;
		this.center = center;
	}
	
	public void addPoint(T point){
		points.add(point);
	}
	public void setPoints(List<T> points){
		this.points = points;
	}
	public List<T> getPoints(){
		return points;
	}
	public void setCenter(T center){
		this.center = center;
	}
	public T getCenter(){
		return center;
	}
	public boolean contains(T point){
		return points.contains(point);
	}
	
}
