package tutoriel;

import FootStats.DataManager;
import FootStats.JoueurStat;
import FootStats.NoPlayerException;
import FootStats.StatsTemps;
import FootStats.StatsTempsJoueur;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import de.lessvoid.nifty.controls.CheckBox;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;
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
	private static JPanel lecturePanel;
	private static JPanel playerChoicePanel;
	private static ArrayList<JCheckBox> players; 
	private static JSlider timebar;
	
	
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
		time = new Timer(50, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(i < data.getRecordTNumber())
				{
					StatsTemps t = data.getEnregT(i);
			//		update(t);
					canvasApplication.displaying = t;
					i++;
					timebar.setValue(i);
				}
				if(i == 1)
				{
					frame.pack();
				}
			}
		});
		
		//Main Frame
		frame = new JFrame("Java - Graphique - IHM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				time.stop();
				System.out.print("sdkjhfjsd");
				canvasApplication.stop();
				frame.dispose();
				System.exit(0);
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
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
		final JMenuItem itemOuvrir5 = new JMenuItem("Other...");

		openNewFileMenu.add(itemOuvrir1);
		openNewFileMenu.add(itemOuvrir2);
		openNewFileMenu.add(itemOuvrir3);
		openNewFileMenu.add(itemOuvrir4);
		openNewFileMenu.add(itemOuvrir5);
		
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
 					
 				}
 				else if(menuListener==itemOuvrir1)
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
 				else if(menuListener==itemOuvrir5)
 				{
 					JFileChooser file = new JFileChooser();
 					file.showOpenDialog(frame);
 					file.getSelectedFile();
 					file.setVisible(true);
 				}
 			}
 		};
 		
     	quitItem.addActionListener(menuListener);
     	itemOuvrir1.addActionListener(menuListener);
     	itemOuvrir2.addActionListener(menuListener);
     	itemOuvrir3.addActionListener(menuListener);
     	itemOuvrir4.addActionListener(menuListener);
     	itemOuvrir5.addActionListener(menuListener);
     	
     	//Left Panel
     	
     	JPanel westPanel = new JPanel(); 
     	westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
     	westPanel.add(new JLabel("Joueurs"));
		playerChoicePanel = new JPanel(new GridLayout(10, 2));
		
		westPanel.add(playerChoicePanel);
		westPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		westPanel.add(Box.createGlue());
		
		//Central Panel
		panel = new JPanel(new BorderLayout());
		panel.add(westPanel, BorderLayout.WEST);
		// Add the canvas to the panel
		panel.add(canvas, BorderLayout.CENTER);
		frame.add(panel);
		
		lecturePanel = new JPanel();
		lecturePanel.setLayout(new FlowLayout()); 
		JButton play = new JButton("lECTURE");
		lecturePanel.add(play);
		timebar = new JSlider(0);
		timebar.addChangeListener(new ChangeListener() 
		{	
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				i = ((JSlider) e.getSource()).getValue();
				
			}
		});
		lecturePanel.add(timebar);
		frame.add(lecturePanel, BorderLayout.SOUTH);
		
		frame.pack();
		playerChoicePanel.setPreferredSize(new Dimension(frame.getWidth()/6,frame.getHeight()*3/4));
		playerChoicePanel.setSize(playerChoicePanel.getPreferredSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		chargementFichier(0);
		
		for(JoueurStat j : data.getListeJoueur())
		{
			JCheckBox c = new JCheckBox(Integer.toString(j.getID()));
			c.setSelected(true);
			players.add(c);
			c.setVisible(true);
			playerChoicePanel.add(c);
		}
	}
	
	/*public static void update(StatsTemps t)
	{
		boolean exist;
		
		for(StatsTempsJoueur j : t.listeStatsTJ)
		{
			exist = false;
			for(JCheckBox c : players)
			{
				if(c.getText().equals(Integer.toString(j.tag_id)))
				{
					exist = true;
					break;
				}
			}
			if(!exist)
			{
				JCheckBox c = new JCheckBox(Integer.toString(j.tag_id));
				c.setSelected(true);
				players.add(c);
				c.setVisible(true);
				playerChoicePanel.add(c);
			}
		}	
		for(JCheckBox cbx : players)
		{
			try 
			{
				canvasApplication.getPlayer(Integer.parseInt(cbx.getText())).toDisplay = cbx.isSelected();
			} catch (NumberFormatException e) 
			{
				e.printStackTrace();
			} catch (NoPlayerException e) 
			{
				System.err.println("No player with that id : "+ cbx.getText());
			}
		}
		//frame.pack();
		playerChoicePanel.setSize(playerChoicePanel.getPreferredSize());
	}*/
	
	public static void chargementFichier(int index)
	{
		players = new ArrayList<JCheckBox>();
		dial.setLocationRelativeTo(frame);
		dial.setVisible(true);
		dial.setAlwaysOnTop(true);
		time.stop();
		i = 0;
		data = new DataManager();
		data.lireFichier(index);
		dial.setVisible(false);
		timebar.setMaximum(data.getRecordTNumber());
		timebar.setValue(0);
		time.restart();
	}

	public static void main(String[] args)
	{
		// create new JME appsettings
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1280, 600);
		settings.setSamples(8);
		canvasApplication = new SoccerApplication();
		canvasApplication.setSettings(settings);
		canvasApplication.setShowSettings(false);
		canvasApplication.createCanvas();
		settings.setFrameRate(60);
		settings.setVSync(true);
		canvasApplication.setDisplayStatView(false);
		canvasApplication.setDisplayFps(false);

		JmeCanvasContext ctx = (JmeCanvasContext) canvasApplication.getContext();
		canvas = ctx.getCanvas();
		Dimension dim = new Dimension(settings.getWidth(), settings.getHeight());
		canvas.setPreferredSize(dim);

		// Create the JFrame with the Canvas on the middle
		createNewJFrame();
		canvasApplication.setPauseOnLostFocus(false);
	}
}
