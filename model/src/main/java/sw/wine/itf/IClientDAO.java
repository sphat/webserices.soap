package sw.wine.itf;

public interface IClientDAO {

	/**
	 * Permettre de créer ou mise à jour le client existant 
	 * @param client
	 * @throws DAOException
	 */
    void insertOrUpdate(IClient client) throws DAOException;

    /**
     * Chercher client par ID
     * @param clientId
     * @return
     * @throws DAOException
     */
    IClient findClientById(int clientId) throws DAOException;
    
    /**
     * Permettre d'ébiter le compte client
     * @param clientID
     * @param a_debit
     * @return
     * @throws DAOException
     */
    Boolean debiterCompteClient(int clientID, double a_debit) throws DAOException;
	
}
