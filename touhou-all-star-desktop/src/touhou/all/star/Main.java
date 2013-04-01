package touhou.all.star;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "touhou-all-star";
		config.useGL20 = false;
		config.vSyncEnabled = true;
		config.width = 800;
		config.height = 600;
		
		new LwjglApplication(new TouhouAllStar(), config);
	}
}
