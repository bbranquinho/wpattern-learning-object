package org.wpattern.learning.object.ga.elements;

import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.INaturalSelection;
import org.wpattern.learning.object.ga.interfaces.IPopulation;

public class BestNaturalSelection implements INaturalSelection {

	public BestNaturalSelection() {
	}

	@Override
	public void config(IConfig config) {
	}

	@Override
	public void execute(IPopulation population) {
		while (population.size() > population.getDefaultPopulationSize()) {
			population.removeWorstIndividual();
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
