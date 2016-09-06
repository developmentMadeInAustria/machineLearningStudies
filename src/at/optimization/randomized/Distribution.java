package at.optimization.randomized;

public interface Distribution<E extends GivingVariables> {

	public E generateSample();
	
}
