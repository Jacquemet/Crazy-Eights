package huitAmericain.tasDeCartes;

import java.io.Serializable;
import huitAmericain.Plateau;


/**
 * <b>Classe Defausse</b>
 * <p>
 * Defausse représente la défausse de la partie, c'est à dire le tas sur lequel les joueurs posent leurs cartes quand ils jouent.
 * La classe hérite donc de TasDeCartes.
 * </p>
 * <p>
 * Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont également sérializables.
 * @see TasDeCartes
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class Defausse extends TasDeCartes implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7501958764336780318L;
	/**
	 * Instance unique de la classe Defausse. La première carte retournée est la bergère.
	 */
	private static Defausse instanceBergere;
	/**
	 * Nombre d'As joué dans la défausse.
	 */
	private int nombreAsJoue;
	/**
	 * Constructeur par défaut de la défausse.
	 */
	private Defausse() {
		super();
		this.setNombreAsJoue(1);
	}
	
	/**
	 * Méthode static qui permet la création d'une défausse et qui garantie son unicité.
	 * @return La defausse unique.
	 */
	public static Defausse getIstance() {
		if(instanceBergere == null) {
			//Synchronisation permet d'éviter d'éventuelles erreurs en cas de threads.
            synchronized(Plateau.class) {
                if (instanceBergere == null) {
        			instanceBergere = new Defausse();	
                }
            }
		}
		return instanceBergere;
	}
	
	/**
	 * Méthode qui permet l'affichage d'une défausse.
	 */
    public String toString()
    {
        String res = new String();
        res = res +String.format("%9s","--------")+'\n' ;
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +'|'+String.format("%6s",this.jeu.get(0).getNumero()) + String.format("%3s","|")+"\n";
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +"|"+String.format("%8s",this.jeu.get(0).getCouleur())+"|\n" ;
        res = res +String.format("%9s","--------")+'\n' ;
        return res;
    }

    /**
     * Récupère de nombre d'As joué.
     * @return Entier : nombre d'As.
     */
	public int getNombreAsJoue() {
		return nombreAsJoue;
	}

	/**
	 * Met à jour la valeur du nombre d'As joué
	 * @param nombreAsJoue Le nouveau nombre d'As.
	 */
	public void setNombreAsJoue(int nombreAsJoue) {
		this.nombreAsJoue = nombreAsJoue;
	}
	
	/**
	 * Méthode qui permet de supprimer toute les carte de la defausse et de les ajouter au tas de carte placé en paramètre.
	 * @param jeu Le jeu de carte dans lequel on ajoute les cartes.
	 */
	public void vider(TasDeCartes jeu) {
		jeu.ajouterCarteDebut(this.getCarte(1));
		this.supprimerDerniereCarte();
	}
}
