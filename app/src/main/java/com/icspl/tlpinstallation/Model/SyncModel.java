package com.icspl.tlpinstallation.Model;

public class SyncModel {
    String date,clientName,Contractorname,Sectionname,SectionNo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractorname() {
        return Contractorname;
    }

    public void setContractorname(String contractorname) {
        Contractorname = contractorname;
    }

    public String getSectionname() {
        return Sectionname;
    }

    public void setSectionname(String sectionname) {
        Sectionname = sectionname;
    }

    public String getSectionNo() {
        return SectionNo;
    }

    public void setSectionNo(String sectionNo) {
        SectionNo = sectionNo;
    }

    public SyncModel(String date, String clientName, String contractorname, String sectionname, String sectionNo) {
        this.date = date;
        this.clientName = clientName;
        this.Contractorname = contractorname;
        this.Sectionname = sectionname;
        this.SectionNo = sectionNo;
    }
}
