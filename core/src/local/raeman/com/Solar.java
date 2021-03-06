package local.raeman.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Solar extends InputAdapter implements ApplicationListener {
	TextButton button;

	public PerspectiveCamera cam;
	final float[] startPos = {150f, -9f, 0f};

	public System system = new System();
	public System planet1 = new System();
	public System luna = new System();
	public System planet2 = new System();

	public ModelBatch modelBatch;

	public enum State
	{
		PAUSE,
		RUN,
		RESUME,
		STOPPED
	}

	State state = State.RUN;


	public void create() {
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(startPos[0], startPos[1], startPos[2]);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 200f;
		cam.update();
		ModelBuilder modelBuilder = new ModelBuilder();

		system.setCenterPlanet(modelBuilder.createSphere(20f,20f,20f,10,10,new Material(ColorAttribute.createDiffuse(Color.YELLOW)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		system.rotation(true,false,true);//задание осей вращения
		system.r = 0; // радиус вращения
		system.angle = 0;//начальное положение на орбите
		system.velocity = (float)Math.PI / 180f; // скорость вращения

		planet1.setCenterPlanet(modelBuilder.createSphere(10f,10f,10f,5,5,new Material(ColorAttribute.createDiffuse(Color.GREEN)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		planet1.rotation(true,false,true); //Оси вращения
		planet1.r = 50; // радиус вращения
		planet1.angle = 180;//начальное положение на орбите
		planet1.velocity = (float)Math.PI / 360f; // скорость вращения

		luna.setCenterPlanet(modelBuilder.createSphere(5f,5f,5f,5,5,new Material(ColorAttribute.createDiffuse(Color.BLUE)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		luna.rotation(true,false,true); //Оси вращения
		luna.r = 10; // радиус вращения
		luna.angle = 180;//начальное положение на орбите
		luna.velocity = (float)Math.PI / 30f; // скорость вращения
		luna.inOrbite = null;

		planet1.inOrbite = new System[]{luna};

		planet2.setCenterPlanet(modelBuilder.createSphere(10f,10f,10f,5,5,new Material(ColorAttribute.createDiffuse(Color.RED)),VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
		planet2.rotation(true,false,true); //Оси вращения
		planet2.r = 20; // радиус вращения
		planet2.angle = 0;//начальное положение на орбите
		planet2.velocity = (float)Math.PI / 180f; // скорость вращения
		planet2.inOrbite = null;

		system.inOrbite = new System[]{planet1,planet2};

		/*
		button.setSize(50,50);
		button.setPosition(50,50);
		button.setText("PAUSE");
		*/
		Gdx.input.setInputProcessor(new InputMultiplexer(this));
		Gdx.app.log("Game", "create");
	}

	@Override
	public void render() {

		switch (state)
		{
			case RUN:
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
				modelBatch.begin(cam);
				system.render(system,modelBatch,new double[] { 0, 0, 0 });
				modelBatch.end();
				break;
			case PAUSE:
				break;
			case RESUME:
				break;
			default:
				break;
		}

	}


	@Override
	public void resize(int a,int b) {}

	@Override
	public void pause() {
		this.state = State.PAUSE;
	}

	@Override
	public void resume() {
		this.state = State.RUN;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("Game", "touchDown");
		if(state == State.PAUSE){
			this.state = State.RUN;
		}else{
			this.state = State.PAUSE;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("Game", "touchUp");
		return true;
	}

	public void dispose() {
		system.dispose();
		modelBatch.dispose();
	}
}
