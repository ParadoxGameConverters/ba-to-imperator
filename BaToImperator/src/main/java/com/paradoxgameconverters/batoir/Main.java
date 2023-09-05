package com.paradoxgameconverters.batoir;   

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main
 *
 * @author The Imperator:Rome to CK II converter was originally developed by Shinymewtwo99
 * 
 */
public class Main
{
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int x;

    public static void main (String[] args) throws IOException
    {

        try {
            ConverterLogger.setup();
            LOGGER.setLevel(Level.FINEST);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        LOGGER.info("Converter version 0.0 \"Pre-release\" - compatible with Bronze Age Reborn and Imperator:Rome 2.0+");
        System.out.println("Test");
        LOGGER.finest("0%");
        System.out.println("5%");

        long startTime = System.nanoTime(); //Starts the converter clock, used to tell how much time has passed

        try {
            Scanner input = new Scanner(System.in);
            String Dir; //Desired user directory, usually located in documents
            String Dir2; //.mod files use reverse slashes (/ instead of \) 
            String modName; //important for creating directories
            String saveName; //The save file (.rome) to read from
            String impDir; //The directory of the save file (.rome) to read from

            Importer importer = new Importer();
            Output output = new Output();
            Directories directories = new Directories();

            //LOGGER.info("Please input your system profile username");

            String[] configDirectories = Importer.importDir("configuration.txt");
            String VM = "\\";
            VM = VM.substring(0);
            String VN = "//";
            VN = VN.substring(0);
            Dir2 = configDirectories[1];
            String irModDir = configDirectories[2];
            //Dir = configDirectories[3];
            Dir = "output";

            modName = configDirectories[4].replace(VM,"~~~");//.substring() hates working with \ characters
            modName = modName.replace(VN,"~~~");//.substring() hates working with / characters

            modName = modName.split("~~~")[modName.split("~~~").length-1];

            if (configDirectories[6].equals("")) { //if there is a name or not
                modName = Processing.formatSaveName(modName);
            } else {
                modName = configDirectories[6];
            }
            
            String date = "100.1.1"; //default date in case something goes wrong
            
            int irProvTot = 9500;

            String impGameDir = configDirectories[1];

            String ck2Dir = configDirectories[0];

            String impDirSave = configDirectories[4];
            
            String republicOption = configDirectories[10];

            directories.modFolders (Dir,modName); //Creating the folders to write the mod files
            //along with nessicery sub-folders
            directories.descriptors(Dir,modName); //Basic .mod files required for the launcher

            String modDirectory = Dir+VN+modName;

            String[] impProvtoCK;   // Owner Culture Religeon PopTotal Buildings
            impProvtoCK = new String[2];

            Provinces baProvInfo;   // Owner Culture Religeon PopTotal Buildings
            //baProvInfo = new String[5];

            ArrayList<Provinces> baProvInfoList = new ArrayList<Provinces>();

            String[][] ck2ProvInfo;   // Array list of array lists...
            ck2ProvInfo = new String[5][irProvTot];

            //[0] is owner, [1] is culture, [2] is religion, [3] is calculated from pop
            int totalPop = 0;//pop total
            //int totalCKProv = 2550;
            int totalCKProv = 8550;

            String[] ck2PopTotals;   // Owner Culture Religeon PopTotal Buildings
            ck2PopTotals = new String[totalCKProv];
            /////////////////////////////////////////
            String[] ck2TagTotals;   // Owner Culture Religeon PopTotal Buildings
            ck2TagTotals = new String[totalCKProv];

            //TAG1,0~TAG2,0,~TAG3,0

            String[] ck2CultureTotals;   // Owner Culture Religeon PopTotal Buildings
            ck2CultureTotals = new String[totalCKProv];

            String[] ck2RelTotals;   // Owner Culture Religeon PopTotal Buildings
            ck2RelTotals = new String[totalCKProv];

            String[] ck2MonumentTotals;   // Province monuments
            ck2MonumentTotals = new String[totalCKProv];

            output.localizationBlankFile(modDirectory); //creates the country localization file

            String[] ck2HasLand;   // If country has land or not in CK II
            ck2HasLand = new String[5000];

            int[] ck2LandTot;   // The ammount of land each country has
            ck2LandTot = new int[5000];

            ArrayList<String> convertedCharacters = new ArrayList<String>(); //characters who have been converted

            convertedCharacters.add("0"); //Debug at id 0 so list will never be empty

            ArrayList<String> impSubjectInfo = new ArrayList<String>(); //Overlord-Subject relations

            ArrayList<String[]> impCharInfoList = new ArrayList<String[]>();

            ArrayList<String> impDynList = new ArrayList<String>();
            
            ArrayList<String> flaggedGovernorships = new ArrayList<String>(); //governorships which have been given flags (GFX)
            
            flaggedGovernorships.add("Glorious__Bingo_region"); //debug so that list starts with 1 item

            int aqtest = 0;
            while (aqtest < 5000) { //sets the default for all tags as landless in CKII
                ck2HasLand[aqtest] = "no";
                ck2LandTot[aqtest] = 0;
                aqtest = aqtest + 1;
            }

            int tagNum = 0;
            int cultNum = 0;
            int relNum = 0;

            String tempTest = "1000000";
            int tempNum = 1000000;

            int aqq = 0;

            int aq2 = 340;

            int aq3 = 0;

            int ckProvNum = 0;

            int temp;
            int temp2;

            int flag = 0;

            int flag2 = 0;

            String tab = "	";

            String saveCountries = "tempCountries.txt";

            String saveProvinces = "tempProvinces.txt";

            String saveCharacters = "tempCharacters.txt";

            String saveDynasty = "tempDynasty.txt";

            String saveDiplo = "tempDiplo.txt";

            String saveMonuments = "tempMonuments.txt";

            int compressedOrNot = Importer.compressTest(impDirSave); //0 for compressed, 1 for decompressed
            
            int empireRank = 350; //Ammount of holdings to be Empire
            
            int duchyRank = 30; //Ammount of holdings to be duchy
            
            int splitSize = empireRank+800;
            
            String provinceMappingSource = "provinceConversionCore.txt";
            
            if (compressedOrNot == 0) { //compressed save! Initiating Rakaly decompressor
                LOGGER.info("Compressed save detected! Implementing Rakaly Decompressor...");
                
                String newImpDirSave = impDirSave.replace(".rome", "_melted.rome");
                
                String[] rakalyCommand = new String [3];
                rakalyCommand[0] = "rakaly_win.exe";
                rakalyCommand[1] = "melt";
                rakalyCommand[2] = impDirSave;
                Processing.fileExcecute(rakalyCommand);
                impDirSave = newImpDirSave;
                
                LOGGER.info("Decompressed save generated at " + newImpDirSave);
                
            }
            
            String[] saveInfo = Importer.importSaveInfo(impDirSave);
            
            if (saveInfo[0].equals("bad")) {
                LOGGER.warning("Unable to detect save file version! Defaulting to 2.0.3");
                saveInfo[0] = ("2.0.3");
            }
            
            if (saveInfo[1].equals("bad")) {
                LOGGER.warning("Unable to detect save file date! Defaulting to 450.1.1");
                saveInfo[1] = ("450.1.1");
            }
            
            if (configDirectories[7].equals("saveYearAUC")) { //if AUC date is selected
                date = saveInfo[1];
                //System.out.println(date);
            } else if (configDirectories[7].equals("saveYear")){ //if AD date is selected
                //System.out.println(saveInfo[1]);
                String tmpSaveDate = saveInfo[1].replace(".",",");
                int tmpYear = Integer.parseInt(tmpSaveDate.split(",")[0]);
                tmpYear = tmpYear - 754;
                date = date.replace(".",","); //'.' character breaks .split function
                if (tmpYear >= 100) { //use AD version of date
                    date = Integer.toString(tmpYear)+"."+tmpSaveDate.split(",")[1]+"."+tmpSaveDate.split(",")[2];
                } else { //if year is less then 100 AD, game will use 100 AD as year while preserving the day and month
                    date = "100."+tmpSaveDate.split(",")[1]+"."+tmpSaveDate.split(",")[2];
                }
                date = date.replace(",",".");
            } else if (configDirectories[7].equals("customYear")){
                String tmpDate = configDirectories[8].replace(".",","); //'.' character breaks .split function
                int tmpYear = Integer.parseInt(tmpDate.split(",")[0]);
                if (tmpYear >= 100) { //if less then 100 AD, game will use 100 AD
                    date = configDirectories[8];
                }
                if (tmpYear <= 400) { //Timeline extended save
                    splitSize = splitSize + 400;
                }
            }
            
            if (republicOption.equals("bad")) { //If something goes wrong with reading the republic option, default to repMer
                LOGGER.warning("Error with Republic Conversion Option! Defaulting to Merchant Republic");
                republicOption = ("repMer");
            }
            
            ArrayList<String> govMap = Importer.importBasicFile("governmentConversion.txt"); //government mappings
            LOGGER.info("Importing mod directories...");
  
            ArrayList<String> modDirs = Importer.importModDirs(impDirSave,irModDir);
            ArrayList<String> modFlagGFX = Importer.importModFlagDirs(modDirs); //flag gfx files
            ArrayList<String> modRegion = Importer.importModRegionDirs(modDirs); //flag gfx files

            LOGGER.info("Importing flag information...");
            
            ArrayList<String[]> flagList = Importer.importAllFlags(impGameDir,modDirs);
            LOGGER.info("Importing color information...");
            
            ArrayList<String[]> colorList = Importer.importAllColors(impGameDir,modDirs);
            
            LOGGER.info("Importing localization information...");
            
            ArrayList<String> locList = Importer.importAllLoc(impGameDir,modDirs);
            
            ArrayList<String> regionDirList = Importer.importModRegionDirs(modDirs);
            
            String regionDir = Importer.getRegionDir(impGameDir,regionDirList,modDirs);
            
            String[] impProvRegions = Processing.importRegionList(9500,regionDir);

            LOGGER.info("Creating temp files...");

            TempFiles.tempCreate(impDirSave, tab+"country_database={", tab+"state_database={", saveCountries);

            LOGGER.info("temp Countries created");

            //TempFiles.tempCreate(impDirSave, "provinces={", "}", saveProvinces);
            TempFiles.tempCreate(impDirSave, "population={", "}", saveProvinces);

            LOGGER.info("temp Provinces created");   

            TempFiles.tempCreate(impDirSave, "character={", "objectives={", saveCharacters);

            LOGGER.info("temp Characters created");

            TempFiles.tempCreate(impDirSave, tab+"families={", "character={", saveDynasty);

            LOGGER.info("temp Dynasties created");

            TempFiles.tempCreate(impDirSave, "diplomacy={", "jobs={", saveDiplo);

            LOGGER.info("temp Diplo created");

            TempFiles.tempCreate(impDirSave, "great_work_manager={", "country_culture_manager={", saveMonuments);

            LOGGER.info("temp Monuments created");

            long tempTime = System.nanoTime();
            long tempTot = (((tempTime - startTime) / 1000000000)/60) ;

            LOGGER.info("All temp files created after "+ tempTot + " minutes");

            LOGGER.finest("5%");

            LOGGER.info("Importing territory data..."); 

            Processing.convertProvConvList(provinceMappingSource,"provinceConversion.txt");

            //processing information

            baProvInfoList = importer.importProv(saveProvinces);
            totalPop = 0;
            try { //Get counts of culture, religion, and TAG ownership
            while (flag == 0) {
                //System.out.println(aqq);
                impProvtoCK = importer.importConvList("provinceConversion.txt",aqq); 
                //System.out.println(impProvtoCK[0]+"_"+impProvtoCK[1]+"_"+impProvtoCK[2]+"_"+impProvtoCK[3]+"_"+impProvtoCK[4]);
                //System.out.println(impProvtoCK[1]);

                if (impProvtoCK[0].equals ("peq")) {
                }

                else {
                    if (ckProvNum != Integer.parseInt(impProvtoCK[1])) {
                        ckProvNum = Integer.parseInt(impProvtoCK[1]);
                        totalPop = 0;
                        tagNum = 0;
                        cultNum = 0;
                        relNum = 0;
                    }

                    baProvInfo = baProvInfoList.get(aqq);
                    //System.out.println(impProvInfo);
                    int popTotal = 0;
                    try {
                        popTotal = baProvInfo.getPops().size();
                    } catch (java.lang.NullPointerException exception) {
                        
                    }
                    
                    String popTotalStr = Integer.toString(popTotal);
                    String provOwner = baProvInfo.getOwner();
                    String provCulture = baProvInfo.getCulture();
                    String provReligion = baProvInfo.getReligion();

                    temp = 0;
                    temp2 = 0;
                    //nation
                    if (ck2TagTotals[ckProvNum] == (null)) {

                        ck2TagTotals[ckProvNum] = provOwner + "," + popTotalStr;
                        System.out.println(ck2TagTotals[ckProvNum]+" = "+provOwner + "," + popTotalStr);
                    }else {
                        ck2TagTotals[ckProvNum] = ck2TagTotals[ckProvNum] + "~" + provOwner + "," + popTotalStr;
                        System.out.println(ck2TagTotals[ckProvNum]+" = "+provOwner + "," + popTotalStr);
                    }
                    //culture
                    if (ck2CultureTotals[ckProvNum] == (null)) {

                        ck2CultureTotals[ckProvNum] = provCulture + "," + popTotalStr;
                    }else {
                        ck2CultureTotals[ckProvNum] = ck2CultureTotals[ckProvNum] + "~" + provCulture + "," + popTotalStr;
                    }
                    //religeon
                    if (ck2RelTotals[ckProvNum] == (null)) {

                        ck2RelTotals[ckProvNum] = provReligion + "," + popTotalStr;
                    }else {
                        ck2RelTotals[ckProvNum] = ck2RelTotals[ckProvNum] + "~" + provReligion + "," + popTotalStr;
                    }
                    //region for governor conversion

                    try {
                        totalPop = Integer.parseInt(ck2PopTotals[ckProvNum]);

                    }catch (java.lang.NumberFormatException exception) {
                        totalPop = 0;  
                    }

                    if (popTotalStr == null) {
                        popTotalStr = "0";  
                    }
                    totalPop = Integer.parseInt(popTotalStr) + totalPop;
                    ck2ProvInfo[3][ckProvNum] = Integer.toString(totalPop);

                    ck2PopTotals[ckProvNum] = Integer.toString(totalPop);

                }

                if (aqq == 9843) {
                    flag = 1;   
                }

                aqq = aqq + 1;
            }
            } catch (java.lang.IndexOutOfBoundsException exception){
                System.out.println(aqq);
                flag = 1;
            }

            //Culture, rel, tag Info, and pop total returned

            long territoryTime = System.nanoTime();
            long territoryTot = (((territoryTime - startTime) / 1000000000)/60);
            LOGGER.info("Territory data imported after "+ territoryTot + " minutes");

            LOGGER.finest("25%");

            LOGGER.info("Combining territories into provinces...");

            aq2 = 0;
            flag = 0;
            flag2 = 0;
            int aq5 = 0;
            int aq6 = 0;
            String[] irOwners;

            while( aq2 < totalCKProv) { // Calculate province ownership
                if (ck2TagTotals[aq2] != null)  {

                    irOwners = ck2TagTotals[aq2].split("~"); 

                    while (aq5 < irOwners.length) {
                        String[] owners = irOwners[aq5].split(","); 

                        //LOGGER.info(irOwners[aq5]+"_irOwners_"+aq2);  

                        int[] ownerTot;
                        ownerTot = new int[totalCKProv]; //should redefine each time

                        int ownNum = Integer.parseInt(owners[0]);

                        if (ownNum == 9999) {
                            ownNum = 0;

                            //LOGGER.info(aq5);
                        }

                        if (owners[1].equals ("null")) {
                            owners[1] = "0";

                            //LOGGER.info(aq5);
                        }

                        ownerTot[ownNum] = Integer.parseInt(owners[1]);
                        //LOGGER.info(owners[0]+owners[1]+"b_owners");    
                        ck2ProvInfo[0][aq2] = owners[0];
                        aq6 = 1;
                        while (aq6 < totalCKProv) {
                            if (ownerTot[aq6] > ownerTot[aq6-1]){
                                ck2ProvInfo[0][aq2] = owners[0];
                                //LOGGER.info((ck2ProvInfo[0][aq2])+"_"+aq2+"cq");
                            }
                            aq6 = aq6 + 1;

                        }
                        aq5 = aq5 + 1;
                        int tempQ = Integer.parseInt(ck2ProvInfo[0][aq2]);
                        //LOGGER.info(tempQ);
                        if (tempQ != 9999){
                            ck2HasLand[tempQ] = "yes"; //marks country as landed in CK II
                            ck2LandTot[tempQ] = ck2LandTot[tempQ] + 1; //adds tag's CK II province count
                        }

                    }
                    aq5 = 0;

                }
                else if (aq2 < 380) {
                    //LOGGER.info (ck2TagTotals[aq2] + "_" + aq2);    
                }
                aq2 = aq2 + 1;

            }
            long provinceTime = System.nanoTime();
            long provinceTimeTot = (((provinceTime - startTime) / 1000000000)/60);
            LOGGER.info("Province ownership calculated after "+provinceTimeTot+" minutes");
            LOGGER.finest("35%");
            aq2 = 0;
            flag = 0;
            
            int globalPopTotal = 4000;//Number of pops to be redistributed

            while( aq2 < totalCKProv) { // Combines data based off of majority ownership, 30 Roman pops and 15 Punic'll make CKII prov Roman
                try {

                    if (ck2TagTotals[aq2] == null) {

                    }
                    ck2ProvInfo[1][aq2] = Processing.basicProvinceTotal(totalCKProv,ck2CultureTotals,ck2ProvInfo,1,aq2);
                    ck2ProvInfo[2][aq2] = Processing.basicProvinceTotal(totalCKProv,ck2RelTotals,ck2ProvInfo,2,aq2);
                }
                catch (java.lang.NullPointerException exception) {

                }
                aq2 = aq2 + 1;
            }

            ArrayList<String> dejureDuchies = Processing.calculateDuchyNameList(ck2Dir,ck2ProvInfo);

            aq2 = 0;
            LOGGER.info("Province religion and culture calculated");
            LOGGER.info("Province data combined");
            LOGGER.finest("45%");
            LOGGER.info("Importing country data...");

            //LOGGER.config("The region is" + ck2ProvInfo[4][343]);
            int flagCount = 0;

            ArrayList<String[]> impTagInfo = new ArrayList<String[]>();
            //Country processing
            impTagInfo = importer.importCountry(saveCountries);

            long countryTime = System.nanoTime();
            long countryTimeTot = (((countryTime - startTime) / 1000000000)/60);
            LOGGER.info("Country data imported after "+countryTimeTot+" minutes");
            LOGGER.finest("55%");

            LOGGER.config("and the culture is" + ck2ProvInfo[1][343]);

            LOGGER.config("and the culture is" + ck2ProvInfo[1][1574]);
            int aq4 = 0;
            LOGGER.config(ck2TagTotals[343]);

            int totCountries = impTagInfo.size(); //ammount of IR countries in save file

            LOGGER.info("Importing subject data...");

            impSubjectInfo = Importer.importSubjects(saveDiplo);
            ck2LandTot = Processing.addSubjectsToSize(impSubjectInfo,ck2LandTot);

            long subjectTime = System.nanoTime();
            long subjectTimeTot = (((subjectTime - startTime) / 1000000000)/60);

            LOGGER.info("Subject data imported after "+subjectTimeTot+" minutes");
            LOGGER.finest("65%");

            LOGGER.info("Copying default output... (This will take 5-6 minutes)");

            //Default output, will be included in every conversion regardless of what occured in the save file
            //Output.copyRaw("defaultOutput"+VM+"cultures"+VM+"00_cultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"00_cultures.txt");
            //Processing.customDate(date,"defaultOutput/default/bookmarks/50_customBookmark.txt",modDirectory+VM+"common/bookmarks/50_customBookmark.txt");
            Output.copyDefaultOutput("defaultOutput/default",modDirectory);
            Processing.customDate(date,modDirectory+"/common/bookmarks/50_customBookmark.txt",modDirectory+"/common/bookmarks/50_customBookmark.txt");
            Processing.setTechYear(date,modDirectory);
            

            long outputTime = System.nanoTime();
            long outputTimeTot = (((outputTime - startTime) / 1000000000)/60);

            LOGGER.info("defaultOutput copied after "+outputTimeTot+" minutes");
            LOGGER.finest("69%");

            flag = 0;
            String[] Character;

            int aq7 = 0;
            String governor;
            String governorID;
            String[] governorships;
            String govReg;
            String govRegID;
            String[] govCharacter;

            impCharInfoList = Characters.importChar(saveCharacters,compressedOrNot);

            impDynList = Characters.importDynasty(saveDynasty);

            //Array

            try {
                try {
                    while (flag == 0) {

                        if (!ck2HasLand[aq4].equals ("yes") && ck2LandTot[aq4] > 0) { //if has no land but has subjects, eat subjects to prevent shattering
                            ck2ProvInfo = Processing.annexSubjects(aq4,totCountries,ck2ProvInfo,impSubjectInfo);
                            LOGGER.info (impTagInfo.get(aq4)[0] + "'s subjects own all of it's land! Eating subjects...");
                            ck2HasLand[aq4] = "yes";
                        }
                        if (ck2HasLand[aq4] != null) {
                            if (ck2HasLand[aq4].equals ("yes")) {

                                String tempNum2 = Integer.toString( tempNum + Integer.parseInt(impTagInfo.get(aq4)[16]));
                                String rank = "k";
                                String oldName = impTagInfo.get(aq4)[0]; //used to determine whether or not title is converted

                                int subjectOrNot = Processing.checkSubjectList(aq4,impSubjectInfo);
                                //LOGGER.config("subjectOrNot at " + aq4 + " is " + subjectOrNot);
                                impTagInfo.get(aq4)[17] = Processing.checkGovList(impTagInfo.get(aq4)[17],govMap); //converted government type
                                
                                Character = impCharInfoList.get(Integer.parseInt(impTagInfo.get(aq4)[16]));
                                String rulerDynasty = Characters.searchDynasty(impDynList,Character[7]);

                                if (subjectOrNot == 9999) { //if tag is free or independent
                                    if (ck2LandTot[aq4] >= empireRank || impTagInfo.get(aq4)[17].equals("imperium")) {
                                        rank = "e";
                                    }
                                    else if (ck2LandTot[aq4] <= duchyRank) {
                                        rank = "d";
                                    }
                                    impTagInfo.get(aq4)[0] = Processing.convertTitle("titleConversion.txt",rank,impTagInfo.get(aq4)[21],impTagInfo.get(aq4)[0]);
                                    convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],impTagInfo.get(aq4)[17],
                                    impTagInfo.get(aq4)[5],rank,"no_liege",date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,aq4,
                                    impTagInfo.get(aq4)[17],modDirectory);
                                    //LOGGER.info("Free Nation at " + aq4);
                                } else { //if tag is subject
                                    String[] subjectInfo = impSubjectInfo.get(subjectOrNot).split(",");
                                    String overlord = impTagInfo.get(Integer.parseInt(subjectInfo[0]))[0];
                                    String overlordGov = Processing.checkGovList(impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17],govMap);

                                    if (ck2LandTot[Integer.parseInt(subjectInfo[0])] >= empireRank || 
                                        impTagInfo.get(Integer.parseInt(subjectInfo[0]))[17].equals("imperium")) {
                                        //if overlord is empire, make subject kingdom, else make duchy
                                        rank = "k";
                                    } else if (ck2LandTot[Integer.parseInt(subjectInfo[0])] <= duchyRank) {
                                        //if overlord is duchy, make county
                                        rank = "c";
                                    } else {
                                        rank = "d";
                                    }

                                    impTagInfo.get(aq4)[0] = Processing.convertTitle("titleConversion.txt",rank,impTagInfo.get(aq4)[21],impTagInfo.get(aq4)[0]);

                                    if (subjectInfo[2].equals ("feudatory") || subjectInfo[2].equals ("satrapy") || subjectInfo[2].equals ("client_state")) { 
                                        //convert as vassal
                                        if (!rank.equals("c")) {//if below duchy, don't generate titular county title
                                            convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],
                                            impTagInfo.get(aq4)[17],impTagInfo.get(aq4)[5],rank,overlord,date,republicOption,Character[7],impDynList,
                                            impCharInfoList,convertedCharacters,aq4,overlordGov,modDirectory);
                                            //LOGGER.info("Subject Nation at " + aq4 + " Overlord is " + subjectInfo[0]);
                                        }

                                    }

                                    else { 
                                        //convert as CK II tributary
                                        //WIP
                                        if (!rank.equals("c")) {//if below duchy, don't generate titular county title
                                            convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,impTagInfo.get(aq4)[3],impTagInfo.get(aq4)[17],
                                            impTagInfo.get(aq4)[5],rank,overlord,date,republicOption,Character[7],impDynList,impCharInfoList,
                                            convertedCharacters,aq4,overlordGov,modDirectory);
                                        }
                                        //LOGGER.info("Tributary Nation at " + aq4 + " Overlord is " + subjectInfo[0]);
                                    }
                                }

                                //LOGGER.info (impTagInfo.get(aq4)[16] + " rules " + impTagInfo.get(aq4)[0] + "_" + aq4);
                                
                                convertedCharacters = Output.characterCreation(tempNum2, Output.cultureOutput(Character[1]),Output.religionOutput(Character[2]),
                                    Character[3],Character[0],Character[7],Character[4],Character[8],Character[10],Character[11],Character[12],Character[13],Character[14],
                                    Character[15],impTagInfo.get(aq4)[17],"q","q",convertedCharacters,impCharInfoList,date,modDirectory);
                                //LOGGER.config ("c");


                                Output.dynastyCreation(rulerDynasty,Character[7],Character[16],modDirectory);

                                String[] locName = importer.importLocalisation(locList,impTagInfo.get(aq4)[19],rulerDynasty);
                                Output.localizationCreation(locName,impTagInfo.get(aq4)[0],rank,modDirectory);
                                if (oldName.equals(impTagInfo.get(aq4)[0])) { //Try to generate flag, if failure occurs, copy capital flag
                                    int genFlag = 0;
                                    try {
                                        genFlag = Output.generateFlag(ck2Dir,impGameDir,rank,flagList,impTagInfo.get(aq4)[0],impTagInfo.get(aq4)[23],
                                        colorList,modFlagGFX,modDirectory);
                                        //File test = new File (modDirectory+"/gfx/flags/"+ck2Tag+".tga");
                                    } catch(Exception e) { //if something goes wrong, don't crash entire converter
                                        LOGGER.warning("Exception created while generating flag "+impTagInfo.get(aq4)[23]+" for "+impTagInfo.get(aq4)[0]+
                                        ", aborting flag generation");
                                    }
                                    
                                    if (genFlag == 0) {
                                        Output.copyFlag(ck2Dir,modDirectory,rank,impTagInfo.get(aq4)[5],impTagInfo.get(aq4)[0]);
                                    }
                                    else if (genFlag == 1) {
                                        LOGGER.info("I:R flag "+impTagInfo.get(aq4)[23]+" for "+impTagInfo.get(aq4)[0]+" successfully generated!");
                                    }
                                }

                                //LOGGER.info(tempTest+impTagInfo.get(aq4)[16] + "_" +Character[3]+Character[0]+Character[7]);
                                //LOGGER.info ("Name is " + locName[0] + " for " +impTagInfo.get(aq4)[0]);
                                //LOGGER.config ("output1");
                                aq7 = 0;
                                String subRank = "d";//rank of governorships, 1 below primary title
                                if (rank.equals("e")) { //Create kingdom tier title of capital region for empire title
                                    subRank = "k";
                                    String capitalColor = Processing.capitalColor(impTagInfo.get(aq4)[3]); //sets the capital region to use different color
                                    
                                    convertedCharacters = Output.titleCreation(impTagInfo.get(aq4)[0],tempNum2,capitalColor,impTagInfo.get(aq4)[17],
                                    impTagInfo.get(aq4)[5],subRank,"no_liege",date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,
                                    aq4,impTagInfo.get(aq4)[17],modDirectory);

                                    
                                    String capitalName = "PROV"+impTagInfo.get(aq4)[5]; //use name of capital for generated kingdom
                                    String[] capitalLoc = importer.importLocalisation(locList,capitalName,rulerDynasty);
                                    //String[] capitalLoc = Importer.importProvLocalisation(impGameDir,capitalName);
                                    if (capitalLoc[0].equals(capitalName)) { //In the event I:R prov has no name, use CK2 prov name
                                        capitalName = Importer.importConvList("provinceConversion.txt",Integer.parseInt(impTagInfo.get(aq4)[5]))[1];
                                        capitalName = Processing.importNames("a",Integer.parseInt(capitalName),ck2Dir)[0];
                                        capitalLoc = (capitalName+","+capitalName).split(",");
                                    }

                                    Output.localizationCreation(capitalLoc,impTagInfo.get(aq4)[0],subRank,modDirectory);
                                    Output.copyFlag(ck2Dir,modDirectory,subRank,impTagInfo.get(aq4)[5],impTagInfo.get(aq4)[0]); //use flag of empire
                                    
                                    //generate dynamic ew split
                                    if (ck2LandTot[aq4] >= empireRank+800 && impTagInfo.get(aq4)[17].equals("imperium") &&
                                    !impTagInfo.get(aq4)[0].equals("byzantium") && !impTagInfo.get(aq4)[0].equals("roman_empire_west") &&
                                    !impTagInfo.get(aq4)[21].equals("WRE")) {
                                        Processing.dynamicSplit(impTagInfo.get(aq4)[0],rank,impTagInfo.get(aq4)[3],locName,impTagInfo.get(aq4)[23],
                                        impGameDir,impTagInfo.get(aq4)[5],flagList,colorList,modFlagGFX,impTagInfo.get(aq4)[17],
                                        impTagInfo.get(aq4)[6],ck2Dir,modDirectory);
                                        LOGGER.info("Generated east/west split for "+impTagInfo.get(aq4)[0]);
                                    }
                                } else if (rank.equals("d")) { //For duchies, don't create governorship titles
                                    subRank = "c";
                                }
                                
                                impTagInfo.get(aq4)[22] = rank;

                                //governor conversion
                                if (impTagInfo.get(aq4)[20] != "none" && subjectOrNot == 9999) {
                                    governorships = impTagInfo.get(aq4)[20].split(",");
                                    while (aq7 < governorships.length) {
                                        governor = governorships[aq7].split("~")[1]; 
                                        governorID = Integer.toString(tempNum + Integer.parseInt(governor)); 
                                        govReg = governorships[aq7].split("~")[0]; 
                                        govRegID = impTagInfo.get(aq4)[0]+"__"+govReg; 

                                        if (!subRank.equals("c")) {//For duchies, don't create governorship titles
                                            convertedCharacters = Output.titleCreation(govRegID,governorID,Processing.randomizeColor(),"no","none",subRank,
                                            impTagInfo.get(aq4)[0],date,republicOption,Character[7],impDynList,impCharInfoList,convertedCharacters,aq4,
                                            "govq",modDirectory);
                                            Output.copyFlag(ck2Dir,modDirectory,subRank,impTagInfo.get(aq4)[5],govRegID); //default flag for governorships
                                        }
                                        
                                        govCharacter = impCharInfoList.get(Integer.parseInt(governor));
                                        convertedCharacters = Output.characterCreation(governorID, Output.cultureOutput(govCharacter[1]),Output.religionOutput(govCharacter[2]),govCharacter[3],
                                            govCharacter[0],govCharacter[7],govCharacter[4],govCharacter[8],govCharacter[10],govCharacter[11],govCharacter[12],govCharacter[13],
                                            govCharacter[14],govCharacter[15],saveCharacters,"q","q",convertedCharacters,impCharInfoList,date,modDirectory);
                                            
                                        String governorDynasty = Characters.searchDynasty(impDynList,govCharacter[7]);
                                        
                                        Output.dynastyCreation(governorDynasty,govCharacter[7],govCharacter[16],modDirectory);

                                        aq7 = aq7 + 1;
                                    }

                                }

                            }
                        }

