package huitAmericain.joueurs.strategie;

import java.util.List;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * 
 * <b>Interface Strategie</b>
 * <p>
 * L'interface stratégie sera implémentée dans les classes définissant 
 * chaque stratégie possible pour les joueurs virtuels.
 * </p>
 * @see Facile
 * @see Moyen
 * @see Difficile
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 * 
 */
public interface Strategie {

	/**
	 * Prototype de la méthode qui sera implémentée pour chaque stratégie.
	 * Elle permet au joueur virtuel de jouer la stratégie définie par la suite.
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau);
}
