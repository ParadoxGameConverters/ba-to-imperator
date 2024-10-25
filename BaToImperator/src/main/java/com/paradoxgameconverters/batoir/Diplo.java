package com.paradoxgameconverters.batoir;
public class Diplo
{
    private int overlord; //first
    private int subject; //second
    private String relationType;
    private String relationSubType;

    public Diplo()
    {
        int overlord;
        int subject;
        String relationType;
        String relationSubType;
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
    
    public static Diplo newDiplo(int overlordID, int subjectID, String dipRelationType, String dipRelationSubType) {
        Diplo newDiplo = new Diplo();
        newDiplo.setOverlord(overlordID);
        newDiplo.setSubject(subjectID);
        newDiplo.setRelationType(dipRelationType);
        newDiplo.setRelationSubType(dipRelationSubType);
        
        return newDiplo;
    }
}
