package sw.wine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sw.wine.itf.IClient;

@Entity
@Table(name = "client")
@XmlRootElement(name = "client", namespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/")
@XmlAccessorType(XmlAccessType.FIELD)
public class Client implements IClient {

	/**
	 * L'identifiant du client.
	 */
	@Id
	@GeneratedValue
	@XmlElement(name = "client-id")
	private int id;
 
	/**
	 * Le nom du client.
	 */
	@Column(name = "nom", columnDefinition = "character(255)")
	@XmlElement(name = "nom")
	private String nom;
 
	/**
	 * La quantité d'argent que le client possède sur son compte de location.
	 */
	@Column(name = "compte")
	@XmlElement(name = "compte")
	private double compte;
	
	public Client(){
		super();
	}
 
	public String getNom() {
		return nom;
	}
 
	public void setNom(String nom) {
		this.nom = nom;
	}
 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public double getCompte() {
		return compte;
	}
 
	public void setCompte(double compte) {
		this.compte = compte;
	}	
	
}
