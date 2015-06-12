import com.bmcatech.lwsgl.exception.LWSGLStateException;
import com.bmcatech.lwsgl.game.StateBasedGame;
import com.bmcatech.lwsgl.exception.LWSGLException;

public class Core extends StateBasedGame{
	
	public static final int MENU = 0;
	public static final int CREATE = 1;
	public static final int LOAD = 2;
	public static final int CONFIG = 3;
	public static final int ABOUT = 4;
	public static final int GAME = 5;
	public static final int TESTAREA = 6;
	public static final int MMOTEST = 7;
	
	public Core(String name){
		super(name);
		this.addState(new MainMenu(MENU, this));
		this.addState(new Create(CREATE, this));
		this.addState(new Load(LOAD, this));
		this.addState(new Config(CONFIG, this));
		this.addState(new About(ABOUT, this));
		this.addState(new Game(GAME, this));
		this.addState(new TestArea(TESTAREA, this));
		this.addState(new MMOTest(MMOTEST, this));
		setScreenSize(1000, 600);
	}

	public void initStatesList() throws LWSGLStateException{
		this.getState(MENU).init();
		this.getState(CREATE).init();
		this.getState(LOAD).init();
		this.getState(CONFIG).init();
		this.getState(ABOUT).init();
		this.getState(TESTAREA).init();
		this.getState(GAME).init();
		this.getState(MMOTEST).init();
		this.enterState(MMOTEST);
	}
	
	public static void main(String [] args) throws LWSGLException{
		Core g = new Core("Test");
		g.init();
		g.start();
		System.exit(0);
	}
	
}
