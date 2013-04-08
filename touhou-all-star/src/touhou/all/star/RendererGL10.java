package touhou.all.star;

import java.util.ArrayList;

import touhou.all.star.simulation.Boss;
import touhou.all.star.simulation.EnemyShot;
import touhou.all.star.simulation.Reimu;
import touhou.all.star.simulation.ReimuShot;
import touhou.all.star.simulation.Simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RendererGL10 implements Renderer {
	private SpriteBatch spriteBatch;
	private Texture reimuTexture;
	private Texture rumiaTexture;
	private Texture reimuShotTexture;
	private Texture enemyShotTexture;
	private Texture backgroundTexture;
	
	private Texture yuTexture;

	private Sprite reimuSprite;
	private Sprite enemyShotSprite;
	private Sprite judgePointSprite;
	private Sprite pointSprite;

	private Stage stage;
	private RotateByAction rotateByAction;
	private RotateToAction rotateToAction;
	private MoveToAction movetoAction;
	private Image yuImage;
	private TextureRegion rumiaTextureRegion;
	private TextureRegion reimuShotTextureRegion;

	private PerspectiveCamera camera;

	public RendererGL10() {
		
		spriteBatch = new SpriteBatch();
		
		TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("touhou_pic.pack")); //根据pack文件获取所有图片 

		backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
		
		rumiaTextureRegion =atlas.findRegion("rumia");
		reimuShotTextureRegion = atlas.findRegion("reimushot");


		reimuSprite = new Sprite(atlas.findRegion("reimu"));
		enemyShotSprite = new Sprite(atlas.findRegion("enemyshot"));
		enemyShotSprite.setPosition(0, 0);
		judgePointSprite = new Sprite(atlas.findRegion("judgepoint"));
		pointSprite = new Sprite(atlas.findRegion("point"));

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		yuImage = new Image(atlas.findRegion("yu"));
		yuImage.setPosition(50, 50);

		movetoAction = new MoveToAction();
		movetoAction.setPosition(300, 300);
		movetoAction.setDuration(30);

//		yuImage.addAction(movetoAction);
		
		 rotateByAction = new RotateByAction();
		 rotateByAction.setAmount(4500f);
		 rotateByAction.setDuration(10);
		 
		 yuImage.addAction(rotateByAction);
		 
		 
		 rotateToAction = new RotateToAction();
		 rotateToAction.setDuration(0);
		 
//		 rotateAction.setActor(yu);

		Gdx.input.setInputProcessor(stage);
		stage.addActor(yuImage);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
	}

	@Override
	public void render(Simulation simulation, float delta) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		renderBackground();
		
//		gl.glDisable(GL10.GL_DITHER);
//		gl.glEnable(GL10.GL_DEPTH_TEST);
//		gl.glEnable(GL10.GL_CULL_FACE);

//		setProjectionAndCamera(gl, simulation.reimu);
		
		renderReimu(gl, simulation.reimu);
		renderBoss(gl, simulation.boss);
		
		// gl.glViewport(0, 0, Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		
		renderBackground();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
//		yu.act(delta);

		// gl.glDisable(GL10.GL_DITHER);
		// gl.glEnable(GL10.GL_DEPTH_TEST);
		// gl.glEnable(GL10.GL_CULL_FACE);

		// setProjectionAndCamera(gl, simulation.reimu);

		renderReimu(gl, simulation.reimu);
		renderBoss(gl, simulation.boss);

		renderReimuShots(gl, simulation.reimushots);
		renderEnemyShots(gl, simulation.enemyshots);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

	private void renderBackground() {
		// viewMatrix.setToOrtho2D(0, 0, 400, 320);
		// spriteBatch.setProjectionMatrix(viewMatrix);
		// spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		// spriteBatch.disableBlending();
		// spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(backgroundTexture, 0, -250, 1024, 1024, 0, 0, 1024,
				1024, false, false);

		spriteBatch.end();
	}

	private void setProjectionAndCamera(GL10 gl, Reimu reimu) {
		camera.position.set(reimu.position.x, 6, 2);
		camera.direction.set(reimu.position.x, 0, -4).sub(camera.position)
				.nor();
		camera.update();
		camera.apply(gl);
	}

	private void renderReimu(GL10 gl, Reimu reimu) {
		if (reimu.isExploding)
			return;
		spriteBatch.begin();
		// spriteBatch.disableBlending();
		// gl.glPushMatrix();
		// gl.glTranslatef(reimu.position.x, reimu.position.y,
		// reimu.position.z);
		// gl.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 0, 1);
		// gl.glRotatef(180, 0, 1, 0);
		// spriteBatch.draw(reimuTexture,reimu.position.x,reimu.position.y);

		reimuSprite.setPosition(reimu.position.x, reimu.position.y);
		reimuSprite.draw(spriteBatch);
		reimu.center.set(reimu.position.x + 18, reimu.position.y + 35);

		if (reimu.slowMode) {
			judgePointSprite.setPosition(reimu.center.x, reimu.center.y);
			judgePointSprite.draw(spriteBatch);
			spriteBatch.draw(pointSprite, reimu.position.x, reimu.position.y);
			pointSprite.setPosition(reimu.position.x, reimu.position.y);
			pointSprite.draw(spriteBatch);
		}
		spriteBatch.end();
		// gl.glPopMatrix();

	}

	private void renderBoss(GL10 gl, Boss boss) {
		if (boss.isExploding)
			return;
		spriteBatch.begin();
		// gl.glPushMatrix();
		// gl.glTranslatef(boss.position.x, boss.position.y, boss.position.z);
		// gl.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 0, 1);
		// gl.glRotatef(180, 0, 1, 0);
		spriteBatch.draw(rumiaTextureRegion, boss.position.x, boss.position.y);
		// gl.glPopMatrix();

		spriteBatch.end();
	}

	private void renderReimuShots(GL10 gl, ArrayList<ReimuShot> reimushots) {
		gl.glColor4f(1, 1, 0, 1);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for (int i = 0; i < reimushots.size(); i++) {
			ReimuShot shot = reimushots.get(i);
			// shot.position.z);
			spriteBatch
					.draw(reimuShotTextureRegion, shot.position.x, shot.position.y);
			// gl.glPopMatrix();
		}
		spriteBatch.end();
		gl.glColor4f(1, 1, 1, 1);
	}

	private void renderEnemyShots(GL10 gl, ArrayList<EnemyShot> enemyshots) {
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

}
