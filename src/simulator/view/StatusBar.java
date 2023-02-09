package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -7183028210207114767L;
	
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		 JLabel TimeText = new JLabel("Time:");
		 JLabel BodiesText = new JLabel("Bodies:");
		 JLabel LawText = new JLabel("Laws:");
		 _currTime = new JLabel("0.0");
		 _currTime.setPreferredSize(new Dimension(100,20));
		 _currLaws = new JLabel("null");
		 
		 _numOfBodies=new JLabel("0");
		 _numOfBodies.setPreferredSize(new Dimension(60,20));
		this.add(TimeText);
		this.add(_currTime);
		this.add(BodiesText);
		this.add(_numOfBodies);
		this.add(LawText);
		this.add(_currLaws);
	}
	
	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_currTime.setText(String.valueOf(time));

		_currLaws.setText(fLawsDesc);
		_numOfBodies.setText(String.valueOf(bodies.size()));
	}
	
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_currTime.setText(String.valueOf(time));
		_currLaws.setText(fLawsDesc);
		_numOfBodies.setText(String.valueOf(bodies.size()));
	}
	
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_numOfBodies.setText(String.valueOf(bodies.size()));
	}
	
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_currTime.setText(String.valueOf(time));
		_numOfBodies.setText(String.valueOf(bodies.size()));
	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText(fLawsDesc);
		
	}
}
