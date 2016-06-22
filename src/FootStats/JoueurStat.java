package FootStats;

public class JoueurStat 
{
	private int idJoueur;
	private Parcelle[][] parcelles;
	
	public JoueurStat(int id)
	{
		this.idJoueur = id;
		this.parcelles = new Parcelle[Parcelle.longueur][Parcelle.largeur];
		int i, j;
		for(i=0;i<Parcelle.longueur;i++)
		{
			for(j=0;j<Parcelle.largeur;j++)
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
