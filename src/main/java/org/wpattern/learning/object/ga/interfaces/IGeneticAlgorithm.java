package org.wpattern.learning.object.ga.interfaces;

public interface IGeneticAlgorithm {

	public void config(IConfig config);
	
	public void execute();
	
	public IPopulation getPopulation();
	
}
