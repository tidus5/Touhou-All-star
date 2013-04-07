package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;


public class Reimu {
	public static final float SHIP_RADIUS = 1;
	public static float reimu_VELOCITY = 150;     // speed of reimu
	public static final Vector2 center = new Vector2();
	public static final Vector2 center1 = new Vector2(6, 30);
	public static final Vector2 center2 = new Vector2(26, 30);
	public final Vector2 position = new Vector2(0, 0);	
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
	public boolean slowMode = false;
	
	public void update (float delta) {
//		if (isExploding) {
//			explodeTime += delta;
//			if (explodeTime > Explosion.EXPLOSION_LIVE_TIME) {
//				isExploding = false;
//				explodeTime = 0;
//			}
//		}
	}

}
