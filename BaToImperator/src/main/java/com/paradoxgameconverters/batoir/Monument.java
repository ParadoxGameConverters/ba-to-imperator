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
    public Monument()
    {
        String key;
        ArrayList<String[]> components;
        ArrayList<String[]> effects;
        String category;
        String name; //custom name
        int id;
        int oldID;
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
    
    public static Monument newMonument(String sourceKey, ArrayList<String[]> sourceComponents,ArrayList<String[]> sourceEffects,String sourceCategory,
    String sourceName, int sourceOldID) {
        Monument newMon = new Monument();
        
        newMon.setKey(sourceKey);
        newMon.setComponents(sourceComponents);
        newMon.setEffects(sourceEffects);
        newMon.setCategory(sourceCategory);
        newMon.setName(sourceName);
        newMon.setOldID(sourceOldID);
        
        return newMon;
    }
}
