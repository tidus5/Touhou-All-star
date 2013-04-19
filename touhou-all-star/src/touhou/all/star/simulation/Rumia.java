package touhou.all.star.simulation;

import com.badlogic.gdx.math.Vector2;

public class Rumia extends Boss{
	
	public final Vector2 position = new Vector2();
	public final Vector2 center = new Vector2();
	public int life = 1;
	public int HP = 100;
	
	public Rumia (Vector2 position) {
		this.position.set(position);
		
		this.width = 53;
		this.height = 74;
	}
}
