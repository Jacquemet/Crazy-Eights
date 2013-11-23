package huitAmericain;

import huitAmericain.cartes.Carte;
import huitAmericain.cartes.CarteNormale;
import huitAmericain.tasDeCartes.Defausse;
import huitAmericain.cartes.cartesSpeciales.*;
import huitAmericain.joueurs.Joueur;

/**
 * <b>R�gleJeu est la classe qui contient toutes les r�gles du jeu de 8 am�ricain.</b>
 * <p>
 * Cette classe conteint toutes les v�rification de carte jouable ou non mais �galement les v�rifications 
 * concernant la fin d'un partie et la fin d'un tour.
 * </p>
 * @author Thibault Jacquemet & Antoine Bl�chet
 */
public class ReglesJeu {

	/**
	 * M�thode qui permet de v�rifier si une carte est jouable ou non
	 * @param carte qui doit �tre jou�
	 * @param plateau plateau sur lequel la carte doit �tre jou� 
	 * 
	 * @return Bool�en si la carte est jouable(true) ou non (false) 
	 */
	public static boolean verifierCarte(Carte carte, Plateau plateau) {
		boolean resultat = false;
		Defausse defausse = plateau.getDefausse();
		Carte carteDefausse = defausse.getCarte(0);
		
		if(carte instanceof CarteNormale) {
			if((plateau.getCouleurActuelle().equals(carte.getCouleur())) || (carteDefausse.getNumero()).equals(carte.getNumero()))
				resultat = true;
		}
		else if(carte instanceof Carte2) {
			if((plateau.getCouleurActuelle().equals(carte.getCouleur())) || (carteDefausse.getNumero()).equals(carte.getNumero()))
				resultat = true;
		}
		else if (carte instanceof Carte7) {
			if((plateau.getCouleurActuelle().equals(carte.getCouleur())) || (carteDefausse.getNumero()).equals(carte.getNumero()))
				resultat = true;
		}
		else if (carte instanceof Carte8) {
			if(!(carteDefausse instanceof CarteAs) && plateau.getJoueurActuel().getMain().getSize() != 1)
				resultat = true;
		}
		else if (carte instanceof Carte10){
			if((plateau.getCouleurActuelle().equals(carte.getCouleur())) || (carteDefausse.getNumero()).equals(carte.getNumero()))
				resultat = true;
		}
		else if (carte instanceof CarteAs) {
			if((plateau.getCouleurActuelle().equals(carte.getCouleur())) || (carteDefausse.getNumero()).equals(carte.getNumero()))
				resultat = true;
		}
		else if (carte instanceof CarteJoker) { 
			if(!(carteDefausse instanceof CarteAs) && plateau.getJoueurActuel().getMain().getSize() != 1)
				resultat = true;
		}
		
		return resultat;
	}
	
	/**
	 * M�thode qui d�termine si la partie est terminer ou non 
	 * @return true si la partie est finie false sinon
	 */
	public static boolean finPartie(Plateau plateau){
		boolean resultat = false;
		for(Joueur j : plateau.getListeJoueurs()){
			if(j.getNombrePoints() >= plateau.getLimitePoints() && plateau.getLimitePoints() != -1)
				resultat = true;
			else if (plateau.getLimitePoints() == -1 && plateau.getNbManche() == 0)
				resultat = true;
		}
		return resultat;
	}
	
	/**
	 * M�thode qui d�termine si la manche en cours est termin� ou non
	 * @return true si la manche est finie et false si non
	 */
	public static boolean finManche(Plateau plateau){
		boolean resultat = false;
		for(Joueur j : plateau.getListeJoueurs()) {
			if(j.getMain().getSize() == 0)
				resultat = true;
		}
		return resultat;
	}
	
}
