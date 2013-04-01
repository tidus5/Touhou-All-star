
package touhou.all.star;

import touhou.all.star.simulation.Simulation;


public interface Renderer {
	public void render (Simulation sim, float delta);

	public void dispose ();
}
