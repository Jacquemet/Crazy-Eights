package huitAmericain;

import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.Joueur;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;
import huitAmericain.tasDeCartes.Defausse;
import huitAmericain.tasDeCartes.Main;
import huitAmericain.tasDeCartes.Talon;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;


/**
 * <b>Plateau est la classe représentant le plateau sur lequel une partie se déroulera.</b>
 * <p>
 * Un plateau est principalement caractérisé par les informations suivantes :
 * <ul>
 * <li>Une Liste de joueurs variable de 2 à 8.</li>
 * <li>Un talon comportant 54 ou 108 cartes</li>
 * <li>Une défausse où les joueurs poseront leurs cartes</li>
 * </ul>
 * </p>
 * <p>
 * On retrouve également des informations importantes sur les caractéristiques de la partie 
 * comme la limite de points, le sens de rotation, le joueur qui joue actuellementet 
 * et la couleur qui doit être joué.
 * </p>
 * <p>
 * 	Cette classe est sérializable pour permettre sa sauvegarde et son chargement.
 * 	Tous les attributs qu'elle contient sont également sérializable à l'exception du Thread qui ne ne peut pas être sérialisé.
 * 	De plus elle implémente la classe runnable pour permettre la mise en place du Thread.
 * 	Et enfin elle hérite de la classe observable qui permet la mise en place de du modèle mvc.
 * </p>
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 *
 */
public class Plateau extends Observable implements Runnable, Serializable{
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -2846933495558925051L;
	/**
	 * Instance unique de la classe Plateau.
	 */
	private static Plateau instancePlateau = null;	
	/**
	 * Booléen qui permet de savoir si on commence une nouvelle partie ou une nouvelle manche.
	 */
	private boolean firstLoad;
	/**
	 * Nombre de manche que comprendra la partie.
	 */
	private int nbManche;
	/**
	 * Limite de points qu'un joueur pourra atteindre, si il dépasse cette limite la partie 
	 * se terminera à la fin de la manche en cours.
	 */
	private int limitePoints;
	/**
	 * Tableau de chaine de caractère la valeur correspond au nom des joueurs
	 * et la taille de la liste correspond au nombre de joueurs réels dans la partie.
	 */
	private List<String> nomJoueurReel;
	/**
	 *  HashMap de chaine de caractère la clé correspond au nom des joueurs, la valeur correspond au niveau du joueur
	 * et la taille de la liste correspond au nombre de joueurs virtuels dans la partie.
	 */
	private HashMap<String, Integer> nomJoueurVirtuel;
	/**
	 * Nombre de carte que possèdera chaque joueur au début d'une manche.
	 */
	private int nbCarteEnMain;
	/**
	 * Talon du plateau.
	 * @see Talon
	 */
	private Talon talon;
	/**
	 * Défausse du plateau.
	 * @see Defausse
	 */
	private Defausse defausse;
	/**
	 * Sens de rotation du plateau 1 pour le sens classique de rotation et -1 pour le sens inverse.
	 * @value 1 ou -1
	 */
	private int sensRotation;
	/**
	 * Couleur actuelle du plateau, quatre valeurs possible
	 * @value Coeur, Carre, Pique, Trefle. 
	 */
	private String couleurActuelle;
	/**
	 * Liste des joueurs de la partie, elle comprend 2 à 8 joueurs maximum.
	 */
	private List<Joueur> listeJoueurs;
	/**
	 * Joueur qui joue le tour actuellement. 
	 */
	private Joueur joueurActuel;
	
	/**
	 * Thread permettant de jouer une partie 
	 * Il n'est pas sauvegardé lors de la sérialisation
	 */
	private transient Thread threadPlateau;
	
	/**
	 * Booléen qui permet de savoir si un joueur a fini de jouer un tour 
	 */
	private boolean fintour;
	
	/**
	 * Booléen qui détermine si une manche est en cours ou non
	 */
	private boolean mancheEnCours = false;
	
	/**
	 * Permet de faire varier la vitesse de jeu des IA
	 */
	private int vitesseIA = 10;
	
	/**
	 * Constructeur par défaut d'un plateau
	 */
	private Plateau(){
		super();
	}
	
