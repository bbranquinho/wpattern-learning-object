package org.wpattern.learning.object.ga.interfaces;

import java.util.Iterator;
import java.util.List;

import org.wpattern.learning.object.ga.beans.IndividualBean;

public interface IPopulation {

	public IndividualBean getBestIndividual();

	public IndividualBean get(int index);

	public Iterator<IndividualBean> iterator();

	public int size();

	public IndividualBean removeWorstIndividual();

	public void add(IndividualBean individual);

	public void addAll(List<IndividualBean> individuals);

	public int getDefaultPopulationSize();

}
