package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Carte10</b>
 * <p>
 * La classe Carte10 repr�sente l'ensemble des cartes 10 pr�sentes au cours du jeu.
 * Une carte 10 est une carte sp�ciale, elle est caract�ris�e par :
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
public class Carte10 extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2806879385665193761L;
	/**
	 * Constructeur par d�faut d'une carte 10.
	 */
	public Carte10(){
		super();
	}
	/**
	 * Constructeur d'une carte 10 en saisissant sa couleur.
	 * Son num�ro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte 10
	 */
	public Carte10(String color){
		super(color);
		this.numero = Constante.NUMERO_CARTE_10;
		this.points = Constante.POINT_CARTE_10;
	}

	/**
	 * Red�finition de l'effet d'une carte pour la carte 10.
	 * La carte 10 provoque une inversion du sens de rotation de la partie.
	 */
	public String effet(Plateau plateau) {
		plateau.setSensRotation(-plateau.getSensRotation());
		return "";
	}
}
