package org.example;

import javax.swing.*;

public class AirConditionApplication extends JFrame{
    private JTabbedPane ValuesPane;
    private JComboBox cityBox;
    private JTabbedPane ChartsPane;
    private JPanel so2Panel;
    private JPanel no2Panel;
    private JPanel pm10Panel;
    private JPanel pm25Panel;
    private JPanel o3Panel;
    private JTable stationsTable;
    private JLabel StationsLabel;
    private JButton readButton;
    private JButton nextButton;
    private JButton previousButton;
    private JPanel chartsPanel;
    private JPanel mainPanel;
    private JLabel citiesLabel;

    public AirConditionApplication(){
        this.setTitle("MainForm");                                     // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(mainPanel);
    }
}
