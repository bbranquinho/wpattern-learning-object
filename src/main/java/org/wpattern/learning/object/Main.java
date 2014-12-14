package org.wpattern.learning.object;

import org.apache.log4j.Logger;

public final class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Application started.");
		}

		if (args.length != 1) {
			LOGGER.error("Properties must be passed.");
			System.exit(-1);
		}

		try {
			LoadAlgorithms.processAlgorithm(args[0]);
		} catch (Throwable  e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
