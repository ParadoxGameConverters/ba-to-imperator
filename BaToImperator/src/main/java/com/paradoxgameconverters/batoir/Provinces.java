package com.paradoxgameconverters.batoir;
import java.util.ArrayList;
public class Provinces
{

    //private int id;
    private String owner;
    private String culture;
    private String religion;
    private ArrayList<Pop> provPops;
    private int monument;
    private double nobleRatio;
    private double citizenRatio;
    private double freemenRatio;
    private double tribesmenRatio;
    private double slaveRatio;
    public Provinces() {
        //int id;
        String owner;
        String culture;
        String religion;
        ArrayList<Pop> provPops;
        int monument;
        double nobleRatio;
        double citizenRatio;
        double freemenRatio;
        double tribesmenRatio;
        double slaveRatio;
    }

    public void setNobleRatio(double num) {
        nobleRatio = num;
    }
    public double getNobleRatio() {
        return nobleRatio;
    }
    
    public void setCitizenRatio(double num) {
        citizenRatio = num;
    }
    public double getCitizenRatio() {
        return citizenRatio;
    }
    
    public void setFreemenRatio(double num) {
        freemenRatio = num;
    }
    public double getFreemenRatio() {
        return freemenRatio;
    }
    
    public void setTribesmenRatio(double num) {
        tribesmenRatio = num;
    }
    public double getTribesmenRatio() {
        return tribesmenRatio;
    }
    
    public void setSlaveRatio(double num) {
        slaveRatio = num;
    }
    public double getSlaveRatio() {
        return slaveRatio;
    }
    
    public void setOwner(String tag) {
        owner = tag;
    }
    public String getOwner() {
        return owner;
    }
    
    public void setCulture(String name) {
        culture = name;
    }
    public String getCulture() {
        return culture;
    }
    
    public void setReligion(String name) {
        religion = name;
    }
    public String getReligion() {
        return religion;
    }
    
    public void setMonument(int monId) {
        monument = monId;
    }
    public int getMonument() {
        return monument;
    }
    
    public void addPop(Pop pop) {
        if (provPops == null) {
            provPops = new ArrayList<Pop> ();
        }
        provPops.add(pop);
    }
    public void addPopArray(ArrayList<Pop> popListToAdd) {
        if (provPops == null) {
            provPops = new ArrayList<Pop> ();
        }
        provPops.add(popListToAdd.get(0));
        int count = 1;
        while (count < popListToAdd.size()) {
            provPops.add(popListToAdd.get(count));
            count = count + 1;
        }
    }
    public ArrayList<Pop> getPops() {
        return provPops;
    }
    
    public static Provinces newProv(String provOwner, String provCul, String provRel, int provMon, double provNR, double provCR, double provFR,
    double provTR, double provSR) {
        Provinces newProv = new Provinces();
        newProv.setOwner(provOwner);
        newProv.setCulture(provCul);
        newProv.setReligion(provRel);
        newProv.setMonument(provMon);
        newProv.setNobleRatio(provNR);
        newProv.setCitizenRatio(provCR);
        newProv.setFreemenRatio(provFR);
        newProv.setTribesmenRatio(provTR);
        newProv.setSlaveRatio(provSR);
        return newProv;
    }
    
}