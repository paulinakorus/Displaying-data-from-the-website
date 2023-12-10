package org.example;

import org.example.model.*;
import org.example.service.ClientInterface;
import org.example.service.implementation.Parsing;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Client;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.awt.Color.WHITE;

public class AirConditionApplication extends JFrame {
    private JComboBox<String> cityBox;
    private JTabbedPane paramCharts;
    private JTabbedPane ChartsPane;
    private JPanel so2Panel;
    private JPanel no2Panel;
    private JPanel pm10Panel;
    private JPanel pm25Panel;
    private JPanel c6h6Panel;
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
    private JLabel cityLabel;
    private JPanel coPanel;


    private final ClientInterface client = Client.getInstance();
    private final ParsingInterface parsing = Parsing.getInstance();
    private List<Station> stationList = new ArrayList<>();
    private List<Station> stationCityList = new ArrayList<>();
    private String selectedCity;
    private int currentStationPositionInList = 0;
    private JPanel[] tabPanel = {o3Panel, no2Panel, so2Panel, pm25Panel, pm10Panel, c6h6Panel, coPanel};
    private ParamValuesChart paramChart;
    private ParamLevelChart levelChart;

    public AirConditionApplication() {
        this.setTitle("MainForm");                                     // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(800, 700);                            // setting size
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
        setUpPanels();
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
                    currentStationPositionInList = 0;
                    uploadStation(0);

                    clearPanels();
                    paramChart = new ParamValuesChart(tabPanel, stationCityList);
                    paramChart.createParamsCharts(0);
                    levelChart = new ParamLevelChart(chartsPanel, stationCityList);
                    levelChart.createLevelBarChart(0);
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == previousButton) {
                    if (currentStationPositionInList > 0) {
                        uploadStation(--currentStationPositionInList);

                        clearPanels();
                        paramChart.createParamsCharts(currentStationPositionInList);
                        levelChart.createLevelBarChart(currentStationPositionInList);
                    }
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == nextButton) {
                    if (currentStationPositionInList < stationCityList.size() - 1) {
                        uploadStation(++currentStationPositionInList);

                        clearPanels();
                        paramChart.createParamsCharts(currentStationPositionInList);
                        levelChart.createLevelBarChart(currentStationPositionInList);
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

    private void setUpPanels(){
        for (JPanel onePanel : tabPanel) {
            onePanel.setLayout(new BorderLayout());
            onePanel.setSize(400, 300);
        }
    }

    private void clearPanels(){
        for (JPanel panel:tabPanel) {
            panel.removeAll();
        }
        chartsPanel.removeAll();
    }
}
