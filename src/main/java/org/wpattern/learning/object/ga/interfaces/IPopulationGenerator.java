package org.wpattern.learning.object.ga.interfaces;


public interface IPopulationGenerator {

	public void config(IConfig config);

	public IPopulation execute(int populationSize);

}
