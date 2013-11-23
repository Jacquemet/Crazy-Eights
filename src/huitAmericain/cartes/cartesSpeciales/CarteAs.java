package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;
import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.ReglesJeu;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe CarteAs</b>
 * <p>
 * La classe CarteAs représente l'ensemble des cartes 10 présentes au cours du jeu.
 * Une carte As est une carte spéciale, elle est caractérisée par :
 * <ul>
 * <li>Sa couleur</li>
 * <li>Sa hauteur</li>
 * <li>Son nombre de points</li>
 * </ul>
 * <p>
 * Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont également sérializables.
 * Elle hérite de la classe Carte.
 * </p>
 * @see Carte
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class CarteAs extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3416712762060768877L;
	/**
	 * Constructeur par défaut d'une carte As.
	 */
	public CarteAs(){
		super();
	}
	/**
	 * Constructeur d'une carte As en saisissant sa couleur.
	 * Son numéro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte As
	 */		
	public CarteAs(String color){
		super(color);
		this.numero = Constante.NUMERO_CARTE_AS;
		this.points = Constante.POINT_CARTE_AS;
	}

	/**
	 * Redéfinition de l'effet d'une carte pour la carte As.
	 */
	public String effet(Plateau plateau) {
		String res = "";
		plateau.setJoueurActuel(plateau.getJoueurSuivant());
		int positionAs = plateau.getJoueurActuel().possedeAs();
		//Si le joueur possède un as et que la manche n'est pas terminée
		// (Joueur peut poser un as en dernière Carte dans  ce cas le joueur suivant pioche et la manche prend fin)
		if(positionAs !=-1 && !ReglesJeu.finManche(plateau)) {
			plateau.getDefausse().setNombreAsJoue(plateau.getDefausse().getNombreAsJoue()+1);
			res = plateau.getJoueurActuel().jouerCarte(plateau.getJoueurActuel().getMain().getCarte(positionAs), plateau);
		}
		//Sinon on fait piocher le joueur qui n'a pas d'As
		else {
			res = plateau.getJoueurActuel().piocher(2*(plateau.getDefausse().getNombreAsJoue()), plateau.getTalon());
			//On remet le nombre d'As joué à la valeur initiale
			plateau.getDefausse().setNombreAsJoue(1);
		}
		return res;
	}
}
