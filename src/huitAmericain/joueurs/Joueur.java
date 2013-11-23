package huitAmericain.joueurs;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte7;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.tasDeCartes.Main;
import huitAmericain.tasDeCartes.Talon;
import huitAmericain.tasDeCartes.TasDeCartes;

import java.io.Serializable;

/**
 * <b>Classe Joueur</b>
 * <p>
 * La classe joueur représente les joueurs de la partie.
 * C'est une classe abstraite car il ne peut y avoir de joueurs qui ne sont ni réels ni virtuels.
 * </p>
 * <p>
 * Un joueur est caractérisé par :
 * <ul>
 * <li>Son nom</li>
 * <li>Sa main (son nombre de cartes)</li>
 * <li>Sa position sur le plateau</li>
 * <li>Son nombre de points</li>
 * </ul>
 * </p>
 * <p>
 * 	Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * 	Tous les attributs qu'elle contient sont également sérializables.
 * </p>
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public abstract class Joueur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1398438408818622008L;
	/**
	 * Nom du joueur.
	 */
	private String nom;
	/**
	 * Main du joueur comportant toutes les cartes qu'il possède.
	 * @see Carte
	 */
	private Main main;
	/**
	 * Position du joueur sur le plateau, 
	 * la position 0 correspond à la position du donneur.
	 */
	private int position;
	/**
	 * Nombre de points de l'utilisateur, ils sont comptés a chaque fin de manche.
	 * @see Plateau#compterNbPoints
	 */
	private int nombrePoints;
	/**
	 * Booléen qui indique si le joueur à annoncer carte ou non.
	 */
	private boolean annoncerCarte;
	
	/**
	 * Constructeur d'un joueur en sélectionnant son nom.
	 * @param nom
	 * Le nom du joueur
	 */
	public Joueur(String nom) {
		this.nom = nom;
		this.main = new Main();
		this.position = 0;
		this.nombrePoints = 0;
		this.setAnnoncerCarte(false);
	}
	
	/**
	 * Retourne la position qu'occupe le joueur sur le plateau.
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Met à jour la position du joueur.
	 * @param position
	 * La nouvelle position du joueur sur le plateau
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Permet de récupérer le nom du joueur.
	 * @return nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Met à jour le nom du joueur.
	 * @param nom
	 * Le nouveau nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne la main du joueur qui comprend toutes ses cartes.
	 * @return main du joueur
	 * @see Main
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * Met à jour la main d'un joueur.
	 * @param main
	 * La nouvelle main du joueur
	 * @see Main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Retourne le nombre de points du joueur pour la partie en cours.
	 * @return nombre de points
	 */
	public int getNombrePoints() {
		return nombrePoints;
	}

	/**
	 * Met à jour le nombre de points d'un joueur.
	 * @param nombrePoints 
	 * Le nouveau nombre de points
	 */
	public void setNombrePoints(int nombrePoints) {
		this.nombrePoints = nombrePoints;
	}
	
	/**
	 * Permet de savoir si le joueur à annoncer carte où non.
	 * @return la valeur du booléen annoncer carte
	 * @see annoncerCarte
	 */
	public boolean isAnnoncerCarte() {
		return annoncerCarte;
	}

	/**
	 * Met à jour "annoncerCarte" pour le joueur.
	 * @param annoncerCarte
	 * La nouvelle valeur du booléen
	 */
	public void setAnnoncerCarte(boolean annoncerCarte) {
		this.annoncerCarte = annoncerCarte;
	}

	/**
	 * Prend un certain nombre de cartes dans talon et ajoute ces dernières dans la main du joueur.
	 * @param nombreCarte
	 * Nombre de carte que le joueur doit piochier
	 * @param tasDeCarte
	 * Tas de carte dans lequel le joueur va piocher les cartes
	 */
	public String piocher(int nombreCarte, TasDeCartes tasDeCarte){
		
		//Si il n'y a plus assez de carte dans le talon on rajoute un jeu de carte de 54 cartes
		if(nombreCarte > tasDeCarte.getSize() && tasDeCarte instanceof Talon) {
			tasDeCarte.ajouterTas(Talon.createJeu(54));
		}
		
		//Si il n'avais plus qu'une carte on remet annoncerCarte à false
		if(this.getMain().getSize() == 1) {
			this.annoncerCarte = false;
		}
		
		//Pour chaque carte que le joueur doit piocher
		for(int i=0; i<nombreCarte; i++){
			//On ajoute la carte dans sa main
			this.main.ajouterCarte(tasDeCarte.getCarte(tasDeCarte.getSize()-1));
			//Et on la retire du tas de carte placé en paramètre
			tasDeCarte.supprimerDerniereCarte();
		}
		
		//Affichage du message qui sera utilisé dans la console ou dans le JTextArea
		String affichage = this.nom + " a pioché ";
		if(nombreCarte == 1)
			affichage += (nombreCarte) + " carte.";
		else 
			affichage += (nombreCarte) + " cartes.";
		
		return affichage;
	}
	
	/**
	 * Fait jouer une carte au joueur sur le plateau.
	 * @param carte
	 * La carte dont le joueur veut se défausser
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 * @return une chaîne de caractère qui représente la carte jouée par le joueur
	 */
	public String jouerCarte(Carte carte, Plateau plateau) {
		//
		plateau.getDefausse().ajouterCarteDebut(carte);
		//La carte choisie est supprimée de la main du joueur.
		this.main.supprimerCarte(carte);
		
		String res = this.nom + " a joué : " + carte.getNumero() +" "+ carte.getCouleur();
		
		//On enlève l'autre carte qui se trouve dans la défausse et on l'a met en dessous le Talon
		//Cela permet de toujours avoir des cartes dans le Talon
		plateau.getDefausse().vider(plateau.getTalon());
		
		//Si la carte jouée est un 7 
		//et que le joueur n'a plus de carte dans sa main on applique pas l'effet de la carte.
		if(!(carte instanceof Carte7))
			res += "\n"+carte.effet(plateau);
		else 
			if(this.getMain().getSize() != 0)
				res += "\n"+carte.effet(plateau);
		
		//Si ce n'est pas un 8 ou un joker on actualise la couleur actuelle du plateau
		if(!(carte instanceof Carte8) && !(carte instanceof CarteJoker)) {
			plateau.setCouleurActuelle(plateau.getDefausse().getCarte(0).getCouleur());
		} 
		
		return res;
	}
	
	/**
	 * Permet de connaître la position d'un As dans la main d'un joueur.
	 * Utile car si un joueur joue un As et que le suivant en possède un, il sera joué automatiquement.
	 * @return la position de l'As dans la main
	 */
	public int possedeAs() {
		int positionAs = -1;
		int hasAs = 0;
		//On parcours toutes les cartes du joueur
		for(Carte carte :  this.main.getJeu()) {
			//si il possède un as on récupère sa position
			if(carte.getNumero().equals(Constante.NUMERO_CARTE_AS)) 
				positionAs = hasAs;
			hasAs++;
		}
		//On retourne la position d'un as dans la main d'un joueur
		return positionAs;
	}
}
