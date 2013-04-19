package touhou.all.star.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.math.Matrix4;

public class GameMainMenu extends GameScreen {
	private final SpriteBatch spriteBatch;
	private final Texture background;
	private boolean isDone = false;
	public static final String FONT_CHARACTERS = "东方全明星";
	
	FreeTypeFontGenerator generator;
	FreeTypeBitmapFontData fontData;
	private BitmapFont freetypeBitmapFont; 
	BitmapFont title;

	public GameMainMenu() {
		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("MainMenuBackground.jpg"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font1.ttf"));
		fontData = generator.generateData(85, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		title = new BitmapFont(fontData, fontData.getTextureRegion(), false);    
		generator.dispose();
		
		title.setColor(1f, 0f, 0f, 1f);
	}

	@Override
	public void update (float delta) {
		if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.SPACE)) {
			isDone = true;
		}
	}

	@Override
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	public void draw(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//		viewMatrix.setToOrtho2D(0, 0, 480, 320);
//		spriteBatch.setProjectionMatrix(viewMatrix);
//		spriteBatch.setTransformMatrix(transformMatrix);
		
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(background, 0, 0, 1024, 768, 0, 0, 1024, 723, false, false);
//		spriteBatch.draw(reimu, 0, 0, 128, 128, 0, 0, 128, 128, false, false);
		spriteBatch.enableBlending();
		title.draw(spriteBatch, FONT_CHARACTERS, 350, 760);
		spriteBatch.end();
	}
	
	
	public void dispose () {
		spriteBatch.dispose();
		background.dispose();
		title.dispose();
	}
}
