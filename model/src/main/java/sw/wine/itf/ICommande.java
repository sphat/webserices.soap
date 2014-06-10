package sw.wine.itf;

import java.util.Date;

public interface ICommande {
	
	Long getId();
	
	/**
	 * cmdID, commande ID, pour communiquer avec le client
	 */
	String getCmdID();
	
	Date dateLivraison();
	
	String commandeStatus();
	
}
