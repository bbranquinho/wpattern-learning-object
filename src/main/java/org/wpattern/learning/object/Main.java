package org.wpattern.learning.object;

import org.apache.log4j.Logger;

public final class Main {

	private static Logger LOGGER;

	public static void main(String[] args) {
		if (args.length == 2) {
			System.setProperty("logLevel", args[1]);
		} else {
			System.setProperty("logLevel", "info");
		}

		LOGGER = Logger.getLogger(Main.class);

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
