package MODEL;

/**
 * 
 * @author marceau
 * @date Decembre '1
 * @version 1.0
 */

import java.util.Vector;

public class League {
	private String id = null;
	private Vector<Match> matches = null;
	private String link = null;
	public League(String nom){
		id = nom;
		matches = new Vector<Match>();
	}
	public League(String nom, String Weblink){
		id = nom;
		link = Weblink;
		matches = new Vector<Match>();
	}
	
	public void addMatch(String id,String dt, String home, String away,String ligue){
		matches.add(new Match(id,dt,home,away,ligue));
	}
	public void addMatch(Match m){
		matches.add(m);
	}
	
	public Vector<Match> getMatches(){
		return matches;
	}
	
	public String toString(){
		return id;
	}
	public String getLink()
	{
		return link;
	}


}
