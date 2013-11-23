package huitAmericain.joueurs.strategie;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;
import huitAmericain.tasDeCartes.TasDeCartes;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <b>Classe Moyen</b>
 * <p>
 * La classe Moyen permet de définir la stratégie moyen des joueurs virtuels.
 * Si un joueur virtuel se voit attribuer cette stratégie, il jouera selon la façon définie dans jouerStratégie();
 * Le joueur moyen essaie de garder un équilibre dans ses couleurs.
 * </p>
 * <p>
 * Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont également sérializables.
 * De plus elle implémente l'interface stratégie.
 * </p>
 * @see Moyen#jouerStrategie(List, Plateau)
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class Moyen implements Strategie, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -6296970633033400886L;

	/**
	 * Redéfinition de la méthode jouerStratégie() pour la stratégie moyen.
	 * <p>
	 * Avec cette stratégie, le joueur virtuel classe ses cartes jouables par couleur.
	 * Il ne regarde ensuite que les cartes de la couleur la plus présente, les mélange
	 * et joue la première carte, qu'elle soit normale ou spéciale.
	 * Si il joue un 8 ou un joker, il parcourt sa main et classe par couleur cette fois-ci toutes les cartes.
	 * La nouvelle couleur sera la couleur la plus présente dans sa main.
	 * Si il ne peut pas jouer, alors il pioche.
	 * Il annonce carte mais pas non carte.
	 * </p>
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau) {
		String res = "";
		//Si le joueur ne peut pas jouer de cartes il pioche
		if(cartesJouable.isEmpty()) {
			plateau.getJoueurActuel().piocher(1, plateau.getTalon());
		} 
		//Sinon on le fait jouer une stratégie qui consiste à garder un équilibre des couleurs
		else {
			
			//Le joueur annonce carte lorsqu'il lui reste plus qu'une carte
			if(plateau.getJoueurActuel().getMain().getSize() == 2) {
				plateau.getJoueurActuel().setAnnoncerCarte(true);
				res += plateau.getJoueurActuel().getNom() + " a annoncé carte. \n";
			}
			
			List<Carte> listeCarteCouleur = new LinkedList<Carte>();
			//On récupère la couleur la plus présente dans les cartes jouables.
			switch (TasDeCartes.getCouleurMax(cartesJouable)) {
				/*Si le nombre de carte maximum est à l'indice 0 du tableau, ça signifie que 
				 *les cartes les plus nombreuses sont des carreaux.
				 *On créé une nouvelle liste contenant uniquement les cartes carreaux pour que le
				 *joueur virtuel puisse jouer aléatoirement à partir de cette liste.
				*/
				case 0 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.CARREAU)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est à l'indice 1 du tableau, ça signifie que 
				 *les cartes les plus nombreuses sont des coeurs.
				 *On créé une nouvelle liste contenant uniquement les cartes coeurs pour que le
				 *joueur virtuel puisse jouer aléatoirement à partir de cette liste. */
				case 1 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.COEUR)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est à l'indice 2 du tableau, ça signifie que 
				 *les cartes les plus nombreuses sont des piques.
				 *On créé une nouvelle liste contenant uniquement les cartes piques pour que le
				 *joueur virtuel puisse jouer aléatoirement à partir de cette liste. */
				case 2 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.PIQUE)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est à l'indice 3 du tableau, ça signifie que 
				 *les cartes les plus nombreuses sont des trèfles.
				 *On créé une nouvelle liste contenant uniquement les cartes trèfles pour que le
				 *joueur virtuel puisse jouer aléatoirement à partir de cette liste. */
				case 3 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.TREFLE)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
			}
			
			Collections.shuffle(listeCarteCouleur);
			
			if(listeCarteCouleur.isEmpty()) {
				//Si le joueur ne peut jouer qu'un joker...
				if((cartesJouable.get(0) instanceof CarteJoker)) {
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
				res += plateau.getJoueurActuel().jouerCarte(cartesJouable.get(0), plateau);
			}
			else {
				//Si la première carte est un 8...
				if((listeCarteCouleur.get(0) instanceof Carte8)) {
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
				
				res += plateau.getJoueurActuel().jouerCarte(listeCarteCouleur.get(0), plateau);
			}
		}
		return res;
	}

}


