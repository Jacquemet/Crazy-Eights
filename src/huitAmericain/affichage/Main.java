package huitAmericain.affichage;

import javax.swing.SwingUtilities;

/**
 *	Main est la classe qui permet � l'application de se lancer. 
 *	@see FenetrePrincipale 
 * 	@author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class Main {
	
	/**
	 * M�thode main qui d�marre une partie
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
