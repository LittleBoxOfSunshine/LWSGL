package com.bmcatech.lwsgl.map;

import com.bmcatech.lwsgl.exception.LWSGLMapException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cahenk on 5/17/15.
 */
public class TiledMap {

    private Map<String, Layer> layers;
    private Map<String, String> properties;
    private Map<Integer, Tile> tiles;

    public TiledMap(String path) throws LWSGLMapException{
        Scanner file;

        this.layers = new HashMap<String, Layer>();
        this.tiles = new HashMap<Integer, Tile>();

        try {
            file = new Scanner(new File(path));
        }catch(Exception e){
            throw new LWSGLMapException("TiledMap could not be loaded... Is the path \"" + path + "\" correct?");
        }

        file.useDelimiter("[<>]+");
        while(file.hasNext()){
            String temp = file.next().trim();
            if(temp.equals(""))
                continue;

            String props[] = temp.split(" ");

            switch(props[0]){
                case "map":
                    this.properties = parseProperties(props);
                    break;
                case "tileset":
                    buildTiles(props, file);
                    break;
                case "layer":
                    buildLayer(props, file);
                    break;
                case "objectgroup":
                    System.out.println("Object layers not yet supported. Skipping this object group");
                    break;
            }

        }
    }

    public void drawTiles(int x, int y, int row, int col, int width, int height, Graphics g){
        int tempTileID;
        for(int i=0; i<height; i++)
            for(int z=0; z<width; z++)
                for(String key : this.layers.keySet()) {
                    tempTileID = this.layers.get(key).getTileID(row + i, col + z);
                    if(tempTileID > 0)
                        this.tiles.get(tempTileID).drawTile(x, y, g);
                }

    }

    public BufferedImage renderTiles(int x, int y, int row, int col, int width, int height){
        return null;
    }

    private void buildLayer(String[] props, Scanner file){
        Map<String, String> layerProperties = parseProperties(props);
        try{
            int width = Integer.parseInt(layerProperties.get("width"));
            int height = Integer.parseInt(layerProperties.get("height"));
            int[][] tempArr = new int[height][width];

            //clear the data line
            file.next();
            file.next();

            String derp = file.next();
            String[] temp = derp.split(",");

            for(int i=0; i < height; i++){
                for(int z=0; z< width; z++) {
                    tempArr[i][z] = Integer.parseInt(temp[z].trim());
                }
            }

            this.layers.put(layerProperties.get("name"), new Layer(tempArr, layerProperties));

            //clear the ending data tag and layer tag
            file.next();
            file.next();
            file.next();
            file.next();

        }catch(Exception e){
            //TO DO: throw map exception based on explicit exception thrown to cause this
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void buildTiles(String[] props, Scanner file){
        Map<String, String> tileProperties = parseProperties(props);
        try{
            int firstgid = Integer.parseInt(tileProperties.get("firstgid"));
            int tileWidth = Integer.parseInt(tileProperties.get("tilewidth"));
            int tileHeight = Integer.parseInt(tileProperties.get("tileheight"));

            file.next();
            Map<String,String> imageProps = parseProperties(file.next());

            BufferedImage image = ImageIO.read(new File(imageProps.get("source")));
            int imageWidth = Integer.parseInt(imageProps.get("width"));
            int imageHeight = Integer.parseInt(imageProps.get("height"));

            //create and add all tiles
            for(int y = 0; y+tileHeight<=imageHeight; y+=tileHeight){
                for(int x =0; x+tileWidth<=imageWidth; x+=tileWidth){
                    this.tiles.put(firstgid, new Tile(firstgid, image.getSubimage(x, y, tileWidth, tileHeight)));
                    firstgid++;
                }
            }

            //load any specific tile properties that exist
            String nextItem;

            while(file.hasNext()){
                nextItem = file.next().trim();
                if(nextItem.equals("/tileset"))
                    break;
                else if(nextItem.trim().equals(""))
                    continue;
                else{
                    String tileID = nextItem.substring(nextItem.indexOf('"')+1, nextItem.lastIndexOf('"'));
                    while(file.hasNext()){
                        nextItem = file.next().trim();
                        if(nextItem.equals("/properties")) {
                            file.next();
                            file.next();
                            break;
                        }
                        else if(nextItem.equals(""))
                            continue;
                        else{
                            file.next();
                            String[] newProperty = file.next().split("[ =/\"]+");
                            this.tiles.get(Integer.parseInt(tileID)).addProperty(newProperty[2], newProperty[4]);
                        }
                    }
                }
            }

        }catch(Exception e){
            //TO DO: throw map exception based on explicit exception thrown to cause this
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Map<String, String> parseProperties(String[] props){
        Map<String, String> tempMap = new HashMap<String, String>();
        String key, value;
        for(int i=1; i<props.length; i++) {
            key = props[i].substring(0, props[i].indexOf("="));
            value = props[i].substring(key.length() + 2);
            value = value.replaceAll("[\"/]+", "");
            tempMap.put(key, value);
        }
        return tempMap;
    }

    private Map<String, String> parseProperties(String propertyString){
        Map<String, String> tempMap = new HashMap<String, String>();
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(propertyString);
        while (regexMatcher.find())
            matchList.add(regexMatcher.group());

        //If size is even, last element is a / so it should be ignored
        int max = matchList.size();
        if(max % 2 == 0)
            max--;

        for(int i = 1; i<max; i+=2)
            tempMap.put(matchList.get(i).replaceAll("=", ""), matchList.get(i+1).replaceAll("\"", ""));

        return tempMap;
    }

}
