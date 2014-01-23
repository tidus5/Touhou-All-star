package touhou.all.star;


import touhou.all.star.screens.GameLoop;
import touhou.all.star.screens.GameLoading;
import touhou.all.star.screens.GameScreen;
import touhou.all.star.screens.GameMenu;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TouhouAllStar extends Game {
	boolean testFlag=true;
	int testRender = 0;
	private Stage stage;
	private Texture texture;
	private FPSLogger fps;
	GameScreen currentScreen;

	@Override
	public void create() {
//		setScreen(new GameLoading());
		setScreen(new GameLoop());
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp (int keycode) {
				if (keycode == Keys.ENTER && Gdx.app.getType() == ApplicationType.WebGL) {
					if (!Gdx.graphics.isFullscreen())
						Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
				}
				return true;
			}
		});
		fps = new FPSLogger();
		
		
//		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
//				true);
//		texture = new Texture(Gdx.files.internal("actor1.jpg"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		float duration = 4f;
//		int maxwidth = Gdx.graphics.getWidth() - texture.getWidth();
//		int maxheight = Gdx.graphics.getHeight() - texture.getHeight();
//
//		Image image = new Image(texture);
//		image.setX(280f);
//		image.setY(280f); // ������
//		MoveToAction action = new MoveToAction();
//		action.setPosition(0f, 0f);
//		action.setDuration(duration);
//		image.addAction(action);
//		stage.addActor(image);
//		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render() {
		
		GameScreen currentScreen = getScreen();
		currentScreen.render(Gdx.graphics.getDeltaTime());
		
		if (currentScreen.isDone()) {
			currentScreen.dispose();

			if (currentScreen instanceof GameLoading) {
				setScreen(new GameLoop());
			}
			
			if (currentScreen instanceof GameMenu) {
				setScreen(new GameLoop());
			}
		}
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
//		texture.dispose();
//		stage.dispose();
	}
	

	@Override
	public void resize (int width, int height) {
	}
	
	@Override
	public GameScreen getScreen () {
		return (GameScreen)super.getScreen();
	}
}
