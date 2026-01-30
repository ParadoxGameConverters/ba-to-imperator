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

        LOGGER.info("Converter version 0.1 \"Akkad\" - compatible with Bronze Age Reborn and Imperator:Rome 2.0+");
        //System.out.println("Test");
        LOGGER.finest("0%");
        //System.out.println("5%");

        long startTime = System.nanoTime(); //Starts the converter clock, used to tell how much time has passed

        try {
            Scanner input = new Scanner(System.in);
            String Dir; //Desired user directory, usually located in documents
            //String Dir2; //.mod files use reverse slashes (/ instead of \) 
            String modName; //important for creating directories
            String saveName; //The save file (.rome) to read from
            String impDir; //The directory of the save file (.rome) to read from

            Importer importer = new Importer();
            Output output = new Output();
            Directories directories = new Directories();

            //LOGGER.info("Please input your system profile username");

            String[] configDirectories = Importer.importDir("configuration.txt");
            int configCount = 0;
            while (configCount < configDirectories.length) {
                LOGGER.info("configuration setting "+configCount+" is "+configDirectories[configCount]);
                configCount = configCount + 1;
            }
            
            String VM = "\\";
            VM = VM.substring(0);
            String VN = "//";
            VN = VN.substring(0,1);
            //Dir2 = configDirectories[1]; //I:R game dir in steamapps/
            String irModDir = configDirectories[2];
            //Dir = configDirectories[3];
            Dir = "output";

            modName = configDirectories[4].replace(VM,"~~~");//.substring() hates working with \ characters
            modName = modName.replace(VN,"~~~");//.substring() hates working with / characters

            modName = modName.split("~~~")[modName.split("~~~").length-1];

            if (configDirectories[6].equals("")) { //if there is a name or not
                modName = Processing.formatSaveName(modName);
            } else {
                modName = Processing.formatSaveName(configDirectories[6]); //format custom names
            }
            
            String date = "100.1.1"; //default date in case something goes wrong
            
            int irProvTot = 15000;

            String impGameDir = configDirectories[1];
            
            impGameDir = Directories.removeGameFromDir(impGameDir); // removes "game from Dir"

            //String baDir = configDirectories[0];

            String impDirSave = configDirectories[4];
            
            String antagonistOption = configDirectories[10];

            directories.modFolders (Dir,modName); //Creating the folders to write the mod files
            //along with nessicery sub-folders

            String modDirectory = Dir+VN+modName;

            String[] baProvtoIR;   // Owner Culture Religeon PopTotal Buildings
            baProvtoIR = new String[2];

            Provinces baProvInfo;   // Owner Culture Religeon PopTotal Buildings
            //baProvInfo = new String[5];

            ArrayList<Provinces> baProvInfoList = new ArrayList<Provinces>(); //source (from BA) provinces
            ArrayList<Provinces> irProvInfoList = new ArrayList<Provinces>(); //output (to IR) provinces

            String[][] ck2ProvInfo;   // Array list of array lists...
            ck2ProvInfo = new String[5][irProvTot];

            //[0] is owner, [1] is culture, [2] is religion, [3] is calculated from pop
            int totalPop = 0;//pop total
            //int totalCKProv = 2550;
            //int totalCKProv = 8550;
            int totalCKProv = 9950;

            String[] ck2PopTotals;   // Owner Culture Religeon PopTotal Buildings
            ck2PopTotals = new String[irProvTot];
            /////////////////////////////////////////


            //output.localizationBlankFile(modDirectory); //creates the country localization file
            output.coaBlankFile(modDirectory); //creates the country localization file

            String[] ck2HasLand;   // If country has land or not in CK II
            ck2HasLand = new String[5000];

            int[] ck2LandTot;   // The ammount of land each country has
            ck2LandTot = new int[5000];

            ArrayList<Diplo> impSubjectInfo = new ArrayList<Diplo>(); //Overlord-Subject relations

            ArrayList<String[]> impCharInfoList = new ArrayList<String[]>();

            ArrayList<String> impDynList = new ArrayList<String>();
            
            ArrayList<String> flaggedGovernorships = new ArrayList<String>(); //governorships which have been given flags (GFX)
            
            flaggedGovernorships.add("Glorious__Bingo_region"); //debug so that list starts with 1 item

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
            
            String saveDeities = "tempDeities.txt";
            
            String saveCultures = "tempCultures.txt";

            int compressedOrNot = Importer.compressTest(impDirSave); //0 for compressed, 1 for decompressed
            
            int empireRank = 350; //Ammount of holdings to be Empire
            
            int duchyRank = 30; //Ammount of holdings to be duchy
            
            int splitSize = empireRank+800;
            
            ArrayList<String> customModInstalls = Importer.importBasicFile("configurables/modDirectories.txt");
            
            boolean invictus = false; //initially just for Invictus, but now for any mod hybridization
            String hybridName = configDirectories[7];
            if (!hybridName.equals("no")) { //if mod hybridization is enabled
                invictus = true;
            }
            ArrayList<String> hybridNamesFull = Importer.importBasicFile("configurables/modAbbreviations.txt");
            String hybridNameFull = Output.cultureOutput(hybridNamesFull,hybridName); //ti becomes Terra Indomita, for example
            directories.descriptors(Dir,irModDir,modName,hybridName,hybridNameFull,false); //Basic .mod files required for the launcher
            directories.descriptors(Dir+"/"+modName,irModDir,modName,hybridName,hybridNameFull,true); //Descriptor for uploading to the Steam workshop
            
            String invictusDir = "";
            if (configDirectories[8].equals("default") && invictus){ //default directory for Steam mods
                ArrayList<String> steamIDs = Importer.importBasicFile("configurables/steamIDs.txt");
                String steamID = Output.cultureOutput(steamIDs,hybridName);
                
                invictusDir = Directories.detectWorkshopFromDir(impGameDir,steamID);
                
                if (invictusDir == null) { //Unable to find standard workshop directory from impGameDir, check default as a backup
                    String steamInvDir = ":/Program Files (x86)/Steam/steamapps/workshop/content/859580/"+steamID;
                    invictusDir = Directories.detectPathDrive(steamInvDir);
                    if (invictusDir == null) {
                        LOGGER.severe("Unable to detect "+hybridName+"! If you have a custom, non-standard, or non-Steam installation, select the 'Custom Installation' Hybrid Directory option before converting.");
                    }
                }
                
            } else if (configDirectories[8].equals("custom")){ //default directory for Invictus
                invictusDir = Output.cultureOutput(customModInstalls,"invictus");
                File invictusTest = new File(invictusDir);
                if (invictusTest.isDirectory()) {
                    LOGGER.info(hybridName+" found!");
                } else {
                    LOGGER.severe("Unable to detect "+hybridName+" at "+invictusDir+"");
                }
            }
            
            boolean hasCustomBADir = false;
            String customBADir = Output.cultureOutput(customModInstalls,"bronzeAge");
            if (!customBADir.equals("none")) {
                File baDirTest = new File(customBADir);
                if (baDirTest.isDirectory()) {
                    LOGGER.info("Custom Bronze Age directory found at "+customBADir);
                    hasCustomBADir = true;
                } else {
                    LOGGER.warning("Unable to detect Custom Bronze Age directory at "+customBADir+"! If you are not using a custom BA directory, set bronzeAge in configurables/modDirectories.txt to 'none'");
                }
            }
            
            String mappingDir = "configurables/vanilla/";
            if (invictus) {
                mappingDir = "configurables/"+hybridName+"/";
            }
            
            String provinceMappingSource = mappingDir+"provinceConversionCore.txt";
            //String provinceMappingSource = "provinceConversionCore.txt";
            
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
                LOGGER.warning("Unable to detect save file date! Defaulting to 785.1.1");
                saveInfo[1] = ("785.1.1");
            }
            
            date = saveInfo[1];
            
            boolean convertAntagonists = false;
            
            if (antagonistOption.equals("bad")) { //If something goes wrong with reading the antagonist option, default to no
                LOGGER.warning("Error with Antagonist Conversion Option! Defaulting to none!");
            } else if (antagonistOption.equals("yes")) { //Convert antagonist modifier from BA
                convertAntagonists = true;
            }
            
            //default is baOnly configuration
            boolean vanillaMonuments = false;
            boolean moddedMonuments = true;
            
            if (configDirectories[9].equals("vanillaOnly")){
                vanillaMonuments = true;
                moddedMonuments = false;
            } else if (configDirectories[9].equals("both")){
                vanillaMonuments = true;
            } else if (antagonistOption.equals("bad")) { //baOnly
                LOGGER.warning("Error with Great Work Conversion Option! Defaulting to BA Only!");
            }
            
            boolean pruneMinorCharactersSetting = false;
            int pruneLevel = 10;
            
            if (configDirectories[11].equals("pruneMinorCharacters")){
                pruneMinorCharactersSetting = true;
            } else if (configDirectories[11].equals("mediumPrune")){
                pruneLevel = 5;
            } else if (configDirectories[11].equals("extremePrune")){
                pruneLevel = 0;
            }
            
            boolean convertHeritage = true;
            if (configDirectories[12].equals("no")){ //default will convert over BA heritages
                convertHeritage = false;
            }
            
            String lawSetting = "all";
            if (configDirectories[13].equals("technology")){ //default will convert over all laws
                lawSetting = "technology";
            } else if (configDirectories[13].equals("noLaws")){ //default will convert over all laws
                lawSetting = "noLaws";
            }
            
            ArrayList<String> govMap = Importer.importBasicFile(mappingDir+"governmentConversion.txt"); //government mappings
            LOGGER.info("Importing mod directories...");
  
            ArrayList<String> modDirs = Importer.importModDirs(impDirSave,irModDir);
            if (hasCustomBADir) {
                modDirs.add(customBADir);
            }
            ArrayList<String> modFlagGFX = Importer.importModFlagDirs(modDirs); //flag gfx files
            ArrayList<String> modRegion = Importer.importModRegionDirs(modDirs); //flag gfx files

            LOGGER.info("Importing flag information...");
            
            ArrayList<String[]> flagList = Importer.importAllFlags(modDirs);
            LOGGER.info("Importing color information...");
            
            ArrayList<String[]> colorList = Importer.importAllColors(impGameDir,modDirs);
            LOGGER.info("Importing heritage information...");
            
            ArrayList<Heritage> heritageList = Importer.importAllHeritages(impGameDir,modDirs);
            
            LOGGER.info("Importing localization information...");
            
            ArrayList<String> vanillaLoc = Importer.importVanillaLoc(impGameDir);
            if (invictus) { //Ensure that new Invictus provinces get localization
                ArrayList<String> vanillaPlusLoc = Importer.importSingleModLoc(invictusDir);
                vanillaLoc.addAll(vanillaPlusLoc);
            }
            ArrayList<String> moddedLoc = Importer.importAllModLoc(impGameDir,modDirs);
            ArrayList<String> locList = new ArrayList<String>();
            locList.addAll(vanillaLoc);
            locList.addAll(moddedLoc);
            //ArrayList<String> locList = Importer.importAllLoc(impGameDir,modDirs);
            
            
            
            LOGGER.info("Importing regions and areas...");
            
            ArrayList<String> regionDirList = Importer.importModRegionDirs(modDirs);
            
            String regionDir = Importer.getRegionDir(impGameDir,regionDirList,modDirs);
            
            String[] provAreas = Processing.importAreas(regionDir+"map_data/areas.txt",irProvTot);
            String[] baProvRegions = Processing.importRegionList(irProvTot,provAreas,regionDir);
            
            String provDir = impGameDir+"/game/setup/provinces";
            String gameFileDir = impGameDir+"/game";
            String originalGameFileDir = gameFileDir; //The path to the original game files
            if (invictus) {
                provDir = invictusDir+"/setup/provinces";
                gameFileDir = invictusDir;
            }
            
            String[] vanillaProvAreas = Processing.importAreas(gameFileDir+"/map_data/areas.txt",irProvTot);
            String[] vanillaProvRegions = Processing.importRegionList(irProvTot,vanillaProvAreas,gameFileDir+"/");
            
            //ArrayList<String[]> extraProvInfo = new ArrayList<String[]>();
            
            LOGGER.info("Importing province tradegoods and terrain types...");
            
            Provinces blankProv = Provinces.newProv("9999","noCulture","noReligion",-1,0,0,0,0,0,"settlement",0.0,0);
            
            //extraProvInfo = Importer.importProvSetup(impGameDir+"/game/setup/provinces",extraProvInfo);
            
            ArrayList<String> buildingNames = new ArrayList<String>();
            try {
                buildingNames = Importer.importBuildingNames(gameFileDir+"/common/buildings/00_default.txt");
            } catch (Exception e) { //Mod has no custom buildings, default to vanilla buildings
                LOGGER.info("No custom buildings detected for "+hybridNameFull+" ("+hybridName+"), using vanilla buildings instead");
                buildingNames = Importer.importBuildingNames(originalGameFileDir+"/common/buildings/00_default.txt");
            }
            LOGGER.info("Building definitions parsed successfully");
            ArrayList<Provinces> vanillaProvinces = new ArrayList<Provinces>();
            
            vanillaProvinces = Importer.importProvSetupAdv(provDir,buildingNames,vanillaProvinces);
            vanillaProvinces = Processing.reorderProvincesByID(vanillaProvinces,blankProv);
            vanillaProvinces = Processing.applyRegionsToProvinces(vanillaProvRegions,vanillaProvinces);
            vanillaProvinces = Processing.applyAreasToProvinces(vanillaProvAreas,vanillaProvinces);
            

            LOGGER.info("Creating temp files...");
            
            //ArrayList<String> wholeSaveFile = Importer.importBasicFile(saveCountries);
            //I was going to replace the tempFile system, however, there is a size limit to Array Lists, so it's not worth the trouble for now
            
            //ArrayList<String> tempCountries = TempFiles.tempCreateInMemory(wholeSaveFile, tab+"country_database={", tab+"state_database={");

            TempFiles.tempCreate(impDirSave, tab+"country_database={", tab+"state_database={", saveCountries);

            LOGGER.info("temp Countries created");

            TempFiles.tempCreate(impDirSave, "population={", "road_network={", saveProvinces);
            //ArrayList<String> tempProvinces = TempFiles.tempCreateInMemory(wholeSaveFile, "population={", "}");
            //Output.outputBasicFile(tempCountries,"tempTest2.txt","defaultOutput");

            LOGGER.info("temp Provinces created");   

            TempFiles.tempCreate(impDirSave, "character={", "objectives={", saveCharacters);
            //ArrayList<String> tempCharacters = TempFiles.tempCreateInMemory(wholeSaveFile, "character={", "objectives={");

            LOGGER.info("temp Characters created");

            TempFiles.tempCreate(impDirSave, tab+"families={", "character={", saveDynasty);
            //ArrayList<String> tempDynasty = TempFiles.tempCreateInMemory(wholeSaveFile, tab+"families={", "character={");

            LOGGER.info("temp Dynasties created");

            TempFiles.tempCreate(impDirSave, "diplomacy={", "jobs={", saveDiplo);
            //ArrayList<String> tempDiplo = TempFiles.tempCreateInMemory(wholeSaveFile, "diplomacy={", "jobs={");

            LOGGER.info("temp Diplo created");

            TempFiles.tempCreate(impDirSave, "great_work_manager={", "country_culture_manager={", saveMonuments);
            //ArrayList<String> tempMonuments = TempFiles.tempCreateInMemory(wholeSaveFile, "great_work_manager={", "country_culture_manager={");

            LOGGER.info("temp Monuments created");
            
            TempFiles.tempCreate(impDirSave, "deity_manager={", "great_work_manager={", saveDeities);
            //ArrayList<String> tempDeities = TempFiles.tempCreateInMemory(wholeSaveFile, "deity_manager={", "great_work_manager={");

            LOGGER.info("temp Deities created");
            
            TempFiles.tempCreate(impDirSave, "country_culture_manager={", "legion_manager={", saveCultures);
            
            LOGGER.info("tempCultures created");

            long tempTime = System.nanoTime();
            long tempTot = (((tempTime - startTime) / 1000000000)/60) ;

            LOGGER.info("All temp files created after "+ tempTot + " minutes");

            LOGGER.finest("5%");

            LOGGER.info("Importing territory data..."); 

            Processing.convertProvConvList(provinceMappingSource,"provinceConversion.txt");

            //processing information

            baProvInfoList = importer.importProv(saveProvinces);
            //System.out.println(baProvInfoList.get(2200).getNobles().size());
            //System.out.println(baProvInfoList.get(2200).getCitizens().size());
            //System.out.println(baProvInfoList.get(2200).getFreemen().size());
            //System.out.println(baProvInfoList.get(2200).getTribesmen().size());
            //System.out.println(baProvInfoList.get(2200).getSlaves().size());
            
            int baPopTotal = 0;//total number of pops in BA save
            totalPop = 0;
            
            ArrayList<String> cultureMappings = Importer.importBasicFile(mappingDir+"cultureConversion.txt");
            ArrayList<String> religionMappings = Importer.importBasicFile(mappingDir+"religionConversion.txt");
            ArrayList<String> provinceMappings = Importer.importBasicFile("provinceConversion.txt");
            ArrayList<String> tagMappings = Importer.importBasicFile(mappingDir+"titleConversion.txt");
            ArrayList<String> exoCultureMappings = Importer.importBasicFile(mappingDir+"exoCultureConversion.txt");
            ArrayList<String> exoNames = Importer.importBasicFile(mappingDir+"exoNames.txt");
            ArrayList<String> exoColors = Importer.importBasicFile(mappingDir+"exoColors.txt");
            ArrayList<String> exoFlags = Importer.importBasicFile(mappingDir+"exoFlags.txt");
            ArrayList<String> monumentMappings = Importer.importBasicFile(mappingDir+"monumentMappings.txt");
            ArrayList<String> lawMappings = Importer.importBasicFile(mappingDir+"lawMappings.txt");
            
            baProvInfoList = Processing.applyRegionsToProvinces(baProvRegions,baProvInfoList);
            baProvInfoList = Processing.applyAreasToProvinces(provAreas,baProvInfoList);
            
            ArrayList<Country> baTagInfo = new ArrayList<Country>();
            //Country processing
            baTagInfo = importer.importCountry(saveCountries);
            ArrayList<Deity> baDeityInfo = new ArrayList<Deity>();
            baDeityInfo = importer.importDeities(saveDeities);
            
            ArrayList<CultureRights> allCRs = new ArrayList<CultureRights>();
            allCRs = Importer.importCultureRights(saveCultures);
            
            ArrayList<Monument> baMonumentInfo = new ArrayList<Monument>();
            baMonumentInfo = importer.importMonuments(saveMonuments);
            baMonumentInfo = Processing.convertMonuments(baMonumentInfo,monumentMappings);
            
            LOGGER.finest("10%");
            int allTagCount = 0;
            while (allTagCount < baTagInfo.size()) {
                Country baTag = baTagInfo.get(allTagCount);
                String capital = baTag.getCapital();
                int capInt = Integer.parseInt(capital);
                Provinces capitalBAProv = baProvInfoList.get(capInt);
                
                String oldCulture = baTag.getCulture();
                String oldReligion = baTag.getReligion();
                String capitalArea = capitalBAProv.getArea();
                String capitalRegion = capitalBAProv.getRegion();
                
                if (capitalArea == null && capInt > 0) {
                    System.out.println(capitalRegion + " Missed");
                    System.out.println(capInt);
                }
                            
                String newCulture = Output.paramMapOutput(cultureMappings,oldCulture,oldCulture,"date",oldCulture,capitalRegion,capitalArea,"none","none","none");
                String newReligion = Output.paramMapOutput(religionMappings,newCulture,newCulture,"date",oldReligion,capitalRegion,capitalArea,"none","none","none");
                if (!convertAntagonists) { //if user selected option not to convert antagonists, all tags loose their antagonist modifier
                    baTag.setAntagonist(false);
                }
                            
                if (newCulture.equals("99999")) {
                    newCulture = "roman"; //Game will crash when a country has a non-existant primary culture
                    System.out.println("Warning, culture "+oldCulture+" is unmapped, setting to 'Roman'");
                }
                baTag.setCulture(newCulture);
                baTag.setReligion(newReligion);
                allTagCount = allTagCount + 1;
            }
            
            LOGGER.finest("15%");
            
            baProvInfoList = Processing.convertAllPops(baProvInfoList,baTagInfo,cultureMappings,religionMappings);
            
            //String[] countTest = Processing.countPops(baProvInfoList.get(1974).getPops(),"cultureAndReligion");
            //ArrayList<String> countTest2 = Processing.condenseArrayStr(countTest);
            //ArrayList<String> countTest3 = Processing.convertPopCountToRatios(countTest2);
            //ArrayList<String> countTest4 = Processing.reallocatePops(countTest3,30);
            ArrayList<Provinces> irProvinceList = new ArrayList<Provinces>();//ArrayList
            irProvinceList.add(blankProv);
            //ArrayList<String[]> irProvMappings = new ArrayList<String[]>();//ArrayList 
            try { ////Get counts of culture, religion, and TAG ownership
                //Calc pop total
            while (flag == 0) {
                //System.out.println(aqq);
                //baProvtoIR = importer.importConvList("provinceConversion.txt",aqq); 
                String aqqStr = Integer.toString(aqq);
                baProvtoIR = Importer.importMappingFromArray(provinceMappings,aqqStr);
                

                if (baProvtoIR[0].equals ("peq")) {
                }

                else {
                    if (ckProvNum != Integer.parseInt(baProvtoIR[1])) {
                        ckProvNum = Integer.parseInt(baProvtoIR[1]);
                        totalPop = 0;
                        ////tagNum = 0;
                        ////cultNum = 0;
                        ////relNum = 0;
                    }
                    int irProvCount = 0;
                    

                    //System.out.println(ckProvNum);
                    baProvInfo = baProvInfoList.get(aqq);
                    baProvInfo.setPopTags();
                    ArrayList<Pop> baPops = baProvInfo.getPops();
                    //System.out.println(impProvInfo);
                    int popTotal = 0;
                    double civValue = baProvInfo.getCivValue();
                    //
                    int createdFlag = 0;
                    int irProvListID = Processing.getProvByID(irProvinceList,ckProvNum);
                    Provinces irProv = irProvinceList.get(irProvListID);
                    if (irProvListID == 0) {
                        irProv = Provinces.newProv("9999","noCulture","noReligion",-1,0,0,0,0,0,"settlement",0.0,0);
                        irProv.setID(ckProvNum);
                        irProvinceList.add(blankProv);
                        irProvListID = irProvinceList.size()-1;
                    }
                    try {
                        popTotal = baPops.size();
                        irProv.addPopArray(baPops);
                        irProv.addCivValue(civValue);
                        irProvinceList.set(irProvListID,irProv);
                        //irProvinceList.get(irProvListID).addPopArray(baPops);
                    } catch (java.lang.NullPointerException exception) {
                        
                    }
                    //irProvinceList.get(irProvListID) = "";
                    baPopTotal = baPopTotal + popTotal;
                    
                    String popTotalStr = Integer.toString(popTotal);
                    ////String provOwner = baProvInfo.getOwner();
                    ////String provCulture = baProvInfo.getCulture();
                    ////String provReligion = baProvInfo.getReligion();
                    temp = 0;
                    temp2 = 0;
                    //if (ckProvNum == 356) {
                        //System.out.println("Adding BA "+aqq+" to IR "+ ckProvNum +" (" +irProvListID+ ")");
                    //}
                    //nation
                    ////if (ck2TagTotals[ckProvNum] == (null)) {

                        ////ck2TagTotals[ckProvNum] = provOwner + "," + popTotalStr;
                        ////System.out.println(ck2TagTotals[ckProvNum]+" = "+provOwner + "," + popTotalStr);
                    ////}else {
                        ////ck2TagTotals[ckProvNum] = ck2TagTotals[ckProvNum] + "~" + provOwner + "," + popTotalStr;
                        ////System.out.println(ck2TagTotals[ckProvNum]+" = "+provOwner + "," + popTotalStr);
                    ////}
                    //culture
                    
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
                    ////System.out.println(totalPop+"Q");
                    ////ck2ProvInfo[3][ckProvNum] = Integer.toString(totalPop);
                    ////System.out.println(ck2ProvInfo[3][ckProvNum]+"Q2");

                    ////ck2PopTotals[ckProvNum] = Integer.toString(totalPop);
                    //System.out.println("Done "+aqq);

                }

                if (aqq == irProvTot) {
                    flag = 1;   
                }
                
                
                //irProvInfoList

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

            aq2 = 1;
            flag = 0;
            flag2 = 0;
            int aq5 = 0;
            int aq6 = 0;
            String[] irOwners;
            int globalPopTotal = 8580;//Number of pops to be redistributed throughout the converted world

            while( aq2 < irProvinceList.size()) { // Calculate province totals
                //aq2 = Processing.getProvByID(irProvinceList,357);
                Provinces irProvInfo = irProvinceList.get(aq2);
                try {
                    float totalProvPops = irProvInfo.getPops().size();
                    int provinceTotal = Processing.calcAllocatedPops(baPopTotal,totalProvPops,globalPopTotal);

                    String[] relCultCount = Processing.countPops(irProvInfo.getPops(),"cultureAndReligion");
                    ArrayList<String> relCultCount2 = Processing.condenseArrayStr(relCultCount);
                    ArrayList<String> relCultCount3 = Processing.convertPopCountToRatios(relCultCount2);
                    ArrayList<String> relCultCount4 = Processing.reallocatePops(relCultCount3,provinceTotal);
                    String[] irProvRelCult = Processing.getMajority(relCultCount4).split("---");
                    irProvInfo.setCulture(irProvRelCult[0]);
                    irProvInfo.setReligion(irProvRelCult[1]);
                    
                    String[] ownerCount = Processing.countPops(irProvInfo.getPops(),"tag");
                    ArrayList<String> ownerCount2 = Processing.condenseArrayStr(ownerCount);
                    String irProvOwner = Processing.getMajority(ownerCount2);
                    irProvInfo.setOwner(irProvOwner);
                    
                    String[] monumentCount = Processing.countPops(irProvInfo.getPops(),"monument");
                    ArrayList<String> monumentCount2 = Processing.condenseArrayStr(monumentCount);
                    int irProvMonument = Integer.parseInt(Processing.getMajorityExcludingNoneKey(monumentCount2,"-1"));
                    irProvInfo.setMonument(irProvMonument);
                    
                    
                    String irProvCS = "settlement";
                    if (provinceTotal > 5) {
                        String[] cityStatusCount = Processing.countPops(irProvInfo.getPops(),"cityStatus");
                        ArrayList<String> cityStatusCount2 = Processing.condenseArrayStr(cityStatusCount);
                        boolean hasMetropolis = Processing.superContainsR(cityStatusCount2,"metropolis");
                        if (hasMetropolis) { //Automatically bump up a prov with a metropolis to city-status
                            irProvCS = "city";
                        } else {
                            irProvCS = Processing.getMajority(cityStatusCount2);
                        }
                    }
                    
                    if (irProvCS.equals("city") && provinceTotal >= 75) {
                        irProvCS = "metropolis";
                    }
                    irProvInfo.setCityStatus(irProvCS);
                    
                    ArrayList<Double> provCivValues = irProvInfo.getCivValues();
                    double civValue = Processing.average(provCivValues);
                    civValue = civValue / 2; //cut final value in half to align with vanilla values on setup
                    irProvInfo.setCivValue(civValue);
                    
                    //String[] typeCount = Processing.countPops(irProvInfo.getPops(),"type");
                    float[] typeRatios = Processing.getTypeRatios(irProvInfo.getPops());
                    
                    irProvInfo.replacePopsFromStringArray(relCultCount4,typeRatios,provinceTotal);
                    int irProvID = irProvInfo.getID();
                    //String[] terrainTradeGood = extraProvInfo.get(Processing.getArrayByID(extraProvInfo,irProvID));
                    int vanillaProvID = Processing.getProvByID(vanillaProvinces,irProvID);
                    Provinces vanillaProv = vanillaProvinces.get(vanillaProvID);
                    
                    irProvInfo.setTerrain(vanillaProv.getTerrain());
                    irProvInfo.setTradeGood(vanillaProv.getTradeGood());
                    
                    irProvinceList.set(aq2,irProvInfo);
                    
                    int ownerInt = Integer.parseInt(irProvOwner);
                    if (ownerInt != 9999) {
                        Country irTag = baTagInfo.get(ownerInt);
                        irTag.setHasLand(true);
                        baTagInfo.set(ownerInt,irTag);
                    }
                    //System.out.println(aq2);
                } catch (java.lang.NullPointerException exception) {
                    
                }
                aq2 = aq2 + 1;

            }
            long provinceTime = System.nanoTime();
            long provinceTimeTot = (((provinceTime - startTime) / 1000000000)/60);
            LOGGER.info("Provinces calculated after "+provinceTimeTot+" minutes");
            LOGGER.finest("35%");
            aq2 = 0;
            
            LOGGER.info("Converting characters...");
            
            int firstAvailableCharID = Characters.getAvailableID(gameFileDir);
            
            ArrayList<Characters> baCharacters = new ArrayList<Characters>();
            //int cutoffNum = 450000;
            int cutoffNum = -1;
            int emergencyCount = 5;
            boolean characterLoop = true;
            while (characterLoop && emergencyCount > 0) { //Will continously attempt with increasingly-high cutoffNum, up to emergencyCount
                try {
                    baCharacters = Characters.importChar(saveCharacters,compressedOrNot,cutoffNum);
                    characterLoop = false;
                } catch (java.lang.OutOfMemoryError exception){ //Replace likely long-dead characters with null as a fail-safe for memory-related crashes
                    LOGGER.warning("Error! Ran out of memory while converting characters, attempting to purge characters below ID "+cutoffNum+".");
                    System.out.println("Error! Ran out of memory while converting characters, attempting to purge characters below ID "+cutoffNum+".");
                    cutoffNum = cutoffNum + 450000;
                } 
                emergencyCount = emergencyCount - 1;
                if (emergencyCount <= 0) {
                    System.out.println("Warning! Emergency cutoff point reached, aborting character conversion");
                }
            }
            
            if (pruneLevel <= 5) {
                baCharacters = Processing.pruneCharactersExtreme(baCharacters,baTagInfo,pruneLevel);
            } else {
                baCharacters = Processing.pruneCharacters(baCharacters,baTagInfo,pruneMinorCharactersSetting);
            }
            
            baCharacters = Processing.divorceLongDistanceRelationships(baCharacters);
            
            baCharacters = Processing.applyNewIdsToChars(baCharacters,firstAvailableCharID);
            
            Processing.checkForIDHoles(baCharacters,firstAvailableCharID,baTagInfo);

            int dateYear = Processing.getYearFromDate(date);
            String vanillaDate = "450.10.1";
            baCharacters = Processing.adjustBirthdays(baCharacters,dateYear,450,vanillaDate);

            aq2 = 0;
            LOGGER.info("Province religion and culture calculated");
            LOGGER.info("Province data combined");
            LOGGER.finest("45%");
            LOGGER.info("Importing country data...");

            //LOGGER.config("The region is" + ck2ProvInfo[4][343]);
            int flagCount = 0;

            /////ArrayList<String[]> impTagInfo = new ArrayList<String[]>();

            long countryTime = System.nanoTime();
            long countryTimeTot = (((countryTime - startTime) / 1000000000)/60);
            LOGGER.info("Country data imported after "+countryTimeTot+" minutes");
            LOGGER.finest("55%");

            LOGGER.config("and the culture is" + ck2ProvInfo[1][343]);

            LOGGER.config("and the culture is" + ck2ProvInfo[1][1574]);
            int aq4 = 0;

            int totCountries = baTagInfo.size(); //ammount of IR countries in save file

            LOGGER.info("Importing subject data...");

            impSubjectInfo = Importer.importSubjects(saveDiplo);

            long subjectTime = System.nanoTime();
            long subjectTimeTot = (((subjectTime - startTime) / 1000000000)/60);

            LOGGER.info("Subject data imported after "+subjectTimeTot+" minutes");
            LOGGER.finest("65%");

            LOGGER.info("Copying default output...");

            //Default output, will be included in every conversion regardless of what occured in the save file
            //Output.copyRaw("defaultOutput"+VM+"cultures"+VM+"00_cultures.txt",modDirectory+VM+"common"+VM+"cultures"+VM+"00_cultures.txt");
            //Processing.customDate(date,"defaultOutput/default/bookmarks/50_customBookmark.txt",modDirectory+VM+"common/bookmarks/50_customBookmark.txt");
            String defaultOutputDir = "defaultOutput/";
            
            String thumbnailName = "thumbnail.png";
            
            try {
                Output.copySuperFast(defaultOutputDir+"/"+thumbnailName,modDirectory+"/"+thumbnailName);
                Processing.createReadMe("Readme.txt",modDirectory);
            } catch (Exception e) {
                LOGGER.warning("Warning! Unable to copy Readme.txt and "+thumbnailName);
            }
            String outputOption = "none";
            if (invictus) {
                defaultOutputDir = defaultOutputDir+hybridName+"/";
                outputOption = hybridName;
            } else {
                defaultOutputDir = defaultOutputDir+"vanilla/";
            }
            //Output.copyDefaultOutput(defaultOutputDir+"default",modDirectory,outputOption);
            Output.copyDefaultOutput(defaultOutputDir+"default",modDirectory,outputOption);
            //Processing.customDate(date,modDirectory+"/common/bookmarks/50_customBookmark.txt",modDirectory+"/common/bookmarks/50_customBookmark.txt");
            //Processing.setTechYear(date,modDirectory);
            

            long outputTime = System.nanoTime();
            long outputTimeTot = (((outputTime - startTime) / 1000000000)/60);

            LOGGER.info("defaultOutput copied after "+outputTimeTot+" minutes");
            LOGGER.finest("69%");
            
            Output.copyBAFlags(modFlagGFX,modDirectory); //Copies flag gfx
            Output.namedColorCreation(colorList,modDirectory);

            flag = 0;
            String[] Character;

            int aq7 = 0;
            String governor;
            String governorID;
            String[] governorships;
            String govReg;
            String govRegID;
            String[] govCharacter;

            impDynList = Characters.importDynasty(saveDynasty);

            //Array
            
            baCharacters = Processing.adjustCharacterCultRel (baCharacters, baTagInfo, baProvInfoList, cultureMappings, religionMappings);
            
            baCharacters = Processing.applyDynastiesToCharacters(baCharacters,impDynList);
            
            baTagInfo = Processing.applyDynastiesToCountries(baTagInfo,impDynList);
            
            aq4 = 1;
            
            int convTag = 0;
            
            //Output.genericBlankFile(modDirectory + "/setup/countries/converted_countries.txt","Converted Countries from Bronze Age");
            //Output.output(Dir2+"/game/setup/countries/countries.txt",modDirectory + "/setup/countries/countries.txt");
            //Output.output("/defaultOutput/default/setup/countries/countries.txt",modDirectory + "/setup/countries/countries.txt");

            try {
                try {
                    while (aq4 < baTagInfo.size()) {
                        //aq4 = 698;
                        //aq4 = 1294;

                        //System.out.println("Getting "+aq4);
                        Country baTag = baTagInfo.get(aq4);
                        boolean baHasLandYes = baTag.getHasLand();
                        if (baHasLandYes) {
                            convTag = convTag + 1;
                            //System.out.println("Getting "+aq4);
                            //String newTagID = Processing.genNewTag(convTag);
                            String histTag = baTag.getHistoricalTag();
                            String newTagID = Processing.convertTag(histTag,convTag,tagMappings);
                            //System.out.println("New Tag is "+newTagID);
                            //3272
                            baTag.setUpdatedTag(newTagID);
                            //System.out.println(baTag.getUpdatedTag());
                            String capital = baTag.getCapital();
                            int capInt = Integer.parseInt(capital);
                            Provinces capitalBAProv = baProvInfoList.get(capInt);
                            String capitalArea = capitalBAProv.getArea();
                            String capitalRegion = capitalBAProv.getRegion();
                            
                            ArrayList<String[]> laws = baTag.getLaws();
                            ArrayList<String[]> newLaws = Processing.updateLaws(laws,lawMappings,lawSetting);
                            baTag.setLaws(newLaws);
                            
                            String oldGovernment = baTag.getGovernment();
                            String newGovernment = Output.cultureOutput(govMap,oldGovernment);
                            baTag.setGovernment(newGovernment);
                            if (baTag.isTribal()) { //set all tribes to have 0 technology
                                baTag.setAllTech(0);
                            }
                            String newCapital = Importer.importMappingFromArray(provinceMappings,capital)[1];
                            baTag.setCapital(newCapital);
                            String[] locName = importer.importLocalisation(moddedLoc,baTag.getLoc(),"rulerDynasty");
                            baTag.setLoc(locName[0]);
                            baTag.setAdj(locName[1]);
                            Output.localizationCreation(locName,newTagID,modDirectory);
                            baTagInfo.set(aq4,baTag);
                            
                            String baTagFlag = baTag.getFlag();
                            
                            Output.generateCOA(flagList,baTagFlag,newTagID,modDirectory);
                            
                            String countryColor = baTag.getColor();
                            boolean genderEquality = baTag.hasGenderEquality();
                            Output.countrySetupCreation(countryColor,newTagID,genderEquality,modDirectory);
                            
                            ArrayList<String> countryCharacterFile = Processing.createCharFileForTag(baTag,baCharacters);
                            Output.outputBasicFile(countryCharacterFile,baTag.getUpdatedTag()+"_converted.txt",modDirectory+"/setup/characters");
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
            
            int availableCharID = Characters.getAvailableIDFromArray(baCharacters);
            ArrayList<String> characterReadme = new ArrayList<String>();
            characterReadme.add("#If you want to add your own custom character, all character ID's must be ordered 1 after another,");
            characterReadme.add("#so if Bob is ID 69, for example, Billybobjoe has to be ID 70, and so on.");
            characterReadme.add("#Additionally, parents must be defined before their children.");
            characterReadme.add("");
            characterReadme.add("#The next available ID is "+availableCharID);
            Output.outputBasicFile(characterReadme,"000_README.txt",modDirectory+"/setup/characters");

            //Output.dejureTitleCreation(impTagInfo,empireRank,duchyRank,ck2LandTot,dejureDuchies,impSubjectInfo,modDirectory);

            long titleTime = System.nanoTime();
            long titleTimeTot = (((titleTime - startTime) / 1000000000)/60);
            LOGGER.info("Titles and characters created after "+titleTimeTot+" minutes");
            LOGGER.finest("85%");
            LOGGER.info("Outputting Province info");
            
            if (!vanillaMonuments) {
                ArrayList<String> vanillaMonumentFile = Importer.importBasicFile(gameFileDir+"/setup/main/00_great_works.txt");
                vanillaMonumentFile = Processing.purgeVanillaMonuments(irProvinceList,vanillaMonumentFile);
                Output.outputBasicFile(vanillaMonumentFile,"00_great_works.txt",modDirectory+"/setup/main");

                ArrayList<String> invMonumentFile = Importer.importBasicFile(gameFileDir+"/setup/main/01_great_works_dde.txt");
                invMonumentFile = Processing.purgeVanillaMonuments(irProvinceList,invMonumentFile);
                Output.outputBasicFile(invMonumentFile,"01_great_works_dde.txt",modDirectory+"/setup/main");
            }
            
            baTagInfo = Processing.applyCRsToCountries(baTagInfo,cultureMappings,allCRs);
            
            ArrayList<String[]> exoProvinces = Importer.importExoMappings(mappingDir+"exoMappings.txt");
            irProvinceList = Processing.addExoProvinces(irProvinceList,exoProvinces,vanillaProvinces,exoCultureMappings);
            ArrayList<Country> exoCountries = Processing.generateExoCountries(irProvinceList,convTag,modDirectory,vanillaLoc,exoNames,exoColors);
            baTagInfo = Processing.appendExoCountries(baTagInfo,exoCountries);
            
            baMonumentInfo = Processing.applyMonumentLoc(baMonumentInfo,locList);
            ArrayList<String> existingCountryFile = Importer.importBasicFile(defaultOutputDir+"templates/00_default.txt");
            
            existingCountryFile = Processing.purgeVanillaSetup(irProvinceList,existingCountryFile);
            existingCountryFile = Processing.purgeVanillaBuildings(irProvinceList,buildingNames,existingCountryFile); //only for buildings in 00_default
            ArrayList<String[]> missions = Processing.getMissionTags(baTagInfo);
            Processing.updateAllMissions(gameFileDir+"/common/missions",modDirectory+"/common/missions",missions);
            Processing.updateAllMissionEvents(gameFileDir,modDirectory,missions);
            Processing.updateAllMissions(gameFileDir+"/common/scripted_effects",modDirectory+"/common/scripted_effects",missions);
            Processing.updateAllMissions(gameFileDir+"/common/scripted_triggers",modDirectory+"/common/scripted_triggers",missions);
            
            ArrayList<Diplo> exoDiplo = Processing.genExoDiplo(exoCountries,missions);
            impSubjectInfo = Processing.addNewDiplo(exoDiplo,impSubjectInfo);
            
            existingCountryFile = Processing.appendFamilies(baTagInfo,existingCountryFile);
            existingCountryFile = Processing.appendDiplo(baTagInfo,impSubjectInfo,existingCountryFile);
            
            ArrayList<String> convertedProvinces = Processing.generateProvinceFile(irProvinceList);
            Output.outputBasicFile(convertedProvinces,"01_converted_provinces.txt",modDirectory+"/setup/provinces");
            ArrayList<String> convertedTags = Processing.generateCountryFile(baTagInfo,irProvinceList,existingCountryFile);
            Output.outputBasicFile(convertedTags,"00_default.txt",modDirectory+"/setup/main");
            ArrayList<String> convertedMonuments = Processing.generateMonumentFile(irProvinceList,baMonumentInfo);
            if (moddedMonuments) {
                Output.outputBasicFile(convertedMonuments,"01_great_works_converted.txt",modDirectory+"/setup/main");
            }
            
            
            ArrayList<String> exoFlagFile = Importer.importBasicFile(defaultOutputDir+"templates/exoFlagFiles.txt");
            exoFlagFile = Processing.assignExoFlags(exoCountries,exoFlags,exoFlagFile);
            Output.outputBasicFile(exoFlagFile,"01_exo_tags.txt",modDirectory+"/common/coat_of_arms/coat_of_arms");
            
            LOGGER.finest("95%");
            
            if (convertHeritage) {
                LOGGER.info("Converting Heritages");
                heritageList = Processing.applyHeritageLoc(locList,heritageList);
                ArrayList<String> heritageFile = Processing.createHeritageFile(heritageList,baTagInfo);
                Output.outputBasicFile(heritageFile,"000_converted_heritages.txt",modDirectory+"/common/heritage/");
                ArrayList<String> heritageLoc = Processing.createHeritageLoc(heritageList);
                Output.addLocalization(heritageLoc,modDirectory+"/localization/english/converted_heritage_l_english.yml");
            }
            
            
            
            Provinces test01 = irProvinceList.get(Processing.getProvByID(irProvinceList,4957));
            Provinces test02 = irProvinceList.get(Processing.getProvByID(irProvinceList,7197));
            Provinces test03 = irProvinceList.get(Processing.getProvByID(irProvinceList,368));

            
            //Output provinces
            
            
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
