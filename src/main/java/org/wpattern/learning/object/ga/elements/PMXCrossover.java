package org.wpattern.learning.object.ga.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.ICrossover;

public class PMXCrossover implements ICrossover {

	private Random random;

	public PMXCrossover() {
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void config(IConfig config) {
	}

	@Override
	public List<IndividualBean> execute(IndividualBean individual1, IndividualBean individual2) {
		List<IndividualBean> individuals = new ArrayList<IndividualBean>();
		int numberLearningObjects = individual1.getLearningObjects().size();

		int index1 = this.random.nextInt(numberLearningObjects);
		int index2 = this.random.nextInt(numberLearningObjects);

		if (index2 < index1) {
			int aux = index2;
			index2 = index1;
			index1 = aux;
		}

		IndividualBean newIndividual1 = new IndividualBean();
		IndividualBean newIndividual2 = new IndividualBean();

		for (int i = 0; i < numberLearningObjects; i++) {
			if ((i < index1) || (i > index2)) {
				newIndividual1.addLearningObject(individual1.getLearningObjects().get(i));
				newIndividual2.addLearningObject(individual2.getLearningObjects().get(i));
			} else {
				newIndividual1.addLearningObject(individual2.getLearningObjects().get(i));
				newIndividual2.addLearningObject(individual1.getLearningObjects().get(i));
			}
		}

		List<Integer> repeatedIndex1 = new ArrayList<Integer>();
		List<Integer> repeatedIndex2 = new ArrayList<Integer>();
		int indexRepeated;

		for (int i = index1; i <= index2; i++) {
			indexRepeated = this.repeatedObject(newIndividual1.getLearningObjects(), i, index1, index2);

			if (indexRepeated >= 0) {
				repeatedIndex1.add(indexRepeated);
			}

			indexRepeated = this.repeatedObject(newIndividual2.getLearningObjects(), i, index1, index2);

			if (indexRepeated >= 0) {
				repeatedIndex2.add(indexRepeated);
			}
		}

		for (int i = 0; i < repeatedIndex1.size(); i++) {
			LearningObjectBean learningObject1 = newIndividual1.getLearningObjects().get(repeatedIndex1.get(i));
			LearningObjectBean learningObject2 = newIndividual2.getLearningObjects().get(repeatedIndex2.get(i));

			newIndividual1.getLearningObjects().set(repeatedIndex1.get(i), learningObject2);
			newIndividual2.getLearningObjects().set(repeatedIndex2.get(i), learningObject1);
		}

		individuals.add(newIndividual1);
		individuals.add(newIndividual2);

		return individuals;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private int repeatedObject(List<LearningObjectBean> learningObjects, int index, int min, int max) {
		for (int i = 0; i < learningObjects.size(); i++) {
			if ((i < min) || (i > max)) {
				if (learningObjects.get(index).getIdentifier().compareTo(learningObjects.get(i).getIdentifier()) == 0) {
					return i;
				}
			}
		}

		return -1;
	}

}
