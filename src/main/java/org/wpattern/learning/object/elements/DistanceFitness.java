package org.wpattern.learning.object.elements;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.IGraphic;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;

public class DistanceFitness implements IFitness {

	private static final Logger LOGGER = Logger.getLogger(DistanceFitness.class);

	private IGraphic graphic;

	private int counter;

	public DistanceFitness() {
		this.counter = 0;
	}

	@Override
	public int getCounter() {
		return this.counter;
	}

	@Override
	public void resetCounter() {
		this.counter = 0;
	}

	@Override
	public void config(IConfig config) {
		this.graphic = config.getGraphic();
	}

	@Override
	public void calculate(NodeBean node) {
		node.setFitness(this.calculateFitness(node.getLearningObjects()));

		if (this.graphic != null) {
			this.graphic.individualProcessed(node);
		}

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("Node [%s], fitness [%s] and learning objects [%s].", node.getIdentifier(),
					node.getFitness(), Utils.printSolution(node)));
		}
	}

	@Override
	public void calculate(IndividualBean individual) {
		individual.setFitness(this.calculateFitness(individual.getLearningObjects()));

		if (this.graphic != null) {
			this.graphic.individualProcessed(individual);
		}

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(String.format("Individual [%s], fitness [%s] and learning objects [%s].", individual.getIdentifier(),
					individual.getFitness(), Utils.printSolution(individual)));
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private long calculateFitness(List<LearningObjectBean> learningObjects) {
		long fitness = 0L;
		Set<String> constraints;
		Set<String> competencies = new HashSet<String>();

		for (int i = 0; i < learningObjects.size(); i++) {
			LearningObjectBean learningObject = learningObjects.get(i);
			int countConstraints = 0;
			int countLearningObject = 0;

			constraints = new HashSet<String>();

			if (learningObject.getConstraints() != null) {
				constraints.addAll(learningObject.getConstraints());

				for (int j = i - 1; (j >= 0) && !constraints.isEmpty(); j--) {
					LearningObjectBean learningObjectBackward = learningObjects.get(j);
					Iterator<String> iteratorConstraints = constraints.iterator();
					boolean anyConstraintSatisfied = false;

					while (iteratorConstraints.hasNext()) {
						String constraint = iteratorConstraints.next();

						if (learningObjectBackward.getCompetencies().contains(constraint)) {
							iteratorConstraints.remove();
							anyConstraintSatisfied = true;
						}
					}

					if (!constraints.isEmpty() && anyConstraintSatisfied && (learningObjectBackward.getConstraints() != null)) {
						constraints.addAll(learningObjectBackward.getConstraints());
					}

					if (!anyConstraintSatisfied) {
						countLearningObject++;
					}
				}

				for (String constraint : learningObject.getConstraints()) {
					if (!competencies.contains(constraint)) {
						countConstraints++;
					}
				}
			}

			fitness += (i + 1) * countConstraints + (i + 1) * countLearningObject;
			competencies.addAll(learningObject.getCompetencies());
		}

		return fitness;
	}

}
