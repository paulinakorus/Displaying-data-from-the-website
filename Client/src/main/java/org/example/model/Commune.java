package org.example.model;

public class Commune {
    private String communeName;
    private String districtName;
    private String provinceName;

    public Commune(String communeName, String districtName, String provinceName){
        this.communeName = communeName;
        this.districtName = districtName;
        this.provinceName = provinceName;
    }

    public Commune(){
        this(null, null, null);
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
