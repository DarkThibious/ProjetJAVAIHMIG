package tutoriel;

import FootStats.DataManager;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class WindowedTest 
{	
	private static PlayGroundTest canvasApplication;
	private static Timer time;
	
	private static Canvas canvas; // JAVA Swing Canvas
	
	private static JFrame frame;
	private static JPanel panel;
	private static JPanel lecturePanel;
	
	
	
	private static void createNewJFrame() {

		time = new Timer(50, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(canvasApplication.loaded)
				{
					if(canvasApplication.i<canvasApplication.data.listeTemps.size())
					{
						System.out.println("Increment");
						canvasApplication.i++;
						canvasApplication.frozen = false;	
					}
				}
			}
		});
		time.start();
		frame = new JFrame("Java - Graphique - IHM");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{
				
			}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				System.out.print("sdkjhfjsd");
				canvasApplication.stop();
				frame.dispose();
			}
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		// Create the menus
		final JMenuBar menubar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenu helpMenu = new JMenu("Help");

		final JMenu openNewFileMenu = new JMenu("Open");
		
		final JMenuItem quitItem = new JMenuItem("Quit");
		final JMenuItem getControlsItem = new JMenuItem("Get controls");

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
		
		panel = new JPanel(new BorderLayout());
		
		JButton Test = new JButton("Swing Components");
		panel.add(Test, BorderLayout.WEST);
		
		// Add the canvas to the panel
		panel.add(canvas, BorderLayout.CENTER);
		
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static DataManager chargerFichier(int index)
	{
		JDialog g = new JDialog();
		DataManager da = new DataManager();
		g.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		g.setLayout(new BorderLayout(20,20));
		g.add(new JLabel("Loading..."), BorderLayout.CENTER);
		g.toFront();
		g.setAlwaysOnTop(true);
		g.pack();
		g.setLocationRelativeTo(null);
		g.setLocation(g.getX()-g.getWidth()/2, g.getY()-g.getHeight()/2);
		g.setVisible(true);
		g.dispose();
		return da;
	}

	public static void main(String[] args){
		// create new JME appsettings
		AppSettings settings = new AppSettings(true);
		settings.setResolution(1280, 800);
		settings.setSamples(8);
		canvasApplication = new PlayGroundTest();
		canvasApplication.setSettings(settings);
		canvasApplication.setShowSettings(false);
		//app.start();
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
