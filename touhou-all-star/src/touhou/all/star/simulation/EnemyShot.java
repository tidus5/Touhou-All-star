package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;

public class EnemyShot {

	public static float SHOT_VELOCITY = 10;
	public final Vector2 position = new Vector2();
	public final Vector2 toward = new Vector2();
	public boolean hasLeftField = false;

	public EnemyShot(Vector2 position, Vector2 toward) {
		this.position.set(position);
		this.toward.set(toward);
	}

	private void checkHasLeftField() {
		if (position.x > 2*Simulation.PLAYFIELD_MAX_X
				|| position.x < 2*Simulation.PLAYFIELD_MIN_X
				|| position.y > 2*Simulation.PLAYFIELD_MAX_Y
				|| position.y < 2*Simulation.PLAYFIELD_MIN_Y)
		{
			hasLeftField = true;
			System.out.println(position);
		}
	}

	public void updateDown(float delta) {
		position.y -= (20 + SHOT_VELOCITY * delta);
		checkHasLeftField();
	}

	public void updateTowards(float delta) {
		position.add(toward);
		checkHasLeftField();
	}

}
