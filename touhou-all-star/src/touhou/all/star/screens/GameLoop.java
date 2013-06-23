package touhou.all.star.screens;

import touhou.all.star.Renderer;
import touhou.all.star.RendererGL10;
import touhou.all.star.RendererGL20;
import touhou.all.star.simulation.Reimu;
import touhou.all.star.simulation.Simulation;
import touhou.all.star.simulation.SimulationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;

public class GameLoop extends GameScreen implements SimulationListener {

	private final Renderer renderer;
	private final Simulation simulation;
	private Controller controller;
	private int buttonsPressed = 0;

	private ControllerListener listener = new ControllerAdapter() {
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			buttonsPressed++;
			return true;
		}

		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			buttonsPressed--;
			return true;
		}
	};

	public GameLoop() {
		simulation = new Simulation();
		renderer = Gdx.graphics.isGL20Available() ? new RendererGL20()
				: new RendererGL10();

		if (Controllers.getControllers().size > 0) {
			Controller controller = Controllers.getControllers().get(0);
			if (Ouya.ID.equals(controller.getName())) {
				this.controller = controller;
				controller.addListener(listener);
			}
		}
	}

	@Override
	public void update(float delta) {
		simulation.update(delta);
		if (simulation.reimu.isExploding == false) {
			if (Gdx.input.isKeyPressed(Keys.DPAD_UP)|| Gdx.input.isKeyPressed(Keys.W))
				simulation.moveReimuUp(delta, 3.5f);
			if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN)|| Gdx.input.isKeyPressed(Keys.S))
				simulation.moveReimuDown(delta, 3.5f);
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)|| Gdx.input.isKeyPressed(Keys.A))
				simulation.moveReimuLeft(delta, 3.5f);
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)|| Gdx.input.isKeyPressed(Keys.D))
				simulation.moveReimuRight(delta, 3.5f);
		}else{
			if(simulation.reimu.rebirth){
				simulation.reimu.position.set(Gdx.graphics.getWidth()/2, 20);
				simulation.reimu.rebirth = false;
				simulation.reimu.rebirthWhile = true;
			}
			if(simulation.reimu.rebirthWhile){
				simulation.reimu.position.y += delta * Reimu.reimu_VELOCITY * 2.5f;
				if(simulation.reimu.position.y>30){
					simulation.reimu.rebirthWhile = false;
				}	
			}
		}
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			simulation.setReimuSlowSpeed();
		} else {
			simulation.setReimuNormalSpeed();
		}
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.Z))
			simulation.shot();
	}

	@Override
	public void draw(float delta) {
		renderer.render(simulation, delta);
	}

	@Override
	public boolean isDone() {
		return simulation.reimu.lives == 0;
	}

	@Override
	public void explosion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shot() {
		// TODO Auto-generated method stub

	}

}
