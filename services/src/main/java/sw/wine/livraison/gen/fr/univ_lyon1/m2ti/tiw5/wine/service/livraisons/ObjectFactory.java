
package sw.wine.livraison.gen.fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Commande_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "commande");
    private final static QName _LivraisonEffectuee_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "livraisonEffectuee");
    private final static QName _Confirmation_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "confirmation");
    private final static QName _CommandeResponse_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "commandeResponse");
    private final static QName _CommandeInconnueException_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "CommandeInconnueException");
    private final static QName _LivraisonEffectueeResponse_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "livraisonEffectueeResponse");
    private final static QName _Infos_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "infos");
    private final static QName _Vin_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "vin");
    private final static QName _NonDisponibleException_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "NonDisponibleException");
    private final static QName _ConfirmationResponse_QNAME = new QName("http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", "confirmationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConfirmationResponse }
     * 
     */
    public ConfirmationResponse createConfirmationResponse() {
        return new ConfirmationResponse();
    }

    /**
     * Create an instance of {@link Commande }
     * 
     */
    public Commande createCommande() {
        return new Commande();
    }

    /**
     * Create an instance of {@link CommandeInconnueException }
     * 
     */
    public CommandeInconnueException createCommandeInconnueException() {
        return new CommandeInconnueException();
    }

    /**
     * Create an instance of {@link LivraisonEffectuee }
     * 
     */
    public LivraisonEffectuee createLivraisonEffectuee() {
        return new LivraisonEffectuee();
    }

    /**
     * Create an instance of {@link Article }
     * 
     */
    public Article createArticle() {
        return new Article();
    }

    /**
     * Create an instance of {@link LivraisonEffectueeResponse }
     * 
     */
    public LivraisonEffectueeResponse createLivraisonEffectueeResponse() {
        return new LivraisonEffectueeResponse();
    }

    /**
     * Create an instance of {@link CommandeInfos }
     * 
     */
    public CommandeInfos createCommandeInfos() {
        return new CommandeInfos();
    }

    /**
     * Create an instance of {@link NonDisponibleException }
     * 
     */
    public NonDisponibleException createNonDisponibleException() {
        return new NonDisponibleException();
    }

    /**
     * Create an instance of {@link Confirmation }
     * 
     */
    public Confirmation createConfirmation() {
        return new Confirmation();
    }

    /**
     * Create an instance of {@link CommandeResponse }
     * 
     */
    public CommandeResponse createCommandeResponse() {
        return new CommandeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Commande }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "commande")
    public JAXBElement<Commande> createCommande(Commande value) {
        return new JAXBElement<Commande>(_Commande_QNAME, Commande.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LivraisonEffectuee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "livraisonEffectuee")
    public JAXBElement<LivraisonEffectuee> createLivraisonEffectuee(LivraisonEffectuee value) {
        return new JAXBElement<LivraisonEffectuee>(_LivraisonEffectuee_QNAME, LivraisonEffectuee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Confirmation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "confirmation")
    public JAXBElement<Confirmation> createConfirmation(Confirmation value) {
        return new JAXBElement<Confirmation>(_Confirmation_QNAME, Confirmation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "commandeResponse")
    public JAXBElement<CommandeResponse> createCommandeResponse(CommandeResponse value) {
        return new JAXBElement<CommandeResponse>(_CommandeResponse_QNAME, CommandeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandeInconnueException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "CommandeInconnueException")
    public JAXBElement<CommandeInconnueException> createCommandeInconnueException(CommandeInconnueException value) {
        return new JAXBElement<CommandeInconnueException>(_CommandeInconnueException_QNAME, CommandeInconnueException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LivraisonEffectueeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "livraisonEffectueeResponse")
    public JAXBElement<LivraisonEffectueeResponse> createLivraisonEffectueeResponse(LivraisonEffectueeResponse value) {
        return new JAXBElement<LivraisonEffectueeResponse>(_LivraisonEffectueeResponse_QNAME, LivraisonEffectueeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandeInfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "infos")
    public JAXBElement<CommandeInfos> createInfos(CommandeInfos value) {
        return new JAXBElement<CommandeInfos>(_Infos_QNAME, CommandeInfos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Article }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "vin")
    public JAXBElement<Article> createVin(Article value) {
        return new JAXBElement<Article>(_Vin_QNAME, Article.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NonDisponibleException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "NonDisponibleException")
    public JAXBElement<NonDisponibleException> createNonDisponibleException(NonDisponibleException value) {
        return new JAXBElement<NonDisponibleException>(_NonDisponibleException_QNAME, NonDisponibleException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/", name = "confirmationResponse")
    public JAXBElement<ConfirmationResponse> createConfirmationResponse(ConfirmationResponse value) {
        return new JAXBElement<ConfirmationResponse>(_ConfirmationResponse_QNAME, ConfirmationResponse.class, null, value);
    }

}
