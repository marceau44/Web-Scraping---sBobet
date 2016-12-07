package CONTROLLER;

/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import CONTROLLER.QueryTimerTask;
import MODEL.HttpURLConn;
import MODEL.League;
import MODEL.LeagueManager;
import MODEL.Match;
import MODEL.MatchManager;
import MODEL.Market;
import MODEL.MarketManager;
import MODEL.Product;
import MODEL.Bet;
import MODEL.BetManager;
import MODEL.Robot;
import MODEL.Scrap;
import MODEL.httpGet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import VIEW.View_History;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;



public class Controller {
	private 	WebDriver 				driver 				= null;
	private 	Robot 					robot 				= null;
	private 	QueryTimerTask 			task				= null;
	private 	java.util.Timer 		timer				= null;
	
	private boolean 					stop_send 			= true;
	
	// UI elements for the new product
	
	@FXML
	private 	ComboBox 				cMatches			= null;
	@FXML
	private 	ComboBox 				cLigues				= null;
	@FXML
	private 	ComboBox 				cMarket				= null;
	@FXML
	private 	Button 					bSearch				= null;
	@FXML
	private 	Button 					bRefresh			= null;
	
	@FXML
	private 	TableView<Match> 		tableMatches		= null;;
	@FXML
	private 	TableView<Match> 		tablePendingBets	= null;;
	@FXML
	private 	TableView<Match> 		tableBets			= null;;
	@FXML
	private 	Button 					bSend				= null;;
	
	private 	Vector<League> 			leagues 			= null;
	private 	Vector<Match> 			responseMatches 	= null;
	private 	ObservableList<String> 	levelChoice 		=  null;
	private 	ObservableList<Match> 	data 				= FXCollections.observableArrayList();
	private 	ObservableList<Match> 	pendingBets 		= FXCollections.observableArrayList();
	private 	ObservableList<Match> 	dataUser 			= FXCollections.observableArrayList();
	private 	ObservableList<Bet> 	dataBets 			= FXCollections.observableArrayList();
	private 	Map<Product,Scrap> 		dataServerMap 		= new HashMap<Product,Scrap>();
	private 	Map<Product,Scrap> 		dataUserMap 		= new HashMap<Product,Scrap>();
	
	@FXML
	private 	Label 					lSlider				= null;
	@FXML
	private 	Slider 					sSlider  			= null;
	
	@FXML
	private 	TableColumn<Match,String> 	colDate			= null;
	@FXML
	private 	TableColumn<Match,String>	colMatch		= null;;
	@FXML
	private 	TableColumn<Match, String> 	colLigue		= null;;
	@FXML
	private 	TableColumn				 	colIssue		= null;;
	@FXML
	private 	TableColumn<Match,Double> 	colOdd			= null;;
	@FXML
	private 	TableColumn 				colConfirmation	= null;;
	//
	//
	@FXML
	private 	TableColumn<Match,String> 	ucolDate		= null;;
	@FXML
	private 	TableColumn<Match, String>	ucolMatch		= null;;
	@FXML
	private 	TableColumn<Match, String>	ucolLigue		= null;;
	@FXML
	private 	TableColumn<Match, String> 	ucoIssue		= null;;
	@FXML
	private 	TableColumn<Match,String> 	ucolOdd_1		= null;;
	@FXML
	private 	TableColumn<Match,String> 	ucolOdd_2		= null;;
	@FXML
	private 	TableColumn 				ucolConfirmation= null;;
	@FXML
	private 	TableColumn<Match,String> 	ucolStake		= null;;
	//f
	@FXML
	private 	TableColumn<Match,String> 	pcolDate		= null;;
	@FXML
	private 	TableColumn<Match, String> 	pcolMatch		= null;;
	@FXML
	private 	TableColumn<Match, String> 	pcolLigue		= null;;
	@FXML
	private 	TableColumn<Match, String> 	pcolIssue		= null;;
	
