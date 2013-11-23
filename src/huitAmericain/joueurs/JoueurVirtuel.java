package huitAmericain.joueurs;

import java.util.LinkedList;
import java.util.List;

import huitAmericain.Plateau;
import huitAmericain.ReglesJeu;
import huitAmericain.cartes.Carte;
import huitAmericain.joueurs.strategie.*;

/**
 * <b>Classe JoueurVirtuel</b>
 * <p>
 * La classe JoueurVirtuel représente l'ensemble des joueurs virtuels de la partie.
 * Elle hérite de la classe joueur
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
 * @see Joueur
 * @author Thibault Jacquemet & Antoine Bléchet
 * @version 1.0
 * 
 */
public class JoueurVirtuel extends Joueur{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5146249969273293218L;
	/**
	 * Niveau qui déterminera la stratégie adoptée par le joueur virtuel.
	 */
	private int niveau;
	/**
	 * Stratégie du joueur virtuel. Le niveau choisi détermine une stratégie (facile, moyen ou difficile).
	 * Le joueur joue différemment en fonction de la stratégie choisie.
	 */
	private Strategie strat;
	
	/**
	 * Constructeur du joueur virtuel en saisissant son nom et son niveau.
	 * @param nom
	 * Le nom du joueur virtuel
	 * @param niveau
	 * Le niveau qui déterminera la stratégie du joueur virtuel
	 * @see Stratégie
	 * @see Facile
	 * @see Moyen
	 * @see Difficile
	 */
	public JoueurVirtuel(String nom, int niveau){
		super(nom);
		this.niveau = niveau;
		switch(niveau) {
			case 1 :
				this.strat = new Facile();
				break;
			case 2 :
				this.strat = new Moyen();
				break;
			case 3 :
				this.strat = new Difficile();
				break;
		}
	}
	
	/**
	 * Récupère le niveau du joueur.
	 * @return le niveau du joueur virtuel
	 */
	public int getNiveau() {
		return niveau;
	}
	/**
	 * Met à jour le niveau du joueur virtuel.
	 * @param niveau
	 * Le nouveau niveau du joueur virtuel
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	/**
	 * Applique la stratégie choisie pour le joueur virtuel.
	 * @param plateau
	 * L'instance du plateau de la partie
	 * @return
	 */
	public String appliquerStrategie(Plateau plateau){
		return strat.jouerStrategie(cartesJouable(plateau), plateau);
	}
	/**
	 * Récupère la stratégie du joueur virtuel.
	 * @return la stratégie du joueur virtuel
	 */
	public Strategie getStrat() {
		return strat;
	}
	/**
	 * Met à jour la stratégie du joueur virtuel.
	 * @param strat
	 * La nouvelle stratégie du joueur virtuel
	 */
	public void setStrat(Strategie strat) {
		this.strat = strat;
	}
	
	/**
	 * Permet de récupérer la liste des cartes jouables du joueur virtuel.
	 * Quand on lui fait jouer une stratégie, ceci permet de ne pas avoir à parcourir la main entière
	 * mais seulement la liste des cartes jouables.
	 * @param plateau
	 * @return la liste des cartes jouables du joueur virtuel
	 */
	public List<Carte> cartesJouable(Plateau plateau) {
		List<Carte> resultat = new LinkedList<Carte>();
		for (Carte carte : this.getMain().getJeu()) {
			if(ReglesJeu.verifierCarte(carte, plateau))
				resultat.add(carte);
		}
		return resultat;
	}
	
}
