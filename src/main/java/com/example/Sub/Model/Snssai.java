package com.example.Sub.Model;

import org.ldaptive.LdapAttribute;
import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;
import org.springframework.stereotype.Component;

@Component
@Entry(
        dn = "distinguishedName",
        attributes = {
                @Attribute(name = "cn", property = "cn"),
                @Attribute(name = "sst", property = "sst"),
                @Attribute(name = "sd", property = "sd"),
                @Attribute(name = "objectClass", values = {"snssai"})

        })
public class Snssai {
    private String cn;
    private int sst;
    private String sd;

    public int getSst() {
        return sst;
    }
    public void setSst(int sst) {
        this.sst = sst;
    }
    public String getSd() {
        return sd;
    }
    public void setSd(String sd) {
        this.sd = sd;
    }
    public String getCn() {
        return cn;
    }
    public void setCn(String cn) {
        this.cn = cn;
    }
    public Snssai() {
    }

    public Snssai( int sst, String sd) {
        this.sst = sst;
        this.sd = sd;
    }

    @Override
    public String toString() {
        return "Snssai{" +
                ", sst='" + sst + '\'' +
                ", sd='" + sd + '\'' +
                '}';
    }


}
