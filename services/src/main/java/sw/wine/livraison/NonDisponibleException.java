package sw.wine.livraison;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nondisponible")
@XmlAccessorType(XmlAccessType.FIELD)
public class NonDisponibleException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="fault")
	private String referenceVin;
	
	public NonDisponibleException(){}

	public NonDisponibleException(Article article) {
		super("Le vin " + article.getReferenceVin() + " n'est pas disponible");
		this.referenceVin = article.getReferenceVin();
	}

	public String getReferenceVin() {
		return referenceVin;
	}

	public void setReferenceVin(String referenceVin) {
		this.referenceVin = referenceVin;
	}
	
	/**
	 * Permettre de déactiver '<stackTrace/>'
	 */
    @Override
    public Throwable fillInStackTrace() {
        return null;
    }  

}
