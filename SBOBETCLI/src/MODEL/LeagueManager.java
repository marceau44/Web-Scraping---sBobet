package MODEL;

import java.util.Vector;

public class LeagueManager {
	
	private Vector<League> leagues = null;
	public LeagueManager(){
		leagues = new Vector<League>();
	}
	public void addLeague(String nom, String link){
		leagues.add(new League(nom, link));
	}
	
	public Vector<League> getLeagues(){
		return leagues;
	}
	
	public String printLeagues(){
		String retour="";
		for (League l : leagues)
		{
			retour+=(l.toString());
		}
		return retour;
	}

}
