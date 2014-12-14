package org.wpattern.learning.object.ga.elements;

import java.util.Random;

import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IMutation;

public class Mutation implements IMutation {

	private Random random;

	private double mutationProbability;

	public Mutation(double mutationProbability) {
		this.random = new Random(System.currentTimeMillis());
		this.mutationProbability = mutationProbability;
	}

	@Override
	public void config(IConfig config) {
	}

	@Override
	public void execute(IndividualBean individual) {
		if (this.random.nextDouble() > this.mutationProbability) {
			return;
		}

		int size =  individual.getLearningObjects().size();

		int index1 = this.random.nextInt(size);
		int index2 = this.random.nextInt(size);

		LearningObjectBean learningObject1 = individual.getLearningObjects().get(index1);
		LearningObjectBean learningObject2 = individual.getLearningObjects().get(index2);

		individual.getLearningObjects().set(index1, learningObject2);
		individual.getLearningObjects().set(index2, learningObject1);
	}

	@Override
	public String toString() {
		return String.format("%s, Mutation Probability [%s]", this.getClass().getSimpleName(), this.mutationProbability);
	}

}
