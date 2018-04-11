package affichage;

import java.util.LinkedList;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class VisuJoueur 
{
	int tag_id;
	Spatial player_geom;
	float angleAct = 0;
	BitmapText txt;
	boolean toDisplay = true;
	LinkedList<Node> traject;
	Vector3f prevPos;
	
	/* On fera des lignes entre les différents points*/
	
	public VisuJoueur(int tag_id, Spatial geom, BitmapFont guiFont)
	{
		this.tag_id = tag_id;
		player_geom = geom.clone();
		txt = new BitmapText(guiFont, false);
		txt.setSize(guiFont.getCharSet().getRenderedSize()/10);
		txt.setText(Integer.toString(tag_id));
		traject = new LinkedList<Node>();
		prevPos = null;
	}
}
