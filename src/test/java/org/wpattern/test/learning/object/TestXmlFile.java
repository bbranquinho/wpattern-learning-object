package org.wpattern.test.learning.object;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wpattern.learning.object.beans.LearningObjectBean;
import org.wpattern.learning.object.beans.RulesBean;
import org.wpattern.learning.object.elements.Fitness;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.beans.NodeBean;

public class TestXmlFile {

	private static final Logger LOGGER = Logger.getLogger(TestXmlFile.class);

	@Test
	public void testSave() {
		LearningObjectBean lo1 = new LearningObjectBean("individual 1");
		lo1.setDuration(10);
		lo1.addCompetency("comp 1");
		lo1.addCompetency("comp 2");
		lo1.addCompetency("comp 3");
		lo1.addConstraint("const 1");
		lo1.addConstraint("const 2");
		lo1.addConstraint("const 3");

		LearningObjectBean lo2 = new LearningObjectBean("individual 2");
		lo2.setDuration(14);
		lo2.addCompetency("comp 4");
		lo2.addCompetency("comp 5");
		lo2.addCompetency("comp 6");
		lo2.addConstraint("const 4");
		lo2.addConstraint("const 5");
		lo2.addConstraint("const 6");

		RulesBean learningObjects = new RulesBean();

		learningObjects.addLearningObject(lo1);
		learningObjects.addLearningObject(lo2);

		Utils.save("d://teste.xml", learningObjects);
	}

	@Test
	public void testLoad() {
		RulesBean rules = Utils.load("D:\\WorkspaceIC\\wpattern-learning-object\\resources\\learning_objects\\learning_objects.xml");

		LOGGER.info(rules);
	}

	@Test
	public void testFitness() {
		RulesBean rules = Utils.load("D:\\projetos\\wpattern\\sample projects\\java\\wpattern-learning-object\\src\\main\\resources\\learning_objects\\template_50.xml");

		LOGGER.info(rules);

		Fitness fitness = new Fitness();

		NodeBean node = new NodeBean();

		node.setLearningObjects(rules.getLearningObjects());

		fitness.calculate(node);

		LOGGER.info(node);
	}

	@Test
	public void testFitnessT10() {
		RulesBean rules = Utils.load("D:\\projetos\\wpattern\\sample projects\\java\\wpattern-learning-object\\src\\main\\resources\\learning_objects\\experiment_t010.xml");

		LOGGER.info(rules);

		Fitness fitness = new Fitness();

		NodeBean node = new NodeBean();

		node.setLearningObjects(rules.getLearningObjects());

		fitness.calculate(node);

		LOGGER.info(node);
	}

	@Test
	public void testFitnessT50() {
		RulesBean rules = Utils.load("D:\\projetos\\wpattern\\sample projects\\java\\wpattern-learning-object\\src\\main\\resources\\learning_objects\\experiment_t070.xml");

		LOGGER.info(rules);

		Fitness fitness = new Fitness();

		NodeBean node = new NodeBean();

		node.setLearningObjects(rules.getLearningObjects());

		fitness.calculate(node);

		LOGGER.info(node.getFitness());
	}

	@Test
	public void testFitnessT100() {
		RulesBean rules = Utils.load("D:\\projetos\\wpattern\\sample projects\\java\\wpattern-learning-object\\src\\main\\resources\\learning_objects\\experiment_t100.xml");

		LOGGER.info(rules);

		Fitness fitness = new Fitness();

		NodeBean node = new NodeBean();

		node.setLearningObjects(rules.getLearningObjects());

		fitness.calculate(node);

		LOGGER.info(node);
	}

}
