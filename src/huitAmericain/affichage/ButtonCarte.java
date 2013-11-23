package huitAmericain.affichage;

import huitAmericain.cartes.Carte;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * <b>La classe ButtonCarte</b>
 * <p>
 * 	Cette classe h�rite de JButton et utilise les m�mes param�tre que cette derni�re 
 *  cependant une Carte est ajout� dans ce composant.
 * </p>
 * @see JButton
 * @author Thibault Jacquemet & Antoine Bl�chet
 */
public class ButtonCarte extends JButton {

	/**
	 * Num�ro de s�rialization
	 */
	private static final long serialVersionUID = 1L;

	/***
	 * La carte qui sera li� au bouton
	 * @see Carte
	 */
	private Carte carte;
	
	/***
	 * Constructeur d'un bouton poss�dant un carte 
	 * @param carte La carte contenu dans le bouton 
	 * @param icon L'icone du bouton
	 */
	public ButtonCarte(Carte carte, ImageIcon icon) {
		//Appel du constructeur de JButton
		super(icon);
		this.carte = carte;
	}
	
	/**
	 * Retourne la la carte qui est li� au bouton 
	 * @return La carte contenu dans le bouton
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Met � jour la carte qui est contenu dans le bouton
	 * @param carte La carte � mettre � jour
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
}
