package org.wpattern.learning.object.ga.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.beans.PopulationBean;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.IPopulation;
import org.wpattern.learning.object.ga.interfaces.IPopulationGenerator;
import org.wpattern.learning.object.ga.utils.Utils;

public class PopulationGenerator implements IPopulationGenerator {

	private String filename;

	private List<LearningObjectBean> learningObjects;

	private Random random;

	private IFitness fitness;

	public PopulationGenerator(String filename) {
		this.filename = filename;
		this.learningObjects = this.loadLearningObjects(this.filename);
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void config(IConfig config) {
		this.fitness = config.getFitness();
	}

	@Override
	public IPopulation execute(int populationSize) {
		PopulationBean population = new PopulationBean(populationSize);

		for (int i = 0; i < populationSize; i++) {
			population.add(this.createIndividual());
		}

		return population;
	}

	@Override
	public String toString() {
		return String.format("%s, Filename [%s]", this.getClass().getSimpleName(), this.filename);
	}

	private IndividualBean createIndividual() {
		IndividualBean individual = new IndividualBean();

		List<LearningObjectBean> copyLearningObject = new ArrayList<LearningObjectBean>();

		for (int i = 0; i < this.learningObjects.size(); i++) {
			copyLearningObject.add(this.learningObjects.get(i));
		}

		while (copyLearningObject.size() > 0) {
			int index = this.randomizeIndex(copyLearningObject.size());
			individual.addLearningObject(copyLearningObject.remove(index));
		}

		this.fitness.calculate(individual);

		return individual;
	}

	private int randomizeIndex(int maxValue) {
		return this.random.nextInt(maxValue);
	}

	private List<LearningObjectBean> loadLearningObjects(String filename) {
		return Utils.load(filename).getLearningObjects();
	}

}
