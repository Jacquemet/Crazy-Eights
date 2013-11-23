package huitAmericain.joueurs.strategie;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.Joueur;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;
import huitAmericain.tasDeCartes.TasDeCartes;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Classe Difficile</b>
 * <p>
 * La classe Difficile permet de d�finir la strat�gie facile des joueurs virtuels.
 * Si un joueur virtuel se voit attribuer cette strat�gie, il jouera selon la fa�on d�finie dans jouerStrat�gie();
 * Le joueur difficile limite la casse en se d�faussant en priorit� des cartes qui ont le plus de points.
 * </p>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * De plus elle impl�mente l'interface strat�gie.
 * </p>
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class Difficile implements Strategie, Serializable{
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8407527725647775863L;

	/**
	 * Red�finition de la m�thode jouerStrat�gie() pour la strat�gie difficile.
	 * <p>
	 * Avec cette strat�gie, le joueur virtuel regarde parmi ses cartes jouables
	 * la carte qui vaut le plus de points et s'en d�fausse.
	 * Si il joue un 8 ou un joker, il parcourt sa main et classe toutes les cartes par couleur.
	 * La nouvelle couleur sera la couleur la plus pr�sente dans sa main.
	 * Si il ne peut pas jouer, alors il pioche.
	 * Il annonce carte et non carte.
	 * </p>
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau) {
		String res = "";
		
		//On parcours tous les joueurs si un joueur n'a pas annonc� carte et qu'il lui reste 1 carte on le fait piocher.
		for(Joueur j : plateau.getListeJoueurs()) {
			if(!j.isAnnoncerCarte() && j.getMain().getSize() == 1 && ! j.equals(plateau.getJoueurActuel())) {
				j.piocher(2, plateau.getTalon());
				res += plateau.getJoueurActuel().getNom() + " a fait piocher 2 cartes � "+j.getNom()+". \n";
			}
		}
		
		//Si le joueur ne peut pas jouer de cartes il pioche
		if(cartesJouable.isEmpty()) {
			res = plateau.getJoueurActuel().piocher(1, plateau.getTalon());
		} 
		
		//Sinon on le fait jouer une stratégie qui consiste à se défausser des cartes qui
		//valent le plus de points
		else {
			
			//Le joueur annonce carte lorsqu'il lui reste plus qu'une carte
			if(plateau.getJoueurActuel().getMain().getSize() == 2) {
				plateau.getJoueurActuel().setAnnoncerCarte(true);
				res += plateau.getJoueurActuel().getNom() + " a annonc� carte. \n";
			}
			
			//Si la carte jouable qui vaut le plus de points est un Joker ou un 8...
			if((cartesJouable.get(getPointMax(cartesJouable)) instanceof CarteJoker)
				|| (cartesJouable.get(getPointMax(cartesJouable)) instanceof Carte8)) {
				//On r�cup�re la couleur que le joueur poss�de le plus.
				switch (TasDeCartes.getCouleurMax(plateau.getJoueurActuel().getMain().getJeu())) {
				case 0:
					plateau.setCouleurActuelle(Constante.CARREAU);
					break;
				case 1:
					plateau.setCouleurActuelle(Constante.COEUR);
					break;
				case 2:
					plateau.setCouleurActuelle(Constante.PIQUE);
					break;
				case 3:
					plateau.setCouleurActuelle(Constante.TREFLE);
					break;
				}					
			}
			
			res += plateau.getJoueurActuel().jouerCarte(cartesJouable.get(getPointMax(cartesJouable)), plateau);
		}
		
		return res;
	}
	/**
	 * R�cup�re la position de la carte qui vaut le plus de points dans une liste donn�e.
	 * @param liste
	 * La liste que l'on veut parcourir pour trouver la carte valant le plus de points
	 * @return la position dans la liste de la carte valant le plus de points
	 */
	public static int getPointMax(List<Carte> liste) {
		int [] Points = new int [liste.size()];
		int PointMax = 0;
		int indListe = 0;
		//On parcourt la liste des cartes jouables pour lister les points des cartes dans un tableau.
		for (Carte carte : liste){
			Points[indListe] = carte.getPoints();
			indListe++;
		}
		
		//Le tableau cr�� est parcouru pour trouver l'indice contenant la valeur de points la plus �lev�e.
		for(int i = 0; i < Points.length; i++){
			if(Points[i] > Points[PointMax]){
				PointMax = i;
			}
		}
		
		return PointMax;
	}	
	
}
