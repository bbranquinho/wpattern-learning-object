package org.wpattern.learning.object.search;

import java.util.ArrayList;
import java.util.List;

import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.search.beans.NodeBean;

public class SearchUtils {

	public static List<NodeBean> getNeighbors(NodeBean current, IFitness fitnessMethod) {
		List<NodeBean> neighbors = new ArrayList<NodeBean>();
		int lenght = current.getLearningObjects().size();
		NodeBean neighbor;

		for (int i = 0; i < lenght; i++) {
			for (int j = i + 1; j < lenght; j++) {
				neighbor = current.clone();

				LearningObjectBean aux = neighbor.getLearningObjects().get(i);
				neighbor.getLearningObjects().set(i, neighbor.getLearningObjects().get(j));
				neighbor.getLearningObjects().set(j, aux);

				fitnessMethod.calculate(neighbor);

				neighbors.add(neighbor);
			}
		}

		return neighbors;
	}

}
