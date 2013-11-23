package huitAmericain.affichage;

import huitAmericain.Plateau;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.text.html.HTMLEditorKit;

/**
 * <b>FenetrePrincipale est la classe le fenetre qui s'affiche lors du lancement du fichier.</b>
 * <p>
 * 	Cette classe h�rite de JFrame et contient plusieurs �l�ments d'affichage (des menus, ...)
 * </p>
 * @author Thibault Jacquemet & Antoine Bl�chet
 *
 */
public class FenetrePrincipale {

	/**
	 * La fenetre principale qui va afficher tous les composants de jeu
	 */
	private JFrame fenetre;
	
	/**
	 * Le plateau qui sera utiliser pour afficher les diff�rents composant
	 */
	private static Plateau plateau;
	
	/**
	 * Panel principale qui qui va servir � afficher les diff�rent labels et boutons de jeu
	 */
	private JPanel panelPrincipal;
	/**
	 * Image qui s'affiche lors du lancement de la partie
	 */
	private JLabel imagePrincipale;
	
	/**
	 * Composant qui sert � afficher une page html dans une fenetre
	 */
	private JEditorPane htmlBox;
	/**
	 * ScrollPane qui sert � faire d�filer les info concernant les r�gles de la partie
	 */
	private JScrollPane scroll;
	/**
	 * Fenetre qui affiche toutes les r�gles
	 */
	private JFrame affichageRegles;
	
	/**
	 * MenuBar de la fenetre
	 */
	private JMenuBar menuBar;
	/**
	 * JMenu fichier
	 */
	private JMenu menuFichier;
	/**
	 * JMenu Aide
	 */
	private JMenu menuAide;
	/**
	 * JMenu nouveau qui permet de lancer une partie soit console soit graphique
	 */
	private JMenu menuNouveau;
	/**
	 * Menu � propos
	 */
	private JMenu menuAbout;
	/**
	 * Menu qui permet de d�marer une partie en mode graphique
	 */
	private JMenuItem itemPartie;
	/**
	 * Menu qui permet de d�marer une partie en mode console
	 */
	private	JMenuItem itemConsole;
	/**
	 * Menu qui permet d'enregistrer une partie
	 */
	private	JMenuItem itemSave ;
	/**
	 * Menu qui permet d'enregistrer sous une partie
	 */
	private	JMenuItem itemSaveAs;
	/**
	 * Menu qui permet de charger une partie
	 */
	private JMenuItem iteamLoad;
	/**
	 * Menu qui permet de quitter la partie ou le jeu
	 */
	private	JMenuItem iteamQuitter;
	/**
	 * Menu qui affiche les r�gles
	 */
	private JMenuItem itemRegles;
	/**
	 * Fichier dans lequel est stock� le plateau
	 */
	private File file;
	/**
	 * FileChooser qui permet de charger les fichiers ou de sauvegarder les fichiers � l'emplacement voulu
	 */
	private JFileChooser fileChooser;
	/**
	 * Filtre qui permet de ne choisir que les fichiers ayant l'extension lo02
	 */
	private Filtre filtre;
	/**
	 * Bool�en qui permet de savoir si c'est la premi�re partie que l'on d�marre ou non 
	 */
	private static boolean firstLoad = true;
	/**
	 * Bool�en qui permet de savoir si la partie est termin� ou non 
	 */
	private static boolean endGame = true;
	
	/**
	 * Constructeur de la fenetre principale
	 * On y cr�e les diff�rent composant � savoir le menu, l'image principale, ...
	 */
	public FenetrePrincipale() {
		//Cr�ation de la fenetre principale
		fenetre = new JFrame("Huit Am�ricain");
		
		//Ajout de la menuBar
		fenetre.setJMenuBar(createMenu());
		
        fenetre.setSize(800, 600);
        fenetre.setIconImage((new ImageIcon(getClass().getResource("/huitAmericain/images/logo.png"))).getImage());
       
        //Positionne la fenetre au centre
        fenetre.setLocationRelativeTo(null);
        
        //Ferme la fenetre lorsqu'on clique sur "Fermer" !
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);

