package org.wpattern.learning.object.ga.utils;

import java.util.Comparator;

import org.wpattern.learning.object.search.beans.NodeBean;

public class NodeComparator  implements Comparator<NodeBean> {

	@Override
	public int compare(NodeBean value1, NodeBean value2) {
		if (value1.getFitness() == null) {
			return -1;
		}

		if (value2.getFitness() == null) {
			return 1;
		}

		return Utils.compareDouble(value1.getFitness(), value2.getFitness()).getCode();
	}

}
