package com.paradoxgameconverters.batoir;  

import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.ArrayList;

/**
 * This will create temp files at specified points in the save files, used to significantly decrease time spent reading.
 *
 * @author Shinymewtwo99
 * @version
 */
public class TempFiles
{



    public static void tempCreate(String save, String keyword, String endword, String type) throws IOException
    {
        int flag = 0;

        FileInputStream fileIn= new FileInputStream(save);
        Scanner scnr= new Scanner(fileIn);

        FileOutputStream fileOut= new FileOutputStream(type);
        PrintWriter out = new PrintWriter(fileOut);   

        String vmm = scnr.nextLine();

        try {

            while (flag == 0) {
                if (vmm.equals (keyword)) {   
                    flag = 1;    
                }
                else {
                    vmm = scnr.nextLine();    
                }
            }
            
            int provFlag = 0;

            while (flag == 1) {
                out.println (vmm);
                if (vmm.equals(endword)) {
                    if (type.equals("tempProvinces.txt") && provFlag == 0) { //for tempProvinces.txt, wait for 2nd endword
                        provFlag = 1;
                        vmm = scnr.nextLine(); 
                    } else {
                        flag = 2;
                    }   

                }
                else {
                    vmm = scnr.nextLine();     
                }
            }
            //developed by Shinymewtwo99
        }
        catch (java.util.NoSuchElementException exception){
            flag = 2;

        }  
        out.flush();
        fileIn.close();
    }
    
    public static ArrayList<String> tempCreateInMemory(ArrayList<String> wholeSaveFile, String keyword, String endword)
    //Creates the temp file all in memory to reduce I/O
    //Note: Because there is a size limit to ArrayLists, it is not feasible to store the save file in memory as planned, so this method will
    //be left unused unless a workaround is found
    {
        int flag = 0;

        ArrayList<String> tempFile = new ArrayList<String>();   

        int count = 0;
        String line = "";
        int saveSize = wholeSaveFile.size();

        //System.out.println(keyword+"Is the keyword");
        try {
            while (count < saveSize) {
                line = wholeSaveFile.get(count);
                //System.out.println(line+"~noFlag");
                if (flag == 1) {
                    tempFile.add(line);
                    //System.out.println(line);
                }
                else if (line.equals (keyword)) {   
                    flag = 1; 
                    tempFile.add(line);
                }
                if (line.equals(endword)) {
                    count = 1 + saveSize;
                }
                count = count + 1;
            }

        }
        catch (java.util.NoSuchElementException exception){
            flag = 2;

        }  
        return tempFile;
    }
}
