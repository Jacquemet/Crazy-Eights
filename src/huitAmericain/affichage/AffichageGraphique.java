package huitAmericain.affichage;

import huitAmericain.Constante;
import huitAmericain.Plateau;
import huitAmericain.ReglesJeu;
import huitAmericain.cartes.Carte;
import huitAmericain.cartes.cartesSpeciales.Carte8;
import huitAmericain.cartes.cartesSpeciales.CarteJoker;
import huitAmericain.joueurs.Joueur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
  * <b>Classe Affichage Console</b>
 * <p>
 * Classe qui permet de jouer une partie avec des composants graphique.<br>
 * On y retrouve tout le paramétrage d'une partie et les même éléments que lors d'une partie en mode console.
 * </p>
 * <p> 
 * Cette classe implémente l'interface Observer pour permettre l'utilisation du pattern MVC.
 * </p> 
 * @see Observer
 * @see Runnable
 * @author Thibault Jacquemet & Antoine Bléchet
 *
 */
public class AffichageGraphique implements Observer, Runnable {
	
	/**
	 *  La fenetre principale qui va permettre de sauvegarder la partie graphique ou bien de redémarer une partie en mode graphique.
	 */
	private JFrame fenetre;
	/**
	 * Le plateau qui est utilisé pour jouer la partie console
	 */
	private Plateau plateau;
	
	/**
	 * Panel Principal qui contenir tous les composants lié au jeu de 8 américain
	 */
	private JPanel panelPrincipal;
	/**
	 * Panel qui va se trouver au Nord de l'affichage qui va contenir la liste des joueurs avec leurs nombre de points associé
	 */
	private JPanel panelSuperieur;
	/**
	 * Panel de gauche qui va contenir les bouton annoncer carte et contre carte et les information sur la partie (couleur, sens de rotation, ...)
	 */
	private JPanel panelGauche;
	/**
	 * Panel central qui va contenir le talon, la defausse, et la main du joueur actuel
	 */
	private JPanel panelCenter;
	/**
	 * Panel qui va contenir les informations de la partie
	 */
	private JPanel panelInfoPartie;
	/**
	 * Panel qui va contenir les deux boutons qui vont permettre d'annoncer carte et contre carte 
	 */
	private JPanel panelAnnoncerCarte;
	/**
	 * Panel qui va contenir la pioche et la defausse
	 */
	private JPanel panelPiocheDefausse;
	
	/**
	 * Label qui va montrer le nom du joueur qui doit jouer le tour
	 */
	private JLabel nomJoueur;
	/**
	 * Label associé au logo de la couleur à jouer
	 * @see AffichageGraphique#labelLogoCouleur
	 */
	private JLabel labelCouleur;
	/**
	 * Logo de la couleur à jouer
	 */
	private JLabel labelLogoCouleur;
	/**
	 * Label associé au logo du sens de la partie
	 * @see AffichageGraphique#labelLogoSens
	 */
	private JLabel labelSens;
	/**
	 * Logo du sens de la partie
	 */
	private JLabel labelLogoSens;
	
	/**
	 * JButton qui va afficher la main du joueur dans une nouvelle fenetre
	 */
	private JButton buttonMain;
	/**
	 * JButton qui va représenter la Pioche
	 */
	private JButton buttonPioche;
	/**
	 * JButton qui va représenter la Défausse
	 */
	private JButton buttonDefausse;
	/**
	 * JTextArea qui va contenir toutes les informations de la partie en cours (toutes les actions des joueurs)
	 */
	private JTextArea infoPartie;
	
	/**
	 * JButton qui va permettre d'annoncer Carte
	 */
	private JButton buttonAnnoncerCarte;
	/**
	 * JButton qui va permettre d'annoncer contre carte
	 */
	private JButton buttonAnnoncerNonCarte;
	
	/**
	 * JFrame qui va permettre à un joueur réel de choisir une nouvelle couleur.
	 */
	private JFrame fenetreChoisirCouleur;
	/**
	 * Bouton qui va permettrede chosir la couleur Carreau
	 * @see AffichageGraphique#fenetreChoisirCouleur
	 */
	private JButton choixCarreau;
	/**
	 * Bouton qui va permettrede chosir la couleur Coeur
	 * @see AffichageGraphique#fenetreChoisirCouleur
	 */
	private JButton choixCoeur;
	/**
	 * Bouton qui va permettrede chosir la couleur Pique
	 * @see AffichageGraphique#fenetreChoisirCouleur
	 */
	private JButton choixPique;
	/**
	 * Bouton qui va permettrede chosir la couleur Trèfle
	 * @see AffichageGraphique#fenetreChoisirCouleur
	 */
	private JButton choixTrefle;
	
