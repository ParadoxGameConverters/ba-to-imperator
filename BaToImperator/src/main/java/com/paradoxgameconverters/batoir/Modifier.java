package com.paradoxgameconverters.batoir;      
public class Modifier
{
    private String name;
    private String value;
    public Modifier()
    {
        String name;
        String value;
    }
    
    public void setName(String modifierName) {
        name = modifierName;
    }
    public String getName() {
        return name;
    }
    
    public void setValue(String totValue) {
        value = totValue;
    }
    public String getValue() {
        return value;
    }
    
    public static Modifier newModifier(String modifierName,String num) {
        Modifier newModifier = new Modifier();
        newModifier.setValue(num);
        newModifier.setName(modifierName);
        
        return newModifier;
    }
}
