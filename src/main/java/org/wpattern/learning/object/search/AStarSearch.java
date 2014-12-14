package org.wpattern.learning.object.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.ga.interfaces.IConfig;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.INodeGenerator;
import org.wpattern.learning.object.ga.utils.NodeComparator;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;
import org.wpattern.learning.object.search.interfaces.ISearch;

import com.google.common.collect.MinMaxPriorityQueue;

public class AStarSearch implements ISearch {

	private static final Logger LOGGER = Logger.getLogger(AStarSearch.class);

	private INodeGenerator nodeGenerator;

	private MinMaxPriorityQueue<NodeBean> open;

	private List<NodeBean> close;

	private IFitness fitness;

	private Random random;

	public AStarSearch() {
		this.random = new Random(System.currentTimeMillis());
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
		this.open = MinMaxPriorityQueue.orderedBy(new NodeComparator()).maximumSize(5000).create();
		this.close = new ArrayList<NodeBean>();

		NodeBean current = null;

		this.open.add(start);

		while (!this.open.isEmpty()) {
			current = this.open.poll();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Open Queue with size [%s] and current node [%s].", this.open.size(), Utils.printSolution(current)));
			}

			if (this.isGoal(current)) {
				break;
			}

			this.close.add(current);
			List<NodeBean> neibhbors = SearchUtils.getNeighbors(current, this.fitness);

			while (!neibhbors.isEmpty()) {
				NodeBean neighbor = neibhbors.remove(this.random.nextInt(neibhbors.size()));

				if (this.close.contains(neighbor)) {
					continue;
				}

				if (!this.open.contains(neighbor)) {
					this.open.add(neighbor);
				}
			}
		}

		return current;
	}

	private boolean isGoal(NodeBean current) {
		return (current.getFitness() != null) && (current.getFitness() == 0L);
	}

}
