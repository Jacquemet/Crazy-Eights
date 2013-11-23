package huitAmericain.affichage;

import huitAmericain.Constante;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <b>Classe PanelCreateJoueur</b>
 * <p>
 * Cette classe permet de créer un panel intermédiaire qui sera utilisé dans la fenetre de paramétrage.
 * Elle contient une combo qui détermine si le joueur est réel ou non, un champ de saisie qui permet de choisir 
 * le nom du joueur et elle contient une autre  comboBox qui sert à choisir le niveau du joueur virtuel 
 * ce dernier composant ne sera présent que lorsque le joueur sera virtuel. 
 * </p>
 * @author Thibault Jacquemet & Antoine Bléchet
 *
 */
public class PanelCreateJoueur extends JPanel{


	/**
	 * Numéro de sérialization généré par la machine
	 */
	private static final long serialVersionUID = -8441279473049279029L;
	
	/**
	 * ComboBox qui permet de choisir le type de joueur (soit réel soit virtuel)
	 * @see JComboBox
	 */
	private JComboBox comboTypeJoueur;
	
	/**
	 * JTextfield qui permet de saisir le nom d'un joueur.<br>
	 * Ce composant n'est modifiable que lorsque le joueur est réel.
	 * @see JTextField
	 */
	private JTextField fieldNom;
	
	/**
	 * JComboBox qui permet de choisir le niveau d'un joueur virtuel.<br>
	 * Ce composant n'est affiché que lorsqu'un joueur virtuel est sélectionné sur le composant comboTypeJoueur.
	 * @see JComboBox
	 */
	private JComboBox comboNiveau;
	
	/**
	 * Entier qui nous permet de savoir quel doit être le numéro du joueur lors de la création des joueurs.
	 * Car les joueurs ne doivent pas posséder le même nom.
	 */
	private int numeroJoueur;
	
	/**
	 * Création du panel qui permet d'afficher la création d'un joueur.
	 * @param numJoueur le numéro du joueur à créer (de 1 à 8).
	 */
	public PanelCreateJoueur(int numJoueur) {
		
		this.numeroJoueur = numJoueur;
		
		//Création du combo 
		comboTypeJoueur = new JComboBox();
		//Ajouts des items
		comboTypeJoueur.addItem("Joueur Virtuel");
		comboTypeJoueur.addItem("Joueur Réel");
		//Ajout d'un toolTipText
		comboTypeJoueur.setToolTipText("Type de joueur");
		comboTypeJoueur.setPreferredSize(new Dimension(150,20));
		//Sélection de l'item par défault
		comboTypeJoueur.setSelectedItem("Joueur Virtuel");
		
		// Création du champ de texte  
		fieldNom = new JTextField(Constante.NOM_JOUEURVIRTUEL_DEFAUT + " " + numeroJoueur);
		fieldNom.setToolTipText("Nom du joueur");
		fieldNom.setEditable(false);
		fieldNom.setPreferredSize(new Dimension(100,20));
		
		//création de la combo qui permet de choisir le niveau
		comboNiveau = new JComboBox();
		comboNiveau.addItem("Facile");
		comboNiveau.addItem("Moyen");
		comboNiveau.addItem("Difficile");
		comboNiveau.setToolTipText("Niveau du joueur");
		comboNiveau.setPreferredSize(new Dimension(100,20));

		//Action lors du changement des valeurs su comboBox
		comboTypeJoueur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Si la valeur sélectionné est joueur Virtuel
				if(comboTypeJoueur.getSelectedItem().equals("Joueur Virtuel")) {
					//Change la valeur de JTexteField
					fieldNom.setText(Constante.NOM_JOUEURVIRTUEL_DEFAUT + " " + numeroJoueur);
					//Bloque la modification de ce dernier
					fieldNom.setEditable(false);
					//Affiche la comboBox lié au niveau
					comboNiveau.setVisible(true);
					//Met à jour la taille des composants
					comboTypeJoueur.setPreferredSize(new Dimension(150,20));
					fieldNom.setPreferredSize(new Dimension(100,20));
				}
				//Sinon si c'est un Joueur réel
				else {
					//Cache la combo lié au niveau car le joueur est réel
					comboNiveau.setVisible(false);
					fieldNom.setText(Constante.NOM_JOUEURREEL_DEFAUT + " " + numeroJoueur);
					//Active la modification
					fieldNom.setEditable(true);
					//Met à jour la taille des composants 
					comboTypeJoueur.setPreferredSize(new Dimension(175,20));
					fieldNom.setPreferredSize(new Dimension(175,20));
				}
			}
		});
		
		//Ajout des composants dans le JPanel
		this.add(comboTypeJoueur);
		this.add(fieldNom);
		this.add(comboNiveau);
		
		this.setLayout(new FlowLayout());
		
	}

	/**
	 * Retourne la comboBox qui permet de choisir le type d'un joueur
	 * @return comboTypeJoueur
	 */
	public JComboBox getComboTypeJoueur() {
		return comboTypeJoueur;
	}

	/**
	 * Retourne la JTextField qui permet de choisir le nom d'un joueur réel
	 * @return JTextField 
	 */
	public JTextField getFieldNom() {
		return fieldNom;
	}

	/**
	 * Retourne la comboBox qui permet de choisir le niveau d'un joueur virtuel
	 * @return ComboBox 
	 */
	public JComboBox getComboNiveau() {
		return comboNiveau;
	}

	/**
	 * Retourne le numero du joueur
	 * @return Un entier qui correspond au numero d'un joueur
	 */
	public int getNumeroJoueur() {
		return numeroJoueur;
	}

	/**
	 * Met à jour le numéro d'un joueur
	 * @param numeroJoueur Le numéro de départ du joueur sur le plateau
	 */
	public void setNumeroJoueur(int numeroJoueur) {
		this.numeroJoueur = numeroJoueur;
	}
	
}
