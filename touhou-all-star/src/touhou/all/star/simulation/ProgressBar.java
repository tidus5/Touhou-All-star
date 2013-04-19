package touhou.all.star.simulation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ProgressBar extends Actor implements Disposable{
	public Texture platform;
	public Texture bar;
	public int height;
	public int width;
    public float progress;
    //做了一个简单的适配，powerx和powery分别当前设备分辨率的权重，以现在主流的800*480为基准
    public float powerx;
    public float powery;
    @Override
    public void draw(SpriteBatch batch, float arg1) {
       // TODO Auto-generated method stub
       batch.draw(platform, (Gdx.graphics.getWidth()-bar.getWidth()*powerx)/2, Gdx.graphics.getHeight() -20,
    		   platform.getWidth()*powerx,platform.getHeight()*powery);
       batch.draw(bar,(Gdx.graphics.getWidth()-bar.getWidth()*powerx)/2,Gdx.graphics.getHeight() -20,
    		   bar.getWidth()*progress/100f*powerx,bar.getHeight()*powery);
    }

    public ProgressBar(int x,int y) {
       super();
       platform=new Texture(Gdx.files.internal("whitebar.png"));
       bar=new Texture(Gdx.files.internal("redbar.png"));
       height=Gdx.graphics.getHeight();
       width=Gdx.graphics.getWidth();
       //做了一个简单的适配，powerx和powery分别当前设备分辨率的权重，以现在主流的800*480为基准
       powerx=Gdx.graphics.getWidth()/800f;
       powery=Gdx.graphics.getHeight()/480f;
    }
    public void setProgress(float progress){
       this.progress=progress;
    }
    public void dispose() {
       // TODO Auto-generated method stub
       platform.dispose();
       bar.dispose();
    }
   
}