package com.paradoxgameconverters.batoir;      
import java.util.ArrayList;
/**
 * Write a description of class Heritage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Heritage
{
    private String id;
    private String name;
    private String description;
    private ArrayList<Modifier> modifiers;
    //private ArrayList<String> tags; //associated tags with this modifier

    public Heritage()
    {
        String id;
        String name;
        ArrayList<Modifier> modifiers;
    }
    
    public void setID(String heritageID) {
        id = heritageID;
    }
    public String getID() {
        return id;
    }
    
    public void setName(String heritageName) {
        name = heritageName;
    }
    public String getName() {
        return name;
    }
    
    public void setDesc(String heritageDesc) {
        description = heritageDesc;
    }
    public String getDesc() {
        return description;
    }
    
    public void setModifiers(ArrayList<Modifier> modifiersToReplace) {
        modifiers = modifiersToReplace;
    }
    public void addModifier(Modifier modifierToAdd) {
       if (modifiers == null) {
           modifiers = new ArrayList<Modifier>();
       }
       modifiers.add(modifierToAdd);
    }
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }
    
    public static Heritage newHeritage(String heritageName) {
        Heritage newHeritage = new Heritage();
        newHeritage.setID(heritageName);
        newHeritage.setName(heritageName);
        
        return newHeritage;
    }
}
