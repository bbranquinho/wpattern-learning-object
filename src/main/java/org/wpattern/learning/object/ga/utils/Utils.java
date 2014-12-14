package org.wpattern.learning.object.ga.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.beans.RulesBean;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.search.beans.NodeBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public final class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);

	private static final double TOLERANCE = 0.000001;

	private static int countIndividuals = 0;

	private static XStream xStream = null;

	private Utils() {
	}

	public static int getIndividualIdentifier() {
		return ++countIndividuals;
	}

	public static CompareType compareDouble(double value1, double value2) {
		if(Math.abs(value1 - value2) <= TOLERANCE) {
			return CompareType.EQUAL;
		}

		if (value1 > value2) {
			return CompareType.GREATER;
		} else {
			return CompareType.LOWER;
		}
	}

	public static void save(String filename, RulesBean learningObjects) {
		try {
			getXStream().toXML(learningObjects, new FileOutputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static RulesBean load(String filename) {
		RulesBean rules = null;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Loading the template [%s].", filename));
		}

		try {
			rules = (RulesBean) getXStream().fromXML(new FileInputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return rules;
	}

	public static String printSolution(IndividualBean individual) {
		String solution = String.format("Fitness %s; ", individual.getFitness());

		for (LearningObjectBean learningObject : individual.getLearningObjects()) {
			solution += "[" + learningObject.getIdentifier() + "]";
		}

		return solution;
	}

	public static String printSolution(NodeBean node) {
		String solution = String.format("Fitness %s; ", node.getFitness());

		for (LearningObjectBean learningObject : node.getLearningObjects()) {
			solution += "[" + learningObject.getIdentifier() + "]";
		}

		return solution;
	}

	private static XStream getXStream() {
		if (xStream == null) {
			xStream = new XStream(new DomDriver());
			xStream.alias("rules", RulesBean.class);
			xStream.alias("learningObject", LearningObjectBean.class);
		}

		return xStream;
	}

}
