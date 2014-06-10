
package sw.wine.livraison.gen.fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommandeInconnueException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommandeInconnueException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commandeId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommandeInconnueException", propOrder = {
    "commandeId"
})
public class CommandeInconnueException {

    @XmlElement(required = true, nillable = true)
    protected String commandeId;

    /**
     * Gets the value of the commandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommandeId() {
        return commandeId;
    }

    /**
     * Sets the value of the commandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommandeId(String value) {
        this.commandeId = value;
    }

}
