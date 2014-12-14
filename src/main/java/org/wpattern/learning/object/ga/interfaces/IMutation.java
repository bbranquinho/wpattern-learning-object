package org.wpattern.learning.object.ga.interfaces;

import org.wpattern.learning.object.ga.beans.IndividualBean;

public interface IMutation {

	public void config(IConfig config);

	public void execute(IndividualBean individual);

}
