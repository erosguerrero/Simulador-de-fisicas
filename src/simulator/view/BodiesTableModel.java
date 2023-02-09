package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6564715995880199027L;
	// ...
 	private List<Body> _bodies;
 	private String[] columnNames;
	BodiesTableModel(Controller ctrl) {
	_bodies = new ArrayList<>();
	ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
	// TODO complete
		return _bodies.size();
	}
	
	@Override
	public int getColumnCount() {
	// TODO complete
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
	// TODO complete
		//Body b = _bodies.get(column);
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	// TODO complete
		Body b = _bodies.get(rowIndex);
		String s = "";
		switch(columnIndex) {//switch segun la columna
			case 0:{//columna 0 id
				return b.getId();
			}		
			
			case 1:{//masa
				return b.getMass();
			}
			
			case 2:{//posicion
				return b.getPosition();
			}		
			
			case 3:{//velocidad
				return b.getVelocity();
			}			
			
			case 4:{//fuerza
				return b.getForce();
			}
		}
	
		return s;
	}
	// SimulatorObserver methods
	// ...

	/*
	En los métodos como observador, cuando cambia el estado (por ejemplo en onAdvance(), onRegister, onBodyAdded y onReset) es necesario en primer lugar actualizar el valor del campo _bodies
y después llamar a fireTableStructureChanged() para notificar a la correspondiente JTable que hay
cambios en la tabla (y por lo tanto es necesario redibujarla).
	 */
	
	public void setColumnNames(String[] columnNames) { //sobrecargar la funcion para el nombre de las columnas
        this.columnNames = columnNames; 
        this.fireTableStructureChanged();
    }
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		this.fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		this.fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		this.fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		this.fireTableStructureChanged();
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}
