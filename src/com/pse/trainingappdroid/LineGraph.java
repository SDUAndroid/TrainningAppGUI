package com.pse.trainingappdroid;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class LineGraph
{

	public GraphicalView getView(Context context)
	{

		// Our first data
		 //int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x
		// values!

		//int[] y = { 5, 10, 11, 13, 16, 12, 22, 22, 22, 23 }; //
		// y values!

		// get the x 'the tries in the time'
		Iterator<Counter> it = TabsHistoryActivity.listOfCounters.iterator();
		//get items from iterator
		int items = 0;
		for ( ; it.hasNext() ; ++items) it.next();
		//init array
		int[] x = new int[items];
		int[] y = new int[items];
		//put x points and restart iterator
		it = TabsHistoryActivity.listOfCounters.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Counter counter = it.next();//avoid read x2 times with .next()
			x[i] = (int) ( counter.getId());
			y[i] = counter.getMaxCounter();
		}
		
		TimeSeries series = new TimeSeries("STRECHT EACH TRY");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds
																				// a
																				// collection
																				// of
																				// XYSeriesRenderer
																				// and
																				// customizes
																				// the
																				// graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used
															// to customize line
															// 1
		mRenderer.addSeriesRenderer(renderer);

		// Customization time for line 1!
		renderer.setColor(Color.BLUE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);

		return ChartFactory.getLineChartView(context, dataset, mRenderer);

	}

}
