package huitAmericain.tasDeCartes;

import java.io.Serializable;
import huitAmericain.Plateau;


/**
 * <b>Classe Defausse</b>
 * <p>
 * Defausse repr�sente la d�fausse de la partie, c'est � dire le tas sur lequel les joueurs posent leurs cartes quand ils jouent.
 * La classe h�rite donc de TasDeCartes.
 * </p>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * @see TasDeCartes
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class Defausse extends TasDeCartes implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7501958764336780318L;
	/**
	 * Instance unique de la classe Defausse. La premi�re carte retourn�e est la berg�re.
	 */
	private static Defausse instanceBergere;
	/**
	 * Nombre d'As jou� dans la d�fausse.
	 */
	private int nombreAsJoue;
	/**
	 * Constructeur par d�faut de la d�fausse.
	 */
	private Defausse() {
		super();
		this.setNombreAsJoue(1);
	}
	
	/**
	 * M�thode static qui permet la cr�ation d'une d�fausse et qui garantie son unicit�.
	 * @return La defausse unique.
	 */
	public static Defausse getIstance() {
		if(instanceBergere == null) {
			//Synchronisation permet d'�viter d'�ventuelles erreurs en cas de threads.
            synchronized(Plateau.class) {
                if (instanceBergere == null) {
        			instanceBergere = new Defausse();	
                }
            }
		}
		return instanceBergere;
	}
	
	/**
	 * M�thode qui permet l'affichage d'une d�fausse.
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
     * R�cup�re de nombre d'As jou�.
     * @return Entier : nombre d'As.
     */
	public int getNombreAsJoue() {
		return nombreAsJoue;
	}

	/**
	 * Met � jour la valeur du nombre d'As jou�
	 * @param nombreAsJoue Le nouveau nombre d'As.
	 */
	public void setNombreAsJoue(int nombreAsJoue) {
		this.nombreAsJoue = nombreAsJoue;
	}
	
	/**
	 * M�thode qui permet de supprimer toute les carte de la defausse et de les ajouter au tas de carte plac� en param�tre.
	 * @param jeu Le jeu de carte dans lequel on ajoute les cartes.
	 */
	public void vider(TasDeCartes jeu) {
		jeu.ajouterCarteDebut(this.getCarte(1));
		this.supprimerDerniereCarte();
	}
}
