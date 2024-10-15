package com.paradoxgameconverters.batoir;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;
/**
 * Everything dealing with characters
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Characters
{
    private int baID;
    private String culture;
    private String religion;
    private int age;
    private String sex;
    private String dynastyName;
    private int dynastyID;
    private ArrayList<String> traits;
    private int martial;
    private int finesse;
    private int charisma;
    private int zeal;
    private int spouse;
    private ArrayList<Integer> children;
    private int irID;
    private double corruption;
    private String birthday;
    private String deathday;
    private String name;
    private int country;
    private boolean pruneStatus;
    private int mother;
    private int father;
    public Characters() {
        int baID;
        String culture;
        String religion;
        int age;
        String sex;
        String dynasty;
        String traits;
        int martial;
        int finesse;
        int charisma;
        int zeal;
        int spouse;
        Integer[] children;
        int familyName;
        int irID;
        double corruption;
        String birthday;
        String deathday;
        String name;
        int country;
        boolean pruneStatus;
        int mother;
        int father;
    }
    
    public void setBaID(int charBaID) {
        baID = charBaID;
    }
    public int getBaID() {
        return baID;
    }
    
    public void setCulture(String charCulture) {
        culture = charCulture;
    }
    public String getCulture() {
        return culture;
    }
    
    public void setReligion(String charReligion) {
        religion = charReligion;
    }
    public String getReligion() {
        return religion;
    }
    
    public void setAge(int charAge) {
        age = charAge;
    }
    public int getAge() {
        return age;
    }
    
    public void setSex(String charSex) {
        sex = charSex;
    }
    public String getSex() {
        return sex;
    }
    
    public void setDynastyName(String charDynastyName) {
        dynastyName = charDynastyName;
    }
    public String getDynastyName() {
        return dynastyName;
    }
    
    public void setDynastyID(int charDynastyID) {
        dynastyID = charDynastyID;
    }
    public int getDynastyID() {
        return dynastyID;
    }
    
    public void setTraits(ArrayList<String> charTraits) {
        traits = charTraits;
    }
    public ArrayList<String> getTraits() {
        return traits;
    }
    
    public void setMartial(int charMartial) {
        martial = charMartial;
    }
    public int getMartial() {
        return martial;
    }
    
    public void setFinesse(int charFinesse) {
        finesse = charFinesse;
    }
    public int getFinesse() {
        return finesse;
    }
    
    public void setCharisma(int charCharisma) {
        charisma = charCharisma;
    }
    public int getCharisma() {
        return charisma;
    }
    
    public void setZeal(int charZeal) {
        zeal = charZeal;
    }
    public int getZeal() {
        return zeal;
    }
    
    public void setChildren(ArrayList<Integer> charChildren) {
        children = charChildren;
    }
    public ArrayList<Integer> getChildren() {
        return children;
    }
    
    public void setSpouse(int charSpouse) {
        spouse = charSpouse;
    }
    public int getSpouse() {
        return spouse;
    }
    
    public void setIrID(int charIrID) {
        irID = charIrID;
    }
    public int getIrID() {
        return irID;
    }
    
    public void setCorruption(double charCorruption) {
        corruption = charCorruption;
    }
    public double getCorruption() {
        return corruption;
    }
    
    public void setBirthday(String charBirthday) {
        birthday = charBirthday;
    }
    public String getBirthday() {
        return birthday;
    }
    
    public void setDeathday(String charDeathday) {
        deathday = charDeathday;
    }
    public String getDeathday() {
        return deathday;
    }
    
    public void setName(String charName) {
        name = charName;
    }
    public String getName() {
        return name;
    }
    
    public void setCountry(int charCountry) {
        country = charCountry;
    }
    public int getCountry() {
        return country;
    }
    
    public void setPruneStatus(boolean charPrune) {
        pruneStatus = charPrune;
    }
    public boolean isPruned() {
        return pruneStatus;
    }
    
    public void setMother(int charMother) {
        mother = charMother;
    }
    public int getMother() {
        return mother;
    }
    
    public void setFather(int charFather) {
        father = charFather;
    }
    public int getFather() {
        return father;
    }
    
    public static Characters newCharacter(int baID,String culture,String religion,String sex,int dynastyID,ArrayList<String> traits,int martial,
    int finesse,int charisma,int zeal,int spouse,ArrayList<Integer> children,double corruption,String birthday,String deathday,int age,String name,
    int country, int mother, int father) {
        Characters newCharacter = new Characters();
        
        newCharacter.setBaID(baID);
        newCharacter.setCulture(culture);
        newCharacter.setReligion(religion);
        newCharacter.setSex(sex);
        newCharacter.setDynastyID(dynastyID);
        newCharacter.setTraits(traits);
        newCharacter.setMartial(martial);
        newCharacter.setFinesse(finesse);
        newCharacter.setCharisma(charisma);
        newCharacter.setZeal(zeal);
        newCharacter.setSpouse(spouse);
        newCharacter.setChildren(children);
        newCharacter.setCorruption(corruption);
        newCharacter.setBirthday(birthday);
        newCharacter.setDeathday(deathday);
        newCharacter.setAge(age);
        newCharacter.setName(name);
        newCharacter.setCountry(country);
        newCharacter.setPruneStatus(false); //All characters are unpruned unless they are pruned
        newCharacter.setMother(mother);
        newCharacter.setFather(father);
        return newCharacter;
    }
    
    public static ArrayList<Characters> importChar (String name,int compressedOrNot) throws IOException
    {

        String tab = "	";
        char quote = '"';

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String keyWord = "1={";

        int aqq = 0;
        
        int idCount = 0;

        ArrayList<String[]> impCharList= new ArrayList<String[]>();
        ArrayList<Characters> baCharacters = new ArrayList<Characters>();

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[23];

        output[0] = "noName"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[3] = "30"; //default age
        output[4] = "m"; //by default all characters are male, unless specified to be female
        output[7] = "q"; //default for no dynasty
        output[8] = "q"; //default for no traits
        output[10] = "0"; //default for no martial
        output[11] = "0"; //default for no finesse
        output[12] = "0"; //default for no charisma
        output[13] = "0"; //default for no zeal
        output[14] = "none"; //default for unmarried character
        output[15] = "-1"; //default for character with no children
        output[16] = "0"; //default for minor character with no family name
        output[17] = "0.0"; //default for no corruption
        output[18] = "0.0.100"; //default for no birthday
        output[19] = "none"; //default for no death date
        output[20] = "0"; //default for no country
        output[21] = "-1"; //default for no mother
        output[22] = "-1"; //default for no father

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                flag = 0;
                //endOrNot = false;
                while (flag == 0) {
                    qaaa = scnr.nextLine();
                    if (compressedOrNot == 0 && qaaa.length() >= 3 ) { //Rakaly adds 2 tab characters to most lines in character database
                        qaaa = qaaa.substring(2,qaaa.length());
                    }

                    if (qaaa.split("=")[0].equals( tab+tab+"name" ) || qaaa.split("=")[0].equals( tab+tab+"custom_name" )) {
                        output[0] = qaaa.split("=")[1];
                        output[0] = output[0].substring(1,output[0].length()-1);
                        output[0] = output[0].replace("_","'");
                    }
                    else if (qaaa.split("=")[0].equals( tab+"country" ) ) {
                        output[20] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+"family_name" ) ) {
                        output[16] = qaaa.split("=")[1];
                        output[16] = output[16].substring(1,output[16].length()-1);
                    }
                    else if (qaaa.split("=")[0].equals( tab+"father" ) ) {
                        output[22] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+"mother" ) ) {
                        output[21] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+"culture" ) ) {
                        output[1] = qaaa.split("=")[1];
                        output[1] = output[1].substring(1,output[1].length()-1);
                    }
                    else if (qaaa.split("=")[0].equals( tab+"religion" ) ) {
                        output[2] = qaaa.split("=")[1];
                        output[2] = output[2].substring(1,output[2].length()-1);
                    }
                    else if (qaaa.split("=")[0].equals( tab+"family" ) ) {
                        output[7] = qaaa.split("=")[1];

                    }
                    else if (qaaa.split("=")[0].equals( tab+"traits" ) ) {
                        if (qaaa.length() == 9) { //Rakaly save format
                            qaaa = scnr.nextLine();
                            output[8] = qaaa.replace(tab,"");
                        } else { //regular decompressed save format
                            output[8] = qaaa.split("=")[1];

                            output[8] = output[8].substring(2,output[8].length()-2);
                        }

                    }
                    else if (qaaa.split("=")[0].equals( tab+tab+"martial" ) ) {
                        output[10] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+tab+"finesse" ) ) {
                        output[11] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+tab+"charisma" ) ) {
                        output[12] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+tab+"zeal" ) ) {
                        output[13] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+"spouse" ) ) {
                        if (qaaa.length() == 9) { //Rakaly save format
                            qaaa = scnr.nextLine();
                            output[14] = qaaa.replace(tab,"");
                        } else { //regular decompressed save format
                            output[14] = qaaa.split("=")[1];
                            output[14] = output[14].substring(2,output[14].length()-2); 
                        }

                        try {
                            if (output[14].split(" ")[1] != null) {
                                output[14] = output[14].split(" ")[output[14].split(" ").length-1];   
                            }
                        }catch (java.lang.ArrayIndexOutOfBoundsException exception) {

                        }
                    }
                    else if (qaaa.split("=")[0].equals( tab+"children" ) ) {

                        if (qaaa.length() == 11) { //Rakaly save format
                            qaaa = scnr.nextLine();
                            output[15] = qaaa.replace(tab,"");
                        } else { //regular decompressed save format
                            output[15] = qaaa.split("=")[1];
                            output[15] = output[15].substring(2,output[15].length()-2);  
                        }

                    }

                    //popTotal
                    else if (qaaa.split("=")[0].equals( tab+"age" ) ) {
                        aqq = aqq + 1;
                        output[3] = qaaa.split("=")[1];
                    }
                    else if (qaaa.split("=")[0].equals( tab+"birth_date" ) ) {
                        aqq = aqq + 1;
                        output[18] = qaaa.split("=")[1];
                    }


                    else if (qaaa.equals( tab+"female=yes" ) ) {
                        aqq = aqq + 1;
                        output[4] = "f";
                    }

                    else if (qaaa.split("=")[0].equals( tab+"death_date" ) ) {
                        aqq = aqq + 1;
                        flag = 1; //end loop, babies which die don't have any experience field
                        //output[4] = output[4]+"_"+qaaa.split("=")[1];
                        output[6] = "0";
                        output[19] = qaaa.split("=")[1];
                    }

                    else if (qaaa.split("=")[0].equals( tab+"character_experience" ) ) {
                        aqq = aqq + 1;
                        flag = 1; //end loop
                        output[6] = qaaa.split("=")[1];
                    }
                    
                    else if (qaaa.split("=")[0].equals( tab+"corruption" ) ) {
                        output[17] = qaaa.split("=")[1];
                    }

                    else if (qaaa.split("=")[0].equals( Integer.toString(impCharList.size()+1) ) ) { //Somehow has gone past checks, immediately end
                        aqq = aqq + 1;
                        flag = 1; //end loop
                        output[6] = "0";
                    }

                    if (flag == 1) {

                        String[] tmpOutput = new String[output.length];

                        int aq2 = 0;

                        while (aq2 < output.length) {
                            tmpOutput[aq2] = output[aq2];
                            aq2 = aq2 + 1;
                        }

                        impCharList.add(tmpOutput);
                        
                        int dynID = Integer.parseInt(tmpOutput[7]);
                        
                        ArrayList<String> traitsList = new ArrayList<String>();
                        String traitsArray[] = tmpOutput[8].split(" ");
                        aq2 = 0;
                        while (aq2 < traitsArray.length-1) {
                            String selectedTrait = traitsArray[aq2];
                            //System.out.println(selectedTrait);
                            selectedTrait = Processing.cutQuotes(selectedTrait);
                            traitsList.add(selectedTrait);
                            aq2 = aq2 + 1;
                        }
                        
                        int tmpM = Integer.parseInt(tmpOutput[10]);
                        int tmpF = Integer.parseInt(tmpOutput[11]);
                        int tmpC = Integer.parseInt(tmpOutput[12]);
                        int tmpZ = Integer.parseInt(tmpOutput[13]);
                        
                        int tmpSpouse = -1;
                        
                        if (tmpOutput[14].contains(" ")) {
                            String[] tmpSpouses = tmpOutput[14].split(" ");
                            tmpOutput[14] = tmpSpouses[tmpSpouses.length-1];
                        }
                        
                        if (!tmpOutput[14].equals("none")) {
                            tmpSpouse = Integer.parseInt(tmpOutput[14]);
                        }
                        
                        ArrayList<Integer> tmpChildren = new ArrayList<Integer>();
                        String childrenArray[] = tmpOutput[15].split(" ");
                        aq2 = 0;
                        while (aq2 < childrenArray.length) {
                            int selectedChild = Integer.parseInt(childrenArray[aq2]);
                            if (selectedChild > -1) {
                                tmpChildren.add(selectedChild);
                            }
                            aq2 = aq2 + 1;
                        }
                        
                        double tmpCorruption = Double.parseDouble(tmpOutput[17]);
                        int tmpAge = Integer.parseInt(tmpOutput[3]);
                        int tmpCountry = Integer.parseInt(tmpOutput[20]);
                        int tmpMother = Integer.parseInt(tmpOutput[21]);
                        int tmpFather = Integer.parseInt(tmpOutput[22]);
                        
                        Characters convertedCharacter = newCharacter(idCount,tmpOutput[1],tmpOutput[2],tmpOutput[4],dynID,traitsList,
                        tmpM,tmpF,tmpC,tmpZ,tmpSpouse,tmpChildren,tmpCorruption,tmpOutput[18],tmpOutput[19],tmpAge,tmpOutput[0],tmpCountry,tmpMother,
                        tmpFather);
                        
                        if (!tmpOutput[16].equals("0")) {
                            convertedCharacter.setDynastyName("minor_"+tmpOutput[16]);
                        }
                        
                        baCharacters.add(convertedCharacter);
                        
                        idCount = idCount + 1;

                        output[0] = "noName"; //default for no owner, uncolonized province
                        output[1] = "noCulture"; //default for no culture
                        output[2] = "noReligion"; //default for no religion
                        output[3] = "30"; //default age
                        output[4] = "m"; //by default all characters are male, unless specified to be female
                        output[7] = "q"; //default for no dynasty
                        output[8] = "q"; //default for no traits
                        output[10] = "0"; //default for no martial
                        output[11] = "0"; //default for no finesse
                        output[12] = "0"; //default for no charisma
                        output[13] = "0"; //default for no zeal
                        output[14] = "none"; //default for unmarried character
                        output[15] = "-1"; //default for character with no children
                        output[16] = "0"; //default for minor character with no family name
                        output[17] = "0.0"; //default for no corruption
                        output[18] = "0.0.100"; //default for no birthday
                        output[19] = "none"; //default for no death date
                        output[20] = "0"; //default for no country
                        output[21] = "-1"; //default for no mother
                        output[22] = "-1"; //default for no father
                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return baCharacters;

    }

    public static ArrayList<String> importDynasty (String file) throws IOException
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(file);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        int flag2 = 0;

        int aqq = 0;
        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String output = "noName"; //default for no name
        String tag = "noTag"; //default for no tag controlling the dynasty
        String major = "yes"; //assume major unless proven otherwise
        String members = "none";

        ArrayList<String> dynList= new ArrayList<String>();

        String id = "0";

        try {
            try {
                while (endOrNot = true){

                    qaaa = scnr.nextLine();
                    if (qaaa.equals(tab+tab+"}")) {
                        int flag1 = 0;
                        while (flag1 == 0) {
                            qaaa = scnr.nextLine();
                            if (qaaa.split("=")[0].equals( tab+tab+tab+"key" )) {
                                flag1 = 1;
                            } 
                            else {
                                id = qaaa.split(tab+tab)[1];
                                id = id.split("=")[0];
                            }

                        }
                    }
                    if (qaaa.split("=")[0].equals( tab+tab+tab+"key" )) {

                        output = qaaa.split("=")[1];
                        output = output.substring(1,output.length()-1);
                        aqq = aqq + 1;
                        qaaa = scnr.nextLine();
                    }
                    if (qaaa.split("=")[0].replace(tab,"").equals( "owner" )) {
                        tag = qaaa.split("=")[1];
                        qaaa = scnr.nextLine();
                    }
                    if (qaaa.split("=")[0].replace(tab,"").equals( "minor_family" )) {
                        major = "no";
                        members = "none";
                        flag2 = 1;

                    }
                    if (qaaa.split("=")[0].replace(tab,"").equals( "member" ) && !output.equals("noName")) {
                        members = qaaa.split("=")[1];
                        if (members.length() == 1) { //melted save
                            qaaa = scnr.nextLine();
                            members = qaaa.replace(tab,"");
                        } else {
                            members = members.substring(2,members.length()-2);
                        }
                        flag2 = 1;
                        members = members.replace(" ","~");
                    }

                    if (flag2 == 1) {
                        flag2 = 0;
                        String tmpOutput = output+","+id+","+tag+","+major+","+members;
                        tmpOutput = tmpOutput.replace("_"," ");
                        dynList.add(tmpOutput);
                        output = "noName"; //default for no name
                        tag = "noTag"; //default for no tag controlling the dynasty
                        major = "yes"; //assume major unless proven otherwise
                        members = "none";
                    }

                }
            }catch(java.lang.ArrayIndexOutOfBoundsException exception) {
                endOrNot = false;
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return dynList;
    }

    public static String searchDynasty (ArrayList<String> dynList, String id) throws IOException //searches dynList for id
    {
        int aqq = 0;

        while (aqq < dynList.size()) {
            String[] dyn = dynList.get(aqq).split(",");
            if (dyn[1].equals(id)) {
                return dyn[0];
            } else {
                aqq = aqq + 1;
            }
        }

        return id;
    }
    
    public static String[] searchWholeDynasty (ArrayList<String> dynList, String id) throws IOException //searches dynList for all info
    {
        int aqq = 0;

        while (aqq < dynList.size()) {
            String[] dyn = dynList.get(aqq).split(",");
            if (dyn[1].equals(id)) {
                return dyn;
            } else {
                aqq = aqq + 1;
            }
        }
        
        String[] debug = "debug".split(",");

        return debug;
    }
    
    public static String getMajorFamilies (ArrayList<String> dynList, String id) throws IOException //get's all major families for a country
    {
        int aqq = 0;
        String familyList = "";

        while (aqq < dynList.size()) {
            String[] dyn = dynList.get(aqq).split(",");
            if (dyn[2].equals(id) && dyn[3].equals("yes")) {
                familyList = familyList + "," + dyn[1];
            }
            
            aqq = aqq + 1;
        }

        return familyList;
    }
    
    public static int getAvailableID (String dir) throws IOException //get's the first available character ID
    {
        int count = 0;
        String charDirectory = dir+"/setup/characters";
        
        File characterFolder = new File(charDirectory);
        String[] characterFiles = characterFolder.list();
        int highestID = 1;
        String highestFile = "";
        System.out.println(charDirectory);

        while (count < characterFiles.length) {
            String charFileLocation = charDirectory+"/"+characterFiles[count];
            ArrayList<String> charFile = Importer.importBasicFile(charFileLocation);
            int count2 = 0;
            while (count2 < charFile.size()) {
                String line = charFile.get(count2);
                if (line.contains("={")) {
                    //System.out.println(line);
                    line = line.split("=")[0];
                    line = line.replace("\t","");
                    line = line.replace(" ","");
                    int charID = 0;
                    try {
                        charID = Integer.parseInt(line);
                        if (charID > highestID) {
                            highestID = charID;
                            highestFile = characterFiles[count];
                        }
                    } catch (java.lang.NumberFormatException exception) {
                        //System.out.println(line+" is no number!");
                    }
                }
                
                count2 = count2 + 1;
            }

            //System.out.println("The highest character is in "+highestFile);
            count = count + 1;
        }
        int availableID = highestID + 1; //first available ID is the highest + 1
        System.out.println("The highest character is in "+highestFile);

        return availableID;
    }
    
}
