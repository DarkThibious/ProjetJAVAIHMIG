package FootStats;

public class JoueurStat 
{
	int largeur = 90;
	int longueur = 120;
	int idJoueur;
	Parcelle[][] parcelles;
	
	public JoueurStat(int id)
	{
		this.idJoueur = id;
		this.parcelles = new Parcelle[longueur][largeur];
		int i, j;
		for(i=0;i<largeur;i++)
		{
			for(j=0;j<longueur;j++)
			{
				parcelles[i][j] = new Parcelle(i, j);
			}
		}
	}
	
	public void passage(int x, int y)
	{
		parcelles[x][y].nbPassages++;
	}
	
}
