package FootStats;

import java.util.ArrayList;
import java.util.Date;

public class StatsTemps 
{
	public ArrayList<StatsTempsJoueur> listeStatsTJ;
	public Date temps;
	
	public StatsTemps(Date time)
	{
		temps = time;
		listeStatsTJ = new ArrayList<StatsTempsJoueur>();
	}
	
	public StatsTempsJoueur getJoueurEnreg(int playerID) throws NoPlayerException
	{	
		for(StatsTempsJoueur j : this.listeStatsTJ)
		{
			if(j.tag_id == playerID)
			{
				return j;
			}
		}
		throw new NoPlayerException();
	}

	@Override
	public String toString() 
	{
		return "StatsTemps [listeStatsTJ=" + listeStatsTJ + ", temps=" + temps
				+ "]";
	}
}
