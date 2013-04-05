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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Action;
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
	private Texture pointTexture;
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
	private TextureRegion yuRegion;

	private PerspectiveCamera camera;
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();

	public RendererGL10() {
		spriteBatch = new SpriteBatch();
		reimuTexture = new Texture(Gdx.files.internal("reimu.png"));
		rumiaTexture = new Texture(Gdx.files.internal("rumia.png"));
		reimuShotTexture = new Texture(Gdx.files.internal("reimushot.png"));
		enemyShotTexture = new Texture(Gdx.files.internal("enemyshot.png"));
		pointTexture = new Texture(Gdx.files.internal("point.png"));
		yuTexture = new Texture(Gdx.files.internal("yu.png"));
		backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));

		reimuSprite = new Sprite(reimuTexture, 0, 0, 60, 80);
		enemyShotSprite = new Sprite(enemyShotTexture, 0, 0, 60, 64);
		enemyShotSprite.setPosition(0, 0);
		judgePointSprite = new Sprite(enemyShotTexture, 64, 0, 14, 18);
		pointSprite = new Sprite(pointTexture, 0, 0, 4, 4);

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		yuRegion = new TextureRegion(yuTexture, 64, 64);
		yuImage = new Image(yuRegion);
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
		stage.dispose();
		spriteBatch.dispose();
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
		reimu.center.set(reimu.position.x + 25, reimu.position.y + 40);

		if (reimu.slowMode) {
			judgePointSprite.setPosition(reimu.center.x, reimu.center.y);
			judgePointSprite.draw(spriteBatch);
			spriteBatch.draw(pointTexture, reimu.position.x, reimu.position.y);
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
		spriteBatch.draw(rumiaTexture, boss.position.x, boss.position.y);
		// gl.glPopMatrix();

		spriteBatch.end();
	}

	private void renderReimuShots(GL10 gl, ArrayList<ReimuShot> reimushots) {
		gl.glColor4f(1, 1, 0, 1);
		spriteBatch.begin();
		spriteBatch.enableBlending();
		for (int i = 0; i < reimushots.size(); i++) {
			ReimuShot shot = reimushots.get(i);
			// gl.glPushMatrix();
			// gl.glTranslatef(shot.position.x, shot.position.y,
			// shot.position.z);
			spriteBatch
					.draw(reimuShotTexture, shot.position.x, shot.position.y);
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
