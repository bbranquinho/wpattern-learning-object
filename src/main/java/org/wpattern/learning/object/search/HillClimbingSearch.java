package org.wpattern.learning.object.search;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.INodeGenerator;
import org.wpattern.learning.object.ga.utils.CompareType;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;
import org.wpattern.learning.object.search.interfaces.ISearch;

public class HillClimbingSearch implements ISearch {

	private static final Logger LOGGER = Logger.getLogger(HillClimbingSearch.class);

	private IFitness fitness;

	private INodeGenerator nodeGenerator;

	private int maxStagnatedSolution;

	private Random random;

	public HillClimbingSearch(int maxStagnatedSolution) {
		this.random = new Random(System.currentTimeMillis());
		this.maxStagnatedSolution = maxStagnatedSolution;
	}

	@Override
	public void config(IConfig config) {
		this.fitness = config.getFitness();
		this.nodeGenerator = config.getNodeGenerator();
	}

	@Override
	public NodeBean find() {
		NodeBean start = this.nodeGenerator.create();

		this.fitness.calculate(start);

		return this.find(start);
	}

	@Override
	public NodeBean find(NodeBean start) {
		NodeBean current = start;
		int countStagnatedSolution = 0;

		while (true) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Current node [%s].", Utils.printSolution(current)));
			}

			if (this.isGoal(current) || (countStagnatedSolution > this.maxStagnatedSolution)) {
				return current;
			}

			List<NodeBean> childrens = SearchUtils.getNeighbors(current, this.fitness);
			int[] lowestNode = new int[childrens.size()];
			int countNodes = 0;
			double lowest = Double.MAX_VALUE;

			for (int i = 0; i < childrens.size(); i++) {
				if (childrens.get(i).getFitness() < lowest) {
					lowest = childrens.get(i).getFitness();
					countNodes = 0;
				}

				if (lowest <= childrens.get(i).getFitness()) {
					lowestNode[countNodes++] = i;
				}
			}

			if (countNodes <= 0) {
				LOGGER.warn(String.format("Not founded chieldren nodes to [%s].", current));
				break;
			}

			if (childrens.get(lowestNode[0]).getFitness() < current.getFitness()) {
				// New best node.
				current = childrens.get(lowestNode[0]);
			} else {
				// Random restart.
				current = childrens.get(lowestNode[this.random.nextInt(countNodes)]);
				countStagnatedSolution++;
			}
		}

		return null;
	}

	private boolean isGoal(NodeBean current) {
		return Utils.compareDouble(current.getFitness(), 0.0d) == CompareType.EQUAL;
	}

}
