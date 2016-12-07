package MODEL;

/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Match {
	private String id = null;
	private String date = null;
	private String hTeam = null;
	private String aTeam = null;
	private String ligue = null;
	private String odd_1 = null;
	private String odd_2 = null;
	private String stake = null;
	private BooleanProperty confirm = null;
	private ObservableList<String> issues = null;
	private String issue = null;
	private String market = null;
	private String draw = null;
	private String odd_3 = null;
	
	//History odds
	private String odd_begin 	= null;
	private String odd_max 		= null;
	private String odd_final 	= null;
	
	public Match(String idMatch,String dt, String home, String away,String lig){
	    id = idMatch;
		date = dt;
		hTeam = home;
		aTeam = away;
		ligue = lig;
		ArrayList<String> arr = new ArrayList<String>();
		issues =  FXCollections.observableArrayList(arr);
	
		confirm = new SimpleBooleanProperty(false);
		odd_1 = "";
		odd_2 = "";
		stake = "";
		market ="";
		draw = "";
		odd_3="";
		
		issue = "";
	}
	public Match(String idMatch,String dt, String home, String away,String lig,String oddsH,String oddsA,String marketM,String drawM,String oddsD){
	    id = idMatch;
		date = dt;
		hTeam = home;
		aTeam = away;
		ligue = lig;
		ArrayList<String> arr = new ArrayList<String>();
		issues =  FXCollections.observableArrayList(arr);
	
		confirm = new SimpleBooleanProperty(false);
		odd_1 = oddsH;
		odd_2 = oddsA;
		stake = "";
		
		issue = "";
		market = marketM;
		draw = drawM;
		odd_3 = oddsD;
	}
	
	public String getID(){
		return id;
	}
	
	public void updateOdd(String new_odd){
		if(odd_begin == null)
			odd_begin = new_odd;
		if(odd_max == null)
			odd_max = new_odd;
		else if(Float.parseFloat(odd_max)<Float.parseFloat(new_odd))
			odd_max = new_odd;
		
		odd_final = new_odd;
	}
	
	public void setOddsHistory(String oddBegin, String oddMax, String oddFinal){
		odd_begin  = oddBegin;
		odd_max = oddMax;
		odd_final = oddFinal;
	}
	
	public String getOdd_Start(){
		return odd_begin;
	}
	public String getOdd_Max(){
		return odd_max;
	}
	public String getOdd_Final(){
		return odd_final;
	}
	
	public String toHistory(){
		return toString()+issue+";"+odd_begin+";"+odd_max+";"+odd_final+";";
	}

	public void addIssue(String issue){
		if(issues.size()==0) this.issue = issue;
		issues.add(issue);
	}
	
	public String getDate(){
		return date;
	}
	public void setConfirm(Boolean val){
		confirm.setValue(val);
	}
	public String getOdd_1(){
		return odd_1;
	}
	
	public String getOdd_2(){
		return odd_2;
	}
	
	public String getOdd(){
		return (odd_1.length()<1)?odd_2:odd_1;
	}
	
	public void setOdd_1(String odd){
		odd_1 = odd;
	}
	
	public void setOdd_2(String odd){
		odd_2 = odd;
	}
	
	public void setStake(String qte){
		stake = qte;
	}
	
	public String getStake(){
		return stake;
	}
	
	public String getMatch(){
		return hTeam+" - " + aTeam;
	}
	
	public String getLigue(){
		return ligue;
	}
	
	public BooleanProperty confirmProperty(){
		return confirm;
	}
	public String getIssue(){
		return issue;
	}
	
	public String getChoice(){
		return issue;
	}
	
	public String getHome(){
		return hTeam;
	}
	
	public String getAway(){
		return aTeam;
	}
	
	public void setIssue(String iss){
	
		issue = iss;
	}
	
	public ObservableList<String> getIssues(){
		return issues;
	}
	
	public Match clone(){
		Match tmp =  new Match( id,
		date,
		hTeam,
		aTeam,
		ligue);
		tmp.setIssue(issue);
		tmp.setConfirm(confirm.getValue());
		return tmp;
	}
	
	
	public String toString(){
		if(market.equals("")){
			return date + ";" +" Match : "+ hTeam + " ("+odd_1+") - ("+odd_2+") " + aTeam+";";
		}
		else{
			return " Match : "+ hTeam + " ("+odd_1+") - ("+odd_2+") " + aTeam+" -  Draw  ("+odd_3+")";
		}
		
		
	}
}
