package com.pse.trainingappdroid;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph{

	public GraphicalView getView(Context context) {
		
		// Our first data
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y =  { 30, 34, 45, 57, 77, 89, 100, 111 ,123 ,145 }; // y values!
		TimeSeries series = new TimeSeries("Line1"); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		

		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		mRenderer.addSeriesRenderer(renderer);

		
		// Customization time for line 1!
		renderer.setColor(Color.BLUE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);

		
		return ChartFactory.getLineChartView(context, dataset, mRenderer);
		 
		
	}

}

