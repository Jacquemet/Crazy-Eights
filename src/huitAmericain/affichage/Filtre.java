package huitAmericain.affichage;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * <b>Classe Filtre </b>
 * <p>
 * 	Cette classe hérite de fileFilter et permet de définir les extension qui seront utilisé dans le filechooser 
 * 	utilisé pour charger et sauvegarder une partie.
 * </p>
 * @see FileFilter
 * @author Thibault Jacquemet & Antoine Bléchet
 *
 */
public class Filtre extends FileFilter {

	/**
	 * Chaine de caractère qui défini l'exetension des fichiers à sauvegarder
	 */
	private String extension = ".lo02";
	
	/**
	 * Chaine de caractère qui défini la description des fichiers ayant l'extension associé 
	 */
	private String description = "Fichier Jeu de 8 Américain";
	
	/**
	 * Constructeur d'un filtre perosnalisé
	 * @param ext extension du filtre
	 * @param descrip la description du filtre
	 */
	public Filtre(String ext, String descrip){
		this.extension = ext;
		this.description = descrip;
	}
	
	/**
	 * Méthode qui vérifie que le fichier pris en paramètre possède l'extension voulu
	 * @param file le fichier à vérifier
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
