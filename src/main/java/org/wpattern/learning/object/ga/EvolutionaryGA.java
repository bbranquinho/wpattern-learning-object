package org.wpattern.learning.object.ga;

import java.util.List;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IGeneticAlgorithm;
import org.wpattern.learning.object.ga.utils.CompareType;
import org.wpattern.learning.object.ga.utils.Utils;

public class EvolutionaryGA extends AbstractGA implements IGeneticAlgorithm {

	private static final Logger LOGGER = Logger.getLogger(EvolutionaryGA.class);

	private int countEvaluation;

	private double selectionPercentual;

	public EvolutionaryGA(double selectionPercentual, int populationSize, int maxEvaluation) {
		super(populationSize, maxEvaluation);

		this.selectionPercentual = selectionPercentual;
	}

	@Override
	public void execute() {
		this.countEvaluation = 0;
		int amountIndividual = this.amountIndividual();
		this.population = this.populationGenerator.execute(this.populationSize);
		boolean solutionFounded = false;

		do {
			// Selection
			List<IndividualBean> individuals = this.selection.execute(this.population, amountIndividual);

			// Crossover
			for (int i = 0; (i < individuals.size()) && !solutionFounded; i += 2) {
				for (IndividualBean individual : this.crossover.execute(individuals.get(i + 0), individuals.get(i + 1))) {
					// Mutation
					this.mutation.execute(individual);

					// Evaluation
					this.fitness.calculate(individual);

					// Insert new individual to the population
					this.population.add(individual);

					if (Utils.compareDouble(individual.getFitness(), 0.0d) == CompareType.EQUAL) {
						solutionFounded = true;
						break;
					}
				}
			}

			// Natural Selection
			this.naturalSelection.execute(this.population);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Best individual [%s].", Utils.printSolution(this.population.getBestIndividual())));
			}
		} while (!this.isSolutionFounded());
	}

	private boolean isSolutionFounded() {
		return (++this.countEvaluation > this.maxEvaluation) ||
				(Utils.compareDouble(this.population.getBestIndividual().getFitness(), 0.0d) == CompareType.EQUAL);
	}

	private int amountIndividual() {
		int amount = (int) (this.populationSize * this.selectionPercentual);

		return amount + amount % 2;
	}

}
