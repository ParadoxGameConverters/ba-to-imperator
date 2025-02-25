package com.paradoxgameconverters.batoir;
public class Building
{
    private String name;
    private int tot;

    public Building()
    {
        String name;
        int tot;
    }
    
    public void setTot(int num) {
        tot = num;
    }
    public int getTot() {
        return tot;
    }
    
    public void setName(String buildingName) {
        name = buildingName;
    }
    public String getName() {
        return name;
    }
    
    public static Building newBuilding(String buildingName,int num) {
        Building newBuilding = new Building();
        newBuilding.setTot(num);
        newBuilding.setName(buildingName);
        
        return newBuilding;
    }

}
