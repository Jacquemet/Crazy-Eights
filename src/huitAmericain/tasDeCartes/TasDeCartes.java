package huitAmericain.tasDeCartes;

import huitAmericain.Constante;
import huitAmericain.cartes.Carte;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * <b>TasDeCartes est la classe représentant l'ensemble des différents tas de cartes de la partie.</b>
 * <p>
 * Un tas de cartes est représenté par le nombre de cartes qui le constituent en fonction de la nature du tas.
 * </p>
 * <p>
 * 	Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * 	Tous les attributs qu'elle contient sont également sérializables.
 * </p>
 * @see Defausse
 * @see Main
 * @see Talon
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public abstract class TasDeCartes implements Serializable{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4647476789520135598L;
	/**
	 * Liste de cartes, correspond aux cartes qu'on peut trouver dans un tas
	 * et sa taille correspond au nombre de cartes dans le tas.
	 */
	protected List<Carte> jeu;
	
	/**
	 * Constructeur par défaut d'un tas de cartes.
	 */
	public TasDeCartes() {
		this.jeu = new LinkedList<Carte>();
	}
	
	/**
	 * Méthode qui donne la liste de cartes d'un tas.
	 * @return le jeu de cartes d'un tas
	 */
	public List<Carte> getJeu() {
		return jeu;
	}
	/**
	 * Met à jour la liste de cartes d'un tas
	 * @param jeu
	 * La nouvelle liste de cartes
	 */
	public void setJeu(List<Carte> jeu) {
		this.jeu = jeu;
	}
	/**
	 * Retourne une carte en donnant en paramètres sa position dans la liste.
	 * @param position
	 * @return la carte se trouvant à la position choisie
	 */
	public Carte getCarte(int position) {
		return jeu.get(position);
	}
	/**
	 * Met à jour une carte en donnant sa position et la carte que l'on veut.
	 * @param position
	 * La nouvelle position de la carte
	 * @param carte
	 * La nouvelle carte
	 */
	public void setCarte(int position, Carte carte) {
		jeu.set(position, carte);
	}
	/**
	 * Donne la taille de la liste de cartes du tas.
	 * @return la taile de la liste
	 */
	public int getSize() {
		return jeu.size();
	}
	
	/**
	 * Ajoute une carte dans la liste.
	 * @param carte
	 * La carte que l'on veut ajouter
	 */
	public void ajouterCarte(Carte carte){
		jeu.add(carte);
	}
	
	/**
	 * Ajouter la première carte du tas.
	 * @param carte
	 * La carte que l'on veut ajouter à la position initiale
	 */
	public void ajouterCarteDebut(Carte carte) {
		jeu.add(0, carte);
	}
	
	/**
	 * Supprime une carte du tas.
	 * @param carte
	 * La carte que l'on veut supprimer
	 */
	public void supprimerCarte(Carte carte){
		//Initialisation de l'indice de position de la carte
		int positionCarte = 0;
		//Parcours de la liste de cartes
		for(Carte c : jeu){
			//Si la carte de la liste correspond à la carte saisie en paramètres on la supprime
			if(c.equals(carte)){
				jeu.remove(positionCarte);
				return;
			}
			//Incrémente l'indice de position
			positionCarte++;
		}
	}
	
	/**
	 * Supprime la dernière carte du tas.
	 */
	public void supprimerDerniereCarte() {
		jeu.remove(jeu.size()-1);
	}
	
	/**
	 * Vide la liste de cartes.
	 */
	public void vider() {
		jeu.clear();
	}
	
	/**
	 * Méthode qui retourne la couleur la plus présente dans une liste de cartes.
	 * @param liste
	 * La liste dans laquelle on veut chercher la couleur
	 * @return la couleur la plus présente au sein de la liste
	 */
	public static int getCouleurMax(List<Carte> liste) {
		//Création d'un tableau qui contiendra le nombre de cartes au sein de la liste pour chaque couleur
		int [] nbCouleur = new int [4];
		int couleurMax = 0;
		//On parcourt la liste de cartes pour classer les cartes par couleur dans un tableau
		for (Carte carte : liste){
			//Si c'est un carreau on l'ajoute à l'indice 0
			if(carte.getCouleur().equals(Constante.CARREAU)){
				nbCouleur[0]++;	
			}
			//Si c'est un coeur on l'ajoute à l'indice 1
			else if(carte.getCouleur().equals(Constante.COEUR)){
				nbCouleur[1]++;
			}
			//Si c'est un pique on l'ajoute à l'indice 2
			else if(carte.getCouleur().equals(Constante.PIQUE)){
				nbCouleur[2]++;
			}
			//Si c'est un trèfle on l'ajoute à l'indice 3
			else if(carte.getCouleur().equals(Constante.TREFLE)){
				nbCouleur[3]++;
			}
		}	
		//Le tableau créé est parcouru pour trouver l'indice qui contient la plus de cartes.
		//Chaque indice correspond à une couleur
		for(int i = 1; i < nbCouleur.length; i++){
			//Si le nombre de cartes de l'indice en cours est supérieur au nombre de cartes de l'indice (couleur)
			//qui contient le plus de cartes, alors la couleur la plus présente devient l'indice en cours. 
			if(nbCouleur[i] > nbCouleur[couleurMax]){
				couleurMax = i;
			}
		}
		return couleurMax;
	}
	
	/**
	 * Méthode qui permet d'ajouter un ensemble de carte dans le Tas de carte de cette classe.
	 * @param listeCarte Le tas de carte à ajouter.
	 */
	public  void ajouterTas (List<Carte> listeCarte) {
		this.getJeu().addAll(listeCarte);
	}	
}
