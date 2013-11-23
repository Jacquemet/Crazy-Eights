package huitAmericain.cartes;

import java.io.Serializable;

import huitAmericain.Plateau;

/**
 * <b>Classe Carte</b>
 * <p>
 * La classe Carte repr�sente l'ensemble des cartes pr�sentes au cours du jeu.
 * Il y a deux cat�gories de cartes, les cartes normales et les cartes sp�ciales.
 * Une carte est caract�ris�e par :
 * <ul>
 * <li>Sa couleur</li>
 * <li>Sa hauteur</li>
 * <li>Son nombre de points</li>
 * </ul>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * </p>
 * @see CarteNormale
 * @see Carte10
 * @see Carte2
 * @see Carte7
 * @see Carte8
 * @see CarteAs
 * @see CarteJoker
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public abstract class Carte implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3985587823412595624L;
	/**
	 * Chaine de caract�res d�finissant la couleur d'une carte.
	 * Les quatres couleurs suivantes sont possibles :
	 * <ul>
	 * 	<li>Carr�
	 * 	<li>Coeur
	 * 	<li>Pique
	 * 	<li>Tr�fle
	 * </ul>
	 */
	protected String couleur;
	/**
	 * Chaine de caract�re d�finissant le numero de chaque carte.
	 */
	protected String numero;
	/**
	 * Entier d�finissant le nombre de points de chaque carte.
	 */
	protected int points;

	/**
	 * Constructeur par d�faut d'une carte.
	 * On instencie les attributs couleur et numero � "" et le nombre de points � 0.
	 */
	public Carte () {
		this.couleur = "";
		this.numero = "";
		this.points = 0;
	}
	
	/**
	 *  Constructeur d'une carte en saisissant sa couleur, sa hauteur, son nombre de points.
	 * @param color
	 * La couleur de la carte
	 * @param num
	 * La hauteur de la carte
	 * @param pts
	 * Le nombre de points de la carte
	 */
	public Carte(String color, String num, int pts) {
		this.couleur = color;
		this.numero = num;
		this.points = pts;
	}
	
	/**
	 * Constructeur d'une carte en saisissant uniquement sa couleur.
	 * @param color
	 * La couleur de la carte
	 */
	public Carte(String color) {
		this.couleur = color;
	}
	
	/**
	 * R�cup�re la couleur de la carte.
	 * @return la couleur couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Actualise la couleur de la carte.
	 * @param couleur
	 * La nouvelle couleur de la carte
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * R�cup�re le num�ro de la carte.
	 * @return la valeur du num�ro de la carte
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Actualise le num�ro de la carte.
	 * @param numero
	 * Le nouveau num�ro de la carte
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * R�cup�re le nombre de points de la carte.
	 * @return la nombre de points de la carte
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Actualise le nombre de points de la carte.
	 * @param points
	 * Le nouveau nombre de points de la carte
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * M�thode qui permet de v�rifier si un objet est une carte.
	 * Si c'est une carte, on v�rifie si c'est la carte instanci�e.
	 * Sinon on retourne faux.
	 */
	public boolean equals(Object o){
		boolean res;
		//Si c'est de l'instance carte
		if ( o instanceof Carte){
			//On cast l'objet en carte 
			Carte c =(Carte)o;
			//Si les 2 cartes sont similaires
			if ((c.couleur).equals(this.couleur) && (c.numero).equals(this.numero))
				res = true;
			else
				res = false;
		}
		else
			res = false;

		return res;
	}
	
	/**
	 * Surcharge de la m�thode toString pour afficher une carte.
	 */
    public String toString(){
        String res = new String();
        res = res +String.format("%9s","--------")+'\n';
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +'|'+String.format("%6s",this.numero) + String.format("%3s","|")+"\n";
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +"|"+String.format("%8s",this.couleur)+"|\n" ;
        res = res +String.format("%9s","--------")+'\n' ;
        return res;
    }
	
    /**
     * M�thode abstraite pour d�finir l'effet d'une carte.
     * Elle sera impl�ment�e dans les classes filles.
     * @param plateau
     * L'unique instance du plateau de la partie
     */
    public abstract String effet(Plateau plateau);  	
    
}
