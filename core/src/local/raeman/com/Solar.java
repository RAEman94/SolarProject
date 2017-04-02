package local.raeman.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Solar extends ApplicationAdapter {
	public PerspectiveCamera cam;
	final float[] startPos = {150f, -9f, 0f};

	public System system = new System();
	public System orbita = new System();

	public ModelBatch modelBatch;

	public enum State{
		Running, PAUSE
	}

	State state = State.Running;

	@Override
	public void pause()
	{
		this.state = State.PAUSE;
	}

	public void create() {
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(startPos[0], startPos[1], startPos[2]);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 200f;
		cam.update();
		ModelBuilder modelBuilder = new ModelBuilder();

		orbita.setCenterPlanet(modelBuilder.createSphere(5f,5f,5f,5,5,new Material(ColorAttribute.createDiffuse(Color.GREEN)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		orbita.axis(0,0,0);
		orbita.rotation(true,false,true); //Оси вращения
		orbita.r = 40; // радиус вращения
		orbita.angle = 180;//начальное положение на орбите
		orbita.velocity = (float)Math.PI / 180f; // скорость вращения
		orbita.inOrbite = null;

		system.setCenterPlanet(modelBuilder.createSphere(10f,10f,10f,10,10,new Material(ColorAttribute.createDiffuse(Color.YELLOW)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		system.axis(0,0,0);
		system.rotation(true,false,true);//задание осей вращения
		system.r = 0; // радиус вращения
		system.angle = 0;//начальное положение на орбите
		system.velocity = (float)Math.PI / 180f; // скорость вращения
		system.inOrbite = orbita;
	}

	public void render() {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


		modelBatch.begin(cam);
		switch(state){
			case Running:
				system.render(system,modelBatch);
				break;
			case PAUSE:
				break;
		}
		modelBatch.end();
		//Gdx.app.log("GameRenderer", "render");
	}

	public void dispose() {
		system.dispose();
		modelBatch.dispose();
	}
}
