package huitAmericain.tasDeCartes;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


import huitAmericain.Constante;
import huitAmericain.cartes.*;
import huitAmericain.cartes.cartesSpeciales.*;

/**
 * <b>Classe Talon</b>
 * <p>
 * Talon représente le talon de la partie, c'est à dire le tas de cartes dans lequel les joueurs piochent.
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
public class Talon extends TasDeCartes implements Serializable{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 818974890227507414L;
	/**
	 * Instance de la classe Talon.
	 */
	private static Talon instanceTalon;
	
	/**
	 * Constructeur d'un talon en sélectionnant son nombre de cartes.
	 * @param nbCartes
	 * Le nombre de cartes du talon
	 */
	private Talon(int nbCartes) {
		this.jeu = createJeu(nbCartes);
	}

	/**
	 * Récupère une instance du talon, si cette dernière n'existe pas on la crée.
	 * @param nbCartes
	 * Le nombre de cartes du talon
	 * @return l'instance du talon utilisée
	 */
	public static Talon getInstance(int nbCartes) {
		if(instanceTalon == null) {
			//Synchronisation permet d'éviter d'éventuelles erreurs en cas de threads.
			synchronized (Talon.class) {
				if(instanceTalon == null) {
					instanceTalon = new Talon(nbCartes);
				}
			}
		}
		return instanceTalon;
	}
	
	/**
	 * Méthode qui permet de créer un jeu de carte complet.
	 * Nous pouvons créer un jeu de carte de 54 cartes, 108 cartes ou plus suivant le nombre de carte choisi.
	 * @param nbCartes Le nombre de cartes (ici 54 ou 108) que le jeu va comporter. 
	 * @return Le jeu de cartes
	 */
	public static List<Carte> createJeu(int nbCartes){
		
		List<Carte> jeu = new LinkedList<Carte>();
		
		for(int i=0; i<(nbCartes/54); i++){
			
			for(Carte c : createCouleur(Constante.CARREAU))
				jeu.add(c);
			
			for(Carte c : createCouleur(Constante.COEUR))
				jeu.add(c);
			
			for(Carte c : createCouleur(Constante.PIQUE))
				jeu.add(c);
			
			for(Carte c : createCouleur(Constante.TREFLE))
				jeu.add(c);
			
			for(int j=0; j<2; j++) {
				Carte carteJoker = new CarteJoker();
				jeu.add(carteJoker);
			}
		}
		return jeu;
	}
	
	/**
	 * Méthode qui permet de créer toutes les cartes pour une couleur placé en paramètre
	 * @param couleur La couleur des cartes
	 * @return une liste de cartes
	 */
	private static List<Carte> createCouleur(String couleur) {
		
		List<Carte> jeuCouleur = new LinkedList<Carte>();
		
		Carte carteA = new CarteAs(couleur);
		jeuCouleur.add(carteA);
		
		Carte carte2 = new Carte2(couleur);
		jeuCouleur.add(carte2);
		
		Carte carte3 = new CarteNormale(couleur, Constante.NUMERO_CARTE_3, Constante.POINT_CARTE_3);
		jeuCouleur.add(carte3);
		
		Carte carte4 = new CarteNormale(couleur, Constante.NUMERO_CARTE_4, Constante.POINT_CARTE_4);
		jeuCouleur.add(carte4);
		
		Carte carte5 = new CarteNormale(couleur, Constante.NUMERO_CARTE_5, Constante.POINT_CARTE_5);
		jeuCouleur.add(carte5);
		
		Carte carte6 = new CarteNormale(couleur, Constante.NUMERO_CARTE_6, Constante.POINT_CARTE_6);
		jeuCouleur.add(carte6);
		
		Carte carte7 = new Carte7(couleur);
		jeuCouleur.add(carte7);
		
		Carte carte8 = new Carte8(couleur);
		jeuCouleur.add(carte8);
		
		Carte carte9 = new CarteNormale(couleur, Constante.NUMERO_CARTE_9, Constante.POINT_CARTE_9);
		jeuCouleur.add(carte9);
		
		Carte carte10 = new Carte10(couleur);
		jeuCouleur.add(carte10);
		
		Carte carteV = new CarteNormale(couleur, Constante.NUMERO_CARTE_VALET, Constante.POINT_CARTE_VALET);
		jeuCouleur.add(carteV);
		
		Carte carteD = new CarteNormale(couleur, Constante.NUMERO_CARTE_DAME, Constante.POINT_CARTE_DAME);
		jeuCouleur.add(carteD);
		
		Carte carteR = new CarteNormale(couleur, Constante.NUMERO_CARTE_ROI, Constante.POINT_CARTE_ROI);
		jeuCouleur.add(carteR);
		
		return jeuCouleur;
	}
	
	/**
	 * Méthode d'affichage du talon.
	 * @return une chaîne de caractère représentant le talon
	 */
    public String toString()
    {
        String res = new String();
        res = res +String.format("%9s","--------")+'\n' ;
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +'|'+String.format("%6s","Pioche") + String.format("%3s","|")+"\n";
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +"|"+String.format("%9s","|")+'\n' ;
        res = res +String.format("%9s","--------")+'\n' ;
        return res;
    }
	
}
