package com.paradoxgameconverters.batoir;

public class Pop
{
    private int id;
    private String type; //pop class, noble/citizen/freemen/tribesmen/slave
    private String culture;
    private String religion;
    public Pop() {
        int id;
        String type; //pop class, noble/citizen/freemen/tribesmen/slave
        String culture;
        String religion;
    }
    
    public void setID(int popID) {
        id = popID;
    }
    public int getID() {
        return id;
    }
    
    public void setType(String name) {
        type = name.toLowerCase();
    }
    public String getType() {
        return type;
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
    
    public static Pop newPop(int popID, String popType, String popCul, String popRel) {
        Pop newPop = new Pop();
        newPop.setID(popID);
        newPop.setType(popType);
        newPop.setCulture(popCul);
        newPop.setReligion(popRel);
        
        return newPop;
    }
}
