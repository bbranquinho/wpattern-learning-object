package org.wpattern.learning.object.ga.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.interfaces.ISelection;

public class RandomSelection implements ISelection {

	private Random random;

	public RandomSelection() {
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public List<IndividualBean> execute(IPopulation population, int number) {
		List<IndividualBean> individuals = new ArrayList<IndividualBean>(number);

		for (int i = 0; i < number; i++) {
			individuals.add(population.get(this.random.nextInt(population.size())));
		}

		return individuals;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
