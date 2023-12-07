package org.example;


import org.example.model.Station;
import org.example.service.ClientInterface;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Table extends AbstractTableModel {
    private final String[] COLUMNS = new String[]{"Id", "Name", "Diameter"};
    private final ClientInterface client = Client.getInstance();
    private final ParsingInterface parsing = org.example.service.Parsing.getInstance();
    private List<Station> stationList;
    public Table(List<Station> stationList){
        this.stationList = stationList.stream().sorted(Comparator.comparing(c -> c.getId())).toList();
    }

    @Override
    public int getRowCount() {
        return stationList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Station station = stationList.get(rowIndex);
        return switch (columnIndex){
            case 0 -> station.getId();
            case 1 -> station.getStationName();
            case 2 -> station.getGegrLat();
            case 3 -> station.getGegrLon();
            case 4 -> station.getCity().getId();
            case 5 -> station.getCity().getName();
            case 6 -> station.getCity().getCommune().getCommuneName();
            default -> "-";
        };
    }
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}
