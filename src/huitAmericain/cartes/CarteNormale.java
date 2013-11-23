package huitAmericain.cartes;

import java.io.Serializable;
import huitAmericain.Plateau;

/**
 * <b>Classe CarteNormale</b>
 * <p>
 * La classe CarteNormale repr�sente l'ensemble des cartes normales pr�sentes au cours du jeu.
 * Une carte normale est caract�ris�e par :
 * <ul>
 * <li>Sa couleur</li>
 * <li>Sa hauteur</li>
 * <li>Son nombre de points</li>
 * </ul>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * Elle h�rite de la classe Carte.
 * </p>
 * @see Carte
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class CarteNormale extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -2635316028414160538L;

	/**
	 * Constructeur par d�faut d'une carte normale.
	 */
	public CarteNormale () {
		super();
	}

	/**
	 * Constructeur d'une carte normale en saisissant sa couleur, son num�ro, son nombre de points.
	 * @param color
	 * La couleur de la carte
	 * @param num
	 * Le num�ro de la carte
	 * @param pts
	 * Le nombre de points de la carte
	 */
	public CarteNormale(String color, String num, int pts) {
		super(color, num, pts);
	}

	/**
	 * Red�finition de la m�thode d�finissant l'effet de la carte.
	 * Une carte sp�ciale n'a pas d'effet, on applique donc un effet vide.
	 */
	public String effet(Plateau plateau) {
		return "";
	}
}
