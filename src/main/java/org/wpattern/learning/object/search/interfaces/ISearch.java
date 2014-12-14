package org.wpattern.learning.object.search.interfaces;

import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.search.beans.NodeBean;


public interface ISearch {

	public void config(IConfig config);

	public NodeBean find();

	public NodeBean find(NodeBean start);

}
