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
 * Cette classe permet de cr�er un panel interm�diaire qui sera utilis� dans la fenetre de param�trage.
 * Elle contient une combo qui d�termine si le joueur est r�el ou non, un champ de saisie qui permet de choisir 
 * le nom du joueur et elle contient une autre  comboBox qui sert � choisir le niveau du joueur virtuel 
 * ce dernier composant ne sera pr�sent que lorsque le joueur sera virtuel. 
 * </p>
 * @author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class PanelCreateJoueur extends JPanel{


	/**
	 * Num�ro de s�rialization g�n�r� par la machine
	 */
	private static final long serialVersionUID = -8441279473049279029L;
	
	/**
	 * ComboBox qui permet de choisir le type de joueur (soit r�el soit virtuel)
	 * @see JComboBox
	 */
	private JComboBox comboTypeJoueur;
	
	/**
	 * JTextfield qui permet de saisir le nom d'un joueur.<br>
	 * Ce composant n'est modifiable que lorsque le joueur est r�el.
	 * @see JTextField
	 */
	private JTextField fieldNom;
	
	/**
	 * JComboBox qui permet de choisir le niveau d'un joueur virtuel.<br>
	 * Ce composant n'est affich� que lorsqu'un joueur virtuel est s�lectionn� sur le composant comboTypeJoueur.
	 * @see JComboBox
	 */
	private JComboBox comboNiveau;
	
	/**
	 * Entier qui nous permet de savoir quel doit �tre le num�ro du joueur lors de la cr�ation des joueurs.
	 * Car les joueurs ne doivent pas poss�der le m�me nom.
	 */
	private int numeroJoueur;
	
	/**
	 * Cr�ation du panel qui permet d'afficher la cr�ation d'un joueur.
	 * @param numJoueur le num�ro du joueur � cr�er (de 1 � 8).
	 */
	public PanelCreateJoueur(int numJoueur) {
		
		this.numeroJoueur = numJoueur;
		
		//Cr�ation du combo 
		comboTypeJoueur = new JComboBox();
		//Ajouts des items
		comboTypeJoueur.addItem("Joueur Virtuel");
		comboTypeJoueur.addItem("Joueur R�el");
		//Ajout d'un toolTipText
		comboTypeJoueur.setToolTipText("Type de joueur");
		comboTypeJoueur.setPreferredSize(new Dimension(150,20));
		//S�lection de l'item par d�fault
		comboTypeJoueur.setSelectedItem("Joueur Virtuel");
		
		// Cr�ation du champ de texte  
		fieldNom = new JTextField(Constante.NOM_JOUEURVIRTUEL_DEFAUT + " " + numeroJoueur);
		fieldNom.setToolTipText("Nom du joueur");
		fieldNom.setEditable(false);
		fieldNom.setPreferredSize(new Dimension(100,20));
		
		//cr�ation de la combo qui permet de choisir le niveau
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
				//Si la valeur s�lectionn� est joueur Virtuel
				if(comboTypeJoueur.getSelectedItem().equals("Joueur Virtuel")) {
					//Change la valeur de JTexteField
					fieldNom.setText(Constante.NOM_JOUEURVIRTUEL_DEFAUT + " " + numeroJoueur);
					//Bloque la modification de ce dernier
					fieldNom.setEditable(false);
					//Affiche la comboBox li� au niveau
					comboNiveau.setVisible(true);
					//Met � jour la taille des composants
					comboTypeJoueur.setPreferredSize(new Dimension(150,20));
					fieldNom.setPreferredSize(new Dimension(100,20));
				}
				//Sinon si c'est un Joueur r�el
				else {
					//Cache la combo li� au niveau car le joueur est r�el
					comboNiveau.setVisible(false);
					fieldNom.setText(Constante.NOM_JOUEURREEL_DEFAUT + " " + numeroJoueur);
					//Active la modification
					fieldNom.setEditable(true);
					//Met � jour la taille des composants 
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
	 * Retourne la JTextField qui permet de choisir le nom d'un joueur r�el
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
	 * Met � jour le num�ro d'un joueur
	 * @param numeroJoueur Le num�ro de d�part du joueur sur le plateau
	 */
	public void setNumeroJoueur(int numeroJoueur) {
		this.numeroJoueur = numeroJoueur;
	}
	
}
