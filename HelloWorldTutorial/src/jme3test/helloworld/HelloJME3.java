package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Quad;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class HelloJME3 extends SimpleApplication {

    public static void main(String[] args) {
        HelloJME3 app = new HelloJME3();
        app.start();
    }

    @Override
    public void simpleInitApp() {
       Sphere mesh = new Sphere(5, 5, 5, false, true);
        Geometry geom = new Geometry("Sphere", mesh);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        Quad quad = new Quad(2,3); 
        Geometry geomQ = new Geometry("Quad", quad); 
        Material matQ = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md"); 
        matQ.setColor("Color", ColorRGBA.Blue); 
        geomQ.setMaterial(matQ); 
        geomQ.setLocalTranslation(0, 0, 0); 
        rootNode.attachChild(geomQ);


    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
