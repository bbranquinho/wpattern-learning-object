package org.wpattern.learning.object.ga.interfaces;

import java.util.List;

import org.wpattern.learning.object.ga.beans.IndividualBean;

public interface ICrossover {

	public void config(IConfig config);

	public List<IndividualBean> execute(IndividualBean individual1, IndividualBean individual2);

}
