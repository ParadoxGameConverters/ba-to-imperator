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
 * Importer deals with importing most information from a save file.
 *
 * @author Shinymewtwo99
 * @version
 */
public class Importer
{

    public static ArrayList<Provinces> importProv (String name) throws IOException
    {

        //String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impProvList= new ArrayList<String[]>();
        
        ArrayList<Pop> baPopList= new ArrayList<Pop>();
        
        ArrayList<Provinces> baProvList= new ArrayList<Provinces>();

        String tab = "	";

        int flag = 0;

        String keyWord = tab+1+"={";

        int aqq = 0;

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[9];

        output[0] = "9999"; //default for no owner, uncolonized province
        output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
        output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[3] = "0"; //default for no pops, uncolonized or uninhabitible province
        output[4] = "{ 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 }"; //default for no buildinga
        output[5] = "9999"; //default for no monument
        output[6] = "settlement"; //default for no city status
        output[7] = "0.0"; //default for no civilization value
        output[8] = "-1"; //default for no holy site

        impProvList.add(output); //default at ID 0
        
        Provinces tmpProv = Provinces.newProv(output[0],output[1],output[2],0,0,0,0,0,0,output[6],0.0,aqq);
        baProvList.add(tmpProv);

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                
                String popLine = qaaa.split("=")[0];
                
                if (popLine.contains(tab+tab) && !qaaa.contains("none")) {
                    popLine = popLine.split(tab+tab)[1];
                    try {
                        int popID = Integer.parseInt(popLine);
                        String popType = scnr.nextLine();
                        String popCulture = scnr.nextLine();
                        String popReligion = scnr.nextLine();
                    
                        //System.out.println(popType);

                        popType = Processing.cutQuotes(popType.split("=")[1]);
                        popCulture = Processing.cutQuotes(popCulture.split("=")[1]);
                        popReligion = Processing.cutQuotes(popReligion.split("=")[1]);
                        //System.out.println(popID+" "+popType+" "+popCulture+" "+popReligion);
                        Pop combinedPop = Pop.newPop(popID,popType,popCulture,popReligion);
                        baPopList.add(combinedPop);
                    } catch(java.lang.NumberFormatException exception) {
                    
                        //System.out.println(qaaa);
                    }
                    
                    
                }

                if (qaaa.equals(keyWord)){//begin prov importation
                    endOrNot = false;
                    ArrayList<Pop> provincePopList = new ArrayList<Pop>();
                    double citizenRatio = 0;
                    double freemenRatio = 0;
                    double nobleRatio = 0;
                    double slaveRatio = 0;
                    double tribesmenRatio = 0;

                    while (flag == 0) {
                        qaaa = scnr.nextLine();
                        if (qaaa.split("=")[0].equals( tab+tab+"owner" ) ) {
                            output[0] = qaaa.split("=")[1];
                        }
                        if (qaaa.split("=")[0].equals( tab+tab+"culture" ) ) {
                            output[1] = qaaa.split("=")[1];
                            output[1] = output[1].substring(1,output[1].length()-1);
                        }
                        if (qaaa.split("=")[0].equals( tab+tab+"religion" ) ) {
                            output[2] = qaaa.split("=")[1];
                            output[2] = output[2].substring(1,output[2].length()-1);
                        }
                        
                        if (qaaa.split("=")[0].equals( tab+tab+"population_ratio" ) ) {
                            qaaa = scnr.nextLine();
                            qaaa = qaaa.split(tab+tab+tab)[1];
                            String[] popRatios = qaaa.split(" ");
                            citizenRatio = Double.parseDouble(popRatios[0]);
                            freemenRatio = Double.parseDouble(popRatios[1]);
                            nobleRatio = Double.parseDouble(popRatios[2]);
                            slaveRatio = Double.parseDouble(popRatios[3]);
                            tribesmenRatio = Double.parseDouble(popRatios[4]);
                        }

                        //popList
                        if (qaaa.split("=")[0].equals( tab+tab+"pop" ) ) {
                            //aqq = aqq + 1; //count pop
                            int popID = Integer.parseInt(qaaa.split("pop=")[1]);
                            Pop indvPop = Processing.getPopFromID(popID,baPopList);
                            provincePopList.add(indvPop);
                            output[3] = Integer.toString(aqq);
                        }
                        
                        if (qaaa.split("=")[0].equals( tab+tab+"civilization_value" ) ) {
                            output[7] = qaaa.split("=")[1];
                        }
                        
                        //cityStatus
                        if (qaaa.split("=")[0].equals( tab+tab+"province_rank" ) ) {
                            output[6] = qaaa.split("=")[1];
                            output[6] = output[6].substring(1,output[6].length()-1);
                            if (output[6].equals("city_metropolis")) {
                                output[6] = "metropolis"; //switched to just metropolis for clarity
                            }
                        }

                        //might be used or ignored
                        if (qaaa.split("=")[0].equals( tab+tab+"buildings" ) ) {
                            output[4] = qaaa.split("=")[1];

                        }

                        if (qaaa.split("=")[0].equals( tab+tab+"great_work" ) ) {
                            output[5] = qaaa.split("=")[1];

                        }

                        if (qaaa.split("=")[0].equals( tab+"}" ) ) { //ends here

                            String[] tmpOutput = new String[output.length];
                            int aq2 = 0;
                            while (aq2 < output.length) {
                                tmpOutput[aq2] = output[aq2];
                                aq2 = aq2 + 1;
                            }

                            //impProvList.add(tmpOutput);
                            
                            //baProvList
                            int monID = Integer.parseInt(tmpOutput[5]);
                            
                            double civValue = Double.parseDouble(tmpOutput[7]);
                            
                            Provinces baProv = Provinces.newProv(tmpOutput[0],tmpOutput[1],tmpOutput[2],monID,nobleRatio,citizenRatio,freemenRatio,
                            tribesmenRatio,slaveRatio,tmpOutput[6],civValue,aqq);
                            //System.out.println("NobleRatio:"+baProv.getNobleRatio()+"|SlaveRatio:"+baProv.getSlaveRatio()+"|Cult:"+baProv.getCulture());
                            
                            if (provincePopList.size() > 0) {
                                baProv.addPopArray(provincePopList);
                                baProv.setPopCS();
                            }
                            
                            
                            baProvList.add(baProv);

                            output[0] = "9999"; //default for no owner, uncolonized province
                            output[1] = "noCulture"; //default for no culture, uncolonized province with 0 pops
                            output[2] = "noReligion"; //default for no religion, uncolonized province with 0 pops
                            output[3] = "0"; //default for no pops, uncolonized or uninhabitible province
                            output[4] = "{ 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 }"; //default for no buildinga
                            output[5] = "9999"; //default for no monument
                            output[6] = "settlement"; //default for no city status
                            output[7] = "0.0"; //default for no civilization value
                            
                            provincePopList = new ArrayList<Pop>();
                            
                            monID = 0;

                            aqq = 0; //reset pop count

                        }

                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return baProvList;

    }

    public static ArrayList<Country> importCountry (String name) throws IOException
    {

        String tab = "	";

        String VQ2 = "{}q";

        String bracket1 = VQ2.substring(0,1);
        String bracket2 = VQ2.substring(1,2);

        //System.out.println ("Load 1 done");
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        ArrayList<Country> impTagInfo = new ArrayList<Country>();

        //double q = 0.0;

        //String idStr = provID.toString();

        String startWord = tab+"country_database={";
        String endWord = tab+"state_database={";

        String keyWord = tab+tab+"}";

        int aqq = 0;
        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[24];

        output[0] = "9999"; //default for no tag
        output[1] = "6969"; //default for no flag seed
        output[2] = "no"; //default for no gender equality laws
        output[3] = "0 0 0"; //default for no color
        output[4] = "0"; //default for no gold
        output[5] = "4529"; //default for no capital (Province of Olbia)
        output[6] = "noCulture"; //default for no culture, uncolonized province with 0 pops";
        output[7] = "noReligion"; //default for no religion, uncolonized province with 0 pops
        output[8] = "0"; //default for no technology
        output[9] = "0"; //default for no technology
        output[10] = "0"; //default for no technology
        output[11] = "0"; //default for no technology
        output[12] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[13] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[14] = "0"; //default for no researcher, will either be a random character in CKII or no character 
        output[15] = "0"; //default for no researcher, will either be a random character in CKII or no character
        output[16] = "0"; //default for no leader, will either be a random character in CKII or no character
        output[17] = "tribal_chiefdom"; //default for no government
        output[20] = "none"; //default for no governors/governorships, land will be directly held by the ruler
        output[21] = "9999"; //default for no historical tag used in nation formation
        output[22] = "k"; //depreciated
        output[23] = "noFlag"; //default for no flag

        Country defaultCountry = Country.newCountry(0,output[0],output[6],output[7],"Debug_Loc","Debug_Adj",output[21],output[5],output[23],output[3],
        output[16],output[17]);
        impTagInfo.add(defaultCountry); //default entry at ID 0

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (vmm.equals(startWord)){

                    while (flag == 0) {
                        qaaa = scnr.nextLine(); 

                        if (qaaa.equals(keyWord)){
                            flag = 1;

                            while (flag == 1) {
                                qaaa = scnr.nextLine();
                                if (qaaa.split("=")[0].equals( tab+tab+tab+"tag" ) ) {
                                    output[0] = qaaa.split("=")[1];
                                    output[0] = output[0].substring(1,output[0].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"historical" ) ) {
                                    output[21] = qaaa.split("=")[1];
                                    output[21] = output[21].substring(1,output[21].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"country_name" ) ) {
                                    qaaa = scnr.nextLine(); 
                                    output[19] = qaaa.split("=")[1];
                                    output[19] = output[19].substring(1,output[19].length()-1);
                                    if (output[19].equals("CIVILWAR_FACTION_NAME")) {
                                        qaaa = scnr.nextLine();
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"adjective")) {
                                            qaaa = scnr.nextLine();    
                                        }
                                        if (qaaa.split("=")[0].equals(tab+tab+tab+tab+"base")) {

                                            qaaa = scnr.nextLine();
                                            qaaa = qaaa.split("=")[1];
                                            qaaa = qaaa.substring(1,qaaa.length()-1);
                                            if (qaaa.equals("CIVILWAR_FACTION_NAME")) {
                                                int civilWarFlag = 0;
                                                while (civilWarFlag == 0) {
                                                    qaaa = scnr.nextLine();
                                                    qaaa = qaaa.replace(tab,"");
                                                    if (qaaa.split("=")[0].equals("name")) {
                                                        qaaa = qaaa.split("=")[1];
                                                        qaaa = qaaa.substring(1,qaaa.length()-1);
                                                        if (!qaaa.equals ("CIVILWAR_FACTION_NAME")) {
                                                            civilWarFlag = 1;
                                                        }
                                                    }
                                                }

                                            }
                                            output[19] = "CIVILWAR-"+qaaa;
                                        }
                                    }

                                    //System.out.println ("Load 3 done");
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"flag" ) ) {
                                    output[23] = qaaa.split("=")[1];
                                    output[23] = output[23].substring(1,output[23].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"seed" ) ) {
                                    output[1] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"gender_equality" ) ) {
                                    output[2] = qaaa.split("=")[1]; //will be used for determining inheiratance laws
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"color" ) ) {

                                    if (qaaa.length() == 14) { //Rakaly save format
                                        qaaa = scnr.nextLine();
                                        output[3] = qaaa.replace(tab,"");
                                    } else { //regular decompressed save format

                                        output[3] = qaaa.split(tab+tab+tab+"color2")[0];
                                        output[3] = qaaa.substring(15,output[3].length()-2);
                                    }
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"gold" ) ) {
                                    output[4] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"capital" ) ) {
                                    output[5] = qaaa.split("=")[1];
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"primary_culture" ) ) {
                                    output[6] = qaaa.split("=")[1];
                                    output[6] = output[6].substring(1,output[6].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"religion" ) ) {
                                    output[7] = qaaa.split("=")[1];
                                    output[7] = output[7].substring(1,output[7].length()-1);
                                }
                                //Technology
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"military_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[12] = qaaa.substring(33,qaaa.length()); //researcher
                                    }
                                    qaaa = scnr.nextLine();
                                    output[8] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"civic_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[13] = qaaa.substring(30,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[9] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"oratory_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[14] = qaaa.substring(32,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[10] = qaaa.split("=")[1]; //technology
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"religious_tech" ) ) {
                                    if (qaaa.substring(qaaa.length()-14, qaaa.length()-13).equals( "c" )) {

                                        output[15] = qaaa.substring(34,qaaa.length()); //researcher
                                    }

                                    qaaa = scnr.nextLine();
                                    output[11] = qaaa.split("=")[1]; //technology
                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"monarch" ) ) {
                                    output[16] = qaaa.split("=")[1];

                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"government_key" ) ) {
                                    output[17] = qaaa.split("=")[1];
                                    output[17] = output[17].substring(1,output[17].length()-1);
                                }
                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"succession_law" ) ) {
                                    output[18] = qaaa.split("=")[1];

                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+"governorship" ) ) {
                                    qaaa = scnr.nextLine();
                                    String tempReg = qaaa.split("=")[1].substring(1,qaaa.split("=")[1].length()-1);
                                    qaaa = scnr.nextLine();
                                    if (qaaa.split("=")[0].equals (tab+tab+tab+tab+"governor")) {
                                        if (output[20].equals ("none") ) {
                                            output[20] = tempReg + "~" + qaaa.split("=")[1];
                                        }
                                        else {
                                            output[20] =  output[20] + "," + tempReg + "~" + qaaa.split("=")[1];   
                                        }
                                    }

                                }

                                else if (qaaa.split("=")[0].equals( tab+tab+tab+tab+"budget_dates" ) ) {

                                    if (output[21].equals("9999")) { //failsafe if somehow there is no historical tag
                                        output[21] = output[0];
                                    }

                                    String[] tmpOutput = new String[output.length];

                                    int aq2 = 0;

                                    while (aq2 < output.length) {
                                        tmpOutput[aq2] = output[aq2];
                                        aq2 = aq2 + 1;
                                    }
                                    
                                    aqq = aqq + 1;
                                    
                                    Country countryToAdd = Country.newCountry(aqq,output[0],output[6],output[7],output[19],"Debug_Adj",output[21],
                                    output[5],output[23],output[3],output[16],output[17]);

                                    impTagInfo.add(countryToAdd);

                                    //aqq = aqq + 1;

                                    output[0] = "9999"; //default for no tag
                                    output[1] = "6969"; //default for no flag seed
                                    output[2] = "no"; //default for no gender equality laws
                                    output[3] = "0 0 0"; //default for no color
                                    output[4] = "0"; //default for no gold
                                    output[5] = "4529"; //default for no capital (Province of Olbia)
                                    output[6] = "noCulture"; //default for no culture, uncolonized province with 0 pops";
                                    output[7] = "noReligion"; //default for no religion, uncolonized province with 0 pops
                                    output[8] = "0"; //default for no technology
                                    output[9] = "0"; //default for no technology
                                    output[10] = "0"; //default for no technology
                                    output[11] = "0"; //default for no technology
                                    output[12] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[13] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[14] = "0"; //default for no researcher, will either be a random character in CKII or no character 
                                    output[15] = "0"; //default for no researcher, will either be a random character in CKII or no character
                                    output[16] = "0"; //default for no leader, will either be a random character in CKII or no character
                                    output[17] = "tribal_chiefdom"; //default for no government
                                    output[20] = "none"; //default for no governors/governorships, land will be directly held by the ruler
                                    output[21] = "9999"; //default for no historical tag used in nation formation
                                    output[22] = "k"; //default for no rank, ranks are not stored in the save file, will be calculated during output
                                    output[23] = "noFlag"; //default for no flag

                                }
                            }
                        }
                    }   

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return impTagInfo;
    }

    public static ArrayList<Diplo> importSubjects (String name) throws IOException
    {

        //Primarily used for subjects/vassals in IR

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String tab = "	";

        int aqq = 0;

        ArrayList<Diplo> currentList = new ArrayList<Diplo>();

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;
        output = new String[5];

        output[0] = "9999"; //default for no overlord, overlord has no subjects
        output[1] = "9999"; //default for no subject
        output[2] = "9999"; //default for no subject relation
        output[3] = "9999"; //default for initial type

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                
                String identifier = qaaa.split("=")[0];
                
                if (identifier.equals(tab+"dependency") || identifier.equals(tab+"alliance")) {
                    output[3] = qaaa.split("=")[0];
                    output[3] = output[3].replace(tab,"");
                    //System.out.println(output[3]);
                }

                //overlord
                if (identifier.equals( tab+tab+"first" ) ) {
                    output[0] = qaaa.split("=")[1];
                    //System.out.println(output[0]);
                }

                //subject
                if (identifier.equals( tab+tab+"second" ) ) {
                    output[1] = qaaa.split("=")[1];
                    //System.out.println(output[1]);
                }
                //subject type
                if (identifier.equals( tab+tab+"subject_type" ) ) {
                    output[2] = qaaa.split("=")[1];
                    output[2] = Processing.cutQuotes(output[2]);
                    //System.out.println(output[2]);
                }
                
                if (qaaa.equals(tab+"}")) {
                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }
                    
                    int overlord = Integer.parseInt(tmpOutput[0]);
                    int subject = Integer.parseInt(tmpOutput[1]);
                    String type = tmpOutput[3];
                    String subType = tmpOutput[2];
                    
                    if (overlord != 9999 && subject != 9999 && !type.equals("9999")) {
                        Diplo dip = Diplo.newDiplo(overlord,subject,type,subType);
                        currentList.add(dip);
                    }

                    output[0] = "9999";
                    output[1] = "9999";
                    output[2] = "9999";
                    output[3] = "9999"; //default for initial type
                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }

        if (output[1].equals("9999") || output[2].equals("9999")) { //Fallback in case something goes wrong, subject relation won't be converted
            output[0] = "9999"; //default for no overlord, overlord has no subjects
            output[1] = "9999"; //default for no subject
            output[2] = "9999"; //default for no subject relation    
        }

        return currentList;

    }
    
    public static ArrayList<Deity> importDeities (String name) throws IOException
    {

        //Primarily used for subjects/vassals in IR

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String tab = "	";

        int aqq = 0;

        ArrayList<Deity> currentList = new ArrayList<Deity>();

        boolean endOrNot = true;
        String vmm = scnr.nextLine();
        String line = vmm;
        String[] output;
        output = new String[3];

        output[0] = "none"; //default for no id
        output[1] = "none"; //default for rulerId
        output[2] = "none"; //default for no subject relation

        try {
            line = scnr.nextLine();
            while (endOrNot = true){

                line = scnr.nextLine();
                
                int deityCount = 0;
                while (line.contains("=")) { //deity detected
                    line = line.replace(tab,"");
                    String[] splitLine = line.split("=");
                    String identifier = splitLine[0];
                    String data = splitLine[1];
                    if (deityCount == 0) {
                        output[0] = identifier;
                    } else if (identifier.equals("deify_ruler")) {
                        output[1] = data;
                    } else if (identifier.equals("deity")) {
                        output[2] = data;
                    }
                    deityCount = deityCount + 1;
                    
                    line = scnr.nextLine();
                }
                line = line.replace(tab,"");
                //System.out.println(line);
                
                if (line.equals("}") && !output[0].equals("none")) {
                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }
                    int id = Integer.parseInt(tmpOutput[0]);
                    int rulerID = -1;
                    if (tmpOutput[1].length() < 10) {
                        rulerID = Integer.parseInt(tmpOutput[1]);
                    }
                    String deityName = tmpOutput[2];
                    deityName = Processing.cutQuotes(deityName);
                    Deity addedDeity = Deity.newDeity(id,rulerID,deityName);
                    
                    currentList.add(addedDeity);

                    output[0] = "none";
                    output[1] = "none";
                    output[2] = "none";
                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }

        if (output[1].equals("9999") || output[2].equals("9999")) { //Fallback in case something goes wrong, subject relation won't be converted
            output[0] = "9999"; //default for no overlord, overlord has no subjects
            output[1] = "9999"; //default for no subject
            output[2] = "9999"; //default for no subject relation    
        }

        return currentList;

    }

    public static String[] importDir (String name) throws IOException //Imports directories from configuration.txt
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[11];

        output[0] = "bad"; //default for no Bronze Age directory
        output[1] = "bad"; //default for no IR game directory
        output[2] = "bad"; //default for no IR mod directory
        output[3] = "bad"; //default for no CK II mod directory
        output[4] = "bad"; //default for no save game directory
        output[5] = "bad"; //default for no selected IR mods
        output[6] = "bad"; //default for no custom CK II mod name
        output[7] = "bad"; //default for no year choice
        output[8] = "bad"; //default for no custom year date
        output[9] = "bad"; //default for no dejure conversion
        output[10] = "bad"; //default for no dejure conversion

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(" = ")[0].equals("CK2Directory")){
                    output[0] = qaaa.split(" = ")[1];
                    output[0] = output[0].substring(1,output[0].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("ImperatorDirectory")){
                    output[1] = qaaa.split(" = ")[1];
                    output[1] = output[1].substring(1,output[1].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("ImperatorModPath")){
                    output[2] = qaaa.split(" = ")[1];
                    output[2] = output[2].substring(1,output[2].length()-1);
                }
                else if (qaaa.split(" = ")[0].equals("targetGameModPath")){
                    output[3] = qaaa.split(" = ")[1];
                    output[3] = output[3].substring(1,output[3].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("SaveGame")){
                    output[4] = qaaa.split(" = ")[1];
                    output[4] = output[4].substring(1,output[4].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("selectedMods")){
                    output[5] = qaaa.split(" = ")[1];

                }
                else if (qaaa.split(" = ")[0].equals("output_name")){
                    output[6] = qaaa.split(" = ")[1];
                    output[6] = output[6].substring(1,output[6].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("year")){
                    output[7] = qaaa.split(" = ")[1];
                    output[7] = output[7].substring(1,output[7].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("customYearDate")){
                    output[8] = qaaa.split(" = ")[1];
                    output[8] = output[8].substring(1,output[8].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("dejure")){
                    output[9] = qaaa.split(" = ")[1];
                    output[9] = output[9].substring(1,output[9].length()-1);

                }
                else if (qaaa.split(" = ")[0].equals("republic")){
                    output[10] = qaaa.split(" = ")[1];
                    output[10] = output[10].substring(1,output[10].length()-1);

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importConvList (String name, int provIDnum) throws IOException //Checks old format first, then new format
    {

        String provID = Integer.toString(provIDnum);
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provID)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importConvListR (String name, int provIDnum) throws IOException //Reverse of importConvList
    {

        String provID = Integer.toString(provIDnum);
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                try {

                    if (qaaa.split(",")[1].equals(provID)){
                        endOrNot = false;
                        output[0] = qaaa.split(",")[0];
                        output[1] = qaaa.split(",")[1];

                    }

                } catch (java.lang.ArrayIndexOutOfBoundsException exception) {

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCultList (String name, String provIDnum) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provIDnum)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importLocalisation (ArrayList<String> locList, String tag, String dynasty) throws IOException
    {
        char quote = '"';
        String[] output;
        output = new String[2];
        String revoltName = "no";
        int usesDynasty = 0;

        try { 
            if (tag.split("-")[0].equals("CIVILWAR")) { //For the opposing side of a country undergoing civil war
                revoltName = tag.split("-")[1];
            }
        }catch (java.lang.NullPointerException exception){
            revoltName = "no";
        }

        if (revoltName.equals("no")) {

            output[0] = tag; //default for no name, will just use tag ID
            output[1] = tag; //default for no adjective, will just use tag ID
            int aqq = 0;
            boolean name = false;

            try {
                while (aqq < locList.size()){

                    String qaaa = locList.get(aqq);
                    try {
                        if (qaaa.charAt(0) != ' ') {//If loc lacks leading space, add a space
                            qaaa = " " + qaaa;
                        }
                    } catch (java.lang.StringIndexOutOfBoundsException exception) {
                        
                    }
                    
                    try {
                        qaaa = qaaa.split(" #")[0];
                    }
                    catch (Exception e) {
                        
                    }
                    if (qaaa.split(":")[0].equals(" "+tag)){
                        output[0] = qaaa.split(":")[1];
                        output[0] = output[0].substring(3,output[0].length()-1);
                        name = true;

                    }

                    String adjName = tag.replace("_FEUDATORY_NAME","_FEUDATORY_ADJECTIVE"); //for certain mission tags which have different formatting
                    adjName = adjName.replace("_NAME","");

                    if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE")
                    || qaaa.split(":")[0].equals(" "+adjName+"_ADJ") || qaaa.split(":")[0].equals(" "+adjName+"_ADJECTIVE")){
                        if (name) {
                          aqq = locList.size() + 1;  
                        }
                        output[1] = qaaa.split(":")[1];
                        output[1] = output[1].substring(3,output[1].length()-1);

                    } else {
                        //System.out.println(output[1]);
                        if (output[1].length() < 1) {
                            output[1] = "1234";
                        }
                        if (output[1].charAt(output[1].length()-1) == 'a') {
                            output[1] = output[0] + "n";    
                        } else {
                            output[1] = output[0];
                        }
                    }

                    aqq = aqq + 1;
                }

            }catch (java.util.NoSuchElementException exception){
                aqq = locList.size() + 1;

            }   

        }
        else {

            String[] revoltNames = importLocalisation (locList, revoltName, dynasty);
            output[0] = revoltNames[1] + " Revolt";
            output[1] = revoltNames[1] + " Revolter";
        }
        
        output[0] = output[0].split("#")[0];//Remove any comments in loc
        output[1] = output[1].split("#")[0];
        

        if (output[0].charAt(0) == '[') { //For countries which use a dynasty name for their country, like the Seleukid Empire
            output[1] = dynasty;
            if (output[1].charAt(output[1].length()-1) == 'a') {
                output[1] = output[1] + "n";    
            }
            output[0] = output[1] + " Empire"; //may change out Empire for country rank
        }

        return output;

    }

    public static String[] importProvLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "provincenames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importAreaLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "regionnames_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importCustCountryLocalisation (String tag) throws IOException //Supported Loc for popular IR mods
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = "supportedModLoc.txt";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importRegionLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "macroregions_l_english.yml";
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = tag; //default for no prov name, will just use prov ID
        output[1] = tag; //default for no prov adjective, will just use prov ID
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    endOrNot = false;
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);
                    output[1] = output[0];
                    if (output[1].charAt(output[1].length()-1) == 'a') {
                        output[1] = output[1] + "n";    
                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String[] importFormableLocalisation (String directory, String tag) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];
        String provOrName = "debug";

        String name = directory + VM + "game" + VM + "localization" + VM + "english" + VM + "nation_formation_l_english.yml";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String tag2 = tag;

        try {
            tag2 = tag.split("_NAM")[0];  
        }

        catch (java.lang.NullPointerException exception){

        }

        output[0] = tag; //default for no name, will just use tag ID
        output[1] = tag; //default for no adjective, will just use tag ID

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(":")[0].equals(" "+tag)){
                    
                    output[0] = qaaa.split(":")[1];
                    output[0] = output[0].substring(3,output[0].length()-1);

                }

                if (qaaa.split(":")[0].equals(" "+tag+"_ADJ") || qaaa.split(":")[0].equals(" "+tag+"_ADJECTIVE") || 
                qaaa.split(":")[0].equals(" "+tag2+"_ADJ") || qaaa.split(":")[0].equals(" "+tag2+"_ADJECTIVE")){
                    endOrNot = false;
                    output[1] = qaaa.split(":")[1];
                    output[1] = output[1].substring(3,output[1].length()-1);
                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String> importModLocalisation (String directory) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        ArrayList<String> oldFile = new ArrayList<String>();
        char quote = '"';

        String name = directory + "/localization/english/converted_countries_l_english.yml";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                oldFile.add(qaaa);

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return oldFile;

    }

    public static ArrayList<String> importBasicFile (String directory) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        ArrayList<String> oldFile = new ArrayList<String>();
        char quote = '"';

        FileInputStream fileIn= new FileInputStream(directory);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                oldFile.add(qaaa);

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return oldFile;

    }

    public static String[] importDejureList (String name, String provIDnum) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;

        String qaaa;
        String[] output;   // culture e_title k_title
        output = new String[3];

        output[0] = "nomap"; //default for no mapping
        output[1] = "e_nomap"; //default for no mapping
        output[2] = "k_nomap"; //default for no mapping

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split(",")[0].equals(provIDnum)){
                    endOrNot = false;
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];
                    output[2] = qaaa.split(",")[2];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String> importDuchyNameList (String ck2Dir) throws IOException //imports dejure duchies, along with the counties they go to
    {

        String VM = "\\";
        VM = VM.substring(0);
        String tab = "	";

        FileInputStream fileIn= new FileInputStream(ck2Dir+VM+"common"+VM+"landed_titles"+VM+"landed_titles.txt");
        Scanner scnr= new Scanner(fileIn);

        String qaaa = scnr.nextLine();
        String tab3 = tab+tab+tab;

        String duchyWord = tab+tab+"d"; //Word used to identify duchies
        String duchyWord2 = "        d"; //Bohemia and Moravia use different formatting
        String countyWord = tab3+"c";
        String countyWord2 = "            "+tab+"c";//Bohemia and Moravia use different formatting
        String countyWord3 = "            c";
        String countyWord4 = tab3+tab+"c";

        ArrayList<String> duchies = new ArrayList<String>();

        int aqq = 1;
        int endOrNot = 0;

        String duchyList = " ";

        try {
            while (endOrNot == 0){

                if (qaaa.split("_")[0].equals(duchyWord) || qaaa.split("_")[0].equals(duchyWord2)) {

                    duchies.add(duchyList);

                    duchyList = qaaa.split(" = ")[0].replace(tab,"");
                    aqq = aqq + 1;
                }

                if (qaaa.split("_")[0].equals(countyWord) || qaaa.split("_")[0].equals(countyWord2) || qaaa.split("_")[0].equals(countyWord3) ||
                qaaa.split("_")[0].equals(countyWord4)) {
                    String county = qaaa.split(" = ")[0].replace(tab,"");
                    duchyList = duchyList + "," + county;
                    qaaa = scnr.nextLine();
                }

                qaaa = scnr.nextLine();

                aqq = aqq + 1;

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = 1;

        }   
        return duchies;

    }

    public static int compressTest (String saveDir) throws IOException //Checks if the file is compressed or not
    {

        String VM = "\\";
        VM = VM.substring(0);
        String tab = "	";

        FileInputStream fileIn= new FileInputStream(saveDir);
        Scanner scnr= new Scanner(fileIn);

        String qaaa = scnr.nextLine();

        int compressedOrNot = 0; //0 for compressed, 1 for decompressed

        int aqq = 1;

        try {
            while (aqq != 10){ //loop for the first 10 lines (potential futureproofing), if the key is there, decompressed. Else, compressed

                if (qaaa.split("=")[0].equals("save_game_version")) {

                    compressedOrNot = 1;
                }

                qaaa = scnr.nextLine();
                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            aqq = 10;

        }   
        return compressedOrNot;

    }

    public static String[] importSaveInfo (String directory) throws IOException //imports basic save info, like version
    {

        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        FileInputStream fileIn= new FileInputStream(directory);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        boolean endOrNot = true;
        String qaaa;
        qaaa = scnr.nextLine();
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "bad"; //default for no version
        output[1] = "bad"; //default for no date
        String idNum;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split("=")[0].equals("version")){
                    output[0] = qaaa.split("=")[1];
                }

                if (qaaa.split("=")[0].equals("date")){
                    output[1] = qaaa.split("=")[1];
                    endOrNot = false;
                }

                if (qaaa.split("=")[0].equals("variables")){ //end of save has been found without finding save date
                    endOrNot = false;
                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static ArrayList<String[]> importFlag (String name) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impFlagList= new ArrayList<String[]>();

        String tab = "	";
        char quote = '"';
        String strQuote = quote+"_";
        strQuote = strQuote.split("_")[0];
        int flag = 0;

        String line;
        String[] output;
        output = new String[2];

        output[0] = "unnamedFlag"; //default for no tag/name
        output[1] = "flagCore"; //default for no pattern
        //output[2] = "none"; //default for color1, format = hsvOrRgb,r g b
        //output[3] = "none"; //default for color2, format = hsvOrRgb,r g b
        //output[4] = "0"; //default for no emblems, format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        //~_~ divides aspects of an emblem, ~~ divides emblems

        impFlagList.add(output); //default at ID 0

        try {

            while (flag == 0) {
                line = scnr.nextLine();
                //System.out.println("Line = |"+line+"|");
                if (output[0].equals("unnamedFlag") && line.contains("=")) {
                    line = line.replace(" ","");
                    line = line.split("=")[0];
                    output[0] = line;
                    //System.out.println("flagName = |"+output[0]+"|");
                } else if (output[1].equals("flagCore") && !output[0].equals("unnamedFlag")) {
                    output[1] = line;
                    //System.out.println("flagCore Starting with |"+output[1]+"|");
                } else if (!output[0].equals("unnamedFlag")) {
                    output[1] = output[1] + "," + line;
                    //System.out.println("flagCore Continuing with |"+output[1]+"|");
                }
                

                if (line.equals( "}" ) ) { //ends here

                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }

                    impFlagList.add(tmpOutput);

                    output[0] = "unnamedFlag"; //default for no tag/name
                    output[1] = "flagCore"; //default for no pattern
                    //output[2] = "none"; //default for color1, format = hsvOrRgb~r,g,b
                    //output[3] = "none"; //default for color2, format = hsvOrRgb~r,g,b
                    //output[4] = "0"; //default for no emblems, format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)

                }

            }

        }catch (java.util.NoSuchElementException exception){

        }   

        return impFlagList;

    }

    public static ArrayList<String[]> importAllFlags (ArrayList<String> modDirs) throws IOException
    {
        ArrayList<String[]> allFlags = new ArrayList<String[]>();
        //ArrayList<String[]> vanillaFlags = importFlag(name+"/game/common/coat_of_arms/coat_of_arms/00_pre_scripted_countries.txt");
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/common/coat_of_arms/coat_of_arms";
                File flagInfo = new File (modDir);
                String[] flagList = flagInfo.list();

                if (flagList != null) {
                    int aq2 = 0;
                    while (aq2 < flagList.length) {
                        ArrayList<String[]> modFlags = importFlag(modDir+"/"+flagList[aq2]);
                        allFlags.addAll(modFlags);
                        aq2 = aq2 + 1;
                    }

                }
            }
            aqq = aqq + 1;
        }

        //allFlags.addAll(vanillaFlags);

        return allFlags;
    }

    public static ArrayList<String[]> importColors (String name) throws IOException //named_colors for flags
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String[]> impColorList= new ArrayList<String[]>();
        
        //System.out.println(name);

        String tab = "	";

        int flag = 0;

        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "none"; //default for no name
        output[1] = "none"; //default for no color

        impColorList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace(" "+tab,"");
                qaaa = qaaa.replace(tab,"");
                qaaa = qaaa.replace("    ","");
                qaaa = qaaa.replace(" = ","=");
                qaaa = qaaa.replace("= ","=");
                qaaa = qaaa.replace(" =","=");
                if (!qaaa.equals( "colors={" ) && qaaa.contains("=") ) {
                    output[0] = qaaa.split("=")[0];
                    output[1] = qaaa.split("=")[1];
                    if (qaaa.contains("rgb ") || qaaa.contains("rgb ")) {
                        output[1] = output[1].split("rgb ")[1];
                        output[1] = output[1].split(" }")[0];
                        output[1] = "rgb," + output[1].substring(2,output[1].length());
                    }
                    else if (qaaa.contains("hsv ")) {
                        output[1] = output[1].split("hsv ")[1];
                        output[1] = output[1].split(" }")[0];
                        output[1] = "hsv," + output[1].substring(2,output[1].length());
                    }
                    else if (qaaa.contains("={")) { //if colors do not specify rgb/hsv, default to rgb
                        output[1] = output[1].split(" }")[0];
                        output[1] = "rgb," + output[1].substring(2,output[1].length());
                    }
                    String[] tmpOutput = new String[output.length];
                    int aq2 = 0;
                    while (aq2 < output.length) {
                        tmpOutput[aq2] = output[aq2];
                        aq2 = aq2 + 1;
                    }

                    impColorList.add(tmpOutput);

                    output[0] = "none"; //default for no name
                    output[1] = "none"; //default for no color
                }

            }
        }catch (java.util.NoSuchElementException exception){

        }   

        return impColorList;

    }

    public static ArrayList<String[]> importAllColors (String name, ArrayList<String> modDirs) throws IOException
    {
        ArrayList<String[]> allColors = new ArrayList<String[]>();
        ArrayList<String[]> vanillaColors = importColors(name+"/game/common/named_colors/default_colors.txt");
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/common/named_colors";
                File colorInfo = new File (modDir);
                String[] colorList = colorInfo.list();

                if (colorList != null) {
                    int aq2 = 0;
                    while (aq2 < colorList.length) {
                        ArrayList<String[]> modColors = importColors(modDir+"/"+colorList[aq2]);
                        allColors.addAll(modColors);
                        aq2 = aq2 + 1;
                    }

                }
            }
            aqq = aqq + 1;
        }

        allColors.addAll(vanillaColors);

        return allColors;
    }
    
    public static ArrayList<String> importRegions (String name) throws IOException //list of all regions
    {
        //System.out.println(name);

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String> impRegionList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;

        String qaaa = "";
        String output = "none";
        String endBracket = " }".replace(" ","");

        impRegionList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace ("_region {","_region = {"); //special edge case for vanilla madhyadesa_region
                if (qaaa.contains( "_region = {" ) || qaaa.contains("_area = {") ) { //_area is special edge case for vanilla bohemia_area region
                    output = qaaa.split(" =")[0];
                    while (!qaaa.equals(endBracket)){
                        qaaa = scnr.nextLine();
                        qaaa = qaaa.replace("    ",tab); //special Kydonia syntax
                        qaaa = qaaa.replace(tab+" "+tab,tab+tab); //special Gournia syntax
                        if (qaaa.contains(tab+tab) && !qaaa.equals(tab+tab)) {
                            String area = qaaa.split(tab+tab)[1];
                            area = area.split(tab)[0]; //formatting cleanup
                            area = area.split(" ")[0];
                            area = area.split("#")[0];
                            output = output + "," + area;
                        }
                    }

                    String tmpOutput = output;
                    
                    //System.out.println(tmpOutput);

                    impRegionList.add(tmpOutput);

                    output = "none"; //default for no name
                }

            }
        }catch (java.util.NoSuchElementException exception){

        }   

        return impRegionList;

    }
    
    public static String getRegionDir (String name, ArrayList<String> regionList, ArrayList<String> modDirs) throws IOException
    {
        String regionDir = name+"//game//";
        int aqq = 0;
        while (regionList.size() > aqq) {
            //if (!regionList.get(aqq).equals("none")) {
            if (!regionList.get(aqq).equals("none")) {
                //regionDir = modDirs.get(aqq)+"//";
                //regionDir = modDirs.get(aqq)+"//";
                regionDir = regionList.get(aqq).split("map_data")[0];
            }
            aqq = aqq + 1;
        }

        return regionDir;
    }

    public static ArrayList<String> importAllLoc (String name, ArrayList<String> modDirs) throws IOException //imports all localization files
    {
        ArrayList<String> allLoc = new ArrayList<String>();
        ArrayList<String> moddedLoc = new ArrayList<String>();
        ArrayList<String> regionLoc = importBasicFile(name+"/game/localization/english/macroregions_l_english.yml");
        ArrayList<String> formableLoc = importBasicFile(name+"/game/localization/english/nation_formation_l_english.yml");
        ArrayList<String> countryLoc = importBasicFile(name+"/game/localization/english/countries_l_english.yml");
        ArrayList<String> provLoc = importBasicFile(name+"/game/localization/english/provincenames_l_english.yml");
        ArrayList<String> areaLoc = importBasicFile(name+"/game/localization/english/regionnames_l_english.yml");
        //ArrayList<String> supportedModLoc = importBasicFile("supportedModLoc.txt"); //default loc for some mods in case mod files are missing
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/localization/english";
                try {
                    importModLoc(modDir,modDirs,moddedLoc);
                    //System.out.println("Dir: "+modDirs.get(aqq));
                    //System.out.println("Loc total: "+test.size());
                    //moddedLoc.addAll(importModLoc(modDir,modDirs,moddedLoc));
                } catch (java.lang.OutOfMemoryError exception) { //User has many mods with too many lines of localization to handle in memory
                    aqq = 1 + modDirs.size(); 
                }
            }
            aqq = aqq + 1;
        }

        allLoc.addAll(regionLoc);
        allLoc.addAll(formableLoc);
        allLoc.addAll(countryLoc);
        allLoc.addAll(provLoc);
        allLoc.addAll(areaLoc);
        //allLoc.addAll(supportedModLoc);
        allLoc.addAll(moddedLoc);

        return allLoc;
    }
    
    public static ArrayList<String> importVanillaLoc (String name) throws IOException //imports all localization files
    {
        ArrayList<String> allLoc = new ArrayList<String>();
        ArrayList<String> regionLoc = importBasicFile(name+"/game/localization/english/macroregions_l_english.yml");
        ArrayList<String> formableLoc = importBasicFile(name+"/game/localization/english/nation_formation_l_english.yml");
        ArrayList<String> countryLoc = importBasicFile(name+"/game/localization/english/countries_l_english.yml");
        ArrayList<String> provLoc = importBasicFile(name+"/game/localization/english/provincenames_l_english.yml");
        ArrayList<String> areaLoc = importBasicFile(name+"/game/localization/english/regionnames_l_english.yml");

        allLoc.addAll(regionLoc);
        allLoc.addAll(formableLoc);
        allLoc.addAll(countryLoc);
        allLoc.addAll(provLoc);
        allLoc.addAll(areaLoc);

        return allLoc;
    }
    
    public static ArrayList<String> importAllModLoc (String name, ArrayList<String> modDirs) throws IOException //imports all localization files
    {
        ArrayList<String> moddedLoc = new ArrayList<String>();
        int aqq = 0;
        while (modDirs.size() > aqq) {
            if (!modDirs.get(aqq).equals("none")) {
                String modDir = modDirs.get(aqq)+"/localization/english";
                try {
                    importModLoc(modDir,modDirs,moddedLoc);
                    //System.out.println("Dir: "+modDirs.get(aqq));
                    //System.out.println("Loc total: "+test.size());
                    //moddedLoc.addAll(importModLoc(modDir,modDirs,moddedLoc));
                } catch (java.lang.OutOfMemoryError exception) { //User has many mods with too many lines of localization to handle in memory
                    aqq = 1 + modDirs.size(); 
                }
            }
            aqq = aqq + 1;
        }

        return moddedLoc;
    }

    public static ArrayList<String> importModLoc (String modDir, ArrayList<String> modDirs, ArrayList<String> allModLoc) throws IOException
    //imports all modded localization files
    {
        int aqq = 0;
        File locInfo = new File (modDir);
        String[] locList = locInfo.list();

        try {
            if (locList != null) {
                while (aqq < locList.length) {
                    importModLoc(modDir+"/"+locList[aqq],modDirs,allModLoc);
                    aqq = aqq + 1;
                }

            }
            else {
                ArrayList<String> modLoc = importBasicFile(modDir);
                allModLoc.addAll(modLoc);
            }
        } catch (Exception e){ //if a non-existant file is accessed, cancel so that converter doesn't crash
            
        }

        return allModLoc;
    }

    public static ArrayList<String> importModDirs (String name, String irModDir) throws IOException //imports all directories for mods used in save file
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        ArrayList<String> impModList= new ArrayList<String>();

        irModDir = irModDir.substring(0,irModDir.length()-4);

        String tab = "	";

        int flag = 0;

        String vmm = scnr.nextLine();
        String qaaa = vmm;
        String output;   // Owner Culture Religeon PopTotal Buildings

        output = "none"; //default for no name

        impModList.add(output); //default at ID 0

        try {
            while (flag == 0){
                qaaa = scnr.nextLine();
                if (qaaa.contains( "enabled_mods" ) ) {
                    flag = 1;
                    qaaa = scnr.nextLine();
                    String tmpOutput;
                    while (!qaaa.contains("}") && !qaaa.contains("speed=")) {
                        qaaa = qaaa.replace(tab,"");
                        String[] mods = qaaa.split(" ");
                        int aqq = 0;
                        while (aqq < mods.length) {
                            mods[aqq] = mods[aqq].substring(1,mods[aqq].length()-1);
                            try { //get real mod dir
                                output = importModDirInfo(irModDir+"/"+mods[aqq]);
                                impModList.add(output);
                            } catch (Exception e){ //if something goes wrong in finding the mod dir, ignore mod to prevent entire converter from crashing

                            }
                            aqq = aqq + 1;
                        }
                        qaaa = scnr.nextLine();
                    }
                }

            }
        }catch (java.util.NoSuchElementException exception){
            return impModList;
        }   

        return impModList;

    }

    public static String importModDirInfo (String name) throws IOException //imports directory info for a specific mod from the .mod file
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;

        String output = "none"; //default for no name

        try {
            //try
            while (flag == 0){
                String qaaa = scnr.nextLine();
                if (qaaa.contains( "path=" ) ) {
                    flag = 1;
                    output = qaaa.split("=")[1];
                    output = output.substring(1,output.length()-1);
                }

            }
        }catch (Exception e){ //if anything goes wrong, return to avoid stopping converter
            return output;
        }   

        return output;

    }

    public static ArrayList<String> importModFlagDirs (ArrayList<String> modDirs) throws IOException //imports modded flag gfx locations/names
    {

        String coloredEmblems = "/gfx/coat_of_arms/colored_emblems";
        String patterns = "/gfx/coat_of_arms/patterns";
        String texturedEmblems = "/gfx/coat_of_arms/textured_emblems";

        ArrayList<String> gfxList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;
        int aqq = 0;
        String qaaa = "";
        String output;

        output = "none"; //default for no name

        gfxList.add(output); //default at ID 0

        try {
            while (aqq < modDirs.size()){
                if (!modDirs.get(aqq).equals ("none")) {
                    File embDir = new File(modDirs.get(aqq)+coloredEmblems);
                    String[] emblemFiles = embDir.list();
                    if (emblemFiles != null) {
                        int aq2 = 0;
                        while (aq2 < emblemFiles.length) {
                            output = emblemFiles[aq2];
                            output = modDirs.get(aqq)+coloredEmblems+"/"+output;
                            gfxList.add(output);
                            aq2 = aq2 + 1;

                        }
                    }
                    File patDir = new File(modDirs.get(aqq)+patterns);
                    String[] patternFiles = patDir.list();
                    if (patternFiles != null) {
                        int aq3 = 0;
                        while (aq3 < patternFiles.length) {
                            output = patternFiles[aq3];
                            output = modDirs.get(aqq)+patterns+"/"+output;
                            gfxList.add(output);
                            aq3 = aq3 + 1;

                        }
                    }
                    File texDir = new File(modDirs.get(aqq)+texturedEmblems);
                    String[] textureFiles = texDir.list();
                    if (textureFiles != null) {
                        int aq4 = 0;
                        while (aq4 < textureFiles.length) {
                            output = textureFiles[aq4];
                            output = modDirs.get(aqq)+texturedEmblems+"/"+output;
                            gfxList.add(output);
                            aq4 = aq4 + 1;

                        }
                    }
                }

                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            flag = 1;
        }   

        return gfxList;

    }
    
    public static ArrayList<String> importModRegionDirs (ArrayList<String> modDirs) throws IOException //imports modded region ID's
    {

        String regionFile = "/map_data";

        ArrayList<String> gfxList= new ArrayList<String>();

        String tab = "	";

        int flag = 0;
        int aqq = 0;
        String qaaa = "";
        String output;

        output = "none"; //default for no name

        gfxList.add(output); //default at ID 0

        try {
            while (aqq < modDirs.size()){
                if (!modDirs.get(aqq).equals ("none")) {
                    File regionDir = new File(modDirs.get(aqq)+regionFile);
                    String[] emblemFiles = regionDir.list();
                    if (emblemFiles != null) {
                        int aq2 = 0;
                        while (aq2 < emblemFiles.length) {
                            output = emblemFiles[aq2];
                            if (output.equals("regions.txt")) {
                                output = modDirs.get(aqq)+regionFile+"/"+output;
                                gfxList.add(output);
                            }
                            aq2 = aq2 + 1;

                        }
                    }
                }

                aqq = aqq + 1;
            }
        }catch (java.util.NoSuchElementException exception){
            flag = 1;
        }   

        return gfxList;

    }
    
    public static ArrayList<String[]> importProvSetup (String modDir, ArrayList<String[]> allProvInfo) throws IOException
    //imports all basegame prov files
    {
        int aqq = 0;
        File locInfo = new File (modDir);
        String[] locList = locInfo.list();

        try {
            if (locList != null) { //get all files in directory
                while (aqq < locList.length) {
                    //System.out.println("Importing "+modDir+"/"+locList[aqq]);
                    importProvSetup(modDir+"/"+locList[aqq],allProvInfo);
                    aqq = aqq + 1;
                }

            }
            else { //get everything in file
                ArrayList<String> provSetupFile = importBasicFile(modDir);
                int count = 0;
                while (count < provSetupFile.size()) {
                    String setupLine = provSetupFile.get(count);
                    setupLine = setupLine.split("#")[0]; //ignore comments
                    if (setupLine.contains("=")) { //prov detected, loop through until end bracket
                        setupLine = setupLine.replace(" ","");
                        String provID = setupLine.split("=")[0];
                        count = count + 1;
                        String provTerrain = "Debug";
                        String provTradeGood = "Debug";
                        while (!setupLine.equals("}")) {
                           setupLine = provSetupFile.get(count);
                           if (setupLine.contains("terrain")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provTerrain = setupLine.split("=")[1];
                               provTerrain = Processing.cutQuotes(provTerrain);
                           }
                           else if (setupLine.contains("trade_goods")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provTradeGood = setupLine.split("=")[1];
                               provTradeGood = Processing.cutQuotes(provTradeGood);
                           }
                           count = count + 1;
                        }
                        
                        String[] provInfo = new String[3];
                        provInfo[0] = provID;
                        provInfo[1] = provTerrain;
                        provInfo[2] = provTradeGood;
                        
                        allProvInfo.add(provInfo);
                        
                    } else {
                        count = count + 1;
                    }
                }
                
                //allProvInfo.addAll(provInfo);
            }
        } catch (Exception e){ //if a non-existant file is accessed, cancel so that converter doesn't crash
            
        }

        return allProvInfo;
    }
    
    public static String[] importMappingFromArray (ArrayList<String> source, String provIDnum) throws IOException
    //Simpler mapper that doesn't check for arguments and is faster, returning as soon as it finds a valid match
    {
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops
        
        int count = 0;

        try {
            while (count < source.size()){

                qaaa = source.get(count);

                if (qaaa.split(",")[0].equals(provIDnum)){
                    count = 1 + source.size();
                    output[0] = qaaa.split(",")[0];
                    output[1] = qaaa.split(",")[1];

                }
                count = count + 1;
            }

        }catch (java.util.NoSuchElementException exception){
            count = 1 + source.size();

        }   

        return output;

    }
    
    public static ArrayList<String[]> importMappingFromArrayArgs (ArrayList<String> source, String provIDnum) throws IOException
    //Should only be used on mapping files that support arguments
    {
        String line;
        ArrayList<String[]> allMatches = new ArrayList<String[]>();
        String[] match;
        match = new String[2];

        match[0] = "peq"; //default for no ir input
        match[1] = "99999"; //default for no ck2 output
        
        int count = 0;

        try {
            while (count < source.size()){

                line = source.get(count);
                
                if (line.contains("~META~")) { //metamapping, shoehorn several BA cultures into one I:R culture to be split later
                    line = line.replace("~META~","");
                    String[] metaHead = line.split(",");
                    String[] metaMappings = metaHead[1].split("=");
                    int metaCount = 0;
                    while (metaCount < metaMappings.length) {
                        if (provIDnum.equals(metaMappings[metaCount])) {
                            provIDnum = metaHead[0];
                            metaCount = metaMappings.length;
                        }
                        metaCount = metaCount + 1;
                    }
                }

                else if (line.split(",")[0].equals(provIDnum) && !line.contains("#")){
                    //count = 1 + source.size();
                    if (line.split(",").length >= 3) {
                        //return qaaa.split(","); //if mapping file has arguments, return those as well
                        allMatches.add(line.split(","));
                    } else {
                        match[0] = line.split(",")[0];
                        match[1] = line.split(",")[1];
                        allMatches.add(match);
                    }

                }
                count = count + 1;
            }

        }catch (java.util.NoSuchElementException exception){
            count = 1 + source.size();

        }   

        return allMatches;

    }
    
    public static ArrayList<String[]> importExoMappings (String directory) throws IOException //imports provinces which weren't in BA, but are effected by conversion
    {

        //exoMap format is ba prov, ir prov, ir prov..., changes
        String VM = "\\";
        VM = VM.substring(0);
        char quote = '"';
        FileInputStream fileIn= new FileInputStream(directory);
        Scanner scnr= new Scanner(fileIn);

        boolean endOrNot = true;
        String changes = "test";
        String qaaa;
        qaaa = scnr.nextLine();

        ArrayList<String[]> exoMappings = new ArrayList<String[]>();

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.contains("~_")){
                    changes = qaaa.split("_~_")[1];
                }
                
                else if (qaaa.contains("ba = ")){
                    String mapping = qaaa.split("ba = ")[1];
                    mapping = mapping.split(" } # ")[0];
                    mapping = mapping.replace("imperator = ","");
                    mapping = mapping + " " + changes;
                    String[] mappingArray = mapping.split(" ");
                    exoMappings.add(mappingArray);
                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return exoMappings;

    }
    
    public static ArrayList<Provinces> importProvSetupAdv (String modDir, ArrayList<Provinces> allProvInfo) throws IOException
    //imports all basegame prov files as Provinces
    {
        int aqq = 0;
        File locInfo = new File (modDir);
        String[] locList = locInfo.list();

        try {
            if (locList != null) { //get all files in directory
                while (aqq < locList.length) {
                    //System.out.println("Importing "+modDir+"/"+locList[aqq]);
                    importProvSetupAdv(modDir+"/"+locList[aqq],allProvInfo);
                    aqq = aqq + 1;
                }

            }
            else { //get everything in file
                ArrayList<String> provSetupFile = importBasicFile(modDir);
                int count = 0;
                while (count < provSetupFile.size()) {
                    String setupLine = provSetupFile.get(count);
                    if (!setupLine.equals("#")) {
                        setupLine = setupLine.split("#")[0]; //ignore comments
                    }
                    
                    if (setupLine.contains("=")) { //prov detected, loop through until end bracket
                        setupLine = setupLine.replace(" ","");
                        String provID = setupLine.split("=")[0];
                        count = count + 1;
                        String provTerrain = "Debug";
                        String provTradeGood = "Debug";
                        String provCulture = "Debug";
                        String provReligion = "Debug";
                        String provRank = "settlement";
                        double provCiv = 0.0;
                        ArrayList<Pop> provPops = new ArrayList<Pop>();
                        //int popTot = 0;
                        while (!setupLine.equals("}")) {
                           setupLine = provSetupFile.get(count);
                           if (setupLine.contains("civilization_value")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               String tmpCiv = setupLine.split("=")[1];
                               provCiv = Double.parseDouble(tmpCiv);
                           }
                           else if (setupLine.contains("culture")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provCulture = setupLine.split("=")[1];
                               provCulture = Processing.cutQuotes(provCulture);
                           }
                           else if (setupLine.contains("religion")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provReligion = setupLine.split("=")[1];
                               provReligion = Processing.cutQuotes(provReligion);
                           }
                           else if (setupLine.contains("terrain")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provTerrain = setupLine.split("=")[1];
                               provTerrain = Processing.cutQuotes(provTerrain);
                           }
                           else if (setupLine.contains("trade_goods")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provTradeGood = setupLine.split("=")[1];
                               provTradeGood = Processing.cutQuotes(provTradeGood);
                           }
                           else if (setupLine.contains("province_rank")) {
                               setupLine = setupLine.replace(" ","");
                               setupLine = setupLine.split("#")[0];
                               provRank = setupLine.split("=")[1];
                               provRank = Processing.cutQuotes(provRank);
                           }
                           else if (setupLine.contains("citizen") || setupLine.contains("freemen") || setupLine.contains("slaves")
                           || setupLine.contains("tribesmen") || setupLine.contains("nobles")) {
                               setupLine = setupLine.split("#")[0];
                               String popClass = setupLine.split("=")[0].replace("\t","");
                               String popCulture = provCulture;
                               String popReligion = provReligion;
                               //System.out.println(provID);
                               //System.out.println(setupLine+"~~"+count);
                               String popCount = "1";
                               while (!setupLine.contains("}")) {
                                   //setupLine = scnr.nextLine();
                                   count = count + 1;
                                   setupLine = provSetupFile.get(count);
                                   //setupLine = setupLine.split("#")[0];
                                   //System.out.println(setupLine+"~"+count);
                                   if (setupLine.contains("culture")) {
                                       popCulture = setupLine.split("=")[1];
                                       popCulture = Processing.cutQuotes(popCulture);
                                   }
                                   else if (setupLine.contains("religion")) {
                                       popReligion = setupLine.split("=")[1];
                                       popReligion = Processing.cutQuotes(popReligion);
                                   }
                                   else if (setupLine.contains("amount")) {
                                       popCount = setupLine.split("=")[1];
                                       popCount = popCount.replace(" ","");
                                   }
                                   
                               }
                               int popTot = 0;
                               try {
                                  popTot = Integer.parseInt(popCount);
                               } catch (java.lang.NumberFormatException exception) {
                                   System.out.println("Error, something is wrong with "+popCount+" in "+modDir);
                               }
                               while (popTot > 0) {
                                   //setupLine = scnr.nextLine();
                                   Pop tmpPop = Pop.newPop(0,popClass,popCulture,popReligion);
                                   provPops.add(tmpPop);
                                   popTot = popTot - 1;
                               }
                               //popTotalCache = id+","+popTot;
                               //System.out.println(popTotalCache);
                               
                           }
                           count = count + 1;
                        }
                        
                        //String[] provInfo = new String[3];
                        //provInfo[0] = provID;
                        //provInfo[1] = provTerrain;
                        //provInfo[2] = provTradeGood;
                        
                        Provinces tmpProv = Provinces.newProv("9999",provCulture,provReligion,0,0,0,0,0,0,provRank,provCiv,Integer.parseInt(provID));
                        tmpProv.setTerrain(provTerrain);
                        tmpProv.setTradeGood(provTradeGood);
                        if (provPops.size() > 0) {
                            tmpProv.addPopArray(provPops);
                        }
                        
                        allProvInfo.add(tmpProv);
                        //System.out.println("Adding "+provID+" with "+provTerrain+" "+provTradeGood);
                        
                    } else {
                        count = count + 1;
                    }
                }
                
                //allProvInfo.addAll(provInfo);
            }
        } catch (Exception e){ //if a non-existant file is accessed, cancel so that converter doesn't crash
            System.out.println("Error in "+modDir);
        }

        return allProvInfo;
    }
    
    public static ArrayList<String> importPopNumbers(String fileName) throws IOException
    {
        String name = fileName;
        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);
        
        boolean tf = true;
        boolean popFlag = false;
        char quote = '"';
        ArrayList<String> popTotals = new ArrayList<String>();
        String popTotalCache = "";
        int id = 0;
        int popTot = 0;
        
        try {
        
        while (tf) {
            String line = scnr.nextLine();
            String numTest = line.split("=")[0];
            numTest = numTest.replace(" ","");
            numTest = numTest.replace("﻿","");//newline character
            try {
                id = Integer.parseInt(numTest);
                //System.out.println(line);
                popTotals.add(popTotalCache);
                popTot = 0;
            } catch (java.lang.NumberFormatException exception) {
                //checkNumberE(numTest);
            }
            
            if (line.contains("citizen") || line.contains("freemen") || line.contains("slaves") || line.contains("tribesmen")
            || line.contains("nobles")) {
                line = line.split("#")[0];
                while (!line.contains("amount")) {
                    line = scnr.nextLine();
                    line = line.split("#")[0];
                }
                String popCount = line.split("=")[1];
                popCount = popCount.replace(" ","");
                popTot = popTot + Integer.parseInt(popCount);
                popTotalCache = id+","+popTot;
                //System.out.println(popTotalCache);
                
            }
            
        } 
        }
        
        catch (java.util.NoSuchElementException exception) {
            popTotals.add(popTotalCache);
            return popTotals;
        }
        popTotals.add(popTotalCache);
        return popTotals;
    }

    //developed originally by Shinymewtwo99
}
