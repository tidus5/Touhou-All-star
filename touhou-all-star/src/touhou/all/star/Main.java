package touhou.all.star;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "touhou-all-star";
		config.useGL20 = false;
		config.vSyncEnabled = true;
		config.width = 1024;
		config.height = 768;
		config.resizable = false;
		
		
		new LwjglApplication(new TouhouAllStar(), config);
	}
}
