package sw.wine.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import sw.wine.itf.DAOException;
import sw.wine.model.dao.ClientDAO;
import sw.wine.model.dao.JPAWineDAO;
import junit.framework.TestCase;

public class JPATestClient extends TestCase {
	
	public void testCreateOrUpdateClient() throws DAOException{
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        ClientDAO dao = new ClientDAO(em);
        em.getTransaction().begin();
        Client client = new Client();
        client.setNom("TOTO");
        client.setCompte(13421D);
        dao.insertOrUpdate(client);
        em.getTransaction().commit();
        em.close();
	}
	
    public void testCommandeDetail() throws DAOException{
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        JPAWineDAO dao = new JPAWineDAO(em);
        em.getTransaction().begin();
		@SuppressWarnings("unused")
		Map<String, Object> cmdDetail = new HashMap<String, Object>();
		cmdDetail = dao.getCommandeDetail("CMD-40");
        em.getTransaction().commit();
        em.close();
    }
    
    
    public void testDebiterCompteClient() throws DAOException{
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        ClientDAO dao = new ClientDAO(em);
        dao.debiterCompteClient(18, 29.3D);
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
    }
}
