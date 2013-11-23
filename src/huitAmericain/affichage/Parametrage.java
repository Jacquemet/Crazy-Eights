package huitAmericain.affichage;

import huitAmericain.Constante;
import huitAmericain.Plateau;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <b>Classe param�trage</b>
 * <p>
 * Cette classe conprend tous les �l�ments graphiques qui permette de param�trer une partie de 8 am�ricain.
 * On choisi le nombre de joueur, leurs niveau si ils sont virtuels, le nombre de cartes en main, la vitesse des IA et enfin le type de partie.
 * </p>
 * @author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class Parametrage {

	/**
	 * La fenetre qui affiche le param�trage. 
	 */
	private JFrame fenetreParametrage;
	
	/**
	 * Liste de PanelCreateJoueur pour afficher la cr�ation des joueurs
	 */
	private List<PanelCreateJoueur> listePanel;
	
	/**
	 * Panel qui permet de choisir le nombre de joueur
	 */
	private JPanel panelNbJoueur;
	/**
	 * Label qui correspond au nombre de joueurs.
	 * @see Parametrage#panelNbJoueur
	 */
	private JLabel labelNbJoueur;
	/**
	 * Spinner qui permet de choisir le nombre de joueur.
	 * Ce spinner est contenu dans le panel NbJoueur.
	 * @see Parametrage#panelNbJoueur
	 */
	private JSpinner spinnerNbJoueur;
	
	/**
	 * Panel pour choisir comment va se d�rouler une partie.
	 */
	private JPanel panelLimite;
	/**
	 * ComboBox qui permet de choisir le type de partie (limite en points ou limite en manche).
	 * Ce composant est contenu dans le panelLimite.
	 * @see Parametrage#panelLimite
	 */
	private JComboBox comboLimitePartie;
	/**
	 * Spinner qui permet de choisir la limite de points ou de manche. 
	 * Ses valeurs sont compises entre 100 et 1000 pour les nombres de point et entre  2 et 8.
	 * @see Parametrage#panelLimite
	 */
	private JSpinner spinnerLimite;

	/**
	 * Panel qui permet de choisir le nombre de cartes.
	 */
	private JPanel panelNbCartes;
	/**
	 * Label qui est associ� au spinnerNbcartes.
	 * @see Parametrage#spinnerNbCartes
	 */
	private JLabel labelNbCartes;
	/**
	 * Spinner qui permet de choisir le nombre de cartes entre 8 et 12 cartes.
	 * @see	Parametrage#panelNbCartes
	 */
	private JSpinner spinnerNbCartes;
	
	/**
	 * Slider qui permet de choisir la vitesse de jeu des IA.
	 */
	private JSlider vitesseIA;
	/**
	 * Label associ� � la vitesse des IA.
	 */
	private JLabel labelVitesse;
	
	/**
	 * Bouton qui permet de param�trer la partie avec les �l�ments s�lectionn�s. 
	 */
	private JButton buttonSend;
	
	/**
	 * M�thode qui cr�e la fenetre de param�trage, qui cr�e et affiche les diff�rents composant de cette classe.
	 * @param plateau Le plateau � param�trer
	 * @param affichage L'affichage graphique associ� au param�trage
	 * @see Plateau
	 * @see AffichageGraphique
	 */
	public Parametrage(final Plateau plateau, final AffichageGraphique affichage) {
		
		//Cr�ation de la fentre
		fenetreParametrage = new JFrame("Param�trage de la partie");
		//ajout de l'icone
		fenetreParametrage.setIconImage((new ImageIcon(getClass().getResource("/huitAmericain/images/logo.png"))).getImage());
        fenetreParametrage.setSize(450, 450);
        fenetreParametrage.setLocationRelativeTo(null);
        fenetreParametrage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Cr�ation de la liste de panelCreateJoueur
        listePanel = new LinkedList<PanelCreateJoueur>();
        
        //Cr�ation de la comboBox
        comboLimitePartie = new JComboBox();
        comboLimitePartie.addItem("Limite en points");
        comboLimitePartie.addItem("Limite en manches");
        
        //Cr�ation du spinner
        spinnerLimite = new JSpinner();
        spinnerLimite.setModel(new SpinnerNumberModel(100, 100, 1000, 50));
        panelLimite = new JPanel();
        
        //Ajout des composant dans le panel et dans la fenetre
        panelLimite.add(comboLimitePartie);
        panelLimite.add(spinnerLimite);
        fenetreParametrage.add(panelLimite);

        //Ajout de l'�v�nement sur la combo suivant la valeur de la combo on  change les bornes du spinner 
        comboLimitePartie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboLimitePartie.getSelectedItem().equals("Limite en points")) {
					spinnerLimite.setModel(new SpinnerNumberModel(100, 100, 1000, 50));
				}
				else if(comboLimitePartie.getSelectedItem().equals("Limite en manches")) {
					spinnerLimite.setModel(new SpinnerNumberModel(2, 2, 10, 1));
				}
			}
		});
        
        //Cr�ation des composants
        panelNbCartes = new JPanel(new FlowLayout());
        labelNbCartes = new JLabel("Nombre de cartes en main par d�faut : ");
        spinnerNbCartes = new JSpinner(new SpinnerNumberModel(8, 8, 12, 1));
        
        //Ajouts des composant dans le panel et la fenetre
        panelNbCartes.add(labelNbCartes);
        panelNbCartes.add(spinnerNbCartes);
        fenetreParametrage.add(panelNbCartes);
        
        
        //Cr�ation des diff�rnets composant
        labelNbJoueur = new JLabel("Nombre de Joueur");
        spinnerNbJoueur = new JSpinner();
        spinnerNbJoueur.setModel(new SpinnerNumberModel(2, 2, 8, 1));
        //ev�nement sur le spinner suivant la valeur de ce dernier on va afficher plus ou moins de champs pour cr�er un joueur.
        spinnerNbJoueur.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				Integer tmp = (Integer) spinnerNbJoueur.getValue();
				switch(tmp){
					case 2 :
						listePanel.get(2).setVisible(false);
						break;
					case 3 :
						listePanel.get(2).setVisible(true);
						listePanel.get(3).setVisible(false);
						break;
					case 4 :
						listePanel.get(3).setVisible(true);
						listePanel.get(4).setVisible(false);
						break;
					case 5 :
						listePanel.get(4).setVisible(true);
						listePanel.get(5).setVisible(false);
						break;
					case 6 :
						listePanel.get(5).setVisible(true);
						listePanel.get(6).setVisible(false);
						break;
					case 7 :
						listePanel.get(6).setVisible(true);
						listePanel.get(7).setVisible(false);
						break;
					case 8 :
						listePanel.get(7).setVisible(true);
						break;
				}
			}
		});        
        
        //Cr�ation du label 
        labelVitesse = new JLabel("Vitesse de jeu des IA : ");
        //On centre le label 
        labelVitesse.setHorizontalAlignment(JLabel.CENTER);
        labelVitesse.setVerticalAlignment(JLabel.CENTER);
        fenetreParametrage.add(labelVitesse);
        
        //Cr�ation du slider
        vitesseIA = new JSlider();
        //Param�trage du slider
        vitesseIA.setMinimum(10);
        vitesseIA.setMaximum(500);
        vitesseIA.setValue(250);
        fenetreParametrage.add(vitesseIA);
        
        panelNbJoueur = new JPanel(new FlowLayout());
        panelNbJoueur.add(labelNbJoueur);
        panelNbJoueur.add(spinnerNbJoueur);
        fenetreParametrage.add(panelNbJoueur);   
        
        //Ajout des panel de creation de Joueur
        listePanel.add(new PanelCreateJoueur(1));
        listePanel.add(new PanelCreateJoueur(2));
        listePanel.add(new PanelCreateJoueur(3));
        listePanel.add(new PanelCreateJoueur(4));
        listePanel.add(new PanelCreateJoueur(5));
        listePanel.add(new PanelCreateJoueur(6));
        listePanel.add(new PanelCreateJoueur(7));
        listePanel.add(new PanelCreateJoueur(8));

        int defaultPanel = 0;
        
        for(PanelCreateJoueur panel : listePanel) {
        	fenetreParametrage.add(panel);
        	if(defaultPanel < 2 )
        		defaultPanel++;
        	else
        		panel.setVisible(false);
        }
        
        //creation du bouton d�marer une partie
        buttonSend = new JButton();
        buttonSend.setText("D�marer");
        fenetreParametrage.add(buttonSend);
        
        //Ajout de l'action sur le bouton d�marer la partie
        buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//On cr�e les listes contenat les informations des diff�rents joueurs
				int nombreDeCarte = 0;
				List<String> nomJoueurReel = new LinkedList<String>();
				HashMap<String, Integer> nomJoueurVirtuel = new HashMap<String, Integer>();

				//Pour chaque panel de joueur (c'est � dire 8) 
				for(PanelCreateJoueur panel : listePanel) {
					//Si le panel est visible donc si le joueur est ajout�
					if(panel.isVisible()) {
						//Si c'est un joueur r�el 
						if(panel.getComboTypeJoueur().getSelectedItem().equals("Joueur R�el")) {
							nomJoueurReel.add(panel.getFieldNom().getText());
						}
						//Sinon si c'est un joueur virtuel
						else {
							int niveau = 0;
							if(panel.getComboNiveau().getSelectedItem().equals("Facile")) {
								niveau = 1;
							} else if (panel.getComboNiveau().getSelectedItem().equals("Moyen")) {
								niveau = 2;
							} else {
								niveau = 3;
							}
							nomJoueurVirtuel.put(panel.getFieldNom().getText(),niveau);
						}
					}
				}
				
				//Si il ya plus de 4 joueurs on joue avec 2 jeux de cartes
				if((nomJoueurReel.size()+nomJoueurVirtuel.size()) >= 5)
					nombreDeCarte = Constante.NOMBRE_CARTE_TALON_MAX;
				//Sinon on ne prend qu'un seul jeu de cartes
				else 
					nombreDeCarte = Constante.NOMBRE_CARTE_TALON_MIN;
				
				//si la limite est en points 
				if(comboLimitePartie.getSelectedItem().equals("Limite en points")) {
					plateau.initialisation(-1, (Integer)spinnerLimite.getValue(), nomJoueurReel, nomJoueurVirtuel, (Integer) spinnerNbCartes.getValue(), nombreDeCarte,vitesseIA.getValue());
				}
				//sinon si elle est en manche
				else if (comboLimitePartie.getSelectedItem().equals("Limite en manches")) {
					plateau.initialisation((Integer)spinnerLimite.getValue(), -1, nomJoueurReel, nomJoueurVirtuel, (Integer)spinnerNbCartes.getValue(), nombreDeCarte,vitesseIA.getValue());
				}
				
				fenetreParametrage.setVisible(false);
				//affichage.DebuterPartieGraphique();
				plateau.jouerPartie();
			}
		});
        
        fenetreParametrage.setLayout(new GridLayout(0, 1));
        fenetreParametrage.setVisible(true);
	}
	
}
