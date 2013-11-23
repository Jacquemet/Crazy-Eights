package huitAmericain.cartes;

import java.io.Serializable;
import huitAmericain.Plateau;

/**
 * <b>Classe CarteNormale</b>
 * <p>
 * La classe CarteNormale représente l'ensemble des cartes normales présentes au cours du jeu.
 * Une carte normale est caractérisée par :
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
public class CarteNormale extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -2635316028414160538L;

	/**
	 * Constructeur par défaut d'une carte normale.
	 */
	public CarteNormale () {
		super();
	}

	/**
	 * Constructeur d'une carte normale en saisissant sa couleur, son numéro, son nombre de points.
	 * @param color
	 * La couleur de la carte
	 * @param num
	 * Le numéro de la carte
	 * @param pts
	 * Le nombre de points de la carte
	 */
	public CarteNormale(String color, String num, int pts) {
		super(color, num, pts);
	}

	/**
	 * Redéfinition de la méthode définissant l'effet de la carte.
	 * Une carte spéciale n'a pas d'effet, on applique donc un effet vide.
	 */
	public String effet(Plateau plateau) {
		return "";
	}
}
