package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;
import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Carte8</b>
 * <p>
 * La classe Carte8 représente l'ensemble des cartes 10 présentes au cours du jeu.
 * Une carte 8 est une carte spéciale, elle est caractérisée par :
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
public class Carte8  extends Carte implements Serializable{

	/**
	 * Identifiant de sérialisation
	 */
	private static final long serialVersionUID = 5925479905340997483L;
	
	/**
	 * Constructeur par défaut d'une carte 8.
	 */
	public Carte8(){
		super();
	}
	/**
	 * Constructeur d'une carte 8 en saisissant sa couleur.
	 * Son numéro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte 8
	 */	
	public Carte8(String color){
		super(color);
		this.numero = Constante.NUMERO_CARTE_8;
		this.points = Constante.POINT_CARTE_8;
	}

	/**
	 * Redéfinition de l'effet d'une carte pour la carte 8.
	 * Le joueur actuel doit choisir une nouvelle couleur parmis les quatre possibles.
	 * Cet effet est géré dans l'affichage dans la méthode jouerTour() avec la méthode choisirCouleur().
	 * @see AffichageConsole#jouerTour
	 * @see AffichageConsole#choisirCouleur
	 * @see AffichageGraphique#jouerTour
	 * @see AffichageGraphique#choisirCouleur
	 */
	public String effet(Plateau plateau) {
		return "";
	}
}
