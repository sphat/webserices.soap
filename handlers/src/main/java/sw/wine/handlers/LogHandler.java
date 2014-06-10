package sw.wine.handlers;

import java.io.StringWriter;

import javax.annotation.Resource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogHandler implements LogicalHandler<LogicalMessageContext> {

	private final static Logger LOG = LoggerFactory.getLogger(LogHandler.class);

	// Log Infos pour Services
	private static final String SERVICE_CONFIRMATION = "confirmation";
	private static final String SERVICE_COMMANDE = "commande";
	private static final String SERVICE_LIVRAISON_EFFECTUEE = "livraisonEffectuee";

	private static final String SERVICE_CMD_RESPONSE = "commandeResponse";
	private static final String SERVICE_CONF_RESPONSE = "confirmationResponse";
	private static final String SERVICE_LVR_EFFECTUEE = "livraisonEffectueeResponse";

	// Log Infos pour Wine Services
	private static final String W_SERVICE_ROOT_ELEMENT_NAME = "location";
	private static final String W_SERVICE_SOUS_ROOT_ELEMENT_NAME = "locations";
	private static final String W_SERVICE_ADD_WINE_BY_LOC = "addWinesFromLocation";
	private static final String W_SERVICE_ADD_WINE_BY_LOC_RESPONSE = "addWinesFromLocationResponse";
	private static final String W_SERVICE_GET_WINE_BY_LOC = "getWines";
	private static final String W_SERVICE_GET_WINE_BY_LOC_RESPONSE = "getWinesResponse";
	private static String sortant = "----------------Sortant-----------------";
	private static String entrant = "----------------Entrant-----------------";

	@Autowired(required = true)
	@Resource(name = "client_nom")
	private String client_nom;

	@Override
	public void close(MessageContext ctx) {
	}

	@Override
	public boolean handleFault(LogicalMessageContext ctx) {
		LOG.warn("faute");
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext ctx) {
		Source source = ctx.getMessage().getPayload();
		DOMResult dom = new DOMResult();
		Transformer trans;
		try {
			boolean outbound = (Boolean) ctx
					.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			LOG.info((outbound ? sortant : entrant));

			trans = TransformerFactory.newInstance().newTransformer();
			trans.transform(source, dom);
			Node node = dom.getNode();
			Node root = node.getFirstChild();

			if (outbound) {
				// Sortant
				if (root.getLocalName().equals(SERVICE_CMD_RESPONSE)) {
					LongInfo(node.getChildNodes().item(0));
				} else if (root.getLocalName().equals(SERVICE_CONF_RESPONSE)
						|| root.getLocalName().equals(SERVICE_LVR_EFFECTUEE)) {
					LongInfo(node.getChildNodes().item(0));
				} else if (root.getLocalName().equals(
						W_SERVICE_ADD_WINE_BY_LOC_RESPONSE)) {
					LongWineInfo(root);
				} else if (root.getLocalName().equals(
						W_SERVICE_GET_WINE_BY_LOC_RESPONSE)) {
					LongWineInfo(root);
				}
			} else {
				// Entrant
				if (root.getLocalName().equals(SERVICE_COMMANDE)) {
					LongInfo(node);
				} else if (root.getLocalName().equals(SERVICE_CONFIRMATION)
						|| root.getLocalName().equals(
								SERVICE_LIVRAISON_EFFECTUEE)) {
					LongInfo(node);
				} else if (root.getLocalName()
						.equals(W_SERVICE_ADD_WINE_BY_LOC)) {
					LongWineInfo(root);
				} else if (root.getLocalName()
						.equals(W_SERVICE_GET_WINE_BY_LOC)) {
					LongWineInfo(root);
				}
			}

		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e1) {
			e1.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Client Info
	 */
	public void GetClientInfo() {
		if (ClientHandler.client_info != null)
			LOG.info("->client-id : "
					+ String.valueOf(ClientHandler.client_info.getId()));
	}

	/**
	 * LOG infos pour le LIVRAISON Service
	 * 
	 * @param node
	 */
	@SuppressWarnings("static-access")
	public void LongInfo(Node node) {
		GetClientInfo();
		NodeList nlist = node.getChildNodes();
		Node tmpNode = nlist.item(0);
		if (tmpNode.getNodeType() == node.ELEMENT_NODE) {
			// commande
			Element eElement = (Element) tmpNode;
			if (eElement.getChildNodes().getLength() > 1) {
				NodeList chlidNList = eElement.getChildNodes();
				for (int i = 0; i < chlidNList.getLength(); i++) {
					Node tmpChildNode = chlidNList.item(i);
					// wine
					if (tmpChildNode.getNodeType() == node.ELEMENT_NODE) {
						NodeList sousChlidNList = tmpChildNode.getChildNodes();
						// pour la réponse de la commande
						if (tmpChildNode.getChildNodes().getLength() == 1) {
							LOG.info("->"
									+ tmpChildNode.getLocalName()
									+ " : "
									+ tmpChildNode.getFirstChild()
											.getNodeValue());
						} else {
							// pour la commande infos
							for (int j = 0; j < sousChlidNList.getLength(); j++) {
								Node tmpsChildNode = sousChlidNList.item(j);
								// commande info
								if (tmpsChildNode.getNodeType() == node.ELEMENT_NODE) {
									LOG.info("->"
											+ tmpsChildNode.getLocalName()
											+ " : "
											+ tmpsChildNode.getFirstChild()
													.getNodeValue());
								}
							}
						}
					}
				}

			} else {
				// Pour confirmation et livraisonEffectuée
				LOG.info("->" + eElement.getLocalName() + " : "
						+ eElement.getFirstChild().getNodeValue());
			}
		}
	}

	/**
	 * LOG infos pour le AddWineService
	 * 
	 * @param node
	 */
	@SuppressWarnings("static-access")
	public void LongWineInfo(Node node) {

		NodeList nl = node.getChildNodes();
		Node loc = null;
		// Pour la réponse 'addWine'
		if (nl.getLength() == 1 && nl.item(0).getLocalName().equals("ack")) {
			LOG.info("->" + node.getFirstChild().getLocalName() + " : "
					+ node.getFirstChild().getTextContent());
		} else {
			for (int h = 0; h < nl.getLength(); h++) {
				Node tmpNode = nl.item(h);
				if (tmpNode.getNodeType() == node.ELEMENT_NODE) {
					if (tmpNode.getLocalName().equals(
							W_SERVICE_ROOT_ELEMENT_NAME)) {
						loc = tmpNode;
						// Pour la réponse 'getWine'
					} else if (tmpNode.getLocalName().equals(
							W_SERVICE_SOUS_ROOT_ELEMENT_NAME)) {
						loc = tmpNode.getChildNodes().item(0);
					}
				}
			}
		}

		if (loc != null) {
			NodeList nlist = loc.getChildNodes();
			for (int i = 0; i < nlist.getLength(); i++) {
				Node tmpNode = nlist.item(i);
				if (tmpNode.getNodeType() == node.ELEMENT_NODE) {
					Element eElement = (Element) tmpNode;
					if (eElement.getChildNodes().getLength() > 1) {
						NodeList chlidNList = eElement.getChildNodes();
						for (int j = 0; j < chlidNList.getLength(); j++) {
							Node tmpChildNode = chlidNList.item(j);
							if (tmpChildNode.getNodeType() == node.ELEMENT_NODE) {
								if (tmpChildNode.getFirstChild() != null) {
									// Pour composition
									if (tmpChildNode.getChildNodes()
											.getLength() > 1) {
										NodeList sousChlidNList = tmpChildNode
												.getChildNodes();
										for (int k = 0; k < sousChlidNList
												.getLength(); k++) {
											Node tmpSousChildNode = sousChlidNList
													.item(k);
											if (tmpSousChildNode.getNodeType() == node.ELEMENT_NODE) {
												LOG.info("->"
														+ tmpSousChildNode
																.getLocalName()
														+ " : "
														+ tmpSousChildNode
																.getFirstChild()
																.getNodeValue());
											}
										}
									} else {
										LOG.info("->"
												+ tmpChildNode.getLocalName()
												+ " : "
												+ tmpChildNode.getFirstChild()
														.getNodeValue());
									}
								} else {
									// Pour bottle
									LOG.info("->"
											+ tmpChildNode.getLocalName()
											+ " : "
											+ tmpChildNode.getAttributes()
													.getNamedItem("id")
													.getLocalName()
											+ "="
											+ tmpChildNode.getAttributes()
													.getNamedItem("id")
													.getNodeValue());
								}
							}
						}
					} else {
						// Pour location
						LOG.info("->" + eElement.getLocalName() + " : "
								+ eElement.getFirstChild().getNodeValue());
					}
				}
			}
		}

	}

	/**
	 * Permettre afficher Node element à String
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unused")
	private String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

}