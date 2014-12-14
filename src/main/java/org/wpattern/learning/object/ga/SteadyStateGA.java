package org.wpattern.learning.object.ga;

import java.util.List;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IGeneticAlgorithm;
import org.wpattern.learning.object.ga.utils.CompareType;
import org.wpattern.learning.object.ga.utils.Utils;

public class SteadyStateGA extends AbstractGA implements IGeneticAlgorithm {

	private static final Logger LOGGER = Logger.getLogger(SteadyStateGA.class);

	private double bestFitness;

	private int currentIteration;

	public SteadyStateGA(int populationSize, int maxEvaluation) {
		super(populationSize, maxEvaluation);
	}

	@Override
	public void execute() {
		this.population = this.populationGenerator.execute(this.populationSize);
		this.bestFitness = Double.MAX_VALUE;
		this.currentIteration = 0;

		do {
			// Selection
			List<IndividualBean> selectedIndividuals = this.selection.execute(this.population, 2);

			// Crossover
			for (IndividualBean individual : this.crossover.execute(selectedIndividuals.get(0), selectedIndividuals.get(1))) {
				// Mutation
				this.mutation.execute(individual);

				// Evaluation
				this.fitness.calculate(individual);

				// Insert new individual to the population
				this.population.add(individual);
			}

			// Natural Selection
			this.naturalSelection.execute(this.population);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Best individual [%s].", Utils.printSolution(this.population.getBestIndividual())));
			}
		} while (!this.isSolutionFounded());
	}

	private boolean isSolutionFounded() {
		double fitness = this.population.getBestIndividual().getFitness();

		if (this.bestFitness <= fitness) {
			this.currentIteration++;
		} else {
			this.currentIteration = 0;
			this.bestFitness = fitness;
		}

		return (this.currentIteration > this.maxEvaluation) || (Utils.compareDouble(this.bestFitness, 0.0d) == CompareType.EQUAL);
	}

}