	/**
	 * JFrame qui va afficher les cartes que le joueur actuel possède
	 */
	private JFrame fenetreMain;
	/**
	 * Panel qui va afficher toutes les cartes d'un joueur
	 */
	private JPanel panelMainJoueur;
	
	/**
	 * ScrollPane qui va être associé au JTextArea
	 * @see AffichageGraphique#infoPartie
	 */
	private JScrollPane scrollPane;

	/**
	 * Booléen qui va permettre de savoir si un joueur à fini de jouer son ou non 
	 */
	private boolean finTour = false;
	/**
	 * Thread qui permet de joueur une partie graphique
	 */
	private Thread threadJouer;
	
	/**
	 * Constructeur de l'affichage graphique 
	 * @param fenetre La fenetre qui va servir à afficher les composants
	 * @param plateau Le plateau qui est associé à la partie en cours
	 */
	public AffichageGraphique (JFrame fenetre, Plateau plateau) {
		//Si c'est le premier chargement
		if(FenetrePrincipale.isFirstLoad()) {
			this.fenetre = fenetre;
			this.plateau = plateau;
			//Ajout de l'observer
			this.plateau.addObserver(this);
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(new BorderLayout());
		} 
	}
	
	/**
	 * Retourne le JTextArea qui va afficher les information de la partie
	 * @return JTextArea
	 */
	public JTextArea getInfoPartie() {
		return infoPartie;
	}
	
	/**
	 * Retourne les information sur l'etat du tour du joueur actuel.
	 * @return Booléen true si le tour est terminé false si non.
	 */
	public boolean isFinTour() {
		return finTour;
	}

	/**
	 * Met à jour l'état du tour du joueur actuel
	 * @param finTour  L'état du tour en cours 
	 */
	public void setFinTour(boolean finTour) {
		this.finTour = finTour;
	}
	
