package touhou.all.star.simulation;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Simulation {
	
	public final static float PLAYFIELD_MIN_X = -14;
	public final static float PLAYFIELD_MAX_X = 14;
	public final static float PLAYFIELD_MIN_Z = -15;
	public final static float PLAYFIELD_MAX_Z = 2;
	
	public ArrayList<ReimuShot> reimushots = new ArrayList<ReimuShot>();
	public transient SimulationListener listener;
	public Reimu reimu;
	public Boss boss;
	public ReimuShot reimuShot;
	private int bossMovsCount=1;
	
	public Simulation () {
		generate();
	}
	
	private void generate () {
		reimu = new Reimu();
		reimu.position.set(600, 200, 0);

		boss = new Rumia(new Vector3(0, 0, 0));
		boss.position.set(400, 600, 0);

	}
	
	public void update (float delta) {
		if(boss.position.x< Gdx.graphics.getWidth() && bossMovsCount>0) 
			boss.position.x+=2;
		else
			bossMovsCount=-1;
		if(boss.position.x> 0 && bossMovsCount<0)
			boss.position.x-=2;
		else
			bossMovsCount=1;
	}
	
	public void moveReimuLeft (float delta, float scale) {
//		if (reimu.isExploding) return;

		reimu.position.x -= delta * reimu.reimu_VELOCITY * scale;
//		if (reimu.position.x < PLAYFIELD_MIN_X) reimu.position.x = PLAYFIELD_MIN_X;
	}

	public void moveReimuRight (float delta, float scale) {
//		if (reimu.isExploding) return;

		reimu.position.x += delta * reimu.reimu_VELOCITY * scale;
//		if (reimu.position.x > PLAYFIELD_MAX_X) reimu.position.x = PLAYFIELD_MAX_X;
	}
	
	public void moveReimuUp (float delta, float scale) {
//		if (reimu.isExploding) return;

		reimu.position.y += delta * reimu.reimu_VELOCITY * scale;
//		if (reimu.position.x < PLAYFIELD_MIN_X) reimu.position.x = PLAYFIELD_MIN_X;
	}
	public void moveReimuDown (float delta, float scale) {
//		if (reimu.isExploding) return;

		reimu.position.y -= delta * reimu.reimu_VELOCITY * scale;
//		if (reimu.position.x < PLAYFIELD_MIN_X) reimu.position.x = PLAYFIELD_MIN_X;
	}

	public void shot () {
		if (reimuShot == null && !reimu.isExploding) {
			reimuShot = new ReimuShot(reimu.position, false);
			reimushots.add(reimuShot);
			if (listener != null) listener.shot();
		}
	}

}
