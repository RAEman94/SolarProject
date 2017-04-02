package local.raeman.com;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by RAEman on 01.04.2017.
 */

public class System {
    private ModelInstance cpinstance;

    //Центральная планета вокруг которой все кружится
    private Model centerPlanet;
    public Model getCenterPlanet(){return centerPlanet;}
    public void setCenterPlanet(Model value){
        this.cpinstance = new ModelInstance(value);
        this.centerPlanet = value;
    }
    //Задание параметров
    private double x,y,z,x0,y0,z0;
    public double r,angle;
    public void axis(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void rotation(boolean x,boolean y,boolean z){
        this.x0 = (x)?1:0;
        this.y0 = (y)?1:0;
        this.z0 = (z)?1:0;
    }
    //Задание скорости вращения
    public float velocity;
    //Система движущаяся на орбите
    public System[] inOrbite;
    public void render(System s, ModelBatch modelBatch,double[] centerPosition){
        s.axis(centerPosition[0],centerPosition[1],centerPosition[2]);
        angle += velocity;//(float)Math.PI / 180f;
        if (angle > 2f * Math.PI)
            angle -= 2f * (float)Math.PI;

        double mx = (float)Math.cos(angle) * r * x0 + x;
        double my = (float)Math.cos(angle) * r * y0 + y;
        double mz = (float)Math.sin(angle) * r * z0 + z;

        Vector3 motion = new Vector3((float) mx,(float) my,(float) mz);

        modelBatch.render(this.cpinstance);
        cpinstance.transform.set(motion,new Quaternion(0,0,0,0));
        if(inOrbite != null)
            for(int i = 0;i<inOrbite.length;i++)
                s.render(inOrbite[i], modelBatch, new double[]{ mx, my, mz});
    }
    public void dispose() {
        centerPlanet.dispose();
    }
}
