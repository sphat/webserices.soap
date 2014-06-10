package sw.wine.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import sw.wine.itf.DAOException;
import sw.wine.itf.IClient;
import sw.wine.itf.IClientDAO;
import sw.wine.model.Client;

public class ClientDAO implements IClientDAO {
	private static EntityManager em;
	private static EntityManagerFactory emf;
	private static EntityTransaction tx;
	
	public ClientDAO() {
	}
	
	public ClientDAO(EntityManager em_ref) {
		em = em_ref;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void setEmf(EntityManagerFactory emf) {
		ClientDAO.emf = emf;
		em = emf.createEntityManager();
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em_ref) {
		em = em_ref;
	}

	/**
	 * Permettre de créer ou mise à jour le client existant 
	 * @param client
	 * @throws DAOException
	 */
	@Override
	public void insertOrUpdate(IClient client) throws DAOException {
		try {
			Client c = (Client) client;
			if (c.getId() > 0 && em.find(Client.class, c.getId()) != null) {
				em.merge(c);
			} else {
				em.persist(c);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

    /**
     * Chercher client par ID
     * @param clientId
     * @return
     * @throws DAOException
     */
	public IClient findClientById(int clientId) throws DAOException {
		return em.find(Client.class, clientId);
	}

    /**
     * Permettre d'ébiter le compte client
     * @param clientID
     * @param a_debit
     * @return
     * @throws DAOException
     */
	@Override
	public Boolean debiterCompteClient(int clientID, double a_debit)
			throws DAOException {
		// TODO Auto-generated method stub
		Client c = em.find(Client.class, clientID);
		if(c!=null){
			if(c.getCompte() >= a_debit){
				double compte_courant = c.getCompte() - a_debit;
				c.setCompte(compte_courant);
				tx = em.getTransaction();
				tx.begin();
				em.merge(c);
				tx.commit();
				return true;
			}
		}
		return false;
	}
	
}
