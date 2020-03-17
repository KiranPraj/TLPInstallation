package com.icspl.tlpinstallation.Model;

public class TLPCableModel
{
    private String tlpcabletyp;
    private  String number;

    public TLPCableModel(String tlpcabletyp, String number) {
        this.tlpcabletyp = tlpcabletyp;
        this.number = number;
    }

    public String getTlpcabletyp() {
        return tlpcabletyp;
    }

    public void setTlpcabletyp(String tlpcabletyp) {
        this.tlpcabletyp = tlpcabletyp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
