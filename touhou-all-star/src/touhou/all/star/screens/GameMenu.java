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

public class GameMenu extends GameScreen {
	private final SpriteBatch spriteBatch;
	private Image background0;
	private Image background1;
	private Image background2;
	private Image background3;
	private Image background4;
	private Stage stage; 
    private Texture texture;
    
    private float alpha=0;
    private float menuDuration=0;
    private boolean isDone = false;
    
    private RotateByAction rotateAction;
    private MoveByAction moveAction;
    private ArrayList<Flower> flowerArray = new ArrayList<Flower>();
    private ArrayList<Float> flowerDuration = new ArrayList<Float>();
    
	public static final String FONT_CHARACTERS = "東东方全明星上海爱丽丝幻乐团弹幕过敏者注意伪本游戏根据网的视频制作而来很多面都不足若有侵犯到版权联系我处理请包涵：，。";
	public static final String mainTitle = "东方弹幕All Star(伪)";
	public static final String version = "Touhou Barrage All Star (fake)";
	public static final String caution1 = "注：本游戏根据网上东方全明星的视频制作而来，很多方面都有不足。请多多包涵";
	public static final String caution2 = "若有侵犯到版权，十分抱歉，请联系我处理^_^";
	
	FreeTypeFontGenerator generator;
	FreeTypeBitmapFontData fontData;
	private BitmapFont freetypeBitmapFont; 
	BitmapFont font1;
	BitmapFont font2;
	BitmapFont font3;
	BitmapFont font4;
	

	public GameMenu() {
		Texture.setEnforcePotImages(false);
		spriteBatch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("background1_2.png"));
		background0 = new Image (texture);
		
		texture = new Texture(Gdx.files.internal("start_back_1.png"));
		background1 = new Image(texture);
		texture = new Texture(Gdx.files.internal("start_back_2.png"));
		background2 = new Image(texture);
		texture = new Texture(Gdx.files.internal("start_back_3.png"));
		background3 = new Image(texture);
		texture = new Texture(Gdx.files.internal("start_back_4.png"));
		background4 = new Image(texture);
		
		background1.setColor(1, 1, 1, 0);
		background2.setColor(1, 1, 1, 0);
		background3.setColor(1, 1, 1, 0);
		background4.setColor(1, 1, 1, 0);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font2.ttf"));
		fontData = generator.generateData(55, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font1 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		font1.setColor(0.6f, 0.6f, 1, 0);
		
		fontData = generator.generateData(25, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font2 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		
		fontData = generator.generateData(25, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font3 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		
		fontData = generator.generateData(30, FreeTypeFontGenerator.DEFAULT_CHARS + FONT_CHARACTERS, false);
		font4 = new BitmapFont(fontData, fontData.getTextureRegion(), false);
		font4.setColor(0.3f, 0.56f, 0.7f, 1f);
		
		generator.dispose();
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 
                true); 
		
		 Gdx.input.setInputProcessor(stage); 
	}

	@Override
	public void update (float delta) {
		if (menuDuration > 4f) {
			if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.ENTER)
					|| Gdx.input.isKeyPressed(Keys.SPACE) || menuDuration > 8f) {
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
		alpha += delta;
		
		if(background1.getColor().a<1){
			if(alpha>1)
				alpha=1;
			background1.setColor(1, 1, 1, alpha);
		}else{
			if(background2.getColor().a<1){
				if(alpha>2)
					alpha=2;
				background2.setColor(1, 1, 1, alpha-1);
			}else{
				if(background3.getColor().a<1){
					if(alpha>3)
						alpha=3;
					background3.setColor(1, 1, 1, alpha-2);
				}else{
					if(background4.getColor().a<1){
						if(alpha>4)
							alpha=4;
						background4.setColor(1, 1, 1, alpha-3);
					}
				}
			}
		}
		stage.addActor(background0);
		stage.addActor(background1);
		stage.addActor(background2);
		stage.addActor(background3);
		stage.addActor(background4);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
//		viewMatrix.setToOrtho2D(0, 0, 480, 320);
//		spriteBatch.setProjectionMatrix(viewMatrix);
//		spriteBatch.setTransformMatrix(transformMatrix);
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
//		spriteBatch.enableBlending();

		if(alpha>=4)
		{
			if(alpha<5){
				font1.setColor(0.6f, 0.6f, 1, alpha-4);
				font2.setColor(1,1,1,alpha-4);
			}else if(alpha<6){
				font1.setColor(0.6f, 0.6f, 1, 1);
				font2.setColor(1,1,1,1);
				
			}
			font1.draw(spriteBatch, mainTitle, 260, 460); // left bottom is (0,0)
			font2.draw(spriteBatch, version, 320, 400);
			
			font3.draw(spriteBatch, caution1, 80, 160);
//			font3.draw(spriteBatch, caution2, 80, 100);
		}
//		font4.draw(spriteBatch, loading, 800, 120);
		spriteBatch.end();
	}
	
	
	public void dispose () {
		spriteBatch.dispose();
		texture.dispose();
		font1.dispose();
		font2.dispose();
		font3.dispose();
	}
}