	/**
	 * Méthode qui permet de commencer une partie en mode graphique.
	 * Cette dernière va initialiser tous les composants graphique du plateau
	 */
	public void DebuterPartieGraphique() {
		//Création de la pioche
		buttonPioche = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/cartes/recto.png")));
		//Permet de ne dessiner que l'icone du bouton
		buttonPioche.setFocusPainted(false);
		buttonPioche.setOpaque(false);
		buttonPioche.setBorder(null);

		buttonDefausse =  new JButton();
		//Permet de ne dessiner que l'icone du bouton
		buttonDefausse.setFocusPainted(false);
		buttonDefausse.setOpaque(false);
		buttonDefausse.setBorder(null);
		
		buttonMain = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/main.png")));
		//Permet de ne dessiner que l'icone du bouton
		buttonMain.setFocusPainted(false);
		buttonMain.setOpaque(false);
		buttonMain.setBorder(null);
		buttonMain.setBackground(null);
		
		//Centre les boutons
		buttonMain.setHorizontalAlignment(JButton.CENTER);
		buttonMain.setVerticalAlignment(JButton.CENTER);
		
		buttonDefausse.setHorizontalAlignment(JButton.CENTER);
		buttonDefausse.setVerticalAlignment(JButton.CENTER);
		
		buttonPioche.setHorizontalAlignment(JButton.CENTER);
		buttonPioche.setVerticalAlignment(JButton.CENTER);
		
		//Ajout de l'évènement lors du clic (Affichage des cartes dans une fenetre)
		buttonMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AffichageMain();
			}
		});
		//évènement sur le bouton pioche
		buttonPioche.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent arg0) {
				//Lorsque le joueur pioche on met à jour l'info Partie
				infoPartie.setText(infoPartie.getText() + "\n" + plateau.getJoueurActuel().piocher(1, plateau.getTalon()));
				//On met fin au thread graphique et par la même occasion au tour du joueur
				setFinTour(false);
				plateau.setFintour(true);
				//On cache la main du joueur (forcément réel) pour éviter qu'un  autre joueur réel puisse voir cette main
				fenetreMain.setVisible(false);
			}
		});
		
		panelSuperieur = new JPanel();
		panelSuperieur.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		panelInfoPartie = new JPanel(new GridLayout(5, 0));
		panelInfoPartie.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		/**** Création des labels puis centrage de ces derniers ****/
		nomJoueur = new JLabel();
		nomJoueur.setHorizontalAlignment(JLabel.CENTER);
		nomJoueur.setVerticalAlignment(JLabel.CENTER);
		
		labelSens = new JLabel("Sens de rotation : ");
		labelSens.setHorizontalAlignment(JLabel.CENTER);
		labelSens.setVerticalAlignment(JLabel.CENTER);
		
		labelLogoSens = new JLabel();
		labelLogoSens.setHorizontalAlignment(JLabel.CENTER);
		labelLogoSens.setVerticalAlignment(JLabel.CENTER);
		
		labelCouleur = new JLabel("Couleur actuelle : ");
        labelCouleur.setHorizontalAlignment(JLabel.CENTER);
        labelCouleur.setVerticalAlignment(JLabel.CENTER);
        
		labelLogoCouleur = new JLabel();
        labelLogoCouleur.setHorizontalAlignment(JLabel.CENTER);
        labelLogoCouleur.setVerticalAlignment(JLabel.CENTER);
        
        /*** Gestion du Annoncer cart et contre carte ***/
        
        //Création du bouton annoncer carte
		panelAnnoncerCarte = new JPanel(new GridLayout(2, 0));
		buttonAnnoncerCarte = new JButton("Annoncer carte");
		//Ajout de l'évènement
		buttonAnnoncerCarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Si il n'a pas déjà annoncer carte ce tour, sinon on ne fait rien
				if(!plateau.getJoueurActuel().isAnnoncerCarte()) {
					//Annonce carte
					plateau.getJoueurActuel().setAnnoncerCarte(true);
					//Ajoute l'information dans l'infoPartie
					infoPartie.setText(infoPartie.getText()+"\n "+ plateau.getJoueurActuel().getNom() + " a annoncé carte.");
				}
			}
		});
		
		//Création du bouton Annoncer contre carte
		buttonAnnoncerNonCarte = new JButton("Annoncer contre carte");
		//Ajout de l'évènement
		buttonAnnoncerNonCarte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Vous avez fait piocher : "+"\n";
				//On parcourstous les joueurs
				for(Joueur j : plateau.getListeJoueurs()) {
					//Si le joueur n'a pas Annoncé carte et qu'il lui reste une carte
					if(!j.isAnnoncerCarte() && j.getMain().getSize() == 1 && !j.equals(plateau.getJoueurActuel())) {
						//On fait piocher le joueur.
						j.piocher(2, plateau.getTalon());
						message += j.getNom() +  "\n";  
						infoPartie.setText(infoPartie.getText()+"\n "+ plateau.getJoueurActuel().getNom() + " a fait piocher 2 cartes à "+j.getNom()+".");
					}
				}
				//Si aucun joueur ne rentre dans les condition du contre carte on affiche un popup d'erreur
				if(message.equals("Vous avez fait piocher : "+"\n")) {
					message = "Vous ne pouvez pas annoncer contre carte.";
					JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon on affiche la fenetre avec tous les joueurs qui ont du piocher une carte
				else {
					JOptionPane.showMessageDialog(null, message, "Annoncer contre carte", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});

		panelAnnoncerCarte.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		panelGauche =new JPanel(new GridLayout(2, 0));
		
		panelCenter = new JPanel();
		//On dessine le background
		fondEcran();
		
		panelCenter.setLayout(new GridLayout(0, 1));
		panelCenter.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		panelPiocheDefausse = new JPanel();

		infoPartie = new JTextArea("		");
		infoPartie.setEditable(false);
		scrollPane = new JScrollPane(infoPartie);
		
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
	}
	
	/**
	 * Méthode qui va permettre d'ajouter tous les composants initialisé (dans la méthode qui permet de débuter une partie en mode graphique) dans les différent panels.
	 * @see AffichageGraphique#DebuterPartieGraphique()
	 */
	public void ajoutComposant() {
		
        panelInfoPartie.add(nomJoueur);
        panelInfoPartie.add(labelSens);
        panelInfoPartie.add(labelLogoSens);
        panelInfoPartie.add(labelCouleur);
        panelInfoPartie.add(labelLogoCouleur);
		
		panelAnnoncerCarte.add(buttonAnnoncerCarte);
		panelAnnoncerCarte.add(buttonAnnoncerNonCarte);
		panelGauche.add(panelInfoPartie);
		panelGauche.add(panelAnnoncerCarte);
		panelPiocheDefausse.add(buttonPioche);
		panelPiocheDefausse.add(buttonDefausse);
		panelCenter.add(panelPiocheDefausse);
		panelCenter.add(buttonMain);
		panelCenter.validate();	
		
		panelPrincipal.add(panelSuperieur, BorderLayout.NORTH);
		panelPrincipal.add(scrollPane, BorderLayout.EAST);
		panelPrincipal.add(panelGauche, BorderLayout.WEST);
		panelPrincipal.add(panelCenter, BorderLayout.CENTER);
		
		fenetre.add(panelPrincipal);
	}
	
	@Override
	/**
	 * Méthode qui va être appelé lors du changement d'état de l'observable à savoir le plateau
	 */
	public synchronized void update(Observable arg0, Object arg1) {

		//Actualisation des composants
		actualiser();
		
		if(plateau.getDefausse() != null && ! plateau.getDefausse().getJeu().isEmpty()) {
			/**Mise à jour de l'image de la défausse**/
			String path = "/huitAmericain/images/cartes/";
			if(plateau.getDefausse().getCarte(0) instanceof CarteJoker) {
				path += "Joker" ;
			} else {
				path += plateau.getDefausse().getCarte(0).getCouleur()+"/"+plateau.getDefausse().getCarte(0).getNumero();
			}
			path+=".png";
			buttonDefausse.setIcon(new ImageIcon(getClass().getResource(path)));
		}
		 //Si l'objet placé dans le paramètre est une chaine de caractère
		 if(arg1 != null && arg1 instanceof String) {
			 String chaine = (String) arg1;
			 //Si on doit afficher le classement on fait apparaitre un popup
			 if(chaine.contains("Classement")) {
				 JOptionPane.showMessageDialog(null, chaine, "Fin de la manche", JOptionPane.INFORMATION_MESSAGE);
			 } 
			 //Sinon si c'est la fin de la partie on affiche un autre popup avec le classement
			 else if(chaine.contains("Fin de la partie ! ")) {
				 FenetrePrincipale.setEndGame();
				 JOptionPane.showMessageDialog(null, chaine, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
			 } 
			 //Sinon on ajoute les informations dans le JTexteArea infoPartie
			 else {
				 infoPartie.setText(infoPartie.getText() + "\n" + chaine);
			 }
		 } 
		 //Sinon si l'object placé est null
		 else if (arg1 == null) {
			//On démarre le tour d'un joueur (ce cas n'intervient que pour les joueurs réel)
			setFinTour(true);
			threadJouer = new Thread(this, "Thread Graphique");
			threadJouer.start();
			
			//Tant que le tour du joueur n'est pas terminé
			 while(!plateau.isFintour()) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			 //On rafarichi les composants
			fenetre.validate();
			//On notify aux thread que l'état à changé
			this.notifyAll();
		}
	}

	@Override
	/**
	 * Méthode qui va exécuter le thread de l'affichage graphique
	 */
	public synchronized void run() {
		//Tant que le tour du joueur n'est pas fini on ne reprend pas le cours de la partie
		while (isFinTour()) {}
		this.notifyAll();
	}

	/**
	 * Méthode qui va afficher la main du joueur actuel dans une fenetre
	 * @see Main
	 * @see Joueur
	 */
	public void AffichageMain() {
		if(fenetreMain == null || !fenetreMain.isVisible()) {
			
			//Création de la fenetre
			fenetreMain = new JFrame();
			fenetreMain.setTitle(" Main de " + plateau.getJoueurActuel().getNom());
			fenetreMain.setSize(600, 250);
			fenetreMain.setResizable(false);
	        fenetreMain.setLocationRelativeTo(null);
	        fenetreMain.setIconImage((new ImageIcon(getClass().getResource("/huitAmericain/images/logo.png"))).getImage());

	        //Initialisation du panel qui va contenir les cartes
	        panelMainJoueur = new JPanel();
	        
	        JScrollPane scroll = new JScrollPane();

	        //Pour chaque carte contenu dans la main du joueur 
			for(Carte c : plateau.getJoueurActuel().getMain().getJeu()) {
				
				Carte carte = c;
				//on récupère la bonne image
				String path = "/huitAmericain/images/cartes/";
				if(carte instanceof CarteJoker) {
					path += "Joker" ;
				} else {
					path += carte.getCouleur()+"/"+carte.getNumero();
				}
				path+=".png";
				//On crée le bouton
				final ButtonCarte b = new ButtonCarte(c,new ImageIcon(getClass().getResource(path)));
				//On enlève le background et les bordures
				b.setFocusPainted(false);
				b.setOpaque(false);
				b.setBorder(null);
				//On l'ajoute au panel
				panelMainJoueur.add(b);
				
				//Ajout de l'évènement
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//Si la carte est jouable
						if(ReglesJeu.verifierCarte(b.getCarte(), plateau)) {
							//Une fois la carte joué on la cache
							fenetreMain.setVisible(false);
							//On met à jour le JTextArea
							infoPartie.setText(infoPartie.getText() + "\n" + plateau.getJoueurActuel().jouerCarte(b.getCarte(), plateau));
							//Si c'est un joker ou un 8 on affiche la fenetre qui permet de choisir une couleur
							if(b.getCarte() instanceof CarteJoker || b.getCarte() instanceof Carte8) {
								choisirCouleur();
							} 
							//Sinon on met fin au du joueur 
							else {
								setFinTour(false);
								plateau.setFintour(true);
							}
						}
						//Si la carte n'est pas jouable on affiche un popup d'erreur
						else {
							JOptionPane.showMessageDialog(null, "La carte  " + b.getCarte().getNumero() +" "+b.getCarte().getCouleur() +" n'est pas jouable  ! \n (Consultez les règles pour plus d'informations)", "Carte Invalide", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}

			//Ajout du panel contenant les main dans un scrollPane
			scroll.setViewportView(panelMainJoueur);
			//Ajout du scroll dans un panel intermédiaire (ne fonctionne pas sans ce panel pour des raisons obscure ...)
			JPanel jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(scroll, BorderLayout.CENTER);
			
			fenetreMain.add(jContentPane);
			fenetreMain.setVisible(true);
		}
	}
	
	/**
	 * Méthode qui va permettre d'afficher le choix de la nouvelle couleur dans une petite fenetre.
	 */
	public void choisirCouleur() {
		
		//Création de la fenetre qui permet de choisir la nouvelle couleur actuelle
		fenetreChoisirCouleur = new JFrame("Choix d'une nouvelle couleur");
		fenetreChoisirCouleur.setResizable(false);
		fenetreChoisirCouleur.setLocationRelativeTo(null);
		fenetreChoisirCouleur.setIconImage((new ImageIcon(getClass().getResource("/huitAmericain/images/logo.png"))).getImage());
		
		//Création du bouton
		choixCarreau = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/carreau.png")));
		//On n'affiche pas les bordures et le background
		choixCarreau.setFocusPainted(false);
		choixCarreau.setOpaque(false);
		choixCarreau.setBorder(null);
		//Ajout de l'évènement
		choixCarreau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//on change la couleur 
				plateau.setCouleurActuelle(Constante.CARREAU);
				//on cache la fenetre
				fenetreChoisirCouleur.setVisible(false);
				//et on termine le tour du joueur 
				setFinTour(false);
				plateau.setFintour(true);
			}
		});
		fenetreChoisirCouleur.add(choixCarreau);
		
		//Création du bouton
		choixCoeur = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/coeur.png")));
		//On n'affiche pas les bordures et le background
		choixCoeur.setFocusPainted(false);
		choixCoeur.setOpaque(false);
		choixCoeur.setBorder(null);
		//Ajout de l'évènement
		choixCoeur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//on change la couleur 
				plateau.setCouleurActuelle(Constante.COEUR);
				//on cache la fenetre
				fenetreChoisirCouleur.setVisible(false);
				//et on termine le tour du joueur
				setFinTour(false);
				plateau.setFintour(true);
			}
		});
		fenetreChoisirCouleur.add(choixCoeur);
		
		//Création du bouton
		choixPique = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/pique.png")));
		//On n'affiche pas les bordures et le background
		choixPique.setFocusPainted(false);
		choixPique.setOpaque(false);
		choixPique.setBorder(null);
		//Ajout de l'évènement
		choixPique.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//on change la couleur 
				plateau.setCouleurActuelle(Constante.PIQUE);
				//on cache la fenetre
				fenetreChoisirCouleur.setVisible(false);
				//et on termine le tour du joueur
				setFinTour(false);
				plateau.setFintour(true);
			}
		});
		fenetreChoisirCouleur.add(choixPique);
		
		//Création du bouton
		choixTrefle = new JButton(new ImageIcon(getClass().getResource("/huitAmericain/images/trefle.png")));
		//On n'affiche pas les bordures et le background
		choixTrefle.setFocusPainted(false);
		choixTrefle.setOpaque(false);
		choixTrefle.setBorder(null);
		//Ajout de l'évènement
		choixTrefle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//on change la couleur 
				plateau.setCouleurActuelle(Constante.TREFLE);
				//on cache la fenetre
				fenetreChoisirCouleur.setVisible(false);
				//et on termine le tour du joueur
				setFinTour(false);
				plateau.setFintour(true);
			}
		});
		fenetreChoisirCouleur.add(choixTrefle);
		//Ajout d'un layout
		fenetreChoisirCouleur.setLayout(new GridLayout(0,2));
		//On pack tous les composants 
		fenetreChoisirCouleur.pack();
		//Enfin on affiche la fenetre
		fenetreChoisirCouleur.setVisible(true);
	}
	
	/**
	 * Méthode qui va mettre à jour tous les composants de l'affichage du plateau
	 */
	public void actualiser() {
		
		//Si le plateau n'est pas null
		if(plateau.getJoueurActuel() != null)
			//On met à jour le label du nom du joueur
			nomJoueur.setText("Tour de " + plateau.getJoueurActuel().getNom());

		/*** On met à jour l'image de la couleur actuelle ***/
		String chaine = "/huitAmericain/images/";
		if(plateau.getCouleurActuelle() != null) {
			chaine += plateau.getCouleurActuelle() + "_50x50.png";
		} else {
			chaine += Constante.CARREAU + "_50x50.png";
		}
		
		labelLogoCouleur.setIcon(new ImageIcon(getClass().getResource(chaine)));

		/*** On met à jour l'image du sens de rotation de la partie ***/
		if(plateau.getSensRotation() == 1) {
			labelLogoSens.setIcon(new ImageIcon(getClass().getResource("/huitAmericain/images/sensNormal.png")));
		} else {
			labelLogoSens.setIcon(new ImageIcon(getClass().getResource("/huitAmericain/images/sensInverse.png")));
		}
		
		/*** Mise à jour du bouton annoncer carte (activation ou désactivation) ***/
		if(plateau.getJoueurActuel() != null && plateau.getJoueurActuel().getMain().getSize() != 2 ) {
			buttonAnnoncerCarte.setEnabled(false);
		} else {
			buttonAnnoncerCarte.setEnabled(true);
		}
		
		panelGauche.validate();
		
		panelSuperieur.removeAll();
		
		/*** On met à jour le label des joueurs adverse avec leur nombre de carte associé ***/
		JPanel pane = new JPanel();
		for(Joueur j : plateau.getListeJoueurs()) { 
			JLabel label = new JLabel();
			if(!j.getNom().equals(plateau.getJoueurActuel().getNom())) {
				label.setText(j.getNom() +" : "+ j.getMain().getSize() + " cartes");
			} else {
				label.setText("Vous : "+ j.getMain().getSize() + " cartes");
			}
			//Ajout d'un JSeparator
			JSeparator sep = new JSeparator();
			pane.add(label);
			pane.add(sep);
		}
		
		panelSuperieur.add(pane);
		panelSuperieur.validate();
	}
	
	/**
	 * Méthode qui va permettre de dessiner l'image du fond d'écran
	 */
    public void fondEcran() {
        
        panelCenter = new JPanel() {
	        private static final long serialVersionUID = 1L;
	
	        public void paint(Graphics g) {
	            try {
	                BufferedImage image;
					image = ImageIO.read(getClass().getResource("/huitAmericain/images/littleBackground.png"));
					g.drawImage(image, 0, 0, null);
	                    
	            } catch (IOException e) {
	                    e.printStackTrace();
	            }
	        }
        };
     }
}
