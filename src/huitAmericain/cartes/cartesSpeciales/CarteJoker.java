package huitAmericain.cartes.cartesSpeciales;

import java.io.Serializable;
import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe CarteJoker</b>
 * <p>
 * La classe CarteJoker repr�sente l'ensemble des cartes 10 pr�sentes au cours du jeu.
 * Une carte Joker est une carte sp�ciale, elle est caract�ris�e par :
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
public class CarteJoker extends Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8557980650683369376L;
	/**
	 * Constructeur d'une carte Joker en saisissant sa couleur.
	 * Son num�ro et son nombre de points son des constantes.
	 * @see Constante
	 * @param color
	 * La couleur de la carte Joker
	 */		
	public CarteJoker(){
		this.couleur = "";
		this.numero = Constante.NUMERO_CARTE_JOKER;
		this.points = Constante.POINT_CARTE_JOKER;
	}

	/**
	 * Red�finition de l'effet d'une carte pour la carte Joker.
	 * Le joueur suivant doit piocher 5 cartes et passer son tour.
	 * Le joueur actuel doit choisir une nouvelle couleur. Cette partie de l'effet
	 * est g�r�e dans l'affichage.
	 * @see AffichageConsole#jouerTour
	 * @see AffichageConsole#choisirCouleur
	 * @see AffichageGraphique#jouerTour
	 * @see AffichageGraphique#choisirCouleur
	 */
	public String effet(Plateau plateau) {
		String res = "";
		res = plateau.getJoueurSuivant().piocher(5, plateau.getTalon());
		plateau.setJoueurActuel(plateau.getJoueurSuivant());
		return res;
	}
	
}
