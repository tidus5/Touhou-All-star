package touhou.all.star.simulation;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class Simulation {

	public final static float PLAYFIELD_MIN_X = -34;
	public final static float PLAYFIELD_MAX_X = 934;
	public final static float PLAYFIELD_MIN_Y = -56;
	public final static float PLAYFIELD_MAX_Y = 656;

	public ArrayList<ReimuShot> reimushots = new ArrayList<ReimuShot>();
	public ArrayList<EnemyShot> enemyshots = new ArrayList<EnemyShot>();
	public transient SimulationListener listener;
	public Reimu reimu;
	public Boss boss;
	public ReimuShot reimuShot;
	public EnemyShot enemyShot;
	private int bossMovsCount = 1;
	private float timerRumia = 0;
	private float timerRumia2 = 0;
	private float degree = 0;
	private float degreeplus = 0;

	public Simulation() {
		generate();
	}

	private void generate() {
		reimu = new Reimu();
		reimu.position.set(Gdx.graphics.getWidth() / 2, 0);

		boss = new Rumia(new Vector2(0, 0));
		boss.position.set(400, 600);
		
		degree = MathUtils.PI / 7;
		degreeplus = degree;
	}

	public void update(float delta) {
		updateBoss(delta);
		// updateYoukai(delta);
		updateReimuShots(delta);
		updateEnemyShots(delta);
		updateReimuCollision(delta);
		updateBossCollision(delta);
	}

	public void updateBoss(float delta) {
		timerRumia2 += delta;
		if (timerRumia2 > 0.5) {
			enemyShot1();
			timerRumia2 = 0;
		}
		if(timerRumia2==-1){
		if (bossMovsCount > 0) {
			if (boss.position.x < Gdx.graphics.getWidth() - 128 - 300)
				boss.position.x += 6;
			else {
				if (timerRumia == 0) {
					// enemyShot1();
				}
				timerRumia += delta;
				if (timerRumia > 1) {
					bossMovsCount = -1;
					timerRumia = 0;
				}
			}
		} else {
			if (boss.position.x > 300)
				boss.position.x -= 6;
			else {
				if (timerRumia == 0) {
					// enemyShot1();
				}
				timerRumia += delta;
				if (timerRumia > 1) {
					bossMovsCount = 1;
					timerRumia = 0;
				}
			}
		}
		}
		
		boss.center.set(boss.position.x+23, boss.position.y+40);
	}

	public void moveReimuLeft(float delta, float scale) {
		if (reimu.isExploding)
			return;

		reimu.position.x -= delta * Reimu.reimu_VELOCITY * scale;
		if (reimu.position.x < PLAYFIELD_MIN_X)
			reimu.position.x = PLAYFIELD_MIN_X;
	}

	public void moveReimuRight(float delta, float scale) {
		if (reimu.isExploding)
			return;

		reimu.position.x += delta * Reimu.reimu_VELOCITY * scale;
		if (reimu.position.x > PLAYFIELD_MAX_X)
			reimu.position.x = PLAYFIELD_MAX_X;
	}

	public void moveReimuUp(float delta, float scale) {
		if (reimu.isExploding)
			return;

		reimu.position.y += delta * Reimu.reimu_VELOCITY * scale;
		if (reimu.position.y > PLAYFIELD_MAX_Y)
			reimu.position.y = PLAYFIELD_MAX_Y;
	}

	public void moveReimuDown(float delta, float scale) {
		if (reimu.isExploding)
			return;

		reimu.position.y -= delta * Reimu.reimu_VELOCITY * scale;
		if (reimu.position.y < PLAYFIELD_MIN_Y)
			reimu.position.y = PLAYFIELD_MIN_Y;
	}

	public void setReimuSlowSpeed() {
		reimu.slowMode = true;
		Reimu.reimu_VELOCITY = 50;
	}

	public void setReimuNormalSpeed() {
		reimu.slowMode = false;
		Reimu.reimu_VELOCITY = 150;
	}

	public void updateReimuShots(float delta) {
		for (int i = 0; i < reimushots.size(); i++) {
			reimuShot = reimushots.get(i);
			reimuShot.update(delta);

			// gl.glPushMatrix();
			// gl.glTranslatef(shot.position.x, shot.position.y,
			// shot.position.z);
			// spriteBatch.draw(reimuShotTexture,shot.position.x,
			// shot.position.y);
			// gl.glPopMatrix();
			if (reimuShot.hasLeftField)
				reimushots.remove(reimuShot);
			if (reimuShot != null && reimuShot.hasLeftField) {
				reimuShot = null;
			}
		}
	}

	public void updateEnemyShots(float delta) {
		for (int i = 0; i < enemyshots.size(); i++) {
			enemyShot = enemyshots.get(i);
			enemyShot.updateTowards(delta);
			enemyShot.center.set(enemyShot.position.x + 28,
					enemyShot.position.y + 30);

			// gl.glPushMatrix();
			// gl.glTranslatef(shot.position.x, shot.position.y,
			// shot.position.z);
			// spriteBatch.draw(reimuShotTexture,shot.position.x,
			// shot.position.y);
			// gl.glPopMatrix();
			if (enemyShot.hasLeftField) {
				enemyshots.remove(enemyShot);
			}
			if (enemyShot != null && enemyShot.hasLeftField) {
				enemyShot = null;
			}
		}

	}

	public void shot() {
		if (!reimu.isExploding) {
			reimuShot = new ReimuShot(new Vector2(0, 0).add(reimu.position)
					.add(Reimu.center1));
			reimushots.add(reimuShot);
			reimuShot = new ReimuShot(new Vector2(0, 0).add(reimu.position)
					.add(Reimu.center2));
			reimushots.add(reimuShot);
			if (listener != null)
				listener.shot();
		}
	}

	private void enemyShot1() {
		if (!boss.isExploding) {
			Vector2 center = new Vector2(boss.position);
			Vector2 toward = new Vector2();
			int i = 0, j = 0;

			j = (int) (MathUtils.PI * 2 / degree);

			for (i = 0; i < j; i++) {
				degreeplus += MathUtils.PI / 7.4;
				// toward.set(MathUtils.cos(2*MathUtils.PI * i/6),
				// MathUtils.sin(2*MathUtils.PI * i/6));
				toward.set(MathUtils.cos(degreeplus), MathUtils.sin(degreeplus));
				enemyShot = new EnemyShot(center.add(toward), toward);
				enemyshots.add(enemyShot);
			}
		}
	}

	private void updateReimuCollision(float delta) {
		reimu.isExploding = false;
		
		for (int i = 0; i < enemyshots.size(); i++) {
			enemyShot = enemyshots.get(i);
			if (Math.abs(enemyShot.center.x - reimu.center.x) < enemyShot.length
					&& Math.abs(enemyShot.center.y - reimu.center.y) < enemyShot.length
					&& reimu.invincible == false) {
				reimu.isExploding = true;
				if (reimu.lives > 0) {
					reimu.rebirth = true;
					reimu.lives--;
					if(reimu.lives==0)
						reimu.dead=true;
				}
			}
		}
	}
	
	private void updateBossCollision(float delta) {
		boss.beingShot = false;
		for (int i = 0; i < reimushots.size(); i++) {
			reimuShot = reimushots.get(i);
			if (!(reimuShot.position.x+reimuShot.width < boss.position.x||
					reimuShot.position.x > boss.position.x+boss.width ||
					reimuShot.position.y +reimuShot.height< boss.position.y||
					reimuShot.position.y>boss.position.y+boss.height) ){
				boss.beingShot = true;
				break;
			}
		}
		
		if(boss.beingShot && boss.HP>0){
			boss.HP-=0.3;
		}
		
		if(boss.HP<=0){
			boss.isExploding = true;
		}
		
//		System.out.println("width:"+reimuShot.width+"height"+reimuShot.height);
	}
	
}
