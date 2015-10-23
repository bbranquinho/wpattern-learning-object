package org.wpattern.learning.object;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.elements.Fitness;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;

public final class MainFitnessCalculation {

	private static Logger LOGGER;

	public static void main(String[] args) {
		if (args.length == 2) {
			System.setProperty("logLevel", args[1]);
		} else {
			System.setProperty("logLevel", "trace");
		}

		LOGGER = Logger.getLogger(MainFitnessCalculation.class);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Application started.");
		}

		if (args.length < 1) {
			LOGGER.error("Template required.");
			System.exit(-1);
		}

		try {
			IFitness fitness = new Fitness();
			NodeBean node = new NodeBean();

			node.setLearningObjects(Utils.load(args[0]).getLearningObjects());

			fitness.calculate(node);

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Fitness [%s].", node.getFitness()));
			}
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
