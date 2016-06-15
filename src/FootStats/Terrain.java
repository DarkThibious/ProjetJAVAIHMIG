package FootStats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.management.timer.Timer;

public class Terrain 
{
	String[] cheminsFichiers = {"2013-11-03_tromso_stromsgodset_first.csv", "2013-11-03_tromso_stromsgodset_second.csv", "2013-11-07_tromso_anji_first.csv", "2013-11-07_tromso_anji_second.csv"};
	ArrayList<JoueurStat> listeJoueurs;
	ArrayList<StatsTemps> listeTemps;
	
	public Terrain()
	{
		listeJoueurs = new ArrayList<JoueurStat>();
		listeTemps = new ArrayList<StatsTemps>();
	}
	
	void lireFicher(int index)
	{
		File fichier = new File(cheminsFichiers[index]);
		FileReader fr = null;
		try
		{
			fr = new FileReader(fichier);
			BufferedReader br = new BufferedReader(fr);
			try
			{
				int i = 0;
				String ligne = br.readLine();
				while(ligne != null)
				{
					i++;
					System.out.println(i);
					String[] tab = ligne.split(",");
					addStats(tab);
					ligne = br.readLine();
				}
				br.close();
				fr.close();
			}
			catch(IOException e)
			{
				System.out.println("Erreur de lecture : "+e.getMessage());
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addStats(String[] tab)
	{
		String n = tab[0].substring(1, tab[0].length()-1);
		Date timestamp = parseDate(n);
		StatsTempsJoueur stat = new StatsTempsJoueur(timestamp, tab);
		
		if(listeTemps.size() != 0)
		{
			StatsTemps s = listeTemps.get(listeTemps.size()-1);
			if(s.temps.equals(timestamp))
			{
			s.listeStatsTJ.add(stat);
			return;
			}
		}
			
		StatsTemps nouveau = new StatsTemps(timestamp);
		nouveau.listeStatsTJ.add(stat);
		listeTemps.add(nouveau);
	}
	
	public Date parseDate(String s)
	{
		int t = s.length();
		int vt = "yyyy-MM-dd hh:mm:ss.SS".length();
		if(t != vt)
		{
			if(vt - t == 1)
			{
				s += "0";
			}
			else
			{
				s += ".00";		
			}
		}
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
		try 
		{
			d = df.parse(s);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return d;
	}
	
	@Override
	public String toString() 
	{
		return "Terrain [cheminsFichiers=" + Arrays.toString(cheminsFichiers)
				+ "\nlisteJoueurs=" + listeJoueurs + "\nlisteTemps="
				+ listeTemps + "]";
	}

	public static void main(String[] args)
	{
		Terrain e = new Terrain();
		e.lireFicher(0);
		for(StatsTemps s : e.listeTemps)
		{
			System.out.println(s);
		}
	}
	
}

