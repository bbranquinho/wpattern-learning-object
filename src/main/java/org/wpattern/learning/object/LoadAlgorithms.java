package org.wpattern.learning.object;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.wpattern.learning.object.beans.ConfigBean;
import org.wpattern.learning.object.elements.DistanceFitness;
import org.wpattern.learning.object.elements.Fitness;
import org.wpattern.learning.object.elements.Graphic;
import org.wpattern.learning.object.ga.EvolutionaryGA;
import org.wpattern.learning.object.ga.SteadyStateGA;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.elements.BestNaturalSelection;
import org.wpattern.learning.object.ga.elements.Mutation;
import org.wpattern.learning.object.ga.elements.PMXCrossover;
import org.wpattern.learning.object.ga.elements.PopulationGenerator;
import org.wpattern.learning.object.ga.elements.RandomSelection;
import org.wpattern.learning.object.ga.elements.RouletteSelection;
import org.wpattern.learning.object.ga.elements.TournamentSelection;
import org.wpattern.learning.object.ga.interfaces.ICrossover;
import org.wpattern.learning.object.ga.interfaces.IFitness;
import org.wpattern.learning.object.ga.interfaces.IGeneticAlgorithm;
import org.wpattern.learning.object.ga.interfaces.IGraphic;
import org.wpattern.learning.object.ga.interfaces.IMutation;
import org.wpattern.learning.object.ga.interfaces.INaturalSelection;
import org.wpattern.learning.object.ga.interfaces.INodeGenerator;
import org.wpattern.learning.object.ga.interfaces.IPopulationGenerator;
import org.wpattern.learning.object.ga.interfaces.ISelection;
import org.wpattern.learning.object.ga.utils.Utils;
import org.wpattern.learning.object.search.AStarSearch;
import org.wpattern.learning.object.search.HillClimbingSearch;
import org.wpattern.learning.object.search.NodeGenerator;
import org.wpattern.learning.object.search.beans.NodeBean;
import org.wpattern.learning.object.search.interfaces.ISearch;

public final class LoadAlgorithms {

	private static final Logger LOGGER = Logger.getLogger(LoadAlgorithms.class);

	// ALGORITHMS

	private static final String ALGORITHM_TYPE_PROPERTY = "algorithm.type";

	private static final String ALGORITHM_TEMPLATE_FILENAME_PROPERTY = "algorithm.template.filename";

	private static final String ALGORITHM_GRAPHIC_PROPERTY = "algorithm.graphic";

	// GENETIC ALGORITHM

	private static final String GA_TYPE_PROPERTY = "ga.type";

	private static final String GA_MAX_EVALUATION_PROPERTY = "ga.maxEvaluation";

	private static final String GA_POPULATION_SIZE_PROPERTY = "ga.population.size";

	private static final String GA_EVOLUTIONARY_PERCENTUAL_PROPERTY = "ga.type.evolutionary.percentual";

	private static final String GA_CROSSOVER_PROPERTY = "ga.crossover";

	private static final String GA_MUTATION_PROPERTY = "ga.mutation";

	private static final String GA_FITNESS_PROPERTY = "ga.fitness";

	private static final String GA_MUTATION_PROBABILITY_PROPERTY = "ga.mutation.probability";

	private static final String GA_SELECTION_PROPERTY = "ga.selection";

	private static final String GA_SELECTION_TOURNAMENT_N_PROPERTY = "ga.selection.tournament.n";

	private static final String GA_POPULATION_GENERATOR_PROPERTY = "ga.population.generator";

	private static final String GA_NATURAL_SELECTION_PROPERTY = "ga.naturalSelection";

	private static final String GA_NUM_REPETITIONS_PROPERTY = "ga.num.repetitions";

	// SEARCH

	private static final String SEARCH_TYPE_PROPERTY = "search.type";

	private static final String SEARCH_MAX_STAGNATED_SOLUTION_PROPERTY = "search.maxStagnatedSolution";

	private static final String SEARCH_FITNESS_PROPERTY = "search.fitness";

	private static final String SEARCH_NUM_REPETITIONS_PROPERTY = "search.num.repetitions";

	public static final void processAlgorithm(String filename) {
		Properties properties = new Properties();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Loading the file [%s].", filename));
		}

		try {
			properties.load(new FileInputStream(filename));
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			System.exit(-1);
		}

		String algorithmType = ((String)properties.get(ALGORITHM_TYPE_PROPERTY)).trim().toLowerCase();

