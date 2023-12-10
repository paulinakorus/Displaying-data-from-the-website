package org.example;

import org.example.model.IndeksAir;
import org.example.model.Station;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Parsing;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.awt.Color.WHITE;

public class ParamLevelChart {

    private JPanel chartsPanel;
    private java.util.List<Station> stationCityList;
    private final ParsingInterface parsing = Parsing.getInstance();

    public ParamLevelChart(JPanel chartsPanel, List<Station> stationCityList){
        this.chartsPanel = chartsPanel;
        this.stationCityList = stationCityList;
    }
    void createLevelBarChart(int stationID){
        int id = stationCityList.get(stationID).getId();
        IndeksAir app = parsing.fetchIndeksAir(id);
        chartsPanel.setLayout(new BorderLayout());
        chartsPanel.add(new ChartPanel(createBarChart(app)));
    }
    private JFreeChart createBarChart(IndeksAir app){
        DefaultCategoryDataset dataset = createDataSetBarChart(app);
        JFreeChart chart = ChartFactory.createBarChart("Levels of param", "Param", "Level", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(WHITE);

        CategoryItemRenderer renderer = new BarRenderer();
        for (int i=0; i<dataset.getRowCount(); i++){
            for(int j=0; j<dataset.getColumnCount(); j++){
                setColorToLevel(i, j, dataset, renderer);
                System.out.println("row: " + i + "  column: " + j + "  value: " + dataset.getValue(i, j));
            }
        }
        return chart;
    }

    private void setColorToLevel(int i, int j, DefaultCategoryDataset dataset, CategoryItemRenderer renderer){
        if(dataset.getValue(i, j).equals(5.0))
            renderer.setSeriesPaint(j, new Color(0,174,29));
        else if(dataset.getValue(i, j).equals(4.0))
            renderer.setSeriesPaint(j, new Color(0, 238, 40));
        else if(dataset.getValue(i, j).equals(3.0))
            renderer.setSeriesPaint(j, new Color(198, 238, 0));
        else if(dataset.getValue(i, j).equals(2.0))
            renderer.setSeriesPaint(j, new Color(238, 166, 0));
        else if(dataset.getValue(i, j).equals(1.0))
            renderer.setSeriesPaint(j, new Color(238, 71, 0));
    }

    private DefaultCategoryDataset createDataSetBarChart(IndeksAir app){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(app.getSo2IndexLevel()!=null)
            dataset.addValue(5-app.getSo2IndexLevel().getId(), "Level", "SO2");
        if(app.getNo2IndexLevel()!=null)
            dataset.addValue(5-app.getNo2IndexLevel().getId(), "Level", "NO2");
        if(app.getO3IndexLevel()!=null)
            dataset.addValue(5-app.getO3IndexLevel().getId(), "Level", "O3");
        if(app.getPm25IndexLevel()!=null)
            dataset.addValue(5-app.getPm25IndexLevel().getId(), "Level", "PM2.5");
        if(app.getPm10IndexLevel()!=null)
            dataset.addValue(5-app.getPm10IndexLevel().getId(), "Level", "PM10");
        if(app.getC6h6IndexLevel()!=null)
            dataset.addValue(5-app.getC6h6IndexLevel().getId(), "Level", "C6H6");
        if(app.getCoIndexLevel()!=null)
            dataset.addValue(5-app.getCoIndexLevel().getId(), "Level", "CO");
        return dataset;
    }
}
