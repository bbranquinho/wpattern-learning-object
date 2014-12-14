package org.wpattern.learning.object.ga.interfaces;

import org.wpattern.learning.object.search.beans.NodeBean;


public interface INodeGenerator {

	public void config(IConfig config);

	public NodeBean create();

}
