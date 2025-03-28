package com.paradoxgameconverters.batoir;
public class CultureRights //These are in addition to the ptimary culture
{
    private int id;
    private String culture;
    private String type; //pop class rights, noble/citizen/freemen/tribesmen/slave
    private int country; //id of country
    public CultureRights()
    {
        int id;
        String culture;
        String type; //pop class rights, noble/citizen/freemen/tribesmen/slave
        int country; //id of country
    }
    
    public void setID(int popID) {
        id = popID;
    }
    public int getID() {
        return id;
    }
    
    public void setCulture(String name) {
        culture = name;
    }
    public String getCulture() {
        return culture;
    }
    
    public void setType(String name) {
        type = name.toLowerCase();
    }
    public String getType() {
        return type;
    }
    
    public void setCountry(int countryID) {
        country = countryID;
    }
    public int getCountry() {
        return country;
    }
    
    public static CultureRights newCultureRights(String newCulture, String newType, int newCountry) {
        CultureRights CR = new CultureRights();
        CR.setCulture(newCulture);
        CR.setType(newType);
        CR.setCountry(newCountry);
        
        return CR;
    }

}
