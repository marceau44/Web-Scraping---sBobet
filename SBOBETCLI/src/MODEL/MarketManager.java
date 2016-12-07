package MODEL;

import java.util.Vector;

public class MarketManager {
	private Vector<Market> markets = null;
	
	public MarketManager(){
		markets = new Vector<Market>();
	}

	public void addMarket(String market,String id){
		markets.add(new Market(market,id));
	}
	
	public Vector<Market> getMarkets(){
		return markets;
	}
	public String printMarkets(){
		String retour="";
		int i= 0;
		for (Market m : markets)
		{
			retour+=("Market "+i+" : "+m.toString()+"\n");
			i++;
		}
		return retour;
	}
	
	public String removeMarkets(){
		markets.clear();
		String retour="";
		int i= 0;
		for (Market m : markets)
		{
			retour+=("Market "+i+" : "+m.toString()+"\n");
			i++;
		}
		return retour;
	}
}
