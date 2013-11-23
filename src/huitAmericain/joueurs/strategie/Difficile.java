package huitAmericain.joueurs.strategie;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.Joueur;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;
import huitAmericain.tasDeCartes.TasDeCartes;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Classe Difficile</b>
 * <p>
 * La classe Difficile permet de définir la stratégie facile des joueurs virtuels.
 * Si un joueur virtuel se voit attribuer cette stratégie, il jouera selon la façon définie dans jouerStratégie();
 * Le joueur difficile limite la casse en se défaussant en priorité des cartes qui ont le plus de points.
 * </p>
 * <p>
 * Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont également sérializables.
 * De plus elle implémente l'interface stratégie.
 * </p>
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class Difficile implements Strategie, Serializable{
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8407527725647775863L;

	/**
	 * Redéfinition de la méthode jouerStratégie() pour la stratégie difficile.
	 * <p>
	 * Avec cette stratégie, le joueur virtuel regarde parmi ses cartes jouables
	 * la carte qui vaut le plus de points et s'en défausse.
	 * Si il joue un 8 ou un joker, il parcourt sa main et classe toutes les cartes par couleur.
	 * La nouvelle couleur sera la couleur la plus présente dans sa main.
	 * Si il ne peut pas jouer, alors il pioche.
	 * Il annonce carte et non carte.
	 * </p>
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau) {
		String res = "";
		
		//On parcours tous les joueurs si un joueur n'a pas annoncé carte et qu'il lui reste 1 carte on le fait piocher.
		for(Joueur j : plateau.getListeJoueurs()) {
			if(!j.isAnnoncerCarte() && j.getMain().getSize() == 1 && ! j.equals(plateau.getJoueurActuel())) {
				j.piocher(2, plateau.getTalon());
				res += plateau.getJoueurActuel().getNom() + " a fait piocher 2 cartes à "+j.getNom()+". \n";
			}
		}
		
		//Si le joueur ne peut pas jouer de cartes il pioche
		if(cartesJouable.isEmpty()) {
			res = plateau.getJoueurActuel().piocher(1, plateau.getTalon());
		} 
		
		//Sinon on le fait jouer une stratÃ©gie qui consiste Ã  se dÃ©fausser des cartes qui
		//valent le plus de points
		else {
			
			//Le joueur annonce carte lorsqu'il lui reste plus qu'une carte
			if(plateau.getJoueurActuel().getMain().getSize() == 2) {
				plateau.getJoueurActuel().setAnnoncerCarte(true);
				res += plateau.getJoueurActuel().getNom() + " a annoncé carte. \n";
			}
			
			//Si la carte jouable qui vaut le plus de points est un Joker ou un 8...
			if((cartesJouable.get(getPointMax(cartesJouable)) instanceof CarteJoker)
				|| (cartesJouable.get(getPointMax(cartesJouable)) instanceof Carte8)) {
				//On récupère la couleur que le joueur possède le plus.
				switch (TasDeCartes.getCouleurMax(plateau.getJoueurActuel().getMain().getJeu())) {
				case 0:
					plateau.setCouleurActuelle(Constante.CARREAU);
					break;
				case 1:
					plateau.setCouleurActuelle(Constante.COEUR);
					break;
				case 2:
					plateau.setCouleurActuelle(Constante.PIQUE);
					break;
				case 3:
					plateau.setCouleurActuelle(Constante.TREFLE);
					break;
				}					
			}
			
			res += plateau.getJoueurActuel().jouerCarte(cartesJouable.get(getPointMax(cartesJouable)), plateau);
		}
		
		return res;
	}
	/**
	 * Récupère la position de la carte qui vaut le plus de points dans une liste donnée.
	 * @param liste
	 * La liste que l'on veut parcourir pour trouver la carte valant le plus de points
	 * @return la position dans la liste de la carte valant le plus de points
	 */
	public static int getPointMax(List<Carte> liste) {
		int [] Points = new int [liste.size()];
		int PointMax = 0;
		int indListe = 0;
		//On parcourt la liste des cartes jouables pour lister les points des cartes dans un tableau.
		for (Carte carte : liste){
			Points[indListe] = carte.getPoints();
			indListe++;
		}
		
		//Le tableau créé est parcouru pour trouver l'indice contenant la valeur de points la plus élevée.
		for(int i = 0; i < Points.length; i++){
			if(Points[i] > Points[PointMax]){
				PointMax = i;
			}
		}
		
		return PointMax;
	}	
	
}