        //Cr�ation de l'image lors du d�marage de l'application
        imagePrincipale = new JLabel();
        imagePrincipale.setIcon(new ImageIcon(getClass().getResource("/huitAmericain/images/logo8US.png")));
        imagePrincipale.setSize(400, 173);
        imagePrincipale.setHorizontalAlignment(JLabel.CENTER);
        imagePrincipale.setVerticalAlignment(JLabel.CENTER);
        
        //Cr�ation du background lors du lancement de la partie
		Image fond;	
		
		fond = new ImageIcon(getClass().getResource("/huitAmericain/images/background.png")).getImage();
		
		try {
			MediaTracker mt = new MediaTracker(fenetre);
			mt.addImage(fond,0);
			mt.waitForAll();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		fenetre.setContentPane(new ContentPane(fond));
        
        fenetre.setLayout(new BorderLayout());
        fenetre.add(imagePrincipale, BorderLayout.CENTER);
        fenetre.setVisible(true);
	}
	
	/**
	 * <b>Classe ContentPane</b>
	 * Classe interne qui permet de dessiner le background sur la fenetre d'origine
	 * @author Thibault Jacquemet & Antoine Bl�chet
	 *
	 */
	private class ContentPane extends JPanel{
		
		/**
		 * Identifiant de s�rialisation
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Image qui sera charg� en background
		 */
		private Image image;
		
		/**
		 * Constructeur du ContentPane
		 * @param leFond L'image qui sera charg� en fond
		 */
		public ContentPane(Image leFond){
			super();
			image = leFond;
		}
		/**
		 * M�thode qui dessine le composant sur le background
		 */
		public void paintComponent(Graphics g){
			g.drawImage(image,0,0,null);
		}
	}
	
