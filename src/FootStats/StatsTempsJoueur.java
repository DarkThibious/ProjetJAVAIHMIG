package FootStats;

import java.util.Date;

public class StatsTempsJoueur 
{
	public Date temps;
	public int tag_id;
	public float pos_x, pos_y;
	public float angleVue, direction;
	public float energie, vitesse, distanceParcourue;
	
	public StatsTempsJoueur(Date t, String[] tab)
	{
		temps = t;
		tag_id = Integer.parseInt(tab[1]);
		pos_x = Float.parseFloat(tab[2]);
		pos_y = Float.parseFloat(tab[3]);
		angleVue = Float.parseFloat(tab[4]);
		direction = Float.parseFloat(tab[5]);
		energie = Float.parseFloat(tab[6]);
		vitesse = Float.parseFloat(tab[7]);
		distanceParcourue = Float.parseFloat(tab[8]);
	}

	@Override
	public String toString() {
		return "StatsTempsJoueur [temps=" + temps + ", tag_id=" + tag_id
				+ ", pos_x=" + pos_x + ", pos_y=" + pos_y + ", angleVue="
				+ angleVue + ", direction=" + direction + ", energie="
				+ energie + ", vitesse=" + vitesse + ", distanceParcourue="
				+ distanceParcourue + "]";
	}
	
/*	public String toString()
	{
		return temps + "\nJoueur : " + tag_id + "\npos : " + pos_x + ", " + pos_y + "\n Direction : " + direction + ", regard : " + angleVue
				energie + " " + t + " " + t + " ";
	}*/
}
