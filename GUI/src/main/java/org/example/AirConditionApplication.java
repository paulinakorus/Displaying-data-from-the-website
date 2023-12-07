package org.example;

import org.example.model.*;
import org.example.service.ClientInterface;
import org.example.service.Parsing;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Client;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirConditionApplication extends JFrame {
    private JTabbedPane ValuesPane;
    private JComboBox<String> cityBox;
    private JTabbedPane ChartsPane;
    private JPanel so2Panel;
    private JPanel no2Panel;
    private JPanel pm10Panel;
    private JPanel pm25Panel;
    private JPanel o3Panel;
    private JLabel StationsLabel;
    private JButton readButton;
    private JPanel chartsPanel;
    private JPanel mainPanel;
    private JLabel citiesLabel;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel stationIDLabel;
    private JLabel stationNameLabel;
    private JLabel gegrLatLabel;
    private JLabel gegrLonLabel;
    private JLabel cityIDLabel;
    private JLabel cityNameLabel;
    private JLabel communeNameLabel;


    private final ClientInterface client = Client.getInstance();
    private final ParsingInterface parsing = Parsing.getInstance();
    private List<Station> stationList = new ArrayList<>();
    private List<Station> stationCityList = new ArrayList<>();
    private String selectedCity;
    private int currentStationID = 0;

    public AirConditionApplication() {
        this.setTitle("MainForm");                                     // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(mainPanel);

        this.stationList = parsing.fetchAll();

        stationIDLabel.setText("ID: -");
        stationNameLabel.setText("Name: -");
        gegrLatLabel.setText("Lat: -");
        gegrLonLabel.setText("Lon: -");
        cityIDLabel.setText("City ID: -");
        cityNameLabel.setText("City name: -");
        communeNameLabel.setText("Commune name: -");

        setUpComboBox();
        setUpButtons();
    }

    private void setUpComboBox() {
        List<City> cityList = parsing.getCityList();
        cityBox.addItem("Choose");
        for (City city : cityList) {
            cityBox.addItem(city.getName());
        }

        cityBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCity = Objects.requireNonNull(cityBox.getSelectedItem()).toString();
                stationCityList = parsing.getAllCity(selectedCity);
            }
        });
    }

    private void setUpButtons() {
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == readButton) {
                    stationCityList = parsing.getAllCity(selectedCity);
                    currentStationID = 0;
                    uploadStation(0);
                    createParamsCharts(0);
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == previousButton) {
                    if (currentStationID > 0) {
                        uploadStation(--currentStationID);
                        createParamsCharts(currentStationID);
                    }
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nextButton) {
                    if (currentStationID < stationCityList.size() - 1) {
                        uploadStation(++currentStationID);
                        createParamsCharts(currentStationID);
                    }
                }
            }
        });
    }

    private void uploadStation(int StationID) {
        Station station = stationCityList.get(StationID);
        stationIDLabel.setText("ID: " + station.getId());
        stationNameLabel.setText("Name: " + station.getStationName());
        gegrLatLabel.setText("Lat: " + station.getGegrLat());
        gegrLonLabel.setText("Lon: " + station.getGegrLon());
        cityIDLabel.setText("City ID: " + station.getCity().getId());
        cityNameLabel.setText("City name: " + station.getCity().getName());
        communeNameLabel.setText("Commune name: " + station.getCity().getCommune().getCommuneName());
    }

    private void createParamsCharts(int StationID){
        Stand[] standTab = parsing.fetchStand(StationID);
        for (Stand oneStand : standTab) {
            String paramFormula = oneStand.getParam().getParamFormula();
            int paramID = oneStand.getParam().getIdParam();
            Sensor sensor = parsing.fetchSensor(paramID);

            JFreeChart chart = createLineChart(sensor);
            if(paramFormula.equals("NO2"))
                no2Panel.add(new ChartPanel(chart));
            else if(paramFormula.equals("O3"))
                no2Panel.add(new ChartPanel(chart));
            else if(paramFormula.equals("SO2"))
                no2Panel.add(new ChartPanel(chart));
            else if(paramFormula.equals("PM25"))
                no2Panel.add(new ChartPanel(chart));
            else if(paramFormula.equals("PM10"))
                no2Panel.add(new ChartPanel(chart));
        }
    }
    private JFreeChart createLineChart(Sensor sensor){
        DefaultCategoryDataset dataset = createDataSet(sensor);
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
        return chart;
    }

    private DefaultCategoryDataset createDataSet(Sensor sensor){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Value oneValue : sensor.getValues()) {
            dataset.addValue(oneValue.getValue(), sensor.getKey(), oneValue.getDate());
        }

        return dataset;
    }
}
