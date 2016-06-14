package FootStats;

import java.util.ArrayList;
import java.util.Date;

public class StatsTemps 
{
	ArrayList<StatsTempsJoueur> listeStatsTJ;
	Date temps;
	
	public StatsTemps(Date time)
	{
		temps = time;
		listeStatsTJ = new ArrayList<StatsTempsJoueur>();
	}
}
