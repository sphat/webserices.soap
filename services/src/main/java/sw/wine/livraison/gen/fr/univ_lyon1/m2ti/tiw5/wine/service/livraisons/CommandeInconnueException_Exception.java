
package sw.wine.livraison.gen.fr.univ_lyon1.m2ti.tiw5.wine.service.livraisons;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.2
 * 2013-10-16T18:13:09.274+02:00
 * Generated source version: 2.6.2
 */

@WebFault(name = "CommandeInconnueException", targetNamespace = "http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/")
public class CommandeInconnueException_Exception extends Exception {
    
    private CommandeInconnueException commandeInconnueException;

    public CommandeInconnueException_Exception() {
        super();
    }
    
    public CommandeInconnueException_Exception(String message) {
        super(message);
    }
    
    public CommandeInconnueException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandeInconnueException_Exception(String message, CommandeInconnueException commandeInconnueException) {
        super(message);
        this.commandeInconnueException = commandeInconnueException;
    }

    public CommandeInconnueException_Exception(String message, CommandeInconnueException commandeInconnueException, Throwable cause) {
        super(message, cause);
        this.commandeInconnueException = commandeInconnueException;
    }

    public CommandeInconnueException getFaultInfo() {
        return this.commandeInconnueException;
    }
}
