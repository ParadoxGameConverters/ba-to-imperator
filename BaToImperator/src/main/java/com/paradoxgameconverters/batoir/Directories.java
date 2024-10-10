package com.paradoxgameconverters.batoir;      

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
/**
 * Creates the basic directories for the CK II mod
 *
 * 
 */
public class Directories
{
    private int x;

    public static int modFolders (String Dir,String modName ) throws IOException
    {
        //Necessary to create all of the folders required for the mod
        File output = new File(Dir);
        output.mkdir();

        int aqv = 0;
        boolean  endOrNot2 = true;
        String VM = "\\";
        VM = VM.substring(0);
        String mainModFolder = Dir+VM+modName;
        File f = new File(mainModFolder);
        f.mkdir();

        File f2 = new File(mainModFolder+VM+"common");
        f2.mkdir();

        File f3 = new File(mainModFolder+VM+"events");
        f3.mkdir();   

        File f4 = new File(mainModFolder+VM+"gfx");
        f4.mkdir();    

        File f6 = new File(mainModFolder+VM+"localization");
        f6.mkdir(); 
        // Sub folders

        File f11 = new File(mainModFolder+VM+"common"+VM+"governments");
        f11.mkdir();  

        File f12 = new File(mainModFolder+VM+"common"+VM+"cultures");
        f12.mkdir();  

        File f15 = new File(mainModFolder+VM+"common"+VM+"religions");
        f15.mkdir(); 

        File f18 = new File(mainModFolder+VM+"gfx"+VM+"interface");
        f18.mkdir(); 

        File f23 = new File(mainModFolder+VM+"common"+VM+"bloodlines");
        f23.mkdir();  
        
        File f24 = new File(mainModFolder+VM+"common"+VM+"disease");
        f24.mkdir();
        
        File f25 = new File(mainModFolder+VM+"common"+VM+"government_flavor");
        f25.mkdir();
        
        File f26 = new File(mainModFolder+VM+"common"+VM+"laws");
        f26.mkdir();
        
        File f27 = new File(mainModFolder+VM+"gfx"+VM+"flags");
        f27.mkdir();
        
        File f28 = new File(mainModFolder+VM+"decisions");
        f28.mkdir();
        
        //File f29 = new File(mainModFolder+VM+"setup");
        //f29.mkdir();
        
        File f30 = new File(mainModFolder+"/common/event_modifiers");
        f30.mkdir();
        
        File f31 = new File(mainModFolder+"/interface");
        f31.mkdir();
        
        File f33 = new File(mainModFolder+"/gfx/interface/bloodlines");
        f33.mkdir();  
        
        File f34 = new File(mainModFolder+"/common/cb_types");
        f34.mkdir(); 
        
        File f35 = new File(mainModFolder+VM+"setup");
        f35.mkdir();
        
        File f36 = new File(mainModFolder+"/localization/english");
        f36.mkdir();
        
        File f37 = new File(mainModFolder+"/common/named_colors");
        f37.mkdir();
        
        File f38 = new File(mainModFolder+"/common/coat_of_arms");
        f38.mkdir();
        
        File f39 = new File(mainModFolder+"/common/coat_of_arms/coat_of_arms");
        f39.mkdir();
        
        File f40 = new File(mainModFolder+"/setup/countries");
        f40.mkdir();
        
        File f41 = new File(mainModFolder+"/setup/countries/converted");
        f41.mkdir();
        
        File f42 = new File(mainModFolder+"/setup/provinces");
        f42.mkdir();
        
        File f43 = new File(mainModFolder+"/setup/main");
        f43.mkdir();

        return aqv;
    }

    public static void descriptors(String outputDir, String irModDir, String modName) throws IOException 
    {
        //Each mod requires a .mod "descriptor" files so the game launcher can
        //read the mod files as a mod
        char quote = '"';
        String VM = "\\";
        VM = VM.substring(0);
        irModDir = irModDir.replace(VM,"/");
        //String mainModFolder = irModDir+"/"+modName;
        //String mainModFolder = modName;
        String mainModFolder = irModDir+"/"+modName;

        FileOutputStream fileOut= new FileOutputStream(outputDir+"/"+modName+".mod");
        PrintWriter out = new PrintWriter(fileOut);
        String tab = "	";
        
        out.println("version="+quote+"1.0"+quote);
        out.println("tags={");
        out.println(tab+quote+"Overhaul"+quote);
        out.println(tab+quote+"Total Conversions"+quote);
        out.println("}");
        out.println("name="+quote+"Converted - "+modName+quote);
        out.println("path="+quote+mainModFolder+"/"+quote);
        out.println("supported_version="+quote+"2.*"+quote);

        out.flush();

        fileOut.close();
    }

}
