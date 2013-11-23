package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;
import java.util.Collections;
import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Carte7</b>
 * <p>
 * La classe Carte7 représente l'ensemble des cartes 10 présentes au cours du jeu.
 * Une carte 7 est une carte spéciale, elle est caractérisée par :
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
public class Carte7 extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1992640075035004967L;
	/**
	 * Constructeur par défaut d'une carte 7.
	 */
	public Carte7(){
		super();
	}
	/**
	 * Constructeur d'une carte 7 en saisissant sa couleur.
	 * Son numéro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte 7
	 */	
	public Carte7(String color){
		super(color);
		this.numero = Constante.NUMERO_CARTE_7;
		this.points = Constante.POINT_CARTE_7;
	}

	/**
	 * Redéfinition de l'effet d'une carte pour la carte 7.
	 * Le jeu du joueur actuel est mélangé. Le joueur suivant y pioche une carte au hasard et passe sont tour.
	 */
	public String effet(Plateau plateau) {
		String res = "";
		//On mélange le jeu du joueur qui vient de poser un 7.
		Collections.shuffle(plateau.getJoueurActuel().getMain().getJeu());
		//Le joueur suivant pioche la carte dans le jeu du joueur qui vient de jouer.
		res = plateau.getJoueurSuivant().piocher(1, plateau.getJoueurActuel().getMain());
		//Le joueur suivant passe son tour
		plateau.setJoueurActuel(plateau.getJoueurSuivant());
		return res;
	}
}
