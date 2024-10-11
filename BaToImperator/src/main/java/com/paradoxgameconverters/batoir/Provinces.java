package com.paradoxgameconverters.batoir;
import java.util.ArrayList;
public class Provinces
{

    private int id;
    private String owner;
    private String culture;
    private String religion;
    private ArrayList<Pop> provPops;
    private String tradeGood;
    private String terrain;
    private int monument;
    private double nobleRatio;
    private double citizenRatio;
    private double freemenRatio;
    private double tribesmenRatio;
    private double slaveRatio;
    private String region;
    private String area;
    private String cityStatus;
    public Provinces() {
        int id;
        String owner;
        String culture;
        String religion;
        ArrayList<Pop> provPops;
        String tradeGood;
        String terrain;
        int monument;
        double nobleRatio;
        double citizenRatio;
        double freemenRatio;
        double tribesmenRatio;
        double slaveRatio;
        String region;
        String area;
        String cityStatus; //is expected to be either settlement, city, or metropolis
    }
    
    public void setCityStatus(String provCityStatus) {
        cityStatus = provCityStatus;
    }

    public String getCityStatus() {
        return cityStatus;
    }
    
    public void setArea(String provArea) {
        area = provArea;
    }

    public String getArea() {
        return area;
    }
    
    public void setRegion(String provRegion) {
        region = provRegion;
    }

    public String getRegion() {
        return region;
    }
    
    public void setTradeGood(String provTradeGood) {
        tradeGood = provTradeGood;
    }

    public String getTradeGood() {
        return tradeGood;
    }
    
    public void setTerrain(String provTerrain) {
        terrain = provTerrain;
    }

    public String getTerrain() {
        return terrain;
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
    
    public void setID(int provinceID) {
        //monument = provinceID;
        id = provinceID;
    }

    public int getID() {
        return id;
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
    
    public void replacePops(ArrayList<Pop> popListToAdd) {
        provPops = new ArrayList<Pop> ();
        provPops = popListToAdd;
    }
    
    public void replacePopsFromStringArray(ArrayList<String> popListToAdd,float[] typeRatios, int allPopTot) {
        provPops = new ArrayList<Pop> ();
        int count = 0;
        //int allPopTot = popListToAdd.size();
        int nobleNum = Processing.round(typeRatios[0] * allPopTot);
        int citizenNum = Processing.round(typeRatios[1] * allPopTot);
        int freemenNum = Processing.round(typeRatios[2] * allPopTot);
        int slaveNum = Processing.round(typeRatios[3] * allPopTot);
        int tribesmenNum = Processing.round(typeRatios[4] * allPopTot);
        
        int nobleNumOrg = Processing.roundUp(typeRatios[0] * allPopTot);
        int citizenNumOrg = Processing.roundUp(typeRatios[1] * allPopTot);
        int freemenNumOrg = Processing.roundUp(typeRatios[2] * allPopTot);
        int slaveNumOrg = Processing.roundUp(typeRatios[3] * allPopTot);
        int tribesmenNumOrg = Processing.roundUp(typeRatios[4] * allPopTot);
        int baq = popListToAdd.size();
        while (count < popListToAdd.size()) {
            String[] popSource = popListToAdd.get(count).split(",");
            int popTot = Integer.parseInt(popSource[1]);
            String[] popInfo = popSource[0].split("---");
            String popCulture = popInfo[0];
            String popReligion = popInfo[1];
            int count2 = 0;
            while (count2 < popTot) {
                int Rng = (int) (Math.random() * 5);
                String popType = "Debug";
                //Determine which pop gets which type through RNG, until all available types are exhausted
                if (Rng == 0 && nobleNum > 0) {
                    popType = "nobles";
                    nobleNum = nobleNum - 1;
                }
                else if (Rng == 1 && citizenNum > 0) {
                    popType = "citizen";
                    citizenNum = citizenNum - 1;
                }
                else if (Rng == 2 && freemenNum > 0) {
                    popType = "freemen";
                    freemenNum = freemenNum - 1;
                }
                else if (Rng == 3 && slaveNum > 0) {
                    popType = "slaves";
                    slaveNum = slaveNum - 1;
                }
                else if (Rng == 4 && tribesmenNum > 0) {
                    popType = "tribesmen";
                    tribesmenNum = tribesmenNum - 1;
                } else { //if reached the bottom of the barrel, ignore RNG and assign based on what is left
                    if (slaveNum > 0) {
                        popType = "slaves";
                        slaveNum = slaveNum - 1;
                    }
                    else if (freemenNum > 0) {
                        popType = "freemen";
                        freemenNum = freemenNum - 1;
                    }
                    else if (citizenNum > 0) {
                        popType = "citizen";
                        citizenNum = citizenNum - 1;
                    }
                    else if (nobleNum > 0) {
                        popType = "nobles";
                        nobleNum = nobleNum - 1;
                    }
                    else if (tribesmenNum > 0) {
                        popType = "tribesmen";
                        nobleNum = tribesmenNum - 1;
                    }
                    else { //if something goes wrong, display error
                        System.out.println("Error, ran out of pop types");
                    }
                }
                Pop generatedPop = Pop.newPop(0,popType,popCulture,popReligion);
                provPops.add(generatedPop);
                count2 = count2 + 1;
            }
            //Pop generatedPop = Pop.newPop();
            count = count + 1;
        }
        //provPops = popListToAdd;
    }

    public ArrayList<Pop> getPops() {
        return provPops;
    }

    public ArrayList<Pop> getPopType(String type) {
        int count = 0;
        ArrayList<Pop> typePops = new ArrayList<Pop> ();
        try {
            while(count < provPops.size()) {
                Pop selectedPop = provPops.get(count);
                //System.out.println(selectedPop.getType());
                if (selectedPop.getType().equals(type)) {
                    typePops.add(selectedPop);
                }
                count = count + 1;
            }
        } catch (Exception e) {

        }
        return typePops;

    }
    
    public ArrayList<Pop> getNobles() {
        return getPopType("nobles");
    }
    public ArrayList<Pop> getCitizens() {
        return getPopType("citizen");
    }
    public ArrayList<Pop> getFreemen() {
        return getPopType("freemen");
    }
    public ArrayList<Pop> getTribesmen() {
        return getPopType("tribesmen");
    }
    public ArrayList<Pop> getSlaves() {
        return getPopType("slaves");
    }
    
    public void setPopTags() {
        int count = 0;
        try {
            while(count < provPops.size()) {
                Pop selectedPop = provPops.get(count);
                selectedPop.setTag(owner);
                //System.out.println(selectedPop.getType());
                
                count = count + 1;
            }
        } catch (Exception e) {

        }

    }
    
    public void setPopCS() { //sets city-status for all pops
        int count = 0;
        try {
            while(count < provPops.size()) {
                Pop selectedPop = provPops.get(count);
                selectedPop.setCityStatus(cityStatus);
                //System.out.println(selectedPop.getType());
                
                count = count + 1;
            }
        } catch (Exception e) {

        }

    }
    

    public static Provinces newProv(String provOwner, String provCul, String provRel, int provMon, double provNR, double provCR, double provFR,
    double provTR, double provSR, String provCS, int provID) {
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
        newProv.setCityStatus(provCS);
        newProv.setID(provID);
        return newProv;
    }

}
