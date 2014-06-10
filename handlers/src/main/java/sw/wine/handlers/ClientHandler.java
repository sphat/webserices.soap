package sw.wine.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Node;

import sw.wine.itf.DAOException;
import sw.wine.model.Client;
import sw.wine.model.dao.ClientDAO;

public class ClientHandler implements SOAPHandler<SOAPMessageContext> {

	public static final short ELEMENT_NODE = 1;
	public static final String NS_URI = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/";
	public static String clientId = "";
	public static Client client_info;

	@Resource
	ClientDAO cliDAO;

	public static Client getClient_info() {
		return client_info;
	}

	public static void setClient_info(Client client_info) {
		ClientHandler.client_info = client_info;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	/**
	 * Permttre de détecter le message header<br />
	 * S'il y a le client info dans le header <br />
	 * Récupérer le client id et injecter dans le cotext pour le SOAP Response
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		ApplicationContext app_context = new ClassPathXmlApplicationContext(
				"beans.xml");
		JAXBContext jaxbContext = (JAXBContext) app_context
				.getBean("jaxbContext");
		Unmarshaller unmarShaller = null;

		SOAPMessage soapMsg = context.getMessage();

		// Entrant
		if (Boolean.FALSE.equals(context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
			try {
				unmarShaller = jaxbContext.createUnmarshaller();
				SOAPHeader soapHeader = soapMsg.getSOAPHeader();

				if (soapHeader != null
						&& soapHeader.getChildNodes().getLength() > 0) {
					soapHeader.getChildNodes().item(1).getFirstChild();
					
					// UnmarShaller client
					for (int i = 0; i < soapHeader.getChildNodes().getLength(); i++) {
						Node tmpChildNode = soapHeader.getChildNodes().item(i);
						if (tmpChildNode.getNodeType() == ELEMENT_NODE) {
							setClient_info((Client) unmarShaller
									.unmarshal(tmpChildNode));

							// Authentication
							handlerAuthentification(client_info.getId(),
									soapMsg);
						}
					}

					//Inject Client-ID dans 'HTTP_REQUEST_HEADERS'
					Map<String, List<String>> requestHeader = new HashMap<String, List<String>>();
					requestHeader.put("client-id", Collections.singletonList(String.valueOf(client_info.getId())));
					context.put(MessageContext.HTTP_REQUEST_HEADERS, requestHeader);
					
				} else {
					client_info = null;
				}

			} catch (SOAPException e1) {
				e1.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}

		}

		// Sortant
		if (Boolean.TRUE.equals(context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
			if (client_info != null) {
				if (client_info.getId() > 0) {
					try {
						SOAPPart sp = soapMsg.getSOAPPart();
						SOAPEnvelope se = sp.getEnvelope();
						SOAPBody sb = se.getBody();
						QName qnameClient = new QName(NS_URI, "client", "cli");
						SOAPBodyElement bodyElement = sb
								.addBodyElement(qnameClient);
						@SuppressWarnings("unused")
						SOAPElement seClient = bodyElement.addChildElement(
								"client-id").addTextNode(
								String.valueOf(client_info.getId()));
					} catch (SOAPException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Si le client n'existe pas, le handler interdit l'accès au service
	 * 
	 * @param cli
	 *            Client
	 */
	public void handlerAuthentification(int cli_id, SOAPMessage soapMsg) {
		if (cli_id > 0) {
			try {
				cliDAO = new ClientDAO();
				if (cliDAO.findClientById(cli_id) == null) {
					try {
						SOAPBody soapBody = soapMsg.getSOAPPart().getEnvelope()
								.getBody();
						SOAPFault soapFault = soapBody.addFault();
						soapFault.setFaultString("Client : "
								+ String.valueOf(cli_id) + " n'existe pas !");
						throw new SOAPFaultException(soapFault);
					} catch (SOAPException e) {
					}
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
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
