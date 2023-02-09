package simulator.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
        

    /**
     * 
     */
    private static final long serialVersionUID = -8270084646397289800L;
    private Controller _ctrl; //Controlador
    private boolean _stopped; //Parado
    private JButton open, exit, physics, stop, run;//Botones
    private JToolBar toolbar;
    private JSpinner spinner;
    private JTextField deltaField;
    private PhysicDialog _pdialog;//Physic selector dialog
    public ControlPanel(Controller ctrl) { //Constructor
        _ctrl = ctrl;
        _stopped = true;
        initGUI();
        _ctrl.addObserver(this);
    }
    
  
    private void initGUI( ) {
        // TODO build the tool bar by adding buttons, etc.
        this.setLayout(new BorderLayout());
        toolbar = new JToolBar();
        this.add(toolbar,BorderLayout.PAGE_START);
        //Creación de botones;
        
        //OPEN
        open = new JButton();
        open.setToolTipText("Open button");
        open.setBounds (20, 20, 5, 5);
        open.setIcon(loadImage("resources/icons/open.png"));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //PREGUNTAR
            	OpenFun();
            }
        });
        
        toolbar.add(open);
        toolbar.addSeparator();
        //PHYSIC BUTTON
        physics = new JButton();
        physics.setToolTipText("physics button");
        physics.setBounds (20, 20, 5, 5);
        physics.setIcon(loadImage("resources/icons/physics.png"));
        physics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //PREGUNTAR
                
              PhysicsFun();
            }    
 
        });
        toolbar.add(physics);
        toolbar.addSeparator();
        //RUN BUTTON
        run = new JButton();
        run.setToolTipText("run button");
        run.setBounds (20, 20, 5, 5);
        run.setIcon(loadImage("resources/icons/run.png"));
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               RunFun();
            }        
        });
        toolbar.add(run);
        toolbar.addSeparator();
        //STOP BUTTON
        stop = new JButton();
        stop.setToolTipText("stop button");
        stop.setBounds (20, 20, 5, 5);
        stop.setIcon(loadImage("resources/icons/stop.png"));
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               StopFun();
            
            }        
        });
        toolbar.add(stop);
        toolbar.addSeparator();
        JLabel stepsText = new JLabel("Steps: ");
        spinner = new JSpinner(new SpinnerNumberModel(1000,1,10000,100));
        spinner.setMaximumSize(new Dimension(80,40));
        spinner.setMinimumSize(new Dimension(80,40));
        spinner.setPreferredSize(new Dimension(80,40));
        toolbar.add(stepsText);
        toolbar.addSeparator();
        toolbar.add(spinner);
        
        
        JLabel deltaText = new JLabel("Delta-Time: ");
        deltaField = new JTextField("2500");
        deltaField.setHorizontalAlignment(JTextField.CENTER);
        deltaField.setMaximumSize(new Dimension(80,40));
        deltaField.setMinimumSize(new Dimension(80,40));
        deltaField.setPreferredSize(new Dimension(80,40));
        
        
        toolbar.addSeparator();
        toolbar.add(deltaText);
        toolbar.addSeparator();
        toolbar.add(deltaField);
        toolbar.add(Box.createGlue()); //separa todo lo que puede
        toolbar.addSeparator();
        //glue
        
        
        //Aqui va todo lo de steps, delta-time...
        
        //EXIT
        exit = new JButton();
        exit.setToolTipText("exit button");
        exit.setBounds (20, 20, 5, 5);
        exit.setIcon(loadImage("resources/icons/exit.png"));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ExitFun();
            }
        });
        toolbar.add(exit);
       
        //JSPINNER
       
        this.setSize(200, 100);
        this.setVisible(true);
    }
    

    
    protected Icon loadImage(String string) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(string));
	}

  private void OpenFun() {
        
        // Botón OPEN
        JFileChooser FileFinder = new JFileChooser("./resources/examples");
        //accion de abrir
        int Value = FileFinder.showOpenDialog(FileFinder);
        if (Value == JFileChooser.APPROVE_OPTION) {
            String path = FileFinder.getSelectedFile().getAbsolutePath();
                    try {
                        File f = new File(path);
                        _ctrl.reset();
                        _ctrl.loadBodies(new FileInputStream(f));
                        
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
        }
    }
    private void ExitFun() {
    	  //PREGUNTAR
        int opt = JOptionPane.showConfirmDialog(null, "Are sure you want to quit?");
        if (opt == 0) System.exit(0);
    }
    private void PhysicsFun() {
    	  // Botón PHYSICS
    	
  
        
    	if(_pdialog == null) {
    		_pdialog = new PhysicDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl);
    	}
    	int status = _pdialog.open();
    	if (status == 0) {
    		JOptionPane.showMessageDialog(null,"Cancelled the selection.");
		} else {
			try{
				_ctrl.setForceLaws(new JSONObject(_pdialog.getJSON()));
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Error con las leyes.");
			}
		}
    }
    
    private void StopFun() {
    	 //PREGUNTAR
        
        // Botón STOP    
          _stopped = true;
    }
    
    
    private void RunFun() {
    	 //PREGUNTAR
        // Botón RUN  
        open.setEnabled(false);
        exit.setEnabled(false);
        physics.setEnabled(false);
        run.setEnabled(false);
        deltaField.setEnabled(false);
        spinner.setEnabled(false);
        _stopped = false;
      _ctrl.setDeltaTime(Double.parseDouble(deltaField.getText())); 
       run_sim(Integer.parseInt(spinner.getValue().toString()));
    }
    
    
    private void EnableAllButtons() {
    	exit.setEnabled(true);
    	open.setEnabled(true);
    	physics.setEnabled(true);
    	run.setEnabled(true);
    	stop.setEnabled(true);
        deltaField.setEnabled(true);
        spinner.setEnabled(true);
    }
    
    
    private void run_sim(int n) {
        if ( n>0 && !_stopped ) {
            try {
                _ctrl.run(1);
            } catch (Exception e) {
                // TODO show the error in a dialog box
            	JOptionPane.showMessageDialog(null, "Error");
                // TODO enable all buttons
            	EnableAllButtons();
                _stopped = true;
                return;
            }

            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    run_sim(n-1);
                }
            });
        } else {
            _stopped = true;
            //TODO enable all buttons
            EnableAllButtons();
        }
    }
    //SimulatorObserver methods
        //...

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        // TODO Auto-generated method stub
        
        //Modifica Dt en textField
    	deltaField.setText(String.valueOf(dt));
    	
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        // TODO Auto-generated method stub
        //Modifica Dt en textField
    	deltaField.setText(String.valueOf(dt));
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDeltaTimeChanged(double dt) {
        // TODO Auto-generated method stub
        //Modifica Dt en textField
    	deltaField.setText(String.valueOf(dt));
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        // TODO Auto-generated method stub
        
    }
}
