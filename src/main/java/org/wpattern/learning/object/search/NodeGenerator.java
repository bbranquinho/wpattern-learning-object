package org.wpattern.learning.object.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.INodeGenerator;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;

public class NodeGenerator implements INodeGenerator {

	private String filename;

	private List<LearningObjectBean> learningObjects;

	private Random random;

	private IFitness fitness;

	public NodeGenerator(String filename) {
		this.filename = filename;
		this.learningObjects = this.loadLearningObjects(this.filename);
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void config(IConfig config) {
		this.fitness = config.getFitness();
	}

	@Override
	public NodeBean create() {
		return this.createNode();
	}

	private NodeBean createNode() {
		NodeBean node = new NodeBean();

		List<LearningObjectBean> copyLearningObject = new ArrayList<LearningObjectBean>();

		for (int i = 0; i < this.learningObjects.size(); i++) {
			copyLearningObject.add(this.learningObjects.get(i));
		}

		while (copyLearningObject.size() > 0) {
			int index = this.randomizeIndex(copyLearningObject.size());
			node.addLearningObject(copyLearningObject.remove(index));
		}

		this.fitness.calculate(node);

		return node;
	}

	private int randomizeIndex(int maxValue) {
		return this.random.nextInt(maxValue);
	}

	private List<LearningObjectBean> loadLearningObjects(String filename) {
		return Utils.load(filename).getLearningObjects();
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", this.getClass().getSimpleName(), this.filename);
	}

}
