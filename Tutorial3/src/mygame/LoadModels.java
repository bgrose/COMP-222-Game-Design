package ;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * test
 *
 * @author normenhansen
 */
public class LoadModels extends SimpleApplication {

    public static void main(String[] args) {
        LoadModels app = new LoadModels();
        app.start();
    }
    private Node monkey;
    private Node table;

    @Override
    public void simpleInitApp() {
        // load the models
        monkey = (Node) assetManager.loadModel("Models/monkeyTut14.j3o");
        table = (Node) assetManager.loadModel("Models/table.j3o");

        // Forming the scene graph...
        //   ...table
        rootNode.attachChild(table);

        //   ...monkey
        rootNode.attachChild(monkey);

        
        // Set up the lights
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, -1, -1));
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {        
    }
}