	/**
	 * Récupère l'unique instance du plateau, si cette dernière n'existe pas on la créée
	 * @return l'unique plateau qui sera utilisé tout au long d'une partie
	 */
	public static Plateau getInstance(){
		if(instancePlateau == null){
			//Synchronisation permet d'éviter d'éventuelles erreurs en cas de threads.
            synchronized(Plateau.class) {
                if (instancePlateau == null) {
                	instancePlateau = new Plateau();	
                }
            }
		}
		return instancePlateau;
	}

	/**
	 * Retourne le nombre de manche de la partie.
	 * @return Le nombre de manche
	 */
	public int getNbManche() {
		return nbManche;
	}

	/**
	 * Met à jour le nombre de manche d'une partie.
	 * @param nbManche
	 * 			Le nouveau nombre de manche.
	 */
	public void setNbManche(int nbManche) {
		this.nbManche = nbManche;
	}

	/**
	 * Retourne la limite de points d'une partie.
	 * @return limite de points
	 */
	public int getLimitePoints() {
		return limitePoints;
	}
	
	/**
	 * Met à jour la limite de points de la partie.
	 * @param limitePoints
	 *  La nouvelle limite de points
	 */
	public void setLimitePoints(int limitePoints) {
		this.limitePoints = limitePoints;
	}
	
	/**
	 * Retourne la liste des noms des Joueurs Réels
	 * @return liste des joueurs réels
	 */
	public List<String> getNomJoueurReel() {
		return this.nomJoueurReel;
	}
	
	/**
	 * Met à jour le nombre de joueur réel.
	 * @param nomJoueurReel
	 * La nouvelle liste contenant le nom et le nombre de joueurs
	 */
	public void setNomJoueurReel(List<String> nomJoueurReel) {
		this.nomJoueurReel = nomJoueurReel;
	}

	/**
	 * Retourne le nombre de Carte en main au début d'une partie.
	 * @return nombre de carte en main par défaut
	 */
	public int getNbCarteEnMain() {
		return nbCarteEnMain;
	}

	/**
	 * Met à jour le nombre  de carte en main par défaut.
	 * @param nbCarteEnMain
	 * Le nouveau nombre de carte en main par défaut
	 */
	public void setNbCarteEnMain(int nbCarteEnMain) {
		this.nbCarteEnMain = nbCarteEnMain;
	}

	/**
	 * Retourne le Talon du plateau.
	 * @return Talon
	 */
	public Talon getTalon() {
		return talon;
	}

	/**
	 * Retourne la Défausse du plateau.
	 * @return Défausse
	 */
	public Defausse getDefausse() {
		return defausse;
	}

	/**
	 * Retourne le sens de rotation du plateau.
	 * @return sens de rotation
	 */
	public int getSensRotation() {
		return sensRotation;
	}

	/**
	 * Met à jour le sens de rotation du plateau.
	 * @param sensRotation
	 * Le nouveau sens de rotation
	 */
	public void setSensRotation(int sensRotation) {
		this.sensRotation = sensRotation;
	}

	/**
	 * Retourne la couleur actuelle de la défausse ou la couleur qui doit être joué.
	 * @return couleur actuelle
	 */
	public String getCouleurActuelle() {
		return couleurActuelle;
	}

	/**
	 * Met à jour la couleur actuel du plateau
	 * @param couleurActuelle
	 * La nouvelle couleur
	 */
	public void setCouleurActuelle(String couleurActuelle) {
		this.couleurActuelle = couleurActuelle;
	}

	/**
	 * Retourne la liste des joueurs de la partie 
	 * @return liste de joueurs
	 */
	public List<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	/**
	 * Met à jour la liste des joueurs d'un partie.
	 * @param listeJoueurs
	 * La nouvelle liste des joueurs
	 */
	public void setListeJoueurs(List<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	
	/**
	 * Retourne le joueur qui joue actuellement.
	 * @return le joueur actuel
	 */
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}
	
	/**
	 * Met à jour le joueur actuel.
	 * @param joueurActuel
	 * Le nouveau joueur actuel
	 */
	public void setJoueurActuel(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}
	
	/**
	 * Retourne la liste des noms et des niveaux des joueurs virtuels 
	 * @return liste de noms
	 */
	public HashMap<String, Integer> getNomJoueurVirtuel() {
		return nomJoueurVirtuel;
	}
	
