package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;

public class ReimuShot {
	
	public static float SHOT_VELOCITY = 10;
	public final Vector2 position = new Vector2();
	public int width=18;
	public int height=60;
	public boolean hasLeftField = false;
	
	public ReimuShot (Vector2 position) {
		this.position.set(position);
	}
	
	public void update (float delta) {
			position.y += (20+SHOT_VELOCITY * delta);

		if (position.y > Simulation.PLAYFIELD_MAX_Y ||
		position.y < Simulation.PLAYFIELD_MIN_Y) hasLeftField = true;
	}

}
