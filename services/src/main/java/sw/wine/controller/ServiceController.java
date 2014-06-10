package sw.wine.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import org.w3c.dom.Node;

import sw.wine.livraison.Article;
import sw.wine.livraison.CommandeInconnueException;
import sw.wine.livraison.CommandeInfos;
import sw.wine.livraison.Livraison;
import sw.wine.livraison.NonDisponibleException;

//Traiter le message au niveu du PAYLOAD
@ServiceMode(value = Service.Mode.PAYLOAD)

// Specifier le nom de port et end point
@WebServiceProvider(portName="NewPort", serviceName="LivraisonService", targetNamespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", wsdlLocation="classpath:/wsdl/livraisons.wsdl")
public class ServiceController implements Provider<Source> {

	private static final String CONFIRMATION = "confirmation";
	private static final String COMMANDE = "commande";
	private static final String LIVRAISON_EFFECTUEE = "livraisonEffectuee";

	private static JAXBContext jaxbContext;
	private static MessageFactory messageFactory;
	
	public ServiceController() throws SOAPException {
		messageFactory = MessageFactory.newInstance();
	}

	/**
	 * JWS [AT]WebServiceProvider<br />
	 * Traiter le message au niveau du PayLoad
	 */
	@Override
	public Source invoke(Source source) {
		Source sourceResponse = null;
		try {
			// Créer message pour la réponse
			SOAPMessage soapResponse = messageFactory.createMessage();
			DOMResult dom = new DOMResult();
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			
			trans.transform(source, dom);
			
			Node node = dom.getNode();
			Node root = node.getFirstChild();
			Node fist = root.getFirstChild();
			
			if(root.getLocalName().equals(COMMANDE)){
				System.out.println("PayLoad:COMMANDE");
				soapResponse = xmlToObjetCommande(null,node);
			}else if (root.getLocalName().equals(CONFIRMATION)){
				System.out.println("PayLoad:CONFIRMATION");
				soapResponse = xmlToObjetCMDConfirm(fist.getNodeValue());
			}else if (root.getLocalName().equals(LIVRAISON_EFFECTUEE)){
				System.out.println("PayLoad:LIVRAISON_EFFECTUEE");
				soapResponse = xmlToObjetLvrEffectuer(fist.getNodeValue());
			}
			
			sourceResponse = new StreamSource(new ByteArrayInputStream(serializeSOAPMessage(soapResponse).getBytes()));
			
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessageAndLocation());
		} catch (TransformerFactoryConfigurationError e) {
			System.out.println("ServiceControlleu->TransformerFactoryConfigurationError" + e.getMessage());
		} catch (TransformerException e) {
			System.out.println("ServiceControlleu->TransformerException" + e.getMessage());
		} catch (SOAPException e) {
			System.out.println("ServiceControlleu->SOAPException" + e.getMessage());
		} catch (JAXBException e) {
			System.out.println("ServiceControlleu->JAXBException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("ServiceControlleu->IOException" + e.getMessage());
		}
		return sourceResponse;
	}

	/**
	 * Service Cotrôlleur, permettre d'appeller la bonne métier en selon le
	 * SOAPMessage
	 * 
	 * @param request
	 * @return SOAPMessage
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public SOAPMessage handleSOAPRequest(SOAPMessage request)
			throws SOAPException, JAXBException, IOException {
		SOAPBody soapBody = request.getSOAPBody();
		// Créer message pour la réponse
		SOAPMessage soapResponse = messageFactory.createMessage();

		for (Iterator i = soapBody.getChildElements(); i.hasNext();) {
			Object next = i.next();
			if (next instanceof SOAPElement) {
				SOAPElement soapElement = (SOAPElement) next;
				QName qname = soapElement.getElementQName();
				if (qname.getLocalPart().equals(CONFIRMATION)) {
					System.out.println("Servlet:CONFIRMATION");
					soapResponse = xmlToObjetCMDConfirm(soapElement
							.getFirstChild().getNodeValue());
				} else if (qname.getLocalPart().equals(COMMANDE)) {
					System.out.println("Servlet:COMMANDE");
					soapResponse = xmlToObjetCommande(soapElement,null);
				} else if (qname.getLocalPart().equals(LIVRAISON_EFFECTUEE)) {
					System.out.println("Servlet:LIVRAISON_EFFECTUEE");
					soapResponse = xmlToObjetLvrEffectuer(soapElement
							.getFirstChild().getNodeValue());
				}
			}
		}
		return soapResponse;
	}

	/**
	 * Changer la commande statut à effectuer
	 * 
	 * @param cmdId
	 * @return true, fase si la livraison a été effectuée
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	public SOAPMessage xmlToObjetLvrEffectuer(String cmdId)
			throws SOAPException, JAXBException {
		Livraison livraisonObjet = new Livraison();
		// Créer message pour la réponse
		SOAPMessage soapResponse = messageFactory.createMessage();
		SOAPBody soapBodyResp = soapResponse.getSOAPBody();
		Object serviceObjet = null;

		try {
			serviceObjet = livraisonObjet.livraisonEffectuee(cmdId);
			soapBodyResp.addChildElement("livraisonEffectuee").setValue(
					serviceObjet.toString());
		} catch (CommandeInconnueException cmdInconnue) {
			if (cmdInconnue instanceof CommandeInconnueException) {
				SOAPFault fault = soapBodyResp.addFault();
				jaxbContext = JAXBContext
						.newInstance(CommandeInconnueException.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(cmdInconnue, new SAAJResult(fault));
				fault.setFaultString(cmdInconnue.getMessage());
			}
		}

		return soapResponse;
	}

	/**
	 * Confirmer la commande
	 * 
	 * @param cmdId
	 * @return Date de livraison
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	@SuppressWarnings("deprecation")
	public SOAPMessage xmlToObjetCMDConfirm(String cmdId) throws SOAPException,
			JAXBException {
		Livraison livraisonObjet = new Livraison();

		// Créer message pour la réponse
		SOAPMessage soapResponse = messageFactory.createMessage();
		SOAPBody soapBodyResp = soapResponse.getSOAPBody();
		Object serviceObjet = null;

		try {
			serviceObjet = livraisonObjet.confirmeCommande(cmdId);
			if (serviceObjet instanceof Date) {
				soapBodyResp.addChildElement("dateLivraison").setValue(
						((Date) serviceObjet).toGMTString());
			}
		} catch (CommandeInconnueException cmdInconnue) {
			if (cmdInconnue instanceof CommandeInconnueException) {
				SOAPFault fault = soapBodyResp.addFault();
				jaxbContext = JAXBContext
						.newInstance(CommandeInconnueException.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(cmdInconnue, new SAAJResult(fault));
				fault.setFaultString(cmdInconnue.getMessage());
			}
		} catch (NonDisponibleException nonDispo) {
			if (nonDispo instanceof NonDisponibleException) {
				SOAPFault fault = soapBodyResp.addFault();
				jaxbContext = JAXBContext
						.newInstance(NonDisponibleException.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(nonDispo, new SAAJResult(fault));
				fault.setFaultString(nonDispo.getMessage());
			}
		}
		return soapResponse;
	}

	/**
	 * Effectuer la commande
	 * 
	 * @param soapElement
	 * @return SOAP Message
	 * @throws JAXBException
	 * @throws SOAPException
	 */
	public SOAPMessage xmlToObjetCommande(SOAPElement soapElement, Node PayLoad)
			throws JAXBException, SOAPException {
		jaxbContext = JAXBContext.newInstance(new Class[] { Article.class,
				Livraison.class });
		Unmarshaller unmarShaller = jaxbContext.createUnmarshaller();
		Livraison livraisonObjet = null;
		if(soapElement != null){
		livraisonObjet = (Livraison) unmarShaller
				.unmarshal(new DOMSource(soapElement));
		}
		if(PayLoad != null){
			livraisonObjet = (Livraison) unmarShaller
					.unmarshal(PayLoad);			
		}
		Article[] cmdArt = livraisonObjet.getArticle();

		// Créer message pour la réponse
		SOAPMessage soapResponse = messageFactory.createMessage();
		SOAPBody soapBodyResp = soapResponse.getSOAPBody();
		Object serviceObjet = null;

		try {
			serviceObjet = livraisonObjet.commande(cmdArt);
			
			// Trouver le bon class à transformer en XML SOAP
			if (serviceObjet instanceof CommandeInfos) {
				jaxbContext = JAXBContext.newInstance(CommandeInfos.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(serviceObjet, new SAAJResult(soapBodyResp));
			}
		} catch (NonDisponibleException nonDispo) {
			if (nonDispo instanceof NonDisponibleException) {
				SOAPFault fault = soapBodyResp.addFault();
				jaxbContext = JAXBContext
						.newInstance(NonDisponibleException.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.marshal(nonDispo, new SAAJResult(fault));
				fault.setFaultString(nonDispo.getMessage());
			}
		}

		return soapResponse;
	}

	/**
	 * Permettre d'affichier SOAP Message en string
	 * 
	 * @param msg
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static String serializeSOAPMessage(SOAPMessage msg)
			throws SOAPException, IOException {
		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
		msg.writeTo(byteArrayOS);
		return new String(byteArrayOS.toByteArray());
	}

}
