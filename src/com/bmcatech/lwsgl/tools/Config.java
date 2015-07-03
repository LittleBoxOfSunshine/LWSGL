package com.bmcatech.lwsgl.tools;

import com.bmcatech.lwsgl.exception.LWSGLToolException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by cahenk on 5/17/15.
 */
public class Config {

    private	static String path;
    public static Map<String,String> pairs;
    private static String[]
            defaultKeys,
            defaultValues;

    public static void init(String p, String[] keys, String[] values) throws LWSGLToolException{
        path = p;
        pairs = new HashMap<String, String>();
        if(keys.length != values.length)
            throw new LWSGLToolException("ERROR: Config must have an equal number of keys...\nkeys.length = " + keys.length + " values.length = "+ values.length);
        defaultKeys = keys;
        defaultValues = values;
    }

    public static void loadCFG() throws LWSGLToolException{
        confirmInitialized();
        try{//check that file exists if not load and write default file
            File f = new File(Config.path);
            if(f.exists()){
                //load file
                Scanner s = new Scanner(f);
                while(s.hasNextLine()){
                    String[] temp = s.nextLine().split("=");
                    if(temp.length!=2){
                        loadDefault();
                        break;
                    }
                    Config.pairs.put(temp[0], temp[1]);
                }
                s.close();
                if(!isValid())//if file is corrupted, replace with default settings
                    loadDefault();
            }
            else
                loadDefault();
        }catch(IOException e){
            System.out.println("Could not open config file. Is the filepath \"" + Config.path + "\" correct?");
        }
    }

    public static void editValue(String key, String value) throws LWSGLToolException{
        confirmInitialized();
        Config.pairs.put(key, value);
    }

    public static String getValue(String key) throws LWSGLToolException{
        confirmInitialized();
        return Config.pairs.get(key);
    }

    public static void saveCFG() throws LWSGLToolException{
        confirmInitialized();
        try{
            FileWriter fw = new FileWriter(new File(path));
            Object[] keys = Config.pairs.keySet().toArray();
            for(int i=0; i<Config.pairs.size(); i++)
                fw.write(keys[i]+"="+Config.pairs.get(keys[i])+System.lineSeparator());
            fw.close();
        }catch(IOException e){
            System.out.println("Could not write to config file. Is the filepath \"" + path + "\" correct?");
        }
    }

    public static void loadDefault() throws LWSGLToolException{
        confirmInitialized();
        for(int i=0; i<defaultKeys.length; i++)
            Config.pairs.put(defaultKeys[i], defaultValues[i]);
        saveCFG();
    }

    public static boolean isValid() throws LWSGLToolException{
        confirmInitialized();
        //check for all values existence
        String[] temp = (String[])Config.pairs.keySet().toArray();
        for(String element: temp)
            if(Config.pairs.get(element) == null)
                return false;
        return true;
    }

    public static boolean isValid(String[] keys, int[] min, int[] max) throws LWSGLToolException{
        confirmInitialized();
        if(!isValid())
            return false;

        if(keys.length != min.length || keys.length != max.length)
            throw new LWSGLToolException("ERROR: Arrays must have equal sizes...\nkeys.length = " +
                    keys.length + " min.length = "+ min.length + " max.length = " + max.length);
        int temp;
        String tempS;
        for(int i=0; i<keys.length; i++){
            tempS = Config.pairs.get(keys[i]);
            if(tempS == null)
                throw new LWSGLToolException("ERROR: The Config key \"" + keys[i] + "\" does not exist...");
            try {
                temp = Integer.parseInt(tempS);
            }catch(Exception e){
                throw new LWSGLToolException("ERROR: The Config value loaded from key = \"" + keys[i] +
                        "\" (value = \"" + tempS + "\") cannot be converted to an integer to check if within range [" +
                        min[i] + "," + max[i] + "...");
            }
            if(temp < min[i] || temp > max[i])
                return false;
        }
        return true;
    }

    public static boolean isValid(String[][] validOptions) throws LWSGLToolException{
        confirmInitialized();
        if(!isValid())
            return false;

        String tempS;
        boolean temp;

        for(int i=0; i<validOptions.length; i++){
            tempS = Config.pairs.get(validOptions[i][0]);
            if(tempS == null)
                throw new LWSGLToolException("ERROR: The Config key \"" + validOptions[i][0] + "\" does not exist...");
            temp = true;
            for(int z=1; z<validOptions[z].length; z++)
                if(tempS.compareTo(validOptions[i][z]) == 0)
                    return false;
        }

        return true;
    }

    private static void confirmInitialized() throws LWSGLToolException{
        if(Config.pairs != null)
            throw new LWSGLToolException("ERROR: The configuration was not initialized. Configuration must be initialized before it can be used...");
    }
}