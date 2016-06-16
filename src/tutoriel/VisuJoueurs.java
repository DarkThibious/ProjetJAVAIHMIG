package tutoriel;

import com.jme3.scene.Spatial;

public class VisuJoueurs 
{
	int tag_id;
	Spatial player_geom;
	
	public VisuJoueurs(int tag_id, Spatial geom)
	{
		this.tag_id = tag_id;
		player_geom = geom.clone();
	}
}
