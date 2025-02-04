package com.paradoxgameconverters.batoir;
public class Diplo
{
    private int overlord; //first
    private int subject; //second
    private String relationType;
    private String relationSubType;
    private String rawOverlord; //Raw tag, used only to reference vanilla countries or exoTags
    private String rawSubject; //Raw tag, used only to reference vanilla countries or exoTags

    public Diplo()
    {
        int overlord;
        int subject;
        String relationType;
        String relationSubType;
        String rawOverlord;
        String rawSubject;
    }

    public void setOverlord(int id) {
        overlord = id;
    }
    public int getOverlord() {
        return overlord;
    }
    
    public void setSubject(int id) {
        subject = id;
    }
    public int getSubject() {
        return subject;
    }
    
    public void setRelationType(String name) { //dependency, alliance, guarentee, ect.
        relationType = name;
    }
    public String getRelationType() {
        return relationType;
    }
    
    public void setRelationSubType(String name) { //client state, tributary, ect.
        relationSubType = name;
    }
    public String getRelationSubType() {
        return relationSubType;
    }
    
    public void setRawOverlord(String name) {
        rawOverlord = name;
    }
    public String getRawOverlord() {
        return rawOverlord;
    }
    
    public void setRawSubject(String name) {
        rawSubject = name;
    }
    public String getRawSubject() {
        return rawSubject;
    }
    
    public static Diplo newDiplo(int overlordID, int subjectID, String dipRelationType, String dipRelationSubType) {
        Diplo newDiplo = new Diplo();
        newDiplo.setOverlord(overlordID);
        newDiplo.setSubject(subjectID);
        newDiplo.setRelationType(dipRelationType);
        newDiplo.setRelationSubType(dipRelationSubType);
        
        newDiplo.setRawOverlord("none"); //default values
        newDiplo.setRawSubject("none"); //default values
        
        return newDiplo;
    }
}
