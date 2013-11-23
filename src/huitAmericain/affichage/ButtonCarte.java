package huitAmericain.affichage;

import huitAmericain.cartes.Carte;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * <b>La classe ButtonCarte</b>
 * <p>
 * 	Cette classe hérite de JButton et utilise les mêmes paramètre que cette dernière 
 *  cependant une Carte est ajouté dans ce composant.
 * </p>
 * @see JButton
 * @author Thibault Jacquemet & Antoine Bléchet
 */
public class ButtonCarte extends JButton {

	/**
	 * Numéro de sérialization
	 */
	private static final long serialVersionUID = 1L;

	/***
	 * La carte qui sera lié au bouton
	 * @see Carte
	 */
	private Carte carte;
	
	/***
	 * Constructeur d'un bouton possédant un carte 
	 * @param carte La carte contenu dans le bouton 
	 * @param icon L'icone du bouton
	 */
	public ButtonCarte(Carte carte, ImageIcon icon) {
		//Appel du constructeur de JButton
		super(icon);
		this.carte = carte;
	}
	
	/**
	 * Retourne la la carte qui est lié au bouton 
	 * @return La carte contenu dans le bouton
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Met à jour la carte qui est contenu dans le bouton
	 * @param carte La carte à mettre à jour
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
}
