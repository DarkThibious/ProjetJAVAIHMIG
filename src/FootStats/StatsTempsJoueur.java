package FootStats;

public class StatsTempsJoueur 
{
	int tag_id;
	float pos_x, pos_y;
	float angleVue, direction;
	float energie, vitesse, distanceParcourue;
	
	public StatsTempsJoueur(String[] tab)
	{
		tag_id = Integer.parseInt(tab[1]);
		pos_x = Float.parseFloat(tab[2]);
		pos_y = Float.parseFloat(tab[3]);
		angleVue = Float.parseFloat(tab[4]);
		direction = Float.parseFloat(tab[5]);
		energie = Float.parseFloat(tab[6]);
		vitesse = Float.parseFloat(tab[7]);
		distanceParcourue = Float.parseFloat(tab[8]);
	}
}
