package org.wpattern.learning.object.ga.interfaces;

public interface IConfig {

	public ICrossover getCrossover();

	public ISelection getSelection();

	public INaturalSelection getNaturalSelection();

	public IFitness getFitness();

	public IPopulationGenerator getPopulationGenerator();

	public IMutation getMutation();

	public IGraphic getGraphic();

	public INodeGenerator getNodeGenerator();

}
