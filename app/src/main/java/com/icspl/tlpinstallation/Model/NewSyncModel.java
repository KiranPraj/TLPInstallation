package com.icspl.tlpinstallation.Model;

public class NewSyncModel
{
    String tlpinstallation_no,chainage,location,tlp_typ,submited_date;

    public NewSyncModel(String tlpinstallation_no, String chainage, String location, String tlp_typ, String submited_date) {
        this.tlpinstallation_no = tlpinstallation_no;
        this.chainage = chainage;
        this.location = location;
        this.tlp_typ = tlp_typ;
        this.submited_date = submited_date;
    }

    public String getTlpinstallation_no() {
        return tlpinstallation_no;
    }

    public void setTlpinstallation_no(String tlpinstallation_no) {
        this.tlpinstallation_no = tlpinstallation_no;
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

    public String getTlp_typ() {
        return tlp_typ;
    }

    public void setTlp_typ(String tlp_typ) {
        this.tlp_typ = tlp_typ;
    }

    public String getSubmited_date() {
        return submited_date;
    }

    public void setSubmited_date(String submited_date) {
        this.submited_date = submited_date;
    }
}
