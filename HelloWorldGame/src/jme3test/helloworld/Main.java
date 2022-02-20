package jme3test.helloworld;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;



public class Main extends SimpleApplication {

    Geometry floor1;
    Node boxes;
    Geometry ball;
    private BulletAppState bulletAppState;

    protected Geometry boxFromNormal(String name, Vector3f n) {
        Box b = new Box(10f, 1f, 10f);
        Geometry bg = new Geometry(name, b);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Gray);
        mat.setColor("Diffuse", ColorRGBA.Gray);
        bg.setMaterial(mat);

        Quaternion q = new Quaternion();
        q.fromAxes(n.cross(Vector3f.UNIT_Z), n, Vector3f.UNIT_Z);
        bg.setLocalRotation(q);
        return bg;
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, -5, -7));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        floor1 = boxFromNormal("floor1", Vector3f.UNIT_Y);
        floor1.move(0,10,0);
        rootNode.attachChild(floor1);
        
        setPendulum(0);
        setPendulum(2.1f);
        Geometry last = setPendulum(4.2f);
        last.getControl(RigidBodyControl.class).applyImpulse(new Vector3f(10,0,0),Vector3f.ZERO);

        cam.setLocation(new Vector3f(0, 10, 50));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float tpf) {
   
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    private Geometry setPendulum(float xPos) {
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Red);
        
        Node hookNode = new Node("HookNode");
        RigidBodyControl hookNodeControl = new RigidBodyControl(new BoxCollisionShape(new Vector3f( .3f, .3f, .3f)), 0);
        hookNode.addControl(hookNodeControl);
        hookNodeControl.setPhysicsLocation(new Vector3f(xPos,10,0));
        bulletAppState.getPhysicsSpace().add(hookNodeControl);
        rootNode.attachChild(hookNode);

        Sphere s = new Sphere(60, 60, 1f);
        ball = new Geometry("Sphere", s);
        ball.setMaterial(mat);
        RigidBodyControl ballControl = new RigidBodyControl(1f);
        ball.addControl(ballControl);
        ballControl.setPhysicsLocation(new Vector3f(xPos,0,0));
        ballControl.setRestitution(1);

        bulletAppState.getPhysicsSpace().add(ballControl);
        rootNode.attachChild(ball);
        
        HingeJoint joint=new HingeJoint(hookNodeControl, // A
                     ballControl, // B
                     new Vector3f(0f, 0f, 0f),  // pivot point local to A
                     new Vector3f(0f, 10f, 0f),  // pivot point local to B 
                     Vector3f.UNIT_Z,           // DoF Axis of A (Z axis)
                     Vector3f.UNIT_Z  );        // DoF Axis of B (Z axis)
        
        bulletAppState.getPhysicsSpace().add(joint);
        
        return ball;
    }
}