package org.wpattern.learning.object.beans;

import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.ICrossover;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.IGraphic;
import org.wpattern.learning.object.ga.interfaces.IMutation;
import org.wpattern.learning.object.ga.interfaces.INaturalSelection;
import org.wpattern.learning.object.ga.interfaces.INodeGenerator;
import org.wpattern.learning.object.ga.interfaces.IPopulationGenerator;
import org.wpattern.learning.object.ga.interfaces.ISelection;

public class ConfigBean extends BaseBean implements IConfig {

	private static final long serialVersionUID = 201406081111L;

	private ICrossover crossover;

	private ISelection selection;

	private INaturalSelection naturalSelection;

	private IFitness fitness;

	private IPopulationGenerator populationGenerator;

	private IMutation mutation;

	private INodeGenerator nodeGenerator;

	private IGraphic graphic;

	public ConfigBean() {
	}

	@Override
	public ICrossover getCrossover() {
		return this.crossover;
	}

	public void setCrossover(ICrossover crossover) {
		this.crossover = crossover;
	}

	@Override
	public ISelection getSelection() {
		return this.selection;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	@Override
	public INaturalSelection getNaturalSelection() {
		return this.naturalSelection;
	}

	public void setNaturalSelection(INaturalSelection naturalSelection) {
		this.naturalSelection = naturalSelection;
	}

	@Override
	public IFitness getFitness() {
		return this.fitness;
	}

	public void setFitness(IFitness fitness) {
		this.fitness = fitness;
	}

	@Override
	public IPopulationGenerator getPopulationGenerator() {
		return this.populationGenerator;
	}

	public void setPopulationGenerator(IPopulationGenerator populationGenerator) {
		this.populationGenerator = populationGenerator;
	}

	@Override
	public IMutation getMutation() {
		return this.mutation;
	}

	public void setMutation(IMutation mutation) {
		this.mutation = mutation;
	}

	@Override
	public IGraphic getGraphic() {
		return this.graphic;
	}

	public void setGraphic(IGraphic graphic) {
		this.graphic = graphic;
	}

	@Override
	public INodeGenerator getNodeGenerator() {
		return this.nodeGenerator;
	}

	public void setNodeGenerator(INodeGenerator nodeGenerator) {
		this.nodeGenerator = nodeGenerator;
	}

}
