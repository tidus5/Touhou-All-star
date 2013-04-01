package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector3;

public class Boss {
	
	public static final float BOSS_RADIUS = 1;
	public static final float boss_VELOCITY = 20;
	public final Vector3 position = new Vector3(0, 0, 0);
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
	
	public void update (float delta){
		
	}

}
