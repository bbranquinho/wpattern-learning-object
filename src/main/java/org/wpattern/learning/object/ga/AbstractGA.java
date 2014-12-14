package org.wpattern.learning.object.ga;

import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.ICrossover;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.IGeneticAlgorithm;
import org.wpattern.learning.object.ga.interfaces.IMutation;
import org.wpattern.learning.object.ga.interfaces.INaturalSelection;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.interfaces.IPopulationGenerator;
import org.wpattern.learning.object.ga.interfaces.ISelection;

public abstract class AbstractGA implements IGeneticAlgorithm {

	protected IPopulation population;

	protected ICrossover crossover;

	protected ISelection selection;

	protected INaturalSelection naturalSelection;

	protected IFitness fitness;

	protected IPopulationGenerator populationGenerator;

	protected IMutation mutation;

	protected int populationSize;

	protected int maxEvaluation;

	public AbstractGA(int populationSize, int maxEvaluation) {
		this.populationSize = populationSize;
		this.maxEvaluation = maxEvaluation;
	}

	@Override
	public void config(IConfig config) {
		this.crossover = config.getCrossover();
		this.selection = config.getSelection();
		this.naturalSelection = config.getNaturalSelection();
		this.populationGenerator = config.getPopulationGenerator();
		this.fitness = config.getFitness();
		this.mutation = config.getMutation();

		this.populationGenerator.config(config);
		this.crossover.config(config);
		this.mutation.config(config);
		this.naturalSelection.config(config);
		this.fitness.config(config);
	}

	@Override
	public IPopulation getPopulation() {
		return this.population;
	}

	@Override
	public String toString() {
		return String.format("%s, Population Size [%s], Maximum Evaluation [%s]",
				this.getClass().getSimpleName(), this.populationSize, this.maxEvaluation);
	}

}
