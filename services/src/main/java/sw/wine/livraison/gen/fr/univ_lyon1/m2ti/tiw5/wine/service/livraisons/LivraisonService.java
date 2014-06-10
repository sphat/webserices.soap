package sw.wine.livraison.gen.fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.2
 * 2013-10-16T18:13:09.279+02:00
 * Generated source version: 2.6.2
 * 
 */
@WebServiceClient(name = "LivraisonService", 
                  wsdlLocation = "file:/home/sereivuth/Desktop/SW/SW_TP3_MonRepot/p1211510-tp3sw/services/src/main/resources/wsdl/livraisonsGenerateParCXF.wsdl",
                  targetNamespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/") 
public class LivraisonService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "LivraisonService");
    public final static QName NewPort = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "NewPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/sereivuth/Desktop/SW/SW_TP3_MonRepot/p1211510-tp3sw/services/src/main/resources/wsdl/livraisonsGenerateParCXF.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(LivraisonService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/home/sereivuth/Desktop/SW/SW_TP3_MonRepot/p1211510-tp3sw/services/src/main/resources/wsdl/livraisonsGenerateParCXF.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public LivraisonService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LivraisonService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LivraisonService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ServiceNameLivraisonItf
     */
    @WebEndpoint(name = "NewPort")
    public ServiceNameLivraisonItf getNewPort() {
        return super.getPort(NewPort, ServiceNameLivraisonItf.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceNameLivraisonItf
     */
    @WebEndpoint(name = "NewPort")
    public ServiceNameLivraisonItf getNewPort(WebServiceFeature... features) {
        return super.getPort(NewPort, ServiceNameLivraisonItf.class, features);
    }

}
