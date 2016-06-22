package affichage;


import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import footStats.DataManager;
import footStats.JoueurStat;
import footStats.NoPlayerException;
import footStats.StatsTemps;
import footStats.StatsTempsJoueur;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SoccerWindow 
{	
	private static DataManager data;
	private static int i = 0;
	private static SoccerApplication canvasApplication;
	private static Timer time;

	private static Canvas canvas; // JAVA Swing Canvas

	private static JDialog dial;
	private static JPanel pane;

	private static JFrame frame;	
	private static JPanel panel;
	private static JButton play;
	private static JPanel lecturePanel;
	private static JLabel timeLbl;
	private static JSpinner nbPoints;
	private static JPanel playerChoicePanel;
	private static JComboBox<Integer> listeJoueurs; 
	private static JSlider timebar;
	private static int delay = 50;
	private static boolean loaded;
	
	private static JLabel temps, tag_id, pos_x, pos_y, angleVue, direction, energie, vitesse, distanceParcourue;

	private static void createNewJFrame() 
	{	
		//Dialog Loading
		dial = new JDialog(frame, "Loading...");
		pane = new JPanel();
		JLabel cautionText = new JLabel("Please wait while the data is loaded \n" + '\n');
		cautionText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(cautionText);
		dial.add(pane);
		dial.pack();

		//Timer
		time = new Timer(delay, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(i < data.getRecordTNumber())
				{
					StatsTemps t = data.getEnregT(i);
					canvasApplication.displaying = t;
					i++;
					timeLbl.setText(t.temps.toString());
					timebar.setValue(i);
					if(listeJoueurs.getSelectedIndex() != -1)
					{
						StatsTempsJoueur j;
						try {
							j = t.getJoueurEnreg((int) listeJoueurs.getSelectedItem());
							tag_id.setText("ID : " + Integer.toString(j.tag_id));
							pos_x.setText("x = " + Float.toString(j.pos_x));
							pos_y.setText("y = " + Float.toString(j.pos_y));
							angleVue.setText("Regard = " + Float.toString(j.angleVue));
							direction.setText("Direction = " + Float.toString(j.direction)); 
							energie.setText("Energie = " + Float.toString(j.energie));
							vitesse.setText("Vitesse = " + Float.toString(j.vitesse));
							distanceParcourue.setText("Distance = " + Float.toString(j.distanceParcourue));
						} catch (NoPlayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					{
						tag_id.setText("ID : --");
						pos_x.setText("x = --");
						pos_y.setText("y = --");
						angleVue.setText("Regard = --");
						direction.setText("Direction = --"); 
						energie.setText("Energie = --");
						vitesse.setText("Vitesse = --");
						distanceParcourue.setText("Distance = --");
					}
				}
				if(i == 1)
				{
					//frame.pack();
				}
			}
		});

		//Main Frame
		frame = new JFrame("Java - Graphique - IHM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) 
			{
				System.out.print("sdkjhfjsd");
				time.stop();
				canvasApplication.stop();
				try 
				{
					wait(100);
				} catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
		});

		// Create the menus
		final JMenuBar menubar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenu helpMenu = new JMenu("Help");

		final JMenu openNewFileMenu = new JMenu("Open");

		final JMenuItem quitItem = new JMenuItem("Quit");
		final JMenuItem getControlsItem = new JMenuItem("Get controls");

		final JMenuItem itemOuvrir1 = new JMenuItem("Tromso vs Stromgodset - 1");
		final JMenuItem itemOuvrir2 = new JMenuItem("Tromso vs Stromgodset - 2");
		final JMenuItem itemOuvrir3 = new JMenuItem("Tromso vs Anji - 1");
		final JMenuItem itemOuvrir4 = new JMenuItem("Tromso vs Anji - 2");

		openNewFileMenu.add(itemOuvrir1);
		openNewFileMenu.add(itemOuvrir2);
		openNewFileMenu.add(itemOuvrir3);
		openNewFileMenu.add(itemOuvrir4);

		fileMenu.add(openNewFileMenu);
		fileMenu.add(quitItem);
		helpMenu.add(getControlsItem);
		menubar.add(fileMenu);
		menubar.add(helpMenu);
		frame.setJMenuBar(menubar);

		getControlsItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				final JFrame dial = new JFrame("Controls");
				final JPanel pane = new JPanel();
				pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

				JTextArea cautionText = new JTextArea("Add some text here to describe the controls \n" + '\n');
				cautionText.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
				cautionText.setEditable(false);
				pane.add(cautionText);

				JButton okButton = new JButton("Ok");
				okButton.setSize(50, okButton.getHeight());
				okButton.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						dial.dispose();
					}
				});

				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
				buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				buttonPane.add(Box.createHorizontalGlue());
				buttonPane.add(okButton);

				pane.add(buttonPane);
				pane.add(Box.createRigidArea(new Dimension(0, 5)));
				dial.add(pane);
				dial.pack();
				dial.setLocationRelativeTo(frame);
				dial.setVisible(true);
			}
		});

		ActionListener menuListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JMenuItem menuListener = (JMenuItem) event.getSource();

				if(menuListener==quitItem)
				{
					frame.dispose();
				}
				if(menuListener==itemOuvrir1)
				{
					chargementFichier(0);
				}
				else if(menuListener==itemOuvrir2)
				{
					chargementFichier(1);
				}
				else if(menuListener==itemOuvrir3)
				{
					chargementFichier(2);
				}
				else if(menuListener==itemOuvrir4)
				{
					chargementFichier(3);
				}	
			}

		};

		quitItem.addActionListener(menuListener);
		itemOuvrir1.addActionListener(menuListener);
		itemOuvrir2.addActionListener(menuListener);
		itemOuvrir3.addActionListener(menuListener);
		itemOuvrir4.addActionListener(menuListener);

		//Left Panel

		JPanel westPanel = new JPanel(); 
		westPanel.setLayout(new BorderLayout());
		westPanel.add(new JLabel("Joueurs"), BorderLayout.NORTH);
		playerChoicePanel = new JPanel(new GridLayout(10, 2));

		westPanel.add(playerChoicePanel, BorderLayout.CENTER);
		JPanel trajectPanel = new JPanel();
		trajectPanel.setLayout(new BoxLayout(trajectPanel, BoxLayout.Y_AXIS));
		JCheckBox ligne = new JCheckBox("Afficher les trajectoires", false);
		ligne.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				canvasApplication.drawTraject = ((JCheckBox) arg0.getSource()).isSelected();
			}
		});
		trajectPanel.add(ligne);
		JLabel lbl = new JLabel("Nombre de Points");
		trajectPanel.add(lbl);
		nbPoints = new JSpinner(new SpinnerNumberModel(1000, 0, 5000, 10));
		nbPoints.addChangeListener(new ChangeListener() 
		{	
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				SpinnerNumberModel model = (SpinnerNumberModel) nbPoints.getModel();
				canvasApplication.nbPoints = model.getNumber().intValue();
			}
		});
		trajectPanel.add(nbPoints);

		westPanel.add(trajectPanel, BorderLayout.SOUTH);
		//Central Panel
		panel = new JPanel(new BorderLayout());
		panel.add(westPanel, BorderLayout.WEST);
		// Add the canvas to the panel
		panel.add(canvas, BorderLayout.CENTER);
		frame.add(panel);

		lecturePanel = new JPanel();
		lecturePanel.setLayout(new FlowLayout());

		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				i = 0;
				time.getActionListeners()[0].actionPerformed(null);
				play.setText("Lecture");
				time.stop();
			}
		});
		lecturePanel.add(stop);
		play = new JButton("Pause");
		play.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				JButton b = (JButton) (arg0.getSource());
				if(b.getText().equals("Lecture"))
				{
					time.start();
					b.setText("Pause");
				}
				else
				{
					time.stop();
					b.setText("Lecture");
				}
			}
		});

		lecturePanel.add(play);

		timebar = new JSlider(0);
		timebar.setValue(0);
		timebar.addChangeListener(new ChangeListener() 
		{	
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				i = ((JSlider) e.getSource()).getValue();

			}
		});
		lecturePanel.add(timebar);
		timeLbl = new JLabel();
		lecturePanel.add(timeLbl);

		ButtonGroup group = new ButtonGroup();

		JToggleButton speed = new JToggleButton("x1");
		JToggleButton speed2 = new JToggleButton("x2");
		JToggleButton speed4 = new JToggleButton("x4");
		JToggleButton speed8 = new JToggleButton("x8");
		JToggleButton speed16 = new JToggleButton("x16");

		speed.setName("1");
		speed2.setName("2");
		speed4.setName("4");
		speed8.setName("8");
		speed16.setName("16");

		speed.addActionListener(speedListener);
		speed2.addActionListener(speedListener);
		speed4.addActionListener(speedListener);
		speed8.addActionListener(speedListener);
		speed16.addActionListener(speedListener);

		group.add(speed);
		group.add(speed2);
		group.add(speed4);
		group.add(speed8);
		group.add(speed16);

		lecturePanel.add(speed);
		lecturePanel.add(speed2);
		lecturePanel.add(speed4);
		lecturePanel.add(speed8);
		lecturePanel.add(speed16);

		frame.add(lecturePanel, BorderLayout.SOUTH);

		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BorderLayout());
		listeJoueurs = new JComboBox<Integer>();	
		listeJoueurs.setEditable(false);
		listeJoueurs.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if(loaded)
				{
					try 
					{
						canvasApplication.heatMapPlayer = data.getJStatsTot((int) listeJoueurs.getSelectedItem());
					} catch (NoPlayerException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		JPanel heatMapPanel = new JPanel();
		heatMapPanel.setLayout(new BoxLayout(heatMapPanel, BoxLayout.Y_AXIS));
		heatMapPanel.add(listeJoueurs);
		
		JCheckBox choixRelief = new JCheckBox("Relief");
		choixRelief.setSelected(true);
		choixRelief.addItemListener(new ItemListener() 
		{	
			@Override
			public void itemStateChanged(ItemEvent arg0) 
			{
				canvasApplication.relief = ((JCheckBox) arg0.getSource()).isSelected();
			}
		});
		heatMapPanel.add(choixRelief);
		playerPanel.add(heatMapPanel, BorderLayout.NORTH);
		
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		tag_id = new JLabel();
		pos_x = new JLabel();
		pos_y = new JLabel();
		angleVue = new JLabel();
		direction = new JLabel(); 
		energie  = new JLabel(); 
		vitesse  = new JLabel(); 
		distanceParcourue = new JLabel();
		tag_id.setText("ID : --");
		pos_x.setText("x = --");
		pos_y.setText("y = --");
		angleVue.setText("Regard = --");
		direction.setText("Direction = --"); 
		energie.setText("Energie = --");
		vitesse.setText("Vitesse = --");
		distanceParcourue.setText("Distance = --");
		statsPanel.add(Box.createVerticalGlue());
		statsPanel.add(tag_id);
		statsPanel.add(pos_x);
		statsPanel.add(pos_y);
		statsPanel.add(angleVue);
		statsPanel.add(direction);
		statsPanel.add(energie);
		statsPanel.add(vitesse);
		statsPanel.add(distanceParcourue);
		statsPanel.add(Box.createVerticalGlue());
		statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		playerPanel.add(statsPanel, BorderLayout.CENTER);
		frame.add(playerPanel, BorderLayout.EAST);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		listeJoueurs.setSize(100, 50);

		chargementFichier(0);
	}

	public static ActionListener speedListener = new ActionListener() 
	{	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			time.setDelay(delay/(Integer.parseInt(((JToggleButton) e.getSource()).getName())));
		}
	}; 

	public static ItemListener itemStateChanged = new ItemListener() 
	{	
		@Override
		public void itemStateChanged(ItemEvent e) 
		{
			JCheckBox cbx = (JCheckBox) e.getSource();
			try {
				canvasApplication.getPlayer(Integer.parseInt(cbx.getText())).toDisplay = cbx.isSelected();
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (NoPlayerException e1) {
				System.err.println("Pas de joueur : " + cbx.getText());
			}
		}
	};

	public static void chargementFichier(int index)
	{
		loaded = false;
		frame.setEnabled(false);
		dial.setLocationRelativeTo(frame);
		dial.setVisible(true);
		dial.setAlwaysOnTop(true);
		time.stop();
		i = 0;
		data = new DataManager();
		data.lireFichier(index);
		playerChoicePanel.removeAll();
		listeJoueurs.removeAllItems();
		int i = 1,nb = 0;
		JoueurStat j;
		while(nb != data.getListeJoueur().size())
		{
			try 
			{
				j = data.getJStatsTot(i);
				JCheckBox c = new JCheckBox(Integer.toString(j.getID()));
				c.setSelected(true);
				playerChoicePanel.add(c);
				c.addItemListener(itemStateChanged);
				listeJoueurs.addItem(j.getID());
				nb++;
			} catch (NoPlayerException e) {}
			i++;
		}
		listeJoueurs.setSelectedIndex(-1);
		canvasApplication.heatMapPlayer = null;
		canvasApplication.createJoueurs(data.getListeJoueur());
		dial.setVisible(false);
		timebar.setMaximum(data.getRecordTNumber());
		timebar.setValue(0);
		loaded = true;
		frame.setEnabled(true);
		time.restart();
	}

	public static void main(String[] args)
	{
		// create new JME appsettings
		AppSettings settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setSamples(8);
		canvasApplication = new SoccerApplication();
		canvasApplication.setSettings(settings);
		canvasApplication.setShowSettings(false);
		settings.setFrameRate(60);
		settings.setVSync(true);
		canvasApplication.setDisplayStatView(false);
		canvasApplication.setDisplayFps(false);

		canvasApplication.createCanvas();

		JmeCanvasContext ctx = (JmeCanvasContext) canvasApplication.getContext();
		canvas = ctx.getCanvas();
		Dimension dim = new Dimension(settings.getWidth(), settings.getHeight());
		canvas.setPreferredSize(dim);

		// Create the JFrame with the Canvas on the middle
		createNewJFrame();
		canvasApplication.setPauseOnLostFocus(false);
	}
}
