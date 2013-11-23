package huitAmericain.affichage;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.ReglesJeu;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.tasDeCartes.Defausse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * <b>Classe Affichage Console</b>
 * <p>
 * Classe qui permet de jouer uen partie dans la console.<br>
 * On y retrouve tout le param�trage d'une partie et les m�me �l�ments que lors d'une partie en mode graphique.
 * Cependant il n'est pas possible d'anoncer carte dans ce mode d'affichage.
 * </p>
 * <p> 
 * Cette classe impl�mente l'interface Observer pour permettre l'utilisation du pattern MVC.
 * </p>
 * @see Observer
 * @see Plateau
 * @author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class AffichageConsole implements Observer {
	
	/**
	 * Le plateau qui est utilis� pour jouer la partie console
	 */
	private Plateau plateau;
	/**
	 * La fenetre principale qui va permettre de sauvegarder la partie console ou bien de red�marer une partie en mode console
	 */
	private JFrame fenetre;
	
	/**
	 * Constructeur d'un affichage console
	 * @param fenetre la fenetre principale 
	 * @param plateau le plateau qui est utilis� pour jouer
	 */
	public AffichageConsole (JFrame fenetre, Plateau plateau) {
		this.fenetre = fenetre;
		this.plateau = plateau;
		//Ajout de l'observateur
		this.plateau.addObserver(this);
		this.affichage();
	}
	
	/**
	 * M�thode qui affiche les quatres couleurs et demande au joueur actuel de choisir la nouvelle couleur actuelle du plateau.
	 * @see Plateau#couleurActuelle
	 * @see Plateau#joueurActuel
	 */
	private void choisirCouleur() {
		Scanner sc = new Scanner(System.in);
		//Affichage des quatres couleurs
		String res = "";
        res += String.format("%9s","--------")+"  "+String.format("%9s","--------")+"  "+String.format("%9s","--------")+"  "+String.format("%9s","--------")+'\n';
        res += "|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+'\n' ;
        res += '|'+String.format("%6s","Carreau") + String.format("%2s","|")+" "+'|'+String.format("%6s","Coeur") + String.format("%3s","|")+" "+'|'+String.format("%6s","Pique") + String.format("%3s","|")+" "+'|'+String.format("%6s","Tr�fle") + String.format("%3s","|")+"\n";
        res += "|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+'\n' ;
        res += "|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+"|"+String.format("%9s","|")+" "+'\n' ;
        res += String.format("%9s","--------")+"  "+String.format("%9s","--------")+ "  "+String.format("%9s","--------")+"  "+String.format("%9s","--------")+'\n'; 
		//Demande au joueur r�el de saisir la couleur de son choix.
        res += " Choissisez une couleur (1 = Carreau, 2 = Coeur, 3 = Pique et 4 = Tr�fle): ";
		
		System.out.println(res);
		//R�cup�re la valeur saisie et actualise la couleur actuelle.
		int couleur = sc.nextInt();
		switch (couleur) {
			case 1 :
				plateau.setCouleurActuelle(Constante.CARREAU);
				break;
			case 2 :
				plateau.setCouleurActuelle(Constante.COEUR);
				break;
			case 3 :
				plateau.setCouleurActuelle(Constante.PIQUE);
				break;
			case 4 :
				plateau.setCouleurActuelle(Constante.TREFLE);
				break;
		}
        
	}
	
	/**
	 * M�thode qui fait jouer le joueur Actuel :
	 * Le joueur s�lectionne sa carte si elle est valide on la pose 
	 * sinon si il n'a aucune carte valide il doit piocher un carte.<br>
	 * On passe ensuite au joueur suivant.  
	 * @see Plateau
	 * @see ReglesJeu#verifierCarte(Carte, Defausse, Plateau) 
	 */
	private synchronized void jouerTour() {
		boolean finTour = false;
		//Scanner pour r�cup�rer les cartes que le joueur va s�lectionner
		Scanner sc = new Scanner(System.in);
		//Tant que le joueur n'a pas terminer son tour.
		while(!finTour) {
			System.out.println("Veuillez choisir une carte � jouer (0 = piocher, 1 = 1�re carte , ...) :");
			//On r�cup�re la i�me carte
			int str = sc.nextInt();
			//Si le joueur veut piocher.
			if(str == 0) {
				System.out.println(plateau.getJoueurActuel().piocher(1, plateau.getTalon()));
				finTour = true;
			}
			//Sinon on v�rifie que la position est la position d'une carte valide.
			else if(str < plateau.getJoueurActuel().getMain().getSize()+1 && str > 0 ){
				Carte carteAJouer = plateau.getJoueurActuel().getMain().getCarte(str-1);
				//On v�rifie que la carte est valide 
				if(ReglesJeu.verifierCarte(carteAJouer, plateau)) {
					//Si c'est un joker ou un 8 on affiche la saisie d'une couleur.
					if(carteAJouer instanceof Carte8 || carteAJouer instanceof CarteJoker)
						choisirCouleur();
					//On joue la carte
					System.out.println(plateau.getJoueurActuel().jouerCarte(carteAJouer, plateau));
					//On met fin au tour du joueur
					finTour = true;
				}
				else System.out.println("La Carte " +carteAJouer.getNumero()+" "+carteAJouer.getCouleur()+" n'est pas valide !");
			}
			else System.out.println("La carte s�lectionn�e n'existe pas !");
		}
		plateau.setFintour(true);
		//On notify au plateau que le thread peut reprendre la ou il s'est arret�
		this.notify();
	}
	
	/**
	 * M�thode qui affiche le param�trage du mode console mais aussi qui permet de d�marer une partie
	 */
	public void affichage() {
		Scanner sc = new Scanner(System.in);
		
		List<String> nomJoueurReel = new LinkedList<String>();
		HashMap<String, Integer> nomJoueurVirtuel = new HashMap<String, Integer>();
		
		//Initialisation du nombre de joueurs.
		int nbJoueursReels = 0;
		int nbJoueursVirtuels = 0;	
		int nbJoueurs = 0;
		
		//Tant que le nombre de joueurs � est inf�rieur � deux.
		while(nbJoueurs < 2){
			//On saisit le nombre de joueurs r��ls. On recommence l'op�ration si le nombre saisit est sup�rieur � 8.
			do {
				System.out.println("Saisissez le nombre de joueurs r�els (de 0 � 8) :");
				nbJoueursReels = sc.nextInt();
			} while(nbJoueursReels > 8);		
			
			//On choisit un nom pour chaque joueur r��l, qui est ensuite ajout� � la liste.
			for(int i = 0; i < nbJoueursReels; i++){
				System.out.println("Saisissez le nom du joueur r�el " + (i+1) + " :");
				String realName = sc.next();
				
				nomJoueurReel.add(realName);
			}		
			
			//On saisit le nombre de joueurs r��ls. On recommence l'op�ration si le nombre total de joueurs est sup�rieur � 8.
			do {
			System.out.println("Saisissez le nombre de joueurs virtuels (de 0 � " +(8 - nbJoueursReels)+ ") :");
			nbJoueursVirtuels = sc.nextInt();		
			} while(nbJoueursVirtuels > (8 - nbJoueursReels));
			
			//Pour chaque joueur virtuel, on choisit sa strat�gie. Le nom est laiss� par d�faut.
			for(int i = 0; i < nbJoueursVirtuels; i++){
				int strat = 0;
				do{
					System.out.println("Saisissez la strat�gie (1, 2 ou 3) du joueur virtuel " + (i+1) + " :");
					strat = sc.nextInt();
				}while(strat != 1 && strat !=2 && strat != 3);
				nomJoueurVirtuel.put("Joueur Virtuel"+(i+1), strat);
			}
			nbJoueurs = nbJoueursReels+nbJoueursVirtuels;
		}		
		
		//On choisit le nombre de cartes dans la main des joueurs au d�but de la partie.
		int nbCartesMain = 0;
		while(nbCartesMain < 8 || nbCartesMain > 12){
		System.out.println("Choisissez le nombre de cartes dans la main des joueurs au d�but de chaque manche (entre 8 et 12) :");
		nbCartesMain = sc.nextInt();
		}
		
		int talon = 0;
		//Si il y a 4 joueurs ou moins on choisit le talon de 54 cartes.
		if((nbJoueurs) <= 4 ){
			talon = Constante.NOMBRE_CARTE_TALON_MIN;
		}
		//Sinon on choisit celui de 108 cartes.
		else {
			talon = Constante.NOMBRE_CARTE_TALON_MAX;		
		}
		
		//On choisit le type de partie : Points ou Manches.
		int limite = 0;
		while(limite != 1 && limite != 2){
			System.out.println("Voulez-vous jouer avec une limite en points ou en manches (1 pour points, 2 pour manches)");
			limite = sc.nextInt();		
		}				
		
		//Si on joue en points on choisit le nombre de points limites et on initialise le plateau avec ces param�tres.
		if(limite == 1){
			int limiteP = 0;
			while(limiteP < 100 || limiteP > 1000){
				System.out.println("Saisissez le nombre de points limites (entre 100 et 1000) :");
				limiteP = sc.nextInt();
			}
			
			plateau.initialisation(-1, limiteP, nomJoueurReel, nomJoueurVirtuel, nbCartesMain, talon,10);
			plateau.jouerPartie();
			fenetre.setVisible(true);
		}
		//Sinon on choisit le nombre de manches limites et on initialise le plateau avec ces param�tres.
		else if(limite == 2){
			int limiteM = 0;
			while (limiteM < 2 || limiteM > 10 ) {
				System.out.println("Saisissez le nombre de manches limites (entre 2 et 10) :");
				limiteM = sc.nextInt();
			}
			plateau.initialisation(limiteM, -1, nomJoueurReel, nomJoueurVirtuel, nbCartesMain, talon,10);
			//Lance le thread de la partie qui permet de jouer
			plateau.jouerPartie();
			fenetre.setVisible(true);
		}								
	}

	/**
	 * M�thode de mise � jour lorsque l'observable (Plateau) change d'�tat
	 * @see Plateau
	 */
	@Override
	public void update(Observable o, Object arg) {

		// Si on affiche seulement un message 
		if(arg != null && arg instanceof String) {
			System.out.println(arg);
			String chaine = (String) arg;
			if(chaine.contains("Fin de la partie ! ")) {
				FenetrePrincipale.setEndGame();
				plateau.deleteObserver(this);
			}
		}
		//Sinon si c'est la fin du tour du joueur pr�c�dent alors on passe au joueur suivant en affichant sa main
		else if(arg == null){
			System.out.println(plateau);
			jouerTour();
		}
	}
}


