package sw.wine.itf;

import sw.wine.model.Commande;
import sw.wine.model.Wine;

public interface ICommandeArticle {
	
	Long getId();
	
	Wine getWine();
	
	int getQuantity();
	
	Commande getCommande();

}
