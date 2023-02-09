package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import simulator.control.Controller;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8877948025931264431L;
	// ...
	Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		//<3
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setLocation(500, 100); //posicion donde aparece el main window al ejecutar el programa
		
		setContentPane(mainPanel);
		
		//ContentPanel
		JPanel contentPanel = new JPanel();
    	contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(600, 600));
		
		//Control Panel
		ControlPanel controlPanel = new ControlPanel(_ctrl); //toolbar
		controlPanel.setPreferredSize(new Dimension(700, 50)); //tamaño del control panel
		this.add(controlPanel, BorderLayout.PAGE_START);
		//Border b = BorderFactory.createLineBorder(Color.black, 2);
		
		
		//Bodies Table
		BodiesTable bodiestable = new BodiesTable(_ctrl);
	   	 bodiestable.setPreferredSize(new Dimension(700, 200));
	   	 

	 	contentPanel.add(bodiestable);
	   	 mainPanel.add(contentPanel, BorderLayout.CENTER);
	   
		//Viewer
		Viewer view = new Viewer(_ctrl);
		view.setPreferredSize(new Dimension(700,300));
		this.add(view, BorderLayout.AFTER_LAST_LINE);
		
		//Status Bar
		 contentPanel.add(new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	   	 StatusBar statusBar = new StatusBar(_ctrl);
	   	 statusBar.setPreferredSize(new Dimension(100, 30));
	   	 mainPanel.add(statusBar,BorderLayout.PAGE_END);

		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
}
