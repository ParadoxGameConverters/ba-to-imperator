package com.paradoxgameconverters.batoir;
import java.util.ArrayList;
public class Monument
{
    private String key;
    private ArrayList<String[]> components;
    private ArrayList<String[]> effects;
    private String category;
    private String name; //custom name
    private int id; //unique ID
    private int oldID; //original ID referenced to in save-file
    private String genericName; //auto-generated namr
    private String familyName;
    private String provName;
    private boolean isHistorical;
    public Monument()
    {
        String key;
        ArrayList<String[]> components;
        ArrayList<String[]> effects;
        String category;
        String name; //custom name
        int id;
        int oldID;
        String genericName; //auto-generated namr
        String familyName;
        String provName;
        boolean isHistorical;
    }
    
    public void setKey(String sourceKey) {
        key = sourceKey;
    }
    public String getKey() {
        return key;
    }
    
    public void setComponents(ArrayList<String[]> sourceComponents) {
        components = sourceComponents;
    }
    public ArrayList<String[]> getComponents() {
        return components;
    }
    
    public void setEffects(ArrayList<String[]> sourceEffects) {
        effects = sourceEffects;
    }
    public ArrayList<String[]> getEffects() {
        return effects;
    }
    
    public void setCategory(String sourceCategory) {
        category = sourceCategory;
    }
    public String getCategory() {
        return category;
    }
    
    public void setName(String sourceName) {
        name = sourceName;
    }
    public String getName() {
        return name;
    }
    
    public void setID(int sourceID) {
        id = sourceID;
    }
    public int getID() {
        return id;
    }
    
    public void setOldID(int sourceOldID) {
        oldID = sourceOldID;
    }
    public int getOldID() {
        return oldID;
    }
    
    public void setGenericName(String sourceGN) {
        genericName = sourceGN;
    }
    public String getGenericName() {
        return genericName;
    }
    
    public void setFamilyName(String sourceFN) {
        familyName = sourceFN;
    }
    public String getFamilyName() {
        return familyName;
    }
    
    public void setProvName(String sourcePN) {
        provName = sourcePN;
    }
    public String getProvName() {
        return provName;
    }
    
    public void setIsHistorical(boolean tf) {
        isHistorical = tf;
    }
    public boolean getIsHistorical() {
        return isHistorical;
    }
    
    public static Monument newMonument(String sourceKey, ArrayList<String[]> sourceComponents,ArrayList<String[]> sourceEffects,String sourceCategory,
    String sourceName, int sourceOldID) {
        Monument newMon = new Monument();
        
        newMon.setKey(sourceKey);
        newMon.setComponents(sourceComponents);
        newMon.setEffects(sourceEffects);
        newMon.setCategory(sourceCategory);
        newMon.setName(sourceName);
        newMon.setOldID(sourceOldID);
        
        newMon.setID(sourceOldID);
        
        newMon.setGenericName("none");
        newMon.setFamilyName("none");
        newMon.setProvName("none");
        
        newMon.setIsHistorical(false); //Default is false, unless later specified to be true
        
        return newMon;
    }
}
