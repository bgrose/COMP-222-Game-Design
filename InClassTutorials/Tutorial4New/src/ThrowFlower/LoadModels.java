package ThrowFlower;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.PointLightShadowRenderer;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class LoadModels extends SimpleApplication {

    public static void main(String[] args) {
        LoadModels app = new LoadModels();
        app.start();
    }
    private Node monkey;
    private Node table;
    private Node dancePivot = new Node();
    private Node somersaultPivot = new Node();
    private float myTimer = 0;
    private BulletAppState bulletAppState;
    private static final String THROW = "thorw";

    @Override
    public void simpleInitApp() {


        // load the models
        monkey = (Node) assetManager.loadModel("Models/monkeyTut14.j3o");
        table = (Node) assetManager.loadModel("Models/table.j3o");

        // scale the monkey
        monkey.scale(0.1f);

        // forming the scene graph
        //   Table
        rootNode.attachChild(table);

        //   Pivot nodes for rotation
        dancePivot.attachChild(somersaultPivot);
        somersaultPivot.attachChild(monkey);
        //   Attaching the monkey with pivot nodes
        rootNode.attachChild(dancePivot);

        //   Backdrop 
        Quad quad = new Quad(100, 100);
        Geometry geom = new Geometry("Quad", quad);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        geom.setMaterial(mat);
        geom.setLocalTranslation(-50, -50, -5);
        rootNode.attachChild(geom);

        // Setting up the camera
        cam.setLocation(new Vector3f(0, 2, 7));

        // Set up the lights
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, -1, -1));
        rootNode.addLight(sun);

        PointLight myLight = new PointLight();
        myLight.setColor(ColorRGBA.White);
        myLight.setPosition(new Vector3f(0, 2, 0));
        myLight.setRadius(20);
        rootNode.addLight(myLight);


        // Casting shadows
        // The monkey can only cast shadows
        monkey.setShadowMode(RenderQueue.ShadowMode.Cast);
        // The table can both cast and receive
        table.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        // setting up the renderers. Every kind of light needs a separate one
        PointLightShadowRenderer plsr = new PointLightShadowRenderer(assetManager, 512);
        plsr.setLight(myLight);
        plsr.setFlushQueues(false); // should be false for all but the last renderer

        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 512, 2);
        dlsr.setLight(sun);

        // adding them to the view port (what we see)
        viewPort.addProcessor(plsr);
        viewPort.addProcessor(dlsr);
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        RigidBodyControl tControl = new RigidBodyControl(0);
        table.addControl(tControl);
        bulletAppState.getPhysicsSpace().add(tControl);
        
        RigidBodyControl mControl = new RigidBodyControl(10);
        monkey.addControl(mControl);
        bulletAppState.getPhysicsSpace().add(mControl);
        mControl.setKinematic(true);
        bulletAppState.getPhysicsSpace().setAccuracy(1/300f);
        
        
        
        
    }
    
    
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals(THROW) && !keyPressed) {
                throwFlower();
            }
        }
    };
    
    public void throwFlower() {
        Node flowerNode = (Node) assetManager.loadModel("Models/flower.j3o");
        flowerNode.scale(0.1f);
        flowerNode.setLocalTranslation(cam.getLocation());
        rootNode.attachChild(flowerNode);
        flowerNode.setShadowMode(RenderQueue.ShadowMode.Cast);

        /**
         * Create physical cannon ball and add to physics space.
         */
        RigidBodyControl flowerPhy = new RigidBodyControl(.5f);
        flowerNode.addControl(flowerPhy);
        bulletAppState.getPhysicsSpace().add(flowerPhy);
        flowerPhy.setCcdSweptSphereRadius(1f);
        flowerPhy.setCcdMotionThreshold(0.001f);
        /**
         * Accelerate the physical ball in camera direction to shoot it!
         */
        flowerPhy.setLinearVelocity(cam.getDirection().mult(10));
        flowerPhy.setAngularVelocity(new Vector3f(0, 5, 0));
  }
   
    @Override
    public void simpleUpdate(float tpf) {
        // monkey spins
        monkey.rotate(0, 4 * FastMath.PI * tpf, 0);
        // monkey somersaults
        somersaultPivot.rotate(-FastMath.PI * tpf, 0, 0);
        // monkey dances
        dancePivot.rotate(0, tpf, 0);
        // monkey jumps
        myTimer += tpf;
        somersaultPivot.setLocalTranslation(1, 0.4f
                * FastMath.sin(FastMath.PI * myTimer) + .8f, 0);
    }
}