                        aq4 = aq4 + 1;
                    }

                }catch (java.util.NoSuchElementException exception){
                    flag = 1;
                    //LOGGER.config("NoSuchElementException and flag = 1");
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception){
                flag = 1;
                //LOGGER.config("ArrayIndexOutOfBoundsException and flag = 1" + "_" + aq4);
            }
            aq4 = 0;
            aq7 = 0;
            LOGGER.config(ck2HasLand[343]);

            Output.dejureTitleCreation(impTagInfo,empireRank,duchyRank,ck2LandTot,dejureDuchies,impSubjectInfo,modDirectory);

            long titleTime = System.nanoTime();
            long titleTimeTot = (((titleTime - startTime) / 1000000000)/60);
            LOGGER.info("Titles and characters created after "+titleTimeTot+" minutes");
            LOGGER.finest("85%");
            LOGGER.info("Outputting Province info");

            
            //Output

            try {
                try {
                    while (flag == 1) {

                        

                        aq4 = aq4 + 1;
                    }

                }catch (java.util.NoSuchElementException exception){
                    flag = 1;
                    //LOGGER.config ("Exception1");
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException exception){
                flag = 2;
                //LOGGER.config ("Exception2");
                //LOGGER.config(ck2ProvInfo[1][343] + "_343");
                //LOGGER.config(ck2ProvInfo[1][342] + "_342");
                //LOGGER.config(ck2ProvInfo[1][341] + "_341");
                //LOGGER.config(ck2ProvInfo[1][340] + "_340");
                //LOGGER.config(ck2ProvInfo[1][339] + "_339");

            }
            //LOGGER.config(ck2ProvInfo[1][343] + "_343");
            //LOGGER.config(ck2ProvInfo[1][342] + "_342");
            //LOGGER.config(ck2ProvInfo[1][341] + "_341");
            //LOGGER.config(ck2ProvInfo[1][340] + "_340");
            //LOGGER.config(ck2ProvInfo[1][339] + "_339");
            //LOGGER.config(ck2ProvInfo[1][338] + "_338");

            long endTime = System.nanoTime();
            long elapsedTot = (((endTime - startTime) / 1000000000)/60) ;

            LOGGER.info("Converter successfully finished after " + elapsedTot + " minutes!");

            LOGGER.finest("100%");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            LOGGER.severe(sw.toString());
            throw e;
        }
    }
}
