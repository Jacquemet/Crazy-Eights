package huitAmericain.joueurs.strategie;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.CarteNormale;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <b>Classe Facile</b>
 * <p>
 * La classe Facile permet de d�finir la strat�gie facile des joueurs virtuels.
 * Si un joueur virtuel se voit attribuer cette strat�gie, il jouera selon la fa�on d�finie dans jouerStrat�gie();
 * Le joueur facile joue al�atoirement.
 * </p>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * De plus elle impl�mente l'interface strat�gie.
 * </p>
 * @see Facile#jouerStrategie(List, Plateau)
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class Facile implements Strategie, Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4166730129349678603L;

	/**
	 * Red�finition de la m�thode jouerStrat�gie() pour la strat�gie facile.
	 * <p>
	 * Avec cette strat�gie, le joueur virtuel joue en priorit� une carte normale au hasard.
	 * Si il n'a pas de cartes normales dans sa liste de cartes jouables, il joue une carte sp�ciale au hasard.
	 * Si il ne peut pas jouer de cartes, alors il pioche.
	 * Il n'annonce ni carte, ni non carte.
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
			res = plateau.getJoueurActuel().piocher(1, plateau.getTalon());
		}
		//Sinon on le fait jouer une strat�gie simple.
		else {
			//Cr�ation d'une liste interm�diaire qui contiendra seulement les cartes normales qui sont jouable.
			List<Carte> listeCarteNormale = new LinkedList<Carte>();
			for(Carte carte : cartesJouable) {
				//Si c'est une carte normale on l'ajoute.
				if(carte instanceof CarteNormale) {
					listeCarteNormale.add(carte);
				}
			}
			
			//Si le joueur virtuel ne peut pas jouer de carte normale alors on joue une carte sp�ciale au hasard.
			if(listeCarteNormale.isEmpty()) {
				Collections.shuffle(cartesJouable);
				//Si il joue un 8 ou un joker on choisi une couleur au hasard.
				if((cartesJouable.get(0) instanceof CarteJoker) || (cartesJouable.get(0) instanceof Carte8)) {
					int aleatoire = ((int)(Math.random()* 3)) + 1;
					switch (aleatoire) {
					case 1:
						plateau.setCouleurActuelle(Constante.CARREAU);
						break;
					case 2:
						plateau.setCouleurActuelle(Constante.COEUR);
						break;
					case 3:
						plateau.setCouleurActuelle(Constante.PIQUE);
						break;
					case 4:
						plateau.setCouleurActuelle(Constante.TREFLE);
						break;
					}
				}
				res = plateau.getJoueurActuel().jouerCarte(cartesJouable.get(0), plateau);
			}
			//Si il peut jouer une carte normal on en choisi une au hasard parmi la liste.
			else {
				Collections.shuffle(listeCarteNormale);
				res = plateau.getJoueurActuel().jouerCarte(listeCarteNormale.get(0), plateau);
			}
		}
		return res;
	}

}
