package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;

public class Boss {
	
	public static final float BOSS_RADIUS = 1;
	public static final float boss_VELOCITY = 20;
	public final Vector2 position = new Vector2(0, 0);
	public final Vector2 center = new Vector2();
	public int lives = 3;
	public boolean isExploding = false;
	public boolean beingShot = false;
	public float explodeTime = 0;
	public float HP = 100;
	public int width;
	public int height;
	
	public void update (float delta){
		
	}

}
