package huitAmericain.joueurs;

/**
 * <b>Classe JoueurReel</b>
 * <p>
 * La classe JoueurReel repr�sente l'ensemble des joueurs r�els de la partie.
 * Elle h�rite de la classe joueur
 * </p>
 * <p>
 * Un joueur est caract�ris� par :
 * <ul>
 * <li>Son nom</li>
 * <li>Sa main (son nombre de cartes)</li>
 * <li>Sa position sur le plateau</li>
 * <li>Son nombre de points</li>
 * </ul>
 * </p>
 * <p>
 * 	Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * </p>
 * @see Joueur
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 * 
 */
public class JoueurReel extends Joueur{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -7282994904288145525L;

	/**
	 * Constructeur d'un joueur en saisissant son nom.
	 * @param nom
	 */
	public JoueurReel (String nom){
		super(nom);
	}

}
