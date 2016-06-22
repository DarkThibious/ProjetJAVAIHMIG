package footStats;

public class Parcelle 
{
	public static final int LARGEUR = 68;
	public static final int LONGUEUR = 105;
	int x, y, nbPassages;
	
	public Parcelle(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.nbPassages = 0;
	}
}
