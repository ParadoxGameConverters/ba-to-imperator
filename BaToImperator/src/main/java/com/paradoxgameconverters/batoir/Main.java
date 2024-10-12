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
            directories.descriptors(Dir,irModDir,modName); //Basic .mod files required for the launcher

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

            //output.localizationBlankFile(modDirectory); //creates the country localization file
            output.coaBlankFile(modDirectory); //creates the country localization file

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
            
            ArrayList<String[]> flagList = Importer.importAllFlags(modDirs);
            LOGGER.info("Importing color information...");
            
            ArrayList<String[]> colorList = Importer.importAllColors(impGameDir,modDirs);
            
            LOGGER.info("Importing localization information...");
            
            ArrayList<String> locList = Importer.importAllLoc(impGameDir,modDirs);
            
            ArrayList<String> regionDirList = Importer.importModRegionDirs(modDirs);
            
            String regionDir = Importer.getRegionDir(impGameDir,regionDirList,modDirs);
            
            String[] provAreas = Processing.importAreas(regionDir+"map_data/areas.txt",9500);
            String[] baProvRegions = Processing.importRegionList(9500,provAreas,regionDir);
            
            ArrayList<String[]> extraProvInfo = new ArrayList<String[]>();
            
            LOGGER.info("Importing province tradegoods and terrain types...");
            
            extraProvInfo = Importer.importProvSetup(impGameDir+"/game/setup/provinces",extraProvInfo);

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
            System.out.println(baProvInfoList.get(2200).getNobles().size());
            System.out.println(baProvInfoList.get(2200).getCitizens().size());
            System.out.println(baProvInfoList.get(2200).getFreemen().size());
            System.out.println(baProvInfoList.get(2200).getTribesmen().size());
            System.out.println(baProvInfoList.get(2200).getSlaves().size());
            
            int baPopTotal = 0;//total number of pops in BA save
            totalPop = 0;
            
            ArrayList<String> cultureMappings = Importer.importBasicFile("cultureConversion.txt");
            ArrayList<String> religionMappings = Importer.importBasicFile("religionConversion.txt");
            ArrayList<String> provinceMappings = Importer.importBasicFile("provinceConversion.txt");
            
            baProvInfoList = Processing.applyRegionsToProvinces(baProvRegions,baProvInfoList);
            baProvInfoList = Processing.applyAreasToProvinces(provAreas,baProvInfoList);
            baProvInfoList = Processing.convertAllPops(baProvInfoList,cultureMappings,religionMappings);
            
            //String[] countTest = Processing.countPops(baProvInfoList.get(1974).getPops(),"cultureAndReligion");
            //ArrayList<String> countTest2 = Processing.condenseArrayStr(countTest);
            //ArrayList<String> countTest3 = Processing.convertPopCountToRatios(countTest2);
            //ArrayList<String> countTest4 = Processing.reallocatePops(countTest3,30);
            ArrayList<Provinces> irProvinceList = new ArrayList<Provinces>();//ArrayList
            Provinces blankProv = Provinces.newProv("9999","noCulture","noReligion",0,0,0,0,0,0,"settlement",0);
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
                    //
                    int createdFlag = 0;
                    int irProvListID = Processing.getProvByID(irProvinceList,ckProvNum);
                    Provinces irProv = irProvinceList.get(irProvListID);
                    if (irProvListID == 0) {
                        irProv = Provinces.newProv("9999","noCulture","noReligion",0,0,0,0,0,0,"settlement",0);
                        irProv.setID(ckProvNum);
                        irProvinceList.add(blankProv);
                        irProvListID = irProvinceList.size()-1;
                    }
                    try {
                        popTotal = baPops.size();
                        irProv.addPopArray(baPops);
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
                    if (ckProvNum == 356) {
                        System.out.println("Adding BA "+aqq+" to IR "+ ckProvNum +" (" +irProvListID+ ")");
                    }
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

                }

                if (aqq == 9843) {
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
            
            ArrayList<Country> baTagInfo = new ArrayList<Country>();
            //Country processing
            baTagInfo = importer.importCountry(saveCountries);

            LOGGER.finest("25%");

            LOGGER.info("Combining territories into provinces...");

            aq2 = 1;
            flag = 0;
            flag2 = 0;
            int aq5 = 0;
            int aq6 = 0;
            String[] irOwners;
            int globalPopTotal = 4000;//Number of pops to be redistributed

            while( aq2 < irProvinceList.size()) { // Calculate province totals
                //aq2 = Processing.getProvByID(irProvinceList,357);
                //aq2 = Processing.getProvByID(irProvinceList,549);
                //aq2 = Processing.getProvByID(irProvinceList,413);
                Provinces irProvInfo = irProvinceList.get(aq2);
                try {
                    float totalProvPops = irProvInfo.getPops().size();
                    //float provinceRatio = (totalProvPops) /baPopTotal;
                    int provinceTotal = Processing.calcAllocatedPops(baPopTotal,totalProvPops);

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
                    
                    String[] cityStatusCount = Processing.countPops(irProvInfo.getPops(),"cityStatus");
                    ArrayList<String> cityStatusCount2 = Processing.condenseArrayStr(cityStatusCount);
                    String irProvCS = Processing.getMajority(cityStatusCount2);
                    if (irProvCS.equals("city") && provinceTotal >= 75) {
                        irProvCS = "metropolis";
                    }
                    irProvInfo.setCityStatus(irProvCS);
                    
                    //String[] typeCount = Processing.countPops(irProvInfo.getPops(),"type");
                    float[] typeRatios = Processing.getTypeRatios(irProvInfo.getPops());
                    
                    irProvInfo.replacePopsFromStringArray(relCultCount4,typeRatios,provinceTotal);
                    int irProvID = irProvInfo.getID();
                    String[] terrainTradeGood = extraProvInfo.get(Processing.getArrayByID(extraProvInfo,irProvID));
                    
                    irProvInfo.setTerrain(terrainTradeGood[1]);
                    irProvInfo.setTradeGood(terrainTradeGood[2]);
                    
                    irProvinceList.set(aq2,irProvInfo);
                    
                    int ownerInt = Integer.parseInt(irProvOwner);
                    if (ownerInt != 9999) {
                        Country irTag = baTagInfo.get(ownerInt);
                        irTag.setHasLand(true);
                        baTagInfo.set(ownerInt,irTag);
                    }
                    System.out.println(aq2);
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
            LOGGER.config(ck2TagTotals[343]);

            int totCountries = baTagInfo.size(); //ammount of IR countries in save file

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
            //Processing.customDate(date,modDirectory+"/common/bookmarks/50_customBookmark.txt",modDirectory+"/common/bookmarks/50_customBookmark.txt");
            //Processing.setTechYear(date,modDirectory);
            

            long outputTime = System.nanoTime();
            long outputTimeTot = (((outputTime - startTime) / 1000000000)/60);

            LOGGER.info("defaultOutput copied after "+outputTimeTot+" minutes");
            LOGGER.finest("69%");
            
            ////Output.copyBAFlags(modFlagGFX,modDirectory); //Copies flag gfx, really slow, comment out during testing
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

            ArrayList<Characters> baCharacters = new ArrayList<Characters>();
            baCharacters = Characters.importChar(saveCharacters,compressedOrNot);

            //impDynList = Characters.importDynasty(saveDynasty);

            //Array
            
            aq4 = 1;
            
            int convTag = 0;
            
            //Output.genericBlankFile(modDirectory + "/setup/countries/converted_countries.txt","Converted Countries from Bronze Age");
            Output.output(Dir2+"/game/setup/countries/countries.txt",modDirectory + "/setup/countries/countries.txt");

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
                            System.out.println("Getting "+aq4);
                            //String newTagID = Processing.genNewTag(convTag);
                            String histTag = baTag.getHistoricalTag();
                            String newTagID = Processing.convertTag(histTag,convTag);
                            //System.out.println("New Tag is "+newTagID);
                            //3272
                            baTag.setUpdatedTag(newTagID);
                            //System.out.println(baTag.getUpdatedTag());
                            String capital = baTag.getCapital();
                            int capInt = Integer.parseInt(capital);
                            Provinces capitalBAProv = baProvInfoList.get(capInt);
                            String capitalArea = capitalBAProv.getArea();
                            String capitalRegion = capitalBAProv.getRegion();
                            
                            String oldCulture = baTag.getCulture();
                            String oldReligion = baTag.getReligion();
                            String oldGovernment = baTag.getGovernment();
                            String newCulture = Output.paramMapOutput(cultureMappings,oldCulture,oldCulture,"date",oldCulture,capitalRegion,capitalArea);
                            String newReligion = Output.cultureOutput(religionMappings,oldReligion);
                            String newGovernment = Output.cultureOutput(govMap,oldGovernment);
                            String newCapital = Importer.importMappingFromArray(provinceMappings,capital)[1];
                            if (newCulture.equals("99999")) {
                                newCulture = "roman"; //Game will crash when a country has a non-existant primary culture
                                System.out.println("Warning, culture "+oldCulture+" is unmapped, setting to 'Roman'");
                            }
                            baTag.setCulture(newCulture);
                            baTag.setReligion(newReligion);
                            baTag.setGovernment(newGovernment);
                            baTag.setCapital(newCapital);
                            String[] locName = importer.importLocalisation(locList,baTag.getLoc(),"rulerDynasty");
                            baTag.setLoc(locName[0]);
                            baTag.setAdj(locName[1]);
                            Output.localizationCreation(locName,newTagID,modDirectory);
                            baTagInfo.set(aq4,baTag);
                            
                            String baTagFlag = baTag.getFlag();
                            
                            Output.generateCOA(flagList,baTagFlag,newTagID,modDirectory);
                            
                            String countryColor = baTag.getColor();
                            Output.countrySetupCreation(countryColor,newTagID,modDirectory);
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

            //Output.dejureTitleCreation(impTagInfo,empireRank,duchyRank,ck2LandTot,dejureDuchies,impSubjectInfo,modDirectory);

            long titleTime = System.nanoTime();
            long titleTimeTot = (((titleTime - startTime) / 1000000000)/60);
            LOGGER.info("Titles and characters created after "+titleTimeTot+" minutes");
            LOGGER.finest("85%");
            LOGGER.info("Outputting Province info");
            
            //ArrayList<String> existingCountryFile = Importer.importBasicFile(impGameDir+"/game/setup/main/00_default.txt");
            ArrayList<String> existingCountryFile = Importer.importBasicFile("defaultOutput/templates/00_default.txt");
            //temporarily disabled due to a bug where certain provinces will cause crashes if uncolonized
            //existingCountryFile = Processing.purgeVanillaSetup(irProvinceList,existingCountryFile);
            
            ArrayList<String> convertedProvinces = Processing.generateProvinceFile(irProvinceList);
            Output.outputBasicFile(convertedProvinces,"01_converted_provinces.txt",modDirectory+"/setup/provinces");
            ArrayList<String> convertedTags = Processing.generateCountryFile(baTagInfo,irProvinceList,existingCountryFile);
            Output.outputBasicFile(convertedTags,"00_default.txt",modDirectory+"/setup/main");
            
            
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
