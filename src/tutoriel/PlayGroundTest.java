package tutoriel;

import java.awt.Font;
import java.util.ArrayList;

import FootStats.DataManager;
import FootStats.NoPlayerException;
import FootStats.Parcelle;
import FootStats.StatsTemps;
import FootStats.StatsTempsJoueur;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetLocator;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.plugins.BitmapFontLoader;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;

public class PlayGroundTest extends SimpleApplication
{	
	DataManager data;
	int i = 0;
	boolean frozen = false;
	boolean loaded;
	Node field_node;
	Node load_node;
	ArrayList<VisuJoueurs> joueurs;
	Spatial player_geom;
	ChaseCamera chaseCam;
	
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
		
		load_node = new Node("loading");
		BitmapText loading = new BitmapText(guiFont);
		loading.setText("LOADING...");
		load_node.attachChild(loading);
		rootNode.attachChild(load_node);
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(-2, -10,1));
		directionalLight.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(directionalLight);
	
		/* Camera setting */
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, field_node, inputManager);
		chaseCam.setDragToRotate(true);
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setRotationSpeed(10.0f);
		chaseCam.setMinVerticalRotation(0.001f);//(float) - (Math.PI/2 - 0.0001f));
		chaseCam.setMaxVerticalRotation((float) Math.PI/2);
		chaseCam.setMinDistance(7.5f);
		chaseCam.setMaxDistance(100.0f);
		
		viewPort.setBackgroundColor(new ColorRGBA(0, 0, 0, 0));
		
		//Faire une ligne
	 	Node LinesNode = new Node("linesNode");
		Vector3f oldVect = new Vector3f(0,0,0);
		Vector3f newVect = new Vector3f(10,0,0);
		
		Line line = new Line(oldVect, newVect);
		Geometry lineGeo = new Geometry("line", line);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color",ColorRGBA.Red);
		lineGeo.setMaterial(mat);
		LinesNode.attachChild(lineGeo);
		field_node.attachChild(LinesNode);
		
		LinesNode = new Node("linesNode");
		oldVect = new Vector3f(0,0,0);
		newVect = new Vector3f(0,10,0);
		
		line = new Line(oldVect, newVect);
		lineGeo = new Geometry("line", line);
		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color",ColorRGBA.Green);
		lineGeo.setMaterial(mat);
		LinesNode.attachChild(lineGeo);
		field_node.attachChild(LinesNode);
		
		LinesNode = new Node("linesNode");
		oldVect = new Vector3f(0,0,0);
		newVect = new Vector3f(0,0,10);
		
		line = new Line(oldVect, newVect);
		lineGeo = new Geometry("line", line);
		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color",ColorRGBA.Blue);
		lineGeo.setMaterial(mat);
		LinesNode.attachChild(lineGeo);
		field_node.attachChild(LinesNode);
		
		LinesNode = new Node("linesNode");
		oldVect = new Vector3f(0,0,0);
		newVect = new Vector3f(1,0,0);
		
		line = new Line(oldVect, newVect);
		lineGeo = new Geometry("line", line);
		mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color",ColorRGBA.Pink);
		lineGeo.setMaterial(mat);
		LinesNode.attachChild(lineGeo);
		field_node.attachChild(LinesNode);
		
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
		System.out.println("OUI");
		init();
	}
	
	public void init()
	{
		chargementFichier(2);
	}
	
	@Override
	public void simpleUpdate(float tpf)
	{
		if(loaded)
		{
			VisuJoueurs v;
			System.out.println("Affichage : "+ i);
			StatsTemps t = data.getEnregT(i);
			if(!frozen)
			{
				for(VisuJoueurs vJ : joueurs)
				{
					field_node.detachChild(vJ.player_geom);
					field_node.detachChild(vJ.txt);
				}	
			}
			for(StatsTempsJoueur j : t.listeStatsTJ)
			{
				try 
				{
					v = getPlayer(j.tag_id);
				} 
				catch (NoPlayerException e) 
				{
					v = new VisuJoueurs(j.tag_id, player_geom, guiFont);
					joueurs.add(v);
				}
				if(!frozen)
				{
					field_node.attachChild(v.player_geom);
					field_node.attachChild(v.txt);
					v.player_geom.center();
					v.txt.center();
					v.player_geom.rotate(0, (j.direction-v.angleAct), 0);
					v.angleAct = j.direction;
					v.player_geom.setLocalTranslation(Parcelle.longueur/2-j.pos_x, 0, Parcelle.largeur/2+j.pos_y); //
					v.txt.setLocalTranslation(-Parcelle.longueur/2+j.pos_x, v.txt.getLineHeight()+0.5f, -Parcelle.largeur/2+j.pos_y);
				}

				v.txt.lookAt(getCameraPos(), new Vector3f(0,1,0));
			}
			frozen = true;
		}
	}
	
	public Vector3f getCameraPos()
	{
		float hDistance = (float) ((chaseCam.getDistanceToTarget()) * Math.sin((Math.PI / 2) - chaseCam.getVerticalRotation()));
	    Vector3f pos = new Vector3f();
	    pos.set((float) (hDistance * Math.cos(chaseCam.getHorizontalRotation())), (float)((chaseCam.getDistanceToTarget()) * Math.sin(chaseCam.getVerticalRotation())), (float)(hDistance * Math.sin(chaseCam.getHorizontalRotation())));
	    pos.addLocal(rootNode.getWorldTranslation());
		return pos;
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
	
	public void chargementFichier(int index)
	{
		loaded = false;
		rootNode.detachAllChildren();
		chaseCam.setEnabled(false);
		flyCam.setEnabled(true);
		rootNode.attachChild(this.load_node);
		data = new DataManager();
		data.lireFichier(index);
		System.out.println("FNI");
		rootNode.detachAllChildren();
		rootNode.attachChild(field_node);
		chaseCam.setEnabled(true);
		flyCam.setEnabled(false);
		loaded = true;
	}
}