	/**
	 * Met à jour la liste de noms et de niveaux des joueurs virtuels
	 * @param nomJoueurVirtuel
	 * 	 La nouvelle liste de noms et niveaux des joueurs virtuels
	 */
	public void setNomJoueurVirtuel(HashMap<String, Integer> nomJoueurVirtuel) {
		this.nomJoueurVirtuel = nomJoueurVirtuel;
	}
	
	/**
	 * Retourne le thread qui est lancé lors d'une nouvelle partie.
	 * @return le Thread d'une partie
	 */
	public Thread getThreadPlateau() {
		return threadPlateau;
	}

	/**
	 * Retourne la valeur de fin de tour permet de reprendre le cours d'une partie.
	 * @return la valeur de la fin du tour du joueur.
	 */
	public boolean isFintour() {
		return fintour;
	}

	/**
	 * Met à jour la valeur de fin de tour
	 * @param fintour
	 */
	public void setFintour(boolean fintour) {
		this.fintour = fintour;
	}
	
	/**
	 * Retourne la vitesse des joueurs virtuels
	 * @return vitesse de l'IA
	 */
	public int getVitesseIA() {
		return vitesseIA;
	}

	/**
	 * Met à jour la vitesse de l'IA
	 * @param vitesseIA
	 */
	public void setVitesseIA(int vitesseIA) {
		this.vitesseIA = vitesseIA;
	}
	
	/**
	 * Initialise le plateau avec toutes les valeurs sélectionnées par l'utilisateur.
	 * @param nbManche
	 * Le nombre de manches 
	 * @param limitePts
	 * La limite de points
	 * @param nomJoueurReel
	 * La liste des noms des joueurs réel
	 * @param niveauJoueurVirtuel
	 * La liste des joueurs viruels avec leur niveau associé
	 * @param nomJoueurVirtuel
	 * La liste des noms des joueurs virtuels
	 * @param nbCarteEnMain
	 * Le nombre de carte en main par défaut
	 * @param nbCarteTalon
	 * Le nombre de cartes dans le talon
	 */
	public void initialisation(int nbManche, int limitePts, List<String> nomJoueurReel, HashMap<String, Integer> nomJoueurVirtuel, int nbCarteEnMain, int nbCarteTalon, int vitesseIA){
		//On met à jour les différents paramètre
		setNbManche(nbManche);
		setLimitePoints(limitePts);
		setNomJoueurReel(nomJoueurReel);
		setNomJoueurVirtuel(nomJoueurVirtuel);
		setNbCarteEnMain(nbCarteEnMain);
		setVitesseIA(vitesseIA);
		this.firstLoad = true;
		this.sensRotation = 1;
		this.defausse = Defausse.getIstance();
		this.talon = Talon.getInstance(nbCarteTalon);
		//initialisation de la liste
		this.listeJoueurs = new LinkedList<Joueur>();
	}
	
