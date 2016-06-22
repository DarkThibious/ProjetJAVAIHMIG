package footStats;

public class JoueurStat 
{
	private int idJoueur;
	private Parcelle[][] parcelles;
	
	public JoueurStat(int id)
	{
		this.idJoueur = id;
		this.parcelles = new Parcelle[Parcelle.LONGUEUR][Parcelle.LARGEUR];
		int i, j;
		for(i=0;i<Parcelle.LONGUEUR;i++)
		{
			for(j=0;j<Parcelle.LARGEUR;j++)
			{
				parcelles[i][j] = new Parcelle(i, j);
			}
		}
	}
	
	public void passage(int x, int y)
	{
		parcelles[x][y].nbPassages++;
	}
	
	public int getID()
	{
		return idJoueur;
	}
	
	public int getPassage(int x, int y)
	{
		return parcelles[x][y].nbPassages;
	}
	
}
