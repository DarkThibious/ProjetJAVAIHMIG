package FootStats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleTest {

	DataManager myDataManager;
	
	@Before
	public void setUp() throws Exception 
	{
		//Initialisation de la partie applicative
		myDataManager = new DataManager();
		myDataManager.lireFichier(0);
	}
	
	@Test
	public void loadTest() 
	{
		//Chargement du fichier 2013-11-03_tromso_stromsgodset_first.csv
		assertNotEquals(myDataManager, null);
	}
	
	@Test
	public void recordNumberTest() 
	{
		//Vérifier que le nombre d'enregistrements est égal à 56661
		assertEquals(new Integer(56661), (Integer) myDataManager.getRecordTNumber());   
	}
	
	@Test
	public void playerPositionTest() 
	{
		//Récupérer l'enregistrement pour l'index 10000 et vérifier que la position en x du joueur avec l'id 5 est égale à 65.57721
			try 
			{
				assertEquals(new Float(65.57721), (Float) myDataManager.getEnregT(10000).getJoueurEnreg(5).pos_x);
			} catch (NoPlayerException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}	
	
	@Test
	public void playerHeatMapMaxValueTest() 
	{
		//vérifier que le joueur #14 a été enregistré au maximum 314 fois dans la même zone d'un mètre carré
		boolean good = false;
		try 
		{
			
			JoueurStat j = myDataManager.getJStatsTot(14);
			good = true;
			for(int x=0;x < Parcelle.longueur;x++)
			{
				for(int y=0;y < Parcelle.largeur;y++)
				{
					if(j.getPassage(x, y) > 314)
					{
						good = false;
					}
				}
			}
		} catch (NoPlayerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(good);
	}	

	@Test
	public void playerHeatMapCornerTest() 
	{
		//vérifier que le joueur #14 n'a jamais été dans le coin de corner le plus proche de l'origine du repère des enregistrements
		try {
			assertEquals(0, myDataManager.getJStatsTot(14).getPassage(0, 0));
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		new SimpleTest();
	}
	
}



