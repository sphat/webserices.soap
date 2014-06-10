package sw.wine.livraison;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import sw.wine.itf.DAOException;
import sw.wine.model.Wine;
import sw.wine.model.dao.ClientDAO;
import sw.wine.model.dao.JPAWineDAO;

@WebService(name = "ServiceNameLivraison", portName = "NewPort", serviceName = "LivraisonService", targetNamespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", endpointInterface = "sw.wine.livraison.ILivraison")
@XmlRootElement(name = "commande", namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/")
@XmlAccessorType(XmlAccessType.FIELD)
public class Livraison implements ILivraison {

	@Resource
	WebServiceContext wsMessageCtx;

	@Resource
	JPAWineDAO dao;
	
	@Resource
	ClientDAO cliDAO;

	@XmlElement(name = "vin")
	private Article[] article;

	public Article[] getArticle() {
		return article;
	}

	public void setArticle(Article[] article) {
		this.article = article;
	}

	public Livraison() {

	}

	/**
	 * Créée une commande en fonction de la liste des vins demandée, et renvoie
	 * l'identifiant de la commande, ainsi que le prix.
	 * 
	 * Algo 1.Créer un nouvelle commande(date_livraison=null,
	 * commande_status_ouverte). 2.Bougler dans Objet vins(VinID, VinQTY) et
	 * créer commande_article
	 * 
	 * @param vins
	 *            la liste des vins commandée
	 * @return un objet contenant l'identifiant de la commande, ainsi que le
	 *         prix de cette commande.
	 * @throws NonDisponibleException
	 *             si le nombre de bouteilles disponibles pour un certain vin
	 *             est insuffisant.
	 */
	public CommandeInfos commande(Article[] vins) throws NonDisponibleException {
		try {
			Map<String, Integer> cmdArt = new HashMap<String, Integer>();
			// Vérifier si le stock est suffisant pour la commande
			for (Article v_tmp : vins) {
				Wine objV = dao.findWineById(v_tmp.getReferenceVin());
				if (objV == null)
					throw new NonDisponibleException(v_tmp);
				if (objV.getBottles().size() < v_tmp.getNombre()) {
					throw new NonDisponibleException(v_tmp);
				}
				// Construire map pour la méthode dao.createCommande
				cmdArt.put(v_tmp.getReferenceVin(), v_tmp.getNombre());
			}
			List<Object> cmdInfo = dao.createCommande(cmdArt);
			return new CommandeInfos(Double.parseDouble(cmdInfo.get(1)
					.toString()), cmdInfo.get(0).toString());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

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
	public Date confirmeCommande(String commandeId)
			throws CommandeInconnueException, NonDisponibleException {
		try {

			Map<String, Object> cmdConfirme = new HashMap<String, Object>();
			cmdConfirme = dao.confirmeCommande(commandeId);

			if (cmdConfirme.get(JPAWineDAO._INTROUVABLE) != null) {
				throw new CommandeInconnueException(cmdConfirme.get(
						JPAWineDAO._INTROUVABLE).toString());
			}

			if (cmdConfirme.get(JPAWineDAO._INSUFFISANT) != null) {
				@SuppressWarnings("unchecked")
				List<Object> lstArt = (List<Object>) cmdConfirme
						.get(JPAWineDAO._INSUFFISANT);
				throw new NonDisponibleException(
						new Article(lstArt.get(0).toString(),
								Integer.parseInt(lstArt.get(1).toString())));
			}

			if (cmdConfirme.get(JPAWineDAO._LIVRE_DATE) != null) {
				Date date_lvr = (Date) cmdConfirme.get(JPAWineDAO._LIVRE_DATE);
				return date_lvr;
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Confirme la bonne livraison de la commande.
	 * 
	 * @param commandeId
	 *            l'identifiant de la commande concernée.
	 * @return false si la commande avait déjà été confirmée.
	 * @throws CommandeInconnueException
	 *             si l'identifiant fourni ne correspond à aucune commande.
	 */
	public boolean livraisonEffectuee(String commandeId)
			throws CommandeInconnueException {
		try {

			Map<String, Object> cmdLvrEffec = new HashMap<String, Object>();
			cmdLvrEffec = dao.livraisonEffectuee(commandeId);
			if (cmdLvrEffec.get(JPAWineDAO._INTROUVABLE) != null) {
				throw new CommandeInconnueException(cmdLvrEffec.get(
						JPAWineDAO._INTROUVABLE).toString());
			}
			
			if(!debiterCompteClient(commandeId)) return false;
			
			return Boolean.parseBoolean(cmdLvrEffec.get(JPAWineDAO._CONFIRMEE)
					.toString());

		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Permettre de débiter Compte du client en fonction de ClientID et
	 * CommandeID. 
	 * Cette méthode est appellé dans l'étape 'livraisonEffectuee'
	 * @param cmdID
	 *            Commande ID
	 * @throws DAOException
	 */
	@SuppressWarnings("rawtypes")
	public boolean debiterCompteClient(String cmdID) throws DAOException {
		Map<String, Object> cmdDetail = new HashMap<String, Object>();
		cmdDetail = dao.getCommandeDetail(cmdID);

		if (cmdDetail != null) {
			// Récupérer le infor du client a partier de HTTP_REQUEST_HEADERS,
			// ce info été injecter depuis ClientHandler
			MessageContext mSgctx = wsMessageCtx.getMessageContext();

			Map http_headers = (Map) mSgctx
					.get(MessageContext.HTTP_REQUEST_HEADERS);
			List client = (List) http_headers.get("client-id");

			// Client ID, raw type de clientID est Integer
			String clientID = client.get(0).toString();
			
			if(cliDAO.debiterCompteClient(Integer.parseInt(clientID), (Double) cmdDetail.get(JPAWineDAO.CMD_DETAIL_TOTAL_PRICE))) return true;
			
		}
		return false;
	}

}