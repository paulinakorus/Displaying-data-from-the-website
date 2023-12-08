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


    private final ClientInterface client = Client.getInstance();
    private final ParsingInterface parsing = Parsing.getInstance();
    private List<Station> stationList = new ArrayList<>();
    private List<Station> stationCityList = new ArrayList<>();
    private String selectedCity;
    private int currentStationPositionInList = 0;
    private Boolean[] isThereData = {false,false,false,false,false,false};
    JPanel[] tabPanel = {o3Panel, no2Panel, so2Panel, pm25Panel, pm10Panel, c6h6Panel};

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

        for (JPanel onePanel : tabPanel) {
            onePanel.setLayout(new BorderLayout());
            onePanel.setSize(400, 300);
        }
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
                    createParamsCharts(0);
                    createLevelBarChart(0);
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == previousButton) {
                    if (currentStationPositionInList > 0) {
                        uploadStation(--currentStationPositionInList);
                        createParamsCharts(currentStationPositionInList);
                        createLevelBarChart(currentStationPositionInList);
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
                        createParamsCharts(currentStationPositionInList);
                        createLevelBarChart(currentStationPositionInList);
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
        Stand[] standTab = parsing.fetchStand(stationCityList.get(StationID).getId());
        for (Stand oneStand : standTab) {
            String paramFormula = oneStand.getParam().getParamFormula();
            int paramID = oneStand.getId();
            Sensor sensor = parsing.fetchSensor(paramID);

            JFreeChart chart = createLineChart(sensor);
            if(paramFormula.equals("O3")){
                o3Panel.add(new ChartPanel(chart));
                isThereData[0] = true;
            }else if(paramFormula.equals("NO2")){
                no2Panel.add(new ChartPanel(chart));
                isThereData[1] = true;
            }else if(paramFormula.equals("SO2")){
                so2Panel.add(new ChartPanel(chart));
                isThereData[2] = true;
            }
            else if(paramFormula.equals("PM25")){
                pm25Panel.add(new ChartPanel(chart));
                isThereData[3] = true;
            }
            else if(paramFormula.equals("PM10")){
                pm10Panel.add(new ChartPanel(chart));
                isThereData[4] = true;
            }else if(paramFormula.equals("C6H6")){
                c6h6Panel.add(new ChartPanel(chart));
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
                dataset.addValue(oneValue.getValue(), sensor.getKey(), oneValue.getDate());
            else
                dataset.addValue(oneValue.getValue(), sensor.getKey(), "");
        }
        return dataset;
    }

    private void createLevelBarChart(int stationID){
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
        return chart;
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
        return dataset;
    }


    /*private Date formattingDate (Value oneValue){
        String dateInString = oneValue.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        Date date = (Date) formatter.parse(dateInString);
        return date;
    }*/

}
