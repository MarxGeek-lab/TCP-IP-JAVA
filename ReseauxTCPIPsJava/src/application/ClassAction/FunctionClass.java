package application.ClassAction;

import java.net.InetAddress;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.MACAddressString;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.mac.MACAddress;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FunctionClass {
	
	//fonction pour verifier 
	public void mouseVerifyOctet(String champ, Label label, TextField tf,TextField tf2) {
		int nb = Integer.parseInt(champ);
		if(nb >= 0 && nb <= 255) {
			label.setText("");
			tf.setDisable(false);
			tf.requestFocus();
			tf2.setId("textCorrecte");
		}else {
			label.setText(" Champ incorrecte...");
			label.setId("textError");
			tf.setDisable(true);
			tf2.setId("textError");
		}
	}
	
	public void operationIp(TextField t1, TextField t2, TextField t3, TextField t4, TextField t5, Label label) {
	
		if(!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() &&
				!t4.getText().isEmpty()) {
			int n1 = Integer.parseInt(t1.getText());
			int n2 = Integer.parseInt(t1.getText());
			int n3 = Integer.parseInt(t1.getText());
			int n4 = Integer.parseInt(t1.getText());
			
			if(n1 >=1 && n1 <= 127) {
				t5.setText("8");
			}
			else if(n1 >=128 && n1 <= 191) {
				t5.setText("16");
			}
			else if(n1 >=192 && n1 <= 223) {
				t5.setText("24");
			}else if(n1 >= 223) {
				label.setText("Addresse Inutilisable...");
			}
		}
	}
	
	//fonction qui determine le type de classe
	public void Classe(TextField t, Label label) {
		int n = Integer.parseInt(t.getText());
		
		if(!t.getText().isEmpty()) {
			if(n >=1 && n <= 127) {
				label.setText("A");
			}else if(n >=128 && n <= 191) {
				label.setText("B");
			}else if(n >=192 && n <= 223) {
				label.setText("C");
			}
		}
		
	}
	
	//fonction qui determine les attribut d'un ip
	public void resultIP(TextField t1, TextField t2, TextField t3, TextField t4, TextField t5,
			Label classe, Label notationCIDR, Label addReseau,Label mask, Label broadcast,
			Label wildcast) throws java.net.UnknownHostException {
		if(!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() && !t4.getText().isEmpty()) {
			
			Classe(t1,classe);
			
			String addr = t1.getText()+"."+t2.getText()+"."+t3.getText()+"."+t4.getText()+"/"+t5.getText();
			String[] parties = addr.split("/");
			String ip = parties[0];
			int prefixe;
			
			if(parties.length < 2) {
				prefixe = 0;
			}else {
				prefixe = Integer.parseInt(parties[1]);
			}
			
			notationCIDR.setText(ip+"/"+prefixe);
			//conversion du entier en tableau
			int masque = 0xffffffff << (32-prefixe);
			int valeur = masque;
			byte[] bytes_mask = new byte[] {
					(byte)(valeur >>>24), 
					(byte)(valeur >> 16 & 0xff),
					(byte)(valeur >> 8 & 0xff),
					(byte)(valeur & 0xff)};
			
				//obtention du masque
				InetAddress netAdd = InetAddress.getByAddress(bytes_mask);
				System.out.println(netAdd);
				mask.setText(netAdd.toString());
				
				/****reseau Adresse***/
				//conversion de l'address ip en long
				long iplong = ipToLong(ip);
				//conversion de l'ip en un tableau de 32 bit
				byte[] bytes_ip = new byte[] {
						(byte)((iplong >> 24) & 0xff),
						(byte)((iplong >> 16) & 0xff),
						(byte)((iplong >>8) & 0xff),
						(byte)(iplong & 0xff)};
				
				// et logique entre ip et le masque
				byte[] bytes_reseau = new byte[] {
						(byte)(bytes_ip[0] & bytes_mask[0]),
						(byte)(bytes_ip[1] & bytes_mask[1]),
						(byte)(bytes_ip[2] & bytes_mask[2]),
						(byte)(bytes_ip[3] & bytes_mask[3])};
				
				// addresse reseau
				InetAddress addRes = InetAddress.getByAddress(bytes_reseau);
				System.out.println(addRes);
				addReseau.setText(addRes.toString());
				
				/*******broadcast********/
				//insersement du masque
				bytes_mask = new byte[] {
						(byte)(~bytes_mask[0] & 0xff),
						(byte)(~bytes_mask[1] & 0xff),
						(byte)(~bytes_mask[2] & 0xff),
						(byte)(~bytes_mask[3] & 0xff)};
				
				InetAddress wildcasts = InetAddress.getByAddress(bytes_mask);
				System.out.println(wildcasts);
				wildcast.setText(wildcasts.toString());
				
				/*******broadcast*********/
				byte[] bytes_broadcast = new byte[] {
						(byte)(bytes_reseau[0] | bytes_mask[0]),
						(byte)(bytes_reseau[1] | bytes_mask[1]),
						(byte)(bytes_reseau[2] | bytes_mask[2]),
						(byte)(bytes_reseau[3] | bytes_mask[3])};
				
				InetAddress broad = InetAddress.getByAddress(bytes_broadcast);
				System.out.println(broad);
				broadcast.setText(broad.toString());
		}else {
			JOptionPane.showMessageDialog(null, "Remplissez tout les champs, puis réessayez...");
		}
	}
	
	//function qui permet de convertir l'ip en long
	public static long ipToLong(String ip) {
		long result = 0;
		String [] addrArray = ip.split("\\.");
		
		for(int i=3; i>=0; i--) {
			long ips = Long.parseLong(addrArray[3-i]);
			result |= ips << (i*8);
		}
		return result;
	}
	
	// 

	
	/**** fonction qui permet d'obtenir les sous-reseaux et les
	 *  plages d'adresse IP
	 * @param tf1
	 * @param tf2
	 * @param tf3
	 * @param tf4
	 * @param tf5
	 * @param labMask
	 * @param list
	 * @param nbNet
	 * @param notifyClass
	 * @param host
	 */
	public void subnetting(TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5, Label labMask
			, ListView < String > list, Label nbNet, Label notifyClass, Label host) {
		

		ObservableList<String> listAd = FXCollections.observableArrayList() ;

		if(!tf1.getText().isEmpty() && !tf2.getText().isEmpty() && !tf3.getText().isEmpty() && 
				!tf4.getText().isEmpty() && !tf5.getText().isEmpty()) {
			
			int n1 = Integer.parseInt(tf1.getText());
			int nb = Integer.parseInt(tf5.getText());
			String octet = null;
			int prefixe = 0;
			
			/**** pour la classe C *****/
			if(n1 > 191 && n1 <= 223) {
				
				notifyClass.setText("C");
				octet = tf1.getText()+"."+tf2.getText()+"."+tf3.getText();
				
				if(nb >= 2 && nb <= 4) {
					labMask.setText("• 255.255.255.192");
					nbNet.setText("• 4 sous-reseaux");
					host.setText("• 62 Adresse IP");
					prefixe = 26;
					
					for(int i=0; i < 193; i += 64) {				
						listAd.add(octet+"."+i+" / " + prefixe + "    IP utilisable : de " + octet + "." + (i + 1) + "/" + prefixe + "  à  " + octet + "." 
								+ ( i + 62 ) + "/" + prefixe + "   Broadcast: " + octet + "." + ( i + 63 ) + "/" + prefixe );
						list.setItems(listAd);											
					}
				} else if (nb > 4 && nb <= 6) {
					labMask.setText("255.255.255.224");
					nbNet.setText("• 6 sous-reseaux");
					host.setText("• 30 Adresse IP");
					prefixe = 27;
					
					for(int i=0; i < 225; i += 32) {
						listAd.add(octet+"."+i+" / " + prefixe + "    IP utilisable : de " + octet + "." + (i + 1) + "/" + prefixe + "  à  " + octet + "." 
								+ ( i + 30 ) + "/" + prefixe + "   Broadcast: " + octet + "." + ( i + 31) + "/" + prefixe );
						list.setItems(listAd);
					}
				} else if (nb > 6 && nb <= 16) {
					labMask.setText("255.255.255.240");
					nbNet.setText("• 16 sous-reseaux");
					host.setText("• 14 Adresse IP");
					prefixe = 28;
					
					for(int i=0; i < 241; i += 16) {
						listAd.add(octet+"."+i+" / " + prefixe + "    IP utilisable : de " + octet + "." + (i + 1) + "/" + prefixe + "  à  " + octet + "." 
								+ ( i + 14 ) + "/" + prefixe + "   Broadcast: " + octet + "." + ( i + 15) + "/" + prefixe );
						list.setItems(listAd);
					}
				} else if (nb > 16 && nb <= 32) {
					nbNet.setText("• 32 sous-reseaux");
					labMask.setText("255.255.255.248");
					host.setText("• 6 Adresse IP");
					prefixe = 29;
					
					for(int i=0; i < 249; i += 8) {
						listAd.add(octet+"."+i+" / " + prefixe + "    IP utilisable : de " + octet + "." + (i + 1) + "/" + prefixe + "  à  " + octet + "." 
								+ ( i + 6 ) + "/" + prefixe + "   Broadcast: " + octet + "." + ( i + 7) + "/" + prefixe );
						list.setItems(listAd);
					}
				} else if (nb > 32 && nb <= 64) {
					labMask.setText("255.255.255.252");
					nbNet.setText("• 64 sous-reseaux");
					host.setText("• 2 Adresse IP");
					prefixe = 30;
					
					for(int i=0; i < 253; i += 4) {
						listAd.add(octet+"."+i+" / " + prefixe + "    IP utilisable : de " + octet + "." + (i + 1) + "/" + prefixe + "  à  " + octet + "." 
								+ ( i + 2 ) + "/" + prefixe + "   Broadcast: " + octet + "." + ( i + 3) + "/" + prefixe );
						list.setItems(listAd);
					}
				}
			}
			/**** pour la classe A *****/
			else if( n1 > 0 && n1 < 128) {
				
				notifyClass.setText("A");
				octet = tf1.getText();
				
				if(nb >= 2 && nb <= 4) {
					labMask.setText("• 255.192.0.0");
					nbNet.setText("• 4 sous-reseaux");
					host.setText("• 16 777 214 Adresse IP");
					prefixe = 10;
					
					for(int i=0; i < 193; i += 64) {
						listAd.add(octet+"."+i + ".0.0/" + prefixe + "      IP Utilisable: " + octet + "." + ( i + 1 ) + ".0.0/" + prefixe + 
								"  à " + octet + "." + ( i + 62 ) + ".255.255/" + prefixe + "       Broadcast:" + octet + "." + ( i + 63 ) + ".255.255");
						list.setItems(listAd);	
					}
				} else if (nb > 4 && nb <= 6) {
					labMask.setText("255.224.0.0");
					nbNet.setText("• 6 sous-reseaux");
					host.setText("• 2 097 152 Adresse IP");
					prefixe = 11;
					
					for(int i=0; i < 225; i += 32) {
						listAd.add(octet+"."+i + ".0.0/" + prefixe + "      IP Utilisable: " + octet + "." + ( i + 1 ) + ".0.0/" + prefixe + 
								"  à " + octet + "." + ( i + 30 ) + ".255.255/" + prefixe + "       Broadcast:" + octet + "." + ( i + 31 ) + ".255.255");
						list.setItems(listAd);
					}
				} else if (nb > 7 && nb <= 16) {
					labMask.setText("255.240.0.0");
					nbNet.setText("• 16 sous-reseaux");
					host.setText("• 1 048 574 Adresse IP");
					prefixe = 12;
					
					for(int i=0; i < 241; i += 16) {
						listAd.add(octet+"."+i + ".0.0/" + prefixe + "      IP Utilisable: " + octet + "." + ( i + 1 ) + ".0.0/" + prefixe + 
								"  à " + octet + "." + ( i + 14 ) + ".255.255/" + prefixe + "       Broadcast:" + octet + "." + ( i + 15 ) + ".255.255");
						list.setItems(listAd);
					}
				} else if (nb > 16 && nb <= 32) {
					nbNet.setText("• 32 sous-reseaux");
					labMask.setText("255.248.0.0");
					host.setText("• 524 286 Adresse IP");
					prefixe = 13;
					
					for(int i=0; i < 249; i += 8) {
						listAd.add(octet+"."+i + ".0.0/" + prefixe + "      IP Utilisable: " + octet + "." + ( i + 1 ) + ".0.0/" + prefixe + 
								"  à " + octet + "." + ( i + 6 ) + ".255.255/" + prefixe + "       Broadcast:" + octet + "." + ( i + 7 ) + ".255.255");
						list.setItems(listAd);
					}
				} else if (nb > 32 && nb <= 64) {
					labMask.setText("255.252.0.0");
					nbNet.setText("• 64 sous-reseaux");
					host.setText("• 262 142 Adresse IP");
					prefixe = 14;
					
					for(int i=3; i < 251; i += 4) {
						listAd.add(octet+"."+i + ".0.0/" + prefixe + "      IP Utilisable: " + octet + "." + ( i + 1 ) + ".0.0/" + prefixe + 
								"  à " + octet + "." + ( i + 2 ) + ".255.255/" + prefixe + "       Broadcast:" + octet + "." + ( i + 3 ) + ".255.255");
						list.setItems(listAd);
					}
				}
			} 
			/**** pour la classe B *****/
			else if( n1 > 127 && n1 < 192 ) {
				
				notifyClass.setText("B");
				octet = tf1.getText()+"."+tf2.getText();
				
				if(nb >= 2 && nb <= 4) {
					labMask.setText("• 255.255.192.0");
					nbNet.setText("• 4 sous-reseaux");
					host.setText("• 16 382 Adresse IP");
					prefixe = 18;
					
					for(int i = 0; i < 193; i += 64 ) {
						
						listAd.add(octet+"."+i+".0 /" + prefixe + "  IP utilisable: " + octet + "." + ( i + 1 ) + ".1  à " + 
								octet + "." + ( i + 62 ) + ".254" + " Broadcast: " + octet + "." + ( i + 63 ) + ".255" );
						list.setItems(listAd);

					}
				} else if (nb >= 5 && nb <= 6) {
					labMask.setText("255.255.224.0");
					nbNet.setText("• 6 sous-reseaux");
					host.setText("• 8 190 Adresse IP");
					prefixe = 19;
					
					for(int i=0; i < 225; i += 32) {
						listAd.add(octet+"."+i+".0 /" + prefixe + "  IP utilisable: " + octet + "." + ( i + 1 ) + ".1  à " + 
								octet + "." + ( i + 30 ) + ".254" + " Broadcast: " + octet + "." + ( i + 31 ) + ".255" );
						list.setItems(listAd);
					}
				} else if (nb > 6 && nb <= 16) {
					labMask.setText("255.255.240.0");
					nbNet.setText("• 16 sous-reseaux");
					host.setText("• 4 094 Adresse IP");
					prefixe = 20;
					
					for(int i=0; i < 241; i += 16) {
						listAd.add(octet+"."+i+".0 /" + prefixe + "  IP utilisable: " + octet + "." + ( i + 1 ) + ".1  à " + 
								octet + "." + ( i + 14 ) + ".254" + " Broadcast: " + octet + "." + ( i + 15 ) + ".255" );
						list.setItems(listAd);
					}
				} else if (nb > 16 && nb <= 32) {
					nbNet.setText("• 32 sous-reseaux");
					labMask.setText("255.255.248.0");
					host.setText("• 2 046 Adresse IP");
					prefixe = 21;
					
					for(int i=0; i < 249; i += 8) {
						listAd.add(octet+"."+i+".0 /" + prefixe + "  IP utilisable: " + octet + "." + ( i + 1 ) + ".1  à " + 
								octet + "." + ( i + 6 ) + ".254" + " Broadcast: " + octet + "." + ( i + 7 ) + ".255" );
						list.setItems(listAd);
					}
				} else if (nb > 32 && nb <= 64) {
					labMask.setText("255.255.252.0");
					nbNet.setText("• 64 sous-reseaux");
					host.setText("• 1 022 Adresse IP");
					prefixe = 22;
					
					for(int i=0; i < 253; i += 4) {
						listAd.add(octet+"."+i+".0 /" + prefixe + "  IP utilisable: " + octet + "." + ( i + 1 ) + ".1  à " + 
								octet + "." + ( i + 2 ) + ".254" + " Broadcast: " + octet + "." + ( i + 3 ) + ".255" );
						list.setItems(listAd);
					}
				}
			}
		}
	}
	

	//fonction pour convertir un adresse mac en ipv6
	public void toIPV6(Label ipv6, TextField t1, TextField t2, TextField t3, TextField t4,
			TextField t5, TextField t6) {
		String mak = t1.getText()+":"+t2.getText()+":"+t3.getText()+":"+t4.getText()+":"+t5.getText()+":"+t6.getText();
		try {
			MACAddress mac = new MACAddressString(mak).toAddress();
			IPv6Address local = mac.toLinkLocalIPv6();
			ipv6.setText(""+local);
		} catch(AddressStringException e) {
			e.printStackTrace();
		}
	}
	
	//
	public void limitNb(TextField t1, TextField t2, Label error, Button btn) {
//		String regex = "*[a-zA-Z0-9]$";
//		Pattern pat = Pattern.compile(regex);
//		Matcher match = pat.matcher(input);
		if(t1.getText().length() == 2) {
			error.setText("");
			t2.requestFocus();
			btn.setDisable(false);
		} else if(t1.getText().length() > 2 || t1.getText().length() < 2 ) {
			error.setText("la longueur maximale de chaque champ doit etre 2");
			error.setId("textError");
			btn.setDisable(true);
		} else {
			error.setText("");
			btn.setDisable(true);
		}
	}
	
	
	
}
