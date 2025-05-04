package com.paradoxgameconverters.batoir;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * Information which is output
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Output
{

    private int x;

    public static int output(String source, String destination) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream(source);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(destination);
        PrintWriter out = new PrintWriter(fileOut);

        String qaaa = scnr.nextLine();
        int flag = 0;
        try {
            while (flag == 0) {
                out.println(qaaa);
                qaaa = scnr.nextLine();

            }

        }catch (java.util.NoSuchElementException exception){
            flag = 1; 
            out.flush();
            fileOut.close();
        }
        return 0;
    }

        public static String cultureOutput(ArrayList<String> mappings,String irCulture) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);

        String ck2CultureInfo;   // Owner Culture Religeon PopTotal Buildings

        Importer importer = new Importer();

        //ck2CultureInfo = importer.importCultList("cultureConversion.txt",irCulture)[1];
        ck2CultureInfo = Importer.importMappingFromArray(mappings,irCulture)[1];

        return ck2CultureInfo;
    }

    public static String paramMapOutput(ArrayList<String> mappings,String ck2Culture,String tagCulture,String date,String source,String region,
    String area,String religion,String tag) throws IOException //mapping with parameters
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        int count = 0;

        String[] ck2ReligionInfo;
        String ck2Religion = "roman"; //default in case there's no mapping

        Importer importer = new Importer();

        ArrayList<String[]> validMappings = Importer.importMappingFromArrayArgs(mappings,source);
        
        while (count < validMappings.size()) {
            ck2ReligionInfo = validMappings.get(count);
            //System.out.println(ck2ReligionInfo[0]+"-A"+ck2ReligionInfo.length);
            if (ck2ReligionInfo.length == 2) {
                //System.out.println(ck2ReligionInfo[0]+" "+ck2ReligionInfo[1]+"-B");
                ck2Religion = ck2ReligionInfo[1];
                return ck2Religion;
            }
            else {
                int numArgs = ck2ReligionInfo.length-2;
                int count2 = 0;
                boolean passedCheck = true;
                while (count2 < numArgs) { //check all arguments, if all are true, return culture
                    //System.out.println(numArgs);
                    int argBeingChecked = count2+2;
                    String[] relArgument = ck2ReligionInfo[argBeingChecked].split("=");
                    if (relArgument[0].equals("culture")) {
                        //System.out.println(relArgument[1]+" "+ck2Culture);
                        if (!ck2Culture.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("tagCulture")) {
                        //System.out.println(relArgument[1]+" "+tagCulture);
                        if (!tagCulture.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("notTagCulture")) {
                        //System.out.println(relArgument[1]+" "+tagCulture);
                        if (tagCulture.equals(relArgument[1]) || tagCulture.equals("none")) { //catch uncolonized provinces
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("year")) {
                        //System.out.println(relArgument[1]+" "+date);
                        String tmpDate = date.replace(".",",");
                        int saveYearInt = Integer.parseInt(tmpDate.split(",")[0]);
                        int argumentYear = Integer.parseInt(relArgument[1]);
                        if (saveYearInt < argumentYear) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("region")) {
                        if (!region.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("area")) {
                        if (!area.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("religion")) {
                        if (!religion.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else if (relArgument[0].equals("tag")) {
                        if (!tag.equals(relArgument[1])) {
                            count2 = count2 + numArgs;
                            passedCheck = false;
                        }
                    }
                    else { //if argument is invalid, nullify whole mapping
                       count2 = count2 + numArgs;
                       passedCheck = false; 
                    }
                    
                    count2 = count2+1;
                }
                if (passedCheck == true) {
                    ck2Religion = ck2ReligionInfo[1];
                    return ck2Religion;
                }
                
            }
            count = count + 1;
            
        }
        
        //System.out.println("Something went wrong, "+irRel+" will convert as noculture");
        return ck2Religion;
    }

    public static String titleCreationCommon(String irTAG, String irColor, String government,String capital,String rank, String Directory) throws IOException
    {

        String tab = "	";
        String VM = "\\"; 
        VM = VM.substring(0);
        Directory = Directory + VM + "common" + VM + "landed_titles";
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + rank+"_" + irTAG + "_LandedTitle.txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println (rank+"_"+irTAG+" = {");
        if (!irColor.equals("none")) {
            out.println (tab+"color={ "+irColor+" }");
            out.println (tab+"color2={ "+irColor+" }");
        }

        if (!capital.equals("none")) { //governorships don't have set capitals

            capital = Importer.importConvList("provinceConversion.txt",Integer.parseInt(capital))[1];

            out.println (tab+"capital = "+capital);

        }
        if ( government.equals("republic") ) {
            out.println (tab+tab+tab+"is_republic = yes"); //if it is a republic and republics are enabled  
        } else if (government.equals("imperium") && rank.equals("e")) {
            out.println (tab+"purple_born_heirs = yes"); //if government is imperial, enable born in purple mechanic
            out.println (tab+"has_top_de_jure_capital = yes");
        }
        out.println ("}");

        out.flush();
        fileOut.close();

        return irColor;
    }

    public static String provinceCreation(String ckProv, String ckCult, String ckRel, String Directory, String landType, 
    String name, String gov, String pops, String[] bList, String saveMonuments, String republicOption, int id) throws IOException
    {

        String tab = "	";
        String VM = "\\"; 
        VM = VM.substring(0);
        String bracket1 = "{{"; 
        bracket1 = bracket1.substring(0);
        Directory = Directory + VM + "history" + VM + "provinces";

        FileOutputStream fileOut= new FileOutputStream(Directory + VM + ckProv + " - " + name + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        String[] barony = bList[id].split(",");

        name = name.toLowerCase();

        name = name.replace(' ','_');

        if (id == 103) { //Leon in Brittany and Spain have the same name in definition.csv
            name = "french_leon";  
        } else if (id == 1955) {
            name = "hy_many";  
        } else if (id == 1966) {
            name = "aurillac";  
        } else if (id == 1781) {
            name = "alqusair";  
        } else if (id == 722) {
            name = "al_aqabah";  
        } else if (id == 1379) {
            name = "asayita";  
        } else if (id == 1234) {
            name = "damin_i_koh";  
        } else if (id == 254) {
            name = "wurzburg";  
        } else if (id == 446) {
            name = "znojmo";  
        } else if (id == 715) {
            name = "zanjan_abhar";  
        } else if (id == 242) {
            name = "aargau";  
        } else if (id == 355) {
            name = "padova";  
        } else if (id == 1949) {
            name = "anglesey";  
        } else if (id == 935) {
            //name = "amalfi";  
        }

        String holding1 = "castle";
        String holding2 = "city";
        String holding3 = "temple";
        boolean convRepublic = false;
        if (republicOption.equals("repMer") || republicOption.equals("repRep")) {
            convRepublic = true;
        }

        if (gov.equals ("tribal")) {
            holding1 = "tribal";
            holding2 = "tribal";
            holding3 = "tribal";
        }
        else if (gov.equals ("republic") && convRepublic == true) { //If republic, primary holding becomes city instead of castle
            holding1 = "city";   
            holding2 = "castle";   
        }


        int popNum = Integer.parseInt(pops);
        int holdingTot = 1;

        if (popNum <= 15) {
            holdingTot = 1;  
        } 
        else if (popNum <= 40) {
            holdingTot = 2;    
        }
        else if (popNum <= 60) {
            holdingTot = 3;    
        }
        else if (popNum <= 85) {
            holdingTot = 4;   
        }
        else if (popNum <= 100) {
            holdingTot = 5;   
        }
        else if (popNum <= 300) {
            holdingTot = 6;   
        }
        else if (popNum >= 500) {
            holdingTot = 7;  
        }

        out.println ("# County Title");
        out.println ("title = c_"+name);
        out.println ("");

        out.println ("# Settlements");
        out.println ("max_settlements = "+holdingTot);
        out.println ("b_"+barony[0]+" = "+holding1);

        if (popNum >= 30) {
            out.println ("b_"+barony[1]+" = "+holding2);  
        } 
        if (popNum >= 80) {
            out.println ("b_"+barony[2]+" = "+holding3);    
        }
        if (popNum >= 120) {
            out.println ("b_"+barony[3]+" = "+holding2);  
        }
        if (popNum >= 170) {
            out.println ("b_"+barony[4]+" = "+holding2);
        }
        if (popNum >= 600) {
            out.println ("b_"+barony[5]+" = "+holding2);
        }
        if (popNum <= 1000) {
            out.println ("b_"+barony[6]+" = "+holding2);  
        }
        out.println ("");

        out.println ("# Misc");
        out.println ("culture = "+ckCult);
        out.println ("religion = "+ckRel);
        if (landType.equals ("plains")){ 
        }
        else {
            out.println (landType);
        }

        if (id == 23) { //Stonehenge in pre-2.0 saves
            if (Processing.checkMonumentList(saveMonuments) == 0 || 1 == 1) {//if 0, it is an old save before dynamic/custom monuments
                out.println ("");
                out.println ("# History");
                out.println ("1.1.1 = {");
                out.println (tab+"build_wonder = wonder_pagan_stones_stonehenge");
                out.println (tab+"set_wonder_stage = 3");
                out.println (tab+"set_wonder_damaged = yes");
                out.println ("}");
            }
        }

        if (id == 800) { //The great pyramids of Giza in pre-2.0 saves
            if (Processing.checkMonumentList(saveMonuments) == 0 || 1 == 1) {//if 0, it is an old save before dynamic/custom monuments
                out.println ("");
                out.println ("# History");
                out.println ("1.1.1 = {");
                out.println (tab+"build_wonder = wonder_pyramid_giza");
                out.println (tab+"set_wonder_stage = 3");
                out.println (tab+"build_wonder_upgrade = upgrade_mythological_beast");
                out.println ("}");
            }
        }

        out.flush();
        fileOut.close();

        return ckProv;
    }

    public static String dynastyCreation(String name, String id, String backupName, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        if (name.split(" ")[0].equals ("minor")) {
            name = backupName;
        }

        if (backupName.equals("debug")) { //gives all IR character dynasties + 700000000 to prevent conflict, generated ones (debug) use + 6000000
        } else {

            id = Processing.calcDynID(id);
        }

        Directory = Directory + VM + "common" + VM + "dynasties";
        String ck2CultureInfo ="a";   // debug output
        FileOutputStream fileOut= new FileOutputStream(Directory + VM + "c_" + id + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;

        String date1 = "100.1.1";
        String date2 = "1066.9.15";

        out.println (id+"=");
        out.println ("{");
        out.println (tab+"name="+VMq+name+VMq);
        out.println (tab+"used_for_random=no");
        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static void localizationCreation(String[] name, String title, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char quote = '"';

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importModLocalisation(Directory);

        Directory = Directory + "/localization/english";

        FileOutputStream fileOut= new FileOutputStream(Directory + "/converted_countries_l_english.yml");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        int aqq = 0;

        try {

            while (flag == 0) {
                out.println (oldFile.get(aqq));
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            flag = 1;

        } 

        out.println (title+":0 "+quote+name[0]+quote);
        out.println (title+"_ADJ:0 "+quote+name[1]+quote);
        out.flush();
        fileOut.close();
    }
    
    public static String namedColorCreation(ArrayList<String[]> colors, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char quote = '"';
        String tab = "	";

        Directory = Directory + "/common/named_colors";
        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings

        FileOutputStream fileOut= new FileOutputStream(Directory + "/converted_ba_colors.txt");
        PrintWriter out = new PrintWriter(fileOut);
        int count = 1;
        out.println ("#Colors converted from Bronze Age");
        out.println ("colors = {");

        try {

            while (colors.size() > count) {
                String[] colorDef = colors.get(count);
                int count2 = 0;
                //String colorName = colorDef[0]+"_converted_ba";
                String colorName = colorDef[0];
                String colorValue = colorDef[1];
                String colorType = colorValue.split(",")[0]; //HSV or RGB
                colorValue = colorValue.split(",")[1];
                out.println (tab+colorName+" = "+colorType+" { "+colorValue+" }");
                count = count + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){

        } 

        out.println ("}");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }
    
    public static void generateCOA(ArrayList<String[]> flagList, String flagIDTag, String updatedTag, String Directory) throws IOException
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char quote = '"';

        ArrayList<String> oldFile = new ArrayList<String>();

        Directory = Directory + "/common/coat_of_arms/coat_of_arms";
        
        oldFile = Importer.importBasicFile(Directory + "/00_converted_ba_countries.txt");

        FileOutputStream fileOut= new FileOutputStream(Directory + "/00_converted_ba_countries.txt");
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        int aqq = 0;

        try {

            while (flag == 0) {
                out.println (oldFile.get(aqq));
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            flag = 1;
            int count = 0;
            while (flagList.size() > count) {
                String[] flagInfo = flagList.get(count);
                if (flagInfo[0].equals(flagIDTag)) {
                    int count2 = 0;
                    count = flagList.size() + 1;
                    out.println(updatedTag+" = {");
                    String[] flagDef = flagInfo[1].split(",");
                    while (count2 < flagDef.length) {
                        out.println(flagDef[count2]);
                        count2 = count2+1;
                    }
                }
                count = count + 1;

            }
            

        } 

        //out.println (title+":0"+quote+name[0]+quote);
        //out.println (title+"_ADJ:0"+quote+name[1]+quote);
        out.flush();
        fileOut.close();

    }

    public static String localizationBlankFile(String Directory) throws IOException
    {

        Directory = Directory + "/localization";
        String ck2CultureInfo ="a";   // blank default
        FileOutputStream fileOut= new FileOutputStream(Directory + "/english/converted_countries_l_english.yml");
        PrintWriter out = new PrintWriter(fileOut);

        out.println ("#Localization for all converted BA countries");
        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }
    
    public static void coaBlankFile(String Directory) throws IOException
    {

        Directory = Directory + "/common/coat_of_arms/coat_of_arms";
        FileOutputStream fileOut= new FileOutputStream(Directory + "/00_converted_ba_countries.txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println ("#Flags for all converted BA countries");
        out.flush();
        fileOut.close();

    }
    
    public static void genericBlankFile(String Directory, String comment) throws IOException
    {
        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        out.println ("#"+comment);
        out.flush();
        fileOut.close();

    }

    public static String govCreation(String title, String rank, String govFile, String Directory) throws IOException 
    //needed to allow TAGs imperial government and merchant republic government
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        if (govFile.equals("i")) { //for convienience
            govFile = "imperial_governments.txt";
        } else if (govFile.equals("m")) {
            govFile = "merchant_republic_governments.txt";
        }

        Directory = Directory + VM + "common" + VM + "governments" + VM + govFile;

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            out.println (oldFile.get(aqq));
            String key = oldFile.get(aqq).replace(tab,"");
            key = key.replace("#","");
            if (key.equals("title = e_TAG")) {
                String imperialTag = oldFile.get(aqq).replace("#","");
                imperialTag = imperialTag.replace("e_TAG",rank+"_"+title);
                out.println (imperialTag);
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static String imperialSuccession(String title, String rank, String Directory) throws IOException //needed to allow TAGs imperial laws
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        Directory = Directory + VM + "common" + VM + "laws" + VM + "succession_laws.txt";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        String ck2CultureInfo ="a";   // Owner Culture Religeon PopTotal Buildings
        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            out.println (oldFile.get(aqq));
            if (oldFile.get(aqq).contains("e_TAG")) {
                String imperialTag = oldFile.get(aqq).replace("e_TAG",rank+"_"+title);
                imperialTag = imperialTag.replace("#","");
                out.println (imperialTag);
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return ck2CultureInfo;
    }

    public static void copyRaw(String dir1, String dir2) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);

        FileInputStream fileIn= new FileInputStream(dir1);
        FileOutputStream fileOut= new FileOutputStream(dir2);

        boolean endOrNot = true;
        int aqq = 0;

        try {
            while (endOrNot = true && aqq != -1){
                if (aqq != -1) {
                    aqq = fileIn.read();
                    fileOut.write(aqq);
                }

            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;
            fileIn.close();
            fileOut.close();

        }   

    }
    
    public static void copySuperFast(String dir1, String dir2) throws IOException
    {

        File inputFile = new File(dir1);
        File outputFile = new File(dir2);
        Path inputPath = inputFile.toPath();
        Path outputPath = outputFile.toPath();
        Files.copy(inputPath,outputPath);

    }

    public static void copyFlag(String ck2Dir, String modDirectory, String rank, String prov, String tag) throws IOException //copies flag files
    {

        if (!tag.contains("dynamic") && !tag.contains("__")) { //if the tag is dynamically generated or is governorship, already uses CK II province ID
            prov = Importer.importConvList("provinceConversion.txt",Integer.parseInt(prov))[1];
        }

        prov = Processing.importNames("a",Integer.parseInt(prov),ck2Dir)[0];
        prov = Processing.formatProvName(prov);
        try {
            Output.copyRaw(ck2Dir+"/gfx/flags/c_"+prov+".tga",modDirectory+"/gfx/flags/"+rank+"_"+tag+".tga");
        }catch (java.io.FileNotFoundException exception) { //if flag cannot be found, will use default one
            Output.copyRaw("defaultOutput/flagDev/c_default.tga",modDirectory+"/gfx/flags/"+rank+"_"+tag+".tga");
        }

    }
    
    public static void copyBAFlags(ArrayList<String> baFlags,String outputDir) throws IOException //copies flag files
    {

        int count = 1;
        while (count < baFlags.size()) {
            String emblemDir = baFlags.get(count);
            //String updatedEmblem = emblem.replace(".tga","_ba_converted.tga");
            //updatedEmblem = updatedEmblem.replace(".tga","_ba_converted.tga");
            try {
                int length = emblemDir.length();
                String emblem = emblemDir.split("colored_emblems/")[1];
                String updatedEmblem = emblem.replace(".tga","_ba_converted.tga");
                updatedEmblem = updatedEmblem.replace(".dds","_ba_converted.dds");
                String outputFile = outputDir+"/gfx/coat_of_arms/colored_emblems/"+emblem;
                copySuperFast(emblemDir,outputFile);
            }catch (Exception e) { //if flag cannot be found, will ignore
                //System.out.println("Error with copying "+emblemDir+", skipping image");
            }
            count = count + 1;
        }

    }

    //creates I:R flag
    public static int generateFlag(String ck2Dir, String irDirectory, String rank, ArrayList<String[]> flagList, String tag, String flagID,
    ArrayList<String[]> colorList, ArrayList<String> flagGFXList, String modDirectory) throws IOException
    {
        int flagCreated = 0; //if flag is successfully created, change to 1
        //output[2] format = hsvOrRgb,r g b
        //output[3] format = hsvOrRgb,r g b
        // output[4] format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        int aqq = 1;
        int flag = 0;
        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(flagID)) {
                flag = 1; //end loop
                String[] flagSource = flagList.get(aqq);
                String ck2Tag = rank+"_"+tag;
                if (flagSource[1].equals("pattern_horizontal_split_02.tga")) {
                    flagSource[1] = "pattern_horizontal_split_01.tga";
                } else if (flagSource[1].equals("pattern_horizontal_split_01.tga")) {
                    flagSource[1] = "pattern_horizontal_split_02.tga";
                }
                String pattern = irDirectory + "/game/gfx/coat_of_arms/patterns/" + flagSource[1];
                String color1 = flagSource[2];
                String color2 = flagSource[3];
                String emblems = flagSource[4];

                int aq3 = 0;
                while (aq3 < flagGFXList.size()) {
                    if (flagGFXList.get(aq3).contains(flagSource[1])) {
                        pattern = flagGFXList.get(aq3);
                        aq3 = flagGFXList.size();
                    }
                    aq3 = aq3 + 1;
                }

                color1 = getColor(color1,colorList);
                color2 = getColor(color2,colorList);

                String devFlagName = "defaultOutput/flagDev/"+ck2Tag+"Dev.gif";
                String flagName = modDirectory+"/gfx/flags/"+ck2Tag+".tga";

                irFlagBackground(pattern,devFlagName,color1,color2);

                String[] emblemList = emblems.split("~~");
                int aq2 = 0;
                while (aq2 < emblemList.length) {
                    String[] emblem = emblemList[aq2].split("~_~");
                    String eTexture = irDirectory+"/game/gfx/coat_of_arms/colored_emblems/"+emblem[0];
                    String eColor1 = emblem[1];
                    String eColor2 = emblem[2];
                    String eScale = emblem[3];
                    String ePos = emblem[4];
                    String eRot= emblem[5];
                    String eNameOld = "defaultOutput/flagDev/emblem"+aq2+"Old"+".gif";
                    String eName = "defaultOutput/flagDev/emblem"+aq2+".gif";

                    int aq4 = 0;
                    while (aq4 < flagGFXList.size()) {
                        if (flagGFXList.get(aq4).contains(emblem[0])) {
                            eTexture = flagGFXList.get(aq4);
                            aq4 = flagGFXList.size();
                        }
                        aq4 = aq4 + 1;
                    }

                    eColor1 = getColor(eColor1,colorList);
                    if (!eColor2.equals("none")) {
                        eColor2 = getColor(eColor2,colorList);
                    }

                    irFlagEmblem(eTexture,eNameOld,eColor1,eColor2,eName,eScale,eRot,ePos,devFlagName);
                    aq2 = aq2+1;
                }
                irFlagScaleExact(devFlagName,flagName,"128","128"); //create final .tga flag, scale to CK2 128x128
                File flagCheck = new File (flagName);//Check if flag exists
                flagCreated = 1; //Flag has been created
            }

            aqq = aqq + 1;

        }
        return flagCreated;

    }

    public static void irFlagEmblem(String eTexture,String eNameOld,String eColor1,String eColor2,String eName,String eScale,
    String eRot,String ePos,String devFlagName) throws IOException //generates and applies emblem to flag
    {
        //eTexture is source emblem gfx file
        //eNameOld is modified gfx file potentially not 256x256 (needs to be a separate image from eName due to IM quirks)
        //eName is modified gfx file set to 256x256, ready to be scaled, rotated, or positioned
        //devFlagName is the name of the combined development .gif flag
        if (!eTexture.contains("gfx/coat_of_arms/textured_emblems/")) { //if not a textured emblem, colorize emblem
            irFlagBackground(eTexture,eNameOld,eColor1,eColor2);
            irFlagScaleExact(eNameOld,eName,"256","256"); //set's size to 256 x 256
        } else { //if a textured emblem, don't colorize
            irFlagScaleExact(eTexture,eName,"256","256"); //set's size to 256 x 256
        }

        if (!eScale.equals("none")) {
            irFlagScale(eName,eScale);
        }

        if (!eRot.equals("none")) {
            irFlagRotate(eName,eRot);
        }
        if (!ePos.equals("none")) {
            irFlagPos(eName,ePos);
        }

        irFlagCombine(devFlagName,eName,devFlagName); //merge emblem into dev flag
    }

    public static void irFlagBackground(String oldName, String name, String color, String color2) throws IOException
    {
        irFlagColor(name,oldName,color,"1");
        if (!color2.equals("none") && !oldName.contains("pattern_solid.tga")) {
            String layer2Name = name.replace(".gif","layer2.gif");
            layer2Name = layer2Name.replace(".tga","layer2.gif");
            layer2Name = layer2Name.replace(".dds","layer2.gif");
            irFlagColor(layer2Name,oldName,"none","1");
            irFlagColor(layer2Name,layer2Name,color2,"2");
            irFlagCombine(name,layer2Name,name);
        }
    }

    public static void irFlagColor(String name, String oldName, String color, String oneOrTwo) throws IOException
    {
        String replaceColor = oneOrTwo;
        if (oneOrTwo.equals("1")) {
            replaceColor = "red";
        }
        else if (oneOrTwo.equals("2")) {
            replaceColor = "yellow";
        }
        String[] rakalyCommand = new String [10];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = oldName;
        rakalyCommand[3] = "-fuzz";
        rakalyCommand[4] = "40%";
        rakalyCommand[5] = "-fill";
        rakalyCommand[6] = color;
        rakalyCommand[7] = "-opaque";
        rakalyCommand[8] = replaceColor;
        rakalyCommand[9] = name;
        Processing.fileExcecute(rakalyCommand);
    }

    public static String irFlagScale(String name, String percent) throws IOException
    {

        percent = percent.replace("  "," ");
        String[] numbers = percent.split(" ");
        double scaleNum1 = Double.parseDouble(numbers[0]) * 256;
        double scaleNum2 = Double.parseDouble(numbers[1]) * 256;
        if (scaleNum1 < 0) {
            scaleNum1 = scaleNum1 * -1;
            irFlagFlip(name,name,"x");
        }
        if (scaleNum2 < 0) {
            scaleNum2 = scaleNum2 * -1;
            irFlagFlip(name,name,"y");
        }

        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-resize";
        rakalyCommand[4] = scaleNum1 + "x" + scaleNum2 + "!";
        rakalyCommand[5] = "-quality";
        rakalyCommand[6] = "92";
        rakalyCommand[7] = name;
        Processing.fileExcecute(rakalyCommand);
        irFlagCanvas(name,"256","256");
        return scaleNum1 + "x" + scaleNum2;
    }

    public static void irFlagScaleExact(String oldName,String newName, String dim1, String dim2) throws IOException //scales based on exact dimensions
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = oldName;
        rakalyCommand[3] = "-resize";
        rakalyCommand[4] = dim1 + "x" + dim2 + "!";
        rakalyCommand[5] = "-quality";
        rakalyCommand[6] = "92";
        rakalyCommand[7] = newName;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagCanvas(String name,String dim1,String dim2) throws IOException //set's the canvas
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-gravity";
        rakalyCommand[4] = "center";
        rakalyCommand[5] = "-extent";
        rakalyCommand[6] = dim1+"x"+dim2;
        rakalyCommand[7] = name;
        Processing.fileExcecute(rakalyCommand);

    }

    public static void irFlagRotate(String name, String degrees) throws IOException
    {
        String[] rakalyCommand = new String [11];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-background";
        rakalyCommand[4] = "none";
        rakalyCommand[5] = "-virtual-pixel";
        rakalyCommand[6] = "background";
        rakalyCommand[7] = "-distort";
        rakalyCommand[8] = "ScaleRotateTranslate";
        rakalyCommand[9] = degrees;
        rakalyCommand[10] = name;
        Processing.fileExcecute(rakalyCommand);
    }

    public static String irFlagPos(String name, String position) throws IOException
    {
        String[] numbers = position.split(" ");
        if (numbers.length < 2) { //in case flag has broken formatting (OEO's 0.5340.5, for example)
            //numbers[0] = position.split(".")[0] + "." + position.split(".")[1];
            return "Malformed position data " + position;
        }
        int posNumX = (int)(Double.parseDouble(numbers[0]) * 256);
        int posNumY = (int)(Double.parseDouble(numbers[1]) * 256);
        String posXY = posNumX+","+posNumY;
        String test = "'128,128 "+posXY+"'";

        String[] rakalyCommand = new String [9];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = name;
        rakalyCommand[3] = "-virtual-pixel";
        rakalyCommand[4] = "none";
        rakalyCommand[5] = "-distort";
        rakalyCommand[6] = "Affine";
        rakalyCommand[7] = "128,128 "+posXY;
        rakalyCommand[8] = name;
        Processing.fileExcecute(rakalyCommand);

        return test;
    }

    public static void irFlagCombine(String background, String emblem, String product) throws IOException //combined test
    {
        String[] rakalyCommand = new String [7];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "composite";
        rakalyCommand[2] = "-gravity";
        rakalyCommand[3] = "center";
        rakalyCommand[4] = emblem;
        rakalyCommand[5] = background;
        rakalyCommand[6] = product;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagFlip(String background, String product, String dim) throws IOException //combined test
    {
        String flipOrFlop = "-flop"; //flop for x, flip for y
        if (dim.equals("y")) {
            flipOrFlop = "-flip";
        }
        String[] rakalyCommand = new String [4];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = background;
        rakalyCommand[2] = flipOrFlop;
        rakalyCommand[3] = product;
        Processing.fileExcecute(rakalyCommand);
    }

    public static void irFlagHue(String background, String hue, String product) throws IOException //combined test
    {
        String[] rakalyCommand = new String [6];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = background;
        rakalyCommand[3] = "-modulate";
        rakalyCommand[4] = hue;
        rakalyCommand[5] = product;
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagShadow(String shadow, String devFlagName) throws IOException //gives image a shadow, doesn't work with .gif files!
    {
        String[] rakalyCommand = new String [6];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-shadow";
        rakalyCommand[4] = "70x15+60+60";
        rakalyCommand[5] = shadow;
        Processing.fileExcecute(rakalyCommand);
        irFlagCombine(shadow,devFlagName,shadow);
    }
    
    public static void irFlagCombineCutout(String devFlagName,String mask,String overlay,String output) throws IOException
    //Cuts out parts of the flag based on the mask, then fills those parts back in with the overlay
    {
        String[] rakalyCommand = new String [18];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-alpha";
        rakalyCommand[4] = "set";
        rakalyCommand[5] = "-gravity";
        rakalyCommand[6] = "center";
        rakalyCommand[7] = "-extent";
        rakalyCommand[8] = "256x256";
        rakalyCommand[9] = mask;
        rakalyCommand[10] = "-compose";
        rakalyCommand[11] = "DstIn";
        rakalyCommand[12] = "-composite";
        rakalyCommand[13] = overlay;
        rakalyCommand[14] = "-compose";
        rakalyCommand[15] = "Over";
        rakalyCommand[16] = "-composite";
        rakalyCommand[17] = output;
        
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagTexture(String devFlagName, String texture, String output) throws IOException //applies texture to image
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "composite";
        rakalyCommand[2] = texture;
        rakalyCommand[3] = devFlagName;
        rakalyCommand[4] = "-tile";
        rakalyCommand[5] = "-compose";
        rakalyCommand[6] = "Hardlight";
        rakalyCommand[7] = output;
        
        Processing.fileExcecute(rakalyCommand);
    }
    
    public static void irFlagGlow(String shadow, String devFlagName, String color, String intensity) throws IOException //combined test
    {
        String[] rakalyCommand = new String [8];
        rakalyCommand[0] = "magick.exe";
        rakalyCommand[1] = "convert";
        rakalyCommand[2] = devFlagName;
        rakalyCommand[3] = "-background";
        rakalyCommand[4] = color;
        rakalyCommand[5] = "-shadow";
        rakalyCommand[6] = "1000x"+intensity+"+60+60";
        rakalyCommand[7] = shadow;
        Processing.fileExcecute(rakalyCommand);
        Output.irFlagCombine(shadow,devFlagName,shadow);
    }

    public static String getColor(String colorName,ArrayList<String[]> colorList) throws IOException
    //get's and converts I:R color to correct format
    {

        int aqq = 0;
        int flag = 0;
        while (aqq < colorList.size() && flag == 0) {
            String color = colorName;
            if (colorList.get(aqq)[0].equals(colorName)) {
                flag = 1; //end loop
                color = colorList.get(aqq)[1];
                color = color.replace("  "," ");
            }
            if (color.split(",")[0].equals("rgb")) {
                color = color.split(",")[1];
                color = Processing.fixWhite(color);
                //Pure white(255,255,255) messes with image scaling, turning the entire emblem white. Changing it to 254,254,254 fixes the issue
                color = "rgb(" + color.replace(" ",",") + ")";
                return color;
            }
            if (color.split(",")[0].equals("hsv")) {
                color = color.split(",")[1];
                color = Processing.deriveRgbFromHsv(color);
                color = Processing.fixWhite(color);
                //Pure white(255,255,255) messes with image scaling, turning the entire emblem white. Changing it to 254,254,254 fixes the issue
                color = "rgb(" + color.replace(" ",",") + ")";
                return color;
            }
            aqq = aqq + 1;
        }
        //colorName = "252 99 225"; //default in case there is no color
        return colorName;

    }

    public static void copyDefaultOutput (String defDir, String outputDir, String option) throws IOException
    {
        ArrayList<String[]> allColors = new ArrayList<String[]>();
        ArrayList<String[]> vanillaColors = new ArrayList<String[]>();
        File fileInfo = new File (defDir);
        String[] fileList = fileInfo.list();

        if (fileList != null) {
            int aqq = 0;
            while (aqq < fileList.length) {
                copyDefaultOutput(defDir+"/"+fileList[aqq],outputDir,option);
                aqq = aqq + 1;
            }

        } else {
            String fileName = fileInfo.getPath();
            int pathLength = 21;
            if (!option.equals("none")) {
                int optionLength = option.length() + 2;
                pathLength = pathLength + 9;
            }
            String[] fileNameSplit = fileName.split("default");
            String newFileName = fileNameSplit[fileNameSplit.length-1];
            //String newFileName = fileName.substring(pathLength,fileName.length());
            newFileName = outputDir + newFileName;
            String folder = fileInfo.getParent();
            //System.out.println(outputDir+folder);
            //try {
            //folder = outputDir + folder.substring(21,folder.length());
            ///folder = outputDir + folder.substring(pathLength,folder.length());
            String[] folderSplit = folder.split("default");
            folder = outputDir+folderSplit[folderSplit.length-1];
            System.out.println(folder+" A");
            //} catch (Exception e) {
                
            //}
            File folderFile = new File (folder);
            if (!folderFile.exists()) { //if folders don't exist, make them
                folderFile.mkdirs();
            }
            if (defDir.contains ("common/cultures") || defDir.contains ("common/religions") || defDir.contains ("common/dynasties")
            || defDir.contains ("/gfx") || defDir.contains ("/localisation"))  {
                copyRaw(fileName,newFileName);
            }
            else {
                output(fileName,newFileName);
            }
        }
    }

    public static void dynamicSplitTemplateFill(String country, String[] loc, String[] easternEmpire, String[] westernEmpire, String eastTitle,
    String westTitle, String capital, String tagCulture, String outputDirectory, String templateDirectory) throws IOException
    //dynamic east/west empire split
    {

        ArrayList<String> oldFile = new ArrayList<String>();
        oldFile = Importer.importBasicFile(templateDirectory);
        FileOutputStream fileOut= new FileOutputStream(outputDirectory);
        PrintWriter out = new PrintWriter(fileOut);

        String empName = loc[0];
        if (!empName.contains("Empire")) {
            empName = loc[1] + " Empire";
        }
        String adjName = loc[1];

        int aqq = 0;

        while (aqq < oldFile.size()) {
            if (oldFile.get(aqq).contains("e_TAG") || oldFile.get(aqq).contains("tagEmpireName") || oldFile.get(aqq).contains("eastEmpireName")
            || oldFile.get(aqq).contains("westEmpireName") || oldFile.get(aqq).contains("TAG_capital") || oldFile.get(aqq).contains("TAGEmpireAdj")
            || oldFile.get(aqq).contains("TAGCulture")) {
                String imperialTag = oldFile.get(aqq).replace("e_TAG",country);
                imperialTag = imperialTag.replace("tagEmpireName",empName);
                imperialTag = imperialTag.replace("eastEmpireName",easternEmpire[0]);
                imperialTag = imperialTag.replace("westEmpireName",westernEmpire[0]);
                imperialTag = imperialTag.replace(country+"_east","e_"+eastTitle);
                imperialTag = imperialTag.replace(country+"_west","e_"+westTitle);
                imperialTag = imperialTag.replace("TAG_capital",capital);
                imperialTag = imperialTag.replace("TAGEmpireAdj",adjName);
                imperialTag = imperialTag.replace("TAGCulture",tagCulture);
                out.println (imperialTag);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;
        }
        out.flush();
        fileOut.close();
    }

    public static void eastWestTitle(String irTAG, String government, String capital,String rank,
    String date1,String Directory) throws IOException
    {

        String tab = "	";

        String oldDirectory = Directory;
        Directory = Directory+"/history/titles";
        Importer importer = new Importer();

        FileOutputStream fileOut= new FileOutputStream(Directory + "/" + rank+"_" + irTAG + ".txt");
        PrintWriter out = new PrintWriter(fileOut);

        out.println (date1+"={");
        out.println (tab+"active = no");
        if (government.equals("imperium") && rank.equals("e")) { //If I:R government is imperial, set government to CK II imperial (roman_imperial_government)
            out.println (tab+"law = crown_authority_2");
            out.println (tab+"law = succ_byzantine_elective");
            out.println (tab+"law = centralization_3");
            out.println (tab+"law = imperial_administration");
            out.println (tab+"law = ze_administration_laws_2");
            out.println (tab+"law = vice_royalty_2");
            out.println (tab+"law = revoke_title_law_1");
            govCreation(irTAG,rank,"i",oldDirectory);
            imperialSuccession(irTAG,rank,oldDirectory);
        }
        out.println ("}");
        out.println ();
        out.flush();
        fileOut.close();
    }

    public static void eastWestFlagGen(String irFlag, String title, String color, String eastColor, String eastTitle,ArrayList<String[]> flagList,
    ArrayList<String[]> colorList,String rank,String capital,ArrayList<String> modFlagGFX,String useRatio,String ck2Dir,String impGameDir,String modDirectory)
    throws IOException
    {

        int genFlag = 0;
        int aqq = 0;
        int flag = 0;

        String irFlagSource = "none";
        boolean usesPNG = false;

        String baseFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+title+".tga";
        String eastFlagDir = modDirectory+"/gfx/flags/"+rank+"_"+eastTitle+".tga";

        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(irFlag)) {
                flag = 1;
                String[] flagSource = flagList.get(aqq);
                String[] flagSourceEast = flagSource;
                String irFlagOriginal = flagSourceEast[0];
                flagSourceEast[0] = eastTitle;
                int aq2 = 0;
                String emblems = flagSource[4];
                String eastEmblems = flagSource[4];
                String[] emblemList = emblems.split("~~");
                String eastColorFormatted = "rgb("+eastColor+")";
                String colorFormatted = ("rgb("+color+")");
                int range = 15;
                boolean changedColor = false;
                boolean changedColorR = false;
                ArrayList<String[]> rangeFlag = new ArrayList<String[]>();
                ArrayList<String[]> ratioFlag = new ArrayList<String[]>(); //Used when building flag using color ratio
                ArrayList<String[]> backgroundFlag = new ArrayList<String[]>();
                String rangeC1 = flagSourceEast[2];
                String rangeC2 = flagSourceEast[3];
                String ratioC1 = flagSourceEast[2];
                String ratioC2 = flagSourceEast[3];
                String backgroundColor1F = Output.getColor(flagSourceEast[2],colorList);
                String backgroundColor2F = Output.getColor(flagSourceEast[3],colorList);
                boolean bC1IsWhite = Processing.isWithinColorRatio(backgroundColor1F,"128,128,128",range);
                boolean bC2IsWhite = Processing.isWithinColorRatio(backgroundColor2F,"128,128,128",range);
                if (Processing.isWithinColorRange(backgroundColor1F,("rgb("+color+")"),range)) {
                    rangeC1 = eastColorFormatted;
                    changedColor = true;
                }
                else if (Processing.isWithinColorRatio(backgroundColor1F,("rgb("+color+")"),range) && !bC1IsWhite) {
                    ratioC1 = eastColorFormatted;
                    changedColorR = true;
                }
                if (Processing.isWithinColorRange(backgroundColor2F,("rgb("+color+")"),range)) {
                    rangeC2 = eastColorFormatted;
                    changedColor = true;
                }
                else if (Processing.isWithinColorRatio(backgroundColor2F,("rgb("+color+")"),range) && !bC2IsWhite) {
                    ratioC2 = eastColorFormatted;
                    changedColorR = true;
                }
                String[] rangeFlagString = new String[5];
                rangeFlagString[0] = eastTitle;
                rangeFlagString[1] = flagSourceEast[1];
                rangeFlagString[2] = rangeC1;
                rangeFlagString[3] = rangeC2;
                rangeFlagString[4] = eastEmblems;
                
                String[] ratioFlagString = new String[5];
                ratioFlagString[0] = eastTitle;
                ratioFlagString[1] = flagSourceEast[1];
                ratioFlagString[2] = ratioC1;
                ratioFlagString[3] = ratioC2;
                ratioFlagString[4] = eastEmblems;
                
                String[] backgroundFlagString = new String[5];
                backgroundFlagString[0] = eastTitle;
                backgroundFlagString[1] = flagSourceEast[1];
                backgroundFlagString[2] = flagSourceEast[2];
                backgroundFlagString[3] = flagSourceEast[3];
                backgroundFlagString[4] = eastEmblems;
                
                String rangeEmblems =  rangeFlagString[4];
                String ratioEmblems =  ratioFlagString[4];
                while (aq2 < emblemList.length) {
                    String emblem = emblemList[aq2];
                    if (emblem.split("~_~")[0].contains(".png")) {
                        usesPNG = true;
                        aq2 = aq2 + emblemList.length;
                    }
                    String embColor1 = emblem.split("~_~")[1];
                    String embColor2 = emblem.split("~_~")[2];
                    String embColor1F = Output.getColor(embColor1,colorList);
                    String embColor2F = Output.getColor(embColor2,colorList);
                    boolean c1IsWhite = Processing.isWithinColorRatio(embColor1F,"128,128,128",range);
                    boolean c2IsWhite = Processing.isWithinColorRatio(embColor2F,"128,128,128",range);
                    if (Processing.isWithinColorRange(embColor1F,colorFormatted,range)) {
                        rangeEmblems = rangeEmblems.replace(emblem,emblem.replace(embColor1,eastColorFormatted));
                        changedColor = true;
                    }
                    else if (Processing.isWithinColorRatio(embColor1F,colorFormatted,range) && !c1IsWhite) {
                        ratioEmblems = ratioEmblems.replace(emblem,emblem.replace(embColor1,eastColorFormatted));
                        changedColorR = true;
                    }
                    if (Processing.isWithinColorRange(embColor2F,colorFormatted,range)) {
                        rangeEmblems = rangeEmblems.replace(emblem,emblem.replace(embColor2,eastColorFormatted));
                        changedColor = true;
                    }
                    else if (Processing.isWithinColorRatio(embColor2F,colorFormatted,range) && !c2IsWhite) {
                        ratioEmblems = ratioEmblems.replace(emblem,emblem.replace(embColor2,eastColorFormatted));
                        changedColorR = true;
                    }
                    aq2 = aq2 + 1;
                }
                rangeFlagString[4] = rangeEmblems;
                rangeFlag.add(rangeFlagString);
                rangeFlag.add(rangeFlagString);
                ratioFlagString[4] = ratioEmblems;
                ratioFlag.add(ratioFlagString);
                ratioFlag.add(ratioFlagString);
                if (!changedColor) {
                    if (changedColorR && !usesPNG) { //Try rebuilding flag using color ratio rather then color range
                        genFlag = generateFlag(ck2Dir,impGameDir,rank,ratioFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                    } else {
                        backgroundFlagString[2] = eastColorFormatted;
                        backgroundFlagString[4] = eastEmblems;
                        backgroundFlag.add(backgroundFlagString);
                        backgroundFlag.add(backgroundFlagString);
                        changedColorR = false;
                    }
                }

                if (changedColor) {
                    genFlag = generateFlag(ck2Dir,impGameDir,rank,rangeFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                } else {
                    genFlag = generateFlag(ck2Dir,impGameDir,rank,backgroundFlag,eastTitle,eastTitle,colorList,modFlagGFX,modDirectory);
                }
                flagSource[0] = irFlagOriginal;

            }
            aqq = aqq + 1;
        }

        if (genFlag == 0) {
            Output.copyFlag(ck2Dir,modDirectory,rank,capital,eastTitle);
        }
        if (usesPNG || genFlag == 0) {
            if (eastTitle.contains("east")) {
                Output.irFlagHue(eastFlagDir,"100,100,33.3",eastFlagDir);
            }
        }
    }
    
    public static void splitBloodlineEmbGen(String ck2Dir, String irDirectory, String rank, ArrayList<String[]> flagList, String tag, String flagID,
    ArrayList<String[]> colorList, ArrayList<String> flagGFXList, String modDirectory)
    throws IOException
    {

        int flagCreated = 0; //if flag is successfully created, change to 1
        //output[2] format = hsvOrRgb,r g b
        //output[3] format = hsvOrRgb,r g b
        // output[4] format is texture~_~color1~_~color2~_~scale~_~position~_~rotation~~(nextEmblem)
        int aqq = 1;
        int flag = 0;
        boolean usesPNG = false;
        boolean hasOnlyBorders = true;
        boolean isBrightBackground = false;
        String ck2Tag = rank+"_"+tag;
        String devFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_splitDev.gif";
        String shadowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_splitDevShadow.tga";
        String flagName = modDirectory+"/gfx/interface/bloodlines/bloodlines_symbol_"+ck2Tag+"_split.tga";
        String lineGFX = "defaultOutput/templates/gfx/interface/SplitLine04.tga";
        
        String glowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow.tga";
        String glowFlagName2 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow2.tga";
        String glowFlagName3 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow3.tga";
        String glowFlagName4 = "defaultOutput/flagDev/bloodlines_symbol_"+ck2Tag+"_restoredDevShadow4.tga";
        String flagNameR = modDirectory+"/gfx/interface/bloodlines/bloodlines_symbol_"+ck2Tag+"_restored.tga";
        
        while (aqq < flagList.size() && flag == 0) {
            if (flagList.get(aqq)[0].equals(flagID)) {
                flag = 1; //end loop
                String[] flagSource = flagList.get(aqq);
                if (flagSource[1].equals("pattern_horizontal_split_02.tga")) {
                    flagSource[1] = "pattern_horizontal_split_01.tga";
                } else if (flagSource[1].equals("pattern_horizontal_split_01.tga")) {
                    flagSource[1] = "pattern_horizontal_split_02.tga";
                }
                String pattern = irDirectory + "/game/gfx/coat_of_arms/patterns/" + "pattern_solid.tga";
                String color1 = flagSource[2];
                String color2 = flagSource[3];
                String emblems = flagSource[4];
                
                isBrightBackground = Processing.isBright(getColor(color1,colorList),200);

                int aq3 = 0;
                while (aq3 < flagGFXList.size()) {
                    if (flagGFXList.get(aq3).contains(flagSource[1])) {
                        pattern = flagGFXList.get(aq3);
                        aq3 = flagGFXList.size();
                    }
                    aq3 = aq3 + 1;
                }

                irFlagBackground(pattern,devFlagName,"None","None");

                String[] emblemList = emblems.split("~~");
                int aq2 = 0;
                while (aq2 < emblemList.length) {
                    String[] emblem = emblemList[aq2].split("~_~");
                    String eTexture = irDirectory+"/game/gfx/coat_of_arms/colored_emblems/"+emblem[0];
                    String eColor1 = emblem[1];
                    String eColor2 = emblem[2];
                    String eScale = emblem[3];
                    String ePos = emblem[4];
                    String eRot= emblem[5];
                    String eNameOld = "defaultOutput/flagDev/emblem"+aq2+"Old"+".gif";
                    String eName = "defaultOutput/flagDev/emblem"+aq2+".gif";

                    int aq4 = 0;
                    while (aq4 < flagGFXList.size()) {
                        if (flagGFXList.get(aq4).contains(emblem[0])) {
                            eTexture = flagGFXList.get(aq4);
                            aq4 = flagGFXList.size();
                        }
                        aq4 = aq4 + 1;
                    }
                    
                    if (eTexture.contains(".png")) {
                        usesPNG = true;
                        aq2 = aq2 + emblemList.length;
                    }

                    eColor1 = getColor(eColor1,colorList);
                    if (!eColor2.equals("none")) {
                        eColor2 = getColor(eColor2,colorList);
                    }

                    if (!emblem[0].contains("ce_border_")) { //Don't include borders onto flag GFX
                        irFlagEmblem(eTexture,eNameOld,eColor1,eColor2,eName,eScale,eRot,ePos,devFlagName);
                        hasOnlyBorders = false;
                    }
                    aq2 = aq2+1;
                }
                if (aq2 != 0 && !usesPNG && !hasOnlyBorders) {
                    flagCreated = 1; //Flag has been created
                }
            }

            aqq = aqq + 1;

        }
        
        if (flagCreated != 1) {
            String oldFlagName = modDirectory+"/gfx/flags/"+ck2Tag+".tga";
            File oldFlagFile = new File(oldFlagName);
            boolean oldFlag = true;
            if (!oldFlagFile.exists()) { //TAGs converted to existing titles don't copy that title's flag to the mod files
                oldFlagName = ck2Dir+"/gfx/flags/"+ck2Tag+".tga";
                oldFlag = false;
            }
            String circleMaskName = "defaultOutput/templates/gfx/interface/CircleMask.gif";
            String greyMaskName = "defaultOutput/templates/gfx/interface/greyMask.gif";
            String transparantOverlay256_256 = "defaultOutput/templates/gfx/interface/transparant256_256.gif";
            lineGFX = "defaultOutput/templates/gfx/interface/SplitLine05.tga";
            irFlagScaleExact(oldFlagName,devFlagName,"256","256");
            if (!usesPNG || !oldFlag) {
                irFlagFlip(devFlagName,devFlagName,"y");
            }
            irFlagTexture(devFlagName,greyMaskName,devFlagName);
            irFlagCombineCutout(devFlagName,circleMaskName,transparantOverlay256_256,devFlagName);
        }
        
        irFlagGlow(glowFlagName,devFlagName,"yellow","15");
        irFlagGlow(glowFlagName2,devFlagName,"rgb(254,254,180)","5");
        irFlagGlow(glowFlagName3,devFlagName,"gold","5");
        String glow4 = "black";
        if (isBrightBackground) {
            glow4 = "yellow";
        }
        irFlagGlow(glowFlagName4,devFlagName,glow4,"5");
        irFlagCombine(glowFlagName,glowFlagName2,glowFlagName);
        irFlagCombine(glowFlagName,glowFlagName3,glowFlagName);
        irFlagCombine(glowFlagName,glowFlagName4,glowFlagName);
        
        irFlagScaleExact(glowFlagName,flagNameR,"96","93"); //create final .tga flag, scale to bloodline icon size 96x93
        
        irFlagCombine(devFlagName,lineGFX,devFlagName);
        irFlagShadow(shadowFlagName,devFlagName);
        irFlagScaleExact(shadowFlagName,flagName,"96","93"); //create final .tga flag, scale to bloodline icon size 96x93

    }
    
    public static void eastWestDecisionIcon(String country,String modDirectory) throws IOException
    {
        String iconDevName = "defaultOutput/flagDev/iconDev"+country+".gif";
        String iconTemplateName = "defaultOutput/templates/gfx/interface/decision_icon_e_TAG_split.gif";
        String shadowFlagName = "defaultOutput/flagDev/bloodlines_symbol_"+country+"_splitDevShadow.tga";
        String iconName = modDirectory+"/gfx/interface/decision_icon_"+country+"_split.tga";
        irFlagScaleExact(shadowFlagName,iconDevName,"26","26");
        irFlagCombine(iconTemplateName,iconDevName,iconName);
        
    }
    
    public static void eastWestRestorationIcon(String country,String irFlag,ArrayList<String[]> flagList,String ck2Dir,String modDirectory)
    throws IOException
    {
        String iconDevName = "defaultOutput/flagDev/iconRestorationDev"+country+".gif";
        String iconTemplateName = "defaultOutput/templates/gfx/interface/decision_icon_e_TAG_restoration.gif";
        String devFlagName = modDirectory+"/gfx/flags/"+country+".tga";
        File devFlagFile = new File(devFlagName);
        String iconName = modDirectory+"/gfx/interface/decision_icon_"+country+"_restoration.tga";
        if (!devFlagFile.exists()) { //TAGs converted to existing titles don't copy that title's flag to the mod files
            String sourceFlagName = ck2Dir+"/gfx/flags/"+country+".tga";
            irFlagFlip(sourceFlagName,iconDevName,"y"); //CK2-sourced flags get flipped, unflips flag
            devFlagName = iconDevName;
        } else { //Check to see if flag was converted from I:R. If not, unflip
            int aqq = 0;
            boolean hasFlag = false;
            while (aqq < flagList.size()) {
                if (flagList.get(aqq)[0].equals(irFlag)) {
                    hasFlag = true;
                    aqq = flagList.size() +1;
                }
                aqq = aqq + 1;
            }
            if (!hasFlag) { //CK2-sourced flags get flipped, unflips flag
                irFlagFlip(devFlagName,iconDevName,"y");
            }
        }
        irFlagScaleExact(devFlagName,iconDevName,"24","20");
        irFlagCombine(iconTemplateName,iconDevName,iconName);
        
    }
    
    public static void replaceInFile(String textToReplace, String testToReplaceWith, String Directory) throws IOException // Replace A with B
    {

        String tab = "	";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(Directory);

        FileOutputStream fileOut= new FileOutputStream(Directory);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < oldFile.size()) {
            //out.println (oldFile.get(aqq));
            if (oldFile.get(aqq).contains(textToReplace)) {
                String replacedLine = oldFile.get(aqq).replace(textToReplace,testToReplaceWith);
                replacedLine = replacedLine.replace("#","");
                out.println (replacedLine);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();
    }
    
    public static void countrySetupCreation(String color, String title, String Directory) throws IOException
    {

        //try {
        String VM = "\\"; 
        VM = VM.substring(0);
        char quote = '"';

        ArrayList<String> oldFile = new ArrayList<String>();

        Directory = Directory + "/setup/countries";
        
        String outputFile = Directory + "/countries.txt";
        
        oldFile = Importer.importBasicFile(outputFile);

        FileOutputStream fileOut= new FileOutputStream(outputFile);
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        int aqq = 0;

        try {

            while (flag == 0) {
                out.println (oldFile.get(aqq));
                //System.out.println("Outputting "+oldFile.get(aqq));
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            flag = 1;

        } 

        //System.out.println("Outputting2 "+title+" = "+quote+"setup/countries/converted/"+title+".txt"+quote);
        //out.println("test");
        out.println (title+" = "+quote+"setup/countries/converted/"+title+".txt"+quote);
        out.flush();
        fileOut.close();
        
        setupForCountry(color,title,Directory);
        
        //}catch (Exception e) {
        //    genericBlankFile(Directory + "/converted_countries.txt","#Converted Countries from Bronze Age");
        //}

    }
    
    public static void setupForCountry(String color, String title, String Directory) throws IOException
    {
        FileOutputStream fileOut= new FileOutputStream(Directory + "/converted/"+title+".txt");
        PrintWriter out = new PrintWriter(fileOut);
        out.println ("color = rgb { "+color+" }");
        out.println ("color2 = rgb { "+color+" }");
        out.println ("");
        out.println ("ship_names = {");
        out.println ("}");
        out.flush();

    }
    
    public static void outputBasicFile(ArrayList<String> text, String fileName, String Directory) throws IOException
    {
        FileOutputStream fileOut= new FileOutputStream(Directory + "/"+ fileName);
        PrintWriter out = new PrintWriter(fileOut);
        int count = 0;
        while (count < text.size()) {
            out.println (text.get(count));
            count = count + 1;
        }
        out.flush();

    }

}
