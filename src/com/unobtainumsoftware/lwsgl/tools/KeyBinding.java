package com.unobtainumsoftware.lwsgl.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KeyBinding {
    private static String filename;
    public static Map<String,Character> values = new HashMap<String,Character>();

    public KeyBinding(String path){
        filename = path;
    }

    public static void loadKeyBinding(){
        try{//check that file exists if not load and write default file
            File f = new File(filename);
            if(f.exists()){
                //load file
                Scanner s = new Scanner(f);
                while(s.hasNextLine()){
                    String[] temp = s.nextLine().split("=");
                    values.put(temp[0], temp[1].charAt(0));
                }
                s.close();
            }
        }catch(IOException e){
            System.out.println("Could not open cfg file.");
        }
    }

    public static void editValue(String key, char value){
        values.put(key, value);
    }

    public static char getValue(String key){
        return values.get(key);
    }

    public static void addValue(String key, char value){
        values.put(key, value);
    }

    public static String[] getLabels(){
        return (String[])values.keySet().toArray();
    }

    public static void saveKeyBinding(){
        try{
            FileWriter fw = new FileWriter(new File(filename));
            Object[] keys = values.keySet().toArray();
            for(int i=0; i<values.size(); i++)
                fw.write(keys[i]+"="+values.get(keys[i])+System.lineSeparator());
            fw.close();
        }catch(IOException e){
            System.out.println("Could not write to cfg file.");
        }
    }
}
