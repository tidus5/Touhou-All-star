package touhou.all.star;

import java.util.ArrayList;

import touhou.all.star.simulation.Boss;
import touhou.all.star.simulation.EnemyShot;
import touhou.all.star.simulation.ProgressBar;
import touhou.all.star.simulation.Reimu;
import touhou.all.star.simulation.ReimuShot;
import touhou.all.star.simulation.Simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RendererGL10 implements Renderer {
	SpriteBatch spriteBatch;

	Texture rumiaTexture;
	Texture reimuShotTexture;
	Texture enemyShotTexture;
	Texture backgroundTexture;
	Texture yuTexture;

	Sprite reimuSprite0;
	Sprite reimuSprite1;
	Sprite reimuSprite2;
	Sprite reimuSprite;
	Sprite enemyShotSprite;
	Sprite judgePointSprite;
	Sprite pointSprite;
	Sprite whitepointSprite;

	Stage stage;
	RotateByAction rotateByAction;
	RotateToAction rotateToAction;
	RepeatAction rotateYuAlways;
	MoveToAction movetoAction;
	Image yuImage;
	TextureRegion rumiaTextureRegion;
	TextureRegion reimuShotTextureRegion;

	PerspectiveCamera camera;

	ParticleEffect particle;
	ParticleEffect tem;
	ParticleEffectPool particlepool;
	ArrayList<ParticleEffect> particlelist;
	BitmapFont bf;

	Animation animation;
	float stateTime;
	TextureRegion[] walksFrame;
	TextureRegion reimuRegion0;
	TextureRegion reimuRegion1;
	TextureRegion reimuRregion2;

	TextureAtlas atlas;
	TextureRegion currentFrame;

	ProgressBar bar;

	public RendererGL10() {

		Texture.setEnforcePotImages(false);

		spriteBatch = new SpriteBatch();

		atlas = new TextureAtlas(Gdx.files.internal("touhou_pic.pack")); // 根据pack文件获取所有图片

		backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));

		rumiaTextureRegion = atlas.findRegion("rumia");
		reimuShotTextureRegion = atlas.findRegion("reimushot");

		reimuSprite = new Sprite(atlas.findRegion("reimu"));
		enemyShotSprite = new Sprite(atlas.findRegion("enemyshot"));
		enemyShotSprite.setPosition(0, 0);
		judgePointSprite = new Sprite(atlas.findRegion("judgepoint"));
		pointSprite = new Sprite(atlas.findRegion("point"));
		whitepointSprite = new Sprite(new Texture(
				Gdx.files.internal("whitepoint.png")));
		reimuSprite0 = new Sprite(atlas.findRegion("reimu"));
		reimuSprite1 = new Sprite(atlas.findRegion("reimu1"));
		reimuSprite2 = new Sprite(atlas.findRegion("reimu2"));
		yuImage = new Image(atlas.findRegion("yu"));

		bar = new ProgressBar(0, 0);

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);

		initializeReimu();
		initializeParticle();

		Gdx.input.setInputProcessor(stage);
		stage.addActor(yuImage);
		stage.addActor(bar);
	}

	void initializeReimu() {
		yuImage.setOrigin(15f, 15f);

		rotateByAction = new RotateByAction();
		rotateByAction.setAmount(400f);
		rotateByAction.setDuration(1);

		rotateYuAlways = new RepeatAction();
		rotateYuAlways.setAction(rotateByAction);
		rotateYuAlways.setCount(RepeatAction.FOREVER);
		yuImage.addAction(rotateYuAlways);

		rotateToAction = new RotateToAction();
		rotateToAction.setDuration(0);

		reimuRegion0 = new TextureRegion(reimuSprite0);
		reimuRegion1 = new TextureRegion(reimuSprite1);
		reimuRregion2 = new TextureRegion(reimuSprite2);
		walksFrame = new TextureRegion[30];
		for (int i = 0; i < 30; i++) {
			if (i % 3 == 0) {
				walksFrame[i] = reimuRegion0;
			} else if (i % 3 == 1) {
				walksFrame[i] = reimuRegion1;
			} else {
				walksFrame[i] = reimuRregion2;
			}
		}
		animation = new Animation(0.25f, walksFrame);
	}

	void initializeParticle() {
		bf = new BitmapFont();
		particle = new ParticleEffect();
		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
		particlepool = new ParticleEffectPool(particle, 5, 10);
		particlelist = new ArrayList<ParticleEffect>();
	}

	@Override
	public void render(Simulation simulation, float delta) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		renderBackground();

		stage.act(Gdx.graphics.getDeltaTime());

		renderReimu(gl, simulation.reimu, delta);
		renderBoss(gl, simulation.boss);
		renderReimuShots(gl, simulation.reimushots);
		renderEnemyShots(gl, simulation.enemyshots);
		renderParticle();
		updateBossLifeBar(gl, simulation.boss);

		stage.draw();
		if (simulation.reimu.dead)
			stage.clear();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();

		rumiaTexture.dispose();
		reimuShotTexture.dispose();
		enemyShotTexture.dispose();
		backgroundTexture.dispose();
		yuTexture.dispose();

		stage.dispose();

		particle.dispose();
		tem.dispose();
		bf.dispose();

		atlas.dispose();
		bar.dispose();
	}

	void renderBackground() {
		spriteBatch.begin();
		// spriteBatch.disableBlending();.
		// spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(backgroundTexture, 0, -250, 1024, 1024, 0, 0, 1024,
				1024, false, false);

		spriteBatch.end();
	}

	void setProjectionAndCamera(GL10 gl, Reimu reimu) {
		camera.position.set(reimu.position.x, 6, 2);
		camera.direction.set(reimu.position.x, 0, -4).sub(camera.position)
				.nor();
		camera.update();
		camera.apply(gl);
	}

	void renderReimu(GL10 gl, Reimu reimu, float delta) {
		if (reimu.dead) {
			return;
		}
		spriteBatch.begin();
		// spriteBatch.draw(reimuTexture,reimu.position.x,reimu.position.y);

		reimuSprite.setPosition(reimu.position.x, reimu.position.y);
		reimuSprite.draw(spriteBatch);
		yuImage.setPosition(reimu.position.x + 10, reimu.position.y + 80);

		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, reimu.position.x, reimu.position.y);

		reimu.center.set(reimu.position.x + 18, reimu.position.y + 35);

		if (reimu.slowMode) {
			judgePointSprite.setPosition(reimu.center.x, reimu.center.y);
			judgePointSprite.draw(spriteBatch);
			spriteBatch.draw(pointSprite, reimu.position.x, reimu.position.y);
			// pointSprite.setPosition(reimu.position.x, reimu.position.y);
			// pointSprite.draw(spriteBatch);
		}
		spriteBatch.end();
		// gl.glPopMatrix();
	}

	void renderBoss(GL10 gl, Boss boss) {
		if (boss.isExploding)
			return;
		spriteBatch.begin();
		spriteBatch.draw(rumiaTextureRegion, boss.position.x, boss.position.y);
		spriteBatch.draw(whitepointSprite, boss.center.x, boss.center.y);

		// spriteBatch.draw(whitepointSprite, boss.position.x, boss.position.y);
		// spriteBatch.draw(whitepointSprite, boss.position.x,
		// boss.position.y+boss.height);
		// spriteBatch.draw(whitepointSprite, boss.position.x+boss.width,
		// boss.position.y);
		// spriteBatch.draw(whitepointSprite, boss.position.x+boss.width,
		// boss.position.y+boss.height);
		spriteBatch.end();
	}

	void renderReimuShots(GL10 gl, ArrayList<ReimuShot> reimushots) {
		gl.glColor4f(1, 1, 0, 1);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for (int i = 0; i < reimushots.size(); i++) {
			ReimuShot shot = reimushots.get(i);
			spriteBatch.draw(reimuShotTextureRegion, shot.position.x,
					shot.position.y);

			// spriteBatch.draw(pointSprite, shot.position.x, shot.position.y);
			// spriteBatch.draw(pointSprite, shot.position.x,
			// shot.position.y+shot.height);
			// spriteBatch.draw(pointSprite, shot.position.x+shot.width,
			// shot.position.y);
			// spriteBatch.draw(pointSprite, shot.position.x+shot.width,
			// shot.position.y+shot.height);
		}
		spriteBatch.end();
		gl.glColor4f(1, 1, 1, 1);
	}

	void renderEnemyShots(GL10 gl, ArrayList<EnemyShot> enemyshots) {
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for (int i = 0; i < enemyshots.size(); i++) {
			EnemyShot shot = enemyshots.get(i);
			enemyShotSprite.setPosition(shot.position.x, shot.position.y);
			enemyShotSprite.draw(spriteBatch);

			// pointSprite.setPosition(shot.center.x, shot.center.y);
			// pointSprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}

	void renderParticle() {
		if (true) {
			if (Gdx.input.isTouched()) {
				// 当此触摸点与上一触摸点距离大于一定值的时候触发新的粒子系统，由此减小系统负担
				tem = particlepool.obtain();
				tem.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()
						- Gdx.input.getY());
				particlelist.add(tem);
			}
			spriteBatch.begin();
			for (int i = 0; i < particlelist.size(); i++) {
				particlelist.get(i).draw(spriteBatch,
						Gdx.graphics.getDeltaTime());
			}
			spriteBatch.end();

			// 清除已经播放完成的粒子系统
			ParticleEffect temparticle;
			for (int i = 0; i < particlelist.size(); i++) {
				temparticle = particlelist.get(i);
				if (temparticle.isComplete()) {
					particlelist.remove(i);
				}
			}
		}
	}

	void updateBossLifeBar(GL10 gl, Boss boss) {
		// if(bar.progress<100)
		// bar.progress+=0.5;
		// //重新置零
		// if(bar.progress==100)
		// bar.progress=0;
		bar.progress = boss.HP;
	}
}
