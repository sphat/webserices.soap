package sw.wine.itf;

public interface IClient {
	
	/**
	 * L'identifiant du client.
	 */
	int getId();
	
	/**
	 * Le nom du client.
	 */
	String getNom();

	/**
	 * La quantité d'argent que le client possède sur son compte de location.
	 */
	double getCompte();
}
