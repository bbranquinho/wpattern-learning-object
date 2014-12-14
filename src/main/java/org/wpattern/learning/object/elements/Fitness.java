package org.wpattern.learning.object.elements;

import java.util.HashSet;
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

public class Fitness implements IFitness {

	private static final Logger LOGGER = Logger.getLogger(Fitness.class);

	private IGraphic graphic;

	private int counter;

	public Fitness() {
		this.resetCounter();
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
		this.counter++;
		long fitness = 0L;
		int learningObjectIndex = 1;
		Set<String> competencies = new HashSet<String>();

		for (LearningObjectBean learningObject : learningObjects) {
			int countConstraints = 0;

			if (learningObject.getConstraints() != null) {
				for (String constraint : learningObject.getConstraints()) {
					if (!competencies.contains(constraint)) {
						countConstraints++;
					}
				}
			}

			fitness += learningObjectIndex * countConstraints;

			learningObjectIndex++;

			competencies.addAll(learningObject.getCompetencies());
		}

		return fitness;
	}

}
