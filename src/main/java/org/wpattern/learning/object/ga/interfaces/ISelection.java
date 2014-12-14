package org.wpattern.learning.object.ga.interfaces;

import java.util.List;

import org.wpattern.learning.object.ga.beans.IndividualBean;

public interface ISelection {

	public List<IndividualBean> execute(IPopulation population, int number);

}
