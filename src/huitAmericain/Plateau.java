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
 * <b>Plateau est la classe repr�sentant le plateau sur lequel une partie se d�roulera.</b>
 * <p>
 * Un plateau est principalement caract�ris� par les informations suivantes :
 * <ul>
 * <li>Une Liste de joueurs variable de 2 � 8.</li>
 * <li>Un talon comportant 54 ou 108 cartes</li>
 * <li>Une d�fausse o� les joueurs poseront leurs cartes</li>
 * </ul>
 * </p>
 * <p>
 * On retrouve �galement des informations importantes sur les caract�ristiques de la partie 
 * comme la limite de points, le sens de rotation, le joueur qui joue actuellementet 
 * et la couleur qui doit �tre jou�.
 * </p>
 * <p>
 * 	Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * 	Tous les attributs qu'elle contient sont �galement s�rializable � l'exception du Thread qui ne ne peut pas �tre s�rialis�.
 * 	De plus elle impl�mente la classe runnable pour permettre la mise en place du Thread.
 * 	Et enfin elle h�rite de la classe observable qui permet la mise en place de du mod�le mvc.
 * </p>
 * @author Thibault Jacquemet & Antoine Bl�chet
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
	 * Bool�en qui permet de savoir si on commence une nouvelle partie ou une nouvelle manche.
	 */
	private boolean firstLoad;
	/**
	 * Nombre de manche que comprendra la partie.
	 */
	private int nbManche;
	/**
	 * Limite de points qu'un joueur pourra atteindre, si il d�passe cette limite la partie 
	 * se terminera � la fin de la manche en cours.
	 */
	private int limitePoints;
	/**
	 * Tableau de chaine de caract�re la valeur correspond au nom des joueurs
	 * et la taille de la liste correspond au nombre de joueurs r�els dans la partie.
	 */
	private List<String> nomJoueurReel;
	/**
	 *  HashMap de chaine de caract�re la cl� correspond au nom des joueurs, la valeur correspond au niveau du joueur
	 * et la taille de la liste correspond au nombre de joueurs virtuels dans la partie.
	 */
	private HashMap<String, Integer> nomJoueurVirtuel;
	/**
	 * Nombre de carte que poss�dera chaque joueur au d�but d'une manche.
	 */
	private int nbCarteEnMain;
	/**
	 * Talon du plateau.
	 * @see Talon
	 */
	private Talon talon;
	/**
	 * D�fausse du plateau.
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
	 * Liste des joueurs de la partie, elle comprend 2 � 8 joueurs maximum.
	 */
	private List<Joueur> listeJoueurs;
	/**
	 * Joueur qui joue le tour actuellement. 
	 */
	private Joueur joueurActuel;
	
	/**
	 * Thread permettant de jouer une partie 
	 * Il n'est pas sauvegard� lors de la s�rialisation
	 */
	private transient Thread threadPlateau;
	
	/**
	 * Bool�en qui permet de savoir si un joueur a fini de jouer un tour 
	 */
	private boolean fintour;
	
	/**
	 * Bool�en qui d�termine si une manche est en cours ou non
	 */
	private boolean mancheEnCours = false;
	
	/**
	 * Permet de faire varier la vitesse de jeu des IA
	 */
	private int vitesseIA = 10;
	
	/**
	 * Constructeur par d�faut d'un plateau
	 */
	private Plateau(){
		super();
	}
	
	/**
	 * R�cup�re l'unique instance du plateau, si cette derni�re n'existe pas on la cr��e
	 * @return l'unique plateau qui sera utilis� tout au long d'une partie
	 */
	public static Plateau getInstance(){
		if(instancePlateau == null){
			//Synchronisation permet d'�viter d'�ventuelles erreurs en cas de threads.
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
	 * Met � jour le nombre de manche d'une partie.
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
	 * Met � jour la limite de points de la partie.
	 * @param limitePoints
	 *  La nouvelle limite de points
	 */
	public void setLimitePoints(int limitePoints) {
		this.limitePoints = limitePoints;
	}
	
	/**
	 * Retourne la liste des noms des Joueurs R�els
	 * @return liste des joueurs r�els
	 */
	public List<String> getNomJoueurReel() {
		return this.nomJoueurReel;
	}
	
	/**
	 * Met � jour le nombre de joueur r�el.
	 * @param nomJoueurReel
	 * La nouvelle liste contenant le nom et le nombre de joueurs
	 */
	public void setNomJoueurReel(List<String> nomJoueurReel) {
		this.nomJoueurReel = nomJoueurReel;
	}

	/**
	 * Retourne le nombre de Carte en main au d�but d'une partie.
	 * @return nombre de carte en main par d�faut
	 */
	public int getNbCarteEnMain() {
		return nbCarteEnMain;
	}

	/**
	 * Met � jour le nombre  de carte en main par d�faut.
	 * @param nbCarteEnMain
	 * Le nouveau nombre de carte en main par d�faut
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
	 * Retourne la D�fausse du plateau.
	 * @return D�fausse
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
	 * Met � jour le sens de rotation du plateau.
	 * @param sensRotation
	 * Le nouveau sens de rotation
	 */
	public void setSensRotation(int sensRotation) {
		this.sensRotation = sensRotation;
	}

	/**
	 * Retourne la couleur actuelle de la d�fausse ou la couleur qui doit �tre jou�.
	 * @return couleur actuelle
	 */
	public String getCouleurActuelle() {
		return couleurActuelle;
	}

	/**
	 * Met � jour la couleur actuel du plateau
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
	 * Met � jour la liste des joueurs d'un partie.
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
	 * Met � jour le joueur actuel.
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
	 * Met � jour la liste de noms et de niveaux des joueurs virtuels
	 * @param nomJoueurVirtuel
	 * 	 La nouvelle liste de noms et niveaux des joueurs virtuels
	 */
	public void setNomJoueurVirtuel(HashMap<String, Integer> nomJoueurVirtuel) {
		this.nomJoueurVirtuel = nomJoueurVirtuel;
	}
	
	/**
	 * Retourne le thread qui est lanc� lors d'une nouvelle partie.
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
	 * Met � jour la valeur de fin de tour
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
	 * Met � jour la vitesse de l'IA
	 * @param vitesseIA
	 */
	public void setVitesseIA(int vitesseIA) {
		this.vitesseIA = vitesseIA;
	}
	
	/**
	 * Initialise le plateau avec toutes les valeurs s�lectionn�es par l'utilisateur.
	 * @param nbManche
	 * Le nombre de manches 
	 * @param limitePts
	 * La limite de points
	 * @param nomJoueurReel
	 * La liste des noms des joueurs r�el
	 * @param niveauJoueurVirtuel
	 * La liste des joueurs viruels avec leur niveau associ�
	 * @param nomJoueurVirtuel
	 * La liste des noms des joueurs virtuels
	 * @param nbCarteEnMain
	 * Le nombre de carte en main par d�faut
	 * @param nbCarteTalon
	 * Le nombre de cartes dans le talon
	 */
	public void initialisation(int nbManche, int limitePts, List<String> nomJoueurReel, HashMap<String, Integer> nomJoueurVirtuel, int nbCarteEnMain, int nbCarteTalon, int vitesseIA){
		//On met � jour les diff�rents param�tre
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
		//On v�rifie que la manche n'est pas termin�
		if(!ReglesJeu.finManche(this)) {
			setJoueurActuel(getJoueurSuivant());
			//Si c'est un joueur virtuel on le fait jouer sinon on laisse jouer le joueur. 
			if(joueurActuel instanceof JoueurVirtuel){
				JoueurVirtuel joueurVirtu = (JoueurVirtuel)joueurActuel;
				setChanged();
				notifyObservers(joueurVirtu.appliquerStrategie(this));
				//On rapelle la m�thode jusqu'a temps que le joueur suivant soit un joueur r�el.
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
	 * Cr�ation de tous les joueurs R�els pour la partie en cours.
	 * @see JoueurReel
	 */
	public void creerJoueurReel() {
		//Si il y a des joueurs r�el
		if(nomJoueurReel != null){
			for(String nom : nomJoueurReel){
				Joueur jr = new JoueurReel(nom);
				listeJoueurs.add(jr);
			}	
		}
	}
	
	/**
	 * Cr�ation de tous les joueurs virtuels pour la partie en cours.
	 * @see JoueurVirtuel
	 */
	public void creerJoueurVirtuel(){
		//It�rateur qui va permettre le parcours du HashMap
		Iterator<String> iterator = nomJoueurVirtuel.keySet().iterator(); 
		//Tant qu'il y a un prochain �l�ment dans le HashMap
		while(iterator.hasNext()) {
			//On r�cup�re le nom du joueur virtuel.
			String nomJoueur = (String) iterator.next();
			//On r�cup�re le niveau du joueur virtuel.
			Integer niveau = (Integer) nomJoueurVirtuel.get(nomJoueur);
			//Cr�ation du joueur virtuel nomJoueurVirtuel.get(iteration)
			Joueur jv = new JoueurVirtuel(nomJoueur, niveau);
			//Ajout du joueur virtuel � la liste des joueurs
			listeJoueurs.add(jv);
			iterator.remove(); // supprime l'element courant
		}
	}

	/**
	 * Commence une nouvelle manche : 
	 * <ul>
	 * <li>change de position tous les joueurs al�atoirement et actualise la position des joueurs</li>
	 * <li>le donneur distribue les cartes pour chaque joueurs</li>
	 * <li>le donneur retourne la berg�re</li>
	 * <li>le donneur laisse la main au joueur se trouvant � sa gauche</li>
	 * </ul>
	 * si c'est la premi�re manche de la partie on cr�er les joueurs<br>
	 * sinon on vide la pile D�fausse et on remplit le talon
	 */
	public void nouvelleManche(){
		this.mancheEnCours = true;
		// Si c'est la premi�re manche on cr�er les tous les joueurs
		if(firstLoad){
			firstLoad = false;
			creerJoueurReel();
			creerJoueurVirtuel();
		} else {
			//Sinon on parcours chaque carte de la defausse pour l'ajouter au talon.
			for(Carte c : defausse.getJeu())
				talon.ajouterCarte(c);
			//Puis on vide la d�fausse.
			defausse.vider();
		}
		
		//Position initiale
		int pos = 0;
		//On m�lange les joueurs pour qu'ils aient des positions al�atoires.
		Collections.shuffle(listeJoueurs);
		for(Joueur j : listeJoueurs){
			j.setPosition(pos);
			pos++;
		}
		
		// On met � jour le sens de rotation
		setSensRotation(1);
		//On actualise le joueur actuel.
		setJoueurActuel(listeJoueurs.get(0));
		//On m�lange le talon
		Collections.shuffle(talon.getJeu());
		//Le joueur Actuel distribut les cartes.
		distribuerCarte();
		//On retourne la berg�re.
		retournerBergere();
		//On laisse commencer le joueur � la gauche du joueur actuel (le joueur qui vient de distribuer)
		joueurSuivant();
	}
	
	/**
	 * Le joueur se trouvant � la position 0 c'est-�-dire le donneur 
	 * distribue le nombre de carte par d�faut � chaque joueurs de la partie.
	 */
	public void distribuerCarte(){
		//On distribue le nombre de cartes plac�es en param�tre.
		for(int i=0; i<this.nbCarteEnMain;i++){
			//Pour chaque joueurs on ajoute les cartes � leurs mains.
			for(int j=1; j<listeJoueurs.size()+1; j++){
				//listeJoueurs.get(j%listeJoueurs.size()).piocher(1, talon);
				listeJoueurs.get(j%listeJoueurs.size()).getMain().ajouterCarte(talon.getCarte(talon.getSize()-1));
				talon.supprimerDerniereCarte();
			}
		}
	}
	
	/**
	 * Retourne la Berg�re sur la d�fausse pour commencer une nouvelle manche.<br>
	 * Si la berg�re est un joker, on m�lange � nouveau le talon et on rapelle cette m�thode jusqu'a ce que la berg�re ne soit pas un joker.
	 * @see Defausse
	 * @see Talon
	 */
	public void retournerBergere(){
		
		// Si la berg�re est un as on rem�lange le jeu et on retourne la nouvelle Berg�re.
		if((talon.getCarte(talon.getSize()-1)) instanceof CarteJoker) {
			Collections.shuffle(talon.getJeu());
			retournerBergere();
		}
		//Sinon on retourne la berg�re sur la d�fausse.
		else {
			defausse.ajouterCarte(talon.getCarte(talon.getSize()-1));
			talon.supprimerDerniereCarte();
			this.setCouleurActuelle(defausse.getCarte(0).getCouleur());
		}
	}
	
	/**
	 * M�thode � appliquer � la fin d'une manche, 
	 * Compte les points pour chaque cartes restantes dans la main de chauqe joueur 
	 * et remet toutes les cartes que les joueurs poss�dent dans le talon.
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
	 * R�cup�re le joueur qui va jouer le prochain tour en utilisant le sens de rotation de la partie.
	 * @return le joueur suivant.
	 * @see Plateau#sensRotation
	 */
	public Joueur getJoueurSuivant() {
		int positionSuivante = (joueurActuel.getPosition()+getSensRotation())%(listeJoueurs.size());
		//V�rifiation en cas de sens inverse.
		if(positionSuivante == -1)
			positionSuivante = listeJoueurs.size()-1;
		
		return listeJoueurs.get(positionSuivante);
	}

	/**
	 * M�thode qui affiche le nom de chaque joueurs avec leurs nombre de points associ�.
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
	 * Surcharge de la m�thode d'affichage d'un plateau,
	 * On affiche le Talon, la defausse et la main du joueur actuel
	 */
    public String toString() {
    	String res = new String();
		res += getTalon();
		res += getDefausse()+"\n";
		res += " Couleur � jouer :  "+this.couleurActuelle+"\n"+"\n";
		res += getJoueurActuel().getNom()+"\n";
		res += getJoueurActuel().getMain()+"\n";
    	return res;
    }
	
    
	/**
	 * M�thode qui fait jouer tous les joueurs, cette m�thode prend fin lorsqu'un joueur n'a plus de carte dans sa main.
	 * @see Plateau#jouerTour()
	 * @see Plateau#joueurSuivant()
	 */
	public synchronized void jouerManche() {
		//Tant que la manche n'est pas termin�e.
		while(!ReglesJeu.finManche(this)) {
			//Si c'est un joueur r�el on le notify � l'interface et on met a jour l'affichage.
			if(joueurActuel instanceof JoueurReel) {
				setFintour(false);
				//On notify aux observateurs qu'il faut mettre � jour les composants
				setChanged();
				notifyObservers();
			}
			joueurSuivant();
		}
	}
	
	/**
	 * M�thode qui d�marre une nouvelle partie dans un nouveau Thread.
	 * @see Plateau#threadPlateau
	 */
	public void jouerPartie() {
		//On cr�e et on d�marre le nouveau Thread
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
			//Si la limite de la de partie se compte en manche on r�duit le nombre de manche
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
		//On retrouve le joueur qui poss�de le moins de points 
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