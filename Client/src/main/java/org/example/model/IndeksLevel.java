package org.example.model;

public class IndeksLevel {
    private int id;
    private String indexLevelName;

    public IndeksLevel(int id, String indexLevelName){
        this.id = id;
        this.indexLevelName = indexLevelName;
    }

    public IndeksLevel(){
        this(0, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }

    public void setIndexLevelName(String indexLevelName) {
        this.indexLevelName = indexLevelName;
    }
}
