package sw.wine.livraison;

import junit.framework.TestCase;

public class TestLivraison extends TestCase {

	public void testCommande() {
		Livraison lvr = new Livraison();
		Article[] arrArt = new Article[2];
		arrArt[0] = new Article("azertyuiop", 1);
		arrArt[1] = new Article("azertyuiop01", 1);
		try {
			lvr.commande(arrArt);
		} catch (NonDisponibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testConfirmeCommande(){
		Livraison lvr = new Livraison();
		try {
			lvr.confirmeCommande("CMD-103");
		} catch (CommandeInconnueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonDisponibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLivraisonEffectuee(){
		Livraison lvr = new Livraison();
		try {
			lvr.livraisonEffectuee("CMD-103");
		} catch (CommandeInconnueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
