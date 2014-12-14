package org.wpattern.learning.object.ga.beans;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.wpattern.learning.object.beans.BaseBean;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.utils.ISortedList;
import org.wpattern.learning.object.ga.utils.IndividualComparator;
import org.wpattern.learning.object.ga.utils.SortedList;

public class PopulationBean extends BaseBean implements IPopulation {

	private static final long serialVersionUID = 201406081122L;

	private ISortedList<IndividualBean> population;

	private int defaultPopulationSize;

	public PopulationBean(int defaultPopulationSize) {
		Comparator<IndividualBean> comparator = new IndividualComparator();
		this.population = new SortedList<IndividualBean>(comparator);
		this.defaultPopulationSize = defaultPopulationSize;
	}

	@Override
	public void add(IndividualBean individual) {
		this.population.add(individual);
	}

	@Override
	public void addAll(List<IndividualBean> individuals) {
		this.population.addAll(individuals);
	}

	@Override
	public Iterator<IndividualBean> iterator() {
		return this.population.iterator();
	}

	@Override
	public IndividualBean getBestIndividual() {
		return this.population.peekFirst();
	}

	@Override
	public int getDefaultPopulationSize() {
		return this.defaultPopulationSize;
	}

	public void setDefaultPopulationSize(int defaultPopulationSize) {
		this.defaultPopulationSize = defaultPopulationSize;
	}

	@Override
	public IndividualBean get(int index) {
		return this.population.get(index);
	}

	@Override
	public int size() {
		return this.population.size();
	}

	@Override
	public IndividualBean removeWorstIndividual() {
		return this.population.pollLast();
	}

}
