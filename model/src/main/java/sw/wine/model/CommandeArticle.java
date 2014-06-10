package sw.wine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sw.wine.itf.ICommandeArticle;

@Entity
@Table(name = "commande_article")
public class CommandeArticle implements ICommandeArticle, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="wine_id")
	private Wine wine;
	
	@Column(name = "quantity", columnDefinition = "integer")
	private Integer quantity;

	@ManyToOne
	//@Column(name = "commande_id")
	private Commande commande;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public Wine getWine() {
		return this.wine;
	}

	public void setWine(Wine wine) {
		this.wine = wine;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	
}