package touhou.all.star;

import touhou.all.star.simulation.Boss;
import touhou.all.star.simulation.Reimu;
import touhou.all.star.simulation.Simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;


public class RendererGL10 implements Renderer {
	private SpriteBatch spriteBatch;
	private Texture reimuTexture;
	private Texture rumiaTexture;
	private Texture backgroundTexture;
	
	private PerspectiveCamera camera;
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 transformMatrix = new Matrix4();
	
	public RendererGL10 () {
		spriteBatch = new SpriteBatch();
		reimuTexture = new Texture(Gdx.files.internal("reimu.png")); 
		rumiaTexture = new Texture(Gdx.files.internal("rumia.png"));
		backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(Simulation simulation, float delta) {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		renderBackground();
		
//		gl.glDisable(GL10.GL_DITHER);
//		gl.glEnable(GL10.GL_DEPTH_TEST);
//		gl.glEnable(GL10.GL_CULL_FACE);

//		setProjectionAndCamera(gl, simulation.reimu);
		
		renderReimu(gl, simulation.reimu);
		renderBoss(gl, simulation.boss);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void renderBackground () {
//		viewMatrix.setToOrtho2D(0, 0, 400, 320);
//		spriteBatch.setProjectionMatrix(viewMatrix);
//		spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		spriteBatch.disableBlending();
//		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(backgroundTexture, 0, -250, 1024, 1024, 0, 0, 1024, 1024, false, false);
		spriteBatch.end();
	}
	
	private void setProjectionAndCamera (GL10 gl, Reimu reimu) {
		camera.position.set(reimu.position.x, 6, 2);
		camera.direction.set(reimu.position.x, 0, -4).sub(camera.position).nor();
		camera.update();
		camera.apply(gl);
	}
	
	private void renderReimu (GL10 gl, Reimu reimu) {
		if (reimu.isExploding) return;
		spriteBatch.begin();
		gl.glPushMatrix();
		gl.glTranslatef(reimu.position.x, reimu.position.y, reimu.position.z);
		gl.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 0, 1);
		gl.glRotatef(180, 0, 1, 0);
		spriteBatch.draw(reimuTexture,reimu.position.x,reimu.position.y);
		gl.glPopMatrix();
		
		spriteBatch.end();
	}
	
	private void renderBoss (GL10 gl, Boss boss) {
		if (boss.isExploding) return;
		spriteBatch.begin();
		gl.glPushMatrix();
		gl.glTranslatef(boss.position.x, boss.position.y, boss.position.z);
		gl.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 0, 1);
		gl.glRotatef(180, 0, 1, 0);
		spriteBatch.draw(rumiaTexture,boss.position.x,boss.position.y);
		gl.glPopMatrix();
		
		spriteBatch.end();
	}

}
