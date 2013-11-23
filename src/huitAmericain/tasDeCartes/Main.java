package huitAmericain.tasDeCartes;

import java.io.Serializable;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Main</b>
 * <p>
 * Main repr�sente la main d'un joueur, c'est � dire l'ensemble de ses cartes.
 * Elle h�rite de tas de cartes.
 * </p>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * @see TasDeCartes
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class Main extends TasDeCartes implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5159954696174964190L;
	/**
	 * Constructeur par d�faut d'une main.
	 */
	public Main() {
		super();
	}
	/**
	 * M�thode d'affichage de la main d'un joueur dans la console.
	 * @return une cha�ne de caract�res repr�sentant la main du joueur
	 */
    public String toString()
    {
        String res = new String();
        for(@SuppressWarnings("unused") Carte c : this.jeu){
            res += String.format("%9s","--------")+"  ";
        }
        res+='\n';
        
        for(@SuppressWarnings("unused") Carte c : this.jeu){
            res +="|"+String.format("%9s","|")+" " ;
        }
        res+='\n';
        for(Carte c : this.jeu){
            res += '|'+String.format("%6s",c.getNumero()) + String.format("%3s","|")+" ";
        }
        res+='\n';
        for(@SuppressWarnings("unused") Carte c : this.jeu){
            res +="|"+String.format("%9s","|")+" ";
        }
        res+='\n';
        for(Carte c : this.jeu){
            res +="|"+String.format("%8s",c.getCouleur())+"|"+ " " ;
        }
        res+='\n';
        for(@SuppressWarnings("unused") Carte c : this.jeu){
            res +=String.format("%9s","--------")+"  ";
        }
        res+='\n';
        
        return res;
    }
}
