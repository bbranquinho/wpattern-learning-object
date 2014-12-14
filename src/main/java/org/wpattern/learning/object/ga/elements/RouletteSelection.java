package org.wpattern.learning.object.ga.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.interfaces.ISelection;
import org.wpattern.learning.object.ga.utils.Utils;

public class RouletteSelection implements ISelection {

	private Random random;

	public RouletteSelection() {
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public List<IndividualBean> execute(IPopulation population, int number) {
		List<RankIndividual> rank = new ArrayList<RankIndividual>();
		Iterator<IndividualBean> iterator = population.iterator();
		int index = 0;

		while (iterator.hasNext()) {
			IndividualBean individual = iterator.next();
			rank.add(new RankIndividual(index++, individual.getFitness()));
		}

		Collections.sort(rank, new Comparator<RankIndividual>() {
			@Override
			public int compare(RankIndividual o1, RankIndividual o2) {
				return Utils.compareDouble(o1.getFitness(), o2.getFitness()).getCode();
			}
		});

		double totalFitness = rank.get(0).getFitness();

		for (int i = 1; i < rank.size(); i++) {
			rank.get(i).setFitness(rank.get(i).getFitness() + rank.get(i - 1).getFitness());
			totalFitness = rank.get(i).getFitness();
		}

		List<IndividualBean> individuals = new ArrayList<IndividualBean>();

		for (int j = 0; j < number; j++) {
			double fitnessIndividual = this.random.nextDouble() * totalFitness;
			index = rank.get(rank.size() - 1).getIndex();

			for (int i = 1; i < rank.size(); i++) {
				if (fitnessIndividual <= rank.get(i).getFitness()) {
					index = rank.get(i).getIndex();
					break;
				}
			}

			individuals.add(population.get(index));
		}

		return individuals;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private class RankIndividual {

		private int index;

		private double fitness;

		public RankIndividual(int index, double fitness) {
			super();
			this.index = index;
			this.fitness = fitness;
		}

		public int getIndex() {
			return this.index;
		}

		public double getFitness() {
			return this.fitness;
		}

		public void setFitness(double fitness) {
			this.fitness = fitness;
		}

	}

}
