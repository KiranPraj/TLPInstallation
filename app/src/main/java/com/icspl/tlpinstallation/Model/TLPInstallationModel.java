package com.icspl.tlpinstallation.Model;

public class TLPInstallationModel {
    private String tlpno;
    private String chainage;
    private String location;
    private String image;
    private String typeofcabel;
    private String valueoftlpcable;

    public String getTypeofcabel() {
        return typeofcabel;
    }

    public void setTypeofcabel(String typeofcabel) {
        this.typeofcabel = typeofcabel;
    }

    public String getValueoftlpcable() {
        return valueoftlpcable;
    }

    public void setValueoftlpcable(String valueoftlpcable) {
        this.valueoftlpcable = valueoftlpcable;
    }

    public String getTlpno() {
        return tlpno;
    }

    public void setTlpno(String tlpno) {
        this.tlpno = tlpno;
    }

    public String getChainage() {
        return chainage;
    }

    public void setChainage(String chainage) {
        this.chainage = chainage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TLPInstallationModel(String tlpno, String chainage, String location, String image,String valueoftlpcable) {
        this.tlpno = tlpno;
        this.chainage = chainage;
        this.location = location;
        this.image = image;

        this.valueoftlpcable=valueoftlpcable;
    }

}
