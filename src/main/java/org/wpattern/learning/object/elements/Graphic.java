package org.wpattern.learning.object.elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.wpattern.learning.object.ga.beans.IndividualBean;
import org.wpattern.learning.object.ga.interfaces.IGraphic;
import org.wpattern.learning.object.search.beans.NodeBean;

public class Graphic implements IGraphic {

	private static final Logger LOGGER = Logger.getLogger(Graphic.class);

	private String graphFilename;

	private List<Long> fitness;

	private int graphWidth;

	private int graphHeight;

	public Graphic() {
		this.fitness = new ArrayList<Long>();
		this.graphFilename = String.format("%s\\target\\graph_%s.png", System.getProperty("user.dir"), System.currentTimeMillis());
		this.graphWidth = 800;
		this.graphHeight = 600;
	}

	@Override
	public void individualProcessed(IndividualBean individual) {
		this.fitness.add(individual.getFitness());
	}

	@Override
	public void individualProcessed(NodeBean node) {
		this.fitness.add(node.getFitness());
	}

	@Override
	public void process() {
		this.saveGraphics();
	}

	private void saveGraphics() {
		if (this.graphFilename  == null) {
			return;
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries timeSeries[] = new XYSeries[1];
		double maxValue = Double.MIN_VALUE;
		double minValue = Double.MAX_VALUE;

		timeSeries[0] = new XYSeries("Evolution");

		for (int i = 0; i < this.fitness.size(); i++) {
			timeSeries[0].add(i, this.fitness.get(i));

			if (this.fitness.get(i) > maxValue) {
				maxValue = this.fitness.get(i);
			}

			if (this.fitness.get(i) < minValue) {
				minValue = this.fitness.get(i);
			}
		}

		dataset.addSeries(timeSeries[0]);

		JFreeChart lineChartObject = ChartFactory.createXYLineChart("Fitness by Generation", "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, true, true, false);

		lineChartObject.getXYPlot().getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		lineChartObject.getXYPlot().getRangeAxis().setRange(new Range(minValue - 1.0, maxValue + 1.0));

		File lineChart = new File(this.graphFilename);

		try {
			ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, this.graphWidth, this.graphHeight);

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Saving the graphic [%s].", this.graphFilename));
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