		switch (algorithmType) {
		case "search":
			executeSearch(properties);
			break;

		case "ga":
			executeGA(properties);
			break;

		default:
			LOGGER.error(String.format("Invalid type of algorithm [%s].", algorithmType));
			System.exit(-1);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// GENETIC ALGORITHM
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final void executeGA(Properties properties) {
		IGraphic graphic = getGraphic(properties);
		List<IGeneticAlgorithm> geneticAlgorithms = getGeneticAlgorithm(properties);
		List<ICrossover> crossovers = getCrossover(properties);
		List<ISelection> selections = getSelection(properties);
		List<IMutation> mutations = getMutation(properties);
		List<IFitness> fitnesses = getGAFitness(properties);
		List<IPopulationGenerator> populationGenerators = getPopulationGenerator(properties);
		List<INaturalSelection> naturalSelections = getNaturalSelection(properties);

		for (IGeneticAlgorithm geneticAlgorithm : geneticAlgorithms) {
			for (ICrossover crossover : crossovers) {
				for (ISelection selection : selections) {
					for (IMutation mutation : mutations) {
						for (IFitness fitness : fitnesses) {
							for (IPopulationGenerator populationGenerator : populationGenerators) {
								for (INaturalSelection naturalSelection : naturalSelections) {
									if (LOGGER.isInfoEnabled()) {
										LOGGER.info(String.format("Genetic Algorithm started with parameters Genetic Algorithm [%s],"
												+ " Crossover [%s], Selection [%s], Mutation [%s], Fitness [%s], Population Generator [%s]"
												+ " and Natural Selection [%s].", geneticAlgorithm,  crossover, selection, mutation,
												fitness, populationGenerator, naturalSelection));
									}

									ConfigBean config = new ConfigBean();

									config.setGraphic(graphic);
									config.setCrossover(crossover);
									config.setSelection(selection);
									config.setMutation(mutation);
									config.setFitness(fitness);
									config.setPopulationGenerator(populationGenerator);
									config.setNaturalSelection(naturalSelection);

									crossover.config(config);
									mutation.config(config);
									fitness.config(config);
									populationGenerator.config(config);
									naturalSelection.config(config);
									geneticAlgorithm.config(config);

									int numRepetitions = Integer.parseInt(properties.getProperty(GA_NUM_REPETITIONS_PROPERTY).trim());
									long totalTime = 0, timeSpent;
									List<IndividualBean> individuals = new ArrayList<IndividualBean>(numRepetitions);

									for (int i = 0; i < numRepetitions; i++) {
										long startTime = System.currentTimeMillis();

										geneticAlgorithm.execute();

										individuals.add(geneticAlgorithm.getPopulation().getBestIndividual());

										timeSpent = System.currentTimeMillis() - startTime;

										totalTime += timeSpent;

										if (LOGGER.isInfoEnabled()) {
											LOGGER.info(String.format("Genetic Algorithm [%s], spent the time [%s ms] and solution [%s].",
													i, timeSpent, Utils.printSolution(individuals.get(i))));
										}
									}

									if (LOGGER.isInfoEnabled()) {
										long averageFitness = 0;

										for (int i = 0; i < individuals.size(); i++) {
											LOGGER.info(Utils.printSolution(individuals.get(i)));
											averageFitness += individuals.get(i).getFitness();
										}

										LOGGER.info(String.format("[%s] genetic algorithms executed with average time [%s ms] and average fitness [%s].", numRepetitions,
												totalTime / numRepetitions, averageFitness / individuals.size()));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static List<INaturalSelection> getNaturalSelection(Properties properties) {
		List<INaturalSelection> naturalSelections = new ArrayList<INaturalSelection>();

		for (String naturalSelectionType : properties.getProperty(GA_NATURAL_SELECTION_PROPERTY).split("\\|")) {
			naturalSelectionType = naturalSelectionType.trim().toLowerCase();

			switch (naturalSelectionType) {
			case "best":
				naturalSelections.add(new BestNaturalSelection());
				break;

			default:
				LOGGER.error(String.format("Invalid type of natural selection [%s]", naturalSelectionType));
				System.exit(-1);
			}
		}

		return naturalSelections;
	}

	private static List<IPopulationGenerator> getPopulationGenerator(Properties properties) {
		List<IPopulationGenerator> populationGenerators = new ArrayList<IPopulationGenerator>();

		for (String populationGeneratorType : properties.getProperty(GA_POPULATION_GENERATOR_PROPERTY).split("\\|")) {
			populationGeneratorType = populationGeneratorType.trim().toLowerCase();

			switch (populationGeneratorType) {
			case "simple":
				for (String filename : properties.getProperty(ALGORITHM_TEMPLATE_FILENAME_PROPERTY).split("\\|")) {
					populationGenerators.add(new PopulationGenerator(filename));
				}
				break;

			default:
				LOGGER.error(String.format("Invalid type of population generator [%s]", populationGeneratorType));
				System.exit(-1);
			}
		}

		return populationGenerators;
	}

	private static List<IFitness> getGAFitness(Properties properties) {
		List<IFitness> fitnesses = new ArrayList<IFitness>();

		for (String fitnessType : properties.getProperty(GA_FITNESS_PROPERTY).split("\\|")) {
			fitnessType = fitnessType.trim().toLowerCase();

			switch (fitnessType) {
			case "simple":
				fitnesses.add(new Fitness());
				break;

			case "distance":
				fitnesses.add(new DistanceFitness());
				break;

			default:
				LOGGER.error(String.format("Invalid type of fitness [%s]", fitnessType));
				System.exit(-1);
			}
		}

		return fitnesses;
	}

	private static List<IMutation> getMutation(Properties properties) {
		List<IMutation> mutations = new ArrayList<IMutation>();

		for (String mutationType : properties.getProperty(GA_MUTATION_PROPERTY).split("\\|")) {
			mutationType = mutationType.trim().toLowerCase();

			switch (mutationType) {
			case "simple":
				for (String mutationProbability : properties.getProperty(GA_MUTATION_PROBABILITY_PROPERTY).split("\\|")) {
					mutations.add(new Mutation(Double.parseDouble(mutationProbability.trim())));
				}
				break;

			default:
				LOGGER.error(String.format("Invalid type of mutation [%s]", mutationType));
				System.exit(-1);
			}
		}

		return mutations;
	}

	private static List<ISelection> getSelection(Properties properties) {
		List<ISelection> selections = new ArrayList<ISelection>();

		for (String selectionType : properties.getProperty(GA_SELECTION_PROPERTY).split("\\|")) {
			selectionType = selectionType.trim().toLowerCase();

			switch (selectionType) {
			case "roulette":
				selections.add(new RouletteSelection());
				break;

			case "random":
				selections.add(new RandomSelection());
				break;

			case "tournament":
				for (String n : properties.getProperty(GA_SELECTION_TOURNAMENT_N_PROPERTY).split("\\|")) {
					selections.add(new TournamentSelection(Integer.parseInt(n.trim())));
				}
				break;

			default:
				LOGGER.error(String.format("Invalid type of selection [%s]", selectionType));
				System.exit(-1);
			}
		}

		return selections;
	}

	private static List<ICrossover> getCrossover(Properties properties) {
		List<ICrossover> crossovers = new ArrayList<ICrossover>();

		for (String crossoverType : properties.getProperty(GA_CROSSOVER_PROPERTY).split("\\|")) {
			crossoverType = crossoverType.trim().toLowerCase();

			switch (crossoverType) {
			case "pmx":
				crossovers.add(new PMXCrossover());
				break;

			default:
				LOGGER.error(String.format("Invalid type of crossover [%s]", crossoverType));
				System.exit(-1);
			}
		}

		return crossovers;
	}

	private static List<IGeneticAlgorithm> getGeneticAlgorithm(Properties properties) {
		List<IGeneticAlgorithm> gas = new ArrayList<IGeneticAlgorithm>();

		for (String populationSize : properties.getProperty(GA_POPULATION_SIZE_PROPERTY).split("\\|")) {
			for (String maxEvaluation : properties.getProperty(GA_MAX_EVALUATION_PROPERTY).split("\\|")) {
				maxEvaluation = maxEvaluation.trim();

				for (String gaType : properties.getProperty(GA_TYPE_PROPERTY).split("\\|")) {
					gaType = gaType.trim().toLowerCase();

					switch (gaType) {
					case "evolutionary":
						for (String evolutionaryPercentual : properties.getProperty(GA_EVOLUTIONARY_PERCENTUAL_PROPERTY).split("\\|")) {
							gas.add(new EvolutionaryGA(Double.parseDouble(evolutionaryPercentual), Integer.parseInt(populationSize),
									Integer.parseInt(maxEvaluation)));
						}
						break;

					case "steady state":
						gas.add(new SteadyStateGA(Integer.parseInt(populationSize), Integer.parseInt(maxEvaluation)));
						break;

					default:
						LOGGER.error(String.format("Invalid type of genetic algorithm [%s]", gaType));
						System.exit(-1);
					}
				}
			}
		}

		return gas;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// SEARCH
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final void executeSearch(Properties properties) {
		IGraphic graphic = getGraphic(properties);
		List<ISearch> searchs = getSearchType(properties);
		List<INodeGenerator> nodeGenerators = getNodeGenerator(properties);
		List<IFitness> fitnesss = getSearchFitness(properties);

		for (ISearch search : searchs) {
			for (INodeGenerator nodeGenerator : nodeGenerators) {
				for (IFitness fitness : fitnesss) {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info(String.format("Search started with parameters Search [%s], Node Generator [%s] and Fitness [%s].",
								search.getClass().getSimpleName(), nodeGenerator, fitness.getClass().getSimpleName()));
					}

					ConfigBean config = new ConfigBean();

					config.setNodeGenerator(nodeGenerator);
					config.setFitness(fitness);
					config.setGraphic(graphic);

					nodeGenerator.config(config);
					fitness.config(config);
					search.config(config);

					int numRepetitions = Integer.parseInt(properties.getProperty(SEARCH_NUM_REPETITIONS_PROPERTY).trim());
					long totalTime = 0, timeSpent;
					List<NodeBean> nodes = new ArrayList<NodeBean>(numRepetitions);

					for (int i = 0; i < numRepetitions; i++) {
						long startTime = System.currentTimeMillis();

						nodes.add(search.find());

						timeSpent = System.currentTimeMillis() - startTime;

						totalTime += timeSpent;

						if (LOGGER.isInfoEnabled()) {
							LOGGER.info(String.format("Search [%s], spent the time [%s ms] and solution [%s].", i, timeSpent,
									Utils.printSolution(nodes.get(i))));
						}
					}

					if (LOGGER.isInfoEnabled()) {
						long averageFitness = 0;

						for (int i = 0; i < nodes.size(); i++) {
							LOGGER.info(Utils.printSolution(nodes.get(i)));
							averageFitness += nodes.get(i).getFitness();
						}

						LOGGER.info(String.format("[%s] searchs executed with average time [%s ms] and average fitness [%s].", numRepetitions,
								totalTime / numRepetitions, averageFitness / nodes.size()));
					}
				}
			}
		}
	}

	private static IGraphic getGraphic(Properties properties) {
		String graphic = properties.getProperty(ALGORITHM_GRAPHIC_PROPERTY);

		if (graphic == null) {
			return null;
		}

		switch (graphic.trim().toLowerCase()) {
		case "enable":
			return new Graphic();

		default:
			return null;
		}
	}

	private static List<IFitness> getSearchFitness(Properties properties) {
		List<IFitness> fitnesss = new ArrayList<IFitness>();
		String[] fitnesssType = properties.getProperty(SEARCH_FITNESS_PROPERTY).split("\\|");

		for (String fitnessType : fitnesssType) {
			fitnessType = fitnessType.trim().toLowerCase();

			switch (fitnessType) {
			case "simple":
				fitnesss.add(new Fitness());
				break;

			case "distance":
				fitnesss.add(new DistanceFitness());
				break;

			default:
				LOGGER.error(String.format("Invalid type of fitness [%s]", fitnessType));
				System.exit(-1);
			}
		}

		return fitnesss;
	}

	private static List<INodeGenerator> getNodeGenerator(Properties properties) {
		List<INodeGenerator> nodeGenerators = new ArrayList<INodeGenerator>();

		for (String filename : properties.getProperty(ALGORITHM_TEMPLATE_FILENAME_PROPERTY).split("\\|")) {
			nodeGenerators.add(new NodeGenerator(filename));
		}

		return nodeGenerators;
	}

	private static List<ISearch> getSearchType(Properties properties) {
		String[] searchsType = properties.getProperty(SEARCH_TYPE_PROPERTY).split("\\|");

		//trim().toLowerCase();
		List<ISearch> searchs = new ArrayList<ISearch>();

		for (String searchType : searchsType) {
			searchType = searchType.trim().toLowerCase();

			switch (searchType) {
			case "hill climbing":
				String maxStagnatedSolution = properties.getProperty(SEARCH_MAX_STAGNATED_SOLUTION_PROPERTY).trim().toLowerCase();
				searchs.add(new HillClimbingSearch(Integer.parseInt(maxStagnatedSolution)));
				break;

			case "a star":
				searchs.add(new AStarSearch());
				break;

			default:
				LOGGER.error(String.format("Invalid type of search algorithm [%s].", searchType));
				System.exit(-1);
			}
		}

		return searchs;
	}

}