	/**
	 * Passe au joueur suivant en actualisant le joueur actuel.
	 */
	public void joueurSuivant() {
		//On vérifie que la manche n'est pas terminé
		if(!ReglesJeu.finManche(this)) {
			setJoueurActuel(getJoueurSuivant());
			//Si c'est un joueur virtuel on le fait jouer sinon on laisse jouer le joueur. 
			if(joueurActuel instanceof JoueurVirtuel){
				JoueurVirtuel joueurVirtu = (JoueurVirtuel)joueurActuel;
				setChanged();
				notifyObservers(joueurVirtu.appliquerStrategie(this));
				//On rapelle la méthode jusqu'a temps que le joueur suivant soit un joueur réel.
				try {
					Thread.sleep(getVitesseIA());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				joueurSuivant();	
			}
		}
	}
	
	/**
	 * Création de tous les joueurs Réels pour la partie en cours.
	 * @see JoueurReel
	 */
	public void creerJoueurReel() {
		//Si il y a des joueurs réel
		if(nomJoueurReel != null){
			for(String nom : nomJoueurReel){
				Joueur jr = new JoueurReel(nom);
				listeJoueurs.add(jr);
			}	
		}
	}
	
	/**
	 * Création de tous les joueurs virtuels pour la partie en cours.
	 * @see JoueurVirtuel
	 */
	public void creerJoueurVirtuel(){
		//Itérateur qui va permettre le parcours du HashMap
		Iterator<String> iterator = nomJoueurVirtuel.keySet().iterator(); 
		//Tant qu'il y a un prochain élément dans le HashMap
		while(iterator.hasNext()) {
			//On récupère le nom du joueur virtuel.
			String nomJoueur = (String) iterator.next();
			//On récupère le niveau du joueur virtuel.
			Integer niveau = (Integer) nomJoueurVirtuel.get(nomJoueur);
			//Création du joueur virtuel nomJoueurVirtuel.get(iteration)
			Joueur jv = new JoueurVirtuel(nomJoueur, niveau);
			//Ajout du joueur virtuel à la liste des joueurs
			listeJoueurs.add(jv);
			iterator.remove(); // supprime l'element courant
		}
	}

	/**
	 * Commence une nouvelle manche : 
	 * <ul>
	 * <li>change de position tous les joueurs aléatoirement et actualise la position des joueurs</li>
	 * <li>le donneur distribue les cartes pour chaque joueurs</li>
	 * <li>le donneur retourne la bergère</li>
	 * <li>le donneur laisse la main au joueur se trouvant à sa gauche</li>
	 * </ul>
	 * si c'est la première manche de la partie on créer les joueurs<br>
	 * sinon on vide la pile Défausse et on remplit le talon
	 */
	public void nouvelleManche(){
		this.mancheEnCours = true;
		// Si c'est la première manche on créer les tous les joueurs
		if(firstLoad){
			firstLoad = false;
			creerJoueurReel();
			creerJoueurVirtuel();
		} else {
			//Sinon on parcours chaque carte de la defausse pour l'ajouter au talon.
			for(Carte c : defausse.getJeu())
				talon.ajouterCarte(c);
			//Puis on vide la défausse.
			defausse.vider();
		}
		
		//Position initiale
		int pos = 0;
		//On mélange les joueurs pour qu'ils aient des positions aléatoires.
		Collections.shuffle(listeJoueurs);
		for(Joueur j : listeJoueurs){
			j.setPosition(pos);
			pos++;
		}
		
		// On met à jour le sens de rotation
		setSensRotation(1);
		//On actualise le joueur actuel.
		setJoueurActuel(listeJoueurs.get(0));
		//On mélange le talon
		Collections.shuffle(talon.getJeu());
		//Le joueur Actuel distribut les cartes.
		distribuerCarte();
		//On retourne la bergère.
		retournerBergere();
		//On laisse commencer le joueur à la gauche du joueur actuel (le joueur qui vient de distribuer)
		joueurSuivant();
	}
	
	/**
	 * Le joueur se trouvant à la position 0 c'est-à-dire le donneur 
	 * distribue le nombre de carte par défaut à chaque joueurs de la partie.
	 */
	public void distribuerCarte(){
		//On distribue le nombre de cartes placées en paramètre.
		for(int i=0; i<this.nbCarteEnMain;i++){
			//Pour chaque joueurs on ajoute les cartes à leurs mains.
			for(int j=1; j<listeJoueurs.size()+1; j++){
				//listeJoueurs.get(j%listeJoueurs.size()).piocher(1, talon);
				listeJoueurs.get(j%listeJoueurs.size()).getMain().ajouterCarte(talon.getCarte(talon.getSize()-1));
				talon.supprimerDerniereCarte();
			}
		}
	}
	
	/**
	 * Retourne la Bergère sur la défausse pour commencer une nouvelle manche.<br>
	 * Si la bergère est un joker, on mélange à nouveau le talon et on rapelle cette méthode jusqu'a ce que la bergère ne soit pas un joker.
	 * @see Defausse
	 * @see Talon
	 */
	public void retournerBergere(){
		
		// Si la bergère est un as on remélange le jeu et on retourne la nouvelle Bergère.
		if((talon.getCarte(talon.getSize()-1)) instanceof CarteJoker) {
			Collections.shuffle(talon.getJeu());
			retournerBergere();
		}
		//Sinon on retourne la bergère sur la défausse.
		else {
			defausse.ajouterCarte(talon.getCarte(talon.getSize()-1));
			talon.supprimerDerniereCarte();
			this.setCouleurActuelle(defausse.getCarte(0).getCouleur());
		}
	}
	
	/**
	 * Méthode à appliquer à la fin d'une manche, 
	 * Compte les points pour chaque cartes restantes dans la main de chauqe joueur 
	 * et remet toutes les cartes que les joueurs possèdent dans le talon.
	 * @see Joueur
	 * @see Main
	 */
	public void compterNbPoints() {
		for(Joueur j : listeJoueurs) {
			for(Carte c : j.getMain().getJeu()) {
				j.setNombrePoints(j.getNombrePoints()+c.getPoints());
				talon.ajouterCarte(c);
			}
			//On vide la main du joueur
			j.getMain().vider();
		}
	}
	
	/**
	 * Récupère le joueur qui va jouer le prochain tour en utilisant le sens de rotation de la partie.
	 * @return le joueur suivant.
	 * @see Plateau#sensRotation
	 */
	public Joueur getJoueurSuivant() {
		int positionSuivante = (joueurActuel.getPosition()+getSensRotation())%(listeJoueurs.size());
		//Vérifiation en cas de sens inverse.
		if(positionSuivante == -1)
			positionSuivante = listeJoueurs.size()-1;
		
		return listeJoueurs.get(positionSuivante);
	}

	/**
	 * Méthode qui affiche le nom de chaque joueurs avec leurs nombre de points associé.
	 * @return l'affichage du plateau
	 */
	public  String afficherClassement() {
		String res = "\n"+ "Classement :"+"\n";
		for(Joueur j : listeJoueurs) {
			res += j.getNom() + " : " + j.getNombrePoints() + " points."+"\n";
		}
		return res;
	}
	
	/**
	 * Surcharge de la méthode d'affichage d'un plateau,
	 * On affiche le Talon, la defausse et la main du joueur actuel
	 */
    public String toString() {
    	String res = new String();
		res += getTalon();
		res += getDefausse()+"\n";
		res += " Couleur à jouer :  "+this.couleurActuelle+"\n"+"\n";
		res += getJoueurActuel().getNom()+"\n";
		res += getJoueurActuel().getMain()+"\n";
    	return res;
    }
	
    
	/**
	 * Méthode qui fait jouer tous les joueurs, cette méthode prend fin lorsqu'un joueur n'a plus de carte dans sa main.
	 * @see Plateau#jouerTour()
	 * @see Plateau#joueurSuivant()
	 */
	public synchronized void jouerManche() {
		//Tant que la manche n'est pas terminée.
		while(!ReglesJeu.finManche(this)) {
			//Si c'est un joueur réel on le notify à l'interface et on met a jour l'affichage.
			if(joueurActuel instanceof JoueurReel) {
				setFintour(false);
				//On notify aux observateurs qu'il faut mettre à jour les composants
				setChanged();
				notifyObservers();
			}
			joueurSuivant();
		}
	}
	
	/**
	 * Méthode qui démarre une nouvelle partie dans un nouveau Thread.
	 * @see Plateau#threadPlateau
	 */
	public void jouerPartie() {
		//On crée et on démarre le nouveau Thread
		threadPlateau = new Thread(this, "Thread Plateau");
		threadPlateau.start();
	}


	@Override
	public void run() {
		//Tant que la partie n'est pas finie.
		while(!ReglesJeu.finPartie(this)){
			//Nofications  du commencement d'une partie aux observers 
			setChanged();
			notifyObservers("Debut d'une nouvelle manche.");
			//Initialisation d'une nouvelle manche
			if(!this.mancheEnCours) {
				nouvelleManche();
			}
			//On joue une manche
			jouerManche();
			this.mancheEnCours = false;
			//Si la limite de la de partie se compte en manche on réduit le nombre de manche
			if(getNbManche() != -1)
				setNbManche(getNbManche()-1);

			//On compte le nombre de point
			compterNbPoints();
			//On notifie aux observers que la manche est finie.
			setChanged();
			notifyObservers(afficherClassement() + "\n" + "Fin de la manche.");
		}
		
		int nbpoints = this.getLimitePoints();
		String winner = "";
		//On retrouve le joueur qui possède le moins de points 
		for(Joueur j : listeJoueurs) {
			if(j.getNombrePoints() <= nbpoints) {
				nbpoints = j.getNombrePoints();
				winner = j.getNom();
			}
		}
		String chaine = "Fin de la partie ! " + "\n Le vainqueur de la partie est " + winner;
		//On nofifie aux observers que la partie est finie.
		setChanged();
		notifyObservers(chaine);
	}
	
}