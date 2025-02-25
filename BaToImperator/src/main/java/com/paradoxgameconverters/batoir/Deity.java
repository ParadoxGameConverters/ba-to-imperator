package com.paradoxgameconverters.batoir;
public class Deity
{

    int id;
    int deifiedRuler;
    String deityName;
    public Deity()
    {
        int id;
        int deifiedRuler;
        String deityName;
    }
    
    public void setID(int name) {
        id = name;
    }
    public int getID() {
        return id;
    }
    
    public void setDeifiedRuler(int name) {
        deifiedRuler = name;
    }
    public int getDeifiedRuler() {
        return deifiedRuler;
    }
    
    public void setDeityName(String name) {
        deityName = name;
    }
    public String getDeityName() {
        return deityName;
    }
    
    public static Deity newDeity(int deityID, int rulerID, String nameOfDeity) {
        Deity newDeity = new Deity();
        newDeity.setID(deityID);
        newDeity.setDeifiedRuler(rulerID);
        newDeity.setDeityName(nameOfDeity);
        
        return newDeity;
    }
    
}