	/**
	 * Recup�re le PanelPrincipal du plateau
	 * @return le panel principal
	 */
	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}

	/**
	 * R�cup�re le plateau
	 * @return le plateau du jeu
	 */
	public static Plateau getPlateau() {
		return plateau;
	}

	/**
	 * Met � jour le plateau du jeu permet le chargement
	 * @param plateau Le Plateau du jeu 
	 */
	public static void setPlateau(Plateau plateau) {
		FenetrePrincipale.plateau = plateau;
	}
	
	/**
	 * M�thode qui permet de cr�er tous les menus et de les ajouter � la menuBar
	 * @return le JMenuBar associ� � la fenetre voulu
	 */
	public JMenuBar createMenu() {
	
		/***Cr�ation des diff�rents Menu ***/
		menuBar = new JMenuBar();
		
		menuFichier = new JMenu("Fichier");
		menuAide = new JMenu("Aide");
		menuAbout = new JMenu("A Propos");
		
		menuNouveau =  new JMenu("Nouvelle...");
		itemPartie = new JMenuItem("Partie");
		itemPartie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		itemConsole = new JMenuItem("Partie console");
		itemConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		
		itemSave =  new JMenuItem("Enregistrer");
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		itemSaveAs =  new JMenuItem("Enregistrer sous...");
		iteamLoad =  new JMenuItem("Charger...");
		iteamLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		iteamQuitter =  new JMenuItem("Quitter");
		itemRegles =  new JMenuItem("R�gles du jeu");
		
		//Cr�ation du JFileChooser qui va permettre d'enregistrer et de charger une partie
		fileChooser = new JFileChooser();
		//Cr�ation du filtre qui va permettre de ne prendre que les fichiers dont l'extension sera en .lo02
		filtre = new Filtre(".lo02", "Fichier Jeu de 8 Am�ricain");
		
		//Ajout du filtre pour ne prendre que des fichier de type LO02
		this.fileChooser.addChoosableFileFilter(filtre);
		
		//Ajout de l'�v�nement sur le bouton d�marer une nouvelle partie graphique
		itemPartie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AffichageGraphique ag;
				//Si il n'y a pas de partie en cours ou qu'elle est termnin� alors on peut en d�marer une nouvelle
				if(endGame) {
					endGame = false;
					imagePrincipale.setVisible(false);
					//On cr�e le plateau
			        plateau = Plateau.getInstance();
			        //On cr�e l'affichage graphique
					ag= new AffichageGraphique(fenetre, plateau);
					//Puis on param�tre la partie
					new Parametrage(plateau, ag);
					//Enfin on d�marre la partie
					ag.DebuterPartieGraphique();
					
					//Si c'est la premi�re fois que l'on lance une partie depuis que le jeu est lanc� 
					//On cr�e et on ajoute les composant qui seront modifi�
					if(firstLoad) {
						ag.ajoutComposant();
						firstLoad = false;
					}
				} 
				//Sinon on affiche un popup d'erreur
				else  {
					String mess = "Une partie est d�j� en cours vous devez d'abord la terminer";
					JOptionPane.showMessageDialog(null, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		//Ajout d'un �v�nement qui permet de lancer une partie en mode console
		itemConsole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Si il n'y a pas de partie en cours ou qu'elle est termnin� alors on peut en d�marer une nouvelle
				if(endGame) {
					plateau = Plateau.getInstance();
					fenetre.setVisible(false);
					//On cr�e un nouvelle affichage console
					new AffichageConsole(fenetre, plateau);
				} 
				//Sinon on affiche un popup d'erreur
				else  {
					String mess = "Une partie est d�j� en cours vous devez d'abord la terminer";
					JOptionPane.showMessageDialog(null, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Lors du clic sur le bouton on affiche la fenetre des regles du jeu
    	itemRegles.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AffichageRegles();
			}    		    		
    	});
		
    	//Lors du clic  sur le menu A propos on affiche un popup qui affiche les cr�ateurs du jeu
    	menuAbout.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String mess = "Ce jeu a �t� r�alis� dans le cadre de l'UV LO02 de l'UTT \n et a �t� concu par Bl�chet Antoine et Jacquemet Thibault.";
				JOptionPane.showMessageDialog(null, mess, "A propos", JOptionPane.INFORMATION_MESSAGE);
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
    	
    	//Lors du clic sur cet �v�nement on quitte le jeu
		iteamQuitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}				
		});
		
		// Lors du clic sur le bouton sauvegarder si la partie n'a jamais �t� enregistrer alors on appelle la fonction enregistrer sous
		//Sinon on enregistre la fichier � l'emplacement de l'ancien
		itemSave.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				ObjectOutputStream oos ;
				//S'il ne s'agit pas du premier enregistrement !
				if(file != null){
					try {
						//Cr�ation du ObjectOutputStream
						oos = new ObjectOutputStream(new FileOutputStream(file));
						//On �crit le plateau actuel dans le fichier
						oos.writeObject(getPlateau());
						//On ferme le fichier
						oos.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//Sinon on demande le nom du fichier
				else{
					if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						//On r�cup�re le fichier contenu dans le filechooser
						file = fileChooser.getSelectedFile();
						//Si l'extension est valide
						if(fileChooser.getFileFilter().accept(file))
						{
							try {
								//Cr�ation du ObjectOutputStream
								oos = new ObjectOutputStream(new FileOutputStream(file));
								//On �crit le plateau actuel dans le fichier
								oos.writeObject(getPlateau());
								//On ferme le fichier
								oos.close();
								
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}						
						else{
							//Si vous n'avez pas sp�cifi� une extension valide ! 
							JOptionPane.showMessageDialog(null, "Erreur d'extension de fichier ! \nVotre sauvegarde a �chou� !", "Erreur", JOptionPane.ERROR_MESSAGE);
						}						
					}
				}
			}			
		});		
		
		//Ajout de l'�v�nement lors du clic su le bouton enregister sous
		itemSaveAs.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				//On demande l'emplacement du fichier et son nom puis on enregistre le plateau
				if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					//Si l'extension est valide
					if(fileChooser.getFileFilter().accept(file)){
						try {
							//Cr�ation du ObjectOutputStream
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
							//On �crit le plateau actuel dans le fichier
							oos.writeObject(getPlateau());
							//On ferme le fichier
							oos.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						//Si vous n'avez pas sp�cifi� une extension valide ! 
						JOptionPane.showMessageDialog(null, "Erreur d'extension de fichier ! \nVotre sauvegarde a �chou� !", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}			
		});

		// Ajout de l'�v�nement lors du clic sur le chargement d'une partie
		iteamLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(fileChooser.showOpenDialog(null) ==JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					if(fileChooser.getFileFilter().accept(file))
					{
						try {
							AffichageGraphique ag;
							//Si aucune partie n'est en cours
							if(endGame) {
								endGame = false;
								imagePrincipale.setVisible(false);
								
								//On charge le plateau � partir du fichier
								ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
								setPlateau((Plateau)ois.readObject());
								ois.close();
								
								// On d�mare la partie 
								ag= new AffichageGraphique(fenetre, plateau);
								ag.DebuterPartieGraphique();
								//Si aucune partie n'a �t� charg� avant on affiche les composant graphique
								if(firstLoad) {
									ag.ajoutComposant();
									firstLoad = false;
								}
								//Puis on d�mare le thread dans plateau pour pouvoir reprendre la partie en cours
								plateau.jouerPartie();
								ag.actualiser();
							} 
							//Sinon si il y a une erreur
							else  {
								String mess = "Une partie est d�j� en cours vous devez d'abord la terminer";
								JOptionPane.showMessageDialog(null, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Erreur d'extension de fichier ! \nVotre chargement a �chou� !", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		/*** Ajouts des Menu dans le JMenuBar ***/
		
		menuNouveau.add(itemPartie);
		menuNouveau.add(itemConsole);
		
		menuFichier.add(menuNouveau);
		menuFichier.addSeparator();
		menuFichier.add(itemSave);
		menuFichier.add(itemSaveAs);
		menuFichier.add(iteamLoad);
		menuFichier.addSeparator();
		menuFichier.add(iteamQuitter);
		
		menuAide.add(itemRegles);
		
		menuBar.add(menuFichier);
		menuBar.add(menuAide);
		menuBar.add(menuAbout);
		
		return menuBar;
	}
	
	/**
	 * M�thode qui cr�e une fenetre qui est compos� d'un composant html qui va servir � afficher toutes les r�gles du jeu de 8 Am�rician
	 */
	public void AffichageRegles(){
		
		//Cr�ation de la fenetre
		affichageRegles = new JFrame();
		//Ajout de l'icone
		affichageRegles.setIconImage((new ImageIcon(getClass().getResource("/huitAmericain/images/logo.png"))).getImage());
		affichageRegles.setSize(600, 400);
		affichageRegles.setTitle("R�gles du huit am�ricain");
		//Place la fenetre au centre de l'ecran 
		affichageRegles.setLocationRelativeTo(null);
				
		//Cr�ation du composant permettant l'affichage du HTML
		htmlBox = new JEditorPane();
		htmlBox.setEditable(false);
		htmlBox.setEditorKit(new HTMLEditorKit());	
		
		try {
			htmlBox.setPage(getClass().getResource("/huitAmericain/affichage/Regles.html"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		//Ajout de l'htmlBox dans un ScrollPane
		scroll = new JScrollPane(htmlBox);
		affichageRegles.getContentPane().add(scroll, BorderLayout.CENTER);
		affichageRegles.setVisible(true);
	}
	
	/**
	 * M�thode qui permet de sp�cifier � l'affichage que la partie en cours est termin�e
	 */
	public static void setEndGame() {
		endGame = true;
	}
	
	/**
	 * M�thode qui r�cup�re l'�tat de la partie 
	 * @return le bool�en contenant les information sur la partie (Si c'est la premi�re fois que le jeu est lanc� ou non)
	 */
	public static boolean isFirstLoad(){
		return firstLoad;
	}
}
