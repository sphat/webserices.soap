
package sw.wine.livraison.gen.fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NonDisponibleException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonDisponibleException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceVin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonDisponibleException", propOrder = {
    "referenceVin"
})
public class NonDisponibleException {

    @XmlElement(required = true, nillable = true)
    protected String referenceVin;

    /**
     * Gets the value of the referenceVin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceVin() {
        return referenceVin;
    }

    /**
     * Sets the value of the referenceVin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceVin(String value) {
        this.referenceVin = value;
    }

}
