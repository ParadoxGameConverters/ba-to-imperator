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
 * Write a description of class Processing here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Processing
{

    public static String basicProvinceTotal(int totalCKProv, String[] ck2TagTotals, String[][] ck2ProvInfo, int typeToCollect,int aq2)
    {

        int flag = 0;
        int flag2 = 0;
        int aq5 = 0;
        int aq6 = 0;
        String[] irOwners;
        String[] owners;
        String[] provtags;
        String[] pops;
        int culRelID = 0;
        String[] pastOwners;
        pastOwners = new String[totalCKProv];
        pastOwners[0] = "debug";
        int aq7 = 0;

        irOwners = ck2TagTotals[aq2].split("~"); 

        while (aq5 < irOwners.length) {
            owners = irOwners[aq5].split(","); 

            int[] ownerTot;
            ownerTot = new int[totalCKProv]; //should redefine each time

            while (aq7 <= totalCKProv) {
                if (owners[0].equals (pastOwners[aq7])) {
                    culRelID = aq7;    
                }

                if (pastOwners[aq7] == null ) {
                    culRelID = aq7;
                    pastOwners[aq7] = owners[0];
                    aq7 = 9999;
                }
                else {
                    aq7 = aq7 + 1;    
                }
            }   

            if (owners[1].equals("null")) {
                ownerTot[culRelID] = 0;    
            }
            else {
                ownerTot[culRelID] = Integer.parseInt(owners[1])+1;
            }

            aq6 = 1;
            while (aq6 < totalCKProv) {
                if (ownerTot[aq6] > ownerTot[aq6-1]){
                    ck2ProvInfo[typeToCollect][aq2] = owners[0];

                }

                aq6 = aq6 + 1;

            }
            aq5 = aq5 + 1;

            aq7 = 0;
            culRelID = 0;

        }

        return ck2ProvInfo[typeToCollect][aq2];
    }

    public static String[] importNames (String name, int provIDnum, String ck2Dir) throws IOException //Imports CK II prov name
    {

        String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream("provinceNames.txt");
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";

        int aqq = 0;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2];

        output[0] = "noName"; //default for no owner, uncolonized province
        output[1] = "plains"; //default for no culture, uncolonized province with 0 pops

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();
                if (qaaa.split(",")[0].equals(provID)){
                    endOrNot = false;
                    ckName = qaaa.split(",")[4];
                    output[0] = ckName;
                    output[1] = importCK2ProvFile(ck2Dir,provIDnum,ckName)[0];

                }
            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   
        fileIn.close();
        return output;

    }

    public static String[] importCK2ProvFile (String ck2Dir, int provIDnum, String ck2Prov) throws IOException
    {

        String VM = "\\";
        VM = VM.substring(0);
        String provID = Integer.toString(provIDnum);
        String name = ck2Dir+VM+"history"+VM+"provinces"+VM+provID+" - "+ck2Prov+".txt";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[1];

        try {

            FileInputStream fileIn= new FileInputStream(name);
            Scanner scnr= new Scanner(fileIn);

            int flag = 0;
            String ckName = "debug";

            int aqq = 0;
            boolean endOrNot = true;
            String qaaa = "debug";

            output[0] = "plains"; //default terrain type

            try {
                while (endOrNot = true){

                    qaaa = scnr.nextLine();
                    if (qaaa.split("= ")[0].equals("terrain ")){
                        endOrNot = false;
                        output[0] = qaaa.split(",")[0];

                    }

                }

            }catch (java.util.NoSuchElementException exception){
                endOrNot = false;

            }   

        }

        catch(java.io.FileNotFoundException exception) {

            output[0] = "error";

        }

        return output;

    }

    public static String importBaronyName (String name, int provIDnum, String ck2Dir) throws IOException
    {

        int aqq = 0;
        String tab = "	";
        String VM = "\\";

        VM = VM.substring(0);

        String bracket1 = "a{";
        String bracket2 = "a}";

        bracket1 = bracket1.substring(1);
        bracket2 = bracket2.substring(1);

        String provID = Integer.toString(provIDnum);

        FileInputStream fileIn= new FileInputStream(ck2Dir+VM+"common"+VM+"landed_titles"+VM+"landed_titles.txt");
        Scanner scnr= new Scanner(fileIn);

        String ckName = "debug";

        int flag = 0;
        String qaaa = "debug";
        String output = qaaa;   // Owner Culture Religeon PopTotal Buildings
        String provName = "noProv";
        String tab3 = tab+tab+tab;
        String tab4 = tab+tab+tab+tab;

        provName = importNames(name,provIDnum,ck2Dir)[0];

        provName = provName.toLowerCase();
        provName = provName.replace(" ","_");
        provName = provName.replace("-","_");

        if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("angseley")) {

            provName = "anglesey";
        }

        else if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("aurilliac")) {

            provName = "aurillac";
        }

        else if (provName.equals("el_arish")) {

            provName = "el-arish";
        }

        else if (provName.equals("augstgau")) {

            provName = "aargau";
        }

        else if (provName.equals("franken")) {

            provName = "wurzburg";
        }

        else if (provName.equals("nidaros")) {

            provName = "trondelag";
        }

        else if (provName.equals("jarnbaraland")) {

            provName = "dalarna";
        } 

        else if (provName.equals("trans_portage")) {

            provName = "trans-portage";
        }  

        else if (provName.equals("morava")) {

            provName = "znojmo";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("desht_i_kipchak")) {

            provName = "desht-i-kipchak";
        }

        else if (provName.equals("kara_kum")) {

            provName = "kara-kum";
        }

        else if (provName.equals("petra") || provName.equals("al'_aqabah")|| provName.equals("al_'aqabah")) {

            provName = "al_aqabah";
        }

        else if (provName.equals("anti_atlas")) {

            provName = "anti-atlas";
        }

        else if (provName.equals("asaita")) {

            provName = "asayita";
        }

        else if (provName.equals("al_qusair")) {

            provName = "alqusair";
        }

        else if (provName.equals("kyzyl_su")) {

            provName = "kyzyl-su";
        }

        if (provName.equals ("error")) {
            flag = 3;  
        }

        try {
            while (flag == 0){

                qaaa = scnr.nextLine();

                if (qaaa.split("=")[0].equals(tab3 + "c_" + provName + " ") || qaaa.split("=")[0].equals(tab3 + "c_" + provName)
                || qaaa.split("=")[0].equals("            "+tab+ "c_" + provName+" ") || qaaa.split("=")[0].equals(tab4 + "c_" + provName + " ") ||
                qaaa.split("=")[0].equals("            " + "c_" + provName + " ") || qaaa.split("=")[0].equals("           "+tab+tab+ "c_" + provName+" ") ||
                qaaa.split("=")[0].equals("           " + tab + "c_" + provName + " ")){
                    flag = 1;
                    output = null;
                    while (flag == 1){

                        try {
                            if (qaaa.split("_")[0].equals(tab4+"b") || qaaa.split("_")[0].equals("                "+tab+"b") ||
                            qaaa.split("_")[0].equals(tab4+tab+"b") || qaaa.split("_")[0].equals("                   "+tab+"b") || 
                            qaaa.split("_")[0].equals("                "+"b")){
                                qaaa = qaaa.split("b_")[1];
                                qaaa = qaaa.replace(bracket1,"");
                                qaaa = qaaa.replace(bracket2,"");
                                qaaa = qaaa.replace("=","");
                                qaaa = qaaa.replace(" ","");
                                if (output != null) {
                                    output = output + "," + qaaa.split(" =")[0];
                                } else { output = qaaa.split(" =")[0]; }

                                aqq = aqq + 1;
                            }
                        }catch (java.util.NoSuchElementException exception){
                            qaaa = scnr.nextLine();

                        }
                        qaaa = scnr.nextLine();
                        if (aqq == 7) {
                            flag = 2;    
                        }
                    }

                }
            }

        }catch (java.util.NoSuchElementException exception){
            flag = 2;

        }   

        fileIn.close();

        return output;

    }

    public static String[] importBaronyNameList (String name, int provIDnum, String ck2Dir) throws IOException
    {

        String provID = Integer.toString(provIDnum);

        int flag = 0;
        String ckName = "debug";
        int aqq = 1;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[2015];

        output[0] = "noName"; //default for no owner, uncolonized province

        try {
            while (endOrNot = true){
                output[aqq]=importBaronyName(ck2Dir,aqq,ck2Dir);
                if (output[aqq].equals("debug")) {
                    //Output.logPrint(aqq+"is for"+output[aqq]);

                }
                aqq = aqq + 1;

            }

        }catch (java.lang.ArrayIndexOutOfBoundsException exception){
            endOrNot = false;

        }   
        return output;

    }

    public static String[] importRegionList (int totProv,String[] provAreas, String regionDir) throws IOException
    {

        int flag = 0;
        String ckName = "debug";

        int aqq = 1;
        boolean endOrNot = true;
        String qaaa = "debug";
        String[] provList;
        provList = new String[totProv];

        ArrayList<String> regions = Importer.importRegions(regionDir+"map_data/regions.txt");

        //String[] provAreas = importAreas(regionDir+"map_data/areas.txt",totProv);

        try {
            while (endOrNot = true){ //Goes through and get's each province for each region
                int regionID = 1;
                while (regionID < regions.size()) {
                    qaaa = regions.get(regionID);
                    String[] regionList = qaaa.split(",");
                    String provArea = provAreas[aqq];
                    int regionCount = 1;
                    while (regionCount < regionList.length){
                        String selectedArea = regionList[regionCount];
                        if (selectedArea.equals(provArea)) {
                            //System.out.println(regionList[0]+"_QA " + "Area: "+selectedArea);
                            provList[aqq] = regionList[0];
                            //System.out.println(provList[aqq]);
                            regionCount = 1 + regionList.length;
                        }
                        regionCount = regionCount + 1;
                    }

                    regionID = regionID + 1;
                }
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            endOrNot = false;

        }   
        return provList;

    }

    public static String[] importRegion (String[] provList, String dir, String region) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(dir);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";
        int aqq = 0;
        String qaaa = "aa";
        boolean endOrNot = true;

        int number = 0;

        try {
            while (endOrNot = true){
                qaaa = scnr.nextLine();

                if (aqq == 0) {
                    qaaa = qaaa.substring(1);    
                }

                try {

                    qaaa = qaaa.split("=")[0];
                    number = Integer.parseInt(qaaa);    

                    provList[number] = region;   

                }catch (java.lang.NumberFormatException exception) {
                }
                aqq = aqq + 1;    
            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return provList;

    }

    public static String[] importAreas (String dir,int num) throws IOException
    {

        FileInputStream fileIn= new FileInputStream(dir);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        String ckName = "debug";
        int aqq = 0;
        String qaaa = "aa";
        boolean endOrNot = true;

        int number = 0;

        String tab = "	";
        String endBracket = " }".replace(" ","");
        String endBracket2 = endBracket + " ";

        String[] provList = new String [num];

        try {
            while (endOrNot = true){
                qaaa = scnr.nextLine();
                qaaa = qaaa.replace("﻿",""); //Newline character

                if (qaaa.contains(" = {") && !qaaa.contains("provinces")) {
                    String provinceName = qaaa.split(" = ")[0];
                    while (!qaaa.equals(endBracket) && !qaaa.equals(endBracket2)) {
                        qaaa = scnr.nextLine();
                        int badFormattingCount = 0;
                        while (badFormattingCount < 10) { //Some BA provs have terrible formatting which are difficult to read
                            String badFormatBeginning = "{"+badFormattingCount;
                            String badFormatEnd = badFormattingCount+"}";
                            String goodFormatBeginning = "{ "+badFormattingCount;
                            String goodFormatEnd = badFormattingCount+" }";
                            
                            qaaa = qaaa.replace(badFormatBeginning,goodFormatBeginning);
                            qaaa = qaaa.replace(badFormatEnd,goodFormatEnd);
                            badFormattingCount = badFormattingCount + 1;
                        }
                        
                        qaaa = qaaa.replace("  }"," }");
                        qaaa = qaaa.replace(tab,"");
                        if (qaaa.contains("provinces = { ")) {
                            qaaa = qaaa.split(" = ")[1];
                            qaaa = qaaa.replace("  "," ");
                            qaaa = qaaa.replace("{","");
                        }
                        qaaa = qaaa.replace("   ","");
                        qaaa = qaaa.replace("  ","");
                        qaaa = qaaa.replace("	","");
                        qaaa = qaaa.replace("provinces = { ","");

                        if (!qaaa.equals(endBracket) && !qaaa.contains("color") && !qaaa.equals(endBracket2)){
                            String[] numList = qaaa.split(" ");
                            int numCount = 0;
                            while (numCount < numList.length){
                                try {
                                    int provID = Integer.parseInt(numList[numCount]);
                                    provList[provID] = provinceName;
                                }

                                catch (Exception e) {

                                }
                                numCount = numCount + 1;
                            }
                        }

                    }

                    qaaa = qaaa.substring(1);    
                }

                aqq = aqq + 1;    
            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        return provList;

    }
    
    public static ArrayList<Provinces> applyRegionsToProvinces (String[] regionList, ArrayList<Provinces> baProvInfoListOld) throws IOException
    {

        ArrayList<Provinces> baProvInfoListNew = new ArrayList<Provinces>();
        int count = 0;
        while (count < baProvInfoListOld.size()) {
            Provinces baProv = baProvInfoListOld.get(count);
            //int provID = baProv.getID();
            String region = regionList[count];
            baProv.setRegion(region);
            baProvInfoListNew.add(baProv);
            //baProv
            
            count = count + 1;
        }
        return baProvInfoListNew;

    }
    
    public static ArrayList<Provinces> applyAreasToProvinces (String[] regionList, ArrayList<Provinces> baProvInfoListOld) throws IOException
    {

        ArrayList<Provinces> baProvInfoListNew = new ArrayList<Provinces>();
        int count = 0;
        while (count < baProvInfoListOld.size()) {
            Provinces baProv = baProvInfoListOld.get(count);
            //int provID = baProv.getID();
            String area = regionList[count];
            baProv.setArea(area);
            baProvInfoListNew.add(baProv);
            //baProv
            
            count = count + 1;
        }
        return baProvInfoListNew;

    }
    
    public static ArrayList<Provinces> reorderProvincesByID (ArrayList<Provinces> baProvInfoListOld, Provinces blankProv) throws IOException
    {

        ArrayList<Provinces> baProvInfoListNew = new ArrayList<Provinces>();
        baProvInfoListNew.add(blankProv);
        int count = 1;
        while (count < baProvInfoListOld.size()) {
            int baProvID = getProvByID(baProvInfoListOld,count);
            Provinces baProv = baProvInfoListOld.get(baProvID);
            baProvInfoListNew.add(baProv);
            //baProv
            
            count = count + 1;
        }
        return baProvInfoListNew;

    }

    public static String randomizeColor ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng1 = (int) (Math.random() * 255);
        int Rng2 = (int) (Math.random() * 255);
        int Rng3 = (int) (Math.random() * 255);
        String color = Integer.toString(Rng1) + " " + Integer.toString(Rng2) + " " + Integer.toString(Rng3); 

        return color;

    }

    public static String randomizeColorGrey ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng = (int) (Math.random() * 70);
        String color = Integer.toString(Rng) + " " + Integer.toString(Rng) + " " + Integer.toString(Rng); 

        return color;

    }

    public static String randomizeAge ()
    {

        int flag = 0;
        int aqq = 0;
        String qaaa = "aa";
        int Rng = (int) (Math.random() * 34);
        Rng = Rng + 16;
        String age = Integer.toString(Rng); 

        return age;

    }

    public static String capitalColor (String overlordColor) //Generates color for kingdom tier capital region, as to be different from main country
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        //int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) * 0.97);
        //int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) / 0.75);
        //int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) * 0.13);
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) + 60);
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) + 60);
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) + 60);
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static void convertProvConvList (String name, String outputDest) throws IOException //Converts standard mapper tool format into regular format
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[25000];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "animism"; //default for no culture, uncolonized province with 0 pops

        char bracket1 = 123;
        char bracket2 = 125;

        String bracket1Str = bracket1+" ";

        bracket1Str = bracket1Str.replace(" ","");

        String bracket2Str = bracket2+" ";

        bracket2Str = bracket2Str.replace(" ","");

        int aq2 = 0;

        try {
            while (endOrNot = true){

                qaaa = scnr.nextLine();

                if (qaaa.split("ink = ")[0].equals(tab+"l")){
                    qaaa = qaaa.split("ink = ")[1];
                    qaaa = qaaa.split(tab)[0];
                    qaaa = qaaa.substring(2,qaaa.length()-2);;
                    qaaa = qaaa.replace(" = ",",");

                    String[] provinceMappings = qaaa.split(" ");

                    String impProv = "a";

                    int aqq = 0;

                    while (aqq < provinceMappings.length) {

                        if (provinceMappings[aqq].split(",")[0].equals("imperator")){
                            impProv = provinceMappings[aqq].split(",")[1];
                            aqq = 99999;
                        }
                        else {aqq = aqq + 1;}  

                    }
                    aqq = 0;   
                    if (impProv != "a") {
                        while (aqq < provinceMappings.length) {
                            if (provinceMappings[aqq].split(",")[0].equals("ba")){
                                output[aq2] = (provinceMappings[aqq].split(",")[1]+","+impProv);
                                //System.out.println(output[aq2]);
                            }
                            aqq = aqq + 1;
                            aq2 = aq2 + 1;
                        }

                    }

                }

            }

        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        fileIn.close();
        int count3 = 0;
        FileOutputStream fileOut= new FileOutputStream(outputDest);
        PrintWriter out = new PrintWriter(fileOut);
        while (count3 < aq2) {
            if (output[count3] != null) {
                out.println(output[count3]);
            }
            count3 = count3 + 1;
        }
        out.flush();
        fileOut.close();
        //outputProvConvList(output,outputDest);

    }

    public static String[] convertProvConvListR (String name, String outputDest) throws IOException //Converts regular format into mapper tool format
    {

        String tab = "	";

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(outputDest);
        PrintWriter out = new PrintWriter(fileOut);

        int flag = 0;
        boolean endOrNot = true;
        String qaaa;
        String[] output;   // Owner Culture Religeon PopTotal Buildings
        output = new String[15000];

        output[0] = "peq"; //default for no owner, uncolonized province
        output[1] = "99999"; //default for no culture, uncolonized province with 0 pops

        char bracket1 = 123;
        char bracket2 = 125;

        String bracket1Str = bracket1+" ";

        bracket1Str = bracket1Str.replace(" ","");

        String bracket2Str = bracket2+" ";

        bracket2Str = bracket2Str.replace(" ","");

        int aq2 = 0;

        ArrayList<String> oldMappingList = Importer.importBasicFile(name);

        //ArrayList<String> newMappingList;

        int ck2Prov = 0;

        try {
            while (ck2Prov < 3000){
                int line = 0;
                String newMap = "link = {";
                while (line < oldMappingList.size()){
                    qaaa = oldMappingList.get(line);
                    //System.out.println(qaaa);
                    if (!qaaa.contains("#") && !qaaa.equals("")) {
                        if (qaaa.split(",")[1].equals(Integer.toString(ck2Prov))) {
                            newMap = newMap + " imperator = "+qaaa.split(",")[0];
                        }
                    }

                    line = line + 1;

                }
                if (!newMap.equals("link = {")) {
                    newMap = newMap + " ck2 = " + ck2Prov + " }";
                    out.println(newMap);
                }
                ck2Prov = ck2Prov + 1;

            }
        }catch (java.util.NoSuchElementException exception){
            endOrNot = false;

        }   

        out.flush();
        fileOut.close();

        fileIn.close();
        return output;

    }

    public static int outputProvConvList(String[] toAdd, String destination) throws IOException
    //Outputs combined provList
    {
        FileOutputStream fileOut= new FileOutputStream(destination);
        PrintWriter out = new PrintWriter(fileOut);

        int aqq = 0;

        while (aqq < toAdd.length) {
            if (toAdd[aqq] != null) {
                out.println(toAdd[aqq]); 
            }
            aqq = aqq + 1; 
        }

        return 0;
    }

    public static ArrayList<String> generateSubjectList(int tot, String source) throws IOException
    {

        ArrayList<String> subjects = new ArrayList<String>();

        subjects.add("0,0,feudatory"); //Debug at id 0 so list will never be empty

        int aqq = 0;

        while (aqq < tot) {

            //String[] relation = Importer.importSubjects(source,aqq,subjects);
            //subjects = Importer.importSubjects(source,aqq,subjects);
            //if (relation[0] != "9999") {
            //subjects.add(relation[0]+","+relation[1]+","+relation[2]);

            //}
            aqq = aqq + 1; 
        }

        return subjects;
    }

    public static int checkSubjectList(int country, ArrayList<String> subjects) throws IOException
    {

        String countryStr = Integer.toString(country);
        int aqq = 0;

        int output = 9999;//default, if 9999, country remains free

        while (aqq < subjects.size()) {

            String subCountry = subjects.get(aqq).split(",")[1];

            if (countryStr.equals(subCountry)) { //if match, return id of overlord
                output = aqq;
                aqq = subjects.size() + 1000; //end loop
            }
            aqq = aqq + 1; 
        }

        return output;
    }
    
    public static boolean compareStringLists(ArrayList<String> strList, ArrayList<String> strList2) throws IOException //checks if both lists are equal
    {
        int count = 0;

        boolean tf = true;
        
        int size1 = strList.size();
        int size2 = strList2.size();
        
        if (size1 != size2) {
            tf = false;
            return tf;
        }

        while (count < strList.size()) {

            String line1 = strList.get(count);
            String line2 = strList2.get(count);
            
            if (!line1.equals(line2)) {
                tf = false;
                return tf;
            }
            
            count = count + 1; 
        }

        return tf;
    }

    public static int checkMonumentList(String name) throws IOException //Checks if save is from 2.0+ or before
    {

        FileInputStream fileIn= new FileInputStream(name);
        Scanner scnr= new Scanner(fileIn);
        int flag = 0;
        try {
            String qaaa = scnr.nextLine();

            if (qaaa.equals ("great_work_manager={")) {
                flag = 1;

            }
        } catch (java.util.NoSuchElementException exception) {

        }
        return flag;
    }

    public static String formatSaveName(String name) {// replaces /:*?"<>|- and space with _, while removing tabs and .rome
        String quote = '"'+""; // " character, Java doesn't like isoated " characters
        String tab = "	";

        name = name.replace("/","_");
        name = name.replace(":","_");
        name = name.replace("*","_");
        name = name.replace("?","_");
        name = name.replace(quote,"_");
        name = name.replace("<","_");
        name = name.replace(">","_");
        name = name.replace("|","_");
        name = name.replace("-","_");
        name = name.replace(" ","_");
        name = name.replace(tab,"");

        name = name.replace(".","~~~"); //Java's .split() method does not like period characters, temporarily switches "." to "~~~"

        String[] extensionSplit = name.split("~~~");

        if (extensionSplit[extensionSplit.length-1] != name) { //removes .rome or any other extension. If no extension at all, is left untouched

            name = name.replace(extensionSplit[extensionSplit.length-1],"");

            name = name.substring(0,name.length()-3);

        }

        name = name.replace("~~~",".");

        //Output.logPrint(name+"Q");

        return name;
    }

    public static String convertTag(String tag, int numericalID, ArrayList<String> tagMappings) throws IOException
    {// Converts dynamically generated BA tag to IR counterpart (if one exists)
        //String fileName = "titleConversion.txt";
        String tmpTag = Importer.importMappingFromArray(tagMappings,tag)[1];//converts title

        if (tmpTag.equals("99999") || tmpTag.equals("peq")) {//if there is no vanilla match
            tag = genNewTag(numericalID);
        } else {
            tag = tmpTag;
        }

        return tag;
    }

    

    public static String calcDuchyMajority (String duchy) throws IOException //calculates majority dejure ownership of duchy
    {

        String[] counties = duchy.split(",");

        int aqq = 0;

        ArrayList<String> count = new ArrayList<String>();

        while (aqq < counties.length) {
            if (aqq == 0) {
                count.add("debug");
            }
            int aq2 = 0;
            while (aq2 < count.size()) {
                if (counties[aqq] == count.get(aq2)) {
                    count.set(aq2,count.get(aq2)+","+counties[aqq]);
                } else if (counties[aqq] != "null" && counties[aqq] != "99999") {
                    count.add(counties[aqq]);
                }
                aq2 = aq2 + 1;
            }

            aqq = aqq + 1;

        }

        int aq3 = 0;

        int countNum = 0;

        int id = 0;

        while (aq3 < count.size()) {
            if (count.get(aq3).split(",").length > countNum) {
                countNum = count.get(aq3).split(",").length;
                id = aq3;
            }
            aq3 = aq3 + 1;
        }

        id = id - 1;

        duchy = counties[id];

        return duchy;
    }

    public static String formatProvName (String provName) throws IOException //formats province name to internal format
    {

        provName = provName.toLowerCase();
        provName = provName.replace(" ","_");
        provName = provName.replace("-","_");

        if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("angseley")) {

            provName = "anglesey";
        }

        else if (provName.equals("padua")) {

            provName = "padova";
        }

        else if (provName.equals("aurilliac")) {

            provName = "aurillac";
        }

        else if (provName.equals("el_arish")) {

            provName = "el-arish";
        }

        else if (provName.equals("augstgau")) {

            provName = "aargau";
        }

        else if (provName.equals("franken")) {

            provName = "wurzburg";
        }

        else if (provName.equals("nidaros")) {

            provName = "trondelag";
        }

        else if (provName.equals("jarnbaraland")) {

            provName = "dalarna";
        } 

        else if (provName.equals("trans_portage")) {

            provName = "trans-portage";
        }  

        else if (provName.equals("morava")) {

            provName = "znojmo";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("sieradzko_leczyckie")) {

            provName = "sieradzko-leczyckie";
        }

        else if (provName.equals("desht_i_kipchak")) {

            provName = "desht-i-kipchak";
        }

        else if (provName.equals("kara_kum")) {

            provName = "kara-kum";
        }

        else if (provName.equals("petra") || provName.equals("al'_aqabah")|| provName.equals("al_'aqabah")) {

            provName = "al_aqabah";
        }

        else if (provName.equals("anti_atlas")) {

            provName = "anti-atlas";
        }

        else if (provName.equals("asaita")) {

            provName = "asayita";
        }

        else if (provName.equals("al_qusair")) {

            provName = "alqusair";
        }

        else if (provName.equals("kyzyl_su")) {

            provName = "kyzyl-su";
        }

        return provName;

    }

    public static String[] defaultDejureConversion(String cult) throws IOException
    {

        Importer importer = new Importer();

        String[] dejureTitles = importer.importDejureList("dejureConversion.txt",cult);

        return dejureTitles;
    }

    public static String[] calculateUsedTitles(String[] cultureTitles, ArrayList<String[]> impTagInfo,int empireRank,int[] ck2LandTot) throws IOException
    //calculates if titles exist
    {

        int aqq = 0;

        int flag = 0; //Sets it so that only the first correct tag becomes cultureTitle. 

        int flag2 = 0; //Defeated civil war tags will still exist in save, causing calc to set non-existant civil war faction as cultureTitle without flags

        while (aqq < impTagInfo.size()) {
            if (("e_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[1]) && flag == 0 && ck2LandTot[aqq] >= empireRank) {
                cultureTitles[1] = "e_"+impTagInfo.get(aqq)[0];
                flag = 1;
            }

            if (("k_"+impTagInfo.get(aqq)[21]).equals(cultureTitles[2]) && flag2 == 0 && ck2LandTot[aqq] > 0) {
                cultureTitles[2] = "k_"+impTagInfo.get(aqq)[0];
                flag2 = 1;
            }
            aqq = aqq + 1;

        }

        return cultureTitles;
    }

    public static boolean checkFile(String file) throws IOException //checks if file exists or not
    {

        try {

            FileInputStream fileIn= new FileInputStream(file);

        }catch (java.io.FileNotFoundException exception){
            return false;

        }

        return true;
    }

    public static void fileExcecute(String[] file) throws IOException //checks if file exists or not
    {
        try {
            Runtime runTime = Runtime.getRuntime();
            Process process = runTime.exec(file);
            process.waitFor();
        } catch (Exception q) {

        }

    }

    public static String customDate(String date, String oldDirectory, String newDirectory) throws IOException //needed to allow TAGs imperial laws
    {

        String VM = "\\"; 
        VM = VM.substring(0);
        char VMq = '"';
        String tab = "	";

        ArrayList<String> oldFile = new ArrayList<String>();

        oldFile = Importer.importBasicFile(oldDirectory);

        FileOutputStream fileOut= new FileOutputStream(newDirectory);
        PrintWriter out = new PrintWriter(fileOut);

        String tmpDate = date.replace(".",",");

        int year = Integer.parseInt(tmpDate.split(",")[0]);
        int month = Integer.parseInt(tmpDate.split(",")[1]);
        int day = Integer.parseInt(tmpDate.split(",")[2]);

        String bookmark = "converted";
        String bookmarkName = "converted_bookmark_name";
        String bookmarkDesc = "converted_bookmark_info";
        String image = "GFX_pick_era_image_4";

        if (year >= 769) {
            bookmark = "bm_charlemagne";
            bookmarkName = "BM_CHARLEMAGNE_ERA";
            bookmarkDesc = "BM_CHARLEMAGNE_ERA_INFO";
            image = "GFX_pick_era_image_1";
        }

        if (year >= 867) {
            bookmark = "bm_the_old_gods";
            bookmarkName = "BM_THE_OLD_GODS_ERA";
            bookmarkDesc = "BM_THE_OLD_GODS_ERA_INFO";
            image = "GFX_pick_era_image_2";
        }

        if (date.equals("936.8.7")) {
            bookmark = "bm_otto_the_first";
            bookmarkName = "OTTO_THE_FIRST_ERA";
            bookmarkDesc = "OTTO_THE_FIRST_ERA_INFO";
            image = "GFX_pick_era_image_6";
        }

        if ((year >= 1066 && month >= 9 && day >= 15) || year >= 1067) {
            bookmark = "bm_fate_of_england";
            bookmarkName = "BM_FATE_OF_ENGLAND_ERA";
            bookmarkDesc = "BM_FATE_OF_ENGLAND_ERA_INFO";
            image = "GFX_pick_era_image_3";
        }

        if (year >= 1337) {
            bookmark = "bm_100_years_war";
            bookmarkName = "BM_100_YEARS_WAR_ERA";
            bookmarkDesc = "BM_100_YEARS_WAR_ERA_INFO";
            image = "GFX_pick_era_image_5";
        }

        int aqq = 0;

        while (aqq < oldFile.size()) {
            if (oldFile.get(aqq).contains("converted = ")) {
                String newTitle = oldFile.get(aqq).replace("converted = ",bookmark);
                out.println (newTitle);
            }

            else if (oldFile.get(aqq).contains("converted_bookmark_name")) {
                String newName = oldFile.get(aqq).replace("converted_bookmark_name",bookmarkName);
                out.println (newName);
            }

            else if (oldFile.get(aqq).contains("converted_bookmark_info")) {
                String newInfo = oldFile.get(aqq).replace("converted_bookmark_info",bookmarkDesc);
                out.println (newInfo);
            }

            else if (oldFile.get(aqq).contains("100.1.1")) {
                String newDate = oldFile.get(aqq).replace("100.1.1",date);
                out.println (newDate);
            } 

            else if (oldFile.get(aqq).contains("GFX_pick_era_image_4")) {
                String newDate = oldFile.get(aqq).replace("GFX_pick_era_image_4",image);
                out.println (newDate);
            } else {
                out.println (oldFile.get(aqq));
            }

            aqq = aqq + 1;

        }

        out.flush();
        fileOut.close();

        return "a";
    }

    public static String checkGovList (String gov, ArrayList<String> govMap) throws IOException //Imports government mappings
    {

        int flag = 0;

        boolean endOrNot = true;

        int aqq = 0;

        String qaaa;
        String output = gov; //backup in case there is no mapping

        String rank = "k"; //may be implemented in the future

        try {
            while (endOrNot = true){

                qaaa = govMap.get(aqq);

                if (qaaa.split(",")[0].equals(gov)){
                    if (qaaa.split(",").length == 2) { //if no rank requirement, convert
                        endOrNot = false;
                        output = qaaa.split(",")[1];
                    } else if (qaaa.split(",").length == 3) {
                        if (qaaa.split(",")[2].equals(rank)) { //if ranks match, convert
                            endOrNot = false;
                            output = qaaa.split(",")[1];
                        }
                    }

                }
                aqq = aqq + 1;

            }

        }catch (java.lang.IndexOutOfBoundsException exception){
            endOrNot = false;

        }   

        return output;

    }

    public static String calcDynID (String id) //gives all IR character dynasties + 700000000 to prevent conflict
    {
        if (!id.equals("noDynasty")) {
            if (id.length() >= 10) {
                id = id.substring(0,9);
            }
            id = Integer.toString(Integer.parseInt(id) + 700000000);
        }

        return id;
    }

    public static String calcCharID (String id) //gives all IR character dynasties + 700000000 to prevent conflict
    {
        id = Integer.toString(1000000 + Integer.parseInt(id));

        return id;

    }

    public static String calcHead (ArrayList<String[]> impCharInfoList, String dynasty) //calculates which character is the head of a dynasty
    {
        int aqq = 1;
        String[] dynastyList = dynasty.split("~");
        String head = dynastyList[0];
        int age = Integer.parseInt(impCharInfoList.get(Integer.parseInt(head))[3]);
        String sex = impCharInfoList.get(Integer.parseInt(head))[4];
        while (aqq > dynastyList.length) {
            String newHead = dynastyList[aqq];
            int newAge = Integer.parseInt(impCharInfoList.get(Integer.parseInt(newHead))[3]);
            if (newAge < age) { //if newHead is older then the current head and is male, newHead becomes the head
                String newSex = impCharInfoList.get(Integer.parseInt(newHead))[4];
                if (newSex.equals("m")) {
                    head = newHead;
                }
            }
            aqq = aqq + 1;

        }

        return head;

    }

    public static String deriveRgbFromHsv (String color) //Converts HSV to RGB, adapted from commonItems
    {

        {
            float h = Float.parseFloat(color.split(" ")[0]);
            float s = Float.parseFloat(color.split(" ")[1]);
            float v = Float.parseFloat(color.split(" ")[2]);

            float r = 0;
            float g = 0;
            float b = 0;
            if (s == 0.0f) // achromatic (grey)
            {
                r = g = b = v;
            }
            else
            {
                if (h >= 1.0f)
                    h = 0.0f;
                int sector = (int)((h * 6.0f));
                float fraction = h * 6.0f - (float)(sector);
                float p = v * (1 - s);
                float q = v * (1 - s * fraction);
                float t = v * (1 - s * (1 - fraction));
                switch (sector)
                {
                    case 0:
                    r = v;
                    g = t;
                    b = p;
                    break;
                    case 1:
                    r = q;
                    g = v;
                    b = p;
                    break;
                    case 2:
                    r = p;
                    g = v;
                    b = t;
                    break;
                    case 3:
                    r = p;
                    g = q;
                    b = v;
                    break;
                    case 4:
                    r = t;
                    g = p;
                    b = v;
                    break;
                    case 5:
                    r = v;
                    g = p;
                    b = q;
                    break;
                    default:
                }
            }
            r *= 255;
            g *= 255;
            b *= 255;
            color = r+" "+g+" "+b;
        }

        return color;

    }

    public static String fixWhite (String color) //prevents colors from being 255 255 255
    {
        if (color.split(" ")[0].contains("255") || color.split(" ")[0].contains("254")) {
            if (color.split(" ")[1].contains("255") || color.split(" ")[1].contains("254")) {
                if (color.split(" ")[2].contains("255") || color.split(" ")[2].contains("254")) {
                    color = color.replace("255","254");
                }
            }
        }
        return color;

    }

    public static String[] eastWestNames (String eastWest, String[] loc) throws IOException
    {
        String[] empire = new String[2];

        empire[0] = eastWest+" "+loc[0];
        empire[1] = eastWest+" "+loc[1];
        if (empire[0].contains("Republic")) {
            empire[0] = empire[0].replace("Republic","Empire");
            empire[1] = empire[0].replace("Republic","Empire");
        }
        else if (empire[0].contains("Kingdom")) {
            empire[0] = empire[0].replace("Kingdom","Empire");
            empire[1] = empire[0].replace("Kingdom","Empire");
        }
        else if (!empire[0].contains("Empire")) {
            empire[0] = eastWest+" "+loc[1];
            empire[0] = empire[0] + " Empire";
        }

        return empire;

    }

    

    public static String eastColor (String overlordColor) //Generates color for easternEmpire
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0])); //Red
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1])); //Green
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2])); //Blue

        double cTot = c1 + c2 + c3;
        double rPercent = (c1 / cTot) * 100;
        double bPercent = (c3 / cTot) * 100;

        c2 = c2 - 50;
        if (rPercent <= 25 && bPercent >= 50){ //if base country is blue, make purple rather then dark blue
            c1 = c1 + 50;
            c3 = c3 - 50;
        } else {
            c1 = c1 - 50;
            c3 = c3 + 50;
        }
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static String westColor (String overlordColor) //Generates color for westernEmpire
    {

        String[] overlordColorSplit = overlordColor.split(" ");
        int c1 = (int) (Integer.parseInt(overlordColorSplit[0]) + 50);
        int c2 = (int) (Integer.parseInt(overlordColorSplit[1]) + 50);
        int c3 = (int) (Integer.parseInt(overlordColorSplit[2]) + 50);
        if (c1 < 0) {
            c1 = 0;
        }
        if (c2 < 0) {
            c2 = 0;
        }
        if (c3 < 0) {
            c3 = 0;
        }
        if (c1 > 255) {
            c1 = 255;
        }
        if (c2 > 255) {
            c2 = 255;
        }
        if (c3 > 255) {
            c3 = 255;
        }
        String color = Integer.toString(c1) + " " + Integer.toString(c2) + " " + Integer.toString(c3); 

        return color;

    }

    public static boolean isWithinColorRange (String color1, String color2, int range) //Checks if RGB color falls within the specified range
    {

        boolean yn = false;
        try {
            if (color1.equals("none") || color2.equals("none")) {
                return yn;
            }
            color1 = color1.replace(" ",",");
            color2 = color2.replace(" ",",");
            color1 = color1.replace("rgb(","");
            color2 = color2.replace("rgb(","");
            color1 = color1.replace(")","");
            color2 = color2.replace(")","");
            String[] color1Split = color1.split(",");
            double c1a = (double) (Double.parseDouble(color1Split[0]));
            double c2a = (double) (Double.parseDouble(color1Split[1]));
            double c3a = (double) (Double.parseDouble(color1Split[2]));

            String[] color2Split = color2.split(",");
            double c1b = (double) (Double.parseDouble(color2Split[0]));
            double c2b = (double) (Double.parseDouble(color2Split[1]));
            double c3b = (double) (Double.parseDouble(color1Split[2]));

            double range1 = c1a - c1b;
            if (range1 < 0) {
                range1 = range1 * -1;
            }
            double range2 = c2a - c2b;
            if (range2 < 0) {
                range2 = range2 * -1;
            }
            double range3 = c3a - c3b;
            if (range3 < 0) {
                range3 = range3 * -1;
            }

            if (range1 <= range && range2 <= range && range3 <= range) {
                yn = true;
            }
        } catch(Exception e) {

        }

        return yn;

    }

    public static boolean isWithinColorRatio (String color1, String color2, int range) //Checks if RGB color falls within the specified ratio
    {

        boolean yn = false;
        try {
            if (color1.equals("none") || color2.equals("none")) {
                return yn;
            }
            color1 = color1.replace(" ",",");
            color2 = color2.replace(" ",",");
            color1 = color1.replace("rgb(","");
            color2 = color2.replace("rgb(","");
            color1 = color1.replace(")","");
            color2 = color2.replace(")","");

            String[] color1Split = color1.split(",");
            double c1a = (double) (Double.parseDouble(color1Split[0]));
            double c2a = (double) (Double.parseDouble(color1Split[1]));
            double c3a = (double) (Double.parseDouble(color1Split[2]));

            String[] color2Split = color2.split(",");
            double c1b = (double) (Double.parseDouble(color2Split[0]));
            double c2b = (double) (Double.parseDouble(color2Split[1]));
            double c3b = (double) (Double.parseDouble(color2Split[2]));

            double tot1 = c1a + c2a + c3a;
            double tot2 = c1b + c2b + c3b;

            double r1Ratio = c1a / tot1 * 100;
            double r2Ratio = c1b / tot2 * 100;

            double g1Ratio = c2a / tot1 * 100;
            double g2Ratio = c2b / tot2 * 100;

            double b1Ratio = c3a / tot1 * 100;
            double b2Ratio = c3b / tot2 * 100;

            double range1 = r1Ratio - r2Ratio;
            if (range1 < 0) {
                range1 = range1 * -1;
            }
            double range2 = g1Ratio - g2Ratio;
            if (range2 < 0) {
                range2 = range2 * -1;
            }
            double range3 = b1Ratio - b2Ratio;
            if (range3 < 0) {
                range3 = range3 * -1;
            }

            if (range1 <= range && range2 <= range && range3 <= range) {
                yn = true;
            }
        } catch(Exception e) {

        }

        return yn;

    }

    public static boolean isBright (String color1, int range) //Checks if RGB color is at least x bright
    {

        boolean yn = false;
        if (color1.equals("none")) {
            return yn;
        }
        color1 = color1.replace(" ",",");
        color1 = color1.replace("rgb(","");
        color1 = color1.replace(")","");
        String[] color1Split = color1.split(",");
        double c1 = (double) (Double.parseDouble(color1Split[0]));
        double c2 = (double) (Double.parseDouble(color1Split[1]));
        double c3 = (double) (Double.parseDouble(color1Split[2]));

        if (c1 >= range) {
            yn = true;
        }
        else if (c2 >= range) {
            yn = true;
        }
        else if (c3 >= range) {
            yn = true;
        }

        return yn;

    }

    public static int[] addSubjectsToSize (ArrayList<String> impSubjectInfo, int[] ck2LandTot) throws IOException 
    //Recalculates sizes of all tags to accomodate for owned subjects
    {

        int count = 1;

        while (count<impSubjectInfo.size()) {
            int overlord = Integer.parseInt(impSubjectInfo.get(count).split(",")[0]);
            int subject = Integer.parseInt(impSubjectInfo.get(count).split(",")[1]);
            String overlordGov = impSubjectInfo.get(count).split(",")[2];
            if (overlordGov.equals ("feudatory") || overlordGov.equals ("satrapy") || overlordGov.equals ("client_state")) {
                if (overlord != 9999) { //if there's an overlord, add subject's size to overlord's size
                    ck2LandTot[overlord] = ck2LandTot[overlord]+ck2LandTot[subject];
                }
            }
            count = count + 1;
        }

        return ck2LandTot;

    }

    public static String[][] annexSubjects(int country,int totCountries,String[][] ck2ProvInfo,ArrayList<String> impSubjectInfo) throws IOException
    //Annexes all subjects into the overlord
    {
        int count = 1;

        while (count<impSubjectInfo.size()) {

            String overlord = impSubjectInfo.get(count).split(",")[0];
            int overlordID = Integer.parseInt(overlord);
            //System.out.println("Overlord " + overlordID + " and Subject " + impSubjectInfo.get(count).split(",")[1] + " looking for " + country);
            if (overlordID == country) {
                String subject = impSubjectInfo.get(count).split(",")[1];
                int provCount = 1;
                while (provCount < ck2ProvInfo[0].length) {
                    if (ck2ProvInfo[0][provCount] != null) {

                        if (ck2ProvInfo[0][provCount].equals(subject)) {
                            //System.out.println(ck2ProvInfo[0][provCount] + " set to " + overlord);
                            ck2ProvInfo[0][provCount] = overlord;
                        }
                    }
                    provCount = provCount + 1;
                }
            }
            count = count + 1; 
        }

        return ck2ProvInfo;
    }

    public static boolean checkForInvictus(ArrayList<String> modDirs) throws IOException
    //Checks whether or not user is using Imperator Invictus
    {
        int count = 0;
        while (count < modDirs.size()) {
            String currentModDir = modDirs.get(count);
            String currentModDescDir = currentModDir+"/descriptor.mod";
            //System.out.println(currentModDir);
            //System.out.println(checkForInvictusID(currentModDir));
            if (checkForInvictusID(currentModDir)) { //check for Invictus or HMOv2 Steam ID
                return true;
            } else { //Check for manual Invictus installations
                File currentModDescFile = new File(currentModDescDir);
                if (currentModDescFile.exists()) {
                    FileInputStream fileIn= new FileInputStream(currentModDescDir);
                    Scanner scnr= new Scanner(fileIn);
                    String line = scnr.nextLine();
                    boolean endOrNot = true;
                    try {
                        while (endOrNot = true) {
                            if (checkForInvictusID(line)) {
                                return true;
                            }
                            else {
                                line = scnr.nextLine(); 
                            }
                        }
                    }
                    catch (Exception e) {
                        endOrNot = false;
                    }

                }

            }
            count = count + 1;
        }

        return false;
    }

    public static boolean checkForInvictusID(String modDir) throws IOException
    //Check given directory to see whether or not it has an Invictus-based mod ID in it.
    //Will create false positive if a user has a file with invModID in name, but shouldn't be an issue due to how long invModID is.
    {
        ArrayList<String> invModIDList = new ArrayList(); //List of all known Invictus-based mod ID's
        invModIDList.add("2532715348");
        invModIDList.add("2723164890");
        invModIDList.add("2971810224");
        invModIDList.add("2651142140");
        invModIDList.add("2765744228");
        invModIDList.add("2856497654");

        int count = 0;
        while (count < invModIDList.size()) {
            if (modDir.contains(invModIDList.get(count))) {
                return true;
            }
            count = count + 1;
        }

        return false;
    }

    public static void setTechYear(String startDate, String directory) throws IOException
    //Sets the start year of all technology to align with startDate
    {
        String textToReplace = "[startdate]";
        String techDir = directory+"/history/technology/";
        String techDir2 = directory+"/common/defines/tech_defines.lua";
        startDate = startDate.replace(".",",");
        String startYear = startDate.split(",")[0];
        File techFileInfo = new File (techDir);
        String[] techFileList = techFileInfo.list();
        if (techFileList != null) {
            int aq2 = 0;
            while (aq2 < techFileList.length) {
                String techFile = techFileList[aq2];
                Output.replaceInFile(textToReplace,startYear,techDir+techFile);
                System.out.println("Replaced "+textToReplace+" with "+startYear+" in "+techDir+techFile);
                aq2 = aq2 + 1;
            }

        }
        Output.replaceInFile(textToReplace,startYear,techDir2);

    }

    public static Pop getPopFromID(int id, ArrayList<Pop> popList)
    //Select a pop from it's ID
    {
        int count = 0;
        while (count < popList.size()) {
            Pop selectedPop = popList.get(count);
            if (selectedPop.getID() == id) {
                return selectedPop;
            }
            count = count + 1;
        }

        return null; //if no pop found, return null
    }

    public static String cutQuotes(String word)
    //Cut off quotes ("Bob" becomes Bob)
    {
        String newWord = word.substring(1,word.length()-1);

        return newWord; //if no pop found, return null
    }

    public static ArrayList<String> condenseArrayStr(String[] longArray)
    //Condenses a tag,number array into an ArrayList such that there are no repeat tags
    {
        //longArray;
        int count = 0;
        ArrayList<Integer> checkedCount = new ArrayList<Integer>();
        ArrayList<String> shortArray = new ArrayList<String>();
        //System.out.println("__________ Start __________");
        while (count < longArray.length) {
            String element = longArray[count];
            String identifier = element.split(",")[0];
            //System.out.println(element);
            String numStr = element.split(",")[1];
            if (numStr.equals("null")) {
                numStr = "0";
            }
            int elementNum = Integer.parseInt(numStr);
            //System.out.println("Checking ID "+identifier+" with "+elementNum+" elements");

            int count2 = count+1;
            while (count2 < longArray.length) {
                String element2 = longArray[count2];
                String identifier2 = element2.split(",")[0];
                //System.out.println("Checking ID "+identifier2+" with "+element2.split(",")[1]+" elements");
                if (identifier.equals(identifier2)) {
                    String numStr2 = element2.split(",")[1];
                    if (numStr2.equals("null")) {
                        numStr2 = "0";
                    }
                    int elementNum2 = Integer.parseInt(numStr2);
                    elementNum = elementNum + elementNum2;
                    checkedCount.add(count2);
                    //System.out.println(identifier+" and "+identifier2+" match, adding "+element+" and "+element2);
                }
                count2 = count2 + 1;
            }
            checkedCount.add(count);
            String shortArrayItem = identifier+","+Integer.toString(elementNum);
            shortArray.add(shortArrayItem);
            count = count + 1;
            if (checkedCount.contains(count)) {
                while (checkedCount.contains(count)) {
                    count = count + 1;
                }
            }
            //System.out.println("__________ NewLine __________");
        }

        return shortArray;

    }

    public static String[] countPops(ArrayList<Pop> pops, String type) {
        int count = 0;
        String[] longArray;
        String longText = "";
        while (count < pops.size()) {
            String popType = "debug";
            if (type.equals("culture")) {
                popType = pops.get(count).getCulture();
            }
            else if (type.equals("religion")) {
                popType = pops.get(count).getReligion();
            }
            else if (type.equals("culture")) {
                popType = pops.get(count).getCulture();
            }
            else if (type.equals("cultureAndReligion")) {
                popType = pops.get(count).getCulture()+"---"+pops.get(count).getReligion();
            }
            else if (type.equals("tag")) {
                popType = pops.get(count).getTag();
            }
            else if (type.equals("type")) {
                popType = pops.get(count).getType();
            }
            else if (type.equals("cultureAndReligionAndType")) {
                popType = pops.get(count).getCulture()+"---"+pops.get(count).getReligion()+"---"+pops.get(count).getType();
            }
            else if (type.equals("cityStatus")) {
                popType = pops.get(count).getCityStatus();
                if (popType.equals("metropolis")) {
                    popType = "city"; //combine cities and metropoli for the purposes of city-generation
                }
            }
            else if (type.equals("monument")) {
                popType = pops.get(count).getMonument();
            }

            if (count == 0) {
                longText = popType+","+1;
            } else {
                longText = longText +"~"+popType+","+1;
            }
            count = count + 1;
        }
        longArray = longText.split("~");

        return longArray;
    }

    public static ArrayList<String> convertPopCountToRatios(ArrayList<String> condensedArray) {
        int count = 0;
        int popTotal = 0;
        String longText = "";
        ArrayList<String> newArray = new ArrayList<String>();
        while (count < condensedArray.size()) { //getPopTotal
            String numberStr = condensedArray.get(count).split(",")[1];
            popTotal = popTotal + Integer.parseInt(numberStr);
            count = count + 1;
        }
        count = 0;
        while (count < condensedArray.size()) { //getRatio
            String[] info = condensedArray.get(count).split(",");
            String numberStr = info[1];
            double numberDouble = Double.parseDouble(numberStr);
            double ratio = numberDouble / popTotal;
            newArray.add(info[0]+","+ratio);
            count = count + 1;
        }

        return newArray;
    }

    public static ArrayList<String> reallocatePops(ArrayList<String> ratioArray,int totalPops) {
        int count = 0;
        int popTotal = 0;
        ArrayList<String> newArray = new ArrayList<String>();
        while (count < ratioArray.size()) { 
            String[] info = ratioArray.get(count).split(",");
            double ratio = Double.parseDouble(info[1]);
            double newNumber = ratio * totalPops;
            int newNumberInt = round(newNumber);
            newArray.add(info[0]+","+newNumberInt);
            popTotal = popTotal + newNumberInt;
            count = count + 1;
        }

        //System.out.println("PopTotal is "+popTotal);
        //System.out.println("totalPops is "+totalPops);

        if (popTotal > totalPops) { //If extra pops were added due to rounding, trim array
            String majorityType = getMajority(newArray);
            int count2 = 0;
            //while (count2 < count-1) { //first purge pops below size 1
            while (count2 < newArray.size()) { //first purge pops below size 1
                String[] info = newArray.get(count2).split(",");
                int popNumber = Integer.parseInt(info[1]);
                if (popNumber <= 0) {
                    newArray.remove(newArray.get(count2));
                    count2 = count2 - 1;
                }
                count2 = count2+1;
            }
            if (popTotal > totalPops) { //if still over limit, trim non-majority pops
                count2 = 0;
                while (count2 < popTotal-1) {
                    //while (count2 < newArray.size()) {
                    //System.out.println(count2+" "+(popTotal-1));
                    //System.out.println(newArray.get(count2));
                    String[] info = newArray.get(count2).split(",");
                    if (!info[0].equals(majorityType)) {
                        //System.out.println("Removing 1 "+info[0]);
                        int popNumber = Integer.parseInt(info[1]) - 1;
                        if (popNumber < 1) {
                            newArray.remove(newArray.get(count2));
                        }
                        //newArray.remove(newArray.get(count2));
                        popTotal = popTotal - 1;
                        //System.out.println("PopTotal is "+popTotal);
                        //System.out.println("totalPops is "+totalPops);
                        if (popTotal == totalPops) {
                            //System.out.println("Returning");
                            return newArray;
                        }
                    }
                    count2 = count2+1;
                }
            }
            if (popTotal > totalPops) { //if still over limit, trim into majority
                count2 = 0;
                while (count2 < popTotal-1) {
                    String[] info = newArray.get(count2).split(",");
                    newArray.remove(newArray.get(count2));
                    popTotal = popTotal - 1;
                    if (popTotal == totalPops) {
                        return newArray;
                    }
                    count2 = count2+1;
                }
            }
        }

        return newArray;
    }

    public static int round(double startNumber) {
        String[] startNumStr = Double.toString(startNumber).replace(".",",").split(",");
        startNumStr[1] = startNumStr[1].substring(0,1);
        int decimalInt = Integer.parseInt(startNumStr[1]);
        int mainInt = Integer.parseInt(startNumStr[0]);
        if (decimalInt >= 5) {
            mainInt = mainInt + 1;
        }

        return mainInt;
    }

    public static int roundUp(double startNumber) {
        String[] startNumStr = Double.toString(startNumber).replace(".",",").split(",");
        startNumStr[1] = startNumStr[1].substring(0,1);
        int decimalInt = Integer.parseInt(startNumStr[1]);
        int mainInt = Integer.parseInt(startNumStr[0]);
        if (decimalInt > 0) {
            mainInt = mainInt + 1;
        }

        return mainInt;
    }

    public static int getProvByID(ArrayList<Provinces> provList,int id) {
        int count = 0;
        while (count < provList.size()) { 
            Provinces province = provList.get(count);
            int provID = province.getID();
            if (provID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }
    
    public static int getArrayByID(ArrayList<String[]> provList,int id) {
        int count = 0;
        while (count < provList.size()) { 
            String[] province = provList.get(count);
            int provID = Integer.parseInt(province[0]);
            if (provID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }
    
    public static int getCountryByID(ArrayList<Country> countryList,int id) {
        int count = 0;
        while (count < countryList.size()) { 
            Country country = countryList.get(count);
            int countryID = country.getID();
            if (countryID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }
    
    public static int getDeityByID(ArrayList<Deity> deityList,int id) {
        int count = 0;
        while (count < deityList.size()) { 
            Deity selectedDeity = deityList.get(count);
            int deityID = selectedDeity.getID();
            if (deityID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }
    
    public static int getMonumentByID(ArrayList<Monument> monList,int id) {
        int count = 0;
        while (count < monList.size()) { 
            Monument mon = monList.get(count);
            int monID = mon.getID();
            if (monID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }
    
    public static int getMonumentByOldID(ArrayList<Monument> monList,int id) {
        int count = 0;
        while (count < monList.size()) { 
            Monument mon = monList.get(count);
            int monID = mon.getOldID();
            if (monID == id) {
                return count;
            }
            count = count + 1;
        }

        return 0;
    }

    public static int calcAllocatedPops(int baPopTotal, float totalProvPops)
    {

        //int baPopTotal = 34822;
        //float totalProvPops = 200;
        int globalPopTotal = 6000;
        //System.out.println("------3------");
        float provinceRatio = (totalProvPops) /baPopTotal;
        //System.out.println(provinceRatio);
        int provinceTotal = Processing.round(provinceRatio * globalPopTotal);

        return provinceTotal;
    }

    public static String getMajority(ArrayList<String> popList)
    {

        int count = 0;
        int highestNumber =  0;
        String highestPop = popList.get(count).split(",")[0];
        while (count < popList.size()) {
            String[] popInfo = popList.get(count).split(",");
            int popNumber = Integer.parseInt(popInfo[1]);
            if (popNumber > highestNumber) {
                highestNumber = popNumber;
                highestPop = popInfo[0];
            }
            count = count + 1;
        }

        return highestPop;
    }
    
    public static String getMajorityExcludingNoneKey(ArrayList<String> popList,String noneKey) //Will exclude specified noneKey from the calculations
    {

        int count = 0;
        int highestNumber =  0;
        String highestPop = noneKey; //by default return with noneKey
        while (count < popList.size()) {
            String[] popInfo = popList.get(count).split(",");
            String popType = popInfo[0];
            if (!popType.equals(noneKey)) {
                int popNumber = Integer.parseInt(popInfo[1]);
                if (popNumber > highestNumber) {
                    highestNumber = popNumber;
                    highestPop = popInfo[0];
                }
            }
            
            count = count + 1;
        }

        return highestPop;
    }

    public static float[] getTypeRatios(ArrayList<Pop> popList)
    {

        int count = 0;
        float nobleCount = 0;
        float citizenCount = 0;
        float freemenCount = 0;
        float slaveCount = 0;
        while (count < popList.size()) {
            //String[] popInfo = popList.get(count).split(",");
            String popInfo = popList.get(count).getType();
            if (popInfo.equals("nobles")) {
                nobleCount = nobleCount + 1;
            }
            else if (popInfo.equals("citizen")) {
                citizenCount = citizenCount + 1;
            }
            else if (popInfo.equals("freemen")) {
                freemenCount = freemenCount + 1;
            }
            else if (popInfo.equals("slaves")) {
                slaveCount = slaveCount + 1;
            }
            count = count + 1;
        }

        float tribesmenCount = ((((count - nobleCount) - citizenCount) - freemenCount) - slaveCount);

        float nobleRatio = nobleCount/count;
        float citizenRatio = citizenCount/count;
        float freemenRatio = freemenCount/count;
        float slaveRatio = slaveCount/count;
        float tribesmenRatio = tribesmenCount/count;

        //String[] typeRatios = new String[5];
        float[] typeRatios = new float[5];
        //typeRatios[0] = Float.toString(nobleRatio);
        //typeRatios[1] = Float.toString(citizenRatio);
        //typeRatios[2] = Float.toString(freemenRatio);
        //typeRatios[3] = Float.toString(slaveRatio);
        //typeRatios[4] = Float.toString(tribesmenRatio);
        typeRatios[0] = nobleRatio;
        typeRatios[1] = citizenRatio;
        typeRatios[2] = freemenRatio;
        typeRatios[3] = slaveRatio;
        typeRatios[4] = tribesmenRatio;

        return typeRatios;
    }

    public static String genNewTag(int countryID)
    {
        int count = 0;
        String part3 = "A";
        String part2 = "A";
        String part1 = "1";
        int count2 = 0;
        int count3 = 0;
        int count4 = 9;

        while (count < countryID) {
            if (count3 > 24) {
                count4 = count4 - 1;
                count3 = 0;
            } else if (count2 > 24) {
                count3 = count3 + 1;
                count2 = 0;
            } else {
                count2 = count2 + 1;
            }
            count = count + 1;

            //if (count 
        }
        
        part1 = getLetterFromNumber(count2);
        part2 = getLetterFromNumber(count3);
        part3 = Integer.toString(count4);
        //String newTagID = part3+part2+part1;
        String newTagID = part2+part3+part1;

        return newTagID;
    }

    public static String getLetterFromNumber(int count2)
    {
        String part3 = "A";
        if (count2 == 1) {
            part3 = "B";
        } else if (count2 == 2) {
            part3 = "C";
        } else if (count2 == 3) {
            part3 = "D";
        } else if (count2 == 4) {
            part3 = "E";
        } else if (count2 == 5) {
            part3 = "F";
        } else if (count2 == 6) {
            part3 = "G";
        } else if (count2 == 7) {
            part3 = "H";
        } else if (count2 == 8) {
            part3 = "I";
        } else if (count2 == 9) {
            part3 = "J";
        } else if (count2 == 10) {
            part3 = "K";
        } else if (count2 == 11) {
            part3 = "L";
        } else if (count2 == 12) {
            part3 = "M";
        } else if (count2 == 13) {
            part3 = "N";
        } else if (count2 == 14) {
            part3 = "O";
        } else if (count2 == 15) {
            part3 = "P";
        } else if (count2 == 16) {
            part3 = "Q";
        } else if (count2 == 17) {
            part3 = "R";
        } else if (count2 == 18) {
            part3 = "S";
        } else if (count2 == 19) {
            part3 = "T";
        } else if (count2 == 20) {
            part3 = "U";
        } else if (count2 == 21) {
            part3 = "V";
        } else if (count2 == 22) {
            part3 = "W";
        } else if (count2 == 23) {
            part3 = "X";
        } else if (count2 == 24) {
            part3 = "Y";
        } else if (count2 == 25) {
            part3 = "Z";
        }

        return part3;
    }
    
    public static ArrayList<String> generateProvinceFile(ArrayList<Provinces> irProvinceList)
    {
        int count = 1;
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("#Converted Provinces");
        String tab = "	";
        String quote = '"'+"";
        while (count < irProvinceList.size()) {
            Provinces irProv = irProvinceList.get(count);
            
            int provID = irProv.getID();
            if (provID == 0) {
                //System.out.println("Province ID 0 at count "+count+", skipping");
                
            } else {
                String provCulture = irProv.getCulture();
                String provReligion = irProv.getReligion();
                String provCityStatus = irProv.getCityStatus();
                double provCivValue = irProv.getCivValue();
                if (provCityStatus.equals("metropolis")) {
                    provCityStatus = "city_metropolis";
                }
                //String provCulture = "roman";
                //String provReligion = "indo_iranian_religion";
            
                lines.add(provID+" = {");
                lines.add(tab+"barbarian_power=0");
                lines.add(tab+"civilization_value="+provCivValue);
                lines.add(tab+"culture="+quote+provCulture+quote);
                lines.add(tab+"province_rank="+quote+provCityStatus+quote);
                lines.add(tab+"religion="+quote+provReligion+quote);
                lines.add(tab+"terrain="+quote+irProv.getTerrain()+quote);
                lines.add(tab+"trade_goods="+quote+irProv.getTradeGood()+quote);
                //pops
                ArrayList<Pop> pops = irProv.getPops();
                int popCount = 0;
                if (pops != null) {
                
                    while (popCount < pops.size()) {
                        Pop selectedPop = pops.get(popCount);
                        String popCulture = selectedPop.getCulture();
                        String popReligion = selectedPop.getReligion();
                        //String popCulture = "roman"; //selectedPop.getCulture()
                        //String popReligion = "indo_iranian_religion"; //selectedPop.getReligion()
                        String popType = selectedPop.getType();
                        if (popType.equals("debug")) {
                            //System.out.println("Pop in province "+provID+" has no type, setting type to slaves");
                            popType = "slaves";
                        }
                        lines.add(tab+popType+"={");
                        lines.add(tab+tab+"amount=1");
                        lines.add(tab+tab+"culture="+popCulture);
                        lines.add(tab+tab+"religion="+popReligion);
                        lines.add(tab+"}");
                        popCount = popCount + 1;
                    }
                }
            
                lines.add("}");
            }
            count = count + 1;
        }

        return lines;
    }
    
    public static ArrayList<String> generateCountryFile(ArrayList<Country> irCountryList, ArrayList<Provinces> irProvinceList, ArrayList<String> OldLines)
    {
        int count = 0;
        ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean countrySectionFlag = false;
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            if (selectedLine.equals("country = {")) {
                countrySectionFlag = true;
            }
            
            if (countrySectionFlag && selectedLine.equals(tab+"}")) {
                lines.add("#Converted Countries");
                //lines.add("country = {");
                //lines.add(tab+"countries = {");
        
                while (count < irCountryList.size()) {
                    Country irCountry = irCountryList.get(count);
                    boolean hasLand = irCountry.getHasLand();
                    if (hasLand) {
                        String id = Integer.toString(irCountry.getID()); //original tag used in BA
                        String updatedTag = irCountry.getUpdatedTag(); //converted tag
                        ArrayList<Integer> ownedProvs = getAllOwnedProvinces(irProvinceList,id);
                        String government = irCountry.getGovernment();
                        String culture = irCountry.getCulture();
                        String religion = irCountry.getReligion();
                        String capital = irCountry.getCapital();
                        ArrayList<Integer> families = irCountry.getMajorFamilies();
                        boolean antagonist = irCountry.getAntagonist();
                        ArrayList<String[]> laws = irCountry.getLaws();
                        //String culture = "roman"; //testing
                        //String religion = "indo_iranian_religion"; //testing

                        lines.add(tab+tab+updatedTag+" = {");
                        int familyCount = 0;
                        int familySize = 0;
                        if (families != null) {
                            familySize = families.size();
                        }
                        while (familyCount < familySize) {
                            int family = families.get(familyCount);
                            lines.add(tab+tab+tab+"family = "+family);
                            familyCount = familyCount + 1;
                        }
                            
                        lines.add(tab+tab+tab+"government = "+government);
                        lines.add(tab+tab+tab+"primary_culture = "+culture);
                        lines.add(tab+tab+tab+"religion = "+religion);
                        if (laws != null) {
                            int lawCount = 0;
                            while (lawCount < laws.size()) {
                                String[] law = laws.get(lawCount);
                                String lawTail = cutQuotes(law[1]);
                                lines.add(tab+tab+tab+law[0]+" = "+lawTail);
                                lawCount = lawCount + 1;
                            }
                        }
                        if (!capital.equals("99999")) { //in case a country has no capital, don't output
                            lines.add(tab+tab+tab+"capital = "+capital);
                        }
                        if (antagonist) {
                            lines.add(tab+tab+tab+"is_antagonist = yes");
                        }
                        lines.add(tab+tab+tab+"own_control_core = {");
                        int provCount = 0;
                        if (ownedProvs != null) {
                            String ownedProvSTR = "#q";
                            while (provCount < ownedProvs.size()) {
                                String prov = Integer.toString(ownedProvs.get(provCount));
                                //System.out.println(prov);
                                if (ownedProvSTR.equals("#q")) {
                                    ownedProvSTR = prov;
                                } else {
                                    ownedProvSTR = ownedProvSTR + " " + prov;
                                }
                            
                                provCount = provCount + 1;
                            }
                            lines.add(tab+tab+tab+tab+ownedProvSTR);
                        }
                        lines.add(tab+tab+tab+"}");    
                        lines.add(tab+tab+"}");
                    }
            
            
                    count = count + 1;
                }
                lines.add(tab+"}");
                countrySectionFlag = false;
            } else {
                lines.add(selectedLine);
            }
            countOldFile = countOldFile + 1;
        }
        
        
        //lines.add("}");

        return lines;
    }
    
    public static ArrayList<String> generateMonumentFile(ArrayList<Provinces> irProvinceList, ArrayList<Monument> allMonuments)
    {
        int count = 1;
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("#Converted Monuments");
        String tab = "	";
        String quote = '"'+"";
        int offset = 10000; //offset all monument ID's by 10000 to avoid conflict with vanilla
        lines.add("provinces={");
        
        while (count < irProvinceList.size()) {
            Provinces irProv = irProvinceList.get(count);
            
            int provID = irProv.getID();
            if (provID == 0) {
                //System.out.println("Province ID 0 at count "+count+", skipping");
                
            } else {
                int provMonument = irProv.getMonument();
                if (provMonument > -1) {
                    int newProvMonument = provMonument;
                    int monIDInArray = getMonumentByOldID(allMonuments,provMonument);
                    Monument selectedMon = allMonuments.get(monIDInArray);
                    boolean isHistorical = selectedMon.getIsHistorical();
                    if (isHistorical) {
                        newProvMonument = selectedMon.getID();
                    } else {
                        newProvMonument = provMonument + offset;
                    }
                    lines.add(tab+provID+"={");
                    lines.add(tab+tab+"great_work="+newProvMonument);
                    lines.add(tab+"}");
                }
            
            }
            count = count + 1;
        }
        
        lines.add("}");
        lines.add(" ");
        lines.add("great_work_manager={");
        lines.add(tab+"great_works_database={");
        
        count = 0;
        while (count < allMonuments.size()) {
            Monument selectedMonument = allMonuments.get(count);
            boolean isHistorical = selectedMonument.getIsHistorical();
            if (!isHistorical) { //Monuments with vanilla counterparts don't get re-defined
                String key = selectedMonument.getKey();
                ArrayList<String[]> components = selectedMonument.getComponents();
                ArrayList<String[]> effects = selectedMonument.getEffects();
                String category = selectedMonument.getCategory();
                String name = selectedMonument.getName();
                if (name.equals("none")) {
                    name = key;
                }
                int oldID = selectedMonument.getOldID();
                int newID = oldID + offset;
                String baseline = tab+tab+tab;
                
                lines.add(tab+tab+newID+"={");
                
                lines.add(baseline+"dlc = "+quote+"Hellenistic World Flavor Pack"+quote);
                lines.add(baseline+"key="+key);
                lines.add(baseline+"great_work_state=great_work_state_completed");
                lines.add(baseline+"finished_date=450.10.1");
                lines.add(baseline+"great_work_category="+category);
                lines.add(baseline+"great_work_name={");
                lines.add(baseline+tab+"name="+name);
                lines.add(baseline+"}");
                lines.add(baseline+"great_work_components={");
                int count2 = 0;
                while (count2 < components.size()) {
                    String[] component = components.get(count2);
                    lines.add(baseline+tab+"{ great_work_module="+component[0]+" great_work_material="+component[1]+" }");
                    count2 = count2 + 1;
                }
                lines.add(baseline+"}");
                lines.add(baseline+"great_work_effect_selections={");
                count2 = 0;
                while (count2 < effects.size()) {
                    String[] effect = effects.get(count2);
                    lines.add(baseline+tab+"{ great_work_effect="+effect[0]+" great_work_effect_tier="+effect[1]+" }");
                    count2 = count2 + 1;
                    }
                lines.add(baseline+"}");
                lines.add(tab+tab+"}");
            }
            
            count = count + 1;
        }
        
        lines.add(tab+"}");
        lines.add("}");

        return lines;
    }
    
    public static ArrayList<Integer> getAllOwnedProvinces(ArrayList<Provinces> provList,String tag) {
        int count = 0;
        ArrayList<Integer> newArray = new ArrayList<Integer>();
        while (count < provList.size()) { 
            Provinces province = provList.get(count);
            String provTag = province.getOwner();
            int provID = province.getID();
            if (tag.equals(provTag)) {
                newArray.add(provID);
            }
            count = count + 1;
        }

        return newArray;
    }
    
    public static ArrayList<Provinces> convertAllPops(ArrayList<Provinces> provList,ArrayList<Country> baTagInfo,ArrayList<String> cultureMappings,
    ArrayList<String> religionMappings) throws IOException {
        int count = 0;
        while (count < provList.size()) { 
            Provinces province = provList.get(count);
            String provRegion = province.getRegion();
            String provArea = province.getArea();
            ArrayList<Pop> provPops = province.getPops();
            ArrayList<Pop> newProvPops = new ArrayList<Pop>();
            String baTagCulture = "none";
            int provOwner = Integer.parseInt(province.getOwner());
            if (provOwner != 9999) { //if uncolonized, don't get tagCulture
                Country baTag = baTagInfo.get(provOwner);
                baTagCulture = baTag.getCulture();
            }
            
            int popCount = 0;
            try {
                while (popCount < provPops.size()) {
                    Pop selectedPop = provPops.get(popCount);
                    String popCulture = selectedPop.getCulture();
                    String popReligion = selectedPop.getReligion();
                    popCulture = Output.paramMapOutput(cultureMappings,popCulture,baTagCulture,"date",popCulture,provRegion,provArea,"none","none");
                    popReligion = Output.paramMapOutput(religionMappings,popCulture,baTagCulture,"date",popReligion,provRegion,provArea,"none","none");
                    selectedPop.setCulture(popCulture);
                    selectedPop.setReligion(popReligion);
                    newProvPops.add(selectedPop);
                
                    popCount = popCount + 1;
                }
                province.replacePops(newProvPops);
            } catch (java.lang.NullPointerException exception) {
                
            }
            
            count = count + 1;
        }

        return provList;
    }
    
    public static ArrayList<Integer> getAllUncolonizedProvinces(ArrayList<Provinces> provList) {
        int count = 0;
        ArrayList<Integer> newArray = new ArrayList<Integer>();
        String uncolonizedDefault = "9999";
        while (count < provList.size()) { 
            Provinces province = provList.get(count);
            String provTag = province.getOwner();
            int provID = province.getID();
            if (provTag.equals(uncolonizedDefault)) {
                newArray.add(provID);
            }
            count = count + 1;
        }

        return newArray;
    }
    
    public static ArrayList<Integer> getAllProvincesInScope(ArrayList<Provinces> provList) { //get's all provinces which will be converted
        int count = 0;
        ArrayList<Integer> newArray = new ArrayList<Integer>();
        while (count < provList.size()) { 
            Provinces province = provList.get(count);
            String provTag = province.getOwner();
            int provID = province.getID();
            newArray.add(provID);
            count = count + 1;
        }

        return newArray;
    }
    
    public static boolean checkIntInList(ArrayList<Integer> convProvinces, int numberBeingChecked)
    {

        int count = 0;
        while (count < convProvinces.size()) {
            if (numberBeingChecked == (convProvinces.get(count))) {
                return true;
            }
            count = count + 1;
        }

        return false;
    }
    
    public static ArrayList<String> purgeVanillaSetup(ArrayList<Provinces> irProvinceList, ArrayList<String> OldLines) { 
        //Purges the vanilla tag setup of all provinces within conv scope
        int count = 0;
        ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean countrySectionFlag = false;
        boolean coreFlag = false;
        boolean oneLineFlag = false; //flag for when own_control_core is all on one line
        ArrayList<Integer> convProvinces = getAllProvincesInScope(irProvinceList);
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            String selectedLineNoComment = selectedLine.split(" #")[0];
            selectedLineNoComment = selectedLineNoComment.split("#")[0];
            if (selectedLine.equals("country = {")) {
                countrySectionFlag = true;
            }
            
            if (countrySectionFlag && selectedLineNoComment.contains("own_control_core")) {
                coreFlag = true;
                if (!selectedLine.contains("}")) {
                    lines.add(selectedLine);
                    countOldFile = countOldFile + 1;
                    selectedLine = OldLines.get(countOldFile);
                    oneLineFlag = false;
                } else if (selectedLine.contains("}")) {
                    oneLineFlag = true;
                    //System.out.println("OneLine at"+selectedLine);
                }
            }
            
            if (countrySectionFlag && coreFlag) {
                    selectedLineNoComment = selectedLine.split(" #")[0];
                    selectedLineNoComment = selectedLineNoComment.split("#")[0];
                    selectedLineNoComment = selectedLineNoComment.replace(tab," ");
                    String[] countryProvinces = selectedLineNoComment.split(" ");
                    int count2 = 0;
                    String newProvs = tab+tab+tab+tab;
                    while (count2 < countryProvinces.length) {
                        String selectedProv = countryProvinces[count2];
                        selectedProv = selectedProv.replace(tab,"");
                        try {
                            //if (!selectedProv.equals(" ") && selectedProv != null && !selectedProv.equals("}") && !selectedProv.equals("")) {
                                int selectedProvInt = Integer.parseInt(selectedProv);
                                boolean inConvScope = checkIntInList(convProvinces,selectedProvInt);
                                
                                String provOwner = "0";
                                int provArrayID = getProvByID(irProvinceList,selectedProvInt);
                                if (provArrayID > 0) {
                                    Provinces selectedProvDetail = irProvinceList.get(provArrayID);
                                    provOwner = selectedProvDetail.getOwner();
                                }
                                
                                if (!inConvScope || provOwner.equals("9998")) { //Either is in-scope or is a minority mapping
                                    newProvs = newProvs + " " + selectedProv;
                                }
                            
                            //}
                        } catch (java.lang.NumberFormatException exception) {
                        
                        }
                        count2 = count2 + 1;
                    }
                    if (newProvs.contains(tab+tab+tab+tab+" ")) {
                        newProvs.replace(tab+tab+tab+tab+" ",tab+tab+tab+tab);
                        if (oneLineFlag) {
                            //System.out.println("OneLine");
                            newProvs = newProvs.replace(tab+tab+tab+tab,tab+tab+tab+"own_control_core = {");
                            newProvs = newProvs + " }";
                        }
                    }
                    //System.out.println(newProvs+"_newProvs");
                    
                    lines.add(newProvs);
            } else {
                lines.add(selectedLine);
            }
            
            if (coreFlag && selectedLineNoComment.contains("}")) { //encapsulate modified provs
                coreFlag = false;
                if (!oneLineFlag) {
                    lines.add(tab+tab+"}");
                }
                oneLineFlag = false;
            }
            countOldFile = countOldFile + 1;
        }

        return lines;
    }
    
    public static ArrayList<Characters> applyNewIdsToChars (ArrayList<Characters> convCharacters, int availableID)
    {

        ArrayList<Characters> convCharactersNew = new ArrayList<Characters>();
        int count = 0;
        while (count < convCharacters.size()) {
            Characters selectedChar = convCharacters.get(count);
            if (!selectedChar.isPruned()) { //if pruned, don't assign new ID
                int newID = availableID;
                selectedChar.setIrID(newID);
                availableID = availableID + 1;
            }
            convCharactersNew.add(selectedChar);
            
            count = count + 1;
        }
        return convCharactersNew;

    }
    
    public static ArrayList<Characters> adjustBirthdays (ArrayList<Characters> convCharacters, int baYear, int irYear, String irDate)
    {

        ArrayList<Characters> convCharactersNew = new ArrayList<Characters>();
        int count = 0;
        int difference = baYear - irYear;
        int minYear = 1;
        while (count < convCharacters.size()) {
            Characters selectedChar = convCharacters.get(count);
            String birthday = selectedChar.getBirthday();
            String newBirthday = Processing.birthDeathLogic(difference,birthday,minYear,irDate);
            
            selectedChar.setBirthday(newBirthday);
            
            String deathday = selectedChar.getDeathday(); 
            int age = selectedChar.getAge();

            if (!deathday.equals("none")) { //if character is still alive, don't set death
                String newDeathday = Processing.birthDeathLogic(difference,deathday,age+1,irDate);
            
                selectedChar.setDeathday(newDeathday);
            }
            
            convCharactersNew.add(selectedChar);
            
            count = count + 1;
        }
        return convCharactersNew;

    }
    
    public static String birthDeathLogic (int difference, String birthday, int minYear, String irDate)
    {
        
        birthday = birthday.replace(".",",");
        String[] birthdaySplit = birthday.split(",");
        int birthYear = Integer.parseInt(birthdaySplit[0]);
        int newBirthYear = birthYear - difference;
        if (newBirthYear < minYear) {
            newBirthYear = minYear;
        }
        
        int birthMonth = Integer.parseInt(birthdaySplit[1]);
        
        int birthDayOfMonth = Integer.parseInt(birthdaySplit[2]);
        
        String[] irDateArray = irDate.replace(".",",").split(",");
        
        int irDateMonth = Integer.parseInt(irDateArray[1]);
        
        int irDateDayOfMonth = Integer.parseInt(irDateArray[2]);
        
        int irDateYear = Integer.parseInt(irDateArray[0]);
        
        if (birthMonth > irDateMonth && newBirthYear >= irDateYear) { //rare edge-case if birth/death occured on same year of save on or after October
            birthMonth = irDateMonth;
            newBirthYear = irDateYear;
            //System.out.println("Adjusted date from "+birthday+" to "+newBirthYear+"."+birthMonth+"."+birthDayOfMonth);
        }
        
        if (birthMonth == irDateMonth && newBirthYear >= irDateYear && birthDayOfMonth > irDateDayOfMonth) {
            birthDayOfMonth = irDateDayOfMonth;
            newBirthYear = irDateYear;
            //System.out.println("Adjusted date from "+birthday+" to "+newBirthYear+"."+birthMonth+"."+birthDayOfMonth);
        }
            
        String newBirthday = newBirthYear+"."+birthMonth+"."+birthDayOfMonth;
        
        return newBirthday;
    }
    
    public static int getYearFromDate (String date)
    {
        
        date = date.replace(".",",");
        String[] dateSplit = date.split(",");
        int dateYear = Integer.parseInt(dateSplit[0]);
        
        return dateYear;
    }
    
    public static ArrayList<Characters> adjustCharacterCultRel (ArrayList<Characters> convCharacters, ArrayList<Country> baTagInfo,
    ArrayList<Provinces> baProvInfoList, ArrayList<String> cultureMappings, ArrayList<String> religionMappings) throws IOException
    {
        
        ArrayList<Characters> convCharactersNew = new ArrayList<Characters>();
        int count = 0;
        while (count < convCharacters.size()) {
            Characters selectedChar = convCharacters.get(count);
            int charCountry = selectedChar.getCountry();
            Country selectedCountry = baTagInfo.get(charCountry);
            String capital = selectedCountry.getCapital();
            int capInt = Integer.parseInt(capital);
            Provinces capitalBAProv = baProvInfoList.get(capInt);
            String capitalArea = capitalBAProv.getArea();
            String capitalRegion = capitalBAProv.getRegion();
            String tagCulture = selectedCountry.getCulture();
            
            String oldCulture = selectedChar.getCulture();
            String oldReligion = selectedChar.getReligion();
            
            if (capitalRegion == null || capitalArea == null) {
                //System.out.println("Area "+capitalArea+" of province "+capInt+" has no region!");
                capitalRegion = "debug";
                capitalArea = "debug";
            }
            
            String newCulture = Output.paramMapOutput(cultureMappings,oldCulture,tagCulture,"date",oldCulture,capitalRegion,capitalArea,"none","none");
            String newReligion = Output.paramMapOutput(religionMappings,newCulture,tagCulture,"date",oldReligion,capitalRegion,capitalArea,"none","none");
            
            if (newCulture.equals("99999")) {
                newCulture = "roman"; //Game will crash when a country has a non-existant primary culture
                System.out.println("Warning, culture "+oldCulture+" is unmapped, setting to 'Roman'");
            }
            
            selectedChar.setCulture(newCulture);
            selectedChar.setReligion(newReligion);
            
            convCharactersNew.add(selectedChar);
            
            count = count + 1;
        }
        return convCharactersNew;
    }
    
    public static ArrayList<Characters> applyDynastiesToCharacters (ArrayList<Characters> convCharacters, ArrayList<String> dynList)
    {

        ArrayList<Characters> convCharactersNew = new ArrayList<Characters>();
        int count = 0;
        while (count < convCharacters.size()) {
            Characters selectedChar = convCharacters.get(count);
            int charDynID = selectedChar.getDynastyID();
            String tempDyn = selectedChar.getDynastyName();
            int count2 = 0;
            while (count2 < dynList.size()) {
                String[] dynInfo = dynList.get(count2).split(",");
                int dynID = Integer.parseInt(dynInfo[1]);
                String dynName = dynInfo[0];
                if (tempDyn == null && charDynID == dynID && !dynName.contains("minor ") && !dynName.equals("")) {
                    dynName = dynName.replace(" ","_");
                    selectedChar.setDynastyName(dynName);
                    count2 = dynList.size() + 1;
                } else if (tempDyn != null) { //end loop if from a minor family
                    count2 = dynList.size() + 1;
                }
                
                count2 = count2 + 1;
            }
            charDynID = charDynID + 100000; //set not to conflict with regular I:R dynasties
            selectedChar.setDynastyID(charDynID);
            convCharactersNew.add(selectedChar);
            
            count = count + 1;
        }
        return convCharactersNew;

    }
    
    public static ArrayList<Characters> pruneCharacters (ArrayList<Characters> convCharacters, ArrayList<Country> convCountries)
    //prune characters who don't have a country
    {

        ArrayList<Characters> convCharactersNew = new ArrayList<Characters>();
        int count = 0;
        while (count < convCharacters.size()) {
            Characters selectedChar = convCharacters.get(count);
            int charCountryID = selectedChar.getCountry();
            Country charCountry = convCountries.get(charCountryID);
            boolean hasLand = charCountry.getHasLand();
            if (!hasLand) {
                selectedChar.setPruneStatus(true); // pruned
                //System.out.println("Prunning "+count);
            }
            
            //if (!selectedChar.getDeathday().equals("none")) {
            //    selectedChar.setPruneStatus(true); // pruned
                //System.out.println("Prunning "+count);
            //}
            
            convCharactersNew.add(selectedChar);
            
            count = count + 1;
        }
        return convCharactersNew;

    }
    
    public static ArrayList<Country> applyDynastiesToCountries (ArrayList<Country> convCountries, ArrayList<String> dynList)
    {

        ArrayList<Country> convCountriesNew = new ArrayList<Country>();
        int count = 0;
        while (count < convCountries.size()) {
            Country selectedCountry = convCountries.get(count);
            ArrayList<Integer> addedDynasties = new ArrayList<Integer>();
            ArrayList<String> addedDynastiesStr = new ArrayList<String>();
            int count2 = 0;
            while (count2 < dynList.size()) {
                String[] dynInfo = dynList.get(count2).split(",");
                int dynID = Integer.parseInt(dynInfo[1]);
                String owner = dynInfo[2];
                if (!owner.equals("no") && owner.length() < 10) {
                    int countryDynasty = Integer.parseInt(owner);
                    if (countryDynasty == count && !dynInfo[0].contains("minor ")) {
                        if (dynInfo[0].equals("")) {
                            System.out.println("Blank dynasty detected, ignoring");
                        } else {
                            dynInfo[0] = dynInfo[0].replace(" ","_");
                            addedDynastiesStr.add(dynInfo[0]);
                            dynID = dynID + 100000; //set not to conflict with regular I:R dynasties
                            addedDynasties.add(dynID);
                        }
                        
                        
                    }
                }
                
                
                count2 = count2 + 1;
            }
            
            if (addedDynasties != null) {
                selectedCountry.setMajorFamilies(addedDynasties);
                selectedCountry.setMajorFamiliesStr(addedDynastiesStr);
            }
            convCountriesNew.add(selectedCountry);
            
            count = count + 1;
        }
        return convCountriesNew;

    }
    
    public static ArrayList<String> appendFamilies(ArrayList<Country> irCountryList, ArrayList<String> OldLines)
    {
        int count = 0;
        ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean countrySectionFlag = false;
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            lines.add(selectedLine);
            if (selectedLine.equals(tab+"families={")) {
                countrySectionFlag = true;
            }
            
            if (countrySectionFlag) {
                lines.add(tab+tab+"#Converted Families");
                //lines.add("country = {");
                //lines.add(tab+"countries = {");
        
                while (count < irCountryList.size()) {
                    Country irCountry = irCountryList.get(count);
                    boolean hasLand = irCountry.getHasLand();
                    if (hasLand) {
                        ArrayList<Integer> irFamilies = irCountry.getMajorFamilies();
                        ArrayList<String> irFamiliesStr = irCountry.getMajorFamiliesStr();
                        String countryTag = irCountry.getUpdatedTag();
                        String countryCulture = irCountry.getCulture();
                        int familyCount = 0;
                        int familySize = 0;
                        if (irFamilies != null) {
                            familySize = irFamilies.size();
                        }
                        while (familyCount < familySize) {
                            int selectedFamily = irFamilies.get(familyCount);
                            String selectedFamilyStr = irFamiliesStr.get(familyCount);
                            lines.add(tab+tab+selectedFamily+" = {");
                            lines.add(tab+tab+tab+"key = "+quote+selectedFamilyStr+quote);
                            lines.add(tab+tab+tab+"prestige=300");
                            lines.add(tab+tab+tab+"culture="+countryCulture);
                            lines.add(tab+tab+tab+"owner="+countryTag);
                            lines.add(tab+tab+"}");
                            //System.out.println(selectedFamily);
                            familyCount = familyCount + 1;
                        }
                    }
        
                    count = count + 1;
        
                }
                
                countrySectionFlag = false;
            }
            
            countOldFile = countOldFile + 1;
        }
        return lines;
    }
    
    public static ArrayList<String> appendDiplo(ArrayList<Country> irCountryList,ArrayList<Diplo> diploList,ArrayList<String> OldLines)
    {
        int count = 0;
        ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean countrySectionFlag = false;
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            lines.add(selectedLine);
            if (selectedLine.equals("diplomacy = {")) {
                countrySectionFlag = true;
            }
            
            if (countrySectionFlag) {
                lines.add(tab+tab+"#Converted Diplomacy");
        
                while (count < diploList.size()) {
                    Diplo selectedDiplo = diploList.get(count);
                    int overlord = selectedDiplo.getOverlord();
                    int subject = selectedDiplo.getSubject();
                    Country subjectCountry = irCountryList.get(subject);
                    Country overlordCountry = irCountryList.get(overlord);
                    if (overlordCountry.getHasLand() && subjectCountry.getHasLand()) {
                        String type = selectedDiplo.getRelationType();
                        String subType = selectedDiplo.getRelationSubType();
                        String overlordTag = overlordCountry.getUpdatedTag();
                        String subjectTag = subjectCountry.getUpdatedTag();
                        
                        String subjectLine = tab+type+" = { first = "+overlordTag+" second = "+subjectTag;
                        if (!subType.equals("9999") && subType != null) {
                            subjectLine = subjectLine+" subject_type = "+subType;
                        }
                        subjectLine = subjectLine+" }";
                        lines.add(subjectLine);
                    }
        
                    count = count + 1;
        
                }
                
                countrySectionFlag = false;
            }
            
            countOldFile = countOldFile + 1;
        }
        return lines;
    }
    
    public static ArrayList<String> createCharFileForTag(Country baTag, ArrayList<Characters> convCharacters)
    //Each country requires its own .txt file in setup/characters
    {
        int count = 0;
        ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";

        boolean countrySectionFlag = false;
        
        int countryID = baTag.getID();
        String tag = baTag.getUpdatedTag();
        int countryRuler = Integer.parseInt(baTag.getRuler());
        lines.add(quote+tag+"_converted"+quote+"={");
        lines.add(tab+"country="+quote+tag+quote);
        Characters selectedCharRuler = convCharacters.get(countryRuler);
        int countryRulerID = selectedCharRuler.getIrID();
        
        while (count < convCharacters.size()) {
            //lines.add(selectedLine);
            Characters selectedChar = convCharacters.get(count);
            int charCountryID = selectedChar.getCountry();
            boolean isPruned = selectedChar.isPruned();
            if (charCountryID == countryID && !isPruned) {
                int charID = selectedChar.getIrID();
                //System.out.println("Printing " +charID+" in "+tag);
                //if (charID == 456) { //isn't printing
                //    System.out.println("Printing " +charID+" in "+tag);
                //}
                lines.add(tab+charID+"={"+tab);
                lines.add(tab+tab+"first_name="+quote+selectedChar.getName()+quote);
                String family = selectedChar.getDynastyName();
                if (family == null) {
                    lines.add(tab+tab+"#No Family");   
                    //System.out.println("Character "+count+" ("+charID+" )"+" has a null family!");
                }
                else if (family.contains("minor_") || family.contains("minor ")) {
                    family = family.replace("minor_","");
                    family = family.replace("minor ","");
                    lines.add(tab+tab+"family_name="+quote+family+quote);
                } else {
                    lines.add(tab+tab+"family = c:"+tag+".fam:"+family);
                }
                String sex = selectedChar.getSex();
                if (sex.equals("f")) {
                    lines.add(tab+tab+"female=yes");
                }
                lines.add(tab+tab+"birth_date="+selectedChar.getBirthday());
                String deathday = selectedChar.getDeathday();
                if (!deathday.equals("none")) {
                    lines.add(tab+tab+"death_date="+deathday);
                }
                lines.add(tab+tab+"culture="+quote+selectedChar.getCulture()+quote);
                lines.add(tab+tab+"religion="+quote+selectedChar.getReligion()+quote);
                lines.add(tab+tab+"no_stats=yes");
                lines.add(tab+tab+"add_martial="+selectedChar.getMartial());
                lines.add(tab+tab+"add_charisma="+selectedChar.getCharisma());
                lines.add(tab+tab+"add_finesse="+selectedChar.getFinesse());
                lines.add(tab+tab+"add_zeal="+selectedChar.getZeal());
                lines.add(tab+tab+"no_traits=yes");
                int traitCount = 0;
                ArrayList<String> traits = selectedChar.getTraits();
                if (traits != null) {
                    while (traitCount < traits.size()) {
                        String selectedTrait = traits.get(traitCount);
                        lines.add(tab+tab+"add_trait="+quote+selectedTrait+quote);
                        traitCount = traitCount + 1;
                    }
                }
                int spouse = selectedChar.getSpouse();
                if (spouse > -1 && deathday.equals("none")) {
                    Characters selectedCharSpouse = convCharacters.get(spouse);
                    if (!selectedCharSpouse.isPruned()) {
                        int spouseID = selectedCharSpouse.getIrID();
                        lines.add(tab+tab+"marry_character=char:"+spouseID);
                    }
                }
                int father = selectedChar.getFather();
                if (father > -1) {
                    Characters selectedCharFather = convCharacters.get(father);
                    if (!selectedCharFather.isPruned()) {
                        int fatherID = selectedCharFather.getIrID();
                        lines.add(tab+tab+"father="+quote+"char:"+fatherID+quote);
                    }
                }
                int mother = selectedChar.getMother();
                if (mother > -1) {
                    Characters selectedCharMother = convCharacters.get(mother);
                    if (!selectedCharMother.isPruned()) {
                        int motherID = selectedCharMother.getIrID();
                        lines.add(tab+tab+"mother="+quote+"char:"+motherID+quote);
                    }
                }
                if (countryRulerID == charID) {
                    lines.add(tab+tab+"c:"+tag+"={");
                    lines.add(tab+tab+tab+"set_as_ruler=char:"+charID);
                    lines.add(tab+tab+"}");
                }
                lines.add(tab+"}");
            }
            
            
            count = count + 1;
        }
        
        lines.add("}");
        return lines;
    }
    
    public static ArrayList<Provinces> addExoProvinces (ArrayList<Provinces> convProvinces, ArrayList<String[]> exoProvinces,
    ArrayList<Provinces> vanillaProvinces, ArrayList<String> exoCultureMappings) throws IOException
    //Add provinces from outside BA's map
    {
        int count = 0;
        while (count < exoProvinces.size()) {
            String[] exoProvinceArray = exoProvinces.get(count);
            int masterProvID = Integer.parseInt(exoProvinceArray[0]);
            int masterProvArrayID = getProvByID(convProvinces,masterProvID);
            Provinces masterProv = convProvinces.get(masterProvArrayID);
            String owner = masterProv.getOwner();
            String masterProvCulture = masterProv.getCulture();
            String masterProvReligion = masterProv.getReligion();
            String changeCulture = "none";
            String changeReligion = "none";
            String changes = exoProvinceArray[exoProvinceArray.length-1];
            String exoRole = "none"; //which tag the country'll be treated as for missions
            String exoGov = "none";
            if (!changes.equals("NoChange~~~")) {
                //System.out.println(changes);
                String[] changesTopData = changes.split("~~~");
                String changesHeader = changesTopData[0];
                if (changesHeader.equals("Dyn")) {
                    owner = Integer.toString(10000+count);
                } else if (changesHeader.equals("VanillaTag")) {
                    owner = "9998";
                }
                
                changes = changesTopData[1];
                String[] changesArray = changes.split("~~");
                int changesCount = 0;
                while (changesCount < changesArray.length) {
                    String[] selectedChange = changesArray[changesCount].split(",");
                    String changeType = selectedChange[0];
                    String changeData = selectedChange[1];
                    if (changeType.equals("Cult")) {
                        changeCulture = changeData;
                    } else if (changeType.equals("Rel")) {
                        changeReligion = changeData;
                    } else if (changeType.equals("Missions")) {
                        exoRole = changeData;
                    } else if (changeType.equals("Gov")) {
                        exoGov = changeData;
                    }
                    changesCount = changesCount + 1;
                }
            }
            
            int exoProvCount = 1;
            while (exoProvCount < exoProvinceArray.length-1) {
                int selectedExoProv = Integer.parseInt(exoProvinceArray[exoProvCount]);
                int vanillaProvID = Processing.getProvByID(vanillaProvinces,selectedExoProv);
                Provinces vanillaProv = vanillaProvinces.get(vanillaProvID);
                String culture = vanillaProv.getCulture();
                String religion = vanillaProv.getReligion();
                String cityStatus = vanillaProv.getCityStatus();
                double civValue = vanillaProv.getCivValue();
                ArrayList<Pop> tmpPops = vanillaProv.getPops();
                String region = vanillaProv.getRegion();
                String area = vanillaProv.getArea();
                
                Provinces tmpProv = Provinces.newProv(owner,culture,religion,-1,0,0,0,0,0,cityStatus,civValue,selectedExoProv);
                tmpProv.setRegion(region);
                tmpProv.setArea(area);
                tmpProv.setTerrain(vanillaProv.getTerrain());
                tmpProv.setTradeGood(vanillaProv.getTradeGood());
                if (!changeCulture.equals("none") ) {
                    tmpProv.setExoTagRequirement(changeCulture);
                    if (culture.equals(changeCulture)) {
                        String tmpCulture = Output.paramMapOutput(exoCultureMappings,masterProvCulture,masterProvCulture,"date",masterProvCulture,region,area,"none","none");
                        if (!tmpCulture.equals("roman")) {
                            masterProvCulture = tmpCulture; //If no special regional culture, use original culture from the motherland
                        }
                        tmpProv.setCulture(masterProvCulture);
                    }
                    tmpPops = updatePops("culture",changeCulture,masterProvCulture,tmpPops);
                }
                if (!changeReligion.equals("none")) {
                    if (religion.equals(changeReligion)) {
                        tmpProv.setReligion(masterProvReligion);
                    }
                    tmpPops = updatePops("religion",changeReligion,masterProvReligion,tmpPops);
                }
                tmpProv.setExoRole(exoRole);
                tmpProv.setExoGov(exoGov);
                
                tmpProv.addPopArray(tmpPops);
                convProvinces.add(tmpProv);
                exoProvCount = exoProvCount + 1;
            }
            
            
            count = count + 1;
        }
        return convProvinces;

    }
    
    public static ArrayList<Pop> updatePops (String type, String oldData, String updatedData, ArrayList<Pop> pops)
    {
        int popArrayCount = 0;
        ArrayList<Pop> newPopArray = new ArrayList<Pop>();
        while (popArrayCount < pops.size()) {
            Pop selectedPop = pops.get(popArrayCount);
            if (type.equals("culture")) {
                String popCulture = selectedPop.getCulture();
                if (popCulture.equals(oldData)) {
                   selectedPop.setCulture(updatedData); 
                }
            } else if (type.equals("religion")) {
                String popReligion = selectedPop.getReligion();
                if (popReligion.equals(oldData)) {
                   selectedPop.setReligion(updatedData);
                }
            }
            newPopArray.add(selectedPop);
            popArrayCount = popArrayCount + 1;
        }
        return newPopArray;
    }
    
    public static ArrayList<Country> generateExoCountries (ArrayList<Provinces> irProvinceList, int convTag, String modDirectory,
    ArrayList<String> locList, ArrayList<String> exoNames) throws IOException
    {
        int provCount = 0;
        ArrayList<Country> exoCountries = new ArrayList<Country>();
        while (provCount < irProvinceList.size()) {
            Provinces selectedProvince = irProvinceList.get(provCount);
            String owner = selectedProvince.getOwner();
            int ownerID = Integer.parseInt(owner);
            if (ownerID >= 10000) {
                int exoCount = 0;
                boolean isUsed = false;
                while (exoCount < exoCountries.size()) {
                    Country selectedExoCountry = exoCountries.get(exoCount);
                    int selectedID = selectedExoCountry.getID();
                    if (ownerID == selectedID) {
                        exoCount = exoCountries.size();
                        isUsed = true;
                    }
                    exoCount = exoCount + 1;
                }
                if (!isUsed) {
                    String culture = selectedProvince.getCulture();
                    String religion = selectedProvince.getReligion();
                    int provID = selectedProvince.getID();
                    String capital = Integer.toString(provID);
                    String government = selectedProvince.getExoGov();
                    if (government.equals("none")) {
                        government = "oligarchic_republic";
                    }
                    String color = randomizeColor();
                    String name = "none";
                    String adjective = "Dynamic Placeholder";
                    String[] names;
                    names = new String[2];
                    
                    String missions = selectedProvince.getExoRole();
                    Country newExoCountry = Country.newCountry(ownerID,owner,culture,religion,name,adjective,owner,capital,"none",color,"none",government);
                    String irTag = "none";
                    newExoCountry.setUpdatedTag(irTag);
                    newExoCountry.setHasLand(true);
                    
                    // Go through and determine if the tag uses a special name, tag, or missions
                    // Not ideally organized, but this way the entire game loc doesn't need to be parsed when it doesn't have to
                    if (!missions.equals("none")) {
                        newExoCountry.setMissions(missions);
                        String baseTag = missions;
                        String specialName = Output.paramMapOutput(exoNames,"none","none","date",culture,"none","none","none",baseTag);
                        if (!specialName.equals("roman")) {
                            name = specialName;
                            names[0] = name;
                            adjective = genAdjective(name);
                            names[1] = adjective;
                            newExoCountry.setLoc(name);
                            newExoCountry.setAdj(adjective);
                        }
                        String requiredCulture = selectedProvince.getExoTagRequirement();
                        if (requiredCulture.equals(culture)) {
                            irTag = baseTag;
                        }
                    }
                    
                    if (name.equals("none")) {
                        name = "PROV"+capital;
                        names = Importer.importLocalisation(locList,name,"none");
                        name = names[0];
                        adjective = names[1];
                    }
                    
                    if (irTag.equals("none")) { //only output localization and setup if country used a dynamic tag
                        convTag = convTag + 1;
                        irTag = genNewTag(convTag);
                        Output.localizationCreation(names,irTag,modDirectory);
                        Output.countrySetupCreation(color,irTag,modDirectory);
                    }
                    
                    newExoCountry.setUpdatedTag(irTag);
                    exoCountries.add(newExoCountry);
                }
            }
            provCount = provCount + 1;
        }
        return exoCountries;
    }
    
    public static ArrayList<Country> appendExoCountries (ArrayList<Country> convCountries, ArrayList<Country> exoCountries)
    {
        int exoCount = 0;
        while (exoCount < exoCountries.size()) {
            Country selectedExoCountry = exoCountries.get(exoCount);
            convCountries.add(selectedExoCountry);
            exoCount = exoCount + 1;
        }
        return convCountries;
    }
    
    public static ArrayList<String> assignExoFlags (ArrayList<Country> exoCountries,ArrayList<String> flagMappings,ArrayList<String> flagTemplate)
    throws IOException
    {
        int exoCount = 0;
        while (exoCount < exoCountries.size()) {
            Country selectedExoCountry = exoCountries.get(exoCount);
            String exoRole = selectedExoCountry.getMissions();
            if (exoRole != null) {
                String culture = selectedExoCountry.getCulture();
                String religion = selectedExoCountry.getReligion();
                String exoFlag = Output.paramMapOutput(flagMappings,"none","noTagCulture","date",culture,"noRegion","noArea",religion,exoRole);
                System.out.println("Flag:"+exoFlag+" | culture:"+culture+" | religion:"+religion+" | tag:"+exoRole);
                if (!exoFlag.equals("roman")) { //exoFlag detected, update template
                    String exoTag = selectedExoCountry.getUpdatedTag();
                    int templateCount = 0;
                    while (templateCount < flagTemplate.size()) {
                        String line = flagTemplate.get(templateCount);
                        line = line.replace(exoFlag,exoTag);
                        flagTemplate.set(templateCount,line);
                        templateCount = templateCount + 1;
                    }
                }
            }
            exoCount = exoCount + 1;
        }
        return flagTemplate;
    }
    
    public static double average (ArrayList<Double> numbers)
    {
        int avgCount = 0;
        double total = 0;
        while (avgCount < numbers.size()) {
            double number = numbers.get(avgCount);
            total = number + total;
            avgCount = avgCount + 1;
        }
        double avg = total / avgCount;
        return avg;
    }
    
    public static ArrayList<String[]> getMissionTags (ArrayList<Country> convCountries)
    {
        int count = 0;
        ArrayList<String[]> missionTags = new ArrayList<String[]>();
        while (count < convCountries.size()) {
            Country selectedCountry = convCountries.get(count);
            String missionTag = selectedCountry.getMissions();
            if (missionTag != null) {
                String irTag = selectedCountry.getUpdatedTag();
                String[] missionCombo = (missionTag+","+irTag).split(",");
                missionTags.add(missionCombo);
            }
            count = count + 1;
        }
        
        return missionTags;
    }
    
    public static ArrayList<String> updateMission (ArrayList<String> oldMission, ArrayList<String[]> missionTags)
    {
        ArrayList<String> newMission = new ArrayList<String>();
        int count = 0;
        int oldMissionSize = oldMission.size();
        while (count < oldMissionSize) {
            String line = oldMission.get(count);
            String updatedLine = line;
            int tagCount = 0;
            int tagSize = missionTags.size();
            while (tagCount < tagSize) {
                String[] missionCombo = missionTags.get(tagCount);
                String originalTag = missionCombo[0];
                String newTag = missionCombo[1];
                
                updatedLine = updatedLine.replace("c:"+originalTag,"c:"+newTag);
                updatedLine = updatedLine.replace("tag = "+originalTag,"tag = "+newTag);
                
                tagCount = tagCount + 1;
            }
            newMission.add(updatedLine);
            count = count + 1;
        }
        
        return newMission;
    }
    
    public static void updateAllMissions (String dir, String modDir, ArrayList<String[]> missionTags) throws IOException //updates all missions
    {
        int count = 0;
        //String missionDirectory = dir+"/common/missions";
        //String missionModDir = modDir +"/common/missions";
        String missionDirectory = dir;
        String missionModDir = modDir;
        
        File missionFolder = new File(missionDirectory);
        String[] missionFiles = missionFolder.list();

        while (count < missionFiles.length) {
            String fileName = missionFiles[count];
            String missionFileLocation = missionDirectory+"/"+fileName;
            ArrayList<String> missionFile = Importer.importBasicFile(missionFileLocation);
            ArrayList<String> updatedMission = updateMission(missionFile,missionTags);
            boolean check = compareStringLists(missionFile,updatedMission);
            if (!check) {
                Output.outputBasicFile(updatedMission,fileName,missionModDir);
            }

            //System.out.println("Updated "+fileName);
            count = count + 1;
        }
    }
    
    public static void updateAllMissionEvents (String dir, String modDir, ArrayList<String[]> missionTags) throws IOException //updates all missions
    {
        int count = 0;
        String missionDirectory = dir+"/events/mission_events";
        String missionModDir = modDir +"/events/mission_events";
        
        File missionFolder = new File(missionDirectory);
        String[] missionFiles = missionFolder.list();

        while (count < missionFiles.length) {
            
            String fileName = missionFiles[count];
            String missionFileLocation = missionDirectory+"/"+fileName;
            if (fileName.contains(".txt")) {
                ArrayList<String> eventFile = Importer.importBasicFile(missionFileLocation);
                ArrayList<String> updatedMission = updateMission(eventFile,missionTags);
                boolean check = compareStringLists(eventFile,updatedMission);
                if (!check) {
                    Output.outputBasicFile(updatedMission,fileName,missionModDir);
                }

                //System.out.println("Updated "+fileName);
            } else {
                File missionEventFolder = new File(missionFileLocation);
                String[] missionEventFiles = missionEventFolder.list();
                int count2 = 0;
                while (count2 < missionEventFiles.length) {
                    String eventFileName = missionEventFiles[count2];
                    String missionEventFileLocation = missionFileLocation+"/"+eventFileName;
                    ArrayList<String> missionFile = Importer.importBasicFile(missionEventFileLocation);
                    ArrayList<String> updatedMission = updateMission(missionFile,missionTags);
                    boolean check = compareStringLists(missionFile,updatedMission);
                    if (!check) {
                        Output.outputBasicFile(updatedMission,eventFileName,missionModDir+"/"+fileName);
                    }
                    
                    count2 = count2 + 1;

                    //System.out.println("Updated "+eventFileName);
                }
            
            }
            
            count = count + 1;
        }
    }
    
    public static ArrayList<Monument> applyMonumentLoc (ArrayList<Monument> allMonuments, ArrayList<String> locList) throws IOException
    {
        String quote = '"'+""; // " character, Java doesn't like isoated " characters
        ArrayList<Monument> updatedMonuments = new ArrayList<Monument>();
        int count = 0;
        while (count < allMonuments.size()) {
            Monument selectedMonument = allMonuments.get(count);
            String name = selectedMonument.getName();
            if (name.equals("none")) { //If generic name, build the name
                String genericName = selectedMonument.getGenericName();
                String familyName = selectedMonument.getFamilyName();
                String provName = selectedMonument.getProvName();
                String[] locNames = Importer.importLocalisation(locList,genericName,"none");
                String[] provLoc = Importer.importLocalisation(locList,provName,"none");
                String newName = locNames[0];
                String provNameLoc = provLoc[0];
                newName = newName.replace("$ADJ$","The ");
                newName = newName.replace("$PROVINCE$",provNameLoc);
                newName = newName.replace("$FAMILY$",familyName);
                selectedMonument.setName(quote+newName+quote);
            }
            updatedMonuments.add(selectedMonument);
            count = count + 1;
        }
        
        return updatedMonuments;
    }
    
    public static ArrayList<String> purgeVanillaMonuments(ArrayList<Provinces> irProvinceList, ArrayList<String> OldLines) { 
        //Purges the vanilla monument setup of all provinces within conv scope
        int count = 0;
        //ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean monSectionFlag = false;
        boolean coreFlag = false;
        boolean oneLineFlag = false; //flag for when own_control_core is all on one line
        ArrayList<Integer> convProvinces = getAllProvincesInScope(irProvinceList);
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            String selectedLineNoComment = selectedLine.split(" #")[0];
            selectedLineNoComment = selectedLineNoComment.split("#")[0];
            selectedLineNoComment = selectedLineNoComment.replace(" = ","=");
            if (selectedLineNoComment.contains("great_work_manager")) {
                return OldLines;
            }else if (selectedLineNoComment.equals("provinces={")) {
                monSectionFlag = true;
            } else if (monSectionFlag && selectedLineNoComment.contains("{")) {
                
                String provID = selectedLineNoComment.split("=")[0];
                provID = provID.replace(tab,"");
                provID = provID.replace(" ","");
                int provIDInt = Integer.parseInt(provID);
                boolean inConvScope = checkIntInList(convProvinces,provIDInt);
                //System.out.println("ID "+provIDInt+" is "+inConvScope);
                if (inConvScope) {
                    while (!selectedLine.contains("}")) {
                        selectedLine = "#"+selectedLine;
                        OldLines.set(countOldFile,selectedLine);
                        countOldFile = countOldFile + 1;
                        selectedLine = OldLines.get(countOldFile);
                    }
                    selectedLine = "#"+selectedLine;
                    OldLines.set(countOldFile,selectedLine);
                }
            }
            
            
            countOldFile = countOldFile + 1;
        }

        return OldLines;
    }
    
    public static ArrayList<String> purgeVanillaBuildings(ArrayList<Provinces> irProvinceList, ArrayList<String> buildingNames, ArrayList<String> OldLines) { 
        //Purges the vanilla building setup of all provinces within conv scope
        int count = 0;
        //ArrayList<String> lines = new ArrayList<String>();
        String tab = "	";
        String quote = '"'+"";
        
        int countOldFile = 0;
        boolean monSectionFlag = false;
        boolean coreFlag = false;
        boolean oneLineFlag = false; //flag for when own_control_core is all on one line
        ArrayList<Integer> convProvinces = getAllProvincesInScope(irProvinceList);
        monSectionFlag = true;
        
        
        while (countOldFile < OldLines.size()) {
            String selectedLine = OldLines.get(countOldFile);
            String selectedLineNoComment = selectedLine.split(" #")[0];
            selectedLineNoComment = selectedLineNoComment.split("#")[0];
            selectedLineNoComment = selectedLineNoComment.replace(" = ","=");
            if (selectedLineNoComment.contains("road_network")) {
                return OldLines;
            } else if (selectedLineNoComment.contains("{")) {
                
                String provID = selectedLineNoComment.split("=")[0];
                provID = provID.replace(tab,"");
                provID = provID.replace(" ","");
                boolean inConvScope = false;
                int provIDInt = -1;
                try {
                    provIDInt = Integer.parseInt(provID);
                } catch (Exception E) {
                    
                }
                
                inConvScope = checkIntInList(convProvinces,provIDInt);
                if (inConvScope) {
                    //System.out.println("ID "+provIDInt+" is "+inConvScope);
                    while (!selectedLine.contains("}")) {
                        selectedLine = selectedLine.split("#")[0];
                        int buildingCount = 0;
                        int buildingSize = buildingNames.size();
                        while (buildingCount < buildingSize) {
                            String selectedBuilding = buildingNames.get(buildingCount);
                            String prunedSelectedLine = selectedLine.replace(" ","");
                            prunedSelectedLine = prunedSelectedLine.replace(tab,"");
                            prunedSelectedLine = prunedSelectedLine.split("=")[0];
                            //System.out.println("|"+prunedSelectedLine+"| selectedBuilding: "+selectedBuilding);
                            if (prunedSelectedLine.equals(selectedBuilding)) {
                                selectedLine = "#"+selectedLine;
                                OldLines.set(countOldFile,selectedLine);
                                buildingCount = 1 + buildingSize;
                            }
                            buildingCount = buildingCount + 1;
                        }
                        
                        //selectedLine = "#"+selectedLine;
                        //OldLines.set(countOldFile,selectedLine);
                        countOldFile = countOldFile + 1;
                        selectedLine = OldLines.get(countOldFile);
                    }
                    //selectedLine = "#"+selectedLine;
                    OldLines.set(countOldFile,selectedLine);
                }
            }
            
            
            countOldFile = countOldFile + 1;
        }

        return OldLines;
    }
    
    public static String genAdjective (String name) { //Generates an adjective for a given name
        String adjective = name;
        char endChar = name.charAt(name.length()-1);
        char preEndChar = name.charAt(name.length()-2);
        String last2 = preEndChar+""+endChar;
        if (endChar == 'a') {
            adjective = adjective + "n";
        } else if (last2.equals("io")) {
            adjective = adjective.substring(0,adjective.length()-2);
            adjective = adjective + "ian";
        } else if (endChar == 'i' || endChar == 'u' || endChar == 'o' || endChar == 'm' || endChar == 'n' || last2.equals("sh")) {
            
        } else {
            adjective = adjective + "ian";
        }
        
        return adjective;
        
    }
    
    public static ArrayList<Monument> convertMonuments(ArrayList<Monument> monuments, ArrayList<String> monumentMappings) throws IOException {
        int count = 0;
        ArrayList<Monument> updatedMonuments = new ArrayList<Monument>();
        while (count < monuments.size()) { 
            Monument selectedMonument = monuments.get(count);
            String key = cutQuotes(selectedMonument.getKey());
            String mapping = Output.cultureOutput(monumentMappings,key);
            if (!mapping.equals("99999")) {
                selectedMonument.setIsHistorical(true);
                int mappingID = Integer.parseInt(mapping);
                selectedMonument.setID(mappingID);
            }
            updatedMonuments.add(selectedMonument);
            count = count + 1;
        }

        return updatedMonuments;
    }
    
}
