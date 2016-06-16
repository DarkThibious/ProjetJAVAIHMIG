package tutoriel;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


public class CubesTest extends SimpleApplication {
	Geometry geomB ,geomR , geomG;
	@Override
	public void simpleInitApp() {
	Box b = new Box(1, 1,1);
	
	//Cube bleu
	geomB = new Geometry("Box", b);
	Material matB = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	matB.setColor("Color",ColorRGBA.Blue);
	geomB.setMaterial(matB);
	geomB.setLocalTranslation(5, 0, 0);
	rootNode.attachChild(geomB);
	
	//Cube rouge
	geomR = new Geometry("Box", b);
	Material matR = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	matR.setColor("Color",ColorRGBA.Red);
	geomR.setMaterial(matR);
	geomR.setLocalTranslation(0, 5, 5);
	rootNode.attachChild(geomR);
	
	//Cube vert
	geomG = new Geometry("Box", b);
	Material matV = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	matV.setColor("Color",ColorRGBA.Green);
	geomG.setMaterial(matV);
	geomG.setLocalTranslation(0, 5, 0);
	rootNode.attachChild(geomG);
	
	/* Camera setting */
	flyCam.setEnabled(false);
	ChaseCamera chaseCam = new ChaseCamera(cam, geomB, inputManager);
	chaseCam.setDragToRotate(true);
	chaseCam.setInvertVerticalAxis(true);
	chaseCam.setRotationSpeed(10.0f);
	chaseCam.setMinVerticalRotation((float) - (Math.PI/2 - 0.0001f));
	chaseCam.setMaxVerticalRotation((float) Math.PI/2);
	chaseCam.setMinDistance(7.5f);
	chaseCam.setMaxDistance(30.0f);
	}
	
	@Override
	public void simpleUpdate(float tpf)
	{
		geomB.rotate(3, 0, 0);
		geomR.rotate(0, 3, 0);
		geomG.rotate(0, 0, 3);
	}
	

	
	
	
	public static void main(String[] args) {
		
		
		

	}

}
