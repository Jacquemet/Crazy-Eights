package huitAmericain.tasDeCartes;

import huitAmericain.Constante;
import huitAmericain.cartes.Carte;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * <b>TasDeCartes est la classe repr�sentant l'ensemble des diff�rents tas de cartes de la partie.</b>
 * <p>
 * Un tas de cartes est repr�sent� par le nombre de cartes qui le constituent en fonction de la nature du tas.
 * </p>
 * <p>
 * 	Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * 	Tous les attributs qu'elle contient sont �galement s�rializables.
 * </p>
 * @see Defausse
 * @see Main
 * @see Talon
 * @author Thibault Jacquemet & Antoine Bl�chet
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
	 * Constructeur par d�faut d'un tas de cartes.
	 */
	public TasDeCartes() {
		this.jeu = new LinkedList<Carte>();
	}
	
	/**
	 * M�thode qui donne la liste de cartes d'un tas.
	 * @return le jeu de cartes d'un tas
	 */
	public List<Carte> getJeu() {
		return jeu;
	}
	/**
	 * Met � jour la liste de cartes d'un tas
	 * @param jeu
	 * La nouvelle liste de cartes
	 */
	public void setJeu(List<Carte> jeu) {
		this.jeu = jeu;
	}
	/**
	 * Retourne une carte en donnant en param�tres sa position dans la liste.
	 * @param position
	 * @return la carte se trouvant � la position choisie
	 */
	public Carte getCarte(int position) {
		return jeu.get(position);
	}
	/**
	 * Met � jour une carte en donnant sa position et la carte que l'on veut.
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
	 * Ajouter la premi�re carte du tas.
	 * @param carte
	 * La carte que l'on veut ajouter � la position initiale
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
			//Si la carte de la liste correspond � la carte saisie en param�tres on la supprime
			if(c.equals(carte)){
				jeu.remove(positionCarte);
				return;
			}
			//Incr�mente l'indice de position
			positionCarte++;
		}
	}
	
	/**
	 * Supprime la derni�re carte du tas.
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
	 * M�thode qui retourne la couleur la plus pr�sente dans une liste de cartes.
	 * @param liste
	 * La liste dans laquelle on veut chercher la couleur
	 * @return la couleur la plus pr�sente au sein de la liste
	 */
	public static int getCouleurMax(List<Carte> liste) {
		//Cr�ation d'un tableau qui contiendra le nombre de cartes au sein de la liste pour chaque couleur
		int [] nbCouleur = new int [4];
		int couleurMax = 0;
		//On parcourt la liste de cartes pour classer les cartes par couleur dans un tableau
		for (Carte carte : liste){
			//Si c'est un carreau on l'ajoute � l'indice 0
			if(carte.getCouleur().equals(Constante.CARREAU)){
				nbCouleur[0]++;	
			}
			//Si c'est un coeur on l'ajoute � l'indice 1
			else if(carte.getCouleur().equals(Constante.COEUR)){
				nbCouleur[1]++;
			}
			//Si c'est un pique on l'ajoute � l'indice 2
			else if(carte.getCouleur().equals(Constante.PIQUE)){
				nbCouleur[2]++;
			}
			//Si c'est un tr�fle on l'ajoute � l'indice 3
			else if(carte.getCouleur().equals(Constante.TREFLE)){
				nbCouleur[3]++;
			}
		}	
		//Le tableau cr�� est parcouru pour trouver l'indice qui contient la plus de cartes.
		//Chaque indice correspond � une couleur
		for(int i = 1; i < nbCouleur.length; i++){
			//Si le nombre de cartes de l'indice en cours est sup�rieur au nombre de cartes de l'indice (couleur)
			//qui contient le plus de cartes, alors la couleur la plus pr�sente devient l'indice en cours. 
			if(nbCouleur[i] > nbCouleur[couleurMax]){
				couleurMax = i;
			}
		}
		return couleurMax;
	}
	
	/**
	 * M�thode qui permet d'ajouter un ensemble de carte dans le Tas de carte de cette classe.
	 * @param listeCarte Le tas de carte � ajouter.
	 */
	public  void ajouterTas (List<Carte> listeCarte) {
		this.getJeu().addAll(listeCarte);
	}	
}
