package org.example.model;

public class City {
    private int id;
    private String name;
    private Commune commune;

    public City (int id, String name, Commune commune){
        this.id = id;
        this.name = name;
        this.commune = commune;
    }

    public City (){
        this(0, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
}
