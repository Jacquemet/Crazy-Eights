package huitAmericain.joueurs.strategie;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.JoueurReel;
import huitAmericain.joueurs.JoueurVirtuel;
import huitAmericain.tasDeCartes.TasDeCartes;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <b>Classe Moyen</b>
 * <p>
 * La classe Moyen permet de d�finir la strat�gie moyen des joueurs virtuels.
 * Si un joueur virtuel se voit attribuer cette strat�gie, il jouera selon la fa�on d�finie dans jouerStrat�gie();
 * Le joueur moyen essaie de garder un �quilibre dans ses couleurs.
 * </p>
 * <p>
 * Cette classe est s�rializable pour permettre sa sauvegarde et son chargement.
 * Tous les attributs qu'elle contient sont �galement s�rializables.
 * De plus elle impl�mente l'interface strat�gie.
 * </p>
 * @see Moyen#jouerStrategie(List, Plateau)
 * @see JoueurReel
 * @see JoueurVirtuel
 * @author Thibault Jacquemet & Antoine Bl�chet
 * @version 1.0
 *
 */
public class Moyen implements Strategie, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -6296970633033400886L;

	/**
	 * Red�finition de la m�thode jouerStrat�gie() pour la strat�gie moyen.
	 * <p>
	 * Avec cette strat�gie, le joueur virtuel classe ses cartes jouables par couleur.
	 * Il ne regarde ensuite que les cartes de la couleur la plus pr�sente, les m�lange
	 * et joue la premi�re carte, qu'elle soit normale ou sp�ciale.
	 * Si il joue un 8 ou un joker, il parcourt sa main et classe par couleur cette fois-ci toutes les cartes.
	 * La nouvelle couleur sera la couleur la plus pr�sente dans sa main.
	 * Si il ne peut pas jouer, alors il pioche.
	 * Il annonce carte mais pas non carte.
	 * </p>
	 * @param cartesJouable
	 * La liste des cartes jouables du joueur virtuel
	 * @param plateau
	 * L'instance unique du plateau de la partie
	 */
	public String jouerStrategie(List<Carte> cartesJouable, Plateau plateau) {
		String res = "";
		//Si le joueur ne peut pas jouer de cartes il pioche
		if(cartesJouable.isEmpty()) {
			plateau.getJoueurActuel().piocher(1, plateau.getTalon());
		} 
		//Sinon on le fait jouer une strat�gie qui consiste � garder un �quilibre des couleurs
		else {
			
			//Le joueur annonce carte lorsqu'il lui reste plus qu'une carte
			if(plateau.getJoueurActuel().getMain().getSize() == 2) {
				plateau.getJoueurActuel().setAnnoncerCarte(true);
				res += plateau.getJoueurActuel().getNom() + " a annonc� carte. \n";
			}
			
			List<Carte> listeCarteCouleur = new LinkedList<Carte>();
			//On r�cup�re la couleur la plus pr�sente dans les cartes jouables.
			switch (TasDeCartes.getCouleurMax(cartesJouable)) {
				/*Si le nombre de carte maximum est � l'indice 0 du tableau, �a signifie que 
				 *les cartes les plus nombreuses sont des carreaux.
				 *On cr�� une nouvelle liste contenant uniquement les cartes carreaux pour que le
				 *joueur virtuel puisse jouer al�atoirement � partir de cette liste.
				*/
				case 0 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.CARREAU)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est � l'indice 1 du tableau, �a signifie que 
				 *les cartes les plus nombreuses sont des coeurs.
				 *On cr�� une nouvelle liste contenant uniquement les cartes coeurs pour que le
				 *joueur virtuel puisse jouer al�atoirement � partir de cette liste. */
				case 1 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.COEUR)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est � l'indice 2 du tableau, �a signifie que 
				 *les cartes les plus nombreuses sont des piques.
				 *On cr�� une nouvelle liste contenant uniquement les cartes piques pour que le
				 *joueur virtuel puisse jouer al�atoirement � partir de cette liste. */
				case 2 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.PIQUE)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
				/*Si le nombre de carte maximum est � l'indice 3 du tableau, �a signifie que 
				 *les cartes les plus nombreuses sont des tr�fles.
				 *On cr�� une nouvelle liste contenant uniquement les cartes tr�fles pour que le
				 *joueur virtuel puisse jouer al�atoirement � partir de cette liste. */
				case 3 :
					for(Carte carte : cartesJouable) {
						//Si c'est une carte normale on l'ajoute.
						if(carte.getCouleur().equals(Constante.TREFLE)) {
							listeCarteCouleur.add(carte);
						}
					}
				break;
			}
			
			Collections.shuffle(listeCarteCouleur);
			
			if(listeCarteCouleur.isEmpty()) {
				//Si le joueur ne peut jouer qu'un joker...
				if((cartesJouable.get(0) instanceof CarteJoker)) {
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
				res += plateau.getJoueurActuel().jouerCarte(cartesJouable.get(0), plateau);
			}
			else {
				//Si la premi�re carte est un 8...
				if((listeCarteCouleur.get(0) instanceof Carte8)) {
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
				
				res += plateau.getJoueurActuel().jouerCarte(listeCarteCouleur.get(0), plateau);
			}
		}
		return res;
	}

}


