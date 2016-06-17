package tutoriel;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Spatial;

public class VisuJoueurs 
{
	int tag_id;
	Spatial player_geom;
	BitmapText txt;
	
	public VisuJoueurs(int tag_id, Spatial geom, BitmapFont guiFont)
	{
		this.tag_id = tag_id;
		player_geom = geom.clone();
		txt = new BitmapText(guiFont, false);
		txt.setSize(guiFont.getCharSet().getRenderedSize()/10);
		txt.setText(Integer.toString(tag_id));
	}
}
