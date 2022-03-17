package application.ClassAction;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import application.Main;
import application.ClassAction.object.AdresseList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerHome implements Initializable{
	  @FXML
	    private TextField p1;

	    @FXML
	    private TextField p2;

	    @FXML
	    private TextField p3;

	    @FXML
	    private TextField p4;

	    @FXML
	    private TextField p5;

	    @FXML
	    private Label classe;

	    @FXML
	    private Label notation;

	    @FXML
	    private Label adReseaux;

	    @FXML
	    private Label broadcast;

	    @FXML
	    private Label wildcast;
	    
	    @FXML
	    private Label mask;

	    @FXML
	    private Label msgIP;

	    @FXML
	    private TextField nbreSubnet;

	    @FXML
	    private Label msgErrorSubnet;

	    @FXML
	    private Label msgErrorNbre;

	    @FXML
	    private ListView<String> listSubnet;

	    @FXML
	    private Label msgNbreSubnet;

	    @FXML
	    private Label msgNbreHost;

	    @FXML
	    private Label msgAdresseMAC;

	    @FXML
	    private TextField r1;

	    @FXML
	    private Label msgErrorNbre1;

	    @FXML
	    private TextField r2;

	    @FXML
	    private Label msgErrorNbre11;

	    @FXML
	    private TextField r3;

	    @FXML
	    private Label msgErrorNbre111;

	    @FXML
	    private TextField r4;

	    @FXML
	    private TextField ad1;

	    @FXML
	    private TextField ad2;

	    @FXML
	    private TextField ad3;

	    @FXML
	    private TextField ad4;

	    @FXML
	    private TextField ad5;

	    @FXML
	    private TextField ad6;

	    @FXML
	    private Label ipv6;
	    
	    @FXML
	    private Label msgClassNotify;

	    @FXML
	    private Label msgErrorIPV6;
	    
	    @FXML
	    private Button btnSubnet;
	    @FXML
	    private Button traduire;
	    
		int n1;
		int n2;
		int n3;
		int n4;
		int n5;
	    
	    private Main main;
	    private Stage stageHome;
	    
	    FunctionClass font = new FunctionClass();
	    
	    
	    public void setMain(Main m) {
	    	main = m;
	    	stageHome = main.getStage();
	    }


	    @FXML
	    void close(ActionEvent event) {
	    	System.exit(0);
	    }

	    @FXML
	    void vider(ActionEvent event) {

	    }
	    
	    //fonction qui se declenche si les valeur deborde dans les champs
	    @FXML
	    void ipCase1() {
	    	
	    	if(!p1.getText().isEmpty()) {
	    		font.mouseVerifyOctet(p1.getText(), msgIP, p2, p1);
	    	}
	    	if(!p2.getText().isEmpty()) {
	    		font.mouseVerifyOctet(p2.getText(), msgIP, p3, p2);
	    	}
	    	if(!p3.getText().isEmpty()) {
	    		font.mouseVerifyOctet(p3.getText(), msgIP, p4, p3);
	    	}
	    	if(!p4.getText().isEmpty()) {
	    		font.mouseVerifyOctet(p4.getText(), msgIP, p5, p4);
	    	}
	    	
	    	if(!p5.getText().isEmpty()) {
		    	int n5=Integer.parseInt(p5.getText());
		    	if(n5 >=8 && n5 <= 32) {
		    		msgIP.setText("");
		    		p5.setId("textCorrecte");
		    	} else {
		    		msgIP.setText("Champ incorrecte...");
		    		p5.setId("textError");
		    	}
	    	}
	    }
	    
	    /****fonction qui attribue le prefixe en fonction de l'addresse*****/
	    @FXML
	    void mousePrefixe() {
	    	font.operationIp(p1, p2, p3, p4, p5, msgIP);
	    }
	    
	    /******fonction du bouton lancer*******/
	    @FXML
	    void lancer() {
	    	try {
				font.resultIP(p1, p2, p3, p4, p5, classe, notation, adReseaux, mask, broadcast, wildcast);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    /*
	     * partie qui gère la partie des sous reseaux
	     */
	    
	    /****fonction qui gère les case du reseaux adresse***/
	    @FXML
	    void verifyNetworkAd() {
	     	if(!r1.getText().isEmpty()) {
	    		font.mouseVerifyOctet(r1.getText(), msgErrorSubnet, r2, r1);
	    	}
	    	if(!r2.getText().isEmpty()) {
	    		font.mouseVerifyOctet(r2.getText(), msgErrorSubnet, r3, r2);
	    	}
	    	if(!r3.getText().isEmpty()) {
	    		font.mouseVerifyOctet(r3.getText(), msgErrorSubnet, r4, r4);
	    	}
	    }
	    
	    /****method qui permet de géré les erreurs d'entré au niveau du susbneting***/
	    @FXML
	    void lastCaseVerify() {
	    	if( !r4.getText().isEmpty()) {
	    		int nb = Integer.parseInt( r1.getText() );
	    		int nb2 = Integer.parseInt( r2.getText() );
	    		int nb3 = Integer.parseInt( r3.getText() );
	    		int nb4 = Integer.parseInt( r4.getText() );
	    		
	    		if( nb > 0 && nb <= 127 ) {
	    			if( nb2 != 0 || nb3 != 0 || nb4 != 0 ) {
	    				msgErrorSubnet.setText( "Les trois derniers octet doivent ètre 0" );
	    				nbreSubnet.setDisable(true);
	    				msgErrorSubnet.setId("textError");
	    				
	    			} else {
	    				msgErrorSubnet.setText("");
	    				nbreSubnet.setDisable(false);
	    			}
	    		}
	    		else if( nb > 127 && nb <= 191 ) {
	    			if( nb3 != 0 || nb4 != 0 ) {
	    				msgErrorSubnet.setText( "Les deux derniers octet doivent ètre 0" );
	    				nbreSubnet.setDisable(true);
	    				msgErrorSubnet.setId("textError");
	    			} else {
	    				msgErrorSubnet.setText("");
	    				nbreSubnet.setDisable(false);
	    			}
	    		}
	    		else if( nb > 191 && nb <= 223 ) {
	    			if( nb4 != 0 ) {
	    				msgErrorSubnet.setText( "Le dernier octet doit ètre 0" );
	    				nbreSubnet.setDisable(true);
	    				msgErrorSubnet.setId("textError");
	    			} else {
	    				msgErrorSubnet.setText("");
	    				nbreSubnet.setDisable(false);
	    			}
	    		}
	    
	    	}
	    	
	    	if( !nbreSubnet.getText().isEmpty() ) {
	    		int nbre = Integer.parseInt( nbreSubnet.getText() );
	    		
	    		if( nbre == 1 || nbre < 1 ) {
	    			msgErrorNbre.setText("Choisissez un nombre supérieur à 1");
	    			msgErrorNbre.setId("textError");
	    			nbreSubnet.setDisable(false);
	    			btnSubnet.setDisable(true);
	    		} else {
	    			msgErrorNbre.setText("");
	    			nbreSubnet.setDisable(false);
	    			btnSubnet.setDisable(false);
	    		}
	    	}
	    }
	    
	    @FXML
	    void btnSubnet() {
	    	
	    	font.subnetting(r1, r2, r3, r4, nbreSubnet, msgAdresseMAC, listSubnet, 
	    			msgNbreSubnet, msgClassNotify, msgNbreHost);
	    	AdresseList adr = new AdresseList("max");
	    }

	    // method qui permet de valider les champs et de passer au champs suivant
	    @FXML
	    void mous() {
	    	if( !ad1.getText().isEmpty() ) {
	    		font.limitNb(ad1, ad2, msgErrorIPV6, traduire);
	    	}
	    	if( !ad2.getText().isEmpty() ) {
	    		font.limitNb(ad2, ad3, msgErrorIPV6, traduire);
	    	}
	    	
	    	if( !ad3.getText().isEmpty() ) {
	    		font.limitNb(ad3, ad4, msgErrorIPV6, traduire);
	    	}
	    	if( !ad4.getText().isEmpty() ) {
	    		font.limitNb(ad4, ad5, msgErrorIPV6, traduire);
	    	}
	    	if( !ad4.getText().isEmpty() ) {
	    		font.limitNb(ad5, ad6, msgErrorIPV6, traduire);
	    	}
	    }
	    
	    //method du bouton pour l'obtention de l'ipv6
	    @FXML
	    void traduire(ActionEvent event) {
	    	font.toIPV6(ipv6 , ad1, ad2, ad3, ad4, ad5, ad6);
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		
			
		}

}
