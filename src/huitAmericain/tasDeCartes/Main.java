package huitAmericain.tasDeCartes;

import java.io.Serializable;
import huitAmericain.cartes.Carte;

/**
 * <b>Classe Main</b>
 * <p>
 * Main représente la main d'un joueur, c'est à dire l'ensemble de ses cartes.
 * Elle hérite de tas de cartes.
 * </p>
 * <p>
 * Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont également sérializables.
 * @see TasDeCartes
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class Main extends TasDeCartes implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5159954696174964190L;
	/**
	 * Constructeur par défaut d'une main.
	 */
	public Main() {
		super();
	}
	/**
	 * Méthode d'affichage de la main d'un joueur dans la console.
	 * @return une chaîne de caractères représentant la main du joueur
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
