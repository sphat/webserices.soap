package sw.wine.livraison;

import java.util.Date;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

@HandlerChain(file="handler-chains.xml")
@WebService(targetNamespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name="ServiceNameLivraisonItf")
public interface ILivraison {

	/**
	 * Créée une commande en fonction de la liste des vins demandée, et renvoie
	 * l'identifiant de la commande, ainsi que le prix.
	 * 
	 * @param vins
	 *            la liste des vins commandée
	 * @return un objet contenant l'identifiant de la commande, ainsi que le
	 *         prix de cette commande.
	 * @throws NonDisponibleException
	 *             si le nombre de bouteilles disponibles pour un certain vin
	 *             est insuffisant.
	 */
	@Transactional(value="transactionManager")
	@WebMethod(operationName="commande")
	CommandeInfos commande(@WebParam(name="vin") Article[] vins) throws NonDisponibleException;

	/**
	 * Valide une commande précédement effectuée, ce qui supprime ou réserve les
	 * bouteilles commandées, et renvoie une date de livraison.
	 * 
	 * @param commandeId
	 *            l'identifiant de la commande concernée.
	 * @return la date de livraison prévue.
	 * @throws CommandeInconnueException
	 *             si l'identifiant fourni ne correspond à aucune commande.
	 * @throws NonDisponibleException
	 *             si le stock de bouteilles n'est plus suffisant pour un
	 *             certain vin.
	 */
	@Transactional(value="transactionManager")
	@WebMethod(operationName="confirmation")
	Date confirmeCommande(@WebParam(name="confirmation") String commandeId) throws CommandeInconnueException,
			NonDisponibleException;

	/**
	 * Confirme la bonne livraison de la commande.
	 * 
	 * @param commandeId
	 *            l'identifiant de la commande concernée.
	 * @return false si la commande avait déjà été confirmée.
	 * @throws CommandeInconnueException
	 *             si l'identifiant fourni ne correspond à aucune commande.
	 */
	@Transactional(value="transactionManager")
	@WebMethod
	boolean livraisonEffectuee(@WebParam(name="confirmation") String commandeId)
			throws CommandeInconnueException;
}
