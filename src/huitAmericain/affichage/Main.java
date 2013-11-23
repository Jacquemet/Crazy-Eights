package huitAmericain.affichage;

import javax.swing.SwingUtilities;

/**
 *	Main est la classe qui permet à l'application de se lancer. 
 *	@see FenetrePrincipale 
 * 	@author Thibault Jacquemet & Antoine Bléchet
 *
 */
public class Main {
	
	/**
	 * Méthode main qui démarre une partie
	 * @param args
	 */
	public static void main (String [] args) {

		SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new FenetrePrincipale();
            }
        });
	}
	
}
