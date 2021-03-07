package com.example.vp;

public class UserHelperClass {

    String dname, dage,dgender, demail, dhospital, dspcl, dmob, dpass;

    public UserHelperClass() {
    }

    public UserHelperClass(String dname, String dage, String dgender, String demail, String dhospital, String dspcl, String dmob, String dpass) {
        this.dname = dname;
        this.dage = dage;
        this.dgender = dgender;
        this.demail = demail;
        this.dhospital = dhospital;
        this.dspcl = dspcl;
        this.dmob = dmob;
        this.dpass = dpass;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDage() {
        return dage;
    }

    public void setDage(String dage) {
        this.dage = dage;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDgender() {
        return dgender;
    }

    public void setDgender(String demail) {
        this.dgender = dgender;
    }

    public String getDhospital() {
        return dhospital;
    }

    public void setDhospital(String dhospital) {
        this.dhospital = dhospital;
    }

    public String getDspcl() {
        return dspcl;
    }

    public void setDspcl(String dspcl) {
        this.dspcl = dspcl;
    }

    public String getDmob() {
        return dmob;
    }

    public void setDmob(String dmob) {
        this.dmob = dmob;
    }

    public String getDpass() {
        return dpass;
    }

    public void setDpass(String dpass) {
        this.dpass = dpass;
    }

}
