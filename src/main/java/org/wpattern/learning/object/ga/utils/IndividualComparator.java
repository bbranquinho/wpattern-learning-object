package org.wpattern.learning.object.ga.utils;

import java.util.Comparator;

import org.wpattern.learning.object.ga.beans.IndividualBean;

public class IndividualComparator  implements Comparator<IndividualBean> {

	@Override
	public int compare(IndividualBean value1, IndividualBean value2) {
		if (value1.getFitness() == null) {
			return -1;
		}

		if (value2.getFitness() == null) {
			return 1;
		}

		return Utils.compareDouble(value1.getFitness(), value2.getFitness()).getCode();
	}

}
