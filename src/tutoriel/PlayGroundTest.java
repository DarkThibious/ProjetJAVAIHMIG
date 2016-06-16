package tutoriel;

import java.util.ArrayList;

import FootStats.DataManager;
import FootStats.NoPlayerException;
import FootStats.StatsTemps;
import FootStats.StatsTempsJoueur;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PlayGroundTest extends SimpleApplication
{	
	DataManager data;
	Node field_node;
	ArrayList<VisuJoueurs> joueurs;
	Spatial player_geom;
	
	@Override
	public void simpleInitApp() 
	{
		joueurs = new ArrayList<VisuJoueurs>();
		
		assetManager.registerLocator("stade.zip", ZipLocator.class);
		Spatial field_geom = assetManager.loadModel("stade/soccer.obj");
		player_geom = assetManager.loadModel("stade/player.obj");
		
		field_node= new Node("field");
		field_node.attachChild(field_geom);
		rootNode.attachChild(field_node);
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(-2, -10,1));
		directionalLight.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(directionalLight);
	
		/* Camera setting */
		flyCam.setEnabled(false);
		ChaseCamera chaseCam = new ChaseCamera(cam, field_node, inputManager);
		chaseCam.setDragToRotate(true);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setRotationSpeed(10.0f);
		chaseCam.setMinVerticalRotation((float) - (Math.PI/2 - 0.0001f));
		chaseCam.setMaxVerticalRotation((float) Math.PI/2);
		chaseCam.setMinDistance(7.5f);
		chaseCam.setMaxDistance(30.0f);
		
		viewPort.setBackgroundColor(new ColorRGBA(0, 0, 0, 0));
		
	/*	Faire une ligne
	 	Node LinesNode = new Node("linesNode");
		Vector3f oldVect = new Vector3f(1,0,0);
		Vector3f newVect = new Vector3f(-1,1,0);
		
		Line line = new Line(oldVect, newVect);
		Geometry lineGeo = new Geometry("line", line);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color",ColorRGBA.Green);
		lineGeo.setMaterial(mat);
		LinesNode.attachChild(lineGeo);
		rootNode.attachChild(LinesNode);*/
		
		/* Faire un ressort
		Vector3f oldVect = new Vector3f(1, 0,0);
		for(int i=0; i<100; i++)
		{
			float t =i / 5.0f;
			Vector3f newVect = new Vector3f(FastMath.cos(t), t/5.0f, FastMath.sin(t));
			Line line = new Line(oldVect, newVect);
			Geometry lineGeo = new Geometry("line", line);
			Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			mat.setColor("Color",ColorRGBA.Green);
			lineGeo.setMaterial(mat);
			LinesNode.attachChild(lineGeo);
			rootNode.attachChild(LinesNode);
			
			oldVect=newVect;
		}
		*/
	}
	
	public void update(StatsTemps t)
	{
		for(StatsTempsJoueur j : t.listeStatsTJ)
		{
			try 
			{
				getPlayer(j.tag_id).player_geom.setLocalTranslation(j.pos_x, j.pos_y, 0);
				getPlayer(j.tag_id).player_geom.rotate(0, 0, j.angleVue);
			} 
			catch (NoPlayerException e) 
			{
				joueurs.add(new VisuJoueurs(j.tag_id, player_geom));
			}
		}
	}
	
	public VisuJoueurs getPlayer(int playerID) throws NoPlayerException
	{
		for(VisuJoueurs j : joueurs)
		{
			if(j.tag_id == playerID)
			{
				return j;
			}
		}
		throw new NoPlayerException();
	}
	
}
