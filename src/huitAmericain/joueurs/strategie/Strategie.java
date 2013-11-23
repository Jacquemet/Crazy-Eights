package huitAmericain.joueurs.strategie;

import java.util.List;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * 
 * <b>Interface Strategie</b>
 * <p>
 * L'interface strat�gie sera impl�ment�e dans les classes d�finissant 
 * chaque strat�gie possible pour les joueurs virtuels.
 * </p>
 * @see Facile
 * @see Moyen
 * @see Difficile
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 * 
 */
public interface Strategie {

	/**
	 * Prototype de la m�thode qui sera impl�ment�e pour chaque strat�gie.
	 * Elle permet au joueur virtuel de jouer la strat�gie d�finie par la suite.
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau);
}
