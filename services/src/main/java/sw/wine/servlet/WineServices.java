package sw.wine.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import sw.wine.controller.ServiceController;

public class WineServices extends HttpServlet {

	private static final long serialVersionUID = 1L;	
	private String message;
	private static MessageFactory messageFactory;
	private static SOAPMessage soapMessageRequest, soapMessageResponse;
	private static ServiceController serviceController;

	public void init(){
		message = "this is my livraison servlet";
		try {
			messageFactory = MessageFactory.newInstance();
			serviceController = new ServiceController();
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>"+ message +"</h1>");
	}
	
	/**
	 * Récupérer le HttpRequest puis traiter le request<br />
	 * Après le traitement, envoie la réponse au client.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException{
		try {
			//Recupérer l'entête de HTTP request
			MimeHeaders headers = getHeaders(request);
			InputStream is = request.getInputStream();
			soapMessageRequest = messageFactory.createMessage(headers, is);
			
			//Contrôller
			soapMessageResponse = serviceController.handleSOAPRequest(soapMessageRequest);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html");
			OutputStream os = response.getOutputStream();
			soapMessageResponse.writeTo(os);
			os.flush();	
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static MimeHeaders getHeaders(HttpServletRequest request){
		@SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
		MimeHeaders headers = new MimeHeaders();
		while(headerNames.hasMoreElements()){
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			StringTokenizer values = new StringTokenizer(headerValue, ",");
			while(values.hasMoreTokens()){
				headers.addHeader(headerName, values.nextToken().trim());
			}
		}
		return headers;
	}
	
	public void destroy(){
		
	}
}