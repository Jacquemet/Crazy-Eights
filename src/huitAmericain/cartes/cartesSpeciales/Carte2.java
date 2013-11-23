package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;
import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Carte2</b>
 * <p>
 * La classe Carte2 représente l'ensemble des cartes 2 présentes au cours du jeu.
 * Une carte 2 est une carte spéciale, elle est caractérisée par :
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
public class Carte2 extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7000829601325378208L;
	/**
	 * Constructeur par défaut d'une carte 10.
	 */
	public Carte2(){
		super();
	}
	/**
	 * Constructeur d'une carte 2 en saisissant sa couleur.
	 * Son numéro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte 2
	 */	
	public Carte2(String color){
		super(color);
		this.numero = Constante.NUMERO_CARTE_2;
		this.points = Constante.POINT_CARTE_2;
	}

	/**
	 * Redéfinition de l'effet d'une carte pour la carte 2.
	 * Le joueur suivant doit piocher deux cartes dans le talon et passe.
	 */
	public String effet(Plateau plateau) {
		String res = "";
		res = plateau.getJoueurSuivant().piocher(2, plateau.getTalon());
		plateau.setJoueurActuel(plateau.getJoueurSuivant());
		return res;
	}
}
