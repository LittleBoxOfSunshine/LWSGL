package test;

import com.unobtainumsoftware.lwsgl.exception.LWSGLException;
import com.unobtainumsoftware.lwsgl.game.GameState;
import com.unobtainumsoftware.lwsgl.game.StateBasedGame;
import com.unobtainumsoftware.lwsgl.map.TiledMap;

import java.awt.*;

/**
 * Created by cahenk on 5/21/15.
 */
public class MMOTest extends GameState {

    private TiledMap map;

    public MMOTest(int id, StateBasedGame sbg) {
        super(id, sbg);
    }

    public void init(){
        try {
            map = new TiledMap("res/test.tmx");
        }catch(Exception e){

        }
    }

    public void render(Graphics g){
        map.drawTiles(100,0,0,0, 25, 25, g);
    }

    public void update() throws LWSGLException{

    }
}