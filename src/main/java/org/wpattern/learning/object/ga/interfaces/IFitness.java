package org.wpattern.learning.object.ga.interfaces;

import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.search.beans.NodeBean;

public interface IFitness {

	public int getCounter();

	public void resetCounter();

	public void config(IConfig config);

	public void calculate(IndividualBean individual);

	public void calculate(NodeBean individual);

}
