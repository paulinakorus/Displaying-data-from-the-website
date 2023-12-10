package org.example;

import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;
import org.example.model.Value;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Parsing;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.WHITE;
import static org.example.AirConditionApplication.*;

public class ParamValuesChart {
    private Boolean[] isThereData = {false,false,false,false,false,false};
    private JPanel[] tabPanel;
    private List<Station> stationCityList = new ArrayList<>();
    private final ParsingInterface parsing = Parsing.getInstance();

    public ParamValuesChart(JPanel[] tabPanel, List<Station> stationCityList){
        this.tabPanel = tabPanel;
        this.stationCityList = stationCityList;
    }

    void createParamsCharts(int StationID){
        Stand[] standTab = parsing.fetchStand(stationCityList.get(StationID).getId());
        for (Stand oneStand : standTab) {
            String paramFormula = oneStand.getParam().getParamFormula();
            int paramID = oneStand.getId();
            Sensor sensor = parsing.fetchSensor(paramID);

            JFreeChart chart = createLineChart(sensor);
            if(paramFormula.equals("O3")){
                tabPanel[0].add(new ChartPanel(chart));
                isThereData[0] = true;
            }else if(paramFormula.equals("NO2")){
                tabPanel[1].add(new ChartPanel(chart));
                isThereData[1] = true;
            }else if(paramFormula.equals("SO2")){
                tabPanel[2].add(new ChartPanel(chart));
                isThereData[2] = true;
            }
            else if(paramFormula.equals("PM25")){
                tabPanel[3].add(new ChartPanel(chart));
                isThereData[3] = true;
            }
            else if(paramFormula.equals("PM10")){
                tabPanel[4].add(new ChartPanel(chart));
                isThereData[4] = true;
            }else if(paramFormula.equals("C6H6")){
                tabPanel[5].add(new ChartPanel(chart));
                isThereData[5] = true;
            }
        }

        for(int i=0; i<tabPanel.length; i++){
            if (!isThereData[i])
                setUpNoDataLabel(tabPanel[i]);
        }
    }

    private void setUpNoDataLabel(JPanel panel){
        Label noDataLabel = new Label("No data");
        noDataLabel.setForeground(WHITE);
        noDataLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        noDataLabel.setLocation(200, 100);
        panel.add(noDataLabel);
        panel.setBackground(new java.awt.Color(109,144,142));
    }

    private JFreeChart createLineChart(Sensor sensor){
        DefaultCategoryDataset dataset = createDataSetLineChart(sensor);
        JFreeChart chart = ChartFactory.createLineChart(
                sensor.getKey(),                  // chart title
                "Date",                           // X-Axis Label
                "Value",                          // Y-Axis Label
                dataset,                          // dataset
                PlotOrientation.VERTICAL,         // orientation
                false,                            // legend
                true,                             // tooltips
                false                             // urls
        );
        CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);           // ustawienie pozycji dat pod kątem 90 stopni
        return chart;
    }

    private DefaultCategoryDataset createDataSetLineChart(Sensor sensor){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int iterator=0;
        for (Value oneValue : sensor.getValues()) {
            if (++iterator%2 == 0)
                dataset.addValue(oneValue.getValue(), sensor.getKey(), formattingDate(oneValue.getDate()));
            else
                dataset.addValue(oneValue.getValue(), sensor.getKey(), "");
        }
        return dataset;
    }

    private String formattingDate (String dataRaw){
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var date = LocalDateTime.parse(dataRaw, formatter);
        var month = date.getMonthValue();
        var day = date.getDayOfMonth();
        var hour = date.getHour();
        return day + "." + month + " " + hour + ":00";
    }
}
