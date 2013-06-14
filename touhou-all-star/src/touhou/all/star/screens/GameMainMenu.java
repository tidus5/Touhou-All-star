package touhou.all.star.screens;

import java.util.ArrayList;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameMainMenu extends GameScreen {
	private final SpriteBatch spriteBatch;
	private final Texture background;
	private boolean isDone = false;
	private Stage stage; 
    private Texture texture;
    private float menuDuration=0;
    private RotateByAction rotateAction;
    private MoveByAction moveAction;
    private ArrayList<Flower> flowerArray = new ArrayList<Flower>();
    private ArrayList<Float> flowerDuration = new ArrayList<Float>();
    
	public static final String FONT_CHARACTERS = "東东方全明星上海爱丽丝幻乐团弹幕过敏者注意";
	public static final String mainTitle = "东方  Project";
	public static final String version = "12.x Project Shrine Maiden";
	public static final String produceby = "Produced by";
	public static final String alice = "上海爱丽丝幻乐团";
	public static final String caution = "弹幕过敏者注意";
	public static final String loading = "Loading...";
	
	FreeTypeFontGenerator generator;
	FreeTypeBitmapFontData fontData;
	private BitmapFont freetypeBitmapFont; 
	BitmapFont font1;
	BitmapFont font2;
	BitmapFont font3;
	BitmapFont font4;

	public GameMainMenu() {
		Texture.setEnforcePotImages(false);
		spriteBatch = new SpriteBatch();		
		background = new Texture(Gdx.files.internal("background1_mix.png"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font1.ttf"));
		fontData = generator.generateData(55, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font1 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		
		fontData = generator.generateData(25, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font2 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		
		fontData = generator.generateData(35, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font3 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		
		fontData = generator.generateData(30, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font4 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		font4.setColor(0.3f, 0.56f, 0.7f, 1f);
		
		generator.dispose();
		
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 
                true); 
		
		
		 for (int i = 0; i < 10; i++) {
			 
			 
		 }
		 Gdx.input.setInputProcessor(stage); 
	}

	@Override
	public void update (float delta) {
		System.out.println(menuDuration);
		if (menuDuration > 4f) {
			if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.ENTER)
					|| Gdx.input.isKeyPressed(Keys.SPACE) || menuDuration > 5f) {
				isDone = true;
			}
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
		
		menuDuration += delta;
		
//		viewMatrix.setToOrtho2D(0, 0, 480, 320);
//		spriteBatch.setProjectionMatrix(viewMatrix);
//		spriteBatch.setTransformMatrix(transformMatrix);
		
		texture = new Texture(Gdx.files.internal("petal_0.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		int maxwidth = Gdx.graphics.getWidth() - texture.getWidth();
		int maxheight = 240;
		int minwidth = 680;
		int minheight = 150;
		
		if( MathUtils.random()<0.3 ){
			Flower flower = new Flower();
			 flower.image = new Image(texture); 
			 flowerArray.add(flower);
			 flower.image.setX(MathUtils.random(minwidth, maxwidth));
			 flower.image.setY(MathUtils.random(minheight, maxheight));
			 flower.image.setOrigin(20f, 20f);
			 flower.image.setColor(1, 1, 1, 0.01f);
			 rotateAction = new RotateByAction();
			 rotateAction.setAmount(4000f);
			 rotateAction.setDuration(20);
			 
			 moveAction = new MoveByAction();
			 moveAction.setAmount(0, -300);
			 moveAction.setDuration(4f);
			 
			 flower.image.addAction(rotateAction);
			 flower.image.addAction(moveAction);
			 stage.addActor(flower.image);
		}
		
		float duration = 0.8f;
		for(int i=0;i<flowerArray.size();i++){
			Flower flower = flowerArray.get(i);			
			float alpha = flower.image.getColor().a;
			flower.duration += delta;
			
			if (flower.duration < duration) {
				alpha += delta *4 ;
				if (alpha > 1)
					alpha = 1;
				flower.image.setColor(1, 1, 1, alpha);
			}
			if (flower.duration > duration) {
				alpha -= delta *4 ;
				if (alpha < 0 ){
					alpha = 0;
					flowerArray.remove(flower);
				}
				flower.image.setColor(1, 1, 1, alpha);
			}
		}

		
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(background, 0, 0, 1024, 768, 0, 0, 1024, 768, false, false);
//		spriteBatch.draw(reimu, 0, 0, 128, 128, 0, 0, 128, 128, false, false);
		spriteBatch.enableBlending();
		font1.draw(spriteBatch, mainTitle, 380, 560);
		font2.draw(spriteBatch, version, 400, 500);
		font2.draw(spriteBatch, produceby, 450, 350);
		font2.draw(spriteBatch, alice, 400, 300);
		font3.draw(spriteBatch, caution, 680, 160);
		font4.draw(spriteBatch, loading, 800, 120);
		spriteBatch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	
	public void dispose () {
		spriteBatch.dispose();
		background.dispose();
		font1.dispose();
	}
}


class Flower{
	Image image;
	float duration;
	
}