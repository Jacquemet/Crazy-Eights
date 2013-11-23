package huitAmericain.affichage;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * <b>Classe Filtre </b>
 * <p>
 * 	Cette classe h�rite de fileFilter et permet de d�finir les extension qui seront utilis� dans le filechooser 
 * 	utilis� pour charger et sauvegarder une partie.
 * </p>
 * @see FileFilter
 * @author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class Filtre extends FileFilter {

	/**
	 * Chaine de caract�re qui d�fini l'exetension des fichiers � sauvegarder
	 */
	private String extension = ".lo02";
	
	/**
	 * Chaine de caract�re qui d�fini la description des fichiers ayant l'extension associ� 
	 */
	private String description = "Fichier Jeu de 8 Am�ricain";
	
	/**
	 * Constructeur d'un filtre perosnalis�
	 * @param ext extension du filtre
	 * @param descrip la description du filtre
	 */
	public Filtre(String ext, String descrip){
		this.extension = ext;
		this.description = descrip;
	}
	
	/**
	 * M�thode qui v�rifie que le fichier pris en param�tre poss�de l'extension voulu
	 * @param file le fichier � v�rifier
	 * @return vrai si le fichier est valide et false si non
	 */
	public boolean accept(File file){
		return (file.isDirectory() || file.getName().endsWith(this.extension));
	}
	
	/**
	 * Retourne la description du filtre 
	 * @return la description du filtre 
	 */
	public String getDescription() {
		return this.extension + " - " + this.description;
	}	
}