	private 	int 						selectedRow 	= 	-1;
	private		int 						selectedRowServ = 	-1;
	private 	int 						selectedRowUser = 	-1;
	LeagueManager leagueManager = new LeagueManager();
	Vector<League> listLeagues = leagueManager.getLeagues();
	MatchManager matchManager = new MatchManager();
	Vector<Match> listMatches = matchManager.getMatches();
	BetManager betManager = new BetManager();
	Vector<Bet> listBets = betManager.getBets();
	MarketManager marketManager = new MarketManager();
	Vector<Market> listMarkets = marketManager.getMarkets();
	

	public Controller(){
		
		robot = new Robot();
		try {
			listLeagues = robot.searchLeagues();

		} catch (FileNotFoundException | UnsupportedEncodingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//client = new Client(robot);
		//driver = new ChromeDriver();
		//task = new QueryTimerTask(this,null);
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public Robot getRobot(){
		return robot;
	}
	
	public void startTimer(ObservableList<Match> req){
		stopTimer();
		task 			= null;
		task 			= new QueryTimerTask(this,null);
		timer 			= new java.util.Timer();
		task.setReq(req);
		timer.schedule(task, 1000);
	}
	
	public void changeReq(ObservableList<Match> req){
		stopTimer();
		System.err.println("New time : "+QueryTimerTask.tick_unit);
		startTimer(req);
	}	
	public void stopTimer(){
		if(timer != null) {
			task.closeAllTasks();
			timer.cancel();}
		timer = null;
	}
	
	public void removePendingMatch(Match m){
		pendingBets.remove(m);
	}
	
	public void removePendingMatch(String id){
		for(Match m : pendingBets){
			if(m.getID().equalsIgnoreCase(id)){
				pendingBets.remove(m);
				break;
			}
				
		}
	}
	
	
	public void sendRequest() throws InterruptedException{
		
		if(stop_send){
			changeReq(pendingBets);
			bSend.setText("STOP");
			stop_send=false;
		}
		else{
			stop_send=true;
			bSend.setText("SEND");
			this.stopTimer();
		}

	}
	
	public void stopRequest(){
		this.stopTimer();
	}

	public void initialize(){
		 Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>()
			        {
			            @Override
			            public TableCell call( TableColumn p )
			            {
			                return new ComboBoxCell();
			            }
			        };
			        
        lSlider.setText(((int)sSlider.getValue()/10)*10+" (sec)");
        sSlider.valueProperty().addListener((obs,old_val,new_val)->{
        	int time = (new_val.intValue()/10)*10;
        	QueryTimerTask.tick_unit = time*1000;
        	changeReq(pendingBets);
        	lSlider.setText(time+" (sec)");
        });
        
		colDate.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Date"));
		colLigue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Ligue"));
		colMatch.setCellValueFactory(new PropertyValueFactory<Match, String>("Match"));
		
		
		colIssue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Issues"));
		colIssue.setCellFactory( cellFactory );
		

		colIssue.setOnEditCommit(
	                new EventHandler<TableColumn.CellEditEvent<Match, String>>()
	                {
	                    @Override
	                    public void handle( TableColumn.CellEditEvent<Match, String> t )
	                    {
	                        (( Match ) t.getTableView().getItems().get(
	                                t.getTablePosition().getRow() )).setIssue( t.getNewValue() );
	                    }
	                }
	        );
		colOdd.setCellValueFactory(
                new PropertyValueFactory<Match, Double>("Odd"));
		colConfirmation.setCellValueFactory(new PropertyValueFactory<Match,Boolean>("confirm"));
		colConfirmation.setCellFactory(new Callback<TableColumn<Match, Boolean>, TableCell<Match, Boolean>>() {

			 

            public TableCell<Match, Boolean> call(TableColumn<Match, Boolean> p) {

                return new CheckBoxTableCell<Match, Boolean>();

            }

        });
		
		tableMatches.setItems(data);
		tableMatches.setEditable(true);
		tableMatches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tableMatches.getSelectionModel().getSelectedItem() != null) 
		        {    
		           TableViewSelectionModel selectionModel = tableMatches.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           selectedRow = tablePosition.getRow();
		           Match m = tableBets.getSelectionModel().getSelectedItem();
		           if(m!=null)System.out.println("Selected Value" + selectedRow+" -- "+m.getID());
		         
		        }
		         }
		     });
		
		initilaizeMatches();
		initializeMarkets();
		initializeBets();
		initializeServer();
		initializeUser();

	}
	

	public void initilaizeMatches(){
		//

		cLigues.valueProperty().addListener(new ChangeListener<League>() {
	       
			@Override
			public void changed(ObservableValue<? extends League> observable,
					League oldValue, League newValue) {
				// We first clear all the fields 
	              cMatches.getItems().clear();
	              matchManager.removeMatches();
	              cMarket.getItems().clear();
		          marketManager.removeMarkets();
		          
		          //We get all the leagues from javeFX
	              League l = listLeagues.get(cLigues.getSelectionModel().getSelectedIndex());
            	  System.out.println("la ligue selectionnée : "+l.toString());

	              if(l.toString().contains("Outright")){
	            	  listMarkets = robot.searchOutrights(l);
	            	  for(Market m : listMarkets){
			  				cMarket.getItems().add(m);
			  			 }
	              }else {
		              
		              //We get all the match in function of the league selected
		              listMatches = robot.searchMatches(l);
		              for(Match m : listMatches){
		  				cMatches.getItems().add(m);
		  			 }
	              }
	              
	             
	              
	              
				
			}    
	      });
		//
		
		 
		HttpURLConn http = new HttpURLConn();
		httpGet get = new httpGet();
		String req1 = null;
		try {
			req1 = http.sendGet("robot?matches=all");
			//req1 = http.sendGet("matches.php");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		leagues = get.handleGetReq1(req1);
		System.out.println(leagues);
		/*
		for(Ligue l : ligues){
			System.err.println(l);
			for(Match m : l.getMatches()){
				System.err.println(m);
			}
		}
		*/
		fillLeagues();
		fillMatches();

	}
	
	public void initializeBets(){
		//

		cMarket.valueProperty().addListener(new ChangeListener<Market>() {
	       
			@Override
			public void changed(ObservableValue<? extends Market> observable,
					Market oldValue, Market newValue) {
				  //vider la table de paris
	              betManager.removeBets();

	              League l = listLeagues.get(cLigues.getSelectionModel().getSelectedIndex());
            	  System.out.println("la ligue selectionnée : "+l.toString());
            	  
	              //récupérer le market selectionné
            	  Market m = listMarkets.get(cMarket.getSelectionModel().getSelectedIndex());
	              System.out.println("le market selectionné : "+m.toString());
	              if(l.toString().contains("Outright")){
	            	  listBets = robot.searchOutrightsBets(m);
	              }
	              else{
		             
		              Match currentMatch = listMatches.get(cMatches.getSelectionModel().getSelectedIndex());
		              
		              //récuperer les bets du match
		              listBets = robot.searchBets(m,currentMatch);
		              
		              //remplir la table des bets 
		              for(Bet b : listBets){
		            	  //tableBets.setItems(dataBets);

		  			}
	              }
	              
	              
	              
				
			}    
	      });
		
	
		fillLeagues();
		fillMatches();

	}
	
	public void initializeMarkets(){
		//

		cMatches.valueProperty().addListener(new ChangeListener<Match>() {
	       
			@Override
			public void changed(ObservableValue<? extends Match> observable,
					Match oldValue, Match newValue) {
				  //vider la table de paris
	              marketManager.removeMarkets();
	              cMarket.getItems().clear();
	              
	              //récupérer le match selectionné
	              Match m = listMatches.get(cMatches.getSelectionModel().getSelectedIndex());
	              System.out.println("le match selectionné : "+m.toString());
	              
	              //récuperer les markets du match
	              listMarkets = robot.searchMarkets(m,cMatches.getSelectionModel().getSelectedIndex());
	              
	              //remplir la table des markets 
	              for(Market ma : listMarkets){

	            	  cMarket.getItems().add(ma);
	  			}
	              
	              
				
			}    
	      });
		//
		
	
		fillLeagues();
		fillMatches();

	}
	
	
	public void fillMatches(){
		/*if(listLeagues == null) {
			System.err.println("No connection with server");
			return;
		}*/
		cMatches.getItems().clear();
		//cLigues.getItems().clear();
		//System.out.println("liste des leagues en cours"+leagueManager.printLeagues());
		
		for(League l : listLeagues){
		
			for(Match m : l.getMatches()){
				
				cMatches.getItems().add(m);
			}
		}
		
		//cLigues.getItems().addAll(listLeagues);
	
	}
	public void fillLeagues(){
		if(listLeagues == null) {
			System.err.println("No connection with server");
			return;
		}
		cLigues.getItems().clear();		
		cLigues.getItems().addAll(listLeagues);
	
	}
	
	public void fillMarkets(){
		if(listMarkets==null){
			System.err.println("No connection with server");
			return;
		}
		cMarket.getItems().clear();
		cMarket.getItems().addAll(listMarkets);
	}
	
	public void showNotif(){
		
	}
	
	public void initializeServer(){

		pcolDate.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Date"));
		pcolMatch.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Match"));
		pcolLigue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Ligue"));
		pcolIssue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Issue"));
		
		tablePendingBets.setItems(pendingBets);
		tablePendingBets.setEditable(true);
		tablePendingBets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tablePendingBets.getSelectionModel().getSelectedItem() != null) 
		        {    
		           TableViewSelectionModel selectionModel = tablePendingBets.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           selectedRowServ = tablePosition.getRow();
		            }
		         }
		     });
	}
	
	public void initializeUser(){
		ucolDate.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Date"));
		ucolMatch.setCellValueFactory(new PropertyValueFactory<Match, String>("Match"));
		
		ucolLigue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Ligue"));
		ucoIssue.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Issue"));
		ucolOdd_1.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Odd_1"));
		ucolOdd_1.setCellFactory(TextFieldTableCell.forTableColumn());
		ucolOdd_1.setOnEditCommit(
		    new EventHandler<CellEditEvent<Match, String>>() {
		        @Override
		        public void handle(CellEditEvent<Match, String> t) {
		            ((Match) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setOdd_1(t.getNewValue());
		        }
		    }
		);  
		ucolOdd_2.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Odd_2"));
		ucolOdd_2.setCellFactory(TextFieldTableCell.forTableColumn());
		ucolOdd_2.setOnEditCommit(
		    new EventHandler<CellEditEvent<Match, String>>() {
		        @Override
		        public void handle(CellEditEvent<Match, String> t) {
		            ((Match) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setOdd_2(t.getNewValue());
		        }
		    }
		);   
		ucolConfirmation.setCellValueFactory(new PropertyValueFactory<Match,Boolean>("confirm"));
		ucolConfirmation.setCellFactory(new Callback<TableColumn<Match, Boolean>, TableCell<Match, Boolean>>() {

			 

            public TableCell<Match, Boolean> call(TableColumn<Match, Boolean> p) {

                return new CheckBoxTableCell<Match, Boolean>();

            }

        });
		ucolStake.setCellValueFactory(
                new PropertyValueFactory<Match, String>("Quantity"));
		ucolStake.setCellFactory(TextFieldTableCell.forTableColumn());
		ucolStake.setOnEditCommit(
		    new EventHandler<CellEditEvent<Match, String>>() {
		        @Override
		        public void handle(CellEditEvent<Match, String> t) {
		            ((Match) t.getTableView().getItems().get(
		                t.getTablePosition().getRow())
		                ).setStake(t.getNewValue());
		        }
		    }
		);   
		tableBets.setItems(dataUser);
		tableBets.setEditable(true);
		tableBets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    @Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(tableBets.getSelectionModel().getSelectedItem() != null) 
		        {    
		           TableViewSelectionModel selectionModel = tableBets.getSelectionModel();
		           ObservableList selectedCells = selectionModel.getSelectedCells();
		           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		           selectedRowUser = tablePosition.getRow();
		          }
		         }
		     });

		tableBets.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            System.out.println(tableBets.getSelectionModel().getSelectedItem());  
		            Match m = tableBets.getSelectionModel().getSelectedItem();
		            if(!pendingBets.contains(m))
		            	pendingBets.add(m);	
		        }
		    }
		});
	}
	
	public void search() throws IOException, InterruptedException, ParseException{
		
		for(Scrap s : dataServerMap.values())
		{
			robot.loadSite(s.getLinkElement());
		}
	}

	public void removeProductUser(){
		System.out.println("Removed : " + selectedRow);
		if(selectedRow >=0 && selectedRow< data.size())
			data.remove(selectedRow);
	}
	
	public void removeBet(){
		System.out.println("Removed bet :" + selectedRowUser);
		if(selectedRowUser >=0 && selectedRowUser< dataUser.size()){
			Match tmp = dataUser.get(selectedRowUser);
			/*
			for(Match m : pendingBets){
				if(tmp.getID().equalsIgnoreCase(m.getID())){
					pendingBets.remove(m);
					break;
				}
			}
			*/
			pendingBets.remove(tmp);
			dataUser.remove(selectedRowUser);		
		}
	}
	
	public void addBet(){
	
		Match m = tableMatches.getSelectionModel().getSelectedItem();
		 
		if(m == null) return;
		Match clone = m.clone();
		this.dataUser.add(clone);
		this.pendingBets.add(clone);	
	}
	
	public void refreshConnection(){

		initilaizeMatches();
		if(leagues == null)
			showNotif();
	}
	
	public void searchMatch(){
		
		if(cLigues.getSelectionModel().getSelectedIndex()>=0)
			System.out.println(cLigues.getSelectionModel().getSelectedItem().toString());
	
		if(cMatches.getSelectionModel().getSelectedIndex()>=0){
			HttpURLConn http = new HttpURLConn();
			httpGet get = new httpGet();
			String req2 = null;
			   
			try {
				req2 = http.sendGet("robot_match?id="+((Match)cMatches.getSelectionModel().getSelectedItem()).getID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			responseMatches = get.handleGetReq2(req2);
			    
			this.data.clear();
			this.data.addAll(responseMatches);

		}
	}
	
	public void showHistory(){
		
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/VIEW/History.fxml"));
		Parent root = null;
		try {
			root = fxml.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cont = fxml.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	

	class ComboBoxCell extends TableCell<Match, Object>
    {

        private ComboBox<String> comboBox;


        public ComboBoxCell()
        {
            comboBox = new ComboBox<>();
            /*
            comboBox.getSelectionModel().selectedItemProperty().addListener((obs,old_val,new_val)->{
            	System.err.println("Value "+new_val);
            	
            	commitEdit( comboBox.getSelectionModel().getSelectedItem() );
                 
            });
            */
        }


        @Override
        public void startEdit()
        {
            if ( !isEmpty() )
            {
                super.startEdit();
                comboBox.setItems( getTableView().getItems().get( getIndex() ).getIssues() );
                comboBox.getSelectionModel().select(getTableView().getItems().get( getIndex() ).getIssue());
                comboBox.focusedProperty().addListener( new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue )
                    {
                        if ( !newValue )
                        {
                            commitEdit( comboBox.getSelectionModel().getSelectedItem() );
                        }
                    }
                } );

                setText(null);
                setGraphic( comboBox );
            }
        }


        @Override
        public void cancelEdit()
        {
            super.cancelEdit();

            setText(getItem().toString() );
            setGraphic( null );
        }


        @Override
        public void updateItem( Object item, boolean empty )
        {
            super.updateItem( item, empty );

            if ( empty )
            {
                setText( null );
                setGraphic( null );
            }
            else
            {
                if ( isEditing() )
                {
                    setText( null );
                    setGraphic( comboBox );
                }
                else
                {

                    setText(getItem().toString());
                    
                    setGraphic( null );
                }
            }
        }

    }
}
