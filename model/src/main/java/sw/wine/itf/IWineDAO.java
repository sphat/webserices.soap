package sw.wine.itf;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IWineDAO {

    void insertOrUpdate(IWine wine) throws DAOException;

    void insertOrUpdate(IBottle bottle) throws DAOException;

    void insertOrUpdate(ILocation location) throws DAOException;

    IWine findWineById(String fbId) throws DAOException;

    IBottle findBottleById(long id) throws DAOException;
    
    ICommande findCommandById(Long cmd_id) throws DAOException;
    
    /**
     * Commande ID à communiquer avec le client
     * @param cmdID
     * @return objet Commande
     * @throws DAOException
     */
    ICommande findCommandById(String cmdID) throws DAOException;

    ILocation findOrCreateLocation(String country, String region, String subregion) throws DAOException;

    Collection<IWine> getAllWines();

    Collection<ILocation> getAllLocations();

    Collection<IBottle> getAllBottles();
    
    Collection<ICommande> getAllCommandes();

    void deleteWine(IWine wine);

    void deleteBottle(IBottle bottle);

    void deleteLocation(ILocation location);
    
    void deleteCommande(ICommande commande);
    
    void updateCommandeStatus(Long cmd_id, String cmd_status) throws DAOException;
    
    void setLivraisonDate(Long cmd_id, Date lvr_date) throws DAOException;
    
    List<Object> createCommande(Map<String, Integer> cmdArt) throws DAOException;
    
    Map<String, Object> confirmeCommande(String commande_id) throws DAOException;
    
    Map<String, Object> livraisonEffectuee(String commande_id) throws DAOException;

}
