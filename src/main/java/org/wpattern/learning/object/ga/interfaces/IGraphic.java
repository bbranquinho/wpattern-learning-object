package org.wpattern.learning.object.ga.interfaces;

import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.search.beans.NodeBean;

public interface IGraphic {

	public void individualProcessed(IndividualBean individual);

	public void individualProcessed(NodeBean node);

	public void process();

}
