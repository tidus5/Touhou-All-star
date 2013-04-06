package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;


public class Reimu {
	public static final float SHIP_RADIUS = 1;
	public static float reimu_VELOCITY = 150;     // speed of reimu
<<<<<<< HEAD
	public static final Vector2 center1 = new Vector2(6, 60);
	public static final Vector2 center2 = new Vector2(26, 60);
=======
	public static final Vector2 center = new Vector2();
	public static final Vector2 center1 = new Vector2(6, 30);
	public static final Vector2 center2 = new Vector2(26, 30);
>>>>>>> 2f7ee1f1e68d4a1fabd9d36db99527c2138130f4
	public final Vector2 position = new Vector2(0, 0);	
	public int lives = 3;
	public boolean isExploding = false;
	public float explodeTime = 0;
<<<<<<< HEAD
	public boolean slowMode = true;
=======
	public boolean slowMode = false;
>>>>>>> 2f7ee1f1e68d4a1fabd9d36db99527c2138130f4
	
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
