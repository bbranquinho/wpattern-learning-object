package org.wpattern.learning.object.ga.elements;

import java.util.ArrayList;
import java.util.List;

import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.interfaces.ISelection;

public class TournamentSelection implements ISelection {

	private int n;

	private ISelection rouletteSelection;

	public TournamentSelection(int n) {
		this.n = n;
		this.rouletteSelection = new RouletteSelection();
	}

	@Override
	public List<IndividualBean> execute(IPopulation population, int number) {
		List<IndividualBean> individuals = new ArrayList<IndividualBean>();

		for (int i = 0; i < number; i++) {
			List<IndividualBean> rouletteIndividuals = this.rouletteSelection.execute(population, this.n);
			IndividualBean selectedIndividual = null;
			double minFitness = Double.MAX_VALUE;

			for (int j = 0; j < rouletteIndividuals.size(); j++) {
				if (rouletteIndividuals.get(j).getFitness() < minFitness) {
					minFitness = rouletteIndividuals.get(j).getFitness();
					selectedIndividual = rouletteIndividuals.get(j);
				}
			}

			individuals.add(selectedIndividual);
		}

		return individuals;
	}

	@Override
	public String toString() {
		return String.format("%s, n [%s]", this.getClass().getSimpleName(), this.n);
	}

}
