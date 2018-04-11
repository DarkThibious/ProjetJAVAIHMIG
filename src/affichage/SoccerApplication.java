package affichage;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;

import footStats.JoueurStat;
import footStats.NoPlayerException;
import footStats.Parcelle;
import footStats.StatsTemps;
import footStats.StatsTempsJoueur;

public class SoccerApplication extends SimpleApplication
{	
	protected StatsTemps displaying;
	private Node field_node;
	private ArrayList<VisuJoueur> joueurs;
	private Spatial player_geom;
	private Spatial selected_player_geom;
	private Spatial field_geom;
	private ChaseCamera chaseCam;
	protected boolean drawTraject = false;
	protected int nbPoints = 1000;
	protected JoueurStat heatMapPlayer = null;
	protected boolean relief  = true;
	protected boolean heatMap = true;
	
	@Override
	public void simpleInitApp() 
	{
		joueurs = new ArrayList<VisuJoueur>();
		
		assetManager.registerLocator("stade.zip", ZipLocator.class);		
		field_geom = assetManager.loadModel("stade/soccer.obj");
		player_geom = assetManager.loadModel("stade/player.obj");
		selected_player_geom = assetManager.loadModel("stade/sPlayer.obj");
		field_node= new Node("field");
		field_node.attachChild(field_geom);
		rootNode.attachChild(field_node);
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(-2, -10,1));
		directionalLight.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(directionalLight);
	
		/* Camera setting */
		flyCam.setEnabled(false);
		chaseCam = new ChaseCamera(cam, field_node, inputManager);
		chaseCam.setDragToRotate(true);
		chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setRotationSpeed(10.0f);
		chaseCam.setMinVerticalRotation(0.001f);//(float) - (Math.PI/2 - 0.0001f));
		chaseCam.setMaxVerticalRotation((float) Math.PI/2);
		chaseCam.setMinDistance(7.5f);
		chaseCam.setMaxDistance(100.0f);
		chaseCam.setHideCursorOnRotate(true);
		
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
		
		inputManager.addMapping("pick target", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
		inputManager.addListener(selectListener, "pick target");
	}
	
	private ActionListener selectListener = new ActionListener() {
		@Override
	    public void onAction(String name, boolean keyPressed, float tpf)
	    {
	      if (name.equals("pick target") && !keyPressed) {
	        // Reset results list.
	        CollisionResults results = new CollisionResults();
	        // Convert screen click to 3d position
	        Vector2f click2d = inputManager.getCursorPosition();
	        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
	        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
	        // Aim the ray from the clicked spot forwards.
	        Ray ray = new Ray(click3d, dir);
	        // Collect intersections between ray and all nodes in results list.
	        rootNode.collideWith(ray, results);
	        // Use the results -- we rotate the selected geometry.
	        if (results.size() > 0) 
	        {
	          // The closest result is the target that the player picked:
	          Geometry target = results.getClosestCollision().getGeometry();
	          // Here comes the action:
	          for(VisuJoueur v : joueurs)
	          {
	        	  if(v.player_geom == target)
	        	  {
	        		  SoccerWindow.setSelectedPlayer(v.tag_id);
	        		  return;
	        	  }
	          }
	        }
	        SoccerWindow.setSelectedPlayer(0);
	      }
	    }
	  };
	  
	public void createJoueurs(ArrayList<JoueurStat> liste) 
	{
		for(JoueurStat j : liste)
		{
			VisuJoueur v = new VisuJoueur(j.getID(), player_geom, guiFont);
			joueurs.add(v);
		}
	}
	
	@Override
	public void simpleUpdate(float tpf)
	{
		if(SoccerWindow.isLoaded())
		{
			for(VisuJoueur v : joueurs)
			{
				v.setGeom(player_geom);
			}
			if(heatMapPlayer != null)
			{
				try 
				{
					getPlayer(heatMapPlayer.getID()).setGeom(selected_player_geom);
				} catch (NoPlayerException e) {}
			}
			drawPlayers();
			if(heatMap && heatMapPlayer != null)
			{
				drawHeatMap();
			}
		}
	}
	
	public void drawHeatMap()
	{
		Box b = new Box(0.5f,0.5f,0.5f);
		for(Parcelle[] p1 : heatMapPlayer.parcelles)
		{
			for(Parcelle p : p1)
			{
				if(p.nbPassages != 0)
				{
					Geometry geom = new Geometry("Box", b);
					Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
					mat.setColor("Color", new ColorRGBA(0.005f*p.nbPassages, 0, 1.0f-0.005f*p.nbPassages, 0.25f));
					geom.setMaterial(mat);
					if(relief)
					{
						geom = (Geometry) geom.scale(1, p.nbPassages/50, 1);
						geom.setLocalTranslation(-Parcelle.LONGUEUR/2 + p.x, p.nbPassages/50*0.5f, -Parcelle.LARGEUR/2 + p.y);
					}
					else
					{
						geom = (Geometry) geom.scale(1, 0.001f, 1);
						geom.setLocalTranslation(-Parcelle.LONGUEUR/2 + p.x, 0, -Parcelle.LARGEUR/2 + p.y);
					}
					field_node.attachChild(geom);
				}

			}
		}
	}

	public void drawPlayers()
	{
		if(displaying != null)
		{
			VisuJoueur v;
			field_node.detachAllChildren();
			field_node.attachChild(field_geom);
			for(StatsTempsJoueur j : displaying.listeStatsTJ)
			{
				try 
				{
					v = getPlayer(j.tag_id);
					if(v.toDisplay)
					{
						if(drawTraject)
						{
							Vector3f newPos = new Vector3f(-Parcelle.LONGUEUR/2+j.pos_x,0,-Parcelle.LARGEUR/2+j.pos_y);
							if(v.prevPos != null)
							{
								Node lineNode = new Node();
								Line line = new Line(v.prevPos, newPos);
								Geometry line_geom = new Geometry("line", line);
								Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
								mat.setColor("Color", new ColorRGBA(0.2f*j.vitesse, 0, 1.0f-0.2f*j.vitesse, 0.5f));
								System.out.println(j.vitesse);
								line_geom.setMaterial(mat);
								lineNode.attachChild(line_geom);
								v.traject.add(lineNode);
								while(v.traject.size() > nbPoints/2)
								{
									v.traject.remove();
								}
								for(Node l : v.traject)
								{
									field_node.attachChild(l);
								}
							}
							v.prevPos = newPos.clone();
						}
						else
						{
							v.prevPos = null;
							v.traject.clear();
						}
						field_node.attachChild(v.player_geom);
						field_node.attachChild(v.txt);
						v.player_geom.rotate(0, (float) (j.angleVue), 0);//-v.angleAct), 0);
						v.angleAct = (float) j.angleVue;
						v.player_geom.setLocalTranslation(-Parcelle.LONGUEUR/2+j.pos_x, 0, -Parcelle.LARGEUR/2+j.pos_y); 
						v.txt.setLocalTranslation(-Parcelle.LONGUEUR/2+j.pos_x, v.txt.getLineHeight()+0.5f, -Parcelle.LARGEUR/2+j.pos_y);
						v.txt.lookAt(getCameraPos(), new Vector3f(0,1,0));
					}
					else
					{
						v.prevPos = null;
						v.traject.clear();
					}
				}
				catch (NoPlayerException e) {}
			}
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
	
	public VisuJoueur getPlayer(int playerID) throws NoPlayerException
	{
		for(VisuJoueur j : joueurs)
		{
			if(j.tag_id == playerID)
			{
				return j;
			}
		}
		throw new NoPlayerException();
	}
}
